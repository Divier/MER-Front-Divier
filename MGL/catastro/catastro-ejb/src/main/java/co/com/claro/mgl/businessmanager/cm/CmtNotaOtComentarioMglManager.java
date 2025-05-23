/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtNotaOtComentarioMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtComentarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import java.util.List;

/**
 * Manager Comentario Nota Orden de Trabajo. Contiene la logica de negocio de
 * los comentatirios de las notas de ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtNotaOtComentarioMglManager {
    
    /**
     * Crea una nota asociadas a una Ordene de Trabajo.Permite realizar la
 creacion de una nota de una Ordene de Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param comentarioNotaOtMgl nota de la orden de trabajo
     * @param usuario
     * @param perfil
     * @return nota creada en el repositorio
     * @throws ApplicationException
     */
    public CmtNotaOtComentarioMgl crear(CmtNotaOtComentarioMgl comentarioNotaOtMgl, String usuario, int perfil) throws ApplicationException {
        CmtNotaOtComentarioMglDaoImpl dao = new CmtNotaOtComentarioMglDaoImpl();
        return dao.createCm(comentarioNotaOtMgl, usuario, perfil);
    }
    
    /**
     * Consulta lista de comentarios asociados a una nota de OT.
     *
     * @author Victor Bocanegra Ortiz
     * @param notaOtMgl nota de la orden de trabajo
     * @return List<CmtNotaOtComentarioMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    public List<CmtNotaOtComentarioMgl> findByNotaOt(CmtNotaOtMgl notaOtMgl)
            throws ApplicationException {
        
        CmtNotaOtComentarioMglDaoImpl dao = new CmtNotaOtComentarioMglDaoImpl();
        return dao.findByNotaOt(notaOtMgl);
    }
}
