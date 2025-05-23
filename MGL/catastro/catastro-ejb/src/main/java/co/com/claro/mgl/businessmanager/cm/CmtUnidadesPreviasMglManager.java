/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtUnidadesPreviasMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesPreviasMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtUnidadesPreviasMglManager {

    private CmtUnidadesPreviasMglDaoImpl previasMglDaoImpl = new CmtUnidadesPreviasMglDaoImpl();

    public CmtUnidadesPreviasMgl create(CmtUnidadesPreviasMgl previasMgl, String user, int perfil) throws ApplicationException {
        return previasMglDaoImpl.createCm(previasMgl, user, perfil);
    }
    public CmtUnidadesPreviasMgl update(CmtUnidadesPreviasMgl previasMgl, String user, int perfil) throws ApplicationException {
        return previasMglDaoImpl.updateCm(previasMgl, user, perfil);
    }
    public boolean delete(CmtUnidadesPreviasMgl previasMgl, String user, int perfil) throws ApplicationException {
        return previasMglDaoImpl.deleteCm(previasMgl, user, perfil);
    }
    
    public List<CmtUnidadesPreviasMgl> updateList(List<CmtUnidadesPreviasMgl> listUnidadesPrevias, 
            String user, int perfil) throws ApplicationException{
        for(CmtUnidadesPreviasMgl previasMgl:listUnidadesPrevias){
             previasMgl.setJustificacion("EDICION");
            previasMgl=update(previasMgl, user, perfil);
        }
        return listUnidadesPrevias;
    }

    public List<CmtUnidadesPreviasMgl> createList(List<CmtUnidadesPreviasMgl> listUnidadesPrevias, 
            String user, int perfil) throws ApplicationException{
        for(CmtUnidadesPreviasMgl previasMgl:listUnidadesPrevias){
            previasMgl.setJustificacion("CREACION");
            previasMgl=create(previasMgl, user, perfil);
        }
        return listUnidadesPrevias;
    }    
}
