/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dao.impl.RazonesOtDireccionesDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglRazonesOtDireccion;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import java.util.List;

/**
 *
 * @author Orlando Velasquez
 */
public class RazonesOtDireccionManager extends GenericDaoImpl<MglRazonesOtDireccion> {

    /**
     * Metodo para crear la razon de la Ot Direccion
     *
     * @param razonOtDireccion objeto a ser persistido
     * @param user
     * @param perfil
     * @Author Orlando Velasquez
     * @return razon creada
     * @throws ApplicationException Excepci&oacute;n lanzada en la insercion
     */
    public MglRazonesOtDireccion create(MglRazonesOtDireccion razonOtDireccion, String user, Integer perfil)
            throws ApplicationException {

        RazonesOtDireccionesDaoImpl dao = new RazonesOtDireccionesDaoImpl();
        return dao.createCm(razonOtDireccion, user, perfil);

    }

    /**
     * Metodo para crear la razon de la Ot Direccion
     *
     * @param tipoOtDirecciones
     * @Author Orlando Velasquez
     * @return razon creada
     * @throws ApplicationException Excepci&oacute;n lanzada en la insercion
     */
    public List<MglRazonesOtDireccion> findRazonesOtDireccionByTipoOTDirecciones(
            TipoOtHhppMgl tipoOtDirecciones)
            throws ApplicationException {

        RazonesOtDireccionesDaoImpl dao = new RazonesOtDireccionesDaoImpl();
        return dao.findRazonesOtDireccionByTipoOTDirecciones(tipoOtDirecciones);

    }

    /**
     * Metodo para crear la razon de la Ot Direccion
     *
     * @param tipoOtDirecciones
     * @param user
     * @param perfil
     * @Author Orlando Velasquez
     * @throws ApplicationException Excepci&oacute;n lanzada en la insercion
     */
    public void eliminarRazonOtDirecciones(MglRazonesOtDireccion tipoOtDirecciones,
             String user, Integer perfil)
            throws ApplicationException {

        RazonesOtDireccionesDaoImpl dao = new RazonesOtDireccionesDaoImpl();
        dao.deleteCm(tipoOtDirecciones, user, perfil);

    }

}
