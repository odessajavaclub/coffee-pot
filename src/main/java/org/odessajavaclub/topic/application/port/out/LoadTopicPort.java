package org.odessajavaclub.topic.application.port.out;

import org.odessajavaclub.topic.domain.Topic;

import java.util.List;
import java.util.Optional;

public interface LoadTopicPort {
    Optional<Topic> loadTopic(Topic.TopicId topicId);
    List<Topic> listAll();
}
