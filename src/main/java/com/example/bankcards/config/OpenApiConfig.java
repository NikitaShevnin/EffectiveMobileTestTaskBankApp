package com.example.bankcards.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация OpenAPI для Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankCardsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank Cards API")
                        .version("1.0")
                        .description("API documentation for Bank Cards service"));
    }
}