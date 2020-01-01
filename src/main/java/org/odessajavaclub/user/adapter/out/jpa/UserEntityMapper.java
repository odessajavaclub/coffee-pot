package org.odessajavaclub.user.adapter.out.jpa;

import lombok.NoArgsConstructor;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserEntityMapper {

    public UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                         .id(user.getId()
                                 .map(User.UserId::getValue)
                                 .orElse(null))
                         .firstName(user.getFirstName())
                         .lastName(user.getLastName())
                         .email(user.getEmail())
                         .password(user.getPassword())
                         .role(user.getRole())
                         .active(user.isActive())
                         .build();
    }

    public User toUser(UserEntity userEntity) {
        return User.withId(userEntity.getId(),
                           userEntity.getFirstName(),
                           userEntity.getLastName(),
                           userEntity.getEmail(),
                           userEntity.getPassword(),
                           userEntity.getRole(),
                           userEntity.isActive());
    }
}
