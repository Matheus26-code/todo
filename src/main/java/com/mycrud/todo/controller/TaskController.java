package com.mycrud.todo.controller;

import com.mycrud.todo.dto.TaskRequestDTO;
import com.mycrud.todo.dto.TaskResponseDTO;
import com.mycrud.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask (@RequestBody TaskRequestDTO request) {
        TaskResponseDTO result = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping()
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks () {
        List<TaskResponseDTO> result = taskService.listTasks();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO>findTaskById (@PathVariable Long id) {
        TaskResponseDTO result = taskService.findTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask (
            @PathVariable Long id,
            @RequestBody TaskRequestDTO request) {
        TaskResponseDTO result = taskService.updateTask(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteTask (
            @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
