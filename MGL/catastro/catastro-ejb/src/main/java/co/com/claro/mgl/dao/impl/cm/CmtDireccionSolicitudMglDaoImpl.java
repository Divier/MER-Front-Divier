/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Dao mapeo tabla CMT_DIRECCION_SOLICITUD
 *
 * @author yimy diaz
 * @versión 1.00.0000
 */
public class CmtDireccionSolicitudMglDaoImpl
        extends GenericDaoImpl<CmtDireccionSolicitudMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtDireccionSolicitudMglDaoImpl.class);

    public List<CmtDireccionSolicitudMgl> findAll() throws ApplicationException {
        List<CmtDireccionSolicitudMgl> resultList;
        Query query =
                entityManager.createNamedQuery("CmtDireccionSolicitudMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtDireccionSolicitudMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtDireccionSolicitudMgl> findSolicitudId(BigDecimal idSolicitud) {
        List<CmtDireccionSolicitudMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "CmtDireccionSolicitudMgl c WHERE c.solicitudCmId = "
                + ":idSolicitud   ORDER BY c.tdiId DESC ");
        query.setParameter("idSolicitud", idSolicitud);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtDireccionSolicitudMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtSolicitudCmMgl> findByDrDireccion(DrDireccion drDireccion, String centroPoblado)
            throws ApplicationException {
        List<CmtSolicitudCmMgl> resultList = null;
        try {
            String querySql = "SELECT s FROM CmtSolicitudCmMgl s ";
            querySql += " JOIN  s.listCmtDireccionesMgl a ";
            querySql += "WHERE s.estadoRegistro=1 AND a.estadoRegistro=1 AND s.centroPobladoGpo.geoCodigoDane= :comunidad ";

            if (drDireccion.getIdTipoDireccion() != null 
                    && !drDireccion.getIdTipoDireccion().trim().isEmpty()) {
                querySql += "AND UPPER(a.codTipoDir) = UPPER(:idTipoDireccion) ";
            } else {
                querySql += "AND a.codTipoDir is null  ";
            }
            if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
                querySql += "AND  UPPER(a.barrio)   = UPPER(:barrio) ";
            } else {
                querySql += "AND  a.barrio is null ";
            }
            
            
            if (drDireccion.getIdTipoDireccion() != null) {
                if (drDireccion.getIdTipoDireccion().equals("CK")) {
                    if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.tipoViaPrincipal)    = UPPER(:tipoViaPrincipal) ";
                    } else {
                        querySql += "AND  a.tipoViaPrincipal is null  ";
                    }
                    if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.numViaPrincipal)    = UPPER(:numViaPrincipal) ";
                    } else {
                        querySql += "AND  a.numViaPrincipal is null  ";
                    }
                    if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.ltViaPrincipal)    = UPPER(:ltViaPrincipal) ";
                    } else {
                        querySql += "AND a.ltViaPrincipal  is null  ";
                    }
                    if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.nlPostViaP)    = UPPER(:nlPostViaP) ";
                    } else {
                        querySql += "AND  a.nlPostViaP  is null  ";
                    }
                    if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.bisViaPrincipal)    = UPPER(:bisViaPrincipal) ";
                    } else {
                        querySql += "AND  a.bisViaPrincipal  is null  ";
                    }
                    if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cuadViaPrincipal)    = UPPER(:cuadViaPrincipal)  ";
                    } else {
                        querySql += "AND  a.cuadViaPrincipal    is null  ";
                    }
                    if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.placaDireccion)    = UPPER(:placaDireccion) ";
                    } else {
                        querySql += "AND  a.placaDireccion  is null  ";
                    }
                    if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.tipoViaGeneradora)    = UPPER(:tipoViaGeneradora) ";
                    } else {
                        querySql += "AND  a.tipoViaGeneradora is null  ";
                    }
                    if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.numViaGeneradora)    = UPPER(:numViaGeneradora) ";
                    } else {
                        querySql += "AND  a.numViaGeneradora is null  ";
                    }
                    if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.ltViaGeneradora)    = UPPER(:ltViaGeneradora) ";
                    } else {
                        querySql += "AND  a.ltViaGeneradora  is null ";
                    }
                    if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.nlPostViaG)    = UPPER(:nlPostViaG) ";
                    } else {
                        querySql += "AND  a.nlPostViaG is null  ";
                    }
                    if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.bisViaGeneradora)    = UPPER(:bisViaGeneradora) ";
                    } else {
                        querySql += "AND  a.bisViaGeneradora is null  ";
                    }
                    if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cuadViaGeneradora)    = UPPER(:cuadViaGeneradora) ";
                    } else {
                        querySql += "AND  a.cuadViaGeneradora is null  ";
                    }
                }
                
                if (drDireccion.getIdTipoDireccion().equals("BM")
                        || drDireccion.getIdTipoDireccion().equals("IT")) {
                    //Direccion Manzana-Casa
                    if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel1)    = UPPER(:mzTipoNivel1) ";
                    } else {
                        querySql += "AND  a.mzTipoNivel1 is null ";
                    }
                    if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel2)    = UPPER(:mzTipoNivel2) ";
                    } else {
                        querySql += "AND  a.mzTipoNivel2  is null  ";
                    }
                    if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel3)    = UPPER(:mzTipoNivel3)  ";
                    } else {
                        querySql += "AND  a.mzTipoNivel3 is null  ";
                    }
                    if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel4)    = UPPER(:mzTipoNivel4) ";
                    } else {
                        querySql += "AND  a.mzTipoNivel4 is null ";
                    }
                    if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel5)    = UPPER(:mzTipoNivel5) ";
                    } else {
                        querySql += "AND  a.mzTipoNivel5 is null ";
                    }
                    if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel1)    = UPPER(:mzValorNivel1) ";
                    } else {
                        querySql += "AND  a.mzValorNivel1 is null ";
                    }
                    if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel2)    = UPPER(:mzValorNivel2) ";
                    } else {
                        querySql += "AND  a.mzValorNivel2  is null ";
                    }
                    if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel3)    = UPPER(:mzValorNivel3) ";
                    } else {
                        querySql += "AND  a.mzValorNivel3  is null ";
                    }
                    if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel4)    = UPPER(:mzValorNivel4) ";
                    } else {
                        querySql += "AND  a.mzValorNivel4 is null  ";
                    }
                    if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel5)    = UPPER(:mzValorNivel5) ";
                    } else {
                        querySql += "AND  a.mzValorNivel5 is null  ";
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("CK")
                        || drDireccion.getIdTipoDireccion().equals("BM")
                        || drDireccion.getIdTipoDireccion().equals("IT")) {
                    //Complemeto o intraduciple
                    if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel1)    = UPPER(:cpTipoNivel1) ";
                    } else {
                        querySql += "AND  a.cpTipoNivel1 is null  ";
                    }
                    if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel2)    = UPPER(:cpTipoNivel2) ";
                    } else {
                        querySql += "AND  a.cpTipoNivel2 is null  ";
                    }
                    if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel3)    = UPPER(:cpTipoNivel3) ";
                    } else {
                        querySql += "AND  a.cpTipoNivel3 is null  ";
                    }
                    if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel4)    = UPPER(:cpTipoNivel4) ";
                    } else {
                        querySql += "AND  a.cpTipoNivel4 is null  ";
                    }
                    if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel5)    = UPPER(:cpTipoNivel5) ";
                    } else {
                        querySql += "AND  a.cpTipoNivel5 is null  ";
                    }
                    if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel6)    = UPPER(:cpTipoNivel6) ";
                    } else {
                        querySql += "AND  a.cpTipoNivel6  is null  ";
                    }

                    if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel1)    = UPPER(:cpValorNivel1) ";
                    } else {
                        querySql += "AND  a.cpValorNivel1   is null  ";
                    }
                    if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel2)    = UPPER(:cpValorNivel2) ";
                    } else {
                        querySql += "AND  a.cpValorNivel2  is null  ";
                    }
                    if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel3)    = UPPER(:cpValorNivel3) ";
                    } else {
                        querySql += "AND  a.cpValorNivel3  is null  ";
                    }
                    if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel4)    = UPPER(:cpValorNivel4) ";
                    } else {
                        querySql += "AND  a.cpValorNivel4  is null  ";
                    }
                    if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel5)    = UPPER(:cpValorNivel5) ";
                    } else {
                        querySql += "AND  a.cpValorNivel5  is null  ";
                    }
                    if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel6)    = UPPER(:cpValorNivel6) ";
                    } else {
                        querySql += "AND  a.cpValorNivel6  is null  ";
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("IT")) {
                    if (drDireccion.getMzTipoNivel6() != null && !drDireccion.getMzTipoNivel6().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel6)    = UPPER(:mzTipoNivel6) ";
                    } else {
                        querySql += "AND  a.mzTipoNivel6 is null  ";
                    }
                    if (drDireccion.getMzValorNivel6() != null && !drDireccion.getMzValorNivel6().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel6)    = UPPER(:mzValorNivel6)  ";
                    } else {
                        querySql += "AND  a.mzValorNivel6 is null  ";
                    }
                    if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.itTipoPlaca)    = UPPER(:itTipoPlaca) ";
                    } else {
                        querySql += "AND  a.itTipoPlaca  is null  ";
                    }
                    if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.itValorPlaca)    = UPPER(:itValorPlaca) ";
                    } else {
                        querySql += "AND  a.itValorPlaca  is null ";
                    }

                }
            }

            querySql += "AND s.estadoSolicitudObj.basicaId = :estadoSolicitud";

            Query query = entityManager.createQuery(querySql);

            query.setParameter("comunidad", centroPoblado);
            
            if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio", drDireccion.getBarrio());
            }
            
            if (drDireccion.getIdTipoDireccion() != null) {
                if (!drDireccion.getIdTipoDireccion().trim().isEmpty()) {
                    query.setParameter("idTipoDireccion", drDireccion.getIdTipoDireccion());
                }

                if (drDireccion.getIdTipoDireccion().equals("CK")) {
                    //Direccion Calle - Carrera
                    if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
                        query.setParameter("tipoViaPrincipal", drDireccion.getTipoViaPrincipal());
                    }
                    if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
                        query.setParameter("numViaPrincipal", drDireccion.getNumViaPrincipal());
                    }
                    if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
                        query.setParameter("ltViaPrincipal", drDireccion.getLtViaPrincipal());
                    }
                    if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
                        query.setParameter("nlPostViaP", drDireccion.getNlPostViaP());
                    }
                    if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
                        query.setParameter("bisViaPrincipal", drDireccion.getBisViaPrincipal());
                    }
                    if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
                        query.setParameter("cuadViaPrincipal", drDireccion.getCuadViaPrincipal());
                    }
                    if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
                        query.setParameter("placaDireccion", drDireccion.getPlacaDireccion());
                    }
                    if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
                        query.setParameter("tipoViaGeneradora", drDireccion.getTipoViaGeneradora());
                    }
                    if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
                        query.setParameter("numViaGeneradora", drDireccion.getNumViaGeneradora());
                    }
                    if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
                        query.setParameter("ltViaGeneradora", drDireccion.getLtViaGeneradora());
                    }
                    if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
                        query.setParameter("nlPostViaG", drDireccion.getNlPostViaG());
                    }
                    if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
                        query.setParameter("bisViaGeneradora", drDireccion.getBisViaGeneradora());
                    }
                    if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
                        query.setParameter("cuadViaGeneradora", drDireccion.getCuadViaGeneradora());
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("BM")
                        || drDireccion.getIdTipoDireccion().equals("IT")) {
                    //Direccion Manzana-Casa
                    if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel1", drDireccion.getMzTipoNivel1());
                    }
                    if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel2", drDireccion.getMzTipoNivel2());
                    }
                    if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel3", drDireccion.getMzTipoNivel3());
                    }
                    if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel4", drDireccion.getMzTipoNivel4());
                    }
                    if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel5", drDireccion.getMzTipoNivel5());
                    }
                    if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
                        query.setParameter("mzValorNivel1", drDireccion.getMzValorNivel1());
                    }
                    if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
                        query.setParameter("mzValorNivel2", drDireccion.getMzValorNivel2());
                    }
                    if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
                        query.setParameter("mzValorNivel3", drDireccion.getMzValorNivel3());
                    }
                    if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
                        query.setParameter("mzValorNivel4", drDireccion.getMzValorNivel4());
                    }
                    if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
                        query.setParameter("mzValorNivel5", drDireccion.getMzValorNivel5());
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("CK")
                        || drDireccion.getIdTipoDireccion().equals("BM")
                        || drDireccion.getIdTipoDireccion().equals("IT")) {
                    //Complemeto o intraduciple
                    if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel1", drDireccion.getCpTipoNivel1());
                    }
                    if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel2", drDireccion.getCpTipoNivel2());
                    }
                    if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel3", drDireccion.getCpTipoNivel3());
                    }
                    if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel4", drDireccion.getCpTipoNivel4());
                    }
                    if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel5", drDireccion.getCpTipoNivel5());
                    }
                    if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel6", drDireccion.getCpTipoNivel6());
                    }
                    if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
                        query.setParameter("cpValorNivel1", drDireccion.getCpValorNivel1());
                    }
                    if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
                        query.setParameter("cpValorNivel2", drDireccion.getCpValorNivel2());
                    }
                    if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
                        query.setParameter("cpValorNivel3", drDireccion.getCpValorNivel3());
                    }
                    if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
                        query.setParameter("cpValorNivel4", drDireccion.getCpValorNivel4());
                    }
                    if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
                        query.setParameter("cpValorNivel5", drDireccion.getCpValorNivel5());
                    }
                    if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
                        query.setParameter("cpValorNivel6", drDireccion.getCpValorNivel6());
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("IT")) {
                    if (drDireccion.getMzTipoNivel6() != null && !drDireccion.getMzTipoNivel6().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel6", drDireccion.getMzTipoNivel6());
                    }
                    if (drDireccion.getMzValorNivel6() != null && !drDireccion.getMzValorNivel6().trim().isEmpty()) {
                        query.setParameter("mzValorNivel6", drDireccion.getMzValorNivel6());
                    }
                    if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
                        query.setParameter("itTipoPlaca", drDireccion.getItTipoPlaca());
                    }
                    if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
                        query.setParameter("itValorPlaca", drDireccion.getItValorPlaca());
                    }
                }
            }
            
            query.setParameter("estadoSolicitud", Constant.SOLICITUD_BASICA_ESTADO_PENDIENTE);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtSolicitudCmMgl>) query.getResultList();

        } catch (NoResultException ex) {
            resultList = new ArrayList<CmtSolicitudCmMgl>();
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        if (resultList == null) {
            resultList = new ArrayList<CmtSolicitudCmMgl>();
        }
        return resultList;
    }
}