package com.alanbrito.todoapi.application.event;

import com.alanbrito.todoapi.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskChangedEvent(
        UUID taskId,
        String title,
        TaskStatus status,
        String action,
        LocalDateTime occurredAt
) {
}