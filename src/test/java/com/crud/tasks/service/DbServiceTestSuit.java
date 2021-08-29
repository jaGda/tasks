package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DbServiceTestSuit {

    @InjectMocks
    DbService dbService;

    @Mock
    TaskRepository repository;

    private final List<Task> tasks = Arrays.asList(
            new Task(1213L, "Shopping", "Buy new leather jacket."),
            new Task(697L, "Learning", "Do the kodilla course."),
            new Task(25189L, "Cooking", "Knead pizza dough."),
            new Task(42L, "Cleaning", "Sweep the floor.")
    );

    @Test
    void testGetAllTasks() {
        // Given
        when(repository.findAll()).thenReturn(tasks);

        // When
        List<Task> result = dbService.getAllTasks();

        // Then
        assertAll(
                () -> assertEquals(4, result.size()),
                () -> assertEquals("Cooking", result.get(2).getTitle()),
                () -> assertEquals("Sweep the floor.", result.get(3).getContent()),
                () -> assertEquals(697L, result.get(1).getId())
        );
    }

    @Test
    void testGetTask() {
        // Given
        when(repository.findById(25189L)).thenReturn(Optional.of(tasks.get(2)));

        // When
        Task result = dbService.getTask(25189L).orElse(new Task());

        // Then
        assertAll(
                () -> assertEquals("Cooking", result.getTitle()),
                () -> assertEquals("Knead pizza dough.", result.getContent())
        );
    }

    @Test
    void testSaveTask() {
        // Given
        Task task = new Task(42L, "Cleaning", "Sweep the floor.");
        when(repository.save(task)).thenReturn(task);

        // When
        Task result = dbService.saveTask(task);

        // Then
        assertAll(
                () -> assertEquals(42L, result.getId()),
                () -> assertEquals("Cleaning", result.getTitle()),
                () -> assertEquals("Sweep the floor.", result.getContent())
        );
    }
}