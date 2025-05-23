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
 * @author camargomf
 */
@XmlRootElement
public class ResponseTipoTrabajoList extends BaseResponse {

    @XmlElement
    private List<ResponseDataTipoTrabajo> listTipoTrabajo =
            new ArrayList<ResponseDataTipoTrabajo>();

    public void cargarListaRespuesta(ArrayList parListTipoTrabajo)
             {
        if (parListTipoTrabajo.size() > 0) {
            for (Object linea : parListTipoTrabajo) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataTipoTrabajo responseDataTipoTrabajo =
                        new ResponseDataTipoTrabajo(aux);
                if (responseDataTipoTrabajo.getCodigo() != null
                        && !responseDataTipoTrabajo.getCodigo().equals("")) {
                    listTipoTrabajo.add(responseDataTipoTrabajo);
                }
            }
        }
    }

    public List<ResponseDataTipoTrabajo> getListTipoTrabajo() {
        return listTipoTrabajo;
    }

    public void setListTipoTrabajo(List<ResponseDataTipoTrabajo> listTipoTrabajo) {
        this.listTipoTrabajo = listTipoTrabajo;
    }
}
