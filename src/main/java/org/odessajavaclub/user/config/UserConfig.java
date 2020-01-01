package org.odessajavaclub.user.config;

import org.odessajavaclub.user.adapter.in.rest.RestUserDtoMapper;
import org.odessajavaclub.user.adapter.in.springevents.model.SpringEventUserDtoMapper;
import org.odessajavaclub.user.adapter.out.jpa.UserEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    RestUserDtoMapper restUserDtoMapper() {
        return new RestUserDtoMapper();
    }

    @Bean
    UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }

    @Bean
    SpringEventUserDtoMapper eventUserDtoMapper() {
        return new SpringEventUserDtoMapper();
    }
}
