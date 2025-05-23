/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoMgl;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface VetoMglFacadeLocal extends BaseFacadeLocal<VetoMgl>{
    
    public void setUser(String mUser, int mPerfil) throws ApplicationException;
    public List<VetoMgl> findAllVetoList() throws ApplicationException;
    public List<VetoMgl> findAllVetoPaginadaList
            (int page, int maxResults) throws ApplicationException;
    public int countAllVetoList() throws ApplicationException;
    
    public VetoMgl findByPolitica(String politica) throws ApplicationException;
     
    /**
     * Crea un veto en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public VetoMgl crearVeto(VetoMgl t)
            throws ApplicationException;
    
     /**
     * actualiza un veto en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public VetoMgl updateVeto(VetoMgl t)
            throws ApplicationException;
}
