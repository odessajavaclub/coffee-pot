package org.odessajavaclub.user.shared;

import org.odessajavaclub.user.domain.User.UserId;

public class UserIdMapper {

  /**
   * Maps long id value to {@link UserId}.
   *
   * @param id long id value
   * @return {@link UserId}
   */
  public UserId asUserId(Long id) {
    if (id == null) {
      return null;
    }
    return new UserId(id);
  }

  /**
   * Maps {@link UserId} to long id value.
   *
   * @param userId {@link UserId}
   * @return long id value
   */
  public Long asId(UserId userId) {
    if (userId == null) {
      return null;
    }
    return userId.getValue();
  }
}
