package com.mycrud.todo.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO (
    int status,
    String message,
    LocalDateTime timestamp
) {}
