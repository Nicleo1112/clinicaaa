<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Paciente</title>
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
        <h1>Cadastrar Paciente</h1>
        <% if (request.getAttribute("erro") != null) { %>
            <div class="error-message"><%= request.getAttribute("erro") %></div>
        <% } %>
        <form method="post" action="pacientes" class="form-container">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha" required>
            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name="cpf" required>
            <label for="celular">Celular:</label>
            <input type="text" id="celular" name="celular">
            <button type="submit" class="button">Cadastrar</button>
        </form>
    </div>
</body>
</html>
