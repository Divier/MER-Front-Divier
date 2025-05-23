/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtReglaEstadoCmMglManager;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaEstadoCmMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class CmtReglaEstadoCmMglFacade implements CmtReglaEstadoCmMglFacadeLocal{

    private String user = "";
    private int perfil = 0;
    CmtReglaEstadoCmMglManager cmtReglaEstadoCmMglManager = new CmtReglaEstadoCmMglManager();



    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<CmtReglaEstadoCmMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtReglaEstadoCmMgl create(CmtReglaEstadoCmMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtReglaEstadoCmMgl update(CmtReglaEstadoCmMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CmtReglaEstadoCmMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtReglaEstadoCmMgl findById(CmtReglaEstadoCmMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    /*
     * Angel Gonzalez, Estados combinados en MER de MGL_INSPIRA : 23814 
     * Migrando cambios de estados combinados
    */
    @Override
    public List<CmtReglaEstadoCmMgl> findAllByStates(int ruleState) throws ApplicationException {
        return cmtReglaEstadoCmMglManager.findAllByState(ruleState);
    }

    @Override
    public int countRules(String tipoFiltro, String contentFiltro) throws ApplicationException {
        return cmtReglaEstadoCmMglManager.countRules(tipoFiltro,contentFiltro);
    }

    @Override
    public String addRule(List<CmtReglaEstadoCmMgl> combinedState) throws ApplicationException {
        return cmtReglaEstadoCmMglManager.addRule(combinedState, user, perfil);
    }

    @Override
    public Boolean disabledRule(String ruleNumber) throws ApplicationException {
        return cmtReglaEstadoCmMglManager.disabledRule(ruleNumber);
    }

    @Override
    public List<CmtReglaEstadoCmMgl> findRulesPaginacion(int page, int filasPag, int state, String tipoFiltro, String contentFiltro) throws ApplicationException {
        return cmtReglaEstadoCmMglManager.findRulesPaginacion(page, filasPag, state, tipoFiltro, contentFiltro);
    }

    
}
