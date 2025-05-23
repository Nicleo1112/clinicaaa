<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mack.clinica.model.Consulta" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Consultar Agenda</title>
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
        <h1>Consultas Agendadas</h1>
        <form method="get" action="consultarAgenda" class="form-container">
            <label for="paciente">Paciente:</label>
            <input type="text" id="paciente" name="paciente" value="<%= request.getAttribute("filtroPaciente") != null ? request.getAttribute("filtroPaciente") : "" %>">
            <label for="medico">Médico:</label>
            <input type="text" id="medico" name="medico" value="<%= request.getAttribute("filtroMedico") != null ? request.getAttribute("filtroMedico") : "" %>">
            <label for="data">Data:</label>
            <input type="date" id="data" name="data" value="<%= request.getAttribute("filtroData") != null ? request.getAttribute("filtroData") : "" %>">
            <button type="submit" class="button">Filtrar</button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>Paciente</th>
                    <th>Médico</th>
                    <th>Data</th>
                    <th>Horário</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Consulta> consultas = (List<Consulta>) request.getAttribute("consultas");
                    if (consultas != null && !consultas.isEmpty()) {
                        for (Consulta consulta : consultas) {
                %>
                <tr>
                    <td><%= consulta.getPacienteNome() %></td>
                    <td><%= consulta.getMedicoNome() %></td>
                    <td><%= consulta.getData() %></td>
                    <td><%= consulta.getHorario() %></td>
                </tr>
                <%      }
                    } else { %>
                <tr><td colspan="4">Nenhuma consulta encontrada.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
