/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.ModificacionDirDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ModificacionDir;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public class ModificacionDirManager {
    ModificacionDirDaoImpl modificacionDirDaoImpl = new ModificacionDirDaoImpl();
    public List<ModificacionDir> findByIdSolicitud(BigDecimal solicitud) throws ApplicationException {
        List<ModificacionDir> resultList;       
        resultList = modificacionDirDaoImpl.findByIdSolicitud(solicitud);
        return resultList;

    }
    
       public List<ModificacionDir> findHhppConflicto(BigDecimal solicitud) throws ApplicationException {
        List<ModificacionDir> resultList;       
        resultList = modificacionDirDaoImpl.findHhppConflicto(solicitud);
        return resultList;

    }
    
    public ModificacionDir create(ModificacionDir modDir, String usuario, int perfil) throws ApplicationException {
        return modificacionDirDaoImpl.createCm(modDir,usuario,perfil);

    }
}
