package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;

import javax.xml.bind.annotation.*;

/**
 * Clase que contiene las propiedades de entrada a la operacion consultaFactibilidad
 *
 * @author Yasser Leon
 */
@XmlRootElement(name = "consultaFactibilidadRequest")
public class ConsultaFactibilidadRequestDto extends CmtDefaultBasicResquest {

    @XmlElement
    private String codigoConsulta;
    @XmlElement
    private String idFactibilidad;
    @XmlElement
    private String idDireccion;

    /**
     * Gets the value of the codigoConsulta property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    /**
     * Sets the value of the codigoConsulta property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCodigoConsulta(String value) {
        this.codigoConsulta = value;
    }

    /**
     * Gets the value of the idFactibilidad property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIdFactibilidad() {
        return idFactibilidad;
    }

    /**
     * Sets the value of the idFactibilidad property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIdFactibilidad(String value) {
        this.idFactibilidad = value;
    }

    /**
     * Gets the value of the idDireccion property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIdDireccion() {
        return idDireccion;
    }

    /**
     * Sets the value of the idDireccion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIdDireccion(String value) {
        this.idDireccion = value;
    }

}