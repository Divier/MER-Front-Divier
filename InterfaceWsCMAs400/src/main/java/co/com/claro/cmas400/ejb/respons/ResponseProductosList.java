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
public class ResponseProductosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataProductos> listProductos =
            new ArrayList<ResponseDataProductos>();

    public void cargarListaRespuesta(ArrayList parListProductos)
             {
        if (parListProductos.size() > 0) {
            for (Object linea : parListProductos) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataProductos responseDataProductos =
                        new ResponseDataProductos(aux);
                if (responseDataProductos.getCodigo() != null
                        && !responseDataProductos.getCodigo().equals("")) {
                    listProductos.add(responseDataProductos);
                }
            }
        }
    }

    public List<ResponseDataProductos> getListProductos() {
        return listProductos;
    }

    public void setListProductos(List<ResponseDataProductos> listProductos) {
        this.listProductos = listProductos;
    }
}
