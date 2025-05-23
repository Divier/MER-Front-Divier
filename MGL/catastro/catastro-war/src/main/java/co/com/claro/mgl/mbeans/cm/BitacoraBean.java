/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "bitacoraBean")
@ViewScoped
public class BitacoraBean {

    private String usuarioVT = null;
    private int perfilVt = 0;
    private BigDecimal cuentaMatrizId;
    private ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(BitacoraBean.class);
    private CmtSubEdificioMgl subEdificioMgl;
    private String pintarAuditoria = "0";
    private CuentaMatrizBean cuentaMatrizBean;
    private List<AuditoriaDto> listAuditoria;

    public BitacoraBean() {
        SecurityLogin securityLogin;
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
        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Error en Bitacora : " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en Bitacora : " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            cmtSubEdificioMglFacadeLocal.setUser(usuarioVT, perfilVt);
            FacesContext contx = FacesContext.getCurrentInstance();
            cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizId = cuentaMatrizBean.cuentaMatriz.getCuentaMatrizId();
            SubEdificiosMglBean subEdificioBean
                    = (SubEdificiosMglBean) JSFUtil.getSessionBean(SubEdificiosMglBean.class);
            subEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            pintarAuditoria = "1";
            auditoria(subEdificioMgl);

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en Bitacora en BitacoraBean: init(): " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en Bitacora en BitacoraBean: init(): " + e.getMessage(), e, LOGGER);
        }
    }

    public String auditoria(CmtSubEdificioMgl subEdificioMgl) {
        cuentaMatrizBean.setSelectedTab("BITACORA");
        pintarAuditoria = "1";
        try {
            listAuditoria = cmtSubEdificioMglFacadeLocal.construirAuditoria(subEdificioMgl);
            String bita = "";
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            FacesUtil.mostrarMensajeError("Error en la auditoria  en BitacoraBean: auditoria() : " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en la auditoria  en BitacoraBean: auditoria() : " + e.getMessage(), e, LOGGER);
        }

        return "/view/MGL/CM/tabs/bitacora";
    }

    public String getPintarAuditoria() {

        return pintarAuditoria;
    }

    public String retornarGeneral() {
        pintarAuditoria = "0";
        return "/view/MGL/CM/tabs/general";
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

}
