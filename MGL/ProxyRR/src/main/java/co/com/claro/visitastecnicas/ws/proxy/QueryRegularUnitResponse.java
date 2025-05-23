/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author user
 */
@XmlRootElement(name = "QueryRegularUnitResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryRegularUnitResponse", propOrder = {
    "queryRegularUnitManager"
})
public class QueryRegularUnitResponse {

    @XmlElement(name = "QueryRegularUnitManager")
    protected ResponseQueryRegularUnit queryRegularUnitManager;

    /**
     * Obtiene el valor de la propiedad queryRegularUnitManager.
     *
     * @return possible object is {@link ResponseQueryStreet }
     *
     */
    public ResponseQueryRegularUnit getQueryRegularUnitManager() {
        return queryRegularUnitManager;
    }

    /**
     * Define el valor de la propiedad queryRegularUnitManager.
     *
     * @param value allowed object is {@link ResponseQueryStreet }
     *
     */
    public void setQueryRegularUnitManager(ResponseQueryRegularUnit value) {
        this.queryRegularUnitManager = value;
    }

}
