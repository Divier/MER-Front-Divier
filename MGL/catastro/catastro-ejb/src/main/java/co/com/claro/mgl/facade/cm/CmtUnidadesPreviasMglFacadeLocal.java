/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesPreviasMgl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface CmtUnidadesPreviasMglFacadeLocal {

    CmtUnidadesPreviasMgl create(CmtUnidadesPreviasMgl previasMgl) throws ApplicationException;

    CmtUnidadesPreviasMgl update(CmtUnidadesPreviasMgl previasMgl) throws ApplicationException;

    boolean delete(CmtUnidadesPreviasMgl previasMgl) throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    public List<CmtUnidadesPreviasMgl> updateList(List<CmtUnidadesPreviasMgl> listUnidadesPrevias,
            String user, int perfil) throws ApplicationException;

    public List<CmtUnidadesPreviasMgl> createList(List<CmtUnidadesPreviasMgl> listUnidadesPrevias,
            String user, int perfil) throws ApplicationException;
}
