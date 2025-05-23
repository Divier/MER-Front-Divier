/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.manager;

import co.com.claro.cmas400.ejb.constant.Constantes;
import co.com.claro.cmas400.ejb.core.Manager;
import co.com.claro.cmas400.ejb.core.ManagerLocal;
import co.com.claro.cmas400.ejb.core.PcmlException;
import co.com.claro.cmas400.ejb.pcmlinterface.CMABLCR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMAEAVR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMAIEFR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMAINFR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCOMSR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCPEFR1;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCPEFR2;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCPEFR3;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCPEFR4;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCUNIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCXEFR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMDIVSR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMEDFAR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMHBLCR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMINFAR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMINFSR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMINFTR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMINVFR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMMBLFR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMONEFR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMSEDMR0;
import co.com.claro.cmas400.ejb.request.RequestDataBlackListLog;
import co.com.claro.cmas400.ejb.request.RequestDataCompetenciaEdificioBlackList;
import co.com.claro.cmas400.ejb.request.RequestDataCompetenciaEdificioList;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaComunidad;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaEdificios;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaInventario;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaPorInventarioEquipo;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaPorTelefono;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaUnidades;
import co.com.claro.cmas400.ejb.request.RequestDataCuentaMatrizByCod;
import co.com.claro.cmas400.ejb.request.RequestDataCuentaMatrizByDir;
import co.com.claro.cmas400.ejb.request.RequestDataEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataEquiposEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataGestionDeAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionAdicionalEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionAdicionalSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataInventarioCuentaMatriz;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoSubEdificios;
import co.com.claro.cmas400.ejb.request.RequestDataNotasCuentaMatriz;
import co.com.claro.cmas400.ejb.respons.ResponseBlackListLogList;
import co.com.claro.cmas400.ejb.respons.ResponseCompetenciaEdificioBlackList;
import co.com.claro.cmas400.ejb.respons.ResponseCompetenciaEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaComunidadList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaDivisionList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaInventarioList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaPorInventarioEquipoList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaPorTelefonoList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaUnidadesList;
import co.com.claro.cmas400.ejb.respons.ResponseCuentaMatriz;
import co.com.claro.cmas400.ejb.respons.ResponseEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseEquiposEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseGestionDeAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionAdicionalEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionAdicionalSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseInventarioCuentaMatrizList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoSubEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasCuentaMatrizList;
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
public class CmCuentasMatricesManager {
    
    private static final Logger LOGGER = LogManager.getLogger(CmCuentasMatricesManager.class);
    
    /** N&uacute;mero m&aacute;ximo de caracteres del campo <b>PUSERID</b>. */
    private final int MAX_PUSERID_LENGHT = 10;
    
    
    // Objeto utilizado para llamar los PCML
    private ManagerLocal pcmlManager;

