package com.mack.clinica.model;

import java.sql.Connection; // Importa a classe para conexão com o banco de dados
import java.sql.PreparedStatement; // Importa a classe para preparar comandos SQL
import java.sql.ResultSet; // Importa a classe para manipular resultados de consultas SQL
import java.sql.SQLException; // Importa a classe para tratamento de exceções SQL
import java.util.ArrayList; // Importa a classe para criar listas dinâmicas
import java.util.List; // Importa a interface para listas

import com.mack.clinica.util.DatabaseConnection; // Importa utilitário para obter conexão com o banco

public class UsuarioDAO {

    /**
     * Consulta o usuário pelo email e senha no banco.
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return Objeto Usuario encontrado ou null se não encontrado.
     */
    public static Usuario buscarUsuario(String email, String senha, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém a conexão com banco
            String sql = "SELECT id, nome, tipo FROM usuarios WHERE email = ? AND senha = ?"; // Query para buscar usuário
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara a consulta SQL
            stmt.setString(1, email); // Seta o parâmetro 1 (email)
            stmt.setString(2, senha); // Seta o parâmetro 2 (senha)

            ResultSet rs = stmt.executeQuery(); // Executa a consulta e obtém o resultado

            if (rs.next()) { // Se encontrou usuário com email e senha informados - cria objeto e preenche com os dados da consulta
                Usuario usuario = new Usuario(); // Cria um novo objeto Usuario
                usuario.setId(rs.getInt("id")); // Seta o id, Atribui ao paciente o valor do ID retornado pelo banco de dados
                usuario.setNome(rs.getString("nome")); // Seta o nome, Atribui ao paciente o Nome retornado pelo banco de dados
                usuario.setTipo(rs.getString("tipo")); // Seta o tipo (paciente, médico, etc)
                return usuario; // Retorna o objeto usuário
            }

        } catch (SQLException e) { // Captura exceção SQL
            e.printStackTrace(); // Imprime stacktrace para debugging
            throw new RuntimeException("Erro ao buscar usuário no banco de dados.", e); // Relança exceção como Runtime
        }
        return null; // Retorna null Caso o usuário não exista ou email/senha estejam incorretos
    }

    /**
     * Lista todos os pacientes do banco de dados.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return Lista de pacientes.
     */
    public static List<Usuario> listarPacientes(String realPathBase) {
        List<Usuario> pacientes = new ArrayList<>(); // Cria lista para armazenar pacientes
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            String sql = "SELECT id, nome, email, cpf, celular FROM usuarios WHERE tipo = 'paciente'"; // Query para pacientes
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara a consulta
            ResultSet rs = stmt.executeQuery(); // Executa a consulta

            while (rs.next()) { // Itera sobre cada resultado
                Usuario paciente = new Usuario(); // Cria o objeto paciente
                // Define campos verificando se não são nulos para evitar NullPointerException
                paciente.setId(rs.getInt("id")); 
                paciente.setNome(rs.getString("nome") != null ? rs.getString("nome") : "");
                paciente.setEmail(rs.getString("email") != null ? rs.getString("email") : "");
                paciente.setCpf(rs.getString("cpf") != null ? rs.getString("cpf") : "");
                paciente.setCelular(rs.getString("celular") != null ? rs.getString("celular") : "");
                pacientes.add(paciente); // Adiciona paciente à lista
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pacientes.", e); // Lança exceção se erro ocorrer
        }
        return pacientes; // Retorna lista de pacientes
    }

    /**
     * Lista todos os médicos do banco de dados.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return Lista de médicos.
     */
    public static List<Usuario> listarMedicos(String realPathBase) {
        List<Usuario> medicos = new ArrayList<>(); // Cria lista para armazenar médicos
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            String sql = "SELECT id, nome, email, cpf FROM usuarios WHERE tipo = 'medico'"; // Query para médicos
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara a consulta
            ResultSet rs = stmt.executeQuery(); // Executa consulta
            while (rs.next()) { // Itera os resultados
                Usuario medico = new Usuario(); // Cria o novo objeto médico
                medico.setId(rs.getInt("id")); // Seta id , Atribui ao Medico o valor do id retornado pelo banco de dados
                medico.setNome(rs.getString("nome")); // Seta Nome, Atribui ao Medico o Nome retornado pelo banco de dados
                medico.setEmail(rs.getString("email")); // Seta email, Atribui ao Medico o email retornado pelo banco de dados 
                medico.setCpf(rs.getString("cpf")); // Seta cpf (usado como crm aqui), Atribui ao medico o Cpf retornado pelo banco de dados
                medicos.add(medico); // Adiciona à lista
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar médicos.", e); // Lança exceção em erro
        }
        return medicos; // Retorna a lista de médicos
    }

    /**
     * Verifica se o email já existe no banco de dados.
     * @param email Email a ser verificado.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return true se o email já existe, false caso contrário.
     */
    public static boolean emailExiste(String email, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            String sql = "SELECT 1 FROM usuarios WHERE email = ?"; // Query para verificar email
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara consulta
            stmt.setString(1, email); // Seta parâmetro email
            ResultSet rs = stmt.executeQuery(); // Executa consulta
            return rs.next(); // Retorna true se existir pelo menos um resultado
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar email.", e); // Lança exceção se erro
        }
    }

    /**
     * Cadastra um novo paciente no banco de dados.
     */
    public static void cadastrarPaciente(String nome, String email, String senha, String cpf, String celular, String realPathBase) {
        if (emailExiste(email, realPathBase)) { // Verifica se o email já existe
            throw new RuntimeException("Já existe um paciente com este e-mail cadastrado."); // Lança erro se já existe
        }
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém a conexão
            String sql = "INSERT INTO usuarios (nome, email, senha, cpf, celular, tipo) VALUES (?, ?, ?, ?, ?, 'paciente')"; // Query para inserir paciente
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara o comando
            stmt.setString(1, nome); // Seta nome
            stmt.setString(2, email); // Seta email
            stmt.setString(3, senha); // Seta senha
            stmt.setString(4, cpf); // Seta cpf
            stmt.setString(5, celular); // Seta celular
            stmt.executeUpdate(); // Executa a inserção
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar paciente.", e); // Lança exceção em erro
        }
    }

    /**
     * Cadastra um novo médico no banco de dados.
     */
    public static void cadastrarMedico(String nome, String email, String senha, String crm, String realPathBase) {
        if (emailExiste(email, realPathBase)) { // Verifica se email já existe
            throw new RuntimeException("Já existe um médico com este e-mail cadastrado."); // Lança erro se já existe
        }
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            String sql = "INSERT INTO usuarios (nome, email, senha, cpf, tipo) VALUES (?, ?, ?, ?, 'medico')"; // Query para inserir médico (crm salvo no campo cpf)
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara comando
            stmt.setString(1, nome); // Seta nome
            stmt.setString(2, email); // Seta email
            stmt.setString(3, senha); // Seta senha
            stmt.setString(4, crm); // Seta crm (no campo cpf)
            stmt.executeUpdate(); // Executa a inserção
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar médico.", e); // Lança exceção em erro
        }
    }

    /**
     * Busca paciente por ID.
     */
    public static Usuario buscarPacientePorId(int id, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            String sql = "SELECT id, nome, email, cpf, celular FROM usuarios WHERE id = ? AND tipo = 'paciente'"; // Query para paciente por id
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara comando
            stmt.setInt(1, id); // Seta id
            ResultSet rs = stmt.executeQuery(); // Executa consulta
            if (rs.next()) { // Caso encontre o paciente
                Usuario paciente = new Usuario(); // Cria o objeto
                // Seta os atributos com verificação para evitar null
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome") != null ? rs.getString("nome") : "");
                paciente.setEmail(rs.getString("email") != null ? rs.getString("email") : "");
                paciente.setCpf(rs.getString("cpf") != null ? rs.getString("cpf") : "");
                paciente.setCelular(rs.getString("celular") != null ? rs.getString("celular") : "");
                return paciente; // Retorna paciente
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por ID. SQL: " + e.getMessage(), e); // Exceção personalizada
        }
        return null; // Retorna null se não encontrou
    }

    /**
     * Busca médico por ID.
     */
    public static Usuario buscarMedicoPorId(int id, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            String sql = "SELECT id, nome, email, cpf FROM usuarios WHERE id = ? AND tipo = 'medico'"; // Query para médico por id
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara comando
            stmt.setInt(1, id); // Seta id
            ResultSet rs = stmt.executeQuery(); // Executa consulta
            if (rs.next()) { // Se encontrou médico
                Usuario medico = new Usuario(); // Cria objeto médico
                medico.setId(rs.getInt("id")); // Seta id
                medico.setNome(rs.getString("nome")); // Seta nome
                medico.setEmail(rs.getString("email")); // Seta email
                medico.setCpf(rs.getString("cpf")); // Seta crm no campo cpf
                return medico; // Retorna médico
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar médico por ID.", e); // Exceção personalizada
        }
        return null; // Retorna null se não encontrar nada 
    }

    /**
     * Atualiza paciente no banco de dados.
     */
    public static void atualizarPaciente(int id, String nome, String email, String senha, String cpf, String celular, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            // Define query dependendo se senha será atualizada ou não
            String sql = (senha != null && !senha.isEmpty()) ?
                "UPDATE usuarios SET nome = ?, email = ?, senha = ?, cpf = ?, celular = ? WHERE id = ? AND tipo = 'paciente'" :
                "UPDATE usuarios SET nome = ?, email = ?, cpf = ?, celular = ? WHERE id = ? AND tipo = 'paciente'";
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara comando
            stmt.setString(1, nome); // Seta nome
            stmt.setString(2, email); // Seta email

            if (senha != null && !senha.isEmpty()) { // Se senha está preenchida
                stmt.setString(3, senha); // Seta senha
                stmt.setString(4, cpf); // Seta cpf
                stmt.setString(5, celular); // Seta celular
                stmt.setInt(6, id); // Seta id
            } else { // Senha vazia ou null, não atualiza senha
                stmt.setString(3, cpf); // Seta cpf
                stmt.setString(4, celular); // Seta celular
                stmt.setInt(5, id); // Seta id
            }
            stmt.executeUpdate(); // Executa a atualização
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente.", e); // Exceção personalizada
        }
    }

    /**
     * Atualiza médico no banco de dados.
     */
    public static void atualizarMedico(int id, String nome, String email, String senha, String crm, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            // Query varia se senha será atualizada
            String sql = (senha != null && !senha.isEmpty()) ?
                "UPDATE usuarios SET nome = ?, email = ?, senha = ?, cpf = ? WHERE id = ? AND tipo = 'medico'" :
                "UPDATE usuarios SET nome = ?, email = ?, cpf = ? WHERE id = ? AND tipo = 'medico'";
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara comando
            stmt.setString(1, nome); // Seta nome
            stmt.setString(2, email); // Seta email

            if (senha != null && !senha.isEmpty()) { // Se senha preenchida
                stmt.setString(3, senha); // Seta senha
                stmt.setString(4, crm); // Seta crm no campo cpf
                stmt.setInt(5, id); // Seta id
            } else { // Senha não será atualizada
                stmt.setString(3, crm); // Seta crm
                stmt.setInt(4, id); // Seta id
            }
            stmt.executeUpdate(); // Executa a atualização
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar médico.", e); // Exceção personalizada
        }
    }

    /**
     * Exclui paciente do banco de dados pelo id.
     */
    public static void excluirPaciente(int id, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            String sql = "DELETE FROM usuarios WHERE id = ? AND tipo = 'paciente'"; // Query para deletar paciente
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara comando
            stmt.setInt(1, id); // Seta id
            stmt.executeUpdate(); // Executa a deleção
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir paciente.", e); // Exceção personalizada
        }
    }

    /**
     * Exclui médico do banco de dados pelo id.
     */
    public static void excluirMedico(int id, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) { // Obtém conexão
            String sql = "DELETE FROM usuarios WHERE id = ? AND tipo = 'medico'"; // Query para deletar médico
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara comando
            stmt.setInt(1, id); // Seta id
            stmt.executeUpdate(); // Executa a deleção
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir médico.", e); // Exceção personalizada
        }
    }

}
