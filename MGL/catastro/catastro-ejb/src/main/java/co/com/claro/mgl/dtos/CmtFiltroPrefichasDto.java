/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

/**
 *
 * @author guerreropa
 */
public class CmtFiltroPrefichasDto {
    
    
    private Integer aptos;
    private Integer locales;
    private Integer oficinas;
    private String distribucion;
    private String estrato;
    
    public CmtFiltroPrefichasDto(){
        
    }

    public Integer getAptos() {
        return aptos;
    }

    public void setAptos(Integer aptos) {
        this.aptos = aptos;
    }

    public Integer getLocales() {
        return locales;
    }

    public void setLocales(Integer locales) {
        this.locales = locales;
    }

    public Integer getOficinas() {
        return oficinas;
    }

    public void setOficinas(Integer oficinas) {
        this.oficinas = oficinas;
    }

    public String getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(String distribucion) {
        this.distribucion = distribucion;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }
}
