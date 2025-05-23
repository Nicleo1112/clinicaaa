<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mack.clinica.model.Usuario" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Médicos</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="navbar">
        <div class="nav-links">
            <a href="admin_dashboard">Home</a>
            <a href="pacientes">Cadastro de Pacientes</a>
            <a href="medicos">Cadastro de Médicos</a>
            <a href="consultarAgenda">Consultar Agenda</a>
            <a href="fichaClinica">Ficha Clínica</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
        </div>
    </div>
    <div class="content">
        <h1>Médicos</h1>
        <a href="/cadastrarMedico" class="button">Novo Médico</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>CRM</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Usuario> medicos = (List<Usuario>) request.getAttribute("medicos");
                    if (medicos != null) {
                        for (Usuario medico : medicos) {
                %>
                <tr>
                    <td><%= medico.getId() %></td>
                    <td><%= medico.getNome() %></td>
                    <td><%= medico.getEmail() %></td>
                    <td><%= medico.getCpf() %></td>
                    <td>
                        <a href="medicos?action=editar&id=<%= medico.getId() %>" class="button">Editar</a>
                        <a href="medicos?action=excluir&id=<%= medico.getId() %>" class="button" onclick="return confirm('Tem certeza que deseja excluir?');">Excluir</a>
                    </td>
                </tr>
                <%      }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
