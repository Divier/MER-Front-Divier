/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.RrNodosDthDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrNodosDth;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class RrNodosDthManager {

    public List<RrNodosDth> findNodosDthByCodCiudad(String codCiudad) throws ApplicationException {
        RrNodosDthDaoImpl nodosDaoImpl = new RrNodosDthDaoImpl();
        return nodosDaoImpl.findNodosDthByCodCiudad(codCiudad);

    }
}
