package org.odessajavaclub.topic.adapter.out.persistence;

import org.junit.jupiter.api.Test;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TopicPersistenceAdapter.class, TopicEntityMapper.class})
@Sql("TopicData.sql")
class TopicPersistenceAdapterTest {

  @Autowired private TopicPersistenceAdapter adapterUnderTest;

  @Autowired private TopicJpaRepository repository;

  @Test
  void loadsFirstTopic() {
    Optional<Topic> topic = adapterUnderTest.loadTopic(new Topic.TopicId(0L));
    assertThat(topic.isPresent()).isTrue();
    assertThat(topic.get().getTitle()).isEqualTo("Java9 Modules");
  }

  @Test
  void getTopicsByTypePositive() {
    List<Topic> topics = adapterUnderTest.listByType(TopicType.STUDY, "event", "desc", 0, 100);
    assertThat(topics.size()).isEqualTo(4);
    assertThat(topics.get(0).getTitle()).isEqualTo("OpenAPI/Swagger");
  }

  @Test
  void getTopicsByTypeNegative() {
    List<Topic> topics = adapterUnderTest.listByType(TopicType.REPORT, "event", "desc", 0, 100);
    assertThat(topics.size()).isEqualTo(0);
  }

  @Test
  void getTopicsByStatusPositive() {
    List<Topic> topics = adapterUnderTest.listByStatus(TopicStatus.DONE, "event", "asc", 0, 100);
    assertThat(topics.size()).isEqualTo(2);
    assertThat(topics.get(1).getTitle()).isEqualTo("Spring Cloud Contract");
  }

  @Test
  void getTopicsByStatusNegative() {
    List<Topic> topics = adapterUnderTest.listByStatus(TopicStatus.PENDING, "event", "asc", 0, 100);
    assertThat(topics.size()).isEqualTo(0);
  }

  @Test
  void getTopicsByDateRangePositive() {
    Date start =
        Date.from(LocalDate.of(2019, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date end = Date.from(LocalDate.of(2019, 4, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());

    List<Topic> topics = adapterUnderTest.listByDateRange(start, end, "event", "desc", 0, 100);
    assertThat(topics.size()).isEqualTo(2);
    assertThat(topics.get(0).getId().get().getValue()).isEqualTo(3);
    assertThat(topics.get(1).getId().get().getValue()).isEqualTo(0);
  }

  @Test
  void getTopicsByDateRangeNegative() {
    Date start =
        Date.from(LocalDate.of(2019, 4, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date end = Date.from(LocalDate.of(2019, 5, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());

    List<Topic> topics = adapterUnderTest.listByDateRange(start, end, "event", "desc", 0, 100);
    assertThat(topics.size()).isEqualTo(0);
  }

  @Test
  void getTopicsInParticularDatePositive() {
    Date start =
        Date.from(LocalDate.of(2019, 12, 4).atStartOfDay(ZoneId.systemDefault()).toInstant());
    List<Topic> topics = adapterUnderTest.listByDate(start, "event", "desc", 0, 100);
    assertThat(topics.size()).isEqualTo(1);
    assertThat(topics.get(0).getId().get().getValue()).isEqualTo(2);
  }

  @Test
  void getTopicsByTitlePositive() {
    List<Topic> topics = adapterUnderTest.listByTitleLike("spring", "event", "desc", 0, 100);
    assertThat(topics.size()).isEqualTo(2);
    assertThat(topics.get(0).getId().get().getValue()).isEqualTo(1);
  }

  @Test
  void createNewTopic() {
    Topic newTopic = new Topic();
    newTopic.setTitle("Spring Core Events");
    newTopic.setEvent(Date.from(Instant.now()));
    newTopic.setType(TopicType.STUDY);
    newTopic.setStatus(TopicStatus.PENDING);
    newTopic.setScore(20);

    Topic createdTopic = adapterUnderTest.createTopic(newTopic);
    assertThat(createdTopic.getId()).isNotEmpty();
    assertThat(repository.count()).isEqualTo(5);
  }

  @Test
  void updateTopic() {
    Optional<Topic> topic = adapterUnderTest.loadTopic(new Topic.TopicId(3L));
    assertThat(topic.isPresent()).isTrue();
    Topic updateTopic = topic.get();
    updateTopic.setScore(80);
    updateTopic.setStatus(TopicStatus.INPROGRESS);
    adapterUnderTest.updateTopic(updateTopic);
    Optional<Topic> update = adapterUnderTest.loadTopic(new Topic.TopicId(3L));
    assertThat(update.isPresent()).isTrue();
    assertThat(update.get().getStatus()).isEqualTo(TopicStatus.INPROGRESS);
  }

  @Test
  void deleteTopic() {
    Boolean result = adapterUnderTest.deleteTopic(new Topic.TopicId(1L));
    assertThat(result).isTrue();
    Optional<Topic> topic = adapterUnderTest.loadTopic(new Topic.TopicId(1L));
    assertThat(topic.isPresent()).isFalse();
  }
}
