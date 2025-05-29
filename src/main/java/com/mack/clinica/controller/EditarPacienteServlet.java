// Pacote do controlador
package com.mack.clinica.controller;

// Importações necessárias
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editarPaciente") // Mapeia o servlet para a URL /editarPaciente
public class EditarPacienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Encaminha para a página de edição de paciente
        request.getRequestDispatcher("/editarPaciente.jsp").forward(request, response);
    }
}
