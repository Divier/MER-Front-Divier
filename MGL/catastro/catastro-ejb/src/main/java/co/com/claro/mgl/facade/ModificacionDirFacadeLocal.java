/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ModificacionDir;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public interface ModificacionDirFacadeLocal {
    
    List<ModificacionDir> findByIdSolicitud(BigDecimal solicitud) throws ApplicationException;
    List<ModificacionDir> findHhppConflicto(BigDecimal solicitud) throws ApplicationException;
    ModificacionDir create(ModificacionDir modDir,String usuario, int perfil)throws ApplicationException;
}
