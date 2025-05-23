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
public class ResponseProveedoresList extends BaseResponse {

    @XmlElement
    private List<ResponseDataProveedores> listProductos =
            new ArrayList<ResponseDataProveedores>();

    public void cargarListaRespuesta(ArrayList parListProveedores)
             {
        if (parListProveedores.size() > 0) {
            for (Object linea : parListProveedores) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataProveedores responseDataProveedores =
                        new ResponseDataProveedores(aux);
                if (responseDataProveedores.getCodigo() != null
                        && !responseDataProveedores.getCodigo().equals("")) {
                    listProductos.add(responseDataProveedores);
                }
            }
        }
    }

    public List<ResponseDataProveedores> getListProductos() {
        return listProductos;
    }

    public void setListProductos(List<ResponseDataProveedores> listProductos) {
        this.listProductos = listProductos;
    }
}
