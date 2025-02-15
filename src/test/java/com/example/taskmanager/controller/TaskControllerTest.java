package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private FacesContext facesContext;

    @InjectMocks
    private TaskController taskController;

    private Task task;
    private MockedStatic<FacesContext> mockedStatic;

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

        // Mock do FacesContext
        mockedStatic = mockStatic(FacesContext.class);
        mockedStatic.when(FacesContext::getCurrentInstance).thenReturn(facesContext);
    }

    @AfterEach
    void tearDown() {
        if (mockedStatic != null) {
            mockedStatic.close();
        }
    }

    @Test
    void deveSalvarTarefaComSucesso() {
        when(taskService.salvar(any(Task.class))).thenReturn(task);

        taskController.salvar();

        verify(taskService, times(1)).salvar(any(Task.class));
        verify(facesContext, times(1)).addMessage(eq(null), any(FacesMessage.class));
        assertNotNull(taskController.getTasks());
    }

    @Test
    void deveExcluirTarefaComSucesso() {
        doNothing().when(taskService).excluir(1L);

        taskController.excluir(task);

        verify(taskService, times(1)).excluir(1L);
        verify(facesContext, times(1)).addMessage(eq(null), any(FacesMessage.class));
    }

    @Test
    void deveEditarTarefaComSucesso() {
        taskController.editar(task);

        assertEquals(task.getId(), taskController.getTask().getId());
        assertEquals(task.getTitulo(), taskController.getTask().getTitulo());
        verify(facesContext, times(1)).addMessage(eq(null), any(FacesMessage.class));
    }

    @Test
    void deveCancelarEdicaoComSucesso() {
        taskController.setTask(task);
        
        taskController.cancelarEdicao();

        assertNotEquals(task.getId(), taskController.getTask().getId());
        verify(facesContext, times(1)).addMessage(eq(null), any(FacesMessage.class));
    }

    @Test
    void deveConcluirTarefaComSucesso() {
        when(taskService.salvar(any(Task.class))).thenReturn(task);

        taskController.concluirTarefa(task);

        assertEquals(Task.Situacao.CONCLUIDA, task.getSituacao());
        verify(taskService, times(1)).salvar(task);
        verify(facesContext, times(1)).addMessage(eq(null), any(FacesMessage.class));
    }

    @Test
    void deveFiltrarTarefasComSucesso() {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.buscarComFiltro(any(Specification.class))).thenReturn(tasks);

        taskController.filtrar();

        assertNotNull(taskController.getTasks());
        assertEquals(1, taskController.getTasks().size());
        verify(taskService, times(1)).buscarComFiltro(any(Specification.class));
    }

    @Test
    void deveLimparFiltrosComSucesso() {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.listarTodas()).thenReturn(tasks);

        taskController.limparFiltros();

        assertNotNull(taskController.getFiltro());
        assertNotNull(taskController.getTasks());
        verify(taskService, times(1)).listarTodas();
        verify(facesContext, times(1)).addMessage(eq(null), any(FacesMessage.class));
    }
}