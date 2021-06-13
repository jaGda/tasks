package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskMapper {

    public Task mapToTask(TaskDto dto) {
        return new Task(
                dto.getId(),
                dto.getTitle(),
                dto.getContent()
        );
    }

    public TaskDto mapToTaskDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getContent()
        );
    }

    public List<TaskDto> mapToTaskDtoList(List<Task> tasks) {
        return tasks.stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }
}
