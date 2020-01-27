package org.odessajavaclub.user.adapter.in.rest.mapper;

import java.util.Objects;
import org.odessajavaclub.user.domain.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleRestMapper {

  public UserRole asUserRole(String role) {
    Objects.requireNonNull(role, "Role must not be null");
    switch (role) {
      case "admin":
        return UserRole.ADMIN;
      case "user":
        return UserRole.USER;
      case "readonly":
        return UserRole.READONLY;
      default:
        throw new IllegalArgumentException("Unknown role: " + role);
    }
  }

  public String asRole(UserRole userRole) {
    Objects.requireNonNull(userRole, "User role must not be null");
    switch (userRole) {
      case ADMIN:
        return "admin";
      case USER:
        return "user";
      case READONLY:
        return "readonly";
      default:
        throw new IllegalArgumentException("Unknown user role: " + userRole);
    }
  }
}
