/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.OrdenesTrabajoSolicitudesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoIntxExtMglFacaceLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudCmMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.cm.menu.MenuCmBean;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author ADMIN
 */
@ManagedBean(name = "listOrdenTrabajoCm")
@ViewScoped
public class ListOrdenTrabajoCm implements Serializable {

    private static final String TABORDENCCMMSOLICITUD = "TABORDENCCMMSOLICITUD";
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal cmtOrdenTrabajoMglFacade;
    @EJB
    private CmtSolicitudCmMglFacadeLocal solicitudCmMglFacadeLocal;
    @EJB
    private CmtEstadoIntxExtMglFacaceLocal cmtEstadoIntxExtMglFacaceLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
        
    private static final long serialVersionUID = 1L;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ListOrdenTrabajoCm.class);
    private final String FORMULARIO_CREAR_OT = "OT";
    private final String FORMULARIO_GENERAL = "OTTABGENERAL";
    private final String FORMULARIO_BACK_LOG = "OTTABGENERALBACKLOG";

    private CuentaMatrizBean cuentaMatrizBean;
    private CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
    private String pageActual;
    private String numPagina;
    private List<Integer> paginaList;
    private int actual;
    private String tipoSolicitudCmSeleccionada;
    private List<CmtOrdenTrabajoMgl> listWorkOrders;
    private List<CmtSolicitudCmMgl> listSolicitudes;
    
    private List<OrdenesTrabajoSolicitudesDto> listaAll;
    private CmtOrdenTrabajoMgl orderTrabajoSelected;
    private CmtSolicitudCmMgl solicitudSelected;
    private OrdenesTrabajoSolicitudesDto allSeleccion;

    private boolean renderAuditoria;
    private boolean renderDetalle;
    private int tipoVerTabla;
    private CmtSubEdificioMgl selectedCmtSubEdificioMgl;
    private List<AuditoriaDto> listAuditoria;
    private CmtEstadoIntxExtMgl cmtEstadoIntxExtMgl;
    private boolean renderDetOrden;
    private boolean renderDetGeneral;
    private boolean renderDetBackLog;
    private boolean tabDetSelected;
    private String space = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private SecurityLogin securityLogin;
    private String paginaSelected;
    private String usuarioSesion;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    
    public ListOrdenTrabajoCm() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cmtCuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
            selectedCmtSubEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            usuarioSesion = securityLogin.getLoginUser();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void fillList() {
        tipoVerTabla = 0;
        listInfoByPage(1);
        renderAuditoria = false;
        renderDetalle = false;
        renderDetOrden = false;
        renderDetGeneral = true;
        renderDetBackLog = false;
        tabDetSelected = true;
    }

    /**
     * Verifica si tiene rol para visualizar el botón de nuevo tipo solicitud
     * @return {@code boolean}
     * @author Gildardo Mora
     */
    public boolean isRolNuevoTipoSolicitudOt() {
        try {
            return ValidacionUtil.validarVisualizacion(TABORDENCCMMSOLICITUD,
                    "VER", cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación. ");
        }
        return false;
    }

    /**
     * Verifica si tiene rol para visualizar el botón de nueva solicitud
     * @return {@code boolean}
     * @author Gildardo Mora
     */
    public boolean isRolNuevaSolicitudOt() {
        try {
            return ValidacionUtil.validarVisualizacion(TABORDENCCMMSOLICITUD,
                    ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación de nueva solicitud Ot ");
        }
        return false;
    }

    public String getEstadoExterno(CmtOrdenTrabajoMgl ordenTrabajo) {
        String estadoExternoStr = "";
        try {
            CmtEstadoIntxExtMgl estadoIntxExtMgl = cmtEstadoIntxExtMglFacaceLocal.findByEstadoInterno(ordenTrabajo.getEstadoInternoObj());
            if (estadoIntxExtMgl != null
                    && estadoIntxExtMgl.getIdEstadoExt() != null) {
                estadoExternoStr = estadoIntxExtMgl.getIdEstadoExt().getNombreBasica();
            }
        } catch (ApplicationException e) {
           FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }
        return estadoExternoStr;
    }

    public void listInfoByPage(int page) {
        try {
            this.cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cmtCuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
            selectedCmtSubEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            if (selectedCmtSubEdificioMgl != null) {
                if (tipoVerTabla == 1 || tipoVerTabla == 0){
                    if (!selectedCmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().equals(
                            cmtBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) 
                          ) {
                        if (cmtSubEdificioMglFacadeLocal.countSubEdificiosCuentaMatriz(selectedCmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId()).intValue() > 1) {
                            listWorkOrders = cmtOrdenTrabajoMglFacade.findPaginacionSub(selectedCmtSubEdificioMgl);
                            List<CmtOrdenTrabajoMgl> listWorkOrdersAcometida = cmtOrdenTrabajoMglFacade.findOtAcometidaSubEdificio(selectedCmtSubEdificioMgl);
                            if (listWorkOrders != null) {
                                if(listWorkOrdersAcometida != null && !listWorkOrdersAcometida.isEmpty()){
                                listWorkOrders.addAll(listWorkOrdersAcometida);
                                }
                            } else {
                                listWorkOrders = new ArrayList();
                                if (listWorkOrdersAcometida != null && !listWorkOrdersAcometida.isEmpty()) {
                                    listWorkOrders.addAll(listWorkOrdersAcometida);
                                }
                            }
                        } else {
                            listWorkOrders = cmtOrdenTrabajoMglFacade.findPaginacion(cmtCuentaMatrizMgl);
                        }
                    } else {//cuando no es multiedificio
                        listWorkOrders = cmtOrdenTrabajoMglFacade.findPaginacion( cmtCuentaMatrizMgl );
                    }
                }
                if (tipoVerTabla == 2 || tipoVerTabla == 0){
                    listSolicitudes = solicitudCmMglFacadeLocal.findByCuentaMatrizPaginado( cmtCuentaMatrizMgl );
                }
                if (tipoVerTabla == 0){
                    listaAll = new ArrayList<OrdenesTrabajoSolicitudesDto>();
                    for(CmtOrdenTrabajoMgl tempOrden :listWorkOrders){
                        OrdenesTrabajoSolicitudesDto nuevo = new OrdenesTrabajoSolicitudesDto();
                        nuevo.setAnsSol("");//no muestra este campo la orden
                        nuevo.setEstadoInternoOtSol(tempOrden.getEstadoInternoObj().getNombreBasica());
                        nuevo.setEstadoOT(getEstadoExterno(tempOrden));
                        nuevo.setFechaCreacion(tempOrden.getFechaCreacion());
                        nuevo.setFechaEdicion(tempOrden.getFechaEdicion());
                        nuevo.setLog("N/A");//no muestra este campo la orden
                        nuevo.setNumeroOtSol(tempOrden.getIdOt());
                        nuevo.setOrdSol("Orden");
                        nuevo.setTecnologiaOT(tempOrden.getBasicaIdTecnologia().getNombreBasica());
                        nuevo.setTipoOtSol(tempOrden.getTipoOtObj().getDescTipoOt());
                        nuevo.setUsuario(tempOrden.getUsuarioCreacion());
                        nuevo.setOrdenObj(tempOrden);
                        listaAll.add(nuevo);
                    }
                    for(CmtSolicitudCmMgl tempSolicitudes :listSolicitudes){
                        OrdenesTrabajoSolicitudesDto nuevo = new OrdenesTrabajoSolicitudesDto();
                        nuevo.setAnsSol(tempSolicitudes.getAnsSolicitud());
                        nuevo.setEstadoInternoOtSol(tempSolicitudes.getEstadoSolicitudObj().getNombreBasica());
                        nuevo.setEstadoOT("N/A");//no muestra este campo la solicitud
                        nuevo.setFechaCreacion(tempSolicitudes.getFechaCreacion());
                        nuevo.setFechaEdicion(null);
                        nuevo.setLog("Log");
                        nuevo.setNumeroOtSol(tempSolicitudes.getSolicitudCmId());
                        nuevo.setOrdSol("Solicitud");
                        nuevo.setTecnologiaOT("N/A");
                        nuevo.setTipoOtSol(tempSolicitudes.getTipoSolicitudObj().getNombreTipo());
                        nuevo.setUsuario(tempSolicitudes.getUsuarioCreacion());
                        nuevo.setSolicitudObj(tempSolicitudes);
                        listaAll.add(nuevo);
                    }
                }
            }
            actual = page;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
    public String goGestionAll(OrdenesTrabajoSolicitudesDto seleccionado) throws ApplicationException {
        if (seleccionado.getOrdSol().trim().compareToIgnoreCase("Orden") == 0) {
            OtMglBean otMglBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);

            CmtOrdenTrabajoMgl orden = cmtOrdenTrabajoMglFacade.findOtById(seleccionado.getOrdenObj().getIdOt());
            
            if (orden != null) {
                boolean disponible = orden.getDisponibilidadGestion() == null;

                if (disponible) {
                    return otMglBean.ingresarGestion(orden);
                } else {
                    if (usuarioSesion.equalsIgnoreCase(orden.getDisponibilidadGestion())) {
                        return otMglBean.ingresarGestion(orden);
                    } else {
                        String telefono = "";
                        String correo = "";
                        try {
                            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(orden.getDisponibilidadGestion());
                            if (usuarioLogin != null && usuarioLogin.getDescripcionArea() != null) {
                                telefono = usuarioLogin.getTelefono();
                                correo = usuarioLogin.getEmail();
                            }
                        } catch (ApplicationException e) {
                            FacesUtil.mostrarMensajeWarn("No se pudo obtener datos del usuario " + orden.getDisponibilidadGestion());
                        }
                        
                        String msnErr = "La orden " + orden.getIdOt() + " se encuentra en proceso de Gestión por el usuario: "
                            + orden.getDisponibilidadGestion() + (telefono.isEmpty() ? "" : ", teléfono: " + telefono) + (correo.isEmpty() ? "" : ", correo: " + correo);
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "En Proceso", msnErr);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msnErr, ""));
                    }
                }
            }
        }
        
        if (seleccionado.getOrdSol().trim().compareToIgnoreCase("Solicitud") == 0) {
            return verDetallesSolicitud(seleccionado.getSolicitudObj());
        }
        return null;
    }
    
        /**
     * Redirecciona al tipo de solicitud de CM seleccionada
     *
     * @author Juan David Hernandez
     * @return 
     */
    public String irMenuNuevaSolCM() {
        try {
            if (tipoSolicitudCmSeleccionada != null && !tipoSolicitudCmSeleccionada.isEmpty()) {
                MenuCmBean menuCmBean = (MenuCmBean) JSFUtil.getBean("menuCmBean");
                return menuCmBean.irMenu(tipoSolicitudCmSeleccionada);
            } else {
               String msnError = "Debe seleccionar el tipo de solicitud que desea crear";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
            }

        } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void itemSelected(CmtOrdenTrabajoMgl item) {
        orderTrabajoSelected = item;
    }

    public CmtCuentaMatrizMgl getCmtCuentaMatrizMgl() {
        return cmtCuentaMatrizMgl;
    }

    public void setCmtCuentaMatrizMgl(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) {
        this.cmtCuentaMatrizMgl = cmtCuentaMatrizMgl;
    }

    public String mostrarAuditoriaOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajo) {
        renderAuditoria = true;
        renderDetalle = false;
        try {
            listAuditoria = cmtOrdenTrabajoMglFacade.construirAuditoria(ordenTrabajo);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String mostrarAuditoriaSolicitud(CmtSolicitudCmMgl solicitudCmMgl) {
        renderAuditoria = true;
        renderDetalle = false;
        try {
            listAuditoria = solicitudCmMglFacadeLocal.construirAuditoria(solicitudCmMgl);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
           FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }

        return null;
    }

    public String verDetalleOt(CmtOrdenTrabajoMgl ordenTrabajo) {
        renderDetalle = true;
        renderAuditoria = false;
        orderTrabajoSelected = ordenTrabajo;
        renderDetOrden = true;
        try {
            cmtEstadoIntxExtMgl = cmtEstadoIntxExtMglFacaceLocal.findByEstadoInterno(ordenTrabajo.getEstadoInternoObj());
        } catch (ApplicationException e) {
            String msn2 = "Error buscar el estado externo ";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String verDetallesSolicitud(CmtSolicitudCmMgl solicitudCmMgl) {
        try {
            solicitudSelected = solicitudCmMgl;
            CmtConsultarEstadoSolBean consultarEstadoSolBean = new CmtConsultarEstadoSolBean();

            return consultarEstadoSolBean.findBySolicitud(solicitudCmMgl);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    public int asignarFormulario(String abreviatura) {
        try {

            if (ConstantsCM.TIPO_SOLICITUD_CREACION_CM.equals(abreviatura)) {
                return 1;
            } else if (ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM.equals(abreviatura)) {
                return 2;
            } else if (ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA.equals(abreviatura)) {
                return 3;
            } else if (ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP.equals(abreviatura)) {
                return 4;
            } else if (ConstantsCM.TIPO_SOLICITUD_CREACION_HHPP.equals(abreviatura)) {
                return 5;
            } else if (ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM.equals(abreviatura)) {
                return 6;
            }

        } catch (Exception ex) {
            LOGGER.error("Error al momento de asignar el formulario. EX000: " + ex.getMessage(), ex);
        }
        return 0;
    }

    public String verDetalleGeneral() {
        renderDetGeneral = true;
        renderDetBackLog = false;
        return null;
    }

    public String verDetalleBackLog() {
        renderDetBackLog = true;
        renderDetGeneral = false;
        return null;
    }

    public void listTipoSolChange() {
        listInfoByPage(1);
    }

    public String volver() {
        renderDetalle = false;
        renderAuditoria = false;
        return null;
    }

    /**
     *
     * @return
     */
    public boolean validarCreacionOT() {
        return validarAccion(FORMULARIO_CREAR_OT);
    }

    /**
     *
     * @return
     */
    public boolean validarGeneral() {
        return validarAccion(FORMULARIO_GENERAL);
    }

    /**
     *
     * @return
     */
    public boolean validarBalcList() {
        return validarAccion(FORMULARIO_BACK_LOG);
    }

    /**
     * M&eacute;todo para validar habilitar acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(
                    formulario, ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ListOrdenTrabajoCm class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public boolean isTabDetSelected() {
        return tabDetSelected;
    }

    public void setTabDetSelected(boolean tabDetSelected) {
        this.tabDetSelected = tabDetSelected;
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

    public List<CmtOrdenTrabajoMgl> getListWorkOrders() {
        return listWorkOrders;
    }

    public void setListWorkOrders(List<CmtOrdenTrabajoMgl> listWorkOrders) {
        this.listWorkOrders = listWorkOrders;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public boolean isRenderDetalle() {
        return renderDetalle;
    }

    public void setRenderDetalle(boolean renderDetalle) {
        this.renderDetalle = renderDetalle;
    }

    public CmtOrdenTrabajoMgl getOrderTrabajoSelected() {
        return orderTrabajoSelected;
    }

    public void setOrderTrabajoSelected(CmtOrdenTrabajoMgl orderTrabajoSelected) {
        this.orderTrabajoSelected = orderTrabajoSelected;
    }

    public int getTipoVerTabla() {
        return tipoVerTabla;
    }

    public void setTipoVerTabla(int tipoVerTabla) {
        this.tipoVerTabla = tipoVerTabla;
    }

    public List<CmtSolicitudCmMgl> getListSolicitudes() {
        return listSolicitudes;
    }

    public void setListSolicitudes(List<CmtSolicitudCmMgl> listSolicitudes) {
        this.listSolicitudes = listSolicitudes;
    }

    public CmtSolicitudCmMgl getSolicitudSelected() {
        return solicitudSelected;
    }

    public void setSolicitudSelected(CmtSolicitudCmMgl solicitudSelected) {
        this.solicitudSelected = solicitudSelected;
    }

    public List<AuditoriaDto> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<AuditoriaDto> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public CmtEstadoIntxExtMgl getCmtEstadoIntxExtMgl() {
        return cmtEstadoIntxExtMgl;
    }

    public void setCmtEstadoIntxExtMgl(CmtEstadoIntxExtMgl cmtEstadoIntxExtMgl) {
        this.cmtEstadoIntxExtMgl = cmtEstadoIntxExtMgl;
    }

    public int getFilasPag() {
        return ConstantsCM.PAGINACION_SEIS_FILAS;
    }

    public boolean isRenderDetOrden() {
        return renderDetOrden;
    }

    public void setRenderDetOrden(boolean renderDetOrden) {
        this.renderDetOrden = renderDetOrden;
    }

    public boolean isRenderDetGeneral() {
        return renderDetGeneral;
    }

    public void setRenderDetGeneral(boolean renderDetGeneral) {
        this.renderDetGeneral = renderDetGeneral;
    }

    public boolean isRenderDetBackLog() {
        return renderDetBackLog;
    }

    public void setRenderDetBackLog(boolean renderDetBackLog) {
        this.renderDetBackLog = renderDetBackLog;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public CmtSubEdificioMgl getSelectedCmtSubEdificioMgl() {
        return selectedCmtSubEdificioMgl;
    }

    public void setSelectedCmtSubEdificioMgl(CmtSubEdificioMgl selectedCmtSubEdificioMgl) {
        this.selectedCmtSubEdificioMgl = selectedCmtSubEdificioMgl;
    }

    public String getPaginaSelected() {
        return paginaSelected;
    }

    public void setPaginaSelected(String paginaSelected) {
        this.paginaSelected = paginaSelected;
    }

    public String getTipoSolicitudCmSeleccionada() {
        return tipoSolicitudCmSeleccionada;
    }

    public void setTipoSolicitudCmSeleccionada(String tipoSolicitudCmSeleccionada) {
        this.tipoSolicitudCmSeleccionada = tipoSolicitudCmSeleccionada;
    }

    public List<OrdenesTrabajoSolicitudesDto> getListaAll() {
        return listaAll;
    }

    public void setListaAll(List<OrdenesTrabajoSolicitudesDto> listaAll) {
        this.listaAll = listaAll;
    }

    public OrdenesTrabajoSolicitudesDto getAllSeleccion() {
        return allSeleccion;
    }

    public void setAllSeleccion(OrdenesTrabajoSolicitudesDto allSeleccion) {
        this.allSeleccion = allSeleccion;
    }
    
    
}