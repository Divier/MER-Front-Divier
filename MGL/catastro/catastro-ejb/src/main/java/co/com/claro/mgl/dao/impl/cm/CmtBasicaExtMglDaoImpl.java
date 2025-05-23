/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtBasicaExtMglDaoImpl extends GenericDaoImpl<CmtBasicaExtMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtBasicaExtMglDaoImpl.class);

    public List<CmtBasicaExtMgl> findByBasicaId(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        List<CmtBasicaExtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtBasicaExtMgl.findByBasicaId");
        query.setParameter("basicaId", cmtBasicaMgl.getBasicaId());
        resultList = (List<CmtBasicaExtMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    
    /**
     * Consulta lista de extendidas de una basica
     * por el id del tipo basica ext
     * @author Victor Bocanegra
     * @param tipoBasicaExtMgl 
     * @return List<CmtBasicaExtMgl>
     * @throws ApplicationException
     */
    public List<CmtBasicaExtMgl> findByTipoBasicaExt(CmtTipoBasicaExtMgl tipoBasicaExtMgl)
            throws ApplicationException {
        List<CmtBasicaExtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtBasicaExtMgl.findByIdTipoBasicaExt");
        query.setParameter("idTipoBasicaExt", tipoBasicaExtMgl);
        resultList = (List<CmtBasicaExtMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
    /**
     * Consulta lista de extendidas de una basica por el id del tipo basica ext
     * y por la basica
     *
     * @author Victor Bocanegra
     * @param tipoBasicaExtMgl
     * @param basicaMgl
     * @return List<CmtBasicaExtMgl>
     * @throws ApplicationException
     */
    public List<CmtBasicaExtMgl> findByTipoBasicaExtByBasica(List<CmtTipoBasicaExtMgl> lstTipoBasicaExtMgl,
            CmtBasicaMgl basicaMgl)
            throws ApplicationException {

        List<CmtBasicaExtMgl> resultList;

        try {

            String consulta = "SELECT c FROM CmtBasicaExtMgl c WHERE c.idBasicaObj= :basicaId   AND c.estadoRegistro = 1";
            if (lstTipoBasicaExtMgl != null && !lstTipoBasicaExtMgl.isEmpty()) {
                consulta += " and c.idTipoBasicaExt IN :tipoBasExtId";
            }

            Query query = entityManager.createQuery(consulta);
            
    
            if (lstTipoBasicaExtMgl != null && !lstTipoBasicaExtMgl.isEmpty()) {
                query.setParameter("tipoBasExtId", lstTipoBasicaExtMgl);
            }

            if (basicaMgl != null) {
                query.setParameter("basicaId", basicaMgl);
            }

            resultList = query.getResultList();
            getEntityManager().clear();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las basicas extendidas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
