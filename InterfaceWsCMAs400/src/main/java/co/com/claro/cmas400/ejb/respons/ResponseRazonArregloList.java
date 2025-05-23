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
public class ResponseRazonArregloList extends BaseResponse {

    @XmlElement
    private List<ResponseDataRazonArreglo> listRazonArreglo = new ArrayList<ResponseDataRazonArreglo>();

    public void cargarListaRespuesta(ArrayList parListRazonArreglo)  {
        if (parListRazonArreglo.size() > 0) {
            for (Object linea : parListRazonArreglo) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataRazonArreglo responseDataRazonArreglo = new ResponseDataRazonArreglo(aux);
                if (responseDataRazonArreglo.getCodigo() != null
                        && !responseDataRazonArreglo.getCodigo().equals("")) {
                    listRazonArreglo.add(responseDataRazonArreglo);
                }
            }
        }
    }

    public List<ResponseDataRazonArreglo> getListRazonArreglo() {
        return listRazonArreglo;
    }

    public void setListRazonArreglo(List<ResponseDataRazonArreglo> listRazonArreglo) {
        this.listRazonArreglo = listRazonArreglo;
    }
}
