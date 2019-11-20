package org.odessajavaclub.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class User {

    private UserId id;

    @Getter
    @NonNull
    private String firstName;

    @Getter
    @NonNull
    private String lastName;

    @Getter
    private boolean isDeactivated;

    @Value
    public static class UserId {

        @NonNull
        private Long value;
    }

    public static User withoutId(String firstName, String lastName, boolean isDeactivated) {
        return new User(null, firstName, lastName, isDeactivated);
    }

    public static User withId(UserId userId, String firstName, String lastName, boolean isDeactivated) {
        return new User(userId, firstName, lastName, isDeactivated);
    }

    public Optional<UserId> getId() {
        return Optional.ofNullable(this.id);
    }
}
