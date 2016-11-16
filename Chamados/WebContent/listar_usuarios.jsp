<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" errorPage="error.jsp" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usuários cadastrados</title>
</head>
<body>
<table>
	<tr>
		<td><b>Código</b></td>
		<td><b>Login</b></td>
	</tr>
		<%
			//	Para usuar (instanciar) uma classe dentro do JSP, ela é obrigada a estar dentro de um pacote

			String sql = "SELECT * FROM usuario";
			
			// Conexão via Driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://localhost:3306/rlsystem";
			final String user = "root";
			final String password = "Oni@2015";
			Connection conn = DriverManager.getConnection(url, user, password);
			
			Statement statement = conn.createStatement();
			
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()){
		%>
			<tr>
				<td><%=resultSet.getLong("id")%></td>
				<td><%=resultSet.getString("login")%></td>
			</tr>
		<%
			}
		%>
</table>
</body>
</html>