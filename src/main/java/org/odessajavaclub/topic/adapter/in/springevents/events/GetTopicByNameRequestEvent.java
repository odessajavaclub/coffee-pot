package org.odessajavaclub.topic.adapter.in.springevents.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetTopicByNameRequestEvent extends ApplicationEvent {
  private final String name;
  private final String sortBy;
  private final String order;
  private final int page;
  private final int size;

  /**
   * Constructor.
   *
   * @param source source
   * @param name   name
   * @param sortBy sortBy
   * @param order  order
   * @param page   page
   * @param size   size
   */
  public GetTopicByNameRequestEvent(Object source,
                                    String name,
                                    String sortBy,
                                    String order,
                                    int page,
                                    int size) {
    super(source);
    this.name = name;
    this.sortBy = sortBy;
    this.order = order;
    this.page = page;
    this.size = size;
  }
}
