/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtTipoBasicaExtMglDaoImpl extends GenericDaoImpl<CmtTipoBasicaExtMgl>{
    
    private static final Logger LOGGER = LogManager.getLogger(CmtTiempoSolicitudMglDaoImpl.class);
    
        /**
     * Buscar lista de campos en tabla tipo  basica extendida
     *
     * @author cardenaslb.
     * @version 1.0 revision 03/10/2017.
     * @param cmtTipoBasicaMgl
     * @return Los datos de la entidad basica.
     */
    public List<CmtTipoBasicaExtMgl> findCamposByTipoBasica(CmtTipoBasicaMgl cmtTipoBasicaMgl) {
        if (cmtTipoBasicaMgl == null) {
            return null;
        }
       
        try {

            Query query = entityManager.createNamedQuery("CmtTipoBasicaExtMgl.findByIdTipoBasica");
            query.setParameter("idTipoBasica", cmtTipoBasicaMgl);
            List<CmtTipoBasicaExtMgl> list = query.getResultList();
            return list;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }
    
            /**
     * Buscar lista de campos en tabla tipo  basica extendida
     *
     * @author cardenaslb.
     * @version 1.0 revision 03/11/2017.
     * @return Los datos de la entidad basica.
     */
    public List<CmtTipoBasicaExtMgl> findAll() {
    
        try {

            Query query = entityManager.createNamedQuery("CmtTipoBasicaExtMgl.findAll");
            List<CmtTipoBasicaExtMgl> list = query.getResultList();
            return list;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }
    
    
    
    
    
}
