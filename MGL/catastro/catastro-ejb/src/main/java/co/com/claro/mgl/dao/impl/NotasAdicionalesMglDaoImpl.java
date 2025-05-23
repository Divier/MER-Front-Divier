/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class NotasAdicionalesMglDaoImpl extends GenericDaoImpl<NotasAdicionalesMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(NotasAdicionalesMglDaoImpl.class);

    public List<NotasAdicionalesMgl> findAll() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("NotasAdicionalesMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (List<NotasAdicionalesMgl>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<HhppMgl> findHhppBySelect(BigDecimal geo, BigDecimal blackLis, String tipoUnidad, String estadoUnidad, String calle, String placa, String apartamento, BigDecimal hhppId, NodoMgl nodo, Date fechaCreacion) throws ApplicationException {

       
        try {
            List<HhppMgl> resultList;

            String consulta = "SELECT h FROM HhppMgl h WHERE  ";

            if (calle != null) {
                consulta += "  h.HhpCalle = :HhpCalle ";
            }
            if (placa != null) {
                if (calle != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.HhpPlaca = :HhpPlaca ";
            }

            if (apartamento != null) {
                if (calle != null || placa != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.HhpApart = :HhpApart ";
            }
           
            if (fechaCreacion != null) {
                if (calle != null || placa != null || apartamento != null) {
                    consulta += "  AND ";
                } 
                consulta += " ( h.hhpFechaCreacion BETWEEN :hhpFechaCreacionI and  :hhpFechaCreacionF )  ";
            }

            if (hhppId != null) {
                if (calle != null || placa != null || apartamento != null || fechaCreacion != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.hhpId = :hhpId ";
            }

            if (nodo != null) {
                if (calle != null || placa != null || apartamento != null || fechaCreacion != null || hhppId != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.nodId.nodId = :nodId ";
            }

            Query query = entityManager.createQuery(consulta);
           
            if (calle != null) {
                query.setParameter("HhpCalle", calle);
            }
            if (placa != null) {
                query.setParameter("HhpPlaca", placa);

            }

            if (apartamento != null) {
                query.setParameter("HhpApart", apartamento);

            }

            if (fechaCreacion != null) {
                query.setParameter("hhpFechaCreacionI", fechaCreacion);
                query.setParameter("hhpFechaCreacionF", new Date(fechaCreacion.getTime()+(1000*60*60*24)));

            }

            if (hhppId != null) {
                query.setParameter("hhpId", hhppId);

            }

            if (nodo != null) {
                query.setParameter("nodId", nodo.getNodId());

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<HhppMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            List<HhppMgl> resultList = new ArrayList<HhppMgl>();
            return resultList;
        }
    }

    public List<HhppMgl> findHhppByInHhppListId(List<BigDecimal> hhppList) {
        try {
            List<HhppMgl> resultList;
            Query query = entityManager.createQuery("SELECT h FROM HhppMgl h WHERE h.hhpId in :hhpIdList");
            query.setParameter("hhpIdList", hhppList);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<HhppMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            List<HhppMgl> resultList = new ArrayList<HhppMgl>();
            return resultList;
        }
    }
    
    
        public List<HhppMgl> findHhppByGeoId(BigDecimal gpoId) {
        try {
            List<HhppMgl> resultList;
            Query query = entityManager.createQuery("SELECT h FROM HhppMgl h WHERE h.nodId.gpoId = :nodGpoId ");

            query.setParameter("nodGpoId", gpoId);
            resultList = (List<HhppMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            List<HhppMgl> resultList = new ArrayList<HhppMgl>();
            return resultList;
        }
    }
        
    /**
     * yfvalbuena metodo para listar las notas dicionales de un hhpp
     *
     * @param hhpId
     * @return
     * @throws ApplicationException
     */
    public List<NotasAdicionalesMgl> findNotasAdicionalesIdHhpp(BigDecimal hhpId) throws ApplicationException {
        List<NotasAdicionalesMgl> respuesta;
        try {
            Query query = entityManager.createNamedQuery("NotasAdicionalesMgl.findNotasAdicionalesIdHhpp");
            query.setParameter("hhppId", hhpId.toString());
            respuesta = (List<NotasAdicionalesMgl>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
        return respuesta;
    }
    
}
