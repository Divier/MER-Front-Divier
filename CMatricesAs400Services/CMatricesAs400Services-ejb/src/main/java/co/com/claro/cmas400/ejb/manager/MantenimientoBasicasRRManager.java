/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.manager;

import co.com.claro.cmas400.ejb.constant.Constantes;
import co.com.claro.cmas400.ejb.core.Manager;
import co.com.claro.cmas400.ejb.core.ManagerLocal;
import co.com.claro.cmas400.ejb.core.PcmlException;
import co.com.claro.cmas400.ejb.pcmlinterface.CLALIIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CLENDIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CLHMEIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CLHOMIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CLMUNIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CLNETIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CLNODIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CLPLTIR0;
import co.com.claro.cmas400.ejb.request.HhppPaginationRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRAliadosRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRREstadoNodosRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRHomologacionRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRNodosRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRPlantaRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRTipificacionDeRedRequest;
import co.com.claro.cmas400.ejb.respons.HhppId;
import co.com.claro.cmas400.ejb.respons.HhppPaginationResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRAliadosResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRREstadoNodosResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRHomologacionResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRNodosResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRPlantaResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRTipificacionDeRedResponse;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 *
 * @author jrodriguez
 */
public class MantenimientoBasicasRRManager {

    private static final Logger LOGGER = LogManager.getLogger(MantenimientoBasicasRRManager.class);
    // Objeto utilizado para llamar los PCML
    private ManagerLocal pcmlManager;

