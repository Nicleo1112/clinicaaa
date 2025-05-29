// Pacote do controlador
package com.mack.clinica.controller;

// Importações necessárias
import java.io.IOException;
import java.util.List;

import com.mack.clinica.model.Consulta; // Modelo de consulta
import com.mack.clinica.model.ConsultaDAO; // DAO de consulta
import com.mack.clinica.model.Usuario; // Modelo de usuário

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/minhaAgenda") // Mapeia o servlet para a URL /minhaAgenda
public class MinhaAgendaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Obtém a sessão
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Obtém usuário da sessão
        if (usuario != null) {
            String realPath = getServletContext().getRealPath("/"); // Caminho real do contexto
            List<Consulta> consultas = ConsultaDAO.listarConsultasPorPaciente(usuario.getId(), realPath); // Busca consultas do paciente
            request.setAttribute("consultas", consultas); // Seta consultas no request
        }
        request.getRequestDispatcher("/minhaAgenda.jsp").forward(request, response); // Encaminha para JSP
    }
}
