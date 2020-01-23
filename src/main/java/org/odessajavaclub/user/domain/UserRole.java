package org.odessajavaclub.user.domain;

import java.util.Arrays;

public enum UserRole {

  ADMIN("admin"),
  USER("user"),
  READONLY("readonly");

  private String name;

  UserRole(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static UserRole fromName(String name) {
    return Arrays.stream(UserRole.values())
                 .filter(ur -> ur.name.equalsIgnoreCase(name))
                 .findFirst()
                 .orElseThrow(() -> new IllegalArgumentException("Unknown role name: " + name));
  }
}
