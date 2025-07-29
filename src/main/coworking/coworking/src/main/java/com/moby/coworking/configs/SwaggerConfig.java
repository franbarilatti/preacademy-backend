package com.moby.coworking.configs;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("coworking-api")
                .pathsToMatch("/rooms/**", "/reservations/**", "users/**")
                .build();
    }


}
