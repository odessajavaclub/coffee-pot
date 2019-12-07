package org.odessajavaclub.user.config;

import org.odessajavaclub.user.adapter.in.rest.UserDtoMapper;
import org.odessajavaclub.user.adapter.out.jpa.UserEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    UserDtoMapper userDtoMapper() {
        return new UserDtoMapper();
    }

    @Bean
    UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }
}
