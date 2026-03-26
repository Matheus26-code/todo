package com.mycrud.todo.service;

import com.mycrud.todo.dto.PageResponseDTO;
import com.mycrud.todo.dto.TaskRequestDTO;
import com.mycrud.todo.dto.TaskResponseDTO;
import com.mycrud.todo.exception.ResourceNotFoundException;
import com.mycrud.todo.model.Task;
import com.mycrud.todo.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.*;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask (TaskRequestDTO request) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        Task savedTask = taskRepository.save(task);
        return new TaskResponseDTO(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getCreatedAt()
        );
    }

    public PageResponseDTO<TaskResponseDTO> listTasks(Pageable pageable) {
        Page<TaskResponseDTO> page = taskRepository.findAll(pageable)
            .map(task -> new TaskResponseDTO(
                            task.getId(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getStatus(),
                            task.getCreatedAt()
                    ));
        return new PageResponseDTO<>(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast()
        );
    }

    public TaskResponseDTO findTaskById (Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com id: " + id));
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt());
    }

    public TaskResponseDTO updateTask (Long id, TaskRequestDTO request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com id: " + id));

        task.setTitle(request.title());
        task.setDescription(request.description());
        Task savedTask = taskRepository.save(task);
        return new TaskResponseDTO(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getCreatedAt()
        );
    }
    public void deleteTask (Long id) {
        taskRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com id: " + id));
        taskRepository.deleteById(id);
    }
}


