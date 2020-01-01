package org.odessajavaclub.topic.adapter.out.inmemory;

import org.odessajavaclub.topic.application.port.out.CreateTopicPort;
import org.odessajavaclub.topic.application.port.out.DeleteTopicPort;
import org.odessajavaclub.topic.application.port.out.LoadTopicPort;
import org.odessajavaclub.topic.application.port.out.UpdateTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
@Profile("alpha")
public class TopicInMemoryAdapter implements CreateTopicPort, LoadTopicPort, UpdateTopicPort, DeleteTopicPort {
    private static AtomicLong id = new AtomicLong(1L);
    private Map<Long, Topic> storage = new HashMap<>();

    @Override
    public Topic createTopic(Topic topic) {
        storage.put(id.get(), topic);
        return Topic.from(topic, new Topic.TopicId(id.getAndIncrement()));
    }

    @Override
    public Optional<Topic> loadTopic(Topic.TopicId topicId) {
        return Optional.ofNullable(storage.get(topicId.getValue()))
                .map(topic -> Topic.from(topic, topicId));
    }

    @Override
    public List<Topic> listAll(String sortBy, String order, int page, int size) {
        return storage.entrySet()
                .stream()
                .map(e -> Topic.from(e.getValue(), new Topic.TopicId(e.getKey())))
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByType(TopicType type, String sortBy, String order, int page, int size) {
        return storage.entrySet()
                .stream()
                .map(e -> Topic.from(e.getValue(), new Topic.TopicId(e.getKey())))
                .filter(e -> e.getType() == type)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByStatus(TopicStatus status, String sortBy, String order, int page, int size) {
        return storage.entrySet()
                .stream()
                .map(e -> Topic.from(e.getValue(), new Topic.TopicId(e.getKey())))
                .filter(e -> e.getStatus() == status)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByDateRange(Date start, Date end, String sortBy, String order, int page, int size) {
        return storage.entrySet()
                .stream()
                .map(e -> Topic.from(e.getValue(), new Topic.TopicId(e.getKey())))
                .filter(e -> e.getEvent().after(start) && e.getEvent().before(end))
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByDate(Date start, String sortBy, String order, int page, int size) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return storage.entrySet()
                .stream()
                .map(e -> Topic.from(e.getValue(), new Topic.TopicId(e.getKey())))
                .filter(e -> sdf.format(e.getEvent()).equals(sdf.format(start)))
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Topic> listByTitleLike(String title, String sortBy, String order, int page, int size) {
        return storage.entrySet()
                .stream()
                .map(e -> Topic.from(e.getValue(), new Topic.TopicId(e.getKey())))
                .filter(e -> e.getTitle().contains(title))
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTopic(Topic.TopicId topicId) {
        return storage.remove(topicId.getValue()) != null;
    }

    @Override
    public Topic updateTopic(Topic topic) {
        return Optional.ofNullable(topic.getId().get().getValue())
                .map(id -> {
                    storage.put(id, topic);
                    return topic;
                })
                .orElse(null);
    }
}
