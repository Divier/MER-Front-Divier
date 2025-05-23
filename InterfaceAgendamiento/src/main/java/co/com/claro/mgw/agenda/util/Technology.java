/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.util;

/**
 *
 * @author Orlando Velasquez Objetivo: Clase Lista de tecnologias Descripcion:
 * Clase Lista de tecnologias
 *
 *
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para Response complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ampliacionTab"
 *         type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="viable"
 *         type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "technology", propOrder = {
    "ampliacionTab",
    "viable"
})
public class Technology {

    protected Boolean ampliacionTab = false;
    protected Boolean viable = false;

    public Technology() {
    }

    public Technology(Boolean ampliacionTab, Boolean viable) {
        this.ampliacionTab = ampliacionTab;
        this.viable = viable;
    }

    /**
     * Obtiene el valor de la propiedad extensionTab.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getAmpliacionTab() {
        return ampliacionTab;
    }

    /**
     * Define el valor de la propiedad extensionTab.
     *
     * @param value allowed object is {@link UserT }
     *
     */
    public void setAmpliacionTab(Boolean value) {
        this.ampliacionTab = value;
    }

    /**
     * Obtiene el valor de la propiedad viable.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getViable() {
        return viable;
    }

    /**
     * Define el valor de la propiedad viable.
     *
     * @param value allowed object is {@link UserT }
     *
     */
    public void setViable(Boolean value) {
        this.viable = value;
    }

}
