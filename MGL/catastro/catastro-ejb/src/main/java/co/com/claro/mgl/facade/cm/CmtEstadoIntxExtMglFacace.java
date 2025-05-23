/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtEstadoIntxExtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Relacion de Estados Internos y Externos. Expone la logica de negocio
 * de la relacion de estados internos y externos en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtEstadoIntxExtMglFacace implements CmtEstadoIntxExtMglFacaceLocal {

        
    private String user = "";
    private int perfil = 0;
    
    @Override
    public List<CmtEstadoIntxExtMgl> findAll() throws ApplicationException {
        CmtEstadoIntxExtMglManager manager = new CmtEstadoIntxExtMglManager();
        return manager.findAll();
    }

    @Override
    public CmtEstadoIntxExtMgl createEstadoIntxExtMgl(CmtEstadoIntxExtMgl t, String user, int perfil) throws ApplicationException {
       CmtEstadoIntxExtMglManager manager = new CmtEstadoIntxExtMglManager();
        return manager.createEstadoIntxExtMgl(t, user, perfil);
    }
    
    @Override
    public CmtEstadoIntxExtMgl updateEstadoIntxExtMgl(CmtEstadoIntxExtMgl t, String user, int perfil) throws ApplicationException {
        CmtEstadoIntxExtMglManager manager = new CmtEstadoIntxExtMglManager();
        return manager.updateEstadoIntxExtMgl(t, user, perfil);
    }

    @Override
    public CmtEstadoIntxExtMgl update(CmtEstadoIntxExtMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CmtEstadoIntxExtMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtEstadoIntxExtMgl findById(CmtEstadoIntxExtMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
    
    @Override
    public CmtEstadoIntxExtMgl create(CmtEstadoIntxExtMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    /**
     * Obtiene la relacion estado interno externo. Obtiene la relacion estado
     * interno por estado externo por medio del estado interno.
     *
     * @author Johnnatan Ortiz
     * @param estadoInterno Estado Interno
     * @return Relacion de estado Interno y Externo
     * @throws ApplicationException
     */
    @Override
    public CmtEstadoIntxExtMgl findByEstadoInterno(CmtBasicaMgl estadoInterno) throws ApplicationException {
        CmtEstadoIntxExtMglManager manager = new CmtEstadoIntxExtMglManager();
        return manager.findByEstadoInterno(estadoInterno);
    }
    
        /**
     *
     * @author Juan David Hernandez 
     * @param estadoInterno Estado Interno
     * @param estadoExterno
     * @return Relacion de estado Interno y Externo
     * @throws ApplicationException
     */
    @Override
    public CmtEstadoIntxExtMgl findByEstadoInternoXExterno(BigDecimal estadoInterno, 
            BigDecimal estadoExterno, int estadoRegistro) throws ApplicationException {
        CmtEstadoIntxExtMglManager manager = new CmtEstadoIntxExtMglManager();
        return manager.findByEstadoInternoXExterno(estadoInterno, estadoExterno, estadoRegistro);
    }


    @Override
    public List<CmtEstadoIntxExtMgl> findAllEstadoInternoExternoList(int firstResult, int maxResults) throws ApplicationException {
        CmtEstadoIntxExtMglManager manager = new CmtEstadoIntxExtMglManager();
        return manager.findAllEstadoInternoExternoList(firstResult, maxResults);
    }
    
    @Override
    public int countEstadoInternoExterno() throws ApplicationException {
        CmtEstadoIntxExtMglManager manager = new CmtEstadoIntxExtMglManager();
        return manager.countEstadoInternoExterno();
    }
    
 

}
