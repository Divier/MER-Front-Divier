/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtContactosMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtContactosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.util.List;

/**
 *
 * @author rodriguezluim
 */
public class CmtContactosMglManager {

    private CmtContactosMglDaoImpl cmtContactosMglDaoImpl = new CmtContactosMglDaoImpl();

    public CmtContactosMgl create(CmtContactosMgl contactosMgl, String usuario, int perfil) throws ApplicationException {
        cmtContactosMglDaoImpl.createCm(contactosMgl, usuario, perfil);
        return cmtContactosMglDaoImpl.find(contactosMgl.getContactosId());
    }

    public List<CmtContactosMgl> findByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        return cmtContactosMglDaoImpl.findByCuentaMatriz(cuentaMatriz);
    }

    public List<CmtContactosMgl> findByOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajoMgl) throws ApplicationException {
        return cmtContactosMglDaoImpl.findByOrdenTrabajo(ordenTrabajoMgl);
    }

    public CmtContactosMgl eliminarContacto(CmtContactosMgl contactosMgl, String usuario, int perfil) throws ApplicationException {
        contactosMgl.setEstadoRegistro(0);
        return cmtContactosMglDaoImpl.updateCm(contactosMgl, usuario, perfil);
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
    public PaginacionDto<CmtContactosMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws ApplicationException {

        PaginacionDto<CmtContactosMgl> resultado = new PaginacionDto<CmtContactosMgl>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        resultado.setNumPaginas(cmtContactosMglDaoImpl.countContactosByOT(cmtOrdenTrabajoMgl));
        resultado.setListResultado(cmtContactosMglDaoImpl.findByOrdenTrabajo(firstResult, maxResults, cmtOrdenTrabajoMgl));

        return resultado;

    }
}
