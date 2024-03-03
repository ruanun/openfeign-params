package com.ruanun.openfeign.params.strategys;

import com.ruanun.openfeign.params.ParamConfig;
import com.ruanun.openfeign.params.RequestUtils;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author ruan
 */
@Slf4j
public class HeaderParamStrategy implements IParamSettingStrategy, IParamGettingStrategy {

    @Override
    public String type() {
        return "HEADER";
    }

    @Override
    public Object handle(ParamConfig paramConfig, RequestTemplate requestTemplate) {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request == null) {
            log.warn("request is null for paramConfig: {}", paramConfig);
            return null;
        }
        return request.getHeader(paramConfig.getSourceName());
    }

    @Override
    public void handle(ParamConfig paramConfig, RequestTemplate requestTemplate, Object value) {
        requestTemplate.removeHeader(paramConfig.getTargetName());
        if (value instanceof String) {
            requestTemplate.header(paramConfig.getTargetName(), (String) value);
        } else if (value instanceof Collection) {
            Collection<?> collection = (Collection) value;
            String[] array = collection.stream().map(String::valueOf).toArray(String[]::new);
            requestTemplate.header(paramConfig.getTargetName(), array);
        } else {
            log.warn("不支持的参数类型：p {} v {}", paramConfig, value.getClass());
        }
    }
}
