package com.gerdoc.escuela.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;
@Configuration
public class OpenApiConfig {
    @Bean public OpenAPI escuelaApi() {
        return new OpenAPI().info(new Info().title("ESCUELA - Servicios REST").description("Altas, bajas, cambios y consultas de los cinco catálogos.").version("1.0"));
    }
}
