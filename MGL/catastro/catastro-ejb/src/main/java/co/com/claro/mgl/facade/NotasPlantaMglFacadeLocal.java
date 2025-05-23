/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CmtFiltroConsultaNotasPlantaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.PlantaMglNotas;
import java.math.BigDecimal;

/**
 *
 * @author User
 */
public interface NotasPlantaMglFacadeLocal extends BaseFacadeLocal<PlantaMglNotas> {

   
     /**
     * Jonathan Peña  Metodo para buscar las Plantas paginados Sen la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @param plantaId
     * @return PaginacionDto<PlantaMgl>
     * @throws ApplicationException
     */
    public PaginacionDto<PlantaMglNotas> findAllPaginado(int paginaSelected,
            int maxResults,
            CmtFiltroConsultaNotasPlantaDto consulta,
            BigDecimal plantaId) throws ApplicationException;


}

    