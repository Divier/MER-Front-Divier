package co.com.claro.cmas400.ejb.respons;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement
public class ResponseManttoSubEdificiosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoSubEdificios> listManttSubEdificios =
            new ArrayList<ResponseDataManttoSubEdificios>();

    public void cargarListaRespuesta(ArrayList parlistManttoSubEdificios)
             {
        if (parlistManttoSubEdificios.size() > 0) {
            for (Object linea : parlistManttoSubEdificios) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoSubEdificios responseDataManttoSubEdificios =
                        new ResponseDataManttoSubEdificios(aux);
                if (responseDataManttoSubEdificios.getCodigo() != null
                        && !responseDataManttoSubEdificios.getCodigo().equals("")) {
                    listManttSubEdificios.add(responseDataManttoSubEdificios);
                }
            }
        }
    }

    public List<ResponseDataManttoSubEdificios> getListManttSubEdificios() {
        return listManttSubEdificios;
    }

    public void setListManttSubEdificios(List<ResponseDataManttoSubEdificios> listManttSubEdificios) {
        this.listManttSubEdificios = listManttSubEdificios;
    }
}
