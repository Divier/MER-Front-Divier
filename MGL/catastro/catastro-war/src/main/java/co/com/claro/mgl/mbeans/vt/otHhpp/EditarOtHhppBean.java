/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.SolicitudManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.AgendamientoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.direcciones.DetalleHhppBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "editarOtHhppBean")
public class EditarOtHhppBean implements Serializable {

    private static final String TABGENERALEDITOROTHHPP = "TABGENERALEDITOROTHHPP";
    private SecurityLogin securityLogin;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private String selectedTab = "GENERAL";
    private String nombreContacto;
    private String telefonoContacto;
    private String correoContacto;
    private String direccionDetalladaSeleccionada;
    private Date fechaCreacionOt;
    private String[] tecnologiasSeleccionadas;
    private String[] nodosSeleccionados;
    private boolean detalleOtHhpp;
    private boolean showTecnologias;
    private BigDecimal tipoOtHhppId;
    private BigDecimal numeroOt;
    private BigDecimal estadoInicial;
    private CmtBasicaMgl estadoInicialBasica;
    private OtHhppMgl otHhppSeleccionado;
    private List<CmtBasicaMgl> tipoNotaList;
    private List<CmtBasicaMgl> tecnologiasBasicasList;
    private List<CmtBasicaMgl> tecnologiasBasicaList = new ArrayList<>();
    private List<TipoOtHhppMgl> tipoOtHhppList;
    private List<CmtBasicaMgl> estadoGeneralList;
    private List<CmtBasicaMgl> estadoInicialInternoList;
    private List<CmtBasicaMgl> tipoSegmentoOtList;
    private CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
    private List<CmtDireccionDetalladaMgl> cmtDireccionesDetalladasMgl;
    private DetalleDireccionEntity detalleDireccionEntity;
    private DrDireccion drDireccion;
    private BigDecimal estadoInicialInterno;
    private DireccionRREntity direccionRREntity;
    private String complementoDireccion;
    private GeograficoPoliticoMgl ciudadGpo;
    private GeograficoPoliticoMgl departamentoGpo;
    private GeograficoPoliticoMgl centroPobladoGpo;
    private String espacio = "&nbsp;";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(EditarOtHhppBean.class);
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private boolean activacionUCM;
    private boolean otCerradaAnulada = false;
    private TipoOtHhppMgl tipoOtHhppSeleccionada;

    
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private OtHhppTecnologiaMglFacadeLocal otHhppTecnologiaMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private AgendamientoHhppMglFacadeLocal agendamientoHhppFacade;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    public EditarOtHhppBean()  {
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
            FacesUtil.mostrarMensajeError("Error en EditarOtHhppBean. " +e.getMessage(), e, LOGGER);       
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EditarOtHhppBean. " +e.getMessage(), e, LOGGER);       
        }
    }

    @PostConstruct
    private void init() {
        try {
            cargarOtHhppSeleccionada();
            obtenerTipoOtHhppList();
            obtenerTipoTecnologiaList();
            otHhppMglFacadeLocal.setUser(usuarioVT, perfilVt);
            validarEstadoRenderizarBotones(otHhppSeleccionado);

        } catch (ApplicationException e) {
            LOGGER.error("Error en init. " +e.getMessage(), e);       
        } catch (Exception e) {
            LOGGER.error("Error en init. " +e.getMessage(), e);       
        }
    }

    /**
     * Método para validar si el usuario tiene permisos de Editar en general OT HHPP
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolEditar() {
        try {
            return ValidacionUtil.validarVisualizacion(TABGENERALEDITOROTHHPP,
                    ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);

        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de edición de General OT HHPP. ", e);
        }

        return false;
    }

    /**
     * Función que obtiene la Ot seleccionada y carga toda su informacion en
     * pantalla
     *
     * @author Orlando Velasquez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cargarOtHhppSeleccionada() throws ApplicationException {
        try {
            // Instacia Bean de Session para obtener la solicitud seleccionada
            OtHhppMglSessionBean otHhppMglSessionBean
                    = JSFUtil.getSessionBean(OtHhppMglSessionBean.class);

            BigDecimal idOtHhppSeleccionada
                    = otHhppMglSessionBean.getOtHhppMglSeleccionado().getOtHhppId();
            detalleOtHhpp = otHhppMglSessionBean.isDetalleOtHhpp();
            otHhppMglSessionBean.setDetalleOtHhpp(false);
            if (idOtHhppSeleccionada != null) {
                OtHhppMgl otHhpp = new OtHhppMgl();
                otHhpp.setOtHhppId(idOtHhppSeleccionada);
                otHhppSeleccionado = otHhppMglFacadeLocal.findById(otHhpp);
                tipoOtHhppId = otHhppSeleccionado.getTipoOtHhppId().getTipoOtId();
                if (tipoOtHhppId != null) {
                    tipoOtHhppSeleccionada = tipoOtHhppMglFacadeLocal.findTipoOtHhppById(tipoOtHhppId);
                }
                numeroOt = otHhppSeleccionado.getOtHhppId();
                nombreContacto = otHhppSeleccionado.getNombreContacto();
                telefonoContacto = otHhppSeleccionado.getTelefonoContacto();
                correoContacto = otHhppSeleccionado.getCorreoContacto();
                fechaCreacionOt = otHhppSeleccionado.getFechaCreacionOt();
                obtenerEstadoGeneralList();
                obtenerSegmentoList();
                estadoInicial = otHhppSeleccionado.getEstadoGeneral().getBasicaId();
                obtenerEstadoInicialInternoList(estadoInicial);
                estadoInicialInterno = cargarEstadoInicialInterno();
                nodosSeleccionados = obtenerListadoNodosCargados(otHhppSeleccionado.getTecnologiaBasicaList());                
                otHhppSeleccionado.setTecnologiaBasicaList(otHhppTecnologiaMglFacadeLocal.findOtHhppTecnologiaByOtHhppId(otHhppSeleccionado.getOtHhppId()));
                tecnologiasSeleccionadas = obtenerListadoTecnologiasCargadas(otHhppSeleccionado.getTecnologiaBasicaList());
                otHhppSeleccionado.setNodosSeleccionado(obtenerListadoNodosSeleccionados(otHhppSeleccionado.getTecnologiaBasicaList()));
                obtenerDireccionDetallada(otHhppSeleccionado);
                obtenerDrDireccion(cmtDireccionDetalladaMgl);
                obtenerDireccionFormatoRr(otHhppSeleccionado.getDireccionId().getUbicacion().getGpoIdObj().getGeoGpoId());
                obtenerComplementoDireccion();
                obtenerCiudadDepartamentoByCentroPobladoId(otHhppSeleccionado.getDireccionId().getUbicacion().getGpoIdObj().getGpoId());
                showTecnologias = otHhppSeleccionado.getTipoOtHhppId()
                        .getManejaTecnologias().compareTo(BigDecimal.ONE) == 0;
            } else {
                String msn = "Ocurrio un error intentando cargar la "
                        + "ot seleccionada";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msn, ""));
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarOtHhppSeleccionada, al intentar cargar la ot seleccionada: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarOtHhppSeleccionada, al intentar cargar la ot seleccionada: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que obtiene el estado inicial interno de la Ot seleccionada
     *
     * @author Orlando Velasquez
     * @return
     */
    public BigDecimal cargarEstadoInicialInterno() {
        if (otHhppSeleccionado.getEstadoInternoInicial() != null) {
            return otHhppSeleccionado.getEstadoInternoInicial().getBasicaId();
        }
        return null;
    }

    /**
     * Función que obtiene la direccion detallada extraida de la solicitud
     *
     * @author Juan David Hernandez
     * @param otHhppMgl
     */
    public void obtenerDireccionDetallada(OtHhppMgl otHhppMgl) {
        try {
            if (otHhppMgl != null) {

                BigDecimal subDireccion = null;

                if (otHhppMgl.getSubDireccionId() != null) {
                    subDireccion = otHhppMgl.getSubDireccionId().getSdiId();
                }

                cmtDireccionesDetalladasMgl = cmtDireccionDetalleMglFacadeLocal
                        .findDireccionDetallaByDirIdSdirId(otHhppMgl.getDireccionId().getDirId(), subDireccion);

                cmtDireccionDetalladaMgl = cmtDireccionesDetalladasMgl.get(0);

                if (cmtDireccionDetalladaMgl == null) {
                    throw new ApplicationException("No fue posible obtener la direccion detallada"
                            + " de la solicitud por tal motivo no se puede gestionar ");
                }
            } else {
                throw new ApplicationException("La solicitud no cuenta con la direccion "
                        + "detallada asociada por tal motivo no es posible gestionar");
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerDireccionDetallada, al obtener la direccion detallada de la solicitud. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerDireccionDetallada, al obtener la direccion detallada de la solicitud. ", e, LOGGER);
        }
    }

    /**
     * Función que el DrDireccion de una direccion apartir de la dirDetallada
     *
     * @author Juan David Hernandez
     * @param direccionDetallada
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerDrDireccion(CmtDireccionDetalladaMgl direccionDetallada)
            throws ApplicationException {
        try {
            if (direccionDetallada != null) {
                CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();

                drDireccion
                        = direccionDetalladaManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);

                if (drDireccion == null) {
                    throw new ApplicationException("No fue posible obtener la direccion apartir de la dirección detallada,"
                            + " intente crear de nuevo la solicitud ya quen o es posible gestionarla ");
                }
            } else {
                throw new ApplicationException("No fue posible obtener la direccion apartir de la dirección detallada,"
                        + " intente crear de nuevo la solicitud ya quen o es posible gestionarla ");
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerDrDireccion. al obtener la direccion detallada de la solicitud " +e.getMessage());   
             throw new ApplicationException("Error en obtenerDrDireccion. al obtener la direccion detallada de la solicitud " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerDrDireccion. al obtener la direccion detallada de la solicitud " +e.getMessage());   
             throw new ApplicationException("Error en obtenerDrDireccion. al obtener la direccion detallada de la solicitud: " + e.getMessage(), e);
        }
    }

    /**
     * Función que obtiene si la dirección es multi-origen y genera la dirección
     * en formato Rr.
     *
     * @author Juan David Hernandez
     * @param centroPobladoId
     */
    public void obtenerDireccionFormatoRr(BigDecimal centroPobladoId) throws ApplicationException {
        try {
            detalleDireccionEntity = new DetalleDireccionEntity();
            detalleDireccionEntity = drDireccion.convertToDetalleDireccionEntity();
            //obtenemos el centro poblado de la solicitud para conocer si la ciudad es multiorigen
            GeograficoPoliticoMgl centroPobladoSolicitud;
            centroPobladoSolicitud = geograficoPoliticoMglFacadeLocal
                    .findById(centroPobladoId);

            //Conocer si es multi-origen         
            detalleDireccionEntity.setMultiOrigen(centroPobladoSolicitud.getGpoMultiorigen());
            //obtener la direccion en formato RR
            direccionRREntity = direccionRRFacadeLocal
                    .generarDirFormatoRR(detalleDireccionEntity);

        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerDireccionFormatoRr. " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerDireccionFormatoRr: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerDireccionFormatoRr. " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerDireccionFormatoRr: " + e.getMessage(), e);
        }
    }

    /**
     * Función que obtiene el complemento de la dirección
     *
     * @author Juan David Hernandez
     */
    public void obtenerComplementoDireccion() {
        try {
            complementoDireccion = getComplementoDireccion(drDireccion);
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerComplementoDireccion. ", e, LOGGER);   
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerComplementoDireccion. ", e, LOGGER);   
        }
    }

    /**
     * Retorna el complemento de la direccion, o direccion intraducible.
     *
     * @param drDirec
     * @return
     */
    public String getComplementoDireccion(DrDireccion drDirec) {
        String dirResult = "";

        if (drDirec.getCpTipoNivel1() != null
                && !drDirec.getCpTipoNivel1().isEmpty()
                && drDirec.getCpValorNivel1() != null
                && !drDirec.getCpValorNivel1().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel1()
                    + " " + drDirec.getCpValorNivel1() + " ";
        }

        if (drDirec.getCpTipoNivel2() != null
                && !drDirec.getCpTipoNivel2().isEmpty()
                && drDirec.getCpValorNivel2()
                != null && !drDirec.getCpValorNivel2().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel2()
                    + " " + drDirec.getCpValorNivel2() + " ";
        }

        if (drDirec.getCpTipoNivel3() != null
                && !drDirec.getCpTipoNivel3().isEmpty()
                && drDirec.getCpValorNivel3()
                != null && !drDirec.getCpValorNivel3().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel3()
                    + " " + drDirec.getCpValorNivel3() + " ";
        }

        if (drDirec.getCpTipoNivel4() != null
                && !drDirec.getCpTipoNivel4().isEmpty()
                && drDirec.getCpValorNivel4()
                != null && !drDirec.getCpValorNivel4().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel4()
                    + " " + drDirec.getCpValorNivel4() + " ";
        }

        if (drDirec.getCpTipoNivel5() != null
                && !drDirec.getCpTipoNivel5().isEmpty()
                && drDirec.getCpValorNivel5()
                != null && !drDirec.getCpValorNivel5().isEmpty()) {

            if (drDirec.getCpTipoNivel5()
                    .equalsIgnoreCase(Constant.OPT_CASA_PISO)) {

                dirResult += "CASA" + " " + drDirec.getCpValorNivel5() + " ";

            } else if (drDirec.getCpTipoNivel5()
                    .equalsIgnoreCase(Constant.OPT_PISO_INTERIOR)
                    || drDirec.getCpTipoNivel5()
                            .equalsIgnoreCase(Constant.OPT_PISO_LOCAL)
                    || drDirec.getCpTipoNivel5()
                            .equalsIgnoreCase(Constant.OPT_PISO_APTO)) {

                dirResult += "PISO" + " " + drDirec.getCpValorNivel5() + " ";
            } else {
                dirResult += drDirec.getCpTipoNivel5()
                        + " " + drDirec.getCpValorNivel5() + " ";
            }

        }
        // @author Juan David Hernandez 
        if (drDirec.getCpTipoNivel5() != null
                && !drDirec.getCpTipoNivel5().isEmpty()
                && (drDirec.getCpValorNivel5() == null
                || drDirec.getCpValorNivel5().isEmpty())) {
            dirResult += "CASA";
        }
        if (drDirec.getBarrioTxtBM() != null
                && !drDirec.getBarrioTxtBM().isEmpty()) {
            dirResult += "Barrio" + " " + drDirec.getBarrioTxtBM();
        }

        if (drDirec.getCpTipoNivel6() != null
                && !drDirec.getCpTipoNivel6().isEmpty()
                && drDirec.getCpValorNivel6() != null
                && !drDirec.getCpValorNivel6().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel6() + " "
                    + drDirec.getCpValorNivel6() + " ";
        }
        return dirResult;
    }

    /**
     * Función que obtiene listado de unidades asociadas al predio
     *
     * @author Juan David Hernandez
     * @param centroPobladoId
     */
    public void obtenerCiudadDepartamentoByCentroPobladoId(BigDecimal centroPobladoId) throws ApplicationException {
        try {

            centroPobladoGpo = geograficoPoliticoMglFacadeLocal
                    .findById(centroPobladoId);

            ciudadGpo = geograficoPoliticoMglFacadeLocal
                    .findById(centroPobladoGpo.getGeoGpoId());

            departamentoGpo = (geograficoPoliticoMglFacadeLocal
                    .findById(ciudadGpo.getGeoGpoId()));

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg + e.getMessage(), e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg + e.getMessage(), e);
        }
    }

    /**
     * Función que permite navegar entre Tabs en la pantalla
     *
     * @author Juan David Hernandez
     * @param tabSeleccionada
     */
    public void cambiarTab(String tabSeleccionada) {
        try {
            ConstantsCM.TABS_HHPP Seleccionado
                    = ConstantsCM.TABS_HHPP.valueOf(tabSeleccionada);
             ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
      
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

    /**
     * Obtiene listado de estados generales iniciales de tipo de ot
     *
     * @author Juan David Hernandez
     */
    public void obtenerEstadoGeneralList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl tipoBasica;
            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.ESTADO_GENERAL_OT_HHPP);
            estadoGeneralList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasica);

        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerEstadoGeneralList. " +e.getMessage()); 
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerEstadoGeneralList. " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerEstadoGeneralList: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene listado de segmentos
     *
     * @author Diana Enriquez
     */
    public void obtenerSegmentoList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl tipoBasicaSegmento;
            tipoBasicaSegmento = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);
            tipoBasicaSegmento = tipoBasicaMglFacadeLocal.findById(tipoBasicaSegmento);
            tipoSegmentoOtList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaSegmento);
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerSegmentoList. " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerSegmentoList. " + e.getMessage());
            throw new ApplicationException("Error en obtenerSegmentoList: " + e.getMessage(), e);
        }
    }

    public boolean check(BigDecimal idTecnologia) {
        for (String string : tecnologiasSeleccionadas) {
            if (idTecnologia.toString().equals(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene listado de estados iniciales internos
     *
     * @author Juan David Hernandez
     * @param estadoInicialId
     */
    public void obtenerEstadoInicialInternoList(BigDecimal estadoInicialId) throws ApplicationException {
        try {
            if (estadoGeneralList != null && !estadoGeneralList.isEmpty()) {
                if (estadoInicialId != null) {

                    estadoInicialBasica = cmtBasicaMglFacadeLocal.findById(estadoInicialId);
                    CmtTipoBasicaMgl tipoBasica;
                    tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            estadoInternoACargar(estadoInicialBasica.getIdentificadorInternoApp()));

                    estadoInicialInternoList = cmtBasicaMglFacadeLocal
                            .findByTipoBasica(tipoBasica);

                    if (otHhppSeleccionado.getTipoOtHhppId()
                            .getManejaTecnologias().compareTo(BigDecimal.ONE) == 0) {
                    } else {

                        for (int i = 0; i < estadoInicialInternoList.size(); i++) {
                            if (estadoInicialInternoList.get(i).getIdentificadorInternoApp()
                                    .equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_CREAR_HHPP)) {
                                estadoInicialInternoList.remove(estadoInicialInternoList.get(i));
                            }
                        }

                    }

                } else {
                    estadoInicialInternoList = new ArrayList();
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerEstadoInicialInternoList. " +e.getMessage());  
             throw new ApplicationException("Error en obtenerEstadoInicialInternoList: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerEstadoInicialInternoList. " +e.getMessage());  
            throw new ApplicationException("Error en obtenerEstadoInicialInternoList: " + e.getMessage(), e);
        }
    }

    /**
     * Función que válida el el tipo de estado interno que se debe cargar según
     * el seleccionado por el usuario.
     *
     * @author Juan David Hernandez
     * @param estadoSeleccionado
     * @return
     */
    public String estadoInternoACargar(String estadoSeleccionado) throws ApplicationException {
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
            LOGGER.error("Error en estadoInternoACargar. " +e.getMessage());  
            throw new ApplicationException("Error en estadoInternoACargar: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en estadoInternoACargar. " +e.getMessage());  
            throw new ApplicationException("Error en estadoInternoACargar: " + e.getMessage(), e);
        }
    }

    /**
     * Función que obtiene tipo de tecnología
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoTecnologiaList() {
        try {
            //Obtiene valores de tipo de tecnología
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia;
            tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            tecnologiasBasicasList
                    = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTecnologia);
            for (CmtBasicaMgl tecnologiaBasica : tecnologiasBasicasList) {
                for (String tecnologiaSeleccionada : tecnologiasSeleccionadas) {
                    if (tecnologiaBasica.getBasicaId().toString().equalsIgnoreCase(tecnologiaSeleccionada)) {
                        tecnologiasBasicaList.add(tecnologiaBasica);
                    }
                }
            }

            if (tecnologiasBasicasList != null && !tecnologiasBasicasList.isEmpty()) {
                for (CmtBasicaMgl tecnologiaBasica : tecnologiasBasicasList) {
                    if (tecnologiaBasica.getNombreBasica().contains("Sin Tecnologia")) {
                        tecnologiasBasicasList.remove(tecnologiaBasica);
                        break;
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoTecnologiaList. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoTecnologiaList. ", e, LOGGER);
        }
    }

    /**
     * Función que obtiene los tipo de ot registrados en la base de datos
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoOtHhppList() {
        try {
            tipoOtHhppList = tipoOtHhppMglFacadeLocal.findAll();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoOtHhppList, al realizar cargue del listado de tipo de ot hhpp. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerTipoOtHhppList, al realizar cargue del listado de tipo de ot hhpp. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener las tecnologias seleccionadas en pantalla
     * por el usuario.
     *
     * @author Juan David Hernandez
     * @param tecnologiaId
     * @return
     */
    public List<CmtBasicaMgl> obtenerListadoTecnologiasSeleccionadas(String[] tecnologiaId) {
        try {
            if (tecnologiaId != null && tecnologiaId.length > 0) {
                List<CmtBasicaMgl> tecnologiaIdList = new ArrayList();
                for (int i = 0; i < tecnologiaId.length; i++) {
                    BigDecimal idTecnologiaSeleccionada = new BigDecimal(tecnologiaId[i]);
                    CmtBasicaMgl tecnologiaBasica = new CmtBasicaMgl();
                    tecnologiaBasica.setBasicaId(idTecnologiaSeleccionada);
                    tecnologiaIdList.add(tecnologiaBasica);
                }
                return tecnologiaIdList;
            }
            return null;
        } catch (RuntimeException e) {
            LOGGER.error("Error en obtenerListadoTecnologiasSeleccionadas: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerListadoTecnologiasSeleccionadas: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Función utilizada para editar una ot en la base de datos
     *
     * @author Juan David Hernandez
     */
    public void editarOtHhpp() {
        try {
            if (!isRolEditar()) {
                FacesUtil.mostrarMensajeWarn("No tienes permisos para editar General OT HHPP");
                return;
            }
            if (validarDatosOtHhpp()) {

                if (tieneAgendasPendientes(otHhppSeleccionado)) {

                    otHhppSeleccionado.setNombreContacto(nombreContacto);
                    otHhppSeleccionado.setTelefonoContacto(telefonoContacto);
                    otHhppSeleccionado.setCorreoContacto(correoContacto);
                    otHhppSeleccionado.setFechaCreacionOt(fechaCreacionOt);
                    CmtBasicaMgl estadoGeneralSeleccionado = cmtBasicaMglFacadeLocal.findById(estadoInicial);
                    otHhppSeleccionado.setEstadoGeneral(estadoGeneralSeleccionado);
                    if (estadoInicialInterno != null) {
                        CmtBasicaMgl razonInicialInternaSeleccionada = cmtBasicaMglFacadeLocal.findById(estadoInicialInterno);
                        otHhppSeleccionado.setEstadoInternoInicial(razonInicialInternaSeleccionada);
                        if (razonInicialInternaSeleccionada.getIdentificadorInternoApp()
                                .equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_CREAR_HHPP)) {
                            crearHhppApartirDeLaRazon();
                        }
                    }

                    otHhppSeleccionado = otHhppMglFacadeLocal.editarOtHhpp(otHhppSeleccionado);

                    String msn = "Cambios realizados a la Ot Hhpp satisfactoriamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msn, ""));
                    cargarOtHhppSeleccionada();
                    validarEstadoRenderizarBotones(otHhppSeleccionado);

                } else {
                    String msg = "La orden de trabajo No: " + otHhppSeleccionado.getOtHhppId() + "  tiene agendas pendientes por cerrar";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en editarOtHhpp, al intentar guardar cambios en tipo de ot. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en editarOtHhpp, al intentar guardar cambios en tipo de ot. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para crear el Hhpp con las tecnologias pertenecientes a
     * la Ot, bajo el estado de la razon
     *
     * @author Orlando Velasquez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void crearHhppApartirDeLaRazon() throws ApplicationException {
        try {
           
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
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
                                = cmtDireccionDetalleMglFacadeLocal.findDireccionDetallaByDirIdSdirId(direccion.getDirId(), subDireccion);
                        DrDireccion drDireccion1
                                = cmtDireccionDetalleMglFacadeLocal.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetalladas.get(0));

                        drDireccion1.setEstrato(tecnologia.getOtHhppId().getDireccionId().getDirEstrato().toString());
                        //obtiene el estrato de la direccion
                        String estrato = solicitudManager.validarEstrato(drDireccion1);

                        GeograficoPoliticoMgl centroPoblado = direccion.getUbicacion().getGpoIdObj();

                        //obtener instancia inyectandole parametros en el constructor
                        direccionRRManager = obtenerInstanciaDireccionRRManager(centroPoblado.getGeoGpoId(), drDireccion1);

                        direccionRRManager.registrarHHPP_Inspira_Independiente(
                                nodo, nodo,
                                usuarioVT, tipoSolicitud,
                                Constant.FUNCIONALIDAD_DIRECCIONES,
                                estrato, false,
                                null,Constant.RESULTADO_HHPP_CREADO,
                                usuarioVT,
                                centroPoblado.getGeoGpoId(),
                                sincronizarConRr,
                                "", null);
                    } else {

                        //Nodo de la tecnologia
                        String nodo = tecnologia.getNodo().getNodCodigo();

                        //Obtiene el tipo de Solicitud (carpeta RR)
                        String tipoSolicitud = solicitudManager.obtenerCarpetaTipoDireccion(solicitudDth.getTecnologiaId());

                        CmtDireccionDetalladaMgl direccionDetallada
                                = cmtDireccionDetalleMglFacadeLocal
                                        .buscarDireccionIdDireccion(solicitudDth.getDireccionDetallada()
                                                .getDireccionDetalladaId());

                        DrDireccion drDireccion2
                                = cmtDireccionDetalleMglFacadeLocal.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);

                        drDireccion2.setEstrato(solicitudDth.getEstratoAntiguo());
                        //obtiene el estrato de la direccion
                        String estrato = solicitudManager.validarEstrato(drDireccion2);

                        String dirAntiguaFormatoIgac = obtenerDireccionAntiguaFormatoIgac(solicitudDth);

                        //obtener instancia inyectandole parametros en el constructor
                        direccionRRManager = obtenerInstanciaDireccionRRManager(solicitudDth.getCentroPobladoId(), drDireccion2);

                        direccionRRManager.registrarHHPP_Inspira(
                                tecnologia.getNodo(),
                                usuarioVT, tipoSolicitud,
                                Constant.FUNCIONALIDAD_DIRECCIONES,
                                estrato, false,
                                solicitudDth.getIdSolicitud().toString(),
                                solicitudDth.getTipoSol(),
                                solicitudDth.getCiudad(),
                                solicitudDth.getRespuesta(),
                                solicitudDth.getSolicitante(),
                                solicitudDth.getRptGestion(),
                                usuarioVT,
                                solicitudDth.getContacto(),
                                solicitudDth.getTelContacto(),
                                dirAntiguaFormatoIgac, solicitudDth.getCentroPobladoId(),
                                sincronizarConRr,"",null, habilitarRR,null,false);
                    }

                    String msn = tecnologia.getTecnoglogiaBasicaId().getNombreBasica()
                            + ": El Hhpp ha sido creado exitosamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msn, ""));
                
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en crearHhppApartirDeLaRazon. " +e.getMessage()); 
            throw new ApplicationException("Error en crearHhppApartirDeLaRazon: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en crearHhppApartirDeLaRazon. " +e.getMessage()); 
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
    public List<OtHhppTecnologiaMgl> obtenerTecnologiasViablesParaCreacionDeHhpp() throws ApplicationException
             {

        try {
            //Lista de tecnologias asociadas a la Ot Direcciones
            List<OtHhppTecnologiaMgl> tecnologiasViablesOtList
                    = otHhppTecnologiaMglFacadeLocal.findOtHhppTecnologiaByOtHhppId(
                            otHhppSeleccionado.getOtHhppId());
            SubDireccionMgl subdireccion = null;
            
            //valida si existe una subdireccion para esta Ot
            if (otHhppSeleccionado.getSubDireccionId() != null) {
                subdireccion = otHhppSeleccionado.getSubDireccionId();
            }
            
            //Busca Hhpp asociados a la direccion y subdireccion de la Ot
            List<HhppMgl> hhpps = hhppMglFacadeLocal.findByDirAndSubDir(
                    otHhppSeleccionado.getDireccionId(), subdireccion);
            
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
            LOGGER.error("Error en obtenerTecnologiasViablesParaCreacionDeHhpp " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerTecnologiasViablesParaCreacionDeHhpp: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerTecnologiasViablesParaCreacionDeHhpp " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerTecnologiasViablesParaCreacionDeHhpp: " + e.getMessage(), e);
        }
    }

    /**
     * Función usada para obtener el valor si debe o no sincronizar en Rr
     *
     * @author Orlando Velasquez Diaz
     * @param sincronizar
     *
     * @return
     */
    public boolean sincronizarConRr(String sincronizar) {
        return sincronizar.equalsIgnoreCase("1");
    }

    /**
     * Función que valida si la solicitud tiene una dirección antigua para
     * obtener las unidades de la antigua dirección
     *
     * @author Juan David Hernandez
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

    /**
     * Función que obtiene la instancia de DireccionRRManager
     *
     * @author Orlando Velasquez Diaz
     * @param centroPobladoId
     * @param drDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DireccionRRManager obtenerInstanciaDireccionRRManager(BigDecimal centroPobladoId, DrDireccion drDireccion)
            throws ApplicationException, ApplicationException {

        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        DetalleDireccionEntity detalleDireccionEntity1;
        detalleDireccionEntity1 = drDireccion.convertToDetalleDireccionEntity();
        //obtenemos el centro poblado de la solicitud para conocer si la ciudad es multiorigen
        GeograficoPoliticoMgl centroPobladoSolicitud;
        centroPobladoSolicitud = geograficoPoliticoManager.findById(centroPobladoId);

        //Conocer si es multi-origen         
        detalleDireccionEntity1.setMultiOrigen(centroPobladoSolicitud.getGpoMultiorigen());
        //obtener la direccion en formato RR

        DireccionRRManager direccionRRManager
                = new DireccionRRManager(detalleDireccionEntity1);

        return direccionRRManager;
    }

    /**
     * Función usada para validar la cobertura del nodo
     *
     * @author Orlando Velasquez
     * @param otHhppMgl
     * @param tecnologiaList
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean validarCoberturaDeNodo(OtHhppMgl otHhppMgl,
            List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {

        if (!otHhppMglFacadeLocal.validarCoberturaDeNodo(otHhppMgl, tecnologiaList)) {
            String msnError = "No Existe Nodo De Cobertura Para La Tecnologia "
                    + "Seleccionada";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
            return false;
        }

        return true;
    }

    /**
     * Función utilizada para validar los datos correspondiente a la creación de
     * una ot
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarDatosOtHhpp() throws ApplicationException {
        try {
            if (tipoOtHhppId == null) {
                String msnError = "Seleccione un TIPO DE OT por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                return false;
            } else {
                if (nombreContacto == null || nombreContacto.trim().isEmpty()) {
                    String msnError = "Seleccione un NOMBRE DE CONTACTO por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                    return false;
                } else {
                    if (telefonoContacto == null || telefonoContacto.trim().isEmpty()) {
                        String msnError = "Seleccione un TELÉFONO DE CONTACTO por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                        return false;
                    }
                    if (correoContacto == null || correoContacto.trim().isEmpty()) {
                        String msnError = "Debe ingresar un CORREO ELECTRÓNICO "
                                + "del contacto.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        return false;
                    } else {
                        if (!correoContacto.contains("@")
                                || !correoContacto.contains(".")) {
                            String msnError = "Debe ingresar un CORREO "
                                    + "ELECTRÓNICO Válido.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msnError, ""));
                            return false;
                        } else {
                            if (estadoInicial == null) {
                                String msnError = "Seleccione una razón inicial por favor.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en validarDatosOtHhpp: " + e.getMessage(), e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en validarDatosOtHhpp: " + e.getMessage(), e);
        }
    }

    /**
     * Función utilizada para obtener las tecnologias cargadas en la ot
     * seleccionada
     *
     * @author Juan David Hernandez
     * @param tecnologiasCargadas
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String[] obtenerListadoTecnologiasCargadas(List<OtHhppTecnologiaMgl> tecnologiasCargadas) throws ApplicationException {
        try {
            String[] tecnologias;
            //recorremos el listado de tecnologias cargado en la ot seleccionada
            if (tecnologiasCargadas != null && !tecnologiasCargadas.isEmpty()) {
                tecnologias = new String[tecnologiasCargadas.size()];

                int i = 0;
                /*realizamos recorrido del listado de tecnologias para 
                 * asignarlo en el arreglo que se presentara en pantalla. */
                for (OtHhppTecnologiaMgl tecnologia : tecnologiasCargadas) {
                    tecnologias[i] = tecnologia.getTecnoglogiaBasicaId().getBasicaId().toString();
                    i++;
                }
                return tecnologias;
            }
            return null;
        } catch (RuntimeException e) {
            LOGGER.error("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias. " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias: " +e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias. " +e.getMessage());  
            throw new ApplicationException("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias: " +e.getMessage(), e);
        }
    }
    
    /**
     * Función utilizada para obtener las tecnologias cargadas en la ot
     * seleccionada
     *
     * @author Juan David Hernandez
     * @param tecnologiasCargadas
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String[] obtenerNodosOtHhpp(List<OtHhppTecnologiaMgl> tecnologiasCargadas) throws ApplicationException {
        try {
            String[] tecnologias;
            //recorremos el listado de tecnologias cargado en la ot seleccionada
            if (tecnologiasCargadas != null && !tecnologiasCargadas.isEmpty()) {
                tecnologias = new String[tecnologiasCargadas.size()];

                int i = 0;
                /*realizamos recorrido del listado de tecnologias para 
                 * asignarlo en el arreglo que se presentara en pantalla. */
                for (OtHhppTecnologiaMgl tecnologia : tecnologiasCargadas) {
                    tecnologias[i] = tecnologia.getNodo().getNodNombre();
                    i++;
                }
                return tecnologias;
            }
            return null;
        } catch (RuntimeException e) {
            LOGGER.error("Error en obtenerNodos de la ot seleccionada " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerListadoTecnologiasCargadas, de la ot seleccionada: " +e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerNodos, de la ot seleccionada. " +e.getMessage());  
            throw new ApplicationException("Error en obtenerNodos, de la ot seleccionada: " +e.getMessage(), e);
        }
    }
    
     /**
     * Función utilizada para obtener los nodos cargados en la ot
     * seleccionada
     *
     * @author Victor Bocanegra
     * @param tecnologiasCargadas
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String[] obtenerListadoNodosCargados(List<OtHhppTecnologiaMgl> tecnologiasCargadas) throws ApplicationException {
        try {
            String[] nodos;
            //recorremos el listado de tecnologias cargado en la ot seleccionada
            if (tecnologiasCargadas != null && !tecnologiasCargadas.isEmpty()) {
                nodos = new String[tecnologiasCargadas.size()];

                int i = 0;
                /*realizamos recorrido del listado de tecnologias para 
                 * asignarlo en el arreglo que se presentara en pantalla. */
                for (OtHhppTecnologiaMgl tecnologia : tecnologiasCargadas) {
                    nodos[i] = tecnologia.getNodo().getNodCodigo();
                    i++;
                }
                return nodos;
                        }
            return null;
        } catch (RuntimeException e) {
            LOGGER.error("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias. " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias: " +e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias. " +e.getMessage());  
            throw new ApplicationException("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias: " +e.getMessage(), e);
        }
    }
    
      /**
     * Función utilizada para obtener los nodos cargados en la ot
     * seleccionada
     *
     * @author Juan David Hernandez
     * @param tecnologiasCargadas
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String obtenerListadoNodosSeleccionados(List<OtHhppTecnologiaMgl> tecnologiasCargadas) throws ApplicationException {
        try {
            String[] nodos;
            String nodoSeleccionados = "";
            //recorremos el listado de tecnologias cargado en la ot seleccionada
            if (tecnologiasCargadas != null && !tecnologiasCargadas.isEmpty()) {
                nodos = new String[tecnologiasCargadas.size()];

                int i = 0;
                /*realizamos recorrido del listado de tecnologias para 
                 * asignarlo en el arreglo que se presentara en pantalla. */
                for (OtHhppTecnologiaMgl tecnologia : tecnologiasCargadas) {
                    nodos[i] = tecnologia.getTecnoglogiaBasicaId().getAbreviatura() +"-"+ tecnologia.getNodo().getNodCodigo();
                    i++;
                }
                
               if (nodos != null && nodos.length > 0) {
                    int cont = 1;
                    for (int j = 0; j < nodos.length; j++) {
                        nodoSeleccionados += nodos[j];
                        if (cont < nodos.length) {
                            nodoSeleccionados += ", ";
                        }
                        cont++;
                    }
                }
                return nodoSeleccionados;
            }
            return nodoSeleccionados;
        } catch (RuntimeException e) {
            LOGGER.error("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias. " +e.getMessage()); 
            throw new ApplicationException("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias: " +e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias. " +e.getMessage());  
            throw new ApplicationException("Error en obtenerListadoTecnologiasCargadas, al realizar cargue del listado de tecnologias: " +e.getMessage(), e);
        }
    }

    /**
     * Obtiene listado de estados iniciales internos
     *
     * @author Juan David Hernandez
     */
    public void obtenerEstadoInicialInternoList() {
        try {
            if (estadoGeneralList != null && !estadoGeneralList.isEmpty()) {
                if (estadoInicial != null) {

                    estadoInicialBasica = cmtBasicaMglFacadeLocal.findById(estadoInicial);
                    CmtTipoBasicaMgl tipoBasica;
                    tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            estadoInternoACargar(estadoInicialBasica.getIdentificadorInternoApp()));

                    estadoInicialInternoList = cmtBasicaMglFacadeLocal
                            .findByTipoBasica(tipoBasica);

                    if (otHhppSeleccionado.getTipoOtHhppId()
                            .getManejaTecnologias().compareTo(BigDecimal.ONE) == 0) {
                    } else {
                        for (int i = 0; i < estadoInicialInternoList.size(); i++) {
                            if (estadoInicialInternoList.get(i).getIdentificadorInternoApp()
                                    .equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_CREAR_HHPP)) {
                                estadoInicialInternoList.remove(estadoInicialInternoList.get(i));
                            }
                        }
                    }
                } else {
                    estadoInicialInternoList = new ArrayList();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerEstadoInicialInternoList. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerEstadoInicialInternoList. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para redireccionar a la pantalla listado de Ot
     *
     * @author Juan David Hernandez
     */
    public void irListadoOtHhpp() {
        try {
            otHhppSeleccionado = otHhppMglFacadeLocal.bloquearDesbloquearOrden(otHhppSeleccionado, false);            
            FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/otHhppList.jsf");
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irListadoOtHhpp, al redireccionar a creación de Tipo Ot. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irListadoOtHhpp, al redireccionar a creación de Tipo Ot. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para redireccionar a la pantalla listado de Ot del
     * modelo de hhpp
     *
     * @author Juan David Hernandez
     */
    public void retornarModeloHhpp() {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");
            DetalleHhppBean detalleHhppBean
                    = (DetalleHhppBean) JSFUtil.getBean("detalleHhppBean");

            detalleHhppBean.cambiarTab("ORDENES");

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en retornarModeloHhpp, al redireccionar al modulo de hhpp"
                    + " listado de ordenes. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en retornarModeloHhpp, al redireccionar al modulo de hhpp"
                    + " listado de ordenes. ", e, LOGGER);
        }
    }
    
     public boolean activaDesactivaUCM(){

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
     
    public boolean tieneAgendasPendientes(OtHhppMgl ot) throws ApplicationException {

        boolean ultimaAgenda;
        try {
            TipoOtHhppMgl tipoOtHhppMgl = ot.getTipoOtHhppId();
            if (tipoOtHhppMgl != null && tipoOtHhppMgl.getSubTipoOrdenOFSC() != null) {
                String subTipoWorForce = tipoOtHhppMgl.getSubTipoOrdenOFSC().getCodigoBasica();

                if (tipoOtHhppMgl.getIsMultiagenda().equalsIgnoreCase("S")) {
                    //Consultamos si la orden tiene agendas pendientes
                    List<MglAgendaDireccion> agendas = agendamientoHhppFacade.
                            buscarAgendasPendientesByOtAndSubtipopOfsc(ot, subTipoWorForce);
                    if (agendas.size() > 0) {
                        ultimaAgenda = false;

                        for (MglAgendaDireccion agenda : agendas) {
                            if (agenda.getUltimaAgenda().equalsIgnoreCase("Y")) {
                                ultimaAgenda = true;
                            }
                        }
                    } else {
                        ultimaAgenda = true;

                        if (ot.getEstadoGeneral().getBasicaId().compareTo(estadoInicial) != 0) {
                            //Consultamos si la orden tiene agendas canceladas
                            List<MglAgendaDireccion> agendasCance = agendamientoHhppFacade.
                                    agendasHhppCanceladasByOrdenAndSubTipoWorkfoce(ot, subTipoWorForce);
                            if (agendasCance.size() > 0) {
                                //Cierro ot en rr
                                ParametrosMgl parametroHabilitarRR = parametrosMglFacadeLocal.findParametroMgl(Constant.HABILITAR_RR);

                                if (parametroHabilitarRR != null
                                        && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {

                                    MglAgendaDireccion agendaCan = agendasCance.get(0);
                                    //capturo el numero de la ot en rr
                                    String numeroOtRr = agendaCan.getIdOtenrr();

                                    BigDecimal codCuentaPar;
                                    CmtBasicaMgl basicaMglAgrupadora = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_TIPO_EDIFICIO_AGRUPADOR_DIRECCIONES_BARRIO);
                                    CmtCuentaMatrizMgl cuentaAgrupadora;
                                    if (basicaMglAgrupadora != null) {
                                        List<CmtCuentaMatrizMgl> cuentasAgrupadora
                                                = cmtCuentaMatrizMglFacadeLocal.
                                                        findCuentasMatricesAgrupadorasByCentro(ot.getDireccionId().getUbicacion().getGpoIdObj().getGpoId(), basicaMglAgrupadora);
                                        //Hay una sola CM agrupadora
                                        cuentaAgrupadora = cuentasAgrupadora.get(0);
                                        codCuentaPar = cuentaAgrupadora.getNumeroCuenta();

                                        ParametrosMgl parametroHabilitarOtInRr
                                                = parametrosMglFacadeLocal.findParametroMgl(Constant.ACTIVAR_CREACION_OT_RR);
                                        if (parametroHabilitarOtInRr != null
                                                && parametroHabilitarOtInRr.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)
                                                && tipoOtHhppMgl.getEstadoOtRRInicial() != null
                                                && tipoOtHhppMgl.getEstadoOtRRFinal() != null) {
                                            String notaCierre = "Cierre de orden en RR por agendas agendas canceladas - " + usuarioVT + "";
                                            boolean cerrarOtRr = ordenTrabajoMglFacadeLocal.actualizarEstadoResultadoOTRRHhpp(codCuentaPar.toString(), numeroOtRr, usuarioVT, tipoOtHhppMgl,
                                                    false, ot, notaCierre);
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
                    }
                } else {
                    //Consultamos si la orden tiene agendas pendientes diferentes de nodone-canceladas y cerradas
                    List<MglAgendaDireccion> agendas = agendamientoHhppFacade.
                            buscarAgendasPendientesByOtAndSubtipopOfscUnicaAgenda(ot, subTipoWorForce);
                    if (agendas.size() > 0) {
                        ultimaAgenda = false;
                    } else {
                        ultimaAgenda = true;
                    }
                }
            } else {
                ultimaAgenda = true;
            }
            return ultimaAgenda;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + e.getMessage(), e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + e.getMessage(), e);
        }
    }

    
        /**
     * Función que valida si la OT esta cerrada o anulada para renderizar los botones de guardar cambios
     *
     * @author Juan David Hernandez
     * @param otHhppSeleccionado
     */
    public void validarEstadoRenderizarBotones(OtHhppMgl otHhppSeleccionado){
        try {
            if(otHhppSeleccionado != null && otHhppSeleccionado.getEstadoGeneral() != null 
                    && otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp() != null
                    && !otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().isEmpty()){
                
                if(otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO)
                        || otHhppSeleccionado.getEstadoGeneral().getIdentificadorInternoApp().equalsIgnoreCase(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO_COMPLETO)){
                    otCerradaAnulada = true;
                }else{
                    otCerradaAnulada = false;
                }
            }else{
                 otCerradaAnulada = false;
            }
        } catch (Exception e) {        
        FacesUtil.mostrarMensajeError("Error al validar estado de la ot para renderizar botones ", e, LOGGER);
          
        }
    }

    public OtHhppMgl getOtHhppSeleccionado() {
        return otHhppSeleccionado;
    }

    public void setOtHhppSeleccionado(OtHhppMgl otHhppSeleccionado) {
        this.otHhppSeleccionado = otHhppSeleccionado;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public Date getFechaCreacionOt() {
        return fechaCreacionOt;
    }

    public void setFechaCreacionOt(Date fechaCreacionOt) {
        this.fechaCreacionOt = fechaCreacionOt;
    }

    public String[] getTecnologiasSeleccionadas() {
        return tecnologiasSeleccionadas;
    }

    public void setTecnologiasSeleccionadas(String[] tecnologiasSeleccionadas) {
        this.tecnologiasSeleccionadas = tecnologiasSeleccionadas;
    }

    public String[] getNodosSeleccionados() {
        return nodosSeleccionados;
    }

    public void setNodosSeleccionados(String[] nodosSeleccionados) {
        this.nodosSeleccionados = nodosSeleccionados;
    }
    
    public BigDecimal getTipoOtHhppId() {
        return tipoOtHhppId;
    }

    public void setTipoOtHhppId(BigDecimal tipoOtHhppId) {
        this.tipoOtHhppId = tipoOtHhppId;
    }

    public BigDecimal getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(BigDecimal estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public CmtBasicaMgl getEstadoInicialBasica() {
        return estadoInicialBasica;
    }

    public void setEstadoInicialBasica(CmtBasicaMgl estadoInicialBasica) {
        this.estadoInicialBasica = estadoInicialBasica;
    }

    public List<CmtBasicaMgl> getTipoNotaList() {
        return tipoNotaList;
    }

    public void setTipoNotaList(List<CmtBasicaMgl> tipoNotaList) {
        this.tipoNotaList = tipoNotaList;
    }

    public List<CmtBasicaMgl> getTecnologiasBasicasList() {
        return tecnologiasBasicasList;
    }

    public void setTecnologiasBasicasList(List<CmtBasicaMgl> tecnologiasBasicasList) {
        this.tecnologiasBasicasList = tecnologiasBasicasList;
    }

    public List<TipoOtHhppMgl> getTipoOtHhppList() {
        return tipoOtHhppList;
    }

    public void setTipoOtHhppList(List<TipoOtHhppMgl> tipoOtHhppList) {
        this.tipoOtHhppList = tipoOtHhppList;
    }

    public List<CmtBasicaMgl> getEstadoGeneralList() {
        return estadoGeneralList;
    }

    public void setEstadoGeneralList(List<CmtBasicaMgl> estadoGeneralList) {
        this.estadoGeneralList = estadoGeneralList;
    }

    public List<CmtBasicaMgl> getEstadoInicialInternoList() {
        return estadoInicialInternoList;
    }

    public void setEstadoInicialInternoList(List<CmtBasicaMgl> estadoInicialInternoList) {
        this.estadoInicialInternoList = estadoInicialInternoList;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public String getDireccionDetalladaSeleccionada() {
        return direccionDetalladaSeleccionada;
    }

    public void setDireccionDetalladaSeleccionada(String direccionDetalladaSeleccionada) {
        this.direccionDetalladaSeleccionada = direccionDetalladaSeleccionada;
    }

    public boolean isDetalleOtHhpp() {
        return detalleOtHhpp;
    }

    public void setDetalleOtHhpp(boolean detalleOtHhpp) {
        this.detalleOtHhpp = detalleOtHhpp;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public DetalleDireccionEntity getDetalleDireccionEntity() {
        return detalleDireccionEntity;
    }

    public void setDetalleDireccionEntity(DetalleDireccionEntity detalleDireccionEntity) {
        this.detalleDireccionEntity = detalleDireccionEntity;
    }

    public DireccionRREntity getDireccionRREntity() {
        return direccionRREntity;
    }

    public void setDireccionRREntity(DireccionRREntity direccionRREntity) {
        this.direccionRREntity = direccionRREntity;
    }

    public String getComplementoDireccion() {
        return complementoDireccion;
    }

    public void setComplementoDireccion(String complementoDireccion) {
        this.complementoDireccion = complementoDireccion;
    }

    public GeograficoPoliticoMgl getCiudadGpo() {
        return ciudadGpo;
    }

    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }

    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return departamentoGpo;
    }

    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }

    public GeograficoPoliticoMgl getCentroPobladoGpo() {
        return centroPobladoGpo;
    }

    public void setCentroPobladoGpo(GeograficoPoliticoMgl centroPobladoGpo) {
        this.centroPobladoGpo = centroPobladoGpo;
    }

    public BigDecimal getNumeroOt() {
        return numeroOt;
    }

    public void setNumeroOt(BigDecimal numeroOt) {
        this.numeroOt = numeroOt;
    }

    public List<CmtBasicaMgl> getTecnologiasBasicaList() {
        return tecnologiasBasicaList;
    }

    public void setTecnologiasBasicaList(List<CmtBasicaMgl> tecnologiasBasicaList) {
        this.tecnologiasBasicaList = tecnologiasBasicaList;
    }

    public BigDecimal getEstadoInicialInterno() {
        return estadoInicialInterno;
    }

    public void setEstadoInicialInterno(BigDecimal estadoInicialInterno) {
        this.estadoInicialInterno = estadoInicialInterno;
    }

    public boolean isShowTecnologias() {
        return showTecnologias;
    }

    public void setShowTecnologias(boolean showTecnologias) {
        this.showTecnologias = showTecnologias;
    }

    public TipoOtHhppMgl getTipoOtHhppSeleccionada() {
        return tipoOtHhppSeleccionada;
    }

    public void setTipoOtHhppSeleccionada(TipoOtHhppMgl tipoOtHhppSeleccionada) {
        this.tipoOtHhppSeleccionada = tipoOtHhppSeleccionada;
    }

    public boolean isOtCerradaAnulada() {
        return otCerradaAnulada;
    }

    public void setOtCerradaAnulada(boolean otCerradaAnulada) {
        this.otCerradaAnulada = otCerradaAnulada;
    }

    public List<CmtBasicaMgl> getTipoSegmentoOtList() {
        return tipoSegmentoOtList;
    }

    public void setTipoSegmentoOtList(List<CmtBasicaMgl> tipoSegmentoOtList) {
        this.tipoSegmentoOtList = tipoSegmentoOtList;
    }
 
}
