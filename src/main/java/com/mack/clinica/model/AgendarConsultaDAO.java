// Define o pacote onde está localizada esta classe DAO (Data Access Object)
package com.mack.clinica.model;

// Importa classes necessárias para conectar ao banco de dados e manipular os resultados
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mack.clinica.util.DatabaseConnection;

// Classe responsável por realizar operações de agendamento de consultas no banco de dados
public class AgendarConsultaDAO {

    // Caminho real do projeto (geralmente usado para localizar o arquivo do banco SQLite)
    private String realPathBase;

    // Construtor da classe que recebe o caminho real da aplicação
    public AgendarConsultaDAO(String realPathBase) {
        this.realPathBase = realPathBase;
    }

    // Método para agendar uma nova consulta
    public boolean agendarConsulta(int pacienteId, int profissionalId, String dataHora) {
        // SQL que insere uma nova consulta na tabela 'consultas' com status 'agendada' e sem observações
        String sql = "INSERT INTO consultas (paciente_id, profissional_id, data_hora, status, observacoes) VALUES (?, ?, ?, 'agendada', '')";

        // Tenta abrir uma conexão com o banco e executar o comando SQL
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            // Prepara o comando SQL
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Define os valores dos parâmetros
            stmt.setInt(1, pacienteId);
            stmt.setInt(2, profissionalId);
            stmt.setString(3, dataHora);
            // Executa a inserção e armazena o número de linhas afetadas
            int linhasAfetadas = stmt.executeUpdate();
            // Imprime no console o número de linhas afetadas
            System.out.println("Linhas afetadas: " + linhasAfetadas);
            // Retorna true se pelo menos uma linha foi afetada (consulta agendada com sucesso)
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            // Caso ocorra alguma exceção, imprime uma mensagem e o stack trace no console
            System.out.println("entrou aqui");
            e.printStackTrace();
            // Retorna false indicando falha no agendamento
            return false;
        }
    }

    // Método para buscar e listar todos os médicos cadastrados no sistema
    public List<Usuario> listarMedicos() {
        // Lista que armazenará os médicos encontrados
        List<Usuario> medicos = new ArrayList<>();
        // Comando SQL que seleciona id e nome dos usuários cujo tipo é 'medico'
        String sql = "SELECT id, nome FROM usuarios WHERE tipo = 'medico'";
    
        // Tenta abrir uma conexão, preparar o comando e executar a consulta
        try (Connection conn = DatabaseConnection.getConnection(realPathBase);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            // Enquanto houver resultados, cria objetos Usuario e adiciona na lista
            while (rs.next()) {
                Usuario u = new Usuario(); // Cria um novo objeto Usuario
                u.setId(rs.getInt("id")); // Define o id do médico
                u.setNome(rs.getString("nome")); // Define o nome do médico
                medicos.add(u); // Adiciona o objeto na lista
            }
        } catch (SQLException e) {
            // Em caso de erro, imprime a mensagem no console
            System.err.println("Erro ao buscar médicos: " + e.getMessage());
        }
    
        // Retorna a lista de médicos
        return medicos;
    }
}
