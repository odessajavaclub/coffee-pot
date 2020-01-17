package org.odessajavaclub.topic.application.port.in;

import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GetTopicsQuery {

  Optional<Topic> getTopic(Topic.TopicId topicId);

  List<Topic> getTopics(String sortBy, String order, int page, int size);

  List<Topic> getTopicsByName(String topicName, String sortBy, String order, int page, int size);

  List<Topic> getTopicsByStatus(
      TopicStatus status, String sortBy, String order, int page, int size);

  List<Topic> getTopicsByType(TopicType type, String sortBy, String order, int page, int size);

  List<Topic> getTopicsInDate(Date date, String sortBy, String order, int page, int size);

  List<Topic> getTopicsInDateRange(
      Date start, Date end, String sortBy, String order, int page, int size);
}
