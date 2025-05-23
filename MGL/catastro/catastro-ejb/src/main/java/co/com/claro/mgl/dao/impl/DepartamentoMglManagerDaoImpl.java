package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.DepartamentoDTO;
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

/**
 * @author dayver.delahoz@vasslatam.com
 */
public class DepartamentoMglManagerDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger(DepartamentoMglManagerDaoImpl.class);

    public static final String CONSULTA_DEPARTAMENTO_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CONSULTA_DEPARTAMENTO_SP";

    public List<DepartamentoDTO> consultarDepartamentos() {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_DEPARTAMENTO_SP);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.execute();
            List<Object[]> listaDepartamentosResult = storedProcedureQuery.getResultList();
            List<DepartamentoDTO> departamentos = new ArrayList<>();

            if (!listaDepartamentosResult.isEmpty()) {
                listaDepartamentosResult.forEach(r -> departamentos.add(DepartamentoDTO.builder().nombre((String) r[0]).build()));
            }

            return departamentos;

        } catch (NoResultException ex) {
            LOGGER.error("Excepción en el Procedimiento almacenado "+ CONSULTA_DEPARTAMENTO_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_DEPARTAMENTO_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }
}
