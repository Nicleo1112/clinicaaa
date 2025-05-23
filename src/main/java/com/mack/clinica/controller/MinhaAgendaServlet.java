package com.mack.clinica.controller;

import java.io.IOException;
import java.util.List;

import com.mack.clinica.model.Consulta;
import com.mack.clinica.model.ConsultaDAO;
import com.mack.clinica.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/minhaAgenda")
public class MinhaAgendaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            String realPath = getServletContext().getRealPath("/");
            List<Consulta> consultas = ConsultaDAO.listarConsultasPorPaciente(usuario.getId(), realPath);
            request.setAttribute("consultas", consultas);
        }
        request.getRequestDispatcher("/minhaAgenda.jsp").forward(request, response);
    }
}
