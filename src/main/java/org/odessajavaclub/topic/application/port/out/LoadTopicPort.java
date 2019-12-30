package org.odessajavaclub.topic.application.port.out;

import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoadTopicPort {
    Optional<Topic> loadTopic(Topic.TopicId topicId);

    List<Topic> listAll(String sortBy, String order, int page, int size);

    List<Topic> listByType(TopicType type, String sortBy, String order, int page, int size);

    List<Topic> listByStatus(TopicStatus status, String sortBy, String order, int page, int size);

    List<Topic> listByDateRange(LocalDate start, LocalDate end, String sortBy, String order, int page, int size);

    List<Topic> listByDate(LocalDate start, String sortBy, String order, int page, int size);

    List<Topic> listByTitleLike(String title, String sortBy, String order, int page, int size);
}
