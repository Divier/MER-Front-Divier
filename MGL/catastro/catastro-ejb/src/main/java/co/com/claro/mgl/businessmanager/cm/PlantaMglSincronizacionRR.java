/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRPlantaRequest;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRPlantaResponse;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientBasicMaintenanceRRPlants;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PlantaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 *
 * @author JPeña
 */
public class PlantaMglSincronizacionRR {
    ParametrosMglManager parametrosMglManager;
    RestClientBasicMaintenanceRRPlants restClientBasicMaintenanceRRPlants;
    String BASE_URI;

    
     public PlantaMglSincronizacionRR() {
        try{
        parametrosMglManager = new ParametrosMglManager();
        BASE_URI = parametrosMglManager.findByAcronimo(
                ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                .iterator().next().getParValor();
        restClientBasicMaintenanceRRPlants = 
                new RestClientBasicMaintenanceRRPlants(BASE_URI);
        }
        catch(ApplicationException ex){
            LOGGER.error("Error al llamar pcml " + ex.getMessage(), ex);
        }
    }
    private static final Logger LOGGER = LogManager.getLogger(PlantaMglSincronizacionRR.class);
    
    // crud de RR
    /**
     * Metodo para realizar la insercion de una configuracion de planta
     * a RR por pcml
     * 
     * @param plantaMgl
     * @param PFHTYP
     * @param PFHEND
     * @return boolean define si la operacion se realizao correctamente o no
     * @throws ApplicationException
     */
    public  boolean createPlantaRR(PlantaMgl plantaMgl, String PFHTYP, String PFHEND) throws ApplicationException {
        try {
            MantenimientoBasicoRRPlantaRequest mantenimientoBasicoRRPlantaRequest
                    = new MantenimientoBasicoRRPlantaRequest();
            mantenimientoBasicoRRPlantaRequest.setIDUSER(plantaMgl.getUsuarioCreacion());
            mantenimientoBasicoRRPlantaRequest.setPFHTYP(PFHTYP);           //verificando por hits
            mantenimientoBasicoRRPlantaRequest.setPFHEND(PFHEND);           //verificando por hits
            mantenimientoBasicoRRPlantaRequest.setPFSTYP( plantaMgl.getLocationtype());
            mantenimientoBasicoRRPlantaRequest.setPFSNOD( plantaMgl.getLocationcode());
            if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("HE")==0){
                mantenimientoBasicoRRPlantaRequest.setPFRTYP("");
                mantenimientoBasicoRRPlantaRequest.setPFRNOD("");
            }else if ((plantaMgl.getLocationtype().trim().compareToIgnoreCase("CT")==0)||
                    (plantaMgl.getLocationtype().trim().compareToIgnoreCase("ND")==0)){
                mantenimientoBasicoRRPlantaRequest.setPFRTYP(plantaMgl.getConfiguracionplantaparentid().getLocationtype());
                mantenimientoBasicoRRPlantaRequest.setPFRNOD(plantaMgl.getConfiguracionplantaparentid().getLocationcode());
            }else{
                throw new ApplicationException("Error tipo de Planta a crear no concuerda con los tipos "+plantaMgl.getLocationtype());
            }
            mantenimientoBasicoRRPlantaRequest.setPFDESC(plantaMgl.getDescription());
            mantenimientoBasicoRRPlantaRequest.setPF1DYNP(String.format("%03d",plantaMgl.getHour24().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF2DYNP(String.format("%03d",plantaMgl.getHour48().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1WKNP(String.format("%03d",plantaMgl.getWeek().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1MTNP( String.format("%03d",plantaMgl.getMonth().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1YRNP( String.format("%05d",plantaMgl.getYear().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1DYTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF2DYTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1WKTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1MTTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1YRTP("99999");
            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse
                    = restClientBasicMaintenanceRRPlants.callWebServicePost(
                            EnumeratorServiceName.BASICARR_PLANTS_CREAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRPlantaRequest);
            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ACCION_EXITOSA_RR)) {
                throw new ApplicationException("(As400)"
                        + mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage(), ex);
        }
        return true;
    }
    
    /**
     * Metodo para realizar la actualizacion a RR por pcml
     * 
     * @param plantaMgl
     * @param PFHTYP
     * @param PFHEND
     * @return boolean define si la operacion se realizao correctamente o no
     * @throws ApplicationException
     */
    public boolean updateNodoRR(PlantaMgl plantaMgl, String PFHTYP, String PFHEND) throws ApplicationException {
        try {
            MantenimientoBasicoRRPlantaRequest mantenimientoBasicoRRPlantaRequest
                    = new MantenimientoBasicoRRPlantaRequest();
            mantenimientoBasicoRRPlantaRequest.setIDUSER(plantaMgl.getUsuarioCreacion());
            mantenimientoBasicoRRPlantaRequest.setPFHTYP(PFHTYP);           //verificando por hits
            mantenimientoBasicoRRPlantaRequest.setPFHEND(PFHEND);           //verificando por hits
            mantenimientoBasicoRRPlantaRequest.setPFSTYP( plantaMgl.getLocationtype());
            mantenimientoBasicoRRPlantaRequest.setPFSNOD( plantaMgl.getLocationcode());
            if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("HE")==0){
                mantenimientoBasicoRRPlantaRequest.setPFRTYP("");
                mantenimientoBasicoRRPlantaRequest.setPFRNOD("");
            }else if (( plantaMgl.getLocationtype().trim().compareToIgnoreCase("CT") == 0) ||
                    ( plantaMgl.getLocationtype().trim().compareToIgnoreCase("ND") == 0)){
                mantenimientoBasicoRRPlantaRequest.setPFRTYP(plantaMgl.getConfiguracionplantaparentid().getLocationtype());
                mantenimientoBasicoRRPlantaRequest.setPFRNOD(plantaMgl.getConfiguracionplantaparentid().getLocationcode());
            }else{
                throw new ApplicationException("Error tipo de Planta actualizar no concuerda con los tipos "+plantaMgl.getLocationtype());
            }
            mantenimientoBasicoRRPlantaRequest.setPFDESC(plantaMgl.getDescription());
            mantenimientoBasicoRRPlantaRequest.setPF1DYNP(String.format("%03d",plantaMgl.getHour24().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF2DYNP(String.format("%03d",plantaMgl.getHour48().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1WKNP(String.format("%03d",plantaMgl.getWeek().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1MTNP( String.format("%03d",plantaMgl.getMonth().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1YRNP( String.format("%05d",plantaMgl.getYear().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1DYTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF2DYTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1WKTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1MTTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1YRTP("99999");
            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse
                    = restClientBasicMaintenanceRRPlants.callWebServicePut(
                            EnumeratorServiceName.BASICARR_PLANTS_ACTUALIZAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRPlantaRequest);
            if ( mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta().equals(Constant.ACCION_EXITOSA_RR)
                    || (mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta().compareToIgnoreCase("INS0806")==0) ) {
                return false;//no se presento error
            }else{
                LOGGER.error( this.getClass() + " : updateNodoRR : Error : en la "
                        + "ejecucion del servicio RR actualizacion Plantas: "
                        + plantaMgl.getLocationtype() + plantaMgl.getLocationcode() 
                        + " descripcion: " + mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta());
                throw new ApplicationException(" updateNodoRR : Error : en la "
                        + "ejecucion del servicio RR actualizacion Plantas: "+ mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());  
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            LOGGER.error(this.getClass()+" : updateNodoRR : Error : tipo de Planta "
                    + "en RR no se puede realizar actualizacion: "
                    + plantaMgl.getLocationtype() + plantaMgl.getLocationcode()+"(As400-ws)" 
                    + ex.getMessage() +" PilaError: " + ex );
             throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
    }
    
    /**
     * Metodo para realizar la eliminaciion de una configuracionde planta
     * a RR por pcml
     * 
     * @param plantaMgl
     * @param PFHTYP
     * @param PFHEND
     * @return boolean define si la operacion se realizao correctamente o no
     * @throws ApplicationException
     */
    public boolean deletePlantaRR(PlantaMgl plantaMgl,
            String PFHTYP, String PFHEND) throws ApplicationException {
        try {
            MantenimientoBasicoRRPlantaRequest mantenimientoBasicoRRPlantaRequest
                    = new MantenimientoBasicoRRPlantaRequest();
            mantenimientoBasicoRRPlantaRequest.setIDUSER(plantaMgl.getUsuarioCreacion());
            mantenimientoBasicoRRPlantaRequest.setPFHTYP(PFHTYP);           //verificando por hits
            mantenimientoBasicoRRPlantaRequest.setPFHEND(PFHEND);           //verificando por hits
            if (plantaMgl.getLocationtype().trim().compareToIgnoreCase("HE")==0){
                mantenimientoBasicoRRPlantaRequest.setPFRTYP("");
                mantenimientoBasicoRRPlantaRequest.setPFRNOD("");
            }else if (( plantaMgl.getLocationtype().trim().compareToIgnoreCase("CT") == 0) ||
                    ( plantaMgl.getLocationtype().trim().compareToIgnoreCase("ND") == 0)){
                mantenimientoBasicoRRPlantaRequest.setPFRTYP(plantaMgl.getConfiguracionplantaparentid().getLocationtype());
                mantenimientoBasicoRRPlantaRequest.setPFRNOD(plantaMgl.getConfiguracionplantaparentid().getLocationcode());
            }else{
                LOGGER.error(this.getClass()+" : deletePlantaRR : Error : tipo de Planta en RR no se puede realizar eliminacion: "+plantaMgl.getLocationtype()+plantaMgl.getLocationcode());
                return true;
            }
            mantenimientoBasicoRRPlantaRequest.setPFSTYP( plantaMgl.getLocationtype());
            mantenimientoBasicoRRPlantaRequest.setPFSNOD( plantaMgl.getLocationcode());
            mantenimientoBasicoRRPlantaRequest.setPFDESC(plantaMgl.getDescription());
            mantenimientoBasicoRRPlantaRequest.setPF1DYNP(String.format("%03d",plantaMgl.getHour24().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF2DYNP(String.format("%03d",plantaMgl.getHour48().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1WKNP(String.format("%03d",plantaMgl.getWeek().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1MTNP( String.format("%03d",plantaMgl.getMonth().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1YRNP( String.format("%05d",plantaMgl.getYear().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1DYTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF2DYTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1WKTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1MTTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1YRTP("99999");
            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse
                    = restClientBasicMaintenanceRRPlants.callWebServicePut(
                            EnumeratorServiceName.BASICARR_PLANTS_ELIMINAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRPlantaRequest );
            if ( mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta().equals(Constant.ACCION_EXITOSA_RR)
                    || (mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta().compareToIgnoreCase("INS0806")==0) ) {//INS0817
                return false;//no se presento error
            }else{
                LOGGER.error( this.getClass() + " : deletePlantaRR : Error : en la ejecucion del servicio RR eliminacion Plantas: "
                        + plantaMgl.getLocationtype()+plantaMgl.getLocationcode() + " descripcion: " 
                        + mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta());
                 throw new ApplicationException(" deletePlantaRR : Error : en la "
                        + "ejecucion del servicio RR eliminacion Plantas: "+ mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());  
            }
        } catch (UniformInterfaceException | IOException ex) {
            LOGGER.error( this.getClass() + " : deletePlantaRR : Error : tipo de Planta en RR no se puede realizar eliminacion: "
                    + plantaMgl.getLocationtype() + plantaMgl.getLocationcode());
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
      
    }
    
    /**
     * Metodo para realizar la consulta de una configuracion de planta
     * a RR por pcml
     * 
     * @param plantaMgl
     * @return boolean define si la operacion se realizao correctamente o no
     * @throws ApplicationException
     */
    public boolean findNodoRR(PlantaMgl plantaMgl) throws ApplicationException {
        try {
            MantenimientoBasicoRRPlantaRequest mantenimientoBasicoRRPlantaRequest
                    = new MantenimientoBasicoRRPlantaRequest();

            mantenimientoBasicoRRPlantaRequest.setIDUSER(plantaMgl.getUsuarioCreacion());
            mantenimientoBasicoRRPlantaRequest.setPFRTYP(plantaMgl.getLocationtype());
            mantenimientoBasicoRRPlantaRequest.setPFRNOD(plantaMgl.getLocationcode());
            mantenimientoBasicoRRPlantaRequest.setPFHTYP("");           //verificando por hits
            mantenimientoBasicoRRPlantaRequest.setPFHEND("");           //verificando por hits
            if (plantaMgl.getConfiguracionplantaparentid() != null) {
                // INFORMACION DEL PADRE (PARA CT O ND).
                mantenimientoBasicoRRPlantaRequest.setPFSTYP(
                        plantaMgl.getConfiguracionplantaparentid().getLocationtype());
                mantenimientoBasicoRRPlantaRequest.setPFSNOD(
                        plantaMgl.getConfiguracionplantaparentid().getLocationcode());
            } else {
                // SIN PADRE (PARA HE).
                mantenimientoBasicoRRPlantaRequest.setPFSTYP("");
                mantenimientoBasicoRRPlantaRequest.setPFSNOD("");
            }
            mantenimientoBasicoRRPlantaRequest.setPFDESC(plantaMgl.getDescription());
            mantenimientoBasicoRRPlantaRequest.setPF1DYNP(String.format("%03d",plantaMgl.getHour24().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF2DYNP(String.format("%03d",plantaMgl.getHour48().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1WKNP(String.format("%03d",plantaMgl.getWeek().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1MTNP( String.format("%03d",plantaMgl.getMonth().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1YRNP( String.format("%05d",plantaMgl.getYear().intValue()));
            mantenimientoBasicoRRPlantaRequest.setPF1DYTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF2DYTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1WKTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1MTTP("999");
            mantenimientoBasicoRRPlantaRequest.setPF1YRTP("99999");
            MantenimientoBasicoRRPlantaResponse mantenimientoBasicoRRNodosResponse
                    = restClientBasicMaintenanceRRPlants.callWebServicePut(
                            EnumeratorServiceName.BASICARR_PLANTS_OBTENER,
                            MantenimientoBasicoRRPlantaResponse.class,
                            mantenimientoBasicoRRPlantaRequest);
            if (!mantenimientoBasicoRRNodosResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ACCION_EXITOSA_RR)) {
                throw new ApplicationException("(As400)"
                        + mantenimientoBasicoRRNodosResponse.getMensajeDeRespuesta());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage(), ex);
        }
        return true;
    }
}