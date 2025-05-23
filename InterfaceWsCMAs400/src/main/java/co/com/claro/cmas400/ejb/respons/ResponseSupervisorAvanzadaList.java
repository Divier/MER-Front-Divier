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
public class ResponseSupervisorAvanzadaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataSupervisorAvanzada> listSupervisorAvanzada = new ArrayList<ResponseDataSupervisorAvanzada>();

    public void cargarListaRespuesta(ArrayList parListSupervisorAvanzada)  {
        if (parListSupervisorAvanzada.size() > 0) {
            for (Object linea : parListSupervisorAvanzada) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataSupervisorAvanzada responseDataSupervisorAvanzada = new ResponseDataSupervisorAvanzada(aux);
                if (responseDataSupervisorAvanzada.getCodigo() != null
                        && !responseDataSupervisorAvanzada.getCodigo().equals("")) {
                    listSupervisorAvanzada.add(responseDataSupervisorAvanzada);
                }
            }
        }
    }

    public List<ResponseDataSupervisorAvanzada> getListSupervisorAvanzada() {
        return listSupervisorAvanzada;
    }

    public void setListSupervisorAvanzada(List<ResponseDataSupervisorAvanzada> listSupervisorAvanzada) {
        this.listSupervisorAvanzada = listSupervisorAvanzada;
    }
}
