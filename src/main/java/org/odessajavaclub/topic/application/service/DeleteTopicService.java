package org.odessajavaclub.topic.application.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.DeleteTopicUseCase;
import org.odessajavaclub.topic.application.port.out.DeleteTopicPort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class DeleteTopicService implements DeleteTopicUseCase {

  private final DeleteTopicPort deleteTopicPort;

  @Override
  public boolean deleteTopic(DeleteTopicCommand command) {
    return deleteTopicPort.deleteTopic(command.getTopicId());
  }
}
