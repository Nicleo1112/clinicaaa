package com.mack.clinica.controller;

// Importações necessárias
import java.io.IOException;
import java.util.List;

import com.mack.clinica.model.Consulta; // Modelo de consulta
import com.mack.clinica.model.ConsultaDAO; // DAO de consulta

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/consultarAgenda") // Mapeia o servlet para a URL /consultarAgenda
public class ConsultarAgendaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paciente = request.getParameter("paciente"); // Filtro paciente
        String medico = request.getParameter("medico"); // Filtro médico
        String data = request.getParameter("data"); // Filtro data
        String realPath = getServletContext().getRealPath("/"); // Caminho real do contexto
        List<Consulta> consultas = ConsultaDAO.listarConsultasFiltrado(paciente, medico, data, realPath); // Busca consultas filtradas
        request.setAttribute("consultas", consultas); // Seta consultas no request
        request.setAttribute("filtroPaciente", paciente); // Seta filtro paciente
        request.setAttribute("filtroMedico", medico); // Seta filtro médico
        request.setAttribute("filtroData", data); // Seta filtro data
        request.getRequestDispatcher("/consultarAgenda.jsp").forward(request, response); // Encaminha para JSP
    }
}
