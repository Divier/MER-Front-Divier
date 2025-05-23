/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author user
 */
@XmlRootElement(name = "QueryRegularUnit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryRegularUnit", propOrder = {
    "requestQueryRegularUnit"
})
public class QueryRegularUnit {

    protected RequestQueryRegularUnit requestQueryRegularUnit;

    /**
     * Obtiene el valor de la propiedad requestQueryRegularUnit.
     *
     * @return possible object is {@link RequestQueryRegularUnit }
     *
     */
    public RequestQueryRegularUnit getRequestQueryRegularUnit() {
        return requestQueryRegularUnit;
    }

    /**
     * Define el valor de la propiedad requestQueryRegularUnit.
     *
     * @param value allowed object is {@link RequestQueryRegularUnit }
     *
     */
    public void setRequestQueryRegularUnit(RequestQueryRegularUnit value) {
        this.requestQueryRegularUnit = value;
    }

}
