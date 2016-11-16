package br.com.chamados;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class LoginChamado extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html");
		
		Cookie[] cookieDelete = request.getCookies();
		if(cookieDelete != null){
			for(Cookie ck : cookieDelete){
				if(ck.getName().equals("login_name")){
					ck.setMaxAge(0);
					response.addCookie(ck);
				}
			}
		}
		
		try {
			PrintWriter out = response.getWriter();
			
			//	Sair
			if(request.getParameter("msg") != null){
				if(request.getParameter("msg").equals("logoff")){
					//	Recupera a sessao
					HttpSession sessao = request.getSession();
					sessao.removeAttribute("login");
					//	sessao.invalidate(); Remove todas as variáveis de sessões
					out.println("<span style='color:green;'>Deslogado com sucesso!</span>");
				}
			}
			
			
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Login</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Login</h1>");
			out.println("<hr/>");
			
			//	Invalidando login/senha
			if(request.getParameter("msg") != null){
				if(request.getParameter("msg").equals("error")){
					out.println("<span style='color:red;'>Login/Senha incorretos</span>");
				}
			}
			
			//	Pegando um Cookie enviado pelo usuário
			//	Somente os que pertencem ao meu domínio "https://localhost:8080/Chamados"
			String login_name = "";
			Cookie[] cookie = request.getCookies();
			if(cookie != null){
				for(Cookie ck : cookie){
					if(ck.getName().equals("login_name")){
						login_name = ck.getValue();
					}
				}
			}
			
			out.println("<form method='POST'>");
			out.println("Login: <br/><input type='text' name='login' value='" + login_name +"'>");
			out.println("<br/><br/>");
			out.println("Senha: <br/><input type='password' name='senha'>");
			out.println("<br/><br/>");
			out.println("<input type='submit' value='Entrar'/>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		//	Criando um Cookie de nome "login_name" e valor digitado pelo usuário no campo "login"
		Cookie cookie = new Cookie("login_name", login);
		//	Duração do Cookie em segundos 1 ANO
		cookie.setMaxAge(60*60*24*30*12);
		response.addCookie(cookie);
		
		try {
			//	Habilita o println
			@SuppressWarnings("unused")
			PrintWriter out = response.getWriter();
			
			//	Conexão via Driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://localhost:3306/rlsystem";
			final String user = "root";
			final String password = "Oni@2015";
			Connection conn = DriverManager.getConnection(url, user, password);
			
			String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, login);
			statement.setString(2, senha);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				HttpSession session = request.getSession();
				session.setAttribute("login", login);
				response.sendRedirect("/Chamados/ListarChamados");
			}else{
				response.sendRedirect("/Chamados/Login?msg=error");
			}
			
			statement.close();
			conn.close();
						
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
