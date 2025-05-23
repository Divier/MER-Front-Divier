/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

/**
 *
 * @author cardenaslb
 */
public class FiltroConsultaInventarioTecDto {

    private String tipoInv;
    private String claseInv;
    private String fabricante;
    private String serial;
    private String referencia;
    private String mac;
    private int estado;
    private String torre;
    private String tecnologia;

    public String getTipoInv() {
        return tipoInv;
    }

    public void setTipoInv(String tipoInv) {
        this.tipoInv = tipoInv;
    }

    public String getClaseInv() {
        return claseInv;
    }

    public void setClaseInv(String claseInv) {
        this.claseInv = claseInv;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

}
