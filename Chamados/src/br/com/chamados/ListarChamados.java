package br.com.chamados;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class ListarChamados extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		response.setContentType("text/html");
		
		PrintWriter out = null;
		
		//	Impende que eu acesse direto essa URL
		HttpSession session = request.getSession();
		if(session.getAttribute("login") == null){
			response.sendRedirect("/Chamados/Login");
		}
		
		try{
			//	Habilita o println
			out = response.getWriter();
			
			//	Logoff
			out.println("<br/><a href='http://localhost:8080/Chamados/Login?msg=logoff'>Logoff</a>");

			//	Conexão via Driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://localhost:3306/rlsystem";
			final String user = "root";
			final String password = "Oni@2015";
			Connection conn = DriverManager.getConnection(url, user, password);
			
			//	2. Remover dados
			//	Quando existir uma QUERY string id
			if(request.getParameter("id") != null){
				Long id = Long.parseLong(request.getParameter("id"));
				String slqDelete = "DELETE FROM chamados WHERE id = ?";
				PreparedStatement preparedStatement = conn.prepareStatement(slqDelete);
				preparedStatement.setLong(1, id);
				preparedStatement.execute();
			}
			
			//	3. Listar dados
			String sql = "SELECT * FROM chamados";
			
			//	Como não vou utilizar parâmetros, o Statement é mais indicado
			Statement statement = conn.createStatement();
			
			//	Responsável por armazenar todas as informações que vieram da execução de um comando SQL SELECT
			ResultSet resultSet = statement.executeQuery(sql);
			
			//	Criando uma tabela para apresentar os dados
			out.println("<table width='50%'>");
			
			out.println("<tr bgcolor='#c0c0c0'>");
			out.println("<td><b>Código</b></td>");
			out.println("<td><b>Titulo</b></td>");
			out.println("<td><b>Conteúdo</b></td>");
			out.println("<td><b>Data</b></td>");
			out.println("<td><b>Editar</b></td>");
			out.println("<td><b>Remover</b></td>");
			out.println("</tr>");
			
			//	Enquanto tiver linha (resultado)
			while(resultSet.next()){
				out.println("<tr>");
				out.println("<td>" + resultSet.getLong("id") + "</td>");
				out.println("<td>" + resultSet.getString("titulo") + "</td>");
				out.println("<td>" + resultSet.getString("conteudo") + "</td>");
				out.println("<td>" + resultSet.getDate("data_") + "</td>");
				out.println("<td><a href='http://localhost:8080/Chamados/EditarChamado?id=" + resultSet.getLong("id") + "'>[EDITAR]</a></td>");
				out.println("<td><a href='http://localhost:8080/Chamados/ListarChamados?id=" + resultSet.getLong("id") + "'>[REMOVER]</a></td>");
				out.println("</tr>");
			}

			out.println("</table>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/NovoChamado'>Novo Chamado</a>");


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
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		
	}
	
	
}
