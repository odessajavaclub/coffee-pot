package org.odessajavaclub.topic.adapter.in.springevents;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.adapter.in.springevents.events.CreateTopicRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.DeleteTopicByIdRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.GetTopicByIdRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.GetTopicByNameRequestEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.GetTopicResponseEvent;
import org.odessajavaclub.topic.adapter.in.springevents.events.UpdateTopicRequestEvent;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.application.port.in.DeleteTopicUseCase;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.application.port.in.UpdateTopicUseCase;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.text.SimpleDateFormat;

@RequiredArgsConstructor
public class TopicController {
  private final ApplicationEventPublisher applicationEventPublisher;
  private final CreateTopicUseCase createTopicUseCase;
  private final UpdateTopicUseCase updateTopicUseCase;
  private final DeleteTopicUseCase deleteTopicUseCase;
  private final GetTopicsQuery topicsQuery;

  @EventListener
  public void on(CreateTopicRequestEvent event) {
    Topic topic = event.getTopic();
    Topic createdTopic =
        createTopicUseCase.createTopic(
            new CreateTopicUseCase.CreateTopicCommand(
                topic.getTitle(),
                new SimpleDateFormat("dd/MM/yyyy k:mm").format(topic.getEvent()),
                topic.getType(),
                topic.getScore(),
                topic.getStatus()));

    applicationEventPublisher.publishEvent(new GetTopicResponseEvent(this, createdTopic));
  }

  @EventListener
  public void on(GetTopicByIdRequestEvent requestEvent) {
    topicsQuery
        .getTopic(requestEvent.getId())
        .ifPresent(
            topic -> {
              applicationEventPublisher.publishEvent(new GetTopicResponseEvent(this, topic));
            });
  }

  @EventListener
  public void on(GetTopicByNameRequestEvent event) {
    topicsQuery
        .getTopicsByName(
            event.getName(), event.getSortBy(), event.getOrder(), event.getPage(), event.getSize())
        .forEach(
            topic -> {
              applicationEventPublisher.publishEvent(new GetTopicResponseEvent(this, topic));
            });
  }

  @EventListener
  public void on(UpdateTopicRequestEvent event) {
    Topic topic = event.getTopic();
    Topic updatedTopic =
        updateTopicUseCase
            .updateTopic(
                new UpdateTopicUseCase.UpdateTopicCommand(
                    topic.getId().get(),
                    topic.getTitle(),
                    new SimpleDateFormat("dd/MM/yyyy k:mm").format(topic.getEvent()),
                    topic.getType(),
                    topic.getScore(),
                    topic.getStatus()))
            .get(); // FIXME: Add additional check
    applicationEventPublisher.publishEvent(new GetTopicResponseEvent(this, updatedTopic));
  }

  @EventListener
  public void on(DeleteTopicByIdRequestEvent event) {
    if (deleteTopicUseCase.deleteTopic(new DeleteTopicUseCase.DeleteTopicCommand(event.getId()))) {
      // TODO: Add log
    }
  }
}
