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
public class ResponseConsultaInventarioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataConsultaInventario> listConsultaInventario = 
            new ArrayList<ResponseDataConsultaInventario>();

    public void cargarListaRespuesta(ArrayList parListConsultaInventario) {
        if (parListConsultaInventario.size() > 0) {
            for (Object linea : parListConsultaInventario) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataConsultaInventario responseDataConsultaInventario = 
                        new ResponseDataConsultaInventario(aux);
                if (responseDataConsultaInventario.getTipo() != null
                        && !responseDataConsultaInventario.getTipo().equals("")) {
                    listConsultaInventario.add(responseDataConsultaInventario);
                }
            }
        }
    }

    public List<ResponseDataConsultaInventario> getListConsultaInventario() {
        return listConsultaInventario;
    }

    public void setListConsultaInventario(List<ResponseDataConsultaInventario> listConsultaInventario) {
        this.listConsultaInventario = listConsultaInventario;
    }
}
