/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.manager;

import co.com.claro.cmas400.ejb.constant.Constantes;
import co.com.claro.cmas400.ejb.core.Manager;
import co.com.claro.cmas400.ejb.core.ManagerLocal;
import co.com.claro.cmas400.ejb.core.PcmlException;
import co.com.claro.cmas400.ejb.pcmlinterface.CMAAVMR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMADMER0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMAELMR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMASCER0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMBLCKR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCOMPR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMCONSR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMEAVMR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMEEDIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMELOGR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMGEAVR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMMATER0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMMXPRR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMNBAR01;
import co.com.claro.cmas400.ejb.pcmlinterface.CMNDINR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMNEDI01;
import co.com.claro.cmas400.ejb.pcmlinterface.CMNODAR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMODATR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMPINMR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMPRVDR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMRARRR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMRESTR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMSAVMR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTACMR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTCOAR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTCOMR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTDISR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTEDIR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTESTR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTMATR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTNOTR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTPDUR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTPROR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMTTRAR0;
import co.com.claro.cmas400.ejb.pcmlinterface.CMUBCNR0;
import co.com.claro.cmas400.ejb.request.RequestDataAdminCompany;
import co.com.claro.cmas400.ejb.request.RequestDataAlimentacionElectrica;
import co.com.claro.cmas400.ejb.request.RequestDataAsignarAsesorAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataCiaAscensores;
import co.com.claro.cmas400.ejb.request.RequestDataCodigoBlackList;
import co.com.claro.cmas400.ejb.request.RequestDataConstructoras;
import co.com.claro.cmas400.ejb.request.RequestDataEstadoResultadoOt;
import co.com.claro.cmas400.ejb.request.RequestDataEstrato;
import co.com.claro.cmas400.ejb.request.RequestDataManttoAsesorGestionDeAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataManttoCompetencia;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEdificios;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEstadoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEstadosAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataManttoInfoNodo;
import co.com.claro.cmas400.ejb.request.RequestDataManttoInformacionBarrios;
import co.com.claro.cmas400.ejb.request.RequestDataManttoMaterialProveedor;
import co.com.claro.cmas400.ejb.request.RequestDataManttoMateriales;
import co.com.claro.cmas400.ejb.request.RequestDataManttoNodos;
import co.com.claro.cmas400.ejb.request.RequestDataManttoPuntoInicial;
import co.com.claro.cmas400.ejb.request.RequestDataManttoTipoCompetencia;
import co.com.claro.cmas400.ejb.request.RequestDataManttoTipoDistribucionRedInterna;
import co.com.claro.cmas400.ejb.request.RequestDataManttoTipoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoTipoNotas;
import co.com.claro.cmas400.ejb.request.RequestDataMotivosCambioFecha;
import co.com.claro.cmas400.ejb.request.RequestDataOrigenDatos;
import co.com.claro.cmas400.ejb.request.RequestDataProductos;
import co.com.claro.cmas400.ejb.request.RequestDataProveedores;
import co.com.claro.cmas400.ejb.request.RequestDataRazonArreglo;
import co.com.claro.cmas400.ejb.request.RequestDataSupervisorAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataTipoAcometida;
import co.com.claro.cmas400.ejb.request.RequestDataTipoMateriales;
import co.com.claro.cmas400.ejb.request.RequestDataTipoProyecto;
import co.com.claro.cmas400.ejb.request.RequestDataTipoTrabajo;
import co.com.claro.cmas400.ejb.request.RequestDataUbicacionCaja;
import co.com.claro.cmas400.ejb.respons.ResponseAdminCompanyList;
import co.com.claro.cmas400.ejb.respons.ResponseAsignarAsesorAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseCiaAscensoresList;
import co.com.claro.cmas400.ejb.respons.ResponseCodigoBlackList;
import co.com.claro.cmas400.ejb.respons.ResponseConstructorasList;
import co.com.claro.cmas400.ejb.respons.ResponseEstadoResultadoOtList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoAlimentacionElectList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoAsesorGestionDeAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoCompetenciaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEstadoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEstadosAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEstratoList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoInfoNodoList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoInformacionBarriosList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoMaterialProveedorList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoMaterialesList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoMotivosCambioFechaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoNodosList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoPuntoInicialList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoCompetenciaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoDistribucionRedInternaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoNotasList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoTrabajoList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoUbicacionCajaList;
import co.com.claro.cmas400.ejb.respons.ResponseOrigenDatosList;
import co.com.claro.cmas400.ejb.respons.ResponseProductosList;
import co.com.claro.cmas400.ejb.respons.ResponseProveedoresList;
import co.com.claro.cmas400.ejb.respons.ResponseRazonArregloList;
import co.com.claro.cmas400.ejb.respons.ResponseSupervisorAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoAcometidaList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoCompetenciaList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoMaterialesList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoProyectoList;
import co.com.claro.cmas400.ejb.utils.ServiceUtils;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Properties;
import javax.ejb.EJBException;

/**
 *
 * @author Alquiler
 */
public class CmTablasBasicasManager {
    
    private static final Logger LOGGER = LogManager.getLogger(CmTablasBasicasManager.class);
    
    /** N&uacute;mero m&aacute;ximo de caracteres del campo <b>PUSERID</b>. */
    private final int MAX_PUSERID_LENGHT = 10;
    
    // Objeto utilizado para llamar los PCML
    private ManagerLocal pcmlManager;

