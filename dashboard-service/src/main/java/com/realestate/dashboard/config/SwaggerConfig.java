package com.realestate.dashboard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI dashboardServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dashboard Service API")
                        .description("Real Estate Dashboard Service")
                        .version("1.0.0"));
    }
}