/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.manager;

import co.com.claro.cmas400.ejb.constant.Constantes;
import co.com.claro.cmas400.ejb.core.Manager;
import co.com.claro.cmas400.ejb.core.ManagerLocal;
import co.com.claro.cmas400.ejb.core.PcmlException;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCRNFR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCRNFR1;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCTRFR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCTRFR2;
import co.com.claro.cmas400.ejb.pcmlinterface.CMDEAAR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMIADER0;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaDealer;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionVt;
import co.com.claro.cmas400.ejb.request.RequestDataNotasOtCmSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataNotasOtCuentaMatriz;
import co.com.claro.cmas400.ejb.request.RequestDataOtEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataOtSubEdificio;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaDealerList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionVtList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasOtCmSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasOtCuentaMatrizList;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseOtSubEdificiosList;
import co.com.claro.cmas400.ejb.utils.ServiceUtils;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Alquiler
 */
public class CmOrdenTrabajoManager {
    
    /** N&uacute;mero m&aacute;ximo de caracteres del campo <b>PUSERID</b>. */
    private final int MAX_PUSERID_LENGHT = 10;
    private static final Logger LOGGER = LogManager.getLogger(CmOrdenTrabajoManager.class);
    
    
    // Objeto utilizado para llamar los PCML
    private ManagerLocal pcmlManager;

