/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.VetoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class VetoMglManager {
    
    private static final Logger LOGGER = LogManager.getLogger(VetoMglManager.class);
    VetoMglDaoImpl vetoDaoImpl = new VetoMglDaoImpl();

    public List<VetoMgl> findAllVetoList() throws ApplicationException {
        List<VetoMgl> result;
        result = vetoDaoImpl.findAll();
        return result;
    }
    
      public int countAllVetoList() throws ApplicationException {        
        int result = vetoDaoImpl.countAllVetoList();
        return result;
    }
    
    
    public List<VetoMgl> findAllVetoPaginadaList(int page, int maxResults) 
            throws ApplicationException {
        List<VetoMgl> result;
        
         int firstResult = 0;
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        result = vetoDaoImpl.findAllVetoPaginadaList(firstResult, maxResults );
        return result;
    }


    public VetoMgl createVeto(VetoMgl vetoMgl, String usuario, int perfil)
            throws ApplicationException {
        VetoMgl veto = vetoDaoImpl.createCm(vetoMgl, usuario, perfil);
        return veto;
    }
    
    public VetoMgl updateVeto(VetoMgl vetoMgl, String usuario, int perfil)
            throws ApplicationException {
        VetoMgl veto = vetoDaoImpl.updateCm(vetoMgl, usuario, perfil);
        return veto;
    }
    
    public VetoMgl findByPolitica(String politica)
            throws ApplicationException {
        VetoMgl veto = vetoDaoImpl.findByPolitica(politica);
        return veto;
    }
  
 
       
      /**
     * Valida si un veto se encuentra vigente ya se por ciudad/centro poblado,
     * canal o Nodo
     *
     * @param codigoNodo
     * @param idNodo nodo a validar.
     * @param idGpoCentroPoblado Ciudad o centro poblado a validar.
     * @param idCanalParametro Canal a validar
     * @param tipoVeto 1: Veto para Creacion de Hhpp. 2: para venta 3: para ambos
     * 
     * @author Juan David Hernandez
     * @return false si no existe ningun Veto vigente.
     * @throws co.com.claro.mgl.error.ApplicationException
     */  
    public boolean validarVetoVigente(String codigoNodo,
            BigDecimal idGpoCentroPoblado, String idCanalParametro, String tipoVeto)
            throws ApplicationException {

        List<BigDecimal> idVetoList = new ArrayList();
        if (idGpoCentroPoblado != null) {
            VetoCiudadMglManager vetoCiudadMglManager = new VetoCiudadMglManager();
            /*Obtiene los ids de politicas de veto en los cuales se encuentra la 
             * ciudad/centro poblado buscado para despues validar la vigencia 
             * del veto en fechas. */
            idVetoList = vetoCiudadMglManager
                    .findVetoCiudadByIdGpo(idGpoCentroPoblado);
            if (idVetoList == null || idVetoList.isEmpty()) {
                return false;
            }
        } else {
            if (codigoNodo != null && !codigoNodo.isEmpty()) {
                VetoNodosMglManager vetoNodosMglManager = new VetoNodosMglManager();
                /*Obtiene los ids de politicas de veto en los cuales se encuentra el 
                 * nodo buscado para despues validar la vigencia 
                 * del veto en fechas. */
                idVetoList = vetoNodosMglManager.findVetoNodoByCodigoNodo(codigoNodo.trim());
                if (idVetoList == null || idVetoList.isEmpty()) {
                    return false;
                }
            } else {
                if (idCanalParametro != null && !idCanalParametro.isEmpty()) {
                    VetoCanalMglManager vetoCanalMglManager = new VetoCanalMglManager();
                    /*Obtiene los ids de politicas de veto en los cuales se encuentra el 
                     * canal buscado para despues validar la vigencia 
                     * del veto en fechas. */
                    idVetoList = vetoCanalMglManager
                            .findVetoCanalByIdCanalParametro(idCanalParametro);
                    if (idVetoList == null || idVetoList.isEmpty()) {
                        return false;
                    }
                }
            }
        }

        /*Si se obtuvieron vetos procedemos a realizar la validación 
         * de la vigencia de la fecha del listado de vetos encontrados*/
        if (idVetoList.isEmpty()) {
            return false;
        } else {
            /*validamos si los ids de politicas de veto se encuentran con fecha vigente*/
            Date fechaActual = new Date();

            SimpleDateFormat formatoFechaFin = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFins = formatoFechaFin.format(fechaActual);
            Date fechaFinDate = null;
            try {
                fechaFinDate = formatoFechaFin.parse(fechaFins);
            } catch (ParseException e) {
                LOGGER.error("Error en validarVetoVigente. " +e.getMessage(), e);  
            }

            for (BigDecimal vetoId : idVetoList) {
                //Realiza busqueda del veto encontrado
                List<VetoMgl> vetoMglList = vetoDaoImpl.validarVigenciaVeto(fechaFinDate, tipoVeto, vetoId);                
                 //Si existe un Veto vigente con la fecha actual
                if(vetoMglList != null && !vetoMglList.isEmpty()){
                    return true;
                }
            }            
            return false;           
        }
    }
}
