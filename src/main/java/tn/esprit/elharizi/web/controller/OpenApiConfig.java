package tn.esprit.elharizi.web.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {
    @Bean
    public OpenAPI autolocOpenApi() {
        return new OpenAPI().info(new Info()
                .title("AutoLoc API")
                .description("API REST de gestion de location de véhicules")
                .version("1.0.0"));
    }
}
