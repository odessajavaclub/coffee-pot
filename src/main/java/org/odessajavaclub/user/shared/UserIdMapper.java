package org.odessajavaclub.user.shared;

import org.odessajavaclub.user.domain.User.UserId;

public class UserIdMapper {

  public UserId asUserId(long id) {
    return new UserId(id);
  }

  public long asId(UserId userId) {
    return userId.getValue();
  }
}
