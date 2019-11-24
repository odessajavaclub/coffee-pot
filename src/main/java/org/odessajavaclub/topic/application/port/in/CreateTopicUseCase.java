package org.odessajavaclub.topic.application.port.in;

import lombok.EqualsAndHashCode;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.topic.domain.Topic;

import javax.validation.constraints.NotNull;

public interface CreateTopicUseCase {

    Topic createTopic(CreateTopicCommand command);

    @EqualsAndHashCode
    class CreateTopicCommand extends SelfValidating<CreateTopicCommand> {

        @NotNull
        private final Topic topic;

        public CreateTopicCommand(Topic topic) {
            this.topic = topic;
            this.validateSelf();
        }

        public Topic getTopic() {
            return topic;
        }
    }

}
