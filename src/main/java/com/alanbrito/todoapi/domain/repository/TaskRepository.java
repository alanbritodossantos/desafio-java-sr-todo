package com.alanbrito.todoapi.domain.repository;

import com.alanbrito.todoapi.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}