package org.odessajavaclub.topic.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.odessajavaclub.topic.application.port.in.CreateTopicUseCase;
import org.odessajavaclub.topic.application.port.in.DeleteTopicUseCase;
import org.odessajavaclub.topic.application.port.in.GetTopicsQuery;
import org.odessajavaclub.topic.application.port.in.UpdateTopicUseCase;
import org.odessajavaclub.topic.domain.Topic;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {TopicController.class})
@ActiveProfiles("test")
@WithMockUser(username = "lohika_user@gmail.com", password = "password123")
public class TopicControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateTopicUseCase createTopicUseCase;

    @MockBean
    private UpdateTopicUseCase updateTopicUseCase;

    @MockBean
    private DeleteTopicUseCase deleteTopicUseCase;

    @MockBean
    private GetTopicsQuery topicsQuery;

    @MockBean
    private TopicDtoMapper topicDtoMapper;

    @Test
    void getTopicByID_ok() throws Exception {
        mockMvc.perform(get("/topics/1"))
                .andDo(print())
                .andExpect(status().isOk());
        then(topicsQuery).should().getTopic(eq(new Topic.TopicId(1L)));
    }

    @Test
    void testCreateTopic_created() throws Exception {
        Topic newTopic = new Topic();
        newTopic.setTitle("ETCD");
        newTopic.setStatus(TopicStatus.PENDING);
        newTopic.setScore(50);
        newTopic.setType(TopicType.WORKSHOP);
        newTopic.setEvent(Date.from(Instant.now()));

        String content = jsonMapper.writeValueAsString(newTopic);
        mockMvc.perform(post("/topics")
                .contentType(APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isCreated());
        then(createTopicUseCase).should().createTopic(eq(new CreateTopicUseCase.CreateTopicCommand(newTopic)));
    }
}
