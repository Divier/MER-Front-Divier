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
public class ResponseConsultaPorInventarioEquipoList extends BaseResponse {
    
    @XmlElement
    private String codigoEdificio = "";
    @XmlElement
    private List<ResponseDataConsultaPorInventario> listConsultaPorInventario = new ArrayList<ResponseDataConsultaPorInventario>();

    public void cargarListaRespuesta(String parListConsultaPorInventario) {
        if (parListConsultaPorInventario.length() > 0) {
            
            ResponseDataConsultaPorInventario responseDataConsultaPorInventario =
                    new ResponseDataConsultaPorInventario(parListConsultaPorInventario);
            
            listConsultaPorInventario.add(responseDataConsultaPorInventario);
        }
    }

    public String getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(String codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
    }

    public List<ResponseDataConsultaPorInventario> getListConsultaPorInventario() {
        return listConsultaPorInventario;
    }

    public void setListConsultaPorInventario(List<ResponseDataConsultaPorInventario> listConsultaPorInventario) {
        this.listConsultaPorInventario = listConsultaPorInventario;
    }
}
