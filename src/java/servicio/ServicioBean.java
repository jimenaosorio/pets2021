/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.*;

/**
 *
 * @author jimenaosorio
 */
@Stateless
public class ServicioBean implements ServicioBeanLocal {

    @PersistenceContext(unitName = "EjemploJPAPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    

    @Override
    public List<Perfil> getPerfiles() {
        return em.createQuery("select p from Perfil p").getResultList();
    }

    @Override
    public List<Producto> getProductos() {
        return em.createQuery("select p from Producto p").getResultList();
    }

    @Override
    public List<Categoria> getCategorias() {
        return em.createQuery("select c from Categoria c").getResultList();
    }

    @Override
    public Producto buscarProducto(int id) {
        return em.find(Producto.class, id);
    }

    @Override
    public Perfil buscarPerfil(int id) {
        return em.find(Perfil.class, id);
    }

    @Override
    public Usuario iniciarSesion(String rut, String clave) {
        try{
            return (Usuario) em.createNamedQuery("Usuario.iniciarSesion",Usuario.class).setParameter("rutUser",rut).setParameter("clave",clave).getSingleResult();
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    @Override
    public Usuario buscarUsuario(String rut) {
        return em.find(Usuario.class, rut);
    }

    @Override
    public void guardar(Object object) {
        em.persist(object);
    }

    @Override
    public Categoria buscarCategoria(int id) {
        return null;
    }

    @Override
    public void sincronizar(Object obj) {
        em.merge(obj);
        em.flush();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
