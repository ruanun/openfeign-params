package com.ruanun.openfeign.params.strategys;

import com.ruanun.openfeign.params.ParamConfig;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * 对设置header时的参数进行编码
 */
@Slf4j
public class EncoderHeaderParamStrategy extends HeaderParamStrategy {
    @Override
    public void handle(ParamConfig paramConfig, RequestTemplate requestTemplate, Object value) {
        requestTemplate.removeHeader(paramConfig.getTargetName());
        if (value instanceof String) {
            String encoded = URLEncoder.encode((String) value, StandardCharsets.UTF_8);
            requestTemplate.header(paramConfig.getTargetName(), encoded);
        } else if (value instanceof Collection) {
            Collection<?> collection = (Collection) value;
            String[] array = collection.stream().map(String::valueOf).map(s -> URLEncoder.encode(s, StandardCharsets.UTF_8)).toArray(String[]::new);
            requestTemplate.header(paramConfig.getTargetName(), array);
        } else {
            log.warn("不支持的参数类型：p {} v {}", paramConfig, value.getClass());
        }
    }
}
