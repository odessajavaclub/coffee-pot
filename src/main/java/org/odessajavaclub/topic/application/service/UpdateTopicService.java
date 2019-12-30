package org.odessajavaclub.topic.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.UpdateTopicUseCase;
import org.odessajavaclub.topic.application.port.out.UpdateTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UpdateTopicService implements UpdateTopicUseCase {

    private final UpdateTopicPort updateTopicPort;

    @Override
    public Optional<Topic> updateTopic(UpdateTopicCommand command) {
        Topic updateTopic = command.getTopic();
        return Optional.ofNullable(updateTopicPort.updateTopic(updateTopic));
    }
}
