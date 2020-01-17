package org.odessajavaclub.topic.adapter.in.springevents.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DeleteTopicByIdRequestEvent extends ApplicationEvent {
  private final Topic.TopicId id;

  public DeleteTopicByIdRequestEvent(Object source, Topic.TopicId id) {
    super(source);
    this.id = id;
  }
}
