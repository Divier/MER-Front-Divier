/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtFiltroConsultaBacklogsDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBacklogMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBacklogMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bocanegravm
 */
@ManagedBean(name = "cmtBlacklogMglBean")
@ViewScoped
public class CmtBacklogMglBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(CmtBacklogMglBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private int perfilVT = 0;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtBacklogMglFacadeLocal cmtBacklogMglFacadeLocal;
    private List<CmtBasicaMgl> tipoGestBackList;
    private List<CmtBasicaMgl> tipoResGestBackList;
    private CmtBacklogMgl cmtBacklogMgl;
    private CmtBasicaMgl tipoGestionBackSelected;
    private CmtBasicaMgl resGestionBackSelected;
    private List<CmtBacklogMgl> lstBacklogMgls;
    private OtMglBean otMglBean;
    private CmtOrdenTrabajoMgl ordenTrabajo;
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private CmtFiltroConsultaBacklogsDto cmtFiltroConsultaBacklogsDto;
    private static final int numeroRegistrosConsulta = 10;
    private int actual;
    private final String FORMULARIO = "OTTABBACKLOG";
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    private SecurityLogin securityLogin;
    private CmtSubEdificioMgl subEdificioMgl;
    private boolean mostrarPanelCrearBacklog;
    private boolean mostrarPanelListaBacklog;

    public CmtBacklogMglBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en BacklogBean class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en BacklogBean class: " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {

            this.otMglBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);
            ordenTrabajo = otMglBean.getOrdenTrabajo();
            cmtFiltroConsultaBacklogsDto = new CmtFiltroConsultaBacklogsDto();
            cmtBacklogMgl = new CmtBacklogMgl();
            tipoGestionBackSelected = new CmtBasicaMgl();
            resGestionBackSelected = new CmtBasicaMgl();

            CmtTipoBasicaMgl tipoGestBacklog;
            tipoGestBacklog = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_GESTION_BACKLOG);
            tipoGestBacklog = tipoBasicaMglFacadeLocal.findById(tipoGestBacklog);
            tipoGestBackList = basicaMglFacadeLocal.findByTipoBasica(tipoGestBacklog);

            CmtTipoBasicaMgl tipoResGestBack;
            tipoResGestBack = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_RESULTADO_GESTION_BACKLOG);
            tipoResGestBack = tipoBasicaMglFacadeLocal.findById(tipoResGestBack);
            tipoResGestBackList = basicaMglFacadeLocal.findByTipoBasica(tipoResGestBack);
            CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
            if (ordenTrabajo != null) {
                cmtCuentaMatrizMgl = ordenTrabajo.getCmObj();
                subEdificioMgl = cmtCuentaMatrizMgl.getSubEdificioGeneral();
            }
            mostrarPanelCrearBacklog = false;
            mostrarPanelListaBacklog = true;

            if (ordenTrabajo != null && ordenTrabajo.getIdOt() != null) {
                listInfoByPage(1);
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en BacklogBean class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error inicializando en CmtBacklogMglBean: init() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para crear backlog en la orden de trabajo de la CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrear() {
        try {
            return ValidacionUtil.validarVisualizacion("TABBACKLOGOTCCMM",
                    ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de creación de backlog en OT CCMM. " , e);
        }

        return false;
    }

    public void guardarBacklog() {
        try {

            cmtBacklogMgl.setTipoGesBac(tipoGestionBackSelected);
            cmtBacklogMgl.setResultadoGesBac(resGestionBackSelected);
            cmtBacklogMgl.setOrdenTrabajoMglObj(ordenTrabajo);
            cmtBacklogMglFacadeLocal.create(cmtBacklogMgl, usuarioVT, perfilVT);

            if (cmtBacklogMgl.getIdBacklog() != null) {
                String msn = "Proceso exitoso:" + " " + "Se ha creado el Backlog  "
                        + " <b>" + cmtBacklogMgl.getIdBacklog() + "</b>  para la OT "
                        + " <b>" + ordenTrabajo.getIdOt() + "</b>  ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                msn, ""));
                init();
                LOGGER.info(msn);

                mostrarPanelCrearBacklog = false;
                mostrarPanelListaBacklog = true;
            } else {
                String msn = "Proceso fallo: Ocurrio un error al momento de crear "
                        + " el registro  ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msn, ""));
                LOGGER.info(msn);

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en BacklogBean class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error guardando backlog en  CmtBacklogMglBean: guardarBacklog() " + e.getMessage(), e, LOGGER);
        }
    }

    public void eliminarBacklog(BigDecimal idReg) {
        boolean respuesta;

        try {

            cmtBacklogMgl = cmtBacklogMglFacadeLocal.findByIdBacklog(idReg);
            if (cmtBacklogMgl != null) {
                respuesta = cmtBacklogMglFacadeLocal.delete(cmtBacklogMgl, usuarioVT, perfilVT);
                if (respuesta) {
                    String msn = "Proceso Exitoso: se ha eliminado el backlog   "
                            + "<b>" + cmtBacklogMgl.getIdBacklog() + "</b>  "
                            + "asociado a la OT  <b>" + ordenTrabajo.getIdOt() + "</b>";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    LOGGER.info(msn);
                    init();
                } else {
                    String msn = "Proceso falló: ha ocurrido un problema "
                            + " al momento de eliminar el registro ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    LOGGER.info(msn);
                }

            } else {
                String msn = "Proceso falló: no existe el registro en BD ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                LOGGER.info(msn);

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Proceso falló: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error eliminando backlog en  CmtBacklogMglBean: eliminarBacklog() " + e.getMessage(), e, LOGGER);
        }
    }

    public String validacionesObligatoriedad() {
        try {

            if (tipoGestionBackSelected.getBasicaId() == null) {
                String msn = "Campo Tipo Gestión Backlog: Valor es necesario";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                LOGGER.info(msn);
                return null;
            } else if (resGestionBackSelected.getBasicaId() == null) {
                String msn = "Campo Resultado Backlog: Valor es necesario";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                LOGGER.info(msn);
                return null;
            } else if (cmtBacklogMgl.getNota() == null || cmtBacklogMgl.getNota().isEmpty()) {
                String msn = "Campo Nota: Valor es requerido";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                LOGGER.info(msn);
                return null;
            } else if (cmtBacklogMgl.getNota().length() > 500) {
                String msn = "La cantidad de caracteres no debe ser mayor a 500 ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                LOGGER.info(msn);
                return null;
            } else {

                guardarBacklog();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error validando obligatoriedad en CmtBacklogMglBean: validacionesObligatoriedad() " + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public String listInfoByPage(int page) {

        try {

            PaginacionDto<CmtBacklogMgl> paginacionDto = cmtBacklogMglFacadeLocal.findAllPaginado(page, numeroRegistrosConsulta, cmtFiltroConsultaBacklogsDto, ordenTrabajo);

            lstBacklogMgls = paginacionDto.getListResultado();
            actual = page;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Proceso falló: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error llenando lista en CmtBacklogMglBean: validacionesObligatoriedad() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void findByFiltro() {
        pageFirst();
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            String msn = "Ocurrió un error en paginacion en CmtBacklogMglBean: pageFirst() " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
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
            String msn = "Ocurrió un error en paginacion en CmtBacklogMglBean: pagePrevious() " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
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
            FacesUtil.mostrarMensajeError("Se genera error al cargar lista de Backlogs:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en paginacion en CmtBacklogMglBean: irPagina() " + e.getMessage(), e, LOGGER);
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
            String msn = "Ocurrió un error en paginacion en CmtBacklogMglBean: pageNext() " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            String msn = "Ocurrió un error en paginacion en CmtBacklogMglBean: pageLast() " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }

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
            FacesUtil.mostrarMensajeError("Ocurrió un error en paginacion en CmtBacklogMglBean: getPageActual() " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public int getTotalPaginas() {
        try {
            PaginacionDto<CmtBacklogMgl> paginacionDto = cmtBacklogMglFacadeLocal.findAllPaginado(0, numeroRegistrosConsulta, cmtFiltroConsultaBacklogsDto, ordenTrabajo);
            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsulta != 0)
                    ? (count / numeroRegistrosConsulta) + 1
                    : count / numeroRegistrosConsulta);
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al cargar lista de Backlogs:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en paginacion en CmtBacklogMglBean: getTotalPaginas() " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public void volver() {

        mostrarPanelCrearBacklog = false;
        mostrarPanelListaBacklog = true;

    }

    public void nuevoBacklogCrear() throws ApplicationException {
        try {
            if (ordenTrabajo.getIdOt() != null) {
                if (otMglBean.tieneAgendasPendientes(ordenTrabajo)) {
                    mostrarPanelCrearBacklog = true;
                    mostrarPanelListaBacklog = false;
                } else {
                    String msg = "La orden de trabajo No: " + ordenTrabajo.getIdOt() + " tiene agendas pendientes por cerrar";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                }
            } else {
                String msg = "Se requiere crear una Ot previamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msg, ""));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error creando nuevo backlog en CmtBacklogMglBean: nuevoBacklogCrear() " + e.getMessage(), e, LOGGER);
        }

    }

    public boolean validarCreacion() {
        return validarPermisos(ValidacionUtil.OPC_CREACION);
    }

    public boolean validarBorrado() {
        return validarPermisos(ValidacionUtil.OPC_BORRADO);
    }

    private boolean validarPermisos(String accion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, accion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error validando permisos en CmtBacklogMglBean: validarPermisos() " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<CmtBasicaMgl> getTipoGestBackList() {
        return tipoGestBackList;
    }

    public void setTipoGestBackList(List<CmtBasicaMgl> tipoGestBackList) {
        this.tipoGestBackList = tipoGestBackList;
    }

    public List<CmtBasicaMgl> getTipoResGestBackList() {
        return tipoResGestBackList;
    }

    public void setTipoResGestBackList(List<CmtBasicaMgl> tipoResGestBackList) {
        this.tipoResGestBackList = tipoResGestBackList;
    }

    public CmtBacklogMgl getCmtBacklogMgl() {
        return cmtBacklogMgl;
    }

    public void setCmtBacklogMgl(CmtBacklogMgl cmtBacklogMgl) {
        this.cmtBacklogMgl = cmtBacklogMgl;
    }

    public List<CmtBacklogMgl> getLstBacklogMgls() {
        return lstBacklogMgls;
    }

    public void setLstBacklogMgls(List<CmtBacklogMgl> lstBacklogMgls) {
        this.lstBacklogMgls = lstBacklogMgls;
    }

    public CmtBasicaMgl getTipoGestionBackSelected() {
        return tipoGestionBackSelected;
    }

    public void setTipoGestionBackSelected(CmtBasicaMgl tipoGestionBackSelected) {
        this.tipoGestionBackSelected = tipoGestionBackSelected;
    }

    public CmtBasicaMgl getResGestionBackSelected() {
        return resGestionBackSelected;
    }

    public void setResGestionBackSelected(CmtBasicaMgl resGestionBackSelected) {
        this.resGestionBackSelected = resGestionBackSelected;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
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

    public CmtFiltroConsultaBacklogsDto getCmtFiltroConsultaBacklogsDto() {
        return cmtFiltroConsultaBacklogsDto;
    }

    public void setCmtFiltroConsultaBacklogsDto(CmtFiltroConsultaBacklogsDto cmtFiltroConsultaBacklogsDto) {
        this.cmtFiltroConsultaBacklogsDto = cmtFiltroConsultaBacklogsDto;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public CmtSubEdificioMgl getSubEdificioMgl() {
        return subEdificioMgl;
    }

    public void setSubEdificioMgl(CmtSubEdificioMgl subEdificioMgl) {
        this.subEdificioMgl = subEdificioMgl;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public boolean isMostrarPanelCrearBacklog() {
        return mostrarPanelCrearBacklog;
    }

    public void setMostrarPanelCrearBacklog(boolean mostrarPanelCrearBacklog) {
        this.mostrarPanelCrearBacklog = mostrarPanelCrearBacklog;
    }

    public boolean isMostrarPanelListaBacklog() {
        return mostrarPanelListaBacklog;
    }

    public void setMostrarPanelListaBacklog(boolean mostrarPanelListaBacklog) {
        this.mostrarPanelListaBacklog = mostrarPanelListaBacklog;
    }

}
