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
public class ResponseManttoEstadoEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoEstadoEdificio> listEstadoEdificio =
            new ArrayList<ResponseDataManttoEstadoEdificio>();

    public void cargarListaRespuesta(ArrayList parListEstadoEdificio)
             {
        if (parListEstadoEdificio.size() > 0) {
            for (Object linea : parListEstadoEdificio) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoEstadoEdificio responseDataEstadoEdificio =
                        new ResponseDataManttoEstadoEdificio(aux);
                if (responseDataEstadoEdificio.getCodigo() != null
                        && !responseDataEstadoEdificio.getCodigo().equals("")) {
                    listEstadoEdificio.add(responseDataEstadoEdificio);
                }
            }
        }
    }

    public List<ResponseDataManttoEstadoEdificio> getListEstadoEdificio() {
        return listEstadoEdificio;
    }

    public void setListEstadoEdificio(
            List<ResponseDataManttoEstadoEdificio> listEstadoEdificio) {
        this.listEstadoEdificio = listEstadoEdificio;
    }
}
