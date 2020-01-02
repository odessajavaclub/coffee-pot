package org.odessajavaclub.topic.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.application.port.out.LoadTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class GetTopicService implements GetTopicsQuery {
  private final LoadTopicPort loadTopicPort;

  @Override
  public Optional<Topic> getTopic(Topic.TopicId topicId) {
    return loadTopicPort.loadTopic(topicId);
  }

  @Override
  public List<Topic> getTopics(String sortBy, String order, int page, int size) {
    return loadTopicPort.listAll(sortBy, order, page, size);
  }

  @Override
  public List<Topic> getTopicsByName(
      String topicName, String sortBy, String order, int page, int size) {
    return loadTopicPort.listByTitleLike(topicName, sortBy, order, page, size);
  }

  @Override
  public List<Topic> getTopicsByStatus(
      TopicStatus status, String sortBy, String order, int page, int size) {
    return loadTopicPort.listByStatus(status, sortBy, order, page, size);
  }

  @Override
  public List<Topic> getTopicsByType(
      TopicType type, String sortBy, String order, int page, int size) {
    return loadTopicPort.listByType(type, sortBy, order, page, size);
  }

  @Override
  public List<Topic> getTopicsInDate(Date date, String sortBy, String order, int page, int size) {
    return loadTopicPort.listByDate(date, sortBy, order, page, size);
  }

  @Override
  public List<Topic> getTopicsInDateRange(
      Date start, Date end, String sortBy, String order, int page, int size) {
    return loadTopicPort.listByDateRange(start, end, sortBy, order, page, size);
  }
}
