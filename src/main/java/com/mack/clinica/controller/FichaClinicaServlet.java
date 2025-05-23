package com.mack.clinica.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/fichaClinica")
public class FichaClinicaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/fichaClinica.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paciente = request.getParameter("paciente");
        String medico = request.getParameter("medico");
        String data = request.getParameter("data");
        String anamnese = request.getParameter("anamnese");
        String diagnostico = request.getParameter("diagnostico");
        String prescricao = request.getParameter("prescricao");

        // Aqui você pode salvar no banco de dados, se desejar
        // Por enquanto, só retorna para a tela com os dados preenchidos
        request.setAttribute("paciente", paciente);
        request.setAttribute("medico", medico);
        request.setAttribute("data", data);
        request.setAttribute("anamnese", anamnese);
        request.setAttribute("diagnostico", diagnostico);
        request.setAttribute("prescricao", prescricao);
        request.setAttribute("msg", "Ficha clínica salva (simulação). Implemente o salvamento real no banco se desejar.");
        request.getRequestDispatcher("/fichaClinica.jsp").forward(request, response);
    }
}
