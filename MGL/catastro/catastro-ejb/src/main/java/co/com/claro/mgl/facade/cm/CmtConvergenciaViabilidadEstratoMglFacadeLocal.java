/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroConsultaViabilidadSegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadEstratoMgl;
import java.util.List;

/**
 * Facade Local Viabilidad Segmento Estratos Convergencia. Expone la logica de negocio 
 * para el manejo de Viabilidad Segmento Estratos Convergencia en el repositorio.
 *
 * @author Laura Carolina Muñoz - HITSS
 * @version 1.00.000
 */
public interface CmtConvergenciaViabilidadEstratoMglFacadeLocal  extends BaseFacadeLocal<CmtConvergenciaViabilidadEstratoMgl> {
    
    @Override
    public List<CmtConvergenciaViabilidadEstratoMgl> findAll() throws ApplicationException ;
     
     public CmtConvergenciaViabilidadEstratoMgl create(CmtConvergenciaViabilidadEstratoMgl t, String usuario, int perfil) throws ApplicationException ;

    public CmtConvergenciaViabilidadEstratoMgl update(CmtConvergenciaViabilidadEstratoMgl t, String usuario, int perfil) throws ApplicationException ;
    
    public List<CmtConvergenciaViabilidadEstratoMgl> findViabilidadSegEstratoPaginacion(FiltroConsultaViabilidadSegmentoDto filtro, 
            int paginaSelected, int maxResultso) throws ApplicationException;
    
    public int getCountFindByAll(FiltroConsultaViabilidadSegmentoDto filtro) throws ApplicationException;
}
