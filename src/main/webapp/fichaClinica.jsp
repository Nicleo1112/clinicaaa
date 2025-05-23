<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Ficha Clínica</title>
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
        <h1>Ficha Clínica (Prontuário)</h1>
        <% if (request.getAttribute("msg") != null) { %>
            <div class="success-message"><%= request.getAttribute("msg") %></div>
        <% } %>
        <form class="form-container">
            <label for="paciente">Paciente:</label>
            <input type="text" id="paciente" name="paciente" value="<%= request.getAttribute("paciente") != null ? request.getAttribute("paciente") : "" %>" disabled>
            <label for="medico">Médico:</label>
            <input type="text" id="medico" name="medico" value="<%= request.getAttribute("medico") != null ? request.getAttribute("medico") : "" %>" disabled>
            <label for="data">Data da Consulta:</label>
            <input type="date" id="data" name="data" value="<%= request.getAttribute("data") != null ? request.getAttribute("data") : "" %>" disabled>
            <label for="diagnostico">Diagnóstico:</label>
            <textarea id="diagnostico" name="diagnostico" rows="4" disabled><%= request.getAttribute("diagnostico") != null ? request.getAttribute("diagnostico") : "" %></textarea>
            <label for="prescricao">Prescrição:</label>
            <textarea id="prescricao" name="prescricao" rows="4" disabled><%= request.getAttribute("prescricao") != null ? request.getAttribute("prescricao") : "" %></textarea>
            <button type="button" class="button" disabled>Salvar</button>
        </form>
        <p style="color: #888;">Funcionalidade em desenvolvimento.</p>
    </div>
</body>
</html>
