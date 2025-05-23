/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.FactibilidadMglManager;
import co.com.claro.mgl.businessmanager.address.MglRunableMasivoFactiblidadManager;
import co.com.claro.mgl.dtos.CmtReporteFactibilidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.FactibilidadMgl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;
import javax.ejb.Stateless;
import org.primefaces.model.file.UploadedFile;


/**
 *
 * @author bocanegravm
 */
@Stateless
public class FactibilidadMglFacade implements FactibilidadMglFacadeLocal {

    /**
     * Metodo para crear una factibilidad en BD Autor: Victor Bocanegra
     *
     * @param factibilidadMgl
     * @return {@link FactibilidadMgl}
     * @throws ApplicationException excepcion
     */
    @Override
    public FactibilidadMgl create(FactibilidadMgl factibilidadMgl)
            throws ApplicationException {

        FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.create(factibilidadMgl);
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
    @Override
    public List<FactibilidadMgl> findFactibilidadVigByDetallada(BigDecimal idDetallada, Date fechaHoy) throws ApplicationException {

        FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.findFactibilidadVigByDetallada(idDetallada, fechaHoy);
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
    @Override
    public FactibilidadMgl findFactibilidadVigById(BigDecimal idFactibilidad, Date fechaHoy)
            throws ApplicationException {

        FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.findFactibilidadVigById(idFactibilidad, fechaHoy);
    }
    
        /**
     * Metodo para obtener lista de factibilidades por usuario
     * @cardenaslb
     *
     * @param usuario
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    @Override
     public List<FactibilidadMgl> findFactibilidadByUsuario(String usuario) throws ApplicationException {
         FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.findFactibilidadByUsuario(usuario);
     }
     
      /**
     * Metodo obtener  cantidad de solicitudes por usuario
     *
     * @cardenaslb
     * @param usuario
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    @Override
    public int findNumeroSolicitudes(String usuario) throws ApplicationException {
        FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.findNumeroSolicitudes(usuario);
    }
    
    
    
    /**
     * Metodo obtener todas las factibilidades consultadas por el usuario
     *
     * @cardenaslb
     * @param usuario
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    @Override
    public List<FactibilidadMgl> findFactibilidadByDirDetallada(BigDecimal dirDet) throws ApplicationException {
        FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.findFactibilidadByDirDetallada(dirDet);
    }
    
    /**
     * bocanegravm metodo para calcular la factibilidad de los registros en el
     * archivo
     *
     * @param observer
     * @param uploadedFile   
     * @param usuario
     * @param nombreArchivo
     */
    @Override
    public void calcularFactibilidadMasiva(Observer observer, UploadedFile uploadedFile,
            String usuario, String nombreArchivo) {
        Thread thread = new Thread(new MglRunableMasivoFactiblidadManager(observer, uploadedFile, usuario, nombreArchivo));
        thread.start();
    }

    @Override
    public List<CmtReporteFactibilidadDto> getReporte(HashMap<String, Object> params,  int firstResult, int maxResults)
            throws ApplicationException {
        FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.getReporte(params, firstResult, maxResults);
    }
    
    /**
     * Metodo para consultar una factibilidad por idFactibilidad en BD Autor:
     * Victor Bocanegra
     *
     * @param idFactibilidad
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    @Override
    public co.com.claro.mgl.jpa.entities.FactibilidadMgl findFactibilidadById(BigDecimal idFactibilidad) throws ApplicationException {
        FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.findFactibilidadById(idFactibilidad);
    }
    
     @Override
    public int countgetReporte(HashMap<String, Object> params)
            throws ApplicationException {
        FactibilidadMglManager manager = new FactibilidadMglManager();
        return manager.countgetReporte(params);
    }
}
