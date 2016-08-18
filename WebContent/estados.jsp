<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datos.*,java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	ArrayList<Estado> estados = new Estado(getServletContext()).cosultarEstados();
	
	for(int i=0; i<estados.size(); i++)
	{
		out.print(estados.get(i).idEstado);
		out.println(estados.get(i).nombreEstado);
	}
%>
</body>
</html>