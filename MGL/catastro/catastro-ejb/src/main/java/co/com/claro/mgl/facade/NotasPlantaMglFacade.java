/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.NotasPlantaMglManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaNotasPlantaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.PlantaMglNotas;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *sssss
 * @author Admin
 */
@Stateless
public class NotasPlantaMglFacade implements NotasPlantaMglFacadeLocal {
    
      private final NotasPlantaMglManager notasPlantaMglManager;

    public NotasPlantaMglFacade() {
        this.notasPlantaMglManager = new NotasPlantaMglManager();
    }
    
    
    
    
    @Override
    public PaginacionDto<PlantaMglNotas> findAllPaginado(int paginaSelected,
            int maxResults,
            CmtFiltroConsultaNotasPlantaDto consulta,
            BigDecimal plantaId) throws ApplicationException {
        
      return notasPlantaMglManager.findAllPaginado(paginaSelected,
              maxResults,
              consulta,
              plantaId);
    }

    
    @Override
    public List<PlantaMglNotas> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlantaMglNotas create(PlantaMglNotas plantaMglNotas) throws ApplicationException {
        
      return  notasPlantaMglManager.create(plantaMglNotas);
    }

    @Override
    public PlantaMglNotas update(PlantaMglNotas t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(PlantaMglNotas t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlantaMglNotas findById(PlantaMglNotas sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    
}
