package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUsersRequestEvent {

  private final boolean active;

  private final int page;

  private final int size;

  /**
   * Constructor.
   *
   * @param active active
   * @param page   page
   * @param size   size
   */
  public GetUsersRequestEvent(boolean active, int page, int size) {
    this.active = active;
    this.page = page;
    this.size = size;
  }
}
