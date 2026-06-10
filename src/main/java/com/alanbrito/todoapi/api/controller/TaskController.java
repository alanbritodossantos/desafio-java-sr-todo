package com.alanbrito.todoapi.api.controller;

import com.alanbrito.todoapi.api.dto.CreateTaskRequest;
import com.alanbrito.todoapi.api.dto.TaskResponse;
import com.alanbrito.todoapi.api.dto.UpdateTaskRequest;
import com.alanbrito.todoapi.application.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody @Valid CreateTaskRequest request) {
        return taskService.create(request);
    }

    @GetMapping
    public List<TaskResponse> findAll() {
        return taskService.findAll();
    }

    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTaskRequest request
    ) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        taskService.delete(id);
    }
}