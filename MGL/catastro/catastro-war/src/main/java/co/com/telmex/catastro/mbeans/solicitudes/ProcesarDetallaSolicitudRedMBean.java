package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.delegate.SolicitudRedDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author carlos.villa.ext
 */
@ManagedBean(name = "ProcesarDetallaSolicitudRedMBean")
@ViewScoped
public class ProcesarDetallaSolicitudRedMBean extends BaseMBean implements Serializable {

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    BigDecimal solicitudAProcesar = null;
    private String message = "";
    private List<DetalleSolicitud> listaDetalleSolicitud = null;
    private SolicitudRed solicitudRed = null;
    private String valorACambiar = null;
    private BigDecimal idACambiar = null;
    private String page = "1";
    private static final Logger LOGGER = LogManager.getLogger(ProcesarDetallaSolicitudRedMBean.class);

    /**
     * Creates a new instance of ProcesarDetallaSolicitudRed
     */
    public ProcesarDetallaSolicitudRedMBean() {
        solicitudAProcesar = (BigDecimal) session.getAttribute("idSolicitudAProcesar");
        session.removeAttribute("idSolicitudAProcesar");
        if (solicitudAProcesar == null || (solicitudAProcesar.compareTo(BigDecimal.ZERO) == 0)) {
            message = "El id de solicitud a procesar no puede ser Cero '0'";
        } else {
            try {
                listaDetalleSolicitud = SolicitudRedDelegate.ConsultarSolicitudDeRed(solicitudAProcesar);
                if (listaDetalleSolicitud != null && listaDetalleSolicitud.size() > 0) {
                    solicitudRed = listaDetalleSolicitud.get(0).getSolicitudRed();
                    int contFallos = 0;
                    for (DetalleSolicitud det : listaDetalleSolicitud) {
                        if (det.getCalleRR().length() > 50 || det.getNumeroAoartamentoRR().length() > 10 || det.getNumeroUnidadRR().length() > 10) {
                            contFallos += 1;
                        }
                    }
                    if (contFallos != 0) {
                        message = "Hay " + contFallos + " registros con direcciones de RR que superan los caracteres permitidos y serán marcados como rechazados";
                    }
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            }

        }
    }

    public void changeRegError(AjaxBehaviorEvent event) {
        int contFallos = 0;
        if (listaDetalleSolicitud != null && listaDetalleSolicitud.size() > 0) {
            for (DetalleSolicitud det : listaDetalleSolicitud) {
                if (det.getCalleRR().length() > 50 || det.getNumeroAoartamentoRR().length() > 10 || det.getNumeroUnidadRR().length() > 10) {
                    contFallos += 1;
                }
            }
            if (contFallos != 0) {
                message = "Hay " + contFallos + " registros con direcciones de RR que superan los caracteres permitidos y serán marcados como rechazados";
            }
        }
    }

    public String doPosponer() {
        return "procesarSoCreaHHPPSolRedMBean";
    }

    public String doProcesar() {
        try {
            listaDetalleSolicitud = SolicitudRedDelegate.procesarSolicitudDeRed(listaDetalleSolicitud);
        } catch (ApplicationException e) {
            message = e.getMessage();
            LOGGER.error("Error en doProcesar: " + e.getMessage(), e);
        } catch (Exception e) {
            message = e.getMessage();
            LOGGER.error("Error en doProcesar: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the listaDetalleSolicitud
     */
    public List<DetalleSolicitud> getListaDetalleSolicitud() {
        return listaDetalleSolicitud;
    }

    /**
     * @param listaDetalleSolicitud the listaDetalleSolicitud to set
     */
    public void setListaDetalleSolicitud(List<DetalleSolicitud> listaDetalleSolicitud) {
        this.listaDetalleSolicitud = listaDetalleSolicitud;
    }

    /**
     * @return the solicitudRed
     */
    public SolicitudRed getSolicitudRed() {
        return solicitudRed;
    }

    /**
     * @param solicitudRed the solicitudRed to set
     */
    public void setSolicitudRed(SolicitudRed solicitudRed) {
        this.solicitudRed = solicitudRed;
    }

    /**
     * @return the valorACambiar
     */
    public String getValorACambiar() {
        return valorACambiar;
    }

    /**
     * @param valorACambiar the valorACambiar to set
     */
    public void setValorACambiar(String valorACambiar) {
        this.valorACambiar = valorACambiar;
    }

    /**
     * @return the idACambiar
     */
    public BigDecimal getIdACambiar() {
        return idACambiar;
    }

    /**
     * @param idACambiar the idACambiar to set
     */
    public void setIdACambiar(BigDecimal idACambiar) {
        this.idACambiar = idACambiar;
    }

    /**
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }
}
