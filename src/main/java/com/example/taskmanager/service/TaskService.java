package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task salvar(Task task) {
        return taskRepository.save(task);
    }

    public void excluir(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> listarTodas() {
        return taskRepository.findAll();
    }

    public Task buscarPorId(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> buscarComFiltro(Specification<Task> spec) {
        return taskRepository.findAll(spec);
    }
} 