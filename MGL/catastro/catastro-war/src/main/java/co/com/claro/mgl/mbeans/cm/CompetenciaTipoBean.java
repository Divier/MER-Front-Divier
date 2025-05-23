/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCompetenciaTipoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaTipoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @author User
 */
@ManagedBean(name = "competenciaTipoBean")
@ViewScoped
public class CompetenciaTipoBean implements Serializable {

    private SecurityLogin securityLogin;
    private static final long serialVersionUID = 1L;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private static final Logger LOGGER = LogManager.getLogger(CompetenciaTipoBean.class);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_QUINCE_FILAS;
    private CmtCompetenciaTipoMgl competenciaTipoSelected;
    private CmtBasicaMgl proveedorCompetenciaTipo;
    private CmtBasicaMgl servicioCompetenciaTipo;
    private List<CmtBasicaMgl> servicioCompetenciaList;
    private List<CmtBasicaMgl> proveedorCompetenciaList;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private List<CmtCompetenciaTipoMgl> competenciaTipoList;
    private boolean renderDetalle;
    private boolean renderAuditoria;
    
    //Opciones agregadas para Rol
    private final String FRMCPOTPS = "FRMCPOTPS";
    private final String VALIDARIDROLCOM = "VALIDARIDROLCOM";
    
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtCompetenciaTipoMglFacadeLocal competenciaTipoMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    public CompetenciaTipoBean() {

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
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    private void cargarListas() {
        try {
            CmtTipoBasicaMgl tipoBasicaProveedor;
            tipoBasicaProveedor=tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_COMPETENCIA_PROVEEDOR);
            CmtTipoBasicaMgl tipoBasicaServicio;
            tipoBasicaServicio=tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_COMPETENCIA);

            proveedorCompetenciaList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaProveedor);
            servicioCompetenciaList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaServicio);

            competenciaTipoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            renderDetalle = false;
            competenciaTipoSelected = new CmtCompetenciaTipoMgl();
            proveedorCompetenciaTipo = new CmtBasicaMgl();
            servicioCompetenciaTipo = new CmtBasicaMgl();

            listInfoByPage(1);

