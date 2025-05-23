/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author aleal
 */
public class CmtFiltroConsultaComunidadDto {

    public BigDecimal comunidadRrId;
    public String codigoRr;
    public String nombreComunidad;
    public String tecnologia;
    public String regionalRr;
    public String codigoPostal;
    public String nombreCortoRegional;
    public String ubicacion;

    public String getRegionalRr() {
        return regionalRr;
    }

    public void setRegionalRr(String regionalRr) {
        this.regionalRr = regionalRr;
    }
    
    public BigDecimal getComunidadRrId() {
        return comunidadRrId;
    }

    public void setComunidadRrId(BigDecimal comunidadRrId) {
        this.comunidadRrId = comunidadRrId;
    }

    public String getCodigoRr() {
        return codigoRr;
    }

    public void setCodigoRr(String codigoRr) {
        this.codigoRr = codigoRr;
    }

    public String getNombreComunidad() {
        return nombreComunidad;
    }

    public void setNombreComunidad(String nombreComunidad) {
        this.nombreComunidad = nombreComunidad;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }
    
    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombreCortoRegional() {
        return nombreCortoRegional;
    }

    public void setNombreCortoRegional(String nombreCortoRegional) {
        this.nombreCortoRegional = nombreCortoRegional;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    
    
}
