package com.ruanun.openfeign.params;

import com.ruanun.openfeign.params.strategys.*;
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
    @ConditionalOnMissingBean(HeaderParamStrategy.class)
    public HeaderParamStrategy headerParamStrategy() {
        return new HeaderParamStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(QueryParamStrategy.class)
    public QueryParamStrategy queryParamStrategy() {
        return new QueryParamStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(StrategyContainer.class)
    public StrategyContainer paramGettingContainer(List<IParamGettingStrategy> paramGettingStrategies, List<IParamSettingStrategy> paramSettingStrategies) {
        return new StrategyContainer(paramGettingStrategies, paramSettingStrategies);
    }

    @Bean
    public ParamProcessRequestInterceptor paramProcessRequestInterceptor(OpenFeignParamsConfig openFeignParamsConfig, StrategyContainer strategyContainer) {
        return new ParamProcessRequestInterceptor(openFeignParamsConfig, strategyContainer);
    }
}
