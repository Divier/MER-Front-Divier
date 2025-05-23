
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.ModificacionDirManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ModificacionDir;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class ModificacionDirFacade implements ModificacionDirFacadeLocal{

    private final ModificacionDirManager modificacionDirManager;

    public ModificacionDirFacade() {
        this.modificacionDirManager =  new ModificacionDirManager();
    }
    
    @Override
    public List<ModificacionDir> findByIdSolicitud(BigDecimal solicitud) throws ApplicationException {      
        return modificacionDirManager.findByIdSolicitud(solicitud);
    }
    
    @Override
     public List<ModificacionDir> findHhppConflicto(BigDecimal solicitud) throws ApplicationException {      
        return modificacionDirManager.findHhppConflicto(solicitud);
    }
    
    @Override
    public ModificacionDir create(ModificacionDir modDir, String usuario, int perfil) throws ApplicationException {
         return modificacionDirManager.create(modDir,usuario,perfil);
        
    }
    
}
