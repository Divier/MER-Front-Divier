package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadHhppMgl;
import co.com.claro.mgl.dtos.FiltroConsultaViabilidadHhppDto;
import java.util.List;

/**
 * Facade Local Viabilidad HHPP Convergencia. Expone la logica de negocio para el manejo de
 * Viabilidad HHPP Convergencia en el repositorio.
 *
 * @author Laura Carolina Muñoz - HITSS
 * @version 1.00.000
 */
public interface CmtConvergenciaViabilidadHhppMglFacadeLocal extends BaseFacadeLocal<CmtConvergenciaViabilidadHhppMgl>  {
    
    
    List<CmtConvergenciaViabilidadHhppMgl> findAll() throws ApplicationException ;
     
    CmtConvergenciaViabilidadHhppMgl create(CmtConvergenciaViabilidadHhppMgl t, String usuario, int perfil) throws ApplicationException ;

    CmtConvergenciaViabilidadHhppMgl update(CmtConvergenciaViabilidadHhppMgl t, String usuario, int perfil) throws ApplicationException;
    
    List<CmtConvergenciaViabilidadHhppMgl> findViabilidadHhppPaginacion(FiltroConsultaViabilidadHhppDto filtro, 
            int paginaSelected, int maxResultso) throws ApplicationException;
    
    int getCountFindByAll(FiltroConsultaViabilidadHhppDto filtro) throws ApplicationException;
}
