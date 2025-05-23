/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.FactibilidadMglDaoImpl;
import co.com.claro.mgl.dtos.CmtReporteFactibilidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.FactibilidadMgl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class FactibilidadMglManager {

    /**
     * Metodo para crear una factibilidad en BD Autor: Victor Bocanegra
     *
     * @param factibilidadMgl
     * @return {@link FactibilidadMgl}
     * @throws ApplicationException excepcion
     */
    public FactibilidadMgl create(FactibilidadMgl factibilidadMgl)
            throws ApplicationException {

        FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();
        return dao.create(factibilidadMgl);
    }

    /**
     * Metodo para consultar una factibilidad vigente en BD Autor: Victor
     * Bocanegra
     *
     * @param idDetallada
     * @param fechaHoy
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<FactibilidadMgl> findFactibilidadVigByDetallada(BigDecimal idDetallada, Date fechaHoy) throws ApplicationException {

        FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();
        return dao.findFactibilidadVigByDetallada(idDetallada, fechaHoy);
    }

    /**
     * Metodo para consultar una factibilidad vigente en BD Autor: Victor
     * Bocanegra
     *
     * @param idFactibilidad
     * @param fechaHoy
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public FactibilidadMgl findFactibilidadVigById(BigDecimal idFactibilidad, Date fechaHoy)
            throws ApplicationException {

        FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();
        return dao.findFactibilidadVigById(idFactibilidad, fechaHoy);

    }
    
        /**
     * Metodo para obtener lista de factibilidades por usuario
     * @cardenaslb
     *
     * @param usuario
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<FactibilidadMgl> findFactibilidadByUsuario(String usuario) throws ApplicationException {
        FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();

        return dao.findFactibilidadByUsuario(usuario);
    }

    /**
     * Metodo para obtener cantidad de solicitudes por usuario
     *
     * @cardenaslb
     *
     * @param usuario
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public int findNumeroSolicitudes(String usuario) throws ApplicationException {
        FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();
        return dao.findNumeroSolicitudes(usuario);

    }

    public List<FactibilidadMgl> findFactibilidadByDirDetallada(BigDecimal dirDet) throws ApplicationException {
        FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();
        return dao.findFactibilidadByDirDetallada(dirDet);

    }
    
     public List<CmtReporteFactibilidadDto> getReporte(HashMap<String, Object> params, int firstResult, int maxResults)
            throws ApplicationException{
         FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();
         
        return dao.getReporte(params, firstResult, maxResults);
             }
             
    /**
     * Metodo para consultar una factibilidad por idFactibilidad en BD Autor: Victor
     * Bocanegra
     *
     * @param idFactibilidad
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public FactibilidadMgl findFactibilidadById(BigDecimal idFactibilidad) throws ApplicationException {

        FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();
        return dao.findFactibilidadById(idFactibilidad);
    }
    
    public int countgetReporte(HashMap<String, Object> params)
            throws ApplicationException {
        FactibilidadMglDaoImpl dao = new FactibilidadMglDaoImpl();

        return dao.countgetReporte(params);
    }

}
