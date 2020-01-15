package org.odessajavaclub.topic.adapter.in.springevents.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetTopicResponseEvent extends ApplicationEvent {
  private final Topic topic;

  public GetTopicResponseEvent(Object source, Topic topic) {
    super(source);
    this.topic = topic;
  }
}
