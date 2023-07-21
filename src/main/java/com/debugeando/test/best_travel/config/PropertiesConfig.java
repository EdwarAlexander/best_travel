package com.debugeando.test.best_travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
//@PropertySource(value = "classpath:configs/api_currency.properties")
@PropertySources({
        @PropertySource(value = "classpath:configs/api_currency.properties"),
        @PropertySource(value = "classpath:configs/redis.properties"),
        @PropertySource(value = "classpath:configs/client_security.properties"),
})
public class PropertiesConfig {
}
