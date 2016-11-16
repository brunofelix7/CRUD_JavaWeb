<!-- Diretiva page (código Java) -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Novo usuário</title>
</head>
<body>

	<!-- Scriplet - permite programar código java -->
	<% 
		if(request.getParameter("msg") != null){
			if(request.getParameter("msg").equals("success")){
	%>
		<i style="color:green;">Cadastrado com sucesso!</i>
	<%
			}if(request.getParameter("msg").equals("error")){
	%>
		<i style="color:red;">Por favor, preencha todos os campos!</i>
	<%	
			}
		}
	%>
		
	<form method="POST" action="usuario">
		<label>Login:</label><br/>
		<input type="text" name="login"/><br/>
		<label>Senha:</label><br/>
		<input type="password" name="senha"/><br/><br/>
		<input type="submit" value="Cadastrar"/>
	</form>
</body>
</html>