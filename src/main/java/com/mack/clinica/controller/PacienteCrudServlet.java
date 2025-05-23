package com.mack.clinica.controller;

import java.io.IOException;
import java.util.List;

import com.mack.clinica.model.Usuario;
import com.mack.clinica.model.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pacientes")
public class PacienteCrudServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String action = request.getParameter("action");
        String realPath = getServletContext().getRealPath("/");
        if (action != null && action.equals("excluir") && idParam != null) {
            int id = Integer.parseInt(idParam);
            UsuarioDAO.excluirPaciente(id, realPath);
            response.sendRedirect("pacientes");
            return;
        }
        if (idParam != null && action != null && action.equals("editar")) {
            int id = Integer.parseInt(idParam);
            Usuario paciente = UsuarioDAO.buscarPacientePorId(id, realPath);
            if (paciente != null) {
                request.setAttribute("id", paciente.getId());
                request.setAttribute("nome", paciente.getNome());
                request.setAttribute("email", paciente.getEmail());
                request.setAttribute("cpf", paciente.getCpf());
                request.setAttribute("celular", paciente.getCelular());
                request.getRequestDispatcher("/editarPaciente.jsp").forward(request, response);
                return;
            }
        }
        List<Usuario> pacientes = UsuarioDAO.listarPacientes(realPath);
        request.setAttribute("pacientes", pacientes);
        request.getRequestDispatcher("/pacientes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String celular = request.getParameter("celular");
        String realPath = getServletContext().getRealPath("/");
        try {
            if (idParam != null && !idParam.isEmpty() && !idParam.equals("null")) {
                int id = Integer.parseInt(idParam);
                UsuarioDAO.atualizarPaciente(id, nome, email, senha, cpf, celular, realPath);
            } else {
                UsuarioDAO.cadastrarPaciente(nome, email, senha, cpf, celular, realPath);
            }
            response.sendRedirect("pacientes");
        } catch (RuntimeException e) {
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("id", idParam);
            request.setAttribute("nome", nome);
            request.setAttribute("email", email);
            request.setAttribute("cpf", cpf);
            request.setAttribute("celular", celular);
            request.getRequestDispatcher("/editarPaciente.jsp").forward(request, response);
        }
    }
    // Métodos para POST (criar), PUT (editar), DELETE (excluir) podem ser adicionados depois
}
