
package co.com.claro.mgl.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *  Clase que contiene las propiedades de las tecnologias de salida en la operacion consultaFactibilidad
 *
 * @author Yasser Leon
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "factibilidadesDto", propOrder = {
        "listaTecnologia"
})
public class FactibilidadesDto {

    private List<ListaTecnologiaDto> listaTecnologia;

    /**
     * Gets the value of the listaTecnologia property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaTecnologia property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaTecnologia().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListaTecnologiaDto }
     */
    public List<ListaTecnologiaDto> getListaTecnologia() {
        if (listaTecnologia == null) {
            listaTecnologia = new ArrayList<>();
        }
        return this.listaTecnologia;
    }

}
