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
public class ResponseNotasOtCmSubEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataNotasOtCmSubEdificio> listNotasOtSubEdificio = 
            new ArrayList<ResponseDataNotasOtCmSubEdificio>();

    public void cargarListaRespuesta(ArrayList parListNotasOtSubEdificio)  {
        if (parListNotasOtSubEdificio.size() > 0) {
            for (Object linea : parListNotasOtSubEdificio) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataNotasOtCmSubEdificio responseDataNotasOtSubEdificio = 
                        new ResponseDataNotasOtCmSubEdificio(aux);
                if (responseDataNotasOtSubEdificio.getCodigoNota() != null
                        && !responseDataNotasOtSubEdificio.getCodigoNota().equals("")) {
                    listNotasOtSubEdificio.add(responseDataNotasOtSubEdificio);
                }
            }
        }
    }

    public List<ResponseDataNotasOtCmSubEdificio> getListNotasOtSubEdificio() {
        return listNotasOtSubEdificio;
    }

    public void setListNotasOtSubEdificio(List<ResponseDataNotasOtCmSubEdificio> listNotasOtSubEdificio) {
        this.listNotasOtSubEdificio = listNotasOtSubEdificio;
    }
}
