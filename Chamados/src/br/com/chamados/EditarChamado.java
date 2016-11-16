package br.com.chamados;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;

@SuppressWarnings("serial")
public class EditarChamado extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html");
		
		PrintWriter out = null;

		try{
			//	Habilita o println
			out = response.getWriter();
			
			//	Impende que eu acesse direto essa URL
			HttpSession session = request.getSession();
			if(session.getAttribute("login") == null){
				response.sendRedirect("/Chamados/Login");
			}

			//	Conexão via Driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://localhost:3306/rlsystem";
			final String user = "root";
			final String password = "Oni@2015";
			Connection conn = DriverManager.getConnection(url, user, password);
			
			String sql = "SELECT * FROM chamados WHERE id = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, Integer.parseInt(request.getParameter("id")));
			
			//	Mesmo tendo parâmetro, tem que ter o resulSet porque ele vai me trazer um registro (Query)
			ResultSet resultSet = statement.executeQuery();
			
			//	Avança (encontrar um registro)
			if(resultSet.next()){
			
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Editar Chamado</title>");
				out.println("<meta charset=utf-8'/>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Alterar Dados</h1>");
				out.println("<hr/>");
				out.println("<form method='POST'>");
				out.println("Código: <br/><input type='text' disabled='disabled' name='id' value='"+ resultSet.getLong("id") +"'>");
				out.println("<br/>");
				out.println("Titulo: <br/><input type='text' name='titulo' value='"+ resultSet.getString("titulo") +"'>");
				out.println("<br/>");
				out.println("Conteúdo: <br/><textarea name='conteudo' rows='10' cols='40'>"+ resultSet.getString("conteudo") +"</textarea>");
				out.println("<br/>");
				out.println("<input type='submit' value='Atualizar Chamado'/>");
				out.println("<br/>");
				out.println("<br/>");
				out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamados</a>");
				out.println("<br/>");
				out.println("<a href='http://localhost:8080/Chamados/Logoff'>Sair</a>");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
			}else{
				out.println("<i style='color: red;'>Esse chamado não existe!</i>");
			}
			
			statement.close();
			conn.close();

			

		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			out.println("<i style='color: red;'>Erro ao carregar driver de conexão!</i>");
		}catch(SQLException e){
			out.println("<i style='color: red;'>Erro com o banco de dados! </i>" + e.getMessage());
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		PrintWriter out = null;

		try{
			//	Habilita o println
			out = response.getWriter();
			
			//	Recuperando valores do formulário
			Long id = Long.parseLong(request.getParameter("id"));
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
				
				//	4. Atualizar chamado
				String sqlUpdate = "UPDATE chamados SET titulo = ?, conteudo = ? WHERE id = ?";
				
				//	Usado quando eu vou utilizar parêmetros, WHERE por exemplo
				PreparedStatement statement = conn.prepareStatement(sqlUpdate);
				statement.setString(1, titulo);
				statement.setString(2, conteudo);
				statement.setLong(3, id);
				statement.execute();
				
				statement.close();
				conn.close();
				
				out.println("<article style='color: green;'>Atualizado com sucesso!</article>");
				
				response.sendRedirect("/Chamados/ListarChamados");
				
			}
			}catch(IOException e) {
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				out.println("<i style='color: red;'>Erro ao carregar driver de conexão!</i>");
			}catch(SQLException e){
				out.println("<i style='color: red;'>Erro com o banco de dados! </i>" + e.getMessage());
			}			
	}
	
}
	


