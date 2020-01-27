package org.odessajavaclub.user.adapter.in.rest.mapper;

import org.odessajavaclub.user.domain.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleRestMapper {

  /**
   * Maps string representation to its {@link UserRole}.
   *
   * @param role string representation
   * @return {@link UserRole}
   */
  public UserRole asUserRole(String role) {
    if (role == null) {
      return null;
    }
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

  /**
   * Maps {@link UserRole} to its string representation.
   *
   * @param userRole {@link UserRole}
   * @return user role string representation
   */
  public String asRole(UserRole userRole) {
    if (userRole == null) {
      return null;
    }
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
