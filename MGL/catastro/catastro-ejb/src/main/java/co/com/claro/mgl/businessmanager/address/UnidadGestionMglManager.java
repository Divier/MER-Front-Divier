/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.UnidadGestionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.UnidadGestionMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UnidadGestionMglManager {

    public List<UnidadGestionMgl> findAll() throws ApplicationException {

        List<UnidadGestionMgl> resulList;
        UnidadGestionMglDaoImpl UnidadGestionMglDaoImpl = new UnidadGestionMglDaoImpl();

        resulList = UnidadGestionMglDaoImpl.findAll();

        return resulList;
    }
}