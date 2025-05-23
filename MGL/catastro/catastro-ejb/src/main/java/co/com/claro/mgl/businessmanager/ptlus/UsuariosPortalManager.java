package co.com.claro.mgl.businessmanager.ptlus;

import co.com.claro.mgl.dao.impl.ptlus.UsuariosPortalDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ptlus.UsuariosPortal;
import java.math.BigDecimal;
import java.util.List;


/**
 * Manager para el manejo de Usuarios del Portal.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class UsuariosPortalManager {
    
    public List<UsuariosPortal> findAll() throws ApplicationException {
        UsuariosPortalDaoImpl manager = new UsuariosPortalDaoImpl();
        List<UsuariosPortal> usuarios = manager.findAll();
        return (usuarios);
    }
    
    
    public UsuariosPortal findUsuarioPortalById(BigDecimal idPortal) throws ApplicationException {
        UsuariosPortalDaoImpl manager = new UsuariosPortalDaoImpl();
        UsuariosPortal usuario = manager.findUsuarioPortalById(idPortal);
        return (usuario);
    }
    
    
    public UsuariosPortal findUsuarioPortalByCedula(String cedula) throws ApplicationException {
        UsuariosPortalDaoImpl manager = new UsuariosPortalDaoImpl();
        UsuariosPortal usuario = manager.findUsuarioPortalByCedula(cedula);
        return (usuario);
    }
    
    
    public UsuariosPortal findUsuarioPortalByUsuario(String usuario) throws ApplicationException {
        UsuariosPortalDaoImpl manager = new UsuariosPortalDaoImpl();
        UsuariosPortal result = manager.findUsuarioPortalByUsuario(usuario);
        return (result);
    }
    
}
