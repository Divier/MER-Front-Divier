/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseMesaje;
import co.com.telmex.catastro.data.AddressSuggested;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */

@XmlRootElement(name = "responseConstruccionDireccion")
public class CmtResponseConstruccionDireccionDto{
    
    @XmlElement
    private ResponseMesaje responseMesaje;
    @XmlElement
    private DrDireccion drDireccion;
    @XmlElement
    private List<AddressSuggested> barrioList;
    @XmlElement
    private String direccionStr;

    public ResponseMesaje getResponseMesaje() {
        return responseMesaje;
    }

    public void setResponseMesaje(ResponseMesaje responseMesaje) {
        this.responseMesaje = responseMesaje;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }    

    public List<AddressSuggested> getBarrioList() {
        return barrioList;
    }

    public void setBarrioList(List<AddressSuggested> barrioList) {
        this.barrioList = barrioList;
    }

    public String getDireccionStr() {
        return direccionStr;
    }

    public void setDireccionStr(String direccionStr) {
        this.direccionStr = direccionStr;
    }
}
