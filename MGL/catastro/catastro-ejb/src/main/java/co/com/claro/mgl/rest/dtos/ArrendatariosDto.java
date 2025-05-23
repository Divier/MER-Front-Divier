
package co.com.claro.mgl.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *  Clase que contiene las propiedades de los arrendatarios para salida a la operacion consultaFactibilidad
 *
 * @author Yasser Leon
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arrendatariosDto", propOrder = {"arrendatario"})
public class ArrendatariosDto {

    protected List<ArrendatarioDto> arrendatario;

    /**
     * Gets the value of the arrendatario property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arrendatario property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArrendatario().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArrendatarioDto }
     */
    public List<ArrendatarioDto> getArrendatario() {
        if (arrendatario == null) {
            arrendatario = new ArrayList<>();
        }
        return this.arrendatario;
    }

}
