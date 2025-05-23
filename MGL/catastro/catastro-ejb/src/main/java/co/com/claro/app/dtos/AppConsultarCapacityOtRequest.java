/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "appConsultarCapacityOtRequest")
public class AppConsultarCapacityOtRequest extends CmtDefaultBasicResquest{

    @XmlElement
    private String otLocation;
    @XmlElement
    private String idOt;
    

    public String getOtLocation() {
        return otLocation;
    }

    public void setOtLocation(String otLocation) {
        this.otLocation = otLocation;
    }

    public String getIdOt() {
        return idOt;
    }

    public void setIdOt(String idOt) {
        this.idOt = idOt;
    }

}
