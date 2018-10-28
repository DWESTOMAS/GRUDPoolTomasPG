/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controlers;

import es.albarregas.bean.AveBean;
import es.albarregas.connections.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tomlu
 */
@WebServlet(name = "Realizar", urlPatterns = {"/Realizar"})
public class Realizar extends HttpServlet {

    private AveBean datosAve;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        datosAve = getAve(request);
        String opcion = request.getParameter("oculto");

        String url = "";
        request.setAttribute("mensaje", null);
        try {
            if (opcion != null) {
                switch (opcion) {
                    case "insertar":
                        String[] mensajes = comprobarDatos(datosAve);
                        if (mensajes == null) {

                            insertarAnilla();
                        } else {
                            request.setAttribute("mensaje", mensajes);
                            request.setAttribute("anilla", datosAve.getAnilla());
                            request.setAttribute("especie", datosAve.getEspecie());
                            request.setAttribute("fecha", datosAve.getFecha());
                            request.setAttribute("lugar", datosAve.getLugar());
                            url += "./JSP/create/inicioInsertar.jsp";
                        }
                        break;
                    case "eliminar":
                        obtenerAve();
                        eliminarAnilla();
                        break;
                }
            }
            request.setAttribute("opcion", opcion);
            request.setAttribute("datos", datosAve.toString());
            url += "./JSP/resultado.jsp";
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        request.getRequestDispatcher(url)
                .forward(request, response);
    }
    // Método que recoge los parámetros con los datos desde la pantalla.
    // Se leen desde la request los datos que se van a insertar en la nueva ficha
    // del ave.

    private AveBean getAve(HttpServletRequest request) {
        AveBean aveBean = new AveBean();
        aveBean.setAnilla(request.getParameter("anilla"));
        aveBean.setEspecie(request.getParameter("especie"));
        String fechaString = request.getParameter("fecha");
        Date fecha = null;
        if (fechaString != null) {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fecha = formateador.parse(fechaString);

            } catch (ParseException ex) {
                Logger.getLogger(Realizar.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        aveBean.setFecha(fecha);
        aveBean.setLugar(request.getParameter("lugar"));

        return aveBean;
    }

    //Metodo en el que comprobamos si está la anilla en la base de datos, se puede hacer de dos formas que al introducir
    //la anilla nos da error de la base de datos que no me gusta, porque si te da otro tipo de error no lo vamos a controlar
    //por eso lo haré comprobando si existe la anilla en la base de datos, si no existe entonces insertaremos toda la linea, además
    //mostraré una línea en otro color que será la última línea introducida.
    private boolean existeAnilla(String anilla) {
        boolean existe = false;
        //pongo a true porque quiero que me devuelva false cuando exista.

        Connection conectar = Conexion.conectar();
        String sql = "Select count(anilla) from aves where anilla=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conectar.prepareStatement(sql);
            ps.setString(1, anilla);
            rs = ps.executeQuery();
            int contador = 0;
            while (rs.next()) {
                contador = rs.getInt(1);
            }
            existe = contador > 0;
            conectar.close();
        } catch (SQLException ex) {
            existe = true;
        } finally {

            try {
                ps.close();
                rs.close();

                Conexion.cerrarConexion(conectar);

            } catch (SQLException ex) {
                Logger.getLogger(Realizar.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (ServletException ex) {
                Logger.getLogger(Realizar.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        return existe;
    }

    private void insertarAnilla() throws SQLException {

        Connection conectar = Conexion.conectar();
        String sql = "insert into aves values (?, ?,?,?);";
        PreparedStatement ps = null;
        int rs = 0;
        try {
            ps = conectar.prepareStatement(sql);
            ps.setString(1, datosAve.getAnilla());
            ps.setString(2, datosAve.getEspecie());
            ps.setString(3, datosAve.getLugar());
            ps.setString(4, getFechaString(datosAve.getFecha()));
            rs = ps.executeUpdate();

            conectar.close();

        } catch (SQLException ex) {
            Logger.getLogger(Realizar.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

    }

    // Convierte una fecha en la cadena en la que se almacenan las fechas en BBDD.
    private String getFechaString(Date fecha) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        return formateador.format(fecha);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    //Método en el que comprobamos que los campos son correctos
    //Como fecha no puede ser incorrecta entonces solo nos queda comprobar los campos que no son nulos en la base de datos
    //Cómo no lo son el campo anilla por ser primary key, y especie que le pusimos not null
    //Llama también al método que comprueba si existe en la base de datos que lo hare en otro método
    private String[] comprobarDatos(AveBean ave) {
        String[] mensaje = null;

        List<String> lista = new ArrayList<>();
        if (ave.getAnilla() == null || ave.getAnilla().equals("")) {
            lista.add("La anilla es obligatoria");
        }
        if (ave.getEspecie() == null || ave.getEspecie().equals("")) {
            lista.add("La especie es obligatoria");
        }
        if (ave.getFecha() == null) {
            lista.add("La fecha es obligatoria");
        }
        if (existeAnilla(ave.getAnilla())) {
            lista.add("La anilla ya existe");
        }

        if (!lista.isEmpty()) {
            mensaje = new String[lista.size()];
            lista.toArray(mensaje);
        }
        return mensaje;
    }

    private void eliminarAnilla() throws SQLException {
        Connection conectar = Conexion.conectar();
        String sql = "delete from aves where anilla =?;";
        PreparedStatement ps = null;
        int rs = 0;
        try {
            ps = conectar.prepareStatement(sql);
            ps.setString(1, datosAve.getAnilla());

            rs = ps.executeUpdate();

            conectar.close();

        } catch (SQLException ex) {
            Logger.getLogger(Realizar.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    private void obtenerAve() {
       Connection conectar= Conexion.conectar();
        String sql="Select anilla,nombre,lugar,fecha from aves where anilla = '"+datosAve.getAnilla()+"'";
       PreparedStatement ps = null;
       ResultSet rs=null;
       
        try {
            ps=conectar.prepareStatement(sql);
            rs = ps.executeQuery(sql);
             
            while(rs.next()){
                 AveBean ave=new AveBean();
                ave.setAnilla(rs.getString(1));
                ave.setEspecie(rs.getString(2));
                ave.setLugar(rs.getString(3));
                
                ave.setFecha(convertirFecha(rs.getString(4)));
                datosAve = ave;
            }
        } catch (Exception ex) {
            Logger.getLogger(Operacion.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ps.close();
                rs.close();
                try {
                    Conexion.cerrarConexion(conectar);
                } catch (ServletException ex) {
                    Logger.getLogger(Operacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Operacion.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
    }
private Date convertirFecha(String fechaCad){
        try {
            Date fechaCorrecta=null;
            SimpleDateFormat fecha=new SimpleDateFormat("dd/mm/yyyy");
            fechaCorrecta=(Date)fecha.parse(fechaCad);
            return fechaCorrecta;
            
        } catch (ParseException ex) {
            return null;
        }
        
        
    }
}
