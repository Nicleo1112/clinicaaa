package com.mack.clinica.controller;  // Define o pacote ao qual essa classe pertence

import java.io.IOException;  // Importa a classe de exceção para operações de entrada e saída
import java.util.List;  // Importa a interface List para trabalhar com coleções de elementos

import com.mack.clinica.model.AgendarConsultaDAO;  // Importa a classe responsável pelas operações de banco relacionadas a consultas
import com.mack.clinica.model.Usuario;  // Importa a classe que representa os usuários (ex: médicos e pacientes)

import jakarta.servlet.ServletException;  // Importa a exceção para erros relacionados à execução de Servlets
import jakarta.servlet.annotation.WebServlet;  // Importa a anotação usada para mapear URLs ao servlet
import jakarta.servlet.http.HttpServlet;  // Classe base utilizada para a criação de Servlets que lidam com requisições HTTP
import jakarta.servlet.http.HttpServletRequest;  // Permite manipular dados da requisição feita pelo cliente
import jakarta.servlet.http.HttpServletResponse;  // Permite definir e enviar a resposta de volta ao cliente

@WebServlet("/agendarConsulta")  // Define o endpoint (rota) que ativa esse servlet na aplicação
public class AgendarConsultaServlet extends HttpServlet {  // Cria a classe do servlet que herda de HttpServlet para lidar com requisições HTTP
    private static final long serialVersionUID = 1L;  // Código de controle de versão para garantir compatibilidade durante a serialização

    @Override  // Indica que o método abaixo sobrescreve o doGet da superclasse
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera o caminho absoluto do diretório raiz da aplicação no servidor
        String realPathBase = request.getServletContext().getRealPath("/");

        // Cria uma instância do objeto DAO responsável por consultas, fornecendo o caminho da aplicação
        AgendarConsultaDAO dao = new AgendarConsultaDAO(realPathBase);

        // Executa uma consulta no banco para obter todos os médicos disponíveis para agendamento
        List<Usuario> medicos = dao.listarMedicos();

        // Exibe no terminal a quantidade de médicos encontrados (útil para testes e verificação)
        System.out.println("Médicos encontrados: " + (medicos != null ? medicos.size() : 0));

        // Armazena a lista de médicos na requisição para que seja acessada na página JSP
        request.setAttribute("medicos", medicos);

        // Encaminha o fluxo da aplicação para o JSP de agendamento, mantendo os dados da requisição
        request.getRequestDispatcher("/agendar_consulta.jsp").forward(request, response);
        // O forward mantém a mesma requisição HTTP e permite acesso a atributos como a lista de médicos
    }

    @Override  // Indica que o método doPost está sendo sobrescrito para lidar com requisições HTTP POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Recupera o parâmetro do formulário correspondente ao médico selecionado e converte para inteiro
            int profissionalId = Integer.parseInt(request.getParameter("profissionalId"));

            // Captura a data e o horário escolhidos para a consulta, enviados pelo formulário
            String dataHora = request.getParameter("dataHora");

            // Obtém o ID do paciente atualmente autenticado, que está armazenado na sessão
            Integer pacienteIdObj = (Integer) request.getSession().getAttribute("id");

            // Verifica se o ID do paciente está disponível (indicando que ele está logado)
            if (pacienteIdObj == null) {
                // Se o paciente não estiver logado, exibe mensagem no console e redireciona para a tela de login
                System.out.println("Paciente não autenticado. Redirecionando para login.");
                response.sendRedirect("index.jsp");
                return;  // Interrompe a execução do método após redirecionar o usuário
            }

            // Converte o ID do paciente de objeto Integer para primitivo int
            int pacienteId = pacienteIdObj;

            // Recupera novamente o caminho físico do projeto para ser utilizado pelo DAO
            String realPathBase = request.getServletContext().getRealPath("/");

            // Mostra os valores recebidos no console para verificação e depuração
            System.out.println("Paciente ID: " + pacienteId);
            System.out.println("Profissional ID: " + profissionalId);
            System.out.println("Data e Hora: " + dataHora);

            // Instancia o DAO de agendamento passando o caminho do projeto
            AgendarConsultaDAO dao = new AgendarConsultaDAO(realPathBase);

            // Realiza a tentativa de agendar a consulta no banco de dados com os dados fornecidos
            boolean sucesso = dao.agendarConsulta(pacienteId, profissionalId, dataHora);

            // Exibe no console se a operação de agendamento foi concluída com êxito
            System.out.println("Sucesso: " + sucesso);

            if (sucesso) {
                // Caso o agendamento ocorra corretamente, redireciona o usuário para uma página de confirmação
                response.sendRedirect("/mensagem_sucesso.jsp");
            } else {
                // Se houver falha no agendamento, redireciona para a página inicial com uma mensagem de erro
                response.sendRedirect("index.jsp?erro=agendar");
            }

        } catch (Exception e) {  // Captura qualquer erro que possa ocorrer durante o processo
            e.printStackTrace();  // Exibe detalhes do erro no console para facilitar o diagnóstico
            // Em caso de falha inesperada, redireciona para o painel do paciente com uma mensagem de erro
            response.sendRedirect("paciente_dashboard.jsp?msg=erro");
        }
    }
}