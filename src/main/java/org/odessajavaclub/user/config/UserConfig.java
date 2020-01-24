package org.odessajavaclub.user.config;

import javax.validation.Validation;
import org.mapstruct.factory.Mappers;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.adapter.in.rest.mapping.UserRestMapper;
import org.odessajavaclub.user.adapter.in.springevents.mapping.UserSpringEventMapper;
import org.odessajavaclub.user.adapter.out.jpa.UserEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

  @Bean
  UserRestMapper userRestMapper() {
    return Mappers.getMapper(UserRestMapper.class);
  }

  @Bean
  UserEntityMapper userEntityMapper() {
    return Mappers.getMapper(UserEntityMapper.class);
  }

  @Bean
  UserSpringEventMapper userSpringEventMapper() {
    return Mappers.getMapper(UserSpringEventMapper.class);
  }

  @Bean
  Validating validating() {
    return new Validating(Validation.buildDefaultValidatorFactory());
  }
}
