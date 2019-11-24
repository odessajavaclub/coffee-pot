package org.odessajavaclub.topic.application.port.in;

import org.odessajavaclub.topic.domain.Topic;

import java.util.List;
import java.util.Optional;

public interface GetTopicsQuery {

    Optional<Topic> getTopic(Topic.TopicId topicId);

    List<Topic> getTopics();

    Optional<Topic> getTopicByName(String topicName);

    List<Topic> getTopicsByAuthor(String authorName);

    List<Topic> getTopicsByCategory(String category);
}
