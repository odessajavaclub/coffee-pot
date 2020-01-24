package org.odessajavaclub.user.config;

import javax.validation.Validation;
import org.odessajavaclub.shared.Validating;
import org.odessajavaclub.user.shared.UserIdMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.odessajavaclub.user")
public class UserConfig {

  @Bean
  UserIdMapper userIdMapper() {
    return new UserIdMapper();
  }

  @Bean
  Validating validating() {
    return new Validating(Validation.buildDefaultValidatorFactory());
  }
}
