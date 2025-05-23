/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.TipoHhppRedMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppRedMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TipoHhppRedMglManager {

    public List<TipoHhppRedMgl> findAll() throws ApplicationException {

        List<TipoHhppRedMgl> resulList;
        TipoHhppRedMglDaoImpl tipoHhppRedMglDaoImpl = new TipoHhppRedMglDaoImpl();

        resulList = tipoHhppRedMglDaoImpl.findAll();

        return resulList;
    }
    /**
     * Permite la busqueda por id de los tipos de Red
     *
     *
     * @param id id de la tabla tipo Red
     * @return TipoHhppRedMgl.
     * @throws ApplicationException
     */
    public TipoHhppRedMgl findById(BigDecimal id) throws ApplicationException {
        TipoHhppRedMgl resulList;
        TipoHhppRedMglDaoImpl tipoHhppRedMglDaoImpl = new TipoHhppRedMglDaoImpl();
        resulList = tipoHhppRedMglDaoImpl.find(id);
        return resulList;
    }
    
}
