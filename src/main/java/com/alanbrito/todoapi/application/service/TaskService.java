package com.alanbrito.todoapi.application.service;

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

    public TaskResponse create(CreateTaskRequest request) {
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .build();

        Task savedTask = taskRepository.save(task);

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

        return TaskResponse.from(updatedTask);
    }

    public void delete(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

        taskRepository.deleteById(id);
    }
}