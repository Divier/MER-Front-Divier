package co.com.claro.visitasTecnicas.entities;

import co.net.cable.agendamiento.ws.RetornoAgenda;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ADMIN
 */
@XmlRootElement
public class DireccionRREntity implements Serializable {
    @XmlElement
    private String id;
    @XmlElement
    private String idHhpp;
    @XmlElement
    private String calle;
    @XmlElement
    private String numeroUnidad;
    @XmlElement
    private String numeroApartamento;
    @XmlElement
    private String comunidad;
    @XmlElement
    private String division;
    @XmlElement
    private boolean resptRegistroHHPPRR;
    @XmlElement
    private String field1F18 ="";
    @XmlTransient
    private RetornoAgenda agenda;
    @XmlElement
    private boolean createAgenda;
    @XmlElement
    private String tipoMensaje;
    @XmlElement
    private String mensaje;
    

    public DireccionRREntity() {
    }


    public DireccionRREntity(String calle, String numeroUnidad, String numeroApartamento) {
        this.calle = calle;
        this.numeroUnidad = numeroUnidad;
        this.numeroApartamento = numeroApartamento;
    }

    public String getIdHhpp() {
        return idHhpp;
    }

    public void setIdHhpp(String idHhpp) {
        this.idHhpp = idHhpp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroUnidad() {
        return numeroUnidad;
    }

    public void setNumeroUnidad(String numeroUnidad) {
        this.numeroUnidad = numeroUnidad;
    }

    public String getNumeroApartamento() {
        return numeroApartamento;
    }

    public void setNumeroApartamento(String numeroApartamento) {
        this.numeroApartamento = numeroApartamento;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public boolean isResptRegistroHHPPRR() {
        return resptRegistroHHPPRR;
    }

    public void setResptRegistroHHPPRR(boolean resptRegistroHHPPRR) {
        this.resptRegistroHHPPRR = resptRegistroHHPPRR;
    }

    public String getField1F18() {
        return field1F18;
    }

    public void setField1F18(String field1F18) {
        this.field1F18 = field1F18;
    }

    public RetornoAgenda getAgenda() {
        return agenda;
    }

    public void setAgenda(RetornoAgenda agenda) {
        this.agenda = agenda;
    }

    public boolean isCreateAgenda() {
        return createAgenda;
    }

    public void setCreateAgenda(boolean createAgenda) {
        this.createAgenda = createAgenda;
    }   

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
