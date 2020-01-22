package org.odessajavaclub.user.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
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

//    createUserPort.createUser(User.withoutId("Maxim",
//                                             "Sashkin",
//                                             "maxs@email.com",
//                                             "{noop}maxs1234",
//                                             UserRole.USER,
//                                             true));
//
//    createUserPort.createUser(User.withoutId("Alexander",
//                                             "Pletnev",
//                                             "alexp@email.com",
//                                             "{noop}alexp1234",
//                                             UserRole.ADMIN,
//                                             true));
//
//    createUserPort.createUser(User.withoutId("Alexander",
//                                             "Bevziuk",
//                                             "alexb@email.com",
//                                             "{noop}alexb1234",
//                                             UserRole.USER,
//                                             true));
//
//    createUserPort.createUser(User.withoutId("Oleksii",
//                                             "Aleksandrov",
//                                             "oleksiia@email.com",
//                                             "{noop}oleksii1234",
//                                             UserRole.USER,
//                                             true));

    log.info("Finished creating mock users");
  }
}
