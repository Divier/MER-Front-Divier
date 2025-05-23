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
public class ResponseManttoUbicacionCajaList extends BaseResponse {

    @XmlElement
    private List<ResponseManttoUbicacionCaja> listUbicacionCaja = new ArrayList<ResponseManttoUbicacionCaja>();

    public void cargarListaRespuesta(ArrayList parListUbicacionCaja)  {
        if (parListUbicacionCaja.size() > 0) {
            for (Object linea : parListUbicacionCaja) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseManttoUbicacionCaja responseDataUbicacionCaja = new ResponseManttoUbicacionCaja(aux);
                if (responseDataUbicacionCaja.getCodigo() != null
                        && !responseDataUbicacionCaja.getCodigo().equals("")) {
                    listUbicacionCaja.add(responseDataUbicacionCaja);
                }
            }
        }
    }

    public List<ResponseManttoUbicacionCaja> getListUbicacionCaja() {
        return listUbicacionCaja;
    }

    public void setListUbicacionCaja(List<ResponseManttoUbicacionCaja> listUbicacionCaja) {
        this.listUbicacionCaja = listUbicacionCaja;
    }
}
