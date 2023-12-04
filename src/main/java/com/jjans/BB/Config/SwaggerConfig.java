package com.jjans.BB.Config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI api(){

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
        //    .servers(List.of(new Server().url(ngrokBaseUrl))); // Set ngrok URL as server URL

    }
    private Info apiInfo(){
        return new Info()
                .title("BeatBuddy Spring boot API")
                .description("")
                .version("1.0.0");
    }



}
