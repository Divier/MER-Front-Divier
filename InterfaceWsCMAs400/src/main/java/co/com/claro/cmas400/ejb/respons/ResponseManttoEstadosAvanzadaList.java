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
public class ResponseManttoEstadosAvanzadaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoEstadosAvanzada> listEstadosAvanzada = 
            new ArrayList<ResponseDataManttoEstadosAvanzada>();

    public void cargarListaRespuesta(ArrayList parListEstadosAvanzada)  {
        if (parListEstadosAvanzada.size() > 0) {
            for (Object linea : parListEstadosAvanzada) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoEstadosAvanzada responseDataEstadosAvanzada = 
                        new ResponseDataManttoEstadosAvanzada(aux);
                if (responseDataEstadosAvanzada.getCodigo() != null
                        && !responseDataEstadosAvanzada.getCodigo().equals("")) {
                    listEstadosAvanzada.add(responseDataEstadosAvanzada);
                }
            }
        }
    }

    public List<ResponseDataManttoEstadosAvanzada> getListEstadosAvanzada() {
        return listEstadosAvanzada;
    }

    public void setListEstadosAvanzada(List<ResponseDataManttoEstadosAvanzada> listEstadosAvanzada) {
        this.listEstadosAvanzada = listEstadosAvanzada;
    }
}
