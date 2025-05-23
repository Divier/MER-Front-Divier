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
public class ResponseInformacionVtList extends BaseResponse {

    @XmlElement
    private List<ResponseDataInformacionVt> listInformacionVt = new ArrayList<ResponseDataInformacionVt>();

    public void cargarListaRespuesta(ArrayList parListInformacionVt)  {
        if (parListInformacionVt.size() > 0) {
            for (Object linea : parListInformacionVt) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataInformacionVt responseDataInformacionVt = new ResponseDataInformacionVt(aux);
                if (responseDataInformacionVt.getNumTrabajo() != null
                        && !responseDataInformacionVt.getNumTrabajo().equals("")) {
                    listInformacionVt.add(responseDataInformacionVt);
                }
            }
        }
    }

    public List<ResponseDataInformacionVt> getListInformacionVt() {
        return listInformacionVt;
    }

    public void setLisInformacionVt(List<ResponseDataInformacionVt> listInformacionVt) {
        this.listInformacionVt = listInformacionVt;
    }
}
