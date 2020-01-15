package org.odessajavaclub.topic.adapter.in.springevents;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.topic.adapter.in.springevents.events.CreateTopicRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.GetTopicResponseEvent;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.application.port.in.DeleteTopicUseCase;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.application.port.in.UpdateTopicUseCase;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TopicController.class)
@Log4j2
class TopicControllerSpringEventsTest {
  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @MockBean private CreateTopicUseCase createTopicUseCase;

  @MockBean private UpdateTopicUseCase updateTopicUseCase;

  @MockBean private DeleteTopicUseCase deleteTopicUseCase;

  @MockBean private GetTopicsQuery topicsQuery;


  @Test
  void createTopic() {
    Topic newTopic = mock(Topic.class);
    when(newTopic.getEvent()).thenReturn(new Date());
    when(createTopicUseCase.createTopic(any(CreateTopicUseCase.CreateTopicCommand.class)))
        .thenReturn(newTopic);

    //TODO: Fix this, can't catch event. Maybe need to register listener
    new ApplicationListener<GetTopicResponseEvent>() {
      @Override
      public void onApplicationEvent(GetTopicResponseEvent getTopicResponseEvent) {
        verify(getTopicResponseEvent.getTopic()).equals(newTopic);
      }
    };
    applicationEventPublisher.publishEvent(new CreateTopicRequestEvent(this, newTopic));
    verify(createTopicUseCase).createTopic(any(CreateTopicUseCase.CreateTopicCommand.class));
  }
}
