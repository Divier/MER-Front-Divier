/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author ADMIN
 */
public class CmtEstablecimientoMglDaoImpl extends GenericDaoImpl<CmtEstablecimientoCmMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtEstablecimientoMglDaoImpl.class);

    public CmtEstablecimientoCmMgl finById(BigDecimal id) {
        try {
            CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl;
            Query query = entityManager.createNamedQuery("CmtEstablecimientoMglDaoImpl.finById");
            query.setParameter("establesimientoId", id);
            cmtEstablecimientoCmMgl = (CmtEstablecimientoCmMgl) query.getSingleResult();
            return cmtEstablecimientoCmMgl;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    public List<CmtEstablecimientoCmMgl> finBySubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl ) {
        try {        
        List<CmtEstablecimientoCmMgl> list;
            Query query = entityManager.createNamedQuery("CmtEstablecimientosMglDaoImpl.finBySubEdificio");
            query.setParameter("subEdificioObj", cmtSubEdificioMgl);
            list = (List<CmtEstablecimientoCmMgl> ) query.getResultList();
            return list;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return new ArrayList<CmtEstablecimientoCmMgl>();
        }            
    }
    
    public List<AuditoriaDto> construirAuditoria(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtEstablecimientoCmMgl, CmtEstablecimientoCmAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtEstablecimientoCmMgl, CmtEstablecimientoCmAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtEstablecimientoCmMgl);
        return listAuditoriaDto;
    }    
}
