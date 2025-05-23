package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.NotasPlantaMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaNotasPlantaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.PlantaMglNotas;

import java.math.BigDecimal;

/**
 *
 * @author User
 */
public class NotasPlantaMglManager {
  
    private NotasPlantaMglDaoImpl notasPlantaMglDaoImpl = new NotasPlantaMglDaoImpl();

    /**
     * *Jonathan Peña
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @param plantaId
     * @return PaginacionDto<PlantaMglNotas>
     * @throws ApplicationException
     */
    public PaginacionDto<PlantaMglNotas> findAllPaginado(int paginaSelected,
            int maxResults, 
            CmtFiltroConsultaNotasPlantaDto consulta,
            BigDecimal plantaId) throws ApplicationException {

        PaginacionDto<PlantaMglNotas> resultado = new PaginacionDto<PlantaMglNotas>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        if (consulta != null) {
            resultado.setNumPaginas(notasPlantaMglDaoImpl.countByPlantFiltro(
                    consulta,plantaId));
            resultado.setListResultado(notasPlantaMglDaoImpl.findByFiltro(
                    firstResult, maxResults, consulta,plantaId));
        } else {
            consulta = new CmtFiltroConsultaNotasPlantaDto();
            resultado.setNumPaginas(notasPlantaMglDaoImpl.countByPlantFiltro(
                    consulta,plantaId));
            resultado.setListResultado(notasPlantaMglDaoImpl.findByFiltro(
                    firstResult, maxResults, consulta,plantaId));
        }
        return resultado;
    }
   
  
    public PlantaMglNotas  create(PlantaMglNotas  plantaMglNotas) throws ApplicationException{
    
     return notasPlantaMglDaoImpl.create(plantaMglNotas);
    }
    
}
