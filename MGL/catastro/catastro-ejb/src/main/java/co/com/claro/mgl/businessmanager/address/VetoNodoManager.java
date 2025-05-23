/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.VetoNodoDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.VetoNodo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public class VetoNodoManager {

    public boolean vetarNodos(List<NodoMgl> nodos, Date iniVeto, Date finVeto, String politica, String correo) throws ApplicationException {
        VetoNodoDaoImpl vetoNodoDaoImpl = new VetoNodoDaoImpl();

        return vetoNodoDaoImpl.vetarNodos(nodos, iniVeto, finVeto, politica, correo);
    }

    public boolean isNodoVetado(String nodo) throws ApplicationException{
        boolean isVetado = true;
        List<VetoNodo> resultList;
        VetoNodoDaoImpl vetoNodoDaoImpl = new VetoNodoDaoImpl();
        resultList = vetoNodoDaoImpl.validaNodoVetado(nodo);
        if (resultList != null && !resultList.isEmpty()){
            isVetado = true;
        }else{
            isVetado = false;
        }
        return isVetado;
    }
    
    public List<VetoNodo> getVetosActivos() throws ApplicationException{
        List<VetoNodo> resultList;
        VetoNodoDaoImpl vetoNodoDaoImpl = new VetoNodoDaoImpl();
        resultList = vetoNodoDaoImpl.getNodosVetados();
        return resultList;
    }
    
    public List<VetoNodo> findVetos(String politica, Date initDate, Date endDate, BigDecimal ciudad, String tipoVeto) throws ApplicationException {
        List<VetoNodo> resultList;
        VetoNodoDaoImpl vetoNodoDaoImpl = new VetoNodoDaoImpl();
        resultList = vetoNodoDaoImpl.findVetos(politica, initDate, endDate, ciudad, tipoVeto);
        return resultList;
    }
}
