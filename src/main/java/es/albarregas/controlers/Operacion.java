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
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "Operacion", urlPatterns = {"/Operacion"})
public class Operacion extends HttpServlet {

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
        String url="";
       //recogemos por nombre la accion que queremos hacer
       switch (request.getParameter("opcion")){
       
           case "Mostrar las Aves":
             
               request.setAttribute("lista", objetoAve());
               url+="./JSP/read/leer.jsp";
               break;
           case "Nuevo Ave":
               url+="./JSP/create/inicioInsertar.jsp";
               break;
           case "Borrar Ave":
               request.setAttribute("lista", objetoAve());
               if (request.getAttribute("anilla") != null) {
                   request.setAttribute("anilla", request.getAttribute("anilla"));
               } 
               url+="./JSP/delete/eliminar.jsp";
               break;
       }
      // url+=request.getContextPath()+url;
       request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private ArrayList<AveBean> objetoAve(){
        ArrayList<AveBean>lista=new ArrayList<>();
       
       Connection conectar= Conexion.conectar();
       String sql="Select anilla,nombre,lugar,fecha from aves";
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
                lista.add(ave);
               // System.out.println(rs.getString(1));
            
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
       return lista;
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
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
