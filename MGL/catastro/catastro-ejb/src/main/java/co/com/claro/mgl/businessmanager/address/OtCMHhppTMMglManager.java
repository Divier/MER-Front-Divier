/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

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
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.app.dtos.AppCrearAgendaOtRequest;
import co.com.claro.app.dtos.AppCrearOtRequest;
import co.com.claro.app.dtos.AppResponseCrearOtDto;
import co.com.claro.app.dtos.AppResponsesAgendaDto;
import co.com.claro.cmas400.ejb.respons.ResponseDataOtEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mgl.businessmanager.cm.CmtAgendamientoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtCuentaMatrizMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtEstadoxFlujoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtNotaOtMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtOpcionesRolMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTecnologiaSubMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoOtMglManager;
import co.com.claro.mgl.businessmanager.cm.UsRolPerfilManager;
import co.com.claro.mgl.businessmanager.cm.UsuariosManager;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import co.com.claro.mgl.dao.impl.OtHhppMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtDireccionDetalleMglDaoImpl;

import co.com.claro.mgl.dao.impl.cm.CmtOrdenTrabajoMglDaoImpl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import co.com.claro.mgl.jpa.entities.cm.UsRolPerfil;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.rest.dtos.CmtAddressResponseDto;
import co.com.claro.mgl.rest.dtos.CmtDireccionDetalladaRequestDto;
import co.com.claro.mgl.rest.dtos.CmtHhppDto;
import co.com.claro.mgl.rest.dtos.DireccionCCMMHHPPDto;

import co.com.claro.mgl.utils.ValidacionUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Objetivo: Clase que permite la creacion y el agendamiento de ordenes
 * Descripcion: Metodos ejecutados por medio de servicios desde Service Manager
 *
 * @author Divier Casas
 * @versión 1.0
 */
public class OtCMHhppTMMglManager {

    private static final Logger LOGGER = LogManager.getLogger(OtCMHhppTMMglManager.class);

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
    private CmtCuentaMatrizMgl cuentaMatrizMglApp;
    private CmtOrdenTrabajoMgl ordenCcmmMglApp;
    private CmtAgendamientoMgl agendaOtCcmm;
    private MglAgendaDireccion agendaOtHhpp;
    private OtHhppMgl ordenHhppMglApp;
    private OtHhppMgl otDirModMglApp;
    private String codCuentaPar;
    private List<CmtAgendamientoMgl> agendasOtCcmm;
    private List<MglAgendaDireccion> agendasOtHhpp;
    private String mensajesValidacion;
    private String subtipoWorfoce = null;
    private String subtipoOfsc = null;
    private BigDecimal cedulaUser;
    private final int MAX_CARACTERES_NUMERO_OT_RR = 5;

    private final int MAX_CARACTERES_ESTADO_OT = 6;
    private final String FORMULARIO_MENU = "MENU";
    private final String FORMULARIO_AGENDAS = "AGENDAMIENTOSOT";

    private final String OPCION_CREAR_OT_CM = "menuCmCreaOtVt";
    private final String OPCION_GESTION_OT_HHPP = "menuHhppGestionOt";
    private final String CCMM = "CCMM";
    private final String HHPP = "HHPP";
    private final String MENSAJE_VAL = "No se Encuentra dirección por favor continue con el proceso Actual";
    private final String MANTENIMIENTO = "Mantenimiento";
    private final String SIGLA_ERROR = "E";
    private final String SIGLA_INFO = "I";
    private final String FOG = "FOG";
    private final String FOU = "FOU";

