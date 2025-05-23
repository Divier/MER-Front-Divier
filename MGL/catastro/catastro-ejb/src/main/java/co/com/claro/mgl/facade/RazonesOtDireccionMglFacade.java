/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.RazonesOtDireccionManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglRazonesOtDireccion;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Orlando Velasquez
 */
@Stateless
public class RazonesOtDireccionMglFacade implements RazonesOtDireccionMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<MglRazonesOtDireccion> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MglRazonesOtDireccion create(MglRazonesOtDireccion razonOtDireccion) throws ApplicationException {
        RazonesOtDireccionManager razonesOtDireccionManager = new RazonesOtDireccionManager();
        return razonesOtDireccionManager.createCm(razonOtDireccion, user, perfil);
    }

    @Override
    public MglRazonesOtDireccion update(MglRazonesOtDireccion t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(MglRazonesOtDireccion t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MglRazonesOtDireccion findById(MglRazonesOtDireccion sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MglRazonesOtDireccion> findRazonesOtDireccionByTipoOTDirecciones(TipoOtHhppMgl tipoOtDirecciones) throws ApplicationException {
        RazonesOtDireccionManager razonesOtDireccionManager = new RazonesOtDireccionManager();
        return razonesOtDireccionManager.findRazonesOtDireccionByTipoOTDirecciones(tipoOtDirecciones);
    }

    @Override
    public void eliminarRazonOtDirecciones(MglRazonesOtDireccion tipoOtDirecciones) throws ApplicationException {
        RazonesOtDireccionManager razonesOtDireccionManager = new RazonesOtDireccionManager();
        razonesOtDireccionManager.eliminarRazonOtDirecciones(tipoOtDirecciones, user, perfil);
    }

}
