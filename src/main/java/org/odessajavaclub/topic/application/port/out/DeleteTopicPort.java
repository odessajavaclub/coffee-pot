package org.odessajavaclub.topic.application.port.out;

import org.odessajavaclub.topic.domain.Topic;

public interface DeleteTopicPort {
  boolean deleteTopic(Topic.TopicId topicId);
}
