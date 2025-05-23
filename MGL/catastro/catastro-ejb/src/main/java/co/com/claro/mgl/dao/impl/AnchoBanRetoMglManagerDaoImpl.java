package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.AnchoBanRetoDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.EntityManagerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AnchoBanRetoMglManagerDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger(AnchoBanRetoMglManagerDaoImpl.class);

    public static final String CONSULTA_ANCHO_BAND_RETO_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CONSULTA_ANCHO_BAN_RETO_SP";

    public List<AnchoBanRetoDTO> consultarAnchoBanReto() {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_ANCHO_BAND_RETO_SP);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.execute();
            List<Object[]> resultList = storedProcedureQuery.getResultList();
            List<AnchoBanRetoDTO> anchoBanRetoDTOS = new ArrayList<>();

            if (!resultList.isEmpty()) {
                resultList.forEach(r -> anchoBanRetoDTOS.add(AnchoBanRetoDTO.builder().nombre((String) r[0]).build()));
            }

            return anchoBanRetoDTOS;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento almacenado "+ CONSULTA_ANCHO_BAND_RETO_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_ANCHO_BAND_RETO_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }
}
