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
public class ResponseManttoTipoTrabajoList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoTrabajo> listManttoTrabajo = new ArrayList<ResponseDataManttoTrabajo>();

    public void cargarListaRespuesta(ArrayList parListManttoTrabajo)  {
        if (parListManttoTrabajo.size() > 0) {
            for (Object linea : parListManttoTrabajo) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoTrabajo responseDataManttoTrabajo = new ResponseDataManttoTrabajo(aux);
                if (responseDataManttoTrabajo.getCodigo() != null
                        && !responseDataManttoTrabajo.getCodigo().equals("")) {
                    listManttoTrabajo.add(responseDataManttoTrabajo);
                }
            }
        }
    }

    public List<ResponseDataManttoTrabajo> getListManttoTrabajo() {
        return listManttoTrabajo;
    }

    public void setListManttoTrabajo(List<ResponseDataManttoTrabajo> listManttoTrabajo) {
        this.listManttoTrabajo = listManttoTrabajo;
    }
}
