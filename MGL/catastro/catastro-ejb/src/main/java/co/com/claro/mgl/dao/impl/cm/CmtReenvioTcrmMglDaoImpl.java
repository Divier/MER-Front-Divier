/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.jpa.entities.cm.CmtReenvioTcrmMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author valbuenayf
 */
public class CmtReenvioTcrmMglDaoImpl extends GenericDaoImpl<CmtReenvioTcrmMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtReenvioTcrmMglDaoImpl.class);

    /**
     * metodo para listar todos los mensajes pendientes para enviar TCRM
     *
     * @return
     */
    public List<CmtReenvioTcrmMgl> findAllCmtReenvioTcrmMgl() {

        List<CmtReenvioTcrmMgl> resultList = null;
        try {
            Query query = entityManager.createNamedQuery("CmtReenvioTcrmMgl.findAll");
            resultList = (List<CmtReenvioTcrmMgl>) query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en findAllCmtReenvioTcrmMgl de CmtReenvioTcrmMglDaoImpl" + e);
        }

        return resultList;
    }

    /**
     * metodo para crear los mensajes pendientes para enviar TCRM
     *
     * @param cmtReenvioTcrmMgl
     * @return
     */
    public CmtReenvioTcrmMgl crearCmtReenvioTcrmMgl(CmtReenvioTcrmMgl cmtReenvioTcrmMgl) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cmtReenvioTcrmMgl);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            LOGGER.error("Error en crearCmtReenvioTcrmMgl de CmtReenvioTcrmMglDaoImpl" + e);
            return null;
        }
        return cmtReenvioTcrmMgl;
    }
}
