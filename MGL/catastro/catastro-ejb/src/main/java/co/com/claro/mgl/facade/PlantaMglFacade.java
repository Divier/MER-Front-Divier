/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.PlantaMglManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaPlantaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.PlantaMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class PlantaMglFacade implements PlantaMglFacadeLocal {

    private final PlantaMglManager plantaMglManager;

    public PlantaMglFacade() {
        this.plantaMglManager = new PlantaMglManager();
    }

  
    /**
     * *Jonathan Peña Metodo para buscar las plantas paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<NodoMgl>
     * @throws ApplicationException
     */
    @Override
    public PaginacionDto<PlantaMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaPlantaDto consulta) throws ApplicationException {
        return plantaMglManager.findAllPaginado(paginaSelected, maxResults, consulta);
    }

    
    
    
    @Override
    public List<PlantaMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlantaMgl create(PlantaMgl plantaMgl) throws ApplicationException {
        return plantaMglManager.create(plantaMgl);
    }

    @Override
    public PlantaMgl update(PlantaMgl plantaMgl) throws ApplicationException {
         return plantaMglManager.update(plantaMgl);
          }

    @Override
    public boolean delete(PlantaMgl plantaMgl) throws ApplicationException {
        return plantaMglManager.delete(plantaMgl);
     
    }

    @Override
    public PlantaMgl findById(PlantaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public PlantaMgl findByTypeAndCode(String localType, String LocalCode,
        String parentLocalType, String parentLocalCode) throws ApplicationException {
     
        return plantaMglManager.findByTypeAndCode(localType, LocalCode, parentLocalType, parentLocalCode);
    }
    
}
