/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Categoria;
import modelo.Perfil;
import modelo.Usuario;
import servicio.ServicioBeanLocal;
import util.Hash;

/**
 *
 * @author jimenaosorio
 */
@WebServlet(name = "ControlServlet", urlPatterns = {"/control.do"})
public class ControlServlet extends HttpServlet {

    @EJB
    private ServicioBeanLocal servicioBean;
    

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
            String boton=request.getParameter("boton");
            switch(boton)
            {
                case "login": login(request,response);
                    break;
                case "registrar": registrar(request,response);
                    break;
                case "nuevaCategoria": nuevaCategoria(request,response);
                    break;
                case "editardatos": modificarUsuario(request,response);
                    break;
            }
    }
    
    protected void modificarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo=request.getParameter("correo");
        String clave1=request.getParameter("clave1");
        String clave2=request.getParameter("clave2");
        String errores="";
        String mensajes="";
        Usuario usuario=(Usuario) request.getSession().getAttribute("admin");
        //Cambio de clave
        if(!clave1.isEmpty()) //Si quiere cambiar la clave escribió algo en clave1
        {
            if(!clave2.equals(clave1)) //Claves distintas
            {
                errores+="Las claves ingresadas no coinciden, por favor inténtelo de nuevo.<BR/>";
                
            }
            else{
                usuario.setClave(Hash.md5(clave2));
                mensajes+="Se ha actualizado la clave del usuario.<BR/>";
            }
        }
        //Cambio de correo
        if(!correo.isEmpty()) //quiere cambiar el correo
        {
            usuario.setEmailUser(correo);
            mensajes+="Se ha actualizado el correo.<BR/>";
        }
        if(!mensajes.isEmpty())
        {
            //Actualizar en la base de datos
            servicioBean.sincronizar(usuario);
            
            //Actualizar en la sesión
            request.getSession().setAttribute("admin",usuario);
            
            //Enviar el mensaje
            request.setAttribute("msg",mensajes);
        }
        else if(errores.isEmpty())
        {
            request.setAttribute("msg", "No se han actualizado los datos");
        }
        else //hay errores
        {
            request.setAttribute("msg",errores);
        }
        request.getRequestDispatcher("misdatos.jsp").forward(request,response);
        
        
    }
    
    protected void nuevaCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre=request.getParameter("nombre");
        if(nombre.isEmpty())
        {
            request.setAttribute("msg","Debe escribir el nombre de la categoria");
            
        }
        else
        {
            //Agregamos la categoría
            Categoria nueva=new Categoria();
            nueva.setNombreCategoria(nombre);
            servicioBean.guardar(nueva);
            request.setAttribute("msg","Se ha creado la categoria correctamente");
            
        }
        request.getRequestDispatcher("categoria.jsp").forward(request,response);
        
    }
    
    protected void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rut=request.getParameter("rut");
        String nombre=request.getParameter("nombre");
        String apellido=request.getParameter("apellido");
        String email=request.getParameter("email");
        String clave=request.getParameter("clave");
        String clave2=request.getParameter("clave2");
        String errores="";
        
        if(rut.equals(""))
        {
            errores+="Debe ingresar el RUT<br/>";
        }
        //Validar los demás atributos...
        
        if(!clave.equals(clave2))
        {
            errores+="Las clave no coinciden<br/>";
        }
        if(errores.equals(""))
        {
            if(servicioBean.buscarUsuario(rut)==null)
            {
                //Es un nuevo usuario
                Usuario usuario=new Usuario();
                usuario.setRutUser(rut);
                usuario.setNombreUser(nombre);
                usuario.setApellidoUser(apellido);
                usuario.setEmailUser(email);
                usuario.setClave(Hash.md5(clave));
                Perfil perfil=servicioBean.buscarPerfil(2); //Cliente
                usuario.setPerfil(perfil);
                servicioBean.guardar(usuario);
                request.setAttribute("msg","Se ha creado el usuario correctamente");
            }
            else
            {
                errores+="El usuario ya está registrado";
                request.setAttribute("msg","El usuario ya está registrado");
            }
        }
        else
        {
            request.setAttribute("msg", errores);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
        
        
    }
    
    protected void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rut=request.getParameter("rut");
        String clave=request.getParameter("clave");
        String errores="";
        if(rut.isEmpty()) errores += "Debe ingresar el RUT. <BR/>";
        if(clave.isEmpty()) errores += "Debe ingresar la clave. <BR/>";
        if(errores.isEmpty())
        {
            Usuario user=servicioBean.iniciarSesion(rut,Hash.md5(clave));
            if(user!=null)  //usuario correcto
            {
                if(user.getPerfil().getNombrePerfil().equals("administrador"))
                {
                    //Es administador
                    request.getSession().setAttribute("admin", user);
                }
                else
                {
                    //cliente
                    request.getSession().setAttribute("cliente", user);
                }
                //redireccionamos al inicio
                response.sendRedirect("inicio.jsp");
                
            }
            else  //error en el inicio de sesion
            {
                //Redireccionamos el error
                request.setAttribute("msg", "El usuario no se encuentra registrado");
                request.getRequestDispatcher("index.jsp").forward(request,response);
                
            }
                    
            
        }
        else
        {
            request.setAttribute("msg", "Debe ingresar RUT y clave");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
        
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

}
