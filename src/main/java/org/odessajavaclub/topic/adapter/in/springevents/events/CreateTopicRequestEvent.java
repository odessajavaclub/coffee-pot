package org.odessajavaclub.topic.adapter.in.springevents.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CreateTopicRequestEvent extends ApplicationEvent {
  private final Topic topic;

  public CreateTopicRequestEvent(Object source, Topic topic) {
    super(source);
    this.topic = topic;
  }
}
