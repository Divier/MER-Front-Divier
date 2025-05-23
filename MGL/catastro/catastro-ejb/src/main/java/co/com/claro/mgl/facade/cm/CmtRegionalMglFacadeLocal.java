/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.CmtFiltroConsultaRegionalDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */

@Local
public interface CmtRegionalMglFacadeLocal extends BaseFacadeLocal<CmtRegionalRr>{

   
    
    
    public PaginacionDto<CmtRegionalRr> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaRegionalDto consulta) throws ApplicationException ;
    
    
  
    @Override
    public boolean delete(CmtRegionalRr regionalMgl) throws ApplicationException;
    
}
