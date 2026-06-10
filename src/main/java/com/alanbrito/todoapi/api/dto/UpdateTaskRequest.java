package com.alanbrito.todoapi.api.dto;

import com.alanbrito.todoapi.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
        String title,

        String description,

        @NotNull(message = "O status é obrigatório")
        TaskStatus status
) {
}