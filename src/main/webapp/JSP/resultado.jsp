<%-- 
    Document   : index
    Created on : 25-oct-2018, 21:10:38
    Author     : tomlu
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control de Aves Extremadura</title>

    </head>
    <body>
        <div class="primera">
            <div class="resultado">
                <%
                    String opcion = (String)request.getAttribute("opcion");
                    String operacion = "";
                    switch(opcion) {
                        case "insertar":
                            operacion = "insertado";
                            break;
                        case "eliminar":
                            operacion = "eliminado";
                            break;
                        case "modificar":
                            operacion = "modificar";
                            break;
                    }
                %>
                <p>Se ha <%=operacion%> el ave: </p>
                <p><%=request.getAttribute("datos")%></p>
            </div>
            <% String nombre = (String)request.getContextPath();%>
            <p> <a href="<%=nombre%>">Volver</a></p>
        </div>

    </body>
</html>
