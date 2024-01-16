package com.ruanun.openfeign.params.hadlers;

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
public class QueryParamHandler implements IParamSettingHandler, IParamGettingHandler {

    @Override
    public String type() {
        return "QUERY";
    }

    @Override
    public Object handle(ParamConfig paramConfig, RequestTemplate requestTemplate) {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request == null) {
            return null;
        }
        return request.getParameter(paramConfig.getSourceName());
    }


    @Override
    public void handle(ParamConfig paramConfig, RequestTemplate requestTemplate, Object value) {
        if (value instanceof String) {
            requestTemplate.query(paramConfig.getTargetName(), (String) value);
        } else if (value instanceof Collection) {
            Collection<?> collection = (Collection) value;
            String[] array = collection.stream().map(String::valueOf).toArray(String[]::new);
            requestTemplate.query(paramConfig.getTargetName(), array);
        } else {
            log.warn("不支持的参数类型：p {} v {}", paramConfig, value.getClass());
        }
    }
}
