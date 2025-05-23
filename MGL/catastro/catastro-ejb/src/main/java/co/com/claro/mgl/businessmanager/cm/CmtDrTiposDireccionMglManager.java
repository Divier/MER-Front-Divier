/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.DrTipoDireccionDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrTipoDireccion;
import java.util.List;

/**
 *
 * @author camargomf
 */
public class CmtDrTiposDireccionMglManager {

    DrTipoDireccionDaoImpl drTipoDireccionDaoImpl 
            = new DrTipoDireccionDaoImpl();

    public List<DrTipoDireccion> findAll() throws ApplicationException {
        List<DrTipoDireccion> result;
        result = drTipoDireccionDaoImpl.findAll();
        return result;
    }
}
