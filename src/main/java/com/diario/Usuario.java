package com.diario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity 
@Table(name = "usuarios") // Define o nome da tabela no PostgreSQL
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true) // Garante que não existam dois usuários com o mesmo e-mail
    private String email;

    private String senha;

    // Construtor vazio (obrigatório para o JPA)
    public Usuario() {
    }

    // Construtor para testes (adicionamos o email aqui também)
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; } // NOVO
    public void setEmail(String email) { this.email = email; } // NOVO

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}