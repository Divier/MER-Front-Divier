/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.ComunidadRrManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class ComunidadMglFacade implements ComunidadMglFacadeLocal {

    ComunidadRrManager comunidadRrManager;
    private String user;
    private Integer perfil;

    public ComunidadMglFacade() {
        this.comunidadRrManager = new ComunidadRrManager();
        this.user = "";
        this.perfil = 0;
    }
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException{
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
    @Override
    public PaginacionDto<CmtComunidadRr> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaComunidadDto consulta) throws ApplicationException {
        return comunidadRrManager.findAllPaginado(paginaSelected, maxResults, consulta);
    }

    @Override
    public List<CmtComunidadRr> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtComunidadRr create(CmtComunidadRr comunidadRr) throws ApplicationException {
        return comunidadRrManager.create(comunidadRr, user, perfil);
    }

    @Override
    public CmtComunidadRr update(CmtComunidadRr comunidadRr) throws ApplicationException {
        return comunidadRrManager.update(comunidadRr, user, perfil);
    }

    @Override
    public boolean delete(CmtComunidadRr comunidadRr) throws ApplicationException {
        return comunidadRrManager.delete(comunidadRr, user, perfil);
    }
    
    @Override
    public CmtComunidadRr findById(CmtComunidadRr sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
