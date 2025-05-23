/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

/**
 *
 * @author bocanegravm
 */
import co.com.claro.app.dtos.AgendasMglDto;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtHorarioRestriccionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.app.dtos.AppCancelarAgendaOtRequest;
import co.com.claro.app.dtos.AppConsultarAgendasOtRequest;
import co.com.claro.app.dtos.AppReagendarOtRequest;
import co.com.claro.app.dtos.AppConsultarCapacityOtRequest;
import co.com.claro.app.dtos.AppConsultarFactibilidadTrasladoRequest;
import co.com.claro.app.dtos.AppCrearAgendaOtRequest;
import co.com.claro.app.dtos.AppCrearOtRequest;
import co.com.claro.app.dtos.AppResponseCrearOtDto;
import co.com.claro.app.dtos.AppModificarOtRequest;
import co.com.claro.app.dtos.AppResponseUpdateOtDto;
import co.com.claro.app.dtos.AppResponsesAgendaDto;
import co.com.claro.app.dtos.AppResponsesGetCapacityDto;
import co.com.claro.cmas400.ejb.respons.ResponseDataOtEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mgl.businessmanager.cm.CmtAgendamientoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtCuentaMatrizMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDetalleFlujoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtEstadoxFlujoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtNotaOtMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtOpcionesRolMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSubEdificiosVtManager;
import co.com.claro.mgl.businessmanager.cm.CmtTecnologiaSubMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoOtMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtVisitaTecnicaMglManager;
import co.com.claro.mgl.businessmanager.cm.OnyxOtCmDirManager;
import co.com.claro.mgl.businessmanager.cm.UsRolPerfilManager;
import co.com.claro.mgl.businessmanager.cm.UsuariosManager;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import co.com.claro.mgl.dao.impl.OtHhppMglDaoImpl;

import co.com.claro.mgl.dao.impl.cm.CmtOrdenTrabajoMglDaoImpl;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import co.com.claro.mgl.jpa.entities.cm.UsRolPerfil;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;

import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import com.jlcg.db.exept.ExceptionDB;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bocanegravm
 */
public class OtCMHhppMglManager {

    private static final Logger LOGGER = LogManager.getLogger(OtCMHhppMglManager.class);
    private CmtDireccionDetalladaMgl detalladaMglApp;
    private CmtBasicaMgl estIniOtMglApp;
    private CmtBasicaMgl razIniOtMglApp;
    private TipoOtHhppMgl tipoOtHhppMglApp;
    private CmtTipoOtMgl tipoOtMglApp;
    private CmtBasicaMgl tipoTrabajoMglApp;
    private CmtBasicaMgl estadoInternoMglApp;
    private CmtBasicaMgl segmentoMglApp;
    private CmtBasicaMgl classOtMglApp;
    private String observacionesMglApp;
    private Date fechaSugeridadMglApp;
    private CmtBasicaMgl tecnoMglApp;
    private NodoMgl nodoMglApp;
    private List<CmtNotasOtHhppMgl> notasHhpp;
    private List<CmtNotaOtMgl> notasOtCcmm;
    private List<CmtBasicaMgl> listTecnologias;
    private CmtOnyxResponseDto cmtOnyxResponseDto;
    private OnyxOtCmDir onyxOtCmDir;
    private CmtCuentaMatrizMgl cuentaMatrizMglApp;
    private CmtOrdenTrabajoMgl ordenCcmmMglApp;
    private CmtAgendamientoMgl agendaOtCcmm;
    private MglAgendaDireccion agendaOtHhpp;
    private OtHhppMgl ordenHhppMglApp;
    private CmtBasicaMgl estadoAnteriorRazonado;
    private List<CmtDetalleFlujoMgl> detalleFlujoEstActual;
    private CmtBasicaMgl estActualOtHhppMglApp;
    private OtHhppMgl otDirModMglApp;
    private String codCuentaPar;
    private List<CmtAgendamientoMgl> agendasOtCcmm;
    private List<MglAgendaDireccion> agendasOtHhpp;
    private String mensajesValidacion;
    private String subtipoWorfoce = null;
    private String subtipoOfsc = null;
    private CmtDireccionDetalladaMgl detalladaMglTraslado;
    private GeograficoPoliticoMgl centroPobladoTraslado;
    private BigDecimal cedulaUser;
    private List<HhppMgl> lstHhppMglApp;
    /**
     * N&uacute;mero m&aacute;ximo de caracteres permitido para la
     * construcci&oacute;n del n&uacute;mero de OT de RR.
     */
    private final int MAX_CARACTERES_NUMERO_OT_RR = 5;
    /**
     * N&uacute;mero m&aacute;ximo de caracteres permitido para la
     * construcci&oacute;n del estado de la OT.
     */
    private final int MAX_CARACTERES_ESTADO_OT = 6;
    private final String FORMULARIO_GENERAL = "GENERALOT";
    private final String FORMULARIO_MENU = "MENU";
    private final String FORMULARIO_AGENDAS = "AGENDAMIENTOSOT";
    private static final String OPCION_REAGENDAR = "REAGENDAR";

    private final String OPCION_CREAR_OT_CM = "menuCmCreaOtVt";
    private final String OPCION_GESTION_OT_HHPP = "menuHhppGestionOt";

    private final HhppMglManager hhppMglManager = new HhppMglManager();
    private final CmtOrdenTrabajoMglManager ordenTrabajoMglManager = new CmtOrdenTrabajoMglManager();
    private final OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
    private final CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
    private final CmtTipoBasicaMglManager tipoBasicaMglManager = new CmtTipoBasicaMglManager();
    private final ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
    private final CmtAgendamientoMglManager agendamientoMglManager = new CmtAgendamientoMglManager();
    private final AgendamientoHhppMglManager agendamientoHhppMglManager = new AgendamientoHhppMglManager();
    private final CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();

