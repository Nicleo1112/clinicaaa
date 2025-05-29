// Define o pacote onde a classe está localizada
package com.mack.clinica.model;

/**
 * Modelo que representa o usuário do sistema.
 * Essa classe serve como um bean Java (POJO) para armazenar os dados de um usuário.
 */
public class Usuario {
    // Atributo que representa o identificador único do usuário
    private int id;

    // Atributo que representa o nome do usuário
    private String nome;

    // Atributo que indica o tipo de usuário (por exemplo, "paciente", "admin" ou "medico")
    private String tipo; // paciente ou admin

    // Atributo que representa o e-mail do usuário
    private String email;

    // Atributo que representa o CPF do usuário
    private String cpf;

    // Atributo que representa o telefone fixo do usuário
    private String telefone;

    // Atributo que representa o telefone celular do usuário
    private String celular;

    // Métodos getters e setters abaixo permitem acessar e modificar os atributos privados da classe

    // Retorna o ID do usuário
    public int getId() {
        return id;
    }

    // Define o ID do usuário
    public void setId(int id) {
        this.id = id;
    }

    // Retorna o nome do usuário
    public String getNome() {
        return nome;
    }

    // Define o nome do usuário
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o tipo de usuário (paciente, admin, etc.)
    public String getTipo() {
        return tipo;
    }

    // Define o tipo de usuário
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Retorna o e-mail do usuário
    public String getEmail() {
        return email;
    }

    // Define o e-mail do usuário
    public void setEmail(String email) {
        this.email = email;
    }

    // Retorna o CPF do usuário
    public String getCpf() {
        return cpf;
    }

    // Define o CPF do usuário
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Retorna o telefone fixo do usuário
    public String getTelefone() {
        return telefone;
    }

    // Define o telefone fixo do usuário
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Retorna o telefone celular do usuário
    public String getCelular() {
        return celular;
    }

    // Define o telefone celular do usuário
    public void setCelular(String celular) {
        this.celular = celular;
    }
}
