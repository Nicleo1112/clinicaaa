package com.mack.clinica.controller;

// Importa classes necessárias para manipulação de IO e servlets
import java.io.IOException;

import jakarta.servlet.ServletException; // Exceção para operações de servlet
import jakarta.servlet.annotation.WebServlet; // Anotação para mapear servlet
import jakarta.servlet.http.HttpServlet; // Classe base para servlets HTTP
import jakarta.servlet.http.HttpServletRequest; // Representa a requisição HTTP
import jakarta.servlet.http.HttpServletResponse; // Representa a resposta HTTP

// Mapeia para "/admin_dashboard" sem expor .jsp
@WebServlet("/admin_dashboard") // Define a URL de acesso ao servlet
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Versão para serialização

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Encaminha para a página JSP do painel do administrador
        request.getRequestDispatcher("/admin_dashboard.jsp").forward(request, response);
    }
}

