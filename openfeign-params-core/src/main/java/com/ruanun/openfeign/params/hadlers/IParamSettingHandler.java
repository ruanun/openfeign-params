package com.ruanun.openfeign.params.hadlers;

import com.ruanun.openfeign.params.ParamConfig;
import feign.RequestTemplate;

/**
 * @author ruan
 */
public interface IParamSettingHandler {

    String type();

    void handle(ParamConfig paramConfig, RequestTemplate requestTemplate, Object value);
}
