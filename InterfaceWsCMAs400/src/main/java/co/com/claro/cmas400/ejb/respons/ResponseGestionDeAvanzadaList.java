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
public class ResponseGestionDeAvanzadaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataGestionDeAvanzada> listGestionDeAvanzada = new ArrayList<ResponseDataGestionDeAvanzada>();

    public void cargarListaRespuesta(ArrayList parListGestionDeAvanzada)  {
        if (parListGestionDeAvanzada.size() > 0) {
            for (Object linea : parListGestionDeAvanzada) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataGestionDeAvanzada responseDataGestionDeAvanzada = new ResponseDataGestionDeAvanzada(aux);
                if (responseDataGestionDeAvanzada.getCodigoEdificio() != null
                        && !responseDataGestionDeAvanzada.getCodigoEdificio().equals("")) {
                    listGestionDeAvanzada.add(responseDataGestionDeAvanzada);
                }
            }
        }
    }

    public List<ResponseDataGestionDeAvanzada> getListGestionDeAvanzada() {
        return listGestionDeAvanzada;
    }

    public void setListGestionDeAvanzada(List<ResponseDataGestionDeAvanzada> listGestionDeAvanzada) {
        this.listGestionDeAvanzada = listGestionDeAvanzada;
    }
}
