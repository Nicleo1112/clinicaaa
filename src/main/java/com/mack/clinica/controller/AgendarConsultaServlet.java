package com.mack.clinica.controller;  // Define o pacote onde a classe está localizada

import java.io.IOException;  // Importa exceções relacionadas a entrada e saída
import java.util.List;  // Importa a interface List para manipulação de listas

import com.mack.clinica.model.AgendarConsultaDAO;  // Importa a classe DAO para agendar consultas
import com.mack.clinica.model.Usuario;  // Importa a classe Usuario (provavelmente representa médicos e pacientes)

import jakarta.servlet.ServletException;  // Importa exceção para erros no servlet
import jakarta.servlet.annotation.WebServlet;  // Importa anotação para mapear o servlet
import jakarta.servlet.http.HttpServlet;  // Importa a classe base para criar servlets HTTP
import jakarta.servlet.http.HttpServletRequest;  // Importa classe para manipular requisições HTTP
import jakarta.servlet.http.HttpServletResponse;  // Importa classe para manipular respostas HTTP

@WebServlet("/agendarConsulta")  // Define a URL padrão para acessar esse servlet
public class AgendarConsultaServlet extends HttpServlet {  // Declara a classe que estende HttpServlet para tratar requisições web
    private static final long serialVersionUID = 1L;  // Identificador de versão para serialização da classe

    @Override  // Sobrescreve o método doGet para tratar requisições GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtém o caminho real do diretório base do projeto no servidor
        String realPathBase = request.getServletContext().getRealPath("/");

        // Instancia o DAO para consultas, passando o caminho real do projeto
        AgendarConsultaDAO dao = new AgendarConsultaDAO(realPathBase);

        // Busca a lista de médicos disponíveis para agendamento
        List<Usuario> medicos = dao.listarMedicos();

        // Imprime no console a quantidade de médicos encontrados (para debug)
        System.out.println("Médicos encontrados: " + (medicos != null ? medicos.size() : 0));

        // Atribui a lista de médicos como atributo na requisição para uso na JSP
        request.setAttribute("medicos", medicos);

        // Encaminha a requisição para a página JSP de agendamento de consulta
        request.getRequestDispatcher("/agendar_consulta.jsp").forward(request, response);
    }
    
    @Override  // Sobrescreve o método doPost para tratar as requisições POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtém o id do profissional escolhido no formulário (string para int)
            int profissionalId = Integer.parseInt(request.getParameter("profissionalId"));

            // Obtém a data e hora escolhidas no formulário
            String dataHora = request.getParameter("dataHora");

            // Pega o id do paciente armazenado na sessão (autenticado)
            Integer pacienteIdObj = (Integer) request.getSession().getAttribute("id");

            // Verifica se o paciente está autenticado (id não nulo)
            if (pacienteIdObj == null) {
                // Se não estiver autenticado, sera informado no console e redireciona para a página de login
                System.out.println("Paciente não autenticado. Redirecionando para login.");
                response.sendRedirect("index.jsp");
                return;  // Para execução do método após redirecionamento
            }
            int pacienteId = pacienteIdObj;  // Converte o objeto Integer para int

            // Obtém novamente o caminho real do projeto (necessário para o DAO)
            String realPathBase = request.getServletContext().getRealPath("/");

            // Imprime no console os dados obtidos para conferência (debug)
            System.out.println("Paciente ID: " + pacienteId);
            System.out.println("Profissional ID: " + profissionalId);
            System.out.println("Data e Hora: " + dataHora);

            // Instancia o DAO de agendamento
            AgendarConsultaDAO dao = new AgendarConsultaDAO(realPathBase);

            // Tenta agendar a consulta passando os dados necessários
            boolean sucesso = dao.agendarConsulta(pacienteId, profissionalId, dataHora);

            // Imprime se o agendamento foi bem-sucedido ou não
            System.out.println("Sucesso: " + sucesso);

            if (sucesso) {
                // Se sucesso, redireciona para página que mostra mensagem de sucesso
                response.sendRedirect("/mensagem_sucesso.jsp");
            } else {
                // Se falhou, redireciona para a página inicial com parâmetro indicando erro no agendamento
                response.sendRedirect("index.jsp?erro=agendar");
            }

        } catch (Exception e) {  // Captura qualquer exceção que ocorra durante o processo
            e.printStackTrace();  // Imprime o stacktrace da exceção para ajudar no debug
            // Redireciona para o painel do paciente com mensagem de erro
            response.sendRedirect("paciente_dashboard.jsp?msg=erro");
        }
    }

}
