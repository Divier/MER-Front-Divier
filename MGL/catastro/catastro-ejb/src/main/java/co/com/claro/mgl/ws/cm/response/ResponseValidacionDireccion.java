package co.com.claro.mgl.ws.cm.response;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@XmlRootElement
public class ResponseValidacionDireccion {
    
    @XmlElement
    private ResponseMesaje responseMesaje;
    @XmlElement
    private DrDireccion drDireccion;
    @XmlElement
    private CityEntity cityEntity;

    public ResponseMesaje getResponseMesaje() {
        return responseMesaje;
    }

    public void setResponseMesaje(ResponseMesaje responseMesaje) {
        this.responseMesaje = responseMesaje;
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
}
