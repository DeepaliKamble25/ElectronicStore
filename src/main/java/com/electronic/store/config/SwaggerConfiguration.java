package com.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();

    }

    private ApiInfo getInfo() {

        return new ApiInfo("ElectronicStore Application : ElectronicService",
                "This project developed by me",
                "1.0",
                "Terms of service",
                new Contact("Deepali",
                        "kambledeepali25@gmail.com","kambledeepali25@gmail.com"),
                "License of APIS",
                "API license URL",
                Collections.emptyList());
    }

}
