/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class RequestDataNotasCuentaMatriz {

    @XmlElement
    private String usuarioModificador = "";
    @XmlElement
    private String numeroEdificio = "";
    @XmlElement
    private String codigoNotaConsultar = "";
    @XmlElement
    private List<RequestListNotasCuentaMatriz> dataNotasCuentaMatriz;

    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    public String getNumeroEdificio() {
        return numeroEdificio;
    }

    public void setNumeroEdificio(String numeroEdificio) {
        this.numeroEdificio = numeroEdificio;
    }

    public String getCodigoNotaConsultar() {
        return codigoNotaConsultar;
    }

    public void setCodigoNotaConsultar(String codigoNotaConsultar) {
        this.codigoNotaConsultar = codigoNotaConsultar;
    }

    public List<RequestListNotasCuentaMatriz> getDataNotasCuentaMatriz() {
        return dataNotasCuentaMatriz;
    }

    public void setDataNotasCuentaMatriz(List<RequestListNotasCuentaMatriz> dataNotasCuentaMatriz) {
        this.dataNotasCuentaMatriz = dataNotasCuentaMatriz;
    }

}
