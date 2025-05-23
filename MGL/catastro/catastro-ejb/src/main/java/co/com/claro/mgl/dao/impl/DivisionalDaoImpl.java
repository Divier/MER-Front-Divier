/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.DivisionalDTO;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Divisional;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.EntityManagerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author User
 */
public class DivisionalDaoImpl extends GenericDaoImpl<Divisional>{

    private static final Logger LOGGER = LogManager.getLogger(DivisionalDaoImpl.class);

    private static final String CONSULTA_DIVISIONAL_SP = Constant.MGL_DATABASE_SCHEMA+".CONSULTAS_FILTROS_EC_PKG.CONSULTA_DIVISIONAL_SP";
    public List<Divisional> findAll() throws ApplicationException{
        List<Divisional> resultList; 
        Query query = entityManager.createNamedQuery("Divisional.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<Divisional>)query.getResultList();        
        return resultList;
    }

    public List<DivisionalDTO> listarDivisionalPorDepartamentoCiudadYCentroPoblado(String departamento, String ciudad, String centroPoblado) {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery(CONSULTA_DIVISIONAL_SP);

            storedProcedureQuery.registerStoredProcedureParameter("PI_DEPARTAMENTO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_CIUDAD", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PI_CENTRO_POBLADO", String.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CURSOR", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_RESULTADO", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_DEPARTAMENTO", departamento);
            storedProcedureQuery.setParameter("PI_CIUDAD", ciudad);
            storedProcedureQuery.setParameter("PI_CENTRO_POBLADO", centroPoblado);

            storedProcedureQuery.execute();
            List<Object[]> listaDivisionalesResult = storedProcedureQuery.getResultList();
            List<DivisionalDTO> divisionals = new ArrayList<>();

            if (!listaDivisionalesResult.isEmpty()) {
                listaDivisionalesResult.forEach(r -> divisionals.add(DivisionalDTO.builder().nombre((String) r[0]).build()));
            }

            return divisionals;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento " + CONSULTA_DIVISIONAL_SP + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado " + CONSULTA_DIVISIONAL_SP + e);
            return null;
        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }
    
}
