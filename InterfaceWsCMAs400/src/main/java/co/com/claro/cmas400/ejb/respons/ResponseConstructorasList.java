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
public class ResponseConstructorasList extends BaseResponse {

    @XmlElement
    private List<ResponseDataConstructoras> listConstructoras =
            new ArrayList<ResponseDataConstructoras>();

    public void cargarListaRespuesta(ArrayList parListConstructoras)
             {
        if (parListConstructoras.size() > 0) {
            for (Object linea : parListConstructoras) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataConstructoras responseDataConstructoras =
                        new ResponseDataConstructoras(aux);
                if (responseDataConstructoras.getCodigo() != null
                        && !responseDataConstructoras.getCodigo().equals("")) {
                    listConstructoras.add(responseDataConstructoras);
                }
            }
        }
    }

    public List<ResponseDataConstructoras> getListConstructoras() {
        return listConstructoras;
    }

    public void setListConstructoras(List<ResponseDataConstructoras> listConstructoras) {
        this.listConstructoras = listConstructoras;
    }
}
