/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement
public class RequestDataConsultaPorInventarioEquipo {

    @XmlElement
    private String tipoEquipo = "";
    @XmlElement
    private String fabricaEquipo = "";
    @XmlElement
    private String serieEquipo = "";

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public String getFabricaEquipo() {
        return fabricaEquipo;
    }

    public void setFabricaEquipo(String fabricaEquipo) {
        this.fabricaEquipo = fabricaEquipo;
    }

    public String getSerieEquipo() {
        return serieEquipo;
    }

    public void setSerieEquipo(String serieEquipo) {
        this.serieEquipo = serieEquipo;
    }
    
}
