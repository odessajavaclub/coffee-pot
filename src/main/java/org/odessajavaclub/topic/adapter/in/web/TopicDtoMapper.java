package org.odessajavaclub.topic.adapter.in.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.ValidationException;
import org.odessajavaclub.topic.domain.Topic;

public class TopicDtoMapper {
  private static final long UNDEFINED_ID = -1;

  /**
   * Maps {@link Topic} to {@link TopicDto}.
   *
   * @param topic {@link Topic}
   * @return {@link TopicDto}
   */
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

  /**
   * Maps {@link TopicDto} to {@link Topic}.
   *
   * @param topicDto {@link TopicDto}
   * @return {@link Topic}
   */
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
