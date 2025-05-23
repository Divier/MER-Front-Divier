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
public class ResponseManttoEstratoList extends BaseResponse {

    @XmlElement
    private List<ResponseDataEstrato> listEstrato = new ArrayList<ResponseDataEstrato>();

    public void cargarListaRespuesta(ArrayList parListEstrato)  {
        if (parListEstrato.size() > 0) {
            for (Object linea : parListEstrato) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataEstrato responseDataEstrato = new ResponseDataEstrato(aux);
                if (responseDataEstrato.getCodigo() != null
                        && !responseDataEstrato.getCodigo().equals("")) {
                    listEstrato.add(responseDataEstrato);
                }
            }
        }
    }

    public List<ResponseDataEstrato> getListEstrato() {
        return listEstrato;
    }

    public void setListEstrato(List<ResponseDataEstrato> listEstrato) {
        this.listEstrato = listEstrato;
    }
}
