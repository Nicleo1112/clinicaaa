package com.mack.clinica.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mack.clinica.util.DatabaseConnection;

public class ConsultaDAO {
    public static List<Consulta> listarConsultasPorPaciente(int pacienteId, String realPathBase) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.data_hora, u.nome as medico_nome FROM consultas c " +
                     "JOIN usuarios u ON c.profissional_id = u.id " +
                     "WHERE c.paciente_id = ? ORDER BY c.data_hora DESC";
        try (Connection conn = DatabaseConnection.getConnection(realPathBase);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Consulta consulta = new Consulta();
                String dataHora = rs.getString("data_hora");
                if (dataHora != null && dataHora.length() >= 16) {
                    consulta.setData(dataHora.substring(0, 10));
                    consulta.setHorario(dataHora.substring(11, 16));
                } else {
                    consulta.setData(dataHora);
                    consulta.setHorario("");
                }
                consulta.setMedicoNome(rs.getString("medico_nome"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas do paciente.", e);
        }
        return consultas;
    }

    public static List<Consulta> listarConsultasFiltrado(String paciente, String medico, String data, String realPathBase) {
        List<Consulta> consultas = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.data_hora, p.nome as paciente_nome, m.nome as medico_nome FROM consultas c ");
        sql.append("JOIN usuarios p ON c.paciente_id = p.id ");
        sql.append("JOIN usuarios m ON c.profissional_id = m.id WHERE 1=1 ");
        if (paciente != null && !paciente.isEmpty()) {
            sql.append("AND p.nome LIKE ? ");
        }
        if (medico != null && !medico.isEmpty()) {
            sql.append("AND m.nome LIKE ? ");
        }
        if (data != null && !data.isEmpty()) {
            sql.append("AND date(c.data_hora) = ? ");
        }
        sql.append("ORDER BY c.data_hora DESC");
        try (Connection conn = DatabaseConnection.getConnection(realPathBase);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (paciente != null && !paciente.isEmpty()) {
                stmt.setString(idx++, "%" + paciente + "%");
            }
            if (medico != null && !medico.isEmpty()) {
                stmt.setString(idx++, "%" + medico + "%");
            }
            if (data != null && !data.isEmpty()) {
                stmt.setString(idx++, data);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Consulta consulta = new Consulta();
                String dataHora = rs.getString("data_hora");
                if (dataHora != null && dataHora.length() >= 16) {
                    consulta.setData(dataHora.substring(0, 10));
                    consulta.setHorario(dataHora.substring(11, 16));
                } else {
                    consulta.setData(dataHora);
                    consulta.setHorario("");
                }
                consulta.setPacienteNome(rs.getString("paciente_nome"));
                consulta.setMedicoNome(rs.getString("medico_nome"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas filtradas.", e);
        }
        return consultas;
    }
}
