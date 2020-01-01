package org.odessajavaclub.topic.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

//TODO: Add image field
//TODO: Remove author field
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Data
public class Topic {

    private TopicId id;

    @NotNull
    @NotBlank
    @Min(3)
    @Setter
    private String title;

    @NotNull
    @FutureOrPresent
    @Setter
    private Date event;

    @NotNull
    @NotBlank
    @Setter
    private TopicType type = TopicType.STUDY;

    @NotNull
    @NotBlank
    private String author;

    private int score;

    @NotNull
    @NotBlank
    @Setter
    private TopicStatus status = TopicStatus.PENDING;

    @Value
    public static class TopicId {
        @NonNull
        private Long value;
    }

    public Topic() {
    }

    public static Topic from(Topic topic, TopicId topicId) {
        Topic result = new Topic();
        result.setId(topicId);
        result.setTitle(topic.getTitle());
        result.setEvent(topic.getEvent());
        result.setType(topic.getType());
        result.setStatus(topic.getStatus());
        result.setScore(topic.getScore());
        return result;
    }

    public Optional<TopicId> getId() {
        return Optional.ofNullable(this.id);
    }
}
