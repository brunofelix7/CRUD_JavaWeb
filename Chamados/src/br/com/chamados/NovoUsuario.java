package br.com.chamados;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/usuario")
public class NovoUsuario extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");		
		PrintWriter out = response.getWriter();

		try{
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String sql = "INSERT INTO usuario(login, senha) VALUES(?, ?)";

			if(login.trim().equals("") || senha.trim().equals("")){
				response.sendRedirect("/Chamados/cadastro_usuario.jsp?msg=error");
				return;
			}else{
				Class.forName("com.mysql.jdbc.Driver");
				final String url = "jdbc:mysql://localhost:3306/rlsystem";
				final String user = "root";
				final String password = "Oni@2015";
				Connection conn = DriverManager.getConnection(url, user, password);

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, login);
				statement.setString(2, senha);
				statement.execute();
				
				statement.close();
				conn.close();
				
				response.sendRedirect("/Chamados/cadastro_usuario.jsp?msg=success");
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
