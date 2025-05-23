/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtUnidadesPreviasMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesPreviasMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author ADMIN
 */
@Stateless
public class CmtUnidadesPreviasMglFacade implements CmtUnidadesPreviasMglFacadeLocal {

    private CmtUnidadesPreviasMglManager previasMglManager = new CmtUnidadesPreviasMglManager();
    private String user = "";
    private int perfil = 0;

    @Override
    public CmtUnidadesPreviasMgl create(CmtUnidadesPreviasMgl previasMgl) throws ApplicationException {
        return previasMglManager.create(previasMgl, user, perfil);
    }

    @Override
    public CmtUnidadesPreviasMgl update(CmtUnidadesPreviasMgl previasMgl) throws ApplicationException {
        return previasMglManager.update(previasMgl, user, perfil);
    }

    @Override
    public boolean delete(CmtUnidadesPreviasMgl previasMgl) throws ApplicationException {
        return previasMglManager.delete(previasMgl, user, perfil);
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
    public List<CmtUnidadesPreviasMgl> updateList(List<CmtUnidadesPreviasMgl> listUnidadesPrevias,
            String user, int perfil) throws ApplicationException {
        return previasMglManager.updateList(listUnidadesPrevias, user, perfil);
    }

    @Override
    public List<CmtUnidadesPreviasMgl> createList(List<CmtUnidadesPreviasMgl> listUnidadesPrevias,
            String user, int perfil) throws ApplicationException {
        return previasMglManager.createList(listUnidadesPrevias, user, perfil);
    }
}
