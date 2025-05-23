/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtContactosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rodriguezluim
 */
@Local
public interface CmtContactosMglFacadeLocal extends BaseCmFacadeLocal<CmtContactosMgl> {

    public CmtContactosMgl create(CmtContactosMgl contactosMgl) throws ApplicationException;

    public List<CmtContactosMgl> findByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException;

    public List<CmtContactosMgl> findByOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajoMgl) throws ApplicationException;

    public CmtContactosMgl eliminarContacto(CmtContactosMgl contactosMgl) throws ApplicationException;

    /**
     * *Victor Bocanegra Metodo para buscar los Backlogs paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param cmtOrdenTrabajoMgl
     * @return PaginacionDto<CmtContactosMgl>
     * @throws ApplicationException
     */
    public PaginacionDto<CmtContactosMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws ApplicationException;
}
