package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTestSuit {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper mapper;

    @MockBean
    private DbService service;

    @Test
    void shouldDeleteTask() throws Exception {
        // Given & When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(service, times(1)).deleteTask(123L);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(123L, "Title_test", "Content_test");
        Task task = new Task(123L, "Title_test", "Content_test");

        when(mapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);

        JSONObject jo = new JSONObject();
        jo.put("id", 123);
        jo.put("title", "Title_test");
        jo.put("content", "Content_test");

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(String.valueOf(jo)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title_test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content_test")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(123L, "Title_test", "Content_test");
        Task task = new Task(123L, "Title_test", "Content_test");

        when(mapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
        verify(mapper, times(1)).mapToTask(any(TaskDto.class));
        verify(service, times(1)).saveTask(task);
    }

    @Test
    void shouldFetchTaskById() throws Exception {
        // Given
        Task task = new Task(123L, "Title_test", "Content_test");
        TaskDto taskDto = new TaskDto(123L, "Title_test", "Content_test");

        when(service.getTask(123L)).thenReturn(Optional.of(task));
        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title_test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content_test")));
    }

    @Test
    void shouldFetchTaskList() throws Exception {
        // Given
        List<Task> tasks = List.of(
                new Task(231L, "Test", "test test"),
                new Task(436L, "Test", "test test")
        );
        List<TaskDto> taskDtos = List.of(
                new TaskDto(231L, "Test", "test test"),
                new TaskDto(436L, "Test", "test test")
        );

        when(service.getAllTasks()).thenReturn(tasks);
        when(mapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(436)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("test test")));
    }
}