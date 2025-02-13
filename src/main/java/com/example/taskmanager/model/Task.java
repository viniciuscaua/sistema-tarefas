package com.example.taskmanager.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tarefas")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(length = 1000)
    private String descricao;
    
    @Column(nullable = false)
    private String responsavel;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;
    
    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Situacao situacao = Situacao.EM_ANDAMENTO;
    
    public enum Prioridade {
        ALTA("Alta"),
        MEDIA("Média"),
        BAIXA("Baixa");

        private final String descricao;

        Prioridade(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public static Prioridade fromString(String text) {
            if (text != null) {
                for (Prioridade p : Prioridade.values()) {
                    if (text.equalsIgnoreCase(p.name())) {
                        return p;
                    }
                }
            }
            return null;
        }
    }

    public enum Situacao {
        EM_ANDAMENTO("Em andamento"),
        CONCLUIDA("Concluída");

        private final String descricao;

        Situacao(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
} 