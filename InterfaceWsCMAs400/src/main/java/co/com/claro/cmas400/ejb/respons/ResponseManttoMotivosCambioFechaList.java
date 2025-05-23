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
public class ResponseManttoMotivosCambioFechaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataMotivosCambioFecha> listMotivosCambioFecha = 
            new ArrayList<ResponseDataMotivosCambioFecha>();

    public void cargarListaRespuesta(ArrayList parListMotivosCambioFecha)  {
        if (parListMotivosCambioFecha.size() > 0) {
            for (Object linea : parListMotivosCambioFecha) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataMotivosCambioFecha responseDataMotivosCambioFecha = 
                        new ResponseDataMotivosCambioFecha(aux);
                if (responseDataMotivosCambioFecha.getCodigo() != null
                        && !responseDataMotivosCambioFecha.getCodigo().equals("")) {
                    listMotivosCambioFecha.add(responseDataMotivosCambioFecha);
                }
            }
        }
    }

    public List<ResponseDataMotivosCambioFecha> getListMotivosCambioFecha() {
        return listMotivosCambioFecha;
    }

    public void setListMotivosCambioFecha(List<ResponseDataMotivosCambioFecha> listMotivosCambioFecha) {
        this.listMotivosCambioFecha = listMotivosCambioFecha;
    }
}
