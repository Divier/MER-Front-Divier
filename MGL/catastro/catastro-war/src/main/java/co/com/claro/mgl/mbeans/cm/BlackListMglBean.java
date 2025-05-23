/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBlackListMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
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
import java.util.Collections;
import java.util.Comparator;
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
@ManagedBean(name = "blackListMglBean")
//@RequestScoped
@ViewScoped
public class BlackListMglBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(BlackListMglBean.class);
    private static final String TAB_BLACK_LIST_CCMM = "TABBLACKLISTCCMM";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_SEIS_FILAS;
    private List<CmtBlackListMgl> blackListSubEdificioList;
    private List<CmtBasicaMgl> blackListTipoList;
    private CmtBasicaMgl blackListTipoSelected;
    private CmtBlackListMgl blackListSelected;
    private CmtSubEdificioMgl subEdificiosMglSelected;
    private boolean renderDetalle;
    private boolean renderAuditoria;
    private List<AuditoriaDto> listAuditoria;
    private CuentaMatrizBean cuentaMatrizBean;
    private SecurityLogin securityLogin;

    @EJB
    private CmtBlackListMglFacadeLocal blackListMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    public BlackListMglBean() {
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
            FacesUtil.mostrarMensajeError("Se generea error en BlackList class en BlackListMglBean() ... " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en BlackList class en BlackListMglBean()..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    private void cargarListas() {
        try {
            SubEdificiosMglBean subEdificioBean
                    = JSFUtil.getSessionBean(SubEdificiosMglBean.class);
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            subEdificiosMglSelected = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            renderDetalle = false;
            renderAuditoria = false;
            blackListSelected = new CmtBlackListMgl();
            CmtTipoBasicaMgl tipoBasicaBlacListMgl;
            tipoBasicaBlacListMgl=cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_BLACK_LIST_CM);
            blackListTipoList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaBlacListMgl);
            Collections.sort(blackListTipoList,
                    new Comparator<CmtBasicaMgl>() {
                @Override
                public int compare(CmtBasicaMgl f1, CmtBasicaMgl f2) {
                    return f1.getNombreBasica().compareTo(f2.getNombreBasica());
                }
            });

            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en BlackListMglBean : cargarListas()  ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en BlackListMglBean: cargarListas()  ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Metodo para validar si el usuario tiene permisos de creacion
     * @return  {@code boolean} true si tiene permisos de creacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolCrear(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_BLACK_LIST_CCMM, ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación. ");
        }
        return false;
    }

    /**
     * Metodo para validar si el usuario tiene permisos de edicion
     * @return  {@code boolean} true si tiene permisos de edicion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEditar(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_BLACK_LIST_CCMM, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de edición. ");
        }
        return false;
    }

    public String verDetalleCompetencia(CmtBlackListMgl blackListMgl) {
        blackListSelected = blackListMgl;
        blackListTipoSelected = blackListMgl.getBlackListObj();
        renderDetalle = true;
        renderAuditoria = false;
        return null;
    }

    public String mostrarAuditoriaCompetencia(CmtBlackListMgl blackListMgl) {
        blackListSelected = blackListMgl;
        blackListTipoSelected = blackListMgl.getBlackListObj();
        renderDetalle = false;
        renderAuditoria = true;
        try {
            listAuditoria = blackListMglFacadeLocal.construirAuditoria(blackListMgl);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
             FacesUtil.mostrarMensajeError("Se generea error en BlackListMglBean: mostrarAuditoriaCompetencia()  ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en BlackListMglBean: mostrarAuditoriaCompetencia()  ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String irCrearBlacList() {
        try {
            blackListTipoSelected = new CmtBasicaMgl();
            blackListSelected = new CmtBlackListMgl();
            blackListSelected.setActivado("Y");
            renderDetalle = true;
            renderAuditoria = false;
        } catch (Exception ex) {
            LOGGER.error("Error creando BlackList en  BlackListMglBean : irCrearBlacList() " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public String volver() {
        try {
            renderDetalle = false;
            renderAuditoria = false;
        } catch (Exception ex) {
            LOGGER.error("Error en BlackListMglBean : volver() " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public void crearBlacklist() {
        try {
            if (blackListTipoSelected.getBasicaId() == null || blackListSelected.getObservaciones().isEmpty()) {
                String msn = "Existen campos vacios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

            } else {
                blackListTipoSelected = basicaMglFacadeLocal.findById(blackListTipoSelected);

                if (blackListSubEdificioList != null && !blackListSubEdificioList.isEmpty()) {
                    for (CmtBlackListMgl l : blackListSubEdificioList) {
                        if (l.getBlackListObj().getBasicaId().compareTo(blackListTipoSelected.getBasicaId()) == 0) {
                            throw new ApplicationException(": Este Black list ya se encuentra asociado");
                        }
                    }
                }
                
                blackListSelected.setBlackListObj(blackListTipoSelected);
                blackListSelected.setSubEdificioObj(subEdificiosMglSelected);
                if (blackListSelected.getSubEdificioObj() != null) {
                    blackListSelected = blackListMglFacadeLocal.create(blackListSelected, usuarioVT, perfilVT);
                    renderDetalle = false;
                    renderAuditoria = false;
                    listInfoByPage(actual);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error creando BlackList en BlackListMglBean: crearBlacklist() ..." + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error creando BlackList en BlackListMglBean: crearBlacklist() ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String actualizarBlacklist() {
        try {

            blackListSelected = blackListMglFacadeLocal.update(blackListSelected, usuarioVT, perfilVT);
            renderDetalle = false;
            renderAuditoria = false;
            listInfoByPage(actual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en actualizando BlackList en BlackListMglBean: actualizarBlacklist() ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en actualizando BlackList en BlackListMglBean: actualizarBlacklist() ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    private String listInfoByPage(int page) {
        try {
            blackListSubEdificioList = blackListMglFacadeLocal.findBySubEdificioPaginado(page, filasPag, subEdificiosMglSelected);
            Collections.sort(blackListSubEdificioList,
                    new Comparator<CmtBlackListMgl>() {
                @Override
                public int compare(CmtBlackListMgl f2, CmtBlackListMgl f1) {
                    return f1.getActivado().compareTo(f2.getActivado());
                }
            });

            actual = page;
            getPageActual();
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Se generea cargando lista de BlackList en BlackListMglBean: listInfoByPage() ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea cargando lista de BlackList en BlackListMglBean: listInfoByPage() ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error en paginacion BlackListMglBean: pageFirst().  " + ex.getMessage(), ex);
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
            LOGGER.error("Error en paginacion BlackListMglBean: pagePrevious().  " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Error en paginacion BlackListMglBean: irPagina().  " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en paginacion BlackListMglBean: irPagina().  "  + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error en paginacion BlackListMglBean: pageNext().  " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error en paginacion BlackListMglBean: pageLast().  " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            int totalPaginas = 0;

            int pageSol = blackListMglFacadeLocal.getCountBySubEdificio(subEdificiosMglSelected);
            totalPaginas = (int) ((pageSol % filasPag != 0) ? (pageSol / filasPag) + 1 : pageSol / filasPag);

            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en paginacion BlackListMglBean: getTotalPaginas().  " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en paginacion BlackListMglBean: getTotalPaginas().  " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en paginacion BlackListMglBean: getPageActual().  " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
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

    public List<CmtBlackListMgl> getBlackListSubEdificioList() {
        return blackListSubEdificioList;
    }

    public void setBlackListSubEdificioList(List<CmtBlackListMgl> blackListSubEdificioList) {
        this.blackListSubEdificioList = blackListSubEdificioList;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public List<CmtBasicaMgl> getBlackListTipoList() {
        return blackListTipoList;
    }

    public void setBlackListTipoList(List<CmtBasicaMgl> blackListTipoList) {
        this.blackListTipoList = blackListTipoList;
    }

    public CmtBasicaMgl getBlackListTipoSelected() {
        return blackListTipoSelected;
    }

    public void setBlackListTipoSelected(CmtBasicaMgl blackListTipoSelected) {
        this.blackListTipoSelected = blackListTipoSelected;
    }

    public CmtBlackListMgl getBlackListSelected() {
        return blackListSelected;
    }

    public void setBlackListSelected(CmtBlackListMgl blackListSelected) {
        this.blackListSelected = blackListSelected;
    }

    public boolean isRenderDetalle() {
        return renderDetalle;
    }

    public void setRenderDetalle(boolean renderDetalle) {
        this.renderDetalle = renderDetalle;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public CmtBlackListMglFacadeLocal getBlackListMglFacadeLocal() {
        return blackListMglFacadeLocal;
    }

    public void setBlackListMglFacadeLocal(CmtBlackListMglFacadeLocal blackListMglFacadeLocal) {
        this.blackListMglFacadeLocal = blackListMglFacadeLocal;
    }

    public List<AuditoriaDto> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<AuditoriaDto> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public CmtSubEdificioMgl getSubEdificiosMglSelected() {
        return subEdificiosMglSelected;
    }

    public void setSubEdificiosMglSelected(CmtSubEdificioMgl subEdificiosMglSelected) {
        this.subEdificiosMglSelected = subEdificiosMglSelected;
    }

}
