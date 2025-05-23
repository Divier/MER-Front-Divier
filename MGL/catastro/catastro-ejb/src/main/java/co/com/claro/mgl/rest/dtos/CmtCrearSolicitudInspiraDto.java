/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement
public class CmtCrearSolicitudInspiraDto  extends CmtDefaultBasicResquest{

    @XmlElement
    private CmtRequestCrearSolicitudInspira cmtRequestCrearSolicitudInspira;
    @XmlElement
    private String tipoTecnologia;
    @XmlElement
    private String tiempoDuracionSolicitud;
    @XmlElement
    private String codigoDane;
    @XmlElement
    private String nodoGestion;
    @XmlElement
    private String nodoCercano;
    @XmlElement
    private String respuestaGestion;
    @XmlElement
    private String respuesta;
    @XmlElement
    private String idCasoTcrm;

    public CmtCrearSolicitudInspiraDto() {
    }

    public CmtRequestCrearSolicitudInspira getCmtRequestCrearSolicitudInspira() {
        return cmtRequestCrearSolicitudInspira;
    }

    public void setCmtRequestCrearSolicitudInspira(CmtRequestCrearSolicitudInspira cmtRequestCrearSolicitudInspira) {
        this.cmtRequestCrearSolicitudInspira = cmtRequestCrearSolicitudInspira;
    }

    public String getTipoTecnologia() {
        return tipoTecnologia;
    }

    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    public String getTiempoDuracionSolicitud() {
        return tiempoDuracionSolicitud;
    }

    public void setTiempoDuracionSolicitud(String tiempoDuracionSolicitud) {
        this.tiempoDuracionSolicitud = tiempoDuracionSolicitud;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public String getNodoGestion() {
        return nodoGestion;
    }

    public void setNodoGestion(String nodoGestion) {
        this.nodoGestion = nodoGestion;
    }

    public String getNodoCercano() {
        return nodoCercano;
    }

    public void setNodoCercano(String nodoCercano) {
        this.nodoCercano = nodoCercano;
    }

    public String getRespuestaGestion() {
        return respuestaGestion;
    }

    public void setRespuestaGestion(String respuestaGestion) {
        this.respuestaGestion = respuestaGestion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getIdCasoTcrm() {
        return idCasoTcrm;
    }

    public void setIdCasoTcrm(String idCasoTcrm) {
        this.idCasoTcrm = idCasoTcrm;
    }
}