    /**
     * Metodo constructor utilizado para crear una instacia del pcmlmanager
     */
    public CmCuentasMatricesManager() {
        
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
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * competencia x edificio.Black list CM.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListQuery(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        CMABLCR0 rpg = new CMABLCR0();
        ResponseCompetenciaEdificioBlackList response =
                new ResponseCompetenciaEdificioBlackList();
        ArrayList tablaRta = new ArrayList();
        String codigoCuenta = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setPACODG(codigoCuenta);
        rpg.setPCML(Constantes.PCML);
        rpg.setPNOPCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * competencia x edificio. Black list CM.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListAdd(
            RequestDataCompetenciaEdificioBlackList alimentacion) {

        CMABLCR0 rpg = new CMABLCR0();
        ResponseCompetenciaEdificioBlackList response =
                new ResponseCompetenciaEdificioBlackList();
        ArrayList tablaRta = new ArrayList();
        String codigoCuenta = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPACODG(codigoCuenta);
        rpg.setPCML(Constantes.PCML);
        rpg.setPNOPCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaBlackList, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento
     * competencia x edificio. Black list CM.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListDelete(
            RequestDataCompetenciaEdificioBlackList alimentacion) {

        CMABLCR0 rpg = new CMABLCR0();
        ResponseCompetenciaEdificioBlackList response =
                new ResponseCompetenciaEdificioBlackList();
        ArrayList tablaRta = new ArrayList();
        String codigoCuenta = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setPACODG(codigoCuenta);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPCML(Constantes.PCML);
        rpg.setPNOPCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaBlackList, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Competencia
     * Edificio.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseCompetenciaEdificioList competenciaEdificioQuery(
            RequestDataCompetenciaEdificioList alimentacion) {
        CMCXEFR0 rpg = new CMCXEFR0();
        ResponseCompetenciaEdificioList response =
                new ResponseCompetenciaEdificioList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setCUENTA(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPNOPCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Competencia
     * Edificio.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseCompetenciaEdificioList competenciaEdificioAdd(
            RequestDataCompetenciaEdificioList alimentacion) {

        CMCXEFR0 rpg = new CMCXEFR0();
        ResponseCompetenciaEdificioList response =
                new ResponseCompetenciaEdificioList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setCUENTA(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPNOPCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEdificioCompetencia, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Competencia
     * Edificio.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseCompetenciaEdificioList competenciaEdificioDelete(
            RequestDataCompetenciaEdificioList alimentacion) {

        CMCXEFR0 rpg = new CMCXEFR0();
        ResponseCompetenciaEdificioList response =
                new ResponseCompetenciaEdificioList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setCUENTA(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPNOPCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEdificioCompetencia, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Histórico de
     * listas negras por edificio.Black list Log.
     *
     * @param alimentacion
     * @return ResponseBlackListLogList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseBlackListLogList manttoBlackListLogQuery(
            RequestDataBlackListLog alimentacion) {
        CMHBLCR0 rpg = new CMHBLCR0();
        ResponseBlackListLogList response =
                new ResponseBlackListLogList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setPACODG(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla SubEdificios
     * Información Adicional.
     *
     * @param alimentacion
     * @return ResponseInformacionSubEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseInformacionSubEdificioList informacionSubEdificioQuery(
            RequestDataInformacionSubEdificio alimentacion) {
        CMINFTR0 rpg = new CMINFTR0();
        ResponseInformacionSubEdificioList response =
                new ResponseInformacionSubEdificioList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setENTRA(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla SubEdificios
     * Información Adicional.
     *
     * @param alimentacion
     * @return ResponseInformacionSubEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdificioQuery(
            RequestDataInformacionAdicionalSubEdificio alimentacion) {
        CMINFSR0 rpg = new CMINFSR0();
        ResponseInformacionAdicionalSubEdificioList response =
                new ResponseInformacionAdicionalSubEdificioList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        String codigoSubEdificio = ("0000" + alimentacion.getCodigoSubEdificio())
                .substring(("0000" + alimentacion.getCodigoSubEdificio()).length() - 4);
        String tablaRta = "";
        rpg.setPACODG(codigoEdificio);
        rpg.setPASEQU(codigoSubEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR13);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(rpg.getTABLARTA());
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro en la tabla información
     * adicional de Sub Edificios.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseInformacionAdicionalSubEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdificioUpdate(
            RequestDataInformacionAdicionalSubEdificio alimentacion) {
        CMINFSR0 rpg = new CMINFSR0();
        ResponseInformacionAdicionalSubEdificioList response =
                new ResponseInformacionAdicionalSubEdificioList();

        ArrayList tablaRta = new ArrayList();
        String codigoCuenta = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        String codigoSubEdificio = ("0000" + alimentacion.getCodigoSubEdificio())
                .substring(("0000" + alimentacion.getCodigoSubEdificio()).length() - 4);
        rpg.setPACODG(codigoCuenta);
        rpg.setPASEQU(codigoSubEdificio);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR02);
        rpg.setPCONFI(Constantes.CONFIRMARACT02);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaInfoAdSubEdificio, alimentacion));
            String aux = tablaRta.toString().substring(1, 645);
            rpg.setTABLARTA(aux);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(rpg.getTABLARTA());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla información
     * adicional de edficios.
     *
     * @param alimentacion
     * @return ResponseInformacionAdicionalEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioQuery(
            RequestDataInformacionAdicionalEdificio alimentacion) {
        CMINFAR0 rpg = new CMINFAR0();
        ResponseInformacionAdicionalEdificioList response =
                new ResponseInformacionAdicionalEdificioList();
        String tablaRta = "";
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setPACODG(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR13);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(rpg.getTABLARTA());
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro en la tabla información
     * adicional de edificios.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioUpdate(
            RequestDataInformacionAdicionalEdificio alimentacion) {
        CMINFAR0 rpg = new CMINFAR0();
        ResponseInformacionAdicionalEdificioList response =
                new ResponseInformacionAdicionalEdificioList();

        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setPACODG(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR02);
        rpg.setPCONFI(Constantes.CONFIRMARACT02);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCiaAdminCompleta, alimentacion));
            String aux = tablaRta.toString().substring(1, 615);
            rpg.setTABLARTA(aux);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(rpg.getTABLARTA());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Equipos Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseEquiposEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseEquiposEdificioList equiposEdificioQuery(
            RequestDataEquiposEdificio alimentacion) {
        CMINVFR0 rpg = new CMINVFR0();
        ResponseEquiposEdificioList response = new ResponseEquiposEdificioList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setPAEDIF(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Mantenimiento Sub
     * Edificios
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del
     */
    public ResponseManttoSubEdificiosList manttoSubEdificiosQuery(
            RequestDataManttoSubEdificios alimentacion) {
        CMSEDMR0 rpg = new CMSEDMR0();
        ResponseManttoSubEdificiosList response = new ResponseManttoSubEdificiosList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setCUENTA(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.CONSULTAR13);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * SubEdificios.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoSubEdificiosList manttoSubEdificiosAdd(
            RequestDataManttoSubEdificios alimentacion) {
        CMSEDMR0 rpg = new CMSEDMR0();
        ResponseManttoSubEdificiosList response = new ResponseManttoSubEdificiosList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setCUENTA(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoSubedificios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro en la tabla de Mantenimiento
     * SubEdificios.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoSubEdificiosList manttoSubEdificiosUpdate(
            RequestDataManttoSubEdificios alimentacion) {
        CMSEDMR0 rpg = new CMSEDMR0();
        ResponseManttoSubEdificiosList response = new ResponseManttoSubEdificiosList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setCUENTA(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoSubedificios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }


    /**
     * Metodo encargado de eliminar un registro en la tabla de Mantenimiento
     * SubEdificios.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoSubEdificiosList manttoSubEdificiosDelete(
            RequestDataManttoSubEdificios alimentacion) {
        CMSEDMR0 rpg = new CMSEDMR0();
        ResponseManttoSubEdificiosList response = new ResponseManttoSubEdificiosList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoCuenta())
                .substring(("000000000" + alimentacion.getCodigoCuenta()).length() - 9);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setCUENTA(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoSubedificios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;

    }

    /**
     * Metodo encargado de consultar la tabla de Mantenimiento de Edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML.
     */
    public ResponseManttoEdificioList manttoEdificioQuery(
            RequestDataManttoEdificio alimentacion) {
        CMMBLFR0 rpg = new CMMBLFR0();
        ResponseManttoEdificioList response = new ResponseManttoEdificioList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setPACODG(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(rpg.getTABLARTA());
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro en la tabla de Mantenimiento de
     * Edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML.
     */
    public ResponseManttoEdificioList manttoEdificioAdd(
            RequestDataManttoEdificio alimentacion) {
        CMMBLFR0 rpg = new CMMBLFR0();
        ResponseManttoEdificioList response = new ResponseManttoEdificioList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPACODG(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.ACTUALIZAR02);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEdificioCompleta, alimentacion));
            String aux = tablaRta.toString().substring(1, 831);
            LOGGER.info("manttoEdificioAdd TABLARTA: "+aux);
            rpg.setTABLARTA(aux);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro en la tabla de Mantenimiento de
     * Edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML.
     */
    public ResponseManttoEdificioList manttoEdificioUpdate(
            RequestDataManttoEdificio alimentacion) {
        CMMBLFR0 rpg = new CMMBLFR0();
        ResponseManttoEdificioList response = new ResponseManttoEdificioList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setPACODG(codigoEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.ACTUALIZAR02);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEdificioCompleta, alimentacion));
            String aux = tablaRta.toString().substring(1, 831);
            rpg.setTABLARTA(aux);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar Edificios.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCuentaMatriz Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseEdificioList edificioQuery(
            RequestDataEdificio alimentacion) {
        CMEDFAR0 rpg = new CMEDFAR0();
        ResponseEdificioList response = new ResponseEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPNFCOD(alimentacion.getCodigo());
        rpg.setPANOMB(alimentacion.getNombre());
        rpg.setPNFCOM(alimentacion.getComunidad());
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar Edificios o Cuenta Matriz por Codigo.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCuentaMatriz Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCuentaMatriz cuentaMatrizByCodQuery(
            RequestDataCuentaMatrizByCod alimentacion) {
        CMCPEFR1 rpg = new CMCPEFR1();
        ResponseCuentaMatriz response = new ResponseCuentaMatriz();
        String codigoEdificio = ("000000000" + alimentacion.getCodigo())
                .substring(("000000000" + alimentacion.getCodigo()).length() - 9);
        rpg.setPCML(Constantes.PCML);
        rpg.setPNNUED_CHR(codigoEdificio);
        try {
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(rpg.getT_CMCEDFR1());
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar Edificios o Cuenta Matriz por Dirección.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCuentaMatriz Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCuentaMatriz cuentaMatrizByDirQuery(
            RequestDataCuentaMatrizByDir alimentacion) {
        CMCPEFR2 rpg = new CMCPEFR2();
        ResponseCuentaMatriz response = new ResponseCuentaMatriz();
        rpg.setPCML(Constantes.PCML);
        rpg.setPNNCAL(alimentacion.getCalle());
        rpg.setPNHOME(alimentacion.getNumeroCasa());
        rpg.setPNDIVI(alimentacion.getDivision());
        rpg.setPNCOMU(alimentacion.getComunidad());
        try {
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(rpg.getT_CMCEDFR1());
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los edificios registrados
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConsultaEdificiosList consultaEdificiosQuery(
            RequestDataConsultaEdificios alimentacion) {
        CMEDFAR0 rpg = new CMEDFAR0();
        ResponseConsultaEdificiosList response = new ResponseConsultaEdificiosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPANOMB(alimentacion.getNombreEdificio());
        rpg.setPNFCOD(alimentacion.getCodigoEdificio());
        rpg.setPNFCOM(alimentacion.getComunidad());
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar un edificio por medio de la informacion del
     * inventario de los equipos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaPorInventarioEquipoList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseConsultaPorInventarioEquipoList consultaPorInventarioEquipoQuery(
            RequestDataConsultaPorInventarioEquipo alimentacion) {
        CMCPEFR3 rpg = new CMCPEFR3();
        ResponseConsultaPorInventarioEquipoList response =
                new ResponseConsultaPorInventarioEquipoList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPNITMC(alimentacion.getTipoEquipo());
        rpg.setPNMANC(alimentacion.getFabricaEquipo());
        rpg.setPNSERI(alimentacion.getSerieEquipo());

        try {
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setCodigoEdificio(rpg.getPRNUED());
            response.cargarListaRespuesta(rpg.getT_CMCEDFR1());
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar un edificio por medio del numero de
     * telefono
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaPorInventarioEquipoList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseConsultaPorTelefonoList consultaPorTelefonoQuery(
            RequestDataConsultaPorTelefono alimentacion) {
        CMCPEFR4 rpg = new CMCPEFR4();
        ResponseConsultaPorTelefonoList response =
                new ResponseConsultaPorTelefonoList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPNTEL1_A(alimentacion.getTelefonoEdificio());
        try {
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.setCodigoEdificio(rpg.getPRNUED());
            response.cargarListaRespuesta(rpg.getT_CMCEDFR1());
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar las Unidades de un Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaUnidadesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConsultaUnidadesList consultaUnidadesQuery(
            RequestDataConsultaUnidades alimentacion) {
        CMCUNIR0 rpg = new CMCUNIR0();
        ResponseConsultaUnidadesList response =
                new ResponseConsultaUnidadesList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        ArrayList tablaRta = new ArrayList();
        rpg.setPAEDIF(codigoEdificio);
        rpg.setPUSCON(alimentacion.getEstado());
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar las divisiones
     *
     * @return ResponseConsultaDivisionList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConsultaDivisionList consultaDivisionHelp() {
        CMDIVSR0 rpg = new CMDIVSR0();
        ResponseConsultaDivisionList response =
                new ResponseConsultaDivisionList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar las Comunidades
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaComunidadList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConsultaComunidadList consultaComunidadHelp(
            RequestDataConsultaComunidad alimentacion) {
        CMCOMSR0 rpg = new CMCOMSR0();
        ResponseConsultaComunidadList response =
                new ResponseConsultaComunidadList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPADIVI(alimentacion.getCodigoDivision());
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar el inventario de una Cuenta Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizQuery(
            RequestDataInventarioCuentaMatriz alimentacion) {
        CMAIEFR0 rpg = new CMAIEFR0();
        ResponseInventarioCuentaMatrizList response =
                new ResponseInventarioCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setCUENTA(codigoEdificio);
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
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de asignar inventario a un equipo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizAdd(
            RequestDataInventarioCuentaMatriz alimentacion) {
        CMAIEFR0 rpg = new CMAIEFR0();
        ResponseInventarioCuentaMatrizList response =
                new ResponseInventarioCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setCUENTA(codigoEdificio);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaInventarioCuentaMatriz, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de desvincular el inventario de un edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizDelete(
            RequestDataInventarioCuentaMatriz alimentacion) {
        CMAIEFR0 rpg = new CMAIEFR0();
        ResponseInventarioCuentaMatrizList response =
                new ResponseInventarioCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificio())
                .substring(("000000000" + alimentacion.getCodigoEdificio()).length() - 9);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setCUENTA(codigoEdificio);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaInventarioCuentaMatriz, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite consultar los inventarios para seleccionar
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaInventarioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConsultaInventarioList consultaInventarioHelp(
            RequestDataConsultaInventario alimentacion) {
        CMAINFR0 rpg = new CMAINFR0();
        ResponseConsultaInventarioList response =
                new ResponseConsultaInventarioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPNFITM(alimentacion.getTipo());
        rpg.setPNFMAN(alimentacion.getFabricante());
        rpg.setPNFSER(("                    " + alimentacion.getNumeroSerie()).
                substring(("                    " + alimentacion.getNumeroSerie()).length() - 20));
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar las notas de una cuenta matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseNotasCuentaMatrizList listNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion) {
        CMONEFR0 rpg = new CMONEFR0();
        ResponseNotasCuentaMatrizList response =
                new ResponseNotasCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();
        String codigoCuenta = ("000000000" + alimentacion.getNumeroEdificio())
                .substring(("000000000" + alimentacion.getNumeroEdificio()).length() - 9);
        rpg.setEDIFICIO(codigoCuenta);
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar la descripcion de las notas de una cuenta
     * matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseNotasCuentaMatrizList descripcionNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion) {
        CMONEFR0 rpg = new CMONEFR0();
        ResponseNotasCuentaMatrizList response =
                new ResponseNotasCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();
        String codigoCuenta = ("000000000" + alimentacion.getNumeroEdificio())
                .substring(("000000000" + alimentacion.getNumeroEdificio()).length() - 9);

        rpg.setEDIFICIO(codigoCuenta);
        rpg.setNOTA(alimentacion.getCodigoNotaConsultar());
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.CONSULTAR5);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite crear una nota en una Cuenta Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseNotasCuentaMatrizList notasCuentaMatrizAdd(
            RequestDataNotasCuentaMatriz alimentacion) {
        CMONEFR0 rpg = new CMONEFR0();
        ResponseNotasCuentaMatrizList response =
                new ResponseNotasCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();
        String codigoCuenta = ("000000000" + alimentacion.getNumeroEdificio())
                .substring(("000000000" + alimentacion.getNumeroEdificio()).length() - 9);
        String numFilas = ("00000" + alimentacion.getDataNotasCuentaMatriz().size())
                .substring(("00000" + alimentacion.getDataNotasCuentaMatriz().size()).length() - 5);

        rpg.setEDIFICIO(codigoCuenta);
        rpg.setPUSERID(alimentacion.getUsuarioModificador());
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.CREAR);
        rpg.setNUM_REG(numFilas);

        try {
            if (alimentacion.getDataNotasCuentaMatriz().size() > 0) {
                for (Object linea : alimentacion.getDataNotasCuentaMatriz()) {
                    tablaRta.add(VectorizarTablaRta.vectorizar(
                            VectorizarTablaRta.tablaNotasCuentaMatriz, linea));
                }
            }
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite actualizar una nota en una Cuenta Matriz
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseNotasCuentaMatrizList notasCuentaMatrizUpdate(
            RequestDataNotasCuentaMatriz alimentacion) {
        CMONEFR0 rpg = new CMONEFR0();
        ResponseNotasCuentaMatrizList response =
                new ResponseNotasCuentaMatrizList();
        ArrayList tablaRta = new ArrayList();
        String codigoCuenta = ("000000000" + alimentacion.getNumeroEdificio())
                .substring(("000000000" + alimentacion.getNumeroEdificio()).length() - 9);
        String numFilas = ("00000" + alimentacion.getDataNotasCuentaMatriz().size())
                .substring(("00000" + alimentacion.getDataNotasCuentaMatriz().size()).length() - 5);

        rpg.setEDIFICIO(codigoCuenta);
        rpg.setNOTA(alimentacion.getCodigoNotaConsultar());
        rpg.setPUSERID(alimentacion.getUsuarioModificador());
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.ACTUALIZAR);
        rpg.setNUM_REG(numFilas);

        try {
            if (alimentacion.getDataNotasCuentaMatriz().size() > 0) {
                for (Object linea : alimentacion.getDataNotasCuentaMatriz()) {
                    tablaRta.add(VectorizarTablaRta.vectorizar(
                            VectorizarTablaRta.tablaNotasCuentaMatriz, linea));
                }
            }
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite consultar la table de Gestion De Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseGestionDeAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaQuery(
            RequestDataGestionDeAvanzada alimentacion) {
        CMAEAVR0 rpg = new CMAEAVR0();
        ResponseGestionDeAvanzadaList response =
                new ResponseGestionDeAvanzadaList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificioConsultar())
                .substring(("000000000" + alimentacion.getCodigoEdificioConsultar()).length() - 9);
        String codigoSubEdificio = ("000000000" + alimentacion.getSubEdificioConsultar())
                .substring(("000000000" + alimentacion.getSubEdificioConsultar()).length() - 4);
        rpg.setCTAMAT(codigoEdificio);
        rpg.setSUBEDIFC(codigoSubEdificio);
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.CONSULTAR11);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite insertar un registro en la tabla de Gestion De
     * Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseGestionDeAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaAdd(
            RequestDataGestionDeAvanzada alimentacion) {
        CMAEAVR0 rpg = new CMAEAVR0();
        ResponseGestionDeAvanzadaList response =
                new ResponseGestionDeAvanzadaList();
        ArrayList tablaRta = new ArrayList();
        String codigoEdificio = ("000000000" + alimentacion.getCodigoEdificioConsultar())
                .substring(("000000000" + alimentacion.getCodigoEdificioConsultar()).length() - 9);
        String codigoSubEdificio = ("000000000" + alimentacion.getSubEdificioConsultar())
                .substring(("000000000" + alimentacion.getSubEdificioConsultar()).length() - 4);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setCTAMAT(codigoEdificio);
        rpg.setSUBEDIFC(codigoSubEdificio);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPCML(Constantes.PCML);
        rpg.setFUNCION(Constantes.CREAR06);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaGestionDeAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException | ApplicationException ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmCuentasMatricesManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }
}
