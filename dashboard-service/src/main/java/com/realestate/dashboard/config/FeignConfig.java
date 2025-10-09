package com.realestate.dashboard.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.realestate.dashboard.client")
public class FeignConfig {
}