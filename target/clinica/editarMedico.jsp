<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.mack.clinica.model.Usuario" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Editar Médico</title>
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
        <h1>Editar Médico</h1>
        <form method="post" action="medicos" class="form-container">
            <input type="hidden" name="id" value="<%= request.getAttribute("id") %>">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="<%= request.getAttribute("nome") %>" required>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= request.getAttribute("email") %>" required>
            <label for="crm">CRM:</label>
            <input type="text" id="crm" name="crm" value="<%= request.getAttribute("crm") %>" required>
            <button type="submit" class="button">Salvar</button>
        </form>
    </div>
</body>
</html>
