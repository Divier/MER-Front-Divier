/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificiosVtFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.util.HhppEstrato;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "subEdificiosMglBean")
@ViewScoped
public class SubEdificiosMglBean implements Serializable {

    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = LogManager.getLogger(SubEdificiosMglBean.class);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private CuentaMatrizBean cuentaMatrizBean;
    private CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
    private CmtSubEdificioMgl selectedCmtSubEdificioMgl;
    private CmtSubEdificioMgl selectedToAuditoria;
    private String pintarAuditoria = "0";
    private List<AuditoriaDto> listAuditoria;
    private List<HhppEstrato> resumenHhppByEstratoList;
    private List<CmtSubEdificioMgl> listaCM;
    private String codigoPostal;
    private BigDecimal idtowerSelected;
    private String selectedCmtTowerMgl = null;
    private String usuarioVT = null;
    @EJB
    private CmtSubEdificiosVtFacadeLocal cmtSubEdificiosVtFacadeLocal;
    private int cantSubEdificio;
     private String nombreSubedificio;

    /**
     * Creates a new instance of subEdificioMglBean
     */
    public SubEdificiosMglBean() {
        try {
            try {
                SecurityLogin securityLogin = new SecurityLogin(facesContext);
                if (!securityLogin.isLogin()) {
                    if (!response.isCommitted()) {
                        response.sendRedirect(securityLogin.redireccionarLogin());
                    }
                    return;
                }
                usuarioVT = securityLogin.getLoginUser();
            } catch (IOException ex) {
                LOGGER.error("Se generea error en ValidarDirUnoAUnoMBean class ...", ex);
            }
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cmtCuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
            selectedCmtSubEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
        } catch (Exception e) {
            LOGGER.error("Error al Mostrar los sub-edificios");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al Mostrar los sub-edificios", ""));
        }
    }

    public void goActualizar() {
        cuentaMatrizBean.setSelectedCmtSubEdificioMgl(selectedCmtSubEdificioMgl);
        String formTabSeleccionado = "";
        formTabSeleccionado = "/view/MGL/CM/tabs/hhpp";
        FacesContext contextGeneral = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandlerGeneral = contextGeneral.getApplication().getNavigationHandler();
        navigationHandlerGeneral.handleNavigation(contextGeneral, null, formTabSeleccionado + "?faces-redirect=true");

    }

    public String retornarGeneral() {
        pintarAuditoria = "0";
        return null;
    }

    public void getSelectedTorre(BigDecimal idtowerSelected) {
        selectedCmtTowerMgl = "tabs";
        if (idtowerSelected != null) {
            List<CmtSubEdificioMgl> listCmtSubEdificioMgl = cmtCuentaMatrizMgl.getListCmtSubEdificioMglActivos();
            if (listCmtSubEdificioMgl != null && !listCmtSubEdificioMgl.isEmpty()) {
                for (CmtSubEdificioMgl cmtSubEdificioMgl : listCmtSubEdificioMgl) {
                    if (idtowerSelected.equals(cmtSubEdificioMgl.getSubEdificioId())) {
                        selectedCmtSubEdificioMgl = cmtSubEdificioMgl;
                        break;
                    }
                }

            }
            goActualizar();
        }

    }

    public CmtCuentaMatrizMgl getCmtCuentaMatrizMgl() {
        return cmtCuentaMatrizMgl;
    }

    public void setCmtCuentaMatrizMgl(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) {
        this.cmtCuentaMatrizMgl = cmtCuentaMatrizMgl;
    }

    public CmtSubEdificioMgl getSelectedCmtSubEdificioMgl() {
        return selectedCmtSubEdificioMgl;
    }

    public void setSelectedCmtSubEdificioMgl(CmtSubEdificioMgl selectedCmtSubEdificioMgl) {
        this.selectedCmtSubEdificioMgl = selectedCmtSubEdificioMgl;
    }

    public CmtSubEdificioMgl getSelectedToAuditoria() {
        return selectedToAuditoria;
    }

    public void setSelectedToAuditoria(CmtSubEdificioMgl selectedToAuditoria) {
        this.selectedToAuditoria = selectedToAuditoria;
    }

    public String getPintarAuditoria() {
        return pintarAuditoria;
    }

    public void setPintarAuditoria(String pintarAuditoria) {
        this.pintarAuditoria = pintarAuditoria;
    }

    public List<AuditoriaDto> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<AuditoriaDto> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public List<CmtSubEdificioMgl> getListaCM() {
        return listaCM;
    }

    public void setListaCM(List<CmtSubEdificioMgl> listaCM) {
        this.listaCM = listaCM;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getSelectedCmtTowerMgl() {
        return selectedCmtTowerMgl;
    }

    public void setSelectedCmtTowerMgl(String selectedCmtTowerMgl) {
        this.selectedCmtTowerMgl = selectedCmtTowerMgl;
    }

    public BigDecimal getIdtowerSelected() {
        return idtowerSelected;
    }

    public void setIdtowerSelected(BigDecimal idtowerSelected) {
        this.idtowerSelected = idtowerSelected;
    }

    public int getCantSubEdificio() {
        return cantSubEdificio;
    }

    public void setCantSubEdificio(int cantSubEdificio) {
        this.cantSubEdificio = cantSubEdificio;
    }

    public String getNombreSubedificio() {
        return nombreSubedificio;
    }

    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }

   
}
