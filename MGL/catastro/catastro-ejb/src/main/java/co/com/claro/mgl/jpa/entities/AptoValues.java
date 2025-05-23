/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@XmlRootElement(name = "aptoValues")
public class AptoValues {
    @XmlElement
    private List<OpcionIdNombre> tiposApto;
    @XmlElement
    private List<OpcionIdNombre> tiposAptoComplemento;

    public List<OpcionIdNombre> getTiposApto() {
        return tiposApto;
    }

    public void setTiposApto(List<OpcionIdNombre> tiposApto) {
        this.tiposApto = tiposApto;
    }

    public List<OpcionIdNombre> getTiposAptoComplemento() {
        return tiposAptoComplemento;
    }

    public void setTiposAptoComplemento(List<OpcionIdNombre> tiposAptoComplemento) {
        this.tiposAptoComplemento = tiposAptoComplemento;
    }
}
