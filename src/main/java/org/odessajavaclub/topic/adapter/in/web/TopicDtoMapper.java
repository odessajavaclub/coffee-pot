package org.odessajavaclub.topic.adapter.in.web;

import org.odessajavaclub.topic.domain.Topic;

public class TopicDtoMapper {
    private static final long UNDEFINED_ID = -1;

    public TopicDto toGetTopicDto(Topic topic) {
        long id = topic.getId().map(Topic.TopicId::getValue).orElse(UNDEFINED_ID);
        return new TopicDto(id,
                topic.getTitle(),
                topic.getEvent(),
                topic.getType().name(),
                topic.getAuthor(),
                topic.getScore(),
                topic.getStatus().name());
    }
}
