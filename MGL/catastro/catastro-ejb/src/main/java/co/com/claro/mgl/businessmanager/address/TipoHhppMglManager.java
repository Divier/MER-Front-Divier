/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.TipoHhppMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TipoHhppMglManager {

    public List<TipoHhppMgl> findAll() throws ApplicationException {

        List<TipoHhppMgl> resulList;
        TipoHhppMglDaoImpl tipoHhppMglDaoImpl = new TipoHhppMglDaoImpl();

        resulList = tipoHhppMglDaoImpl.findAll();

        return resulList;
    }
    
    /**
     * Permite la busqueda por id de los tipos de Hhpp
     *
     *
     * @param id id de la tabla tipo Hhpp
     * @return TipoHhppMgl.
     * @throws ApplicationException
     */
    public TipoHhppMgl findById(String id) throws ApplicationException {
        TipoHhppMgl resulList;
        TipoHhppMglDaoImpl tipoHhppMglDaoImpl = new TipoHhppMglDaoImpl();
        resulList = tipoHhppMglDaoImpl.find(id);
        return resulList;
    }
    
}
