<%-- 
    Document   : leer
    Created on : 25-oct-2018, 21:10:01
    Author     : tomlu
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="es.albarregas.bean.AveBean"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .enlaceboton {    
                padding: 1px 6px; 
                align-items: flex-start;
                text-align: center;
                cursor: default;
                color: buttontext;
                background-color: buttonface;
                box-sizing: border-box;
                padding: 2px 6px 3px;
                border-width: 2px;
                border-style: outset;
                border-color: buttonface;
                border-image: initial;
                -webkit-appearance: push-button;
                user-select: none;
                white-space: pre;
                text-decoration: none; 
                text-rendering: auto;
                color: initial;
                letter-spacing: normal;
                word-spacing: normal;
                text-transform: none;
                text-indent: 0px;
                text-shadow: none;
                display: inline-block;
                text-align: start;
                margin: 0em;
                font: 400 13.3333px Arial;
            } 
            .enlaceboton:link, 
            .enlaceboton:visited { 
                border-top: 1px solid #cccccc; 
                border-bottom: 2px solid #666666; 
                border-left: 1px solid #cccccc; 
                border-right: 2px solid #666666; 
            } 
            .enlaceboton:hover { 
                border-bottom: 1px solid #cccccc; 
                border-top: 2px solid #666666; 
                border-right: 1px solid #cccccc; 
                border-left: 2px solid #666666; 
            }
        </style>

        <script>
            function mostrarDatos() {
                document.getElementById('formulario1').submit();
            }

        </script>
    </head>
    <body>
        <h1>Lista de aves</h1>
        <form id="formulario1" action="./Operacion" method="post">
            <input type="hidden" name="opcion" value="Borrar Ave">

            <div class="listado" id="listado">
                <%
                    //Esta hago un casting al objeto que paso por request que es un array list
                    Object listaAves = request.getAttribute("lista");
                    ArrayList<AveBean> aves = (ArrayList<AveBean>) listaAves;
                    //Creo el objeto fecha que necesito
                    SimpleDateFormat fechaString = new SimpleDateFormat("dd/mm/yyyy");
                %>
                <table style="border: black"><tr><th></th><th>Anilla</th><th>Especie</th><th>Lugar</th><th>Fecha</th></tr>
                            <%
                                if (aves != null) {
                                    String anilla = request.getParameter("anilla");
                                    for (int i = 0; i < aves.size(); i++) {
                                        boolean checked = false;
                                        if (anilla == null) {
                                            checked = false;
                                        } else if (anilla.equals(aves.get(i).getAnilla())) {
                                            checked = true;
                                        }
                            %>
                    <tr>
                        <td><input type="radio" name="anilla" value="<%=aves.get(i).getAnilla()%>" <%=checked ? "checked" : ""%> onclick="mostrarDatos('<%=aves.get(i).getAnilla()%>')"></td>
                        <td><%=aves.get(i).getAnilla()%></td>
                        <td><%=aves.get(i).getEspecie()%></td>
                        <td><%=aves.get(i).getLugar()%></td>
                        <td><%=fechaString.format(aves.get(i).getFecha())%>
                        </td>
                    </tr>
                    <%}%>
                </table>




                <% } else {
                %>
                <p>Lo sentimos no hay aves que mostrar</p>
                <%
                    }%> 


            </div>
        </form>
        <form id="formulario2" action="./Realizar" method="post">
            <%
                AveBean seleccionado = null;
                for (AveBean ave : aves) {
                    if (ave.getAnilla().equals(request.getParameter("anilla"))) {
                seleccionado = ave;
                
            %>
            <p>Va a eliminar el ave: </p>


            <% }
                       }%>
            <p><%=seleccionado != null ? seleccionado.toString() : ""%></p>

            <input type="hidden" name="oculto" value="eliminar">
            <input type="hidden" name="anilla" value="<%=request.getParameter("anilla")%>">
            <input type="hidden" name="avebean" value="<%=seleccionado%>">
            <div class="datos" id="datos">

            </div>
            <a href="<%=request.getContextPath()%>" class="enlaceboton">Volver</a>
            <%
                if (request.getParameter("anilla") != null) {%>
            <input type="submit" name="eliminar" value="Borrar" >
            <%}%>

        </form>
    </body>
</html>
