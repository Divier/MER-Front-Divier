/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtBacklogMglManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaBacklogsDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBacklogMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class CmtBacklogMglFacade implements CmtBacklogMglFacadeLocal {

    /**
     * Busca lista de Backlogs en el repositorio
     *
     * @author Victor Bocanegra
     * @return List<CmtBacklogMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtBacklogMgl> findAll()
            throws ApplicationException {
        
        CmtBacklogMglManager cmtBacklogMglManager = new CmtBacklogMglManager();
        return cmtBacklogMglManager.findAll();
    }

    /**
     * Crea un Backlog de OT en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtBacklogMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return un Backlog de OT
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public CmtBacklogMgl create(CmtBacklogMgl cmtBacklogMgl, String usuarioCrea, int perfilCrea) throws ApplicationException {
        CmtBacklogMglManager cmtBacklogMglManager = new CmtBacklogMglManager();
        return cmtBacklogMglManager.create(cmtBacklogMgl, usuarioCrea, perfilCrea);
    }

    /**
     * Actualiza un Backlog de OT en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtBacklogMgl
     * @param cmtExtendidaTecnologiaMgl
     * @param usuarioMod
     * @param perfilMod
     * @return un Backlog de OT
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public CmtBacklogMgl update(CmtBacklogMgl cmtBacklogMgl, String usuarioMod, int perfilMod) throws ApplicationException {
        CmtBacklogMglManager cmtBacklogMglManager = new CmtBacklogMglManager();
        return cmtBacklogMglManager.update(cmtBacklogMgl, usuarioMod, perfilMod);
    }

    /**
     * Elimina un Backlog de OT en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtBacklogMgl
     * @param usuarioMod
     * @param perfilMod
     * @return true si la transacion es satisfatoria false si no
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public boolean delete(CmtBacklogMgl cmtBacklogMgl,
            String usuarioMod, int perfilMod) throws ApplicationException {
        CmtBacklogMglManager cmtBacklogMglManager = new CmtBacklogMglManager();
        return cmtBacklogMglManager.delete(cmtBacklogMgl, usuarioMod, perfilMod);
        
    }

    /**
     * Busca un Backlog de OT en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idBacklog
     * @return un Backlog de OT.
     * @throws ApplicationException
     */
    @Override
    public CmtBacklogMgl findByIdBacklog(BigDecimal idBacklog) throws ApplicationException {
        
        CmtBacklogMglManager cmtBacklogMglManager = new CmtBacklogMglManager();
        return cmtBacklogMglManager.findByIdBacklog(idBacklog);
        
    }

    /**
     * *Victor Bocanegra Metodo para buscar los Backlogs paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @param cmtOrdenTrabajoMgl
     * @return PaginacionDto<CmtBacklogMgl>
     * @throws ApplicationException
     */
    @Override
    public PaginacionDto<CmtBacklogMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaBacklogsDto consulta, 
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException {
        
        
        CmtBacklogMglManager cmtBacklogMglManager = new CmtBacklogMglManager();
        return cmtBacklogMglManager.findAllPaginado(paginaSelected, maxResults,
                consulta, cmtOrdenTrabajoMgl);
    }
}
