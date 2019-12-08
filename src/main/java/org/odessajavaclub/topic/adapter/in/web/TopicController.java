package org.odessajavaclub.topic.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.domain.Topic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class TopicController {
    private final CreateTopicUseCase createTopicUseCase;
    private final GetTopicsQuery topicsQuery;
    private final TopicDtoMapper topicDtoMapper;


    @GetMapping("/topics")
    List<TopicDto> listTopics() {
        return topicsQuery.getTopics()
                .stream()
                .map(topicDtoMapper::toGetTopicDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/topics/{id}")
    ResponseEntity<TopicDto> listTopic(@PathVariable Long id) {
        return topicsQuery.getTopic(new Topic.TopicId(id))
                .map(topicDtoMapper::toGetTopicDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/topics")
    ResponseEntity<TopicDto> createTopic(@RequestBody Topic topic) {
        // a command uses for validation purposes
        CreateTopicUseCase.CreateTopicCommand command = new CreateTopicUseCase.CreateTopicCommand(topic);
        return createTopicUseCase.createTopic(command)
                .map(topicDtoMapper::toGetTopicDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //TODO: Add PUT and DELETE
}
