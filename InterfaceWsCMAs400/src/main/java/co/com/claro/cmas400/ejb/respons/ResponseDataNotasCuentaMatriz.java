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
public class ResponseDataNotasCuentaMatriz {

    @XmlElement
    private String codigoNota = "";
    @XmlElement
    private String codigoDescripcion = "";
    @XmlElement
    private String descripcionNota = "";
    @XmlElement
    private String fechaCreacion = "";
    @XmlElement
    private String fechaModificacion = "";
    @XmlElement
    private String nombreUsuario = "";

    public ResponseDataNotasCuentaMatriz() {
    }

    public ResponseDataNotasCuentaMatriz(String responseString)  {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigoNota = spliter[0];
            codigoDescripcion = spliter[1];
            descripcionNota = spliter[2];
            fechaCreacion = spliter[3];
            fechaModificacion = spliter[4];
            nombreUsuario = spliter[5];
        }
    }

    public String getCodigoNota() {
        return codigoNota;
    }

    public void setCodigoNota(String codigoNota) {
        this.codigoNota = codigoNota;
    }

    public String getCodigoDescripcion() {
        return codigoDescripcion;
    }

    public void setCodigoDescripcion(String codigoDescripcion) {
        this.codigoDescripcion = codigoDescripcion;
    }

    public String getDescripcionNota() {
        return descripcionNota;
    }

    public void setDescripcionNota(String descripcionNota) {
        this.descripcionNota = descripcionNota;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}
