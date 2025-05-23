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
public class ResponseOrigenDatosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataOrigenDatos> listOrigenDatos = new ArrayList<ResponseDataOrigenDatos>();

    public void cargarListaRespuesta(ArrayList parListOrigenDatos) {
        if (parListOrigenDatos.size() > 0) {
            for (Object linea : parListOrigenDatos) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataOrigenDatos responseDataOrigenDatos = new ResponseDataOrigenDatos(aux);
                if (responseDataOrigenDatos.getCodigo() != null
                        && !responseDataOrigenDatos.getCodigo().equals("")) {
                    listOrigenDatos.add(responseDataOrigenDatos);
                }
            }
        }
    }

    public List<ResponseDataOrigenDatos> getListOrigenDatos() {
        return listOrigenDatos;
    }

    public void setListOrigenDatos(List<ResponseDataOrigenDatos> listOrigenDatos) {
        this.listOrigenDatos = listOrigenDatos;
    }
}
