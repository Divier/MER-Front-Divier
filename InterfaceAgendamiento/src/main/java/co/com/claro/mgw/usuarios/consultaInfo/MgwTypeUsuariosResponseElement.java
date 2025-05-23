/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.usuarios.consultaInfo;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object para Usuarios.
 *
 * @author
 * @author bocanegravm.
 */
@XmlRootElement
public class MgwTypeUsuariosResponseElement {

    @XmlElement(name = "info", required = true)
    private List<MgwTypeUsuariosDtoElement> info;
    @XmlElement(name = "mensaje", required = true)
    private String mensaje;
    @XmlElement(name = "code", required = true)
    private String code;

    public List<MgwTypeUsuariosDtoElement> getInfo() {
        return info;
    }

    public void setInfo(List<MgwTypeUsuariosDtoElement> info) {
        this.info = info;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
