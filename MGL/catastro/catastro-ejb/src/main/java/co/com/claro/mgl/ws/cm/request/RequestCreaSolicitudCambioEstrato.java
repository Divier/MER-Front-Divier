package co.com.claro.mgl.ws.cm.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@XmlRootElement
public class RequestCreaSolicitudCambioEstrato {
    @XmlElement
    private String idUsuario;
    @XmlElement
    private String comunidad;
    @XmlElement
    private String division;
    @XmlElement
    private String calleRr;
    @XmlElement
    private String unidadRr;
    @XmlElement
    private String apartamentoRr;
    @XmlElement
    private String estratoNuevo;
    @XmlElement
    private String observaciones;
    @XmlElement
    private String cuentaSuscriptor;
    @XmlElement
    private String contacto;
    @XmlElement
    private String telefonoContacto;
    @XmlElement
    private String canalVentas;
    @XmlElement
    private String fileName;
    @XmlElement
    private byte[] fileBytes;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getCalleRr() {
        return calleRr;
    }

    public void setCalleRr(String calleRr) {
        this.calleRr = calleRr;
    }

    public String getUnidadRr() {
        return unidadRr;
    }

    public void setUnidadRr(String unidadRr) {
        this.unidadRr = unidadRr;
    }

    public String getApartamentoRr() {
        return apartamentoRr;
    }

    public void setApartamentoRr(String apartamentoRr) {
        this.apartamentoRr = apartamentoRr;
    }

    public String getEstratoNuevo() {
        return estratoNuevo;
    }

    public void setEstratoNuevo(String estratoNuevo) {
        this.estratoNuevo = estratoNuevo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCuentaSuscriptor() {
        return cuentaSuscriptor;
    }

    public void setCuentaSuscriptor(String cuentaSuscriptor) {
        this.cuentaSuscriptor = cuentaSuscriptor;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }
    public String getCanalVentas() {
        return canalVentas;
    }

    public void setCanalVentas(String canalVentas) {
        this.canalVentas = canalVentas;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }    
}
