/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionSolicitudMglManager;
import co.com.claro.mgl.businessmanager.ptlus.UsuariosServicesManager;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dao.impl.OtHhppMglDaoImpl;
import co.com.claro.mgl.dao.impl.TipoOtHhppMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaOtDireccionesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaHhppDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMglAuditoria;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.UtilsHhppAuditoria;
import co.com.claro.mgl.ws.cm.request.RequestCreaDireccionOtDirecciones;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.amx.service.automaticclosingonyx.v1.FaultMessage;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyRequestType;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import com.amx.service.exp.operation.updateclosingtask.v1.ClientOnix;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Juan David Hernandez
 */
public class OtHhppMglManager {

    private static final Logger LOGGER = LogManager.getLogger(OtHhppMglManager.class);
    private final CmtBasicaMglManager tablasBasicasManeger = new CmtBasicaMglManager();
    private final NodoMglManager nodoManager = new NodoMglManager();

    public List<OtHhppMgl> findAll() throws ApplicationException {
        List<OtHhppMgl> resultList;
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        resultList = otHhppMglDaoImpl.findAll();
        return resultList;
    }

    public OtHhppMgl update(OtHhppMgl otHhppMgl)
            throws ApplicationException {
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        return otHhppMglDaoImpl.update(otHhppMgl);
    }

    public boolean eliminarOtHhpp(OtHhppMgl otHhppMgl, String mUser, int mPerfil)
            throws ApplicationException {
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        return otHhppMglDaoImpl.deleteCm(otHhppMgl, mUser, mPerfil);
    }

    public OtHhppMgl findById(OtHhppMgl otHhppMgl)
            throws ApplicationException {
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        return otHhppMglDaoImpl.find(otHhppMgl.getOtHhppId());
    }

    /**
     * Lista los diferentes de ot por el ID.
     *
     * @author Juan David Hernandez
     * @param otId
     * @return
     * @throws ApplicationException
     */
    public List<OtHhppMgl> findOtHhppById(BigDecimal otId)
            throws ApplicationException {
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        return otHhppMglDaoImpl.findOtHhppById(otId);
    }

    /**
     * Obtiene el conteo de todos los registros de ot creadas en la DB.
     *
     * @author Juan David Hernandez
     * @param listadoRolesUsuario
     * @return
     * @throws ApplicationException
     */
    public int countAllOtHhpp(List<String> listadoRolesUsuario) throws ApplicationException {
        int result;
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        result = otHhppMglDaoImpl.countAllOtHhpp(listadoRolesUsuario);
        return result;
    }

    /**
     * Busca una Ot por direccion y subdireccion
     *
     * @author Orlando Velasquez Diaz
     * @param direccion
     * @param subDireccion
     * @param estado
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
    public OtHhppMgl findOtByDireccionAndSubDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccion, CmtBasicaMgl estado) throws ApplicationException {
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();

        return otHhppMglDaoImpl.findOtByDireccionAndSubDireccion(direccion, subDireccion, estado);
    }

    /**
     * Lista los diferentes ot por el ID.
     *
     * @author Juan David Hernandez
     * @param otId
     * @param filtroConsultaOtDireccionesDto
     * @param listadoRolesUsuario
     * @return
     * @throws ApplicationException
     */
    public List<OtHhppMgl> findAllOtHhppPaginada(int paginaSelected,
            int maxResults, List<String> listadoRolesUsuario,
            FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto) throws ApplicationException {
        List<OtHhppMgl> resultList;

        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        CmtBasicaMglManager CmtBasicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl estadoAbiertoBasica;
        estadoAbiertoBasica =  CmtBasicaMglManager.findByCodigoInternoApp(Constant.ESTADO_OT_ABIERTO);

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
       
        
        resultList = otHhppMglDaoImpl.findAllOtHhppPaginada(firstResult,
                maxResults, listadoRolesUsuario, filtroConsultaOtDireccionesDto, estadoAbiertoBasica);
        return resultList;
    }

