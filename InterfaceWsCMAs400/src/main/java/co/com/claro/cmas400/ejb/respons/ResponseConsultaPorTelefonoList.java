/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class ResponseConsultaPorTelefonoList extends BaseResponse {
    
    @XmlElement
    private String codigoEdificio = "";
    @XmlElement
    private List<ResponseDataConsultaPorTelefono> listConsultaPorTelefono = new ArrayList<ResponseDataConsultaPorTelefono>();

    public void cargarListaRespuesta(String parListConsultaPorTelefono) {
        if (parListConsultaPorTelefono.length() > 0) {
            
            ResponseDataConsultaPorTelefono responseDataConsultaPorTelefono =
                    new ResponseDataConsultaPorTelefono(parListConsultaPorTelefono);
            
            listConsultaPorTelefono.add(responseDataConsultaPorTelefono);
        }
    }

    public String getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(String codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
    }

    public List<ResponseDataConsultaPorTelefono> getListConsultaPorTelefono() {
        return listConsultaPorTelefono;
    }

    public void setListConsultaPorTelefono(List<ResponseDataConsultaPorTelefono> listConsultaPorTelefono) {
        this.listConsultaPorTelefono = listConsultaPorTelefono;
    }
}
