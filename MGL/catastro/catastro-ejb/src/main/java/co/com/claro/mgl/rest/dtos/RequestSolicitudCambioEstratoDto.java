package co.com.claro.mgl.rest.dtos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement
public class RequestSolicitudCambioEstratoDto {

    @XmlElement
    private String idUsuario;
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
    @XmlElement
    private BigDecimal direccionDetalladaId;
    @XmlElement
    private String casoTcrmId;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public BigDecimal getDireccionDetalladaId() {
        return direccionDetalladaId;
    }

    public void setDireccionDetalladaId(BigDecimal direccionDetalladaId) {
        this.direccionDetalladaId = direccionDetalladaId;
    }

    public String getCasoTcrmId() {
        return casoTcrmId;
    }

    public void setCasoTcrmId(String casoTcrmId) {
        this.casoTcrmId = casoTcrmId;
    }
}