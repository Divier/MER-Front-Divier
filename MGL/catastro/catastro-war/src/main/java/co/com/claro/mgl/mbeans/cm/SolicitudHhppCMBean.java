/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.cmas400.ejb.request.RequestDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.ResponseDataValidaRazonesCreaHhppVt;
import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.cm.CmtSolictudHhppMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.HhppVirtualMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.*;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gilaj
 */
@ManagedBean(name = "SolicitudHhppCMBean")
@ViewScoped
public class SolicitudHhppCMBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(SolicitudHhppCMBean.class);
    private List<ParametrosCalles> dirNivel5List;
    private List<ParametrosCalles> dirNivel6List;
    private ParametrosCalles parametroIdNvl5;
    private ParametrosCalles parametroIdNvl6;
    private List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges;
    private List<HhppMgl> listaHhppToChanges;
    private EncabezadoSolicitudModificacionCMBean encabezadoSolicitudModificacionCMBean;
    private CmtSubEdificioMgl selectedSubEdificio;
    private CmtSolicitudHhppMgl cmtSolicitudHhppMgl;
    private CmtSolicitudCmMgl solicitudModCM;
    private HhppMgl selectHhppMgl;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private List<HhppMgl> hhppCmList;
    private CmtSubEdificioMgl subEdificioGeneralCm;
    private String pageActual;
    private String numPagina="1";
    private List<Integer> paginaList;
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private CmtCuentaMatrizMgl cuentaMatrizMgl;
    private List<CmtBasicaMgl> listBasicaTecnologia; //valbuenayf rq_crear_modificar_hhpp
    private List<TipoHhppMgl> listaTipoHhpp;
    private String tipoHhpp;
    private String correoUsuario;
    SecurityLogin securityLogin;
    /*Brief 57762 */
    @Getter
    @Setter
    private boolean habilitarCrearHhppVirtual;
    //TODO: revisar acción y msg de respuesta para caso exitoso y fallido de gestion de solicitud
    private String accionFinalizarSolicitudHhppVirtual = null;
    private String msgRespuestaSolicitudHhppVirtual = null;
    /* Cierre Brief 57762 */

    /* ----------------------------------------------------------------*/
    @EJB
    private CmtSolicitudHhppMglFacadeLocal cmtSolicitudHhppMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal tipoSolicitudFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtNotasSolicitudMglFacadeLocal notasSolicitudMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    /* Brief 57762 */
    @EJB
    private HhppVirtualMglFacadeLocal hhppVirtualMglFacadeLocal;
    @Inject
    private ExceptionServBean exceptionServBean;

    /**
     * Creates a new instance of SolicitudHhppCMBean
     */
    public SolicitudHhppCMBean() {
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

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al SolicitudHhppCMBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al SolicitudHhppCMBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            dirNivel5List = parametrosCallesFacadeLocal.findByTipo("DIR_NUM_APTO");
            dirNivel6List = parametrosCallesFacadeLocal.findByTipo("DIR_NUM_APTOC");
            cmtSolicitudHhppMglFacadeLocal.setUser(usuarioVT, perfilVt);
            solicitudFacadeLocal.setUser(usuarioVT, perfilVt);
            encabezadoSolicitudModificacionCMBean = JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
            Optional<EncabezadoSolicitudModificacionCMBean> modificacionCmBean = Optional.ofNullable(encabezadoSolicitudModificacionCMBean);
            selectedSubEdificio = modificacionCmBean.map(EncabezadoSolicitudModificacionCMBean::getSelectedCmtSubEdificioMgl).orElse(null);
            solicitudModCM = modificacionCmBean.map(EncabezadoSolicitudModificacionCMBean::getSolicitudModCM).orElse(null);
            cuentaMatrizMgl = modificacionCmBean.map(EncabezadoSolicitudModificacionCMBean::getCuentaMatriz).orElse(null);
            correoUsuario = modificacionCmBean.map(EncabezadoSolicitudModificacionCMBean::getUsuarioSolicitudCreador)
                    .map(UsuariosServicesDTO::getEmail).orElse(null);

            if (solicitudModCM != null) {
                if (solicitudModCM.getSolicitudCmId() != null) {
                    solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                    cmtSolicitudHhppMglListToChanges = solicitudModCM.getHhppToChangeList();
                } else {
                    cmtSolicitudHhppMglListToChanges = solicitudModCM.getModHhppCreateBySubEdificio(selectedSubEdificio);
                }
            } else {
                solicitudModCM = new CmtSolicitudCmMgl();
                cmtSolicitudHhppMglListToChanges = solicitudModCM.getModHhppCreateBySubEdificio(selectedSubEdificio);
            }
            if(selectedSubEdificio != null){
            listaHhppToChanges = selectedSubEdificio.getListHhpp();    
            }
            //Valido si existen cambios.
            if (listaHhppToChanges != null) {
                listaHhppToChanges = cmtSolicitudHhppMglFacadeLocal.ValidacionCambiosHhpp(listaHhppToChanges, cmtSolicitudHhppMglListToChanges);
            }
            selectHhppMgl = encabezadoSolicitudModificacionCMBean.getSelectHhppMgl();
            cmtSolicitudHhppMgl = new CmtSolicitudHhppMgl();
            cmtSolicitudHhppMgl.setOpcionNivel5("");
            cmtSolicitudHhppMgl.setOpcionNivel6("");
            cmtSolicitudHhppMgl.setValorNivel5("");
            cmtSolicitudHhppMgl.setValorNivel6("");
            listBasicaTecnologia = new ArrayList<>();//valbuenayf rq_crear_modificar_hhpp
            cargarLista(cuentaMatrizMgl);//valbuenayf rq_crear_modificar_hhpp
            listaTipoHhpp = tipoHhppMglFacadeLocal.findAll();
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
           
            for(CmtSolicitudHhppMgl cmtSolicitudHhpp :cmtSolicitudHhppMglListToChanges){
                String tipoHhpp = "";
                 tipoHhpp =direccionRRManager.obtenerTipoEdificio(cmtSolicitudHhpp.getOpcionNivel5(), usuarioVT, String.valueOf(cmtSolicitudHhpp.getTipoSolicitud()));
                 cmtSolicitudHhpp.setTipoHhpp(tipoHhpp);
            }
            habilitarCrearHhppVirtual = true;
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:init(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:init(). " + e.getMessage(), e, LOGGER);
        }
    }
    
    
    /**
     * valbuenayf rq_crear_modificar_hhpp metodo para cargar la lista de
     * tecnologia
     *
     * @param cuentaMatrizMgl
     */
    private void cargarLista(CmtCuentaMatrizMgl cuentaMatrizMgl) {
        try {
            if (cuentaMatrizMgl != null && cuentaMatrizMgl.getCuentaMatrizId() != null
                    && cuentaMatrizMgl.getListCmtSubEdificioMgl() != null && !cuentaMatrizMgl.getListCmtSubEdificioMgl().isEmpty()) {
                for (CmtSubEdificioMgl cmtSubEdificioMgl : cuentaMatrizMgl.getListCmtSubEdificioMgl()) {
                    // Solo para edificios activos.
                    if (cmtSubEdificioMgl != null && cmtSubEdificioMgl.getEstadoRegistro() == 1) {
                        if (cmtSubEdificioMgl.getSubEdificioId() != null
                                && cmtSubEdificioMgl.getListTecnologiasSub() != null
                                && !cmtSubEdificioMgl.getListTecnologiasSub().isEmpty()) {
                            // adicionar las tecnologias de cada subedificio.
                            for (CmtTecnologiaSubMgl l : cmtSubEdificioMgl.getListTecnologiasSub()) {
                                if (!listBasicaTecnologia.contains(l.getBasicaIdTecnologias())) {
                                    listBasicaTecnologia.add(l.getBasicaIdTecnologias());
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (Exception ex) {
            LOGGER.error("Se genero error en cargarListas de SolicitudHhppCMBean:cargarLista class ...", ex);
        }
    }

    public void cargarHhppCm() {
        try {
            if (solicitudModCM != null
                    && solicitudModCM.getCuentaMatrizObj() != null) {
                subEdificioGeneralCm = solicitudModCM.getCuentaMatrizObj().getSubEdificioGeneral();
                if (subEdificioGeneralCm != null) {
                    pageFirst();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:cargarHhppCm(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:cargarHhppCm(). " + e.getMessage(), e, LOGGER);
        }
    }

    public String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            FiltroConsultaHhppDto filtro = new FiltroConsultaHhppDto();
            PaginacionDto<HhppMgl> paginacionDto =
                    hhppFacadeLocal.findBySubOrCM(page, filasPag, subEdificioGeneralCm,
                    filtro, Constant.FIND_HHPP_BY.CUENTA_MATRIZ);
            hhppCmList = paginacionDto.getListResultado();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:listInfoByPage. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:listInfoByPage. " + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la primera página. EX000 " + ex.getMessage(), ex);
        }
    }

    public void pagePrevious() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la página anterior. EX000 " + ex.getMessage(), ex);
        }
    }

    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
             FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:irPagina(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:irPagina(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error eal ir a la siguiente página. EX000 " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página. EX000 " + ex.getMessage(), ex);
        }

    }

    public int getTotalPaginas() {
        try {
            int totalPaginas = 0;
            FiltroConsultaHhppDto filtro = new FiltroConsultaHhppDto();
            PaginacionDto<HhppMgl> paginacionDto =
                    hhppFacadeLocal.findBySubOrCM(0, filasPag, subEdificioGeneralCm,
                    filtro, Constant.FIND_HHPP_BY.CUENTA_MATRIZ_SOLO_CONTAR);
            long pageSol = paginacionDto.getNumPaginas();
            totalPaginas = (int) ((pageSol % filasPag != 0) ? (pageSol / filasPag) + 1 : pageSol / filasPag);

            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }
    
    public String getPageActual() {
        try {
            paginaList = new ArrayList<Integer>();
            int totalPaginas = getTotalPaginas();
            for (int i = 1; i <= totalPaginas; i++) {
                paginaList.add(i);
            }
            pageActual = String.valueOf(actual) + " de "
                    + String.valueOf(totalPaginas);

            if (numPagina == null) {
                numPagina = "1";
            }
            numPagina = String.valueOf(actual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }


    /**
     * Crea solicitudes HHPP
     *
     * @author Antonio Gil
     * @return null
     */
    public String crearHhpp() {
        try {
           
            String newAdress;

            //Validaciones de Seleccion de los nivel 5 y 6
            if (cmtSolicitudHhppMgl.getOpcionNivel5() == null
                    || cmtSolicitudHhppMgl.getOpcionNivel5().trim().isEmpty()) {
                String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación del HHPP";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            if (cmtSolicitudHhppMgl.getValorNivel5() != null
                    && !cmtSolicitudHhppMgl.getValorNivel5().trim().isEmpty()
                    && (cmtSolicitudHhppMgl.getOpcionNivel5() == null
                    || cmtSolicitudHhppMgl.getOpcionNivel5().trim().isEmpty())) {
                String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación del HHPP";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }

            if (cmtSolicitudHhppMgl.getValorNivel6() != null
                    && !cmtSolicitudHhppMgl.getValorNivel6().trim().isEmpty()
                    && (cmtSolicitudHhppMgl.getOpcionNivel6() == null
                    || cmtSolicitudHhppMgl.getOpcionNivel6().trim().isEmpty())) {
                String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación del HHPP";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            if (cmtSolicitudHhppMgl.getOpcionNivel5() != null
                    && !cmtSolicitudHhppMgl.getOpcionNivel5().trim().isEmpty()) {

                if (cmtSolicitudHhppMgl.getValorNivel5() == null
                        || cmtSolicitudHhppMgl.getValorNivel5().trim().isEmpty()) {
                    String msn = "Debe Ingresar valor para el nivel 5, ya que es requerido para la generación del HHPP";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }
                if (cmtSolicitudHhppMgl.getOpcionNivel5().trim().contains("+")
                        && (cmtSolicitudHhppMgl.getOpcionNivel6() == null
                        || cmtSolicitudHhppMgl.getOpcionNivel6().trim().isEmpty())) {
                    String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación del HHPP";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }
                if (!cmtSolicitudHhppMgl.getOpcionNivel5().trim().contains("+")
                        && (cmtSolicitudHhppMgl.getOpcionNivel6() != null
                        && !cmtSolicitudHhppMgl.getOpcionNivel6().trim().isEmpty())) {
                    String msn = "No debe Seleccionar nivel 6, para la generación de los HHPP";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }

                if (cmtSolicitudHhppMgl.getOpcionNivel6() != null
                        && !cmtSolicitudHhppMgl.getOpcionNivel6().trim().isEmpty()) {

                    if (cmtSolicitudHhppMgl.getValorNivel6() == null
                            || cmtSolicitudHhppMgl.getValorNivel6().trim().isEmpty()) {
                        String msn = "Debe Ingresar valor para el nivel 6, ya que es requerido para la generación del HHPP";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }

                }
                //Validaciones Opcion Piso + Cajero
                if (cmtSolicitudHhppMgl.getOpcionNivel5().equals("PISO + CAJERO")) {
                    if (!cmtSolicitudHhppMgl.getOpcionNivel6().equals("CAJERO")) {
                        String msn = "En la opcion del nivel 6 debe ser seleccionado CAJERO para la opcion PISO + CAJERO";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    if (cmtSolicitudHhppMgl.getValorNivel5().length() > 2) {
                        String msn = "El valor ingresado en el nivel 5 debe ser numerico y no mayor a 2 cifras";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    if (cmtSolicitudHhppMgl.getValorNivel6().length() > 2) {
                        String msn = "El valor ingresado en el nivel 6 debe ser numerico y no mayor a 2 cifras";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    if (!cmtSolicitudHhppMgl.getValorNivel5().matches("[0-9]*")) {
                        String msn = "El valor ingresado en el nivel 5 debe ser numerico";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    if (!cmtSolicitudHhppMgl.getValorNivel6().matches("[0-9]*")) {
                        String msn = "El valor ingr*esado en el nivel 6 debe ser numerico";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                }
            }
            //valbuenayf inicio rq_crear_modificar_hhpp
            boolean isTecnologiavalida = validarTecnologiaEnSolicitud();
            if (!isTecnologiavalida) {
                return null;
            }

           //valbuenayf fin rq_crear_modificar_hhpp
            CmtTipoSolicitudMgl tipoSolicitud = tipoSolicitudFacadeLocal.
                    findTipoSolicitudByAbreviatura(ConstantsCM.TIPO_SOLICITUD_CREACION_HHPP);
            
            solicitudModCM.setTipoSolicitudObj(tipoSolicitud);
            cmtSolicitudHhppMgl.setCmtSubEdificioMglObj(selectedSubEdificio);
            cmtSolicitudHhppMgl.setCmtSolicitudCmMglObj(solicitudModCM);
            cmtSolicitudHhppMgl.setTipoSolicitud(1);
            cmtSolicitudHhppMgl.setEstadoRegistro(1);
            if (solicitudModCM.getListCmtSolicitudHhppMgl() == null) {
                solicitudModCM.setListCmtSolicitudHhppMgl(new ArrayList<CmtSolicitudHhppMgl>());
            }
            //Validacion de la direccion enviada.
            newAdress = cmtSolicitudHhppMglFacadeLocal.ValidadorDireccionHHPP(cmtSolicitudHhppMgl);
            if (newAdress != null) {
                //Validacion que el HHPP no supere los 10 caracteres.
                if (newAdress.length() >= 11) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, ConstantsCM.MSN_ERROR_PROCESO + " El HHPP (" + newAdress + ") no puede superar los 10 caracteres.", ""));
                    return null;
                }
                //Validacion duplicados
                for (CmtSolicitudHhppMgl duplic : cmtSolicitudHhppMglListToChanges) {
                    String newAdressValid = cmtSolicitudHhppMglFacadeLocal.ValidadorDireccionHHPP(duplic);
                    if (newAdress.equalsIgnoreCase(newAdressValid)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, ConstantsCM.MSN_ERROR_PROCESO + " Registro duplicado.", ""));
                        return null;
                    }
                }
                solicitudModCM.getListCmtSolicitudHhppMgl().add(cmtSolicitudHhppMgl);
                cmtSolicitudHhppMgl = new CmtSolicitudHhppMgl();
                cmtSolicitudHhppMglListToChanges = solicitudModCM.getModHhppCreateBySubEdificio(selectedSubEdificio);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, ConstantsCM.MSN_ERROR_PROCESO + " Validacion de dirreccion es posible que ya exista en el subedificio", ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(ConstantsCM.MSN_ERROR_PROCESO + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:crearHhpp(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Actualizar solicitudes HHPP
     *
     * @author Antonio Gil
     * @return null
     */
    public String actualizarHhpp() {
        try {
            String newAdress;
            //Validaciones de Seleccion de los nivel 5 y 6
            if (cmtSolicitudHhppMgl.getOpcionNivel5() == null
                    || cmtSolicitudHhppMgl.getOpcionNivel5().trim().isEmpty()) {
                String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación del HHPP";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            if (cmtSolicitudHhppMgl.getValorNivel5() != null
                    && !cmtSolicitudHhppMgl.getValorNivel5().trim().isEmpty()
                    && (cmtSolicitudHhppMgl.getOpcionNivel5() == null
                    || cmtSolicitudHhppMgl.getOpcionNivel5().trim().isEmpty())) {
                String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación del HHPP";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }

            if (cmtSolicitudHhppMgl.getValorNivel6() != null
                    && !cmtSolicitudHhppMgl.getValorNivel6().trim().isEmpty()
                    && (cmtSolicitudHhppMgl.getOpcionNivel6() == null
                    || cmtSolicitudHhppMgl.getOpcionNivel6().trim().isEmpty())) {
                String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación del HHPP";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            if (cmtSolicitudHhppMgl.getOpcionNivel5() != null
                    && !cmtSolicitudHhppMgl.getOpcionNivel5().trim().isEmpty()) {

                if (cmtSolicitudHhppMgl.getValorNivel5() == null
                        || cmtSolicitudHhppMgl.getValorNivel5().trim().isEmpty()) {
                    String msn = "Debe Ingresar valor para el nivel 5, ya que es requerido para la generación del HHPP";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }
                if (cmtSolicitudHhppMgl.getOpcionNivel5().trim().contains("+")
                        && (cmtSolicitudHhppMgl.getOpcionNivel6() == null
                        || cmtSolicitudHhppMgl.getOpcionNivel6().trim().isEmpty())) {
                    String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación del HHPP";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }
                if (!cmtSolicitudHhppMgl.getOpcionNivel5().trim().contains("+")
                        && (cmtSolicitudHhppMgl.getOpcionNivel6() != null
                        && !cmtSolicitudHhppMgl.getOpcionNivel6().trim().isEmpty())) {
                    String msn = "No debe Seleccionar nivel 6, para la generación de los HHPP";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }

                if (cmtSolicitudHhppMgl.getOpcionNivel6() != null
                        && !cmtSolicitudHhppMgl.getOpcionNivel6().trim().isEmpty()) {

                    if (cmtSolicitudHhppMgl.getValorNivel6() == null
                            || cmtSolicitudHhppMgl.getValorNivel6().trim().isEmpty()) {
                        String msn = "Debe Ingresar valor para el nivel 6, ya que es requerido para la generación del HHPP";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }

                }
            }

            CmtTipoSolicitudMgl tipoSolicitud = tipoSolicitudFacadeLocal.
                    findTipoSolicitudByAbreviatura(ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP);
            
            solicitudModCM.setTipoSolicitudObj(tipoSolicitud);
            
            cmtSolicitudHhppMgl.setCmtSubEdificioMglObj(selectedSubEdificio);
            cmtSolicitudHhppMgl.setCmtSolicitudCmMglObj(solicitudModCM);
            cmtSolicitudHhppMgl.setHhppMglObj(selectHhppMgl);
            //Tipo solicitud 2 Modificacion.
            cmtSolicitudHhppMgl.setTipoSolicitud(2);
            cmtSolicitudHhppMgl.setEstadoRegistro(1);
            if (solicitudModCM.getListCmtSolicitudHhppMgl() == null) {
                solicitudModCM.setListCmtSolicitudHhppMgl(new ArrayList<CmtSolicitudHhppMgl>());
            }
            //Validacion de la direccion enviada.
            newAdress = cmtSolicitudHhppMglFacadeLocal.ValidadorDireccionHHPP(cmtSolicitudHhppMgl);
            if (newAdress != null) {
                //Validacion que el HHPP no supere los 10 caracteres.
                if (newAdress.length() >= 11) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, ConstantsCM.MSN_ERROR_PROCESO + " El HHPP (" + newAdress + ") no puede superar los 10 caracteres.", ""));
                    return null;
                }
                for (CmtSolicitudHhppMgl duplic : cmtSolicitudHhppMglListToChanges) {
                    String newAdressValid = cmtSolicitudHhppMglFacadeLocal.ValidadorDireccionHHPP(duplic);
                    if (newAdress.equalsIgnoreCase(newAdressValid)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, ConstantsCM.MSN_ERROR_PROCESO + " Registro duplicado.", ""));
                        return null;
                    }
                }
                for (CmtSolicitudHhppMgl hhpp: solicitudModCM.getListCmtSolicitudHhppMgl()) {
                    if (hhpp!= null && hhpp.getHhppMglObj() != null
                            && hhpp.getHhppMglObj().getHhpId() != null
                            && cmtSolicitudHhppMgl.getHhppMglObj() != null
                            && cmtSolicitudHhppMgl.getHhppMglObj().getHhpId() != null
                            && hhpp.getHhppMglObj().getHhpId().compareTo(
                            cmtSolicitudHhppMgl.getHhppMglObj().getHhpId())==0){
                        solicitudModCM.getListCmtSolicitudHhppMgl().remove(hhpp);
                        break;
                    }
                }
                solicitudModCM.getListCmtSolicitudHhppMgl().add(cmtSolicitudHhppMgl);                
                cmtSolicitudHhppMgl = new CmtSolicitudHhppMgl();
                cmtSolicitudHhppMglListToChanges = solicitudModCM.getModHhppCreateBySubEdificio(selectedSubEdificio);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, ConstantsCM.MSN_ERROR_PROCESO + " Validacion de direccion.", ""));
                return null;
            }
        } catch (ApplicationException e) {
            String msn = ConstantsCM.MSN_ERROR_PROCESO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:actualizarHhpp(). " + e.getMessage(), e, LOGGER);
        }
        return "/view/MGL/CM/solicitudes/HHPP/modificaSolicitudhhpp";
    }

    /**
     * Gestiona las solicitudes HHPP
     *
     * @author Antonio Gil
     * @return null
     */
    public String gestionarHhpp() {
        try {
            String newAdress;
            if (solicitudModCM.getListCmtSolicitudHhppMgl() == null) {
                solicitudModCM.setListCmtSolicitudHhppMgl(new ArrayList<CmtSolicitudHhppMgl>());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "", ""));
                return null;
            }
            //Validacion de la direccion enviada.
            newAdress = cmtSolicitudHhppMglFacadeLocal.ValidadorDireccionHHPP(cmtSolicitudHhppMgl);
        } catch (ApplicationException e) {
            String msn = ConstantsCM.MSN_ERROR_PROCESO + "" + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:gestionarHhpp(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Elimnina solicitudes HHPP
     *
     * @author Antonio Gil
     * @return null
     */
    public String eliminaHhpp() {
        try {
            cmtSolicitudHhppMglListToChanges =
                    solicitudModCM.getModHhppDeleteBySubEdificio(
                    solicitudModCM.getListCmtSolicitudHhppMgl(), selectedSubEdificio, cmtSolicitudHhppMgl);
            solicitudModCM.setListCmtSolicitudHhppMgl(cmtSolicitudHhppMglListToChanges);
            encabezadoSolicitudModificacionCMBean.setSolicitudModCM(solicitudModCM);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, ConstantsCM.MSN_PROCESO_EXITOSO, ""));
        } catch (Exception e) {
            String msn = ConstantsCM.MSN_ERROR_PROCESO + "" + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        solicitudModCM.setListCmtSolicitudHhppMgl(cmtSolicitudHhppMglListToChanges);
        cmtSolicitudHhppMglListToChanges = solicitudModCM.getModHhppCreateBySubEdificio(selectedSubEdificio);
            try {
            listaHhppToChanges = hhppFacadeLocal.findHhppBySubEdificioId(selectedSubEdificio.getSubEdificioId());
        } catch (ApplicationException ex) {
            String msn = ConstantsCM.MSN_ERROR_PROCESO + "Eliminar Hhpp";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage(), ex, LOGGER);
        }
        return null;
    }

    /**
     * Elimnina Gestiones HHPP
     *
     * @author Antonio Gil
     * @return null
     */
    public String eliminaGesHhpp() {
        try {
            cmtSolicitudHhppMglListToChanges =
                    cmtSolicitudHhppMglFacadeLocal.ActualizaCambiosHhpp(
                    cmtSolicitudHhppMglListToChanges, cmtSolicitudHhppMgl, usuarioVT, perfilVt);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, ConstantsCM.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = ConstantsCM.MSN_ERROR_PROCESO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:eliminaGesHhpp(). " + e.getMessage(), e, LOGGER);
        }
        solicitudModCM.setListCmtSolicitudHhppMgl(cmtSolicitudHhppMglListToChanges);
        cmtSolicitudHhppMglListToChanges = solicitudModCM.getListCmtSolicitudHhppMgl();
        return null;
    }

    /**
     * editarHhpp solicitudes HHPP
     *
     * @author Antonio Gil
     * @return null
     */
    public String editarHhpp() {
        String formTabSeleccionado = "/view/MGL/CM/solicitudes/HHPP/updateSolicitudhhpp";
        try {
            encabezadoSolicitudModificacionCMBean.setSelectedTab("MODIFICACION_HHPP");
        } catch (Exception e) {
            String msn = ConstantsCM.MSN_ERROR_PROCESO + "" + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        return formTabSeleccionado;
    }
    
    /**
     * crearSolicitud crea las solicitudes HHPP
     *
     * @author Antonio Gil
     * @return null
     */
    public String crearSolicitud() {
        try {
            if (CollectionUtils.isEmpty(cmtSolicitudHhppMglListToChanges)) {
                generateNotificationMsg(ConstantsCM.MSN_ERROR_PROCESO + " No existen cambios", MessageSeverityEnum.ERROR);
                return null;
            }
            // correo asesor
            boolean esCorreoAsesorValido = validarCorreoAsesor();
            if (!esCorreoAsesorValido) {
                //Si no es válido el correo de asesor muestra el mensaje de error de validación
                return "";
            }

            // correo copia
            String destinatariosCopia = "";
            String emailUsuario = Optional.ofNullable(encabezadoSolicitudModificacionCMBean)
                    .map(EncabezadoSolicitudModificacionCMBean::getUsuarioSolicitudCreador)
                    .map(UsuariosServicesDTO::getEmail).orElse("");

            if (StringUtils.isNotBlank(emailUsuario)) {
                // Se adiciona el email del usuario solicitante.
                destinatariosCopia = emailUsuario;
            }

            String correoCopiaSolicitud = Optional.ofNullable(solicitudModCM).map(CmtSolicitudCmMgl::getCorreoCopiaSolicitud).orElse("");
            boolean isCorreoCopiaValido = validarCorreoCopia(correoCopiaSolicitud);

            if (!isCorreoCopiaValido) {
                //Si no cumple la validación de correo copia, lanza mensaje de error generado.
                return "";
            }

            if (StringUtils.isNotBlank(correoCopiaSolicitud)) {
                // los destinatarios se separan por comma.
                destinatariosCopia += ",";
            }

            destinatariosCopia += correoCopiaSolicitud;

            //valbuenayf rq_crear_modificar_hhpp
            for (CmtSolicitudHhppMgl c : cmtSolicitudHhppMglListToChanges) {
                if (c.getTipoSolicitud() == 1) {
                    boolean isTecnologiaValida = validarTecnologiaEnSolicitud();
                   if (!isTecnologiaValida){
                       //si no cumple validación se muestra el mensaje configurado
                       return null;
                   }
                } else if (c.getTipoSolicitud() == 2) {
                    solicitudModCM.setBasicaIdTecnologia(null);
                }

                //Traslado HHPP Bloqueado
                if (c.getTipoSolicitud() == ConstantsSolicitudHhpp.CM_SOL_TRASLADO_HHPP_BLOQUEADO){
                    solicitudModCM.setNumeroClienteTrasladar(c.getNumCuentaClienteTraslado());
                }
            }
            //valbuenayf fin rq_crear_modificar_hhpp
            solicitudModCM.setCorreoCopiaSolicitud(destinatariosCopia);
            solicitudModCM = encabezadoSolicitudModificacionCMBean.getSolicitudModCM();
            solicitudModCM.setCuentaMatrizObj(cuentaMatrizMgl);
            //asigno todos los datos para guardar
            cmtSolicitudHhppMglListToChanges = solicitudModCM.getListCmtSolicitudHhppMgl();
            solicitudModCM.setListCmtSolicitudHhppMgl(null);
            solicitudModCM.setTempSolicitud(encabezadoSolicitudModificacionCMBean.getTimeSol());
            solicitudModCM.setDisponibilidadGestion("1");
            solicitudFacadeLocal.setUser(usuarioVT, perfilVt);
            // Registra la solicitud
            solicitudModCM = solicitudFacadeLocal.crearSol(solicitudModCM);
            if (solicitudModCM.getSolicitudCmId() != null) {
                //Creacion de las solicitudes
                String validacionHhpp;
                validacionHhpp = cmtSolicitudHhppMglFacadeLocal.GuardaListadoHHPP(cmtSolicitudHhppMglListToChanges, solicitudModCM, usuarioVT, perfilVt);
                if (validacionHhpp == null) {
                    generateNotificationMsg(ConstantsCM.MSN_ERROR_PROCESOHHPP + " No fue posible crear HHPP", MessageSeverityEnum.ERROR);
                    return null;
                }
                //Creacion de nota..
                solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                CmtNotasSolicitudMgl notaSolicitudMgl = encabezadoSolicitudModificacionCMBean.getCmtNotasSolicitudMgl();
                notaSolicitudMgl.setSolicitudCm(solicitudModCM);
                String nombreSolicitud = Optional.of(solicitudModCM).map(CmtSolicitudCmMgl::getTipoSolicitudObj)
                        .map(CmtTipoSolicitudMgl::getNombreTipo).orElse("");
                boolean isTrasladoHhppBloqueado = nombreSolicitud.equalsIgnoreCase(ConstantsSolicitudHhpp.NOMBRE_TIPO_TRASLADO_HHPP_BLOQUEADO);
                notaSolicitudMgl.setDescripcion(isTrasladoHhppBloqueado ? "Traslado HHPP Bloquedo" : "Creacion Solicitud HHPP");
                notaSolicitudMgl = notasSolicitudMglFacadeLocal.crearNotSol(notaSolicitudMgl);
                encabezadoSolicitudModificacionCMBean.setCmtNotasSolicitudMgl(notaSolicitudMgl);

                /* Autogestionar solicitud de Traslado HHPP Bloqueado (creación de HHPP Virtual)*/
                //Si no pudo gestionar la solicitud de traslado HHPP bloqueado
                // retorna el mensaje de error ya procesado en el contexto actual
                //TODO: validar si se esta pasando el num cuenta cliente a trasladar
                //solicitudModCM.setNumeroClienteTrasladar();
                if (isTrasladoHhppBloqueado && !gestionarSolicitudTrasladoHhppBloqueado()) {
                    return "";
                }
            }

            String numCuentaMatriz = Optional.ofNullable(solicitudModCM).map(CmtSolicitudCmMgl::getCuentaMatrizObj)
                    .map(CmtCuentaMatrizMgl::getNumeroCuenta).map(BigDecimal::toString).orElse("");
            String idSolicitud = Optional.ofNullable(solicitudModCM).map(CmtSolicitudCmMgl::getSolicitudCmId).map(BigDecimal::toString).orElse("");
            String msgInfo = "Se ha creado la solicitud de HHPP No.<b> " + idSolicitud + "</b> en la CM (" + numCuentaMatriz + ")";
            generateNotificationMsg(msgInfo, MessageSeverityEnum.INFO);

        } catch (ApplicationException e) {
            String msn = ConstantsCM.MSN_ERROR_PROCESO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudHhppCMBean:crearSolicitud(). " + e.getMessage(), e, LOGGER);
        }
        return "/view/MGL/CM/solicitudes/HHPP/crearSolicitudhhpp";
    }

    /**
     * Verifica si la tecnología es válida para el proceso de la solicitud.
     *
     * @return {code boolean} Retorna true si la tecnología es válida.
     * @throws ApplicationException Excepción personalizada de la App.
     * @author Gildardo Mora
     */
    private boolean validarTecnologiaEnSolicitud() throws ApplicationException {
        BigDecimal basicaId = Optional.ofNullable(solicitudModCM).map(CmtSolicitudCmMgl::getBasicaIdTecnologia)
                .map(CmtBasicaMgl::getBasicaId).orElse(null);

        if (Objects.isNull(basicaId)) {
            String msn = "Debe Ingresar valor para tecnologia, ya que es requerido para la generación del HHPP";
            generateNotificationMsg(msn, MessageSeverityEnum.ERROR);
            return false;
        }

        //Busco la tecnologia
        CmtBasicaMgl tecnologia = cmtBasicaMglFacadeLocal.findById(basicaId);
        BigDecimal tecnologiaBasicaId = Optional.ofNullable(tecnologia).map(CmtBasicaMgl::getBasicaId).orElse(null);
        String identificadorApp = Optional.ofNullable(tecnologia).map(CmtBasicaMgl::getIdentificadorInternoApp).orElse("");
        //se valida si es RED FO  para no permitir la creación
        if (Objects.nonNull(tecnologiaBasicaId) && identificadorApp.equalsIgnoreCase(Constant.RED_FO)) {
            String msn = "No se pueden crear HHPP para una tecnologia FO";
            generateNotificationMsg(msn, MessageSeverityEnum.ERROR);
            return false;
        }

        return true;
    }

    /**
     * crearSolicitud crea las solicitudes HHPP
     *
     * @author Antonio Gil
     * @return null
     */
    public String gestionarSolicitud() {
        try {
            Map<String, Object> responseGestionSolicitudHhpp = new HashMap<>();
            String destinatarioCopia = "";

            if (CollectionUtils.isEmpty(cmtSolicitudHhppMglListToChanges)) {
                String msn = "NO existen cambios en la solicitud";
                generateNotificationMsg(msn, MessageSeverityEnum.WARN);
                return "";
            }

            boolean esCorreoAsesorValido = validarCorreoAsesor();
            if (!esCorreoAsesorValido) {
                //Si no es válido el correo de asesor muestra el mensaje de error de validación
                return "";
            }

            String emailUsuarioCreadorSolicitud = Optional.ofNullable(encabezadoSolicitudModificacionCMBean)
                    .map(EncabezadoSolicitudModificacionCMBean::getUsuarioSolicitudCreador)
                    .map(UsuariosServicesDTO::getEmail).orElse(null);

            if (StringUtils.isNotBlank(emailUsuarioCreadorSolicitud)) {
                // Se adiciona el email del usuario solicitante.
                destinatarioCopia = emailUsuarioCreadorSolicitud;
            }

            String correoCopiaGestion = Optional.ofNullable(encabezadoSolicitudModificacionCMBean)
                    .map(EncabezadoSolicitudModificacionCMBean::getSolicitudModCM).map(CmtSolicitudCmMgl::getCorreoCopiaGestion).orElse("");
            boolean isCorreoCopiaValido = validarCorreoCopia(correoCopiaGestion);

            if (!isCorreoCopiaValido) {
                //Si no cumple la validación de correo copia, lanza mensaje de error generado.
                return "";
            }

            if (StringUtils.isNotBlank(correoCopiaGestion)) {
                destinatarioCopia += ",";
            }

            destinatarioCopia += correoCopiaGestion;
            solicitudModCM.setCorreoCopiaSolicitud(destinatarioCopia);
            solicitudModCM = encabezadoSolicitudModificacionCMBean.getSolicitudModCM();

            if (solicitudModCM == null || solicitudModCM.getSolicitudCmId() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstantsCM.MSN_ERROR_PROCESOHHPP, " No existe solicitud"));
                return null;
            }

                //CODIGO 135 FINALIZAR SOLICITUD CREA HHPP
                BigDecimal tipoBasicaId = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_CREAR_HHPP).getTipoBasicaId();

                if (encabezadoSolicitudModificacionCMBean.getAccionSelect().compareTo(tipoBasicaId) == 0) {
                    solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                     solicitudModCM.setListCmtSolicitudHhppMgl(cmtSolicitudHhppMglListToChanges);
                    //Creacion de las solicitudes
                    String validacionHhpp;
                    solicitudModCM.setTipoVivienda(tipoHhpp);
                    //JDHT
                    CmtSolictudHhppMglManager cmtSolicitudHhppMglManager = new CmtSolictudHhppMglManager();
                    if (cmtSolicitudHhppMglListToChanges != null && !cmtSolicitudHhppMglListToChanges.isEmpty() 
                            && solicitudModCM.getBasicaIdTecnologia() != null) {
                        boolean existeTecnoSub = false;
                        for (CmtSolicitudHhppMgl cmtSolicitudHhppMglListToChange : cmtSolicitudHhppMglListToChanges) {
                            CmtTecnologiaSubMgl tecnologiaSubMgl = cmtSolicitudHhppMglManager.consultarTecnologiaSub(cmtSolicitudHhppMglListToChange, solicitudModCM.getBasicaIdTecnologia());//valbuenayf rq_crear_modificar_hhpp

                            if (tecnologiaSubMgl == null) {
                                existeTecnoSub = true;
                                break;
                            }
                        }
                        if (existeTecnoSub) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(
                                            FacesMessage.SEVERITY_ERROR,
                                            "La tecnología : " + solicitudModCM.getBasicaIdTecnologia().getNombreBasica() + " ya no se encuentra asociada al edificio, es necesario crear una nueva solicitud.",""));
                            return null;
                        }

                    }

                    //Gestión de la solicitud
                    responseGestionSolicitudHhpp = cmtSolicitudHhppMglFacadeLocal.
                            guardaGestionHHPP(solicitudModCM.getHhppToChangeList(), solicitudModCM, usuarioVT, perfilVt);

                    validacionHhpp = (String) responseGestionSolicitudHhpp.getOrDefault("msgGestionHhpp", null);

                    if (validacionHhpp == null) {
                         FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                ConstantsCM.MSN_ERROR_PROCESOHHPP,
                                validacionHhpp +" No fue posible crear HHPP"));
                        return null;
                    }
                }
                //Actualizo el estado de la gestion.
                solicitudModCM = encabezadoSolicitudModificacionCMBean.getSolicitudModCM();
                if(encabezadoSolicitudModificacionCMBean.getRespuestaSelect()!= null){
                  CmtBasicaMgl resultado = cmtBasicaMglFacadeLocal.
                          findById(encabezadoSolicitudModificacionCMBean.getRespuestaSelect());
                  
                  if(resultado.getBasicaId() != null){
                    solicitudModCM.setResultGestion(resultado);
                  }
                }
                solicitudModCM.setFechaInicioGestion(new Date());
                solicitudModCM = solicitudFacadeLocal.gestionSolicitudHhpp(solicitudModCM, usuarioVT, perfilVt);
                solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());

                encabezadoSolicitudModificacionCMBean.setSolicitudModCM(solicitudModCM);
                encabezadoSolicitudModificacionCMBean.setGestionado(true);
                //Mensaje de finalizacion de la solicitud.
                if (encabezadoSolicitudModificacionCMBean.getAccionSelect().
                        compareTo(cmtTipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_RECHAZAR_HHPP).getTipoBasicaId()) == 0) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Se ha cerrado la solicitud. CM (" + solicitudModCM.getCuentaMatrizObj().getNumeroCuenta().toString() + ")", ""));
                } else {
                    //TODO: validar para extraer código completo a nuevo metodo de traslado HHPP Bloqueado
                    //TODO: Validar desde la vista de gestión de solicitudes de CCMM cuales son los datos a proporcionar.
                    Optional<CmtSolicitudHhppMgl> solTrasladoHhppBloqueado = cmtSolicitudHhppMglListToChanges.stream()
                            .filter(x -> x.getTipoSolicitud() == ConstantsSolicitudHhpp.CM_SOL_TRASLADO_HHPP_BLOQUEADO).findFirst();
                    String direccionHhppVirtual = "";
                    String msgVirtual = "";

                    if (solTrasladoHhppBloqueado.isPresent()) {
                        HhppMgl hhppMglVirtual = solTrasladoHhppBloqueado.get().getHhppMglVirtual();
                        direccionHhppVirtual = hhppMglVirtual.getHhpCalle() + " " + hhppMglVirtual.getHhpPlaca() + " " + hhppMglVirtual.getHhpApart()+ "V";
                        msgVirtual = "<br><br>   Para la dirección " + " " + direccionHhppVirtual;
                        boolean isTrasladoHhppExitoso = (boolean) responseGestionSolicitudHhpp.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_GESTION, false);
                        String msgResultado = (String) responseGestionSolicitudHhpp.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULTADO, "");
                        msgVirtual = msgVirtual.concat("<br>")
                                .concat(isTrasladoHhppExitoso ? "Solicitud de traslado HHPP bloqueado Procesada y finalizada con éxito."
                                        : "Solicitud de traslado HHPP bloqueado no se procesó correctamente..")
                                .concat(msgResultado);

                        String msgInfo = "Se ha Gestionado la solictud <b>"
                                .concat(solicitudModCM.getSolicitudCmId().toString())
                                .concat("</b> de la CM <b>(").concat(solicitudModCM.getCuentaMatrizObj().getNumeroCuenta().toString())
                                .concat(")</b>")
                                .concat(msgVirtual);

                        generateNotificationMsg(msgInfo , isTrasladoHhppExitoso ? MessageSeverityEnum.INFO : MessageSeverityEnum.WARN);

                    } else {
                        String msgInfo = "Se ha Gestionado la solictud <b>"
                                .concat(solicitudModCM.getSolicitudCmId().toString())
                                .concat("</b> de la CM <b>(").concat(solicitudModCM.getCuentaMatrizObj().getNumeroCuenta().toString())
                                .concat(")</b>");

                        generateNotificationMsg(msgInfo , MessageSeverityEnum.INFO);
                    }


                    String destinatariosCopia = "";
                    if (correoUsuario != null && !correoUsuario.isEmpty()) {
                        // Se adiciona el email del usuario solicitante.
                        destinatariosCopia = correoUsuario;
                    }
                    // los destinatarios se separan por comma.
                    if (!destinatariosCopia.isEmpty()) {
                        destinatariosCopia += ",";
                    }
                    destinatariosCopia += solicitudModCM.getCorreoCopiaSolicitud();
                    solicitudModCM.setCorreoCopiaSolicitud(destinatariosCopia);

                    solicitudFacadeLocal.envioCorreoGestion(solicitudModCM);
                }

        } catch (ApplicationException | ApplicationExceptionCM e) {
            String msn = "Error al gestionar la solicitud: " + e.getMessage();
            exceptionServBean.notifyError(e, msn, "GESTIONAR SOLICITUD HHPP CCMM");
        } catch (Exception e) {
            String msgError = "Ocurrió un error al gestionar la solicitud: " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, "GESTIONAR SOLICITUD HHPP CCMM");
        }
        return null;
    }

    /**
     * Realiza la comprobación del correo copia
     *
     * @param correoCopia {@link String}
     * @return {@code boolean} Retorna true si el correo cumple validaciones.
     * @author Gildardo Mora
     */
    private boolean validarCorreoCopia(String correoCopia) {
        if (StringUtils.isBlank(correoCopia)) {
            return true;
        }

        boolean esCorreoCopiaValido = validarCorreo(correoCopia);
        if (!esCorreoCopiaValido) {
            String msg = "El campo copia a no tiene el formato requerido";
            generateNotificationMsg(msg, MessageSeverityEnum.ERROR);
            return false;
        }

        boolean esDominioCorreoValido = validarDominioCorreos(correoCopia);
        if (!esDominioCorreoValido) {
            String msg = "El campo copia a debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
            generateNotificationMsg(msg, MessageSeverityEnum.WARN);
            return false;
        }

        return true;
    }

    /**
     * Verifica el correo de asesor.
     *
     * @return {@code boolean} retorna true si el correo es valido.
     * @author Gildardo Mora
     */
    private boolean validarCorreoAsesor() {
        // correo asesor
        String correoAsesor = Optional.ofNullable(solicitudModCM).map(CmtSolicitudCmMgl::getCorreoAsesor).orElse(null);
        if (StringUtils.isNotBlank(correoAsesor)) {
            if (!validarCorreo(correoAsesor)) {
                String msg = "El campo correo asesor no tiene el formato requerido";
                generateNotificationMsg(msg, MessageSeverityEnum.ERROR);
                return false;
            }

            if (!validarDominioCorreos(correoAsesor)) {
                String msg = "El campo asesor debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                generateNotificationMsg(msg, MessageSeverityEnum.WARN);
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica el dominio del correo
     *
     * @param correoCopia {@link String} Correo copia.
     * @return Retorna true, si el dominio es válido
     */
    public boolean validarDominioCorreos(String correoCopia) {
        if (StringUtils.isBlank(correoCopia)) {
            return true;
        }

        return correoCopia.toLowerCase().contains("claro.com.co")
                || correoCopia.toLowerCase().contains("telmex.com.co")
                || correoCopia.toLowerCase().contains("comcel.com.co")
                || correoCopia.toLowerCase().contains("telmexla.com")
                || correoCopia.toLowerCase().contains("telmex.com");
    }
                        
                               /**
     * cardenaslb metodo para validar correo
     *
     * @param correo
     * @return
     */
    private boolean validarCorreo(String correo) {
        boolean respuesta = false;
        try {
            String mail = "([\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z])?";
            Pattern pattern = Pattern.compile(mail);
            Matcher matcher = pattern.matcher(correo);
            respuesta = matcher.matches();
        } catch (Exception e) {
            LOGGER.error("Error en validarCorreo de InfoCreaCMBean: " + e);
        }
        return respuesta;
    }
    
      public boolean validarModHhppCM() {
          final String VALIDARMODHHPPSOLCM = "VALIDARMODHHPPSOLCM";
        return validarEdicion(VALIDARMODHHPPSOLCM);
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /* Brief 57762 */

    /**
     * Verifica si se cumplen los requisitos para la dirección procesada
     * el proceso de traslado HHPP Bloqueado
     *
     * @author Gildardo Mora
     */
    public void validarDireccionHhppTraslado() {

        try {
            if (!validateFieldsTrasladoHhppBloqueado()) {
                // si no cumple validaciones, muestra el mensaje de resultado, generado
                return;
            }

            CmtTipoSolicitudMgl tipoSolicitud = tipoSolicitudFacadeLocal.
                    findTipoSolicitudByAbreviatura(ConstantsCM.TIPO_SOLICITUD_TRASLADO_HHPP_BLOQUEADO);
            solicitudModCM.setTipoSolicitudObj(tipoSolicitud);
            cmtSolicitudHhppMgl.setCmtSubEdificioMglObj(selectedSubEdificio);
            cmtSolicitudHhppMgl.setCmtSolicitudCmMglObj(solicitudModCM);
            cmtSolicitudHhppMgl.setTipoSolicitud(ConstantsSolicitudHhpp.CM_SOL_TRASLADO_HHPP_BLOQUEADO);//1 crear, 2 modificar, 3 HHPP virtual
            cmtSolicitudHhppMgl.setEstadoRegistro(1);
            cmtSolicitudHhppMgl.setHhppMglObj(selectHhppMgl);

            Map<String, Object> apartHhppVirtual = cmtSolicitudHhppMglFacadeLocal.validarApartHhppVirtual(cmtSolicitudHhppMgl);
            HhppMgl hhppEncontrado = (HhppMgl) apartHhppVirtual.getOrDefault("hhppEncontrado", null);
            String newAdress = (String) apartHhppVirtual.getOrDefault("newAdress", null);

            if (newAdress == null) {
                generateNotificationMsg(ConstantsCM.MSN_ERROR_PROCESO
                        + " Validación de dirección es posible que ya exista en el subedificio", MessageSeverityEnum.ERROR);
                return;
            }

            if (Objects.isNull(hhppEncontrado)){
                generateNotificationMsg(" El HHPP no existe, debe solicitar la creación de HHPP", MessageSeverityEnum.WARN);
                return;
            }

            if (Objects.isNull(solicitudModCM.getListCmtSolicitudHhppMgl())) {
                solicitudModCM.setListCmtSolicitudHhppMgl(new ArrayList<>());
            }

            if (!validarTrasladoHhppBloqueado(hhppEncontrado)) {
                // si no cumple validaciones, muestra el mensaje de resultado, generado
                return;
            }

            solicitudModCM.getListCmtSolicitudHhppMgl().add(cmtSolicitudHhppMgl);
            cmtSolicitudHhppMgl = new CmtSolicitudHhppMgl();
            cmtSolicitudHhppMglListToChanges = solicitudModCM.getModHhppCreateBySubEdificio(selectedSubEdificio);
            String msg = "Dirección validada correctamente, puede continuar con el traslado de HHPP.";
            generateNotificationMsg(msg, MessageSeverityEnum.INFO);
            habilitarCrearHhppVirtual = false;

        } catch (ApplicationException ae) {
            String msgError = "App Exc: " + ae.getMessage();
            LOGGER.error(msgError, ae);
            generateNotificationMsg(msgError, MessageSeverityEnum.ERROR);
        } catch (Exception e) {
            String msgError = "Error: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msgError, e, LOGGER);
        }
    }

    /**
     * Comprueba que los campos requeridos cumplan con los parámetros indicados
     *
     * @return Retorna true si al comprobar los fields (campos) son válidos
     * @author Gildardo Mora
     */
    public boolean validateFieldsTrasladoHhppBloqueado() {

        if (Objects.isNull(cuentaMatrizMgl)) {
            String msg = "No se Identificó la cuenta Matriz";
            generateNotificationMsg(msg, MessageSeverityEnum.ERROR);
            return false;
        }

        if (StringUtils.isBlank(cmtSolicitudHhppMgl.getOpcionNivel5())
                || StringUtils.isBlank(cmtSolicitudHhppMgl.getValorNivel5())) {
            String msg = "Debe seleccionar nivel 5, <br> ya que es requerido para validar el hhpp";
            generateNotificationMsg(msg, MessageSeverityEnum.ERROR);
            return false;
        }

        if (StringUtils.isBlank(cmtSolicitudHhppMgl.getNumCuentaClienteTraslado())
                || cmtSolicitudHhppMgl.getNumCuentaClienteTraslado().equals("0")
                || !StringToolUtils.containsOnlyNumbers(cmtSolicitudHhppMgl.getNumCuentaClienteTraslado())) {
            String msg = "El campo número de cuenta cliente, debe tener un valor valido";
            generateNotificationMsg(msg, MessageSeverityEnum.ERROR);
            return false;
        }

        return true;
    }

    /**
     * Se encarga de agregar los mensajes de validación al contexto actual.
     *
     * @param msg     {@link String} Mensaje que se va a mostrar al usuario
     * @param msgType {@link MessageSeverityEnum} Tipo de severidad del mensaje
     * @author Gildardo Mora
     */
    private void generateNotificationMsg(String msg, MessageSeverityEnum msgType) {
        msg = "<Html><ul>" + msg + "</ul></Html>";
        MessageUtils.buildFacesMessage(msgType.getSeverity(), msg);
    }

    /**
     * Verifica si se puede llevar a cabo el traslado de HHPP
     *
     * @param hhppEncontrado {@link HhppMgl}
     * @return Retorna true, si se cumplen las condiciones para realizar el traslado
     * @throws ApplicationException Excepción de la app
     * @author Gildardo Mora
     */
    public boolean validarTrasladoHhppBloqueado(HhppMgl hhppEncontrado) throws ApplicationException {
        String msgValidationHhpp;
        Optional<ResponseDataValidaRazonesCreaHhppVt> responseValidateReasonsRs;

        try {
            //Asigna datos de la petición al servicio
            Optional<RequestDataValidaRazonesCreaHhppVt> requestValidateReasonsRs = generateRequestForServiceValidate(
                    cmtSolicitudHhppMgl.getNumCuentaClienteTraslado(), hhppEncontrado);

           if (!requestValidateReasonsRs.isPresent()){
               generateNotificationMsg("No se pudo generar la petición al servicio de validaciones",MessageSeverityEnum.ERROR);
               return false;
            }

            responseValidateReasonsRs = hhppVirtualMglFacadeLocal.callServiceValidateMoveReasonsResource(requestValidateReasonsRs.get());
            if (!responseValidateReasonsRs.isPresent()) {
                generateNotificationMsg("No hubo respuesta del servicio validateMoveReasonsResource, intente mas tarde.", MessageSeverityEnum.ERROR);
                return false;
            }

           //realiza las validaciones para el traslado de HHPP
            Map<String, Object> validateResult = hhppVirtualMglFacadeLocal.validateTrasladoHhppBloqueado(responseValidateReasonsRs.get(),
                    cmtSolicitudHhppMgl.getNumCuentaClienteTraslado());

            if (!(boolean) validateResult.getOrDefault("esValido", false)) {
                msgValidationHhpp = String.valueOf(validateResult.get("msg"));
                MessageSeverityEnum severityType = (MessageSeverityEnum) validateResult.get("tipoMsg");
                generateNotificationMsg(msgValidationHhpp, severityType);
                return false;
            }

            //si cumple todas las validaciones
            return true;

        } catch (Exception e) {
            msgValidationHhpp = "Ocurrió un error en: " + ClassUtils.getCurrentMethodName(this.getClass()) + " al validar la dirección: " + e.getMessage();
            LOGGER.error(msgValidationHhpp , e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Genera el request requerido para el llamado al servicio validateMoveReasonsResource
     *
     * @param numCuentaClienteTraslado {@link String} Número de cuenta del cliente
     * @param hhpp                     {@link List<HhppMgl>} HHPP al que se le va a crear HHPP virtual
     * @return {@link RequestDataValidaRazonesCreaHhppVt} Retorna la data para la petición al servicio.
     * @author Gildardo Mora
     */
    private Optional<RequestDataValidaRazonesCreaHhppVt> generateRequestForServiceValidate(
            String numCuentaClienteTraslado, HhppMgl hhpp) {

        if (Objects.nonNull(hhpp)) {
            cmtSolicitudHhppMgl.setHhppMglVirtual(hhpp);
            return hhppVirtualMglFacadeLocal.generateRequestForServiceValidate(hhpp, numCuentaClienteTraslado);
        }

        return Optional.empty();
    }

    /**
     * Realiza la gestión de traslado de HHPP Bloqueado
     *
     * @return Retorna true si se gestionó correctamente la solicitud
     * @throws ApplicationException Excepción de la App
     * @author Gildardo Mora
     */
    private boolean gestionarSolicitudTrasladoHhppBloqueado() throws ApplicationException {
        try {
            //TODO: Validar para asignar acción finalizada sin counsultar párametro
            if (Objects.isNull(accionFinalizarSolicitudHhppVirtual)) {
                accionFinalizarSolicitudHhppVirtual = ParametrosMerUtil
                        .findValor(ParametrosMerEnum.ACCION_SOLICITUD_HHPP_VIRTUAL_CCMM.getAcronimo());
            }
            BigDecimal idAccionFinalizada = cmtTipoBasicaMglFacadeLocal
                    .findByCodigoInternoApp(accionFinalizarSolicitudHhppVirtual).getTipoBasicaId();

            //TODO: verificar para asignar respuesta sin realizar consulta de parámetro
            if (Objects.isNull(msgRespuestaSolicitudHhppVirtual)) {
                msgRespuestaSolicitudHhppVirtual = ParametrosMerUtil
                        .findValor(ParametrosMerEnum.RESPUESTA_ACCION_SOLICITUD_HHPP_VIRTUAL_CCMM.getAcronimo());
            }

            List<CmtBasicaMgl> basicaMglList = cmtBasicaMglFacadeLocal.findByNombreBasica(msgRespuestaSolicitudHhppVirtual);
            BigDecimal idRespuesta = basicaMglList.stream()
                    .filter(basica -> {
                        Optional<BigDecimal> idBasica = Optional.ofNullable(basica).map(CmtBasicaMgl::getTipoBasicaObj).map(CmtTipoBasicaMgl::getTipoBasicaId);
                        return idBasica.isPresent() && idBasica.get().equals(idAccionFinalizada);
                    })
                    .findFirst()
                    .map(CmtBasicaMgl::getBasicaId)
                    .orElse(null);

            //FINALIZAR SOLICITUD CREA HHPP 135
            encabezadoSolicitudModificacionCMBean.setAccionSelect(idAccionFinalizada);
            //GESTION HHPP ACEPTADA 887
            encabezadoSolicitudModificacionCMBean.setRespuestaSelect(idRespuesta);
            encabezadoSolicitudModificacionCMBean.getSolicitudModCM().setRespuestaActual(ConstantsSolicitudHhpp.NOMBRE_TIPO_TRASLADO_HHPP_BLOQUEADO);

            /* Gestionar la solicitud de traslado de HHPP bloqueado */
            String responseSolicitud = gestionarSolicitud();
            return StringUtils.isNotBlank(responseSolicitud);//TODO: validar respuesta para determinar cuando falla.

        } catch (Exception e) {
            String msgError = "Error al gestionar Solicitud Traslado HHPP. " + e.getMessage();
            LOGGER.error(msgError, e);
            generateNotificationMsg(msgError,MessageSeverityEnum.ERROR);
            return false;
        }
    }

/* --------------------- Getters and Setters ------------------ */

    public List<CmtSolicitudHhppMgl> getCmtSolicitudHhppMglListToChanges() {
        return cmtSolicitudHhppMglListToChanges;
    }

    public void setCmtSolicitudHhppMglListToChanges(List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges) {
        this.cmtSolicitudHhppMglListToChanges = cmtSolicitudHhppMglListToChanges;
    }

    public List<HhppMgl> getListaHhppToChanges() {
        return listaHhppToChanges;
    }

    public void setListaHhppToChanges(List<HhppMgl> listaHhppToChanges) {
        this.listaHhppToChanges = listaHhppToChanges;
    }

    public ParametrosCalles getParametroIdNvl5() {
        return parametroIdNvl5;
    }

    public CmtSolicitudHhppMgl getCmtSolicitudHhppMgl() {
        return cmtSolicitudHhppMgl;
    }

    public void setCmtSolicitudHhppMgl(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) {
        this.cmtSolicitudHhppMgl = cmtSolicitudHhppMgl;
    }

    public void setParametroIdNvl5(ParametrosCalles parametroIdNvl5) {
        this.parametroIdNvl5 = parametroIdNvl5;
    }

    public ParametrosCalles getParametroIdNvl6() {
        return parametroIdNvl6;
    }

    public void setParametroIdNvl6(ParametrosCalles parametroIdNvl6) {
        this.parametroIdNvl6 = parametroIdNvl6;
    }

    public CmtSolicitudHhppMgl solicitudAgregarHhpp(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) {
        return cmtSolicitudHhppMgl;
    }

    public List<ParametrosCalles> getDirNivel5List() {
        return dirNivel5List;
    }

    public void setDirNivel5List(List<ParametrosCalles> dirNivel5List) {
        this.dirNivel5List = dirNivel5List;
    }

    public List<ParametrosCalles> getDirNivel6List() {
        return dirNivel6List;
    }

    public void setDirNivel6List(List<ParametrosCalles> dirNivel6List) {
        this.dirNivel6List = dirNivel6List;
    }

    public HhppMgl getSelectHhppMgl() {
        return selectHhppMgl;
    }

    public void setSelectHhppMgl(HhppMgl selectHhppMgl) {
        this.selectHhppMgl = selectHhppMgl;
        encabezadoSolicitudModificacionCMBean.setSelectHhppMgl(selectHhppMgl);
    }

    public List<HhppMgl> getHhppCmList() {
        return hhppCmList;
    }

    public void setHhppCmList(List<HhppMgl> hhppCmList) {
        this.hhppCmList = hhppCmList;
    }

    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public List<CmtBasicaMgl> getListBasicaTecnologia() {
        return listBasicaTecnologia;
    }

    public void setListBasicaTecnologia(List<CmtBasicaMgl> listBasicaTecnologia) {
        this.listBasicaTecnologia = listBasicaTecnologia;
    }

    public List<TipoHhppMgl> getListaTipoHhpp() {
        return listaTipoHhpp;
    }

    public void setListaTipoHhpp(List<TipoHhppMgl> listaTipoHhpp) {
        this.listaTipoHhpp = listaTipoHhpp;
    }

    public String getTipoHhpp() {
        return tipoHhpp;
    }

    public void setTipoHhpp(String tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
    }

}