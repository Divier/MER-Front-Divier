/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtFiltroConsultaOrdenesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoOtMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
 * @author bocanegravm
 */
@ManagedBean(name = "generarOtEjecucionBean")
@ViewScoped
public class GenerarOtEjecucionBean {

    
    private static final Logger LOGGER = LogManager.getLogger(GenerarOtEjecucionBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private boolean showFooterTable;
    private List<CmtBasicaMgl> estadoOtList;
    private CmtBasicaMgl estadoOtSelected;
    private String idOtToFind;
    private SecurityLogin securityLogin;
    private List<CmtOrdenTrabajoMgl> ordenTrabajoGeneranAcoList;
    private List<BigDecimal> estadosFLujoUsuario;
    private boolean showFiltro;

    private BigDecimal tecnoSelected;
    private CmtTipoOtMgl tipoOtSelected;
    private List<CmtTipoOtMgl> tipoOtGeneraAco;
    private CmtFiltroConsultaOrdenesDto filtroConsultaOrdenesDto = new CmtFiltroConsultaOrdenesDto();
    private final String ROLOPCIDEJECOT = "ROLOPCIDEJECOT";
    
    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private CmtTipoOtMglFacadeLocal cmtTipoOtMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;


    public GenerarOtEjecucionBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
      
        try {
            estadosFLujoUsuario = ordenTrabajoMglFacadeLocal.getEstadosFLujoUsuario(facesContext);
            obtenerTipoOtGeneraAco();
            tipoOtSelected = new CmtTipoOtMgl();
            showFooterTable = true;
            showFiltro = false;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        }
            listInfoByPage(1);    
    }

