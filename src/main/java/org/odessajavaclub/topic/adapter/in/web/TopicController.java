package org.odessajavaclub.topic.adapter.in.web;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.application.port.in.DeleteTopicUseCase;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.application.port.in.UpdateTopicUseCase;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/topics")
public class TopicController {
  private final CreateTopicUseCase createTopicUseCase;
  private final UpdateTopicUseCase updateTopicUseCase;
  private final DeleteTopicUseCase deleteTopicUseCase;
  private final GetTopicsQuery topicsQuery;
  private final TopicDtoMapper topicDtoMapper;

  @GetMapping
  List<TopicDto> listTopics(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "100") int size,
      @RequestParam(value = "sortBy", defaultValue = "event") String sortBy,
      @RequestParam(value = "order", defaultValue = "ASC") String order) {
    return topicsQuery.getTopics(sortBy, order, page, size).stream()
        .map(topicDtoMapper::toGetTopicDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  ResponseEntity<TopicDto> getTopic(@PathVariable Long id) {
    return topicsQuery
        .getTopic(new Topic.TopicId(id))
        .map(topicDtoMapper::toGetTopicDto)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/status/{status}")
  List<TopicDto> listTopicsByAuthor(
      @PathVariable TopicStatus status,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "100") int size,
      @RequestParam(value = "sortBy", defaultValue = "event") String sortBy,
      @RequestParam(value = "order", defaultValue = "ASC") String order) {
    return topicsQuery.getTopicsByStatus(status, sortBy, order, page, size).stream()
        .map(topicDtoMapper::toGetTopicDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/type/{type}")
  List<TopicDto> listTopicsByAuthor(
      @PathVariable TopicType type,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "100") int size,
      @RequestParam(required = false, value = "sortBy", defaultValue = "event") String sortBy,
      @RequestParam(required = false, value = "order", defaultValue = "ASC") String order) {
    return topicsQuery.getTopicsByType(type, sortBy, order, page, size).stream()
        .map(topicDtoMapper::toGetTopicDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/name/{name}")
  List<TopicDto> listTopicsByName(
      @PathVariable String name,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "100") int size,
      @RequestParam(required = false, value = "sortBy", defaultValue = "event") String sortBy,
      @RequestParam(required = false, value = "order", defaultValue = "ASC") String order) {
    return topicsQuery.getTopicsByName(name, sortBy, order, page, size).stream()
        .map(topicDtoMapper::toGetTopicDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/date/{date}")
  List<TopicDto> listTopicsByDate(
      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "100") int size,
      @RequestParam(required = false, value = "sortBy", defaultValue = "event") String sortBy,
      @RequestParam(required = false, value = "order", defaultValue = "ASC") String order) {
    return topicsQuery.getTopicsInDate(date, sortBy, order, page, size).stream()
        .map(topicDtoMapper::toGetTopicDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/range/{start}/{end}")
  List<TopicDto> listTopicsByDateRange(
      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date end,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "100") int size,
      @RequestParam(required = false, value = "sortBy", defaultValue = "event") String sortBy,
      @RequestParam(required = false, value = "order", defaultValue = "ASC") String order) {
    return topicsQuery.getTopicsInDateRange(start, end, sortBy, order, page, size).stream()
        .map(topicDtoMapper::toGetTopicDto)
        .collect(Collectors.toList());
  }

  @PostMapping
  ResponseEntity<TopicDto> createTopic(@Valid @RequestBody TopicDto topic) {
    // a command uses for validation purposes
    CreateTopicUseCase.CreateTopicCommand command =
        new CreateTopicUseCase.CreateTopicCommand(
            topic.getTitle(),
            topic.getEvent(),
            topic.getType(),
            topic.getScore(),
            topic.getStatus());
    TopicDto created = topicDtoMapper.toGetTopicDto(createTopicUseCase.createTopic(command));
    if (created != null) {
      URI uri =
          MvcUriComponentsBuilder.fromController(getClass())
              .path("/{id}")
              .buildAndExpand(created.getId())
              .toUri();
      return ResponseEntity.created(uri).body(created);
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{id}")
  ResponseEntity<TopicDto> updateTopic(@PathVariable Long id, @Valid @RequestBody TopicDto topic) {
    UpdateTopicUseCase.UpdateTopicCommand command =
        new UpdateTopicUseCase.UpdateTopicCommand(
            new Topic.TopicId(id),
            topic.getTitle(),
            topic.getEvent(),
            topic.getType(),
            topic.getScore(),
            topic.getStatus());
    return updateTopicUseCase
        .updateTopic(command)
        .map(topicDtoMapper::toGetTopicDto)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteTopic(@PathVariable Long id) {
    return deleteTopicUseCase.deleteTopic(
            new DeleteTopicUseCase.DeleteTopicCommand(new Topic.TopicId(id)))
        ? ResponseEntity.noContent().build()
        : ResponseEntity.notFound().build();
  }
}
