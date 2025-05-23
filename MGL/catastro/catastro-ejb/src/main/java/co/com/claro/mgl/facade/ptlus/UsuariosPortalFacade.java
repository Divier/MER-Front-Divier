package co.com.claro.mgl.facade.ptlus;

import co.com.claro.mgl.businessmanager.ptlus.UsuariosPortalManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ptlus.UsuariosPortal;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;


/**
 * Fachada para el manejo de Usuarios del Portal.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@Stateless
public class UsuariosPortalFacade implements UsuariosPortalFacadeLocal {

    @Override
    public UsuariosPortal findUsuarioPortalById(BigDecimal idPortal) throws ApplicationException {
        UsuariosPortalManager manager = new UsuariosPortalManager();
        UsuariosPortal usuario = manager.findUsuarioPortalById(idPortal);
        return (usuario);
    }
    
    
    @Override
    public UsuariosPortal findUsuarioPortalByCedula(String cedula) throws ApplicationException {
        UsuariosPortalManager manager = new UsuariosPortalManager();
        UsuariosPortal usuario = manager.findUsuarioPortalByCedula(cedula);
        return (usuario);
    }

    @Override
    public UsuariosPortal findUsuarioPortalByUsuario(String usuario) throws ApplicationException {
        UsuariosPortalManager manager = new UsuariosPortalManager();
        UsuariosPortal result = manager.findUsuarioPortalByUsuario(usuario);
        return (result);
    }

    @Override
    public List<UsuariosPortal> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public UsuariosPortal create(UsuariosPortal t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuariosPortal update(UsuariosPortal t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(UsuariosPortal t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public UsuariosPortal findById(UsuariosPortal sqlData) throws ApplicationException {
        UsuariosPortal usuario = null;
        if (sqlData != null) {
            usuario = this.findUsuarioPortalById(sqlData.getIdPortal());
        }
        return (usuario);
    }
    
}
