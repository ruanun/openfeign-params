package com.ruanun.openfeign.params;


import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Set;

/**
 * @author ruan
 */
@Data
public class OpenFeignParamsConfig {

    private Boolean enabled = true;

    /**
     * 拦截路径
     */
    private Set<String> interceptPaths = Set.of("/**");

    private Map<String, Set<ParamConfig>> paramConfigs = Map.of();

    private Map<HttpMethod, Set<String>> paramMethodMapping = Map.of();
}
