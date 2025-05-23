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
public class ResponseDataManttoMaterialProveedor {

    @XmlElement
    private String codigoProveedor = "";
    @XmlElement
    private String codigoMaterial = "";
    @XmlElement
    private String costoUnitario = "";
    @XmlElement
    private String fecha = "";
    @XmlElement
    private String tipoMaterial = "";
    @XmlElement
    private String proveedor = "";
    @XmlElement
    private String nombreMaterial = "";

    public ResponseDataManttoMaterialProveedor() {
    }

    public ResponseDataManttoMaterialProveedor(String responseString)  {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            codigoProveedor = spliter[0];
            codigoMaterial = spliter[1];
            costoUnitario = spliter[2];
            fecha = spliter[3];
            tipoMaterial = spliter[4];
            proveedor = spliter[5];
            nombreMaterial = spliter[6];
        }
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getCodigoMaterial() {
        return codigoMaterial;
    }

    public void setCodigoMaterial(String codigoMaterial) {
        this.codigoMaterial = codigoMaterial;
    }

    public String getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(String costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

   
}
