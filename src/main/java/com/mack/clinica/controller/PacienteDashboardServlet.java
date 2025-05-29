package com.mack.clinica.controller;

import java.io.IOException;

import jakarta.servlet.ServletException; // Exceção para operações de servlet
import jakarta.servlet.annotation.WebServlet; // Anotação para mapear servlet
import jakarta.servlet.http.HttpServlet; // Classe base para servlets HTTP
import jakarta.servlet.http.HttpServletRequest; // Representa a requisição HTTP
import jakarta.servlet.http.HttpServletResponse; // Representa a resposta HTTP

@WebServlet("/paciente_dashboard") // Define a URL de acesso ao servlet
public class PacienteDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Versão para serialização

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Encaminha para a página JSP do painel do paciente
        request.getRequestDispatcher("/paciente_dashboard.jsp").forward(request, response);
    }
}

