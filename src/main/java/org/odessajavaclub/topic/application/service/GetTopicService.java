package org.odessajavaclub.topic.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.application.port.out.LoadTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class GetTopicService implements GetTopicsQuery {
    private final LoadTopicPort loadTopicPort;

    @Override
    public Optional<Topic> getTopic(Topic.TopicId topicId) {
        return Optional.ofNullable(loadTopicPort.loadTopic(topicId));
    }

    @Override
    public List<Topic> getTopics() {
        return loadTopicPort.listAll();
    }

    @Override
    public Optional<Topic> getTopicByName(String topicName) {
        //TODO: Implement me
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public List<Topic> getTopicsByAuthor(String authorName) {
        //TODO: Implement me
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public List<Topic> getTopicsByCategory(String category) {
        //TODO: Implement me
        throw new RuntimeException("Not implemented yet");
    }
}
