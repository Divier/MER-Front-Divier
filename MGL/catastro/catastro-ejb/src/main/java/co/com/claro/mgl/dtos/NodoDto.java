/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

/**
 *
 * @author ADMIN
 */
public class NodoDto {
    private String codigo;
    private boolean activado;
    private String placaRr;
    private String tipoRed;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public String getPlacaRr() {
        return placaRr;
    }

    public void setPlacaRr(String placaRr) {
        this.placaRr = placaRr;
    }

    public String getTipoRed() {
        return tipoRed;
    }

    public void setTipoRed(String tipoRed) {
        this.tipoRed = tipoRed;
    }
    
    
}
