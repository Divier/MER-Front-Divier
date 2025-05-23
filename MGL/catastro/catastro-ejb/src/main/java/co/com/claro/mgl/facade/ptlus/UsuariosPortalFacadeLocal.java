package co.com.claro.mgl.facade.ptlus;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.ptlus.UsuariosPortal;
import java.math.BigDecimal;


/**
 * Interfaz local para el manejo de Usuarios del Portal.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public interface UsuariosPortalFacadeLocal extends BaseFacadeLocal<UsuariosPortal>  {
    
    public UsuariosPortal findUsuarioPortalById(BigDecimal idPortal) throws ApplicationException;
    
    public UsuariosPortal findUsuarioPortalByCedula(String cedula) throws ApplicationException;
    
    public UsuariosPortal findUsuarioPortalByUsuario(String usuario) throws ApplicationException;
    
}
