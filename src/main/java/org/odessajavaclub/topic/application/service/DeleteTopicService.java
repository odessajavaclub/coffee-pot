package org.odessajavaclub.topic.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.DeleteTopicUseCase;
import org.odessajavaclub.topic.application.port.out.DeleteTopicPort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
