/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.Query;


/**
 *
 * @author bocanegravm
 */
public class CmtExtendidaTecnologiaMglDaoImpl extends GenericDaoImpl<CmtExtendidaTecnologiaMgl> {

    
     private static final Logger LOGGER = LogManager.getLogger(CmtExtendidaTecnologiaMglDaoImpl.class);
    
    /**
     * Busca el complemento de una tecnologia por la basica de la tecnologia
     *
     * @author Victor Bocanegra
     * @param cmtBasicaMgl 
     * @return CmtExtendidaTecnologiaMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtExtendidaTecnologiaMgl findBytipoTecnologiaObj(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        try {
            CmtExtendidaTecnologiaMgl resultList;
            Query query = entityManager.createNamedQuery("CmtExtendidaTecnologiaMgl.findBytipoTecnologiaObj");
            query.setParameter("tipoTecnologiaObj", cmtBasicaMgl);
            query.setParameter("estadoRegistro", 1);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (CmtExtendidaTecnologiaMgl) query.getSingleResult();
            return resultList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            CmtExtendidaTecnologiaMgl cmtExtendidaTecnologiaMgl = new CmtExtendidaTecnologiaMgl();
            return  cmtExtendidaTecnologiaMgl;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
}