    /**
     * Metodo constructor utilizado para crear una instacia del pcmlmanager
     */
    public MantenimientoBasicasRRManager() {

        Properties properties = new Properties();

        try {
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            String AS400_PROPERTIES_FILE_PATH
                    = parametrosMglManager.findByAcronimo(
                            co.com.telmex.catastro.services.util.Constant.AS400_PROPERTIES_FILE_PATH)
                            .iterator().next().getParValor();
            
            if (AS400_PROPERTIES_FILE_PATH != null && !AS400_PROPERTIES_FILE_PATH.isEmpty()) {
                File archivo = new File(AS400_PROPERTIES_FILE_PATH);
                FileInputStream fis = new FileInputStream(archivo);
                properties.load(fis);
                pcmlManager = new Manager(properties);
            }
            else {
                throw new RuntimeException("Parámetro AS400_PROPERTIES_FILE_PATH no configurado.");
            }
        } catch (IOException ex) {
            properties = null;
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            properties = null;
            LOGGER.error("Error en MantenimientoBasicasRRManager. EX000 " + ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de mantenimiento
     * aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRAliadosResponse Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRAliadosResponse obtenerAliado(
            MantenimientoBasicoRRAliadosRequest request) {

        CLALIIR0 rpg = new CLALIIR0();
        MantenimientoBasicoRRAliadosResponse response
                = new MantenimientoBasicoRRAliadosResponse();

        rpg.setIDPRC(Constantes.CONSULTAR_RR);
        rpg.setIDUSER(request.getIDUSER());
        rpg.setCODIGO(request.getCODIGO());
        rpg.setDESCRIP(request.getDESCRIP());
        rpg.setESTADO(request.getESTADO());

        try {
            pcmlManager.invoke(rpg);
            response.setIDUSER(rpg.getIDUSER());
            response.setCODIGO(rpg.getCODIGO());
            response.setDESCRIP(rpg.getDESCRIP());
            response.setESTADO(rpg.getESTADO());
            response.setIDENDM(rpg.getIDENDM());
            response.setENDMSG(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setIDENDM("E");
            response.setENDMSG(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setIDENDM("E");
            response.setENDMSG(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de crear un registro en la tabla mantenimiento aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse crearAliado(
            MantenimientoBasicoRRAliadosRequest request) {

        CLALIIR0 rpg = new CLALIIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.CREAR_RR);
        rpg.setIDUSER(request.getIDUSER());
        rpg.setCODIGO(request.getCODIGO());
        rpg.setDESCRIP(request.getDESCRIP());
        rpg.setESTADO(request.getESTADO());

        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla mantenimiento
     * aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse eliminarAliado(
            MantenimientoBasicoRRAliadosRequest request) {

        CLALIIR0 rpg = new CLALIIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.ELIMINAR_RR);
        rpg.setIDUSER(request.getIDUSER());
        rpg.setCODIGO(request.getCODIGO());
        rpg.setDESCRIP(request.getDESCRIP());
        rpg.setESTADO(request.getESTADO());

        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro en la tabla mantenimiento
     * aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse actualizarAliado(
            MantenimientoBasicoRRAliadosRequest request) {

        CLALIIR0 rpg = new CLALIIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.MODIFICAR_RR);
        rpg.setIDUSER(request.getIDUSER());
        rpg.setCODIGO(request.getCODIGO());
        rpg.setDESCRIP(request.getDESCRIP());
        rpg.setESTADO(request.getESTADO());

        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de mantenimiento
     * tipificacion de red
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRTipificacionDeRedResponse Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRTipificacionDeRedResponse obtenerTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {

        CLNETIR0 rpg = new CLNETIR0();
        MantenimientoBasicoRRTipificacionDeRedResponse response
                = new MantenimientoBasicoRRTipificacionDeRedResponse();

        rpg.setIDPRC(Constantes.CONSULTAR_RR);
        rpg.setIDUSER(request.getIDUSER());
        rpg.setTRCODR(request.getTRCODR());
        rpg.setTRDESR(request.getTRDESR());
        rpg.setTRSTAT(request.getTRSTAT());

        try {
            pcmlManager.invoke(rpg);
            response.setIDUSER(rpg.getIDUSER());
            response.setTRCODR(rpg.getTRCODR());
            response.setTRDESR(rpg.getTRDESR());
            response.setTRSTAT(rpg.getTRSTAT());
            response.setIDENDM(rpg.getIDENDM());
            response.setENDMSG(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setIDENDM("E");
            response.setENDMSG(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setIDENDM("E");
            response.setENDMSG(ex.getMessage());
            return response;
        }

        return response;

    }

    /**
     * Metodo encargado de crear un registro en la tabla de mantenimiento
     * tipificacion de red
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse crearTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {

        CLNETIR0 rpg = new CLNETIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.CREAR_RR);
        rpg.setIDUSER(request.getIDUSER());
        rpg.setTRCODR(request.getTRCODR());
        rpg.setTRDESR(request.getTRDESR());
        rpg.setTRSTAT(request.getTRSTAT());

        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());

            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;

    }

    /**
     * Metodo encargado de eliminar un registro en la tabla mantenimiento
     * tipificacion de red
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse eliminarTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {

        CLNETIR0 rpg = new CLNETIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.ELIMINAR_RR);
        rpg.setIDUSER(request.getIDUSER());
        rpg.setTRCODR(request.getTRCODR());
        rpg.setTRDESR(request.getTRDESR());
        rpg.setTRSTAT(request.getTRSTAT());

        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());

            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;

    }

    /**
     * Metodo encargado de actualizar un registro en la tabla mantenimiento
     * tipificacion de red
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse actualizarTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {

        CLNETIR0 rpg = new CLNETIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.MODIFICAR_RR);
        rpg.setIDUSER(request.getIDUSER());
        rpg.setTRCODR(request.getTRCODR());
        rpg.setTRDESR(request.getTRDESR());
        rpg.setTRSTAT(request.getTRSTAT());

        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());

            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
           
            return response;
        }

        return response;

    }

    /**
     * Metodo encargado de crear el nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse crearNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        CLNODIR0 rpg = new CLNODIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.CREAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setCODNOD(alimentacion.getCODNOD());
        rpg.setCODNOM(alimentacion.getCODNOM());
        rpg.setCODALI(alimentacion.getCODALI());
        rpg.setCODDIV(alimentacion.getCODDIV());
        rpg.setCODDVS(alimentacion.getCODDVS());
        rpg.setCODARE(alimentacion.getCODARE());
        rpg.setCODZON(alimentacion.getCODZON());
        rpg.setCODCOM(alimentacion.getCODCOM());
        rpg.setCODDIS(alimentacion.getCODDIS());
        rpg.setCODUNI(alimentacion.getCODUNI());
        rpg.setTIPRED(alimentacion.getTIPRED());
        rpg.setEDONOD(alimentacion.getEDONOD());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar el nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse actualizarNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        CLNODIR0 rpg = new CLNODIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.MODIFICAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setCODNOD(alimentacion.getCODNOD());
        rpg.setCODNOM(alimentacion.getCODNOM());
        rpg.setCODALI(alimentacion.getCODALI());
        rpg.setCODDIV(alimentacion.getCODDIV());
        rpg.setCODDVS(alimentacion.getCODDVS());
        rpg.setCODARE(alimentacion.getCODARE());
        rpg.setCODZON(alimentacion.getCODZON());
        rpg.setCODCOM(alimentacion.getCODCOM());
        rpg.setCODDIS(alimentacion.getCODDIS());
        rpg.setCODUNI(alimentacion.getCODUNI());
        rpg.setTIPRED(alimentacion.getTIPRED());
        rpg.setEDONOD(alimentacion.getEDONOD());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar el nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse eliminarNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        CLNODIR0 rpg = new CLNODIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.ELIMINAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setCODNOD(alimentacion.getCODNOD());
        rpg.setCODNOM(alimentacion.getCODNOM());
        rpg.setCODALI(alimentacion.getCODALI());
        rpg.setCODDIV(alimentacion.getCODDIV());
        rpg.setCODDVS(alimentacion.getCODDVS());
        rpg.setCODARE(alimentacion.getCODARE());
        rpg.setCODZON(alimentacion.getCODZON());
        rpg.setCODCOM(alimentacion.getCODCOM());
        rpg.setCODDIS(alimentacion.getCODDIS());
        rpg.setCODUNI(alimentacion.getCODUNI());
        rpg.setTIPRED(alimentacion.getTIPRED());
        rpg.setEDONOD(alimentacion.getEDONOD());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
           
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de obtener un nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRNodosResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRNodosResponse obtenerNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {

        CLNODIR0 rpg = new CLNODIR0();
        MantenimientoBasicoRRNodosResponse response
                = new MantenimientoBasicoRRNodosResponse();

        rpg.setIDPRC(Constantes.CONSULTAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setCODNOD(alimentacion.getCODNOD());
        rpg.setCODNOM(alimentacion.getCODNOM());
        rpg.setCODALI(alimentacion.getCODALI());
        rpg.setCODDIV(alimentacion.getCODDIV());
        rpg.setCODDVS(alimentacion.getCODDVS());
        rpg.setCODARE(alimentacion.getCODARE());
        rpg.setCODZON(alimentacion.getCODZON());
        rpg.setCODCOM(alimentacion.getCODCOM());
        rpg.setCODDIS(alimentacion.getCODDIS());
        rpg.setCODUNI(alimentacion.getCODUNI());
        rpg.setTIPRED(alimentacion.getTIPRED());
        rpg.setEDONOD(alimentacion.getEDONOD());

        try {

            pcmlManager.invoke(rpg);
            response.setIDPRC(rpg.getIDPRC());
            response.setIDUSER(rpg.getIDUSER());
            response.setCODNOD(rpg.getCODNOD());
            response.setCODNOM(rpg.getCODNOM());
            response.setCODALI(rpg.getCODALI());
            response.setCODDIV(rpg.getCODDIV());
            response.setCODDVS(rpg.getCODDVS());
            response.setCODARE(rpg.getCODARE());
            response.setCODZON(rpg.getCODZON());
            response.setCODCOM(rpg.getCODCOM());
            response.setCODDIS(rpg.getCODDIS());
            response.setCODUNI(rpg.getCODUNI());
            response.setTIPRED(rpg.getTIPRED());
            response.setEDONOD(rpg.getEDONOD());
            response.setIDENDM(rpg.getIDENDM());
            response.setENDMSG(rpg.getENDMSG());

        } catch (PcmlException ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setIDENDM("E");
            response.setENDMSG(ex.getMessage());
            return response;
        }catch (Exception ex) {
           
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            
            response.setIDENDM("E");
            response.setENDMSG(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de crear el estado de un nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseDataManttoNodos Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse crearEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        CLENDIR0 rpg = new CLENDIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.CREAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setNDCODEP(alimentacion.getNDCODEP());
        rpg.setNDDESEP(alimentacion.getNDDESEP());
        rpg.setNDSTATP(alimentacion.getNDSTATP());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
           
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);
            
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar el estado nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse eliminarEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        CLENDIR0 rpg = new CLENDIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.ELIMINAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setNDCODEP(alimentacion.getNDCODEP());
        rpg.setNDDESEP(alimentacion.getNDDESEP());
        rpg.setNDSTATP(alimentacion.getNDSTATP());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de obtener el estado de un nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRREstadoNodosResponse Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRREstadoNodosResponse obtenerEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {

        CLENDIR0 rpg = new CLENDIR0();
        MantenimientoBasicoRREstadoNodosResponse response
                = new MantenimientoBasicoRREstadoNodosResponse();

        rpg.setIDPRC(Constantes.CONSULTAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setNDCODEP(alimentacion.getNDCODEP());
        rpg.setNDDESEP(alimentacion.getNDDESEP());
        rpg.setNDSTATP(alimentacion.getNDSTATP());

        try {

            pcmlManager.invoke(rpg);
            response.setIDUSER(rpg.getIDUSER());
            response.setNDCODEP(rpg.getNDCODEP());
            response.setNDDESEP(rpg.getNDDESEP());
            response.setNDSTATP(rpg.getNDSTATP());
            response.setIDENDM(rpg.getIDENDM());
            response.setENDMSG(rpg.getENDMSG());

        } catch (PcmlException ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setIDENDM("E");
            response.setENDMSG(ex.getMessage());
            return response;
        }catch (Exception ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setIDENDM("E");
            response.setENDMSG(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar el estado de un nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse actualizarEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        CLENDIR0 rpg = new CLENDIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.MODIFICAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setNDCODEP(alimentacion.getNDCODEP());
        rpg.setNDDESEP(alimentacion.getNDDESEP());
        rpg.setNDSTATP(alimentacion.getNDSTATP());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {

            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de Insertar un registro en la tabla Planta
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los resultados de la
     * ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse crearPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion) {

        CLPLTIR0 rpg = new CLPLTIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();
        rpg.setIDPRC(Constantes.CREAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setPFHTYP(alimentacion.getPFHTYP());
        rpg.setPFHEND(alimentacion.getPFHEND());
        rpg.setPFSTYP(alimentacion.getPFSTYP());
        rpg.setPFSNOD(alimentacion.getPFSNOD());
        rpg.setPFRTYP(alimentacion.getPFRTYP());
        rpg.setPFRNOD(alimentacion.getPFRNOD());
        rpg.setPFDESC(alimentacion.getPFDESC());
        rpg.setPF1DYNP(alimentacion.getPF1DYNP());
        rpg.setPF1DYTP(alimentacion.getPF1DYTP());
        rpg.setPF2DYNP(alimentacion.getPF2DYNP());
        rpg.setPF2DYTP(alimentacion.getPF2DYTP());
        rpg.setPF1WKNP(alimentacion.getPF1WKNP());
        rpg.setPF1WKTP(alimentacion.getPF1WKTP());
        rpg.setPF1MTNP(alimentacion.getPF1MTNP());
        rpg.setPF1MTTP(alimentacion.getPF1MTTP());
        rpg.setPF1YRNP(alimentacion.getPF1YRNP());
        rpg.setPF1YRTP(alimentacion.getPF1YRTP());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearPlanta EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());

            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearPlanta EX000 " + ex.getMessage(), ex);
            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());

            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla Planta
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los resultados de la
     * ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse eliminarPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion) {

        CLPLTIR0 rpg = new CLPLTIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();
        rpg.setIDPRC(Constantes.ELIMINAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setPFHTYP(alimentacion.getPFHTYP());
        rpg.setPFHEND(alimentacion.getPFHEND());
        rpg.setPFSTYP(alimentacion.getPFSTYP());
        rpg.setPFSNOD(alimentacion.getPFSNOD());
        rpg.setPFRTYP(alimentacion.getPFRTYP());
        rpg.setPFRNOD(alimentacion.getPFRNOD());
        rpg.setPFDESC(alimentacion.getPFDESC());
        rpg.setPF1DYNP(alimentacion.getPF1DYNP());
        rpg.setPF1DYTP(alimentacion.getPF1DYTP());
        rpg.setPF2DYNP(alimentacion.getPF2DYNP());
        rpg.setPF2DYTP(alimentacion.getPF2DYTP());
        rpg.setPF1WKNP(alimentacion.getPF1WKNP());
        rpg.setPF1WKTP(alimentacion.getPF1WKTP());
        rpg.setPF1MTNP(alimentacion.getPF1MTNP());
        rpg.setPF1MTTP(alimentacion.getPF1MTTP());
        rpg.setPF1YRNP(alimentacion.getPF1YRNP());
        rpg.setPF1YRTP(alimentacion.getPF1YRTP());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());
        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.eliminarPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.eliminarPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar un registro en la tabla Planta
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseDataPlantaQuery Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRPlantaResponse obtenerPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion) {

        CLPLTIR0 rpg = new CLPLTIR0();
        MantenimientoBasicoRRPlantaResponse response
                = new MantenimientoBasicoRRPlantaResponse();

        rpg.setIDPRC(Constantes.CONSULTAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setPFHTYP(alimentacion.getPFHTYP());
        rpg.setPFHEND(alimentacion.getPFHEND());
        rpg.setPFSTYP(alimentacion.getPFSTYP());
        rpg.setPFSNOD(alimentacion.getPFSNOD());
        rpg.setPFRTYP(alimentacion.getPFRTYP());
        rpg.setPFRNOD(alimentacion.getPFRNOD());
        rpg.setPFDESC(alimentacion.getPFDESC());
        rpg.setPF1DYNP(alimentacion.getPF1DYNP());
        rpg.setPF1DYTP(alimentacion.getPF1DYTP());
        rpg.setPF2DYNP(alimentacion.getPF2DYNP());
        rpg.setPF2DYTP(alimentacion.getPF2DYTP());
        rpg.setPF1WKNP(alimentacion.getPF1WKNP());
        rpg.setPF1WKTP(alimentacion.getPF1WKTP());
        rpg.setPF1MTNP(alimentacion.getPF1MTNP());
        rpg.setPF1MTTP(alimentacion.getPF1MTTP());
        rpg.setPF1YRNP(alimentacion.getPF1YRNP());
        rpg.setPF1YRTP(alimentacion.getPF1YRTP());

        try {

            pcmlManager.invoke(rpg);
            response.setIDUSER(rpg.getIDUSER());
            response.setPFHTYP(rpg.getPFHTYP());
            response.setPFHEND(rpg.getPFHEND());
            response.setPFSTYP(rpg.getPFSTYP());
            response.setPFSNOD(rpg.getPFSNOD());
            response.setPFRTYP(rpg.getPFRTYP());
            response.setPFRNOD(rpg.getPFRNOD());
            response.setPFDESC(rpg.getPFDESC());
            response.setPF1DYNP(rpg.getPF1DYNP());
            response.setPF1DYTP(rpg.getPF1DYTP());
            response.setPF2DYNP(rpg.getPF2DYNP());
            response.setPF2DYTP(rpg.getPF2DYTP());
            response.setPF1WKNP(rpg.getPF1WKNP());
            response.setPF1WKTP(rpg.getPF1WKTP());
            response.setPF1MTNP(rpg.getPF1MTNP());
            response.setPF1MTTP(rpg.getPF1MTTP());
            response.setPF1YRNP(rpg.getPF1YRNP());
            response.setPF1YRTP(rpg.getPF1YRTP());
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;

    }

    /**
     * Metodo encargado de actualizar un registro en la tabla Planta
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los resultados de la
     * ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse actualizarPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion) {

        CLPLTIR0 rpg = new CLPLTIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.MODIFICAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setPFHTYP(alimentacion.getPFHTYP());
        rpg.setPFHEND(alimentacion.getPFHEND());
        rpg.setPFSTYP(alimentacion.getPFSTYP());
        rpg.setPFSNOD(alimentacion.getPFSNOD());
        rpg.setPFRTYP(alimentacion.getPFRTYP());
        rpg.setPFRNOD(alimentacion.getPFRNOD());
        rpg.setPFDESC(alimentacion.getPFDESC());
        rpg.setPF1DYNP(alimentacion.getPF1DYNP());
        rpg.setPF1DYTP(alimentacion.getPF1DYTP());
        rpg.setPF2DYNP(alimentacion.getPF2DYNP());
        rpg.setPF2DYTP(alimentacion.getPF2DYTP());
        rpg.setPF1WKNP(alimentacion.getPF1WKNP());
        rpg.setPF1WKTP(alimentacion.getPF1WKTP());
        rpg.setPF1MTNP(alimentacion.getPF1MTNP());
        rpg.setPF1MTTP(alimentacion.getPF1MTTP());
        rpg.setPF1YRNP(alimentacion.getPF1YRNP());
        rpg.setPF1YRTP(alimentacion.getPF1YRTP());

        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.actualizarPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;

    }

    /**
     * Metodo encargado de insertar un registro en la tabla Homologacion
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los resultados de la
     * ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse crearHomologacionDane(
            MantenimientoBasicoRRHomologacionRequest alimentacion) {
        CLHOMIR0 rpg = new CLHOMIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.CREAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setCODDIV(alimentacion.getCODDIV());
        rpg.setCODCOM(alimentacion.getCODCOM());
        rpg.setNOMREG(alimentacion.getNOMREG());
        rpg.setNOMDEP(alimentacion.getNOMDEP());
        rpg.setNOMMUN(alimentacion.getNOMMUN());
        rpg.setISMUNI(alimentacion.getISMUNI());
        rpg.setHOMDEP(alimentacion.getHOMDEP());
        rpg.setHOMMUN(alimentacion.getHOMMUN());
        rpg.setCODDAN(alimentacion.getCODDAN());
        rpg.setCODUBI(alimentacion.getCODUBI());
        rpg.setESTREG(alimentacion.getESTREG());
        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());
        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearHomologacionDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla Homologacion
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los resultados de la
     * ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse eliminarHomologacionDane(
            MantenimientoBasicoRRHomologacionRequest alimentacion) {
        CLHOMIR0 rpg = new CLHOMIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC(Constantes.ELIMINAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setCODDIV(alimentacion.getCODDIV());
        rpg.setCODCOM(alimentacion.getCODCOM());
        rpg.setNOMREG(alimentacion.getNOMREG());
        rpg.setNOMDEP(alimentacion.getNOMDEP());
        rpg.setNOMMUN(alimentacion.getNOMMUN());
        rpg.setISMUNI(alimentacion.getISMUNI());
        rpg.setHOMDEP(alimentacion.getHOMDEP());
        rpg.setHOMMUN(alimentacion.getHOMMUN());
        rpg.setCODDAN(alimentacion.getCODDAN());
        rpg.setCODUBI(alimentacion.getCODUBI());
        rpg.setESTREG(alimentacion.getESTREG());
        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());
        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.eliminarHomologacionDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de actualizar un registro en la tabla Homologacion
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRHomologacionResponse Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRHomologacionResponse obtenerHomologacionDane(
            MantenimientoBasicoRRHomologacionRequest alimentacion) {
        CLHOMIR0 rpg = new CLHOMIR0();
        MantenimientoBasicoRRHomologacionResponse response
                = new MantenimientoBasicoRRHomologacionResponse();

        rpg.setIDPRC(Constantes.CONSULTAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setCODDIV(alimentacion.getCODDIV());
        rpg.setCODCOM(alimentacion.getCODCOM());
        rpg.setNOMREG(alimentacion.getNOMREG());
        rpg.setNOMDEP(alimentacion.getNOMDEP());
        rpg.setNOMMUN(alimentacion.getNOMMUN());
        rpg.setISMUNI(alimentacion.getISMUNI());
        rpg.setHOMDEP(alimentacion.getHOMDEP());
        rpg.setHOMMUN(alimentacion.getHOMMUN());
        rpg.setCODDAN(alimentacion.getCODDAN());
        rpg.setCODUBI(alimentacion.getCODUBI());
        rpg.setESTREG(alimentacion.getESTREG());
        try {

            pcmlManager.invoke(rpg);

            response.setIDUSER(rpg.getIDUSER());
            response.setCODDIV(rpg.getCODDIV());
            response.setCODCOM(rpg.getCODCOM());
            response.setNOMREG(rpg.getNOMREG());
            response.setNOMDEP(rpg.getNOMDEP());
            response.setNOMMUN(rpg.getNOMMUN());
            response.setISMUNI(rpg.getISMUNI());
            response.setHOMDEP(rpg.getHOMDEP());
            response.setHOMMUN(rpg.getHOMMUN());
            response.setCODDAN(rpg.getCODDAN());
            response.setCODUBI(rpg.getCODUBI());
            response.setESTREG(rpg.getESTREG());
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerHomologacionDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de actualizar un registro en la tabla Homologacion
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los resultados de la
     * ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse actualizarHomologacionDane(
            MantenimientoBasicoRRHomologacionRequest alimentacion) {
        CLHOMIR0 rpg = new CLHOMIR0();
        MantenimientoBasicoRRBaseResponse response
                = new MantenimientoBasicoRRBaseResponse();
        rpg.setIDPRC(Constantes.MODIFICAR_RR);
        rpg.setIDUSER(alimentacion.getIDUSER());
        rpg.setCODDIV(alimentacion.getCODDIV());
        rpg.setCODCOM(alimentacion.getCODCOM());
        rpg.setNOMREG(alimentacion.getNOMREG());
        rpg.setNOMDEP(alimentacion.getNOMDEP());
        rpg.setNOMMUN(alimentacion.getNOMMUN());
        rpg.setISMUNI(alimentacion.getISMUNI());
        rpg.setHOMDEP(alimentacion.getHOMDEP());
        rpg.setHOMMUN(alimentacion.getHOMMUN());
        rpg.setCODDAN(alimentacion.getCODDAN());
        rpg.setCODUBI(alimentacion.getCODUBI());
        rpg.setESTREG(alimentacion.getESTREG());
        response.setCodigoDeRespuesta(rpg.getIDENDM());
        response.setMensajeDeRespuesta(rpg.getENDMSG());
        try {

            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());
        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.actualizarHomologacionDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerPlanta EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de obtener los registros de Municipio y centros de
     * poblado DANE
     *
     * @param request Objeto que almacena los datos de entrada
     * @return ResponseDataRRMantenimientoCentroPobladoDane Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    /**
     * Metodo encargado de obtener los registros de Municipio y centros de
     * poblado DANE
     *
     * @param request Objeto que almacena los datos de entrada
     * @return ResponseDataRRMantenimientoCentroPobladoDane Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse obtenerMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {

        CLMUNIR0 rpg = new CLMUNIR0();
        MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse response
                = new MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse();

        rpg.setIDPRC("I");
        rpg.setIDUSER(request.getIduser());
        rpg.setCDDANE(request.getCddane());
        rpg.setDSPOBLD(request.getDspobld());
        rpg.setCDDEPTO(request.getCddepto());
        rpg.setDSDEPTO(request.getDsdepto());
        rpg.setCDMPIO(request.getCdmpio());
        rpg.setDSMPIO(request.getDsmpio());
        rpg.setCDCLASE(request.getCdclase());
        rpg.setNUMMANZ(request.getNummanz());
        rpg.setANOCREA(request.getAnocrea());
        rpg.setESTVIG(request.getEstvig());

        try {
            pcmlManager.invoke(rpg);
            response.setIduser(rpg.getIDUSER());
            response.setCddane(rpg.getCDDANE());
            response.setDspobld(rpg.getDSDEPTO());
            response.setCddepto(rpg.getCDDEPTO());
            response.setDsdepto(rpg.getDSDEPTO());
            response.setCdmpio(rpg.getCDMPIO());
            response.setDsmpio(rpg.getDSMPIO());
            response.setCdclase(rpg.getCDCLASE());
            response.setNummanz(rpg.getNUMMANZ());
            response.setAnocrea(rpg.getANOCREA());
            response.setEstvig(rpg.getESTVIG());
            response.setIdendm(rpg.getIDENDM());
            response.setEndmsg(rpg.getENDMSG());
        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerMunicipioCentrosPobladoDane EX000 " + ex.getMessage(), ex);

            response.setIdendm("E");
            response.setEndmsg(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerPlanta EX000 " + ex.getMessage(), ex);

            response.setIdendm("E");
            response.setEndmsg(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de crear los registros de Municipio y centros de poblado
     * DANE
     *
     * @param request Objeto que almacena los datos de entrada
     * @return ResponseDataRRMantenimientoCentroPobladoDane Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse crearMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {

        CLMUNIR0 rpg = new CLMUNIR0();
        MantenimientoBasicoRRBaseResponse response = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC("C");
        rpg.setIDUSER(request.getIduser());
        rpg.setCDDANE(request.getCddane());
        rpg.setDSPOBLD(request.getDspobld());
        rpg.setCDDEPTO(request.getCddepto());
        rpg.setDSDEPTO(request.getDsdepto());
        rpg.setCDMPIO(request.getCdmpio());
        rpg.setDSMPIO(request.getDsmpio());
        rpg.setCDCLASE(request.getCdclase());
        rpg.setNUMMANZ(request.getNummanz());
        rpg.setANOCREA(request.getAnocrea());
        rpg.setESTVIG(request.getEstvig());
        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearMunicipioCentrosPobladoDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearMunicipioCentrosPobladoDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar los registros de Municipio y centros de
     * poblado DANE
     *
     * @param request Objeto que almacena los datos de entrada
     * @return ResponseDataRRMantenimientoCentroPobladoDane Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse eliminarMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {

        CLMUNIR0 rpg = new CLMUNIR0();
        MantenimientoBasicoRRBaseResponse response = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC("D");
        rpg.setIDUSER(request.getIduser());
        rpg.setCDDANE(request.getCddane());
        rpg.setDSPOBLD(request.getDspobld());
        rpg.setCDDEPTO(request.getCddepto());
        rpg.setDSDEPTO(request.getDsdepto());
        rpg.setCDMPIO(request.getCdmpio());
        rpg.setDSMPIO(request.getDsmpio());
        rpg.setCDCLASE(request.getCdclase());
        rpg.setNUMMANZ(request.getNummanz());
        rpg.setANOCREA(request.getAnocrea());
        rpg.setESTVIG(request.getEstvig());
        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.eliminarMunicipioCentrosPobladoDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearMunicipioCentrosPobladoDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar los registros de Municipio y centros de
     * poblado DANE
     *
     * @param request Objeto que almacena los datos de entrada
     * @return ResponseDataRRMantenimientoCentroPobladoDane Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public MantenimientoBasicoRRBaseResponse actualizarMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {

        CLMUNIR0 rpg = new CLMUNIR0();
        MantenimientoBasicoRRBaseResponse response = new MantenimientoBasicoRRBaseResponse();

        rpg.setIDPRC("U");
        rpg.setIDUSER(request.getIduser());
        rpg.setCDDANE(request.getCddane());
        rpg.setDSPOBLD(request.getDspobld());
        rpg.setCDDEPTO(request.getCddepto());
        rpg.setDSDEPTO(request.getDsdepto());
        rpg.setCDMPIO(request.getCdmpio());
        rpg.setDSMPIO(request.getDsmpio());
        rpg.setCDCLASE(request.getCdclase());
        rpg.setNUMMANZ(request.getNummanz());
        rpg.setANOCREA(request.getAnocrea());
        rpg.setESTVIG(request.getEstvig());
        try {
            pcmlManager.invoke(rpg);
            response.setCodigoDeRespuesta(rpg.getIDENDM());
            response.setMensajeDeRespuesta(rpg.getENDMSG());

        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.actualizarMunicipioCentrosPobladoDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearMunicipioCentrosPobladoDane EX000 " + ex.getMessage(), ex);

            response.setCodigoDeRespuesta("E");
            response.setMensajeDeRespuesta(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los HHPP modificados entre fechas
     *
     * @param request Objeto que almacena los datos de entrada
     * @return HhppPaginationResponse Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public HhppPaginationResponse consultaHhpp(HhppPaginationRequest request) {
        CLHMEIR0 rpg = new CLHMEIR0();
        HhppPaginationResponse response = new HhppPaginationResponse();

        rpg.setIDPRC(Constantes.CONSULTAR_RR);
        rpg.setIDUSER(request.getIduser());
        rpg.setDATEIN(request.getDatein());
        rpg.setDATEEN(request.getDateen());
        rpg.setHOURIN(request.getHourin());
        rpg.setHOUREN(request.getHouren());
        rpg.setCTDRGE(request.getCtdrge());
        rpg.setCTDRGR(request.getCtdrgr());
        rpg.setUNAKYN(request.getUnakyn());
        rpg.setCTDRGS(request.getCtdrgs());

        try {
            pcmlManager.invoke(rpg);
            String arr = rpg.getARRUNKY();
            LOGGER.error("Usuario: " + rpg.getIDUSER());
            LOGGER.error("Fecha inicio: " + rpg.getDATEIN());
            LOGGER.error("Hora inicio: " + rpg.getHOURIN());
            LOGGER.error("Fecha final: " + rpg.getDATEEN());
            LOGGER.error("Hora final: " + rpg.getHOUREN());
            LOGGER.error("Registros Solicitados: " + rpg.getCTDRGE());
            LOGGER.error("Encontrados rango de fechas: " + rpg.getCTDRGR());
            LOGGER.error("Devueltos: " + rpg.getCTDRGS());
            LOGGER.error("Ultimo registro: " + rpg.getUNAKYN());
            LOGGER.error("idHhpp: " + rpg.getARRUNKY());
            LOGGER.error("Codigo Respuesta: " + rpg.getIDENDM());
            LOGGER.error("Mensaje Respuesta: " + rpg.getENDMSG());
            List<HhppId> arrKey = new ArrayList<HhppId>();
            HhppId id;
            int lon = 9;
            int j = 0;
            for (int i = 0; i < arr.length() / lon; i++) {

                id = new HhppId(arr.substring(j, j + lon));
                arrKey.add(id);
                j += lon;
            }
            response.setArrunky(arrKey);
            response.setIdendm(rpg.getIDENDM());
            response.setEndmsg(rpg.getENDMSG());
        } catch (PcmlException ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.obtenerAliado EX000 " + ex.getMessage(), ex);

            response.setIdendm("E");
            response.setEndmsg(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en MantenimientoBasicasRRManager.crearMunicipioCentrosPobladoDane EX000 " + ex.getMessage(), ex);

            response.setIdendm("E");
            response.setEndmsg(ex.getMessage());
            return response;
        }

        return response;
    }
}
