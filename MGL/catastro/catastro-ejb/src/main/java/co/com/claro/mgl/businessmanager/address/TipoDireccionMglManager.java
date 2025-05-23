/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.TipoDireccionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoDireccionMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TipoDireccionMglManager {

    public List<TipoDireccionMgl> findAll() throws ApplicationException {
        List<TipoDireccionMgl> resulList;
        TipoDireccionMglDaoImpl tipoDireccionMglDaoImpl = new TipoDireccionMglDaoImpl();
        resulList = tipoDireccionMglDaoImpl.findAll();
        return resulList;
    }
    public TipoDireccionMgl findByid(String idTdir) throws ApplicationException {
        TipoDireccionMglDaoImpl tipoDireccionMglDaoImpl = new TipoDireccionMglDaoImpl();
        return tipoDireccionMglDaoImpl.findId(idTdir);
    }
}
