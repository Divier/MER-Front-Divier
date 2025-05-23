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
public class ResponseTipoProyectoList extends BaseResponse {

    @XmlElement
    private List<ResponseDataTipoProyecto> listTipoProyecto = new ArrayList<ResponseDataTipoProyecto>();

    public void cargarListaRespuesta(ArrayList parListTipoProyecto)  {
        if (parListTipoProyecto.size() > 0) {
            for (Object linea : parListTipoProyecto) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataTipoProyecto responseDataTipoProyecto = new ResponseDataTipoProyecto(aux);
                if (responseDataTipoProyecto.getCodigo() != null
                        && !responseDataTipoProyecto.getCodigo().equals("")) {
                    listTipoProyecto.add(responseDataTipoProyecto);
                }
            }
        }
    }

    public List<ResponseDataTipoProyecto> getListTipoProyecto() {
        return listTipoProyecto;
    }

    public void setListTipoProyecto(List<ResponseDataTipoProyecto> listTipoProyecto) {
        this.listTipoProyecto = listTipoProyecto;
    }
}
