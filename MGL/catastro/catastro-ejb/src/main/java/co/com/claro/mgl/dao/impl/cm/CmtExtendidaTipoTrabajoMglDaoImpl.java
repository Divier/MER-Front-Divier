/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaExtendidaTipoTrabajoDtos;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTipoTrabajoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class CmtExtendidaTipoTrabajoMglDaoImpl extends GenericDaoImpl<CmtExtendidaTipoTrabajoMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtExtendidaTipoTrabajoMglDaoImpl.class);

     final int MILISEGUNDOS_DIA = 86400000;
     /**
     * Busca lista de complementos de un tipo de trabajo
     *
     * @author Victor Bocanegra
     * @param filtro para la consulta
     * @param paginaSelected
     * @param maxResults
     * @param cmtBasicaMgl
     * @return List<CmtExtendidaTipoTrabajoMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    public List<CmtExtendidaTipoTrabajoMgl> findBytipoTrabajoObj(FiltroConsultaExtendidaTipoTrabajoDtos filtro,
            int paginaSelected, int maxResults, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        try {
            List<CmtExtendidaTipoTrabajoMgl> resultList;

            String queryStr = "SELECT c FROM CmtExtendidaTipoTrabajoMgl c WHERE c.tipoTrabajoObj = :tipoTrabajoObj "
                    + " AND c.estadoRegistro = 1 ";

            if (filtro.getCodigoLocationSeleccionado() != null && !filtro.getCodigoLocationSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.locationCodigo) LIKE :locationCodigo ";
            }

            if (filtro.getComunidaSeleccionada() != null && !filtro.getComunidaSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.comunidadRrObj.nombreComunidad) LIKE :comunidad ";
            }
            
             if (filtro.getCodigoComunidad() != null && !filtro.getCodigoComunidad().isEmpty()) {
                queryStr += " AND UPPER(c.comunidadRrObj.codigoRr) LIKE :codigoRR ";
            }

            if (filtro.getFechaCreacionSeleccionada() != null) {
                queryStr += " AND c.fechaCreacion BETWEEN :fechaCreacionInicial AND :fechaCreacionFinal ";
            }

            if (filtro.getTecnicoAntSeleccionada() != null && !filtro.getTecnicoAntSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.tecnicoAnticipado) = :tecnicoAnticipado ";
            }
            
             if (filtro.getAgendaInmediataSeleccionada()!= null && !filtro.getAgendaInmediataSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.agendaInmediata) = :agendaInmediata ";
            }

            queryStr += " ORDER BY c.comunidadRrObj.nombreComunidad ASC ";

            Query query = entityManager.createQuery(queryStr);
            query.setFirstResult(paginaSelected);
            query.setMaxResults(maxResults);

            if (filtro.getCodigoLocationSeleccionado() != null && !filtro.getCodigoLocationSeleccionado().isEmpty()) {
                query.setParameter("locationCodigo", "%" + filtro.getCodigoLocationSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getComunidaSeleccionada() != null && !filtro.getComunidaSeleccionada().isEmpty()) {
                query.setParameter("comunidad", "%" + filtro.getComunidaSeleccionada().trim().toUpperCase() + "%");
            }
            
            if (filtro.getCodigoComunidad() != null && !filtro.getCodigoComunidad().isEmpty()) {
                query.setParameter("codigoRR", "%" + filtro.getCodigoComunidad().trim().toUpperCase() + "%");
            }

            if (filtro.getFechaCreacionSeleccionada() != null) {
                query.setParameter("fechaCreacionInicial", filtro.getFechaCreacionSeleccionada());

                long fechaEnMilisegundos = filtro.getFechaCreacionSeleccionada().getTime() + MILISEGUNDOS_DIA - 1;
                query.setParameter("fechaCreacionFinal", new Date(fechaEnMilisegundos));
            }

            if (filtro.getTecnicoAntSeleccionada() != null && !filtro.getTecnicoAntSeleccionada().isEmpty()) {
                query.setParameter("tecnicoAnticipado",  filtro.getTecnicoAntSeleccionada().trim().toUpperCase());
            }
            
            if (filtro.getAgendaInmediataSeleccionada() != null && !filtro.getAgendaInmediataSeleccionada().isEmpty()) {
                query.setParameter("agendaInmediata",  filtro.getAgendaInmediataSeleccionada().trim().toUpperCase());
            }

            query.setParameter("tipoTrabajoObj", cmtBasicaMgl);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = query.getResultList();
            return resultList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    /**
     * Cuenta lista de complementos de un tipo de trabajo
     *
     * @author Victor Bocanegra
     * @param filtro para la consulta
     * @param cmtBasicaMgl
     * @return long
     * @throws ApplicationException
     */
    public long findExtendidasSearchContar(FiltroConsultaExtendidaTipoTrabajoDtos filtro,
            CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {
        try {
            long resultList;
            String queryStr = "SELECT COUNT(DISTINCT c) FROM  CmtExtendidaTipoTrabajoMgl c  "
                    + " WHERE c.tipoTrabajoObj = :tipoTrabajoObj AND c.estadoRegistro = 1 ";

            if (filtro.getCodigoLocationSeleccionado() != null && !filtro.getCodigoLocationSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.locationCodigo) LIKE :locationCodigo ";
            }

            if (filtro.getComunidaSeleccionada() != null && !filtro.getComunidaSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.comunidadRrObj.nombreComunidad) LIKE :comunidad ";
            }
            
            if (filtro.getCodigoComunidad() != null && !filtro.getCodigoComunidad().isEmpty()) {
                queryStr += " AND UPPER(c.comunidadRrObj.codigoRr) LIKE :codigoRR ";
            }

            if (filtro.getFechaCreacionSeleccionada() != null) {
                queryStr += " AND c.fechaCreacion BETWEEN :fechaCreacionInicial AND :fechaCreacionFinal ";
            }

            if (filtro.getTecnicoAntSeleccionada() != null && !filtro.getTecnicoAntSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.tecnicoAnticipado) LIKE :tecnicoAnticipado ";
            }

            queryStr += " ORDER BY c.comunidadRrObj.nombreComunidad ASC ";

            Query query = entityManager.createQuery(queryStr);

            if (filtro.getCodigoLocationSeleccionado() != null && !filtro.getCodigoLocationSeleccionado().isEmpty()) {
                query.setParameter("locationCodigo", "%" + filtro.getCodigoLocationSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getComunidaSeleccionada() != null && !filtro.getComunidaSeleccionada().isEmpty()) {
                query.setParameter("comunidad", "%" + filtro.getComunidaSeleccionada().trim().toUpperCase() + "%");
            }
            
            if (filtro.getCodigoComunidad() != null && !filtro.getCodigoComunidad().isEmpty()) {
                 query.setParameter("codigoRR", "%" + filtro.getCodigoComunidad().trim().toUpperCase() + "%");
            }

            if (filtro.getFechaCreacionSeleccionada() != null) {
                query.setParameter("fechaCreacionInicial", filtro.getFechaCreacionSeleccionada());

                long fechaEnMilisegundos = filtro.getFechaCreacionSeleccionada().getTime() + MILISEGUNDOS_DIA - 1;
                query.setParameter("fechaCreacionFinal", new Date(fechaEnMilisegundos));
            }

            if (filtro.getTecnicoAntSeleccionada() != null && !filtro.getTecnicoAntSeleccionada().isEmpty()) {
                query.setParameter("tecnicoAnticipado", "%" + filtro.getTecnicoAntSeleccionada().trim().toUpperCase() + "%");
            }

            query.setParameter("tipoTrabajoObj", cmtBasicaMgl);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = ((Long) query.getSingleResult()).intValue();
            return resultList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta. " + ex.getMessage() + "");
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos. Intente más tarde. " + ex.getMessage() + "");
        }
    }
    
         /**
     * Busca un  complemento de un tipo de trabajo asociado a una comunidad
     *
     * @author Victor Bocanegra
     * @param cmtComunidadRr
     * @param cmtBasicaMgl
     * @return List<CmtExtendidaTipoTrabajoMgl>  encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtExtendidaTipoTrabajoMgl> findBytipoTrabajoObjAndCom
            (CmtComunidadRr cmtComunidadRr, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        
         List<CmtExtendidaTipoTrabajoMgl> result = null;
         
        try {  
            Query query = entityManager.createNamedQuery("CmtExtendidaTipoTrabajoMgl.findBytipoTrabajoObjAndCom");
            query.setParameter("tipoTrabajoObj", cmtBasicaMgl);
            query.setParameter("comunidadRrObj", cmtComunidadRr);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result=   query.getResultList();
            return result;
        } catch (NoResultException ex) {
              String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
              LOGGER.error(msg);
              return result;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return result;
        }
    }
            
    /**
     * Cuenta total extendidas tipo trabajo
     *
     * @author Victor Bocanegra
     * @return long
     * @throws ApplicationException
     */
    public long findExtendidasTrabajoCount() throws ApplicationException {
        try {
            long resultList;
            String queryStr = "SELECT COUNT(DISTINCT c) FROM  CmtExtendidaTipoTrabajoMgl c  "
                    + " WHERE c.estadoRegistro = 1 ";

            Query query = entityManager.createQuery(queryStr);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = ((Long) query.getSingleResult()).intValue();
            return resultList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta. " + ex.getMessage() + "");
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos. Intente más tarde. " + ex.getMessage() + "");
        }
    }
    
    /**
     * Busca lista de extendidas tipo trabajo
     *
     * @author Victor Bocanegra
     * @param paginaSelected
     * @param maxResults
     * @return List<CmtExtendidaTipoTrabajoMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    public List<CmtExtendidaTipoTrabajoMgl> findExtendidasTipoTrabajo(
            int paginaSelected, int maxResults)
            throws ApplicationException {
        try {
            List<CmtExtendidaTipoTrabajoMgl> resultList;

            String queryStr = " SELECT c FROM CmtExtendidaTipoTrabajoMgl c "
                    + " WHERE  c.estadoRegistro = 1 ";

            Query query = entityManager.createQuery(queryStr);
            query.setFirstResult(paginaSelected);
            query.setMaxResults(maxResults);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = query.getResultList();
            return resultList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
}
