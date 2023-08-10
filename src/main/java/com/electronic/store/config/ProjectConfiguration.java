package com.electronic.store.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ProjectConfiguration {

    @Bean //class is created to inject
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new BaseEntityAuditorAware();
    }
}
