<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.mack.clinica.model.Usuario" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Editar Paciente</title>
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
        <h1>Editar Paciente</h1>
        <% if (request.getAttribute("erro") != null) { %>
            <div class="error-message"><%= request.getAttribute("erro") %></div>
        <% } %>
        <form method="post" action="pacientes" class="form-container">
            <input type="hidden" name="id" value="<%= request.getAttribute("id") %>">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="<%= request.getAttribute("nome") != null ? request.getAttribute("nome") : "" %>" required>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required>
            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name="cpf" value="<%= request.getAttribute("cpf") != null ? request.getAttribute("cpf") : "" %>" required>
            <label for="celular">Celular:</label>
            <input type="text" id="celular" name="celular" value="<%= request.getAttribute("celular") != null ? request.getAttribute("celular").toString().replaceAll("\\.0$", "") : "" %>">
            <button type="submit" class="button">Salvar</button>
        </form>
    </div>
</body>
</html>
