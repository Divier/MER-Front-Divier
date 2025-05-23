package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.ArchActEstComercialDto;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.EntityManagerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * DAO que consume métodos de paquete de BD: CONSULTAS_FILTROS_EC_PKG
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class ConActEstComercialMglManagerDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger(ConActEstComercialMglManagerDaoImpl.class);

    public static final String CONSULTA_LISTA_REPORTES_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CON_TBL_ESTADO_REPORTE_SP";

    public List<ArchActEstComercialDto> listarTodosConsulta() {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_LISTA_REPORTES_SP);
            storedProcedureQuery.registerStoredProcedureParameter("PI_EST_REPORTE_ID", Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_TIPO_PROCESO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_EST_REPORTE_ID", null);
            storedProcedureQuery.setParameter("PI_TIPO_PROCESO", "CONSULTA");
            storedProcedureQuery.execute();

            List<Object[]> listaResult = storedProcedureQuery.getResultList();
            List<ArchActEstComercialDto> archActEstComercialDtos = new ArrayList<>();

            if (listaResult != null && !listaResult.isEmpty()) {
                listaResult.forEach(r -> archActEstComercialDtos.add(ArchActEstComercialDto.builder()
                        .estReporteId(r[0] == null ? 0 : ((BigDecimal) r[0]).intValue())
                        .nombreArchivo(r[1] == null ? "" : (String) r[1])
                        .fechaRegistro(r[2] == null ? "" : formatDate((r[2]).toString()))
                        .filtro(r[3] == null ? "" : (String) r[3])
                        .estado(r[4] == null ? "" : (String) r[4])
                        .ruta(r[5] == null ? "" : (String) r[5])
                        .totalReg(r[6] == null ? 0 : ((BigDecimal) r[6]).intValue()).build()));
            }

            return archActEstComercialDtos;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento almacenado "+ CONSULTA_LISTA_REPORTES_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_LISTA_REPORTES_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

    public List<ArchActEstComercialDto> listarTodosCargue() {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_LISTA_REPORTES_SP);
            storedProcedureQuery.registerStoredProcedureParameter("PI_EST_REPORTE_ID", Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_TIPO_PROCESO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_EST_REPORTE_ID", null);
            storedProcedureQuery.setParameter("PI_TIPO_PROCESO", "CARGUE");
            storedProcedureQuery.execute();

            List<Object[]> listaResult = storedProcedureQuery.getResultList();
            List<ArchActEstComercialDto> archActEstComercialDtos = new ArrayList<>();
            Timestamp t1 = new Timestamp(6L);
            t1.toString();
            if (listaResult != null && !listaResult.isEmpty()) {
                listaResult.forEach(r -> archActEstComercialDtos.add(ArchActEstComercialDto.builder()
                        .estReporteId(r[0] == null ? 0 : ((BigDecimal) r[0]).intValue())
                        .nombreArchivo(r[1] == null ? "" : (String) r[1])
                        .fechaRegistro(r[2] == null ? "" : formatDate((r[2]).toString()))
                        .filtro(r[3] == null ? "" : (String) r[3])
                        .estado(r[4] == null ? "" : (String) r[4])
                        .ruta(r[5] == null ? "" : (String) r[5])
                        .totalReg(r[6] == null ? 0 : ((BigDecimal) r[6]).intValue()).build()));
            }

            return archActEstComercialDtos;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento almacenado "+ CONSULTA_LISTA_REPORTES_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_LISTA_REPORTES_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

    public ArchActEstComercialDto buscarById(int id) {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_LISTA_REPORTES_SP);
            storedProcedureQuery.registerStoredProcedureParameter("PI_EST_REPORTE_ID", Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_TIPO_PROCESO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_EST_REPORTE_ID", id);
            storedProcedureQuery.setParameter("PI_TIPO_PROCESO", "CARGUE");
            storedProcedureQuery.execute();

            List<Object[]> listaResult = storedProcedureQuery.getResultList();
            List<ArchActEstComercialDto> archActEstComercialDtos = new ArrayList<>();
            Timestamp t1 = new Timestamp(6L);
            t1.toString();
            if (!listaResult.isEmpty()) {
                listaResult.forEach(r -> archActEstComercialDtos.add(ArchActEstComercialDto.builder()
                        .estReporteId(r[0] == null ? 0 : ((BigDecimal) r[0]).intValue())
                        .nombreArchivo(r[1] == null ? "" : (String) r[1])
                        .fechaRegistro(r[2] == null ? "" : (r[2]).toString())
                        .filtro(r[3] == null ? "" : (String) r[3])
                        .estado(r[4] == null ? "" : (String) r[4])
                        .ruta(r[5] == null ? "" : (String) r[5]).build()));
            }

            return archActEstComercialDtos.get(0);

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento almacenado "+ CONSULTA_LISTA_REPORTES_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_LISTA_REPORTES_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

    public String formatDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(date);
            return dateFormat.format(d);
        }catch (ParseException e){
            LOGGER.error(e);
            return date;
        }
    }
}
