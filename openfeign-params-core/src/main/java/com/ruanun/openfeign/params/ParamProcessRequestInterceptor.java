package com.ruanun.openfeign.params;

import com.ruanun.openfeign.params.strategys.IParamGettingStrategy;
import com.ruanun.openfeign.params.strategys.IParamSettingStrategy;
import com.ruanun.openfeign.params.strategys.StrategyContainer;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.util.StringUtils;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ruan
 */
@Slf4j
public class ParamProcessRequestInterceptor implements RequestInterceptor, Ordered {

    private final Map<HttpMethod, List<ParamConfig>> paramMethodMapping;
    private final StrategyContainer strategyContainer;

    private final OpenFeignParamsConfig openFeignParamsConfig;
    private final PathPatternParser pathPatternParser = new PathPatternParser();

    public ParamProcessRequestInterceptor(OpenFeignParamsConfig openFeignParamsConfig, StrategyContainer strategyContainer) {
        this.strategyContainer = strategyContainer;
        this.paramMethodMapping = new HashMap<>();
        this.openFeignParamsConfig = openFeignParamsConfig;
        initParamMethodMapping(openFeignParamsConfig);
    }

    private void initParamMethodMapping(OpenFeignParamsConfig openFeignParamsConfig) {
        for (Map.Entry<HttpMethod, Set<String>> entry : openFeignParamsConfig.getParamMethodMapping().entrySet()) {
            List<ParamConfig> params = entry.getValue().stream()
                    .map(s -> openFeignParamsConfig.getParamConfigs().get(s))
                    .filter(Objects::nonNull)
                    .flatMap(Set::stream)
                    .distinct().filter(e -> {
                        if (!e.isValid()) {
                            log.warn("invalid param config: {}", e);
                        }
                        return e.isValid();
                    }).collect(Collectors.toList());
            paramMethodMapping.put(entry.getKey(), params);
        }
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String feignUrl = getFeignUrl(requestTemplate);
        if (openFeignParamsConfig.getInterceptPaths().stream().noneMatch(pattern -> isMatchesPath(feignUrl, pattern))) {
            return;
        }
        for (ParamConfig paramConfig : paramMethodMapping.get(HttpMethod.resolve(requestTemplate.method()))) {
            if (!isMatchParamPath(feignUrl, paramConfig)) {
                continue;
            }
            IParamGettingStrategy paramGetting = strategyContainer.getParamGetting(paramConfig.getSourceType());
            if (Objects.isNull(paramGetting)) {
                log.warn("can not find Strategy for source type: {}", paramConfig.getSourceType());
                continue;
            }
            Object paramValue = paramGetting.handle(paramConfig, requestTemplate);
            log.debug("paramValue: {} for paramConfig: {}", paramValue, paramConfig);
            if (Objects.isNull(paramValue)) {
                log.debug("paramValue is null: {}", paramConfig);
                continue;
            }
            IParamSettingStrategy paramSetting = strategyContainer.getParamSetting(paramConfig.getTargetType());
            if (Objects.isNull(paramSetting)) {
                log.warn("can not find Strategy for target type: {}", paramConfig.getTargetType());
                continue;
            }
            paramSetting.handle(paramConfig, requestTemplate, paramValue);
        }
    }

    private boolean isMatchParamPath(String feignUrl, ParamConfig paramConfig) {
        //黑名单
        if (paramConfig.getExcludePaths().stream().anyMatch(pattern -> isMatchesPath(feignUrl, pattern))) {
            return false;
        }
        //白名单
        return paramConfig.getIncludePaths().stream().anyMatch(pattern -> isMatchesPath(feignUrl, pattern));
    }

    private boolean isMatchesPath(String feignUrl, String pattern) {
        return pathPatternParser.parse(pattern).matches(PathContainer.parsePath(feignUrl));
    }

    /**
     * 获取feign请求url信息
     *
     * @param requestTemplate 请求信息
     * @return 具体url地址
     */
    public String getFeignUrl(RequestTemplate requestTemplate) {
        String str = requestTemplate.feignTarget().url().replace(String.format("http://%s", requestTemplate.feignTarget().name()), "");
        if (StringUtils.hasText(str)) {
            if (!str.startsWith("/")) {
                str = "/" + str;
            }
            if (str.endsWith("/")) {
                str = str.substring(0, str.length() - 1);
            }
            return str + requestTemplate.path();
        } else {
            return requestTemplate.path();
        }
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
