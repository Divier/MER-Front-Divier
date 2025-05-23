/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxTecnologiaMgl;
import co.com.claro.mgl.utils.Constant;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtEstadoxTecnologiaMglDaoImpl extends GenericDaoImpl<CmtEstadoxTecnologiaMgl> {

    /**
     * Buscar todas las validaciones. Busca todas las validaciones de los
     * proyectos.
     *
     * @author Lenis Cardenas.
     * @version 1.0 revision 03/08/2017.
     * @return La lista de validaciones.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en
     * a ejecución de la sentencia.
     */
    public List<CmtEstadoxTecnologiaMgl> findAll() throws ApplicationException {
        List<CmtEstadoxTecnologiaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtEstadoxTecnologiaMgl.findAll");
        resultList = (List<CmtEstadoxTecnologiaMgl>) query.getResultList();
        return resultList;
    }

    public List<Object[]> getEstados() throws ApplicationException {
        List<Object[]> resultList;

        String sql = "SELECT * FROM "+Constant.MGL_DATABASE_SCHEMA+"."+ "cmt_estadoxtecnologia WHERE ROWID IN (SELECT MAX(ROWID) FROM "+Constant.MGL_DATABASE_SCHEMA+"."+ "cmt_estadoxtecnologia t GROUP BY t.basica_id_estados)";
        String sql2 = "select memberid, this, that, theother from select memberid, this, that, theother row_number() "
                + "over (partition by memberid order by this) rn from   CmtEstadoxTecnologiaMgl where rn = 1;";
        Query query = entityManager.createNativeQuery(sql);
        resultList = (List<Object[]>) query.getResultList();
        return resultList;
    }

    public List<CmtEstadoxTecnologiaMgl> findByEstado(CmtBasicaMgl basicaEstado) throws ApplicationException {
        List<CmtEstadoxTecnologiaMgl> resultList;
        String sql = "select c  from CmtEstadoxTecnologiaMgl c WHERE c.estadoRegistro=1 and c.basicaId_estados= :idEstado ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("idEstado", basicaEstado);
        resultList = (List<CmtEstadoxTecnologiaMgl>) query.getResultList();
        return resultList;
    }

}
