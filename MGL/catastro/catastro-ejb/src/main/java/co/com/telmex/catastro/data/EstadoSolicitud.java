/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.data;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADMIN
 */
@XmlRootElement(name = "estadoSolicitud")
public class EstadoSolicitud extends CmtDefaultBasicResponse{
    @XmlElement(name ="estado")
    private String estado;
    @XmlElement(name ="resultado")
    private String resultado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    
}
