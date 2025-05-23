/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.EstadoHhppMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import java.util.List;

/**
 *
 * @author User
 */
public class EstadoHhppMglManager {

    public List<EstadoHhppMgl> findAll() throws ApplicationException {

        List<EstadoHhppMgl> resulList;
        EstadoHhppMglDaoImpl estadoHhppMglDaoImpl = new EstadoHhppMglDaoImpl();

        resulList = estadoHhppMglDaoImpl.findAll();

        return resulList;
    }
    
    
    
    /**
     * Realiza la busqueda de un estado de Hhpp por id String 
     * direcciones.
     *
     * @param id
     * @return EstadoHhppMgl
     * @throws ApplicationException
     */
    public EstadoHhppMgl find(String id) throws ApplicationException {

        EstadoHhppMgl resulList;
        EstadoHhppMglDaoImpl estadoHhppMglDaoImpl = new EstadoHhppMglDaoImpl();
        resulList = estadoHhppMglDaoImpl.find(id);

        return resulList;
    }
    
    /**
     * Realiza una actaulización de un estado de Hhpp.
     *
     * @param estadoHhppMgl - objeto para ctualizar
     * @return EstadoHhppMgl - objeto actualizado
     * @throws ApplicationException is hay un error de update
     */
    public EstadoHhppMgl update(EstadoHhppMgl estadoHhppMgl) 
            throws ApplicationException {

        EstadoHhppMglDaoImpl estadoHhppMglDaoImpl = new EstadoHhppMglDaoImpl();
        return estadoHhppMglDaoImpl.update(estadoHhppMgl);
    }
    
    public EstadoHhppMgl findByIdEstHhpp(String ehhID) throws ApplicationException {

        EstadoHhppMglDaoImpl estadoHhppMglDaoImpl = new EstadoHhppMglDaoImpl();
        return estadoHhppMglDaoImpl.findByIdEstHhpp(ehhID);

    }

    public EstadoHhppMgl findByNameEstHhpp(String nombre) throws ApplicationException {
        
        EstadoHhppMglDaoImpl estadoHhppMglDaoImpl = new EstadoHhppMglDaoImpl();
        return estadoHhppMglDaoImpl.findByNameEstHhpp(nombre);
    }
}
