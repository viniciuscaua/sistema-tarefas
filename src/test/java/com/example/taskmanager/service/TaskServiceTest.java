package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitulo("Teste");
        task.setDescricao("Descrição teste");
        task.setResponsavel("João");
        task.setPrioridade(Task.Prioridade.ALTA);
        task.setDeadline(LocalDate.now());
        task.setSituacao(Task.Situacao.EM_ANDAMENTO);
    }

    @Test
    void deveSalvarTarefaComSucesso() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task savedTask = taskService.salvar(task);

        assertNotNull(savedTask);
        assertEquals(task.getTitulo(), savedTask.getTitulo());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deveExcluirTarefaComSucesso() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.excluir(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveListarTodasTarefas() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.listarTodas();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarTarefaPorId() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void deveRetornarNullQuandoTarefaNaoEncontrada() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Task result = taskService.buscarPorId(1L);

        assertNull(result);
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void deveBuscarTarefasComFiltro() {
        List<Task> tasks = Arrays.asList(task);
        Specification<Task> spec = mock(Specification.class);
        doReturn(tasks).when(taskRepository).findAll(any(Specification.class));

        List<Task> result = taskService.buscarComFiltro(spec);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll(any(Specification.class));
    }
} 