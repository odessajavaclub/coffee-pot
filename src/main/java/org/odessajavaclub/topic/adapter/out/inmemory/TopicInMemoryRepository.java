package org.odessajavaclub.topic.adapter.out.inmemory;

import org.odessajavaclub.topic.application.port.out.CreateTopicPort;
import org.odessajavaclub.topic.application.port.out.LoadTopicPort;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class TopicInMemoryRepository implements CreateTopicPort, LoadTopicPort {
    private static AtomicLong id = new AtomicLong(1L);
    private Map<Long, Topic> storage = new HashMap<>();

    @Override
    public Topic createTopic(Topic topic) {
        storage.put(id.get(), topic);
        return Topic.from(topic, new Topic.TopicId(id.getAndIncrement()));
    }

    @Override
    public Topic loadTopic(Topic.TopicId topicId) {
        return Optional.ofNullable(storage.get(topicId.getValue()))
                .map(topic -> Topic.from(topic, topicId))
                .orElse(null);
    }

    @Override
    public List<Topic> listAll() {
        return storage.entrySet()
                .stream()
                .map(e -> Topic.from(e.getValue(), new Topic.TopicId(e.getKey())))
                .collect(Collectors.toList());
    }
}
