/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.agendamiento;

import co.com.claro.cmas400.ejb.respons.ResponseDataOtEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mgl.businessmanager.address.AgendamientoHhppMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.OtHhppMglManager;
import co.com.claro.mgl.businessmanager.address.OtHhppTecnologiaMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.RequestAgendaInmediataMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtAgendamientoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtCuentaMatrizMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtEstadoxFlujoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtHorarioRestriccionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTecnologiaSubMglManager;
import co.com.claro.mgl.businessmanager.cm.OnyxOtCmDirManager;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.RequestAgendaInmediataMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bocanegravm
 */
public class AgendamientoRunAble {

    private static final Logger LOGGER = LogManager.getLogger(AgendamientoRunAble.class);
    private final int tipoOt;
    private final RequestAgendaInmediataMgl requestAgendaInmediataMgl;
    private CmtOrdenTrabajoMgl ordenTrabajoMgl;
    private OtHhppMgl ordenDireccion;
    private CmtOrdenTrabajoMglManager ordenTrabajoMglManager;
    private OtHhppMglManager ordenHhppMglManager;
    private ParametrosMglManager parametrosMglManager;
    private CmtEstadoxFlujoMglManager estadoxFlujoMglManager;
    private CmtAgendamientoMglManager agendamientoMglManager;
    private AgendamientoHhppMglManager agendamientoHhppMglManager;
    private CmtBasicaMglManager basicaMglManager;
    private final int MAX_CARACTERES_ESTADO_OT = 6;
    private final int MAX_CARACTERES_NUMERO_OT_RR = 5;
    private NodoMgl nodoMgl;

    public AgendamientoRunAble(RequestAgendaInmediataMgl requestAgendaInmediataMgl, int tipoOrden) {
        this.requestAgendaInmediataMgl = requestAgendaInmediataMgl;
        this.tipoOt = tipoOrden;
    }

