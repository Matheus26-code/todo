package com.mycrud.todo.dto;

import com.mycrud.todo.model.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponseDTO (
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt
) { }
