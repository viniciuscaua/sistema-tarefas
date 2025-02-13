package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Component
@ViewScoped
@Getter
@Setter
public class TaskController implements Serializable {

    @Autowired
    private TaskService taskService;

    private Task task = new Task();
    private List<Task> tasks;
    private Task filtro = new Task();

    @PostConstruct
    public void init() {
        carregarTarefas();
    }

    public void salvar() {
        try {
            taskService.salvar(task);
            task = new Task();
            carregarTarefas();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Tarefa salva com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar tarefa!"));
        }
    }

    public void excluir(Task task) {
        try {
            taskService.excluir(task.getId());
            carregarTarefas();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Tarefa excluída com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir tarefa!"));
        }
    }

    public void editar(Task task) {
        try {
            this.task = new Task();
            this.task.setId(task.getId());
            this.task.setTitulo(task.getTitulo());
            this.task.setDescricao(task.getDescricao());
            this.task.setResponsavel(task.getResponsavel());
            this.task.setPrioridade(task.getPrioridade());
            this.task.setDeadline(task.getDeadline());
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Editando tarefa..."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao carregar tarefa para edição!"));
        }
    }

    public void cancelarEdicao() {
        this.task = new Task();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Edição cancelada!"));
    }

    public void filtrar() {
        try {
            Specification<Task> spec = Specification.where(null);
            
            if (filtro.getTitulo() != null && !filtro.getTitulo().trim().isEmpty()) {
                spec = spec.and((root, query, cb) -> 
                    cb.like(cb.lower(root.get("titulo")), "%" + filtro.getTitulo().toLowerCase().trim() + "%"));
            }
            
            if (filtro.getResponsavel() != null && !filtro.getResponsavel().trim().isEmpty()) {
                spec = spec.and((root, query, cb) -> 
                    cb.like(cb.lower(root.get("responsavel")), "%" + filtro.getResponsavel().toLowerCase().trim() + "%"));
            }
            
            if (filtro.getPrioridade() != null) {
                spec = spec.and((root, query, cb) -> 
                    cb.equal(root.get("prioridade"), filtro.getPrioridade()));
            }
            
            tasks = taskService.buscarComFiltro(spec);
            
            if (tasks.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nenhuma tarefa encontrada com os filtros informados."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Filtro aplicado com sucesso."));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao aplicar filtros!"));
            carregarTarefas();
        }
    }

    public void limparFiltros() {
        this.filtro = new Task();
        carregarTarefas();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Filtros limpos com sucesso."));
    }

    private void carregarTarefas() {
        tasks = taskService.listarTodas();
    }

    public Task.Prioridade[] getPrioridades() {
        return Task.Prioridade.values();
    }
} 