    public void crearAgenda() throws ApplicationException {
        if (tipoOt == 1) {
            //El agendamiento es de orden de CCMM  
            try {
                ordenTrabajoMgl = requestAgendaInmediataMgl.getOrdenTrabajoMgl();
                if (ordenTrabajoMgl != null) {
                    if (validarCreacion()) {
                        ordenTrabajoMglManager = new CmtOrdenTrabajoMglManager();
                        crearAgendasOrdenCCMM(requestAgendaInmediataMgl);
                    } else {
                        String msg = "Ya se encuentra una ultima agenda cerrada no se pueden crear más agendas ";
                        RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                                = new RequestAgendaInmediataMglManager();
                        requestAgendaInmediataMgl.setEstado("2");
                        requestAgendaInmediataMgl.setDescripcionError(msg);
                        requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
                        throw new ApplicationException(msg);
                    }
                }
            } catch (ApplicationException ex) {
                RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                        = new RequestAgendaInmediataMglManager();
                requestAgendaInmediataMgl.setEstado("2");
                requestAgendaInmediataMgl.setDescripcionError(ex.getMessage());
                requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
                throw new ApplicationException(ex.getMessage());

            } catch (Exception ex) {
                RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                        = new RequestAgendaInmediataMglManager();
                requestAgendaInmediataMgl.setEstado("2");
                requestAgendaInmediataMgl.setDescripcionError(ex.getMessage());
                requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
                throw new ApplicationException(ex.getMessage());
            }

        } else if (tipoOt == 2) {
            //El agendamiento es de orden de Direcciones   
            try {
                ordenDireccion = requestAgendaInmediataMgl.getOrdenDirHhppMgl();
                if (ordenDireccion != null) {
                    if (!validarExistenciaAgendaCerrada()) {
                        ordenHhppMglManager = new OtHhppMglManager();
                        ordenTrabajoMglManager = new CmtOrdenTrabajoMglManager();
                        crearAgendasOrdenDirecciones(requestAgendaInmediataMgl);
                    } else {
                        String msg = "No se puede generar una nueva agenda debido"
                                + " a que existe una cerrada exitosamente";
                        RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                                = new RequestAgendaInmediataMglManager();
                        requestAgendaInmediataMgl.setEstado("2");
                        requestAgendaInmediataMgl.setDescripcionError(msg);
                        requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
                        throw new ApplicationException(msg);
                    }
                }
            } catch (ApplicationException ex) {
                RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                        = new RequestAgendaInmediataMglManager();
                requestAgendaInmediataMgl.setEstado("2");
                requestAgendaInmediataMgl.setDescripcionError(ex.getMessage());
                requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
                throw new ApplicationException(ex.getMessage());
            } catch (Exception ex) {
                RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                        = new RequestAgendaInmediataMglManager();
                requestAgendaInmediataMgl.setEstado("2");
                requestAgendaInmediataMgl.setDescripcionError(ex.getMessage());
                requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String crearAgendasOrdenCCMM(RequestAgendaInmediataMgl requestAgendaInmediataMgl1)
            throws ApplicationException, Exception {

        List<CmtAgendamientoMgl> agendarOTSubtipo = null;
        parametrosMglManager = new ParametrosMglManager();
        estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        CmtAgendamientoMgl agenda = new CmtAgendamientoMgl();

        try {

            cargarNodo(ordenTrabajoMgl);
            //Consulto el capacity para referenciar el Num de orden
            agendamientoMglManager = new CmtAgendamientoMglManager();
            agendamientoMglManager.getCapacidad(ordenTrabajoMgl, null, nodoMgl);

            CapacidadAgendaDto capacidadAgendaDto = new CapacidadAgendaDto();
            capacidadAgendaDto.setDate(new Date());

            capacidadAgendaDto.setHoraInicio(requestAgendaInmediataMgl1.getHoraInicio());

            //Valida restricciones de la ccmm
            if (!validarRestriccionesCcmm(capacidadAgendaDto)) {
                String mensajesValidacion = "La cuenta matriz tiene horarios de restriccion o no "
                        + "disponibilidad en la franja: " + capacidadAgendaDto.getHoraInicio() + ".";
                throw new ApplicationException(mensajesValidacion);
            }

            //Consulta el servicio de onix
            CmtOnyxResponseDto cmtOnyxResponseDto = null;
            if (ordenTrabajoMgl.getOnyxOtHija() != null) {
                //Validamos disponibilidad del servicio
                ParametrosMgl wsdlOtHija
                        = parametrosMglManager.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                if (wsdlOtHija == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                }

                URL urlCon = new URL(wsdlOtHija.getParValor());

                ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                //Fin Validacion disponibilidad del servicio
                //
                cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(ordenTrabajoMgl.getOnyxOtHija().toString());
            }

            //CREAR OT EN RR                           
            CmtEstadoxFlujoMgl estadoFlujoOrden;
            String numeroOTRr = null;
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);

            if (parametroHabilitarRR != null
                    && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                estadoFlujoOrden = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(ordenTrabajoMgl.getTipoOtObj().getTipoFlujoOt(), ordenTrabajoMgl.getEstadoInternoObj(), ordenTrabajoMgl.getBasicaIdTecnologia());
                if (estadoFlujoOrden != null) {
                    if (estadoFlujoOrden.getSubTipoOrdenOFSC() != null
                            && estadoFlujoOrden.getTipoTrabajoRR() != null
                            && estadoFlujoOrden.getEstadoOtRRInicial() != null
                            && estadoFlujoOrden.getEstadoOtRRFinal() != null) {
                        String subTipoWorForce = estadoFlujoOrden.getSubTipoOrdenOFSC().getNombreBasica();

                        agendarOTSubtipo = agendamientoMglManager.agendasPorOrdenAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorForce);
                        if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                            LOGGER.info("Se creara OT en RR , es la primera agenda del estado actual de la OT en MGL ");
                            numeroOTRr = ordenTrabajoMglManager.crearOtRRporAgendamiento(ordenTrabajoMgl, estadoFlujoOrden, "AGEINMEDIATA", 0);
                        }
                    } else {
                        LOGGER.info("El estado actual de la OT no tiene los campos de creacion de OT RR configurados , se enviara id de OT MGL");
                        String mensajeCamposRRestado = "Recuerde que para que se cree OT "
                                + "en RR y continuar con el agendamiento debe diligenciar "
                                + "los campos , Tipo OT RR , Estado Inicial y Estado Cierre "
                                + "en el estado x flujo actual de OT MGL. ";
                        LOGGER.error(mensajeCamposRRestado);
                    }
                } else {
                    LOGGER.info("No existe un estado por flujo configurado para la orden");
                }
                //--
            } else {
                LOGGER.info("El parámetro " + Constant.HABILITAR_RR + " no se encuentra habilitado. No se crea orden en RR.");
            }

            if (agendarOTSubtipo != null && !agendarOTSubtipo.isEmpty()) {
                numeroOTRr = agendarOTSubtipo.get(0).getIdOtenrr() != null ? agendarOTSubtipo.get(0).getIdOtenrr() : ordenTrabajoMgl.getIdOt().toString();
            }

            String numeroOTRrOFSC;
            if (numeroOTRr == null) {
                // Si no fue generada la OT en Rr           
                numeroOTRrOFSC = generarNumeroOtRr(ordenTrabajoMgl, numeroOTRr, false);

            } else {
                numeroOTRrOFSC = generarNumeroOtRr(ordenTrabajoMgl, numeroOTRr, true);
            }

            String requestURL = "NA"; //getFullRequestContextPath();

            agenda.setOrdenTrabajo(ordenTrabajoMgl);
            agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
            agenda.setIdentificacionTecnico(requestAgendaInmediataMgl1.getIdTecnico());

            if (numeroOTRr != null && !numeroOTRr.isEmpty()) {
                agenda.setIdOtenrr(numeroOTRr);
            }

            if (nodoMgl != null) {
                agenda.setNodoMgl(nodoMgl);
            }

            agenda.setNumeroOrdenInmediata(requestAgendaInmediataMgl1.getNumeroOrden());
            agenda = agendamientoMglManager.agendar(capacidadAgendaDto, agenda, numeroOTRrOFSC, requestURL, false);
            agenda.setIdOtenrr(numeroOTRr);
            agenda.setAgendaInmediata("Y");
            agendamientoMglManager.create(agenda, "AGEINMEDIATA", 1);

            if (cmtOnyxResponseDto != null) {
                agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                try {
                    agendamientoMglManager.cargarInformacionForEnvioNotificacion(agenda, 1);
                } catch (ApplicationException ex) {
                    String msn = "Ocurrio un error al momento de "
                            + "enviar notificacion de agenda: " + ex.getMessage() + "";
                    LOGGER.error(msn);
                }
            }
            ordenTrabajoMglManager.actualizarOtCcmm(ordenTrabajoMgl, "AGEINMEDIATA", 1);
            if (agenda.getId() != null) {
                //Creo la agenda en MER 
                RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                        = new RequestAgendaInmediataMglManager();
                requestAgendaInmediataMgl.setEstado("1");
                requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
            }
        } catch (ApplicationException ex) {
            //Ocurrio alguna excepcion durante el proceso cierro el hilo y actualizo la tabla
            RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                    = new RequestAgendaInmediataMglManager();
            requestAgendaInmediataMgl.setEstado("2");
            requestAgendaInmediataMgl.setDescripcionError(ex.getMessage());
            requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                rollbackOrdenInRr(agenda);
            }
            throw new ApplicationException(ex.getMessage());
        } catch (IOException ex) {
            //Ocurrio alguna excepcion durante el proceso cierro el hilo y actualizo la tabla
            RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                    = new RequestAgendaInmediataMglManager();
            requestAgendaInmediataMgl.setEstado("2");
            requestAgendaInmediataMgl.setDescripcionError(ex.getMessage());
            requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                rollbackOrdenInRr(agenda);
            }
            throw new ApplicationException(ex.getMessage());
        }

        return "";
    }

    public String crearAgendasOrdenDirecciones(RequestAgendaInmediataMgl requestAgendaInmediataMgl1) throws ApplicationException, Exception {

        List<MglAgendaDireccion> agendarOTSubtipo = null;
        MglAgendaDireccion agenda = new MglAgendaDireccion();
        String codCuentaPar = null;
        parametrosMglManager = new ParametrosMglManager();
        OnyxOtCmDirManager onyxOtCmDirManager = new OnyxOtCmDirManager();
        
        try {

            cargarNodo(ordenDireccion);

            //Consulto el capacity para referenciar el Num de orden
            agendamientoHhppMglManager = new AgendamientoHhppMglManager();

            UsuariosServicesDTO user = new UsuariosServicesDTO();
            user.setCedula(requestAgendaInmediataMgl1.getNumeroOrden());
            agendamientoHhppMglManager.getCapacidadOtDireccion(ordenDireccion, user, null, nodoMgl);

            CapacidadAgendaDto capacidadAgendaDto = new CapacidadAgendaDto();
            capacidadAgendaDto.setDate(new Date());

            capacidadAgendaDto.setHoraInicio(requestAgendaInmediataMgl1.getHoraInicio());

            //Valida restricciones de la ccmm
            if (!validarRestriccionesHhppCcmm(capacidadAgendaDto)) {
                String mensajesValidacion = "La cuenta matriz tiene horarios de restriccion o no "
                        + "disponibilidad en la franja: " + capacidadAgendaDto.getHoraInicio() + ".";
                throw new ApplicationException(mensajesValidacion);
            }

            //Creo las agendas no hay errores
            //Consultamos de nuevo la orden para que tome las ultimas actualizaciones
            OtHhppMgl ot = ordenHhppMglManager.findOtByIdOt(ordenDireccion.getOtHhppId());
            
            
            if (ot != null && ot.getOtHhppId() != null) {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxOtHHppById(ot.getOtHhppId());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    ot.setOnyxOtHija(new BigDecimal(listaOnyx.get(0).getOnyx_Ot_Hija_Dir()));
                }
            }
            
            BigDecimal numOtHija = null;
            TipoOtHhppMgl tipoOtHhppMgl = ot.getTipoOtHhppId();
            if (ot.getOtHhppId() != null && ot.getOnyxOtHija() != null) {
                numOtHija = ot.getOnyxOtHija();
            }
            if (tipoOtHhppMgl.getIsMultiagenda().equalsIgnoreCase("N")) {
                //Es unica Agenda
                agenda.setOrdenTrabajo(ordenDireccion);
                agenda.setPersonaRecVt(ordenDireccion.getNombreContacto());
                agenda.setTelPerRecVt(ordenDireccion.getTelefonoContacto());
                agenda.setEmailPerRecVT(ordenDireccion.getCorreoContacto());
                agenda.setIdentificacionTecnico(requestAgendaInmediataMgl1.getIdTecnico());

                if (nodoMgl != null) {
                    agenda.setNodoMgl(nodoMgl);
                }

                CmtOnyxResponseDto cmtOnyxResponseDto = null;
                if (numOtHija != null) {
                    //Validamos disponibilidad del servicio
                    ParametrosMgl wsdlOtHija
                            = parametrosMglManager.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                    if (wsdlOtHija == null) {
                        throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                    }

                    URL urlCon = new URL(wsdlOtHija.getParValor());

                    ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                    //Fin Validacion disponibilidad del servicio
                    cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(numOtHija.toString());

                }

                if (cmtOnyxResponseDto != null) {
                    agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                }

                agenda.setNumeroOrdenInmediata(requestAgendaInmediataMgl1.getNumeroOrden());
                agenda.setIdentificacionTecnico(requestAgendaInmediataMgl1.getIdTecnico());
                agenda = agendamientoHhppMglManager.agendar(capacidadAgendaDto, agenda, null, null, false);
                agenda.setAgendaInmediata("Y");
                agendamientoHhppMglManager.create(agenda, "AGEINMEDIATA", 1);

                if (agenda.getAgendaId() != null) {
                    //Creo la agenda en MER 
                    RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                            = new RequestAgendaInmediataMglManager();
                    requestAgendaInmediataMgl.setEstado("1");
                    requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
                    if (cmtOnyxResponseDto != null) {
                        agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                        try {
                            agendamientoHhppMglManager.cargarInformacionForEnvioNotificacion(agenda, 1);
                        } catch (ApplicationException ex) {
                            String msn = "Ocurrio un error al momento de "
                                    + "enviar notificacion de agenda: " + ex.getMessage() + "";
                            LOGGER.error(msn);
                        }
                    }

                }
            } else {
                //Es multi agenda

                String mensajeFinal = "";
                CmtOnyxResponseDto cmtOnyxResponseDto = null;
                if (numOtHija != null) {
                    //Validamos disponibilidad del servicio
                    ParametrosMgl wsdlOtHija
                            = parametrosMglManager.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                    if (wsdlOtHija == null) {
                        throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                    }

                    URL urlCon = new URL(wsdlOtHija.getParValor());

                    ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                    //Fin Validacion disponibilidad del servicio
                    cmtOnyxResponseDto = ordenTrabajoMglManager.consultarOTHijaWsOnyx(numOtHija.toString());

                }

                String numeroOTRr = null;            

                    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();

                    CmtCuentaMatrizMgl cuentaAgrupadora = null;
                    ////Consulta de cuenta matriz agrupadora  si es multiagenda

                    String subTipo = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();
                    List<MglAgendaDireccion> agendasCcmm = agendamientoHhppMglManager.
                            buscarAgendasByOtAndSubtipopOfsc(ordenDireccion, subTipo);
                    if ((agendasCcmm == null || agendasCcmm.isEmpty())) {
                        LOGGER.info("Se consulta Ccmm agrupadora ,"
                                + " es la primera agenda del estado actual de la OT en MGL ");

                        if (ordenDireccion.getDireccionId() != null
                                && ordenDireccion.getDireccionId().getUbicacion() != null) {
                            GeograficoPoliticoMgl geograficoPoliticoMgl
                                    = ordenDireccion.getDireccionId().getUbicacion().getGpoIdObj();

                            if (geograficoPoliticoMgl != null) {
                                basicaMglManager = new CmtBasicaMglManager();
                                CmtBasicaMgl basicaMgl = basicaMglManager.
                                        findByCodigoInternoApp(Constant.BASICA_TIPO_EDIFICIO_AGRUPADOR_DIRECCIONES_BARRIO);

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
                    
                    ParametrosMgl parametroHabilitarOtInRr = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR); 

                    if (parametroHabilitarOtInRr != null && parametroHabilitarOtInRr.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                        
                        if (tipoOtHhppMgl.getSubTipoOrdenOFSC() != null  && tipoOtHhppMgl.getTipoTrabajoRR() != null && tipoOtHhppMgl.getEstadoOtRRInicial() != null
                                && tipoOtHhppMgl.getEstadoOtRRFinal() != null) {
                            
                            String subTipoWorForce = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();

                            agendarOTSubtipo = agendamientoHhppMglManager.buscarAgendasByOtAndSubtipopOfsc(ordenDireccion, subTipoWorForce);
                            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                                LOGGER.info("Se creara OT en RR , es la primera agenda del estado actual de la OT en MGL ");
                                numeroOTRr = ordenTrabajoMglManager.crearOtRRporAgendamientoHhpp(codCuentaPar, tipoOtHhppMgl, "AGEINMEDIATA", ordenDireccion);
                            }
                        } else {
                            LOGGER.info("El estado actual del tipo de OT no tiene los campos de creacion de OT RR configurados , se enviara id de OT MGL");
                        }                        
                    }                     
                    

                    if (agendarOTSubtipo != null && !agendarOTSubtipo.isEmpty()) {
                        numeroOTRr = agendarOTSubtipo.get(0).getIdOtenrr() != null ? agendarOTSubtipo.get(0).getIdOtenrr() : ordenDireccion.getOtHhppId().toString();
                    }

                    String numeroOTRrOFSC;

                    if (numeroOTRr == null) {
                        // Si no fue generada la OT en Rr           
                        numeroOTRrOFSC = generarNumeroOtRr(codCuentaPar, numeroOTRr, ordenDireccion, false);
                    } else {
                        numeroOTRrOFSC = generarNumeroOtRr(codCuentaPar, numeroOTRr, ordenDireccion, true);
                    }

                    agenda.setOrdenTrabajo(ordenDireccion);
                    agenda.setPersonaRecVt(ordenDireccion.getNombreContacto());
                    agenda.setTelPerRecVt(ordenDireccion.getTelefonoContacto());
                    agenda.setEmailPerRecVT(ordenDireccion.getCorreoContacto());
                    agenda.setIdentificacionTecnico(requestAgendaInmediataMgl1.getIdTecnico());

                    if (cmtOnyxResponseDto != null) {
                        agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                    }

                    if (nodoMgl != null) {
                        agenda.setNodoMgl(nodoMgl);
                    }

                    agenda.setNumeroOrdenInmediata(requestAgendaInmediataMgl1.getNumeroOrden());
                    agenda = agendamientoHhppMglManager.agendar(capacidadAgendaDto, agenda, numeroOTRrOFSC, cuentaAgrupadora, false);
                    agenda.setIdOtenrr(numeroOTRr);
                    agenda.setAgendaInmediata("Y");
                    agendamientoHhppMglManager.create(agenda, "AGEINMEDIATA", 1);

                    if (agenda.getAgendaId() != null) {
                        //Creo la agenda en MER 
                        RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                                = new RequestAgendaInmediataMglManager();
                        requestAgendaInmediataMgl.setEstado("1");
                        requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
                        if (cmtOnyxResponseDto != null) {
                            agenda.setCmtOnyxResponseDto(cmtOnyxResponseDto);
                            try {
                                agendamientoHhppMglManager.cargarInformacionForEnvioNotificacion(agenda, 1);
                            } catch (ApplicationException ex) {
                                String msn = "Ocurrio un error al momento de "
                                        + "enviar notificacion de agenda: " + ex.getMessage() + "";
                                LOGGER.error(msn);
                            }
                        }
                    }
                
            }

        } catch (ApplicationException | NumberFormatException ex) {
            //Ocurrio alguna excepcion durante el proceso cierro el hilo y actualizo la tabla
            RequestAgendaInmediataMglManager requestAgendaInmediataMglManager
                    = new RequestAgendaInmediataMglManager();
            requestAgendaInmediataMgl.setEstado("2");
            requestAgendaInmediataMgl.setDescripcionError(ex.getMessage());
            requestAgendaInmediataMglManager.update(requestAgendaInmediataMgl);
            if ((agendarOTSubtipo == null || agendarOTSubtipo.isEmpty())) {
                rollbackOrdenInRr(agenda, codCuentaPar);
            }
            throw new ApplicationException(ex.getMessage());
        }
        //Ocurrio alguna excepcion durante el proceso cierro el hilo y actualizo la tabla

        return "";
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
                    if (numeroOtRr != null && orden.getCmObj() != null && orden.getCmObj().getNumeroCuenta() != null) {
                        // Número de cuenta matriz de RR>+<Número orden de RR a 5,  justificada a la derecha y relleno con ceros.
                        numeroGenerado = orden.getCmObj().getNumeroCuenta().toString().trim() + StringUtils.leftPad(numeroOtRr, MAX_CARACTERES_NUMERO_OT_RR, "0");
                    }

                } else {
                    //Si no se generó una Ot en RR:
                    if (orden.getEstadoInternoObj() != null && orden.getEstadoInternoObj().getBasicaId() != null) {
                        String estadoOt = orden.getEstadoInternoObj().getBasicaId().toString();

                        if (orden.getIdOt() != null) {
                            // Número de Orden de MGL>+<PK del Estado en que este la Orden de trabajo a 6 dígitos  justificada a la derecha y relleno con ceros.
                            numeroGenerado = orden.getIdOt().toString().trim() + StringUtils.leftPad(estadoOt, MAX_CARACTERES_ESTADO_OT, "0");
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

    /**
     * Obtiene la URL completa del Request Context Path (Incluye protocolo,
     * host, puerto, y context path).
     *
     * @return Request Context Path URL.
     */
    public static String getFullRequestContextPath() {
        String requestURL;

        ExternalContext context
                = FacesContext.getCurrentInstance().getExternalContext();

        try {
            URL url
                    = new URL(context.getRequestScheme(),
                            context.getRequestServerName(),
                            context.getRequestServerPort(),
                            context.getRequestContextPath());

            requestURL = url.toString();
        } catch (MalformedURLException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(AgendamientoRunAble.class) + "': " + e.getMessage();
            LOGGER.error(msg);
            requestURL = null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(AgendamientoRunAble.class) + "': " + e.getMessage();
            LOGGER.error(msg);
            requestURL = null;
        } catch (Throwable e) {
            throw e; // rethrow the exception/error that occurred
        }

        return (requestURL);
    }

    private void cargarNodo(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        try {

            CmtCuentaMatrizMgl cm = ot.getCmObj();
            CmtSubEdificioMgl subEdificioMgl;
            subEdificioMgl = cm.getSubEdificioGeneral();

            CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
            NodoMglManager nodoMglManager = new NodoMglManager();

            CmtTecnologiaSubMgl tecnologiaSubMgl = tecnologiaSubMglManager.
                    findBySubEdificioTecnologia(subEdificioMgl,
                            ot.getBasicaIdTecnologia());

            if (tecnologiaSubMgl != null && tecnologiaSubMgl.getTecnoSubedificioId() != null) {
                nodoMgl = tecnologiaSubMgl.getNodoId();
            } else {
                //Consulto el geo
                nodoMgl = nodoMglManager.consultaGeo(ot);
                if (nodoMgl == null) {
                    throw new ApplicationException("No existe nodo de cobertura para la orden: " + ot.getIdOt() + " ");
                }
            }

        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    private void cargarNodo(OtHhppMgl otDireccion) throws ApplicationException {

        try {
            OtHhppTecnologiaMglManager otHhppTecnologiaMglManager
                    = new OtHhppTecnologiaMglManager();

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
                nodoMgl = tecnologiaPrioridadLocation.getNodo();
            }

            if (nodoMgl == null) {
                throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot: " + otDireccion.getOtHhppId() + "");
            }
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void rollbackOrdenInRr(CmtAgendamientoMgl agendamientoMgl) throws ApplicationException, Exception {

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
                                    eliminarOtRRporAgendamiento(agendamientoMgl, "AGEINMEDIATA");

                            if (respuestaElimina) {
                                LOGGER.info("Orden No: " + agendamientoMgl.getIdOtenrr() + " borrada por rollback en RR");
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public void rollbackOrdenInRr(MglAgendaDireccion agendaDireccion, String codCuentaPar) throws ApplicationException, Exception {

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
                                    ordenTrabajoEdificioDeleteHhpp(codCuentaPar, agendaDireccion.getIdOtenrr(), "");

                            if (respuestaElimina) {
                                LOGGER.info("Orden No: " + agendaDireccion.getIdOtenrr() + " borrada por rollback en RR");
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    private boolean validarRestriccionesCcmm(CapacidadAgendaDto capacidad) throws ApplicationException {

        CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();

        try {

            if (capacidad.getTimeSlot() != null && capacidad.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                if (ordenTrabajoMgl.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = cmtHorarioRestriccionMglManager.findByCuentaMatrizId(ordenTrabajoMgl.getCmObj().getCuentaMatrizId());

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
                if (ordenTrabajoMgl.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = cmtHorarioRestriccionMglManager.findByCuentaMatrizId(ordenTrabajoMgl.getCmObj().getCuentaMatrizId());

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
                if (ordenTrabajoMgl.getCmObj() != null) {
                    List<CmtHorarioRestriccionMgl> restriccionMgls
                            = cmtHorarioRestriccionMglManager.findByCuentaMatrizId(ordenTrabajoMgl.getCmObj().getCuentaMatrizId());

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
        } catch (NumberFormatException e) {
            LOGGER.error("Se generea error validando restricciones en AgendamientoRunAble: validarRestriccionesCcmm()" + e.getMessage());
        }
        return false;
    }

    public String devuelveDia(int dia) {
        String diaSemana = "";
        try {

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
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error validando fechas en AgendamientoRunAble: devuelveDia() " + e.getMessage());
        }
        return diaSemana;
    }

    public List<String> diasComparar(String diaIni, String diaFin) {

        List<String> result = new ArrayList<>();

        try {
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
        } catch (RuntimeException e) {
            LOGGER.error("Ocurrió un error en diasComparar al comparar días: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error comparando fechas en CmtAgendamientoMglBean: diasComparar() " + e.getMessage());
        }
        return result;
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
    private boolean validarRestriccionesHhppCcmm(CapacidadAgendaDto capacidad) {
        try {

            CmtHorarioRestriccionMglManager cmtHorarioRestriccionMglManager = new CmtHorarioRestriccionMglManager();
            HhppMglManager hhppMglManager = new HhppMglManager();

            if (capacidad.getTimeSlot() != null
                    && capacidad.getTimeSlot().equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                if (ordenDireccion != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(ordenDireccion);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = cmtHorarioRestriccionMglManager.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

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
                if (ordenDireccion != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(ordenDireccion);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = cmtHorarioRestriccionMglManager.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

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
                if (ordenDireccion != null) {
                    HhppMgl hhpp = hhppMglManager.findHhppByDirAndSubDir(ordenDireccion);
                    if (hhpp != null) {
                        if (hhpp.getHhppSubEdificioObj() != null) {
                            CmtSubEdificioMgl subEdificio = hhpp.getHhppSubEdificioObj();
                            CmtCuentaMatrizMgl cuentaMatriz = subEdificio.getCuentaMatrizObj();
                            if (cuentaMatriz != null) {
                                List<CmtHorarioRestriccionMgl> restriccionMgls
                                        = cmtHorarioRestriccionMglManager.findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

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
            LOGGER.error("Se generea error validando restricciones en AgendamientoRunAble: validarRestriccionesHhppCcmm()" + e.getMessage());
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
    public String generarNumeroOtRr(String codCuentaGenerico, String numeroOtRr, OtHhppMgl otHhppMgl,
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

    public boolean validarCreacion() throws ApplicationException {

        boolean respuesta = true;
        try {
            String subTipoWorForce = retornaSubtipoWorfoce(ordenTrabajoMgl);
            //Consultamos si la orden tiene agendas pendientes
            agendamientoMglManager = new CmtAgendamientoMglManager();

            List<CmtAgendamientoMgl> agendasCons = agendamientoMglManager.
                    agendasPorOrdenAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorForce);
            if (!agendasCons.isEmpty()) {
                for (CmtAgendamientoMgl agendasFor : agendasCons) {
                    if (agendasFor.getUltimaAgenda().equalsIgnoreCase("Y")) {
                        respuesta = false;
                    }
                }
            }
        } catch (ApplicationException e) {
            throw new ApplicationException("Ocurrió un error validando creacion en  AgendamientoRunAble: validarCreacion() " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ApplicationException("Ocurrió un error validando creacion en  AgendamientoRunAble: validarCreacion() " + e.getMessage(), e);
        }
        return respuesta;
    }

    public String retornaSubtipoWorfoce(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        String subtipoWorfoce = "";
        try {
            if (ot != null) {
                estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
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
            throw new ApplicationException("Ocurrió un error validando crenacion en  AgendamientoRunAble: retornaSubtipoWorfoce() " + e.getMessage(), e);
        }
        return subtipoWorfoce;
    }

    public boolean validarExistenciaAgendaCerrada() throws ApplicationException {

        agendamientoHhppMglManager = new AgendamientoHhppMglManager();

        List<MglAgendaDireccion> agendasAsociadasAlaOt
                = agendamientoHhppMglManager.buscarAgendasPorOt(ordenDireccion);

        for (MglAgendaDireccion agendaLis : agendasAsociadasAlaOt) {
            if (agendaLis.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equals(Constant.ESTADO_AGENDA_CERRADA)) {
                return true;
            }
        }
        return false;
    }

}