    /**
     * Metodo constructor utilizado para crear una instacia del pcmlmanager
     */
    public CmTablasBasicasManager() {

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
                throw new ApplicationException("Parámetro AS400_PROPERTIES_FILE_PATH no configurado.");
            }
        } catch (NoSuchElementException ex) {
            properties = null;
            LOGGER.error("No se encuentra configurado el parámetro AS400_PROPERTIES_FILE_PATH. EX000: " + ex.getMessage(), ex);
            throw ex;
        } catch (IOException ex) {
            properties = null;
            LOGGER.error("Error al momento de consultar los parámetros de tablas básicas (AS400_PROPERTIES_FILE_PATH). EX000: " + ex.getMessage(), ex);
            throw new RuntimeException("Error al momento de consultar los parámetros de tablas básicas (AS400_PROPERTIES_FILE_PATH). EX000: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            properties = null;
            LOGGER.error("Error al momento de consultar los parámetros de tablas básicas (AS400_PROPERTIES_FILE_PATH). EX000: " + ex.getMessage(), ex);
            throw new RuntimeException("Error al momento de consultar los parámetros de tablas básicas (AS400_PROPERTIES_FILE_PATH). EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     * Metodo encargado de adicionar un registro a la tabla de alimentacion
     * electrica
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectAdd(
            RequestDataAlimentacionElectrica alimentacion) {
        CMAELMR0 rpg = new CMAELMR0();
        ResponseManttoAlimentacionElectList response = new ResponseManttoAlimentacionElectList();
        ArrayList tablaRta = new ArrayList<>();
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAlimentacionElectrica, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException| ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de alimentacion
     * electrica
     *
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectQuery() {
        CMAELMR0 rpg = new CMAELMR0();
        ResponseManttoAlimentacionElectList response = new ResponseManttoAlimentacionElectList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);
        rpg.setPCML(Constantes.PCML);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de actualizar los registros de la tabla de alimentacion
     * electrica
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectUpdate(
            RequestDataAlimentacionElectrica alimentacion) {
        CMAELMR0 rpg = new CMAELMR0();
        ResponseManttoAlimentacionElectList response = new ResponseManttoAlimentacionElectList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAlimentacionElectrica, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de alimentacion
     * electrica
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectDelete(
            RequestDataAlimentacionElectrica alimentacion) {
        CMAELMR0 rpg = new CMAELMR0();
        ResponseManttoAlimentacionElectList response = new ResponseManttoAlimentacionElectList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAlimentacionElectrica, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Compañia
     * Administracion
     *
     * @param alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseAdminCompanyList manttoAdminCompanyQuery(
            RequestDataAdminCompany alimentacion) {
        CMADMER0 rpg = new CMADMER0();
        ResponseAdminCompanyList response = new ResponseAdminCompanyList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAdminCompany, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de crear un registro en la tabla de Compañia
     * Administracion
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseAdminCompanyList manttoAdminCompanyAdd(
            RequestDataAdminCompany alimentacion) {
        CMADMER0 rpg = new CMADMER0();
        ResponseAdminCompanyList response = new ResponseAdminCompanyList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAdminCompany, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro en la tabla de Compañia
     * Administracion
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseAdminCompanyList manttoAdminCompanyUpdate(
            RequestDataAdminCompany alimentacion) {
        CMADMER0 rpg = new CMADMER0();
        ResponseAdminCompanyList response = new ResponseAdminCompanyList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAdminCompany, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Compañia
     * Administracion
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseAdminCompanyList manttoAdminCompanyDelete(
            RequestDataAdminCompany alimentacion) {
        CMADMER0 rpg = new CMADMER0();
        ResponseAdminCompanyList response = new ResponseAdminCompanyList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAdminCompany, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Compañia de
     * Ascensores
     *
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCiaAscensoresList manttoCiaAscensoresQuery() {
        CMASCER0 rpg = new CMASCER0();
        ResponseCiaAscensoresList response = new ResponseCiaAscensoresList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Compañias de
     * Ascensores
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCiaAscensoresList manttoCiaAscensoresAdd(RequestDataCiaAscensores alimentacion) {
        CMASCER0 rpg = new CMASCER0();
        ResponseCiaAscensoresList response = new ResponseCiaAscensoresList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(VectorizarTablaRta.tablaCiaAscensores, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Estados
     * Edificio
     *
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioQuery() {
        CMEEDIR0 rpg = new CMEEDIR0();
        ResponseManttoEstadoEdificioList response =
                new ResponseManttoEstadoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Estados Edificio
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioAdd(
            RequestDataManttoEstadoEdificio alimentacion) {
        CMEEDIR0 rpg = new CMEEDIR0();
        ResponseManttoEstadoEdificioList response =
                new ResponseManttoEstadoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoEstadoEdificio,
                    alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Estados Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioDelete(
            RequestDataManttoEstadoEdificio alimentacion) {
        CMEEDIR0 rpg = new CMEEDIR0();
        ResponseManttoEstadoEdificioList response =
                new ResponseManttoEstadoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoEstadoEdificio,
                    alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Estados
     * Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioUpdate(
            RequestDataManttoEstadoEdificio alimentacion) {
        CMEEDIR0 rpg = new CMEEDIR0();
        ResponseManttoEstadoEdificioList response =
                new ResponseManttoEstadoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoEstadoEdificio,
                    alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Estados
     * Edificio para las pantallas de ayuda
     *
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioHelp() {
        CMEEDIR0 rpg = new CMEEDIR0();
        ResponseManttoEstadoEdificioList response =
                new ResponseManttoEstadoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);
        rpg.setPCML(Constantes.PCML);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Estado
     * Resultado OT
     *
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseEstadoResultadoOtList estadoResultadoOtQuery() {
        CMRESTR0 rpg = new CMRESTR0();
        ResponseEstadoResultadoOtList response =
                new ResponseEstadoResultadoOtList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Estado Resultado
     * OT
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseEstadoResultadoOtList estadoResultadoOtAdd(
            RequestDataEstadoResultadoOt alimentacion) {
        CMRESTR0 rpg = new CMRESTR0();
        ResponseEstadoResultadoOtList response =
                new ResponseEstadoResultadoOtList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEstadoResultadoOt, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Estado Resultado
     * OT
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseEstadoResultadoOtList estadoResultadoOtDelete(
            RequestDataEstadoResultadoOt alimentacion) {
        CMRESTR0 rpg = new CMRESTR0();
        ResponseEstadoResultadoOtList response =
                new ResponseEstadoResultadoOtList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEstadoResultadoOt, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Estado
     * Resultado OT
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseEstadoResultadoOtList estadoResultadoOtUpdate(
            RequestDataEstadoResultadoOt alimentacion) {
        CMRESTR0 rpg = new CMRESTR0();
        ResponseEstadoResultadoOtList response =
                new ResponseEstadoResultadoOtList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEstadoResultadoOt, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * Compentencia
     *
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoCompetenciaList manttoCompetenciaQuery() {
        CMCOMPR0 rpg = new CMCOMPR0();
        ResponseManttoCompetenciaList response =
                new ResponseManttoCompetenciaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Estado Resultado
     * OT
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoCompetenciaList manttoCompetenciaAdd(
            RequestDataManttoCompetencia alimentacion) {
        CMCOMPR0 rpg = new CMCOMPR0();
        ResponseManttoCompetenciaList response =
                new ResponseManttoCompetenciaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoCompetencia, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento
     * Competencia
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoCompetenciaList manttoCompetenciaDelete(
            RequestDataManttoCompetencia alimentacion) {
        CMCOMPR0 rpg = new CMCOMPR0();
        ResponseManttoCompetenciaList response =
                new ResponseManttoCompetenciaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String pUserID = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(pUserID);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoCompetencia, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * Competencia
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoCompetenciaList manttoCompetenciaUpdate(
            RequestDataManttoCompetencia alimentacion) {
        CMCOMPR0 rpg = new CMCOMPR0();
        ResponseManttoCompetenciaList response =
                new ResponseManttoCompetenciaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoCompetencia, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        } catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Productos
     *
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseProductosList productosQuery() {
        CMTPDUR0 rpg = new CMTPDUR0();
        ResponseProductosList response =
                new ResponseProductosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Productos
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseProductosList productosAdd(
            RequestDataProductos alimentacion) {
        CMTPDUR0 rpg = new CMTPDUR0();
        ResponseProductosList response =
                new ResponseProductosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaProductos, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Productos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseProductosList productosDelete(
            RequestDataProductos alimentacion) {
        CMTPDUR0 rpg = new CMTPDUR0();
        ResponseProductosList response =
                new ResponseProductosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaProductos, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Productos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseProductosList productosUpdate(
            RequestDataProductos alimentacion) {
        CMTPDUR0 rpg = new CMTPDUR0();
        ResponseProductosList response =
                new ResponseProductosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaProductos, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Proveedores
     *
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseProveedoresList proveedoresQuery() {
        CMPRVDR0 rpg = new CMPRVDR0();
        ResponseProveedoresList response =
                new ResponseProveedoresList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Proveedores
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseProveedoresList proveedoresAdd(
            RequestDataProveedores alimentacion) {
        CMPRVDR0 rpg = new CMPRVDR0();
        ResponseProveedoresList response =
                new ResponseProveedoresList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaProveedores, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Proveedores
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseProveedoresList proveedoresDelete(
            RequestDataProveedores alimentacion) {
        CMPRVDR0 rpg = new CMPRVDR0();
        ResponseProveedoresList response =
                new ResponseProveedoresList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaProveedores, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Proveedores
     * Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseProveedoresList proveedoresUpdate(
            RequestDataProveedores alimentacion) {
        CMPRVDR0 rpg = new CMPRVDR0();
        ResponseProveedoresList response =
                new ResponseProveedoresList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaProveedores, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * Punto Inicial
     *
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoPuntoInicialList manttoPuntoInicialQuery() {
        CMPINMR0 rpg = new CMPINMR0();
        ResponseManttoPuntoInicialList response =
                new ResponseManttoPuntoInicialList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * Punto Inicial
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoPuntoInicialList manttoPuntoInicialAdd(
            RequestDataManttoPuntoInicial alimentacion) {
        CMPINMR0 rpg = new CMPINMR0();
        ResponseManttoPuntoInicialList response =
                new ResponseManttoPuntoInicialList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoPuntoInicial, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento
     * Punto Inicial
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoPuntoInicialList manttoPuntoInicialDelete(
            RequestDataManttoPuntoInicial alimentacion) {
        CMPINMR0 rpg = new CMPINMR0();
        ResponseManttoPuntoInicialList response =
                new ResponseManttoPuntoInicialList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoPuntoInicial, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * Punto Inicial
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoPuntoInicialList manttoPuntoInicialUpdate(
            RequestDataManttoPuntoInicial alimentacion) {
        CMPINMR0 rpg = new CMPINMR0();
        ResponseManttoPuntoInicialList response =
                new ResponseManttoPuntoInicialList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoPuntoInicial, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * de tipo de distribución de red interna.
     *
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaQuery() {
        CMTDISR0 rpg = new CMTDISR0();
        ResponseManttoTipoDistribucionRedInternaList response =
                new ResponseManttoTipoDistribucionRedInternaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento de
     * tipo de distribución de red interna.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaAdd(
            RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        CMTDISR0 rpg = new CMTDISR0();
        ResponseManttoTipoDistribucionRedInternaList response =
                new ResponseManttoTipoDistribucionRedInternaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoDistribucionRedInterna,
                    alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento }de
     * tipo de distribución de red interna.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaDelete(
            RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        CMTDISR0 rpg = new CMTDISR0();
        ResponseManttoTipoDistribucionRedInternaList response =
                new ResponseManttoTipoDistribucionRedInternaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoDistribucionRedInterna,
                    alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * de tipo de distribución de red interna.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaUpdate(
            RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        CMTDISR0 rpg = new CMTDISR0();
        ResponseManttoTipoDistribucionRedInternaList response =
                new ResponseManttoTipoDistribucionRedInternaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoDistribucionRedInterna,
                    alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * de tipos de edificios.
     *
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoEdificioList manttoTipoEdificioQuery() {
        CMTEDIR0 rpg = new CMTEDIR0();
        ResponseManttoTipoEdificioList response =
                new ResponseManttoTipoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento de
     * tipos de edificios.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoEdificioList manttoTipoEdificioAdd(
            RequestDataManttoTipoEdificio alimentacion) {
        CMTEDIR0 rpg = new CMTEDIR0();
        ResponseManttoTipoEdificioList response =
                new ResponseManttoTipoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento de
     * tipos de edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoEdificioList manttoTipoEdificioDelete(
            RequestDataManttoTipoEdificio alimentacion) {
        CMTEDIR0 rpg = new CMTEDIR0();
        ResponseManttoTipoEdificioList response =
                new ResponseManttoTipoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * de tipos de edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoEdificioList manttoTipoEdificioUpdate(
            RequestDataManttoTipoEdificio alimentacion) {
        CMTEDIR0 rpg = new CMTEDIR0();
        ResponseManttoTipoEdificioList response =
                new ResponseManttoTipoEdificioList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoEdificio, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * de tipos de notas.
     *
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoNotasList manttoTipoNotasQuery() {
        CMTNOTR0 rpg = new CMTNOTR0();
        ResponseManttoTipoNotasList response =
                new ResponseManttoTipoNotasList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento de
     * tipos de notas.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoNotasList manttoTipoNotasAdd(
            RequestDataManttoTipoNotas alimentacion) {
        CMTNOTR0 rpg = new CMTNOTR0();
        ResponseManttoTipoNotasList response =
                new ResponseManttoTipoNotasList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoNotas, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento de
     * tipos de notas.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoNotasList manttoTipoNotasDelete(
            RequestDataManttoTipoNotas alimentacion) {
        CMTNOTR0 rpg = new CMTNOTR0();
        ResponseManttoTipoNotasList response =
                new ResponseManttoTipoNotasList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoNotas, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * de tipos de notas.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoNotasList manttoTipoNotasUpdate(
            RequestDataManttoTipoNotas alimentacion) {
        CMTNOTR0 rpg = new CMTNOTR0();
        ResponseManttoTipoNotasList response =
                new ResponseManttoTipoNotasList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoNotas, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Tipo de
     * Competencia
     *
     * @return ResponseTipoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoCompetenciaList tipoCompetenciaQuery() {
        CMTCOAR0 rpg = new CMTCOAR0();
        ResponseTipoCompetenciaList response =
                new ResponseTipoCompetenciaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setENTRA("");
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG("");

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * Edificios
     *
     * @param alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEdificiosList manttoEdificiosQuery(
            RequestDataManttoEdificios alimentacion) {

        CMNEDI01 rpg = new CMNEDI01();
        ResponseManttoEdificiosList response =
                new ResponseManttoEdificiosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setSFOPCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setCODEDI_A(alimentacion.getCodigo());
        rpg.setDESEDI(alimentacion.getDescripcion());
        rpg.setCODIVI(alimentacion.getCodigoDivision());
        rpg.setCODCOM(alimentacion.getCodigoComunidad());
        rpg.setCODBAR_A(alimentacion.getCodigoBarrio());
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getPMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * Edificios
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEdificiosList manttoEdificiosAdd(
            RequestDataManttoEdificios alimentacion) {

        CMNEDI01 rpg = new CMNEDI01();
        ResponseManttoEdificiosList response = new ResponseManttoEdificiosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setSFOPCI(Constantes.CREAR_SFOPCI);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        String nombreUsuario = ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT);
        rpg.setPUSERID(nombreUsuario);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoEdificios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getPMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento
     * Edificios
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEdificiosList manttoEdificiosDelete(
            RequestDataManttoEdificios alimentacion) {

        CMNEDI01 rpg = new CMNEDI01();
        ResponseManttoEdificiosList response =
                new ResponseManttoEdificiosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setSFOPCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoEdificios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getPMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * Edificios
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEdificiosList manttoEdificiosUpdate(
            RequestDataManttoEdificios alimentacion) {

        CMNEDI01 rpg = new CMNEDI01();
        ResponseManttoEdificiosList response =
                new ResponseManttoEdificiosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setSFOPCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoEdificios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getPMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * Materiales
     *
     * @param alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoMaterialesList manttoMaterialesQuery(
            RequestDataManttoMateriales alimentacion) {

        CMMATER0 rpg = new CMMATER0();
        ResponseManttoMaterialesList response =
                new ResponseManttoMaterialesList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPNFCOD_A(alimentacion.getCodigo_a());
        rpg.setPNFTIP_A(alimentacion.getTipo_a());
        rpg.setPNFDES_A(alimentacion.getDescripcion_a());
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * Materiales
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoMaterialesList manttoMaterialesAdd(
            RequestDataManttoMateriales alimentacion) {

        CMMATER0 rpg = new CMMATER0();
        ResponseManttoMaterialesList response =
                new ResponseManttoMaterialesList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPNFCOD_A(alimentacion.getCodigo_a());
        rpg.setPNFTIP_A(alimentacion.getTipo_a());
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(alimentacion.getNombreUsuarios());
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoMateriales, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento
     * Materiales
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoMaterialesList manttoMaterialesDelete(
            RequestDataManttoMateriales alimentacion) {

        CMMATER0 rpg = new CMMATER0();
        ResponseManttoMaterialesList response =
                new ResponseManttoMaterialesList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPNFCOD_A(alimentacion.getCodigo_a());
        rpg.setPNFTIP_A(alimentacion.getTipo_a());
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(alimentacion.getNombreUsuarios());
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoMateriales, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * Materiales
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoMaterialesList manttoMaterialesUpdate(
            RequestDataManttoMateriales alimentacion) {

        CMMATER0 rpg = new CMMATER0();
        ResponseManttoMaterialesList response =
                new ResponseManttoMaterialesList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPNFCOD_A(alimentacion.getCodigo_a());
        rpg.setPNFTIP_A(alimentacion.getTipo_a());
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(alimentacion.getNombreUsuarios());
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoMateriales, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * Material por Proveedor.
     *
     * @param alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorQuery(
            RequestDataManttoMaterialProveedor alimentacion) {
        CMMXPRR0 rpg = new CMMXPRR0();
        ResponseManttoMaterialProveedorList response =
                new ResponseManttoMaterialProveedorList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        rpg.setPNFPRO_A(alimentacion.getCodigoProveedor());
        rpg.setPNFMAT_A(alimentacion.getCodigoMaterial());
        rpg.setPNFFEC_A(alimentacion.getFecha());

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento de
     * Material por Proveedor.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorAdd(
            RequestDataManttoMaterialProveedor alimentacion) {
        CMMXPRR0 rpg = new CMMXPRR0();
        ResponseManttoMaterialProveedorList response =
                new ResponseManttoMaterialProveedorList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaMaterialProveedor, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento de
     * Material por Proveedor.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorDelete(
            RequestDataManttoMaterialProveedor alimentacion) {
        CMMXPRR0 rpg = new CMMXPRR0();
        ResponseManttoMaterialProveedorList response =
                new ResponseManttoMaterialProveedorList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaMaterialProveedor, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * Material por Proveedor
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorUpdate(
            RequestDataManttoMaterialProveedor alimentacion) {
        CMMXPRR0 rpg = new CMMXPRR0();
        ResponseManttoMaterialProveedorList response =
                new ResponseManttoMaterialProveedorList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaMaterialProveedor, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro en la tabla de Compañias de
     * Ascensores
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCiaAscensoresList manttoCiaAscensoresUpdate(
            RequestDataCiaAscensores alimentacion) {
        CMASCER0 rpg = new CMASCER0();
        ResponseCiaAscensoresList response = new ResponseCiaAscensoresList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCiaAscensores, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla de Compañias de
     * Ascensores
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCiaAscensoresList manttoCiaAscensoresDelete(
            RequestDataCiaAscensores alimentacion) {
        CMASCER0 rpg = new CMASCER0();
        ResponseCiaAscensoresList response = new ResponseCiaAscensoresList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaCiaAscensores, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros en la tabla de Razon del
     * Arreglo
     *
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseRazonArregloList manttoRazonArregloQuery() {
        CMRARRR0 rpg = new CMRARRR0();
        ResponseRazonArregloList response = new ResponseRazonArregloList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Razon del Arreglo
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseRazonArregloList manttoRazonArregloAdd(
            RequestDataRazonArreglo alimentacion) {
        CMRARRR0 rpg = new CMRARRR0();
        ResponseRazonArregloList response = new ResponseRazonArregloList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaRazonArreglo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro en la tabla de Razon del
     * Arreglo
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseRazonArregloList manttoRazonArregloUpdate(
            RequestDataRazonArreglo alimentacion) {
        CMRARRR0 rpg = new CMRARRR0();
        ResponseRazonArregloList response = new ResponseRazonArregloList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaRazonArreglo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla de Razon del Arreglo
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseRazonArregloList manttoRazonArregloDelete(
            RequestDataRazonArreglo alimentacion) {
        CMRARRR0 rpg = new CMRARRR0();
        ResponseRazonArregloList response = new ResponseRazonArregloList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaRazonArreglo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla BlackList
     *
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCodigoBlackList manttoCodigoBlackListQuery() {
        CMBLCKR0 rpg = new CMBLCKR0();
        ResponseCodigoBlackList response = new ResponseCodigoBlackList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Black List
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCodigoBlackList manttoCodigoBlackListAdd(
            RequestDataCodigoBlackList alimentacion) {
        CMBLCKR0 rpg = new CMBLCKR0();
        ResponseCodigoBlackList response = new ResponseCodigoBlackList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaBlackList, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro de la tabla BlackList
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCodigoBlackList manttoCodigoBlackListUpdate(
            RequestDataCodigoBlackList alimentacion) {
        CMBLCKR0 rpg = new CMBLCKR0();
        ResponseCodigoBlackList response = new ResponseCodigoBlackList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaBlackList, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla BlackList
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseCodigoBlackList manttoCodigoBlackListDelete(
            RequestDataCodigoBlackList alimentacion) {
        CMBLCKR0 rpg = new CMBLCKR0();
        ResponseCodigoBlackList response = new ResponseCodigoBlackList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaBlackList, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Origen Datos
     *
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOrigenDatosList manttoOrigenDatosQuery() {
        CMODATR0 rpg = new CMODATR0();
        ResponseOrigenDatosList response = new ResponseOrigenDatosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Origen Datos
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOrigenDatosList manttoOrigenDatosAdd(
            RequestDataOrigenDatos alimentacion) {
        CMODATR0 rpg = new CMODATR0();
        ResponseOrigenDatosList response = new ResponseOrigenDatosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaOrigenDatos, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Origen Datos
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOrigenDatosList manttoOrigenDatosUpdate(
            RequestDataOrigenDatos alimentacion) {
        CMODATR0 rpg = new CMODATR0();
        ResponseOrigenDatosList response = new ResponseOrigenDatosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaOrigenDatos, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Origen Datos
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseOrigenDatosList manttoOrigenDatosDelete(
            RequestDataOrigenDatos alimentacion) {
        CMODATR0 rpg = new CMODATR0();
        ResponseOrigenDatosList response = new ResponseOrigenDatosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaOrigenDatos, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Tipo Acometida
     *
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoAcometidaList manttoTipoAcometidaQuery() {
        CMTACMR0 rpg = new CMTACMR0();
        ResponseTipoAcometidaList response = new ResponseTipoAcometidaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Tipo Acometida
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucuion del PCML
     */
    public ResponseTipoAcometidaList manttoTipoAcometidaAdd(
            RequestDataTipoAcometida alimentacion) {
        CMTACMR0 rpg = new CMTACMR0();
        ResponseTipoAcometidaList response = new ResponseTipoAcometidaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoAcometida, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Tipo Acometida
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoAcometidaList manttoTipoAcometidaUpdate(
            RequestDataTipoAcometida alimentacion) {
        CMTACMR0 rpg = new CMTACMR0();
        ResponseTipoAcometidaList response = new ResponseTipoAcometidaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoAcometida, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Tipo Acometida
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoAcometidaList manttoTipoAcometidaDelete(
            RequestDataTipoAcometida alimentacion) {
        CMTACMR0 rpg = new CMTACMR0();
        ResponseTipoAcometidaList response = new ResponseTipoAcometidaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoAcometida, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Tipo Materiales
     *
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoMaterialesList manttoTipoMaterialesQuery() {
        CMTMATR0 rpg = new CMTMATR0();
        ResponseTipoMaterialesList response = new ResponseTipoMaterialesList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Tipo Materiales
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoMaterialesList manttoTipoMaterialesAdd(
            RequestDataTipoMateriales alimentacion) {
        CMTMATR0 rpg = new CMTMATR0();
        ResponseTipoMaterialesList response = new ResponseTipoMaterialesList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoMateriales, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de actualizar un registro en la tabla Tipo Materiales
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoMaterialesList manttoTipoMaterialesUpdate(
            RequestDataTipoMateriales alimentacion) {
        CMTMATR0 rpg = new CMTMATR0();
        ResponseTipoMaterialesList response = new ResponseTipoMaterialesList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoMateriales, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla Tipo Materiales
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoMaterialesList manttoTipoMaterialesDelete(
            RequestDataTipoMateriales alimentacion) {
        CMTMATR0 rpg = new CMTMATR0();
        ResponseTipoMaterialesList response = new ResponseTipoMaterialesList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoMateriales, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Tipo Proyecto
     *
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoProyectoList manttoTipoProyectoQuery() {
        CMTPROR0 rpg = new CMTPROR0();
        ResponseTipoProyectoList response = new ResponseTipoProyectoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado para insertar un registro en la tabla Tipo Proyecto
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoProyectoList manttoTipoProyectoAdd(
            RequestDataTipoProyecto alimentacion) {
        CMTPROR0 rpg = new CMTPROR0();
        ResponseTipoProyectoList response = new ResponseTipoProyectoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoProyecto, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Tipo Proyecto
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoProyectoList manttoTipoProyectoUpdate(
            RequestDataTipoProyecto alimentacion) {
        CMTPROR0 rpg = new CMTPROR0();
        ResponseTipoProyectoList response = new ResponseTipoProyectoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoProyecto, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Tipo Proyecto
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseTipoProyectoList manttoTipoProyectoDelete(
            RequestDataTipoProyecto alimentacion) {
        CMTPROR0 rpg = new CMTPROR0();
        ResponseTipoProyectoList response = new ResponseTipoProyectoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoProyecto, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite consultar los registros de la tabla Informacion Nodo
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoNodosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoNodosList manttoNodosQuery(RequestDataManttoNodos alimentacion) {
        CMNODAR0 rpg = new CMNODAR0();
        ResponseManttoNodosList response = new ResponseManttoNodosList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPACODG(alimentacion.getCodNodo());
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Supervisor
     * Avanzada
     *
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaQuery() {
        CMSAVMR0 rpg = new CMSAVMR0();
        ResponseSupervisorAvanzadaList response = new ResponseSupervisorAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Supervisor
     * Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaAdd(
            RequestDataSupervisorAvanzada alimentacion) {
        CMSAVMR0 rpg = new CMSAVMR0();
        ResponseSupervisorAvanzadaList response = new ResponseSupervisorAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaSupervisorAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Supervisor Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaUpdate(
            RequestDataSupervisorAvanzada alimentacion) {
        CMSAVMR0 rpg = new CMSAVMR0();
        ResponseSupervisorAvanzadaList response = new ResponseSupervisorAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaSupervisorAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Supervisor Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaDelete(
            RequestDataSupervisorAvanzada alimentacion) {
        CMSAVMR0 rpg = new CMSAVMR0();
        ResponseSupervisorAvanzadaList response = new ResponseSupervisorAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaSupervisorAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla tipo Estrato
     *
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEstratoList manttoEstratoQuery() {
        CMTESTR0 rpg = new CMTESTR0();
        ResponseManttoEstratoList response = new ResponseManttoEstratoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Tipo Estrato
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEstratoList manttoEstratoAdd(
            RequestDataEstrato alimentacion) {
        CMTESTR0 rpg = new CMTESTR0();
        ResponseManttoEstratoList response = new ResponseManttoEstratoList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoEstrato, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Tipo Estrato
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEstratoList manttoEstratoUpdate(
            RequestDataEstrato alimentacion) {
        CMTESTR0 rpg = new CMTESTR0();
        ResponseManttoEstratoList response = new ResponseManttoEstratoList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoEstrato, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Tipo Estrato
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEstratoList manttoEstratoDelete(
            RequestDataEstrato alimentacion) {
        CMTESTR0 rpg = new CMTESTR0();
        ResponseManttoEstratoList response = new ResponseManttoEstratoList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoEstrato, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla tipo Trabajo
     *
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoQuery() {
        ResponseManttoTipoTrabajoList response = new ResponseManttoTipoTrabajoList();
        ArrayList tablaRta = new ArrayList();
        CMTTRAR0 rpg = new CMTTRAR0();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Tipo Trabajo
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoAdd(
            RequestDataTipoTrabajo alimentacion) {
        CMTTRAR0 rpg = new CMTTRAR0();
        ResponseManttoTipoTrabajoList response = new ResponseManttoTipoTrabajoList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoTrabajo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Tipo Trabajo
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoUpdate(
            RequestDataTipoTrabajo alimentacion) {
        CMTTRAR0 rpg = new CMTTRAR0();
        ResponseManttoTipoTrabajoList response = new ResponseManttoTipoTrabajoList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoTrabajo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Tipo Trabajo
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoDelete(
            RequestDataTipoTrabajo alimentacion) {
        CMTTRAR0 rpg = new CMTTRAR0();
        ResponseManttoTipoTrabajoList response = new ResponseManttoTipoTrabajoList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaTipoTrabajo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Ubicacion Caja
     *
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaQuery() {
        CMUBCNR0 rpg = new CMUBCNR0();
        ResponseManttoUbicacionCajaList response = new ResponseManttoUbicacionCajaList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Ubicacion Caja
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaAdd(
            RequestDataUbicacionCaja alimentacion) {
        CMUBCNR0 rpg = new CMUBCNR0();
        ResponseManttoUbicacionCajaList response = new ResponseManttoUbicacionCajaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(alimentacion.getUserId());
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaUbicacionCaja, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo para actualizar un registro de la tabla Ubicacion Caja
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaUpdate(
            RequestDataUbicacionCaja alimentacion) {
        CMUBCNR0 rpg = new CMUBCNR0();
        ResponseManttoUbicacionCajaList response = new ResponseManttoUbicacionCajaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaUbicacionCaja, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Ubicacion Caja
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoUbicacionCajaList Objero utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaDelete(
            RequestDataUbicacionCaja alimentacion) {
        CMUBCNR0 rpg = new CMUBCNR0();
        ResponseManttoUbicacionCajaList response = new ResponseManttoUbicacionCajaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPUSERID(alimentacion.getUserId());
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaUbicacionCaja, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Constructoras.
     *
     * @param alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConstructorasList constructorasQuery(
            RequestDataConstructoras alimentacion) {
        CMCONSR0 rpg = new CMCONSR0();
        ResponseConstructorasList response = new ResponseConstructorasList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaConstructoras, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado para insertar un registro en la tabla Constructoras
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConstructorasList constructorasAdd(
            RequestDataConstructoras alimentacion) {
        CMCONSR0 rpg = new CMCONSR0();
        ResponseConstructorasList response = new ResponseConstructorasList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaConstructoras, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Constructoras
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConstructorasList constructorasUpdate(
            RequestDataConstructoras alimentacion) {
        CMCONSR0 rpg = new CMCONSR0();
        ResponseConstructorasList response = new ResponseConstructorasList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaConstructoras, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Constructoras
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseConstructorasList constructorasDelete(
            RequestDataConstructoras alimentacion) {
        CMCONSR0 rpg = new CMCONSR0();
        ResponseConstructorasList response = new ResponseConstructorasList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaConstructoras, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consular la tabla Mantenimiento Información Nodo.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoInfoNodoList manttoInformacionNodoQuery(
            RequestDataManttoInfoNodo alimentacion) {
        CMNDINR0 rpg = new CMNDINR0();
        ResponseManttoInfoNodoList response = new ResponseManttoInfoNodoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPDCODQ(alimentacion.getCodigo());
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
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro de la tabla Mantenimiento
     * Información Nodo.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoInfoNodoList manttoInformacionNodoAdd(
            RequestDataManttoInfoNodo alimentacion) {
        CMNDINR0 rpg = new CMNDINR0();
        ResponseManttoInfoNodoList response = new ResponseManttoInfoNodoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPDCODQ(alimentacion.getCodigo());
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoInfoNodo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Mantenimiento
     * Información Nodo.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoInfoNodoList manttoInformacionNodoUpdate(
            RequestDataManttoInfoNodo alimentacion) {
        CMNDINR0 rpg = new CMNDINR0();
        ResponseManttoInfoNodoList response = new ResponseManttoInfoNodoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPDCODQ(alimentacion.getCodigo());
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoInfoNodo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Mantenimiento
     * Información Nodo.
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoInfoNodoList manttoInformacionNodoDelete(
            RequestDataManttoInfoNodo alimentacion) {
        CMNDINR0 rpg = new CMNDINR0();
        ResponseManttoInfoNodoList response = new ResponseManttoInfoNodoList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPDCODQ(alimentacion.getCodigo());
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPCML(Constantes.PCML);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoInfoNodo, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Barrios
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosQuery(
            RequestDataManttoInformacionBarrios alimentacion) {
        CMNBAR01 rpg = new CMNBAR01();
        ResponseManttoInformacionBarriosList response = new ResponseManttoInformacionBarriosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFOPCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);
        rpg.setCODBAR_A(alimentacion.getCodigoBarrioB());
        rpg.setDESBAR(alimentacion.getNombreBarrioB());
        rpg.setCODDIV(alimentacion.getCodigoDivisionB());
        rpg.setCODCOM(alimentacion.getCodigoComunidadB());
           
        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException | EJBException ex) {
            LOGGER.error("Error en CmTablasBasicasManager manttoInformacionBarriosQuery. EX000: " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager manttoInformacionBarriosQuery. EX000: " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
        }
        
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Barrios
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosAdd(
            RequestDataManttoInformacionBarrios alimentacion) {
        CMNBAR01 rpg = new CMNBAR01();
        ResponseManttoInformacionBarriosList response = new ResponseManttoInformacionBarriosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFOPCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaBarrios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }

        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Barrios
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosUpdate(
            RequestDataManttoInformacionBarrios alimentacion) {
        CMNBAR01 rpg = new CMNBAR01();
        ResponseManttoInformacionBarriosList response = new ResponseManttoInformacionBarriosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFOPCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaBarrios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Barrios
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosDelete(
            RequestDataManttoInformacionBarrios alimentacion) {
        CMNBAR01 rpg = new CMNBAR01();
        ResponseManttoInformacionBarriosList response = new ResponseManttoInformacionBarriosList();
        ArrayList tablaRta = new ArrayList();
        rpg.setPCML(Constantes.PCML);
        rpg.setPFOPCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaBarrios, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar la tabla de mantenimiento Asesor de
     * Avanzada
     *
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaQuery() {
        CMAAVMR0 rpg = new CMAAVMR0();
        ResponseManttoAsesorGestionDeAvanzadaList response =
                new ResponseManttoAsesorGestionDeAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * Asesor de Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaAdd(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        CMAAVMR0 rpg = new CMAAVMR0();
        ResponseManttoAsesorGestionDeAvanzadaList response =
                new ResponseManttoAsesorGestionDeAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAsesorAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Mantenimiento
     * Asesor Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaUpdate(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        CMAAVMR0 rpg = new CMAAVMR0();
        ResponseManttoAsesorGestionDeAvanzadaList response =
                new ResponseManttoAsesorGestionDeAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAsesorAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla Mantenimiento
     * Asesores Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaDelete(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        CMAAVMR0 rpg = new CMAAVMR0();
        ResponseManttoAsesorGestionDeAvanzadaList response =
                new ResponseManttoAsesorGestionDeAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAsesorAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite consultar los registros de la tabla Mantenimiento
     * Estados Avanzada
     *
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaQuery() {
        CMEAVMR0 rpg = new CMEAVMR0();
        ResponseManttoEstadosAvanzadaList response =
                new ResponseManttoEstadosAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Estados Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaAdd(
            RequestDataManttoEstadosAvanzada alimentacion) {
        CMEAVMR0 rpg = new CMEAVMR0();
        ResponseManttoEstadosAvanzadaList response =
                new ResponseManttoEstadosAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEstadosAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro en la tabla Mantenimiento
     * Estados Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaUpdate(
            RequestDataManttoEstadosAvanzada alimentacion) {
        CMEAVMR0 rpg = new CMEAVMR0();
        ResponseManttoEstadosAvanzadaList response =
                new ResponseManttoEstadosAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEstadosAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Mantenimiento
     * Estados Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaDelete(
            RequestDataManttoEstadosAvanzada alimentacion) {
        CMEAVMR0 rpg = new CMEAVMR0();
        ResponseManttoEstadosAvanzadaList response =
                new ResponseManttoEstadosAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaEstadosAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite consultar los registros de la tabla Asignar Asesor
     * Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaQuery(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        CMGEAVR0 rpg = new CMGEAVR0();
        ResponseAsignarAsesorAvanzadaList response =
                new ResponseAsignarAsesorAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPNFCOD_A(alimentacion.getCuentaConsulta());
        rpg.setPNFCOM_A(alimentacion.getComunidadConsulta());
        rpg.setPNFASE_A(alimentacion.getAsesorConsulta());
        rpg.setPNFNOM_A(alimentacion.getNombreConsulta());
        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite Asignar un Asesor en Gestion Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaAdd(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        CMGEAVR0 rpg = new CMGEAVR0();
        ResponseAsignarAsesorAvanzadaList response =
                new ResponseAsignarAsesorAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPNFCOD_A(alimentacion.getCuentaConsulta());
        rpg.setPNFCOM_A(alimentacion.getComunidadConsulta());
        rpg.setPNFASE_A(alimentacion.getAsesorConsulta());
        rpg.setPNFNOM_A(alimentacion.getNombreConsulta());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPFUNCI(Constantes.ASIGNAR);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAsignarAsesorAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar una Asignacion del Asesor en Gestion De
     * Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaUpdate(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        CMGEAVR0 rpg = new CMGEAVR0();
        ResponseAsignarAsesorAvanzadaList response =
                new ResponseAsignarAsesorAvanzadaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPNFCOD_A(alimentacion.getCuentaConsulta());
        rpg.setPNFCOM_A(alimentacion.getComunidadConsulta());
        rpg.setPNFASE_A(alimentacion.getAsesorConsulta());
        rpg.setPNFNOM_A(alimentacion.getNombreConsulta());
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setNUM_REG(Constantes.MODIFICAR);

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaAsignarAsesorAvanzada, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * Cambio Fecha
     *
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaQuery() {
        CMELOGR0 rpg = new CMELOGR0();
        ResponseManttoMotivosCambioFechaList response =
                new ResponseManttoMotivosCambioFechaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * Cambio Fecha
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaAdd(
            RequestDataMotivosCambioFecha alimentacion) {
        CMELOGR0 rpg = new CMELOGR0();
        ResponseManttoMotivosCambioFechaList response =
                new ResponseManttoMotivosCambioFechaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaMotivosCambioFecha, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registros de la tabla Mantenimiento
     * Motivos Cambio Fecha
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaUpdate(
            RequestDataMotivosCambioFecha alimentacion) {
        CMELOGR0 rpg = new CMELOGR0();
        ResponseManttoMotivosCambioFechaList response =
                new ResponseManttoMotivosCambioFechaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaMotivosCambioFecha, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Mantenimiento Motivo
     * Cambio Fecha
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaDelete(
            RequestDataMotivosCambioFecha alimentacion) {
        CMELOGR0 rpg = new CMELOGR0();
        ResponseManttoMotivosCambioFechaList response =
                new ResponseManttoMotivosCambioFechaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));

        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaMotivosCambioFecha, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo que permite consultar los registros de la tabla Mantenimiento Tipo
     * Competencia
     *
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaQuery() {
        CMTCOMR0 rpg = new CMTCOMR0();
        ResponseManttoTipoCompetenciaList response =
                new ResponseManttoTipoCompetenciaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPFUNCI(Constantes.CONSULTAR);
        rpg.setNUM_REG(Constantes.CONSULTA);

        try {
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de insertar un registro en la tabl Mantenimiento Tipo
     * Competencia
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaAdd(
            RequestDataManttoTipoCompetencia alimentacion) {
        CMTCOMR0 rpg = new CMTCOMR0();
        ResponseManttoTipoCompetenciaList response =
                new ResponseManttoTipoCompetenciaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPFUNCI(Constantes.CREAR);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoCompetencia, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Mantenimiento Tipo
     * Competencia
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaUpdate(
            RequestDataManttoTipoCompetencia alimentacion) {
        CMTCOMR0 rpg = new CMTCOMR0();
        ResponseManttoTipoCompetenciaList response =
                new ResponseManttoTipoCompetenciaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPFUNCI(Constantes.ACTUALIZAR);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoCompetencia, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla Mantenimiento Tipo
     * Acometida
     *
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaDelete(
            RequestDataManttoTipoCompetencia alimentacion) {
        CMTCOMR0 rpg = new CMTCOMR0();
        ResponseManttoTipoCompetenciaList response =
                new ResponseManttoTipoCompetenciaList();
        ArrayList tablaRta = new ArrayList();

        rpg.setPCML(Constantes.PCML);
        rpg.setPCONFI(Constantes.CONFIRMARACT);
        rpg.setPFUNCI(Constantes.ELIMINAR);
        rpg.setNUM_REG(Constantes.MODIFICAR);
        rpg.setPUSERID(ServiceUtils.recortarNombreUsuario(alimentacion.getNombreUsuario(), MAX_PUSERID_LENGHT));
        try {
            tablaRta.add(VectorizarTablaRta.vectorizar(
                    VectorizarTablaRta.tablaManttoTipoCompetencia, alimentacion));
            rpg.setTABLARTA(tablaRta);
            pcmlManager.invoke(rpg);
            response.setResultado(rpg.getRESULTADO());
            response.setMensaje(rpg.getMENSAJE());
            response.cargarListaRespuesta(tablaRta);
        } catch (PcmlException|ApplicationException ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }catch (Exception ex) {
            LOGGER.error("Error en CmTablasBasicasManager. EX000 " + ex.getMessage(), ex);
                    
            response.setResultado("E");
            response.setMensaje(ex.getMessage());
            return response;
        }
        return response;
    }
}