    /**
     * Creacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param appCrearOtRequest
     * @return AppResponseCrearOtDto
     */
    public AppResponseCrearOtDto crearOtCcmmHhppApp(AppCrearOtRequest appCrearOtRequest) {

        AppResponseCrearOtDto appResponseCrearOtDto = new AppResponseCrearOtDto();

        try {
            if (appCrearOtRequest.getOrdenPurpose() != null
                    && appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("Mantenimiento") 
                    || (appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("TrasladoInterno")
                    || appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("Traslado Interno"))) {

                if (appCrearOtRequest.getIdEnlace() == null
                        || appCrearOtRequest.getIdEnlace().isEmpty()) {
                    appResponseCrearOtDto.setMessageType("E");
                    appResponseCrearOtDto.setMessage("El campo idEnlace es obligatorio");
                } else {
                    buscarOtByEnlace(appCrearOtRequest.getIdEnlace());

                    if (ordenCcmmMglApp != null) {
                        if (appCrearOtRequest.getSegment() == null
                                || appCrearOtRequest.getSegment().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo segment es obligatorio");
                        } else if (appCrearOtRequest.getSuggestedDate() == null
                                || appCrearOtRequest.getSuggestedDate().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo suggestedDate es obligatorio");
                        } else if (appCrearOtRequest.getOtClass() == null
                                || appCrearOtRequest.getOtClass().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo otClass es obligatorio");
                        } else if (appCrearOtRequest.getObservations() == null
                                || appCrearOtRequest.getObservations().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo observations es obligatorio");
                        } else if (appCrearOtRequest.getOnyxOtHija() == null
                                || appCrearOtRequest.getOnyxOtHija().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo onyxOtHija es obligatorio");
                        } else if (appCrearOtRequest.getUser() == null
                                || appCrearOtRequest.getUser().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo user es obligatorio");
                        } else {
                            
                            int tipoOrden = 1;
                            if(appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("Mantenimiento")){
                              tipoOrden = 1;
                            }else{
                                if(appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("TrasladoInterno") 
                                        || appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("Traslado Interno")){
                                     tipoOrden = 3;
                                }
                            }
                            
                            //Todos los campos estan llenos validaciones de negocio
                            if (validacionesCrearOtCcmm(appCrearOtRequest,tipoOrden)) {
                                CmtOrdenTrabajoMgl orden = new CmtOrdenTrabajoMgl();
                                orden.setCmObj(cuentaMatrizMglApp);
                                orden.setBasicaIdTecnologia(tecnoMglApp);
                                orden.setSegmento(segmentoMglApp);
                                orden.setBasicaIdTipoTrabajo(tipoTrabajoMglApp);
                                orden.setTipoOtObj(tipoOtMglApp);
                                orden.setEstadoInternoObj(estadoInternoMglApp);
                                orden.setFechaProgramacion(fechaSugeridadMglApp);
                                orden.setClaseOrdenTrabajo(classOtMglApp);
                                orden.setObservacion(observacionesMglApp);
                                orden.setOnyxOtHija(new BigDecimal(appCrearOtRequest.getOnyxOtHija()));
                                orden.setEnlaceId(appCrearOtRequest.getIdEnlace());
                                orden.setUsuarioCreacionId(cedulaUser);

                                orden = ordenTrabajoMglManager.crearOt(orden, appCrearOtRequest.getUser(), 1);
                                
                                if (orden != null && orden.getIdOt() != null) {
                                    //Ajuste de logica para el cambio de estado interno en la creacion de la OT por medio del consumo del servicio
                                    CmtOrdenTrabajoMglDaoImpl dao = new CmtOrdenTrabajoMglDaoImpl();
                                    CmtBasicaMgl basicaActual = basicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_FOESTADO_PENDIENTE);
                                    CmtBasicaMgl basicaNueva =  basicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_FOESTADO_AGENDADO);
                                    CmtOrdenTrabajoMgl ordenCreada = dao.find(orden.getIdOt());
                                    
                                    if (ordenCreada!= null && ordenCreada.getEstadoInternoObj()!= null && ordenCreada.getEstadoInternoObj().getIdentificadorInternoApp()!= null && ordenCreada.getEstadoInternoObj().getIdentificadorInternoApp().equals(basicaActual.getIdentificadorInternoApp())) {
                                        ordenCreada.setEstadoInternoObj(basicaNueva);
                                        dao.update(ordenCreada);
                                    }
                                    if (saveInfoOnix(orden.getOnyxOtHija().toString(), null, orden, appCrearOtRequest.getUser())) {
                                        if (saveInfoNotes(null, orden, appCrearOtRequest.getUser())) {
                                            String msnError = "Se ha creado la Ot numero: "
                                                    + orden.getIdOt() + " satisfactoriamente";
                                            appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                            appResponseCrearOtDto.setMessageType("I");
                                            appResponseCrearOtDto.setMessage(msnError);
                                            appResponseCrearOtDto.setOtLocation("CCMM");
                                        } else {
                                            String msnError = "Se ha creado la Ot numero: "
                                                    + orden.getIdOt() + " satisfactoriamente";
                                            appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                            appResponseCrearOtDto.setMessageType("I");
                                            appResponseCrearOtDto.setMessage(msnError);
                                            appResponseCrearOtDto.setOtLocation("CCMM");
                                        }
                                    } else if (saveInfoNotes(null, orden, appCrearOtRequest.getUser())) {
                                        String msnError = "Se ha creado la Ot numero: "
                                                + orden.getIdOt() + " satisfactoriamente | "
                                                + "No se pudo registrar la ot hija onix ya "
                                                + "que OT con ID " + orden.getOnyxOtHija() + " no encontrada en Onix";
                                        appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                        appResponseCrearOtDto.setMessageType("I");
                                        appResponseCrearOtDto.setMessage(msnError);
                                        appResponseCrearOtDto.setOtLocation("CCMM");
                                    } else {
                                        String msnError = "Se ha creado la Ot numero: "
                                                + orden.getIdOt() + " satisfactoriamente";
                                        appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                        appResponseCrearOtDto.setMessageType("I");
                                        appResponseCrearOtDto.setMessage(msnError);
                                        appResponseCrearOtDto.setOtLocation("CCMM");
                                    }
                                } else {
                                    String msnError = "No se ha podido crear la Ot"
                                            + " de manera correcta. Intente nuevamente por favor";
                                    appResponseCrearOtDto.setMessageType("E");
                                    appResponseCrearOtDto.setMessage(msnError);
                                    appResponseCrearOtDto.setOtLocation("CCMM");
                                }
                            }
                        }
                    } else if (ordenHhppMglApp != null) {
                        if (appCrearOtRequest.getSegment() == null
                                || appCrearOtRequest.getSegment().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo segment es obligatorio");
                        } else if (appCrearOtRequest.getContactName() == null
                                || appCrearOtRequest.getContactName().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo contactName es obligatorio");
                        } else if (appCrearOtRequest.getContactPhone() == null
                                || appCrearOtRequest.getContactPhone().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo contactPhone es obligatorio");
                        } else if (appCrearOtRequest.getContactEmail() == null
                                || appCrearOtRequest.getContactEmail().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo contactMail es obligatorio");
                        } else if (appCrearOtRequest.getGeneralStatus() == null
                                || appCrearOtRequest.getGeneralStatus().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo generalStatus es obligatorio");
                        } else if (appCrearOtRequest.getInitialRazon() == null
                                || appCrearOtRequest.getInitialRazon().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo initialRazon es obligatorio");
                        } else if (appCrearOtRequest.getOnyxOtHija() == null
                                || appCrearOtRequest.getOnyxOtHija().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo onyxOtHija es obligatorio");
                        } else if (appCrearOtRequest.getUser() == null
                                || appCrearOtRequest.getUser().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo user es obligatorio");
                        } else {
                            //Todos los campos estan llenos validaciones de negocio 
                            
                            int tipoOrden = 1;
                            if (appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("Mantenimiento")) {
                                tipoOrden = 1;
                            } else {
                                if (appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("TrasladoInterno") 
                                        || appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("Traslado Interno")) {
                                    tipoOrden = 3;
                                }
                            }
                            if (validacionesCrearOtDir(appCrearOtRequest, tipoOrden)) {
                                OtHhppMgl otHhppCreada = new OtHhppMgl();

                                otHhppCreada.setTipoOtHhppId(tipoOtHhppMglApp);
                                otHhppCreada.setNombreContacto(appCrearOtRequest.getContactName());
                                otHhppCreada.setTelefonoContacto(appCrearOtRequest.getContactPhone());
                                otHhppCreada.setCorreoContacto(appCrearOtRequest.getContactEmail());
                                otHhppCreada.setFechaCreacionOt(new Date());
                                otHhppCreada.setSegmento(segmentoMglApp);
                                otHhppCreada.setEstadoInternoInicial(razIniOtMglApp);
                                otHhppCreada.setEstadoGeneral(estIniOtMglApp);
                                otHhppCreada.setOnyxOtHija(new BigDecimal(appCrearOtRequest.getOnyxOtHija()));
                                otHhppCreada.setEnlaceId(appCrearOtRequest.getIdEnlace());

                                otHhppCreada.setDireccionId(ordenHhppMglApp.getDireccionId());
                                otHhppCreada.setSubDireccionId(ordenHhppMglApp.getSubDireccionId());
                           

                                otHhppCreada = createIndependentOt(otHhppCreada, appCrearOtRequest.getUser(), 1, listTecnologias);

                                if (otHhppCreada != null && otHhppCreada.getOtHhppId() != null) {
                                    if (saveInfoOnix(otHhppCreada.getOnyxOtHija().toString(), otHhppCreada, null, appCrearOtRequest.getUser())) {
                                        if (saveInfoNotes(otHhppCreada, null, appCrearOtRequest.getUser())) {
                                            String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                                            appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                            appResponseCrearOtDto.setMessageType("I");
                                            appResponseCrearOtDto.setMessage(msnError);
                                            appResponseCrearOtDto.setOtLocation("HHPP");
                                        } else {
                                            String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                                            appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                            appResponseCrearOtDto.setMessageType("I");
                                            appResponseCrearOtDto.setMessage(msnError);
                                            appResponseCrearOtDto.setOtLocation("HHPP");
                                        }
                                    } else if (saveInfoNotes(otHhppCreada, null, appCrearOtRequest.getUser())) {
                                        String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " "
                                                + "satisfactoriamente | No se pudo registrar la ot hija onix "
                                                + "ya que OT con ID " + otHhppCreada.getOnyxOtHija() + " no encontrada en Onix";
                                        appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                        appResponseCrearOtDto.setMessageType("I");
                                        appResponseCrearOtDto.setMessage(msnError);
                                        appResponseCrearOtDto.setOtLocation("HHPP");
                                    } else {
                                        String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                                        appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                        appResponseCrearOtDto.setMessageType("I");
                                        appResponseCrearOtDto.setMessage(msnError);
                                        appResponseCrearOtDto.setOtLocation("HHPP");
                                    }
                                } else {
                                    String msnError = "No se ha podido crear la Ot"
                                            + " de manera correcta. Intente nuevamente por favor";
                                    appResponseCrearOtDto.setMessageType("E");
                                    appResponseCrearOtDto.setMessage(msnError);
                                }
                            }
                        }

                    }
                }

            } else if (appCrearOtRequest.getOrdenPurpose() != null
                    && appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase("Traslado")) {
                if (appCrearOtRequest.getIdEnlace() == null
                        || appCrearOtRequest.getIdEnlace().isEmpty()) {
                    appResponseCrearOtDto.setMessageType("E");
                    appResponseCrearOtDto.setMessage("El campo idEnlace es obligatorio");
                } else if (appCrearOtRequest.getIdDirDetalladaDestino() == null
                        || appCrearOtRequest.getIdDirDetalladaDestino().isEmpty()) {
                    appResponseCrearOtDto.setMessageType("E");
                    appResponseCrearOtDto.setMessage("El campo idDirDetalladaDestino es obligatorio");
                }else {
                    buscarOtByEnlace(appCrearOtRequest.getIdEnlace()); 
                    
                    int control;          
                    control = creacionOtdireccionDestino(appCrearOtRequest.getIdDirDetalladaDestino());
                                       
                    if (control == 1) {
                        if (appCrearOtRequest.getSegment() == null
                                || appCrearOtRequest.getSegment().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo segment es obligatorio");
                        } else if (appCrearOtRequest.getSuggestedDate() == null
                                || appCrearOtRequest.getSuggestedDate().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo suggestedDate es obligatorio");
                        } else if (appCrearOtRequest.getOtClass() == null
                                || appCrearOtRequest.getOtClass().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo otClass es obligatorio");
                        } else if (appCrearOtRequest.getObservations() == null
                                || appCrearOtRequest.getObservations().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo observations es obligatorio");
                        } else if (appCrearOtRequest.getOnyxOtHija() == null
                                || appCrearOtRequest.getOnyxOtHija().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo onyxOtHija es obligatorio");
                        } else if (appCrearOtRequest.getUser() == null
                                || appCrearOtRequest.getUser().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo user es obligatorio");
                        } else {
                            //Todos los campos estan llenos validaciones de negocio
                            if (validacionesCrearOtCcmm(appCrearOtRequest, 2)) {
                                CmtOrdenTrabajoMgl orden = new CmtOrdenTrabajoMgl();
                                orden.setCmObj(cuentaMatrizMglApp);
                                orden.setBasicaIdTecnologia(tecnoMglApp);
                                orden.setSegmento(segmentoMglApp);
                                orden.setBasicaIdTipoTrabajo(tipoTrabajoMglApp);
                                orden.setTipoOtObj(tipoOtMglApp);
                                orden.setEstadoInternoObj(estadoInternoMglApp);
                                orden.setFechaProgramacion(fechaSugeridadMglApp);
                                orden.setClaseOrdenTrabajo(classOtMglApp);
                                orden.setObservacion(observacionesMglApp);
                                orden.setOnyxOtHija(new BigDecimal(appCrearOtRequest.getOnyxOtHija()));
                                orden.setEnlaceId(appCrearOtRequest.getIdEnlace());
                                orden.setUsuarioCreacionId(cedulaUser);

                                orden = ordenTrabajoMglManager.crearOt(orden, appCrearOtRequest.getUser(), 1);
                                
                                if (orden != null && orden.getIdOt() != null) {
                                    //Ajuste de logica para el cambio de estado interno en la creacion de la OT por medio del consumo del servicio
                                    CmtOrdenTrabajoMglDaoImpl dao = new CmtOrdenTrabajoMglDaoImpl();
                                    CmtBasicaMgl basicaActual = basicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_FOESTADO_PENDIENTE);
                                    CmtBasicaMgl basicaNueva =  basicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_FOESTADO_AGENDADO);
                                    CmtOrdenTrabajoMgl ordenCreada = dao.find(orden.getIdOt());
                                    
                                    if (ordenCreada!= null && ordenCreada.getEstadoInternoObj()!= null && ordenCreada.getEstadoInternoObj().getIdentificadorInternoApp()!= null && ordenCreada.getEstadoInternoObj().getIdentificadorInternoApp().equals(basicaActual.getIdentificadorInternoApp())) {
                                        ordenCreada.setEstadoInternoObj(basicaNueva);
                                        dao.update(ordenCreada);
                                    }
                                    if (saveInfoOnix(orden.getOnyxOtHija().toString(), null, orden, appCrearOtRequest.getUser())) {
                                        if (saveInfoNotes(null, orden, appCrearOtRequest.getUser())) {
                                            String msnError = "Se ha creado la Ot numero: "
                                                    + orden.getIdOt() + " satisfactoriamente";
                                            appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                            appResponseCrearOtDto.setMessageType("I");
                                            appResponseCrearOtDto.setMessage(msnError);
                                            appResponseCrearOtDto.setOtLocation("CCMM");
                                        } else {
                                            String msnError = "Se ha creado la Ot numero: "
                                                    + orden.getIdOt() + " satisfactoriamente";
                                            appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                            appResponseCrearOtDto.setMessageType("I");
                                            appResponseCrearOtDto.setMessage(msnError);
                                            appResponseCrearOtDto.setOtLocation("CCMM");
                                        }
                                    } else if (saveInfoNotes(null, orden, appCrearOtRequest.getUser())) {
                                        String msnError = "Se ha creado la Ot numero: "
                                                + orden.getIdOt() + " satisfactoriamente | "
                                                + "No se pudo registrar la ot hija onix ya "
                                                + "que OT con ID " + orden.getOnyxOtHija() + " no encontrada en Onix";
                                        appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                        appResponseCrearOtDto.setMessageType("I");
                                        appResponseCrearOtDto.setMessage(msnError);
                                        appResponseCrearOtDto.setOtLocation("CCMM");
                                    } else {
                                        String msnError = "Se ha creado la Ot numero: "
                                                + orden.getIdOt() + " satisfactoriamente";
                                        appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                        appResponseCrearOtDto.setMessageType("I");
                                        appResponseCrearOtDto.setMessage(msnError);
                                        appResponseCrearOtDto.setOtLocation("CCMM");
                                    }
                                } else {
                                    String msnError = "No se ha podido crear la Ot"
                                            + " de manera correcta. Intente nuevamente por favor";
                                    appResponseCrearOtDto.setMessageType("E");
                                    appResponseCrearOtDto.setMessage(msnError);
                                }
                            }
                        }
                    } else if (control == 2) {
                        if (appCrearOtRequest.getSegment() == null
                                || appCrearOtRequest.getSegment().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo segment es obligatorio");
                        } else if (appCrearOtRequest.getContactName() == null
                                || appCrearOtRequest.getContactName().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo contactName es obligatorio");
                        } else if (appCrearOtRequest.getContactPhone() == null
                                || appCrearOtRequest.getContactPhone().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo contactPhone es obligatorio");
                        } else if (appCrearOtRequest.getContactEmail() == null
                                || appCrearOtRequest.getContactEmail().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo contactMail es obligatorio");
                        } else if (appCrearOtRequest.getGeneralStatus() == null
                                || appCrearOtRequest.getGeneralStatus().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo generalStatus es obligatorio");
                        } else if (appCrearOtRequest.getInitialRazon() == null
                                || appCrearOtRequest.getInitialRazon().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo initialRazon es obligatorio");
                        } else if (appCrearOtRequest.getOnyxOtHija() == null
                                || appCrearOtRequest.getOnyxOtHija().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo onyxOtHija es obligatorio");
                        } else if (appCrearOtRequest.getUser() == null
                                || appCrearOtRequest.getUser().isEmpty()) {
                            appResponseCrearOtDto.setMessageType("E");
                            appResponseCrearOtDto.setMessage("El campo user es obligatorio");
                        } else {
                            //Todos los campos estan llenos validaciones de negocio

                            if (validacionesCrearOtDir(appCrearOtRequest, 2)) {
                                OtHhppMgl otHhppCreada = new OtHhppMgl();

                                otHhppCreada.setTipoOtHhppId(tipoOtHhppMglApp);
                                otHhppCreada.setNombreContacto(appCrearOtRequest.getContactName());
                                otHhppCreada.setTelefonoContacto(appCrearOtRequest.getContactPhone());
                                otHhppCreada.setCorreoContacto(appCrearOtRequest.getContactEmail());
                                otHhppCreada.setFechaCreacionOt(new Date());
                                otHhppCreada.setSegmento(segmentoMglApp);
                                otHhppCreada.setEstadoInternoInicial(razIniOtMglApp);
                                otHhppCreada.setEstadoGeneral(estIniOtMglApp);
                                otHhppCreada.setOnyxOtHija(new BigDecimal(appCrearOtRequest.getOnyxOtHija()));
                                otHhppCreada.setEnlaceId(appCrearOtRequest.getIdEnlace());
                                if (lstHhppMglApp != null && !lstHhppMglApp.isEmpty()) {
                                    if (lstHhppMglApp.get(0).getNodId() != null) {
                                        otHhppCreada.setNodosSeleccionado(lstHhppMglApp.get(0).getNodId().getNodCodigo());
                                    }
                                }

                                if (detalladaMglApp != null) {

                                    otHhppCreada.setDireccionId(detalladaMglApp.getDireccion());
                                    otHhppCreada.setSubDireccionId(detalladaMglApp.getSubDireccion());
                                }

                                otHhppCreada = createIndependentOt(otHhppCreada, appCrearOtRequest.getUser(), 1, listTecnologias);

                                if (otHhppCreada != null && otHhppCreada.getOtHhppId() != null) {
                                    if (saveInfoOnix(otHhppCreada.getOnyxOtHija().toString(), otHhppCreada, null, appCrearOtRequest.getUser())) {
                                        if (saveInfoNotes(otHhppCreada, null, appCrearOtRequest.getUser())) {
                                            String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                                            appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                            appResponseCrearOtDto.setMessageType("I");
                                            appResponseCrearOtDto.setMessage(msnError);
                                            appResponseCrearOtDto.setOtLocation("HHPP");
                                        } else {
                                            String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                                            appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                            appResponseCrearOtDto.setMessageType("I");
                                            appResponseCrearOtDto.setMessage(msnError);
                                            appResponseCrearOtDto.setOtLocation("HHPP");
                                        }
                                    } else if (saveInfoNotes(otHhppCreada, null, appCrearOtRequest.getUser())) {
                                        String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " "
                                                + "satisfactoriamente | No se pudo registrar la ot hija onix "
                                                + "ya que OT con ID " + otHhppCreada.getOnyxOtHija() + " no encontrada en Onix";
                                        appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                        appResponseCrearOtDto.setMessageType("I");
                                        appResponseCrearOtDto.setMessage(msnError);
                                        appResponseCrearOtDto.setOtLocation("HHPP");
                                    } else {
                                        String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                                        appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                        appResponseCrearOtDto.setMessageType("I");
                                        appResponseCrearOtDto.setMessage(msnError);
                                        appResponseCrearOtDto.setOtLocation("HHPP");
                                    }
                                } else {
                                    String msnError = "No se ha podido crear la Ot"
                                            + " de manera correcta. Intente nuevamente por favor";
                                    appResponseCrearOtDto.setMessageType("E");
                                    appResponseCrearOtDto.setMessage(msnError);
                                }
                            }
                        }

                    }
                }
            } else if (appCrearOtRequest.getOrdenPurpose() == null
                    || appCrearOtRequest.getOrdenPurpose().isEmpty()) {
                appResponseCrearOtDto.setMessageType("E");
                appResponseCrearOtDto.setMessage("El campo ordenPurpose es obligatorio");
            } else {
                appResponseCrearOtDto.setMessageType("E");
                appResponseCrearOtDto.setMessage("El campo ordenPurpose debe tener"
                        + " alguna de estas opciones: 'Mantenimiento' o 'Traslado' o 'TrasladoInterno'");
            }
        } catch (ApplicationException ex) {
            appResponseCrearOtDto.setMessageType("E");
            appResponseCrearOtDto.setMessage(ex.getMessage());
            return appResponseCrearOtDto;
        }

        return appResponseCrearOtDto;
    }
    
    /**
     * Modificacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param appModificarOtRequest
     * @return AppResponseUpdateOtDto
     */
    public AppResponseUpdateOtDto modificarOTCcmmHhppApp(AppModificarOtRequest appModificarOtRequest) {

        AppResponseUpdateOtDto appResponseUpdateOtDto = new AppResponseUpdateOtDto();
        try {
            if (appModificarOtRequest.getOtLocation() != null
                    && appModificarOtRequest.getOtLocation().equalsIgnoreCase("CCMM")) {
                //Solicitud de modificacion de OT de CCMM
                if (appModificarOtRequest.getIdOt() == null
                        || appModificarOtRequest.getIdOt().isEmpty()) {
                    appResponseUpdateOtDto.setMessageType("E");
                    appResponseUpdateOtDto.setMessage("El campo idOt es obligatorio");
                } else if (appModificarOtRequest.getUser() == null
                        || appModificarOtRequest.getUser().isEmpty()) {
                    appResponseUpdateOtDto.setMessageType("E");
                    appResponseUpdateOtDto.setMessage("El campo user es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesModificarOtCcmm(appModificarOtRequest)) {

                        if (estadoInternoMglApp != null) {
                            ordenCcmmMglApp.setEstadoInternoObj(estadoInternoMglApp);
                        }

                        if (fechaSugeridadMglApp != null) {
                            ordenCcmmMglApp.setFechaProgramacion(fechaSugeridadMglApp);
                        }
                        if (classOtMglApp != null) {
                            ordenCcmmMglApp.setClaseOrdenTrabajo(classOtMglApp);
                        }
                        if (appModificarOtRequest.getIdEnlace() != null) {
                            ordenCcmmMglApp.setEnlaceId(appModificarOtRequest.getIdEnlace());
                        }

                        String mensaje = actualizarEstadoOTCCmm(ordenCcmmMglApp, estadoInternoMglApp,
                                appModificarOtRequest.getUser());
                        if (saveInfoNotes(null, ordenCcmmMglApp, appModificarOtRequest.getUser())) {
                            appResponseUpdateOtDto.setIdOt(ordenCcmmMglApp.getIdOt());
                            appResponseUpdateOtDto.setMessageType("I");
                            appResponseUpdateOtDto.setMessage(mensaje);
                        } else {
                            appResponseUpdateOtDto.setIdOt(ordenCcmmMglApp.getIdOt());
                            appResponseUpdateOtDto.setMessageType("I");
                            appResponseUpdateOtDto.setMessage(mensaje);
                        }
                    }
                }
            } else if (appModificarOtRequest.getOtLocation() != null
                    && appModificarOtRequest.getOtLocation().equalsIgnoreCase("HHPP")) {
                //Solicitud de modificacion de OT de HHPP
                if (appModificarOtRequest.getIdOt() == null
                        || appModificarOtRequest.getIdOt().isEmpty()) {
                    appResponseUpdateOtDto.setMessageType("E");
                    appResponseUpdateOtDto.setMessage("El campo idOt es obligatorio");
                } else if (appModificarOtRequest.getUser() == null
                        || appModificarOtRequest.getUser().isEmpty()) {
                    appResponseUpdateOtDto.setMessageType("E");
                    appResponseUpdateOtDto.setMessage("El campo user es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio

                    if (validacionesModificarOtDir(appModificarOtRequest)) {

                        if (appModificarOtRequest.getContactName() != null
                                && !appModificarOtRequest.getContactName().isEmpty()) {
                            ordenHhppMglApp.setNombreContacto(appModificarOtRequest.getContactName());
                        }
                        if (appModificarOtRequest.getContactPhone() != null
                                && !appModificarOtRequest.getContactPhone().isEmpty()) {
                            ordenHhppMglApp.setTelefonoContacto(appModificarOtRequest.getContactPhone());
                        }
                        if (appModificarOtRequest.getContactEmail() != null
                                && !appModificarOtRequest.getContactEmail().isEmpty()) {
                            ordenHhppMglApp.setCorreoContacto(appModificarOtRequest.getContactEmail());
                        }

                        if (estIniOtMglApp != null) {
                            ordenHhppMglApp.setEstadoGeneral(estIniOtMglApp);
                        }

                        if (razIniOtMglApp != null) {
                            ordenHhppMglApp.setEstadoInternoInicial(razIniOtMglApp);
                            CmtBasicaMgl razonInicialInternaSeleccionada = basicaMglManager.findById(razIniOtMglApp);
                            if (razonInicialInternaSeleccionada.getIdentificadorInternoApp()
                                    .equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_CREAR_HHPP)) {
                                crearHhppApartirDeLaRazon(appModificarOtRequest.getUser());
                            }
                        }

                        if (appModificarOtRequest.getIdEnlace() != null
                                && !appModificarOtRequest.getIdEnlace().isEmpty()) {
                            ordenHhppMglApp.setEnlaceId(appModificarOtRequest.getIdEnlace());
                        }

                        ordenHhppMglApp = otHhppMglManager.editarOtHhpp(ordenHhppMglApp, appModificarOtRequest.getUser(), 1);

                        String msn = "Cambios realizados a la Ot: " + "" + ordenHhppMglApp.getOtHhppId() + " satisfactoriamente";

                        if (saveInfoNotes(ordenHhppMglApp, null, appModificarOtRequest.getUser())) {
                            appResponseUpdateOtDto.setIdOt(ordenHhppMglApp.getOtHhppId());
                            appResponseUpdateOtDto.setMessageType("I");
                            appResponseUpdateOtDto.setMessage(msn);
                        } else {
                            appResponseUpdateOtDto.setIdOt(ordenHhppMglApp.getOtHhppId());
                            appResponseUpdateOtDto.setMessageType("I");
                            appResponseUpdateOtDto.setMessage(msn);
                        }
                    }
                }
            } else if (appModificarOtRequest.getOtLocation() == null
                    || appModificarOtRequest.getOtLocation().isEmpty()) {
                appResponseUpdateOtDto.setMessageType("E");
                appResponseUpdateOtDto.setMessage("El campo otLocation es obligatorio");
            } else {
                appResponseUpdateOtDto.setMessageType("E");
                appResponseUpdateOtDto.setMessage("El campo otLocation debe tener dos opciones: 'CCMM' o 'HHPP'");
            }

        } catch (ApplicationException ex) {
            appResponseUpdateOtDto.setMessageType("E");
            appResponseUpdateOtDto.setMessage(ex.getMessage());
            return appResponseUpdateOtDto;
        }
        return appResponseUpdateOtDto;
    }

    public boolean validacionesCrearOtDir(AppCrearOtRequest request, int tipoOrden) throws ApplicationException {
        
        ParametrosMgl parametroTipoOtMto= null;
        
        if (tipoOrden == 1) {
            //Se busca el parametro de tipo de orden mantenimiento para DIRECCIONES
            parametroTipoOtMto = parametrosMglManager.findParametroMgl(Constant.TIPO_OT_MTO_DIR);

            if (parametroTipoOtMto == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.TIPO_OT_MTO_DIR);
            }
            //Fin busqueda el parametro de tipo de orden mantenimiento para DIRECCIONES     
        } else if (tipoOrden == 2) {
            //Se busca el parametro de tipo de orden traslado para DIRECCIONES
             parametroTipoOtMto = parametrosMglManager.findParametroMgl(Constant.TIPO_OT_TRAS_DIR);

            if (parametroTipoOtMto == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.TIPO_OT_TRAS_DIR);
            }
            //Fin busqueda el parametro de tipo de orden traslado para DIRECCIONES
        }else if (tipoOrden == 3){
            //Se busca el parametro de tipo de orden traslado para DIRECCIONES
             parametroTipoOtMto = parametrosMglManager.findParametroMgl(Constant.TIPO_OT_TRAS_INT_DIR);

            if (parametroTipoOtMto == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.TIPO_OT_TRAS_INT_DIR);
            }
            
        }
       
        if (parametroTipoOtMto != null) {
            //Validacion sub tipo de orden
            Pattern patSubtipoNum = Pattern.compile("[0-9]*");
            Matcher SubtipoNum = patSubtipoNum.matcher(parametroTipoOtMto.getParValor());
            if (SubtipoNum.matches()) {
                //buscamos el tipo de orden
                TipoOtHhppMglManager tipoOtHhppMglManager = new TipoOtHhppMglManager();
                tipoOtHhppMglApp = tipoOtHhppMglManager.findTipoOtHhppById(new BigDecimal(parametroTipoOtMto.getParValor()));
                if (tipoOtHhppMglApp == null || tipoOtHhppMglApp.getTipoOtId() == null) {
                    throw new ApplicationException("No existe un sub tipo "
                            + " de orden con el id: " + parametroTipoOtMto.getParValor() + " en MGL.");
                }
            } else {
                throw new ApplicationException(" El id del tipo de orden parametrizado: "
                        + "" + parametroTipoOtMto.getParValor() + " no es numerico.");
            }
            //Fin Validacion sub tipo de orden    
        }

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
        if (user != null) {
            cedulaUser = user.getIdUsuario();
            CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
            //Consulto  el rol para gestionar ordenes de HHPP 
            CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.
                    consultarOpcionRol(FORMULARIO_MENU, OPCION_GESTION_OT_HHPP);
            boolean isRolGestion = false;

            if (opcionesRolMgl != null) {
                String rolGestion = opcionesRolMgl.getRol();
                UsRolPerfilManager manager = new UsRolPerfilManager();
                UsPerfil perfilUs = user.getPerfil();
                BigDecimal perfil = perfilUs.getIdPerfil();
                List<UsRolPerfil> roles = manager.findByPerfil(perfil);
                if (roles != null && !roles.isEmpty()) {

                    for (UsRolPerfil usRolPerfil : roles) {
                        if (usRolPerfil.getCodRol().equalsIgnoreCase(rolGestion)) {
                            isRolGestion = true;
                        }
                    }
                } else {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene roles asociados en GESTIONNEW");
                }
                if (!isRolGestion) {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene el rol necesario para crear la orden");
                }
            } else {
                throw new ApplicationException("No existe el rol para formulario:"
                        + " " + FORMULARIO_MENU + " y opcion: " + OPCION_GESTION_OT_HHPP + ""
                        + " no existe en base de datos");
            }
        } else {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        //Validacion estado inicial y Razon Inicial
        CmtTipoBasicaMgl cmtTipoBasicaMgl
                = tipoBasicaMglManager.findByCodigoInternoApp(
                        Constant.ESTADO_GENERAL_OT_HHPP);
        CmtBasicaMgl estIni = basicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), request.getGeneralStatus());

        if (estIni != null && estIni.getBasicaId() != null) {
            CmtTipoBasicaMgl tipoBasica = tipoBasicaMglManager.findByCodigoInternoApp(
                    estadoInternoACargar(estIni.getIdentificadorInternoApp()));
            List<CmtBasicaMgl> estadoInicialInternoList;
            estadoInicialInternoList = basicaMglManager
                    .findByTipoBasica(tipoBasica);
            if (estadoInicialInternoList != null && !estadoInicialInternoList.isEmpty()) {
                int control = 0;
                for (CmtBasicaMgl basicaMgl : estadoInicialInternoList) {
                    if (request.getInitialRazon().equalsIgnoreCase(basicaMgl.getCodigoBasica())) {
                        control = 1;
                        razIniOtMglApp = basicaMgl;
                    }
                }
                if (control == 1) {
                    estIniOtMglApp = estIni;
                } else {
                    throw new ApplicationException("la razon inicial ingresada: "
                            + "" + request.getInitialRazon() + " no hace parte del listado "
                            + "para el estado inicial: " + estIni.getNombreBasica() + "");
                }
            } else {
                throw new ApplicationException("No hay lista de razones iniciales "
                        + "configurado en MGL para el estado inicial: " + estIni.getNombreBasica() + "");
            }
        } else {
            throw new ApplicationException("No existe basica en MGL con el codigo: "
                    + "" + request.getGeneralStatus() + ".");
        }
        //Fin Validacion estado inicial y Razon Inicial

        //Validacion nombre contacto
        Pattern patNombre = Pattern.compile("([a-zA-ZÃ‘Ã±Ã¡Ã©Ã­Ã³ÃºÃ?Ã‰Ã?Ã“Ãš]| ){0,200}");
        Matcher matNombre = patNombre.matcher(request.getContactName());
        if (!matNombre.matches()) {
            throw new ApplicationException(" El nombre del contacto ingresado: "
                    + "" + request.getContactName() + " debe contener solo letras");
        }
        //Fin Validacion nombre contacto

        //Validacion correo contacto
        Pattern patEmail = Pattern.compile("([\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z])?");
        Matcher matEmail = patEmail.matcher(request.getContactEmail());
        if (!matEmail.matches()) {
            throw new ApplicationException(" El correo del contacto no tiene el formato adecuado:'sss@dddd.com'");
        }
        //Fin Validacion correo contacto   

        //Validacion telefono contacto
        Pattern patPhone = Pattern.compile("[0-9]*");
        Matcher matPhone = patPhone.matcher(request.getContactPhone());
        if (!matPhone.matches()) {
            throw new ApplicationException(" El telefono del contacto ingresado: "
                    + "" + request.getContactPhone() + " debe contener solo numeros");
        }
        //Fin Validacion telefono contacto

        //Validacion segmento
        CmtTipoBasicaMgl tipoBasicaSegmento;
        tipoBasicaSegmento = tipoBasicaMglManager.
                findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);
        segmentoMglApp = basicaMglManager.findByTipoBasicaAndCodigo(tipoBasicaSegmento.getTipoBasicaId(), request.getSegment());
        if (segmentoMglApp == null || segmentoMglApp.getBasicaId() == null) {
            throw new ApplicationException("No existe basica segmento en MGL con el codigo: "
                    + "" + request.getSegment() + ".");
        }
        //Fin Validacion segmento

        //Validacion Ot hija
        Pattern patOnixHija = Pattern.compile("[0-9]*");
        Matcher matOnixHija = patOnixHija.matcher(request.getOnyxOtHija());
        if (!matOnixHija.matches()) {
            throw new ApplicationException(" El numero de ot hija debe contener solo numeros");
        }
        if (request.getOnyxOtHija().trim().length() > 15) {
            throw new ApplicationException("El tamaño del campo onyxOtHija "
                    + " excede el valor permitido de '15' caracteres");
        }
        try {
            cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(request.getOnyxOtHija().trim());
        } catch (DatatypeConfigurationException e) {
            throw new ApplicationException("Error consultando Ot hija Onyx");
        }
        if (cmtOnyxResponseDto == null || cmtOnyxResponseDto.equals(0)) {
            throw new ApplicationException("OT con ID " + request.getOnyxOtHija().trim()
                    + " no encontrada en Onyx.");
        }   
        //Fin Validacion Ot hija
         
        //Validacion Tecnologia
         if (listTecnologias == null || listTecnologias.isEmpty()) {
            throw new ApplicationException("No existe una tecnologia valida  GPON y/o UNIFILAR "
                    + "para la creacion de la orden de direccion");
        }
        //Fin Validacion Tecnologia

        //Validacion si vienen notas
        if (request.getNewNotes() != null
                && !request.getNewNotes().isEmpty()) {
            notasHhpp = new ArrayList<>();

            for (String datNotas : request.getNewNotes()) {
                if (datNotas.contains("|")) {
                    String infNote[] = datNotas.split("\\|");
                    if (infNote[0] != null && !infNote[0].isEmpty()) {
                        if (infNote[1] != null && !infNote[1].isEmpty()) {
                            //Buscamos el tipo de nota
                            CmtTipoBasicaMgl tipoBasicaNotaOt;
                            tipoBasicaNotaOt = tipoBasicaMglManager.findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_TIPO_DE_NOTAS);
                            CmtBasicaMgl tipoNota = basicaMglManager.
                                    findByTipoBasicaAndCodigo(tipoBasicaNotaOt.getTipoBasicaId(), infNote[1]);
                            if (tipoNota != null && tipoNota.getBasicaId() != null) {
                                if (infNote[2] != null && !infNote[2].isEmpty()) {
                                    if (infNote[2].trim().length() > 4000) {
                                        throw new ApplicationException("El tamaño del campo nota "
                                                + " excede el valor permitido de '4000' caracteres");
                                    }
                                    CmtNotasOtHhppMgl notasOtHhppMgl = new CmtNotasOtHhppMgl();
                                    notasOtHhppMgl.setDescripcion(infNote[0]);
                                    notasOtHhppMgl.setTipoNotaObj(tipoNota);
                                    notasOtHhppMgl.setNota(infNote[2]);
                                    notasHhpp.add(notasOtHhppMgl);
                                } else {
                                    throw new ApplicationException("La lista de notas debe estar "
                                            + "llena de la siguente manera: "
                                            + "'descripcion|codigoTipoNota|nota' campo nota es obligatorio");
                                }
                            } else {
                                throw new ApplicationException(" No existe tipo Nota en MGL con el codigo: "
                                        + "" + infNote[1] + " ingresado.");
                            }
                        } else {
                            throw new ApplicationException("La lista de notas debe estar "
                                    + "llena de la siguente manera: "
                                    + "'descripcion|codigoTipoNota|nota' campo codigoTipoNota es obligatorio");
                        }
                    } else {
                        throw new ApplicationException("La lista de notas debe estar "
                                + "llena de la siguente manera: "
                                + "'descripcion|codigoTipoNota|nota' campo descripcion es obligatorio");
                    }
                } else {
                    throw new ApplicationException("La lista de notas debe estar "
                            + "llena de la siguente manera: "
                            + "'descripcion|codigoTipoNota|nota' actualmente esta: " + datNotas + "");
                }
            }
        }
        //Fin Validacion si vienen notas
        return true;
    }

    public boolean validacionesCrearOtCcmm(AppCrearOtRequest request, int tipoOrden) throws ApplicationException {

   
        //Validacion campo observaciones
        if (request.getObservations().trim().length() > 1000) {
            throw new ApplicationException("El largo del campo "
                    + "observations: es mayor que el maximo"
                    + " permitido de '1000' el tamaño tiene que estar entre 1 y 1000");
        }
        //Fin Validacion campo observaciones

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());

        if (user != null) {
            cedulaUser = user.getIdUsuario();
            CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
            //Consulto  el rol para editar ordenes de CCMM 
            CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.
                    consultarOpcionRol(FORMULARIO_MENU, OPCION_CREAR_OT_CM);
            boolean isRolCrear = false;

            if (opcionesRolMgl != null) {
                String rolCrear = opcionesRolMgl.getRol();
                UsRolPerfilManager manager = new UsRolPerfilManager();
                UsPerfil perfilUs = user.getPerfil();
                BigDecimal perfil = perfilUs.getIdPerfil();
                List<UsRolPerfil> roles = manager.findByPerfil(perfil);
                if (roles != null && !roles.isEmpty()) {

                    for (UsRolPerfil usRolPerfil : roles) {
                        if (usRolPerfil.getCodRol().equalsIgnoreCase(rolCrear)) {
                            isRolCrear = true;
                        }
                    }
                } else {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene roles asociados en GESTIONNEW");
                }
                if (!isRolCrear) {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene el rol necesario para crear la orden");
                }
            } else {
                throw new ApplicationException("No existe el rol para formulario:"
                        + " " + FORMULARIO_MENU + " y opcion: " + OPCION_CREAR_OT_CM + ""
                        + " no existe en base de datos");
            }
        } else {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        ParametrosMgl parametroTipoOtMto = null;

        if (tipoOrden == 1) {
            //Se busca el parametro de tipo de orden mantenimiento para CCMM
            parametroTipoOtMto = parametrosMglManager.findParametroMgl(Constant.TIPO_OT_MTO_CCMM);
            cuentaMatrizMglApp = ordenCcmmMglApp.getCmObj();

            if (parametroTipoOtMto == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.TIPO_OT_MTO_CCMM);
            }
            //Fin busqueda el parametro de tipo de orden mantenimiento para CCMM    
        } else if (tipoOrden == 2) {
            //Se busca el parametro de tipo de orden traslado para CCMM
            parametroTipoOtMto = parametrosMglManager.findParametroMgl(Constant.TIPO_OT_TRAS_CCMM);
            cuentaMatrizMglApp = ordenCcmmMglApp.getCmObj();

            if (parametroTipoOtMto == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.TIPO_OT_TRAS_CCMM);
            }
            //Fin busqueda el parametro de tipo de orden traslado para CCMM  
        }else if (tipoOrden == 3){
                 //Se busca el parametro de tipo de orden traslado interno para CCMM
            parametroTipoOtMto = parametrosMglManager.findParametroMgl(Constant.TIPO_OT_TRAS_INT_CCMM);
            cuentaMatrizMglApp = ordenCcmmMglApp.getCmObj();

            if (parametroTipoOtMto == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.TIPO_OT_TRAS_INT_CCMM);
            }
            //Fin busqueda el parametro de tipo de orden traslado para CCMM                 
            
        }
        
        if (parametroTipoOtMto != null) {
            //Validacion sub tipo de orden
            Pattern patSubtipoNum = Pattern.compile("[0-9]*");
            Matcher SubtipoNum = patSubtipoNum.matcher(parametroTipoOtMto.getParValor());
            if (SubtipoNum.matches()) {
                //buscamos el tipo de orden
                CmtTipoOtMglManager cmtTipoOtMglManager = new CmtTipoOtMglManager();
                tipoOtMglApp = cmtTipoOtMglManager.findById(new BigDecimal(parametroTipoOtMto.getParValor()));
                tipoTrabajoMglApp = tipoOtMglApp != null ? tipoOtMglApp.getBasicaIdTipoOt() : null;
                if (tipoOtMglApp == null || tipoOtMglApp.getIdTipoOt() == null) {
                    throw new ApplicationException("No existe un sub tipo "
                            + " de orden con el id: " + parametroTipoOtMto.getParValor() + " en MGL.");
                }
            } else {
                throw new ApplicationException(" El id del tipo de orden parametrizado: "
                        + "" + parametroTipoOtMto.getParValor() + " no es numerico.");
            }
            //Fin Validacion sub tipo de orden    
        }
        
        //Validacion de tecnologia
        if (tecnoMglApp != null && tecnoMglApp.getBasicaId() != null) {
            LOGGER.info("Existe la tecnologia: " + tecnoMglApp.getNombreBasica() + "");
        } else {
            throw new ApplicationException("No existe una tecnologia "
                    + "valida GPON y/o UNIFILAR para la creacion de la orden de CCMM.");

        }
        //Fin Validacion de tecnologia 
        
        
        //Validacion estado Flujo
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        CmtBasicaMgl flujoTipoOtMgl = tipoOtMglApp.getTipoFlujoOt();
        CmtEstadoxFlujoMgl estadoInicialFlujo = cmtEstadoxFlujoMglManager.
                findEstadoInicialFlujo(flujoTipoOtMgl, tecnoMglApp);

        if (estadoInicialFlujo != null && estadoInicialFlujo.getEstadoxFlujoId() != null) {
            estadoInternoMglApp = estadoInicialFlujo.getEstadoInternoObj();
        } else {
            throw new ApplicationException("No existe un flujo configurado "
                    + "para el tpo de ot: " + tipoOtMglApp.getDescTipoOt() + "  "
                    + "y la tecnologia: " + tecnoMglApp.getNombreBasica() + ".");

        }
        //Fin Validacion estado Flujo

        //Validacion segmento
        CmtTipoBasicaMgl tipoBasicaSegmento;
        tipoBasicaSegmento = tipoBasicaMglManager.
                findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);
        segmentoMglApp = basicaMglManager.findByTipoBasicaAndCodigo(tipoBasicaSegmento.getTipoBasicaId(), request.getSegment());
        if (segmentoMglApp == null || segmentoMglApp.getBasicaId() == null) {
            throw new ApplicationException("No existe basica segmento en MGL con el codigo: "
                    + "" + request.getSegment() + ".");
        }
        //Fin Validacion segmento

        //Validacion fecha sugerida
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            fechaSugeridadMglApp = formatoDelTexto.parse(request.getSuggestedDate());
            if (fechaSugeridadMglApp.compareTo(new Date()) < 0) {
                String msg = "La fecha de entrega debe ser mayor a la de hoy";
                throw new ApplicationException(msg);
            }
        } catch (ParseException ex) {
            throw new ApplicationException("Error en el formato de la fecha "
                    + "sugeridad: " + request.getSuggestedDate() + " : intente con el siguiente formato:'yyyy-MM-dd HH:mm:ss'.");
        }
        //Fin Validacion fecha sugerida

        //Validacion Clase Ot 
        CmtTipoBasicaMgl tipoBasicaClaseOt;
        tipoBasicaClaseOt = tipoBasicaMglManager.findByCodigoInternoApp(
                Constant.TIPO_BASICA_CLASE_ORDEN_TRABAJO);
        classOtMglApp = basicaMglManager.
                findByTipoBasicaAndCodigo(tipoBasicaClaseOt.getTipoBasicaId(), request.getOtClass());
        if (classOtMglApp == null || classOtMglApp.getBasicaId() == null) {
            throw new ApplicationException("No existe basica clase de OT en MGL con el codigo: "
                    + "" + request.getOtClass() + ".");
        }
        //Fin Validacion Clase Ot

        //Validacion Ot hija
        Pattern patOnixHija = Pattern.compile("[0-9]*");
        Matcher matOnixHija = patOnixHija.matcher(request.getOnyxOtHija());
        if (!matOnixHija.matches()) {
            throw new ApplicationException(" El numero de ot hija debe contener solo numeros");
        }

        if (request.getOnyxOtHija().trim().length() > 15) {
            throw new ApplicationException("El tamaño del campo onyxOtHija "
                    + " excede el valor permitido de '15' caracteres");
        }
        try {
            cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(request.getOnyxOtHija().trim());
        } catch (DatatypeConfigurationException e) {
            throw new ApplicationException("Error consultando Ot hija Onyx");
        }
        if (cmtOnyxResponseDto == null || cmtOnyxResponseDto.equals(0)) {
            throw new ApplicationException("OT con ID " + request.getOnyxOtHija().trim()
                    + " no encontrada en Onyx.");
        }   
        //Fin Validacion Ot hija

        observacionesMglApp = request.getObservations();

        //Validacion si vienen notas
        if (request.getNewNotes() != null
                && !request.getNewNotes().isEmpty()) {
            notasOtCcmm = new ArrayList<>();

            for (String datNotas : request.getNewNotes()) {
                if (datNotas.contains("|")) {
                    String infNote[] = datNotas.split("\\|");
                    if (infNote[0] != null && !infNote[0].isEmpty()) {
                        if (infNote[1] != null && !infNote[1].isEmpty()) {
                            //Buscamos el tipo de nota
                            CmtTipoBasicaMgl tipoBasicaNotaOt;
                            tipoBasicaNotaOt = tipoBasicaMglManager.findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_TIPO_DE_NOTAS);
                            CmtBasicaMgl tipoNota = basicaMglManager.
                                    findByTipoBasicaAndCodigo(tipoBasicaNotaOt.getTipoBasicaId(), infNote[1]);
                            if (tipoNota != null && tipoNota.getBasicaId() != null) {
                                if (infNote[2] != null && !infNote[2].isEmpty()) {
                                    if (infNote[2].trim().length() > 4000) {
                                        throw new ApplicationException("El tamaño del campo nota "
                                                + " excede el valor permitido de '4000' caracteres");
                                    }
                                    CmtNotaOtMgl notasOtCcmmMgl = new CmtNotaOtMgl();
                                    notasOtCcmmMgl.setDescripcion(infNote[0]);
                                    notasOtCcmmMgl.setTipoNotaObj(tipoNota);
                                    notasOtCcmmMgl.setNota(infNote[2]);
                                    notasOtCcmm.add(notasOtCcmmMgl);
                                } else {
                                    throw new ApplicationException("La lista de notas debe estar "
                                            + "llena de la siguente manera: "
                                            + "'descripcion|codigoTipoNota|nota' campo nota es obligatorio");
                                }
                            } else {
                                throw new ApplicationException(" No existe tipo Nota en MGL con el codigo: "
                                        + "" + infNote[1] + " ingresado.");
                            }
                        } else {
                            throw new ApplicationException("La lista de notas debe estar "
                                    + "llena de la siguente manera: "
                                    + "'descripcion|codigoTipoNota|nota' campo codigoTipoNota es obligatorio");
                        }
                    } else {
                        throw new ApplicationException("La lista de notas debe estar "
                                + "llena de la siguente manera: "
                                + "'descripcion|codigoTipoNota|nota' campo descripcion es obligatorio");
                    }
                } else {
                    throw new ApplicationException("La lista de notas debe estar "
                            + "llena de la siguente manera: "
                            + "'descripcion|codigoTipoNota|nota' actualmente esta: " + datNotas + "");
                }
            }
        }
        //Fin Validacion si vienen notas

        return true;
    }

    public boolean validacionesModificarOtDir(AppModificarOtRequest request) throws ApplicationException {

        //Validacion idOt
        Pattern patidOtNum = Pattern.compile("[0-9]*");
        Matcher idOtNum = patidOtNum.matcher(request.getIdOt());
        if (idOtNum.matches()) {
            ordenHhppMglApp = otHhppMglManager.findOtByIdOt(new BigDecimal(request.getIdOt()));

            if (ordenHhppMglApp == null || ordenHhppMglApp.getOtHhppId() == null) {
                throw new ApplicationException(" El id de la orden ingresado: "
                        + "" + request.getIdOt() + " no existe en MGL");
            }
        } else {
            throw new ApplicationException(" El id de la orden ingresado: "
                    + "" + request.getIdOt() + " no es numerico");
        }
        //Fin Validacion idOt

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
        if (user != null) {
            cedulaUser = user.getIdUsuario();
            CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
            //Consulto  el rol para gestionar ordenes de HHPP 
            CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.
                    consultarOpcionRol(FORMULARIO_MENU, OPCION_GESTION_OT_HHPP);
            boolean isRolGestion = false;

            if (opcionesRolMgl != null) {
                String rolGestion = opcionesRolMgl.getRol();
                UsRolPerfilManager manager = new UsRolPerfilManager();
                UsPerfil perfilUs = user.getPerfil();
                BigDecimal perfil = perfilUs.getIdPerfil();
                List<UsRolPerfil> roles = manager.findByPerfil(perfil);
                if (roles != null && !roles.isEmpty()) {

                    for (UsRolPerfil usRolPerfil : roles) {
                        if (usRolPerfil.getCodRol().equalsIgnoreCase(rolGestion)) {
                            isRolGestion = true;
                        }
                    }
                } else {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene roles asociados en GESTIONNEW");
                }
                if (!isRolGestion) {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene el rol necesario para crear la orden");
                }
            } else {
                throw new ApplicationException("No existe el rol para formulario:"
                        + " " + FORMULARIO_MENU + " y opcion: " + OPCION_GESTION_OT_HHPP + ""
                        + " no existe en base de datos");
            }
        } else {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        //Validacion estado inicial y Razon Inicial
        if (request.getGeneralStatus() != null && !request.getGeneralStatus().isEmpty()) {
            CmtTipoBasicaMgl cmtTipoBasicaMgl
                    = tipoBasicaMglManager.findByCodigoInternoApp(
                            Constant.ESTADO_GENERAL_OT_HHPP);
            CmtBasicaMgl estIni = basicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), request.getGeneralStatus());

            if (estIni != null && estIni.getBasicaId() != null) {
                CmtTipoBasicaMgl tipoBasica = tipoBasicaMglManager.findByCodigoInternoApp(
                        estadoInternoACargar(estIni.getIdentificadorInternoApp()));
                List<CmtBasicaMgl> estadoInicialInternoList;
                estadoInicialInternoList = basicaMglManager
                        .findByTipoBasica(tipoBasica);
                if (estadoInicialInternoList != null && !estadoInicialInternoList.isEmpty()) {
                    int control = 0;
                    for (CmtBasicaMgl basicaMgl : estadoInicialInternoList) {
                        if (request.getInitialRazon().equalsIgnoreCase(basicaMgl.getCodigoBasica())) {
                            control = 1;
                            razIniOtMglApp = basicaMgl;
                        }
                    }
                    if (control == 1) {
                        estIniOtMglApp = estIni;
                    } else {
                        throw new ApplicationException("la razon inicial ingresada: "
                                + "" + request.getInitialRazon() + ". no hace parte del listado "
                                + "para el estado inicial: " + estIni.getNombreBasica() + "");
                    }
                } else {
                    throw new ApplicationException("No hay lista de razones iniciales "
                            + "configurado en MGL para el estado inicial: " + estIni.getNombreBasica() + "");
                }
            } else {
                throw new ApplicationException("No existe basica en MGL con el codigo: "
                        + "" + request.getGeneralStatus() + ".");
            }
        }
        //Fin Validacion estado inicial y Razon Inicial

        //Validacion Cambio de Estado
        estActualOtHhppMglApp = ordenHhppMglApp.getEstadoGeneral();
        CmtBasicaMgl estadoAnulado = basicaMglManager.findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO_COMPLETO);
        CmtBasicaMgl estadoCerrado = basicaMglManager.findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO);

        if (estadoAnulado != null && estadoCerrado != null) {
            if (estActualOtHhppMglApp.getBasicaId().compareTo(estadoAnulado.getBasicaId()) == 0
                    || estActualOtHhppMglApp.getBasicaId().compareTo(estadoCerrado.getBasicaId()) == 0) {
                throw new ApplicationException("No puede modificar una orden en estado CERRADO O ANULADO el estado de la orden actualmente es : "
                        + "" + estActualOtHhppMglApp.getNombreBasica() + "");
            }
        }
        //Validacion Cambio de Estado

        //Validacion nombre contacto
        if (request.getContactName() != null && !request.getContactName().isEmpty()) {
            Pattern patNombre = Pattern.compile("([a-zA-ZÃ‘Ã±Ã¡Ã©Ã­Ã³ÃºÃ?Ã‰Ã?Ã“Ãš]| ){0,200}");
            Matcher matNombre = patNombre.matcher(request.getContactName());
            if (!matNombre.matches()) {
                throw new ApplicationException(" El nombre del contacto debe contener solo letras");
            }
        }
        //Fin Validacion nombre contacto

        //Validacion correo contacto
        if (request.getContactEmail() != null && !request.getContactEmail().isEmpty()) {
            Pattern patEmail = Pattern.compile("([\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z])?");
            Matcher matEmail = patEmail.matcher(request.getContactEmail());
            if (!matEmail.matches()) {
                throw new ApplicationException(" El correo del contacto no tiene el formato adecuado:'sss@dddd.com'");
            }
        }
        //Fin Validacion correo contacto   

        //Validacion telefono contacto
        if (request.getContactPhone() != null && !request.getContactPhone().isEmpty()) {
            Pattern patPhone = Pattern.compile("[0-9]*");
            Matcher matPhone = patPhone.matcher(request.getContactPhone());
            if (!matPhone.matches()) {
                throw new ApplicationException(" El telefono del contacto debe contener solo numeros");
            }
        }
        //Fin Validacion telefono contacto

        //Validacion si vienen notas
        if (request.getNewNotes() != null
                && !request.getNewNotes().isEmpty()) {
            notasHhpp = new ArrayList<>();

            for (String datNotas : request.getNewNotes()) {
                if (datNotas.contains("|")) {
                    String infNote[] = datNotas.split("\\|");
                    if (infNote[0] != null && !infNote[0].isEmpty()) {
                        if (infNote[1] != null && !infNote[1].isEmpty()) {
                            //Buscamos el tipo de nota
                            CmtTipoBasicaMgl tipoBasicaNotaOt;
                            tipoBasicaNotaOt = tipoBasicaMglManager.findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_TIPO_DE_NOTAS);
                            CmtBasicaMgl tipoNota = basicaMglManager.
                                    findByTipoBasicaAndCodigo(tipoBasicaNotaOt.getTipoBasicaId(), infNote[1]);
                            if (tipoNota != null && tipoNota.getBasicaId() != null) {
                                if (infNote[2] != null && !infNote[2].isEmpty()) {
                                    if (infNote[2].trim().length() > 4000) {
                                        throw new ApplicationException("El tamaño del campo nota "
                                                + " excede el valor permitido de '4000' caracteres");
                                    }
                                    CmtNotasOtHhppMgl notasOtHhppMgl = new CmtNotasOtHhppMgl();
                                    notasOtHhppMgl.setDescripcion(infNote[0]);
                                    notasOtHhppMgl.setTipoNotaObj(tipoNota);
                                    notasOtHhppMgl.setNota(infNote[2]);
                                    notasHhpp.add(notasOtHhppMgl);
                                } else {
                                    throw new ApplicationException("La lista de notas debe estar "
                                            + "llena de la siguente manera: "
                                            + "'descripcion|codigoTipoNota|nota' campo nota es obligatorio");
                                }
                            } else {
                                throw new ApplicationException(" No existe tipo Nota en MGL con el codigo: "
                                        + "" + infNote[1] + " ingresado.");
                            }
                        } else {
                            throw new ApplicationException("La lista de notas debe estar "
                                    + "llena de la siguente manera: "
                                    + "'descripcion|codigoTipoNota|nota' campo codigoTipoNota es obligatorio");
                        }
                    } else {
                        throw new ApplicationException("La lista de notas debe estar "
                                + "llena de la siguente manera: "
                                + "'descripcion|codigoTipoNota|nota' campo descripcion es obligatorio");
                    }
                } else {
                    throw new ApplicationException("La lista de notas debe estar "
                            + "llena de la siguente manera: "
                            + "'descripcion|codigoTipoNota|nota' actualmente esta: " + datNotas + "");
                }
            }
        }
        //Fin Validacion si vienen notas

        //Validacion agendas pendientes
        if (tieneAgendasPendientes(ordenHhppMglApp)) {
            String msg = "La orden de trabajo No: " + ordenHhppMglApp.getOtHhppId() + "  "
                    + "tiene agendas iniciadas, no se puede modificar.";
            throw new ApplicationException(msg);
        }
        //Validacion agendas pendientes

        return true;
    }

    public boolean validacionesModificarOtCcmm(AppModificarOtRequest request)
            throws ApplicationException {

        //Validacion idOt
        Pattern patIdOtNum = Pattern.compile("[0-9]*");
        Matcher idOtNum = patIdOtNum.matcher(request.getIdOt());
        if (idOtNum.matches()) {
            ordenCcmmMglApp = ordenTrabajoMglManager.findOtById(new BigDecimal(request.getIdOt()));

            if (ordenCcmmMglApp == null || ordenCcmmMglApp.getIdOt() == null) {
                throw new ApplicationException(" El id de la orden ingresado: "
                        + "" + request.getIdOt() + " no existe en MGL");
            }
        } else {
            throw new ApplicationException(" El id de la orden ingresado:"
                    + " " + request.getIdOt() + " no es numerico");
        }
        //Fin Validacion idOt

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
        if (user != null) {
            CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
            //Consulto  el rol para editar ordenes de CCMM 
            CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.
                    consultarOpcionRol(FORMULARIO_GENERAL, ValidacionUtil.OPC_EDICION);
            if (opcionesRolMgl != null) {
                String rolEdi = opcionesRolMgl.getRol();
                boolean isRolEdi = false;
                UsRolPerfilManager manager = new UsRolPerfilManager();
                UsPerfil perfilUs = user.getPerfil();
                BigDecimal perfil = perfilUs.getIdPerfil();
                List<UsRolPerfil> roles = manager.findByPerfil(perfil);
                if (roles != null && !roles.isEmpty()) {
                    //Busco los estados por flujo activos
                    List<CmtEstadoxFlujoMgl> estadosFLujo = estadoxFlujoMglManager.findAllItemsActive();
                    List<BigDecimal> estadosFLujoUsuario = new ArrayList<>();

                    if (estadosFLujo != null && !estadosFLujo.isEmpty()) {
                        //verificamos si el ROL del estado x flujo lo tiene asigando el usuario
                        for (CmtEstadoxFlujoMgl e : estadosFLujo) {
                            if (e.getGestionRol() != null) {
                                for (UsRolPerfil usRolPerfil : roles) {
                                    if (usRolPerfil.getCodRol().equalsIgnoreCase(e.getGestionRol())) {
                                        estadosFLujoUsuario.add(e.getEstadoxFlujoId());
                                    }
                                }
                            }
                        }
                    }
                    //Verificamos que el usuario tenga acceso a la orden
                    if (!estadosFLujoUsuario.isEmpty()) {
                        CmtOrdenTrabajoMgl ot = ordenTrabajoMglManager.
                                findByEstadosXFlujoAndIdOt(estadosFLujoUsuario, ordenCcmmMglApp.getIdOt());
                        if (ot == null) {
                            throw new ApplicationException("El usuario ingresado:"
                                    + "" + request.getUser() + " no tiene acceso a la orden No: " + ordenCcmmMglApp.getIdOt() + " segun roles estados X flujo");
                        }
                    }
                    for (UsRolPerfil usRolPerfil : roles) {
                        if (usRolPerfil.getCodRol().equalsIgnoreCase(rolEdi)) {
                            isRolEdi = true;
                        }
                    }
                } else {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene roles asociados en GESTIONNEW");
                }
                if (!isRolEdi) {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene el rol necesario para editar la orden");
                }
            } else {
                throw new ApplicationException("No existe el rol para formulario:"
                        + " " + FORMULARIO_GENERAL + " y opcion: " + ValidacionUtil.OPC_EDICION + ""
                        + " no existe en base de datos");
            }
        } else {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        //Validacion estado interno
        if (request.getInternaStatus() != null && !request.getInternaStatus().isEmpty()) {
            CmtTipoBasicaMgl cmtTipoBasicaMgl
                    = tipoBasicaMglManager.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_ESTADO_INTERNO_OT);
            CmtBasicaMgl estInT = basicaMglManager.findByTipoBasicaAndCodigo(cmtTipoBasicaMgl.getTipoBasicaId(), request.getInternaStatus());

            if (estInT != null && estInT.getBasicaId() != null) {
                if (estInT.getBasicaId().compareTo(ordenCcmmMglApp.getEstadoInternoObj().getBasicaId()) != 0) {
                    CmtDetalleFlujoMglManager detalleFlujoMglManager = new CmtDetalleFlujoMglManager();
                    detalleFlujoEstActual = detalleFlujoMglManager.
                            findByTipoFlujoAndEstadoIni(ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt(),
                                    ordenCcmmMglApp.getEstadoInternoObj(),
                                    ordenCcmmMglApp.getBasicaIdTecnologia());
                    int control = 0;
                    for (CmtDetalleFlujoMgl detalleFlujoMgl : detalleFlujoEstActual) {
                        if (detalleFlujoMgl.getProximoEstado().getBasicaId().
                                compareTo(estInT.getBasicaId()) == 0) {
                            control++;
                            estadoInternoMglApp = detalleFlujoMgl.getProximoEstado();
                        }
                    }
                    if (control == 0) {
                        throw new ApplicationException("El estado interno ingresado: " + estInT.getNombreBasica() + " no esta dentro del flujo "
                                + "de la orden para proximo estado.");
                    }
                } else {
                    estadoInternoMglApp = estInT;
                }
            } else {
                throw new ApplicationException("No existe basica estado interno en MGL"
                        + " con el codigo:" + request.getInternaStatus() + ".");
            }
        }
        //Fin Validacion estado interno

        //Validacion fecha sugerida
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (request.getSuggestedDate() != null && !request.getSuggestedDate().isEmpty()) {
            try {
                fechaSugeridadMglApp = formatoDelTexto.parse(request.getSuggestedDate());
                if (fechaSugeridadMglApp.compareTo(new Date()) < 0) {
                    String msg = "La fecha de entrega debe ser mayor a la de hoy";
                    throw new ApplicationException(msg);
                }
            } catch (ParseException ex) {
                throw new ApplicationException("Error en el formato de la fecha "
                        + "sugeridad intente con el siguiente formato:'yyyy-MM-dd HH:mm:ss'.");
            }
        }
        //Fin Validacion fecha sugerida

        //Validacion Clase Ot 
        if (request.getOtClass() != null && !request.getOtClass().isEmpty()) {
            CmtTipoBasicaMgl tipoBasicaClaseOt;
            tipoBasicaClaseOt = tipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_CLASE_ORDEN_TRABAJO);
            classOtMglApp = basicaMglManager.
                    findByTipoBasicaAndCodigo(tipoBasicaClaseOt.getTipoBasicaId(), request.getOtClass());
            if (classOtMglApp == null || classOtMglApp.getBasicaId() == null) {
                throw new ApplicationException("No existe basica clase de OT en MGL con el codigo: "
                        + "" + request.getOtClass() + ".");
            }
        }
        //Fin Validacion Clase Ot

        //Validacion si vienen notas
        if (request.getNewNotes() != null
                && !request.getNewNotes().isEmpty()) {
            notasOtCcmm = new ArrayList<>();

            for (String datNotas : request.getNewNotes()) {
                if (datNotas.contains("|")) {
                    String infNote[] = datNotas.split("\\|");
                    if (infNote[0] != null && !infNote[0].isEmpty()) {
                        if (infNote[1] != null && !infNote[1].isEmpty()) {
                            //Buscamos el tipo de nota
                            CmtTipoBasicaMgl tipoBasicaNotaOt;
                            tipoBasicaNotaOt = tipoBasicaMglManager.findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_TIPO_DE_NOTAS);
                            CmtBasicaMgl tipoNota = basicaMglManager.
                                    findByTipoBasicaAndCodigo(tipoBasicaNotaOt.getTipoBasicaId(), infNote[1]);
                            if (tipoNota != null && tipoNota.getBasicaId() != null) {
                                if (infNote[2] != null && !infNote[2].isEmpty()) {
                                    if (infNote[2].trim().length() > 4000) {
                                        throw new ApplicationException("El tamaño del campo nota "
                                                + " excede el valor permitido de '4000' caracteres");
                                    }
                                    CmtNotaOtMgl notasOtCcmmMgl = new CmtNotaOtMgl();
                                    notasOtCcmmMgl.setDescripcion(infNote[0]);
                                    notasOtCcmmMgl.setTipoNotaObj(tipoNota);
                                    notasOtCcmmMgl.setNota(infNote[2]);
                                    notasOtCcmm.add(notasOtCcmmMgl);
                                } else {
                                    throw new ApplicationException("La lista de notas debe estar "
                                            + "llena de la siguente manera: "
                                            + "'descripcion|codigoTipoNota|nota' campo nota es obligatorio");
                                }
                            } else {
                                throw new ApplicationException(" No existe tipo Nota en MGL con el codigo: "
                                        + "" + infNote[1] + " ingresado.");
                            }
                        } else {
                            throw new ApplicationException("La lista de notas debe estar "
                                    + "llena de la siguente manera: "
                                    + "'descripcion|codigoTipoNota|nota' campo codigoTipoNota es obligatorio");
                        }
                    } else {
                        throw new ApplicationException("La lista de notas debe estar "
                                + "llena de la siguente manera: "
                                + "'descripcion|codigoTipoNota|nota' campo descripcion es obligatorio");
                    }
                } else {
                    throw new ApplicationException("La lista de notas debe estar "
                            + "llena de la siguente manera: "
                            + "'descripcion|codigoTipoNota|nota' actualmente esta: " + datNotas + "");
                }
            }
        }
        //Fin Validacion si vienen notas

        //Validaciones Estado X Flujo
        if (ordenCcmmMglApp != null && ordenCcmmMglApp.getIdOt() != null) {
            CmtBasicaMgl tipoFlujo = ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt();
            CmtBasicaMgl estadoInterno = ordenCcmmMglApp.getEstadoInternoObj();
            CmtBasicaMgl tecnologia = ordenCcmmMglApp.getBasicaIdTecnologia();
            boolean respuesta;
            respuesta = estadoxFlujoMglManager.finalizoEstadosxFlujoDto(tipoFlujo, estadoInterno, tecnologia);
            if (respuesta) {
                throw new ApplicationException("El estado X flujo actual de la orden no permite modificarla");
            }
        }
        //Fin Validaciones Estado X Flujo

        return true;
    }

    public String estadoInternoACargar(String estadoSeleccionado) {
        try {
            if (estadoSeleccionado != null
                    && !estadoSeleccionado.trim().isEmpty()) {

                if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO)) {
                    return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO;
                } else {
                    if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO)) {
                        return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO;
                    } else {
                        if (estadoSeleccionado.contains(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO)) {
                            return Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO;
                        }
                    }
                }
            }
            return "--";
        } catch (RuntimeException e) {
            LOGGER.error("Error en estadoInternoACargar: " + e.getMessage(), e);
            return "--";
        } catch (Exception e) {
            LOGGER.error("Error en estadoInternoACargar: " + e.getMessage(), e);
            return "--";
        }
    }

    /**
     * Consulta del capacity en OFSC de una OT desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param consultarCapacityOtDirRequest
     * @return AppResponsesGetCapacityDto
     */
    public AppResponsesGetCapacityDto getCapacidadOtCcmmHhppApp(AppConsultarCapacityOtRequest consultarCapacityOtRequest) {

        AppResponsesGetCapacityDto responsesGetCapacityDto = new AppResponsesGetCapacityDto();
        try {

            if (consultarCapacityOtRequest.getOtLocation() != null
                    && consultarCapacityOtRequest.getOtLocation().equalsIgnoreCase("CCMM")) {
                //Consulta capacity  de OT de CCMM
                if (consultarCapacityOtRequest.getIdOt() == null
                        || consultarCapacityOtRequest.getIdOt().isEmpty()) {
                    responsesGetCapacityDto.setMessageType("E");
                    responsesGetCapacityDto.setMessage("El campo idOt es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesConsultaCapacityCcmm(consultarCapacityOtRequest)) {
                        CmtAgendamientoMglManager agendamientoCcmmMglManager = new CmtAgendamientoMglManager();
                        consultarNodoOtCcmm(ordenCcmmMglApp);
                        List<CapacidadAgendaDto> capacity = agendamientoCcmmMglManager.
                                getCapacidad(ordenCcmmMglApp, null, nodoMglApp);

                        if (capacity.isEmpty()) {
                            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglManager.
                                    findByTipoFlujoAndEstadoInt(ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt(),
                                            ordenCcmmMglApp.getEstadoInternoObj(), ordenCcmmMglApp.getBasicaIdTecnologia());
                            String subtipo = "";
                            if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                                if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                                    subtipo = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                                }

                            }
                            String msg = "No hay capacidad disponible para el tipo de  "
                                    + "trabajo:  " + ordenCcmmMglApp.getTipoOtObj().getBasicaIdTipoOt().
                                            getNombreBasica() + "  y el subTipo: " + subtipo + "";

                            responsesGetCapacityDto.setMessageType("E");
                            responsesGetCapacityDto.setMessage(msg);

                        } else {
                            responsesGetCapacityDto.setCapacidad(capacity);
                        }
                    }
                }

            } else if (consultarCapacityOtRequest.getOtLocation() != null
                    && consultarCapacityOtRequest.getOtLocation().equalsIgnoreCase("HHPP")) {

                //Consulta capacity  de OT de HHPP
                if (consultarCapacityOtRequest.getIdOt() == null
                        || consultarCapacityOtRequest.getIdOt().isEmpty()) {
                    responsesGetCapacityDto.setMessageType("E");
                    responsesGetCapacityDto.setMessage("El campo idOt es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesConsultaCapacityHhpp(consultarCapacityOtRequest)) {
                        UsuariosServicesDTO usuariosServicesDTO = new UsuariosServicesDTO();
                        usuariosServicesDTO.setCedula("1");
                        consultarNodoOtHhpp(otDirModMglApp);
                        List<CapacidadAgendaDto> capacity = agendamientoHhppMglManager.
                                getCapacidadOtDireccion(otDirModMglApp, usuariosServicesDTO, null, nodoMglApp);

                        if (capacity.isEmpty()) {
                            String msg = "No hay capacidad disponible para el tipo de  "
                                    + "trabajo:  " + tipoOtHhppMglApp.getTipoTrabajoOFSC().getNombreBasica()
                                    + "  y el subTipo: " + tipoOtHhppMglApp.
                                            getSubTipoOrdenOFSC().getNombreBasica();
                            responsesGetCapacityDto.setMessageType("E");
                            responsesGetCapacityDto.setMessage(msg);
                        } else {
                            responsesGetCapacityDto.setCapacidad(capacity);
                        }
                    }
                }
            } else if (consultarCapacityOtRequest.getOtLocation() == null
                    || consultarCapacityOtRequest.getOtLocation().isEmpty()) {
                responsesGetCapacityDto.setMessageType("E");
                responsesGetCapacityDto.setMessage("El campo otLocation es obligatorio");
            } else {
                responsesGetCapacityDto.setMessageType("E");
                responsesGetCapacityDto.setMessage("El campo otLocation debe tener dos opciones: 'CCMM' o 'HHPP'");
            }
        } catch (ApplicationException ex) {
            responsesGetCapacityDto.setMessageType("E");
            responsesGetCapacityDto.setMessage(ex.getMessage());
            return responsesGetCapacityDto;
        }
        return responsesGetCapacityDto;
    }
    
    /**
     * Metodo para crear una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param crearAgendaOtDirRequest
     * @return AppResponsesAgendaDto
     */
    public AppResponsesAgendaDto agendarOtCcmmHhppApp(AppCrearAgendaOtRequest crearAgendaOtRequest)
            throws UniformInterfaceException, ApplicationException {

        AppResponsesAgendaDto responsesAgendaDto = new AppResponsesAgendaDto();
        List<CmtAgendamientoMgl> agendarOTSubtipo = null;
        List<MglAgendaDireccion> agendarOTSubtipoHhpp = null;

        try {
            if (crearAgendaOtRequest.getOtLocation() != null
                    && crearAgendaOtRequest.getOtLocation().equalsIgnoreCase("CCMM")) {
                if (crearAgendaOtRequest.getCapacidad() == null
                        || crearAgendaOtRequest.getCapacidad().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo capacidad es obligatorio");
                } else if (crearAgendaOtRequest.getIdOt() == null
                        || crearAgendaOtRequest.getIdOt().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo idOt es obligatorio");
                } else if (crearAgendaOtRequest.getPersonaRecVt() == null
                        || crearAgendaOtRequest.getPersonaRecVt().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo personaRecVt es obligatorio");
                } else if (crearAgendaOtRequest.getTelPerRecVt() == null
                        || crearAgendaOtRequest.getTelPerRecVt().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo telPerRecVt es obligatorio");
                } else if (crearAgendaOtRequest.getEmailPerRecVT() == null
                        || crearAgendaOtRequest.getEmailPerRecVT().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo emailPerRecVT es obligatorio");
                } else if (crearAgendaOtRequest.getUser() == null
                        || crearAgendaOtRequest.getUser().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo user es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesCrearAgendaOtCcmm(crearAgendaOtRequest)) {
                        consultarNodoOtCcmm(ordenCcmmMglApp);
                        //CREAR OT EN RR                           
                        CmtEstadoxFlujoMgl estadoFlujoOrden;
                        String numeroOTRr = null;
                        int i = 0;
                        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);

                        if (parametroHabilitarRR == null) {
                            throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.HABILITAR_RR);
                        }

                        if (parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                            estadoFlujoOrden = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt(), ordenCcmmMglApp.getEstadoInternoObj(),
                                    ordenCcmmMglApp.getBasicaIdTecnologia());
                            if (estadoFlujoOrden != null) {
                                if (estadoFlujoOrden.getSubTipoOrdenOFSC() != null
                                        && estadoFlujoOrden.getTipoTrabajoRR() != null
                                        && estadoFlujoOrden.getEstadoOtRRInicial() != null
                                        && estadoFlujoOrden.getEstadoOtRRFinal() != null) {
                                    String subTipoWorForce = estadoFlujoOrden.getSubTipoOrdenOFSC().getNombreBasica();
                                    agendarOTSubtipo = agendamientoMglManager.agendasPorOrdenAndSubTipoWorkfoce(ordenCcmmMglApp, subTipoWorForce);
                                    if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                                        LOGGER.info("Se creara OT en RR , es la primera agenda del estado actual de la OT en MGL ");
                                        numeroOTRr = ordenTrabajoMglManager.crearOtRRporAgendamiento(ordenCcmmMglApp, estadoFlujoOrden, crearAgendaOtRequest.getUser(), 1);
                                    }
                                } else {
                                    LOGGER.info("El estado actual de la OT no tiene los campos de creacion de OT RR configurados , se enviara id de OT MGL");
                                    String mensajeCamposRRestado = "Recuerde que para que se cree OT en RR y "
                                            + "continuar con el agendamiento debe diligenciar los campos , Tipo OT RR ,"
                                            + " Estado Inicial y Estado Cierre en el estado x flujo actual de OT MGL. ";
                                    LOGGER.info(mensajeCamposRRestado);
                                }

                            } else {
                                LOGGER.info("El estado actual de la OT no permite agendamiento");
                            }
                            //--
                        } else {
                            LOGGER.error("El parámetro " + Constant.ACTIVAR_CREACION_OT_RR + " no se encuentra habilitado. No se crea orden en RR.");
                        }

                        if (agendarOTSubtipo != null && !agendarOTSubtipo.isEmpty()) {
                            numeroOTRr = agendarOTSubtipo.get(0).getIdOtenrr() != null ? agendarOTSubtipo.get(0).getIdOtenrr() : ordenCcmmMglApp.getIdOt().toString();
                        }

                        String numeroOTRrOFSC;
                        if (numeroOTRr == null) {
                            // Si no fue generada la OT en Rr           
                            numeroOTRrOFSC = generarNumeroOtRr(ordenCcmmMglApp, numeroOTRr, false);

                        } else {
                            numeroOTRrOFSC = generarNumeroOtRr(ordenCcmmMglApp, numeroOTRr, true);
                        }

                        String mensajeFinal = "";

                        for (CapacidadAgendaDto capacidadAgendaDto : crearAgendaOtRequest.getCapacidad()) {
                            agendaOtCcmm = new CmtAgendamientoMgl();
                            agendaOtCcmm.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                            ordenCcmmMglApp.setPersonaRecVt(crearAgendaOtRequest.getPersonaRecVt());
                            ordenCcmmMglApp.setTelPerRecVt(crearAgendaOtRequest.getTelPerRecVt());
                            ordenCcmmMglApp.setEmailPerRecVT(crearAgendaOtRequest.getEmailPerRecVT());
                            agendaOtCcmm.setOrdenTrabajo(ordenCcmmMglApp);

                            if (numeroOTRr != null && !numeroOTRr.isEmpty()) {
                                agendaOtCcmm.setIdOtenrr(numeroOTRr);
                            }

                            if (nodoMglApp != null) {
                                agendaOtCcmm.setNodoMgl(nodoMglApp);
                            }

                            if (i == 0) {

                                agendaOtCcmm = agendamientoMglManager.agendar(capacidadAgendaDto,
                                        agendaOtCcmm, numeroOTRrOFSC, null, false);
                                agendaOtCcmm.setIdOtenrr(numeroOTRr);

                                agendamientoMglManager.create(agendaOtCcmm, crearAgendaOtRequest.getUser(), 1);

                                if (cmtOnyxResponseDto != null) {
                                    agendaOtCcmm.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                    agendamientoMglManager.cargarInformacionForEnvioNotificacion(agendaOtCcmm, 1);
                                }

                                String msn = "Se ha creado la agenda:  " + agendaOtCcmm.getOfpsOtId() + "  "
                                        + "  para la ot: " + agendaOtCcmm.getOrdenTrabajo().getIdOt() + " ";
                                mensajeFinal += msn;
                            } else {
                                List<CapacidadAgendaDto> resultado = getCapacidadMulti();
                                if (!resultado.isEmpty()) {

                                    agendaOtCcmm = agendamientoMglManager.agendar(capacidadAgendaDto, agendaOtCcmm, numeroOTRrOFSC, null, false);
                                    agendaOtCcmm.setIdOtenrr(numeroOTRr);

                                    agendamientoMglManager.create(agendaOtCcmm, crearAgendaOtRequest.getUser(), 1);
                                    if (cmtOnyxResponseDto != null) {
                                        agendaOtCcmm.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                        agendamientoMglManager.cargarInformacionForEnvioNotificacion(agendaOtCcmm, 1);
                                    }

                                    String msn = "Se ha creado la agenda:  " + agendaOtCcmm.getOfpsOtId() + "  "
                                            + "  para la ot: " + agendaOtCcmm.getOrdenTrabajo().getIdOt() + " ";
                                    mensajeFinal += msn;
                                }
                            }
                            i++;
                        }
                        ordenTrabajoMglManager.actualizarOtCcmm(ordenCcmmMglApp, crearAgendaOtRequest.getUser(), 1);
                        agendasOtCcmm = agendamientoMglManager.
                                agendasPorOrdenAndSubTipoWorkfoce(ordenCcmmMglApp, subtipoWorfoce);
                        List<AgendasMglDto> agendasDto = retornaListAgendas(agendasOtCcmm, null);
                        responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                        responsesAgendaDto.setMessageType("I");
                        responsesAgendaDto.setMessage(mensajeFinal);
                    }
                }
            } else if (crearAgendaOtRequest.getOtLocation() != null
                    && crearAgendaOtRequest.getOtLocation().equalsIgnoreCase("HHPP")) {
                if (crearAgendaOtRequest.getCapacidad() == null
                        || crearAgendaOtRequest.getCapacidad().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo capacidad es obligatorio");
                } else if (crearAgendaOtRequest.getIdOt() == null
                        || crearAgendaOtRequest.getIdOt().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo idOt es obligatorio");
                } else if (crearAgendaOtRequest.getPersonaRecVt() == null
                        || crearAgendaOtRequest.getPersonaRecVt().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo personaRecVt es obligatorio");
                } else if (crearAgendaOtRequest.getTelPerRecVt() == null
                        || crearAgendaOtRequest.getTelPerRecVt().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo telPerRecVt es obligatorio");
                } else if (crearAgendaOtRequest.getEmailPerRecVT() == null
                        || crearAgendaOtRequest.getEmailPerRecVT().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo emailPerRecVT es obligatorio");
                } else if (crearAgendaOtRequest.getUser() == null
                        || crearAgendaOtRequest.getUser().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo user es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesCrearAgendaOtHhpp(crearAgendaOtRequest)) {
                        consultarNodoOtHhpp(ordenHhppMglApp);

                        if (tipoOtHhppMglApp.getIsMultiagenda().equalsIgnoreCase("N")) {
                            //Es unica Agenda

                            CapacidadAgendaDto capacidadAgendaDto = crearAgendaOtRequest.getCapacidad().get(0);
                            agendaOtHhpp = new MglAgendaDireccion();
                            agendaOtHhpp.setOrdenTrabajo(ordenHhppMglApp);
                            agendaOtHhpp.setPersonaRecVt(crearAgendaOtRequest.getPersonaRecVt());
                            agendaOtHhpp.setTelPerRecVt(crearAgendaOtRequest.getTelPerRecVt());
                            agendaOtHhpp.setEmailPerRecVT(crearAgendaOtRequest.getEmailPerRecVT());
                            agendaOtHhpp.setCmtOnyxResponseDto(cmtOnyxResponseDto);

                            if (nodoMglApp != null) {
                                agendaOtHhpp.setNodoMgl(nodoMglApp);
                            }

                            String numeroOtRr = null;

                            agendaOtHhpp = agendamientoHhppMglManager.agendar(capacidadAgendaDto, agendaOtHhpp, numeroOtRr, null, false);

                            agendamientoHhppMglManager.create(agendaOtHhpp, crearAgendaOtRequest.getUser(), 1);

                            if (cmtOnyxResponseDto != null) {
                                agendaOtHhpp.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                agendamientoHhppMglManager.cargarInformacionForEnvioNotificacion(agendaOtHhpp, 1);
                            }

                            String msn = "Se ha creado la agenda:  " + agendaOtHhpp.getOfpsOtId() + "  "
                                    + "  para la ot: " + agendaOtHhpp.getOrdenTrabajo().getOtHhppId() + " ";

                            ordenHhppMglApp = otHhppMglManager.update(ordenHhppMglApp);
                            agendasOtHhpp = agendamientoHhppMglManager.
                                    buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subtipoOfsc);
                            List<AgendasMglDto> agendasDto = retornaListAgendas(null, agendasOtHhpp);
                            responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                            responsesAgendaDto.setMessageType("I");
                            responsesAgendaDto.setMessage(msn);
                        } else {
                            //Es multi agenda
                            String mensajeFinal = "";
                            int i = 0;
                            String numeroOTRr = null;
                            ParametrosMgl parametroHabilitarOtInRr = parametrosMglManager.
                                    findParametroMgl(Constant.HABILITAR_RR);

                            if (parametroHabilitarOtInRr == null) {
                                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.HABILITAR_RR);
                            }

                            CmtCuentaMatrizMgl cuentaAgrupadora = null;
                            ////Consulta de cuenta matriz agrupadora  si es multiagenda

                            String subTipo = tipoOtHhppMglApp.getSubTipoOrdenOFSC().getCodigoBasica();
                            List<MglAgendaDireccion> agendasCcmm = agendamientoHhppMglManager.
                                    buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subTipo);
                            if ((agendasCcmm == null || agendasCcmm.isEmpty())) {
                                LOGGER.info("Se consulta Ccmm agrupadora ,"
                                        + " es la primera agenda del estado actual de la OT en MGL ");

                                if (ordenHhppMglApp.getDireccionId() != null
                                        && ordenHhppMglApp.getDireccionId().getUbicacion() != null) {
                                    GeograficoPoliticoMgl geograficoPoliticoMgl
                                            = ordenHhppMglApp.getDireccionId().getUbicacion().getGpoIdObj();

                                    if (geograficoPoliticoMgl != null) {

                                        CmtBasicaMgl basicaMgl = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_EDIFICIO_AGRUPADOR_DIRECCIONES_BARRIO);

                                        if (basicaMgl != null) {
                                            CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
                                            List<CmtCuentaMatrizMgl> cuentasAgrupadora
                                                    = cuentaMatrizMglManager.findCuentasMatricesAgrupadorasByCentro(geograficoPoliticoMgl.getGpoId(), basicaMgl);

                                            if (cuentasAgrupadora != null && cuentasAgrupadora.size() == 1) {
                                                //Hay una sola CM agrupadora
                                                cuentaAgrupadora = cuentasAgrupadora.get(0);
                                                codCuentaPar = cuentaAgrupadora.getNumeroCuenta().toString();

                                            } else if (cuentasAgrupadora != null && cuentasAgrupadora.size() > 1) {
                                                //Hay Varias CM agrupadora
                                                for (CmtCuentaMatrizMgl cuentaMatrizMgl : cuentasAgrupadora) {
                                                    String msn = "Existe la cuenta matriz:  " + cuentaMatrizMgl.getCuentaMatrizId() + "  "
                                                            + "  creada como agrupadora para la ciudad:  "
                                                            + "" + cuentaMatrizMgl.getMunicipio().getGpoNombre() + ". ";
                                                    mensajeFinal += msn;
                                                }
                                                throw new ApplicationException(mensajeFinal);
                                            } else {
                                                GeograficoPoliticoManager geograficoPoliticoManager
                                                        = new GeograficoPoliticoManager();
                                                GeograficoPoliticoMgl ciudad = geograficoPoliticoManager.
                                                        findById(geograficoPoliticoMgl.getGeoGpoId());

                                                throw new ApplicationException("No hay creada una "
                                                        + "cuenta matriz de tipo: AGRUPADOR_DIRECCIONES_BARRIO "
                                                        + " para la ciudad: " + ciudad.getGpoNombre() + " ");
                                            }
                                        } else {
                                            throw new ApplicationException("No hay configurado "
                                                    + "un tipo edificio:AGRUPADOR_DIRECCIONES_BARRIO en las tablas basicas ");
                                        }

                                    }

                                }
                                //////Consulta de cuenta matriz agrupadora    

                            } else {
                                //Ya existen agendas tomo la ccmm asociada
                                MglAgendaDireccion agendaDir = agendasCcmm.get(0);
                                cuentaAgrupadora = agendaDir.getCuentaMatrizMgl();
                                if (cuentaAgrupadora != null) {
                                    codCuentaPar = cuentaAgrupadora.getNumeroCuenta().toString();
                                }

                            }

                            if (parametroHabilitarOtInRr.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                if (tipoOtHhppMglApp != null) {
                                    if (tipoOtHhppMglApp.getSubTipoOrdenOFSC() != null
                                            && tipoOtHhppMglApp.getTipoTrabajoRR() != null
                                            && tipoOtHhppMglApp.getEstadoOtRRInicial() != null
                                            && tipoOtHhppMglApp.getEstadoOtRRFinal() != null) {
                                        String subTipoWorForce = tipoOtHhppMglApp.getSubTipoOrdenOFSC().getCodigoBasica();

                                        agendarOTSubtipoHhpp = agendamientoHhppMglManager.buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subTipoWorForce);
                                        if ((agendarOTSubtipoHhpp == null || agendarOTSubtipoHhpp.isEmpty())) {
                                            LOGGER.info("Se creara OT en RR , es la primera agenda del estado actual de la OT en MGL ");
                                            numeroOTRr = ordenTrabajoMglManager.crearOtRRporAgendamientoHhpp(codCuentaPar, tipoOtHhppMglApp, crearAgendaOtRequest.getUser(), ordenHhppMglApp);
                                        }
                                    } else {
                                        LOGGER.info("El estado actual del tipo de OT no tiene los campos de creacion de OT RR configurados , se enviara id de OT MGL");
                                    }

                                } else {
                                    LOGGER.info("El estado actual de la OT no permite agendamiento: el tipo de Ot es null");
                                }
                                //--
                            } else {
                                LOGGER.info("El parámetro " + Constant.ACTIVAR_CREACION_OT_RR + " no se encuentra habilitado. No se crea orden en RR.");
                            }

                            if (agendarOTSubtipoHhpp != null && !agendarOTSubtipoHhpp.isEmpty()) {
                                numeroOTRr = agendarOTSubtipoHhpp.get(0).getIdOtenrr() != null ? agendarOTSubtipoHhpp.get(0).getIdOtenrr() : ordenHhppMglApp.getOtHhppId().toString();
                            }

                            String numeroOTRrOFSC;

                            if (numeroOTRr == null) {
                                // Si no fue generada la OT en Rr           
                                numeroOTRrOFSC = generarNumeroOtRrHhpp(codCuentaPar, numeroOTRr, ordenHhppMglApp, false);
                            } else {
                                numeroOTRrOFSC = generarNumeroOtRrHhpp(codCuentaPar, numeroOTRr, ordenHhppMglApp, true);
                            }

                            for (CapacidadAgendaDto capacidadAgendaDto : crearAgendaOtRequest.getCapacidad()) {

                                agendaOtHhpp = new MglAgendaDireccion();
                                agendaOtHhpp.setOrdenTrabajo(ordenHhppMglApp);
                                agendaOtHhpp.setPersonaRecVt(crearAgendaOtRequest.getPersonaRecVt());
                                agendaOtHhpp.setTelPerRecVt(crearAgendaOtRequest.getTelPerRecVt());
                                agendaOtHhpp.setEmailPerRecVT(crearAgendaOtRequest.getEmailPerRecVT());
                                agendaOtHhpp.setCmtOnyxResponseDto(cmtOnyxResponseDto);

                                if (nodoMglApp != null) {
                                    agendaOtHhpp.setNodoMgl(nodoMglApp);
                                }

                                if (i == 0) {

                                    agendaOtHhpp = agendamientoHhppMglManager.agendar(capacidadAgendaDto, agendaOtHhpp, numeroOTRrOFSC, cuentaAgrupadora, false);
                                    agendaOtHhpp.setIdOtenrr(numeroOTRr);
                                    agendamientoHhppMglManager.create(agendaOtHhpp, crearAgendaOtRequest.getUser(), 1);
                                    if (cmtOnyxResponseDto != null) {
                                        agendaOtHhpp.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                        agendamientoHhppMglManager.cargarInformacionForEnvioNotificacion(agendaOtHhpp, 1);
                                    }

                                    String msn = "Se ha creado la agenda:  " + agendaOtHhpp.getOfpsOtId() + "  "
                                            + "  para la ot: " + agendaOtHhpp.getOrdenTrabajo().getOtHhppId() + " ";
                                    mensajeFinal += msn;
                                } else {
                                    List<CapacidadAgendaDto> resultado = getCapacidadMultiHhpp();
                                    if (!resultado.isEmpty()) {
                                        agendaOtHhpp = agendamientoHhppMglManager.agendar(capacidadAgendaDto, agendaOtHhpp, numeroOTRrOFSC, cuentaAgrupadora, false);
                                        agendaOtHhpp.setIdOtenrr(numeroOTRr);

                                        agendamientoHhppMglManager.create(agendaOtHhpp, crearAgendaOtRequest.getUser(), 1);

                                        if (cmtOnyxResponseDto != null) {
                                            agendaOtHhpp.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                                            agendamientoHhppMglManager.cargarInformacionForEnvioNotificacion(agendaOtHhpp, 1);
                                        }

                                        String msn = "Se ha creado la agenda:  " + agendaOtHhpp.getOfpsOtId() + "  "
                                                + "  para la ot: " + agendaOtHhpp.getOrdenTrabajo().getOtHhppId() + " ";
                                        mensajeFinal += msn;
                                    }

                                }
                                i++;
                            }
                            ordenHhppMglApp = otHhppMglManager.update(ordenHhppMglApp);
                            agendasOtHhpp = agendamientoHhppMglManager.
                                    buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subtipoOfsc);
                            List<AgendasMglDto> agendasDto = retornaListAgendas(null, agendasOtHhpp);
                            responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                            responsesAgendaDto.setMessageType("I");
                            responsesAgendaDto.setMessage(mensajeFinal);

                        }
                    }
                }
            } else if (crearAgendaOtRequest.getOtLocation() == null
                    || crearAgendaOtRequest.getOtLocation().isEmpty()) {
                responsesAgendaDto.setMessageType("E");
                responsesAgendaDto.setMessage("El campo otLocation es obligatorio");
            } else {
                responsesAgendaDto.setMessageType("E");
                responsesAgendaDto.setMessage("El campo otLocation debe tener dos opciones: 'CCMM' o 'HHPP'");
            }
        } catch (ApplicationException | DatatypeConfigurationException ex) {
            responsesAgendaDto.setMessageType("E");
            responsesAgendaDto.setMessage(ex.getMessage());
            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())
                    && agendaOtCcmm != null) {
                rollbackOrdenCcmmInRr(agendaOtCcmm, crearAgendaOtRequest.getUser());
            }
            if ((agendarOTSubtipoHhpp == null || agendarOTSubtipoHhpp.isEmpty())
                    && agendaOtHhpp != null) {
                rollbackOrdenHhppInRr(agendaOtHhpp, crearAgendaOtRequest.getUser());
            }
            return responsesAgendaDto;
        }
        return responsesAgendaDto;

    }

    /**
     * Metodo para reagendar una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param reagendarOtRequest
     * @return AppResponsesAgendaDto
     */
    public AppResponsesAgendaDto reagendarOtCcmmHhppApp(AppReagendarOtRequest reagendarOtRequest)
            throws ApplicationException {

        AppResponsesAgendaDto responsesAgendaDto = new AppResponsesAgendaDto();

        try {
            if (reagendarOtRequest.getOtLocation() != null
                    && reagendarOtRequest.getOtLocation().equalsIgnoreCase("CCMM")) {
                if (reagendarOtRequest.getOfpsOtId()== null || reagendarOtRequest.getOfpsOtId().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo ofpsOtId es obligatorio");
                } else if (reagendarOtRequest.getCapacidad() == null) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo capacidad es obligatorio");
                } else if (reagendarOtRequest.getRazonReagendar() == null
                        || reagendarOtRequest.getRazonReagendar().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo razonReagendar es obligatorio");
                } else if (reagendarOtRequest.getComentarioReagendar() == null
                        || reagendarOtRequest.getComentarioReagendar().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo comentarioReagendar es obligatorio");
                } else if (reagendarOtRequest.getUser() == null
                        || reagendarOtRequest.getUser().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo user es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesReagendarOtCcmm(reagendarOtRequest)) {

                        agendamientoMglManager.reagendar(reagendarOtRequest.getCapacidad(), agendaOtCcmm,
                                reagendarOtRequest.getRazonReagendar(), reagendarOtRequest.getComentarioReagendar(),
                                reagendarOtRequest.getUser(), 1);

                        String msn = "Se ha Reagendado la agenda:  " + agendaOtCcmm.getOfpsOtId() + "  "
                                + "  para la ot: " + agendaOtCcmm.getOrdenTrabajo().getIdOt() + " ";
                        agendasOtCcmm = agendamientoMglManager.
                                agendasPorOrdenAndSubTipoWorkfoce(ordenCcmmMglApp, subtipoWorfoce);
                        List<AgendasMglDto> agendasDto = retornaListAgendas(agendasOtCcmm, null);
                        responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                        responsesAgendaDto.setMessageType("I");
                        responsesAgendaDto.setMessage(msn);
                    }
                }
            } else if (reagendarOtRequest.getOtLocation() != null
                    && reagendarOtRequest.getOtLocation().equalsIgnoreCase("HHPP")) {
                if (reagendarOtRequest.getOfpsOtId()== null || reagendarOtRequest.getOfpsOtId().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo ofpsOtId es obligatorio");
                } else if (reagendarOtRequest.getCapacidad() == null) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo capacidad es obligatorio");
                } else if (reagendarOtRequest.getRazonReagendar() == null
                        || reagendarOtRequest.getRazonReagendar().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo razonReagendar es obligatorio");
                } else if (reagendarOtRequest.getComentarioReagendar() == null
                        || reagendarOtRequest.getComentarioReagendar().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo comentarioReagendar es obligatorio");
                } else if (reagendarOtRequest.getUser() == null
                        || reagendarOtRequest.getUser().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo user es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesReagendarOtHhpp(reagendarOtRequest)) {

                        agendamientoHhppMglManager.reagendar(reagendarOtRequest.getCapacidad(), agendaOtHhpp,
                                reagendarOtRequest.getRazonReagendar(), reagendarOtRequest.getComentarioReagendar(),
                                reagendarOtRequest.getUser(), 1);

                        String msn = "Se ha Reagendado la agenda:  " + agendaOtHhpp.getOfpsOtId() + "  "
                                + "  para la ot: " + agendaOtHhpp.getOrdenTrabajo().getOtHhppId() + " ";
                        agendasOtHhpp = agendamientoHhppMglManager.
                                buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subtipoOfsc);
                        List<AgendasMglDto> agendasDto = retornaListAgendas(null, agendasOtHhpp);
                        responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                        responsesAgendaDto.setMessageType("I");
                        responsesAgendaDto.setMessage(msn);
                    }
                }
            } else if (reagendarOtRequest.getOtLocation() == null
                    || reagendarOtRequest.getOtLocation().isEmpty()) {
                responsesAgendaDto.setMessageType("E");
                responsesAgendaDto.setMessage("El campo otLocation es obligatorio");
            } else {
                responsesAgendaDto.setMessageType("E");
                responsesAgendaDto.setMessage("El campo otLocation debe tener dos opciones: 'CCMM' o 'HHPP'");
            }
        } catch (ApplicationException ex) {
            responsesAgendaDto.setMessageType("E");
            responsesAgendaDto.setMessage(ex.getMessage());
        }
        return responsesAgendaDto;

    }

    /**
     * Metodo para cancelar una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param cancelarAgeRequest
     * @return AppResponsesAgendaDto
     */
    public AppResponsesAgendaDto cancelarAgendaOtCcmmHhppApp(AppCancelarAgendaOtRequest cancelarAgeRequest)
            throws ApplicationException {

        AppResponsesAgendaDto responsesAgendaDto = new AppResponsesAgendaDto();

        try {
            if (cancelarAgeRequest.getOtLocation() != null
                    && cancelarAgeRequest.getOtLocation().equalsIgnoreCase("CCMM")) {
                if (cancelarAgeRequest.getOfpsOtId()== null 
                        || cancelarAgeRequest.getOfpsOtId().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo ofpsOtId es obligatorio");
                } else if (cancelarAgeRequest.getCodigoRazonCancela() == null
                        || cancelarAgeRequest.getCodigoRazonCancela().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo codigoRazonCancela es obligatorio");
                } else if (cancelarAgeRequest.getComentarioCancela() == null
                        || cancelarAgeRequest.getComentarioCancela().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo comentarioCancela es obligatorio");
                } else if (cancelarAgeRequest.getUser() == null
                        || cancelarAgeRequest.getUser().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo user es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesCancelarAgendaOtCcmm(cancelarAgeRequest)) {

                        agendamientoMglManager.cancelar(agendaOtCcmm, cancelarAgeRequest.getCodigoRazonCancela(),
                                cancelarAgeRequest.getComentarioCancela(), cancelarAgeRequest.getUser(), 1);
                        String msn = "Se ha solicitado la cancelación  "
                                + "de la orden  " + agendaOtCcmm.getOfpsOtId();
                        agendasOtCcmm = agendamientoMglManager.
                                agendasPorOrdenAndSubTipoWorkfoce(ordenCcmmMglApp, subtipoWorfoce);
                        List<AgendasMglDto> agendasDto = retornaListAgendas(agendasOtCcmm, null);
                        responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                        responsesAgendaDto.setMessageType("I");
                        responsesAgendaDto.setMessage(msn);
                    }
                }
            } else if (cancelarAgeRequest.getOtLocation() != null
                    && cancelarAgeRequest.getOtLocation().equalsIgnoreCase("HHPP")) {
                if (cancelarAgeRequest.getOfpsOtId()== null) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo ofpsOtId es obligatorio");
                } else if (cancelarAgeRequest.getCodigoRazonCancela() == null
                        || cancelarAgeRequest.getCodigoRazonCancela().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo codigoRazonCancela es obligatorio");
                } else if (cancelarAgeRequest.getComentarioCancela() == null
                        || cancelarAgeRequest.getComentarioCancela().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo comentarioCancela es obligatorio");
                } else if (cancelarAgeRequest.getUser() == null
                        || cancelarAgeRequest.getUser().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo user es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    if (validacionesCancelarAgendaOtHhpp(cancelarAgeRequest)) {

                        agendamientoHhppMglManager.cancelar(agendaOtHhpp, cancelarAgeRequest.getCodigoRazonCancela(),
                                cancelarAgeRequest.getComentarioCancela(), cancelarAgeRequest.getUser(), 1);
                        String msn = "Se ha solicitado la cancelación  "
                                + "de la orden  " + agendaOtHhpp.getOfpsOtId();
                        agendasOtHhpp = agendamientoHhppMglManager.
                                buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subtipoOfsc);
                        List<AgendasMglDto> agendasDto = retornaListAgendas(null, agendasOtHhpp);
                        responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                        responsesAgendaDto.setMessageType("I");
                        responsesAgendaDto.setMessage(msn);
                    }
                }
            } else if (cancelarAgeRequest.getOtLocation() == null
                    || cancelarAgeRequest.getOtLocation().isEmpty()) {
                responsesAgendaDto.setMessageType("E");
                responsesAgendaDto.setMessage("El campo otLocation es obligatorio");
            } else {
                responsesAgendaDto.setMessageType("E");
                responsesAgendaDto.setMessage("El campo otLocation debe tener dos opciones: 'CCMM' o 'HHPP'");
            }
        } catch (ApplicationException ex) {
            responsesAgendaDto.setMessageType("E");
            responsesAgendaDto.setMessage(ex.getMessage());
        }
        return responsesAgendaDto;

    }

    /**
     * Metodo para consultar las agendas by id Enlace desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param consultarAgendasOtRequest
     * @return AppResponsesAgendaDto
     */
    public AppResponsesAgendaDto consultarAgendasOtCcmmHhppApp(AppConsultarAgendasOtRequest consultarAgendasOtRequest)
            throws ApplicationException {

        AppResponsesAgendaDto responsesAgendaDto = new AppResponsesAgendaDto();

        try {
            if (consultarAgendasOtRequest.getOtLocation() != null
                    && consultarAgendasOtRequest.getOtLocation().equalsIgnoreCase("CCMM")) {
                if (consultarAgendasOtRequest.getIdEnlace() == null
                        || consultarAgendasOtRequest.getIdEnlace().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo idEnlace es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio
                    agendasOtCcmm = agendamientoMglManager.
                            agendasByIdEnlace(consultarAgendasOtRequest.getIdEnlace());
                    if (agendasOtCcmm != null && !agendasOtCcmm.isEmpty()) {
                        String msn = "Consulta Exitosa";
                        List<AgendasMglDto> agendasDto = retornaListAgendas(agendasOtCcmm, null);
                        responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                        responsesAgendaDto.setMessageType("I");
                        responsesAgendaDto.setMessage(msn);
                    } else {
                        responsesAgendaDto.setMessageType("E");
                        responsesAgendaDto.setMessage("No existen agendas con "
                                + "el id enlace ingresado: " + consultarAgendasOtRequest.getIdEnlace() + "");
                    }
                }
            } else if (consultarAgendasOtRequest.getOtLocation() != null
                    && consultarAgendasOtRequest.getOtLocation().equalsIgnoreCase("HHPP")) {
                if (consultarAgendasOtRequest.getIdEnlace() == null
                        || consultarAgendasOtRequest.getIdEnlace().isEmpty()) {
                    responsesAgendaDto.setMessageType("E");
                    responsesAgendaDto.setMessage("El campo idEnlace es obligatorio");
                } else {
                    //Todos los campos estan llenos validaciones de negocio                    
                    agendasOtHhpp = agendamientoHhppMglManager.agendasByIdEnlace(consultarAgendasOtRequest.getIdEnlace());
                    if (agendasOtHhpp != null && !agendasOtHhpp.isEmpty()) {
                        String msn = "Consulta Exitosa";
                        List<AgendasMglDto> agendasDto = retornaListAgendas(null, agendasOtHhpp);
                        responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                        responsesAgendaDto.setMessageType("I");
                        responsesAgendaDto.setMessage(msn);
                    } else {
                        responsesAgendaDto.setMessageType("E");
                        responsesAgendaDto.setMessage("No existen agendas con "
                                + "el id enlace ingresado: " + consultarAgendasOtRequest.getIdEnlace() + "");
                    }
                }
            } else if (consultarAgendasOtRequest.getOtLocation() == null
                    || consultarAgendasOtRequest.getOtLocation().isEmpty()) {
                responsesAgendaDto.setMessageType("E");
                responsesAgendaDto.setMessage("El campo otLocation es obligatorio");
            } else {
                responsesAgendaDto.setMessageType("E");
                responsesAgendaDto.setMessage("El campo otLocation debe tener dos opciones: 'CCMM' o 'HHPP'");
            }
        } catch (ApplicationException ex) {
            responsesAgendaDto.setMessageType("E");
            responsesAgendaDto.setMessage(ex.getMessage());
        }
        return responsesAgendaDto;

    }

    public boolean validacionesConsultaCapacityHhpp(AppConsultarCapacityOtRequest request)
            throws ApplicationException {

        //Validacion id orden
        Pattern patIdOtNum = Pattern.compile("[0-9]*");
        Matcher idOtNum = patIdOtNum.matcher(request.getIdOt());

        if (idOtNum.matches()) {
            //valido que la orden exista
            otDirModMglApp = otHhppMglManager.findOtByIdOt(new BigDecimal(request.getIdOt()));
            if (otDirModMglApp == null || otDirModMglApp.getOtHhppId() == null) {
                throw new ApplicationException("No existe orden de trabajo en MGL con el id: "
                        + request.getIdOt() + " ");
            } else {
                tipoOtHhppMglApp = otDirModMglApp.getTipoOtHhppId();
            }
            //valido que la orden exista
        } else {
            throw new ApplicationException(" El id de la orden ingresada: "
                    + "" + request.getIdOt() + ". no es numerico.");
        }
        //Validacion id orden

        //Validacion del estado de la orden para agendar
        if (otDirModMglApp.getTipoOtHhppId().getAgendable().compareTo(BigDecimal.ONE) == 0) {
            LOGGER.info("Se puede agendar");
        } else {
            throw new ApplicationException("La orden No:"
                    + " " + otDirModMglApp.getOtHhppId() + " no se encuentra en un estado agendable");
        }

        if (validarEstadoOtHhpp(otDirModMglApp)) {
            throw new ApplicationException("La orden No:"
                    + " " + otDirModMglApp.getOtHhppId() + " "
                    + "se encuentra en estado: " + otDirModMglApp.getEstadoGeneral().getNombreBasica() + " "
                    + "y no se puede agendar");
        }
        //Fin Validacion del estado de la orden para agendar

        //Inicio validacion agendas cerradas y unica agenda 
        int agendasActivas = agendamientoHhppMglManager.
                buscarAgendasActivas(otDirModMglApp);

        if (agendasActivas == 0) {
            if (validarExistenciaAgendaCerrada()) {
                throw new ApplicationException("No se puede generar una nueva agenda "
                        + "debido a que existe una cerrada exitosamente");
            }
        } else {
            if (tipoOtHhppMglApp.getIsMultiagenda().equalsIgnoreCase("S")) {
                if (validarExistenciaAgendaCerrada()) {
                    throw new ApplicationException("No se puede generar una nueva agenda "
                            + "debido a que existe una cerrada exitosamente");
                }
            } else {
                throw new ApplicationException("No es posible crear una nueva agenda "
                        + "debido a que la OT no es multiagenda");
            }
        }
        //Fin validacion agendas cerradas y unica agenda 

        return true;
    }

    public boolean validacionesConsultaCapacityCcmm(AppConsultarCapacityOtRequest request)
            throws ApplicationException {

        //Validacion id orden
        Pattern patIdOtNum = Pattern.compile("[0-9]*");
        Matcher idOtNum = patIdOtNum.matcher(request.getIdOt());

        if (idOtNum.matches()) {
            //valido que la orden exista
            ordenCcmmMglApp = ordenTrabajoMglManager.
                    findOtById(new BigDecimal(request.getIdOt()));
            if (ordenCcmmMglApp == null || ordenCcmmMglApp.getIdOt() == null) {
                throw new ApplicationException("No existe orden de trabajo en MGL "
                        + "con el id: " + request.getIdOt() + " ingresado");
            }
            //valido que la orden exista
        } else {
            throw new ApplicationException(" El id de la orden ingresada: "
                    + "" + request.getIdOt() + " no es numerico.");
        }
        //Validacion id orden

        //Validacion estado para agendar
        if (!validarReglasAgendaOtCcmm()) {
            throw new ApplicationException("La orden No:"
                    + " " + ordenCcmmMglApp.getIdOt() + " no se encuentra en un estado agendable");
        }
        //Fin Validacion estado para agendar

        if (!validarCreacion()) {
            throw new ApplicationException("Ya se encuentra una ultima agenda "
                    + "cerrada para la orden: " + ordenCcmmMglApp.getIdOt() + " no se pueden crear más agendas ");
        }
        return true;
    }

    public boolean validacionesCrearAgendaOtHhpp(AppCrearAgendaOtRequest request)
            throws ApplicationException, DatatypeConfigurationException {

        //Validacion nombre contacto
        Pattern patNombre = Pattern.compile("([a-zA-ZÃ‘Ã±Ã¡Ã©Ã­Ã³ÃºÃ?Ã‰Ã?Ã“Ãš]| ){0,200}");
        Matcher matNombre = patNombre.matcher(request.getPersonaRecVt());
        if (!matNombre.matches()) {
            throw new ApplicationException(" El nombre de la persona "
                    + "que recibe la visita debe contener solo letras");
        }
        //Fin Validacion nombre contacto

        //Validacion correo contacto
        Pattern patEmail = Pattern.compile("([\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z])?");
        Matcher matEmail = patEmail.matcher(request.getEmailPerRecVT());
        if (!matEmail.matches()) {
            throw new ApplicationException(" El correo de la persona "
                    + "que recibe la visita no tiene el formato adecuado:'sss@dddd.com'");
        }
        //Fin Validacion correo contacto   

        //Validacion telefono contacto
        Pattern patPhone = Pattern.compile("[0-9]*");
        Matcher matPhone = patPhone.matcher(request.getTelPerRecVt());
        if (!matPhone.matches()) {
            throw new ApplicationException(" El telefono e la persona "
                    + "que recibe la visita  debe contener solo numeros");
        }
        //Fin Validacion telefono contacto

        //Validacion id orden
        Pattern patIdOtNum = Pattern.compile("[0-9]*");
        Matcher idOtNum = patIdOtNum.matcher(request.getIdOt());

        if (idOtNum.matches()) {
            //valido que la orden exista
            ordenHhppMglApp = otHhppMglManager.
                    findOtByIdOt(new BigDecimal(request.getIdOt()));
            if (ordenHhppMglApp == null || ordenHhppMglApp.getOtHhppId() == null) {
                throw new ApplicationException("No existe orden de trabajo en MGL"
                        + " con el id: " + request.getIdOt() + " ");
            } else {
                tipoOtHhppMglApp = ordenHhppMglApp.getTipoOtHhppId();
                otDirModMglApp = ordenHhppMglApp;
            }
            //valido que la orden exista
        } else {
            throw new ApplicationException(" El id de la orden ingresada:"
                    + " " + request.getIdOt() + " no es numerico.");
        }
        //Fin Validacion id orden

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
        if (user != null) {
            CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
            //Consulto  el rol para crear agendas 
            CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.
                    consultarOpcionRol(FORMULARIO_AGENDAS, ValidacionUtil.OPC_CREACION);
            if (opcionesRolMgl != null) {
                String rolEdi = opcionesRolMgl.getRol();
                boolean isRolEdi = false;
                UsRolPerfilManager manager = new UsRolPerfilManager();
                UsPerfil perfilUs = user.getPerfil();
                BigDecimal perfil = perfilUs.getIdPerfil();
                List<UsRolPerfil> roles = manager.findByPerfil(perfil);
                if (roles != null && !roles.isEmpty()) {
                    for (UsRolPerfil usRolPerfil : roles) {
                        if (usRolPerfil.getCodRol().equalsIgnoreCase(rolEdi)) {
                            isRolEdi = true;
                        }
                    }
                } else {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene roles asociados en GESTIONNEW");
                }
                if (!isRolEdi) {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene el rol necesario para editar la orden");
                }
            } else {
                throw new ApplicationException("No existe el rol para formulario:"
                        + " " + FORMULARIO_AGENDAS + " y opcion: " + ValidacionUtil.OPC_CREACION + ""
                        + " no existe en base de datos");
            }
        } else {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        //Validacion estado de la orden
        if (validarEstadoOtHhpp(ordenHhppMglApp)) {
            throw new ApplicationException("La orden No:"
                    + " " + ordenHhppMglApp.getOtHhppId() + " "
                    + "se encuentra en estado: " + ordenHhppMglApp.getEstadoGeneral().getNombreBasica() + " "
                    + "y no se puede agendar");
        }
        //Fin Validacion estado de la orden 

        //Validacion ot hija Onix
        if (ordenHhppMglApp.getOnyxOtHija() != null) {
            cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(ordenHhppMglApp.getOnyxOtHija().toString());
        } else {
            OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
            List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxOtHHppById(ordenHhppMglApp.getOtHhppId());
            if (listaOnyx != null && !listaOnyx.isEmpty()) {
                OnyxOtCmDir objOnyx = listaOnyx.get(0);
                cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(objOnyx.getOnyx_Ot_Hija_Dir());
                ordenHhppMglApp.setOnyxOtHija(new BigDecimal(objOnyx.getOnyx_Ot_Hija_Dir()));
            } else {
                String msg = "Debe ir a la pestaña de Onix y diligenciar la OT hija para agendar.";
                throw new ApplicationException(msg);
            }
        }
        //Fin Validacion ot hija Onix    

        //Validacion del capacity enviado
        if (request.getCapacidad().size() > 1
                && tipoOtHhppMglApp.getIsMultiagenda().equalsIgnoreCase("N")) {
            String msg = "Debe selecionar solo una celda para agendar.";
            throw new ApplicationException(msg);
        } else {
            String mensajesValidacionFinal = "";
            int errores = 0;
            for (CapacidadAgendaDto capacidadAgendaDto : request.getCapacidad()) {
                //Valida restricciones de la ccmm
                if (!validarRestriccionesCcmmOtHhpp(capacidadAgendaDto)) {
                    mensajesValidacion = "La cuenta matriz tiene horarios de restriccion o no "
                            + "disponibilidad en la franja: " + capacidadAgendaDto.getTimeSlot() + ".";
                    errores++;
                }
                mensajesValidacionFinal += mensajesValidacion;
            }

            List<CapacidadAgendaDto> capacityAllDay = new ArrayList<>();
            request.getCapacidad().stream().filter((capacidadAgendaDto) -> (capacidadAgendaDto.getTimeSlot() != null
                    && capacidadAgendaDto.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA))).forEachOrdered((capacidadAgendaDto) -> {
                capacityAllDay.add(capacidadAgendaDto);
            });

            if (!capacityAllDay.isEmpty()) {
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                for (CapacidadAgendaDto capacidadAllday : capacityAllDay) {
                    for (CapacidadAgendaDto capacidadSeleccionada : request.getCapacidad()) {
                        if ((capacidadAllday.getDate().equals(capacidadSeleccionada.getDate()))
                                && !capacidadSeleccionada.getTimeSlot().
                                        equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                            mensajesValidacion = "No puede agendar una franja y Durante el Dia para "
                                    + "el dia: " + formateador.format(capacidadAllday.getDate()) + " .";
                            mensajesValidacionFinal += mensajesValidacion;
                            errores++;
                        }
                    }
                }
            }
            if (errores > 0) {
                throw new ApplicationException(mensajesValidacionFinal);
            }
        }
        //Validacion del capacity enviado

        //Validamos que no hayan agendas en la misma fecha y hora
        subtipoOfsc = tipoOtHhppMglApp.getSubTipoOrdenOFSC().getCodigoBasica();

        agendasOtHhpp = agendamientoHhppMglManager.
                buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subtipoOfsc);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (CapacidadAgendaDto capacityEntrada : request.getCapacidad()) {
            String fechaAgenda = df.format(capacityEntrada.getDate());
            if (!validoFechaFranjaAgendasHhpp(fechaAgenda, capacityEntrada.getTimeSlot())) {
                String msg = "Existe ya una agenda para el dia: " + fechaAgenda + " y  "
                        + "la misma franja : " + capacityEntrada.getTimeSlot() + "";
                throw new ApplicationException(msg);
            }
        }
        //Fin Validamos que no hayan agendas en la misma fecha y hora

        //Inicio validacion agendas cerradas y unica agenda 
        int agendasActivas = agendamientoHhppMglManager.
                buscarAgendasActivas(otDirModMglApp);

        if (agendasActivas == 0) {
            if (validarExistenciaAgendaCerrada()) {
                throw new ApplicationException("No se puede generar una nueva agenda "
                        + "debido a que existe una cerrada exitosamente");
            }
        } else {
            if (tipoOtHhppMglApp.getIsMultiagenda().equalsIgnoreCase("S")) {
                if (validarExistenciaAgendaCerrada()) {
                    throw new ApplicationException("No se puede generar una nueva agenda "
                            + "debido a que existe una cerrada exitosamente");
                }
            } else {
                throw new ApplicationException("No es posible crear una nueva agenda "
                        + "debido a que la OT no es multiagenda");
            }
        }
        //Fin validacion agendas cerradas y unica agenda 

        return true;
    }

    public boolean validacionesReagendarOtHhpp(AppReagendarOtRequest request)
            throws ApplicationException {

        //Validacion de la agenda
        //Se realiza la busqueda por ofpsOtId de acuerdo a lo solicitado en el BUG-388866
        agendaOtHhpp = agendamientoHhppMglManager.buscarAgendaPorOtIdMgl(request.getOfpsOtId());

        if (agendaOtHhpp == null || agendaOtHhpp.getOfpsOtId()== null) {
            throw new ApplicationException(" El ofpsOtId de la agenda ingresada: "
                    + "" + request.getIdAgenda() + " no existe en MGL.");
        }
        //Fin Validacion id orden

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
        if (user != null) {
            CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
            //Consulto  el rol para crear agendas 
            CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.
                    consultarOpcionRol(FORMULARIO_AGENDAS, OPCION_REAGENDAR);
            if (opcionesRolMgl != null) {
                String rolEdi = opcionesRolMgl.getRol();
                boolean isRolEdi = false;
                UsRolPerfilManager manager = new UsRolPerfilManager();
                UsPerfil perfilUs = user.getPerfil();
                BigDecimal perfil = perfilUs.getIdPerfil();
                List<UsRolPerfil> roles = manager.findByPerfil(perfil);
                if (roles != null && !roles.isEmpty()) {
                    for (UsRolPerfil usRolPerfil : roles) {
                        if (usRolPerfil.getCodRol().equalsIgnoreCase(rolEdi)) {
                            isRolEdi = true;
                        }
                    }
                } else {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene roles asociados en GESTIONNEW");
                }
                if (!isRolEdi) {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene el rol necesario para editar la orden");
                }
            } else {
                throw new ApplicationException("No existe el rol para formulario:"
                        + " " + FORMULARIO_AGENDAS + " y opcion: " + OPCION_REAGENDAR + ""
                        + " no existe en base de datos");
            }
        } else {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        //Validacion de la agenda    
        String msg;
        if (agendaOtHhpp.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
            msg = "La agenda se encuentra cancelada y no se puede reagendar.";
            throw new ApplicationException(msg);
        } else if (agendaOtHhpp.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
            msg = "La agenda se encuentra cerrada y no se puede reagendar.";
            throw new ApplicationException(msg);
        } else if (agendaOtHhpp.getFechaInivioVt() != null) {
            msg = "La agenda ya inicio visita y no se puede reagendar.";
            throw new ApplicationException(msg);
        }
        //Fin Validacion de la agenda

        //Valida restricciones de la ccmm
        ordenHhppMglApp = agendaOtHhpp.getOrdenTrabajo();
        tipoOtHhppMglApp = ordenHhppMglApp.getTipoOtHhppId();

        if (!validarRestriccionesCcmmOtHhpp(request.getCapacidad())) {
            msg = "La cuenta matriz tiene horarios de restriccion o no "
                    + "disponibilidad en la franja: " + request.getCapacidad().getTimeSlot() + ".";
            throw new ApplicationException(msg);
        }
        //Fin Valida restricciones de la ccmm

        //Validamos que no hayan agendas en la misma fecha y hora
        subtipoOfsc = tipoOtHhppMglApp.getSubTipoOrdenOFSC().getCodigoBasica();

        agendasOtHhpp = agendamientoHhppMglManager.
                buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subtipoOfsc);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fechaAgenda = df.format(request.getCapacidad().getDate());
        if (!validoFechaFranjaAgendasHhpp(fechaAgenda, request.getCapacidad().getTimeSlot())) {
            msg = "Existe ya una agenda para el dia: " + fechaAgenda + " y  "
                    + "la misma franja : " + request.getCapacidad().getTimeSlot() + "";
            throw new ApplicationException(msg);
        }

        //Fin Validamos que no hayan agendas en la misma fecha y hora
        return true;
    }

    public boolean validacionesCancelarAgendaOtHhpp(AppCancelarAgendaOtRequest request)
            throws ApplicationException {

        //Validacion de la agenda
        //Se realiza la busqueda por ofpsOtId de acuerdo a lo solicitado en el BUG-388866
        agendaOtHhpp = agendamientoHhppMglManager.buscarAgendaPorOtIdMgl(request.getOfpsOtId());

        if (agendaOtHhpp == null || agendaOtHhpp.getOfpsOtId()== null) {
            throw new ApplicationException(" El ofpsOtId de la agenda ingresada: "
                    + "" + request.getIdAgenda() + " no existe en MGL.");
        }
        //Fin Validacion id orden

        //Validacion de la agenda    
        if (agendaOtHhpp.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
            mensajesValidacion = "La agenda ya se encuentra cancelada.";
            throw new ApplicationException(mensajesValidacion);

        } else if (agendaOtHhpp.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
            mensajesValidacion = "La agenda se encuentra cerrada y no se puede cancelar.";
            throw new ApplicationException(mensajesValidacion);

        } else if (agendaOtHhpp.getFechaInivioVt() != null) {
            mensajesValidacion = "La agenda ya inicio visita y no se puede cancelar.";
            throw new ApplicationException(mensajesValidacion);
        }
        //Fin Validacion de la agenda

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
        if (user == null) {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        ordenHhppMglApp = agendaOtHhpp.getOrdenTrabajo();
        tipoOtHhppMglApp = ordenHhppMglApp.getTipoOtHhppId();

        subtipoOfsc = tipoOtHhppMglApp.getSubTipoOrdenOFSC().getCodigoBasica();

        return true;
    }

    public boolean validacionesCrearAgendaOtCcmm(AppCrearAgendaOtRequest request)
            throws ApplicationException, DatatypeConfigurationException {

        //Validacion nombre contacto
        Pattern patNombre = Pattern.compile("([a-zA-ZÃ‘Ã±Ã¡Ã©Ã­Ã³ÃºÃ?Ã‰Ã?Ã“Ãš]| ){0,200}");
        Matcher matNombre = patNombre.matcher(request.getPersonaRecVt());
        if (!matNombre.matches()) {
            throw new ApplicationException(" El nombre de la persona "
                    + "que recibe la visita debe contener solo letras");
        }
        //Fin Validacion nombre contacto

        //Validacion correo contacto
        Pattern patEmail = Pattern.compile("([\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z])?");
        Matcher matEmail = patEmail.matcher(request.getEmailPerRecVT());
        if (!matEmail.matches()) {
            throw new ApplicationException(" El correo de la persona "
                    + "que recibe la visita no tiene el formato adecuado:'sss@dddd.com'");
        }
        //Fin Validacion correo contacto   

        //Validacion telefono contacto
        Pattern patPhone = Pattern.compile("[0-9]*");
        Matcher matPhone = patPhone.matcher(request.getTelPerRecVt());
        if (!matPhone.matches()) {
            throw new ApplicationException(" El telefono de la persona "
                    + "que recibe la visita  debe contener solo numeros");
        }
        //Fin Validacion telefono contacto

        //Validacion id orden
        Pattern patIdOtNum = Pattern.compile("[0-9]*");
        Matcher idOtNum = patIdOtNum.matcher(request.getIdOt());

        if (idOtNum.matches()) {
            //valido que la orden exista
            ordenCcmmMglApp = ordenTrabajoMglManager.
                    findOtById(new BigDecimal(request.getIdOt()));
            if (ordenCcmmMglApp == null || ordenCcmmMglApp.getIdOt() == null) {
                throw new ApplicationException("No existe orden de trabajo en MGL"
                        + " con el id: " + request.getIdOt() + " ");
            }
            //valido que la orden exista
        } else {
            throw new ApplicationException(" El id de la orden ingresada: "
                    + "" + request.getIdOt() + " no es numerico.");
        }
        //Fin Validacion id orden

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
        if (user != null) {
            CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
            //Consulto  el rol para crear agendas 
            CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.
                    consultarOpcionRol(FORMULARIO_AGENDAS, ValidacionUtil.OPC_CREACION);
            if (opcionesRolMgl != null) {
                String rolEdi = opcionesRolMgl.getRol();
                boolean isRolEdi = false;
                UsRolPerfilManager manager = new UsRolPerfilManager();
                UsPerfil perfilUs = user.getPerfil();
                BigDecimal perfil = perfilUs.getIdPerfil();
                List<UsRolPerfil> roles = manager.findByPerfil(perfil);
                if (roles != null && !roles.isEmpty()) {
                    for (UsRolPerfil usRolPerfil : roles) {
                        if (usRolPerfil.getCodRol().equalsIgnoreCase(rolEdi)) {
                            isRolEdi = true;
                        }
                    }
                } else {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene roles asociados en GESTIONNEW");
                }
                if (!isRolEdi) {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene el rol necesario para editar la orden");
                }
            } else {
                throw new ApplicationException("No existe el rol para formulario:"
                        + " " + FORMULARIO_AGENDAS + " y opcion: " + ValidacionUtil.OPC_CREACION + ""
                        + " no existe en base de datos");
            }
        } else {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        //Validacion estado de la OT para agendar
        if (!validarReglasAgendaOtCcmm()) {
            throw new ApplicationException("La orden No:"
                    + " " + ordenCcmmMglApp.getIdOt() + " no se encuentra en un estado agendable");
        }
        //Fin Validacion estado de la OT para agendar

        //Validacion ot hija Onix
        if (ordenCcmmMglApp.getOnyxOtHija() != null) {
            cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(ordenCcmmMglApp.getOnyxOtHija().toString());
        } else {
            OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
            List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxOtCmById(ordenCcmmMglApp.getIdOt());
            if (listaOnyx != null && !listaOnyx.isEmpty()) {
                OnyxOtCmDir objOnyx = listaOnyx.get(0);
                cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(objOnyx.getOnyx_Ot_Hija_Cm());
                ordenCcmmMglApp.setOnyxOtHija(new BigDecimal(objOnyx.getOnyx_Ot_Hija_Cm()));
            } else {
                String msg = "Debe ir a la pestaña de Onix y diligenciar la OT hija para agendar.";
                throw new ApplicationException(msg);
            }
        }
        //Fin Validacion ot hija Onix    

        //Validacion del capacity enviado
        String mensajesValidacionFinal = "";
        int errores = 0;
        for (CapacidadAgendaDto capacidadAgendaDto : request.getCapacidad()) {
            //Valida restricciones de la ccmm
            if (!validarRestriccionesCcmmOtCcmm(capacidadAgendaDto)) {
                mensajesValidacion = "La cuenta matriz tiene horarios de restriccion o no "
                        + "disponibilidad en la franja: " + capacidadAgendaDto.getTimeSlot() + ".";
                errores++;
            }
            mensajesValidacionFinal += mensajesValidacion;
        }

        List<CapacidadAgendaDto> capacityAllDay = new ArrayList<>();
        request.getCapacidad().stream().filter((capacidadAgendaDto)
                -> (capacidadAgendaDto.getTimeSlot() != null
                && capacidadAgendaDto.getTimeSlot().
                        equalsIgnoreCase(Constant.DURANTE_EL_DIA))).
                forEachOrdered((capacidadAgendaDto) -> {
                    capacityAllDay.add(capacidadAgendaDto);
                });

        if (!capacityAllDay.isEmpty()) {
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            for (CapacidadAgendaDto capacidadAllday : capacityAllDay) {
                for (CapacidadAgendaDto capacidadSeleccionada : request.getCapacidad()) {
                    if ((capacidadAllday.getDate().equals(capacidadSeleccionada.getDate()))
                            && !capacidadSeleccionada.getTimeSlot().
                                    equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                        mensajesValidacion = "No puede agendar una franja y Durante el Dia para "
                                + "el dia: " + formateador.format(capacidadAllday.getDate()) + " .";
                        mensajesValidacionFinal += mensajesValidacion;
                        errores++;
                    }
                }
            }

        }
        if (errores > 0) {
            throw new ApplicationException(mensajesValidacionFinal);
        }
        //Validacion del capacity enviado

        //Validamos que no hayan agendas en la misma fecha y hora
        CmtEstadoxFlujoMglManager flujoMglManager = new CmtEstadoxFlujoMglManager();

        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = flujoMglManager.
                findByTipoFlujoAndEstadoInt(ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt(),
                        ordenCcmmMglApp.getEstadoInternoObj(),
                        ordenCcmmMglApp.getBasicaIdTecnologia());

        if (cmtEstadoxFlujoMgl != null) {
            if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                subtipoWorfoce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
            }
        }

        agendasOtCcmm = agendamientoMglManager.
                agendasPorOrdenAndSubTipoWorkfoce(ordenCcmmMglApp, subtipoWorfoce);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (CapacidadAgendaDto capacityEntrada : request.getCapacidad()) {
            String fechaAgenda = df.format(capacityEntrada.getDate());
            if (!validoFechaFranjaAgendasCcmm(fechaAgenda, capacityEntrada.getTimeSlot())) {
                String msg = "Existe ya una agenda para el dia: " + fechaAgenda + " y  "
                        + "la misma franja : " + capacityEntrada.getTimeSlot() + "";
                throw new ApplicationException(msg);
            }
        }
        //Fin Validamos que no hayan agendas en la misma fecha y hora

        if (!validarCreacion()) {
            throw new ApplicationException("Ya se encuentra una ultima agenda "
                    + "cerrada para la orden: " + ordenCcmmMglApp.getIdOt() + " no se pueden crear más agendas ");
        }

        return true;
    }

    public boolean validacionesReagendarOtCcmm(AppReagendarOtRequest request)
            throws ApplicationException {

        //Validacion de la agenda
        //Se realiza la busqueda por ofpsOtId de acuerdo a lo solicitado en el BUG-388866
        agendaOtCcmm = agendamientoMglManager.buscarAgendaPorOtIdMgl(request.getOfpsOtId());

        if (agendaOtCcmm == null || agendaOtCcmm.getId() == null) {
            throw new ApplicationException(" El id de la agenda ingresada: "
                    + "" + request.getIdAgenda() + " no existe en MGL.");
        }
        //Fin Validacion id orden

        //Validaciones Usuario
        UsuariosManager usuariosManager = new UsuariosManager();
        Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
        if (user != null) {
            CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
            //Consulto  el rol para crear agendas 
            CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.
                    consultarOpcionRol(FORMULARIO_AGENDAS, OPCION_REAGENDAR);
            if (opcionesRolMgl != null) {
                String rolEdi = opcionesRolMgl.getRol();
                boolean isRolEdi = false;
                UsRolPerfilManager manager = new UsRolPerfilManager();
                UsPerfil perfilUs = user.getPerfil();
                BigDecimal perfil = perfilUs.getIdPerfil();
                List<UsRolPerfil> roles = manager.findByPerfil(perfil);
                if (roles != null && !roles.isEmpty()) {
                    for (UsRolPerfil usRolPerfil : roles) {
                        if (usRolPerfil.getCodRol().equalsIgnoreCase(rolEdi)) {
                            isRolEdi = true;
                        }
                    }
                } else {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene roles asociados en GESTIONNEW");
                }
                if (!isRolEdi) {
                    throw new ApplicationException("El usuario ingresado:"
                            + "" + request.getUser() + " no tiene el rol necesario para editar la orden");
                }
            } else {
                throw new ApplicationException("No existe el rol para formulario:"
                        + " " + FORMULARIO_AGENDAS + " y opcion: " + OPCION_REAGENDAR + ""
                        + " no existe en base de datos");
            }
        } else {
            throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
        }
        //Fin Validaciones Usuario

        //Validacion de agenda
        String msg;
        if (agendaOtCcmm.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
            msg = "La agenda se encuentra cancelada y no se puede reagendar.";
            throw new ApplicationException(msg);
        } else if (agendaOtCcmm.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
            msg = "La agenda se encuentra cerrada y no se puede reagendar.";
            throw new ApplicationException(msg);
        } else if (agendaOtCcmm.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)) {
            msg = "La agenda se encuentra en nodone(cerrada) y no se puede reagendar.";
            throw new ApplicationException(msg);
        } else if (agendaOtCcmm.getFechaInivioVt() != null) {
            msg = "La agenda ya inicio visita y no se puede reagendar.";
            throw new ApplicationException(msg);
        }
        //Fin Validacion de agenda

        ordenCcmmMglApp = agendaOtCcmm.getOrdenTrabajo();

        if (!validarRestriccionesCcmmOtCcmm(request.getCapacidad())) {
            msg = "La cuenta matriz tiene horarios de restriccion o no "
                    + "disponibilidad en la franja: " + request.getCapacidad().getTimeSlot() + ".";
            throw new ApplicationException(msg);
        }

        //Validamos que no hayan agendas en la misma fecha y hora
        CmtEstadoxFlujoMglManager flujoMglManager = new CmtEstadoxFlujoMglManager();

        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = flujoMglManager.
                findByTipoFlujoAndEstadoInt(ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt(),
                        ordenCcmmMglApp.getEstadoInternoObj(),
                        ordenCcmmMglApp.getBasicaIdTecnologia());

        if (cmtEstadoxFlujoMgl != null) {
            if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                subtipoWorfoce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
            }
        }

        agendasOtCcmm = agendamientoMglManager.
                agendasPorOrdenAndSubTipoWorkfoce(ordenCcmmMglApp, subtipoWorfoce);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fechaAgenda = df.format(request.getCapacidad().getDate());
        if (!validoFechaFranjaAgendasCcmm(fechaAgenda, request.getCapacidad().getTimeSlot())) {
            msg = "Existe ya una agenda para el dia: " + fechaAgenda + " y  "
                    + "la misma franja : " + request.getCapacidad().getTimeSlot() + "";
            throw new ApplicationException(msg);
        }
        //Fin Validamos que no hayan agendas en la misma fecha y hora  
        return true;
    }

    public boolean validacionesCancelarAgendaOtCcmm(AppCancelarAgendaOtRequest request)
            throws ApplicationException {

        //Validacion de la agenda
        //Se realiza la busqueda por ofpsOtId de acuerdo a lo solicitado en el BUG-388866
        agendaOtCcmm = agendamientoMglManager.buscarAgendaPorOtIdMgl(request.getOfpsOtId());

        if (agendaOtCcmm == null || agendaOtCcmm.getId() == null) {
            throw new ApplicationException(" El id de la agenda ingresada: "
                    + "" + request.getIdAgenda() + " no existe en MGL.");
        }
        //Fin Validacion id orden

        //Validacion de agenda
        String msg;
        Date fechaHoy = new Date();

        if (agendaOtCcmm.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
            msg = "La agenda ya se encuentra cancelada.";
            throw new ApplicationException(msg);
        } else if (agendaOtCcmm.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
            msg = "La agenda se encuentra cerrada y no se puede cancelar.";
            throw new ApplicationException(msg);
        } else if (agendaOtCcmm.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)) {
            msg = "La agenda se encuentra nodone(cerrada) y no se puede cancelar.";
            throw new ApplicationException(msg);
        } else if (agendaOtCcmm.getFechaInivioVt() != null) {
            msg = "La agenda ya inicio visita y no se puede cancelar.";
            throw new ApplicationException(msg);

        } else {

            //Validaciones Usuario
            UsuariosManager usuariosManager = new UsuariosManager();
            Usuarios user = usuariosManager.findUsuarioByUsuario(request.getUser());
            if (user == null) {
                throw new ApplicationException("Usuario: " + request.getUser() + " no existe en Base de datos");
            }
            //Fin Validaciones Usuario

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String fechaAgenda = format.format(agendaOtCcmm.getFechaAgenda());

            //Si la agenda tiene una fecha de reagenda toma esa fecha para hacer validacion de cancelar
            if (agendaOtCcmm.getFechaReagenda() != null) {
                fechaAgenda = format.format(agendaOtCcmm.getFechaReagenda());
            }

            String fechaActual = format.format(fechaHoy);
            Date fechaDate1;
            Date fechaDate2;
            boolean valida;
            try {
                fechaDate1 = format.parse(fechaAgenda);
                fechaDate2 = format.parse(fechaActual);
                if (fechaDate1.before(fechaDate2)) {
                    LOGGER.info("La Fecha de la agenda  es menor a la de hoy ");
                    valida = false;
                } else if (fechaDate2.before(fechaDate1)) {
                    LOGGER.info("La Fecha de la agenda es Mayor a la de hoy ");
                    valida = true;
                } else {
                    LOGGER.info("Las Fechas Son iguales ");
                    valida = true;
                }
                if (valida) {
                    //Valido agendas posteriores
                    if (validarAgendasPosteriores(agendaOtCcmm)) {
                        throw new ApplicationException(mensajesValidacion);
                    } else {
                        LOGGER.info("Se puede cancelar la agenda.");
                    }
                } else {
                    mensajesValidacion = "No se puede cancelar una agenda con fecha menor a la  del dia de hoy.";
                    throw new ApplicationException(mensajesValidacion);
                }
                //Fin Validacion de agenda

                ordenCcmmMglApp = agendaOtCcmm.getOrdenTrabajo();

                //Validamos que no hayan agendas en la misma fecha y hora
                CmtEstadoxFlujoMglManager flujoMglManager = new CmtEstadoxFlujoMglManager();

                CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = flujoMglManager.
                        findByTipoFlujoAndEstadoInt(ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt(),
                                ordenCcmmMglApp.getEstadoInternoObj(),
                                ordenCcmmMglApp.getBasicaIdTecnologia());

                if (cmtEstadoxFlujoMgl != null) {
                    if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                        subtipoWorfoce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                    }
                }

            } catch (ParseException | ApplicationException e) {
                String ms = "Se genera error  cancelando la agenda OtCMHhppMglManager: cancelarAgenda() ..." + e.getMessage();
                throw new ApplicationException(ms);
            }
        }
        return true;
    }

    /**
     * Valida si no existe restriccion de horario para agendar la OT en el caso
     * de que el Hhpp se encuentre dentro de una CM
     *
     * @param capacidad capacidad obtenida de WorkForce para validar
     * restricciones
     * @author Victor Bocanegra
     * @throws ApplicationException Excepcion lanzada en la consulta
     */
    private boolean validarRestriccionesCcmmOtCcmm(CapacidadAgendaDto capacidad) {
        try {
            CmtHorarioRestriccionMglManager restriccionMglManager = new CmtHorarioRestriccionMglManager();

            if (capacidad.getTimeSlot() != null && capacidad.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                if (ordenCcmmMglApp.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = restriccionMglManager.findByCuentaMatrizId(ordenCcmmMglApp.getCmObj().getCuentaMatrizId());

                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(capacidad.getDate());
                    int dia = cal.get(Calendar.DAY_OF_WEEK);
                    String diaFecha = devuelveDia(dia);

                    List<String> diasCompara;

                    if (!restriccionMgls.isEmpty()) {
                        for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                            if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                    equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                    || horarioRestriccionMgl.getTipoRestriccion().toString().
                                            equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                        horarioRestriccionMgl.getDiaFin().getDia());

                                for (String diaComparar : diasCompara) {

                                    if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                        return false;
                                    }
                                }

                            }
                        }
                    }
                }
                return true;
            } else if (capacidad.getTimeSlot() != null) {
                if (ordenCcmmMglApp.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = restriccionMglManager.findByCuentaMatrizId(ordenCcmmMglApp.getCmObj().getCuentaMatrizId());

                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(capacidad.getDate());
                    int dia = cal.get(Calendar.DAY_OF_WEEK);
                    String diaFecha = devuelveDia(dia);

                    String[] partsHorIniFi = capacidad.getTimeSlot().split("-");
                    int parteHorIni = Integer.parseInt(partsHorIniFi[0]);

                    List<String> diasCompara;

                    if (!restriccionMgls.isEmpty()) {
                        for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                            if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                    equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                    || horarioRestriccionMgl.getTipoRestriccion().toString().
                                            equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                        horarioRestriccionMgl.getDiaFin().getDia());

                                for (String diaComparar : diasCompara) {

                                    if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                        String hIni = horarioRestriccionMgl.getHoraInicio().substring(0, 2);
                                        String hFin = horarioRestriccionMgl.getHoraFin().substring(0, 2);
                                        int resHorIni = Integer.parseInt(hIni);
                                        int resHorFin = Integer.parseInt(hFin);
                                        for (int i = resHorIni; i < resHorFin; i++) {
                                            if (i == parteHorIni) {
                                                return false;
                                            }
                                        }

                                    }

                                }

                            }
                        }
                    }
                }
                return true;
            } else if (capacidad.getHoraInicio() != null) {
                if (ordenCcmmMglApp.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = restriccionMglManager.findByCuentaMatrizId(ordenCcmmMglApp.getCmObj().getCuentaMatrizId());

                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(capacidad.getDate());
                    int dia = cal.get(Calendar.DAY_OF_WEEK);
                    String diaFecha = devuelveDia(dia);

                    String[] partsHorIniFi = null;
                    if (capacidad.getHoraInicio().contains(":")) {
                        partsHorIniFi = capacidad.getHoraInicio().split(":");
                    }

                    int parteHorIni = Integer.parseInt(partsHorIniFi[0]);

                    List<String> diasCompara;

                    if (!restriccionMgls.isEmpty()) {
                        for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                            if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                    equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                    || horarioRestriccionMgl.getTipoRestriccion().toString().
                                            equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                        horarioRestriccionMgl.getDiaFin().getDia());

                                for (String diaComparar : diasCompara) {

                                    if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                        String hIni = horarioRestriccionMgl.getHoraInicio().substring(0, 2);
                                        String hFin = horarioRestriccionMgl.getHoraFin().substring(0, 2);
                                        int resHorIni = Integer.parseInt(hIni);
                                        int resHorFin = Integer.parseInt(hFin);
                                        for (int i = resHorIni; i < resHorFin; i++) {
                                            if (i == parteHorIni) {
                                                return false;
                                            }
                                        }

                                    }

                                }

                            }
                        }
                    }
                }
                return true;
            }
        } catch (ApplicationException | NumberFormatException e) {
            return false;
        }
        return false;
    }

    public String devuelveDia(int dia) {

        String diaSemana = "";
        switch (dia) {
            case 1:
                diaSemana = "DOMINGO";
                break;
            case 2:
                diaSemana = "LUNES";
                break;
            case 3:
                diaSemana = "MARTES";
                break;
            case 4:
                diaSemana = "MIERCOLES";
                break;
            case 5:
                diaSemana = "JUEVES";
                break;
            case 6:
                diaSemana = "VIERNES";
                break;
            case 7:
                diaSemana = "SABADO";
                break;
            default:
                break;
        }

        return diaSemana;
    }

    public List<String> diasComparar(String diaIni, String diaFin) {

        List<String> result = new ArrayList();

        if (diaIni.equalsIgnoreCase(diaFin)) {
            result.add(diaIni);
        } else if (diaIni.equalsIgnoreCase("LUNES")) {
            if (diaFin.equalsIgnoreCase("MARTES")) {
                result.add("LUNES");
                result.add("MARTES");
            } else if (diaFin.equalsIgnoreCase("MIERCOLES")) {
                result.add("LUNES");
                result.add("MARTES");
                result.add("MIERCOLES");
            } else if (diaFin.equalsIgnoreCase("JUEVES")) {
                result.add("LUNES");
                result.add("MARTES");
                result.add("MIERCOLES");
                result.add("JUEVES");
            } else if (diaFin.equalsIgnoreCase("VIERNES")) {
                result.add("LUNES");
                result.add("MARTES");
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
            } else if (diaFin.equalsIgnoreCase("SABADO")) {
                result.add("LUNES");
                result.add("MARTES");
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
                result.add("SABADO");
            } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                result.add("LUNES");
                result.add("MARTES");
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
                result.add("SABADO");
                result.add("DOMINGO");
            }
        } else if (diaIni.equalsIgnoreCase("MARTES")) {
            if (diaFin.equalsIgnoreCase("MIERCOLES")) {
                result.add("MARTES");
                result.add("MIERCOLES");
            } else if (diaFin.equalsIgnoreCase("JUEVES")) {
                result.add("MARTES");
                result.add("MIERCOLES");
                result.add("JUEVES");
            } else if (diaFin.equalsIgnoreCase("VIERNES")) {
                result.add("MARTES");
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
            } else if (diaFin.equalsIgnoreCase("SABADO")) {
                result.add("MARTES");
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
                result.add("SABADO");
            } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                result.add("MARTES");
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
                result.add("SABADO");
                result.add("DOMINGO");
            }
        } else if (diaIni.equalsIgnoreCase("MIERCOLES")) {
            if (diaFin.equalsIgnoreCase("JUEVES")) {
                result.add("MIERCOLES");
                result.add("JUEVES");
            } else if (diaFin.equalsIgnoreCase("VIERNES")) {
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
            } else if (diaFin.equalsIgnoreCase("SABADO")) {
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
                result.add("SABADO");
            } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                result.add("MIERCOLES");
                result.add("JUEVES");
                result.add("VIERNES");
                result.add("SABADO");
                result.add("DOMINGO");
            }
        } else if (diaIni.equalsIgnoreCase("JUEVES")) {
            if (diaFin.equalsIgnoreCase("VIERNES")) {
                result.add("JUEVES");
                result.add("VIERNES");
            } else if (diaFin.equalsIgnoreCase("SABADO")) {
                result.add("JUEVES");
                result.add("VIERNES");
                result.add("SABADO");
            } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                result.add("JUEVES");
                result.add("VIERNES");
                result.add("SABADO");
                result.add("DOMINGO");
            }
        } else if (diaIni.equalsIgnoreCase("VIERNES")) {
            if (diaFin.equalsIgnoreCase("SABADO")) {
                result.add("VIERNES");
                result.add("SABADO");
            } else if (diaFin.equalsIgnoreCase("DOMINGO")) {
                result.add("VIERNES");
                result.add("SABADO");
                result.add("DOMINGO");
            }
        } else if (diaIni.equalsIgnoreCase("SABADO")) {
            if (diaFin.equalsIgnoreCase("DOMINGO")) {
                result.add("SABADO");
                result.add("DOMINGO");
            }
        } else {
            result.add("DOMINGO");
        }
        return result;
    }

    public boolean tieneAgendasPendientes(OtHhppMgl ot) throws ApplicationException {

        try {
            TipoOtHhppMgl tipoOtHhppMgl = ot.getTipoOtHhppId();
            String subTipoWorForce = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();

            //Consultamos si la orden tiene agendas pendientes
            AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
            List<MglAgendaDireccion> agendas = manager.
                    buscarAgendasIniciadasByOtAndSubtipopOfsc(ot, subTipoWorForce);
            return !agendas.isEmpty();

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el mÃ©todo '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + e.getMessage(), e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el mÃ©todo '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + e.getMessage(), e);
        }
    }

    public boolean tieneAgendasSuspendidas(OtHhppMgl ot) throws ApplicationException {

        try {
            TipoOtHhppMgl tipoOtHhppMgl = ot.getTipoOtHhppId();
            String subTipoWorForce = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();

            //Consultamos si la orden tiene agendas suspendidas
            AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
            List<MglAgendaDireccion> agendas = manager.
                    buscarAgendasPendientesByOtAndSubtipopOfscUnicaAgenda(ot, subTipoWorForce);
            return !agendas.isEmpty();

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el mÃ©todo '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + e.getMessage(), e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el mÃ©todo '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + e.getMessage(), e);
        }
    }

    public OtHhppMgl createIndependentOt(OtHhppMgl otToCreate, String usuario,
            int perfil, List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {

        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        OtHhppTecnologiaMglManager otHhppTecnologiaMglManager
                = new OtHhppTecnologiaMglManager();

        List<OtHhppTecnologiaMgl> tecList = new ArrayList<>();
        if (tecnologiaList != null && !tecnologiaList.isEmpty()) {
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
    }

    /**
     * FunciÃ³n utilizada para obtener el valor del si la tipo de ot es
     * agendable
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

    public boolean saveInfoNotes(OtHhppMgl otHhppMgl, CmtOrdenTrabajoMgl ot, String user) throws ApplicationException {

        boolean respuest = false;
        int control = 0;
        if (otHhppMgl != null && notasHhpp != null && !notasHhpp.isEmpty()) {
            CmtNotasOtHhppMglManager notasOtHhppMglManager = new CmtNotasOtHhppMglManager();
            for (CmtNotasOtHhppMgl notasHhppMgl : notasHhpp) {
                notasHhppMgl.setOtHhppId(otHhppMgl);
                notasHhppMgl = notasOtHhppMglManager.create(notasHhppMgl, user, 1);
                if (notasHhppMgl.getNotasId() != null) {
                    control++;
                }
            }
            if (control != 0) {
                respuest = true;
            }
        }

        if (ot != null && notasOtCcmm != null && !notasOtCcmm.isEmpty()) {
            CmtNotaOtMglManager manager = new CmtNotaOtMglManager();
            for (CmtNotaOtMgl notasOtMgl : notasOtCcmm) {
                notasOtMgl.setOrdenTrabajoObj(ot);
                notasOtMgl = manager.crear(notasOtMgl, user, 1);
                if (notasOtMgl.getNotaOtId() != null) {
                    control++;
                }
            }
            if (control != 0) {
                respuest = true;
            }
        }

        return respuest;
    }

    public boolean saveInfoOnix(String numOtHija,
            OtHhppMgl otHhppMgl, CmtOrdenTrabajoMgl ot, String user) throws ApplicationException {

        boolean respuest = false;

        try {

            cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(numOtHija);

            if (cmtOnyxResponseDto != null) {
                // persistencia de los campos Onyx en BD
                if (otHhppMgl != null) {
                    onyxOtCmDir = setFieldsOnyxToEntityHHPP(cmtOnyxResponseDto, otHhppMgl, user);
                    OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
                    // Primera ot guardo el registro en la tabla de onix
                    onyxOtCmDir = onyxOtCmDirManager.create(onyxOtCmDir);
                    if (onyxOtCmDir != null && onyxOtCmDir.getId_Onyx() != null) {
                        respuest = true;
                        try {
                            MERNotifyResponseType responseType = otHhppMglManager.notificarOrdenOnix(otHhppMgl, numOtHija);
                            if (responseType != null && responseType.getIcodOperacion().equalsIgnoreCase("1")) {
                                String msnError = "Operacion Exitosa: " + responseType.getVchDescOperacion() + "";
                                LOGGER.info(msnError);
                            }
                        }catch(Exception e){
                            LOGGER.info("NotificarOrdenOnix no disponible: "+e);
                        }
                    }
                }
                if (ot != null) {
                    onyxOtCmDir = setFieldsOnyxToEntityCCMM(cmtOnyxResponseDto, ot, user);
                    OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
                    // Primera ot guardo el registro en la tabla de onix
                    onyxOtCmDir = onyxOtCmDirManager.create(onyxOtCmDir);
                    if (onyxOtCmDir != null && onyxOtCmDir.getId_Onyx() != null) {
                        respuest = true;
                        try {
                            MERNotifyResponseType responseType = ordenTrabajoMglManager.notificarOrdenOnix(ot, numOtHija);
                            if (responseType != null && responseType.getIcodOperacion().equalsIgnoreCase("1")) {
                                String msnError = "Operacion Exitosa: " + responseType.getVchDescOperacion() + "";
                                LOGGER.info(msnError);
                            }
                        } catch (Exception e) {
                            LOGGER.info("NotificarOrdenOnix no disponible: " + e);
                        }
                    }
                }
            }
        } catch (ParseException | ApplicationException | DatatypeConfigurationException ex) {
            throw new ApplicationException("Error en saveInfoOnix: " + ex.getMessage(), ex);
        }
        return respuest;
    }

    public OnyxOtCmDir setFieldsOnyxToEntityHHPP(CmtOnyxResponseDto cmtOnyxResponseDto,
            OtHhppMgl otHhppMgl, String user) throws ParseException {

        if (cmtOnyxResponseDto != null) {
            onyxOtCmDir = new OnyxOtCmDir();
            onyxOtCmDir.setOt_Id_Cm(null);
            onyxOtCmDir.setOt_Direccion_Id(otHhppMgl);
            onyxOtCmDir.setOnyx_Ot_Hija_Cm(null);
            onyxOtCmDir.setOnyx_Ot_Hija_Dir(String.valueOf(cmtOnyxResponseDto.getOTH()));
            onyxOtCmDir.setOnyx_Ot_Padre_Cm(null);
            onyxOtCmDir.setOnyx_Ot_Padre_Dir(String.valueOf(cmtOnyxResponseDto.getOTP()));
            onyxOtCmDir.setNit_Cliente_Onyx(cmtOnyxResponseDto.getNIT_Cliente());
            onyxOtCmDir.setNombre_Cliente_Onyx(cmtOnyxResponseDto.getNombre());
            onyxOtCmDir.setNombre_Ot_Hija_Onyx(cmtOnyxResponseDto.getNombre_OT_Hija());
            if (cmtOnyxResponseDto.getFechaCreacionOTH() != null && !cmtOnyxResponseDto.getFechaCreacionOTH().isEmpty()) {
                SimpleDateFormat formatterFecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha_Creacion_Ot_Hija_Onyx = formatterFecha_Creacion_Ot_Hija_Onyx.parse(cmtOnyxResponseDto.getFechaCreacionOTH());
                DateFormat  formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                onyxOtCmDir.setFecha_Creacion_Ot_Hija_Onyx(dateCreate);      
            } else {
                onyxOtCmDir.setFecha_Creacion_Ot_Hija_Onyx(null);
            }
            if (cmtOnyxResponseDto.getFechaCreacionOTP() != null && !cmtOnyxResponseDto.getFechaCreacionOTP().isEmpty()) {
                SimpleDateFormat formatterFecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha_Creacion_Ot_Hija_Onyx = formatterFecha_Creacion_Ot_Hija_Onyx.parse(cmtOnyxResponseDto.getFechaCreacionOTP());
                DateFormat  formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                onyxOtCmDir.setFecha_Creacion_Ot_Padre_Onyx(dateCreate);
            } else {
                onyxOtCmDir.setFecha_Creacion_Ot_Padre_Onyx(null);
            }

            onyxOtCmDir.setContacto_Tecnico_Ot_Padre_Onyx(cmtOnyxResponseDto.getContactoTecnicoOTP());
            onyxOtCmDir.setTelefono_Tecnico_Ot_Padre_Onyx(cmtOnyxResponseDto.getTelefonoContacto());
            onyxOtCmDir.setDescripcion_Onyx(cmtOnyxResponseDto.getDescripcion());
            onyxOtCmDir.setDireccion_Onyx(cmtOnyxResponseDto.getDireccion());

            onyxOtCmDir.setSegmento_Onyx(cmtOnyxResponseDto.getSegmento());
            onyxOtCmDir.setTipo_Servicio_Onyx(cmtOnyxResponseDto.getTipoServicio());
            onyxOtCmDir.setRecurrente_Mensual_Onyx(cmtOnyxResponseDto.getRecurrenteMensual().toString());
            onyxOtCmDir.setCodigo_Servicio_Onyx(cmtOnyxResponseDto.getCodigoServicio());

            onyxOtCmDir.setVendedor_Onyx(cmtOnyxResponseDto.getVendedor());
            onyxOtCmDir.setTelefono_Vendedor_Onyx(cmtOnyxResponseDto.getTelefono());
            onyxOtCmDir.setServicios_Onyx(cmtOnyxResponseDto.getServicios());
            onyxOtCmDir.setEstado_Ot_Hija_Onyx_Cm(null);

            onyxOtCmDir.setEstado_Ot_Padre_Onyx_Cm(null);
            onyxOtCmDir.setEstado_Ot_Hija_Onyx_Dir(cmtOnyxResponseDto.getEstadoOTH());
            onyxOtCmDir.setEstado_Ot_Padre_Onyx_Dir(cmtOnyxResponseDto.getEstadoOTP());
            if (cmtOnyxResponseDto.getFechaCompromisoOTP() != null && !cmtOnyxResponseDto.getFechaCompromisoOTP().isEmpty()) {
                SimpleDateFormat inputFormatFechaComp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date fecha_Creacion_Ot_Hija_Onyx = inputFormatFechaComp.parse(cmtOnyxResponseDto.getFechaCompromisoOTP());
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                onyxOtCmDir.setFecha_Compromiso_Ot_Padre_Onyx(dateCreate);
            } else {
                onyxOtCmDir.setFecha_Compromiso_Ot_Padre_Onyx(null);
            }

            onyxOtCmDir.setOt_Padre_Resolucion_1_Onyx(cmtOnyxResponseDto.getCodResolucion1OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_2_Onyx(cmtOnyxResponseDto.getCodResolucion2OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_3_Onyx(cmtOnyxResponseDto.getCodResolucion3OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_4_Onyx(cmtOnyxResponseDto.getCodResolucion4OTP());
            onyxOtCmDir.setOt_Hija_Resolucion_1_Onyx(cmtOnyxResponseDto.getCodResolucion1OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_2_Onyx(cmtOnyxResponseDto.getCodResolucion2OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_3_Onyx(cmtOnyxResponseDto.getCodResolucion3OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_4_Onyx(cmtOnyxResponseDto.getCodResolucion4OTH());

            onyxOtCmDir.setEstadoRegistro(1);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date dateCreate = formato.parse(formato.format(new Date()));
            onyxOtCmDir.setFechaCreacion(dateCreate);
            Date dateUpdate = formato.parse(formato.format(new Date()));
            onyxOtCmDir.setFechaEdicion(dateUpdate);
            onyxOtCmDir.setUsuarioCreacion(user);
            onyxOtCmDir.setUsuarioEdicion(user);
        }
        return onyxOtCmDir;
    }

    public OnyxOtCmDir setFieldsOnyxToEntityCCMM(CmtOnyxResponseDto cmtOnyxResponseDto,
            CmtOrdenTrabajoMgl orden, String user) throws ParseException {

        if (cmtOnyxResponseDto != null) {
            onyxOtCmDir = new OnyxOtCmDir();
            onyxOtCmDir.setOt_Id_Cm(orden);
            onyxOtCmDir.setOnyx_Ot_Hija_Cm(String.valueOf(cmtOnyxResponseDto.getOTH()));
            onyxOtCmDir.setOnyx_Ot_Hija_Dir(null);
            onyxOtCmDir.setOnyx_Ot_Padre_Cm(String.valueOf(cmtOnyxResponseDto.getOTP()));
            onyxOtCmDir.setOnyx_Ot_Padre_Dir(null);
            onyxOtCmDir.setNit_Cliente_Onyx(cmtOnyxResponseDto.getNIT_Cliente());
            onyxOtCmDir.setNombre_Cliente_Onyx(cmtOnyxResponseDto.getNombre());
            onyxOtCmDir.setNombre_Ot_Hija_Onyx(cmtOnyxResponseDto.getNombre_OT_Hija());
            if (cmtOnyxResponseDto.getFechaCreacionOTH() != null && !cmtOnyxResponseDto.getFechaCreacionOTH().isEmpty()) {
                SimpleDateFormat formatterFecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha_Creacion_Ot_Hija_Onyx = formatterFecha_Creacion_Ot_Hija_Onyx.parse(cmtOnyxResponseDto.getFechaCreacionOTH());
                DateFormat  formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                onyxOtCmDir.setFecha_Creacion_Ot_Hija_Onyx(dateCreate);
                
            } else {
                onyxOtCmDir.setFecha_Creacion_Ot_Hija_Onyx(null);
            }
            if (cmtOnyxResponseDto.getFechaCreacionOTP() != null && !cmtOnyxResponseDto.getFechaCreacionOTP().isEmpty()) {
                SimpleDateFormat formatterFecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha_Creacion_Ot_Hija_Onyx = formatterFecha_Creacion_Ot_Hija_Onyx.parse(cmtOnyxResponseDto.getFechaCreacionOTP());
                DateFormat  formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                onyxOtCmDir.setFecha_Creacion_Ot_Padre_Onyx(dateCreate);
            } else {
                onyxOtCmDir.setFecha_Creacion_Ot_Padre_Onyx(null);
            }
            onyxOtCmDir.setContacto_Tecnico_Ot_Padre_Onyx(cmtOnyxResponseDto.getContactoTecnicoOTP());
            onyxOtCmDir.setTelefono_Tecnico_Ot_Padre_Onyx(cmtOnyxResponseDto.getTelefonoContacto());
            onyxOtCmDir.setDescripcion_Onyx(cmtOnyxResponseDto.getDescripcion());
            onyxOtCmDir.setDireccion_Onyx(cmtOnyxResponseDto.getDireccion());

            onyxOtCmDir.setSegmento_Onyx(cmtOnyxResponseDto.getSegmento());
            onyxOtCmDir.setTipo_Servicio_Onyx(cmtOnyxResponseDto.getTipoServicio());
            onyxOtCmDir.setRecurrente_Mensual_Onyx(cmtOnyxResponseDto.getRecurrenteMensual().toString());
            onyxOtCmDir.setCodigo_Servicio_Onyx(cmtOnyxResponseDto.getCodigoServicio());

            onyxOtCmDir.setVendedor_Onyx(cmtOnyxResponseDto.getVendedor());
            onyxOtCmDir.setTelefono_Vendedor_Onyx(cmtOnyxResponseDto.getTelefono());
            onyxOtCmDir.setServicios_Onyx(cmtOnyxResponseDto.getServicios());
            onyxOtCmDir.setEstado_Ot_Hija_Onyx_Cm(cmtOnyxResponseDto.getEstadoOTH());

            onyxOtCmDir.setEstado_Ot_Padre_Onyx_Cm(cmtOnyxResponseDto.getEstadoOTP());
            onyxOtCmDir.setEstado_Ot_Hija_Onyx_Dir(null);
            onyxOtCmDir.setEstado_Ot_Padre_Onyx_Dir(null);
            if (cmtOnyxResponseDto.getFechaCompromisoOTP() != null && !cmtOnyxResponseDto.getFechaCompromisoOTP().isEmpty()) {
                    SimpleDateFormat inputFormatFechaComp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date fecha_Creacion_Ot_Hija_Onyx = inputFormatFechaComp.parse(cmtOnyxResponseDto.getFechaCompromisoOTP());
                    DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                    onyxOtCmDir.setFecha_Compromiso_Ot_Padre_Onyx(dateCreate);
            } else {
                onyxOtCmDir.setFecha_Compromiso_Ot_Padre_Onyx(null);
            }

            onyxOtCmDir.setOt_Padre_Resolucion_1_Onyx(cmtOnyxResponseDto.getCodResolucion1OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_2_Onyx(cmtOnyxResponseDto.getCodResolucion2OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_3_Onyx(cmtOnyxResponseDto.getCodResolucion3OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_4_Onyx(cmtOnyxResponseDto.getCodResolucion4OTP());
            onyxOtCmDir.setOt_Hija_Resolucion_1_Onyx(cmtOnyxResponseDto.getCodResolucion1OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_2_Onyx(cmtOnyxResponseDto.getCodResolucion2OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_3_Onyx(cmtOnyxResponseDto.getCodResolucion3OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_4_Onyx(cmtOnyxResponseDto.getCodResolucion4OTH());

            onyxOtCmDir.setEstadoRegistro(1);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date dateCreate = formato.parse(formato.format(new Date()));
            onyxOtCmDir.setFechaCreacion(dateCreate);
            Date dateUpdate = formato.parse(formato.format(new Date()));
            onyxOtCmDir.setFechaEdicion(dateUpdate);
            onyxOtCmDir.setUsuarioCreacion(user);
            onyxOtCmDir.setUsuarioEdicion(user);
        }
        return onyxOtCmDir;
    }

    public String actualizarEstadoOTCCmm(CmtOrdenTrabajoMgl ordenTrabajo, CmtBasicaMgl estadoOtId, String user)
            throws ApplicationException {

        try {

            //Verifico si al estado que se pasa es razonado 
            boolean estadoRazonado = pasaEstadoRazonado(ordenTrabajo, estadoOtId);

            if (!estadoRazonado) {
                if (tieneAgendasPendientesOCanceladas(ordenTrabajo, estadoOtId.getBasicaId(), user)) {
                    LOGGER.info("NO HAY AGENDAS PENDIENTES");
                } else {
                    String msg = "La orden de trabajo No: " + ordenTrabajo.getIdOt() + "  tiene agendas pendientes por cerrar";
                    throw new ApplicationException(msg);
                }
            }
            //Verifico si el estado es razonado debe volver al estado anterior
            boolean cambioEstadorazonado = validaCambioEstadoRazonado(ordenTrabajo, estadoOtId.getBasicaId());

            if (cambioEstadorazonado && estadoAnteriorRazonado != null) {
                LOGGER.info("PUEDE REALIZAR EL CAMBIO DE ESTADO");
            } else if (!cambioEstadorazonado && estadoAnteriorRazonado != null) {
                String msg = "Al ser configurado como estado razonado "
                        + "debe pasar al estado anterior: " + estadoAnteriorRazonado.getNombreBasica() + "";
                throw new ApplicationException(msg);
            }
            //Verifico si el estado es razonado debe volver al estado anterior

            ordenTrabajo = ordenTrabajoMglManager.actualizarOt(ordenTrabajo, estadoOtId.getBasicaId(), user, 1);

            if (ordenTrabajo != null && ordenTrabajo.getTipoOtObj() != null
                    && ordenTrabajo.getTipoOtObj().getEsTipoVT().equalsIgnoreCase("N")) {
                //verificamos si cancelan la orden
                CmtBasicaMgl cancelado = basicaMglManager.
                        findByCodigoInternoApp(Constant.BASICA_EST_INT_CANCELADO);
                CmtBasicaMgl cable = basicaMglManager.
                        findByCodigoInternoApp(Constant.BASICA_EST_INT_CABLE);
                if (cancelado.getBasicaId() != null) {
                    if (estadoOtId.getBasicaId().compareTo(cancelado.getBasicaId()) == 0) {
                        //buscamos la ot de referencia
                        CmtOrdenTrabajoMgl ordenTrabajoRef = ordenTrabajoMglManager.
                                findOtById(ordenTrabajo.getOrdenTrabajoReferencia());
                        if (ordenTrabajoRef.getIdOt() != null) {
                            //buscamos la vt activa  
                            CmtBasicaMgl estIntPenDocCierreComercial
                                    = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_PENDOCCIECOM);
                            CmtVisitaTecnicaMglManager visitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();
                            CmtVisitaTecnicaMgl visitaTecnicaAct = visitaTecnicaMglManager.
                                    findVTActiveByIdOt(ordenTrabajoRef);
                            if (visitaTecnicaAct != null && visitaTecnicaAct.getIdVt() != null) {
                                CmtSubEdificiosVtManager subEdificiosVtManager = new CmtSubEdificiosVtManager();
                                List<CmtSubEdificiosVt> lstSubEdiVT;
                                lstSubEdiVT = subEdificiosVtManager.findByVt(visitaTecnicaAct);
                                if (lstSubEdiVT != null && !lstSubEdiVT.isEmpty()) {

                                    //Actualizamos los subedificios Vt
                                    for (CmtSubEdificiosVt subVt : lstSubEdiVT) {

                                        if (subVt.getOtAcometidaId() != null) {
                                            subVt.setOtAcometidaId(null);
                                            subVt.setGeneraAcometida("N");
                                            subVt = subEdificiosVtManager.update(subVt, user, 1);

                                            if (subVt.getOtAcometidaId() == null) {
                                                LOGGER.info("Se actualiza el subedificio VT a null acometida");
                                            } else {
                                                LOGGER.error("Ocurrio un error al actualizar el subedificio Vt");
                                            }
                                        }
                                    }
                                    if (estIntPenDocCierreComercial != null) {
                                        ordenTrabajoMglManager.actualizarOt(ordenTrabajoRef, estIntPenDocCierreComercial.getBasicaId(), user, 1);
                                    }
                                }

                            }
                        }
                    } else if (cable.getBasicaId() != null) {
                        if (estadoOtId.getBasicaId().compareTo(cable.getBasicaId()) == 0) {
                            //Actualizamos la ot  con la fecha de habilitacion 
                            ordenTrabajo.setFechaHabilitacion(new Date());
                            ordenTrabajoMglManager.actualizarOtCcmm(ordenTrabajo, user, 1);
                        }
                    }
                }

            }

        } catch (ApplicationException | EJBException e) {
            String msg = "Error en actualizarEstadoOT. " + e.getMessage();
            throw new ApplicationException(msg);
        }
        return "Modificacion de la orden exitosa";
    }

    private boolean pasaEstadoRazonado(CmtOrdenTrabajoMgl ordenTrabajoVal, CmtBasicaMgl estadoProximo) throws ApplicationException {
        try {

            List<CmtDetalleFlujoMgl> detalleFlujoEstProximo = null;
            if (estadoProximo != null) {
                if (ordenTrabajoVal != null && ordenTrabajoVal.getTipoOtObj() != null) {
                    CmtDetalleFlujoMglManager detalleFlujoMglManager = new CmtDetalleFlujoMglManager();
                    detalleFlujoEstProximo = detalleFlujoMglManager.
                            findByTipoFlujoAndEstadoProAndTecRazon(ordenTrabajoVal.getTipoOtObj().getTipoFlujoOt(),
                                    estadoProximo, ordenTrabajoVal.getBasicaIdTecnologia());
                }
            }

            if (detalleFlujoEstProximo != null && !detalleFlujoEstProximo.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error en pasaEstadoRazonado. " + ex.getMessage(), ex);
            throw new ApplicationException("Error en pasaEstadoRazonado: " + ex.getMessage() + " ", ex);
        } catch (Exception ex) {
            LOGGER.error("Error en pasaEstadoRazonado. " + ex.getMessage(), ex);
            throw new ApplicationException("Error en pasaEstadoRazonado:" + ex.getMessage() + " ", ex);
        }
    }

    public boolean tieneAgendasPendientesOCanceladas(CmtOrdenTrabajoMgl ot,
            BigDecimal estadoOtId, String user)
            throws ApplicationException {

        boolean ultimaAgenda;

        try {
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglManager.
                    findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(),
                            ot.getEstadoInternoObj(),
                            ot.getBasicaIdTecnologia());

            String subTipoWorForce = "";

            if (cmtEstadoxFlujoMgl != null) {
                if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                    subTipoWorForce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                }
            }
            //Consultamos si la orden tiene agendas pendientes
            List<CmtAgendamientoMgl> agendas = agendamientoMglManager.
                    agendasPendientesByOrdenAndSubTipoWorkfoce(ot, subTipoWorForce);
            if (!agendas.isEmpty()) {
                ultimaAgenda = false;

                for (CmtAgendamientoMgl agenda : agendas) {
                    if (agenda.getUltimaAgenda().equalsIgnoreCase("Y")) {
                        ultimaAgenda = true;
                    }
                }
            } else {
                ultimaAgenda = true;
                if (ot.getEstadoInternoObj().getBasicaId().compareTo(estadoOtId) != 0) {
                    //Consultamos si la orden tiene agendas canceladas
                    List<CmtAgendamientoMgl> agendasCan = agendamientoMglManager.
                            agendasCanceladasByOrdenAndSubTipoWorkfoce(ot, subTipoWorForce);
                    if (!agendasCan.isEmpty()) {
                        //Cierro ot en rr
                        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);

                        if (parametroHabilitarRR != null
                                && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {

                            CmtAgendamientoMgl agendaCan = agendasCan.get(0);
                            //capturo el numero de la ot en rr
                            String numeroOtRr = agendaCan.getIdOtenrr();
                            //Busco el estado cerrado OK
                            CmtTipoBasicaMgl tipoBasicaEstadoResultado
                                    = tipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_RESULTADO);

                            List<String> tiposEstadosCierreRR = Arrays.asList("REALIZADO");
                            List<CmtBasicaMgl> listadoEstadoCierre
                                    = basicaMglManager.findEstadoResultadoOTRR(tiposEstadosCierreRR, tipoBasicaEstadoResultado);
                            CmtBasicaMgl estadoCierreOK = null;
                            if (listadoEstadoCierre != null && !listadoEstadoCierre.isEmpty()) {
                                for (CmtBasicaMgl estadosCierre : listadoEstadoCierre) {
                                    if (estadosCierre.getCodigoBasica().trim().equalsIgnoreCase("OK")) {
                                        estadoCierreOK = estadosCierre;
                                    }
                                }
                            }
                            if (estadoCierreOK != null && cmtEstadoxFlujoMgl != null) {
                                cmtEstadoxFlujoMgl.setEstadoOtRRFinal(estadoCierreOK);
                                boolean cerrarOtRr = ordenTrabajoMglManager.
                                        cerrarOTRR(ot, numeroOtRr, cmtEstadoxFlujoMgl, user, 1);
                                if (cerrarOtRr) {
                                    LOGGER.info("Orden en RR cerrada satisfatoriamente");
                                } else {
                                    LOGGER.error("No se pudo cerrar la orden en RR");
                                }
                            }
                        }

                    }
                }
            }
            return ultimaAgenda;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes:" + msg + " ", e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + msg + " ", e);
        }
    }

    private boolean validaCambioEstadoRazonado(CmtOrdenTrabajoMgl ordenTrabajoVal, BigDecimal estadoNuevo) throws ApplicationException {

        List<CmtDetalleFlujoMgl> detalleFlujoEstIni;
        boolean resultado = false;
        estadoAnteriorRazonado = null;

        if (ordenTrabajoVal != null && ordenTrabajoVal.getTipoOtObj() != null) {
            CmtDetalleFlujoMglManager detalleFlujoMglManager = new CmtDetalleFlujoMglManager();
            detalleFlujoEstIni = detalleFlujoMglManager.
                    findByTipoFlujoAndEstadoProAndTecRazon(ordenTrabajoVal.getTipoOtObj().getTipoFlujoOt(),
                            ordenTrabajoVal.getEstadoInternoObj(), ordenTrabajoVal.getBasicaIdTecnologia());

            if (detalleFlujoEstIni != null && !detalleFlujoEstIni.isEmpty()) {
                CmtOrdenTrabajoAuditoriaMgl ultimoEstado = ordenTrabajoMglManager.findUltimoEstadoOtRazonada(ordenTrabajoVal, ordenTrabajoVal.getEstadoInternoObj().getBasicaId());
                estadoAnteriorRazonado = detalleFlujoEstIni.get(0).getEstadoInicialObj();
                resultado = (ultimoEstado != null && (ultimoEstado.getEstadoInternoObj().getBasicaId().compareTo(estadoNuevo) == 0));
            } else {
                resultado = false;
            }
        }
        return resultado;
    }

    private void consultarNodoOtCcmm(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        try {

            CmtCuentaMatrizMgl cm = ot.getCmObj();
            CmtSubEdificioMgl subEdificioMgl;
            subEdificioMgl = cm.getSubEdificioGeneral();

            CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();

            CmtTecnologiaSubMgl tecnologiaSubMgl = tecnologiaSubMglManager.
                    findBySubEdificioTecnologia(subEdificioMgl,
                            ot.getBasicaIdTecnologia());

            if (tecnologiaSubMgl != null && tecnologiaSubMgl.getTecnoSubedificioId() != null && tecnologiaSubMgl.getNodoId() != null) {
                NodoMglManager nodoMglManager = new NodoMglManager();
                nodoMglApp = nodoMglManager.findById(tecnologiaSubMgl.getNodoId().getNodId());

            } else {
                //Consulto el geo
                NodoMglManager nodoMglManager = new NodoMglManager();
                nodoMglApp = nodoMglManager.consultaGeo(ot);
                if (nodoMglApp == null) {
                    throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
                }
            }
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    private void consultarNodoOtHhpp(OtHhppMgl otDireccion) throws ApplicationException {

        try {
            OtHhppTecnologiaMglManager otHhppTecnologiaMglManager = new OtHhppTecnologiaMglManager();

            List<OtHhppTecnologiaMgl> otHhppTecnologiaMglList
                    = otHhppTecnologiaMglManager.findOtHhppTecnologiaByOtHhppId(otDireccion.getOtHhppId());

            //JDHT
            OtHhppTecnologiaMgl tecnologiaPrioridadLocation = null;

            if (otHhppTecnologiaMglList != null && !otHhppTecnologiaMglList.isEmpty()) {

                for (OtHhppTecnologiaMgl otHhppTecnologiaMgl : otHhppTecnologiaMglList) {

                    if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId() != null) {
                        //prioridades de tecnologias para extraer location
                        //BI, UNI, DTH, FTTH, GPON, FOU, RFO
                        //BI
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //UNI
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //DTH
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //FTTH
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_FTTTH)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //GPON
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_GPON)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //FOU
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_UNI)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }

                        //RFO
                        if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                && !otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                && otHhppTecnologiaMgl.getTecnoglogiaBasicaId()
                                        .getIdentificadorInternoApp().equalsIgnoreCase(Constant.RED_FO)) {
                            tecnologiaPrioridadLocation = otHhppTecnologiaMgl;
                            break;
                        }
                    }
                }
            }

            if (tecnologiaPrioridadLocation != null && tecnologiaPrioridadLocation.getNodo() != null) {
                nodoMglApp = tecnologiaPrioridadLocation.getNodo();
            }

            if (nodoMglApp == null) {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
            }

        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * Genera un N&uacute;mero de OT de RR, de acuerdo a si fue creada o no una
     * OT en RR.
     *
     * @param orden Orden de Trabajo.
     * @param numeroOtRr N&uacute;mero de la OT en RR.
     * @param habilitaRr Flag que determina si se encuentra RR habilitado.
     * @return N&uacute;mero de la OT en RR.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String generarNumeroOtRr(CmtOrdenTrabajoMgl orden, String numeroOtRr, boolean habilitaRr)
            throws ApplicationException {

        String numeroGenerado = "";

        try {
            if (orden != null) {
                if (habilitaRr) {
                    //Si se generó una Ot en RR:
                    if (numeroOtRr != null && orden.getCmObj() != null
                            && orden.getCmObj().getNumeroCuenta() != null) {
                        // Número de cuenta matriz de RR>+<Número orden de RR a 5,  justificada a la derecha y relleno con ceros.
                        numeroGenerado = orden.getCmObj().
                                getNumeroCuenta().toString().trim()
                                + StringUtils.leftPad(numeroOtRr, MAX_CARACTERES_NUMERO_OT_RR, "0");
                    }

                } else {
                    //Si no se generó una Ot en RR:
                    if (orden.getEstadoInternoObj() != null
                            && orden.getEstadoInternoObj().getBasicaId() != null) {
                        String estadoOt = orden.getEstadoInternoObj().getBasicaId().toString();

                        if (orden.getIdOt() != null) {
                            // Número de Orden de MGL>+<PK del Estado en que este la Orden de trabajo a 6 dígitos  justificada a la derecha y relleno con ceros.
                            numeroGenerado = orden.getIdOt().
                                    toString().trim()
                                    + StringUtils.leftPad(estadoOt, MAX_CARACTERES_ESTADO_OT, "0");
                        }
                    }
                }
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de obtener el Número OT de RR: " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }

        return numeroGenerado;
    }

    public List<CapacidadAgendaDto> getCapacidadMulti() throws ApplicationException {

        List<CapacidadAgendaDto> capacidadAgendaDtos = new ArrayList<>();
        try {
            capacidadAgendaDtos
                    = agendamientoMglManager.getCapacidad(ordenCcmmMglApp, null, nodoMglApp);

        } catch (ApplicationException e) {
            String msg = "Ocurrió un error obteniendo capacidad OTCMHhppMglManager: getCapacidadMulti() " + e.getMessage();
            throw new ApplicationException(msg);
        } catch (Exception e) {
            String msg = "Ocurrió un error obteniendo capacidad OTCMHhppMglManager: getCapacidadMulti() " + e.getMessage();
            throw new ApplicationException(msg);
        }
        return capacidadAgendaDtos;
    }

    public List<CapacidadAgendaDto> getCapacidadMultiHhpp() throws ApplicationException {

        List<CapacidadAgendaDto> capacidadAgendaDtos = new ArrayList<>();
        UsuariosServicesDTO usuariosServicesDTO = new UsuariosServicesDTO();
        usuariosServicesDTO.setCedula("1");
        try {
            capacidadAgendaDtos
                    = agendamientoHhppMglManager.getCapacidadOtDireccion(ordenHhppMglApp, usuariosServicesDTO, null, nodoMglApp);

        } catch (ApplicationException e) {
            String msg = "Ocurrió un error obteniendo capacidad OTCMHhppMglManager: getCapacidadMulti() " + e.getMessage();
            throw new ApplicationException(msg);
        } catch (Exception e) {
            String msg = "Ocurrió un error obteniendo capacidad OTCMHhppMglManager: getCapacidadMulti() " + e.getMessage();
            throw new ApplicationException(msg);
        }
        return capacidadAgendaDtos;
    }

    public void rollbackOrdenCcmmInRr(CmtAgendamientoMgl agendamientoMgl, String user) throws ApplicationException {

        boolean respuestaElimina;

        try {
            if (agendamientoMgl.getIdOtenrr() != null && !agendamientoMgl.getIdOtenrr().isEmpty()) {
                //Consultamos la ot en rr si existe
                ResponseOtEdificiosList responseOtEdificiosList = ordenTrabajoMglManager.
                        ordenTrabajoEdificioQuery(agendamientoMgl.getOrdenTrabajo().getCmObj().getNumeroCuenta().toString());

                if (responseOtEdificiosList != null
                        && responseOtEdificiosList.getListOtEdificios() != null
                        && !responseOtEdificiosList.getListOtEdificios().isEmpty()) {

                    for (ResponseDataOtEdificios responseDataOtEdificios
                            : responseOtEdificiosList.getListOtEdificios()) {

                        if (responseDataOtEdificios.getNumeroTrabajo().
                                equalsIgnoreCase(agendamientoMgl.getIdOtenrr())) {
                            //Eliminamos la ot en rr si existe
                            respuestaElimina = ordenTrabajoMglManager.
                                    eliminarOtRRporAgendamiento(agendamientoMgl, user);

                            if (respuestaElimina) {
                                LOGGER.info("Orden No: " + agendamientoMgl.getIdOtenrr() + " borrada por rollback en RR");
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            String msg = "Ocurrió un error al realizar el agendamiento: rollbackOrdenCcmmInRr " + e.getMessage();
            throw new ApplicationException(msg);
        } catch (Exception e) {
            String msg = "Ocurrió un error al realizar el agendamiento: rollbackOrdenCcmmInRr " + e.getMessage();
            throw new ApplicationException(msg);
        }
    }

    public void rollbackOrdenHhppInRr(MglAgendaDireccion agendaDireccion, String user) throws ApplicationException {

        boolean respuestaElimina;

        try {
            if (agendaDireccion.getIdOtenrr() != null && !agendaDireccion.getIdOtenrr().isEmpty()) {
                //Consultamos la ot en rr si existe
                ResponseOtEdificiosList responseOtEdificiosList = ordenTrabajoMglManager.
                        ordenTrabajoEdificioQuery(codCuentaPar);

                if (responseOtEdificiosList != null
                        && responseOtEdificiosList.getListOtEdificios() != null
                        && !responseOtEdificiosList.getListOtEdificios().isEmpty()) {

                    for (ResponseDataOtEdificios responseDataOtEdificios
                            : responseOtEdificiosList.getListOtEdificios()) {

                        if (responseDataOtEdificios.getNumeroTrabajo().
                                equalsIgnoreCase(agendaDireccion.getIdOtenrr())) {
                            //Eliminamos la ot en rr si existe
                            respuestaElimina = ordenTrabajoMglManager.
                                    ordenTrabajoEdificioDeleteHhpp(codCuentaPar, agendaDireccion.getIdOtenrr(), user);

                            if (respuestaElimina) {
                                LOGGER.info("Orden No: " + agendaDireccion.getIdOtenrr() + " borrada por rollback en RR");
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            String msg = "Ocurrió un error al realizar el agendamiento: rollbackOrdenHhppInRr " + e.getMessage();
            throw new ApplicationException(msg);
        } catch (Exception e) {
            String msg = "Ocurrió un error al realizar el agendamiento: rollbackOrdenHhppInRr " + e.getMessage();
            throw new ApplicationException(msg);
        }

    }

    /**
     * Valida si no existe restriccion de horario para agendar la OT en el caso
     * de que el Hhpp se encuentre dentro de una CM
     *
     * @param capacidad capacidad obtenida de WorkForce para validar
     * restricciones
     * @author Victor Bocanegra
     * @throws ApplicationException Excepcion lanzada en la consulta
     */
    private boolean validarRestriccionesCcmmOtHhpp(CapacidadAgendaDto capacidad) {
        try {
            CmtHorarioRestriccionMglManager restriccionMglManager = new CmtHorarioRestriccionMglManager();

            if (capacidad.getTimeSlot() != null
                    && capacidad.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                if (ordenHhppMglApp != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(ordenHhppMglApp);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = restriccionMglManager.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

                                GregorianCalendar cal = new GregorianCalendar();
                                cal.setTime(capacidad.getDate());
                                int dia = cal.get(Calendar.DAY_OF_WEEK);
                                String diaFecha = devuelveDia(dia);

                                List<String> diasCompara;

                                if (!restriccionMgls.isEmpty()) {
                                    for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                                        if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                                equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                                || horarioRestriccionMgl.getTipoRestriccion().toString().
                                                        equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                            diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                                    horarioRestriccionMgl.getDiaFin().getDia());

                                            for (String diaComparar : diasCompara) {
                                                if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            } else if (capacidad.getTimeSlot() != null) {
                if (ordenHhppMglApp != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(ordenHhppMglApp);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = restriccionMglManager.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

                                GregorianCalendar cal = new GregorianCalendar();
                                cal.setTime(capacidad.getDate());
                                int dia = cal.get(Calendar.DAY_OF_WEEK);
                                String diaFecha = devuelveDia(dia);

                                String[] partsHorIniFi = capacidad.getTimeSlot().split("-");
                                int parteHorIni = Integer.parseInt(partsHorIniFi[0]);

                                List<String> diasCompara;

                                if (!restriccionMgls.isEmpty()) {
                                    for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                                        if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                                equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                                || horarioRestriccionMgl.getTipoRestriccion().toString().
                                                        equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                            diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                                    horarioRestriccionMgl.getDiaFin().getDia());

                                            for (String diaComparar : diasCompara) {

                                                if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                                    String hIni = horarioRestriccionMgl.getHoraInicio().substring(0, 2);
                                                    String hFin = horarioRestriccionMgl.getHoraFin().substring(0, 2);
                                                    int resHorIni = Integer.parseInt(hIni);
                                                    int resHorFin = Integer.parseInt(hFin);
                                                    for (int i = resHorIni; i < resHorFin; i++) {
                                                        if (i == parteHorIni) {
                                                            return false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            } else if (capacidad.getHoraInicio() != null) {
                if (ordenHhppMglApp != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(ordenHhppMglApp);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = restriccionMglManager.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

                                GregorianCalendar cal = new GregorianCalendar();
                                cal.setTime(capacidad.getDate());
                                int dia = cal.get(Calendar.DAY_OF_WEEK);
                                String diaFecha = devuelveDia(dia);

                                String[] partsHorIniFi = capacidad.getHoraInicio().split(":");
                                int parteHorIni = Integer.parseInt(partsHorIniFi[0]);

                                List<String> diasCompara;

                                if (!restriccionMgls.isEmpty()) {
                                    for (CmtHorarioRestriccionMgl horarioRestriccionMgl : restriccionMgls) {

                                        if (horarioRestriccionMgl.getTipoRestriccion().toString().
                                                equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)
                                                || horarioRestriccionMgl.getTipoRestriccion().toString().
                                                        equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                                            diasCompara = diasComparar(horarioRestriccionMgl.getDiaInicio().getDia(),
                                                    horarioRestriccionMgl.getDiaFin().getDia());

                                            for (String diaComparar : diasCompara) {

                                                if (diaComparar.equalsIgnoreCase(diaFecha)) {
                                                    String hIni = horarioRestriccionMgl.getHoraInicio().substring(0, 2);
                                                    String hFin = horarioRestriccionMgl.getHoraFin().substring(0, 2);
                                                    int resHorIni = Integer.parseInt(hIni);
                                                    int resHorFin = Integer.parseInt(hFin);
                                                    for (int i = resHorIni; i < resHorFin; i++) {
                                                        if (i == parteHorIni) {
                                                            return false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
        } catch (ApplicationException | NumberFormatException e) {
            return false;
        }
        return false;
    }

    /**
     * Genera un N&uacute;mero de OT de RR, de acuerdo a si fue creada o no una
     * OT en RR.
     *
     * @param codCuentaGenerico
     * @param numeroOtRr N&uacute;mero de la OT en RR.
     * @param otHhppMgl
     * @param habilitaRr Flag que determina si se encuentra RR habilitado.
     * @return N&uacute;mero de la OT en RR.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String generarNumeroOtRrHhpp(String codCuentaGenerico, String numeroOtRr, OtHhppMgl otHhppMgl,
            boolean habilitaRr)
            throws ApplicationException {

        String numeroGenerado = "";

        try {
            if (codCuentaGenerico != null) {
                if (habilitaRr) {
                    //Si se generó una Ot en RR:
                    if (numeroOtRr != null) {
                        // Número de cuenta matriz de RR>+<Número orden de RR a 5,  justificada a la derecha y relleno con ceros.
                        numeroGenerado = codCuentaGenerico.trim() + StringUtils.leftPad(numeroOtRr, MAX_CARACTERES_NUMERO_OT_RR, "0");
                    }

                } else {
                    //Si no se generó una Ot en RR:
                    if (otHhppMgl.getOtHhppId() != null) {
                        // Número de Orden de direccion>+< la Orden de trabajo a 6 dígitos  justificada a la derecha y relleno con ceros.
                        numeroGenerado = otHhppMgl.getOtHhppId().toString().trim() + StringUtils.leftPad(otHhppMgl.getOtHhppId().toString().trim(), MAX_CARACTERES_ESTADO_OT, "0");
                    }

                }
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de obtener el Número OT de RR: " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }

        return numeroGenerado;
    }

    public boolean validarReglasAgendaOtCcmm() throws ApplicationException {

        boolean respuesta = false;

        try {
            if (ordenCcmmMglApp != null && ordenCcmmMglApp.getTipoOtObj() != null
                    && ordenCcmmMglApp.getTipoOtObj().getOtAgendable() != null) {
                if (!ordenCcmmMglApp.getTipoOtObj().getOtAgendable().isEmpty()
                        && ordenCcmmMglApp.getTipoOtObj().getOtAgendable().equalsIgnoreCase("Y")) {
                    if (ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt() != null
                            && ordenCcmmMglApp.getEstadoInternoObj() != null
                            && ordenCcmmMglApp.getBasicaIdTecnologia() != null) {

                        CmtEstadoxFlujoMglManager flujoMglManager = new CmtEstadoxFlujoMglManager();
                        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = flujoMglManager.
                                findByTipoFlujoAndEstadoInt(ordenCcmmMglApp.getTipoOtObj().getTipoFlujoOt(),
                                        ordenCcmmMglApp.getEstadoInternoObj(),
                                        ordenCcmmMglApp.getBasicaIdTecnologia());

                        if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                            respuesta = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null;
                        } else {
                            respuesta = false;
                        }
                    }
                }
            }

        } catch (ApplicationException e) {
            String msg = "Error al validarReglasAgendaOtCcmm. " + e.getMessage();
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            String msg = "Error al validarReglasAgendaOtCcmm. " + e.getMessage();
            throw new ApplicationException(msg, e);
        }
        return respuesta;
    }

    /**
     * Función que valida si la OT esta cerrada o anulada para renderizar los
     * botones de guardar cambios
     *
     * @author Juan David Hernandez
     * @param otHhppSeleccionado
     */
    public boolean validarEstadoOtHhpp(OtHhppMgl otHhppSeleccionado) throws ApplicationException {

        boolean otCerradaAnulada;

        try {
            if (otHhppSeleccionado != null && otHhppSeleccionado.getEstadoGeneral() != null
                    && otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp() != null
                    && !otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().isEmpty()) {

                otCerradaAnulada = otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO)
                        || otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO_COMPLETO);
            } else {
                otCerradaAnulada = false;
            }
        } catch (Exception e) {
            String msg = "Error al validar estado de la ot: " + e.getMessage();
            throw new ApplicationException(msg, e);
        }
        return otCerradaAnulada;
    }

    public List<AgendasMglDto> retornaListAgendas(List<CmtAgendamientoMgl> agendasOtCcmm,
            List<MglAgendaDireccion> agendasOtHhpp) {

        List<AgendasMglDto> returnsList = null;

        if (agendasOtCcmm != null && !agendasOtCcmm.isEmpty()) {
            returnsList = new ArrayList<>();
            for (CmtAgendamientoMgl agendamientoMgl : agendasOtCcmm) {
                AgendasMglDto agendasMglDto = new AgendasMglDto();
                agendasMglDto.setFechaAgenda(agendamientoMgl.getFechaAgenda());
                agendasMglDto.setFechaReagenda(agendamientoMgl.getFechaReagenda());
                agendasMglDto.setTimeSlot(agendamientoMgl.getTimeSlot());
                agendasMglDto.setBasicaIdrazones(agendamientoMgl.getBasicaIdrazones() != null
                        ? agendamientoMgl.getBasicaIdrazones().getNombreBasica() : "NA");
                agendasMglDto.setOfpsOtId(agendamientoMgl.getOfpsOtId());
                agendasMglDto.setWorkForceId(agendamientoMgl.getWorkForceId());
                agendasMglDto.setFechaInivioVt(agendamientoMgl.getFechaInivioVt());
                agendasMglDto.setNombreTecnico(agendamientoMgl.getNombreTecnico() != null
                        ? agendamientoMgl.getNombreTecnico() : "NA");
                agendasMglDto.setFechaFinVt(agendamientoMgl.getFechaFinVt());
                agendasMglDto.setBasicaIdEstadoAgenda(agendamientoMgl.getBasicaIdEstadoAgenda()
                        != null ? agendamientoMgl.getBasicaIdEstadoAgenda().getNombreBasica() : "NA");
                agendasMglDto.setObservacionesTecnico(agendamientoMgl.getObservacionesTecnico() != null
                        ? agendamientoMgl.getObservacionesTecnico() : "NA");
                agendasMglDto.setIdOtenrr(agendamientoMgl.getIdOtenrr() != null
                        ? agendamientoMgl.getIdOtenrr() : "NA");
                agendasMglDto.setIdOt(agendamientoMgl.getOrdenTrabajo().getIdOt()!= null
                        ? agendamientoMgl.getOrdenTrabajo().getIdOt() : null);
                returnsList.add(agendasMglDto);
            }
        }

        if (agendasOtHhpp != null && !agendasOtHhpp.isEmpty()) {
            returnsList = new ArrayList<>();
            for (MglAgendaDireccion agendamientoMgl : agendasOtHhpp) {
                AgendasMglDto agendasMglDto = new AgendasMglDto();
                agendasMglDto.setFechaAgenda(agendamientoMgl.getFechaAgenda());
                agendasMglDto.setFechaReagenda(agendamientoMgl.getFechaReagenda());
                agendasMglDto.setTimeSlot(agendamientoMgl.getTimeSlot());
                agendasMglDto.setBasicaIdrazones(agendamientoMgl.getBasicaIdrazones() != null
                        ? agendamientoMgl.getBasicaIdrazones().getNombreBasica() : "NA");
                agendasMglDto.setOfpsOtId(agendamientoMgl.getOfpsOtId());
                agendasMglDto.setWorkForceId(new BigDecimal(agendamientoMgl.getOfpsId()));
                agendasMglDto.setFechaInivioVt(agendamientoMgl.getFechaInivioVt());
                agendasMglDto.setNombreTecnico(agendamientoMgl.getNombreTecnico() != null
                        ? agendamientoMgl.getNombreTecnico() : "NA");
                agendasMglDto.setFechaFinVt(agendamientoMgl.getFechaFinVt());
                agendasMglDto.setBasicaIdEstadoAgenda(agendamientoMgl.getBasicaIdEstadoAgenda()
                        != null ? agendamientoMgl.getBasicaIdEstadoAgenda().getNombreBasica() : "NA");
                agendasMglDto.setObservacionesTecnico(agendamientoMgl.getObservacionesTecnico() != null
                        ? agendamientoMgl.getObservacionesTecnico() : "NA");
                agendasMglDto.setIdOtenrr(agendamientoMgl.getIdOtenrr() != null
                        ? agendamientoMgl.getIdOtenrr() : "NA");
                agendasMglDto.setIdOt(agendamientoMgl.getOrdenTrabajo().getOtHhppId()!= null
                        ? agendamientoMgl.getOrdenTrabajo().getOtHhppId() : null);
                returnsList.add(agendasMglDto);
            }
        }
        return returnsList;
    }

    public boolean validoFechaFranjaAgendasCcmm(String fechaCapacity, String franjaCapacity) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (agendasOtCcmm != null && !agendasOtCcmm.isEmpty()) {
            for (CmtAgendamientoMgl agendaCom : agendasOtCcmm) {
                if (!agendaCom.getBasicaIdEstadoAgenda().
                        getIdentificadorInternoApp().
                        equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                    if (agendaCom.getFechaReagenda() != null) {
                        String fechAge = df.format(agendaCom.getFechaReagenda());
                        if (fechAge.equalsIgnoreCase(fechaCapacity)
                                && agendaCom.getTimeSlot().equalsIgnoreCase(franjaCapacity)) {
                            return false;
                        }
                    } else {
                        String fechAge = df.format(agendaCom.getFechaAgenda());
                        if (fechAge.equalsIgnoreCase(fechaCapacity)
                                && agendaCom.getTimeSlot().equalsIgnoreCase(franjaCapacity)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean validoFechaFranjaAgendasHhpp(String fechaCapacity, String franjaCapacity) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (agendasOtHhpp != null && !agendasOtHhpp.isEmpty()) {
            for (MglAgendaDireccion agendaCom : agendasOtHhpp) {
                if (!agendaCom.getBasicaIdEstadoAgenda().
                        getIdentificadorInternoApp().
                        equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                    if (agendaCom.getFechaReagenda() != null) {
                        String fechAge = df.format(agendaCom.getFechaReagenda());
                        if (fechAge.equalsIgnoreCase(fechaCapacity)
                                && agendaCom.getTimeSlot().equalsIgnoreCase(franjaCapacity)) {
                            return false;
                        }
                    } else {
                        String fechAge = df.format(agendaCom.getFechaAgenda());
                        if (fechAge.equalsIgnoreCase(fechaCapacity)
                                && agendaCom.getTimeSlot().equalsIgnoreCase(franjaCapacity)) {
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }

    /**
     * Metodo para validar si hay agendas posteriores a la que se va a cancelar
     * Autor: Victor Bocanegra
     *
     * @param agendaCancela agenda que se va a cancelar
     * @return boolean
     */
    public boolean validarAgendasPosteriores(CmtAgendamientoMgl agendaCancela) throws ApplicationException {

        boolean hayAgendasPosteriores = false;
        List<BigDecimal> idsEstAgendadas = estadosAgendadas();
        List<CmtAgendamientoMgl> agendasPos;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {

            agendasPos = agendamientoMglManager.buscarAgendasrPosteriores(agendaCancela, idsEstAgendadas);
            if (agendasPos != null && !agendasPos.isEmpty()) {
                hayAgendasPosteriores = true;
                String msn = "No es posible cancelar la agenda seleccionada antes debe cancelar "
                        + "las agendas de fecha: ";

                StringBuilder mensajeFinal = new StringBuilder("");
                String complemento;
                String fechaAgenda;

                for (CmtAgendamientoMgl agendamientoMgl : agendasPos) {
                    fechaAgenda = format.format(agendamientoMgl.getFechaAgenda());
                    complemento = fechaAgenda + " y franja: " + agendamientoMgl.getTimeSlot() + " |";
                    mensajeFinal.append(complemento);
                }
                mensajesValidacion = msn + mensajeFinal.toString();

            } else {
                //No hay agendas posteriores
                hayAgendasPosteriores = false;

            }

        } catch (ApplicationException ex) {
            String msg = "Ocurrió un error al consultar "
                    + "las agendas posteriores: " + ex.getMessage();
            throw new ApplicationException(msg, ex);
        } catch (Exception ex) {
            String msg = "Ocurrió un error al consultar "
                    + "las agendas posteriores: " + ex.getMessage();
            throw new ApplicationException(msg, ex);
        }
        return hayAgendasPosteriores;
    }

    public List<BigDecimal> estadosAgendadas() throws ApplicationException {

        List<BigDecimal> idsEstados = new ArrayList<>();

        try {
            CmtBasicaMgl agendada = basicaMglManager.findByCodigoInternoApp(Constant.ESTADO_AGENDA_AGENDADA);
            CmtBasicaMgl reagendada = basicaMglManager.findByCodigoInternoApp(Constant.ESTADO_AGENDA_REAGENDADA);

            if (agendada != null) {
                idsEstados.add(agendada.getBasicaId());
            }
            if (reagendada != null) {
                idsEstados.add(reagendada.getBasicaId());
            }

        } catch (ApplicationException ex) {
            String msg = "Ocurrió un error al consultar los estados de agenda: "
                    + "" + ex.getMessage();
            throw new ApplicationException(msg, ex);
        } catch (Exception ex) {
            String msg = "Ocurrió un error al consultar los estados de agenda: "
                    + "" + ex.getMessage();
            throw new ApplicationException(msg, ex);
        }

        return idsEstados;
    }

    /**
     * Metodo para consultar la factibilidad de un traslado de direccion desde
     * el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param trasladoRequest
     * @return CmtDefaultBasicResponse
     */
    public CmtDefaultBasicResponse consultarFactibilidadTrasladoApp(AppConsultarFactibilidadTrasladoRequest trasladoRequest)
            throws ApplicationException {

        CmtDefaultBasicResponse responses = new CmtDefaultBasicResponse();

        try {
            if (trasladoRequest.getIdEnlace() == null
                    || trasladoRequest.getIdEnlace().isEmpty()) {
                responses.setMessageType("E");
                responses.setMessage("El campo idEnlace es obligatorio");
            } else if (trasladoRequest.getCodigoDane() == null
                    || trasladoRequest.getCodigoDane().isEmpty()) {
                responses.setMessageType("E");
                responses.setMessage("El campo codigoDane es obligatorio");
            } else if (trasladoRequest.getDireccionTabulada() == null) {
                responses.setMessageType("E");
                responses.setMessage("El campo direccionTabulada es obligatorio");
            } else if (trasladoRequest.getUser() == null || trasladoRequest.getUser().isEmpty()) {
                responses.setMessageType("E");
                responses.setMessage("El campo user es obligatorio");
            } else {
                if (validacionesTrasladoDir(trasladoRequest)) {

                    //Busco ordenes de CCMM la mas reciente
                    CmtBasicaMgl tecnoGPON = basicaMglManager.findByCodigoInternoApp(Constant.FIBRA_OP_GPON);
                    CmtBasicaMgl tecnoFOU = basicaMglManager.findByCodigoInternoApp(Constant.FIBRA_OP_UNI);
                    List<BigDecimal> idTecno = new ArrayList<>();
                    if (tecnoGPON != null && tecnoGPON.getBasicaId() != null) {
                        idTecno.add(tecnoGPON.getBasicaId());
                    }
                    if (tecnoFOU != null && tecnoFOU.getBasicaId() != null) {
                        idTecno.add(tecnoFOU.getBasicaId());
                    }
                    List<CmtOrdenTrabajoMgl> ordenTrabajoMgls = ordenTrabajoMglManager.
                            findByIdEnlaceAndTecnologia(trasladoRequest.getIdEnlace(), idTecno);
                    if (ordenTrabajoMgls != null && !ordenTrabajoMgls.isEmpty()) {
                        //El origen es CCMM
                        CmtCuentaMatrizMgl ccmm = ordenTrabajoMgls.get(0).getCmObj();
                        //Capturamos la tecnologia de la OT
                        CmtBasicaMgl tecnologiaOrigen = ordenTrabajoMgls.get(0).getBasicaIdTecnologia();
                        //Buscamos el estrato de la direccion
                        String estratoOri = "NA";
                        CmtDireccionMgl direccionCcmm = ccmm.getDireccionPrincipal();
                        if (direccionCcmm != null && direccionCcmm.getDireccionId() != null) {
                            DireccionMgl dir = direccionCcmm.getDireccionObj();
                            if (dir != null) {
                                estratoOri = dir.getDirEstrato() != null ? dir.getDirEstrato().toString() : "NA";
                            }
                        }
                        //determinamos el destino
                        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                        CmtDireccionMgl direccionCcmmDestino = cmtDireccionMglManager.
                                findCmtIdDireccion(detalladaMglTraslado.getDireccionId());
                        //Buscamos el estrato de la direccion destino
                        String estratoDest = "NA";
                        if (direccionCcmmDestino != null && direccionCcmmDestino.getDireccionId() != null) {
                            //la direccion destino es CCMM
                            DireccionMgl dirDes = direccionCcmmDestino.getDireccionObj();
                            if (dirDes != null) {
                                estratoDest = dirDes.getDirEstrato() != null ? dirDes.getDirEstrato().toString() : "NA";
                            }
                            if (estratoOri.equalsIgnoreCase(estratoDest)) {
                                //Busco tecno sub de la CCMM
                                CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                                CmtSubEdificioMgl edificio = direccionCcmmDestino.getCuentaMatrizObj().
                                        getSubEdificioGeneral();
                                List<CmtTecnologiaSubMgl> tecnologys = tecnologiaSubMglManager.
                                        findTecnoSubBySubEdiTec(edificio, tecnologiaOrigen);
                                if (tecnologys != null && !tecnologys.isEmpty()) {
                                    CmtTecnologiaSubMgl tecnoDestino = null;
                                    for (CmtTecnologiaSubMgl tecno : tecnologys) {
                                        if (tecno.getBasicaIdTecnologias().getBasicaId().
                                                compareTo(tecnologiaOrigen.getBasicaId()) == 0) {
                                            tecnoDestino = tecno;
                                        }
                                    }
                                    //Busco la basica fibra optica
                                    if (tecnoDestino != null
                                            && tecnoDestino.getTecnoSubedificioId() != null) {
                                        if (tecnoDestino.getBasicaIdEstadosTec().
                                                getIdentificadorInternoApp().
                                                equalsIgnoreCase(Constant.BASICA_TIPO_TEC_CABLE)
                                                && tecnoDestino.getBasicaIdEstadosTec().getNombreBasica().
                                                        trim().equalsIgnoreCase("FIBRA OPTICA")) {
                                            responses.setMessageType("I");
                                            responses.setMessage("Factible:");
                                        } else {
                                            responses.setMessageType("I");
                                            responses.setMessage("Factible con Restricción: "
                                                    + "El estado de la tecnologia destino no se "
                                                    + "encuentra en FIBRA OPTICA se encuentra en: " + tecnoDestino.getBasicaIdEstadosTec().getNombreBasica() + "");
                                        }
                                    } else {
                                        responses.setMessageType("I");
                                        responses.setMessage("Factible con Restricción:"
                                                + " No hay tecnologia igual a la de "
                                                + "la direccion origen: " + tecnologiaOrigen.getNombreBasica() + "");
                                    }
                                } else {
                                    responses.setMessageType("I");
                                    responses.setMessage("Factible con Restricción:"
                                            + " la CCMM destino no tiene tecnologias");
                                }
                            } else {
                                responses.setMessageType("I");
                                responses.setMessage("Factible con Restricción:"
                                        + " Estratos diferentes: estrato origen = " + estratoOri + "/ estrato destino = " + estratoDest + "");
                            }
                        } else {
                            //la direccion destino es HHPP
                            DireccionMgl direccion = detalladaMglTraslado.getDireccion();
                            SubDireccionMgl subDireccionMgl = detalladaMglTraslado.getSubDireccion();
                            if (subDireccionMgl != null) {
                                estratoDest = subDireccionMgl.getSdiEstrato().toString();
                            } else if (direccion != null) {
                                estratoDest = direccion.getDirEstrato().toString();
                            }
                            if (estratoOri.equalsIgnoreCase(estratoDest)) {
                                List<HhppMgl> hhppMgls = hhppMglManager.
                                        findByDirAndSubDir(direccion, subDireccionMgl);
                                int control = 0;
                                for (HhppMgl hhppMgl : hhppMgls) {
                                    NodoMgl nodo = hhppMgl.getNodId();
                                    if (nodo.getNodTipo().getBasicaId().compareTo(tecnologiaOrigen.getBasicaId()) == 0) {
                                        control++;
                                    }
                                }
                                if (control > 0) {
                                    responses.setMessageType("I");
                                    responses.setMessage("Factible:");
                                } else {
                                    responses.setMessageType("I");
                                    responses.setMessage("Factible con Restricción: "
                                            + "No hay un HHPP con la misma tecnologia origen: " + tecnologiaOrigen.getNombreBasica() + "");
                                }
                            } else {
                                responses.setMessageType("I");
                                responses.setMessage("Factible con Restricción:"
                                        + " Estratos diferentes: estrato origen = "
                                        + " " + estratoOri + "/ estrato destino = " + estratoDest + "");
                            }
                        }
                    } else {
                        //Si no encuentro en ordenes de CCMM 
                        //Busco en la tabla de ordenes de direcciones 
                        List<OtHhppMgl> ordeHhppMgls = otHhppMglManager.
                                findOtHhppByIdEnlaceAndTecnologias(trasladoRequest.getIdEnlace(), idTecno);
                        String estratoOri = "NA";
                        if (ordeHhppMgls != null && !ordeHhppMgls.isEmpty()) {
                            //El origen es HHPP
                            OtHhppMgl otHhppMgl = ordeHhppMgls.get(0);
                            DireccionMgl direccionOrg = otHhppMgl.getDireccionId();
                            SubDireccionMgl subDireccionOrg = otHhppMgl.getSubDireccionId();
                            if (subDireccionOrg != null) {
                                estratoOri = subDireccionOrg.getSdiEstrato().toString();
                            } else if (direccionOrg != null) {
                                estratoOri = direccionOrg.getDirEstrato().toString();
                            }
                            // Busco hhpp de tecnologia GPON/FOU
                            List<HhppMgl> hhppMgls = hhppMglManager.
                                    findByDirAndSubDir(direccionOrg, subDireccionOrg);
                            HhppMgl hhppMglOrg = null;
                            CmtBasicaMgl tecnologiaOrigen = null;

                            if (hhppMgls != null && !hhppMgls.isEmpty()) {
                                for (HhppMgl hhppMgl : hhppMgls) {
                                    NodoMgl nodoMgl = hhppMgl.getNodId();
                                    if (tecnoGPON != null && tecnoFOU != null) {
                                        if ((nodoMgl.getNodTipo().getBasicaId().
                                                compareTo(tecnoGPON.getBasicaId()) == 0)
                                                || (nodoMgl.getNodTipo().getBasicaId().
                                                        compareTo(tecnoFOU.getBasicaId()) == 0)) {
                                            hhppMglOrg = hhppMgl;
                                            tecnologiaOrigen = nodoMgl.getNodTipo();
                                        }
                                    }
                                }
                                if (hhppMglOrg != null && tecnologiaOrigen != null) {

                                    //determinamos el destino
                                    CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                                    CmtDireccionMgl direccionCcmmDestino = cmtDireccionMglManager.
                                            findCmtIdDireccion(detalladaMglTraslado.getDireccionId());
                                    //Buscamos el estrato de la direccion destino
                                    String estratoDest = "NA";
                                    if (direccionCcmmDestino != null && direccionCcmmDestino.getDireccionId() != null) {
                                        //la direccion destino es CCMM
                                        DireccionMgl dirDes = direccionCcmmDestino.getDireccionObj();
                                        if (dirDes != null) {
                                            estratoDest = dirDes.getDirEstrato() != null ? dirDes.getDirEstrato().toString() : "NA";
                                        }
                                        if (estratoOri.equalsIgnoreCase(estratoDest)) {
                                            //Busco tecno sub de la CCMM
                                            CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                                            CmtSubEdificioMgl edificio = direccionCcmmDestino.getCuentaMatrizObj().
                                                    getSubEdificioGeneral();
                                            List<CmtTecnologiaSubMgl> tecnologys = tecnologiaSubMglManager.
                                                    findTecnoSubBySubEdiTec(edificio, tecnologiaOrigen);
                                            if (tecnologys != null && !tecnologys.isEmpty()) {
                                                CmtTecnologiaSubMgl tecnoDestino = null;
                                                for (CmtTecnologiaSubMgl tecno : tecnologys) {
                                                    if (tecno.getBasicaIdTecnologias().getBasicaId().
                                                            compareTo(tecnologiaOrigen.getBasicaId()) == 0) {
                                                        tecnoDestino = tecno;
                                                    }
                                                }
                                                //Busco la basica fibra optica
                                                if (tecnoDestino != null
                                                        && tecnoDestino.getTecnoSubedificioId() != null) {
                                                    if (tecnoDestino.getBasicaIdEstadosTec().
                                                            getIdentificadorInternoApp().
                                                            equalsIgnoreCase(Constant.BASICA_TIPO_TEC_CABLE)
                                                            && tecnoDestino.getBasicaIdEstadosTec().getNombreBasica().
                                                                    trim().equalsIgnoreCase("FIBRA OPTICA")) {
                                                        responses.setMessageType("I");
                                                        responses.setMessage("Factible:");
                                                    } else {
                                                        responses.setMessageType("I");
                                                        responses.setMessage("Factible con Restricción: "
                                                                + "El estado de la tecnologia destino no se "
                                                                + "encuentra en FIBRA OPTICA se encuentra en: " + tecnoDestino.getBasicaIdEstadosTec().getNombreBasica() + "");
                                                    }
                                                } else {
                                                    responses.setMessageType("I");
                                                    responses.setMessage("Factible con Restricción:"
                                                            + " No hay tecnologia igual a la de "
                                                            + "la direccion origen: " + tecnologiaOrigen.getNombreBasica() + "");
                                                }
                                            } else {
                                                responses.setMessageType("I");
                                                responses.setMessage("Factible con Restricción:"
                                                        + " la CCMM destino no tiene tecnologias");
                                            }
                                        } else {
                                            responses.setMessageType("I");
                                            responses.setMessage("Factible con Restricción:"
                                                    + " Estratos diferentes: estrato origen = " + estratoOri + "/ estrato destino = " + estratoDest + "");
                                        }
                                    } else {
                                        //la direccion destino es HHPP
                                        DireccionMgl direccion = detalladaMglTraslado.getDireccion();
                                        SubDireccionMgl subDireccionMgl = detalladaMglTraslado.getSubDireccion();
                                        if (subDireccionMgl != null) {
                                            estratoDest = subDireccionMgl.getSdiEstrato().toString();
                                        } else if (direccion != null) {
                                            estratoDest = direccion.getDirEstrato().toString();
                                        }
                                        if (estratoOri.equalsIgnoreCase(estratoDest)) {
                                            List<HhppMgl> hhppMgls1 = hhppMglManager.
                                                    findByDirAndSubDir(direccion, subDireccionMgl);
                                            int control = 0;
                                            for (HhppMgl hhppMgl : hhppMgls1) {
                                                NodoMgl nodo = hhppMgl.getNodId();
                                                if (nodo.getNodTipo().getBasicaId().compareTo(tecnologiaOrigen.getBasicaId()) == 0) {
                                                    control++;
                                                }
                                            }
                                            if (control > 0) {
                                                responses.setMessageType("I");
                                                responses.setMessage("Factible:");
                                            } else {
                                                responses.setMessageType("I");
                                                responses.setMessage("Factible con Restricción: "
                                                        + "No hay un HHPP con la misma tecnologia origen: " + tecnologiaOrigen.getNombreBasica() + "");
                                            }
                                        } else {
                                            responses.setMessageType("I");
                                            responses.setMessage("Factible con Restricción:"
                                                    + " Estratos diferentes: estrato origen = "
                                                    + " " + estratoOri + "/ estrato destino = " + estratoDest + "");
                                        }
                                    }
                                } else {
                                    responses.setMessageType("I");
                                    responses.setMessage("Factible con Restricción: "
                                            + "La direccion origen no tiene un HHPP con tecnologia GPON u FOU");
                                }
                            } else {
                                responses.setMessageType("I");
                                responses.setMessage("Factible con Restricción: "
                                        + "La direccion origen no tiene HHPP para su validacion");
                            }
                        } else {
                            //Si no encuentro en ordenes de HHPP 
                            //Busco en la tabla de ONIX
                            OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
                            List<OnyxOtCmDir> onyxOtCmBD = onyxOtCmDirManager.
                                    findOnyxByIdEnlace(trasladoRequest.getIdEnlace());

                            if (onyxOtCmBD != null && !onyxOtCmBD.isEmpty()) {
                                OnyxOtCmDir onyxOt = onyxOtCmBD.get(0);
                                CmtOrdenTrabajoMgl ot = onyxOt.getOt_Id_Cm();
                                OtHhppMgl otDir = onyxOt.getOt_Direccion_Id();
                                if (ot != null && ot.getIdOt() != null
                                        && tecnoGPON != null && tecnoFOU != null) {
                                    //El origen es CCMM
                                    CmtCuentaMatrizMgl ccmm = ot.getCmObj();
                                    //Capturamos la tecnologia de la OT
                                    CmtBasicaMgl tecnologiaOrigen = ot.getBasicaIdTecnologia();
                                    if ((tecnologiaOrigen.getBasicaId().compareTo(tecnoGPON.getBasicaId()) == 0) || (tecnologiaOrigen.getBasicaId()
                                            .compareTo(tecnoFOU.getBasicaId()) == 0)) {

                                        //Buscamos el estrato de la direccion
                                        CmtDireccionMgl direccionCcmm = ccmm.getDireccionPrincipal();
                                        if (direccionCcmm != null && direccionCcmm.getDireccionId() != null) {
                                            DireccionMgl dir = direccionCcmm.getDireccionObj();
                                            if (dir != null) {
                                                estratoOri = dir.getDirEstrato() != null ? dir.getDirEstrato().toString() : "NA";
                                            }
                                        }
                                        //determinamos el destino
                                        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                                        CmtDireccionMgl direccionCcmmDestino = cmtDireccionMglManager.
                                                findCmtIdDireccion(detalladaMglTraslado.getDireccionId());
                                        //Buscamos el estrato de la direccion destino
                                        String estratoDest = "NA";
                                        if (direccionCcmmDestino != null && direccionCcmmDestino.getDireccionId() != null) {
                                            //la direccion destino es CCMM
                                            DireccionMgl dirDes = direccionCcmmDestino.getDireccionObj();
                                            if (dirDes != null) {
                                                estratoDest = dirDes.getDirEstrato() != null ? dirDes.getDirEstrato().toString() : "NA";
                                            }
                                            if (estratoOri.equalsIgnoreCase(estratoDest)) {
                                                //Busco tecno sub de la CCMM
                                                CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                                                CmtSubEdificioMgl edificio = direccionCcmmDestino.getCuentaMatrizObj().
                                                        getSubEdificioGeneral();
                                                List<CmtTecnologiaSubMgl> tecnologys = tecnologiaSubMglManager.
                                                        findTecnoSubBySubEdiTec(edificio, tecnologiaOrigen);
                                                if (tecnologys != null && !tecnologys.isEmpty()) {
                                                    CmtTecnologiaSubMgl tecnoDestino = null;
                                                    for (CmtTecnologiaSubMgl tecno : tecnologys) {
                                                        if (tecno.getBasicaIdTecnologias().getBasicaId().
                                                                compareTo(tecnologiaOrigen.getBasicaId()) == 0) {
                                                            tecnoDestino = tecno;
                                                        }
                                                    }
                                                    //Busco la basica fibra optica
                                                    if (tecnoDestino != null
                                                            && tecnoDestino.getTecnoSubedificioId() != null) {
                                                        if (tecnoDestino.getBasicaIdEstadosTec().
                                                                getIdentificadorInternoApp().
                                                                equalsIgnoreCase(Constant.BASICA_TIPO_TEC_CABLE)
                                                                && tecnoDestino.getBasicaIdEstadosTec().getNombreBasica().
                                                                        trim().equalsIgnoreCase("FIBRA OPTICA")) {
                                                            responses.setMessageType("I");
                                                            responses.setMessage("Factible:");
                                                        } else {
                                                            responses.setMessageType("I");
                                                            responses.setMessage("Factible con Restricción: "
                                                                    + "El estado de la tecnologia destino no se "
                                                                    + "encuentra en FIBRA OPTICA se encuentra en: " + tecnoDestino.getBasicaIdEstadosTec().getNombreBasica() + "");
                                                        }
                                                    } else {
                                                        responses.setMessageType("I");
                                                        responses.setMessage("Factible con Restricción:"
                                                                + " No hay tecnologia igual a la de "
                                                                + "la direccion origen: " + tecnologiaOrigen.getNombreBasica() + "");
                                                    }
                                                } else {
                                                    responses.setMessageType("I");
                                                    responses.setMessage("Factible con Restricción:"
                                                            + " la CCMM destino no tiene tecnologias");
                                                }
                                            } else {
                                                responses.setMessageType("I");
                                                responses.setMessage("Factible con Restricción:"
                                                        + " Estratos diferentes: estrato origen = " + estratoOri + "/ estrato destino = " + estratoDest + "");
                                            }
                                        } else {
                                            //la direccion destino es HHPP
                                            DireccionMgl direccion = detalladaMglTraslado.getDireccion();
                                            SubDireccionMgl subDireccionMgl = detalladaMglTraslado.getSubDireccion();
                                            if (subDireccionMgl != null) {
                                                estratoDest = subDireccionMgl.getSdiEstrato().toString();
                                            } else if (direccion != null) {
                                                estratoDest = direccion.getDirEstrato().toString();
                                            }
                                            if (estratoOri.equalsIgnoreCase(estratoDest)) {
                                                List<HhppMgl> hhppMgls = hhppMglManager.
                                                        findByDirAndSubDir(direccion, subDireccionMgl);
                                                int control = 0;
                                                for (HhppMgl hhppMgl : hhppMgls) {
                                                    NodoMgl nodo = hhppMgl.getNodId();
                                                    if (nodo.getNodTipo().getBasicaId().compareTo(tecnologiaOrigen.getBasicaId()) == 0) {
                                                        control++;
                                                    }
                                                }
                                                if (control > 0) {
                                                    responses.setMessageType("I");
                                                    responses.setMessage("Factible:");
                                                } else {
                                                    responses.setMessageType("I");
                                                    responses.setMessage("Factible con Restricción: "
                                                            + "No hay un HHPP con la misma tecnologia origen: " + tecnologiaOrigen.getNombreBasica() + "");
                                                }
                                            } else {
                                                responses.setMessageType("I");
                                                responses.setMessage("Factible con Restricción:"
                                                        + " Estratos diferentes: estrato origen = "
                                                        + " " + estratoOri + "/ estrato destino = " + estratoDest + "");
                                            }
                                        }
                                    } else {
                                        responses.setMessageType("I");
                                        responses.setMessage("Factible con Restricción:"
                                                + " La tecnologia origen no es GPON  ni FOU es: " + tecnologiaOrigen.getNombreBasica() + "");
                                    }
                                } else if (otDir != null && otDir.getOtHhppId() != null) {
                                    //El origen es HHPP
                                    DireccionMgl direccionOrg = otDir.getDireccionId();
                                    SubDireccionMgl subDireccionOrg = otDir.getSubDireccionId();
                                    if (subDireccionOrg != null) {
                                        estratoOri = subDireccionOrg.getSdiEstrato().toString();
                                    } else if (direccionOrg != null) {
                                        estratoOri = direccionOrg.getDirEstrato().toString();
                                    }
                                    // Busco hhpp de tecnologia GPON/FOU
                                    List<HhppMgl> hhppMgls = hhppMglManager.
                                            findByDirAndSubDir(direccionOrg, subDireccionOrg);
                                    HhppMgl hhppMglOrg = null;
                                    CmtBasicaMgl tecnologiaOrigen = null;

                                    if (hhppMgls != null && !hhppMgls.isEmpty()) {
                                        for (HhppMgl hhppMgl : hhppMgls) {
                                            NodoMgl nodoMgl = hhppMgl.getNodId();
                                            if (tecnoGPON != null && tecnoFOU != null) {
                                                if ((nodoMgl.getNodTipo().getBasicaId().
                                                        compareTo(tecnoGPON.getBasicaId()) == 0)
                                                        || (nodoMgl.getNodTipo().getBasicaId().
                                                                compareTo(tecnoFOU.getBasicaId()) == 0)) {
                                                    hhppMglOrg = hhppMgl;
                                                    tecnologiaOrigen = nodoMgl.getNodTipo();
                                                }
                                            }
                                        }
                                        if (hhppMglOrg != null && tecnologiaOrigen != null) {

                                            //determinamos el destino
                                            CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                                            CmtDireccionMgl direccionCcmmDestino = cmtDireccionMglManager.
                                                    findCmtIdDireccion(detalladaMglTraslado.getDireccionId());
                                            //Buscamos el estrato de la direccion destino
                                            String estratoDest = "NA";
                                            if (direccionCcmmDestino != null && direccionCcmmDestino.getDireccionId() != null) {
                                                //la direccion destino es CCMM
                                                DireccionMgl dirDes = direccionCcmmDestino.getDireccionObj();
                                                if (dirDes != null) {
                                                    estratoDest = dirDes.getDirEstrato() != null ? dirDes.getDirEstrato().toString() : "NA";
                                                }
                                                if (estratoOri.equalsIgnoreCase(estratoDest)) {
                                                    //Busco tecno sub de la CCMM
                                                    CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                                                    CmtSubEdificioMgl edificio = direccionCcmmDestino.getCuentaMatrizObj().
                                                            getSubEdificioGeneral();
                                                    List<CmtTecnologiaSubMgl> tecnologys = tecnologiaSubMglManager.
                                                            findTecnoSubBySubEdiTec(edificio, tecnologiaOrigen);
                                                    if (tecnologys != null && !tecnologys.isEmpty()) {
                                                        CmtTecnologiaSubMgl tecnoDestino = null;
                                                        for (CmtTecnologiaSubMgl tecno : tecnologys) {
                                                            if (tecno.getBasicaIdTecnologias().getBasicaId().
                                                                    compareTo(tecnologiaOrigen.getBasicaId()) == 0) {
                                                                tecnoDestino = tecno;
                                                            }
                                                        }
                                                        //Busco la basica fibra optica
                                                        if (tecnoDestino != null
                                                                && tecnoDestino.getTecnoSubedificioId() != null) {
                                                            if (tecnoDestino.getBasicaIdEstadosTec().
                                                                    getIdentificadorInternoApp().
                                                                    equalsIgnoreCase(Constant.BASICA_TIPO_TEC_CABLE)
                                                                    && tecnoDestino.getBasicaIdEstadosTec().getNombreBasica().
                                                                            trim().equalsIgnoreCase("FIBRA OPTICA")) {
                                                                responses.setMessageType("I");
                                                                responses.setMessage("Factible:");
                                                            } else {
                                                                responses.setMessageType("I");
                                                                responses.setMessage("Factible con Restricción: "
                                                                        + "El estado de la tecnologia destino no se "
                                                                        + "encuentra en FIBRA OPTICA se encuentra en: " + tecnoDestino.getBasicaIdEstadosTec().getNombreBasica() + "");
                                                            }
                                                        } else {
                                                            responses.setMessageType("I");
                                                            responses.setMessage("Factible con Restricción:"
                                                                    + " No hay tecnologia igual a la de "
                                                                    + "la direccion origen: " + tecnologiaOrigen.getNombreBasica() + "");
                                                        }
                                                    } else {
                                                        responses.setMessageType("I");
                                                        responses.setMessage("Factible con Restricción:"
                                                                + " la CCMM destino no tiene tecnologias");
                                                    }
                                                } else {
                                                    responses.setMessageType("I");
                                                    responses.setMessage("Factible con Restricción:"
                                                            + " Estratos diferentes: estrato origen = " + estratoOri + "/ estrato destino = " + estratoDest + "");
                                                }
                                            } else {
                                                //la direccion destino es HHPP
                                                DireccionMgl direccion = detalladaMglTraslado.getDireccion();
                                                SubDireccionMgl subDireccionMgl = detalladaMglTraslado.getSubDireccion();
                                                if (subDireccionMgl != null) {
                                                    estratoDest = subDireccionMgl.getSdiEstrato().toString();
                                                } else if (direccion != null) {
                                                    estratoDest = direccion.getDirEstrato().toString();
                                                }
                                                if (estratoOri.equalsIgnoreCase(estratoDest)) {
                                                    List<HhppMgl> hhppMgls1 = hhppMglManager.
                                                            findByDirAndSubDir(direccion, subDireccionMgl);
                                                    int control = 0;
                                                    for (HhppMgl hhppMgl : hhppMgls1) {
                                                        NodoMgl nodo = hhppMgl.getNodId();
                                                        if (nodo.getNodTipo().getBasicaId().compareTo(tecnologiaOrigen.getBasicaId()) == 0) {
                                                            control++;
                                                        }
                                                    }
                                                    if (control > 0) {
                                                        responses.setMessageType("I");
                                                        responses.setMessage("Factible:");
                                                    } else {
                                                        responses.setMessageType("I");
                                                        responses.setMessage("Factible con Restricción: "
                                                                + "No hay un HHPP con la misma tecnologia origen: " + tecnologiaOrigen.getNombreBasica() + "");
                                                    }
                                                } else {
                                                    responses.setMessageType("I");
                                                    responses.setMessage("Factible con Restricción:"
                                                            + " Estratos diferentes: estrato origen = "
                                                            + " " + estratoOri + "/ estrato destino = " + estratoDest + "");
                                                }
                                            }
                                        } else {
                                            responses.setMessageType("I");
                                            responses.setMessage("Factible con Restricción: "
                                                    + "La direccion origen no tiene un HHPP con tecnologia GPON u FOU");
                                        }
                                    } else {
                                        responses.setMessageType("I");
                                        responses.setMessage("Factible con Restricción: "
                                                + "La direccion origen no tiene HHPP para su validacion");
                                    }
                                }
                            } else {
                                responses.setMessageType("E");
                                responses.setMessage("No se encontraron ordenes de trabajo"
                                        + " ni informacion con el enlace: " + trasladoRequest.getIdEnlace() + " ingresado");
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException ex) {
            responses.setMessageType("E");
            responses.setMessage(ex.getMessage());
        }
        return responses;
    }

    public boolean validacionesTrasladoDir(AppConsultarFactibilidadTrasladoRequest request) throws ApplicationException {

        //Buscamos el destino
        CmtDireccionDetalleMglManager detalleMglManager = new CmtDireccionDetalleMglManager();

        if (request.getDireccionTabulada().getIdTipoDireccion() == null
                || request.getDireccionTabulada().getIdTipoDireccion().isEmpty()) {
            String msg = "Error: ".concat(Constant.MSG_TIPO_DIR_TABULADA);
            throw new ApplicationException(msg);
        }

        if (!request.getDireccionTabulada().getIdTipoDireccion().equalsIgnoreCase("CK")
                && !request.getDireccionTabulada().getIdTipoDireccion().equalsIgnoreCase("BM")
                && !request.getDireccionTabulada().getIdTipoDireccion().equalsIgnoreCase("IT")) {
            String msg = "Error: ".concat(Constant.MSG_TIPO_DIR_TABULADA);
            throw new ApplicationException(msg);
        }

        DrDireccion direccionTabulada = detalleMglManager.
                parseCmtDireccionTabuladaDtoToDrDireccion(request.getDireccionTabulada());

        centroPobladoTraslado = detalleMglManager.buscarCiudad(request.getCodigoDane());

        if (centroPobladoTraslado != null && centroPobladoTraslado.getGpoId() != null) {
            List<CmtDireccionDetalladaMgl> listDetalladas;
            listDetalladas = detalleMglManager.buscarDireccionTabuladaUnica(centroPobladoTraslado.getGpoId(), direccionTabulada);
            if (listDetalladas != null && !listDetalladas.isEmpty()) {
                detalladaMglTraslado = listDetalladas.get(0);
            } else {
                //Creamos la direccion 
                if (detalleMglManager.validarCreacionDireccion(direccionTabulada)) {
                    listDetalladas = crearDireccionConsultada(direccionTabulada, centroPobladoTraslado, request.getUser());
                    if (listDetalladas != null && !listDetalladas.isEmpty()) {
                        detalladaMglTraslado = listDetalladas.get(0);
                    } else {
                        String msg = "No fue posible crear la direccion ingresada en MGL";
                        throw new ApplicationException(msg);
                    }
                } else {
                    String msg = "Error: ".concat(Constant.MSG_CAMPOS_OBLIGATORIOS_DIR_ERROR);
                    throw new ApplicationException(msg);
                }
            }
        } else {
            String msg = "No se encontro centro poblado con el codigo "
                    + " dane ingresado: " + request.getCodigoDane() + " en MGL";
            throw new ApplicationException(msg);
        }

        return true;
    }

    /**
     * Función que realiza la creacion de una direccion en las tablas direccion,
     * subdireccion, direcciondetallada
     *
     * @author Victor Bocanegra
     * @param centroPobladoSeleccionado
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> crearDireccionConsultada(DrDireccion direccionConstruida,
            GeograficoPoliticoMgl centroPobladoSeleccionado, String user) throws ApplicationException {
        try {
            List<CmtDireccionDetalladaMgl> resultado = null;
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String address = drDireccionManager.getDireccion(direccionConstruida);
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCodDaneVt(centroPobladoSeleccionado.getGeoCodigoDane());
            addressRequest.setAddress(address);
            addressRequest.setCity(centroPobladoSeleccionado.getGeoCodigoDane());
            addressRequest.setState("");

            if (direccionConstruida.getIdTipoDireccion() != null
                    && direccionConstruida.getIdTipoDireccion().equalsIgnoreCase("CK")
                    && centroPobladoSeleccionado.getGpoMultiorigen() != null
                    && centroPobladoSeleccionado.getGpoMultiorigen().equalsIgnoreCase("1")
                    && direccionConstruida.getBarrio() != null) {
                addressRequest.setNeighborhood(direccionConstruida.getBarrio());
            }
            addressRequest.setTipoDireccion("CK");

            //JDHT Se pone en true para indicar que es una direccion creada desde factibilidad
            direccionConstruida.setDireccionFactibilidad(false);
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            ResponseMessage responseMessageCreateDir
                    = addressEJBRemote.createAddress(addressRequest,
                            user, "MGL", "", direccionConstruida);

            if (responseMessageCreateDir != null
                    && responseMessageCreateDir.getMessageType().equalsIgnoreCase(ResponseMessage.TYPE_ERROR)
                    && responseMessageCreateDir.isDireccionAntiguaFactibilidad()) {

                String msg = address + " " + responseMessageCreateDir.getMessageText();
                throw new ApplicationException(msg);
            }

            if (responseMessageCreateDir != null && responseMessageCreateDir.getIdaddress() != null
                    && !responseMessageCreateDir.getIdaddress().trim()
                            .isEmpty()) {
                if (responseMessageCreateDir.getNuevaDireccionDetallada() != null
                        && responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionDetalladaId() != null) {
                    resultado = new ArrayList<>();
                    resultado.add(responseMessageCreateDir.getNuevaDireccionDetallada());
                }

            }
            return resultado;

        } catch (ApplicationException | ExceptionDB ex) {
            LOGGER.error("Error en crearDireccionConsultada " + ex.getMessage(), ex);
            throw new ApplicationException("Error en crearDireccionConsultada " + ex.getMessage(), ex);
        }
    }

    private AddressEJBRemote lookupaddressEJBBean() throws ApplicationException {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error("Error en lookupaddressEJBBean. " + ne.getMessage(), ne);
            throw new ApplicationException(ne);
        }
    }

    /**
     * Función utilizada para crear el Hhpp con las tecnologias pertenecientes a
     * la Ot, bajo el estado de la razon
     *
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void crearHhppApartirDeLaRazon(String user) throws ApplicationException {
        try {

            boolean habilitarRR = false;
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            CmtDireccionDetalleMglManager detalleMglManager = new CmtDireccionDetalleMglManager();

            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }

            SolicitudManager solicitudManager = new SolicitudManager();
            DireccionRRManager direccionRRManager;

            //lista de tecnologias a las cual se les puede crear el Hhpp
            List<OtHhppTecnologiaMgl> tecnologiasHabilitadasParaCreacionList
                    = obtenerTecnologiasViablesParaCreacionDeHhpp();

            if (!tecnologiasHabilitadasParaCreacionList.isEmpty()) {
                for (OtHhppTecnologiaMgl tecnologia : tecnologiasHabilitadasParaCreacionList) {

                    //Solicitud a procesar
                    Solicitud solicitudDth = tecnologia.getOtHhppId().getSolicitudId();
                    boolean sincronizarConRr = sincronizarConRr(tecnologia.getSincronizaRr());

                    if (solicitudDth == null) {

                        //Nodo de la tecnologia
                        String nodo = tecnologia.getNodo().getNodCodigo();

                        //Obtiene el tipo de Solicitud (carpeta RR)
                        String tipoSolicitud = solicitudManager.obtenerCarpetaTipoDireccion(tecnologia.getTecnoglogiaBasicaId());
                        DireccionMgl direccion = tecnologia.getOtHhppId().getDireccionId();
                        BigDecimal subDireccion = null;

                        if (tecnologia.getOtHhppId().getSubDireccionId() != null) {
                            subDireccion = tecnologia.getOtHhppId().getSubDireccionId().getSdiId();
                        }
                        List<CmtDireccionDetalladaMgl> direccionDetalladas
                                = detalleMglManager.findDireccionDetallaByDirIdSdirId(direccion.getDirId(), subDireccion);
                        DrDireccion drDireccion1
                                = detalleMglManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetalladas.get(0));

                        drDireccion1.setEstrato(tecnologia.getOtHhppId().getDireccionId().getDirEstrato().toString());
                        //obtiene el estrato de la direccion
                        String estrato = solicitudManager.validarEstrato(drDireccion1);

                        GeograficoPoliticoMgl centroPoblado = direccion.getUbicacion().getGpoIdObj();

                        //obtener instancia inyectandole parametros en el constructor
                        direccionRRManager = obtenerInstanciaDireccionRRManager(centroPoblado.getGeoGpoId(), drDireccion1);

                        direccionRRManager.registrarHHPP_Inspira_Independiente(
                                nodo, nodo,
                                user, tipoSolicitud,
                                Constant.FUNCIONALIDAD_DIRECCIONES,
                                estrato, false,
                                null, Constant.RESULTADO_HHPP_CREADO,
                                user,
                                centroPoblado.getGeoGpoId(),
                                sincronizarConRr,
                                "", null);
                    } else {
                        //Obtiene el tipo de Solicitud (carpeta RR)
                        String tipoSolicitud = solicitudManager.obtenerCarpetaTipoDireccion(solicitudDth.getTecnologiaId());

                        CmtDireccionDetalladaMgl direccionDetallada
                                = detalleMglManager
                                        .buscarDireccionIdDireccion(solicitudDth.getDireccionDetallada()
                                                .getDireccionDetalladaId());

                        DrDireccion drDireccion2
                                = detalleMglManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);

                        drDireccion2.setEstrato(solicitudDth.getEstratoAntiguo());
                        //obtiene el estrato de la direccion
                        String estrato = solicitudManager.validarEstrato(drDireccion2);

                        String dirAntiguaFormatoIgac = obtenerDireccionAntiguaFormatoIgac(solicitudDth);

                        //obtener instancia inyectandole parametros en el constructor
                        direccionRRManager = obtenerInstanciaDireccionRRManager(solicitudDth.getCentroPobladoId(), drDireccion2);

                        direccionRRManager.registrarHHPP_Inspira(
                                tecnologia.getNodo(),
                                user, tipoSolicitud,
                                Constant.FUNCIONALIDAD_DIRECCIONES,
                                estrato, false,
                                solicitudDth.getIdSolicitud().toString(),
                                solicitudDth.getTipoSol(),
                                solicitudDth.getCiudad(),
                                solicitudDth.getRespuesta(),
                                solicitudDth.getSolicitante(),
                                solicitudDth.getRptGestion(),
                                user,
                                solicitudDth.getContacto(),
                                solicitudDth.getTelContacto(),
                                dirAntiguaFormatoIgac, solicitudDth.getCentroPobladoId(),
                                sincronizarConRr, "", null, habilitarRR,null,false);
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en crearHhppApartirDeLaRazon. " + e.getMessage());
            throw new ApplicationException("Error en crearHhppApartirDeLaRazon: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en crearHhppApartirDeLaRazon. " + e.getMessage());
            throw new ApplicationException("Error en crearHhppApartirDeLaRazon: " + e.getMessage(), e);
        }
    }

    /**
     * Función usada para comparar los Hhpp ya registrados con los que estan
     * siendo solicitados para la creacion
     *
     * @author Orlando Velasquez Diaz
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<OtHhppTecnologiaMgl> obtenerTecnologiasViablesParaCreacionDeHhpp() throws ApplicationException {

        try {
            //Lista de tecnologias asociadas a la Ot Direcciones
            OtHhppTecnologiaMglManager tecnologiaMglManager = new OtHhppTecnologiaMglManager();
            List<OtHhppTecnologiaMgl> tecnologiasViablesOtList
                    = tecnologiaMglManager.findOtHhppTecnologiaByOtHhppId(
                            ordenHhppMglApp.getOtHhppId());
            SubDireccionMgl subdireccion = null;

            //valida si existe una subdireccion para esta Ot
            if (ordenHhppMglApp.getSubDireccionId() != null) {
                subdireccion = ordenHhppMglApp.getSubDireccionId();
            }

            //Busca Hhpp asociados a la direccion y subdireccion de la Ot
            List<HhppMgl> hhpps = hhppMglManager.findByDirAndSubDir(
                    ordenHhppMglApp.getDireccionId(), subdireccion);

            //Recorre los Hhpp encontrados y si se encuentra tecnologias iguales en la
            //peticion se retira de la lista y se notifica que ya existen dichos Hhpp
            //para la direccion
            for (HhppMgl hhpp : hhpps) {

                for (int i = 0; i < tecnologiasViablesOtList.size(); i++) {

                    OtHhppTecnologiaMgl tecnologiaOtDirecciones = tecnologiasViablesOtList.get(i);

                    if (hhpp.getNodId().getNodTipo().getBasicaId().compareTo(
                            tecnologiaOtDirecciones.getTecnoglogiaBasicaId().getBasicaId()) == 0) {
                        tecnologiasViablesOtList.remove(tecnologiaOtDirecciones);

                    }

                }
            }

            return tecnologiasViablesOtList;
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerTecnologiasViablesParaCreacionDeHhpp " + e.getMessage());
            throw new ApplicationException("Error en obtenerTecnologiasViablesParaCreacionDeHhpp: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerTecnologiasViablesParaCreacionDeHhpp " + e.getMessage());
            throw new ApplicationException("Error en obtenerTecnologiasViablesParaCreacionDeHhpp: " + e.getMessage(), e);
        }
    }

    /**
     * Función usada para obtener el valor si debe o no sincronizar en Rr
     *
     * @author Victor Bocanegra
     * @param sincronizar
     *
     * @return
     */
    public boolean sincronizarConRr(String sincronizar) {
        return sincronizar.equalsIgnoreCase("1");
    }

    /**
     * Función que obtiene la instancia de DireccionRRManager
     *
     * @author Victor Bocanegra
     * @param centroPobladoId
     * @param drDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DireccionRRManager obtenerInstanciaDireccionRRManager(BigDecimal centroPobladoId, DrDireccion drDireccion)
            throws ApplicationException {

        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        DetalleDireccionEntity detalleDireccionEntity1;
        detalleDireccionEntity1 = drDireccion.convertToDetalleDireccionEntity();
        //obtenemos el centro poblado de la solicitud para conocer si la ciudad es multiorigen
        GeograficoPoliticoMgl centroPobladoSolicitud;
        centroPobladoSolicitud = geograficoPoliticoManager.findById(centroPobladoId);

        //Conocer si es multi-origen         
        detalleDireccionEntity1.setMultiOrigen(centroPobladoSolicitud.getGpoMultiorigen());
        //obtener la direccion en formato RR
        return new DireccionRRManager(detalleDireccionEntity1);
    }

    /**
     * Función que valida si la solicitud tiene una dirección antigua para
     * obtener las unidades de la antigua dirección
     *
     * @author Victor Bocanegra
     * @param solicitud
     * @return
     */
    public String obtenerDireccionAntiguaFormatoIgac(Solicitud solicitud) {
        String dirAntiguaFormatoIgac = "";
        if (solicitud.getDireccionAntiguaIgac() != null
                && !solicitud.getDireccionAntiguaIgac().isEmpty()) {

            String[] direccionAntigua = solicitud
                    .getDireccionAntiguaIgac().trim().split("&");
            String calleAntigua = direccionAntigua[0];
            String casaAntigua = direccionAntigua[1];

            dirAntiguaFormatoIgac = calleAntigua
                    + " " + casaAntigua;
        }

        return dirAntiguaFormatoIgac;
    }

    public boolean validarCreacion() throws ApplicationException {

        boolean respuesta = true;
        try {
            String subTipoWorForce = retornaSubtipoWorfoce(ordenCcmmMglApp);
            //Consultamos si la orden tiene agendas pendientes
            List<CmtAgendamientoMgl> agendasCons = agendamientoMglManager.
                    agendasPorOrdenAndSubTipoWorkfoce(ordenCcmmMglApp, subTipoWorForce);
            if (agendasCons != null && !agendasCons.isEmpty()) {
                for (CmtAgendamientoMgl agendasFor : agendasCons) {
                    if (agendasFor.getUltimaAgenda().equalsIgnoreCase("Y")) {
                        respuesta = false;
                    }
                }
            }
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return respuesta;
    }

    public String retornaSubtipoWorfoce(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        try {
            if (ot != null) {
                CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglManager.
                        findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(),
                                ot.getEstadoInternoObj(),
                                ot.getBasicaIdTecnologia());

                if (cmtEstadoxFlujoMgl != null) {
                    if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                        subtipoWorfoce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                    }
                }
            }
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return subtipoWorfoce;
    }

    public boolean validarExistenciaAgendaCerrada() throws ApplicationException {
        List<MglAgendaDireccion> agendasAsociadasAlaOt
                = agendamientoHhppMglManager.buscarAgendasPorOt(otDirModMglApp);

        if (tipoOtHhppMglApp.getIsMultiagenda().equalsIgnoreCase("S")) {
            for (MglAgendaDireccion agendaLis : agendasAsociadasAlaOt) {
                if (agendaLis.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equals(Constant.ESTADO_AGENDA_CERRADA)
                        && agendaLis.getUltimaAgenda().equalsIgnoreCase("Y")) {
                    return true;
                }
            }
        } else {
            for (MglAgendaDireccion agendaLis : agendasAsociadasAlaOt) {
                if (agendaLis.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                        .equals(Constant.ESTADO_AGENDA_CERRADA)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void buscarOtByEnlace(String idEnlace) throws ApplicationException {

        CmtBasicaMgl tecnoGPON = basicaMglManager.findByCodigoInternoApp(Constant.FIBRA_OP_GPON);
        CmtBasicaMgl tecnoFOU = basicaMglManager.findByCodigoInternoApp(Constant.FIBRA_OP_UNI);

        List<BigDecimal> idTecno = new ArrayList<>();
        if (tecnoGPON != null && tecnoGPON.getBasicaId() != null) {
            idTecno.add(tecnoGPON.getBasicaId());
        }
        if (tecnoFOU != null && tecnoFOU.getBasicaId() != null) {
            idTecno.add(tecnoFOU.getBasicaId());
        }
        List<CmtOrdenTrabajoMgl> ordenTrabajoMgls = ordenTrabajoMglManager.
                findByIdEnlaceAndTecnologia(idEnlace, idTecno);
        if (ordenTrabajoMgls != null && !ordenTrabajoMgls.isEmpty()) {
            ordenCcmmMglApp = ordenTrabajoMgls.get(0);
            tecnoMglApp = ordenCcmmMglApp.getBasicaIdTecnologia();
        } else {
            //Si no encuentro en ordenes de CCMM 
            //Busco en la tabla de ordenes de direcciones 
            List<OtHhppMgl> ordeHhppMgls = otHhppMglManager.
                    findOtHhppByIdEnlaceAndTecnologias(idEnlace, idTecno);
            if (ordeHhppMgls != null && !ordeHhppMgls.isEmpty()) {
                ordenHhppMglApp = ordeHhppMgls.get(0);
                OtHhppTecnologiaMglManager otHhppTecnologiaMglManager = new OtHhppTecnologiaMglManager();
                List<OtHhppTecnologiaMgl> lstTecnologias = otHhppTecnologiaMglManager.
                        findOtHhppTecnologiaByOtHhppId(ordenHhppMglApp.getOtHhppId());
                if (lstTecnologias != null && !lstTecnologias.isEmpty()) {
                    listTecnologias = new ArrayList<>();
                    lstTecnologias.forEach((otHhppTecnologiaMgl) -> {
                        CmtBasicaMgl cmtBasicaMglTec = new CmtBasicaMgl();
                        cmtBasicaMglTec.setBasicaId(otHhppTecnologiaMgl.getNodo().getNodTipo().getBasicaId());
                        cmtBasicaMglTec.setNodoGestion(otHhppTecnologiaMgl.getNodo().getNodCodigo());
                        cmtBasicaMglTec.setSincronizaRr(false);
                        listTecnologias.add(cmtBasicaMglTec);
                    });
                    tecnoMglApp = listTecnologias.get(0);
                }
            } else {
                //Si no encuentro en ordenes de HHPP 
                //Busco en la tabla de ONIX
                OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
                List<OnyxOtCmDir> onyxOtCmBD = onyxOtCmDirManager.
                        findOnyxByIdEnlace(idEnlace);

                if (onyxOtCmBD != null && !onyxOtCmBD.isEmpty()) {
                    OnyxOtCmDir onyxOt = onyxOtCmBD.get(0);
                    CmtOrdenTrabajoMgl ot = onyxOt.getOt_Id_Cm();
                    OtHhppMgl otDir = onyxOt.getOt_Direccion_Id();
                    if (ot != null && ot.getIdOt() != null
                            && tecnoGPON != null && tecnoFOU != null) {
                        ordenCcmmMglApp = ot;
                        tecnoMglApp = ordenCcmmMglApp.getBasicaIdTecnologia();
                    } else if (otDir != null && otDir.getOtHhppId() != null) {
                        //El origen es HHPP
                        ordenHhppMglApp = otDir;
                        OtHhppTecnologiaMglManager otHhppTecnologiaMglManager = new OtHhppTecnologiaMglManager();
                        List<OtHhppTecnologiaMgl> lstTecnologias = otHhppTecnologiaMglManager.
                                findOtHhppTecnologiaByOtHhppId(ordenHhppMglApp.getOtHhppId());
                        if (lstTecnologias != null && !lstTecnologias.isEmpty()) {
                            listTecnologias = new ArrayList<>();
                            lstTecnologias.forEach((otHhppTecnologiaMgl) -> {
                                CmtBasicaMgl cmtBasicaMglTec = new CmtBasicaMgl();
                                cmtBasicaMglTec.setBasicaId(otHhppTecnologiaMgl.getNodo().getNodTipo().getBasicaId());
                                cmtBasicaMglTec.setNodoGestion(otHhppTecnologiaMgl.getNodo().getNodCodigo());
                                cmtBasicaMglTec.setSincronizaRr(false);
                                listTecnologias.add(cmtBasicaMglTec);
                            });
                            tecnoMglApp = listTecnologias.get(0);
                        }
                    }
                } else {
                    throw new ApplicationException("No se encontraron ordenes de trabajo"
                            + " ni informacion con el enlace: " + idEnlace + " ingresado");
                }
            }
        }
    }
    
    public int creacionOtdireccionDestino(String idDireccion) throws ApplicationException {

        //Busqueda de la direccion detallada
        Pattern patDetaNum = Pattern.compile("[0-9]*");
        Matcher DetaNum = patDetaNum.matcher(idDireccion);
        int control;

        if (DetaNum.matches()) {
            CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();
            detalladaMglApp = direccionDetalleMglManager.buscarDireccionIdDireccion(new BigDecimal(idDireccion));
                if (detalladaMglApp != null && detalladaMglApp.getDireccionDetalladaId() != null) {
                //Verificamos si la dierccion pertenece a una CCMM o HHPP
                CmtDireccionMglManager direccionMglManager = new CmtDireccionMglManager();
                CmtDireccionMgl direccionDes = direccionMglManager.
                        findCmtIdDireccion(detalladaMglApp.getDireccion().getDirId());
                if (direccionDes != null && direccionDes.getDireccionId() != null) {
                    //Direccion destino es CCMM
                    cuentaMatrizMglApp = direccionDes.getCuentaMatrizObj();
                    control = 1;
                } else {
                    if (detalladaMglApp.getCpTipoNivel5() == null
                            && detalladaMglApp.getCpTipoNivel6() == null) {
                        throw new ApplicationException("Debe solicitar creacion de la CCMM  previamente "
                                + "para la direccion: " + detalladaMglApp.getDireccionTexto() + ".");
                    } else {
                        //Busco el hhpp
                        if (tecnoMglApp != null) {
                            lstHhppMglApp = hhppMglManager.
                                    findHhppByTecnoAndDireccionAndSubDireccion(tecnoMglApp.getBasicaId(), detalladaMglApp.
                                            getDireccion().getDirId(),
                                            detalladaMglApp.getSubDireccion().getSdiId());
                            if (lstHhppMglApp == null) {
                                throw new ApplicationException("Debe solicitar creacion del HHPP  previamente "
                                        + "para la direccion: " + detalladaMglApp.getDireccionTexto() + ".");
                            } else {
                                if (listTecnologias != null) {
                                    if (lstHhppMglApp.get(0).getNodId() != null) {
                                        tecnoMglApp.setNodoGestion(lstHhppMglApp.get(0).getNodId().getNodCodigo());
                                        listTecnologias.add(tecnoMglApp);
                                    } else {
                                        throw new ApplicationException("El Hhpp de la dirección no cuenta con Nodo");
                                    }
                                    listTecnologias.add(tecnoMglApp);
                                } else {
                                    listTecnologias = new ArrayList();
                                    if (lstHhppMglApp.get(0).getNodId() != null) {
                                        tecnoMglApp.setNodoGestion(lstHhppMglApp.get(0).getNodId().getNodCodigo());
                                        listTecnologias.add(tecnoMglApp);
                                    } else {
                                        throw new ApplicationException("El Hhpp de la dirección no cuenta con Nodo");
                                    }

                                }
                                control = 2;
                            }
                        } else {
                            throw new ApplicationException("No existe una tecnologia "
                                    + "GPON y/o UNIFILAR para la busqueda del hhpp.");
                        }

                    }
                }
            } else {
                throw new ApplicationException("No existe direccion con id: "
                        + " " + idDireccion + " en MGL.");
            }
        } else {
            throw new ApplicationException("El id de direccion detallada ingresada: "
                    + " " + idDireccion + " no es numerico.");
        }
        return control;
    }
}
