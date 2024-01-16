package com.ruanun.openfeign.params.hadlers;

import com.ruanun.openfeign.params.ParamConfig;
import feign.RequestTemplate;

/**
 * @author ruan
 */
public interface IParamGettingHandler {

    String type();

    Object handle(ParamConfig paramConfig, RequestTemplate requestTemplate);
}
