/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglRazonesOtDireccion;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import java.util.List;

/**
 *
 * @author User
 */
public interface RazonesOtDireccionMglFacadeLocal extends BaseFacadeLocal<MglRazonesOtDireccion> {

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    public List<MglRazonesOtDireccion> findRazonesOtDireccionByTipoOTDirecciones(
            TipoOtHhppMgl tipoOtDirecciones) throws ApplicationException;

    public void eliminarRazonOtDirecciones(MglRazonesOtDireccion tipoOtDirecciones) throws ApplicationException;

}
