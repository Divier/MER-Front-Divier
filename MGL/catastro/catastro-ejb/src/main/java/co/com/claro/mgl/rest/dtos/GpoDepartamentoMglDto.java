package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Orlando Velasquez
 */
@XmlRootElement(name = "gpoDepartamentoMgl")
public class GpoDepartamentoMglDto {

    @XmlElement
    private String gpoCodigo;
    @XmlElement
    private String gpoNombre;

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

}
