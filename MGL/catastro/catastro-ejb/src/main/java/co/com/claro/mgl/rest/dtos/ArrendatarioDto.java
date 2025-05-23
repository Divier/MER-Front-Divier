
package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *  Clase que contiene las propiedades de los arrendatarios para salida a la operacion consultaFactibilidad
 *
 * @author Yasser Leon
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arrendatarioDto", propOrder = {"nombre", "cuadrante"})
public class ArrendatarioDto {

    protected String nombre;
    protected String cuadrante;

    /**
     * Gets the value of the nombre property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the cuadrante property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCuadrante() {
        return cuadrante;
    }

    /**
     * Sets the value of the cuadrante property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCuadrante(String value) {
        this.cuadrante = value;
    }

}
