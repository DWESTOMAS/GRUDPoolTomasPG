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
    </head>
    <body>
        <h1>Lista de aves</h1>
        <div class="pruebaDiv">
            <%
                //Esta hago un casting al objeto que paso por request que es un array list
                Object listaAves = request.getAttribute("lista");
                ArrayList<AveBean> aves = (ArrayList<AveBean>) listaAves;
                //Creo el objeto fecha que necesito
                SimpleDateFormat fechaString = new SimpleDateFormat("dd/mm/yyyy");
            %>
            <table ><tr><th>Anilla</th><th>Especie</th><th>Lugar</th><th>Fecha</th></tr>
                        <%
                            if (aves != null) {
                                for (int i = 0; i < aves.size(); i++) {
                                    %><tr>
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
        <a href="<%=request.getContextPath()%>" class="enlaceboton">Volver</a>
        
    </body>
</html>
