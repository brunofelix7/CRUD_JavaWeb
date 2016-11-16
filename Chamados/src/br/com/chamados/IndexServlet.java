package br.com.chamados;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Bruno Felix
 * Extender da classe pai, para ser considerado um Servlet
 */
@SuppressWarnings("serial")
public class IndexServlet extends HttpServlet{

	//	Obrigat�rio para trabalhar com Servlet
	//	Tudo que vai aparecer na p�gina
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		response.setContentType("text/html");
		
		//	Impende que eu acesse direto essa URL
		HttpSession session = request.getSession();
		if(session.getAttribute("login") == null){
			response.sendRedirect("/Chamados/Login");
		}
		
		//	Quero escrever algo na tela ao abrir essa p�gina
		//	Possibilitando que eu possa dar println
		try {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Sistema de Chamados</title>");
			out.println("<meta charset=utf-8'/>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Sistemas de Chamados</h1>");
			out.println("<hr/>");
			out.println("<a href='http://localhost:8080/Chamados/NovoChamado'>Novo Chamado</a>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamados</a>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/Logoff'>Sair</a>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//	Obrigat�rio para trabalhar com Servlet
	//	Manipular informa��es que vieram de um formul�rio
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		
	}
	
}
