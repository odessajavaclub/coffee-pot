package org.odessajavaclub.topic.application.port.out;

import org.odessajavaclub.topic.domain.Topic;

import java.util.List;

public interface LoadTopicPort {
    Topic loadTopic(Topic.TopicId topicId);
    List<Topic> listAll();
}
