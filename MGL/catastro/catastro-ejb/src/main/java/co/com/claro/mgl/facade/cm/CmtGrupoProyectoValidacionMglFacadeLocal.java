/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtGrupoProyectoValidacionMgl;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtGrupoProyectoValidacionMglFacadeLocal extends BaseFacadeLocal<CmtGrupoProyectoValidacionMgl> {

    @Override
    public List<CmtGrupoProyectoValidacionMgl> findAll() throws ApplicationException;
    

    public void setUser(String mUser, int mPerfil) throws ApplicationException;
    
    public List<CmtGrupoProyectoValidacionMgl> findPendientesParaGestionPaginacion(int page, int filasPag) throws ApplicationException;
}
