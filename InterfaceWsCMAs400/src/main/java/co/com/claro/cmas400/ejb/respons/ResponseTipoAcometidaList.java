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
public class ResponseTipoAcometidaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataTipoAcometida> listTipoAcometida = new ArrayList<ResponseDataTipoAcometida>();

    public void cargarListaRespuesta(ArrayList parListTipoAcometida)  {
        if (parListTipoAcometida.size() > 0) {
            for (Object linea : parListTipoAcometida) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataTipoAcometida responseDataTipoAcometida = new ResponseDataTipoAcometida(aux);
                if (responseDataTipoAcometida.getCodigo() != null
                        && !responseDataTipoAcometida.getCodigo().equals("")) {
                    listTipoAcometida.add(responseDataTipoAcometida);
                }
            }
        }
    }

    public List<ResponseDataTipoAcometida> getListTipoAcometida() {
        return listTipoAcometida;
    }

    public void setListTipoAcometida(List<ResponseDataTipoAcometida> listTipoAcometida) {
        this.listTipoAcometida = listTipoAcometida;
    }
}
