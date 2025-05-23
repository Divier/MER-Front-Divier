package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.CentroPobladoDTO;
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
 * DAO para la consulta de Centros Poblados en paquete CONSULTAS_FILTROS_EC_PKG
 * @author dayver.delahoz
 * @version Brief100417
 */
public class CentroPobladoMglManagerDaoImpl {
    private static final Logger LOGGER = LogManager.getLogger(CentroPobladoMglManagerDaoImpl.class);

    private static final String CONSULTA_CENTRO_POBLADO_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CONSULTA_CENTROPOBLADO_SP";

    public List<CentroPobladoDTO> consultarCentroPobladoPorDepartamentoYCiudad(String departamento, String ciudad) {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_CENTRO_POBLADO_SP);

            storedProcedureQuery.registerStoredProcedureParameter("PI_DEPARTAMENTO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_CIUDAD", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_DEPARTAMENTO", departamento);
            storedProcedureQuery.setParameter("PI_CIUDAD", ciudad);

            storedProcedureQuery.execute();
            List<Object[]> listaCentroPobladoResult = storedProcedureQuery.getResultList();
            List<CentroPobladoDTO> centroPoblados = new ArrayList<>();

            if (!listaCentroPobladoResult.isEmpty()) {
                listaCentroPobladoResult.forEach(r -> centroPoblados.add(CentroPobladoDTO.builder().nombre((String) r[0]).build()));
            }

            return centroPoblados;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento " + CONSULTA_CENTRO_POBLADO_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_CENTRO_POBLADO_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }
}
