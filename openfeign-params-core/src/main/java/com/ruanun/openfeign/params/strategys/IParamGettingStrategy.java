package com.ruanun.openfeign.params.strategys;

import com.ruanun.openfeign.params.ParamConfig;
import feign.RequestTemplate;

/**
 * @author ruan
 */
public interface IParamGettingStrategy {

    String type();

    Object handle(ParamConfig paramConfig, RequestTemplate requestTemplate);
}
