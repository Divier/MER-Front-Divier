/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtNotaOtComentarioMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtComentarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Comentario Nota Orden de Trabajo. Expone la logica de negocio de los
 * comentatirios de las notas de ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtNotaOtComentarioMglFacade implements CmtNotaOtComentarioMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;
    
     /**
     * Crea una nota asociadas a una Ordene de Trabajo. Permite realizar la
     * creacion de una nota de una Ordene de Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param comentarioNotaOtMgl nota de la orden de trabajo
     * @return nota creada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtNotaOtComentarioMgl crear(CmtNotaOtComentarioMgl comentarioNotaOtMgl) throws ApplicationException {
        CmtNotaOtComentarioMglManager manager = new CmtNotaOtComentarioMglManager();
        return manager.crear(comentarioNotaOtMgl, user, perfil);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
    /**
     * Consulta lista de comentarios asociados a una nota de OT.
     *
     * @author Victor Bocanegra Ortiz
     * @param notaOtMgl nota de la orden de trabajo
     * @return List<CmtNotaOtComentarioMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtNotaOtComentarioMgl> findByNotaOt(CmtNotaOtMgl notaOtMgl)
            throws ApplicationException {

        CmtNotaOtComentarioMglManager manager = new CmtNotaOtComentarioMglManager();
        return manager.findByNotaOt(notaOtMgl);
    }
}
