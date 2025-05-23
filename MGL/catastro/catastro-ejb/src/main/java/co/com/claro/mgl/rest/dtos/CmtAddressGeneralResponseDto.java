/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "addressGeneralResponse")
@XmlType(propOrder = {"idCentroPoblado", "centroPoblado", "listAddresses"})
public class CmtAddressGeneralResponseDto extends CmtDefaultBasicResponse {

    @XmlElement
    private String idCentroPoblado;
    @XmlElement
    private String centroPoblado;
    @XmlElement
    private List<CmtAddressGeneralDto> listAddresses;

    public List<CmtAddressGeneralDto> getListAddresses() {
        return listAddresses;
    }

    public void setListAddresses(List<CmtAddressGeneralDto> listAddresses) {
        this.listAddresses = listAddresses;
    }

    public String getIdCentroPoblado() {
        return idCentroPoblado;
    }

    public void setIdCentroPoblado(String idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }
}