    /**
     * Metodo constructor utilizado para crear una instacia del pcmlmanager
     */
    public CmOrdenTrabajoManager() {

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
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Informacion VT
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInformacionVtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseInformacionVtList informacionVtQuery(
            RequestDataInformacionVt alimentacion) {
        CMIADER0 rpg = new CMIADER0();
        ResponseInformacionVtList response = new ResponseInformacionVtList();
        ArrayList tablaRta = new ArrayList();

        rpg.setCUENTA(alimentacion.getCuenta());
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar las OT de un Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(
            RequestDataOtEdificio alimentacion) {
        CMCTRFR0 rpg = new CMCTRFR0();
        ResponseOtEdificiosList response = new ResponseOtEdificiosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getNumeroEdificio());
        rpg.setPOTINI(alimentacion.getCodigoOt());
        rpg.setFUNCION(Constantes.CONSULTAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de crear una OT para un Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOtEdificiosList ordenTrabajoEdificioAdd(
            RequestDataOtEdificio alimentacion) {
        CMCTRFR0 rpg = new CMCTRFR0();
        ResponseOtEdificiosList response = new ResponseOtEdificiosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getNumeroEdificio());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setFUNCION(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            alimentacion.setNitCliente(alimentacion.getNitCliente()!= null ?alimentacion.getNitCliente().replaceAll("[A-Za-z\\-]", ""):"");
            alimentacion.setNumeroOTPadre(alimentacion.getNumeroOTPadre()!= null? alimentacion.getNumeroOTPadre().replaceAll("[A-Za-z\\-]", ""):"");
            alimentacion.setNumeroOThija(alimentacion.getNumeroOThija()!= null? alimentacion.getNumeroOThija().replaceAll("[A-Za-z\\-]", ""):"");
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCrearTrabajo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de actualizar la informacion de una OT para un Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOtEdificiosList ordenTrabajoEdificioUpdate(
            RequestDataOtEdificio alimentacion) {
        CMCTRFR0 rpg = new CMCTRFR0();
        ResponseOtEdificiosList response = new ResponseOtEdificiosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getNumeroEdificio());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setFUNCION(Constantes.ACTUALIZAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        try {
            alimentacion.setNitCliente(alimentacion.getNitCliente() != null ? alimentacion.getNitCliente().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOTPadre(alimentacion.getNumeroOTPadre() != null ? alimentacion.getNumeroOTPadre().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOThija(alimentacion.getNumeroOThija() != null ? alimentacion.getNumeroOThija().replaceAll("[A-Za-z\\-]", "") : "");
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCrearTrabajo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de cancelar una OT para un Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOtEdificiosList ordenTrabajoEdificioDelete(
            RequestDataOtEdificio alimentacion) {
        CMCTRFR0 rpg = new CMCTRFR0();
        ResponseOtEdificiosList response = new ResponseOtEdificiosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getNumeroEdificio());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setFUNCION(Constantes.ELIMINAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        try {
            alimentacion.setNitCliente(alimentacion.getNitCliente() != null ? alimentacion.getNitCliente().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOTPadre(alimentacion.getNumeroOTPadre() != null ? alimentacion.getNumeroOTPadre().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOThija(alimentacion.getNumeroOThija() != null ? alimentacion.getNumeroOThija().replaceAll("[A-Za-z\\-]", "") : "");
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCrearTrabajo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar las OTs para un SubEdificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioQuery(
            RequestDataOtSubEdificio alimentacion) {
        CMCTRFR2 rpg = new CMCTRFR2();
        ResponseOtSubEdificiosList response = new ResponseOtSubEdificiosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getNumeroEdificio());
        rpg.setPNSEQU(alimentacion.getNumeroTorre());
        rpg.setPOTINI(alimentacion.getCodigoOt());
        rpg.setFUNCION(Constantes.CONSULTAR09);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de crear una OT para un SubEdificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioAdd(
            RequestDataOtSubEdificio alimentacion) {
        CMCTRFR2 rpg = new CMCTRFR2();
        ResponseOtSubEdificiosList response = new ResponseOtSubEdificiosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getNumeroEdificio());
        rpg.setPNSEQU(alimentacion.getNumeroTorre());
        rpg.setFUNCION(Constantes.CREAR06);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            alimentacion.setNitCliente(alimentacion.getNitCliente() != null ? alimentacion.getNitCliente().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOTPadre(alimentacion.getNumeroOTPadre() != null ? alimentacion.getNumeroOTPadre().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOThija(alimentacion.getNumeroOThija() != null ? alimentacion.getNumeroOThija().replaceAll("[A-Za-z\\-]", "") : "");
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCrearTrabajoSubEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de actualizar una OT de un SubEdificio
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioUpdate(
            RequestDataOtSubEdificio alimentacion) {
        CMCTRFR2 rpg = new CMCTRFR2();
        ResponseOtSubEdificiosList response = new ResponseOtSubEdificiosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getNumeroEdificio());
        rpg.setPNSEQU(alimentacion.getNumeroTorre());
        rpg.setFUNCION(Constantes.ACTUALIZAR02);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            alimentacion.setNitCliente(alimentacion.getNitCliente() != null ? alimentacion.getNitCliente().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOTPadre(alimentacion.getNumeroOTPadre() != null ? alimentacion.getNumeroOTPadre().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOThija(alimentacion.getNumeroOThija() != null ? alimentacion.getNumeroOThija().replaceAll("[A-Za-z\\-]", "") : "");
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCrearTrabajoSubEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de cancelar una OT de un SubEdificio
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioDelete(
            RequestDataOtSubEdificio alimentacion) {
        CMCTRFR2 rpg = new CMCTRFR2();
        ResponseOtSubEdificiosList response = new ResponseOtSubEdificiosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getNumeroEdificio());
        rpg.setPNSEQU(alimentacion.getNumeroTorre());
        rpg.setFUNCION(Constantes.ELIMINAR04);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            alimentacion.setNitCliente(alimentacion.getNitCliente() != null ? alimentacion.getNitCliente().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOTPadre(alimentacion.getNumeroOTPadre() != null ? alimentacion.getNumeroOTPadre().replaceAll("[A-Za-z\\-]", "") : "");
            alimentacion.setNumeroOThija(alimentacion.getNumeroOThija() != null ? alimentacion.getNumeroOThija().replaceAll("[A-Za-z\\-]", "") : "");
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCrearTrabajoSubEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de generar la lista de ayuda para seleccionar el Dealer
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaDealerList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConsultaDealerList consultaDealerHelp(
            RequestDataConsultaDealer alimentacion) {
        CMDEAAR0 rpg = new CMDEAAR0();
        ResponseConsultaDealerList response = new ResponseConsultaDealerList();
        ArrayList tablaRta = new ArrayList();

        rpg.setENTRA(alimentacion.getCodigo());
        rpg.setBUSNOM(alimentacion.getNombre());
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar la lista de notas de una Cuenta Matriz en
     * realizadas en la Orden de Trabajo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizListQuery(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        CMCRNFR0 rpg = new CMCRNFR0();
        ResponseNotasOtCuentaMatrizList response = new ResponseNotasOtCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getCodigoEdificioConsulta());
        rpg.setPANUTR(alimentacion.getNumeroOtConsulta());
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar la descripcion de una nota existente para
     * Cuenta Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizDescripcionQuery(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        CMCRNFR0 rpg = new CMCRNFR0();
        ResponseNotasOtCuentaMatrizList response = new ResponseNotasOtCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getCodigoEdificioConsulta());
        rpg.setPANUTR(alimentacion.getNumeroOtConsulta());
        rpg.setPFUNCI(Constantes.CONSULTAR5);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaNotasOtCuentaMatriz, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de crear una Nota en la Orden de trabajo de una Cuenta
     * Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizAdd(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        CMCRNFR0 rpg = new CMCRNFR0();
        ResponseNotasOtCuentaMatrizList response = new ResponseNotasOtCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getCodigoEdificioConsulta());
        rpg.setPANUTR(alimentacion.getNumeroOtConsulta());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaNotasOtCuentaMatriz, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de crear una linea en una Nota Existente en la Orden de
     * trabajo de una Cuenta Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizUpdate(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        CMCRNFR0 rpg = new CMCRNFR0();
        ResponseNotasOtCuentaMatrizList response = new ResponseNotasOtCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getCodigoEdificioConsulta());
        rpg.setPANUTR(alimentacion.getNumeroOtConsulta());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaNotasOtCuentaMatriz, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar la lista de notas de una OT para los
     * SubEdificios de una Cuenta Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioListQuery(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        CMCRNFR1 rpg = new CMCRNFR1();
        ResponseNotasOtCmSubEdificioList response = new ResponseNotasOtCmSubEdificioList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getCodigoEdificioConsulta());
        rpg.setPASEDI(alimentacion.getCodigoSubEdificioConsulta());
        rpg.setPANUTR(alimentacion.getNumeroOtConsulta());
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar la descripcion de una nota existente para
     * un SubEdificion de una Cuenta Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioDescripcionQuery(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        CMCRNFR1 rpg = new CMCRNFR1();
        ResponseNotasOtCmSubEdificioList response = new ResponseNotasOtCmSubEdificioList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getCodigoEdificioConsulta());
        rpg.setPASEDI(alimentacion.getCodigoSubEdificioConsulta());
        rpg.setPANUTR(alimentacion.getNumeroOtConsulta());
        rpg.setPFUNCI(Constantes.CONSULTAR5);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaNotasOtCmSubEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de crear una nota para un SubEdificio de una Cuenta
     * Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioAdd(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        CMCRNFR1 rpg = new CMCRNFR1();
        ResponseNotasOtCmSubEdificioList response = new ResponseNotasOtCmSubEdificioList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getCodigoEdificioConsulta());
        rpg.setPASEDI(alimentacion.getCodigoSubEdificioConsulta());
        rpg.setPANUTR(alimentacion.getNumeroOtConsulta());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaNotasOtCmSubEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de adicionar un linea a un nota ya existente en la Orden
     * de Trabajo de un SubEdificio de una cuenta matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioUpdate(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        CMCRNFR1 rpg = new CMCRNFR1();
        ResponseNotasOtCmSubEdificioList response = new ResponseNotasOtCmSubEdificioList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPAEDIF(alimentacion.getCodigoEdificioConsulta());
        rpg.setPASEDI(alimentacion.getCodigoSubEdificioConsulta());
        rpg.setPANUTR(alimentacion.getNumeroOtConsulta());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaNotasOtCmSubEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmOrdenTrabajoManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }
}
