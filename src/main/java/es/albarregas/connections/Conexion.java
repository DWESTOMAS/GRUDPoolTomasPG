/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.connections;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author tomlu
 */
public class Conexion extends HttpServlet {

    public static DataSource dataSource = null;

    public static Connection conectar() {
        try {
            InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Pool");
            return dataSource.getConnection();
        } catch (NamingException ex) {
            System.out.println("Problemas en  el  acceso  a  la  BD...");
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void cerrarConexion(Connection conexion) throws ServletException, SQLException {

        conexion.close();

    }

    public static Connection getConnection() throws NamingException {
        Connection conn = null;
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jdbc/HopenetDB");
        try {
            conn = (Connection) ds.getConnection();
        } catch (SQLException e) {
            System.out.println("hay error aqui");
            e.printStackTrace();
        }
        return conn;
    }
}
