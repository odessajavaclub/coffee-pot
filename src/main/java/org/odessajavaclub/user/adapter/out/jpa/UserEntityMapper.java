package org.odessajavaclub.user.adapter.out.jpa;

import org.mapstruct.Mapper;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.shared.UserIdMapper;

@Mapper(uses = UserIdMapper.class)
public interface UserEntityMapper {

  UserEntity toUserEntity(User user);

  User toUser(UserEntity userEntity);
}
