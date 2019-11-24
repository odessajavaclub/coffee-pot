package org.odessajavaclub.topic.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.domain.Topic;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
public class CreateTopicService implements CreateTopicUseCase {

    @Override
    public Topic createTopic(CreateTopicCommand command) {
        return null;
    }
}
