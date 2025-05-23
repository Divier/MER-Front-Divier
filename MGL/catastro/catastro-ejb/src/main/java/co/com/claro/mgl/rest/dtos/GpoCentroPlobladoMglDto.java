package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jrodriguez
 */
@XmlRootElement(name = "gpoCentroPobladoMgl")
public class GpoCentroPlobladoMglDto {

    @XmlElement
    private String gpoCodigo;
    @XmlElement
    private String gpoNombre;
    @XmlElement
    private GpoCiudadMglDto ciudad;
    

    public String getGpoCodigo() {
        return gpoCodigo;
    }

    public void setGpoCodigo(String gpoCodigo) {
        this.gpoCodigo = gpoCodigo;
    }

    public String getGpoNombre() {
        return gpoNombre;
    }

    public void setGpoNombre(String gpoNombre) {
        this.gpoNombre = gpoNombre;
    }

    public GpoCiudadMglDto getCiudad() {
        return ciudad;
    }

    public void setCiudad(GpoCiudadMglDto ciudad) {
        this.ciudad = ciudad;
    }

}
