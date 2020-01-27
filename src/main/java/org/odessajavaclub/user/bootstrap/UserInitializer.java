package org.odessajavaclub.user.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserInitializer implements CommandLineRunner {

  private final CreateUserPort createUserPort;

  @Override
  public void run(String... args) {
    log.info("Started creating mock users");

    createUserPort.createUser(User.builder()
                                  .firstName("Maxim")
                                  .lastName("Sashkin")
                                  .email("maxs@email.com")
                                  .password("{noop}maxs1234")
                                  .role(UserRole.USER)
                                  .active(true)
                                  .build());

    createUserPort.createUser(User.builder()
                                  .firstName("Alexander")
                                  .lastName("Pletnev")
                                  .email("alexp@email.com")
                                  .password("{noop}alexp1234")
                                  .role(UserRole.ADMIN)
                                  .active(true)
                                  .build());

    createUserPort.createUser(User.builder()
                                  .firstName("Alexander")
                                  .lastName("Bevziuk")
                                  .email("alexb@email.com")
                                  .password("{noop}alexb1234")
                                  .role(UserRole.USER)
                                  .active(true)
                                  .build());

    createUserPort.createUser(User.builder()
                                  .firstName("Oleksii")
                                  .lastName("Aleksandrov")
                                  .email("oleksiia@email.com")
                                  .password("{noop}oleksii1234")
                                  .role(UserRole.READONLY)
                                  .active(true)
                                  .build());

    log.info("Finished creating mock users");
  }
}
