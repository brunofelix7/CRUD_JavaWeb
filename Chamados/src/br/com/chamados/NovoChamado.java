package br.com.chamados;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.Date;

/**
 * @author Bruno Felix
 * TODA BIBLIOTECA EXTERNA DEVE SER COLOCADA EM WEB-INF -> lib
 */
@SuppressWarnings("serial")
public class NovoChamado extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html");
		
		try {
			PrintWriter out = response.getWriter();
			
			//	Impende que eu acesse direto essa URL
			HttpSession session = request.getSession();
			if(session.getAttribute("login") == null){
				response.sendRedirect("/Chamados/Login");
			}
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Novo Chamado</title>");
			out.println("<meta charset=utf-8'/>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha as informações do chamado</h1>");
			out.println("<hr/>");
			out.println("<form method='POST'>");
			out.println("Titulo: <br/><input type='text' name='titulo'>");
			out.println("<br/>");
			out.println("Conteúdo: <br/><textarea name='conteudo' rows='10' cols='40'></textarea>");
			out.println("<br/>");
			out.println("<input type='submit' value='Abrir Chamado'/>");
			out.println("<br/>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamados</a>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/Logoff'>Sair</a>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;

		try{
			//	Habilita o println
			out = response.getWriter();
			
			//	Recuperando valores do formulário
			String titulo = request.getParameter("titulo");
			String conteudo = request.getParameter("conteudo");
			
			//	Validação do formulário
			//	O trim() RETIRA TODOS OS ESPAÇOS NA HORA DE COMPARAR
			if(titulo.trim().equals("") || conteudo.trim().equals("")){
				out.println("<i style='color: red;'>Preencha os campos</i>");
				return;
			}if(titulo.trim().length() <= 5 || conteudo.trim().length() <= 5){
				out.println("<i style='color: red;'>Os campos devem conter mais de 5 caracteres</i>");
				return;
			}else{
				//	Conexão via Driver JDBC
				Class.forName("com.mysql.jdbc.Driver");
				final String url = "jdbc:mysql://localhost:3306/rlsystem";
				final String user = "root";
				final String password = "Oni@2015";
				Connection conn = DriverManager.getConnection(url, user, password);
				
				//	1. Inserindo dados do formulario
				String sql = "INSERT INTO chamados(titulo, conteudo, data_) VALUES(?, ?, ?)";
				//	Usado quando eu vou utilizar parêmetros, WHERE por exemplo
				PreparedStatement statement = conn.prepareStatement(sql);
				
				Date dataSQL = new Date(new java.util.Date().getTime());
				
				//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//	sdf.format(date) => Retorna uma String
				
				statement.setString(1, titulo);
				statement.setString(2, conteudo);
				statement.setDate(3, dataSQL);
				statement.execute();
				
				statement.close();
				conn.close();
				
				out.println("<article style='color: green;'>Salvo com sucesso!</article>");
				response.sendRedirect("/Chamados/ListarChamados");
			}					
			
			/*
			PrintWriter out = response.getWriter();
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Novo Chamado</title>");
			out.println("<meta charset=utf-8'/>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha as informações do chamado</h1>");
			out.println("<hr/>");
			
			//	Recupera os valores passados no formulário (requisição = request)
			String titulo = request.getParameter("titulo");
			String conteudo = request.getParameter("conteudo");
			
			//	Validação do formulário
			//	O trim() RETIRA TODOS OS ESPAÇOS NA HORA DE COMPARAR
			if(titulo.trim().equals("") || conteudo.trim().equals("")){
				out.println("<i style='color: red;'>Preencha os campos</i>");
			}if(titulo.trim().length() <= 5 || conteudo.trim().length() <= 5){
				out.println("<i style='color: red;'>Os campos devem conter mais de 5 caracteres</i>");
			}else{
				//	Os dados enviados do formulário via POST cairam aqui (foram recuperados)
				out.println("<b>Título enviado: </b>" + titulo);
				out.println("<br/>");
				out.println("<b>Conteúdo enviado: </b>" + conteudo);
			}
			
			out.println("<form method='POST'>");
			out.println("Titulo: <br/><input type='text' name='titulo'>");
			out.println("<br/>");
			out.println("Conteúdo: <br/><textarea name='conteudo' rows='10' cols='40'></textarea>");
			out.println("<br/>");
			out.println("<input type='submit' value'Abrir Chamado'/>");
			out.println("<br/>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamados</a>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/Logoff'>Sair</a>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			*/
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			out.println("<i style='color: red;'>Erro ao carregar driver de conexão!</i>");
		}catch(SQLException e){
			out.println("<i style='color: red;'>Erro com o banco de dados! </i>" + e.getMessage());
		}

	}
	
}
