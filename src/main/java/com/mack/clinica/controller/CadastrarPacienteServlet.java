// Pacote do controlador
package com.mack.clinica.controller;

// Importações necessárias
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastrarPaciente") // Mapeia o servlet para a URL /cadastrarPaciente
public class CadastrarPacienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Encaminha para a página de cadastro de paciente
        request.getRequestDispatcher("/cadastrarPaciente.jsp").forward(request, response);
    }
}
