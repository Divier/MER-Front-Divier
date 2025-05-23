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
public class ResponseInventarioCuentaMatrizList extends BaseResponse {

    @XmlElement
    private List<ResponseDataInventarioCuentaMatriz> listConsultaInventario = 
            new ArrayList<ResponseDataInventarioCuentaMatriz>();

    public void cargarListaRespuesta(ArrayList parListConsultaInventario)  {
        if (parListConsultaInventario.size() > 0) {
            for (Object linea : parListConsultaInventario) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataInventarioCuentaMatriz responseDataConsultaInventario = 
                        new ResponseDataInventarioCuentaMatriz(aux);
                if (responseDataConsultaInventario.getCodigoSubedificio() != null
                        && !responseDataConsultaInventario.getCodigoSubedificio().equals("")) {
                    listConsultaInventario.add(responseDataConsultaInventario);
                }
            }
        }
    }

    public List<ResponseDataInventarioCuentaMatriz> getListConsultaInventario() {
        return listConsultaInventario;
    }

    public void setListConsultaInventario(List<ResponseDataInventarioCuentaMatriz> listConsultaInventario) {
        this.listConsultaInventario = listConsultaInventario;
    }
}