    /**
     * Crea un ot en la base de datos y su respectiva relación con las
     * tecnologias seleccionadas
     *
     * @author Juan David Hernandez
     * @param otHhppMgl ot a ser creada
     * @param usuario
     * @param tecnologiaList lista de tecnologias seleccionadas
     * @param perfil
     * @return
     * @throws ApplicationException
     */
    public OtHhppMgl createOtHhpp(OtHhppMgl otHhppMgl, String usuario,
            int perfil, List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {

        if (validarDatosOtToCreate(otHhppMgl, tecnologiaList)) {

            OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
            OtHhppTecnologiaMglManager otHhppTecnologiaMglManager
                    = new OtHhppTecnologiaMglManager();

            //creamos la OT en la base de datos
            OtHhppMgl otHhppMglReturn = otHhppMglDaoImpl.createCm(otHhppMgl,
                    usuario, perfil);
            /*realizamos la creación de la relación del listado de tecnologias
             seleccionadas por el usuario con la OT creada. */
            if (otHhppMglReturn != null
                    && otHhppMglReturn.getOtHhppId() != null) {
                for (CmtBasicaMgl tecnologiaOt : tecnologiaList) {
                    OtHhppTecnologiaMgl otHhppTecnologiaMgl
                            = new OtHhppTecnologiaMgl();
                    otHhppTecnologiaMgl.setTecnoglogiaBasicaId(tecnologiaOt);
                    otHhppTecnologiaMgl.setOtHhppId(otHhppMglReturn);
                    otHhppTecnologiaMglManager
                            .createOtHhppTecnologiaMgl(otHhppTecnologiaMgl,
                                    usuario, perfil);
                }

            } else {
                throw new ApplicationException(
                        "No Fue posible crear la OT De dirección");
            }
            return otHhppMglReturn;
        } else {
            throw new ApplicationException("Error al Validar la creacion de la OT");
        }
    }

    /**
     * Crea un ot en la base de datos
     *
     * @author Juan David Hernandez
     * @param otHhppMgl ot a ser creada
     * @param usuario
     * @param perfil
     * @return
     * @throws ApplicationException
     */
    public OtHhppMgl createOtDirecciones(OtHhppMgl otHhppMgl, String usuario,
            int perfil) throws ApplicationException {

        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        return otHhppMglDaoImpl.createCm(otHhppMgl,
                usuario, perfil);

    }

    /**
     * edita un ot en la base de datos y su respectiva relación con las
     * tecnologias seleccionadas
     *
     * @author Juan David Hernandez
     * @param otHhppMgl ot a ser editada
     * @param usuario usuario que realiza la accion
     * @param perfil perfil que realiza la accion
     * @return
     * @throws ApplicationException
     */
    public OtHhppMgl editarOtHhpp(OtHhppMgl otHhppMgl, String usuario, int perfil) throws ApplicationException {
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        return otHhppMglDaoImpl.updateCm(otHhppMgl, usuario, perfil);
    }

    /**
     * Se valida la cobertura del nodo para las tecnologias seleccionadas
     *
     * @author Orlando Velasquez Diaz
     * @param otHhppMgl
     * @param tecnologiaList
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
    public boolean validarCoberturaDeNodo(OtHhppMgl otHhppMgl,
            List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {

        for (CmtBasicaMgl tecnologiaBasica : tecnologiaList) {
            if (buscarNodoDeCobertura(otHhppMgl, tecnologiaBasica) == null) {
                return false;
            }
        }
        return true;
    }

    public NodoMgl buscarNodoDeCobertura(OtHhppMgl otHhppMgl, CmtBasicaMgl tecnologiaBasica)
            throws ApplicationException {
        NodoMgl nodo = null;

        CmtBasicaMgl basica = tablasBasicasManeger.findById(tecnologiaBasica.getBasicaId());
        if (basica.getIdentificadorInternoApp() != null) {
            if (basica.getIdentificadorInternoApp().equalsIgnoreCase(
                    Constant.HFC_BID)) {
                if (otHhppMgl.getDireccionId().getDirNodouno() != null) {
                    nodo = nodoManager.findByCodigo(otHhppMgl.getDireccionId()
                            .getDirNodouno());
                }
            } else if (basica.getIdentificadorInternoApp().equalsIgnoreCase(
                    Constant.HFC_UNI)) {
                if (otHhppMgl.getDireccionId().getDirNododos() != null) {
                    nodo = nodoManager.findByCodigo(otHhppMgl.getDireccionId()
                            .getDirNododos());
                }
            } else if (basica.getIdentificadorInternoApp().equalsIgnoreCase(
                    Constant.DTH)) {
                if (otHhppMgl.getDireccionId().getDirNodoDth() != null) {
                    nodo = nodoManager.findByCodigo(otHhppMgl.getDireccionId()
                            .getDirNodoDth());
                }
            } else if (basica.getIdentificadorInternoApp().equalsIgnoreCase(
                    Constant.FIBRA_FTTTH)) {
                if (otHhppMgl.getDireccionId().getDirNodoFtth() != null) {
                    nodo = nodoManager.findByCodigo(otHhppMgl.getDireccionId()
                            .getDirNodoFtth());
                }
            } else if (basica.getIdentificadorInternoApp().equalsIgnoreCase(
                    Constant.BTS_MOVIL)) {
                if (otHhppMgl.getDireccionId().getDirNodoMovil() != null) {
                    nodo = nodoManager.findByCodigo(otHhppMgl.getDireccionId()
                            .getDirNodoMovil());
                }
            } else if (basica.getIdentificadorInternoApp().equalsIgnoreCase(
                    Constant.LTE_INTERNET)) {
                if (otHhppMgl.getDireccionId().getDirNodoWifi() != null) {
                    nodo = nodoManager.findByCodigo(otHhppMgl.getDireccionId()
                            .getDirNodoWifi());
                }
            }
        }

        return nodo;
    }

    /**
     * Lista los diferentes ot por el ID de la direccion detallada
     *
     * @author Juan David Hernandez
     * @param paginaSelected
     * @param maxResults
     * @param otId
     * @param subDireccionId
     * @param direccionId
     * @return
     * @throws ApplicationException
     */
    public List<OtHhppMgl> findAllOtHhppByDireccionDetalladaId(int paginaSelected,
            int maxResults, BigDecimal direccionId, BigDecimal subDireccionId,OtHhppMgl filtro) throws ApplicationException {
        List<OtHhppMgl> resultList;

        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        resultList = otHhppMglDaoImpl.findAllOtHhppByDireccionDetalladaId(firstResult,
                maxResults, direccionId, subDireccionId,filtro);
        return resultList;
    }

    /**
     * Obtiene el conteo de todos los registros de ot creadas en la DB por id de
     * direccion detallada
     *
     * @author Juan David Hernandez
     * @param direccionId
     * @param subDireccionId
     * @return
     * @throws ApplicationException
     */
    public int countAllOtHhppByDireccionDetalladaId(BigDecimal direccionId, BigDecimal subDireccionId, OtHhppMgl filtro) throws ApplicationException {
        int result;
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        result = otHhppMglDaoImpl.countAllOtHhppByDireccionDetalladaId(direccionId, subDireccionId,filtro);
        return result;
    }

    /**
     * Función que realiza validación de color de alerta correspondiente según
     * el valor de ANS del tipo de ot relacionada a la ot
     *
     * @param otHhpp
     *
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String obtenerColorAlerta(OtHhppMgl otHhpp) {
        String colorResult = "blue";
        if (otHhpp.getFechaCreacionOt() != null) {
            long diffDate = (new Date().getTime()) - (otHhpp.getFechaCreacionOt().getTime());
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));

            if ((int) diffMinutes >= otHhpp.getTipoOtHhppId().getAns()) {
                colorResult = "red";
            } else if ((int) diffMinutes < otHhpp.getTipoOtHhppId().getAns()
                    && (int) diffMinutes >= otHhpp.getTipoOtHhppId().getAnsAviso()) {
                colorResult = "yellow";
            } else if ((int) diffMinutes < otHhpp.getTipoOtHhppId().getAnsAviso()) {
                colorResult = "green";
            }
        }
        return colorResult;

    }

    /**
     * valbuenayf metodo para buscar una orden de trabajo con el id de la
     * solicitud
     *
     * @param idSolicitud
     * @return
     */
    public OtHhppMgl findOrdenTrabajoByIdSolicitud(BigDecimal idSolicitud) {
        OtHhppMgl otHhppMgl;
        try {
            OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
            otHhppMgl = otHhppMglDaoImpl.findOrdenTrabajoByIdSolicitud(idSolicitud);
        } catch (Exception e) {
            LOGGER.error("Error en findOrdenTrabajoByIdSolicitud de CmtOrdenTrabajoMglManager: " + e);
            return null;
        }
        return otHhppMgl;
    }

    /**
     * Valida la data de la OT. Función para validar si los datos de la OT a
     * crear estan completos
     *
     * @author ortizjaf
     * @param ot Orden de trabajo a validar
     * @param tecnologiaList lista de Tecnologias
     * @return true si los datos permiten la creacion de la OT
     * @throws ApplicationException
     */
    public boolean validarDatosOtToCreate(OtHhppMgl ot,
            List<CmtBasicaMgl> tecnologiaList)
            throws ApplicationException {
        try {
            if (ot.getTipoOtHhppId() == null
                    || ot.getTipoOtHhppId().getTipoOtId() == null) {
                String msnError = "Seleccione un TIPO DE OT para la creación de la OT ";
                throw new ApplicationException(msnError);
            }
            if (ot.getNombreContacto() == null
                    || ot.getNombreContacto().trim().isEmpty()) {
                String msnError = "Seleccione un NOMBRE DE CONTACTO para la creación de la OT ";
                throw new ApplicationException(msnError);
            }
            if (ot.getTelefonoContacto() == null
                    || ot.getTelefonoContacto().trim().isEmpty()) {
                String msnError = "Seleccione un TELÉFONO DE CONTACTO para la creación de la OT ";
                throw new ApplicationException(msnError);
            }
            if (ot.getCorreoContacto() == null
                    || ot.getCorreoContacto().trim().isEmpty()
                    || !ot.getCorreoContacto().contains("@")
                    || !ot.getCorreoContacto().contains(".")) {
                String msnError = "Debe ingresar un CORREO ELECTRÓNICO "
                        + "del contacto para la creación de la OT ";
                throw new ApplicationException(msnError);
            }
            if (ot.getEstadoGeneral() == null
                    || ot.getEstadoGeneral().getBasicaId() == null) {
                String msnError = "Debe asignar un "
                        + "estado general para la creación de la OT ";
                throw new ApplicationException(msnError);
            }

            if (ot.getDireccionId() == null
                    || ot.getDireccionId().getDirId() == null) {
                String msnError = "Debe seleccionar Dirección para la creación de la OT ";
                throw new ApplicationException(msnError);
            }
            return true;
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar dato de creacion de ot hhpp" + " ", e);
            String msnError = "Error al validar dato de creacion de ot hhpp "
                    + e.getMessage();
            throw new ApplicationException(msnError);
        }
    }

    /**
     * Crea una ot a partir de una solicitud.Permite crear una OT en la base de
 datos y su respectiva relación con las tecnologias a partir de una
 solicitud
     *
     * @author ortizjaf
     * @param tecnologiaList lista de tecnologias seleccionadas
     * @param usuario
     * @param perfil
     * @return
     * @throws ApplicationException
     */
    public OtHhppMgl createOtFromSolicitud(Solicitud solicitudGestion, String usuario,
            int perfil, List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        OtHhppMgl otToCreate = new OtHhppMgl();

        otToCreate.setNombreContacto(solicitudGestion.getContacto());
        otToCreate.setTelefonoContacto(solicitudGestion.getTelContacto());
        otToCreate.setCorreoContacto(solicitudGestion.getCorreo());
        otToCreate.setSolicitudId(solicitudGestion);
        otToCreate.setFechaCreacionOt(new Date());
        CmtBasicaMgl estadoGeneral = cmtBasicaMglManager.findByCodigoInternoApp(
                Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO);
        otToCreate.setEstadoGeneral(estadoGeneral);
        otToCreate.setDireccionId(
                solicitudGestion.getDireccionDetallada().getDireccion());
        if(solicitudGestion.getDireccionDetallada().getSubDireccion() != null){
        otToCreate.setSubDireccionId(
                solicitudGestion.getDireccionDetallada().getSubDireccion());
        }else{
             otToCreate.setSubDireccionId(null);
        }
        //Solo Creamos la OT de Verificacion
        if (solicitudGestion.getRptGestion() != null
                && !solicitudGestion.getRptGestion().trim().isEmpty()
                && solicitudGestion.getRptGestion().equalsIgnoreCase(
                        Constant.RZ_VERIFICACION_AGENDADA)) {
            TipoOtHhppMglDaoImpl tipoOtDao = new TipoOtHhppMglDaoImpl();
            TipoOtHhppMgl tipoOt = tipoOtDao.findTipoOtByIdentificadorInterno(
                    Constant.ABR_TIPO_OT_VERIFICACION);
            if (tipoOt != null) {
                otToCreate.setTipoOtHhppId(tipoOt);
            } else {
                throw new ApplicationException(
                        "No Fue posible Encontrar un tipo de OT a partir de la "
                        + "solicitud para asignar a la OT");
            }
        }
        if (validarDatosOtToCreate(otToCreate, tecnologiaList)) {

            OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
            OtHhppTecnologiaMglManager otHhppTecnologiaMglManager
                    = new OtHhppTecnologiaMglManager();

            List<OtHhppTecnologiaMgl> tecList = new ArrayList<>();
            for (CmtBasicaMgl tecnologiaOt : tecnologiaList) {
                if (tecnologiaOt.getNodoGestion() != null
                        && !tecnologiaOt.getNodoGestion().trim().isEmpty()) {

                    String nodoStr = (tecnologiaOt.getNodoGestion() != null
                            && !tecnologiaOt.getNodoGestion().trim().isEmpty()) ? tecnologiaOt.getNodoGestion()
                            : "";
                    NodoMglManager nodoMglManager = new NodoMglManager();
                    NodoMgl nodoOt = nodoMglManager.findByCodigo(nodoStr);
                    if (nodoOt != null) {
                        OtHhppTecnologiaMgl otHhppTecnologiaMgl
                                = new OtHhppTecnologiaMgl();
                        otHhppTecnologiaMgl.setTecnoglogiaBasicaId(tecnologiaOt);
                        otHhppTecnologiaMgl.setAmpliacionTab("N");
                        otHhppTecnologiaMgl.setNodo(nodoOt);
                        otHhppTecnologiaMgl.setSincronizaRr(
                                obtenerValorCheckbox(tecnologiaOt.isSincronizaRr()).toString());
                        tecList.add(otHhppTecnologiaMgl);
                    }
                }
            }
            //creamos la OT en la base de datos
            OtHhppMgl otCreated = otHhppMglDaoImpl.createCm(otToCreate,
                    usuario, perfil);
            if (!tecList.isEmpty()) {
                for (OtHhppTecnologiaMgl tecOt : tecList) {
                    tecOt.setOtHhppId(otCreated);
                    otHhppTecnologiaMglManager.createOtHhppTecnologiaMgl(
                            tecOt, usuario, perfil);
                }
                otToCreate.setTecnologiaBasicaList(tecList);
            }
            solicitudGestion.setEstado("FINALIZADO");
            SolicitudManager solicitudManager = new SolicitudManager();
            solicitudManager.update(solicitudGestion);
            return otCreated;
        } else {
            throw new ApplicationException("Error al Validar la creacion de la OT");
        }
    }

    public OtHhppMgl createIndependentOt(OtHhppMgl otToCreate, String usuario,
            int perfil, List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {

        if (validarDatosOtToCreate(otToCreate, tecnologiaList)) {

            OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
            OtHhppTecnologiaMglManager otHhppTecnologiaMglManager
                    = new OtHhppTecnologiaMglManager();

            List<OtHhppTecnologiaMgl> tecList = new ArrayList<>();
            for (CmtBasicaMgl tecnologiaOt : tecnologiaList) {
                if (tecnologiaOt.getNodoGestion() != null
                        && !tecnologiaOt.getNodoGestion().trim().isEmpty()) {

                    String nodoStr = (tecnologiaOt.getNodoGestion() != null
                            && !tecnologiaOt.getNodoGestion().trim().isEmpty()) ? tecnologiaOt.getNodoGestion()
                            : "";
                    NodoMglManager nodoMglManager = new NodoMglManager();
                    NodoMgl nodoOt = nodoMglManager.findByCodigo(nodoStr);
                    if (nodoOt != null) {
                        OtHhppTecnologiaMgl otHhppTecnologiaMgl
                                = new OtHhppTecnologiaMgl();
                        otHhppTecnologiaMgl.setTecnoglogiaBasicaId(tecnologiaOt);
                        otHhppTecnologiaMgl.setAmpliacionTab("N");
                        otHhppTecnologiaMgl.setNodo(nodoOt);
                        otHhppTecnologiaMgl.setSincronizaRr(
                                obtenerValorCheckbox(tecnologiaOt.isSincronizaRr()).toString());
                        tecList.add(otHhppTecnologiaMgl);
                    }
                }
            }
            //creamos la OT en la base de datos
            OtHhppMgl otCreated = otHhppMglDaoImpl.createCm(otToCreate,
                    usuario, perfil);
            if (!tecList.isEmpty()) {
                for (OtHhppTecnologiaMgl tecOt : tecList) {
                    tecOt.setOtHhppId(otCreated);
                    otHhppTecnologiaMglManager.createOtHhppTecnologiaMgl(
                            tecOt, usuario, perfil);
                }
                otToCreate.setTecnologiaBasicaList(tecList);
            }

            return otCreated;
        } else {
            throw new ApplicationException("Error al Validar la creacion de la OT");
        }
    }

    /**
     * Función utilizada para obtener el valor del si la tipo de ot es agendable
     *
     * @author Juan David Hernandez
     * @param agendableSeleccionado
     * @return 
     */
    public BigDecimal obtenerValorCheckbox(boolean agendableSeleccionado) {
        if (agendableSeleccionado) {
            return new BigDecimal(1);
        } else {
            return new BigDecimal(0);
        }
    }

    public boolean createDireccionParaOtDirecciones(RequestCreaDireccionOtDirecciones request,
            BigDecimal idCentroPoblado, String usuarioVt, int perfilVt) throws ApplicationException {

        try {
            DireccionesValidacionManager direccionesValidacionManager = new DireccionesValidacionManager();
            //Validación de la dirección con el minimo de partes que la construyan
            direccionesValidacionManager
                    .validarEstructuraDireccion(request.getDrDireccion(), Constant.TIPO_VALIDACION_DIR_HHPP);

            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPoblado = geograficoPoliticoManager.findById(idCentroPoblado);
            
            UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
            UsuariosServicesDTO usuario 
                        = usuariosManager.consultaInfoUserPorUsuario(usuarioVt);
            
            if (usuario == null) {
                throw new ApplicationException("El usuario no fue encontrado");
            }

            //Asigmanos el tipo de direccion al drdireccion P:Principal
            request.getDrDireccion().setDirPrincAlt("P");
            //Asigmanos el estado GEO de la direccion al drdireccion
            if (request.getCityEntity().getEstadoDir() != null
                    && !request.getCityEntity().getEstadoDir().trim().isEmpty()) {
                request.getDrDireccion().setEstadoDirGeo(request.getCityEntity().getEstadoDir());
            }
            //Asigmanos el estrato GEO de la direccion al drdireccion
            if (request.getCityEntity().getEstratoDir() != null
                    && !request.getCityEntity().getEstratoDir().trim()
                            .isEmpty()) {
                request.getDrDireccion().setEstrato(request.getCityEntity().getEstratoDir());
            }

            NegocioParamMultivalor param = new NegocioParamMultivalor();
            CityEntity cityEntityCreaDir = param.consultaDptoCiudadGeo(centroPoblado.getGeoCodigoDane());
            if (cityEntityCreaDir == null
                    || cityEntityCreaDir.getCityName() == null
                    || cityEntityCreaDir.getDpto() == null
                    || cityEntityCreaDir.getCityName().isEmpty()
                    || cityEntityCreaDir.getDpto().isEmpty()) {
                throw new ApplicationException("La Ciudad no esta"
                        + " configurada en Direcciones");
            }

            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String address = drDireccionManager.getDireccion(request.getDrDireccion());
            request.getDrDireccion().setBarrio(request.getCityEntity().getBarrio());
            String barrioStr = drDireccionManager.obtenerBarrio(request.getDrDireccion());
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCodDaneVt(cityEntityCreaDir.getCodDane());
            addressRequest.setAddress(address);
            addressRequest.setCity(cityEntityCreaDir.getCityName());
            addressRequest.setState(cityEntityCreaDir.getDpto());
            addressRequest.setNeighborhood(barrioStr);

            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            ResponseMessage responseMessageCreateDir
                    = addressEJBRemote.createAddress(addressRequest,
                            usuario.getUsuario(), "MGL", "", request.getDrDireccion());
            if (responseMessageCreateDir.getIdaddress() != null
                    && !responseMessageCreateDir.getIdaddress().trim()
                            .isEmpty()) {
                request.getDrDireccion().setIdDirCatastro(
                        responseMessageCreateDir.getIdaddress());
            } else {
                throw new ApplicationException("La dirección no cumple con la estructura "
                        + "requerida y no fue posible guardarla en el ");
            }

            return true;
        } catch (ExceptionDB ex) {
            String msg = "Error al crear la dirección. EX000: " + ex.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, ex);
        }

    }

    public int countByFiltro(
            FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto) throws ApplicationException {

        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        CmtBasicaMglManager CmtBasicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl estadoAbiertoBasica;
        estadoAbiertoBasica =  CmtBasicaMglManager.findByCodigoInternoApp(Constant.ESTADO_OT_ABIERTO);
        
        return otHhppMglDaoImpl.countByFiltro(filtroConsultaOtDireccionesDto, estadoAbiertoBasica);
    }

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    
    /**
     * Bocanegra vm metodo para buscar una orden de trabajo con el id de la
     * orden
     *
     * @param idOt
     * @return OtHhppMgl
     */
    public OtHhppMgl findOtByIdOt(BigDecimal idOt)
            throws ApplicationException {
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        return otHhppMglDaoImpl.findOtByIdOt(idOt);
    }
    
    /**
     * Bloquea o Desbloquea una orden. Permite realizar el bloqueo o desbloqueo
     * de una orden en el repositorio para la gestion.
     *
     * @author Victor Bocanegra
     * @param orden orden a bloquear o desbloquear
     * @param bloqueo si es bloqueo o no
     * @param usuario usuario que realiza el bloqueo-desbloque
     * @param perfil perfil
     * @return OtHhppMgl
     * @throws ApplicationException
     */
    public OtHhppMgl bloquearDesbloquearOrden(OtHhppMgl orden,
            boolean bloqueo, String usuario, int perfil) throws ApplicationException {

        String disponiblidadGestion = (bloqueo ? usuario : null);
        OtHhppMglDaoImpl dao = new OtHhppMglDaoImpl();
        orden = dao.find(orden.getOtHhppId());
        orden.setDisponibilidadGestion(disponiblidadGestion);
        return dao.updateCm(orden, usuario, perfil);
    }
    
    /**
     * Metodo para notificar una orden de trabajo en ONYX
     *
     * @author Victor Bocanegra
     * @param orden orden a notificar
     * @param numOtHija numero de la OT hija
     * @return MERNotifyResponseType
     * @throws ApplicationException
     */
    public MERNotifyResponseType notificarOrdenOnix(OtHhppMgl orden, String numOtHija)
            throws ApplicationException {

        MERNotifyResponseType responseType = null;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        co.com.claro.visitastecnicas.ws.proxy.HeaderRequest headerRequest = new co.com.claro.visitastecnicas.ws.proxy.HeaderRequest();
        
        GregorianCalendar c = new GregorianCalendar();
        Date fecha = new Date();
        c.setTime(fecha);
        
        try {

            URL url = null;
            String wsURL1;

            ClientOnix clientOnix = new ClientOnix();
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            headerRequest.setRequestDate(date2);
            headerRequest.setSystem("MER");

            ParametrosMgl param = parametrosMglManager.
                    findByAcronimoName(Constant.PROPERTY_URL_WS_NOTIFICA_UPDATE_CLOSING_ONIX);
            if (param != null) {
                wsURL1 = param.getParValor();
                url = new URL(wsURL1);
            }
            
            //Validamos disponibilidad del servicio
            ConsumoGenerico.conectionWsdlTest(url, Constant.PROPERTY_URL_WS_NOTIFICA_UPDATE_CLOSING_ONIX);
            //Fin Validacion disponibilidad del servicio


            MERNotifyRequestType request = new MERNotifyRequestType();
            request.setIncidentMer(orden.getOtHhppId().toString());
            int otHija = Integer.parseInt(numOtHija);
            request.setIncidentonyx(otHija);

            responseType = clientOnix.merNotify(headerRequest, request, url);

        } catch (ApplicationException | FaultMessage
                | IOException | DatatypeConfigurationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return responseType;

    }
    
    /**
     * Bocanegra vm metodo para buscar una orden de trabajo de HHPP por
     * direccion y subdireccion
     *
     * @param direccion
     * @param subDireccion
     * @return OtHhppMgl
     */
    public OtHhppMgl findOtHhppByDireccionAndSubDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccion)
            throws ApplicationException {

        OtHhppMglDaoImpl dao = new OtHhppMglDaoImpl();
        return dao.findOtHhppByDireccionAndSubDireccion(direccion, subDireccion);
    }
    
    /**
     * Busqueda de todas las Ordenes de trabajo por id Enlace
     *
     * @author Victor Bocanegra
     * @param idEnlace valor id del enlace para la busqueda
     * @param tecnologias lista de tecnologia para la busqueda
     * @return Retorna una lista de Ordenes de trabajo
     * @throws ApplicationException
     */
    public List<OtHhppMgl> findOtHhppByIdEnlaceAndTecnologias(String idEnlace, List<BigDecimal> tecnologias)
            throws ApplicationException {

        OtHhppMglDaoImpl dao = new OtHhppMglDaoImpl();
        return dao.findOtHhppByIdEnlaceAndTecnologias(idEnlace, tecnologias);
    }

    /**
     * Busqueda de Ordenes de trabajo por direccion y subdireccion y tecnologia
     *
     * @author Victor Bocanegra
     * @param direccion
     * @param subDireccion
     * @param tecnologia tecnologia para la busqueda
     * @return List<OtHhppMgl> 
     * @throws ApplicationException
     */
    public List<OtHhppMgl> findOtHhppByDireccionAndTecnologias(DireccionMgl direccion,
            SubDireccionMgl subDireccion, CmtBasicaMgl tecnologia)
            throws ApplicationException {
        OtHhppMglDaoImpl dao = new OtHhppMglDaoImpl();
        return dao.findOtHhppByDireccionAndTecnologias(direccion, subDireccion, tecnologia);
    }
    
     /**
     * Metodo que contruye la tabla de auditorias de una orden de trabajo
     * @param otHhppMgl {@link OtHhppMgl}
     * @author Luz Villalobos
     * @return List<AuditoriaHhppDto>
     */
    public List<AuditoriaHhppDto> construirAuditoria(OtHhppMgl otHhppMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsHhppAuditoria<OtHhppMgl, OtHhppMglAuditoria> utilsHhppAuditoria
                = new UtilsHhppAuditoria<>();
        return utilsHhppAuditoria.construirAuditoria(otHhppMgl);
    }
}
