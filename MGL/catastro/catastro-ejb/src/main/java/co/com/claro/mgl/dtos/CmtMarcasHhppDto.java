/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.MarcasMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rodriguezluim
 */
@XmlRootElement(name = "cmtMarcasHhppDto")
public class CmtMarcasHhppDto extends CmtDefaultBasicResquest {

    @XmlElement
    private BigDecimal idHhppRequest;
    @XmlElement
    private List<MarcasMgl> listaMarcasMgls;

    public BigDecimal getIdHhppRequest() {
        return idHhppRequest;
    }

    public void setIdHhppRequest(BigDecimal idHhppRequest) {
        this.idHhppRequest = idHhppRequest;
    }

    public List<MarcasMgl> getListaMarcasMgls() {
        return listaMarcasMgls;
    }

    public void setListaMarcasMgls(List<MarcasMgl> listaMarcasMgls) {
        this.listaMarcasMgls = listaMarcasMgls;
    }
}
