package org.odessajavaclub.topic.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.auth.AuthenticationFacade;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.application.port.in.DeleteTopicUseCase;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.application.port.in.UpdateTopicUseCase;
import org.odessajavaclub.topic.config.TopicConfig;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TopicController.class})
@ActiveProfiles("test")
@WithMockUser(username = "testUser", password = "")
@Import(TopicConfig.class)
public class TopicControllerTest {
  private static final String USER_NAME = "testUser";
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @MockBean private AuthenticationFacade authenticationFacade;

  @Autowired private MockMvc mockMvc;

  @MockBean private CreateTopicUseCase createTopicUseCase;

  @MockBean private UpdateTopicUseCase updateTopicUseCase;

  @MockBean private DeleteTopicUseCase deleteTopicUseCase;

  @MockBean private GetTopicsQuery topicsQuery;

  private TopicDto topicDto =
      new TopicDto(
          1, "ETCD", "21/01/2020 13:00", TopicType.WORKSHOP, "apletnev", 20, TopicStatus.PENDING);

  @BeforeEach
  public void setUp() {
    TestingAuthenticationToken authentication =
        new TestingAuthenticationToken(USER_NAME, "some random pass", "USER");
    when(authenticationFacade.getAuthentication()).thenReturn(authentication);
  }

  @Test
  void createTopic_created() throws Exception {
    String content = jsonMapper.writeValueAsString(topicDto);

    when(createTopicUseCase.createTopic(any(CreateTopicUseCase.CreateTopicCommand.class)))
        .thenReturn(new TopicDtoMapper().toTopic(topicDto));

    mockMvc
        .perform(post("/topics").contentType(APPLICATION_JSON).content(content))
        .andDo(print())
        .andExpect(status().isCreated());

    then(createTopicUseCase)
        .should()
        .createTopic(
            eq(
                new CreateTopicUseCase.CreateTopicCommand(
                    topicDto.getTitle(),
                    topicDto.getEvent(),
                    topicDto.getType(),
                    topicDto.getScore(),
                    topicDto.getStatus())));
  }

  @Test
  void updateTopic_ok() throws Exception {
    String content = jsonMapper.writeValueAsString(topicDto);
    when(updateTopicUseCase.updateTopic(any(UpdateTopicUseCase.UpdateTopicCommand.class)))
        .thenReturn(Optional.of(new TopicDtoMapper().toTopic(topicDto)));

    mockMvc
        .perform(put("/topics/1").contentType(APPLICATION_JSON).content(content))
        .andDo(print())
        .andExpect(status().isOk());

    then(updateTopicUseCase)
        .should()
        .updateTopic(
            eq(
                new UpdateTopicUseCase.UpdateTopicCommand(
                    new Topic.TopicId(1L),
                    topicDto.getTitle(),
                    topicDto.getEvent(),
                    topicDto.getType(),
                    topicDto.getScore(),
                    topicDto.getStatus())));
  }

  @Test
  void deleteTopic_ok() throws Exception {
    String content = jsonMapper.writeValueAsString(topicDto);
    when(deleteTopicUseCase.deleteTopic(any(DeleteTopicUseCase.DeleteTopicCommand.class)))
        .thenReturn(true);

    mockMvc
        .perform(delete("/topics/1").contentType(APPLICATION_JSON).content(content))
        .andDo(print())
        .andExpect(status().isNoContent());

    then(deleteTopicUseCase)
        .should()
        .deleteTopic(eq(new DeleteTopicUseCase.DeleteTopicCommand(new Topic.TopicId(1L))));
  }

  @Test
  void getTopicByID_ok() throws Exception {
    Optional<Topic> optional = Optional.of(new TopicDtoMapper().toTopic(topicDto));
    when(topicsQuery.getTopic(any(Topic.TopicId.class))).thenReturn(optional);
    mockMvc.perform(get("/topics/1")).andDo(print()).andExpect(status().isOk());
    then(topicsQuery).should().getTopic(eq(new Topic.TopicId(1L)));
  }

  @Test
  void getTopicsByStatus_ok() throws Exception {
    List<Topic> result = new ArrayList<>();
    result.add(new TopicDtoMapper().toTopic(topicDto));
    when(topicsQuery.getTopicsByStatus(
            any(TopicStatus.class), anyString(), anyString(), anyInt(), anyInt()))
        .thenReturn(result);
    mockMvc.perform(get("/topics/status/PENDING")).andDo(print()).andExpect(status().isOk());
    // FIXME: Add check for eq by enum
    // then(topicsQuery).should().getTopicsByStatus(eq((TopicStatus)any(), "event", "ASC", 0, 100));
  }

  @Test
  void getTopicsByType_ok() throws Exception {
    List<Topic> result = new ArrayList<>();
    result.add(new TopicDtoMapper().toTopic(topicDto));
    when(topicsQuery.getTopicsByType(
            any(TopicType.class), anyString(), anyString(), anyInt(), anyInt()))
        .thenReturn(result);
    mockMvc.perform(get("/topics/type/WORKSHOP")).andDo(print()).andExpect(status().isOk());
    // then(topicsQuery).should().getTopicsByStatus(eq((TopicStatus)any(), "event", "ASC", 0, 100));
  }

  @Test
  void getTopicsByName_ok() throws Exception {
    List<Topic> result = new ArrayList<>();
    result.add(new TopicDtoMapper().toTopic(topicDto));
    when(topicsQuery.getTopicsByName(anyString(), anyString(), anyString(), anyInt(), anyInt()))
        .thenReturn(result);
    mockMvc.perform(get("/topics/name/ETCD")).andDo(print()).andExpect(status().isOk());
    // then(topicsQuery).should().getTopicsByStatus(eq((TopicStatus)any(), "event", "ASC", 0, 100));
  }

  @Test
  void getTopicsByDate_ok() throws Exception {
    List<Topic> result = new ArrayList<>();
    result.add(new TopicDtoMapper().toTopic(topicDto));
    when(topicsQuery.getTopicsInDate(any(), anyString(), anyString(), anyInt(), anyInt()))
        .thenReturn(result);
    mockMvc.perform(get("/topics/date/01-01-2020")).andDo(print()).andExpect(status().isOk());
    // then(topicsQuery).should().getTopicsByStatus(eq((TopicStatus)any(), "event", "ASC", 0, 100));
  }

  @Test
  void getTopicsByRange_ok() throws Exception {
    List<Topic> result = new ArrayList<>();
    result.add(new TopicDtoMapper().toTopic(topicDto));
    when(topicsQuery.getTopicsInDateRange(
            any(), any(), anyString(), anyString(), anyInt(), anyInt()))
        .thenReturn(result);
    mockMvc
        .perform(get("/topics/range/01-01-2020/01-01-2021"))
        .andDo(print())
        .andExpect(status().isOk());
    // then(topicsQuery).should().getTopicsByStatus(eq((TopicStatus)any(), "event", "ASC", 0, 100));
  }
}
