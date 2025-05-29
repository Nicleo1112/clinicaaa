package com.mack.clinica.controller;

import java.io.IOException;

import com.mack.clinica.model.Usuario; // Classe de modelo de usuário
import com.mack.clinica.model.UsuarioDAO; // Classe de acesso a dados do usuário

import jakarta.servlet.ServletException; // Exceção para operações de servlet
import jakarta.servlet.annotation.WebServlet; // Anotação para mapear servlet
import jakarta.servlet.http.HttpServlet; // Classe base para servlets HTTP
import jakarta.servlet.http.HttpServletRequest; // Representa a requisição HTTP
import jakarta.servlet.http.HttpServletResponse; // Representa a resposta HTTP
import jakarta.servlet.http.HttpSession; // Gerencia sessão do usuário

/**
 * Servlet responsável por processar o login do usuário.
 */
@WebServlet("/loginAction") // Mapeia o servlet para a URL /loginAction
public class LoginActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Versão para serialização

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email"); // Obtém o e-mail do formulário
        String senha = request.getParameter("senha"); // Obtém a senha do formulário

        String realPathBase = request.getServletContext().getRealPath("/"); // Caminho real do contexto

        Usuario usuario = UsuarioDAO.buscarUsuario(email, senha, realPathBase); // Busca usuário no banco

        if (usuario != null) { // Se usuário encontrado
            HttpSession session = request.getSession(); // Cria/recupera sessão
            session.setAttribute("id", usuario.getId()); // Salva id na sessão
            session.setAttribute("nome", usuario.getNome()); // Salva nome na sessão
            session.setAttribute("tipo", usuario.getTipo()); // Salva tipo na sessão
            session.setAttribute("usuario", usuario); // Salva objeto usuário na sessão

            if ("admin".equalsIgnoreCase(usuario.getTipo())) { // Se for admin
                response.sendRedirect("admin_dashboard"); // Redireciona para dashboard admin
            } else if ("paciente".equalsIgnoreCase(usuario.getTipo())) { // Se for paciente
                response.sendRedirect("paciente_dashboard"); // Redireciona para dashboard paciente
            } else {
                response.sendRedirect("index.jsp?erro=tipo"); // Redireciona para erro de tipo
            }
        } else {
            response.sendRedirect("index.jsp?erro=login"); // Redireciona para erro de login
        }
    }
}
