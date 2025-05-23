package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Orlando Velasquez
 */
@XmlRootElement(name = "gpoCiudadMgl")
public class GpoCiudadMglDto {
    @XmlElement
    private String gpoCodigo;
    @XmlElement
    private String gpoNombre;
    @XmlElement
    private GpoDepartamentoMglDto departamento;

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

    public GpoDepartamentoMglDto getDepartamento() {
        return departamento;
    }

    public void setDepartamento(GpoDepartamentoMglDto departamento) {
        this.departamento = departamento;
    }
    
}
