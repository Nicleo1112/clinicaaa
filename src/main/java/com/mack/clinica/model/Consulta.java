// Define o pacote onde esta classe está localizada
package com.mack.clinica.model;

// Classe Java que representa uma consulta médica, com seus principais atributos
public class Consulta {

    // Atributo que representa a data da consulta (ex: "2025-06-01")
    private String data;

    // Atributo que representa o horário da consulta (ex: "14:30")
    private String horario;

    // Atributo que armazena o nome do médico responsável pela consulta
    private String medicoNome;

    // Atributo que armazena o nome do paciente que será atendido
    private String pacienteNome;

    // Método getter para o atributo 'data'
    public String getData() {
        return data;
    }

    // Método setter para o atributo 'data'
    public void setData(String data) {
        this.data = data;
    }

    // Método getter para o atributo 'horario'
    public String getHorario() {
        return horario;
    }

    // Método setter para o atributo 'horario'
    public void setHorario(String horario) {
        this.horario = horario;
    }

    // Método getter para o atributo 'medicoNome'
    public String getMedicoNome() {
        return medicoNome;
    }

    // Método setter para o atributo 'medicoNome'
    public void setMedicoNome(String medicoNome) {
        this.medicoNome = medicoNome;
    }

    // Método getter para o atributo 'pacienteNome'
    public String getPacienteNome() {
        return pacienteNome;
    }

    // Método setter para o atributo 'pacienteNome'
    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }
}
