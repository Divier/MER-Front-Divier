/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtFlujosInicialesCm;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtFlujosInicialesCmDaoImpl extends GenericDaoImpl<CmtFlujosInicialesCm> {
       List<CmtFlujosInicialesCm> listaCmtFlujosInicialesCm = new ArrayList<>();
     public List<CmtFlujosInicialesCm> getEstadosIniCMByEstadoxFlujoId(CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) throws ApplicationException {
      
            try {

            String queryStr = "SELECT c FROM CmtFlujosInicialesCm c "
                    + " WHERE c.idEstadoFlujo.estadoxFlujoId = :cmtEstadoxFlujoMgl  "
                    + " AND c.estadoRegistro = :estadoRegistro  ";

            Query query = entityManager.createQuery(queryStr);
            query.setParameter("estadoRegistro", 1);

            if (cmtEstadoxFlujoMgl != null) {
                query.setParameter("cmtEstadoxFlujoMgl", cmtEstadoxFlujoMgl.getEstadoxFlujoId());
            }

            listaCmtFlujosInicialesCm = (List<CmtFlujosInicialesCm>) query.getResultList();
            return listaCmtFlujosInicialesCm;
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de consultar los estados por flujo. EX000: " + e.getMessage(), e);
        }
        
    }
}
