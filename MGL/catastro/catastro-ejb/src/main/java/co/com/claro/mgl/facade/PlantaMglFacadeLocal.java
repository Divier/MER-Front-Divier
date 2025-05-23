/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CmtFiltroConsultaPlantaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.PlantaMgl;

/**
 *
 * @author User
 */
public interface PlantaMglFacadeLocal extends BaseFacadeLocal<PlantaMgl> {

   
     /**
     * Jonathan Peña  Metodo para buscar las Plantas paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<PlantaMgl>
     * @throws ApplicationException
     */
    public PaginacionDto<PlantaMgl> findAllPaginado(int paginaSelected,
            int maxResults,CmtFiltroConsultaPlantaDto consulta) throws ApplicationException;

    /**
     * Jonathan Peña  Metodo para buscar planta a partir de localtype y localCode
     *
     * @param localType
     * @param LocalCode
     * @param parentLocalType
     * @param parentLocalCode
     * @return PlantaMgl
     * @throws ApplicationException
     */
     public PlantaMgl findByTypeAndCode(String localType, String LocalCode,
        String parentLocalType, String parentLocalCode ) throws ApplicationException;
     
    @Override
      public boolean delete(PlantaMgl plantaMgl) throws ApplicationException;
}