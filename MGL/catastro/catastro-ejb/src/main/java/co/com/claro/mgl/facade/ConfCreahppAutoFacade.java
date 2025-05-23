/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.ConfCreahppAutoManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ConfCreahppAuto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * {@inheritDoc}
 */
@Stateless
public class ConfCreahppAutoFacade implements ConfCreahppAutoFacadeLocal {

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfCreahppAuto findByIdCity(GeograficoPoliticoMgl city)
            throws ApplicationException {
        ConfCreahppAutoManager manager = new ConfCreahppAutoManager();
        return manager.findByIdCity(city);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfCreahppAuto findByCodComunidad(String codComunidad)
            throws ApplicationException {
        ConfCreahppAutoManager manager = new ConfCreahppAutoManager();
        return manager.findByCodComunidad(codComunidad);
    }

    @Override
    public List<ConfCreahppAuto> findAll() throws ApplicationException {
        ConfCreahppAutoManager manager = new ConfCreahppAutoManager();
        return manager.findAll();
    }

    @Override
    public ConfCreahppAuto create(ConfCreahppAuto t) throws ApplicationException {
         ConfCreahppAutoManager manager = new ConfCreahppAutoManager();
        return manager.createConfiguracion(t);
    }

    @Override
    public ConfCreahppAuto update(ConfCreahppAuto t) throws ApplicationException {
        ConfCreahppAutoManager manager = new ConfCreahppAutoManager();
        return manager.updateConfiguracion(t);
    }

    @Override
    public boolean delete(ConfCreahppAuto t) throws ApplicationException {
        ConfCreahppAutoManager manager = new ConfCreahppAutoManager();
        return manager.deleteConfiguracion(t);
    }

    @Override
    public ConfCreahppAuto findById(ConfCreahppAuto sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
