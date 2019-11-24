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


@RestController
@RequiredArgsConstructor
public class TopicController {
    private final CreateTopicUseCase createTopicUseCase;
    private final GetTopicsQuery topicsQuery;


    @GetMapping("/topics")
    List<Topic> listTopics() {
        return topicsQuery.getTopics();
    }

    @GetMapping("/topics/{id}")
    ResponseEntity<Topic> listTopic(@PathVariable Long id) {
        return topicsQuery.getTopic(new Topic.TopicId(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/topics")
    ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        // a command uses for validation purposes
        CreateTopicUseCase.CreateTopicCommand command = new CreateTopicUseCase.CreateTopicCommand(topic);
        return createTopicUseCase.createTopic(command)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
