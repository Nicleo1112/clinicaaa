package com.mack.clinica.controller;

// Importa classes necessárias para manipulação de IO e servlets
import java.io.IOException;

import jakarta.servlet.ServletException; // Exceção para operações de servlet
import jakarta.servlet.annotation.WebServlet; // Anotação para mapear servlet
import jakarta.servlet.http.HttpServlet; // Classe base para servlets HTTP
import jakarta.servlet.http.HttpServletRequest; // Representa a requisição HTTP
import jakarta.servlet.http.HttpServletResponse; // Representa a resposta HTTP
import jakarta.servlet.http.HttpSession; // Gerencia sessão do usuário

// Mapeia para "/logout" de forma limpa
@WebServlet("/logout") // Define a URL de acesso ao servlet
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Versão para serialização

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalida a sessão do usuário (logout)
        HttpSession session = request.getSession(false); // Recupera sessão existente, se houver
        if (session != null) {
            session.invalidate(); // Encerra a sessão
        }
        // Redireciona para página inicial (login)
        response.sendRedirect(request.getContextPath() + "/index.jsp"); // Redireciona para login
    }
}
