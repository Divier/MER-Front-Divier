/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtCompetenciaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCompetenciaTipoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaTipoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@ManagedBean(name = "competenciaMglBean")
@ViewScoped
public class CompetenciaMglBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private static final Logger LOGGER = LogManager.getLogger(CompetenciaMglBean.class);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_SEIS_FILAS;
    private CmtCompetenciaTipoMgl competenciaTipo;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private CmtSubEdificioMgl subEdificiosMglSelected;
    private List<CmtCompetenciaMgl> competenciaSubEdificioList;
    private List<CmtCompetenciaTipoMgl> competenciaTipoList;
    private CmtCompetenciaMgl competenciaSelected;
    private boolean renderDetalle;
    private boolean renderAuditoria;
    @EJB
    private CmtCompetenciaMglFacadeLocal competenciaMglFacadeLocal;
    @EJB
    private CmtCompetenciaTipoMglFacadeLocal competenciaTipoMglFacadeLocal;
    private List<AuditoriaDto> listAuditoria;
    
    public CompetenciaMglBean() {

        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    private void cargarListas() {
        try {
            competenciaMglFacadeLocal.setUser(usuarioVT, perfilVT);
            renderDetalle = false;
            renderAuditoria = false;
            competenciaTipo = new CmtCompetenciaTipoMgl();
            competenciaTipo.setCompetenciaTipoId(BigDecimal.ZERO);
            SubEdificiosMglBean subEdificioBean =
                    (SubEdificiosMglBean) JSFUtil.getSessionBean(SubEdificiosMglBean.class);
            subEdificiosMglSelected = subEdificioBean.getSelectedCmtSubEdificioMgl();

            if (subEdificiosMglSelected != null) {
                competenciaTipoList = competenciaTipoMglFacadeLocal.findAllItemsActive();

                listInfoByPage(1);
            } else {
                String msn = "No se ha seleccionado un Subedificio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
//           
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private String listInfoByPage(int page) {
        try {
            competenciaSubEdificioList = competenciaMglFacadeLocal.findBySubEdificioPaginacion(page, filasPag, subEdificiosMglSelected);
            actual = page;
            getPageActual();
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO  ;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a primera pagina. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al ir a pagina anterior. EX000 " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
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
            int totalPaginas = 0;

            int pageSol = competenciaMglFacadeLocal.getCountBySubEdificio(subEdificiosMglSelected);
            totalPaginas = (int) ((pageSol % filasPag != 0) ? (pageSol / filasPag) + 1 : pageSol / filasPag);

            return totalPaginas;
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public String irCrearCompetencia() {
        try {
            
            competenciaTipo = new CmtCompetenciaTipoMgl();
            competenciaTipo.setCompetenciaTipoId(BigDecimal.ZERO);
            competenciaSelected = new CmtCompetenciaMgl();
            
            renderDetalle = true;
            renderAuditoria = false;
        } catch (Exception ex) {
            LOGGER.error("Error al ir a crear competencia. EX000 " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage() ;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public String crearCompetencia() {
        try {
            competenciaTipo=competenciaTipoMglFacadeLocal.findById(competenciaTipo.getCompetenciaTipoId());
            competenciaSelected.setCompetenciaTipo(competenciaTipo);
            competenciaSelected.setSubEdificioObj(subEdificiosMglSelected);
            competenciaSelected = competenciaMglFacadeLocal.create(competenciaSelected);
            renderDetalle = false;
            renderAuditoria = false;
            listInfoByPage(actual);
            subEdificiosMglSelected.setCompetenciaList(
                    competenciaMglFacadeLocal.findBySubEdificio(
                        subEdificiosMglSelected));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String actualizarCompetencia() {
        try {
            competenciaTipo=competenciaTipoMglFacadeLocal.findById(competenciaTipo.getCompetenciaTipoId());
            competenciaSelected.setCompetenciaTipo(competenciaTipo);
            competenciaSelected = competenciaMglFacadeLocal.update(competenciaSelected);
            renderDetalle = false;     
            renderAuditoria = false;
            listInfoByPage(actual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    public String verDetalleCompetencia(CmtCompetenciaMgl competenciaMgl) {
        competenciaSelected = competenciaMgl;
        competenciaTipo = competenciaMgl.getCompetenciaTipo();
        renderDetalle = true;
        return null;
    }
    
    public String mostrarAuditoriaCompetencia(CmtCompetenciaMgl competenciaMgl) {
        renderDetalle = false;
        renderAuditoria = true;
        
        try {
            listAuditoria = competenciaMglFacadeLocal.construirAuditoria(competenciaMgl);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String volver() {
        try {
            renderDetalle = false;
            renderAuditoria = false;
        } catch (Exception ex) {
            LOGGER.error("Error al volver. EX000 " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage() ;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public String eliminarCompetencia(CmtCompetenciaMgl competencia){ 
        try {
            competencia.setEstadoRegistro(0);
            competenciaSelected = competenciaMglFacadeLocal.update(competencia);
            renderDetalle = false;
            renderAuditoria = false;
            listInfoByPage(1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompetenciaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
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

    public List<CmtCompetenciaMgl> getCompetenciaSubEdificioList() {
        return competenciaSubEdificioList;
    }

    public void setCompetenciaSubEdificioList(List<CmtCompetenciaMgl> competenciaSubEdificioList) {
        this.competenciaSubEdificioList = competenciaSubEdificioList;
    }

    public CmtCompetenciaMgl getCompetenciaSelected() {
        return competenciaSelected;
    }

    public void setCompetenciaSelected(CmtCompetenciaMgl competenciaSelected) {
        this.competenciaSelected = competenciaSelected;
    }

    public CmtCompetenciaTipoMgl getCompetenciaTipo() {
        return competenciaTipo;
    }

    public void setCompetenciaTipo(CmtCompetenciaTipoMgl competenciaTipo) {
        this.competenciaTipo = competenciaTipo;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public List<AuditoriaDto> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<AuditoriaDto> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }
    
}
