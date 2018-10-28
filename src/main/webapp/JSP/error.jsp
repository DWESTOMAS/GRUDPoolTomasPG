<%-- 
    Document   : error
    Created on : 14-oct-2018, 20:00:36
    Author     : tomlu
--%>

<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title><%= ((Exception)exception).getMessage()%></title>
    </head>
    <body>
        <h2>Se ha producido un error.</h2>
        <i>Un error se ha producido:</i>
        <b><%= ((Exception)exception).getMessage()%></b>
        <% String nombre=request.getContextPath();%>
        <p> <a href="<%=nombre%>">Volver</a></p>
    </body>
</html>
</html>
