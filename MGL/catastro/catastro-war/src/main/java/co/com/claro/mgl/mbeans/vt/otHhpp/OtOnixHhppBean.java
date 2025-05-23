/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dao.procesomasivo.CmtNotasOtHijaDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.OnyxOtCmDirlFacadeLocal;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bocanegravm
 */
@ViewScoped
@ManagedBean(name = "otOnixHhppBean")
public class OtOnixHhppBean {

    private static final String TAB_ONIX_EDITOR_OT_HHPP = "TABONIXEDITOROTHHPP";
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private int perfilVt = 0;
    private static final Logger LOGGER = LogManager.getLogger(OtOnixHhppBean.class);
    private String selectedTab = "ONYX";
    private OtHhppMgl otHhppSeleccionado;
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private boolean activacionUCM;
    private BigDecimal numeroOtHija;
    private String OnyxNitCliente = "";
    private String OnyxNombreCliente = "";
    private String OnyxNombreOtHija = "";
    private String OnyxNumeroOtPadre = "";
    private String OnyxNumeroOtHija = "";
    private String OnyxFechaCreOtHija = "";
    private String OnyxFechaCreOtPadre = "";
    private String OnyxContactoTecOtPadre = "";
    private String OnyxTelContactoTecOtPadre = "";
    private String OnyxDescripcion = "";
    private String OnyxDireccion = "";
    private String OnyxSegmento = "";
    private String OnyxTipoServicio = "";
    private String OnyxServicios = "";
    private String OnyxRecurrenteMensual = "";
    private String OnyxCodigoServicio = "";
    private String OnyxVendedor = "";
    private String OnyxTelefono = "";
    private String OnyxNotasOtHija = "";
    private String OnyxEstadoOtHija = "";
    private String OnyxEstadoOtPadre = "";
    private String OnyxFechaCompromisoOtPadre = "";
    private String OnyxCodResolOtPadre1 = "";
    private String OnyxCodResolOtPadre2 = "";
    private String OnyxCodResolOtPadre3 = "";
    private String OnyxCodResolOtPadre4 = "";
    private String OnyxCodResolOtHija1 = "";
    private String OnyxCodResolOtHija2 = "";
    private String OnyxCodResolOtHija3 = "";
    private String OnyxCodResolOtHija4 = "";
    private CmtOnyxResponseDto cmtOnyxResponseDto;
    private List<CmtNotasOtHijaDto> lstCmtNotasOtHijaDto;
    private int actualNotOtHij;
    private String numPaginaNotOtHij = "1";
    private List<Integer> paginaListNotOtHij;
    private String pageActualNotOtHij;
    private int filasPagNotOtHij = ConstantsCM.PAGINACION_CUATRO_FILAS;
    private List<CmtNotasOtHijaDto> lstCmtNotasOtHijaDtoAux;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private boolean otCerradaAnulada = false;
    private String OnyxComplejidad = "";
    private OnyxOtCmDir onyxOtCmDir;

    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    @EJB
    private OnyxOtCmDirlFacadeLocal onyxOtCmDirlFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    public OtOnixHhppBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

