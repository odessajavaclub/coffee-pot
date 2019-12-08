package org.odessajavaclub.topic.config;

import org.odessajavaclub.topic.adapter.in.web.TopicDtoMapper;
import org.odessajavaclub.topic.adapter.out.persistence.TopicEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    @Bean
    TopicDtoMapper topicDtoMapper() {
        return new TopicDtoMapper();
    }

    @Bean
    TopicEntityMapper topicEntityMapper() {
        return new TopicEntityMapper();
    }
}
