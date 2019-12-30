package org.odessajavaclub.topic.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.shared.PersistenceAdapter;
import org.odessajavaclub.topic.application.port.out.CreateTopicPort;
import org.odessajavaclub.topic.application.port.out.DeleteTopicPort;
import org.odessajavaclub.topic.application.port.out.LoadTopicPort;
import org.odessajavaclub.topic.application.port.out.UpdateTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class TopicPersistenceAdapter implements CreateTopicPort, LoadTopicPort, UpdateTopicPort, DeleteTopicPort {
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
        return topicJpaRepository.findById(topicId.getValue()).map(topicEntityMapper::toTopic);
    }

    @Override
    public List<Topic> listAll(String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRepository.findAll(pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByType(TopicType type, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRepository.findByType(type, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByStatus(TopicStatus status, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRepository.findByStatus(status, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByDateRange(Date start, Date end, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRepository.findByEventBetween(start, end, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByDate(Date start, String sortBy, String order, int page, int size) {
        LocalDate endDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRepository.findByEventBetween(start, java.sql.Date.valueOf(endDate), pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByTitleLike(String title, String sortBy, String order, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return topicJpaRepository.findByTitleLike(title, pageRequest).stream()
                .map(topicEntityMapper::toTopic)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTopic(Topic.TopicId topicId) {
        return topicJpaRepository.findById(topicId.getValue())
                .map(topic -> {
                    topicJpaRepository.delete(topic);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Topic updateTopic(Topic topic) {
        if (topic.getId().isEmpty()) {
            throw new RuntimeException("Topic ID is absent");
        }

        TopicEntity updatedEntity = topicJpaRepository.save(topicEntityMapper.toTopicJPA(topic));
        return topicEntityMapper.toTopic(updatedEntity);
    }
}