//           
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private String listInfoByPage(int page) {
        try {
            competenciaTipoList = competenciaTipoMglFacadeLocal.findAllActiveItemsPaginado(page, filasPag);
            actual = page;
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error al ir a la página siguiente. EX000 " + ex.getMessage(), ex);
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

            int pageSol = competenciaTipoMglFacadeLocal.getCountAllActiveItems();
            totalPaginas = (int) ((pageSol % filasPag != 0) ? (pageSol / filasPag) + 1 : pageSol / filasPag);

            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public String irCrearCompetencia() {
        try {
            String codCalculoRr = competenciaTipoMglFacadeLocal.calcularCodigoRr();
            competenciaTipoSelected = new CmtCompetenciaTipoMgl();
            competenciaTipoSelected.setCodigoRr(codCalculoRr);
            proveedorCompetenciaTipo = new CmtBasicaMgl();
            servicioCompetenciaTipo = new CmtBasicaMgl();
            renderDetalle = true;
            renderAuditoria = false;

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String crearCompetencia() {
        try {
            proveedorCompetenciaTipo = basicaMglFacadeLocal.findById(proveedorCompetenciaTipo);
            servicioCompetenciaTipo = basicaMglFacadeLocal.findById(servicioCompetenciaTipo);
            competenciaTipoSelected.setProveedorCompetencia(proveedorCompetenciaTipo);
            competenciaTipoSelected.setServicioCompetencia(servicioCompetenciaTipo);
            competenciaTipoSelected = competenciaTipoMglFacadeLocal.create(competenciaTipoSelected);
            renderDetalle = false;
            renderAuditoria = false;
            listInfoByPage(actual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String actualizarCompetencia() {
        try {
            proveedorCompetenciaTipo = basicaMglFacadeLocal.findById(proveedorCompetenciaTipo);
            servicioCompetenciaTipo = basicaMglFacadeLocal.findById(servicioCompetenciaTipo);
            competenciaTipoSelected.setProveedorCompetencia(proveedorCompetenciaTipo);
            competenciaTipoSelected.setServicioCompetencia(servicioCompetenciaTipo);
            competenciaTipoSelected = competenciaTipoMglFacadeLocal.update(competenciaTipoSelected);
            renderDetalle = false;
            renderAuditoria = false;
            listInfoByPage(actual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String verDetalleCompetencia(CmtCompetenciaTipoMgl competenciaMgl) {
        competenciaTipoSelected = competenciaMgl;
        proveedorCompetenciaTipo = competenciaMgl.getProveedorCompetencia();
        servicioCompetenciaTipo = competenciaMgl.getServicioCompetencia();
        renderDetalle = true;
        renderAuditoria = false;
        return null;
    }

    public String mostrarAuditoriaCompetencia(CmtCompetenciaTipoMgl competenciaMgl) {
        competenciaTipoSelected = competenciaMgl;
        renderDetalle = false;
        renderAuditoria = true;
        return null;
    }

    public String volver() {
        try {
            competenciaTipoSelected = new CmtCompetenciaTipoMgl();
            proveedorCompetenciaTipo = new CmtBasicaMgl();
            servicioCompetenciaTipo = new CmtBasicaMgl();
            renderDetalle = false;
            renderAuditoria = false;
        } catch (Exception ex) {
            LOGGER.error("Error al volver. EX000 " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public String eliminarCompetencia() {
        try {
            boolean result = competenciaTipoMglFacadeLocal.delete(competenciaTipoSelected);
            if (result) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
                competenciaTipoSelected = new CmtCompetenciaTipoMgl();
                renderDetalle = false;
                renderAuditoria = false;
                listInfoByPage(actual);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constant.MSN_PROCESO_FALLO, ""));
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaTipoBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(FRMCPOTPS);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al competenciaTipoBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
      
    public boolean validarIdRol() {
        return validarEdicion(VALIDARIDROLCOM);
    }
    
    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, 
                    ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al validarEdicion en CompetenciaTipoBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
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

    public boolean isRenderDetalle() {
        return renderDetalle;
    }

    public void setRenderDetalle(boolean renderDetalle) {
        this.renderDetalle = renderDetalle;
    }

    public List<CmtCompetenciaTipoMgl> getCompetenciaTipoList() {
        return competenciaTipoList;
    }

    public void setCompetenciaTipoList(List<CmtCompetenciaTipoMgl> competenciaTipoList) {
        this.competenciaTipoList = competenciaTipoList;
    }

    public CmtCompetenciaTipoMgl getCompetenciaTipoSelected() {
        return competenciaTipoSelected;
    }

    public void setCompetenciaTipoSelected(CmtCompetenciaTipoMgl competenciaTipoSelected) {
        this.competenciaTipoSelected = competenciaTipoSelected;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public CmtBasicaMgl getProveedorCompetenciaTipo() {
        return proveedorCompetenciaTipo;
    }

    public void setProveedorCompetenciaTipo(CmtBasicaMgl proveedorCompetenciaTipo) {
        this.proveedorCompetenciaTipo = proveedorCompetenciaTipo;
    }

    public CmtBasicaMgl getServicioCompetenciaTipo() {
        return servicioCompetenciaTipo;
    }

    public void setServicioCompetenciaTipo(CmtBasicaMgl servicioCompetenciaTipo) {
        this.servicioCompetenciaTipo = servicioCompetenciaTipo;
    }

    public List<CmtBasicaMgl> getServicioCompetenciaList() {
        return servicioCompetenciaList;
    }

    public void setServicioCompetenciaList(List<CmtBasicaMgl> servicioCompetenciaList) {
        this.servicioCompetenciaList = servicioCompetenciaList;
    }

    public List<CmtBasicaMgl> getProveedorCompetenciaList() {
        return proveedorCompetenciaList;
    }

    public void setProveedorCompetenciaList(List<CmtBasicaMgl> proveedorCompetenciaList) {
        this.proveedorCompetenciaList = proveedorCompetenciaList;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }
}
