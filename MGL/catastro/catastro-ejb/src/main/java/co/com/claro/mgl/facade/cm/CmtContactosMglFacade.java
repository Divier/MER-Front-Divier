/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtContactosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtContactosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author rodriguezluim
 */
@Stateless
public class CmtContactosMglFacade implements CmtContactosMglFacadeLocal {

    private CmtContactosMglManager manager = new CmtContactosMglManager();
    private String user = "";
    private int perfil = 0;

    @Override
    public CmtContactosMgl create(CmtContactosMgl contactosMgl) throws ApplicationException {
        return manager.create(contactosMgl, user, perfil);
    }

    @Override
    public List<CmtContactosMgl> findByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        return manager.findByCuentaMatriz(cuentaMatriz);
    }

    @Override
    public List<CmtContactosMgl> findByOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajoMgl) throws ApplicationException {
        return manager.findByOrdenTrabajo(ordenTrabajoMgl);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public CmtContactosMgl eliminarContacto(CmtContactosMgl contactosMgl) throws ApplicationException {
        return manager.eliminarContacto(contactosMgl, user, perfil);
    }

    /**
     * *Victor Bocanegra Metodo para buscar los Backlogs paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param cmtOrdenTrabajoMgl
     * @return PaginacionDto<CmtContactosMgl>
     * @throws ApplicationException
     */
    @Override
    public PaginacionDto<CmtContactosMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws ApplicationException {
        return manager.findAllPaginado(paginaSelected, maxResults, cmtOrdenTrabajoMgl);
    }
}
