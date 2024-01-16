package com.ruanun.openfeign.params;

import com.ruanun.openfeign.params.hadlers.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.ruanun.openfeign.params.AuoConfiguration.CONFIG_PREFIX;

/**
 * @author ruan
 */
@Configuration
@ConditionalOnProperty(prefix = CONFIG_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(value = {feign.RequestInterceptor.class, FeignAutoConfiguration.class})
public class AuoConfiguration {

    static final String CONFIG_PREFIX = "feign.params";

    @Bean
    @ConfigurationProperties(prefix = CONFIG_PREFIX)
    public OpenFeignParamsConfig openFeignParamsConfig() {
        return new OpenFeignParamsConfig();
    }

    @Bean
    @ConditionalOnMissingBean(HeaderParamHandler.class)
    public HeaderParamHandler headerParamHandler() {
        return new HeaderParamHandler();
    }

    @Bean
    @ConditionalOnMissingBean(QueryParamHandler.class)
    public QueryParamHandler queryParamHandler() {
        return new QueryParamHandler();
    }

    @Bean
    @ConditionalOnMissingBean(HandlerContainer.class)
    public HandlerContainer paramGettingHandlers(List<IParamGettingHandler> paramGettingHandlers, List<IParamSettingHandler> paramSettingHandlers) {
        return new HandlerContainer(paramGettingHandlers, paramSettingHandlers);
    }

    @Bean
    public ParamProcessRequestInterceptor paramProcessRequestInterceptor(OpenFeignParamsConfig openFeignParamsConfig, HandlerContainer handlerContainer) {
        return new ParamProcessRequestInterceptor(openFeignParamsConfig, handlerContainer);
    }
}
