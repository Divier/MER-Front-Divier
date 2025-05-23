/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtBacklogMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaBacklogsDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBacklogMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class CmtBacklogMglManager {

    private CmtBacklogMglDaoImpl dao = new CmtBacklogMglDaoImpl();

    /**
     * Busca lista de Backlogs en el repositorio
     *
     * @author Victor Bocanegra
     * @return List<CmtBacklogMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtBacklogMgl> findAll()
            throws ApplicationException {

        return dao.findAll();
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
    public CmtBacklogMgl create(CmtBacklogMgl cmtBacklogMgl, String usuarioCrea, int perfilCrea) throws ApplicationException {
        cmtBacklogMgl = dao.createCm(cmtBacklogMgl, usuarioCrea, perfilCrea);
        return cmtBacklogMgl;
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
    public CmtBacklogMgl update(CmtBacklogMgl cmtBacklogMgl, String usuarioMod, int perfilMod) throws ApplicationException {
        cmtBacklogMgl = dao.updateCm(cmtBacklogMgl, usuarioMod, perfilMod);
        return cmtBacklogMgl;
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
    public boolean delete(CmtBacklogMgl cmtBacklogMgl,
            String usuarioMod, int perfilMod) throws ApplicationException {
        return dao.deleteCm(cmtBacklogMgl, usuarioMod, perfilMod);

    }

    /**
     * Busca un Backlog de OT en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idBacklog
     * @return un Backlog de OT.
     * @throws ApplicationException
     */
    public CmtBacklogMgl findByIdBacklog(BigDecimal idBacklog) throws ApplicationException {

        return dao.findByIdBacklog(idBacklog);
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
    public PaginacionDto<CmtBacklogMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaBacklogsDto consulta,
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException {

        PaginacionDto<CmtBacklogMgl> resultado = new PaginacionDto<CmtBacklogMgl>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        resultado.setNumPaginas(dao.countByBacklogFiltro(consulta, cmtOrdenTrabajoMgl));
        resultado.setListResultado(dao.findByFiltro(firstResult, maxResults, consulta, cmtOrdenTrabajoMgl));

        return resultado;

    }
}
