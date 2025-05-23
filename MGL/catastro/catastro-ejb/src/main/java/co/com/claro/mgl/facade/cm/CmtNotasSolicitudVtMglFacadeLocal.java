/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudVtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtNotasSolicitudVtMglFacadeLocal{

        /**
     * Lista los diferentes tipos de notas por el ID.
     *
     * @author Juan David Hernandez
     * @param tipoNotasId
     * @return
     * @throws ApplicationException
     */
    public List<CmtNotasSolicitudVtMgl> findTipoNotasId(BigDecimal tipoNotasId) 
            throws ApplicationException;

     /**
     * Lista notas tipos por el ID.
     *
     * @author Juan David Hernandez
     * @param id
     * @return
     * @throws ApplicationException
     */
    public CmtNotasSolicitudVtMgl findById(BigDecimal id)
            throws ApplicationException;
    

      /**
     * Crea una nota en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public CmtNotasSolicitudVtMgl crearNotSol(CmtNotasSolicitudVtMgl t)
            throws ApplicationException;

      /**
     * Listado de notas por id de solicitud
     *
     * @author Juan David Hernandez
     * @param idSolicitud
     * @return
     * @throws ApplicationException
     */
    public List<CmtNotasSolicitudVtMgl>
            findNotasBySolicitudId(BigDecimal idSolicitud)
            throws ApplicationException;
    
    void setUser(String mUser, int mPerfil) throws ApplicationException;
    
}