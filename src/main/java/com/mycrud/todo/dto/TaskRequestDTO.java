package com.mycrud.todo.dto;


import jakarta.validation.constraints.NotBlank;

public record TaskRequestDTO (
        @NotBlank(message = "Titulo é obrigatório")
        String title,

        @NotBlank(message = "Descrição é obrigatório")
        String description
)  {}
