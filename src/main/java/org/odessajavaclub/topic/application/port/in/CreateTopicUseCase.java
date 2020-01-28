package org.odessajavaclub.topic.application.port.in;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;

public interface CreateTopicUseCase {

  Topic createTopic(CreateTopicCommand command);

  @EqualsAndHashCode
  class CreateTopicCommand extends SelfValidating<CreateTopicCommand> {

    @NotNull private final Topic topic;

    public CreateTopicCommand(
        String title, String event, TopicType type, int score, TopicStatus status) {
      this.topic = new Topic();
      this.topic.setTitle(title);
      try {
        this.topic.setEvent(new SimpleDateFormat("dd/MM/yyyy k:mm").parse(event));
      } catch (ParseException e) {
        throw new ValidationException("Date format is incorrect, use dd/MM/yyyy k:mm pattern");
      }
      this.topic.setType(type);
      this.topic.setScore(score);
      this.topic.setStatus(status);
      this.validateSelf();
    }

    public Topic getTopic() {
      return topic;
    }
  }
}
