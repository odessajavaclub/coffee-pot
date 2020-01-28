package org.odessajavaclub.topic.application.port.in;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;

public interface UpdateTopicUseCase {

  Optional<Topic> updateTopic(UpdateTopicCommand command);

  @EqualsAndHashCode
  class UpdateTopicCommand extends SelfValidating<UpdateTopicCommand> {

    @NotNull private final Topic topic;

    public UpdateTopicCommand(
        Topic.TopicId id,
        String title,
        String event,
        TopicType type,
        int score,
        TopicStatus status) {
      this.topic = new Topic();
      this.topic.setId(id);
      this.topic.setTitle(title);
      try {
        this.topic.setEvent(new SimpleDateFormat("dd/MM/yyyy k:mm").parse(event));
      } catch (ParseException e) {
        throw new ValidationException("Date format is incorrect, use dd/MM/yyyy k:m pattern");
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
