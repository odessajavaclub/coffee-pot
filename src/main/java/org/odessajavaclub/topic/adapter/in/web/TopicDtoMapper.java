package org.odessajavaclub.topic.adapter.in.web;

import org.odessajavaclub.topic.domain.Topic;

import javax.validation.ValidationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TopicDtoMapper {
  private static final long UNDEFINED_ID = -1;

  public TopicDto toGetTopicDto(Topic topic) {
    long id = topic.getId().map(Topic.TopicId::getValue).orElse(UNDEFINED_ID);
    return new TopicDto(
        id,
        topic.getTitle(),
        new SimpleDateFormat("dd/MM/yyyy k:mm").format(topic.getEvent()),
        topic.getType(),
        topic.getAuthor(),
        topic.getScore(),
        topic.getStatus());
  }

  public Topic toTopic(TopicDto topicDto) {
    Topic topic = new Topic();
    try {
      topic.setEvent(new SimpleDateFormat("dd/MM/yyyy k:mm").parse(topicDto.getEvent()));
    } catch (ParseException e) {
      throw new ValidationException("Can't parse a date, format is: dd/MM/yyyy k:mm");
    }
    topic.setScore(topicDto.getScore());
    topic.setTitle(topicDto.getTitle());
    topic.setId(new Topic.TopicId(topicDto.getId()));
    topic.setStatus(topicDto.getStatus());
    topic.setType(topicDto.getType());
    topic.setAuthor(topicDto.getAuthor());
    return topic;
  }
}
