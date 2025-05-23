/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;

/**
 *
 * @author User
 */
public interface ComunidadMglFacadeLocal extends BaseFacadeLocal<CmtComunidadRr>{
    
    void setUser(String user, int perfil) throws ApplicationException;
    
    public PaginacionDto<CmtComunidadRr> findAllPaginado(int paginaSelected,
            int maxResults,CmtFiltroConsultaComunidadDto consulta) throws ApplicationException;    
}
