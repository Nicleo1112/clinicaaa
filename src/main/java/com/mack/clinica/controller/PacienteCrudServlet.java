// Define o pacote onde esta classe (Servlet) está localizada
package com.mack.clinica.controller;

// Importa classes para manipulação de exceções de entrada/saída
import java.io.IOException;
import java.util.List;

import com.mack.clinica.model.Usuario;
import com.mack.clinica.model.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Define a URL com a qual este servlet será mapeado (quando acessar /pacientes, este servlet será executado)
@WebServlet("/pacientes")
public class PacienteCrudServlet extends HttpServlet {

    // Método que será executado quando a requisição for do tipo GET (ex: acessos por link ou navegador)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera o parâmetro "id" da URL (se presente)
        String idParam = request.getParameter("id");
        // Recupera o parâmetro "action" da URL (ex: editar, excluir)
        String action = request.getParameter("action");
        // Obtém o caminho real da aplicação no servidor (usado para acessar arquivos do sistema, se necessário)
        String realPath = getServletContext().getRealPath("/");

        // Verifica se a ação é "excluir" e se o id foi informado
        if (action != null && action.equals("excluir") && idParam != null) {
            // Converte o id recebido como String para inteiro
            int id = Integer.parseInt(idParam);
            // Chama o método do DAO para excluir o paciente com esse ID
            UsuarioDAO.excluirPaciente(id, realPath);
            // Redireciona de volta para a listagem de pacientes
            response.sendRedirect("pacientes");
            return; // Encerra o método para não executar o restante
        }

        // Verifica se a ação é "editar" e se o id foi informado
        if (idParam != null && action != null && action.equals("editar")) {
            // Converte o id recebido como String para inteiro
            int id = Integer.parseInt(idParam);
            // Busca o paciente no banco de dados usando o DAO
            Usuario paciente = UsuarioDAO.buscarPacientePorId(id, realPath);
            // Se o paciente foi encontrado
            if (paciente != null) {
                // Define os atributos do paciente para que possam ser acessados na página JSP
                request.setAttribute("id", paciente.getId());
                request.setAttribute("nome", paciente.getNome());
                request.setAttribute("email", paciente.getEmail());
                request.setAttribute("cpf", paciente.getCpf());
                request.setAttribute("celular", paciente.getCelular());
                // Encaminha a requisição para a página de edição do paciente
                request.getRequestDispatcher("/editarPaciente.jsp").forward(request, response);
                return; // Encerra o método para não executar o restante
            }
        }

        // Caso nenhuma ação seja especificada, lista todos os pacientes
        List<Usuario> pacientes = UsuarioDAO.listarPacientes(realPath); // Chama o DAO para listar todos os pacientes
        request.setAttribute("pacientes", pacientes); // Define o atributo "pacientes" para a JSP poder usar
        request.getRequestDispatcher("/pacientes.jsp").forward(request, response); // Encaminha para a JSP que exibe a lista
    }

    // Método que será executado quando a requisição for do tipo POST (ex: envio de formulário)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera os parâmetros enviados no formulário
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String celular = request.getParameter("celular");

        // Obtém o caminho real da aplicação no servidor
        String realPath = getServletContext().getRealPath("/");

        try {
            // Verifica se o campo "id" foi enviado e se não está vazio ou "null" (String)
            if (idParam != null && !idParam.isEmpty() && !idParam.equals("null")) {
                // Converte o id para inteiro
                int id = Integer.parseInt(idParam);
                // Atualiza os dados do paciente usando o DAO
                UsuarioDAO.atualizarPaciente(id, nome, email, senha, cpf, celular, realPath);
            } else {
                // Caso o id não tenha sido enviado, é um novo cadastro
                UsuarioDAO.cadastrarPaciente(nome, email, senha, cpf, celular, realPath);
            }
            // Redireciona para a listagem de pacientes após o cadastro/atualização
            response.sendRedirect("pacientes");
        } catch (RuntimeException e) {
            // Em caso de erro (ex: dados inválidos), define os dados preenchidos de volta nos atributos
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("id", idParam);
            request.setAttribute("nome", nome);
            request.setAttribute("email", email);
            request.setAttribute("cpf", cpf);
            request.setAttribute("celular", celular);
            // Encaminha novamente para a página de edição para que o usuário corrija os dados
            request.getRequestDispatcher("/editarPaciente.jsp").forward(request, response);
        }
    }
}
