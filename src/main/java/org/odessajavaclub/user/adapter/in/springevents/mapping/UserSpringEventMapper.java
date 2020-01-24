package org.odessajavaclub.user.adapter.in.springevents.mapping;

import org.mapstruct.Mapper;
import org.odessajavaclub.user.adapter.in.springevents.model.GetUserDto;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.shared.UserIdMapper;

@Mapper(uses = {UserIdMapper.class, })
public interface UserSpringEventMapper {

  GetUserDto toGetUserDto(User user);
}
