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
public class ResponseCiaAscensoresList extends BaseResponse {

    @XmlElement
    private List<ResponseDataCiaAscensores> listCiaAscensores = new ArrayList<ResponseDataCiaAscensores>();

    public void cargarListaRespuesta(ArrayList parListCiaAscensores) {
        if (parListCiaAscensores.size() > 0) {
            for (Object linea : parListCiaAscensores) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataCiaAscensores responseDataCiaAscensores = new ResponseDataCiaAscensores(aux);
                if (responseDataCiaAscensores.getCodigo() != null
                        && !responseDataCiaAscensores.getCodigo().equals("")) {
                    listCiaAscensores.add(responseDataCiaAscensores);
                }
            }
        }
    }

    public List<ResponseDataCiaAscensores> getListCiaAscensores() {
        return listCiaAscensores;
    }

    public void setListCiaAscensores(List<ResponseDataCiaAscensores> listCiaAscensores) {
        this.listCiaAscensores = listCiaAscensores;
    }
}
