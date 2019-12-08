package org.odessajavaclub.topic.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.out.CreateTopicPort;
import org.odessajavaclub.topic.application.port.out.LoadTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
//TODO: Add remove and update ports as well
public class TopicRepository implements CreateTopicPort, LoadTopicPort {
    private final TopicJPARepository topicJpaRepository;

    private final TopicEntityMapper topicEntityMapper;

    @Override
    public Topic createTopic(Topic topic) {
        TopicEntity topicEntity = topicEntityMapper.toTopicJPA(topic);
        TopicEntity createdTopic = topicJpaRepository.save(topicEntity);
        return topicEntityMapper.toTopic(createdTopic);
    }

    @Override
    public Optional<Topic> loadTopic(Topic.TopicId topicId) {
        return topicJpaRepository.findById(topicId.getValue())
                .map(topicEntityMapper::toTopic);
    }

    @Override
    public List<Topic> listAll() {
        return StreamSupport.stream(topicJpaRepository.findAll().spliterator(), false)
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }
}
