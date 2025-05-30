// Pacote do controlador
package com.mack.clinica.controller;

// Importações necessárias
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastrarMedico") // Mapeia o servlet para a URL /cadastrarMedico
public class CadastrarMedicoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Encaminha para a página de cadastro de médico
        // Redireciona a requisição do cliente para o JSP responsável por exibir o formulário de cadastro de médico
        request.getRequestDispatcher("/cadastrarMedico.jsp").forward(request, response);
        // O método forward mantém a URL original e envia a requisição e resposta para a página JSP,
        // possibilitando que esta utilize dados do request, se necessário
    }
}