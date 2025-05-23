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
public class ResponseManttoMaterialProveedorList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoMaterialProveedor> listManttoMaterialProveedor =
            new ArrayList<ResponseDataManttoMaterialProveedor>();

    public void cargarListaRespuesta(ArrayList parListManttoMaterialProveedor)
             {
        if (parListManttoMaterialProveedor.size() > 0) {
            for (Object linea : parListManttoMaterialProveedor) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoMaterialProveedor responseDataManttoMaterialProveedor =
                        new ResponseDataManttoMaterialProveedor(aux);
                if (responseDataManttoMaterialProveedor.getCodigoProveedor()!= null
                        && !responseDataManttoMaterialProveedor.getCodigoProveedor().equals("")) {
                    listManttoMaterialProveedor.add(responseDataManttoMaterialProveedor);
                }
            }
        }
    }

    public List<ResponseDataManttoMaterialProveedor> getListManttoMaterialProveedor() {
        return listManttoMaterialProveedor;
    }

    public void setListManttoMaterialProveedor(List<ResponseDataManttoMaterialProveedor> listManttoMaterialProveedor) {
        this.listManttoMaterialProveedor = listManttoMaterialProveedor;
    }
}
