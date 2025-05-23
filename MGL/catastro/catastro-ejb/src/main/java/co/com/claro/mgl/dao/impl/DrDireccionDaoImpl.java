/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Parzifal de León
 */
public class DrDireccionDaoImpl extends GenericDaoImpl<DrDireccion> {
    private static final Logger LOGGER = LogManager.getLogger(DrDireccionDaoImpl.class);
    

    public DrDireccion findByIdSolicitud(BigDecimal id) throws ApplicationException {
        try {
            DrDireccion result = null;
            try {
                Query query = entityManager.createQuery("SELECT c FROM DrDireccion c"
                        + " WHERE c.id = :id");
                query.setParameter("id", id);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                result = (DrDireccion) query.getSingleResult();
                getEntityManager().clear();
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(ex.getMessage());
            }
            return result;
            
        }catch(ApplicationException ex){
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
           throw new ApplicationException(msg,ex);  
        }
    }

    /**
     * Busca los datos de la direccion apartir del id de la solicutud
     *
     * @author gilaj
     * @param idSolicitudCm
     * @return DrDireccion
     * @throws ApplicationException
     */
    public DrDireccion findSolicitudCm(BigDecimal idSolicitudCm)
            throws ApplicationException {
        DrDireccion result = null;
        try{
            Query query = entityManager.createQuery("SELECT c FROM DrDireccion c"
                    + " WHERE c.idSolicitud = :idSolicitud");
            query.setParameter("idSolicitud", idSolicitudCm);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (DrDireccion) query.getSingleResult();
            getEntityManager().clear();
        }catch(Exception ex){
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
           throw new ApplicationException(msg,ex);  
        }
        return result;
    }

    public List<DrDireccion> findAll() throws ApplicationException {
        
        List<DrDireccion> resulList;
        
        try {

            Query query = entityManager.createNamedQuery("DrDireccionMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resulList = query.getResultList();
            getEntityManager().clear();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
           throw new ApplicationException(msg,e);  
        }
    }

    public DrDireccion findByRequest(String idRequest) throws ApplicationException {
        try {
            DrDireccion result;
            Query query = entityManager.createQuery("select d from DrDireccionMgl d where d.IdSolicitud = :idSolicitud");
            query.setParameter("idSolicitud", idRequest);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (DrDireccion) query.getSingleResult();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            DrDireccion resultList = new DrDireccion();
            return resultList;
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
                query.setParameter("hhpFechaCreacionF", new Date(fechaCreacion.getTime() + (1000 * 60 * 60 * 24)));

            }

            if (hhppId != null) {
                query.setParameter("hhpId", hhppId);

            }

            if (nodo != null) {
                query.setParameter("nodId", nodo.getNodId());

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<HhppMgl>) query.getResultList();
            getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            List<HhppMgl> resultList = new ArrayList<>();
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
            getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            List<HhppMgl> resultList = new ArrayList<>();
            return resultList;
        }
    }

    public List<HhppMgl> findHhppByGeoId(BigDecimal gpoId) {
        try {
            List<HhppMgl> resultList;
            Query query = entityManager.createQuery("SELECT h FROM HhppMgl h WHERE h.nodId.gpoId = :nodGpoId ");
            query.setParameter("nodGpoId", gpoId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<HhppMgl>) query.getResultList();
            getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            List<HhppMgl> resultList = new ArrayList<>();
            return resultList;
        }
    }
    
