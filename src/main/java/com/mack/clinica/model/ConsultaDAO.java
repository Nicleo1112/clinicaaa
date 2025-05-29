// Define o pacote onde essa classe está localizada
package com.mack.clinica.model;

// Importações necessárias para conexão com o banco de dados e manipulação de dados SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mack.clinica.util.DatabaseConnection;

// Classe responsável por interagir com o banco de dados para operações relacionadas a consultas médicas
public class ConsultaDAO {

    // Método que retorna uma lista de consultas de um paciente específico, usando seu ID
    public static List<Consulta> listarConsultasPorPaciente(int pacienteId, String realPathBase) {
        // Cria uma lista vazia para armazenar as consultas
        List<Consulta> consultas = new ArrayList<>();

        // SQL para buscar data e nome do médico das consultas de um determinado paciente
        String sql = "SELECT c.data_hora, u.nome as medico_nome FROM consultas c " +
                     "JOIN usuarios u ON c.profissional_id = u.id " +
                     "WHERE c.paciente_id = ? ORDER BY c.data_hora DESC";

        // Bloco try-with-resources que garante o fechamento automático da conexão
        try (Connection conn = DatabaseConnection.getConnection(realPathBase);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o valor do parâmetro do paciente
            stmt.setInt(1, pacienteId);

            // Executa a query
            ResultSet rs = stmt.executeQuery();

            // Itera sobre os resultados
            while (rs.next()) {
                // Cria um novo objeto Consulta
                Consulta consulta = new Consulta();

                // Obtém a string da data e hora
                String dataHora = rs.getString("data_hora");

                // Se a string estiver no formato esperado, extrai data e hora separadamente
                if (dataHora != null && dataHora.length() >= 16) {
                    consulta.setData(dataHora.substring(0, 10)); // yyyy-MM-dd
                    consulta.setHorario(dataHora.substring(11, 16)); // HH:mm
                } else {
                    // Se não estiver no formato esperado, define a data completa e horário vazio
                    consulta.setData(dataHora);
                    consulta.setHorario("");
                }

                // Define o nome do médico na consulta
                consulta.setMedicoNome(rs.getString("medico_nome"));

                // Adiciona a consulta à lista
                consultas.add(consulta);
            }

        } catch (SQLException e) {
            // Se ocorrer erro, lança uma RuntimeException com a mensagem
            throw new RuntimeException("Erro ao listar consultas do paciente.", e);
        }

        // Retorna a lista de consultas
        return consultas;
    }

    // Método que lista consultas aplicando filtros por nome de paciente, médico e data
    public static List<Consulta> listarConsultasFiltrado(String paciente, String medico, String data, String realPathBase) {
        // Cria uma lista vazia para armazenar as consultas
        List<Consulta> consultas = new ArrayList<>();

        // Construtor de string para montar dinamicamente a consulta SQL
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.data_hora, p.nome as paciente_nome, m.nome as medico_nome FROM consultas c ");
        sql.append("JOIN usuarios p ON c.paciente_id = p.id ");
        sql.append("JOIN usuarios m ON c.profissional_id = m.id WHERE 1=1 ");

        // Adiciona cláusulas de filtro à query conforme os parâmetros recebidos
        if (paciente != null && !paciente.isEmpty()) {
            sql.append("AND p.nome LIKE ? ");
        }
        if (medico != null && !medico.isEmpty()) {
            sql.append("AND m.nome LIKE ? ");
        }
        if (data != null && !data.isEmpty()) {
            sql.append("AND date(c.data_hora) = ? ");
        }

        // Ordena os resultados por data/hora, da mais recente para a mais antiga
        sql.append("ORDER BY c.data_hora DESC");

        // Bloco try-with-resources que garante o fechamento automático da conexão
        try (Connection conn = DatabaseConnection.getConnection(realPathBase);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Índice para preencher os parâmetros na query preparada
            int idx = 1;

            // Preenche os parâmetros com os valores, se existirem
            if (paciente != null && !paciente.isEmpty()) {
                stmt.setString(idx++, "%" + paciente + "%"); // LIKE com wildcard
            }
            if (medico != null && !medico.isEmpty()) {
                stmt.setString(idx++, "%" + medico + "%"); // LIKE com wildcard
            }
            if (data != null && !data.isEmpty()) {
                stmt.setString(idx++, data); // Data exata
            }

            // Executa a query
            ResultSet rs = stmt.executeQuery();

            // Itera sobre os resultados retornados
            while (rs.next()) {
                // Cria um novo objeto Consulta
                Consulta consulta = new Consulta();

                // Obtém a string da data e hora
                String dataHora = rs.getString("data_hora");

                // Se a string estiver no formato esperado, extrai data e hora separadamente
                if (dataHora != null && dataHora.length() >= 16) {
                    consulta.setData(dataHora.substring(0, 10)); // yyyy-MM-dd
                    consulta.setHorario(dataHora.substring(11, 16)); // HH:mm
                } else {
                    consulta.setData(dataHora);
                    consulta.setHorario("");
                }

                // Define o nome do paciente e do médico
                consulta.setPacienteNome(rs.getString("paciente_nome"));
                consulta.setMedicoNome(rs.getString("medico_nome"));

                // Adiciona a consulta à lista
                consultas.add(consulta);
            }

        } catch (SQLException e) {
            // Se ocorrer erro, lança uma RuntimeException com a mensagem
            throw new RuntimeException("Erro ao listar consultas filtradas.", e);
        }

        // Retorna a lista de consultas filtradas
        return consultas;
    }
}
