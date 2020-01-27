package org.odessajavaclub.topic.adapter.out.persistence;

import lombok.NoArgsConstructor;
import org.odessajavaclub.topic.domain.Topic;

@NoArgsConstructor
public class TopicEntityMapper {

  /**
   * Maps {@link Topic} to {@link TopicEntity}.
   *
   * @param topic {@link Topic}
   * @return {@link TopicEntity}
   */
  public TopicEntity toTopicJpa(Topic topic) {
    return TopicEntity.builder()
        .id(topic.getId().map(Topic.TopicId::getValue).orElse(null))
        .title(topic.getTitle())
        .event(topic.getEvent())
        .type(topic.getType())
        .author(topic.getAuthor())
        .score(topic.getScore())
        .status(topic.getStatus())
        .build();
  }

  /**
   * Maps {@link TopicEntity} to {@link Topic}.
   *
   * @param topicEntity {@link TopicEntity}
   * @return {@link Topic}
   */
  public Topic toTopic(TopicEntity topicEntity) {
    Topic topic = new Topic();
    Topic.TopicId topicId =
        topicEntity.getId() != null ? new Topic.TopicId(topicEntity.getId()) : null;
    topic.setId(topicId);
    topic.setAuthor(topicEntity.getAuthor());
    topic.setTitle(topicEntity.getTitle());
    topic.setEvent(topicEntity.getEvent());
    topic.setStatus(topicEntity.getStatus());
    topic.setType(topicEntity.getType());
    topic.setScore(topicEntity.getScore());
    return topic;
  }
}