    private final CmtDireccionDetalleMglManager cmtDireccionDetalle = new CmtDireccionDetalleMglManager();
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
     * Metodo alterno de Creacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Divier Casas
     * @version 1.0 revision
     * @param appCrearOtRequest
     * @return AppResponseCrearOtDto
     */
    public AppResponseCrearOtDto createOrderSupport(AppCrearOtRequest appCrearOtRequest) {

        AppResponseCrearOtDto appResponseCrearOtDto = new AppResponseCrearOtDto();

        try {
            if (appCrearOtRequest.getOrdenPurpose() != null
                    && appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase(MANTENIMIENTO)) {

                CmtDireccionDetalladaRequestDto cmtDireccionDetalladaRequestDto = new CmtDireccionDetalladaRequestDto();
                cmtDireccionDetalladaRequestDto.setIdDireccion(new BigDecimal(appCrearOtRequest.getIdDirDetalladaDestino()));

                CmtDireccionDetalleMglDaoImpl direccionDetalleMglDaoImpl = new CmtDireccionDetalleMglDaoImpl();
                List<DireccionCCMMHHPPDto> lsDireccionCCMMHHPPDto = direccionDetalleMglDaoImpl.buscarDireccionCCMMHHPP(cmtDireccionDetalladaRequestDto.getIdDireccion());
                DireccionCCMMHHPPDto direccionCCMMHHPPDto = lsDireccionCCMMHHPPDto != null && !lsDireccionCCMMHHPPDto.isEmpty() ? lsDireccionCCMMHHPPDto.get(0) : new DireccionCCMMHHPPDto();
                
                CmtAddressResponseDto cmtAddressResponseDto = cmtDireccionDetalle.ConsultaDireccion(cmtDireccionDetalladaRequestDto);

                if (cmtAddressResponseDto.getAddresses() == null) {
                    appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                    appResponseCrearOtDto.setMessage(MENSAJE_VAL);
                } else {
                    if (cmtAddressResponseDto.getAddresses().getListHhpps() == null || cmtAddressResponseDto.getAddresses().getListHhpps().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage(MENSAJE_VAL);
                    } else {
                        List<CmtHhppDto> lsCmtHhppDto = cmtAddressResponseDto.getAddresses().getListHhpps();
                        boolean isFOG = false;
                        boolean isFOU = false;
                        Map<String, CmtHhppDto> mCmtHhppDto = new HashMap<>();
                        for (CmtHhppDto cmtHhppDto : lsCmtHhppDto) {
                            if (cmtHhppDto.getTechnology().equalsIgnoreCase(FOG)) {
                                isFOG = true;
                                mCmtHhppDto.put(FOG, cmtHhppDto);
                            }
                            if (cmtHhppDto.getTechnology().equalsIgnoreCase(FOU)) {
                                isFOU = true;
                                mCmtHhppDto.put(FOU, cmtHhppDto);
                            }
                        }
                        if (!isFOG && !isFOU) {
                            appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                            appResponseCrearOtDto.setMessage(MENSAJE_VAL);
                        } else if (isFOG) {
                            BigDecimal ccmmId = null;
                            CmtHhppDto cmtHhppDto = mCmtHhppDto.get(FOG);
                            String tecnology = cmtHhppDto.getTechnology();
                            String codeNode = cmtHhppDto.getNode().getCodeNode();
                            if (direccionCCMMHHPPDto.getTipoCCMMHHPP().equals(CCMM)) {
                                ccmmId = new BigDecimal(direccionCCMMHHPPDto.getCuentaMatrizId());
                                CmtCuentaMatrizMgl cmtCuentaMatrizMgl = new CmtCuentaMatrizMgl();
                                cmtCuentaMatrizMgl.setCuentaMatrizId(ccmmId);
                                ordenCcmmMglApp = ordenTrabajoMglManager.findByIdCm(cmtCuentaMatrizMgl).get(0);
                                CmtTipoBasicaMgl tipoBasicaMgl = tipoBasicaMglManager.
                                        findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
                                tecnoMglApp = basicaMglManager.findByTipoBasicaAndCodigo(tipoBasicaMgl.getTipoBasicaId(), tecnology);
                            } else {
                                ordenHhppMglApp = new OtHhppMgl();
                                CmtTipoBasicaMgl tipoBasicaMgl = tipoBasicaMglManager.
                                        findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
                                tecnoMglApp = basicaMglManager.findByTipoBasicaAndCodigo(tipoBasicaMgl.getTipoBasicaId(), tecnology);
                                tecnoMglApp.setNodoGestion(codeNode);
                                listTecnologias = new ArrayList<>();
                                listTecnologias.add(tecnoMglApp);
                            }
                        } else if (isFOU) {
                            BigDecimal ccmmId = null;
                            CmtHhppDto cmtHhppDto = mCmtHhppDto.get(FOU);
                            String tecnology = cmtHhppDto.getTechnology();
                            String codeNode = cmtHhppDto.getNode().getCodeNode();
                            if (direccionCCMMHHPPDto.getTipoCCMMHHPP().equals(CCMM)) {
                                ccmmId = new BigDecimal(direccionCCMMHHPPDto.getCuentaMatrizId());
                                CmtCuentaMatrizMgl cmtCuentaMatrizMgl = new CmtCuentaMatrizMgl();
                                cmtCuentaMatrizMgl.setCuentaMatrizId(ccmmId);
                                ordenCcmmMglApp = ordenTrabajoMglManager.findByIdCm(cmtCuentaMatrizMgl).get(0);
                                CmtTipoBasicaMgl tipoBasicaMgl = tipoBasicaMglManager.
                                        findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
                                tecnoMglApp = basicaMglManager.findByTipoBasicaAndCodigo(tipoBasicaMgl.getTipoBasicaId(), tecnology);
                            } else {
                                ordenHhppMglApp = new OtHhppMgl();
                                CmtTipoBasicaMgl tipoBasicaMgl = tipoBasicaMglManager.
                                        findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
                                tecnoMglApp = basicaMglManager.findByTipoBasicaAndCodigo(tipoBasicaMgl.getTipoBasicaId(), tecnology);
                                tecnoMglApp.setNodoGestion(codeNode);
                                listTecnologias = new ArrayList<>();
                                listTecnologias.add(tecnoMglApp);
                            }
                        }
                    }
                }

                if (ordenCcmmMglApp != null) {
                    if (appCrearOtRequest.getSegment() == null
                            || appCrearOtRequest.getSegment().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo segment es obligatorio");
                    } else if (appCrearOtRequest.getSuggestedDate() == null
                            || appCrearOtRequest.getSuggestedDate().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo suggestedDate es obligatorio");
                    } else if (appCrearOtRequest.getOtSubType() == null
                            || appCrearOtRequest.getOtSubType().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo otSubType es obligatorio");
                    } else if (appCrearOtRequest.getOtClass() == null
                            || appCrearOtRequest.getOtClass().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo otClass es obligatorio");
                    } else if (appCrearOtRequest.getObservations() == null
                            || appCrearOtRequest.getObservations().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo observations es obligatorio");
                    } else if (appCrearOtRequest.getUser() == null
                            || appCrearOtRequest.getUser().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo user es obligatorio");
                    } else {
                        int tipoOrden = 1;
                        if (appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase(MANTENIMIENTO)) {
                            tipoOrden = 1;
                        }
                        //Todos los campos estan llenos validaciones de negocio
                        if (validacionesCrearOtCcmm(appCrearOtRequest, tipoOrden)) {
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
                            orden.setUsuarioCreacionId(cedulaUser);

                            orden = ordenTrabajoMglManager.crearOt(orden, appCrearOtRequest.getUser(), 1);

                            if (orden != null && orden.getIdOt() != null) {
                                //Ajuste de logica para el cambio de estado interno en la creacion de la OT por medio del consumo del servicio
                                CmtOrdenTrabajoMglDaoImpl dao = new CmtOrdenTrabajoMglDaoImpl();
                                CmtOrdenTrabajoMgl ordenCreada = new CmtOrdenTrabajoMgl();
                                CmtBasicaMgl basicaActual = basicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_FOESTADO_PENDIENTE);
                                CmtBasicaMgl basicaNueva = basicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_FOESTADO_AGENDADO);
                                ordenCreada = dao.find(orden.getIdOt());

                                if (ordenCreada != null && ordenCreada.getEstadoInternoObj() != null && ordenCreada.getEstadoInternoObj().getIdentificadorInternoApp() != null && ordenCreada.getEstadoInternoObj().getIdentificadorInternoApp().equals(basicaActual.getIdentificadorInternoApp())) {
                                    ordenCreada.setEstadoInternoObj(basicaNueva);
                                    dao.update(ordenCreada);
                                }
                                if (saveInfoNotes(null, orden, appCrearOtRequest.getUser())) {
                                    String msnError = "Se ha creado la Ot numero: "
                                            + orden.getIdOt() + " satisfactoriamente";
                                    appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                    appResponseCrearOtDto.setMessageType(SIGLA_INFO);
                                    appResponseCrearOtDto.setMessage(msnError);
                                    appResponseCrearOtDto.setOtLocation(CCMM);
                                } else {
                                    String msnError = "Se ha creado la Ot numero: "
                                            + orden.getIdOt() + " satisfactoriamente";
                                    appResponseCrearOtDto.setIdOt(orden.getIdOt());
                                    appResponseCrearOtDto.setMessageType(SIGLA_INFO);
                                    appResponseCrearOtDto.setMessage(msnError);
                                    appResponseCrearOtDto.setOtLocation(CCMM);
                                }
                            } else {
                                String msnError = "No se ha podido crear la Ot"
                                        + " de manera correcta. Intente nuevamente por favor";
                                appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                                appResponseCrearOtDto.setMessage(msnError);
                                appResponseCrearOtDto.setOtLocation(CCMM);
                            }
                        }
                    }
                } else if (ordenHhppMglApp != null) {
                    if (appCrearOtRequest.getSegment() == null
                            || appCrearOtRequest.getSegment().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo segment es obligatorio");
                    } else if (appCrearOtRequest.getOtSubType() == null
                            || appCrearOtRequest.getOtSubType().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo otSubType es obligatorio");
                    } else if (appCrearOtRequest.getContactName() == null
                            || appCrearOtRequest.getContactName().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo contactName es obligatorio");
                    } else if (appCrearOtRequest.getContactPhone() == null
                            || appCrearOtRequest.getContactPhone().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo contactPhone es obligatorio");
                    } else if (appCrearOtRequest.getContactEmail() == null
                            || appCrearOtRequest.getContactEmail().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo contactEmail es obligatorio");
                    } else if (appCrearOtRequest.getGeneralStatus() == null
                            || appCrearOtRequest.getGeneralStatus().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo generalStatus es obligatorio");
                    } else if (appCrearOtRequest.getInitialRazon() == null
                            || appCrearOtRequest.getInitialRazon().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo initialRazon es obligatorio");
                    } else if (appCrearOtRequest.getUser() == null
                            || appCrearOtRequest.getUser().isEmpty()) {
                        appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                        appResponseCrearOtDto.setMessage("El campo user es obligatorio");
                    } else {
                        //Todos los campos estan llenos validaciones de negocio 
                        int tipoOrden = 1;
                        if (appCrearOtRequest.getOrdenPurpose().equalsIgnoreCase(MANTENIMIENTO)) {
                            tipoOrden = 1;
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
                            DireccionMgl direccionMgl = new DireccionMgl();
                            direccionMgl.setDirId(new BigDecimal(direccionCCMMHHPPDto.getDireccionId()));
                            otHhppCreada.setDireccionId(direccionMgl);
                            SubDireccionMgl subDireccionMgl = new SubDireccionMgl();
                            subDireccionMgl.setSdiId(new BigDecimal(direccionCCMMHHPPDto.getSubDireccionId()));
                            otHhppCreada.setSubDireccionId(subDireccionMgl);

                            otHhppCreada = createIndependentOt(otHhppCreada, appCrearOtRequest.getUser(), 1, listTecnologias);

                            if (otHhppCreada != null && otHhppCreada.getOtHhppId() != null) {
                                if (saveInfoNotes(otHhppCreada, null, appCrearOtRequest.getUser())) {
                                    String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                                    appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                    appResponseCrearOtDto.setMessageType(SIGLA_INFO);
                                    appResponseCrearOtDto.setMessage(msnError);
                                    appResponseCrearOtDto.setOtLocation(HHPP);
                                } else {
                                    String msnError = "Se ha creado la Ot numero: " + otHhppCreada.getOtHhppId() + " satisfactoriamente";
                                    appResponseCrearOtDto.setIdOt(otHhppCreada.getOtHhppId());
                                    appResponseCrearOtDto.setMessageType(SIGLA_INFO);
                                    appResponseCrearOtDto.setMessage(msnError);
                                    appResponseCrearOtDto.setOtLocation(HHPP);
                                }
                            } else {
                                String msnError = "No se ha podido crear la Ot"
                                        + " de manera correcta. Intente nuevamente por favor";
                                appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                                appResponseCrearOtDto.setMessage(msnError);
                            }
                        }
                    }
                }
            } else if (appCrearOtRequest.getOrdenPurpose() == null
                    || appCrearOtRequest.getOrdenPurpose().isEmpty()) {
                appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                appResponseCrearOtDto.setMessage("El campo ordenPurpose es obligatorio");
            } else {
                appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
                appResponseCrearOtDto.setMessage("El campo ordenPurpose debe tener"
                        + " alguna de esta opcion: 'Mantenimiento'");
            }
        } catch (ApplicationException ex) {
            appResponseCrearOtDto.setMessageType(SIGLA_ERROR);
            appResponseCrearOtDto.setMessage(ex.getMessage());
            return appResponseCrearOtDto;
        }

        return appResponseCrearOtDto;
    }

    /**
     * Metodo para crear una agenda de Orden desde el sistema APP
     *
     * @author Divier Casas
     * @version 1.0 revision .
     * @param crearAgendaOtRequest
     * @return AppResponsesAgendaDto
     * @throws java.io.IOException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public AppResponsesAgendaDto agendarOtCcmmHhppTMApp(AppCrearAgendaOtRequest crearAgendaOtRequest)
            throws UniformInterfaceException, IOException, ApplicationException {

        AppResponsesAgendaDto responsesAgendaDto = new AppResponsesAgendaDto();
        List<CmtAgendamientoMgl> agendarOTSubtipo = null;
        List<MglAgendaDireccion> agendarOTSubtipoHhpp = null;

        try {
            if (crearAgendaOtRequest.getOtLocation() != null
                    && crearAgendaOtRequest.getOtLocation().equalsIgnoreCase(CCMM)) {
                if (crearAgendaOtRequest.getCapacidad() == null
                        || crearAgendaOtRequest.getCapacidad().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo capacidad es obligatorio");
                } else if (crearAgendaOtRequest.getIdOt() == null
                        || crearAgendaOtRequest.getIdOt().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo idOt es obligatorio");
                } else if (crearAgendaOtRequest.getPersonaRecVt() == null
                        || crearAgendaOtRequest.getPersonaRecVt().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo personaRecVt es obligatorio");
                } else if (crearAgendaOtRequest.getTelPerRecVt() == null
                        || crearAgendaOtRequest.getTelPerRecVt().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo telPerRecVt es obligatorio");
                } else if (crearAgendaOtRequest.getEmailPerRecVT() == null
                        || crearAgendaOtRequest.getEmailPerRecVT().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo emailPerRecVT es obligatorio");
                } else if (crearAgendaOtRequest.getUser() == null
                        || crearAgendaOtRequest.getUser().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo user es obligatorio");
                } else if (crearAgendaOtRequest.getProperty() == null
                        || crearAgendaOtRequest.getProperty().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo property es obligatorio");
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
                        String mensajeFinalErrorCap = "";

                        for (CapacidadAgendaDto capacidadAgendaDto : crearAgendaOtRequest.getCapacidad()) {
                            agendaOtCcmm = new CmtAgendamientoMgl();
                            ordenCcmmMglApp.setPersonaRecVt(crearAgendaOtRequest.getPersonaRecVt());
                            ordenCcmmMglApp.setTelPerRecVt(crearAgendaOtRequest.getTelPerRecVt());
                            ordenCcmmMglApp.setEmailPerRecVT(crearAgendaOtRequest.getEmailPerRecVT());
                            agendaOtCcmm.setOrdenTrabajo(ordenCcmmMglApp);
                            capacidadAgendaDto.setProperty(crearAgendaOtRequest.getProperty());

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
                                String msn = "Se ha creado la agenda:  " + agendaOtCcmm.getOfpsOtId() + "  "
                                        + "  para la ot: " + agendaOtCcmm.getOrdenTrabajo().getIdOt() + " ";
                                mensajeFinal += msn;
                            } else {
                                List<CapacidadAgendaDto> resultado = getCapacidadMulti();
                                if (resultado.size() > 0) {

                                    agendaOtCcmm = agendamientoMglManager.agendar(capacidadAgendaDto, agendaOtCcmm, numeroOTRrOFSC, null, false);
                                    agendaOtCcmm.setIdOtenrr(numeroOTRr);
                                    agendamientoMglManager.create(agendaOtCcmm, crearAgendaOtRequest.getUser(), 1);
                                    String msn = "Se ha creado la agenda:  " + agendaOtCcmm.getOfpsOtId() + "  "
                                            + "  para la ot: " + agendaOtCcmm.getOrdenTrabajo().getIdOt() + " ";
                                    mensajeFinal += msn;
                                } else {
                                    String msnCap = "Ocurrio un error agendando para la "
                                            + " fecha: " + capacidadAgendaDto.getDate() + " ";

                                    mensajeFinalErrorCap += msnCap;
                                }
                            }
                            i++;
                        }
                        ordenTrabajoMglManager.actualizarOtCcmm(ordenCcmmMglApp, crearAgendaOtRequest.getUser(), 1);
                        agendasOtCcmm = agendamientoMglManager.
                                agendasPorOrdenAndSubTipoWorkfoce(ordenCcmmMglApp, subtipoWorfoce);
                        List<AgendasMglDto> agendasDto = retornaListAgendas(agendasOtCcmm, null);
                        responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                        responsesAgendaDto.setMessageType(SIGLA_INFO);
                        responsesAgendaDto.setMessage(mensajeFinal);
                    }
                }
            } else if (crearAgendaOtRequest.getOtLocation() != null
                    && crearAgendaOtRequest.getOtLocation().equalsIgnoreCase(HHPP)) {
                if (crearAgendaOtRequest.getCapacidad() == null
                        || crearAgendaOtRequest.getCapacidad().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo capacidad es obligatorio");
                } else if (crearAgendaOtRequest.getIdOt() == null
                        || crearAgendaOtRequest.getIdOt().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo idOt es obligatorio");
                } else if (crearAgendaOtRequest.getPersonaRecVt() == null
                        || crearAgendaOtRequest.getPersonaRecVt().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo personaRecVt es obligatorio");
                } else if (crearAgendaOtRequest.getTelPerRecVt() == null
                        || crearAgendaOtRequest.getTelPerRecVt().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo telPerRecVt es obligatorio");
                } else if (crearAgendaOtRequest.getEmailPerRecVT() == null
                        || crearAgendaOtRequest.getEmailPerRecVT().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo emailPerRecVT es obligatorio");
                } else if (crearAgendaOtRequest.getUser() == null
                        || crearAgendaOtRequest.getUser().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo user es obligatorio");
                } else if (crearAgendaOtRequest.getProperty() == null
                        || crearAgendaOtRequest.getProperty().isEmpty()) {
                    responsesAgendaDto.setMessageType(SIGLA_ERROR);
                    responsesAgendaDto.setMessage("El campo property es obligatorio");
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
                            capacidadAgendaDto.setProperty(crearAgendaOtRequest.getProperty());

                            if (nodoMglApp != null) {
                                agendaOtHhpp.setNodoMgl(nodoMglApp);
                            }

                            String numeroOtRr = null;
                            agendaOtHhpp = agendamientoHhppMglManager.agendar(capacidadAgendaDto, agendaOtHhpp, numeroOtRr, null, false);
                            agendamientoHhppMglManager.create(agendaOtHhpp, crearAgendaOtRequest.getUser(), 1);

                            String msn = "Se ha creado la agenda:  " + agendaOtHhpp.getOfpsOtId() + "  "
                                    + "  para la ot: " + agendaOtHhpp.getOrdenTrabajo().getOtHhppId() + " ";

                            ordenHhppMglApp = otHhppMglManager.update(ordenHhppMglApp);
                            agendasOtHhpp = agendamientoHhppMglManager.
                                    buscarAgendasByOtAndSubtipopOfsc(ordenHhppMglApp, subtipoOfsc);
                            List<AgendasMglDto> agendasDto = retornaListAgendas(null, agendasOtHhpp);
                            responsesAgendaDto.setAgendasOtCcmmHhpp(agendasDto);
                            responsesAgendaDto.setMessageType(SIGLA_INFO);
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
                                capacidadAgendaDto.setProperty(crearAgendaOtRequest.getProperty());

                                if (nodoMglApp != null) {
                                    agendaOtHhpp.setNodoMgl(nodoMglApp);
                                }

                                if (i == 0) {

                                    agendaOtHhpp = agendamientoHhppMglManager.agendar(capacidadAgendaDto, agendaOtHhpp, numeroOTRrOFSC, cuentaAgrupadora, false);
                                    agendaOtHhpp.setIdOtenrr(numeroOTRr);
                                    agendamientoHhppMglManager.create(agendaOtHhpp, crearAgendaOtRequest.getUser(), 1);

                                    String msn = "Se ha creado la agenda:  " + agendaOtHhpp.getOfpsOtId() + "  "
                                            + "  para la ot: " + agendaOtHhpp.getOrdenTrabajo().getOtHhppId() + " ";
                                    mensajeFinal += msn;
                                } else {
                                    List<CapacidadAgendaDto> resultado = getCapacidadMultiHhpp();
                                    if (resultado.size() > 0) {
                                        agendaOtHhpp = agendamientoHhppMglManager.agendar(capacidadAgendaDto, agendaOtHhpp, numeroOTRrOFSC, cuentaAgrupadora, false);
                                        agendaOtHhpp.setIdOtenrr(numeroOTRr);
                                        agendamientoHhppMglManager.create(agendaOtHhpp, crearAgendaOtRequest.getUser(), 1);
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
                            responsesAgendaDto.setMessageType(SIGLA_INFO);
                            responsesAgendaDto.setMessage(mensajeFinal);
                        }
                    }
                }
            } else if (crearAgendaOtRequest.getOtLocation() == null
                    || crearAgendaOtRequest.getOtLocation().isEmpty()) {
                responsesAgendaDto.setMessageType(SIGLA_ERROR);
                responsesAgendaDto.setMessage("El campo otLocation es obligatorio");
            } else {
                responsesAgendaDto.setMessageType(SIGLA_ERROR);
                responsesAgendaDto.setMessage("El campo otLocation debe tener dos opciones: 'CCMM' o 'HHPP'");
            }
        } catch (ApplicationException | DatatypeConfigurationException ex) {
            responsesAgendaDto.setMessageType(SIGLA_ERROR);
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

    private boolean validacionesCrearOtCcmm(AppCrearOtRequest request, int tipoOrden) throws ApplicationException {

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
            parametroTipoOtMto = parametrosMglManager.findParametroMgl(Constant.SUB_TIPO_OT_MTO_CCMM);
            cuentaMatrizMglApp = ordenCcmmMglApp.getCmObj();

            if (parametroTipoOtMto == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.SUB_TIPO_OT_MTO_CCMM);
            }
            //Fin busqueda el parametro de tipo de orden mantenimiento para CCMM    
        }
        
        //Validacion sub tipo de orden
        if (parametroTipoOtMto != null) {
            CmtTipoOtMglManager cmtTipoOtMglManager = new CmtTipoOtMglManager();
            String valorParam = parametroTipoOtMto.getParValor();
            String[] aValorParam = valorParam.split("\\|");
            for (String valParamInd : aValorParam) {
                String[] aValParamInd = valParamInd.split("\\-");
                if (request.getOtSubType().equals(aValParamInd[0])) {
                    tipoOtMglApp = cmtTipoOtMglManager.findById(new BigDecimal(aValParamInd[1]));
                    tipoTrabajoMglApp = tipoOtMglApp != null ? tipoOtMglApp.getBasicaIdTipoOt() : null;
                    break;
                }
            }
            if (tipoOtMglApp == null || tipoOtMglApp.getIdTipoOt() == null) {
                throw new ApplicationException("No existe un sub tipo"
                        + " de orden homologado con el id: " + request.getOtSubType() + " en MGL.");
            }
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

    private boolean validacionesCrearOtDir(AppCrearOtRequest request, int tipoOrden) throws ApplicationException {

        ParametrosMgl parametroTipoOtMto = null;

        if (tipoOrden == 1) {
            //Se busca el parametro de tipo de orden mantenimiento para DIRECCIONES
            parametroTipoOtMto = parametrosMglManager.findParametroMgl(Constant.SUB_TIPO_OT_MTO_HHPP);

            if (parametroTipoOtMto == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.SUB_TIPO_OT_MTO_HHPP);
            }
            //Fin busqueda el parametro de tipo de orden mantenimiento para DIRECCIONES     
        }
        
        //Validacion sub tipo de orden
        if (parametroTipoOtMto != null) {
            TipoOtHhppMglManager tipoOtHhppMglManager = new TipoOtHhppMglManager();
            String valorParam = parametroTipoOtMto.getParValor();
            String[] aValorParam = valorParam.split("\\|");
            for (String valParamInd : aValorParam) {
                String[] aValParamInd = valParamInd.split("\\-");
                if (request.getOtSubType().equals(aValParamInd[0])) {
                    tipoOtHhppMglApp = tipoOtHhppMglManager.findTipoOtHhppById(new BigDecimal(aValParamInd[1]));
                    break;
                }
            }
            if (tipoOtHhppMglApp == null || tipoOtHhppMglApp.getTipoOtId() == null) {
                throw new ApplicationException("No existe un sub tipo"
                        + " de orden homologado con el id: " + request.getOtSubType() + " en MGL.");
            }
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

    private boolean validacionesCrearAgendaOtCcmm(AppCrearAgendaOtRequest request)
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

    private boolean validacionesCrearAgendaOtHhpp(AppCrearAgendaOtRequest request)
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

    private OtHhppMgl createIndependentOt(OtHhppMgl otToCreate, String usuario,
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

    private BigDecimal obtenerValorCheckbox(boolean agendableSeleccionado) {
        if (agendableSeleccionado) {
            return new BigDecimal(1);
        } else {
            return new BigDecimal(0);
        }
    }

    private boolean saveInfoNotes(
            OtHhppMgl otHhppMgl, CmtOrdenTrabajoMgl ot, String user) throws ApplicationException {

        boolean respuest = false;
        int control = 0;
        if (otHhppMgl != null && notasHhpp != null
                && !notasHhpp.isEmpty()) {
            CmtNotasOtHhppMglManager notasOtHhppMglManager
                    = new CmtNotasOtHhppMglManager();
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

        if (ot != null && notasOtCcmm != null
                && !notasOtCcmm.isEmpty()) {
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

    private String estadoInternoACargar(String estadoSeleccionado) {
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

    private String generarNumeroOtRr(CmtOrdenTrabajoMgl orden, String numeroOtRr, boolean habilitaRr)
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

    private String generarNumeroOtRrHhpp(String codCuentaGenerico, String numeroOtRr, OtHhppMgl otHhppMgl,
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

    private List<AgendasMglDto> retornaListAgendas(List<CmtAgendamientoMgl> agendasOtCcmm,
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
                agendasMglDto.setIdOt(agendamientoMgl.getOrdenTrabajo().getIdOt() != null
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
                agendasMglDto.setIdOt(agendamientoMgl.getOrdenTrabajo().getOtHhppId() != null
                        ? agendamientoMgl.getOrdenTrabajo().getOtHhppId() : null);
                returnsList.add(agendasMglDto);
            }
        }
        return returnsList;
    }

    private void rollbackOrdenCcmmInRr(CmtAgendamientoMgl agendamientoMgl, String user) throws ApplicationException {

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

    private void rollbackOrdenHhppInRr(MglAgendaDireccion agendaDireccion, String user) throws ApplicationException {

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

    private boolean validarReglasAgendaOtCcmm() throws ApplicationException {

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

                    if (restriccionMgls.size() > 0) {
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

                    if (restriccionMgls.size() > 0) {
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

                    if (restriccionMgls.size() > 0) {
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

    private boolean validarCreacion() throws ApplicationException {

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

    private boolean validarEstadoOtHhpp(OtHhppMgl otHhppSeleccionado) throws ApplicationException {

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

                                if (restriccionMgls.size() > 0) {
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

                                if (restriccionMgls.size() > 0) {
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

                                if (restriccionMgls.size() > 0) {
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

    private boolean validoFechaFranjaAgendasHhpp(String fechaCapacity, String franjaCapacity) {

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

    private boolean validarExistenciaAgendaCerrada() throws ApplicationException {
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

    private List<CapacidadAgendaDto> getCapacidadMulti() throws ApplicationException {

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

    private List<CapacidadAgendaDto> getCapacidadMultiHhpp() throws ApplicationException {

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

    private boolean validoFechaFranjaAgendasCcmm(String fechaCapacity, String franjaCapacity) {

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

    private String devuelveDia(int dia) {

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

    private List<String> diasComparar(String diaIni, String diaFin) {

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

    private String retornaSubtipoWorfoce(CmtOrdenTrabajoMgl ot) throws ApplicationException {

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
}
