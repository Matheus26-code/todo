package com.mycrud.todo.service;

import com.mycrud.todo.dto.TaskRequestDTO;
import com.mycrud.todo.dto.TaskResponseDTO;
import com.mycrud.todo.exception.ResourceNotFoundException;
import com.mycrud.todo.model.Task;
import com.mycrud.todo.model.TaskStatus;
import com.mycrud.todo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Test
    public void deveCriarTaskComSucesso(){
        TaskRequestDTO request = new TaskRequestDTO("Testando Mock", "Validando Testes Mocks");
        Task task = new Task();
        task.setTitle("Testando Mock");
        task.setDescription("Validando Testes Mocks");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO resultado = taskService.createTask(request);

        assertNotNull(resultado);
        assertEquals("Testando Mock", resultado.title());
        assertEquals(TaskStatus.PENDING, resultado.status());
    }

    @Test
    public void deveRetornarExcecaoQuandoTaskNaoEncontrada() {

        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.findTaskById(99L)
        );

        verify(taskRepository).findById(99L);
    }

    @Test
    public void deveDeletarTaskComSucesso() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));

        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository).deleteById(1L);

    }
}
