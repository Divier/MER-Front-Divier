/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.CmtFiltroConsultaNotasPlantaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PlantaMglNotas;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class NotasPlantaMglDaoImpl extends GenericDaoImpl<PlantaMglNotas> {


    
    /**
     * Jonathan Peña Metodo para buscar los nodos por los filtros de la
     * tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @param CmtFiltroConsultaPlantaDto
     * @param plantaId
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
    public List<PlantaMglNotas> findByFiltro(int paginaSelected,
            int maxResults, CmtFiltroConsultaNotasPlantaDto consulta, BigDecimal plantaId) throws ApplicationException {
        List<PlantaMglNotas> resultList;

        String queryTipo;
        queryTipo = "SELECT n FROM PlantaMglNotas n  "
                + "WHERE n.estadoRegistro=1 "
                + "AND n.configuracionplantaid.configuracionplantaid = :plantaId " ;
        
        
        if (consulta.getNoteconfiguracionplantaid()!= null) {
            queryTipo += "AND n.noteconfiguracionplantaid LIKE :noteconfiguracionplantaid ";
        }

        if (consulta.getNota()!= null && !consulta.getNota().isEmpty()) {
            queryTipo += "AND UPPER(n.locationtype) LIKE :nota ";
        }
        
        Query query = entityManager.createQuery(queryTipo);

        query.setParameter("plantaId", plantaId);
        
        if (consulta.getNoteconfiguracionplantaid() != null ) {
            query.setParameter("noteconfiguracionplantaid", "%" 
                               + consulta.getNoteconfiguracionplantaid()+ "%");
        }
        if (consulta.getNota() != null && !consulta.getNota().isEmpty()) {
            query.setParameter("nota", "%" + consulta.getNota().toUpperCase() + "%");
        }
       
        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<PlantaMglNotas>) query.getResultList();

        return resultList;
    }

    /**
     * Jonathan Peña Metodo para contar las Configuraciones pantla
     * por los filtros de la tabla
     *
     * @param consulta
     * @param plantaId
     * @return Long
     * @throws ApplicationException
     */
    public Long countByPlantFiltro(CmtFiltroConsultaNotasPlantaDto consulta,
            BigDecimal plantaId) throws ApplicationException {

        Long resultCount = 0L;
        String queryTipo;
        queryTipo = "SELECT COUNT(1) FROM PlantaMglNotas n  "
                + "WHERE n.estadoRegistro=1 "
                + "AND n.configuracionplantaid.configuracionplantaid = :plantaId " ;

        if (consulta.getNoteconfiguracionplantaid()!= null) {
            queryTipo += "AND  n.noteconfiguracionplantaid LIKE :noteconfiguracionplantaid ";
        }

        if (consulta.getNota()!= null && !consulta.getNota().isEmpty()) {
             queryTipo += "AND UPPER(n.locationtype) LIKE :nota ";
        }
       

        Query query = entityManager.createQuery(queryTipo);

        query.setParameter("plantaId", plantaId);
        
        if (consulta.getNoteconfiguracionplantaid() != null ) {
            query.setParameter("noteconfiguracionplantaid", "%" 
                               + consulta.getNoteconfiguracionplantaid()+ "%");
        }
        if (consulta.getNota() != null && !consulta.getNota().isEmpty()) {
            query.setParameter("nota", "%" + consulta.getNota().toUpperCase() + "%");
        }
        
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }

    
   
    
    
    
}
