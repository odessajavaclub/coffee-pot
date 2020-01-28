package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUsersRequestEvent extends ApplicationEvent {

  private final boolean active;

  private final int page;

  private final int size;

  /**
   * Constructor.
   *
   * @param source source
   * @param active active
   * @param page   page
   * @param size   size
   */
  public GetUsersRequestEvent(Object source, boolean active, int page, int size) {
    super(source);
    this.active = active;
    this.page = page;
    this.size = size;
  }
}
