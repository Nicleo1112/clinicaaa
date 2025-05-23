package com.mack.clinica.model;

public class Consulta {
    private String data;
    private String horario;
    private String medicoNome;
    private String pacienteNome;

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public String getMedicoNome() {
        return medicoNome;
    }
    public void setMedicoNome(String medicoNome) {
        this.medicoNome = medicoNome;
    }
    public String getPacienteNome() {
        return pacienteNome;
    }
    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }
}
