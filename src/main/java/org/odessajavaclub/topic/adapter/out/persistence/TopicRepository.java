package org.odessajavaclub.topic.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.out.CreateTopicPort;
import org.odessajavaclub.topic.application.port.out.DeleteTopicPort;
import org.odessajavaclub.topic.application.port.out.LoadTopicPort;
import org.odessajavaclub.topic.application.port.out.UpdateTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TopicRepository implements CreateTopicPort, LoadTopicPort, UpdateTopicPort, DeleteTopicPort {
    private final TopicJPARepository topicJpaRep;

    private final TopicEntityMapper topicEntityMapper;

    @Override
    public Topic createTopic(Topic topic) {
        TopicEntity topicEntity = topicEntityMapper.toTopicJPA(topic);
        TopicEntity createdTopic = topicJpaRep.save(topicEntity);
        return topicEntityMapper.toTopic(createdTopic);
    }

    @Override
    public Optional<Topic> loadTopic(Topic.TopicId topicId) {
        return topicJpaRep.findById(topicId.getValue())
                .map(topicEntityMapper::toTopic);
    }

    @Override
    public List<Topic> listAll(String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRep.findAll(pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByType(TopicType type, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRep.findByType(type, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByStatus(TopicStatus status, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRep.findByStatus(status, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByDateRange(LocalDate start, LocalDate end, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRep.findByEventBetween(start, end, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByDate(LocalDate start, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRep.findByEvent(start, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByTitleLike(String title, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRep.findByTitleLike(title, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTopic(Topic.TopicId topicId) {
        return topicJpaRep.findById(topicId.getValue())
                .map(topic -> {
                    topicJpaRep.delete(topic);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Topic updateTopic(Topic topic) {
        if (topic.getId().isEmpty()) {
            throw new RuntimeException("Topic ID is absent");
        }

        TopicEntity updatedEntity = topicJpaRep.save(topicEntityMapper.toTopicJPA(topic));
        return topicEntityMapper.toTopic(updatedEntity);
    }
}
