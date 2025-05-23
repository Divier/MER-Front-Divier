/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAprobacionesEstadosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;


/**
 * <b>DAO de entidad</b><br />
 * Clase para implementar los acciones de la entidad {@link CmtAprobacionesEstadosMgl}
 *
 * @author Victor Bocanegra
 * @version 2017/09/20
 */
public class CmtAprobacionesEstadoMglDaoImpl extends GenericDaoImpl<CmtAprobacionesEstadosMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtAprobacionesEstadoMglDaoImpl.class);
    
     /**
     * Busca aprobaciones de
     * cambio de estado por id de ot  en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idOt
     * @param id  orden de trabajo
     * @return 
     * @throws ApplicationException
     */
    public CmtAprobacionesEstadosMgl findLstAproByOt(BigDecimal idOt) throws ApplicationException {

        try {
            String queryStr = "SELECT  a  FROM CmtAprobacionesEstadosMgl a "
                    + " WHERE a.estadoRegistro = :estadoRegistro  "
                    + " AND a.OtObj.idOt = :OtObj"
                    + " AND  a.detalleFlujoObj.detalleFlujoId  in  "
                    + "(SELECT c.detalleFlujoId FROM CmtDetalleFlujoMgl c "
                    + "WHERE c.identificadorInternoApp = '" + Constant.DETALLE_FLUJO_APRO_FINAN + "' "
                    + " AND c.estadoRegistro=1)  ";


            Query query = entityManager.createQuery(queryStr);
            query.setParameter("estadoRegistro", 1);
            query.setParameter("OtObj", idOt);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return (CmtAprobacionesEstadosMgl) query.getSingleResult();
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }

    }
    
     /**
     * Busca las Ordenes de Trabajo cerradas externamente y aprobadas
     * financieramente
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param gpoDepartamento
     * @param departamento division de las solicitudes
     * @param gpoCiudad
     * @param ciudad comunidad de las solicitudes
     * @param tipoOt tipo de orden de trabajo
     * @param estadoInternoBasicaId estado interno de la ot
     * @param tecnologia de orden de trabajo
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtAprobacionesEstadosMgl> findByOtCloseAndApruebaFinan(List<BigDecimal> estadosxflujoList, 
            BigDecimal gpoDepartamento, BigDecimal gpoCiudad, CmtTipoOtMgl tipoOt, 
            BigDecimal estadoInternoBasicaId, BigDecimal tecnologia) throws ApplicationException {
        
        try {           
              String queryStr = "SELECT  Distinct a  FROM CmtAprobacionesEstadosMgl a, "
                    + " CmtOrdenTrabajoMgl o, CmtTipoOtMgl t, "
                    + " CmtEstadoxFlujoMgl ef, CmtCuentaMatrizMgl cm   "
                    + " WHERE  a.OtObj.idOt = o.idOt  AND  t.idTipoOt = o.tipoOtObj.idTipoOt  "
                    + " AND ef.tipoFlujoOtObj.basicaId = t.tipoFlujoOt.basicaId  "
                    + " AND ef.basicaTecnologia.basicaId =o.basicaIdTecnologia.basicaId  "
                    + " AND cm.cuentaMatrizId = o.cmObj.cuentaMatrizId "   
                    + " AND  a.estadoRegistro = :estadoRegistro  "
                    + " AND  a.detalleFlujoObj.detalleFlujoId  in  "
                    + "(SELECT c.detalleFlujoId FROM CmtDetalleFlujoMgl c "
                    + " WHERE c.identificadorInternoApp = '"+Constant.DETALLE_FLUJO_APRO_FINAN+"' "
                    + " AND c.estadoRegistro=1)  ";

            if (gpoDepartamento != null && !gpoDepartamento.equals(BigDecimal.ZERO)) {
                queryStr += " AND cm.departamento.gpoId = :gpoDepartamento ";
            }
            if (gpoCiudad != null && !gpoCiudad.equals(BigDecimal.ZERO)) {
                queryStr += " AND cm.municipio.gpoId = :gpoCiudad ";
            }
            if (tipoOt != null && tipoOt.getIdTipoOt() != null) {
                queryStr += " AND o.tipoOtObj.idTipoOt = :tipoOt ";
            }
            if (estadoInternoBasicaId != null && !estadoInternoBasicaId.equals(BigDecimal.ZERO)) {
                queryStr += " AND o.estadoInternoObj.basicaId = :estadoInterno ";
            }
            if (tecnologia != null && !tecnologia.equals(BigDecimal.ZERO)) {
                queryStr += " AND o.basicaIdTecnologia.basicaId = :basicaIdTecnologia ";
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                queryStr += " AND ef.estadoxFlujoId IN :estadosxflujoList ";
            }
            
            queryStr += " ORDER BY a.fechaCreacion ASC ";

            Query query = entityManager.createQuery(queryStr);
            query.setParameter("estadoRegistro", 1);
            
            if (gpoDepartamento != null && !gpoDepartamento.equals(BigDecimal.ZERO)) {
                query.setParameter("gpoDepartamento", gpoDepartamento);
            }
            if (gpoCiudad != null && !gpoCiudad.equals(BigDecimal.ZERO)) {
                query.setParameter("gpoCiudad", gpoCiudad);
            }
            if (tipoOt != null && tipoOt.getIdTipoOt() != null) {
                query.setParameter("tipoOt", tipoOt.getIdTipoOt());
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                query.setParameter("estadosxflujoList", estadosxflujoList);
            }
             if (tecnologia != null && !tecnologia.equals(BigDecimal.ZERO)) {
                query.setParameter("basicaIdTecnologia", tecnologia);
            }
            if (estadoInternoBasicaId != null && !estadoInternoBasicaId.equals(BigDecimal.ZERO)) {
                query.setParameter("estadoInterno", estadoInternoBasicaId);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return query.getResultList();
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }

    }

      /**
     * Busca una aprobacion de cambio de estado por orden de trabafo y flujo
     *
     * @author Victor Bocanegra
     * @param ot orden de trabajo
     * @param detalleFlujoMgl  detalle flujo
     * @return CmtAprobacionesEstadosMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtAprobacionesEstadosMgl  findByOtAndFlujo(CmtOrdenTrabajoMgl ot,
            CmtDetalleFlujoMgl detalleFlujoMgl) throws ApplicationException {
        try {
            CmtAprobacionesEstadosMgl result;
            Query query = entityManager.createNamedQuery("CmtAprobacionesEstadosMgl.findByOtAndFlujo");
            query.setParameter("OtObj", ot);
            query.setParameter("detalleFlujoObj", detalleFlujoMgl);
            result = (CmtAprobacionesEstadosMgl) query.getSingleResult();
            return result;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }

    }
    
     /**
     * Busca aprobaciones de cambio de estado por id de ot y con permisos en el
     * repositorio.
     *
     * @author Victor Bocanegra
     * @param idOt
     * @param estadosxflujoList lista de estado X flujo
     * @return CmtAprobacionesEstadosMgl orden aprobad financieramente en el
     * repositorio
     * @throws ApplicationException
     */
    public CmtAprobacionesEstadosMgl findLstAproByOtAndPermisos(BigDecimal idOt,
            List<BigDecimal> estadosxflujoList) throws ApplicationException {

        try {
            String queryStr = "SELECT   a  FROM CmtAprobacionesEstadosMgl a, "
                    + " CmtOrdenTrabajoMgl o, CmtTipoOtMgl t, "
                    + " CmtEstadoxFlujoMgl ef "
                    + " WHERE  a.OtObj.idOt = o.idOt  "
                    + " AND  t.idTipoOt = o.tipoOtObj.idTipoOt  "
                    + " AND ef.tipoFlujoOtObj.basicaId = t.tipoFlujoOt.basicaId  "
                    + " AND  a.estadoRegistro = :estadoRegistro  "
                    + " AND  a.detalleFlujoObj.detalleFlujoId  in  "
                    + "(SELECT c.detalleFlujoId FROM CmtDetalleFlujoMgl c "
                    + " WHERE c.identificadorInternoApp = '" + Constant.DETALLE_FLUJO_APRO_FINAN + "' "
                    + " AND c.estadoRegistro=1)  ";

            if (idOt != null && !idOt.equals(BigDecimal.ZERO)) {
                queryStr += "AND  a.OtObj.idOt = :OtObj";
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                queryStr += " AND ef.estadoxFlujoId IN :estadosxflujoList ";
            }

            Query query = entityManager.createQuery(queryStr);
            query.setParameter("estadoRegistro", 1);

            if (idOt != null && !idOt.equals(BigDecimal.ZERO)) {
                query.setParameter("idOt", idOt);
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                query.setParameter("estadosxflujoList", estadosxflujoList);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return (CmtAprobacionesEstadosMgl) query.getSingleResult();
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }

    }
}
