/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.enrutar;

import co.com.claro.mgw.agenda.user.UserAut;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asignarRecursoRequest", propOrder = {
    "user",
    "idMensaje",
    "idOt",
    "origen",
    "idTecnico",
    "idBucket",
    "idOrdenEta"
})
public class AsignarRecursoRequest {

    @XmlElement(required = true)
    protected UserAut user;
    protected int idMensaje;
    @XmlElement(required = true)
    protected String idOt;
    @XmlElement(required = true)
    protected String origen;
    @XmlElement(required = true)
    protected String idTecnico;
    protected String idBucket;
    protected String idOrdenEta;

    public AsignarRecursoRequest() {
    }

    public AsignarRecursoRequest(
            UserAut user, int idMensaje, String idOt,
            String origen, String idTecnico, String idBucket,
            String idOrdenEta) {
        this.user = user;
        this.idMensaje = idMensaje;
        this.idOt = idOt;
        this.origen = origen;
        this.idTecnico = idTecnico;
        this.idBucket = idBucket;
        this.idOrdenEta = idOrdenEta;
    }

    public UserAut getUser() {
        return user;
    }

    public void setUser(UserAut user) {
        this.user = user;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getIdOt() {
        return idOt;
    }

    public void setIdOt(String idOt) {
        this.idOt = idOt;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getIdBucket() {
        return idBucket;
    }

    public void setIdBucket(String idBucket) {
        this.idBucket = idBucket;
    }

    public String getIdOrdenEta() {
        return idOrdenEta;
    }

    public void setIdOrdenEta(String idOrdenEta) {
        this.idOrdenEta = idOrdenEta;
    }
    
    
}
