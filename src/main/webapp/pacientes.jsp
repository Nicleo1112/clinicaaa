<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mack.clinica.model.Usuario" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Pacientes</title>
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
        <h1>Pacientes</h1>
        <a href="/cadastrarPaciente" class="button">Novo Paciente</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Celular</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Usuario> pacientes = (List<Usuario>) request.getAttribute("pacientes");
                    if (pacientes != null) {
                        for (Usuario paciente : pacientes) {
                %>
                <tr>
                    <td><%= paciente.getId() %></td>
                    <td><%= paciente.getNome() %></td>
                    <td><%= paciente.getEmail() %></td>
                    <td><%= paciente.getCelular() != null ? paciente.getCelular().toString().replaceAll("\\.0$", "") : "" %></td>
                    <td>
                        <a href="pacientes?action=editar&id=<%= paciente.getId() %>" class="button">Editar</a>
                        <a href="pacientes?action=excluir&id=<%= paciente.getId() %>" class="button" onclick="return confirm('Tem certeza que deseja excluir?');">Excluir</a>
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
