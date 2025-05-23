/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direcciones.business;

import co.com.claro.direcciones.entities.*;
import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase NegocioNodo
 *
 * @author Davide Marangoni
 * @version version 1.2
 * @date 2013/09/06
 */
public class NegocioNodo {

    private static final Logger LOGGER = LogManager.getLogger(NegocioNodo.class);

    public List<Nodo> consultaNodoGeopolitico(String pais, String departamento, String ciudad, String tipo) throws ApplicationException {
        List<Nodo> listaNodos = null;
        String tipoConsulta = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            if (tipo.toUpperCase().equals("ND1")) {
                tipoConsulta = "ND1";
            } else if (tipo.toUpperCase().equals("ND2")) {
                tipoConsulta = "ND2";
            } else {
                tipoConsulta = "ND3";
            }
            DataList list = adb.outDataList(tipoConsulta, pais, departamento, ciudad);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listaNodos = new ArrayList<Nodo>();
                for (DataObject obj : list.getList()) {
                    String valor = obj.getString("NODO");
                    if (valor != null) {
                        Nodo nudo = new Nodo();
                        nudo.setNodo(valor);
                        listaNodos.add(nudo);
                    }
                }
            }
            return listaNodos;
        } catch ( ExceptionDB e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la información del nodo. EX000: " + e.getMessage(), e);
        }
    }
}
