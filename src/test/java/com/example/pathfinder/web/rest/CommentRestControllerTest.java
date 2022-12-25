package com.example.pathfinder.web.rest;

import com.example.pathfinder.model.dto.CommentCreationDto;
import com.example.pathfinder.model.dto.CommentMessageDto;
import com.example.pathfinder.model.view.CommentDisplayView;
import com.example.pathfinder.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

    private static final Long ROUTE_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    @WithMockUser
    public void getComments_twoCommentsExist_commentsReturnedAsJsonAndStatusIsOk() throws Exception {
        when(commentService.getAllCommentsForRoute(ROUTE_ID))
                .thenReturn(List.of(
                        new CommentDisplayView(1L, "John Doe", "Test comment #1"),
                        new CommentDisplayView(2L, "Foo Bar", "Test comment #2")
                ));

        mockMvc.perform(get("/api/" + ROUTE_ID + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorName", is("John Doe")))
                .andExpect(jsonPath("$.[0].message", is("Test comment #1")))
                .andExpect(jsonPath("$.[1].authorName", is("Foo Bar")))
                .andExpect(jsonPath("$.[1].message", is("Test comment #2")));
    }

    @Test
    @WithMockUser(username = "testUsername")
    public void createComment_sampleData_commentIsReturnedAsExpected() throws Exception {
        when(commentService.createComment(any()))
                .thenAnswer(interaction -> {
                    CommentCreationDto commentCreationDto = interaction.getArgument(0);
                    return new CommentDisplayView(
                            1L,
                            commentCreationDto.getUsername(),
                            commentCreationDto.getMessage());

                });

        CommentMessageDto commentMessageDto = new CommentMessageDto("This is comment #1");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/" + ROUTE_ID + "/comments")
                        .content(objectMapper.writeValueAsString(commentMessageDto))
                        .with(csrf())
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.message", is("This is comment #1")))
                .andExpect(jsonPath("$.authorName", is("testUsername")));
    }

}