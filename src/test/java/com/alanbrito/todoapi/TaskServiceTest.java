package com.alanbrito.todoapi.application.service;

import com.alanbrito.todoapi.application.event.TaskChangedEvent;
import com.alanbrito.todoapi.api.dto.CreateTaskRequest;
import com.alanbrito.todoapi.api.dto.TaskResponse;
import com.alanbrito.todoapi.domain.enums.TaskStatus;
import com.alanbrito.todoapi.domain.model.Task;
import com.alanbrito.todoapi.domain.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private final TaskRepository taskRepository = mock(TaskRepository.class);
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    private final TaskService taskService = new TaskService(taskRepository, eventPublisher);

    @Test
    void shouldCreateTaskSuccessfully() {
        CreateTaskRequest request = new CreateTaskRequest(
                "Nova tarefa",
                "Descricao da tarefa"
        );

        Task savedTask = Task.builder()
                .title(request.title())
                .description(request.description())
                .build();

        savedTask.prePersist();

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse response = taskService.create(request);

        assertNotNull(response.id());
        assertEquals("Nova tarefa", response.title());
        assertEquals("Descricao da tarefa", response.description());
        assertEquals(TaskStatus.PENDING, response.status());
        assertNotNull(response.createdAt());

        verify(taskRepository, times(1)).save(any(Task.class));        
        verify(eventPublisher, times(1)).publishEvent(any(TaskChangedEvent.class));
    }
}