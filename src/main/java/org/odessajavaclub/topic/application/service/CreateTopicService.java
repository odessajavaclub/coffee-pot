package org.odessajavaclub.topic.application.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.application.port.out.CreateTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateTopicService implements CreateTopicUseCase {

  private final CreateTopicPort createTopicPort;

  @Override
  public Topic createTopic(CreateTopicCommand command) {
    Topic newTopic = command.getTopic();
    return createTopicPort.createTopic(newTopic);
  }
}