    public String findById() {
        try {
            if (idOtToFind != null && !idOtToFind.trim().isEmpty()) {
                BigDecimal idOt = new BigDecimal(idOtToFind.trim());
                
                ParametrosMgl result = parametrosFacade.findByAcronimoName(Constant.CONSULTA_OT_WITH_ROLES);
                CmtOrdenTrabajoMgl otFound=null;
                 if (result != null) {
                     if (result.getParValor().equalsIgnoreCase("1")) {
                         // otFound = cmtAprobacionesEstadoMglFacadelocal.findLstAproByOtAndPermisos
                         otFound = ordenTrabajoMglFacadeLocal.
                                 findIdOtCloseForGenerarAcometidaAndPermisos(estadosFLujoUsuario,
                                 tipoOtGeneraAco, idOt, true);
                     } else {
                         otFound = ordenTrabajoMglFacadeLocal.
                                 findIdOtCloseForGenerarAcometidaAndPermisos(estadosFLujoUsuario,
                                 tipoOtGeneraAco, idOt, false);
                     }
                }
                if (otFound != null) {
                    ordenTrabajoGeneranAcoList = new ArrayList<>();
                    ordenTrabajoGeneranAcoList.add(otFound);
                    showFooterTable = false;
                }else{
                String msn = "No fue encontrada la OT: "+idOt+" en Base de datos ó No cuenta con el permiso para visualizarla según el estado por flujo parametrizado.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));   
                }
            } else {
                String msn = "Debe Ingresar un numero de OT";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un Error consultando la solicitud " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String goGestionOtEjecucion(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) {
        OtMglBean otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
        return otMglBean.ingresarGestionOtEjecucion(cmtOrdenTrabajoMgl);
    }

    public String goCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        try {
            if (cuentaMatriz != null) {
                CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
                cuentaMatrizBean.setCuentaMatriz(cuentaMatriz);
                ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getSessionBean(ConsultaCuentasMatricesBean.class);
                consultaCuentasMatricesBean.goCuentaMatrizEstadosSol(cuentaMatrizBean);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    private String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            ordenTrabajoGeneranAcoList = ordenTrabajoMglFacadeLocal.
                    findByOtCloseForGenerarAcometida(estadosFLujoUsuario, 
                            filtroConsultaOrdenesDto, tipoOtGeneraAco, page, ConstantsCM.PAGINACION_QUINCE_FILAS);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error al ir a la siguiente página. EX000 " + ex.getMessage(), ex);
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
            int pageSol = ordenTrabajoMglFacadeLocal.getCountOtCloseForGenerarAcometida
                    (estadosFLujoUsuario, filtroConsultaOrdenesDto, tipoOtGeneraAco);
            
            int totalPaginas = (int) ((pageSol % ConstantsCM.PAGINACION_QUINCE_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_QUINCE_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_QUINCE_FILAS);

            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public String getPageActual() {
        try {
            paginaList = new ArrayList<>();
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
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }
    
      public String showFiltro() {
        showFiltro = !showFiltro;
        return null;
    }
 
      
    public String filtrarInfo() {
        listInfoByPage(1);
        return null;
    }
    
    
     /**
     * Obtiene el listado tipo de ot que generan acometidas 
     * de la base de datos.
     *
     * @author Victor Bocanegra
     */
    public void obtenerTipoOtGeneraAco() {
        try {
            //Concultamos los tipos de OT acometidas  
            tipoOtGeneraAco = cmtTipoOtMglFacadeLocal.findAllTipoOtVts();
        } catch (ApplicationException e) {
            String msnError = "Error al obtener listado de tipos de ot";
            FacesUtil.mostrarMensajeError(msnError + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en GenerarOtEjecucion class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public boolean validarIdOtRol() {
        return validarEdicion(ROLOPCIDEJECOT);
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
    
    public int getFilasPag() {
        return ConstantsCM.PAGINACION_QUINCE_FILAS;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public List<CmtBasicaMgl> getEstadoOtList() {
        return estadoOtList;
    }

    public void setEstadoOtList(List<CmtBasicaMgl> estadoOtList) {
        this.estadoOtList = estadoOtList;
    }

    public CmtBasicaMgl getEstadoOtSelected() {
        return estadoOtSelected;
    }

    public void setEstadoOtSelected(CmtBasicaMgl estadoOtSelected) {
        this.estadoOtSelected = estadoOtSelected;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public boolean isShowFooterTable() {
        return showFooterTable;
    }

    public void setShowFooterTable(boolean showFooterTable) {
        this.showFooterTable = showFooterTable;
    }

    public String getIdOtToFind() {
        return idOtToFind;
    }

    public void setIdOtToFind(String idOtToFind) {
        this.idOtToFind = idOtToFind;
    }

    public List<CmtOrdenTrabajoMgl> getOrdenTrabajoGeneranAcoList() {
        return ordenTrabajoGeneranAcoList;
    }

    public void setOrdenTrabajoGeneranAcoList(List<CmtOrdenTrabajoMgl> ordenTrabajoGeneranAcoList) {
        this.ordenTrabajoGeneranAcoList = ordenTrabajoGeneranAcoList;
    }

    public boolean isShowFiltro() {
        return showFiltro;
    }

    public void setShowFiltro(boolean showFiltro) {
        this.showFiltro = showFiltro;
    }
    
    public BigDecimal getTecnoSelected() {
        return tecnoSelected;
    }

    public void setTecnoSelected(BigDecimal tecnoSelected) {
        this.tecnoSelected = tecnoSelected;
    }

    public CmtTipoOtMgl getTipoOtSelected() {
        return tipoOtSelected;
    }

    public void setTipoOtSelected(CmtTipoOtMgl tipoOtSelected) {
        this.tipoOtSelected = tipoOtSelected;
    }

    public List<CmtTipoOtMgl> getTipoOtGeneraAco() {
        return tipoOtGeneraAco;
    }

    public void setTipoOtGeneraAco(List<CmtTipoOtMgl> tipoOtGeneraAco) {
        this.tipoOtGeneraAco = tipoOtGeneraAco;
    }

    public CmtFiltroConsultaOrdenesDto getFiltroConsultaOrdenesDto() {
        return filtroConsultaOrdenesDto;
    }

    public void setFiltroConsultaOrdenesDto(CmtFiltroConsultaOrdenesDto filtroConsultaOrdenesDto) {
        this.filtroConsultaOrdenesDto = filtroConsultaOrdenesDto;
    }
}
