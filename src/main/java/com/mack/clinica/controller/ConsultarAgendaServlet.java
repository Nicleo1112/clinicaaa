package com.mack.clinica.controller;

import java.io.IOException;
import java.util.List;

import com.mack.clinica.model.Consulta;
import com.mack.clinica.model.ConsultaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/consultarAgenda")
public class ConsultarAgendaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paciente = request.getParameter("paciente");
        String medico = request.getParameter("medico");
        String data = request.getParameter("data");
        String realPath = getServletContext().getRealPath("/");
        List<Consulta> consultas = ConsultaDAO.listarConsultasFiltrado(paciente, medico, data, realPath);
        request.setAttribute("consultas", consultas);
        request.setAttribute("filtroPaciente", paciente);
        request.setAttribute("filtroMedico", medico);
        request.setAttribute("filtroData", data);
        request.getRequestDispatcher("/consultarAgenda.jsp").forward(request, response);
    }
}
