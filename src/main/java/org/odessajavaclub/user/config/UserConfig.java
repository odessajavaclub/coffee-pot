package org.odessajavaclub.user.config;

import javax.validation.Validation;
import org.mapstruct.factory.Mappers;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.adapter.in.rest.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

  @Bean
  UserMapper userMapper() {
    return Mappers.getMapper(UserMapper.class);
  }

  @Bean
  Validating validating() {
    return new Validating(Validation.buildDefaultValidatorFactory());
  }
}
