package org.odessajavaclub.topic.application.port.in;

import org.odessajavaclub.topic.domain.Topic;

import java.util.List;

public interface GetTopicsQuery {

    Topic getTopic(String topicId);

    List<Topic> getTopics();

    Topic getTopicByName(String topicName);

    List<Topic> getTopicsByAuthor(String authorName);

    List<Topic> getTopicsByCategory(String category);
}
