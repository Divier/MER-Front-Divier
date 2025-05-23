package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.delegate.SolicitudRedDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

/**
 * Clase AtenderDolDisenoRedMBean Extiende de BaseMBean
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@ManagedBean(name = "atenderSolDisenoRedMBean")
public class AtenderSolDisenoRedMBean extends BaseMBean {

    private static final Logger LOGGER = LogManager.getLogger(AtenderSolDisenoRedMBean.class);

    List<SolicitudRed> solicitudes = null;
    SolicitudRed solicitud = null;
    BigDecimal idSolicitud = null;
    String sreObservaciones = "";
    Date sreFechaCreacion = null;
    String sreUsuarioCreacion = "";
    int qHhpp = 0;
    List<DetalleSolicitud> detalles = null;

    /**
     *
     */
    public AtenderSolDisenoRedMBean() {

        try {
            solicitudes = SolicitudRedDelegate.querySolicitudesRedPendientes();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener las solicitudes pendientes. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener las solicitudes pendientes. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @param ev
     */
    public void onSeleccionar(ActionEvent ev) {
        idSolicitud = ((BigDecimal) (((UIParameter) ev.getComponent().findComponent("sreid")).getValue()));
        for (SolicitudRed sol : solicitudes) {
            if (sol.getSreId().equals(idSolicitud)) {
                solicitud = sol;
            }
        }
        initSol();
    }

    /**
     *
     */
    private void initSol() {
        if (solicitud != null) {
            try {
                sreObservaciones = solicitud.getSreObservaciones();
                sreFechaCreacion = solicitud.getSreFechaCreacion();
                sreUsuarioCreacion = solicitud.getSreUsuarioCreacion();
                qHhpp = SolicitudRedDelegate.queryQDetalleSolicitud(idSolicitud);
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error al momento de obtener los parámetros iniciales: " + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al momento de obtener los parámetros iniciales: " + e.getMessage(), e, LOGGER);
            }
        }
    }

    /**
     *
     * @return
     */
    public String onIrAccion() {
        return "procesarSolicitudRed";
    }

    /**
     *
     */
    public void onActionCreate() {
        initSol();
    }

    /**
     *
     * @return
     */
    public List<SolicitudRed> getSolicitudes() {
        return solicitudes;
    }

    /**
     *
     * @param solicitudes
     */
    public void setSolicitudes(List<SolicitudRed> solicitudes) {
        this.solicitudes = solicitudes;
    }

    /**
     *
     * @return
     */
    public BigDecimal getIdSolicitud() {
        return idSolicitud;
    }

    /**
     *
     * @param idSolicitud
     */
    public void setIdSolicitud(BigDecimal idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     *
     * @return
     */
    public Date getSreFechaCreacion() {
        return sreFechaCreacion;
    }

    /**
     *
     * @param sreFechaCreacion
     */
    public void setSreFechaCreacion(Date sreFechaCreacion) {
        this.sreFechaCreacion = sreFechaCreacion;
    }

    /**
     *
     * @return
     */
    public String getSreObservaciones() {
        return sreObservaciones;
    }

    /**
     *
     * @param sreObservaciones
     */
    public void setSreObservaciones(String sreObservaciones) {
        this.sreObservaciones = sreObservaciones;
    }

    /**
     *
     * @return
     */
    public String getSreUsuarioCreacion() {
        return sreUsuarioCreacion;
    }

    /**
     *
     * @param sreUsuarioCreacion
     */
    public void setSreUsuarioCreacion(String sreUsuarioCreacion) {
        this.sreUsuarioCreacion = sreUsuarioCreacion;
    }

    /**
     *
     * @return
     */
    public int getqHhpp() {
        return qHhpp;
    }

    /**
     *
     * @param qHhpp
     */
    public void setqHhpp(int qHhpp) {
        this.qHhpp = qHhpp;
    }
}
