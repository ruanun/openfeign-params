package com.ruanun.openfeign.params.strategys;

import com.ruanun.openfeign.params.ParamConfig;
import feign.RequestTemplate;

/**
 * @author ruan
 */
public interface IParamSettingStrategy {

    String type();

    void handle(ParamConfig paramConfig, RequestTemplate requestTemplate, Object value);
}
