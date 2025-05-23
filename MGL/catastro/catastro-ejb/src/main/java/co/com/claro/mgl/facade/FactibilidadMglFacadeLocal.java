/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CmtReporteFactibilidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.FactibilidadMgl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;
import org.primefaces.model.file.UploadedFile;


/**
 *
 * @author bocanegravm
 */
public interface FactibilidadMglFacadeLocal {

    /**
     * Metodo para crear una factibilidad en BD Autor: Victor Bocanegra
     *
     * @param factibilidadMgl
     * @return {@link FactibilidadMgl}
     * @throws ApplicationException excepcion
     */
    FactibilidadMgl create(FactibilidadMgl factibilidadMgl)
            throws ApplicationException;

    /**
     * Metodo para consultar una factibilidad vigente en BD Autor: Victor
     * Bocanegra
     *
     * @param idDetallada
     * @param fechaHoy
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    List<FactibilidadMgl> findFactibilidadVigByDetallada(BigDecimal idDetallada, Date fechaHoy) throws ApplicationException;

    /**
     * Metodo para consultar una factibilidad vigente en BD Autor: Victor
     * Bocanegra
     *
     * @param idFactibilidad
     * @param fechaHoy
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    FactibilidadMgl findFactibilidadVigById(BigDecimal idFactibilidad, Date fechaHoy)
            throws ApplicationException;

    
        /**
     * Metodo obtener una lista de factibilidades creadas por el usuario
     * @cardenaslb
     * @param usuario
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    List<FactibilidadMgl> findFactibilidadByUsuario(String usuario) throws ApplicationException;
    
    
    
    
        /**
     * Metodo obtener  cantidad de solicitudes por usuario
     * @cardenaslb
     * @param usuario
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
   int findNumeroSolicitudes(String usuario) throws ApplicationException;
   
   
   
        /**
     * Metodo obtener todas las  factibilidades consultadas  por el usuario
     * @param dirDet
     * @cardenaslb
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    List<FactibilidadMgl> findFactibilidadByDirDetallada(BigDecimal dirDet) throws ApplicationException;
    
                /**
     * bocanegravm metodo para calcular la factibilidad de los registros
     * en el archivo
     *
     * @param observer
     * @param uploadedFile   
     * @param usuario
     * @param nombreArchivo
     */
    void calcularFactibilidadMasiva(Observer observer, UploadedFile uploadedFile, 
            String usuario, String nombreArchivo);
    
    
      /**
     * Metodo para consultar una factibilidad vigente en BD 
     * @param params
     * @param idCentroPobladoList
     * @cardenaslb
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    List<CmtReporteFactibilidadDto> getReporte(HashMap<String, Object> params, int firstResult, int maxResults)
            throws ApplicationException;
    
        /**
     * Metodo para consultar una factibilidad por idFactibilidad en BD Autor:
     * Victor Bocanegra
     *
     * @param idFactibilidad
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    co.com.claro.mgl.jpa.entities.FactibilidadMgl findFactibilidadById(BigDecimal idFactibilidad) throws ApplicationException;
    
    int countgetReporte(HashMap<String, Object> params)
            throws ApplicationException;

}
