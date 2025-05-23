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
@XmlRootElement(name = "queryStreetGridResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryStreetGridResponse", propOrder = {
    "responseQueryStreetGrid"
})
public class QueryStreetGridResponse {

    @XmlElement(name = "responseQueryStreetGrid")
    protected ResponseMessageUnit responseQueryStreetGrid;

    public ResponseMessageUnit getResponseQueryStreetGrid() {
        return responseQueryStreetGrid;
    }

    public void setResponseQueryStreetGrid(ResponseMessageUnit responseQueryStreetGrid) {
        this.responseQueryStreetGrid = responseQueryStreetGrid;
    }

}
