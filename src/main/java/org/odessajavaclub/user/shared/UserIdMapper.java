package org.odessajavaclub.user.shared;

import org.odessajavaclub.user.domain.User.UserId;

public class UserIdMapper {

  public UserId asUserId(Long id) {
    if (id == null) {
      return null;
    }
    return new UserId(id);
  }

  public Long asId(UserId userId) {
    if (userId == null) {
      return null;
    }
    return userId.getValue();
  }
}
