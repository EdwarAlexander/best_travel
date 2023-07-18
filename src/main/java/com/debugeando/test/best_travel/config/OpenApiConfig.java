package com.debugeando.test.best_travel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Best Travel APi", version = "1.0", description = "documentation for endpoints")
)
public class OpenApiConfig {
}
