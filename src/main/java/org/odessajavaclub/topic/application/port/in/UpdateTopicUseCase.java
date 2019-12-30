package org.odessajavaclub.topic.application.port.in;

import lombok.EqualsAndHashCode;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.topic.domain.Topic;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UpdateTopicUseCase {

    Optional<Topic> updateTopic(UpdateTopicCommand command);

    @EqualsAndHashCode
    class UpdateTopicCommand extends SelfValidating<UpdateTopicCommand> {

        @NotNull
        private final Topic topic;

        public UpdateTopicCommand(Long id, Topic topic) {
            if (id != topic.getId().get().getValue()) {
                throw new RuntimeException("Incorrect ID");
            }
            this.topic = topic;
            this.validateSelf();
        }

        public Topic getTopic() {
            return topic;
        }
    }

}
