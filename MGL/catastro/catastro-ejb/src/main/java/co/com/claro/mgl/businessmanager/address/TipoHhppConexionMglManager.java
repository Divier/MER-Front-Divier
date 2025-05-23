/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.TipoHhppConexionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppConexionMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TipoHhppConexionMglManager {
        public List<TipoHhppConexionMgl> findAll() throws ApplicationException {

        List<TipoHhppConexionMgl> resulList;
        TipoHhppConexionMglDaoImpl tipoHhppConexionMglDaoImpl = new TipoHhppConexionMglDaoImpl();

        resulList = tipoHhppConexionMglDaoImpl.findAll();

        return resulList;
    }
        
        
    /**
     * Permite la busqueda por id de los tipos de coneccion
     *
     *
     * @param id id de la tabla tipo conexion
     * @return TipoHhppConexionMgl.
     * @throws ApplicationException
     */
    public TipoHhppConexionMgl findById(BigDecimal id) throws ApplicationException {
        TipoHhppConexionMgl resulList;
        TipoHhppConexionMglDaoImpl tipoHhppConexionMglDaoImpl = new TipoHhppConexionMglDaoImpl();
        resulList = tipoHhppConexionMglDaoImpl.find(id);
        return resulList;
    }
        
}
