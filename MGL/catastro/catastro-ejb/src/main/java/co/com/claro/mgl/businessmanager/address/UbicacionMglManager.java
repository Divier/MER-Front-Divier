/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.UbicacionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UbicacionMglManager {

    public List<UbicacionMgl> findAll() throws ApplicationException {

        List<UbicacionMgl> resulList;
        UbicacionMglDaoImpl ubicacionMglDaoImpl = new UbicacionMglDaoImpl();

        resulList = ubicacionMglDaoImpl.findAll();

        return resulList;
    }

    public UbicacionMgl findById(UbicacionMgl sqlData) throws ApplicationException {
        UbicacionMgl resul;
        UbicacionMglDaoImpl ubicacionMglDaoImpl = new UbicacionMglDaoImpl();
        resul = ubicacionMglDaoImpl.find(sqlData.getUbiId());
        return resul;
    }
    
    public UbicacionMgl create(UbicacionMgl ubicacionMgl) throws ApplicationException {
        UbicacionMglDaoImpl ubicacionMglDaoImpl = new UbicacionMglDaoImpl();
        return ubicacionMglDaoImpl.create(ubicacionMgl);
    }
    
      public boolean actualizarCoordenadasGeoDireccionMgl(UbicacionMgl ubicacionMgl) throws ApplicationException {
        UbicacionMglDaoImpl ubicacionMglDaoImpl = new UbicacionMglDaoImpl();
        return ubicacionMglDaoImpl.actualizarCoordenadasGeoDireccionMgl(ubicacionMgl);
    }
      
    /**
     * Metodo para consultar una ubicacion por centro poblado- longitud and
     * latitud
     *
     * @param centro centro oblado de la direccion.
     * @param longitud de la direccion.
     * @param latitud de la direccion.
     * @return UbicacionMgl
     *
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public UbicacionMgl findByCentroAndLongAndLat(BigDecimal centro, String longitud,
            String latitud) throws ApplicationException {
        UbicacionMglDaoImpl ubicacionMglDaoImpl = new UbicacionMglDaoImpl();
        return ubicacionMglDaoImpl.findByCentroAndLongAndLat(centro, longitud, latitud);
    }
}
