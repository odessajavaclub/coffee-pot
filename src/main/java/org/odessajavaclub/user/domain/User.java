package org.odessajavaclub.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class User {

    private UserId id;

    @NotBlank
    @Getter
    private String firstName;

    @NotBlank
    @Getter
    private String lastName;

    @Email
    @Getter
    private String email;

    @NotBlank
    @Getter
    private String password;

    @NotNull
    private UserRole role;

    @Getter
    private boolean isDeactivated;

    @Value
    public static class UserId {

        @NotNull
        private Long value;
    }

    public static User withoutId(String firstName,
                                 String lastName,
                                 String email,
                                 String password,
                                 UserRole role,
                                 boolean isDeactivated) {
        return new User(null, firstName, lastName, email, password, role, isDeactivated);
    }

    public static User withId(UserId userId,
                              String firstName,
                              String lastName,
                              String email,
                              String password,
                              UserRole role,
                              boolean isDeactivated) {
        return new User(userId, firstName, lastName, email, password, role, isDeactivated);
    }

    public static User from(User user, String newFirstName, String newLastName, String newEmail) {
        return new User(user.id,
                        newFirstName != null ? newFirstName : user.firstName,
                        newLastName != null ? newLastName : user.lastName,
                        newEmail != null ? newEmail : user.email,
                        user.password,
                        user.role,
                        user.isDeactivated);
    }

    public static User from(User user, UserId userId) {
        return new User(userId,
                        user.firstName,
                        user.lastName,
                        user.email,
                        user.password,
                        user.role,
                        user.isDeactivated);
    }

    public static User from(User user, boolean isDeactivated) {
        return new User(user.id,
                        user.firstName,
                        user.lastName,
                        user.email,
                        user.password,
                        user.role,
                        isDeactivated);
    }

    public Optional<UserId> getId() {
        return Optional.ofNullable(this.id);
    }
}
