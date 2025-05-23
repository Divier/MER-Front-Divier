/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtNotasOtHhppMglFacadeLocal{

        /**
     * Lista los diferentes tipos de notas por el ID.
     *
     * @author Juan David Hernandez
     * @param tipoNotasId
     * @return
     * @throws ApplicationException
     */
    public List<CmtNotasOtHhppMgl> findTipoNotasId(BigDecimal tipoNotasId) 
            throws ApplicationException;

     /**
     * Lista notas tipos por el ID.
     *
     * @author Juan David Hernandez
     * @param id
     * @return
     * @throws ApplicationException
     */
    public CmtNotasOtHhppMgl findById(BigDecimal id)
            throws ApplicationException;
    

      /**
     * Crea una nota en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public CmtNotasOtHhppMgl crearNotSol(CmtNotasOtHhppMgl t)
            throws ApplicationException;

      /**
     * Listado de notas por id de hhpp
     *
     * @author Juan David Hernandez
     * @param idHhpp
     * @return
     * @throws ApplicationException
     */
    public List<CmtNotasOtHhppMgl>
            findNotasByOtHhppId(BigDecimal idHhpp)
            throws ApplicationException;
    
    void setUser(String mUser, int mPerfil) throws ApplicationException;
    
}