package com.mack.clinica.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mack.clinica.util.DatabaseConnection;

public class UsuarioDAO {

    /**
     * Consulta o usuário pelo email e senha no banco.
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return Objeto Usuario encontrado ou null se não encontrado.
     */
    public static Usuario buscarUsuario(String email, String senha, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "SELECT id, nome, tipo FROM usuarios WHERE email = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Se encontrou o usuário, cria um objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTipo(rs.getString("tipo"));
                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário no banco de dados.", e);
        }
        return null;
    }

    /**
     * Lista todos os pacientes do banco de dados.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return Lista de pacientes.
     */
    public static List<Usuario> listarPacientes(String realPathBase) {
        List<Usuario> pacientes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "SELECT id, nome, email, cpf, celular FROM usuarios WHERE tipo = 'paciente'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario paciente = new Usuario();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome") != null ? rs.getString("nome") : "");
                paciente.setEmail(rs.getString("email") != null ? rs.getString("email") : "");
                paciente.setCpf(rs.getString("cpf") != null ? rs.getString("cpf") : "");
                paciente.setCelular(rs.getString("celular") != null ? rs.getString("celular") : "");
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pacientes.", e);
        }
        return pacientes;
    }

    /**
     * Lista todos os médicos do banco de dados.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return Lista de médicos.
     */
    public static List<Usuario> listarMedicos(String realPathBase) {
        List<Usuario> medicos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "SELECT id, nome, email, cpf FROM usuarios WHERE tipo = 'medico'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario medico = new Usuario();
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setEmail(rs.getString("email"));
                medico.setCpf(rs.getString("cpf"));
                medicos.add(medico);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar médicos.", e);
        }
        return medicos;
    }

    /**
     * Verifica se o email já existe no banco de dados.
     * @param email Email a ser verificado.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return true se o email já existe, false caso contrário.
     */
    public static boolean emailExiste(String email, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "SELECT 1 FROM usuarios WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar email.", e);
        }
    }

    /**
     * Cadastra um novo paciente no banco de dados.
     */
    public static void cadastrarPaciente(String nome, String email, String senha, String cpf, String celular, String realPathBase) {
        if (emailExiste(email, realPathBase)) {
            throw new RuntimeException("Já existe um paciente com este e-mail cadastrado.");
        }
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "INSERT INTO usuarios (nome, email, senha, cpf, celular, tipo) VALUES (?, ?, ?, ?, ?, 'paciente')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, cpf);
            stmt.setString(5, celular);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar paciente.", e);
        }
    }

    /**
     * Cadastra um novo médico no banco de dados.
     */
    public static void cadastrarMedico(String nome, String email, String senha, String crm, String realPathBase) {
        if (emailExiste(email, realPathBase)) {
            throw new RuntimeException("Já existe um médico com este e-mail cadastrado.");
        }
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "INSERT INTO usuarios (nome, email, senha, cpf, tipo) VALUES (?, ?, ?, ?, 'medico')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, crm);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar médico.", e);
        }
    }

    public static Usuario buscarPacientePorId(int id, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "SELECT id, nome, email, cpf, celular FROM usuarios WHERE id = ? AND tipo = 'paciente'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario paciente = new Usuario();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome") != null ? rs.getString("nome") : "");
                paciente.setEmail(rs.getString("email") != null ? rs.getString("email") : "");
                paciente.setCpf(rs.getString("cpf") != null ? rs.getString("cpf") : "");
                paciente.setCelular(rs.getString("celular") != null ? rs.getString("celular") : "");
                return paciente;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por ID. SQL: " + e.getMessage(), e);
        }
        return null;
    }

    public static Usuario buscarMedicoPorId(int id, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "SELECT id, nome, email, cpf FROM usuarios WHERE id = ? AND tipo = 'medico'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario medico = new Usuario();
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setEmail(rs.getString("email"));
                medico.setCpf(rs.getString("cpf"));
                return medico;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar médico por ID.", e);
        }
        return null;
    }

    public static void atualizarPaciente(int id, String nome, String email, String senha, String cpf, String celular, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = (senha != null && !senha.isEmpty()) ?
                "UPDATE usuarios SET nome = ?, email = ?, senha = ?, cpf = ?, celular = ? WHERE id = ? AND tipo = 'paciente'" :
                "UPDATE usuarios SET nome = ?, email = ?, cpf = ?, celular = ? WHERE id = ? AND tipo = 'paciente'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            if (senha != null && !senha.isEmpty()) {
                stmt.setString(3, senha);
                stmt.setString(4, cpf);
                stmt.setString(5, celular);
                stmt.setInt(6, id);
            } else {
                stmt.setString(3, cpf);
                stmt.setString(4, celular);
                stmt.setInt(5, id);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente.", e);
        }
    }

    public static void atualizarMedico(int id, String nome, String email, String senha, String crm, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = (senha != null && !senha.isEmpty()) ?
                "UPDATE usuarios SET nome = ?, email = ?, senha = ?, cpf = ? WHERE id = ? AND tipo = 'medico'" :
                "UPDATE usuarios SET nome = ?, email = ?, cpf = ? WHERE id = ? AND tipo = 'medico'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            if (senha != null && !senha.isEmpty()) {
                stmt.setString(3, senha);
                stmt.setString(4, crm);
                stmt.setInt(5, id);
            } else {
                stmt.setString(3, crm);
                stmt.setInt(4, id);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar médico.", e);
        }
    }

    public static void excluirPaciente(int id, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "DELETE FROM usuarios WHERE id = ? AND tipo = 'paciente'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir paciente.", e);
        }
    }

    public static void excluirMedico(int id, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "DELETE FROM usuarios WHERE id = ? AND tipo = 'medico'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir médico.", e);
        }
    }
}
