package co.com.claro.mgl.ws.cm.request;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Orlando Velasquez
 */
@XmlRootElement
public class RequestCreaDireccionOtDirecciones {

    @XmlElement
    private DrDireccion drDireccion;
    @XmlElement
    private CityEntity cityEntity;
    @XmlElement
    private String comunidad;
    @XmlElement
    private String idUsuario;

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

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
}
