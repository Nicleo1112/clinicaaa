// Define o pacote onde este servlet está localizado
package com.mack.clinica.controller;

// Importa classe necessária para tratar exceções de entrada e saída
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Define que esse servlet responderá por requisições feitas à URL "/fichaClinica"
@WebServlet("/fichaClinica")
public class FichaClinicaServlet extends HttpServlet {

    // Método que será executado quando uma requisição GET for feita para "/fichaClinica"
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Encaminha a requisição e a resposta para a página fichaClinica.jsp
        request.getRequestDispatcher("/fichaClinica.jsp").forward(request, response);
    }

    // Método que será executado quando uma requisição POST for feita para "/fichaClinica"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera os parâmetros enviados no formulário da página JSP
        String paciente = request.getParameter("paciente"); // Nome ou ID do paciente
        String medico = request.getParameter("medico"); // Nome ou ID do médico
        String data = request.getParameter("data"); // Data da consulta
        String anamnese = request.getParameter("anamnese"); // Texto da anamnese
        String diagnostico = request.getParameter("diagnostico"); // Texto do diagnóstico
        String prescricao = request.getParameter("prescricao"); // Texto da prescrição

        // Comentário indicando que aqui poderia ser implementado o salvamento dos dados no banco de dados
        // Por enquanto, os dados apenas são retornados para a tela, como simulação

        // Atribui os dados como atributos da requisição, para que possam ser acessados na página JSP
        request.setAttribute("paciente", paciente);
        request.setAttribute("medico", medico);
        request.setAttribute("data", data);
        request.setAttribute("anamnese", anamnese);
        request.setAttribute("diagnostico", diagnostico);
        request.setAttribute("prescricao", prescricao);

        // Define uma mensagem de confirmação (simulada) a ser exibida na página
        request.setAttribute("msg", "Ficha clínica salva (simulação). Implemente o salvamento real no banco se desejar.");

        // Encaminha novamente para a mesma página JSP com os dados preenchidos e a mensagem
        request.getRequestDispatcher("/fichaClinica.jsp").forward(request, response);
    }
}

