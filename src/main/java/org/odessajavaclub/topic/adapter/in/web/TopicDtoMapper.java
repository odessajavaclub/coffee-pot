package org.odessajavaclub.topic.adapter.in.web;

import org.odessajavaclub.topic.domain.Topic;

import java.text.SimpleDateFormat;

public class TopicDtoMapper {
    private static final long UNDEFINED_ID = -1;

    public TopicDto toGetTopicDto(Topic topic) {
        long id = topic.getId().map(Topic.TopicId::getValue).orElse(UNDEFINED_ID);
        return new TopicDto(id,
                topic.getTitle(),
                new SimpleDateFormat("dd/MM/yyyy k:mm").format(topic.getEvent()),
                topic.getType(),
                topic.getAuthor(),
                topic.getScore(),
                topic.getStatus());
    }
}
