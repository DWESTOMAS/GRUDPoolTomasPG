<%-- 
    Document   : inicioInsertar
    Created on : 28-oct-2018, 0:54:10
    Author     : tomlu
--%>

<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Este es el formulario de insertar ave-->
        <h1>Insertar Ave</h1>
        <form name="insertar" method="Post" action="./Realizar">
            <% String[] mensajes = (String[])request.getAttribute("mensaje");
                if (mensajes!=null) {%>
                <div id="mensaje" style="background-color: #ccccff">
                    <% for (String mensaje: mensajes) {
                        %>
                        <p style="color: red;"><%=mensaje%></p>
                    <%}%>
                        
                </div>
            <%}%>
            <p><label>Anilla: </label><input type="text" name="anilla" value="<%=request.getAttribute("anilla")!=null?request.getAttribute("anilla"):""%>"></p>
            <p><label>Especie: </label><input type="text" name="especie" value="<%=request.getAttribute("especie")!=null?request.getAttribute("especie"):""%>"></p>
            <p><label>Lugar: </label><input type="text" name="lugar" value="<%=request.getAttribute("lugar")!=null?request.getAttribute("lugar"):""%>"></p>
                <%
                    //para que con el paso del tiempo no se quede con los años atras
                    Calendar cal = Calendar.getInstance();
                    int yearMin = cal.get(Calendar.YEAR) - 50;
                    
                    String max = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                %>
            <p><label>Fecha: </label><input type="date" id="fecha" name="fecha" value="<%= request.getAttribute("fecha")%>"
                                            min="<%=yearMin%>-01-01" max="<%=max%>" />
            </p>
            <input type="hidden" name="oculto" value="insertar">
            <p><input type="submit" name="modificar" value="Aceptar"><input type="reset" name="reset" value="Cancelar"></p>
        </form>
    </body>
</html>
