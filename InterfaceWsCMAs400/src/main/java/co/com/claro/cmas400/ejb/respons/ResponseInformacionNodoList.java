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
public class ResponseInformacionNodoList extends BaseResponse {

    @XmlElement
    private List<ResponseDataInformacionNodo> listInformacionNodo = new ArrayList<ResponseDataInformacionNodo>();

    public void cargarListaRespuesta(ArrayList parListInformacionNodo)  {
        if (parListInformacionNodo.size() > 0) {
            for (Object linea : parListInformacionNodo) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataInformacionNodo responseDataInformacionNodo = new ResponseDataInformacionNodo(aux);
                if (responseDataInformacionNodo.getCodigo() != null
                        && !responseDataInformacionNodo.getCodigo().equals("")) {
                    listInformacionNodo.add(responseDataInformacionNodo);
                }
            }
        }
    }

    public List<ResponseDataInformacionNodo> getListInformacionNodo() {
        return listInformacionNodo;
    }

    public void setListInformacionNodo(List<ResponseDataInformacionNodo> listInformacionNodo) {
        this.listInformacionNodo = listInformacionNodo;
    }
}
