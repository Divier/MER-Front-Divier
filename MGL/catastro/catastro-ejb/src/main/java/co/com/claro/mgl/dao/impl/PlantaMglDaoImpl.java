/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.CmtFiltroConsultaPlantaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PlantaMgl;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class PlantaMglDaoImpl extends GenericDaoImpl<PlantaMgl> {


    
    /**
     * Jonathan Peña Metodo para buscar las configuraciones de planta
     * por los filtros de la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return List<PlantaMgl>
     * @throws ApplicationException
     */
    public List<PlantaMgl> findByFiltro(int paginaSelected,
            int maxResults, CmtFiltroConsultaPlantaDto consulta) throws ApplicationException {
        List<PlantaMgl> resultList;

        String queryTipo;
        queryTipo = "SELECT n FROM PlantaMgl n  "
                + "WHERE n.estadoRegistro = 1 " ;

        if (consulta.getConfiguracionPlantaId()!= null) {
            queryTipo += " AND n.configuracionplantaid LIKE :configuracionPlantaId ";
        }

        if (consulta.getLocationType() != null && !consulta.getLocationType().isEmpty()) {
            queryTipo += " AND UPPER(n.locationtype) LIKE :locationtype ";
        }
        
        if (consulta.getLocationCode()!= null && !consulta.getLocationCode().isEmpty()) {
            queryTipo += "AND UPPER(n.locationcode) LIKE :locationcode ";
        }
        
        if (consulta.getDescription()!= null && !consulta.getDescription().isEmpty()) {
            queryTipo += " AND UPPER(n.description) LIKE :description ";
        }
        
        if (consulta.getPadrePlanta()!= null ) {
            queryTipo += " AND UPPER(n.configuracionplantaparentid.configuracionplantaid)"
                      + " = :padreConfiguracionplantaid ";
        }
        queryTipo += " ORDER BY n.configuracionplantaid DESC ";
        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getConfiguracionPlantaId() != null) {
            query.setParameter("configuracionPlantaId", consulta.getConfiguracionPlantaId());
        }
        if (consulta.getLocationType() != null && !consulta.getLocationType().isEmpty()) {
            query.setParameter("locationtype", "%" 
                               + consulta.getLocationType().toUpperCase() + "%");
        }
        if (consulta.getLocationCode() != null && !consulta.getLocationCode().isEmpty()) {
            query.setParameter("locationcode", "%" + consulta.getLocationCode().toUpperCase() + "%");
        }
        
        if (consulta.getDescription() != null && !consulta.getDescription().isEmpty()) {
            query.setParameter("description", "%" + consulta.getDescription().toUpperCase() + "%");
        }
        
        if (consulta.getPadrePlanta() != null ) {
            query.setParameter("padreConfiguracionplantaid", consulta.getPadrePlanta().getConfiguracionplantaid().toString());
        }
        
        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<PlantaMgl>) query.getResultList();

        return resultList;
    }

    /**
     * Jonathan Peña
     * Metodo para contar las Configuraciones planta
     * por los filtros de la tabla
     *
     * @param consulta
     * @return Long
     * @throws ApplicationException
     */
    public Long countByPlantFiltro(CmtFiltroConsultaPlantaDto consulta)
            throws ApplicationException {

        Long resultCount = 0L;
        String queryTipo;
        queryTipo = "SELECT COUNT(1) FROM PlantaMgl n  "
                + "WHERE n.estadoRegistro = 1 " ;

        if (consulta.getConfiguracionPlantaId()!= null) {
            queryTipo += " AND n.configuracionplantaid LIKE :configuracionPlantaId ";
        }

        if (consulta.getLocationType() != null && !consulta.getLocationType().isEmpty()) {
            queryTipo += " AND UPPER(n.locationtype) LIKE :locationtype ";
        }
        
        if (consulta.getLocationCode()!= null && !consulta.getLocationCode().isEmpty()) {
            queryTipo += " AND UPPER(n.locationcode) LIKE :locationcode ";
        }
        
        if (consulta.getDescription()!= null && !consulta.getDescription().isEmpty()) {
            queryTipo += " AND UPPER(n.description) LIKE :description ";
        }
        
        if (consulta.getPadrePlanta()!= null ) {
            queryTipo += " AND UPPER(n.configuracionplantaparentid.configuracionplantaid) "
                    + " = :padreConfiguracionplantaid ";
        }

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getConfiguracionPlantaId() != null) {
            query.setParameter("configuracionPlantaId", consulta.getConfiguracionPlantaId());
        }
        if (consulta.getLocationType() != null && !consulta.getLocationType().isEmpty()) {
            query.setParameter("locationtype", "%" 
                               + consulta.getLocationType().toUpperCase() + "%");
        }
        if (consulta.getLocationCode() != null && !consulta.getLocationCode().isEmpty()) {
            query.setParameter("locationcode", "%" + consulta.getLocationCode().toUpperCase() + "%");
        }
        if (consulta.getDescription() != null && !consulta.getDescription().isEmpty()) {
            query.setParameter("description", "%" + consulta.getDescription().toUpperCase() + "%");
        }
        if (consulta.getPadrePlanta() != null) {
            query.setParameter("padreConfiguracionplantaid", consulta.getPadrePlanta().getConfiguracionplantaid().toString());
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }

    
    /**
     * Jonathan Peña
     * Metodo para buscar una configuracion de planta,
     * por su localType y Localcode
     *
     * @param localType
     * @param localCode
     * @param parentLocalType
     * @param parentLocalCode
     * @return Long
     * @throws ApplicationException
     */
    public PlantaMgl findByTypeAndCode(String localType, String localCode, 
            String parentLocalType, String parentLocalCode) throws ApplicationException {
        List<PlantaMgl> result;
        StringBuilder queryTipo = new StringBuilder();
        queryTipo.append(" SELECT n FROM PlantaMgl n  "
                + " WHERE n.estadoRegistro=1 "
                + " AND UPPER(n.locationtype) LIKE :locationtype "
                + " AND UPPER(n.locationcode) = :locationcode ");
        if (parentLocalType != null && parentLocalCode != null){
            queryTipo.append(" AND UPPER(n.configuracionplantaparentid.locationtype) LIKE :parentLocationtype "
            + " AND UPPER(n.configuracionplantaparentid.locationcode) = :parentLocationcode ");
        }
        Query query = entityManager.createQuery(queryTipo.toString());
        query.setParameter("locationtype", "%" + localType + "%");
        query.setParameter("locationcode", localCode);
        if (parentLocalType != null && parentLocalCode != null){
            query.setParameter("parentLocationtype", "%" + parentLocalType + "%");
            query.setParameter("parentLocationcode", parentLocalCode);
        }
        result = (List<PlantaMgl>) query.getResultList();
        if (result == null || result.isEmpty()) {
            return null;
        }
        return result.get(0);
        
    }    
}
