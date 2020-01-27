package org.odessajavaclub.user.adapter.out.jpa.mapper;

import org.mapstruct.Mapper;
import org.odessajavaclub.user.adapter.out.jpa.model.UserEntity;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.shared.UserIdMapper;

@Mapper(componentModel = "spring", uses = UserIdMapper.class)
public interface UserEntityMapper {

  UserEntity toUserEntity(User user);

  User toUser(UserEntity userEntity);
}
