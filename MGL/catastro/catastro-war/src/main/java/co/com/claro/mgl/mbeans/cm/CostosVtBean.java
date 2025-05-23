/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtVisitaTecnicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@ManagedBean(name = "costosVtBean")
@ViewScoped
public class CostosVtBean {

    @EJB
    private CmtVisitaTecnicaMglFacadeLocal visitaTecnicaMglFacadeLocal;
    private static final Logger LOGGER = LogManager.getLogger(CostosVtBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private CmtVisitaTecnicaMgl vt;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private BigDecimal ctoTotalInf;
    private BigDecimal costoTotalDiseno;
    private BigDecimal costoTotalAcometida;

    public CostosVtBean() {
        SecurityLogin securityLogin;
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect(
                        securityLogin.redireccionarLogin());
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            VisitaTecnicaBean vtMglBean =
                    (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            this.vt = vtMglBean.getVt();
            if (vtMglBean.getVt() == null) {
                String msn2 = "Error no hay una vt activa";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }
        } catch (IOException ex) {
            String msn2 = "Error al cargar CostosVtBean...." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error(msn2);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CostosVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            ctoTotalInf = BigDecimal.ZERO;
            costoTotalDiseno = BigDecimal.ZERO;
            costoTotalAcometida = BigDecimal.ZERO;
            vt = visitaTecnicaMglFacadeLocal.findById(vt);
            if (vt.getCtoMaterialesRed() != null
                    && vt.getCtoManoObra() != null) {
                ctoTotalInf = vt.getCtoMaterialesRed().add(vt.getCtoManoObra());
            }
            if (vt.getCostoManoObraDiseno() != null
                    && vt.getCostoMaterialesDiseno() != null) {
                costoTotalDiseno = vt.getCostoManoObraDiseno().add(
                        vt.getCostoMaterialesDiseno());
            }
            costoTotalAcometida = ctoTotalInf.add(costoTotalDiseno);

        } catch (ApplicationException e) {
            String msn2 = "Error al cargar los costos de la Visita Tecnica:....";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CostosVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public CmtVisitaTecnicaMgl getVt() {
        return vt;
    }

    public void setVt(CmtVisitaTecnicaMgl vt) {
        this.vt = vt;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public int getPerfilVt() {
        return perfilVt;
    }

    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    public BigDecimal getCtoTotalInf() {
        return ctoTotalInf;
    }

    public void setCtoTotalInf(BigDecimal ctoTotalInf) {
        this.ctoTotalInf = ctoTotalInf;
    }

    public BigDecimal getCostoTotalDiseno() {
        return costoTotalDiseno;
    }

    public void setCostoTotalDiseno(BigDecimal costoTotalDiseno) {
        this.costoTotalDiseno = costoTotalDiseno;
    }

    public BigDecimal getCostoTotalAcometida() {
        return costoTotalAcometida;
    }

    public void setCostoTotalAcometida(BigDecimal costoTotalAcometida) {
        this.costoTotalAcometida = costoTotalAcometida;
    }
    
}
