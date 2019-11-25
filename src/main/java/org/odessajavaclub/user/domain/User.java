package org.odessajavaclub.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class User {

    private UserId id;

    @NotNull
    @NotBlank
    @Getter
    private String firstName;

    @NotNull
    @NotBlank
    @Getter
    private String lastName;

    @Getter
    private boolean isDeactivated;

    @Value
    public static class UserId {

        @NotNull
        private Long value;
    }

    public static User withoutId(String firstName, String lastName, boolean isDeactivated) {
        return new User(null, firstName, lastName, isDeactivated);
    }

    public static User withId(UserId userId, String firstName, String lastName, boolean isDeactivated) {
        return new User(userId, firstName, lastName, isDeactivated);
    }

    public static User from(User user, String newFirstName, String newLastName) {
        return new User(user.id,
                        newFirstName != null ? newFirstName : user.firstName,
                        newLastName != null ? newLastName : user.lastName,
                        user.isDeactivated);
    }

    public static User from(User user, UserId userId) {
        return new User(userId,
                        user.firstName,
                        user.lastName,
                        user.isDeactivated);
    }

    public static User from(User user, boolean isDeactivated) {
        return new User(user.id,
                        user.firstName,
                        user.lastName,
                        isDeactivated);
    }

    public Optional<UserId> getId() {
        return Optional.ofNullable(this.id);
    }
}
