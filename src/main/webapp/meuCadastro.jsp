<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.mack.clinica.model.Usuario" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Meu Cadastro</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="navbar">
        <div class="nav-links">
            <a href="paciente_dashboard">Home</a>
            <a href="agendarConsulta">Agendamento de Consultas</a>
            <a href="minhaAgenda">Minha Agenda</a>
            <a href="meuCadastro">Meu Cadastro</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
        </div>
    </div>
    <div class="content" style="user-select: none;">
        <h1 style="cursor: default;">Meus Dados</h1>
        <div class="form-container" style="user-select: auto;">
            <% if (request.getAttribute("msg") != null) { %>
                <div class="success-message" style="cursor: default; user-select: none;"><%= request.getAttribute("msg") %></div>
            <% } %>
            <form method="post" action="meuCadastro">
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
    </div>
</body>
</html>
