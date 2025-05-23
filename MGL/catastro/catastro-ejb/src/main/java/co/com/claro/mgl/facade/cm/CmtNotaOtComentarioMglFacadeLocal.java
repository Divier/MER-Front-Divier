/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtComentarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import java.util.List;

/**
 *
 * @author ortizjaf
 */
public interface CmtNotaOtComentarioMglFacadeLocal {
    
    CmtNotaOtComentarioMgl crear(CmtNotaOtComentarioMgl notaOtMgl) throws ApplicationException;
    
    
    void setUser(String mUser, int mPerfil) throws ApplicationException;
    
        /**
     * Consulta lista de comentarios asociados a una nota de OT.
     *
     * @author Victor Bocanegra Ortiz
     * @param notaOtMgl nota de la orden de trabajo
     * @return List<CmtNotaOtComentarioMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    List<CmtNotaOtComentarioMgl> findByNotaOt(CmtNotaOtMgl notaOtMgl)
            throws ApplicationException; 
}
