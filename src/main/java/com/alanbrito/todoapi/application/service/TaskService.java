package com.alanbrito.todoapi.application.service;

import com.alanbrito.todoapi.application.event.TaskChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import java.time.LocalDateTime;
import com.alanbrito.todoapi.api.dto.CreateTaskRequest;
import com.alanbrito.todoapi.api.dto.TaskResponse;
import com.alanbrito.todoapi.api.dto.UpdateTaskRequest;
import com.alanbrito.todoapi.domain.model.Task;
import com.alanbrito.todoapi.domain.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TaskResponse create(CreateTaskRequest request) {
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .build();

        Task savedTask = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskChangedEvent(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getStatus(),
                "CREATED",
                LocalDateTime.now()
        ));

        return TaskResponse.from(savedTask);
    }

    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    public TaskResponse update(UUID id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());

        Task updatedTask = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskChangedEvent(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getStatus(),
                "UPDATED",
                LocalDateTime.now()
        ));

        return TaskResponse.from(updatedTask);
    }

    public void delete(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

        taskRepository.deleteById(id);
    }
}