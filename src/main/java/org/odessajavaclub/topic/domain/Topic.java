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
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime event;

    @NotNull
    @NotBlank
    @Setter
    private String category;

    @NotNull
    @NotBlank
    @Setter
    private List<String> tags;

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

    @Setter
    private List<Integer> scores;

    @Setter
    private List<String> materials;

    @Value
    public static class TopicId {

        @NonNull
        private Long value;
    }


}
