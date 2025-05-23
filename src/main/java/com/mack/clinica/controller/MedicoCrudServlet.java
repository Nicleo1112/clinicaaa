package com.mack.clinica.controller;

import com.mack.clinica.model.Usuario;
import com.mack.clinica.model.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/medicos")
public class MedicoCrudServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String action = request.getParameter("action");
        String realPath = getServletContext().getRealPath("/");
        if (action != null && action.equals("excluir") && idParam != null) {
            int id = Integer.parseInt(idParam);
            UsuarioDAO.excluirMedico(id, realPath);
            response.sendRedirect("medicos");
            return;
        }
        if (idParam != null && action != null && action.equals("editar")) {
            int id = Integer.parseInt(idParam);
            Usuario medico = UsuarioDAO.buscarMedicoPorId(id, realPath);
            if (medico != null) {
                request.setAttribute("id", medico.getId());
                request.setAttribute("nome", medico.getNome());
                request.setAttribute("email", medico.getEmail());
                request.setAttribute("crm", medico.getCpf());
                request.getRequestDispatcher("/editarMedico.jsp").forward(request, response);
                return;
            }
        }
        List<Usuario> medicos = UsuarioDAO.listarMedicos(realPath);
        request.setAttribute("medicos", medicos);
        request.getRequestDispatcher("/medicos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String crm = request.getParameter("crm");
        String realPath = getServletContext().getRealPath("/");
        if (idParam != null && !idParam.isEmpty() && !idParam.equals("null")) {
            int id = Integer.parseInt(idParam);
            UsuarioDAO.atualizarMedico(id, nome, email, senha, crm, realPath);
        } else {
            UsuarioDAO.cadastrarMedico(nome, email, senha, crm, realPath);
        }
        response.sendRedirect("medicos");
    }
}
