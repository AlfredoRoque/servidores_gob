<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<title>
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.addDateHeader("Expires", 0);
	//if(session.getAttribute("title")!=null){
	//	out.print("Busqueda: "+session.getAttribute("title"));
	//	session.removeAttribute("title");
	//}else{
	//	out.print("HOME");
	//}
%>
</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/lazysizes/5.2.0/lazysizes.min.js"></script>
</head>
<body>
<header>
	<nav class="bar">
		
	</nav>
</header>
<div class="body">
	<form method="post" action="Search">
		<input type="submit" value="IR">
	</form>
	<div>
	<%
	if(session.getAttribute("cuerpo")!=null){
		out.print(session.getAttribute("cuerpo"));
		session.removeAttribute("cuerpo");
	}else{
		out.print("<h1>SECRETARIAS</h1>");
	}
	%>
	</div>
	<form method="post" action="Entidades">
		<input type="submit" value="IR2">
	</form>
	<%
	if(session.getAttribute("caca")!=null){
		out.print("LISTO");
		session.removeAttribute("caca");
	}else{
		out.print("<h1>ENTIDADES</h1>");
	}
	%>
	<div>
</div>
<footer>
	
</footer>
<script src="js/scripts.js"></script>
</body>
</html>
