package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskMapperTestSuit {

    @Autowired
    private TaskMapper mapper;

    @Test
    void testMapToTask() {
        // Given
        TaskDto dto = new TaskDto(1213L, "Shopping", "Buy new leather jacket.");

        // When
        Task task = mapper.mapToTask(dto);

        // Then
        assertAll(
                () -> assertEquals(1213L, task.getId()),
                () -> assertEquals("Shopping", task.getTitle()),
                () -> assertEquals("Buy new leather jacket.", task.getContent())
        );
    }

    @Test
    void testMapToTaskDto() {
        // Given
        Task task = new Task(1213L, "Shopping", "Buy new leather jacket.");

        // When
        TaskDto taskDto = mapper.mapToTaskDto(task);

        // Then
        assertAll(
                () -> assertEquals(1213L, taskDto.getId()),
                () -> assertEquals("Shopping", taskDto.getTitle()),
                () -> assertEquals("Buy new leather jacket.", taskDto.getContent())
        );
    }

    @Test
    void testToTaskDtoList() {
        // Given
        List<Task> tasks = Arrays.asList(
                new Task(1213L, "Shopping", "Buy new leather jacket."),
                new Task(697L, "Learning", "Do the kodilla course."),
                new Task(25189L, "Cooking", "Knead pizza dough."),
                new Task(42L, "Cleaning", "Sweep the floor.")
        );

        // When
        List<TaskDto> taskDtos = mapper.mapToTaskDtoList(tasks);

        // Then
        assertAll(
                () -> assertEquals(4, taskDtos.size()),
                () -> assertEquals("Cooking", taskDtos.get(2).getTitle()),
                () -> assertEquals("Sweep the floor.", taskDtos.get(3).getContent()),
                () -> assertEquals(697L, taskDtos.get(1).getId())
        );
    }
}