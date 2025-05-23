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
public class ResponseConsultaUnidadesList extends BaseResponse {

    @XmlElement
    private List<ResponseDataConsultaUnidades> listConsultaUnidades = 
            new ArrayList<ResponseDataConsultaUnidades>();

    public void cargarListaRespuesta(ArrayList parListAdminCompany) {
        if (parListAdminCompany.size() > 0) {
            for (Object linea : parListAdminCompany) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataConsultaUnidades responseDataConsultaUnidades = 
                        new ResponseDataConsultaUnidades(aux);
                if (responseDataConsultaUnidades.getDireccion() != null
                        && !responseDataConsultaUnidades.getDireccion().equals("")) {
                    listConsultaUnidades.add(responseDataConsultaUnidades);
                }
            }
        }
    }

    public List<ResponseDataConsultaUnidades> getListConsultaUnidades() {
        return listConsultaUnidades;
    }

    public void setListConsultaUnidades(List<ResponseDataConsultaUnidades> listConsultaUnidades) {
        this.listConsultaUnidades = listConsultaUnidades;
    }
}