            if (usuarioVT == null) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en OtOnixHhppBean, validando autenticación. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en OtOnixHhppBean, validando autenticación. ", e, LOGGER);
        }

    }

    @PostConstruct
    public void init() {

        try {
            cargarOtHhppSeleccionada();
            consultaInicialOtOnyx();
            validarEstadoRenderizarBotones(otHhppSeleccionado);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al hacer cargue inicial de la página: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al hacer cargue inicial de la página: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Metodo para validar si el usuario tiene permisos de creacion
     * @return  {@code boolean} true si tiene permisos de creacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEditar(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_ONIX_EDITOR_OT_HHPP, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación. ");
        }
        return false;
    }

    /**
     * Función que valida si la OT esta cerrada o anulada para renderizar los
     * botones de guardar cambios
     *
     * @author Juan David Hernandez
     * @param otHhppSeleccionado
     */
    public void validarEstadoRenderizarBotones(OtHhppMgl otHhppSeleccionado) {
        try {
            if (otHhppSeleccionado != null && otHhppSeleccionado.getEstadoGeneral() != null
                    && otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp() != null
                    && !otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().isEmpty()) {

                if (otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO)
                        || otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO_COMPLETO)) {
                    otCerradaAnulada = true;
                } else {
                    otCerradaAnulada = false;
                }
            } else {
                otCerradaAnulada = false;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al validar estado de la ot para renderizar botones ", e, LOGGER);

        }
    }

    /**
     * Función que permite navegar entre Tabs en la pantalla
     *
     * @author Victor Bocanegra
     * @param sSeleccionado
     */
    public void cambiarTab(String sSeleccionado) {
        try {
            ConstantsCM.TABS_HHPP Seleccionado
                    = ConstantsCM.TABS_HHPP.valueOf(sSeleccionado);
            switch (Seleccionado) {
                case GENERAL:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "editarOtHhpp.jsf");
                    break;
                case AGENDAMIENTO:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "agendamientoOtHhpp.jsf");
                    selectedTab = "AGENDAMIENTO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case NOTAS:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "notasOtHhpp.jsf");
                    selectedTab = "NOTAS";
                    break;
                case ONYX:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "otOnixHhpp.jsf");
                    selectedTab = "ONYX";
                    consultaInicialOtOnyx();
                    break;
                case HISTORICO:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "historicoAgendasOtHhpp.jsf");
                    selectedTab = "HISTORICO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case BITACORA:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "bitacoraOtHhpp.jsf");
                    selectedTab = "BITACORA";
                    break;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
        }
    }

    public void cargarOtHhppSeleccionada() throws ApplicationException {
        try {
            OtHhppMglSessionBean otHhppMglSessionBean
                    = JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
            OtHhppMgl otHhpp = new OtHhppMgl();
            otHhpp.setOtHhppId(otHhppMglSessionBean.getOtHhppMglSeleccionado().getOtHhppId());
            otHhppSeleccionado = otHhppMglFacadeLocal.findOtByIdOt(otHhpp.getOtHhppId());

        } catch (RuntimeException | ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public boolean activaDesactivaUCM() {

        String msn;
        try {
            ParametrosMgl parametrosMgl = parametrosMglFacadeLocal.findByAcronimoName(Constant.ACTIVACION_ENVIO_UCM);
            String valor;
            if (parametrosMgl != null) {
                valor = parametrosMgl.getParValor();
                if (!valor.equalsIgnoreCase("1") && !valor.equalsIgnoreCase("0")) {
                    msn = "El valor configurado para el parametro:  "
                            + "" + Constant.ACTIVACION_ENVIO_UCM + " debe ser '1' o '0'  "
                            + "actualmente se encuentra el valor: " + valor + "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    activacionUCM = false;
                } else if (valor.equalsIgnoreCase("1")) {
                    activacionUCM = true;
                } else {
                    activacionUCM = false;
                }

            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + ex.getMessage(), ex, LOGGER);
            activacionUCM = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + e.getMessage(), e, LOGGER);
            activacionUCM = false;
        }
        return activacionUCM;
    }

    public void consultaInicialOtOnyx() throws ApplicationException {
        List<OnyxOtCmDir> listaOtOnyx = onyxOtCmDirlFacadeLocal.findOnyxOtHhppById(otHhppSeleccionado.getOtHhppId());
        String id;
        BigDecimal idOtOnyxMgl = null;
        if (listaOtOnyx != null && !listaOtOnyx.isEmpty()) {
            id = listaOtOnyx.get(0).getOnyx_Ot_Hija_Dir();
            idOtOnyxMgl = new BigDecimal(id);
        }
        limpiarCamposOnyx();
        try {
            if (idOtOnyxMgl != null) {
                //Validamos disponibilidad del servicio
                ParametrosMgl wsdlOtHija
                        = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                if (wsdlOtHija == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                }

                URL urlCon = new URL(wsdlOtHija.getParValor());

                ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                //Fin Validacion disponibilidad del servicio 
                
                cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(idOtOnyxMgl.toString());
                if (cmtOnyxResponseDto != null) {
                    setOnyx(cmtOnyxResponseDto);
                    OnyxComplejidad = listaOtOnyx != null ? listaOtOnyx.get(0).getComplejidadServicio() : "";
                    listInfoByPageNotOtHija(1);
                } else {
                    String msnError = "OT con ID " + numeroOtHija + " no encontrada en Onyx.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msnError, ""));
                    limpiarCamposOnyx();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("OT con ID " + numeroOtHija + " no encontrada en Onyx.", e, LOGGER);
            limpiarCamposOnyx();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al consultaInicialOtOnyx con ID " + numeroOtHija + " en Onyx: " + e.getMessage(), e, LOGGER);
            limpiarCamposOnyx();
        }
    }

    public void consultarOTOnyx() {
        List<OnyxOtCmDir> onyxOtCmBD;
        if (!numeroOtHija.toString().isEmpty()) {
            try {
                //Validamos disponibilidad del servicio
                ParametrosMgl wsdlOtHija
                        = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                if (wsdlOtHija == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                }

                URL urlCon = new URL(wsdlOtHija.getParValor());

                ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                //Fin Validacion disponibilidad del servicio

                cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(numeroOtHija.toString());
                if (cmtOnyxResponseDto != null) {
                    setOnyx(cmtOnyxResponseDto);
                    // persistencia de los campos Onyx en BD
                    onyxOtCmDir = setFieldsOnyxToEntity(cmtOnyxResponseDto);
                    // se valida si la ot ya esta almacenada en la tabla Onyx
                    onyxOtCmBD = onyxOtCmDirlFacadeLocal.findOnyxOtHhppById(otHhppSeleccionado.getOtHhppId());
                    if (onyxOtCmBD != null && !onyxOtCmBD.isEmpty()) {
                        for (OnyxOtCmDir onyxOtCm : onyxOtCmBD) {
                            onyxOtCm.setEstadoRegistro(0);
                            onyxOtCmDirlFacadeLocal.update(onyxOtCm);
                        }
                        onyxOtCmDirlFacadeLocal.create(onyxOtCmDir);
                    } else {
                        onyxOtCmDirlFacadeLocal.create(onyxOtCmDir);
                    }

                    try {
                        MERNotifyResponseType responseType = otHhppMglFacadeLocal.notificarOrdenOnix(otHhppSeleccionado, numeroOtHija.toString());
                        if (responseType != null && responseType.getIcodOperacion().equalsIgnoreCase("1")) {
                            String msnError = "Operacion Exitosa: " + responseType.getVchDescOperacion() + "";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            msnError, ""));
                        }
                    } catch (ApplicationException e) {
                        String msnError = "No fue posible realizar la notificación "
                                + "de la orden a Onyx por disponibilidad en el servicio "
                                + "de notificación: "+e.getMessage()+"";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                    }

                    listInfoByPageNotOtHija(1);
                } else {
                    String msnError = "OT con ID " + numeroOtHija + " no encontrada en Onyx.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msnError, ""));
                    limpiarCamposOnyx();
                }
            } catch (ApplicationException |IOException | ParseException e) {
                FacesUtil.mostrarMensajeError("Error al consultarOTOnyx OT con ID " 
                        + numeroOtHija + " no encontrada en Onyx: " + e.getMessage(), e, LOGGER);
                limpiarCamposOnyx();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar"
                            + " Id de incidente OT Onyx", "Incidente Id OT Onyx"));
        }
    }

    public void setOnyx(CmtOnyxResponseDto cmtOnyxResponseDto) {

        if (cmtOnyxResponseDto != null) {

            OnyxNitCliente = cmtOnyxResponseDto.getNIT_Cliente();
            OnyxNombreCliente = cmtOnyxResponseDto.getNombre();
            OnyxNombreOtHija = cmtOnyxResponseDto.getNombre_OT_Hija();
            OnyxNumeroOtPadre = String.valueOf(cmtOnyxResponseDto.getOTP());
            OnyxNumeroOtHija = String.valueOf(cmtOnyxResponseDto.getOTH());
            OnyxFechaCreOtHija = cmtOnyxResponseDto.getFechaCreacionOTH();
            OnyxFechaCreOtPadre = cmtOnyxResponseDto.getFechaCreacionOTP();
            OnyxContactoTecOtPadre = cmtOnyxResponseDto.getContactoTecnicoOTP();
            OnyxTelContactoTecOtPadre = cmtOnyxResponseDto.getTelefonoContacto();
            OnyxDescripcion = cmtOnyxResponseDto.getDescripcion();
            OnyxDireccion = cmtOnyxResponseDto.getDireccion();
            OnyxSegmento = cmtOnyxResponseDto.getSegmento();
            OnyxTipoServicio = cmtOnyxResponseDto.getTipoServicio();
            OnyxServicios = cmtOnyxResponseDto.getServicios();
            OnyxRecurrenteMensual = cmtOnyxResponseDto.getRecurrenteMensual().toString();
            OnyxCodigoServicio = cmtOnyxResponseDto.getCodigoServicio();
            OnyxVendedor = cmtOnyxResponseDto.getVendedor();
            OnyxTelefono = cmtOnyxResponseDto.getTelefono();
            OnyxNotasOtHija = cmtOnyxResponseDto.getNotasOTH();
            cargarListaNotas(OnyxNotasOtHija);
            OnyxEstadoOtHija = cmtOnyxResponseDto.getEstadoOTH();
            OnyxEstadoOtPadre = cmtOnyxResponseDto.getEstadoOTP();
            OnyxFechaCompromisoOtPadre = cmtOnyxResponseDto.getFechaCompromisoOTP();
            OnyxCodResolOtPadre1 = cmtOnyxResponseDto.getCodResolucion1OTP();
            OnyxCodResolOtPadre2 = cmtOnyxResponseDto.getCodResolucion2OTP();
            OnyxCodResolOtPadre3 = cmtOnyxResponseDto.getCodResolucion3OTP();
            OnyxCodResolOtPadre4 = cmtOnyxResponseDto.getCodResolucion4OTP();
            OnyxCodResolOtHija1 = cmtOnyxResponseDto.getCodResolucion1OTH();
            OnyxCodResolOtHija2 = cmtOnyxResponseDto.getCodResolucion2OTH();
            OnyxCodResolOtHija3 = cmtOnyxResponseDto.getCodResolucion3OTH();
            OnyxCodResolOtHija4 = cmtOnyxResponseDto.getCodResolucion4OTH();
            numeroOtHija = new BigDecimal(OnyxNumeroOtHija);
        }
    }

    public void limpiarCamposOnyx() {

        OnyxNitCliente = "";
        OnyxNombreCliente = "";
        OnyxNombreOtHija = "";
        OnyxNumeroOtPadre = "";
        OnyxNumeroOtHija = "";
        OnyxFechaCreOtHija = "";
        OnyxFechaCreOtPadre = "";
        OnyxContactoTecOtPadre = "";
        OnyxTelContactoTecOtPadre = "";
        OnyxDescripcion = "";
        OnyxDireccion = "";
        OnyxSegmento = "";
        OnyxTipoServicio = "";
        OnyxServicios = "";
        OnyxRecurrenteMensual = "";
        OnyxCodigoServicio = "";
        OnyxVendedor = "";
        OnyxTelefono = "";
        OnyxNotasOtHija = "";
        OnyxEstadoOtHija = "";
        OnyxEstadoOtPadre = "";
        OnyxFechaCompromisoOtPadre = "";
        OnyxCodResolOtPadre1 = "";
        OnyxCodResolOtPadre2 = "";
        OnyxCodResolOtPadre3 = "";
        OnyxCodResolOtPadre4 = "";
        OnyxCodResolOtHija1 = "";
        OnyxCodResolOtHija2 = "";
        OnyxCodResolOtHija3 = "";
        OnyxCodResolOtHija4 = "";
        numeroOtHija = null;
        OnyxComplejidad = "";

    }

    public void cargarListaNotas(String cadenaNotas) {

        String cadenaSinEspacios = cadenaNotas.replaceAll("\\s+", " ");
        String[] parts = cadenaSinEspacios.split(Constant.SPLIT_CADENA_NOTAS_OT_HIJA);
        String[] datosNotas;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        try {

            if (parts.length > 0) {
                lstCmtNotasOtHijaDto = new ArrayList<>();

                int inicioCad = 0;
                for (String part : parts) {

                    if (!part.isEmpty()) {

                        CmtNotasOtHijaDto notasOtHijaDto = new CmtNotasOtHijaDto();
                        datosNotas = part.split("(?=\\s)");
                        notasOtHijaDto.setUsuarioIngresaNota(datosNotas[0]);
                        Date fechaIngresoNota = formatter.parse(datosNotas[1]);
                        notasOtHijaDto.setFechaIngresaNota(fechaIngresoNota);
                        inicioCad = datosNotas[0].length() + datosNotas[1].length();
                        String cuerpo = part.substring(inicioCad, part.length());
                        String[] notaPar = cuerpo.split(";");
                        String notaFin = "";
                        for (String nota : notaPar) {
                            notaFin = notaFin + nota + ";" + "\n";
                        }
                        notasOtHijaDto.setDescripcionNota(notaFin);
                        lstCmtNotasOtHijaDto.add(notasOtHijaDto);
                    }
                }
                Collections.sort(lstCmtNotasOtHijaDto);
                lstCmtNotasOtHijaDtoAux = lstCmtNotasOtHijaDto;

            }
        } catch (ParseException ex) {
            FacesUtil.mostrarMensajeError("Error al parsear la fecha: " + ex.getMessage(), ex, LOGGER);

        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al cargar lista de notas : " + ex.getMessage(), ex, LOGGER);
        }
    }

    public void cargarNota(String notaOtHi) {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, notaOtHi, ""));

    }

    ////////Paginado notas Ot HIja///////////
    public void listInfoByPageNotOtHija(int page) throws ApplicationException {
        try {
            actualNotOtHij = page;
            getTotalPaginasNotOtHija();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPagNotOtHij * (page - 1));
            }
            //Obtenemos el rango de la paginación
            if (lstCmtNotasOtHijaDtoAux != null && lstCmtNotasOtHijaDtoAux.size() > 0) {

                int maxResult;
                if ((firstResult + filasPagNotOtHij) > lstCmtNotasOtHijaDtoAux.size()) {
                    maxResult = lstCmtNotasOtHijaDtoAux.size();
                } else {
                    maxResult = (firstResult + filasPagNotOtHij);
                }

                lstCmtNotasOtHijaDto = new ArrayList<>();
                for (int k = firstResult; k < maxResult; k++) {
                    lstCmtNotasOtHijaDto.add(lstCmtNotasOtHijaDtoAux.get(k));
                }
            }

        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageNotOtHija en lista de paginación. ", e, LOGGER);
            throw new ApplicationException(e);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageNotOtHija en lista de paginación. ", e, LOGGER);
            throw new ApplicationException(e);
        }
    }

    public void pageFirstNotOtHija() {
        try {
            listInfoByPageNotOtHija(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstNotOtHija direccionando a la primera página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstNotOtHija direccionando a la primera página. ", e, LOGGER);
        }
    }

    public void pagePreviousNotOtHija() {
        try {
            int totalPaginas = getTotalPaginasNotOtHija();
            int nuevaPageActual = actualNotOtHij - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageNotOtHija(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousNotOtHija direccionando a página anterior. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousNotOtHija direccionando a página anterior. ", e, LOGGER);
        }
    }

    public void irPaginaNotOtHija() {
        try {
            int totalPaginas = getTotalPaginasNotOtHija();
            int nuevaPageActual = Integer.parseInt(numPaginaNotOtHij);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageNotOtHija(nuevaPageActual);
        } catch (NumberFormatException | ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaNotOtHija direccionando a página. ", e, LOGGER);
        } 
    }

    public void pageNextNotOtHija() {
        try {
            int totalPaginas = getTotalPaginasNotOtHija();
            int nuevaPageActual = actualNotOtHij + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageNotOtHija(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageNextNotOtHija direccionando a la siguiente página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNextNotOtHija direccionando a la siguiente página. ", e, LOGGER);
        }
    }

    public void pageLastNotOtHija() {
        try {

            int totalPaginas = getTotalPaginasNotOtHija();
            listInfoByPageNotOtHija(totalPaginas);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageLastNotOtHija direccionando a la última página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLastNotOtHija direccionando a la última página. ", e, LOGGER);
        }
    }

    public int getTotalPaginasNotOtHija() {
        try {
            if (lstCmtNotasOtHijaDtoAux != null) {
                //obtener la lista original para conocer su tamaño total                 
                int pageSol = lstCmtNotasOtHijaDtoAux.size();
                return (pageSol % filasPagNotOtHij != 0)
                        ? (pageSol / filasPagNotOtHij) + 1 : pageSol / filasPagNotOtHij;
            }

        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasNotOtHija direccionando a la primera página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasNotOtHija direccionando a la primera página. ", e, LOGGER);
        }
        return 1;
    }

    public String getPageActualNotOtHija() {
        paginaListNotOtHij = new ArrayList<>();
        int totalPaginas = getTotalPaginasNotOtHija();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListNotOtHij.add(i);
        }
        pageActualNotOtHij = String.valueOf(actualNotOtHij) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaNotOtHij == null) {
            numPaginaNotOtHij = "1";
        }
        numPaginaNotOtHij = String.valueOf(actualNotOtHij);
        return pageActualNotOtHij;
    }

    public OnyxOtCmDir setFieldsOnyxToEntity(CmtOnyxResponseDto cmtOnyxResponseDto) throws ParseException {
        if (cmtOnyxResponseDto != null) {
            onyxOtCmDir = new OnyxOtCmDir();
            onyxOtCmDir.setOt_Id_Cm(null);
            onyxOtCmDir.setOt_Direccion_Id(otHhppSeleccionado);
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
             if (cmtOnyxResponseDto.getFechaCompromisoOTP() != null && 
                     !cmtOnyxResponseDto.getFechaCompromisoOTP() .isEmpty()) {
                SimpleDateFormat formatterFechaCompOTP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (cmtOnyxResponseDto.getFechaCompromisoOTP() != null && !cmtOnyxResponseDto.getFechaCompromisoOTP().isEmpty()) {
                Date fechaCompOTP = formatterFechaCompOTP.parse(cmtOnyxResponseDto.getFechaCompromisoOTP());
                onyxOtCmDir.setFecha_Compromiso_Ot_Padre_Onyx(fechaCompOTP);
            }else{
                 onyxOtCmDir.setFecha_Compromiso_Ot_Padre_Onyx(null);
            
                }

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
            onyxOtCmDir.setUsuarioCreacion(usuarioVT);
            onyxOtCmDir.setUsuarioEdicion(usuarioVT);
            
            onyxOtCmDir.setCellTec(cmtOnyxResponseDto.getCellTec());
            onyxOtCmDir.setEmailTec(cmtOnyxResponseDto.getEmailCTec());
            onyxOtCmDir.setCodigoProyecto(cmtOnyxResponseDto.getCodProyecto());
            onyxOtCmDir.setRegionalOrigen(cmtOnyxResponseDto.getRegionalDestino());
            onyxOtCmDir.setaImplement(cmtOnyxResponseDto.getaImplement());
            onyxOtCmDir.setCiudadFact(cmtOnyxResponseDto.getCiudadFacturacion());
            onyxOtCmDir.setComplejidadServicio(OnyxComplejidad);
        }
        return onyxOtCmDir;
    }

    public BigDecimal getNumeroOtHija() {
        return numeroOtHija;
    }

    public void setNumeroOtHija(BigDecimal numeroOtHija) {
        this.numeroOtHija = numeroOtHija;
    }

    public String getOnyxNitCliente() {
        return OnyxNitCliente;
    }

    public void setOnyxNitCliente(String OnyxNitCliente) {
        this.OnyxNitCliente = OnyxNitCliente;
    }

    public String getOnyxNombreCliente() {
        return OnyxNombreCliente;
    }

    public void setOnyxNombreCliente(String OnyxNombreCliente) {
        this.OnyxNombreCliente = OnyxNombreCliente;
    }

    public String getOnyxNombreOtHija() {
        return OnyxNombreOtHija;
    }

    public void setOnyxNombreOtHija(String OnyxNombreOtHija) {
        this.OnyxNombreOtHija = OnyxNombreOtHija;
    }

    public String getOnyxNumeroOtPadre() {
        return OnyxNumeroOtPadre;
    }

    public void setOnyxNumeroOtPadre(String OnyxNumeroOtPadre) {
        this.OnyxNumeroOtPadre = OnyxNumeroOtPadre;
    }

    public String getOnyxNumeroOtHija() {
        return OnyxNumeroOtHija;
    }

    public void setOnyxNumeroOtHija(String OnyxNumeroOtHija) {
        this.OnyxNumeroOtHija = OnyxNumeroOtHija;
    }

    public String getOnyxFechaCreOtHija() {
        return OnyxFechaCreOtHija;
    }

    public void setOnyxFechaCreOtHija(String OnyxFechaCreOtHija) {
        this.OnyxFechaCreOtHija = OnyxFechaCreOtHija;
    }

    public String getOnyxFechaCreOtPadre() {
        return OnyxFechaCreOtPadre;
    }

    public void setOnyxFechaCreOtPadre(String OnyxFechaCreOtPadre) {
        this.OnyxFechaCreOtPadre = OnyxFechaCreOtPadre;
    }

    public String getOnyxContactoTecOtPadre() {
        return OnyxContactoTecOtPadre;
    }

    public void setOnyxContactoTecOtPadre(String OnyxContactoTecOtPadre) {
        this.OnyxContactoTecOtPadre = OnyxContactoTecOtPadre;
    }

    public String getOnyxTelContactoTecOtPadre() {
        return OnyxTelContactoTecOtPadre;
    }

    public void setOnyxTelContactoTecOtPadre(String OnyxTelContactoTecOtPadre) {
        this.OnyxTelContactoTecOtPadre = OnyxTelContactoTecOtPadre;
    }

    public String getOnyxDescripcion() {
        return OnyxDescripcion;
    }

    public void setOnyxDescripcion(String OnyxDescripcion) {
        this.OnyxDescripcion = OnyxDescripcion;
    }

    public String getOnyxDireccion() {
        return OnyxDireccion;
    }

    public void setOnyxDireccion(String OnyxDireccion) {
        this.OnyxDireccion = OnyxDireccion;
    }

    public String getOnyxSegmento() {
        return OnyxSegmento;
    }

    public void setOnyxSegmento(String OnyxSegmento) {
        this.OnyxSegmento = OnyxSegmento;
    }

    public String getOnyxTipoServicio() {
        return OnyxTipoServicio;
    }

    public void setOnyxTipoServicio(String OnyxTipoServicio) {
        this.OnyxTipoServicio = OnyxTipoServicio;
    }

    public String getOnyxServicios() {
        return OnyxServicios;
    }

    public void setOnyxServicios(String OnyxServicios) {
        this.OnyxServicios = OnyxServicios;
    }

    public String getOnyxRecurrenteMensual() {
        return OnyxRecurrenteMensual;
    }

    public void setOnyxRecurrenteMensual(String OnyxRecurrenteMensual) {
        this.OnyxRecurrenteMensual = OnyxRecurrenteMensual;
    }

    public String getOnyxCodigoServicio() {
        return OnyxCodigoServicio;
    }

    public void setOnyxCodigoServicio(String OnyxCodigoServicio) {
        this.OnyxCodigoServicio = OnyxCodigoServicio;
    }

    public String getOnyxVendedor() {
        return OnyxVendedor;
    }

    public void setOnyxVendedor(String OnyxVendedor) {
        this.OnyxVendedor = OnyxVendedor;
    }

    public String getOnyxTelefono() {
        return OnyxTelefono;
    }

    public void setOnyxTelefono(String OnyxTelefono) {
        this.OnyxTelefono = OnyxTelefono;
    }

    public String getOnyxNotasOtHija() {
        return OnyxNotasOtHija;
    }

    public void setOnyxNotasOtHija(String OnyxNotasOtHija) {
        this.OnyxNotasOtHija = OnyxNotasOtHija;
    }

    public String getOnyxEstadoOtHija() {
        return OnyxEstadoOtHija;
    }

    public void setOnyxEstadoOtHija(String OnyxEstadoOtHija) {
        this.OnyxEstadoOtHija = OnyxEstadoOtHija;
    }

    public String getOnyxEstadoOtPadre() {
        return OnyxEstadoOtPadre;
    }

    public void setOnyxEstadoOtPadre(String OnyxEstadoOtPadre) {
        this.OnyxEstadoOtPadre = OnyxEstadoOtPadre;
    }

    public String getOnyxFechaCompromisoOtPadre() {
        return OnyxFechaCompromisoOtPadre;
    }

    public void setOnyxFechaCompromisoOtPadre(String OnyxFechaCompromisoOtPadre) {
        this.OnyxFechaCompromisoOtPadre = OnyxFechaCompromisoOtPadre;
    }

    public String getOnyxCodResolOtPadre1() {
        return OnyxCodResolOtPadre1;
    }

    public void setOnyxCodResolOtPadre1(String OnyxCodResolOtPadre1) {
        this.OnyxCodResolOtPadre1 = OnyxCodResolOtPadre1;
    }

    public String getOnyxCodResolOtPadre2() {
        return OnyxCodResolOtPadre2;
    }

    public void setOnyxCodResolOtPadre2(String OnyxCodResolOtPadre2) {
        this.OnyxCodResolOtPadre2 = OnyxCodResolOtPadre2;
    }

    public String getOnyxCodResolOtPadre3() {
        return OnyxCodResolOtPadre3;
    }

    public void setOnyxCodResolOtPadre3(String OnyxCodResolOtPadre3) {
        this.OnyxCodResolOtPadre3 = OnyxCodResolOtPadre3;
    }

    public String getOnyxCodResolOtPadre4() {
        return OnyxCodResolOtPadre4;
    }

    public void setOnyxCodResolOtPadre4(String OnyxCodResolOtPadre4) {
        this.OnyxCodResolOtPadre4 = OnyxCodResolOtPadre4;
    }

    public String getOnyxCodResolOtHija1() {
        return OnyxCodResolOtHija1;
    }

    public void setOnyxCodResolOtHija1(String OnyxCodResolOtHija1) {
        this.OnyxCodResolOtHija1 = OnyxCodResolOtHija1;
    }

    public String getOnyxCodResolOtHija2() {
        return OnyxCodResolOtHija2;
    }

    public void setOnyxCodResolOtHija2(String OnyxCodResolOtHija2) {
        this.OnyxCodResolOtHija2 = OnyxCodResolOtHija2;
    }

    public String getOnyxCodResolOtHija3() {
        return OnyxCodResolOtHija3;
    }

    public void setOnyxCodResolOtHija3(String OnyxCodResolOtHija3) {
        this.OnyxCodResolOtHija3 = OnyxCodResolOtHija3;
    }

    public String getOnyxCodResolOtHija4() {
        return OnyxCodResolOtHija4;
    }

    public void setOnyxCodResolOtHija4(String OnyxCodResolOtHija4) {
        this.OnyxCodResolOtHija4 = OnyxCodResolOtHija4;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public OtHhppMgl getOtHhppSeleccionado() {
        return otHhppSeleccionado;
    }

    public void setOtHhppSeleccionado(OtHhppMgl otHhppSeleccionado) {
        this.otHhppSeleccionado = otHhppSeleccionado;
    }

    public String getNumPaginaNotOtHij() {
        return numPaginaNotOtHij;
    }

    public void setNumPaginaNotOtHij(String numPaginaNotOtHij) {
        this.numPaginaNotOtHij = numPaginaNotOtHij;
    }

    public List<Integer> getPaginaListNotOtHij() {
        return paginaListNotOtHij;
    }

    public void setPaginaListNotOtHij(List<Integer> paginaListNotOtHij) {
        this.paginaListNotOtHij = paginaListNotOtHij;
    }

    public int getFilasPagNotOtHij() {
        return filasPagNotOtHij;
    }

    public void setFilasPagNotOtHij(int filasPagNotOtHij) {
        this.filasPagNotOtHij = filasPagNotOtHij;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public List<CmtNotasOtHijaDto> getLstCmtNotasOtHijaDto() {
        return lstCmtNotasOtHijaDto;
    }

    public void setLstCmtNotasOtHijaDto(List<CmtNotasOtHijaDto> lstCmtNotasOtHijaDto) {
        this.lstCmtNotasOtHijaDto = lstCmtNotasOtHijaDto;
    }

    public boolean isOtCerradaAnulada() {
        return otCerradaAnulada;
    }

    public void setOtCerradaAnulada(boolean otCerradaAnulada) {
        this.otCerradaAnulada = otCerradaAnulada;
    }

    public String getOnyxComplejidad() {
        return OnyxComplejidad;
    }

    public void setOnyxComplejidad(String OnyxComplejidad) {
        this.OnyxComplejidad = OnyxComplejidad;
    }
}
