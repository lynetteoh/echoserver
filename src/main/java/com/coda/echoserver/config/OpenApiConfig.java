package com.coda.echoserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${application.description}") String appDesciption, @Value("${application.name}") String appName, @Value("${application.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title(appName)
                        .version(appVersion)
                        .description(appDesciption));
    }





}
