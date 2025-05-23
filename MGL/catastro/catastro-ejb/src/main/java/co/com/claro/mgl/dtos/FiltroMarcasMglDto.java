/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author betancourtjj
 */
public class FiltroMarcasMglDto {
    
    private String codigoEtiqueta;
    private String nombreEtiqueta;
    private String tecnologia;
    private String estado;

       

    
    private List<BigDecimal> hhppIdList;

    public String getCodigoEtiqueta() {
        return codigoEtiqueta;
    }

    public void setCodigoEtiqueta(String codigoEtiqueta) {
        this.codigoEtiqueta = codigoEtiqueta;
    }   

    public List<BigDecimal> getHhppIdList() {
        return hhppIdList;
    }

    public void setHhppIdList(List<BigDecimal> hhppIdList) {
        this.hhppIdList = hhppIdList;
    }

    public String getNombreEtiqueta() {
        return nombreEtiqueta;
    }

    public void setNombreEtiqueta(String nombreEtiqueta) {
        this.nombreEtiqueta = nombreEtiqueta;
    } 
    
    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