    public List<Solicitud> findByDrDireccion(DrDireccion drDireccion, String centroPoblado)
            throws ApplicationException {
        List<Solicitud> resultList;
        try {
            String querySql = "SELECT s FROM Solicitud s, DrDireccion a ";
            querySql += "WHERE s.idSolicitud=a.idSolicitud  AND  s.ciudad= :comunidad ";

            if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().trim().isEmpty()) {
                querySql += "AND UPPER(a.idTipoDireccion) = UPPER(:idTipoDireccion) ";
            }
            if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
                querySql += "AND  UPPER(a.barrio)   = UPPER(:barrio) ";
            }
            if (drDireccion.getIdTipoDireccion().equals("CK")) {
                if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.tipoViaPrincipal)    = UPPER(:tipoViaPrincipal) ";
                }
                if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.numViaPrincipal)    = UPPER(:numViaPrincipal) ";
                }
                if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.ltViaPrincipal)    = UPPER(:ltViaPrincipal) ";
                }
                if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.nlPostViaP)    = UPPER(:nlPostViaP) ";
                }
                if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.bisViaPrincipal)    = UPPER(:bisViaPrincipal) ";
                }
                if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cuadViaPrincipal)    = UPPER(:cuadViaPrincipal)  ";
                }
                if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.placaDireccion)    = UPPER(:placaDireccion) ";
                }
                if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.tipoViaGeneradora)    = UPPER(:tipoViaGeneradora) ";
                }
                if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.numViaGeneradora)    = UPPER(:numViaGeneradora) ";
                }
                if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.ltViaGeneradora)    = UPPER(:ltViaGeneradora) ";
                }
                if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.nlPostViaG)    = UPPER(:nlPostViaG) ";
                }
                if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.bisViaGeneradora)    = UPPER(:bisViaGeneradora) ";
                }
                if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cuadViaGeneradora)    = UPPER(:cuadViaGeneradora) ";
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("BM")
                    || drDireccion.getIdTipoDireccion().equals("IT")) {
                //Direccion Manzana-Casa
                if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzTipoNivel1)    = UPPER(:mzTipoNivel1) ";
                }
                if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzTipoNivel2)    = UPPER(:mzTipoNivel2) ";
                }
                if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzTipoNivel3)    = UPPER(:mzTipoNivel3)  ";
                }
                if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzTipoNivel4)    = UPPER(:mzTipoNivel4) ";
                }
                if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzTipoNivel5)    = UPPER(:mzTipoNivel5) ";
                }
                if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzValorNivel1)    = UPPER(:mzValorNivel1) ";
                }
                if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzValorNivel2)    = UPPER(:mzValorNivel2) ";
                }
                if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzValorNivel3)    = UPPER(:mzValorNivel3) ";
                }
                if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzValorNivel4)    = UPPER(:mzValorNivel4) ";
                }
                if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzValorNivel5)    = UPPER(:mzValorNivel5) ";
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("CK")
                    || drDireccion.getIdTipoDireccion().equals("BM")
                    || drDireccion.getIdTipoDireccion().equals("IT")) {
                //Complemeto o intraduciple
                if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpTipoNivel1)    = UPPER(:cpTipoNivel1) ";
                }
                if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpTipoNivel2)    = UPPER(:cpTipoNivel2) ";
                }
                if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpTipoNivel3)    = UPPER(:cpTipoNivel3) ";
                }
                if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpTipoNivel4)    = UPPER(:cpTipoNivel4) ";
                }
                if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpTipoNivel5)    = UPPER(:cpTipoNivel5) ";
                }
                if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpTipoNivel6)    = UPPER(:cpTipoNivel6) ";
                }

                if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpValorNivel1)    = UPPER(:cpValorNivel1) ";
                }
                if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpValorNivel2)    = UPPER(:cpValorNivel2) ";
                }
                if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpValorNivel3)    = UPPER(:cpValorNivel3) ";
                }
                if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpValorNivel4)    = UPPER(:cpValorNivel4) ";
                }
                if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpValorNivel5)    = UPPER(:cpValorNivel5) ";
                }
                if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.cpValorNivel6)    = UPPER(:cpValorNivel6) ";
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("IT")) {
                if (drDireccion.getMzTipoNivel6() != null && !drDireccion.getMzTipoNivel6().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzTipoNivel6)    = UPPER(:mzTipoNivel6) ";
                }
                if (drDireccion.getMzValorNivel6() != null && !drDireccion.getMzValorNivel6().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.mzValorNivel6)    = UPPER(:mzValorNivel6)  ";
                }
                if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.itTipoPlaca)    = UPPER(:itTipoPlaca) ";
                }
                if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
                    querySql += "AND  UPPER(a.itValorPlaca)    = UPPER(:itValorPlaca) ";
                }

            }

            querySql += "AND s.estado = 'PENDIENTE' ";

            Query query = entityManager.createQuery(querySql);

            query.setParameter("comunidad", centroPoblado);

            if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion", drDireccion.getIdTipoDireccion());
            }
            if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio", drDireccion.getBarrio());
            }
            if (drDireccion.getIdTipoDireccion().equals("CK")) {
                //Direccion Calle - Carrera
                if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
                    query.setParameter("tipoViaPrincipal", drDireccion.getTipoViaPrincipal());
                }
                if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
                    query.setParameter("numViaPrincipal", drDireccion.getNumViaPrincipal());
                }
                if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
                    query.setParameter("ltViaPrincipal", drDireccion.getLtViaPrincipal());
                }
                if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
                    query.setParameter("nlPostViaP", drDireccion.getNlPostViaP());
                }
                if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
                    query.setParameter("bisViaPrincipal", drDireccion.getBisViaPrincipal());
                }
                if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
                    query.setParameter("cuadViaPrincipal", drDireccion.getCuadViaPrincipal());
                }
                if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
                    query.setParameter("placaDireccion", drDireccion.getPlacaDireccion());
                }
                if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
                    query.setParameter("tipoViaGeneradora", drDireccion.getTipoViaGeneradora());
                }
                if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
                    query.setParameter("numViaGeneradora", drDireccion.getNumViaGeneradora());
                }
                if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
                    query.setParameter("ltViaGeneradora", drDireccion.getLtViaGeneradora());
                }
                if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
                    query.setParameter("nlPostViaG", drDireccion.getNlPostViaG());
                }
                if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
                    query.setParameter("bisViaGeneradora", drDireccion.getBisViaGeneradora());
                }
                if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
                    query.setParameter("cuadViaGeneradora", drDireccion.getCuadViaGeneradora());
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("BM")
                    || drDireccion.getIdTipoDireccion().equals("IT")) {
                //Direccion Manzana-Casa
                if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
                    query.setParameter("mzTipoNivel1", drDireccion.getMzTipoNivel1());
                }
                if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
                    query.setParameter("mzTipoNivel2", drDireccion.getMzTipoNivel2());
                }
                if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
                    query.setParameter("mzTipoNivel3", drDireccion.getMzTipoNivel3());
                }
                if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
                    query.setParameter("mzTipoNivel4", drDireccion.getMzTipoNivel4());
                }
                if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
                    query.setParameter("mzTipoNivel5", drDireccion.getMzTipoNivel5());
                }
                if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
                    query.setParameter("mzValorNivel1", drDireccion.getMzValorNivel1());
                }
                if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
                    query.setParameter("mzValorNivel2", drDireccion.getMzValorNivel2());
                }
                if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
                    query.setParameter("mzValorNivel3", drDireccion.getMzValorNivel3());
                }
                if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
                    query.setParameter("mzValorNivel4", drDireccion.getMzValorNivel4());
                }
                if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
                    query.setParameter("mzValorNivel5", drDireccion.getMzValorNivel5());
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("CK")
                    || drDireccion.getIdTipoDireccion().equals("BM")
                    || drDireccion.getIdTipoDireccion().equals("IT")) {
                //Complemeto o intraduciple
                if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
                    query.setParameter("cpTipoNivel1", drDireccion.getCpTipoNivel1());
                }
                if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
                    query.setParameter("cpTipoNivel2", drDireccion.getCpTipoNivel2());
                }
                if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
                    query.setParameter("cpTipoNivel3", drDireccion.getCpTipoNivel3());
                }
                if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
                    query.setParameter("cpTipoNivel4", drDireccion.getCpTipoNivel4());
                }
                if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
                    query.setParameter("cpTipoNivel5", drDireccion.getCpTipoNivel5());
                }
                if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
                    query.setParameter("cpTipoNivel6", drDireccion.getCpTipoNivel6());
                }

                if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
                    query.setParameter("cpValorNivel1", drDireccion.getCpValorNivel1());
                }
                if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
                    query.setParameter("cpValorNivel2", drDireccion.getCpValorNivel2());
                }
                if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
                    query.setParameter("cpValorNivel3", drDireccion.getCpValorNivel3());
                }
                if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
                    query.setParameter("cpValorNivel4", drDireccion.getCpValorNivel4());
                }
                if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
                    query.setParameter("cpValorNivel5", drDireccion.getCpValorNivel5());
                }
                if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
                    query.setParameter("cpValorNivel6", drDireccion.getCpValorNivel6());
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("IT")) {
                if (drDireccion.getMzTipoNivel6() != null && !drDireccion.getMzTipoNivel6().trim().isEmpty()) {
                    query.setParameter("mzTipoNivel6", drDireccion.getMzTipoNivel6());
                }
                if (drDireccion.getMzValorNivel6() != null && !drDireccion.getMzValorNivel6().trim().isEmpty()) {
                    query.setParameter("mzValorNivel6", drDireccion.getMzValorNivel6());
                }
                if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
                    query.setParameter("itTipoPlaca", drDireccion.getItTipoPlaca());
                }
                if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
                    query.setParameter("itValorPlaca", drDireccion.getItValorPlaca());
                }

            }


            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<Solicitud>) query.getResultList();
            getEntityManager().clear();
        } catch (NoResultException ne) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ne.getMessage();
            LOGGER.error(msg);
            resultList = new ArrayList<>();
        }
        if (resultList == null) {
            resultList = new ArrayList<>();
        }
        return resultList;
    }
    
        /**
     * Busca el DrDireccion de la casa 
     *
     * @author gilaj
     * @param idSolicitudCm
     * @return DrDireccion
     * @throws ApplicationException
     */
    public DrDireccion findDrDireccionByIdHhppVtMgl(BigDecimal idSolicitudCm)
            throws ApplicationException {
        DrDireccion result = null;
        try{
        Query query = entityManager.createQuery("SELECT c FROM DrDireccion c"
                + " WHERE c.idSolicitud = :idSolicitud");
        query.setParameter("idSolicitud", idSolicitudCm);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = (DrDireccion) query.getSingleResult();
        getEntityManager().clear();
        }catch(Exception ex){
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
           throw new ApplicationException(msg,ex);
        }
        return result;
    }
    

}
