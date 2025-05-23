package co.com.claro.mgl.mbeans.cm.ot;

import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoInventarioMglManager;
import co.com.claro.mgl.dtos.CmtResutadoPasaInventariosDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtInventarioTecnologiaFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoInventarioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificiosVtFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtVisitaTecnicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoInventarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Victor Bocanegra
 */
@ManagedBean(name = "CmtInventariosOtMglBean")
@ViewScoped
public class CmtInventariosOtMglBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(CmtInventariosOtMglBean.class);
    private String usuarioVT = null;
    private int perfilVT = 0;
    private SecurityLogin securityLogin;
    private OtMglBean otMglBean;
    private CmtOrdenTrabajoMgl ordenTrabajo;
    private CmtOrdenTrabajoInventarioMgl ordenTrabajoInventarioMgl;
    private String tipoInvTecSelected;
    private List<CmtBasicaMgl> tipoInvTecList;
    private String claseInvTecSelected;
    private List<CmtBasicaMgl> claseInvTecList;
    private String fabInvTecSelected;
    private List<CmtBasicaMgl> fabInvTecList;
    private BigDecimal subEdificioMglSelected;
    private List<CmtInventariosTecnologiaMgl> lsCmtInventariosTecnologiaMgls;
    private List<CmtOrdenTrabajoInventarioMgl> lsCmtOrdenTrabajoInventarioMgls;
    private CmtVisitaTecnicaMgl cmtVisitaTecnicaMgls;
    private List<CmtSubEdificiosVt> lsCmtSubEdificiosVts;
    private boolean inhabilitarFormulario;
    @EJB
    private CmtVisitaTecnicaMglFacadeLocal cmtVisitaTecnicaMglFacadeLocal;
    @EJB
    private CmtSubEdificiosVtFacadeLocal cmtSubEdificiosVtFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoInventarioMglFacadeLocal cmtOrdenTrabajoInventarioMglFacadeLocal;
    @EJB
    private CmtInventarioTecnologiaFacadeLocal cmtInventarioTecnologiaFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    
    private boolean mostrarPanelCrearInvOt;
    private boolean mostrarPanelListaInvOt;
    
    private int actualNot;
    private String numPaginaNot = "1";
    private List<Integer> paginaListNot;
    private String pageActualNot;
    private int filasPagNot = ConstantsCM.PAGINACION_CUATRO_FILAS;
    private List<CmtOrdenTrabajoInventarioMgl> lsCmtOrdenTrabajoInventarioMglsAux; 
    
    //

    public CmtInventariosOtMglBean() {
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
        } catch (IOException ex) {
            LOGGER.error("Se generea error en ValidarDirUnoAUnoMBean class ...", ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en CmtInventariosOtMglBean " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            this.otMglBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);
            ordenTrabajo = otMglBean.getOrdenTrabajo();
            ordenTrabajoInventarioMgl = new CmtOrdenTrabajoInventarioMgl();
            cmtVisitaTecnicaMgls = cmtVisitaTecnicaMglFacadeLocal.findVTActiveByIdOt(ordenTrabajo);
            mostrarPanelListaInvOt= true;
            mostrarPanelCrearInvOt=false;
            
            if (cmtVisitaTecnicaMgls != null) {

                lsCmtSubEdificiosVts = new ArrayList<CmtSubEdificiosVt>();
                lsCmtSubEdificiosVts = cmtSubEdificiosVtFacadeLocal.findByVt(cmtVisitaTecnicaMgls);
                CmtTipoBasicaMgl tipoInvTec;
                tipoInvTec = cmtTipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_INVENTARIO_TECNOLOGIA);
                tipoInvTec = tipoBasicaMglFacadeLocal.findById(tipoInvTec);
                tipoInvTecList = basicaMglFacadeLocal.findByTipoBasica(tipoInvTec);

                CmtTipoBasicaMgl claseInvTec;
                claseInvTec = cmtTipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_CLASE_INVENTARIO_TECNOLOGIA);
                claseInvTec = tipoBasicaMglFacadeLocal.findById(claseInvTec);
                claseInvTecList = basicaMglFacadeLocal.findByTipoBasica(claseInvTec);

                CmtTipoBasicaMgl fabInvTec;
                fabInvTec = cmtTipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_FABRICANTES_INVENTARIO_TECNOLOGIA);
                fabInvTec = tipoBasicaMglFacadeLocal.findById(fabInvTec);
                fabInvTecList = basicaMglFacadeLocal.findByTipoBasica(fabInvTec);

                inhabilitarFormulario = false;
                cargarInfo();
            } else {
                inhabilitarFormulario = false;
                String msn = "Debe Crear al menos una VT con Subedificios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                LOGGER.error(msn);
            }
        } catch (ApplicationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            LOGGER.error("Error al momento de cargar las listas. EX000: " + ex.getMessage(), ex);
       } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en CmtInventariosOtMglBean: cargarListas() " + e.getMessage(), e, LOGGER);
        }
    }

    private String cargarInfo() {
        if (otMglBean.getOrdenTrabajo() != null) {
            ordenTrabajo = otMglBean.getOrdenTrabajo();
            try {
                lsCmtOrdenTrabajoInventarioMglsAux = cmtOrdenTrabajoInventarioMglFacadeLocal.cargarInfo(ordenTrabajo, usuarioVT, perfilVT);
                listInfoByPageNot(1);
            } catch (ApplicationException ex) {
                inhabilitarFormulario = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        ConstantsCM.MSN_ERROR_PROCESO + ex.getMessage(), ""));
                LOGGER.error("Error al momento de cargar la información. EX000: " + ex.getMessage(), ex);
            }
        }
        return "";
    }

    public String crearInventarioTecOT() {
        try {
            ordenTrabajoInventarioMgl.setTipoInventario(tipoInvTecSelected);
            ordenTrabajoInventarioMgl.setClaseInventario(claseInvTecSelected);
            ordenTrabajoInventarioMgl.setFabricante(fabInvTecSelected);
            ordenTrabajoInventarioMgl.setCmtOrdenTrabajoMglObj(ordenTrabajo);
            CmtSubEdificiosVt cmtSubEdificiosVt = cmtSubEdificiosVtFacadeLocal.findById(subEdificioMglSelected);
            ordenTrabajoInventarioMgl.setCmtSubEdificiosVtObj(cmtSubEdificiosVt);
            ordenTrabajoInventarioMgl.setVtId(cmtSubEdificiosVt.getVtObj().getIdVt().longValue());
            ordenTrabajoInventarioMgl.setSistemaInventarioId("NEC0001");
            ordenTrabajoInventarioMgl = cmtOrdenTrabajoInventarioMglFacadeLocal
                    .crearOtInvForm(ordenTrabajoInventarioMgl, usuarioVT, perfilVT);

            if (ordenTrabajoInventarioMgl.getOrdentrabajoInventarioId() != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        ConstantsCM.MSN_PROCESO_EXITOSO + ", Se ha creado el inventario de tecnologia: <b>"
                        + ordenTrabajoInventarioMgl.getOrdentrabajoInventarioId().toString() + "</b>", ""));
                limpiarForm();
                mostrarPanelListaInvOt=true;
                mostrarPanelCrearInvOt=false;
                listInfoByPageNot(1);
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de crear el inventario. EX000: " + e.getMessage(), e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    ConstantsCM.MSN_ERROR_PROCESO + e.getMessage(), ""));
          } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en CmtInventariosOtMglBean: crearInventarioTecOT() " + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public void limpiarForm() {
        ordenTrabajoInventarioMgl = new CmtOrdenTrabajoInventarioMgl();
        fabInvTecSelected = "";
        claseInvTecSelected = "";
        tipoInvTecSelected = "";
        subEdificioMglSelected = null;
        cargarInfo();
    }

    public String traerNombreSubedificio(BigDecimal subId) {
        String nombreSub = "";
        try {
            CmtSubEdificioMgl cmtSubEdificioMgl = new CmtSubEdificioMgl();
            cmtSubEdificioMgl.setSubEdificioId(subId);
            cmtSubEdificioMgl = cmtSubEdificioMglFacadeLocal.findById(cmtSubEdificioMgl);
            nombreSub = "[" + subId.toString() + "]" + " " + cmtSubEdificioMgl.getNombreSubedificio();
        } catch (ApplicationException ex) {
            String msg = ConstantsCM.MSN_PROCESO_FALLO + ex.getMessage();
            FacesUtil.mostrarMensajeError(msg, ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de traer el nombre del subedificio: " + e.getMessage(), e, LOGGER);
        }
        return nombreSub;
    }

    public String deshacer(char action, Long idReg) {
        String nombreSub = "";
        try {
            BigDecimal idInv = new BigDecimal(idReg);
            if (action == '2') {
                char actionNew = '3';
                CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl = cmtOrdenTrabajoInventarioMglFacadeLocal.findByIdOtInvTec(idInv);
                if (cmtOrdenTrabajoInventarioMgl != null) {
                    cmtOrdenTrabajoInventarioMglFacadeLocal.updateOtInv(actionNew, cmtOrdenTrabajoInventarioMgl, usuarioVT, perfilVT);
                    cargarInfo();
                }
            } else {
                CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl = cmtOrdenTrabajoInventarioMglFacadeLocal.findByIdOtInvTec(idInv);
                if (cmtOrdenTrabajoInventarioMgl != null) {
                    cmtOrdenTrabajoInventarioMglFacadeLocal.deleteOtInv(cmtOrdenTrabajoInventarioMgl, usuarioVT, perfilVT);
                    cargarInfo();
                }
            }
        } catch (ApplicationException ex) {
            String msg = ConstantsCM.MSN_PROCESO_FALLO + ex.getMessage();
            FacesUtil.mostrarMensajeError(msg, ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de deshacer los cambios: " + e.getMessage(), e, LOGGER);
        }
        return nombreSub;
    }

    public String cambiarEstadoDelete(Long idReg) {
        try {
            BigDecimal idInv = new BigDecimal(idReg);
            CmtOrdenTrabajoInventarioMglManager cmtOrdenTrabajoInventarioMglManager = new CmtOrdenTrabajoInventarioMglManager();
            CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl = cmtOrdenTrabajoInventarioMglManager.findByIdOtInvTec(idInv);
            if (cmtOrdenTrabajoInventarioMgl != null) {
                char Del = '2';
                cmtOrdenTrabajoInventarioMglFacadeLocal.updateOtInv(Del, cmtOrdenTrabajoInventarioMgl, usuarioVT, perfilVT);
                cargarInfo();
            }
        } catch (ApplicationException ex) {
            String msg = ConstantsCM.MSN_PROCESO_FALLO + ex.getMessage();
            FacesUtil.mostrarMensajeError(msg, ex, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de cambiar el estado a eliminado: " + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public String actualizarInventarioTecnologia() {
        try {
            CmtResutadoPasaInventariosDto cmtResutadoPasaInventariosDto =
                    cmtInventarioTecnologiaFacadeLocal.actualizarInvTecCm(ordenTrabajo, usuarioVT, perfilVT);
            terminaProcesoActualizacion(cmtResutadoPasaInventariosDto.getRegistrosAdd(), cmtResutadoPasaInventariosDto.getRegistrodDel());
        } catch (ApplicationException ex) {
            String msg = ConstantsCM.MSN_PROCESO_FALLO + ex.getMessage();
            FacesUtil.mostrarMensajeError(msg, ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de actualizar el inventario de la tecnología: " + e.getMessage(), e, LOGGER);
        }
        return ConstantsCM.PATH_VIEW_OT + "generalOt";
    }

    public boolean terminaProcesoActualizacion(int regAdd, int regDel) {
        String msng = "Se ha actualizado la CM: " + regAdd + "  registros adiccionados en CMT_INVENTARIOS_TECNOLOGIAS y  " + regDel + " modificados";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                ConstantsCM.MSN_PROCESO_EXITOSO + " " + msng, ""));
        return true;
    }
    
    public void crearNewIntOt() {

        mostrarPanelCrearInvOt = true;
        mostrarPanelListaInvOt = false;

    }
    
    public void volver() {

        mostrarPanelCrearInvOt = false;
        mostrarPanelListaInvOt = true;

    }

    public CmtOrdenTrabajoMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public void initializedBean() {
    }

    public List<CmtInventariosTecnologiaMgl> getLsCmtInventariosTecnologiaMgls() {
        return lsCmtInventariosTecnologiaMgls;
    }

    public void setLsCmtInventariosTecnologiaMgls(List<CmtInventariosTecnologiaMgl> lsCmtInventariosTecnologiaMgls) {
        this.lsCmtInventariosTecnologiaMgls = lsCmtInventariosTecnologiaMgls;
    }

    public String getTipoInvTecSelected() {
        return tipoInvTecSelected;
    }

    public void setTipoInvTecSelected(String tipoInvTecSelected) {
        this.tipoInvTecSelected = tipoInvTecSelected;
    }

    public List<CmtBasicaMgl> getTipoInvTecList() {
        return tipoInvTecList;
    }

    public void setTipoInvTecList(List<CmtBasicaMgl> tipoInvTecList) {
        this.tipoInvTecList = tipoInvTecList;
    }

    public String getClaseInvTecSelected() {
        return claseInvTecSelected;
    }

    public void setClaseInvTecSelected(String claseInvTecSelected) {
        this.claseInvTecSelected = claseInvTecSelected;
    }

    public List<CmtBasicaMgl> getClaseInvTecList() {
        return claseInvTecList;
    }

    public void setClaseInvTecList(List<CmtBasicaMgl> claseInvTecList) {
        this.claseInvTecList = claseInvTecList;
    }

    public String getFabInvTecSelected() {
        return fabInvTecSelected;
    }

    public void setFabInvTecSelected(String fabInvTecSelected) {
        this.fabInvTecSelected = fabInvTecSelected;
    }

    public List<CmtBasicaMgl> getFabInvTecList() {
        return fabInvTecList;
    }

    public void setFabInvTecList(List<CmtBasicaMgl> fabInvTecList) {
        this.fabInvTecList = fabInvTecList;
    }

    public List<CmtOrdenTrabajoInventarioMgl> getLsCmtOrdenTrabajoInventarioMgls() {
        return lsCmtOrdenTrabajoInventarioMgls;
    }

    public void setLsCmtOrdenTrabajoInventarioMgls(List<CmtOrdenTrabajoInventarioMgl> lsCmtOrdenTrabajoInventarioMgls) {
        this.lsCmtOrdenTrabajoInventarioMgls = lsCmtOrdenTrabajoInventarioMgls;
    }

    public List<CmtSubEdificiosVt> getLsCmtSubEdificiosVts() {
        return lsCmtSubEdificiosVts;
    }

    public void setLsCmtSubEdificiosVts(List<CmtSubEdificiosVt> lsCmtSubEdificiosVts) {
        this.lsCmtSubEdificiosVts = lsCmtSubEdificiosVts;
    }

    public CmtOrdenTrabajoInventarioMgl getOrdenTrabajoInventarioMgl() {
        return ordenTrabajoInventarioMgl;
    }

    public void setOrdenTrabajoInventarioMgl(CmtOrdenTrabajoInventarioMgl ordenTrabajoInventarioMgl) {
        this.ordenTrabajoInventarioMgl = ordenTrabajoInventarioMgl;
    }

    public BigDecimal getSubEdificioMglSelected() {
        return subEdificioMglSelected;
    }

    public void setSubEdificioMglSelected(BigDecimal subEdificioMglSelected) {
        this.subEdificioMglSelected = subEdificioMglSelected;
    }

    public boolean isInhabilitarFormulario() {
        return inhabilitarFormulario;
    }

    public void setInhabilitarFormulario(boolean inhabilitarFormulario) {
        this.inhabilitarFormulario = inhabilitarFormulario;
    }

    public boolean isMostrarPanelCrearInvOt() {
        return mostrarPanelCrearInvOt;
    }

    public void setMostrarPanelCrearInvOt(boolean mostrarPanelCrearInvOt) {
        this.mostrarPanelCrearInvOt = mostrarPanelCrearInvOt;
    }

    public boolean isMostrarPanelListaInvOt() {
        return mostrarPanelListaInvOt;
    }

    public void setMostrarPanelListaInvOt(boolean mostrarPanelListaInvOt) {
        this.mostrarPanelListaInvOt = mostrarPanelListaInvOt;
    }
    
   //////////////Paginado Inventarios Ot//////////////
    
    public void listInfoByPageNot(int page) {
        try {
            actualNot = page;
            getTotalPaginasNot();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPagNot * (page - 1));
            }
            //Obtenemos el rango de la paginación
            if (lsCmtOrdenTrabajoInventarioMglsAux.size() > 0) {
                
                int maxResult;
                if ((firstResult + filasPagNot) > lsCmtOrdenTrabajoInventarioMglsAux.size()) {
                    maxResult = lsCmtOrdenTrabajoInventarioMglsAux.size();
                } else {
                    maxResult = (firstResult + filasPagNot);
                }
 
                lsCmtOrdenTrabajoInventarioMgls = new ArrayList<CmtOrdenTrabajoInventarioMgl>();
                for (int k = firstResult; k < maxResult; k++) {
                    lsCmtOrdenTrabajoInventarioMgls.add(lsCmtOrdenTrabajoInventarioMglsAux.get(k));
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error en lista de paginación ", e);
            String msnError = "Error en lista de paginación";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + e.getMessage(), ""));
        }
    }

    public void pageFirstNot() {
        try {
            listInfoByPageNot(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + ex.getMessage(), ""));
        }
    }

  
    public void pagePreviousNot() {
        try {
            int totalPaginas = getTotalPaginasNot();
            int nuevaPageActual = actualNot - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageNot(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + ex.getMessage(), ""));
        }
    }


    public void irPaginaNot() {
        try {
            int totalPaginas = getTotalPaginasNot();
            int nuevaPageActual = Integer.parseInt(numPaginaNot);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageNot(nuevaPageActual);
        } catch (NumberFormatException ex) {
            LOGGER.error("Error direccionando a página", ex);
            String msnError = "Error direccionando a página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + ex.getMessage(), ""));
       } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en CmtInventariosOtMglBean: irPaginaNot() " + e.getMessage(), e, LOGGER);
        }
    }


    public void pageNextNot() {
        try {
            int totalPaginas = getTotalPaginasNot();
            int nuevaPageActual = actualNot + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageNot(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + ex.getMessage(), ""));
        }
    }


    public void pageLastNot() {
        try {
            int totalPaginas = getTotalPaginasNot();
            listInfoByPageNot(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + ex.getMessage(), ""));
        }
    }

    public int getTotalPaginasNot() {
        try {
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = lsCmtOrdenTrabajoInventarioMglsAux.size();
            return (int) ((pageSol % filasPagNot != 0)
                    ? (pageSol / filasPagNot) + 1 : pageSol / filasPagNot);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + ex.getMessage(), ""));
        }
        return 1;
    }


    public String getPageActualNot() {
        paginaListNot = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasNot();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListNot.add(i);
        }
        pageActualNot = String.valueOf(actualNot) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaNot == null) {
            numPaginaNot = "1";
        }
        numPaginaNot = String.valueOf(actualNot);
        return pageActualNot;
    }

    public String getNumPaginaNot() {
        return numPaginaNot;
    }

    public void setNumPaginaNot(String numPaginaNot) {
        this.numPaginaNot = numPaginaNot;
    }

    public List<Integer> getPaginaListNot() {
        return paginaListNot;
    }

    public void setPaginaListNot(List<Integer> paginaListNot) {
        this.paginaListNot = paginaListNot;
    }
    
    

}
