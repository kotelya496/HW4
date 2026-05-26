package org.example.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

//    @Bean
//    public OpenAPI api(){
//        return new OpenAPI()
//                .servers(List.of(new Server().url("http://localhost:8080")))
//                .info(
//                        new Info().title("User service API")
//                );
//    }

    @Bean
    public OpenAPI customOpenAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("User service API")
                        .description("the client's interaction with the User database")
                        .version("v1.0.0"))
                .components(new Components().addSecuritySchemes("bearerAuth",securityScheme))
                .addSecurityItem(securityRequirement);
    }
}
