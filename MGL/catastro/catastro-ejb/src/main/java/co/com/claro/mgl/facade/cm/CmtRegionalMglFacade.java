/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.RegionalMglManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaRegionalDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author JPeña
 */
@Stateless
public class CmtRegionalMglFacade implements CmtRegionalMglFacadeLocal {
    
    private final  RegionalMglManager regionalMglManager;

    public CmtRegionalMglFacade() {
        regionalMglManager = new RegionalMglManager();
    }
    
            
   
    @Override
    public PaginacionDto<CmtRegionalRr> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaRegionalDto consulta) throws ApplicationException {

        return regionalMglManager.findAllPaginado(paginaSelected, maxResults, consulta);
    }
     
    @Override
    public CmtRegionalRr create(CmtRegionalRr regionalMgl) throws ApplicationException {
         return regionalMglManager.create(regionalMgl);
    }

    @Override
    public CmtRegionalRr update(CmtRegionalRr regionalMgl) throws ApplicationException {
      return regionalMglManager.update(regionalMgl);
    }

    @Override
    public boolean delete(CmtRegionalRr regionalMgl) throws ApplicationException {
        return regionalMglManager.delete(regionalMgl);
    }

    @Override
    public CmtRegionalRr findById(CmtRegionalRr sqlData) throws ApplicationException {
        return regionalMglManager.findById(sqlData);
    }

    
    @Override
    public List<CmtRegionalRr> findAll() throws ApplicationException {
        return regionalMglManager.findAll();
    }

     
}
