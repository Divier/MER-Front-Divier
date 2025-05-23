/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement
public class ResponseDataEstadoResultadoOt {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String nombre = "";
    @XmlElement
    private String programado = "";
    @XmlElement
    private String enCurso = "";
    @XmlElement
    private String realizado = "";
    @XmlElement
    private String cancelado = "";
    @XmlElement
    private String estadoCodigo = "";

    public ResponseDataEstadoResultadoOt() {
    }

    public ResponseDataEstadoResultadoOt(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigo = spliter[0];
            nombre = spliter[1];
            programado = spliter[2];
            enCurso = spliter[3];
            realizado = spliter[4];
            cancelado = spliter[5];
            estadoCodigo = spliter[6];
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNmbre(String nombre) {
        this.nombre = nombre;
    }

    public String getProgramado() {
        return programado;
    }

    public void setProgramado(String programado) {
        this.programado = programado;
    }

    public String getEnCurso() {
        return enCurso;
    }

    public void setEnCurso(String enCurso) {
        this.enCurso = enCurso;
    }

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }

    public String getCancelado() {
        return cancelado;
    }

    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }

    public String getEstadoCodigo() {
        return estadoCodigo;
    }

    public void setEstadoCodigo(String estadoCodigo) {
        this.estadoCodigo = estadoCodigo;
    }
}
