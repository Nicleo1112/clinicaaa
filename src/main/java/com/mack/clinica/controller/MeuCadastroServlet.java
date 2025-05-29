package com.mack.clinica.controller;

import java.io.IOException;

import com.mack.clinica.model.Usuario; // Modelo de usuário

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/meuCadastro") // Mapeia o servlet para a URL /meuCadastro
public class MeuCadastroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Obtém a sessão
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Obtém usuário da sessão
        if (usuario != null) {
            String realPath = getServletContext().getRealPath("/"); // Caminho real do contexto
            Usuario dados = com.mack.clinica.model.UsuarioDAO.buscarPacientePorId(usuario.getId(), realPath); // Busca dados do paciente
            if (dados != null) {
                request.setAttribute("nome", dados.getNome()); // Seta nome
                request.setAttribute("email", dados.getEmail()); // Seta email
                request.setAttribute("cpf", dados.getCpf()); // Seta CPF
                request.setAttribute("celular", dados.getCelular()); // Seta celular
            }
        }
        request.getRequestDispatcher("/meuCadastro.jsp").forward(request, response); // Encaminha para JSP
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Obtém a sessão
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Obtém usuário da sessão
        if (usuario != null) {
            String nome = request.getParameter("nome"); // Obtém nome
            String email = request.getParameter("email"); // Obtém email
            String cpf = request.getParameter("cpf"); // Obtém CPF
            String celular = request.getParameter("celular"); // Obtém celular
            // Corrige caso venha com .0 (ex: 11999999999.0)
            if (celular != null && celular.endsWith(".0")) {
                celular = celular.substring(0, celular.length() - 2);
            }
            String realPath = getServletContext().getRealPath("/"); // Caminho real do contexto
            com.mack.clinica.model.UsuarioDAO.atualizarPaciente(
                usuario.getId(), nome, email, null, cpf, celular, realPath
            ); // Atualiza paciente
            usuario.setNome(nome); // Atualiza nome na sessão
            usuario.setEmail(email); // Atualiza email na sessão
            usuario.setCpf(cpf); // Atualiza CPF na sessão
            usuario.setCelular(celular); // Atualiza celular na sessão
        }
        response.sendRedirect("meuCadastro"); // Redireciona para a própria página
    }
}
