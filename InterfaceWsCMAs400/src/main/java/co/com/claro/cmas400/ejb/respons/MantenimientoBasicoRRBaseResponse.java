/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;


/**
 *
 * @author Orlando Velasquez
 */
public class MantenimientoBasicoRRBaseResponse{

    private String CodigoDeRespuesta;
    private String MensajeDeRespuesta;

    public String getCodigoDeRespuesta() {
        return CodigoDeRespuesta;
    }

    public void setCodigoDeRespuesta(String CodigoDeRespuesta) {
        this.CodigoDeRespuesta = CodigoDeRespuesta;
    }

    public String getMensajeDeRespuesta() {
        return MensajeDeRespuesta;
    }

    public void setMensajeDeRespuesta(String MensajeDeRespuesta) {
        this.MensajeDeRespuesta = MensajeDeRespuesta;
    }

    

    public MantenimientoBasicoRRBaseResponse() {

    }

}
