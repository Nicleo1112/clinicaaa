// Pacote do controlador
package com.mack.clinica.controller;

// Importações das classes de modelo e servlet necessárias
import java.io.IOException; // Classe de usuário (médico)
import java.util.List; // Classe de acesso a dados do usuário

import com.mack.clinica.model.Usuario; // Exceção para operações de servlet
import com.mack.clinica.model.UsuarioDAO; // Anotação para mapear servlet

import jakarta.servlet.ServletException; // Classe base para servlets HTTP
import jakarta.servlet.annotation.WebServlet; // Representa a requisição HTTP
import jakarta.servlet.http.HttpServlet; // Representa a resposta HTTP
import jakarta.servlet.http.HttpServletRequest; // Exceção de IO
import jakarta.servlet.http.HttpServletResponse; // Lista de objetos

@WebServlet("/medicos") // Mapeia o servlet para a URL /medicos
public class MedicoCrudServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id"); // Obtém o parâmetro id da requisição
        String action = request.getParameter("action"); // Obtém o parâmetro action da requisição
        String realPath = getServletContext().getRealPath("/"); // Caminho real do contexto
        if (action != null && action.equals("excluir") && idParam != null) { // Se ação for excluir e id informado
            int id = Integer.parseInt(idParam); // Converte id para inteiro
            UsuarioDAO.excluirMedico(id, realPath); // Exclui médico pelo id
            response.sendRedirect("medicos"); // Redireciona para a lista de médicos
            return; // Encerra execução
        }
        if (idParam != null && action != null && action.equals("editar")) { // Se ação for editar e id informado
            int id = Integer.parseInt(idParam); // Converte id para inteiro
            Usuario medico = UsuarioDAO.buscarMedicoPorId(id, realPath); // Busca médico pelo id
            if (medico != null) { // Se médico encontrado
                request.setAttribute("id", medico.getId()); // Seta id como atributo
                request.setAttribute("nome", medico.getNome()); // Seta nome como atributo
                request.setAttribute("email", medico.getEmail()); // Seta email como atributo
                request.setAttribute("crm", medico.getCpf()); // Seta CRM como atributo (aqui está usando getCpf para CRM)
                request.getRequestDispatcher("/editarMedico.jsp").forward(request, response); // Encaminha para tela de edição
                return; // Encerra execução
            }
        }
        List<Usuario> medicos = UsuarioDAO.listarMedicos(realPath); // Lista todos os médicos
        request.setAttribute("medicos", medicos); // Seta lista de médicos como atributo
        request.getRequestDispatcher("/medicos.jsp").forward(request, response); // Encaminha para tela de listagem
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id"); // Obtém o parâmetro id do formulário
        String nome = request.getParameter("nome"); // Obtém o nome do formulário
        String email = request.getParameter("email"); // Obtém o email do formulário
        String senha = request.getParameter("senha"); // Obtém a senha do formulário
        String crm = request.getParameter("crm"); // Obtém o CRM do formulário
        String realPath = getServletContext().getRealPath("/"); // Caminho real do contexto
        if (idParam != null && !idParam.isEmpty() && !idParam.equals("null")) { // Se id informado, é edição
            int id = Integer.parseInt(idParam); // Converte id para inteiro
            UsuarioDAO.atualizarMedico(id, nome, email, senha, crm, realPath); // Atualiza médico
        } else {
            UsuarioDAO.cadastrarMedico(nome, email, senha, crm, realPath); // Cadastra novo médico
        }
        response.sendRedirect("medicos"); // Redireciona para a lista de médicos
    }
}
