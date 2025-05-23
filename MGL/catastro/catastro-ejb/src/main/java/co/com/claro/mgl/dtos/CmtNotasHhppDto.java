/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rodriguezluim
 */
@XmlRootElement(name = "cmtNotasHhppDto")
public class CmtNotasHhppDto extends CmtDefaultBasicResquest{

    @XmlElement
    private BigDecimal idHhppRequest;
    @XmlElement
    private List<NotasAdicionalesMgl> listaNotasHhpp;

    public BigDecimal getIdHhppRequest() {
        return idHhppRequest;
    }

    public void setIdHhppRequest(BigDecimal idHhppRequest) {
        this.idHhppRequest = idHhppRequest;
    }

    public List<NotasAdicionalesMgl> getListaNotasHhpp() {
        return listaNotasHhpp;
    }

    public void setListaNotasHhpp(List<NotasAdicionalesMgl> listaNotasHhpp) {
        this.listaNotasHhpp = listaNotasHhpp;
    }
}
