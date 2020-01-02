package org.odessajavaclub.topic.application.port.in;

import lombok.EqualsAndHashCode;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.topic.domain.Topic;

import javax.validation.constraints.NotNull;

public interface DeleteTopicUseCase {

  boolean deleteTopic(DeleteTopicCommand command);

  @EqualsAndHashCode
  class DeleteTopicCommand extends SelfValidating<DeleteTopicCommand> {

    @NotNull private final Topic.TopicId topicId;

    public DeleteTopicCommand(Topic.TopicId topicId) {
      this.topicId = topicId;
      this.validateSelf();
    }

    public Topic.TopicId getTopicId() {
      return topicId;
    }
  }
}
