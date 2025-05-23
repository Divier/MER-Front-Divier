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
 * @author Admin
 */
@XmlRootElement
public class ResponseDataAsesorGestionDeAvanzada {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String codigoSupervisor = "";
    @XmlElement
    private String nombreSupervisor = "";
    @XmlElement
    private String division = "";
    @XmlElement
    private String celular = "";

    public ResponseDataAsesorGestionDeAvanzada() {
    }

    public ResponseDataAsesorGestionDeAvanzada(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigo = spliter[0];
            descripcion = spliter[1];
            codigoSupervisor = spliter[2];
            nombreSupervisor = spliter[3];
            division = spliter[4];
            celular = spliter[5];
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoSupervisor() {
        return codigoSupervisor;
    }

    public void setCodigoSupervisor(String codigoSupervisor) {
        this.codigoSupervisor = codigoSupervisor;
    }

    public String getNombreSupervisor() {
        return nombreSupervisor;
    }

    public void setNombreSupervisor(String nombreSupervisor) {
        this.nombreSupervisor = nombreSupervisor;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
}
