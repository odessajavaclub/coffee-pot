package org.odessajavaclub.topic.adapter.in.springevents;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.topic.adapter.in.springevents.events.CreateTopicRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.DeleteTopicByIdRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.GetTopicByIdRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.GetTopicByNameRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.UpdateTopicRequestEvent;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.application.port.in.DeleteTopicUseCase;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.application.port.in.UpdateTopicUseCase;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TopicController.class)
@Log4j2
class TopicControllerSpringEventsTest {
  @Autowired private GenericApplicationContext applicationContext;

  @MockBean private CreateTopicUseCase createTopicUseCase;

  @MockBean private UpdateTopicUseCase updateTopicUseCase;

  @MockBean private DeleteTopicUseCase deleteTopicUseCase;

  @MockBean private GetTopicsQuery topicsQuery;

  private Topic newTopic;
  private ApplicationListener<?> getTopicListener = mock(ApplicationListener.class);

  @BeforeEach
  void createTopic() {
    newTopic = new Topic();
    newTopic.setId(new Topic.TopicId(1L));
    newTopic.setEvent(new Date());
    newTopic.setTitle("Introduction");
    newTopic.setScore(0);
    newTopic.setType(TopicType.STUDY);
    newTopic.setStatus(TopicStatus.INPROGRESS);
  }

  @Test
  void createTopicAndGetEventWithNewTopic() {
    applicationContext.addApplicationListener(getTopicListener);
    when(createTopicUseCase.createTopic(any(CreateTopicUseCase.CreateTopicCommand.class)))
        .thenReturn(newTopic);

    applicationContext.publishEvent(new CreateTopicRequestEvent(this, newTopic));
    verify(createTopicUseCase).createTopic(any(CreateTopicUseCase.CreateTopicCommand.class));
    // Todo: Need to check why 2 times
    verify(getTopicListener, times(2)).onApplicationEvent(any());
  }

  @Test
  void getTopicByIdAndCatchIt() {
    applicationContext.addApplicationListener(getTopicListener);
    when(topicsQuery.getTopic(any(Topic.TopicId.class))).thenReturn(Optional.of(newTopic));
    applicationContext.publishEvent(new GetTopicByIdRequestEvent(this, new Topic.TopicId(1L)));
    verify(topicsQuery).getTopic(any(Topic.TopicId.class));
    // Todo: Need to check why 2 times
    verify(getTopicListener, times(2)).onApplicationEvent(any());
  }

  @Test
  void getTopicByNameAndCatchIt() {
    applicationContext.addApplicationListener(getTopicListener);
    when(topicsQuery.getTopicsByName("Firts", "event", "ASC", 0, 30))
        .thenReturn(Collections.singletonList(newTopic));
    applicationContext.publishEvent(
        new GetTopicByNameRequestEvent(this, "Firts", "event", "ASC", 0, 30));
    verify(topicsQuery).getTopicsByName(anyString(), anyString(), anyString(), anyInt(), anyInt());
    // Todo: Need to check why 2 times
    verify(getTopicListener, times(2)).onApplicationEvent(any());
  }

  @Test
  void updateTopic() {
    applicationContext.addApplicationListener(getTopicListener);
    newTopic.setScore(100);
    when(updateTopicUseCase.updateTopic(any(UpdateTopicUseCase.UpdateTopicCommand.class)))
        .thenReturn(Optional.of(newTopic));
    applicationContext.publishEvent(new UpdateTopicRequestEvent(this, newTopic));
    verify(updateTopicUseCase).updateTopic(any(UpdateTopicUseCase.UpdateTopicCommand.class));
    // Todo: Need to check why 2 times
    verify(getTopicListener, times(2)).onApplicationEvent(any());
  }

  @Test
  void deleteTopic() {
    when(deleteTopicUseCase.deleteTopic(any(DeleteTopicUseCase.DeleteTopicCommand.class)))
        .thenReturn(true);
    applicationContext.publishEvent(new DeleteTopicByIdRequestEvent(this, new Topic.TopicId(1L)));
    verify(deleteTopicUseCase).deleteTopic(any(DeleteTopicUseCase.DeleteTopicCommand.class));
  }
}
