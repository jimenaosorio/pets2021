/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.List;
import javax.ejb.Local;
import modelo.*;

/**
 *
 * @author jimenaosorio
 */
@Local
public interface ServicioBeanLocal {

    List<Perfil> getPerfiles();

    List<Producto> getProductos();

    List<Categoria> getCategorias();

    Producto buscarProducto(int id);

    Perfil buscarPerfil(int id);

    Usuario iniciarSesion(String rut, String clave);

    Usuario buscarUsuario(String rut);

    void guardar(Object object);
    
}
