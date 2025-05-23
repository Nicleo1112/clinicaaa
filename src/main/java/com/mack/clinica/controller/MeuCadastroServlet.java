package com.mack.clinica.controller;

import java.io.IOException;

import com.mack.clinica.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/meuCadastro")
public class MeuCadastroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            String realPath = getServletContext().getRealPath("/");
            Usuario dados = com.mack.clinica.model.UsuarioDAO.buscarPacientePorId(usuario.getId(), realPath);
            if (dados != null) {
                request.setAttribute("nome", dados.getNome());
                request.setAttribute("email", dados.getEmail());
                request.setAttribute("cpf", dados.getCpf());
                request.setAttribute("celular", dados.getCelular());
            }
        }
        request.getRequestDispatcher("/meuCadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String cpf = request.getParameter("cpf");
            String celular = request.getParameter("celular");
            // Corrige caso venha com .0 (ex: 11999999999.0)
            if (celular != null && celular.endsWith(".0")) {
                celular = celular.substring(0, celular.length() - 2);
            }
            String realPath = getServletContext().getRealPath("/");
            com.mack.clinica.model.UsuarioDAO.atualizarPaciente(
                usuario.getId(), nome, email, null, cpf, celular, realPath
            );
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setCpf(cpf);
            usuario.setCelular(celular);
            session.setAttribute("usuario", usuario);
            request.setAttribute("msg", "Dados atualizados com sucesso!");
        }
        doGet(request, response);
    }
}
