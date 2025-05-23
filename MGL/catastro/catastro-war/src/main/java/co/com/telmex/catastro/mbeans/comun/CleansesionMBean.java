package co.com.telmex.catastro.mbeans.comun;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Clase CleansesionMBean
 * Extiende de BaseMBean
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "cleansesionMBean")
public class CleansesionMBean extends BaseMBean {

    private String pagToRender;
    private static final Logger LOGGER = LogManager.getLogger(CleansesionMBean.class);

    /**
     * 
     */
    public CleansesionMBean() {

        String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pag");
        String index = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("index");
        String ctl = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ctl");
        String c = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");

        FacesContext context = FacesContext.getCurrentInstance();
        try {
            //Se eliminan los beans de session que han sido creados dinamicamente
            context.getExternalContext().getSessionMap().remove("consultaEspecificaMBean");
            context.getExternalContext().getSessionMap().remove("actualizarEstadoSolManualMBean");
            context.getExternalContext().getSessionMap().remove("gestionarSolicitudNegocioMBean");
            context.getExternalContext().getSessionMap().remove("gestionarSolicitudModificacionMBean");
            context.getExternalContext().getSessionMap().remove("procesarSolicitudNegocioMBean");
            context.getExternalContext().getSessionMap().remove("procesarSolicitudModificacionMBean");
            context.getExternalContext().getSessionMap().remove("solicitudModificacionMBean");
            context.getExternalContext().getSessionMap().remove("solicitudModificacionMBean");

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al eliminar los beans de session" + e.getMessage() , e, LOGGER);
        }

        if (!param.isEmpty()) {
            try {
                if (index != null && ctl != null && c != null) {
                    String urlCatalogo = param + "&ctl=" + ctl + "&c=" + c + "&index=" + index;
                    FacesUtil.navegarAPagina(urlCatalogo);
                } else {
                    FacesUtil.navegarAPagina(param);
                }
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error al redireccionar: " + e.getMessage() , e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al redireccionar: " + e.getMessage() , e, LOGGER);
            }
        }

    }

    /**
     * 
     * @param pagToRender
     * @return 
     */
    private String renderTo(String pagToRender) {

        return pagToRender;
    }

    /**
     * 
     * @return
     */
    public String getPagToRender() {
        return pagToRender;
    }

    /**
     * 
     * @param pagToRender
     */
    public void setPagToRender(String pagToRender) {
        this.pagToRender = pagToRender;
    }
}
