<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mack.clinica.model.Consulta" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Minha Agenda</title>
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
    <div class="content">
        <h1>Minhas Consultas</h1>
        <table>
            <thead>
                <tr>
                    <th>Data</th>
                    <th>Horário</th>
                    <th>Médico</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Consulta> consultas = (List<Consulta>) request.getAttribute("consultas");
                    if (consultas != null && !consultas.isEmpty()) {
                        for (Consulta consulta : consultas) {
                %>
                <tr>
                    <td style="cursor:default;"><span><%= consulta.getData() %></span></td>
                    <td style="cursor:default;"><span><%= consulta.getHorario() %></span></td>
                    <td style="cursor:default;"><span><%= consulta.getMedicoNome() %></span></td>
                </tr>
                <%      }
                    } else { %>
                <tr><td colspan="3" style="cursor:default;"><span>Nenhuma consulta agendada.</span></td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
