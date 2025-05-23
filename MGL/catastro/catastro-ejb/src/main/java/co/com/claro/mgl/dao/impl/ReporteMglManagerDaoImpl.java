package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.*;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.EntityManagerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 *
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class ReporteMglManagerDaoImpl {
    private static final Logger LOGGER = LogManager.getLogger(ReporteMglManagerDaoImpl.class);

    public static final String CON_TBL_QUERY = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CON_TBL_QUERY_SP";
    public static final String INSERT_ESTADO_REPORTE_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.ESTADO_REPORTE_SP";
    public static final String NOMBRE_ARCHIVO_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CONSULTA_NOM_ARCHIVO_SP";
    public static final String UPDATE = "U";
    public static final String ACTIVO = "A";

    /***
     *
     * @param accion
     * @param idReporte
     * @param estado
     * @return
     * @throws ApplicationException
     */
    public ReporteConsultaQueryDTO consultaEstadoReporte(String accion, Integer idReporte, String estado) throws ApplicationException {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CON_TBL_QUERY);

            storedProcedureQuery.registerStoredProcedureParameter("PI_ACCION", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_REPORTE_ID", Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_ESTADO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RUTA", String.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_EST_NOM_ARCHIVO", String.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_ESTADO", String.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            if (UPDATE.equalsIgnoreCase(accion) && nonNull(estado)) {
                storedProcedureQuery.setParameter("PI_ESTADO", estado);
            }
            storedProcedureQuery.setParameter("PI_ACCION", accion);
            storedProcedureQuery.setParameter("PI_REPORTE_ID", idReporte);
            storedProcedureQuery.execute();

            ReporteConsultaQueryDTO consultaQueryDTO = ReporteConsultaQueryDTO.builder().ruta((String) storedProcedureQuery.getOutputParameterValue("PO_RUTA")).nombreArchivo((String) storedProcedureQuery.getOutputParameterValue("PO_EST_NOM_ARCHIVO")).estado((String) storedProcedureQuery.getOutputParameterValue("PO_ESTADO")).codigo((Integer) storedProcedureQuery.getOutputParameterValue("PO_CODIGO")).resultado((String) storedProcedureQuery.getOutputParameterValue("PO_RESULTADO")).build();

            return consultaQueryDTO;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento " + CON_TBL_QUERY, ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CON_TBL_QUERY, e);
            throw new ApplicationException("Ocurrió un error error realizando validando el estado del reporte.");
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

    /***
     *
     * @param estadoReporteDTO
     * @return
     * @throws ApplicationException
     */
    public EstadoReporteDTO insertOrUpdateEstadoReporte(EstadoReporteDTO estadoReporteDTO) throws ApplicationException {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(INSERT_ESTADO_REPORTE_SP);

            storedProcedureQuery.registerStoredProcedureParameter("PI_ACCION", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_REPORTE_ID", Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_TIPO_PROCESO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_FILTRO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_NOM_ARCHIVO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_RUTA", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_ROL_EJECUCION", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_EST_REPORTE_ID", Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_ESTADO", String.class, ParameterMode.IN);

            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_ACCION", estadoReporteDTO.getAccion());
            storedProcedureQuery.setParameter("PI_REPORTE_ID", estadoReporteDTO.getIdReporte());
            storedProcedureQuery.setParameter("PI_TIPO_PROCESO", estadoReporteDTO.getTipoProceso());
            storedProcedureQuery.setParameter("PI_FILTRO", estadoReporteDTO.getFiltro());
            storedProcedureQuery.setParameter("PI_NOM_ARCHIVO", estadoReporteDTO.getNombreArchivo());
            storedProcedureQuery.setParameter("PI_RUTA", estadoReporteDTO.getRuta());
            storedProcedureQuery.setParameter("PI_ROL_EJECUCION", estadoReporteDTO.getRolEjecucion());
            storedProcedureQuery.setParameter("PI_EST_REPORTE_ID", estadoReporteDTO.getEstadoReporte());
            storedProcedureQuery.setParameter("PI_ESTADO", estadoReporteDTO.getEstado());
            storedProcedureQuery.execute();

            EstadoReporteDTO reporteDTO = EstadoReporteDTO.builder().codigo((Integer) storedProcedureQuery.getOutputParameterValue("PO_CODIGO")).resultado((String) storedProcedureQuery.getOutputParameterValue("PO_RESULTADO")).build();

            return reporteDTO;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento " + INSERT_ESTADO_REPORTE_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + INSERT_ESTADO_REPORTE_SP + e);
            throw new ApplicationException("Ocurrió un error intentando guardar información del Reporte");
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

    /***
     *
     * @param idReporte
     * @param nombreArchivo
     * @return
     */
    public ArchivoReporteDTO nombreArchivo(Integer idReporte, String nombreArchivo) {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(NOMBRE_ARCHIVO_SP);

            storedProcedureQuery.registerStoredProcedureParameter("PI_REPORTE_ID", Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_NOM_ARCHIVO", String.class, ParameterMode.IN);

            storedProcedureQuery.registerStoredProcedureParameter("PO_EST_REPORTE_ID", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_TOT_REG", String.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_FEC_ENTREGA", String.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RUTA_CARGUE", String.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_NOM_REPORTE", String.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_REPORTE_ID", idReporte);
            storedProcedureQuery.setParameter("PI_NOM_ARCHIVO", nombreArchivo);
            storedProcedureQuery.execute();

            ArchivoReporteDTO build = ArchivoReporteDTO.builder()
                    .idReporte((Integer) storedProcedureQuery.getOutputParameterValue("PO_EST_REPORTE_ID"))
                    .totalRegistros((String) storedProcedureQuery.getOutputParameterValue("PO_TOT_REG"))
                    .fechaEntrega((String) storedProcedureQuery.getOutputParameterValue("PO_FEC_ENTREGA"))
                    .rutaCargue((String) storedProcedureQuery.getOutputParameterValue("PO_RUTA_CARGUE"))
                    .nomeReporte((String) storedProcedureQuery.getOutputParameterValue("PO_NOM_REPORTE"))
                    .codigo((Integer) storedProcedureQuery.getOutputParameterValue("PO_CODIGO"))
                    .resultado((String) storedProcedureQuery.getOutputParameterValue("PO_RESULTADO")).build();

            return build;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento " + NOMBRE_ARCHIVO_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + NOMBRE_ARCHIVO_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

}
