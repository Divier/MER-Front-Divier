package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DataShowSolicitudRed;
import co.com.telmex.catastro.delegate.ProcesarSolHHPPDisenoRedDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Clase ProcesarSoCreaHHPPSolRedMBean
 * Extiende de BaseMBean
 * Implementa Serialización
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
@ManagedBean(name = "procesarSoCreaHHPPSolRedMBean")
@ViewScoped
public class ProcesarSoCreaHHPPSolRedMBean extends BaseMBean implements Serializable {

    /**
     * 
     */
    public static final String NOMBRE_FUNCIONALIDAD = "GESTIONAR CREACION DE HHPP DISEÑO DE RED";
    private List<DataShowSolicitudRed> solicitudeRedCreadas = new ArrayList<DataShowSolicitudRed>();
    private String messageUser="";
    private BigDecimal idSolictudAProcesar;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

    public ProcesarSoCreaHHPPSolRedMBean() throws IOException, ApplicationException {
        
        solicitudeRedCreadas=ProcesarSolHHPPDisenoRedDelegate.querySolicitudesProcesar();
    }

    public String doProces(){
        session.setAttribute("idSolicitudAProcesar", getIdSolictudAProcesar());
        return "procesarDetalleSolicitudRed";
    }
    /**
     * 
     * @return
     */
    public List<DataShowSolicitudRed> getSolicitudeRedCreadas() {
        return solicitudeRedCreadas;
    }

    /**
     * 
     * @param solicitudeRedCreadas
     */
    public void setSolicitudeRedCreadas(List<DataShowSolicitudRed> solicitudeRedCreadas) {
        this.solicitudeRedCreadas = solicitudeRedCreadas;
    }

    /**
     * @return the messageUser
     */
    public String getMessageUser() {
        return messageUser;
    }

    /**
     * @param messageUser the messageUser to set
     */
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    /**
     * @return the idSolictudAProcesar
     */
    public BigDecimal getIdSolictudAProcesar() {
        return idSolictudAProcesar;
    }

    /**
     * @param idSolictudAProcesar the idSolictudAProcesar to set
     */
    public void setIdSolictudAProcesar(BigDecimal idSolictudAProcesar) {
        this.idSolictudAProcesar = idSolictudAProcesar;
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


}