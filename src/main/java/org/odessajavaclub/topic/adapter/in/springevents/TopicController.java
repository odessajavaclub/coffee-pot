package org.odessajavaclub.topic.adapter.in.springevents;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class TopicController {
  private final ApplicationEventPublisher applicationEventPublisher;
  private final CreateTopicUseCase createTopicUseCase;
  private final UpdateTopicUseCase updateTopicUseCase;
  private final DeleteTopicUseCase deleteTopicUseCase;
  private final GetTopicsQuery topicsQuery;

  @EventListener
  public void on(CreateTopicRequestEvent event) {
    log.info("Create new topic: [{}]", event.getTopic());
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
  public void on(GetTopicByIdRequestEvent event) {
    log.info("Get Topics by id: [{}]", event.getId());
    topicsQuery
        .getTopic(event.getId())
        .ifPresent(
            topic -> {
              applicationEventPublisher.publishEvent(new GetTopicResponseEvent(this, topic));
            });
  }

  @EventListener
  public void on(GetTopicByNameRequestEvent event) {
    log.info("Get Topics by topic name: [{}]", event.getName());
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
    Optional<Topic> updatedTopic =
        updateTopicUseCase.updateTopic(
            new UpdateTopicUseCase.UpdateTopicCommand(
                topic.getId().get(),
                topic.getTitle(),
                new SimpleDateFormat("dd/MM/yyyy k:mm").format(topic.getEvent()),
                topic.getType(),
                topic.getScore(),
                topic.getStatus()));

    if (updatedTopic.isPresent()) {
      log.info("Topic: [{}] is updated", topic.getId());
      applicationEventPublisher.publishEvent(new GetTopicResponseEvent(this, updatedTopic.get()));
    }
  }

  @EventListener
  public void on(DeleteTopicByIdRequestEvent event) {
    if (deleteTopicUseCase.deleteTopic(new DeleteTopicUseCase.DeleteTopicCommand(event.getId()))) {
      log.info("Topic: [{}] successfully deleted", event.getId());
    }
  }
}
