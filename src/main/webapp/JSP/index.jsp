<%-- 
    Document   : index
    Created on : 25-oct-2018, 21:10:38
    Author     : tomlu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control de Aves Extremadura</title>
        
    </head>
    <body>
        <div class="primera">
            <div name="primerFormulario">
                <form name="inicial" method="Post" action="./Operacion">
                    <input type="submit" name="opcion" value="Mostrar las Aves">
                    <input type="submit" name="opcion" value="Nuevo Ave">
                    <input type="submit" name="opcion" value="Borrar Ave">
                    <input type="submit" name="opcion" value="Modificar Ave">
                </form>
            </div>
        </div>
        
    </body>
</html>
