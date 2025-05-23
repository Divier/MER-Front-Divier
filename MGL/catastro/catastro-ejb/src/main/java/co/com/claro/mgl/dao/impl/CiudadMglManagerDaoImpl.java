package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.CiudadDTO;
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

public class CiudadMglManagerDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger(CiudadMglManagerDaoImpl.class);
    private static final String CONSULTA_CIUDAD_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CONSULTA_CIUDAD_SP";

    public List<CiudadDTO> consultarCiudadesPorDepartamento(String departamento) {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_CIUDAD_SP);

            storedProcedureQuery.registerStoredProcedureParameter("PI_DEPARTAMENTO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_DEPARTAMENTO", departamento);

            storedProcedureQuery.execute();
            List<Object[]> listaCiudadesResult = storedProcedureQuery.getResultList();
            List<CiudadDTO> ciudades = new ArrayList<>();

            if (!listaCiudadesResult.isEmpty()) {
                listaCiudadesResult.forEach(r -> ciudades.add(CiudadDTO.builder().nombre((String) r[0]).build()));
            }

            return ciudades;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento " + CONSULTA_CIUDAD_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_CIUDAD_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }
}
