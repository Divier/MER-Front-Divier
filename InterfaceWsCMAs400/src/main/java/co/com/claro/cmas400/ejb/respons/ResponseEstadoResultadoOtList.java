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
public class ResponseEstadoResultadoOtList extends BaseResponse {

    @XmlElement
    private List<ResponseDataEstadoResultadoOt> listEstadoResultadoOt =
            new ArrayList<ResponseDataEstadoResultadoOt>();

    public void cargarListaRespuesta(ArrayList parListEstadoResultadoOt)
             {
        if (parListEstadoResultadoOt.size() > 0) {
            for (Object linea : parListEstadoResultadoOt) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataEstadoResultadoOt responseDataEstadoResultadoOt =
                        new ResponseDataEstadoResultadoOt(aux);
                if (responseDataEstadoResultadoOt.getCodigo() != null
                        && !responseDataEstadoResultadoOt.getCodigo().equals("")) {
                    listEstadoResultadoOt.add(responseDataEstadoResultadoOt);
                }
            }
        }
    }

    public List<ResponseDataEstadoResultadoOt> getListEstadoResultadoOt() {
        return listEstadoResultadoOt;
    }

    public void setListEstadoResultadoOt(List<ResponseDataEstadoResultadoOt> listEstadoResultadoOt) {
        this.listEstadoResultadoOt = listEstadoResultadoOt;
    }
}
