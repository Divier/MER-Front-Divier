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
public class ResponseManttoTipoEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoTipoEdificio> listManttoTipoEdificio =
            new ArrayList<ResponseDataManttoTipoEdificio>();

    public void cargarListaRespuesta(ArrayList parListManttoTipoEdificio)
             {
        if (parListManttoTipoEdificio.size() > 0) {
            for (Object linea : parListManttoTipoEdificio) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoTipoEdificio responseDataManttoTipoEdificio =
                        new ResponseDataManttoTipoEdificio(aux);
                if (responseDataManttoTipoEdificio.getCodigo() != null
                        && !responseDataManttoTipoEdificio.getCodigo().equals("")) {
                    listManttoTipoEdificio.add(responseDataManttoTipoEdificio);
                }
            }
        }
    }

    public List<ResponseDataManttoTipoEdificio> getListManttoTipoEdificio() {
        return listManttoTipoEdificio;
    }

    public void setListManttoTipoEdificio(List<ResponseDataManttoTipoEdificio> listManttoTipoEdificio) {
        this.listManttoTipoEdificio = listManttoTipoEdificio;
    }
}
