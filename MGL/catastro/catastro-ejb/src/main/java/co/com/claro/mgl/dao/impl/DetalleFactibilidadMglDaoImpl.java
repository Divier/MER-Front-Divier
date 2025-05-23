/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DetalleFactibilidadMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class DetalleFactibilidadMglDaoImpl extends GenericDaoImpl<DetalleFactibilidadMgl> {

    private static final Logger LOGGER = LogManager.getLogger(DetalleFactibilidadMglDaoImpl.class);

    /**
     * Encuentra la lista de detalle de la factibilidad en el repositorio
     *
     * @author Victor Bocanegra
     * @param idFactibilidad
     * @return List<DetalleFactibilidadMgl>
     * @throws ApplicationException
     */
    public List<DetalleFactibilidadMgl> findListDetalleFactibilidad(BigDecimal idFactibilidad)
            throws ApplicationException {

        try {
            String queryTipo = "SELECT d FROM DetalleFactibilidadMgl d "
                    + " WHERE d.factibilidadMglObj.factibilidadId = :factibilidadId  ";

            Query q = entityManager.createQuery(queryTipo);

            if (idFactibilidad != null) {

            }
            if (idFactibilidad != null && !idFactibilidad.equals(BigDecimal.ZERO)) {
                q.setParameter("factibilidadId", idFactibilidad);
            }
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();

        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * Encuentra un detalle de factibilidad en el repositorio por sla de
     * ejecucion
     *
     * @author Victor Bocanegra
     * @param slaEjecucionMgl
     * @return DetalleFactibilidadMgl
     * @throws ApplicationException
     */
    public List<DetalleFactibilidadMgl>  findDetalleBySlaEjecucion(SlaEjecucionMgl slaEjecucionMgl)
            throws ApplicationException {

        try {
            String queryTipo = "SELECT d FROM DetalleFactibilidadMgl d "
                    + " WHERE d.slaEjecucionMglObj = :slaEjecucionMglObj  ";

            Query q = entityManager.createQuery(queryTipo);

            if (slaEjecucionMgl != null) {
                q.setParameter("slaEjecucionMglObj", slaEjecucionMgl);
            }
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();

        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
     /**
     * Obtener lista de factibilidad por id y tecnologia
     * ejecucion
     *
     * @author cardenaslb
     * @param solicitud
     * @return DetalleFactibilidadMgl
     * @throws ApplicationException
     */
    public List<DetalleFactibilidadMgl> findListByIdFactTec(Solicitud solicitud)
            throws ApplicationException {

        try {
            String queryTipo = "SELECT d FROM DetalleFactibilidadMgl d "
                    + " WHERE d.factibilidadMglObj.factibilidadId = :factibilidadId "
                    + " AND d.nombreTecnologia = :tecnologia ";

            Query q = entityManager.createQuery(queryTipo);


            if (solicitud != null && solicitud.getIdFactibilidad() != null) {
                q.setParameter("factibilidadId", solicitud.getIdFactibilidad());
            }
            if (solicitud != null && solicitud.getTecnologiaId().getBasicaId() != null) {
                q.setParameter("tecnologia", solicitud.getTecnologiaId().getCodigoBasica());
            }
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();

        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    
    public List<DetalleFactibilidadMgl> findListByIdFactFecha(BigDecimal idFactibilidad, String fecha, String usuario)
            throws ApplicationException {
        try {
            String queryTipo = "SELECT d FROM DetalleFactibilidadMgl d  where d.factibilidadMglObj.factibilidadId in ("
                    + " SELECT f.factibilidadId FROM FactibilidadMgl f  WHERE F.usuario = :usuario   "
                    + " AND func('trunc', f.fechaCreacion) = func('to_date', :fecha) "
                    + " and f.factibilidadId = :idFactibilidad )";

            Query q = entityManager.createQuery(queryTipo);

            if (usuario != null && !usuario.isEmpty()) {
                q.setParameter("usuario", usuario);
            }
            if (fecha != null && !fecha.isEmpty()) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = df.parse(fecha);
                java.sql.Date fInicio = new java.sql.Date(date.getTime());
                q.setParameter("fecha", fInicio);
            }
            if (idFactibilidad != null) {
                q.setParameter("idFactibilidad", idFactibilidad);
            }

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (List<DetalleFactibilidadMgl>) q.getResultList();

        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
    
            }
