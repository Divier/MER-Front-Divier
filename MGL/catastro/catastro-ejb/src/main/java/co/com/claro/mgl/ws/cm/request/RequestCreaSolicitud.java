package co.com.claro.mgl.ws.cm.request;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@XmlRootElement
public class RequestCreaSolicitud {
    
    @XmlElement
    private String idUsuario;
    @XmlElement
    private String comunidad;
    @XmlElement
    private String division;
    @XmlElement
    private String observaciones;
    @XmlElement
    private String contacto;
    @XmlElement
    private String telefonoContacto;
    @XmlElement
    private String canalVentas;
    @XmlElement
    private DrDireccion drDireccion;
    @XmlElement
    private CityEntity cityEntity;
    @XmlElement
    private boolean creacionDesdeWebService;
    @XmlElement
    private CmtDireccionDetalladaMgl direccionDetallada;
    @XmlElement
    private String tipoDocumento;
    @XmlElement
    private String numDocCli;

    /* ---- Getters and setters ---- */
    
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public CityEntity getCityEntity() {
        return cityEntity;
    }

    public void setCityEntity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }

    public boolean isCreacionDesdeWebService() {
        return creacionDesdeWebService;
    }

    public void setCreacionDesdeWebService(boolean creacionDesdeWebService) {
        this.creacionDesdeWebService = creacionDesdeWebService;
    }

    public CmtDireccionDetalladaMgl getDireccionDetallada() {
        return direccionDetallada;
    }

    public void setDireccionDetallada(CmtDireccionDetalladaMgl direccionDetallada) {
        this.direccionDetallada = direccionDetallada;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocCli() {
        return numDocCli;
    }

    public void setNumDocCli(String numDocCli) {
        this.numDocCli = numDocCli;
    }

}
