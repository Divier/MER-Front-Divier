package co.com.claro.mgl.dao.impl.cm;


import co.com.claro.mgl.businessmanager.cm.CmtModCcmmAudMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtNodosSolicitudMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSolicitudSubEdificioMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSolictudHhppMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoSolicitudMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtValidadorDireccionesManager;
import co.com.claro.mgl.businessmanager.cm.UsuariosManager;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.CmtReporteDetalladoDto;
import co.com.claro.mgl.dtos.CmtReporteGeneralDto;
import co.com.claro.mgl.dtos.FiltroReporteSolicitudCMDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtModificacionesCcmmAudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNodosSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.StringUtils;
import co.com.telmex.catastro.data.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtSolicitudCmMglDaoImpl extends GenericDaoImpl<CmtSolicitudCmMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtSolicitudCmMglDaoImpl.class);

    /**
     * Cuenta las Solicitudes asociadas a una CM. Permite realizar el conteo de
     * las Solicitudes asociadas a una Cuenta Matriz en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz ID de la Cuenta Matriz
     * @return Solicitudes encontradas en el repositorio asociadas a una Cuenta
     * Matriz
     * @throws ApplicationException
     */
    public int getCountByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("CmtSolicitudCmMgl.getCountByCuentaMatriz");
            query.setParameter("cuentaMatriz", cuentaMatriz);
            int resutl = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
            getEntityManager().clear();
            return resutl;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca las Solicitudes asociadas a una CM.Permite realizar la busqueda de
 las Solicitudes asociadas a una Cuenta Matriz en el repositorio
 realizando paginacion de los resultados.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz ID de la Cuenta Matriz
     * @return Solicitudes encontradas en el repositorio asociadas a una Cuenta
     * Matriz
     * @throws ApplicationException
     */
    public List<CmtSolicitudCmMgl> findByCuentaMatrizPaginado( CmtCuentaMatrizMgl cuentaMatriz)
            throws ApplicationException {
        try {
            List<CmtSolicitudCmMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtSolicitudCmMgl.findByCuentaMatriz");
            query.setParameter("cuentaMatriz", cuentaMatriz);
            resultList = (List<CmtSolicitudCmMgl>) query.getResultList();
             getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<CmtSolicitudCmMgl> findAll() throws ApplicationException {
        try {
            List<CmtSolicitudCmMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtSolicitudCmMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtSolicitudCmMgl>) query.getResultList();
             getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public boolean findEstateSolicitud(DrDireccion drDireccion, String comunidad) throws ApplicationException {
        try {
            List<CmtSolicitudCmMgl> resultList;
            String querySql = "SELECT d FROM CmtSolicitudCmMgl d "
                    + " WHERE d.solicitudCmId "
                    + " IN "
                    + " ( SELECT a.idSolicitudCm FROM DrDireccion a WHERE 1 = 1 ";
            
            if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().trim().isEmpty()) {
                querySql += "AND a.idTipoDireccion = :idTipoDireccion ";
                LOGGER.error("idTipoDireccion:      ----------    X    ---------- " + drDireccion.getIdTipoDireccion());
            }
            if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
                querySql += "AND  a.barrio   = :barrio ";
            }
            if (drDireccion.getIdTipoDireccion().equals("CK")) {
                //Direccion Calle - Carrera
                if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  a.tipoViaPrincipal    = :tipoViaPrincipal ";
                    LOGGER.error("tipoViaPrincipal:      ----------    X    ---------- " + drDireccion.getTipoViaPrincipal());
                }
                if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  a.numViaPrincipal    = :numViaPrincipal ";
                    LOGGER.error("numViaPrincipal:      ----------    X    ---------- " + drDireccion.getNumViaPrincipal());
                }
                if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  a.ltViaPrincipal    = :ltViaPrincipal ";
                }
                if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
                    querySql += "AND  a.nlPostViaP    = :nlPostViaP ";
                }
                if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  a.bisViaPrincipal    = :bisViaPrincipal ";
                }
                if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
                    querySql += "AND  a.cuadViaPrincipal    = :cuadViaPrincipal  ";
                }
                if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
                    querySql += "AND  a.placaDireccion    = :placaDireccion ";
                    LOGGER.error("placaDireccion:      ----------    X    ---------- " + drDireccion.getPlacaDireccion());
                }
                //Direccion Generadora de Calle - carrera
                if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  a.tipoViaGeneradora    = :tipoViaGeneradora ";
                    LOGGER.error("tipoViaGeneradora:      ----------    X    ---------- " + drDireccion.getTipoViaGeneradora());
                }
                if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  a.numViaGeneradora    = :numViaGeneradora ";
                    LOGGER.error("numViaGeneradora:      ----------    X    ---------- " + drDireccion.getNumViaGeneradora());
                }
                if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  a.ltViaGeneradora    = :ltViaGeneradora ";
                }
                if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
                    querySql += "AND  a.nlPostViaG    = :nlPostViaG ";
                }
                if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  a.bisViaGeneradora    = :bisViaGeneradora ";
                }
                if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
                    querySql += "AND  a.cuadViaGeneradora    = :cuadViaGeneradora ";
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("BM")
                    || drDireccion.getIdTipoDireccion().equals("IT")) {
                //Direccion Manzana-Casa
                if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
                    querySql += "AND  a.mzTipoNivel1    = :mzTipoNivel1 ";
                }
                if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
                    querySql += "AND  a.mzTipoNivel2    = :mzTipoNivel2 ";
                }
                if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
                    querySql += "AND  a.mzTipoNivel3    = :mzTipoNivel3  ";
                }
                if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
                    querySql += "AND  a.mzTipoNivel4    = :mzTipoNivel4 ";
                }
                if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
                    querySql += "AND  a.mzTipoNivel5    = :mzTipoNivel5 ";
                }
                if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
                    querySql += "AND  a.mzValorNivel1    = :mzValorNivel1 ";
                }
                if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
                    querySql += "AND  a.mzValorNivel2    = :mzValorNivel2 ";
                }
                if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
                    querySql += "AND  a.mzValorNivel3    = :mzValorNivel3 ";
                }
                if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
                    querySql += "AND  a.mzValorNivel4    = :mzValorNivel4 ";
                }
                if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
                    querySql += "AND  a.mzValorNivel5    = :mzValorNivel5 ";
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("CK")
                    || drDireccion.getIdTipoDireccion().equals("BM")
                    || drDireccion.getIdTipoDireccion().equals("IT")) {
                //Complemeto o intraduciple
                if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
                    querySql += "AND  a.cpTipoNivel1    = :cpTipoNivel1 ";
                }
                if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
                    querySql += "AND  a.cpTipoNivel2    = :cpTipoNivel2 ";
                }
                if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
                    querySql += "AND  a.cpTipoNivel3    = :cpTipoNivel3 ";
                    LOGGER.error("cpTipoNivel3:      ----------    X    ---------- " + drDireccion.getCpTipoNivel3());
                }
                if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
                    querySql += "AND  a.cpTipoNivel4    = :cpTipoNivel4 ";
                }
                if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
                    querySql += "AND  a.cpTipoNivel5    = :cpTipoNivel5 ";
                }
                if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
                    querySql += "AND  a.cpTipoNivel6    = :cpTipoNivel6 ";
                }
                
                if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
                    querySql += "AND  a.cpValorNivel1    = :cpValorNivel1 ";
                    LOGGER.error("cpValorNivel1:      ----------    X    ---------- " + drDireccion.getCpValorNivel1());
                }
                if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
                    querySql += "AND  a.cpValorNivel2    = :cpValorNivel2 ";
                    LOGGER.error("cpValorNivel2:      ----------    X    ---------- " + drDireccion.getCpValorNivel2());
                }
                if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
                    querySql += "AND  a.cpValorNivel3    = :cpValorNivel3 ";
                    LOGGER.error("cpValorNivel3:      ----------    X    ---------- " + drDireccion.getCpValorNivel3());
                }
                if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
                    querySql += "AND  a.cpValorNivel4    = :cpValorNivel4 ";
                    LOGGER.error("cpValorNivel4:      ----------    X    ---------- " + drDireccion.getCpValorNivel4());
                }
                if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
                    querySql += "AND  a.cpValorNivel5    = :cpValorNivel5 ";
                    LOGGER.error("cpValorNivel5:      ----------    X    ---------- " + drDireccion.getCpValorNivel5());
                }
                if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
                    querySql += "AND  a.cpValorNivel6    = :cpValorNivel6 ";
                    LOGGER.error("cpValorNivel6:      ----------    X    ---------- " + drDireccion.getCpValorNivel6());
                }
            }
            if (drDireccion.getIdTipoDireccion().equals("IT")) {
                if (drDireccion.getMzTipoNivel6() != null && !drDireccion.getMzTipoNivel6().trim().isEmpty()) {
                    querySql += "AND  a.mzTipoNivel6    = :mzTipoNivel6 ";
                }
                if (drDireccion.getMzValorNivel6() != null && !drDireccion.getMzValorNivel6().trim().isEmpty()) {
                    querySql += "AND  a.mzValorNivel6    = :mzValorNivel6  ";
                }
                if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
                    querySql += "AND  a.itTipoPlaca    = :itTipoPlaca ";
                }
                if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
                    querySql += "AND  a.itValorPlaca    = :itValorPlaca ";
                }
            }
            if (drDireccion.getEstadoDirGeo() != null && !drDireccion.getEstadoDirGeo().trim().isEmpty()) {
                querySql += "AND  a.estadoDirGeo    = :estadoDirGeo ";
                LOGGER.error("getEstadoDirGeo:      ----------    X    ---------- " + drDireccion.getEstadoDirGeo());
            }
            
            querySql += "  )  ";
            querySql += "AND d.estadoSolicitud = 'PENDIENTE' ";
            LOGGER.error("querySql  ................    °°°°°°°°°°°  .......  " + querySql);
            
            Query query = entityManager.createQuery(querySql);
            
            if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion", drDireccion.getIdTipoDireccion());
            }
            if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio", drDireccion.getBarrio());
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
                //Direccion Generadora de Calle - carrera
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
            if (drDireccion.getEstadoDirGeo() != null && !drDireccion.getEstadoDirGeo().trim().isEmpty()) {
                query.setParameter("estadoDirGeo", drDireccion.getEstadoDirGeo());
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            getEntityManager().clear();
            resultList = (List<CmtSolicitudCmMgl>) query.getResultList();
            if (resultList == null || resultList.isEmpty()) {
                return false;
            } else {
                return true;  // Tiene Solicitud pendiente...
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
        
    }
    
    public Long countAllItemsSolCM() throws ApplicationException {
        try {
            Query q = entityManager.createQuery("SELECT COUNT(1) FROM CmtSolicitudCmMgl t "
                    + " WHERE t.estadoSolicitud = :estadoSolicitud ");
            q.setParameter("estadoSolicitud", "PENDIENTE");
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultCount = (Long) q.getSingleResult();
             getEntityManager().clear();
            return resultCount;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<CmtSolicitudCmMgl> findItemsPaginacionSolCM(int firstResult,
            int maxResults, BigDecimal solicitudCmId)
            throws ApplicationException {
        try {
            List<CmtSolicitudCmMgl> resultList;
            String sq = "SELECT t FROM CmtSolicitudCmMgl t WHERE ";
            if (solicitudCmId == null) {
                sq += "t.estadoSolicitud = :estadoSolicitud ";
            } else {
                sq += " t.solicitudCmId = :solicitudCmId ";
            }
            sq += " ORDER BY t.fechaCreacion ";
            
            Query q = entityManager.createQuery(sq);
            
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            if (solicitudCmId == null) {
                q.setParameter("estadoSolicitud", "PENDIENTE");
            } else {
                q.setParameter("solicitudCmId", solicitudCmId);
            }
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
             getEntityManager().clear();
            resultList = (List<CmtSolicitudCmMgl>) q.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<CmtSolicitudCmMgl> findComunityPaginacionSolCM(int firstResult,
            int maxResults, String comunidad)
            throws ApplicationException {
        try {
            List<CmtSolicitudCmMgl> resultList;
            Query q = entityManager.createQuery("SELECT t FROM CmtSolicitudCmMgl t, "
                    + "CmtDireccionSolicitudMgl d  "
                    + "WHERE  t.estadoSolicitud = :estadoSolicitud  "
                    + "AND  t.solicitudCmId  = d.solicitudCmId  "
                    + "AND  d.comunidad = :comunidad "
                    + "ORDER BY t.fechaCreacion ");
            
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            q.setParameter("estadoSolicitud", "PENDIENTE");
            q.setParameter("comunidad", comunidad);
            
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtSolicitudCmMgl>) q.getResultList();
             getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<CmtSolicitudCmMgl> findComunityDateTodaySolCM(
            String comunidad, Date fechaHoy) throws ApplicationException {
        try {
            List<CmtSolicitudCmMgl> resultList;
            String qs = "SELECT t FROM CmtSolicitudCmMgl t, CmtDireccionSolicitudMgl d  "
                    + "WHERE ( t.fechaCreacion BETWEEN :fechaCreacionI and  :fechaCreacionF )    "
                    + "AND  t.solicitudCmId  = d.solicitudCmId  ";
            if (comunidad != null) {
                qs += "AND  d.comunidad = :comunidad ";
            }
            Query q = entityManager.createQuery(qs);
            if (comunidad != null) {
                q.setParameter("comunidad", comunidad);
            }
            q.setParameter("fechaCreacionI", fechaHoy);
            q.setParameter("fechaCreacionF", new Date(fechaHoy.getTime() + (1000 * 60 * 60 * 24)));
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
             getEntityManager().clear();
            resultList = (List<CmtSolicitudCmMgl>) q.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta las Solicitudes por filtro.Permite realizar el conteo de las
 solicitudes por los parametros de comunidad, division, segmento y lista
 de tipos de solicitudes.
     *
     * @author Johnnatan Ortiz
     * @param division division de las solicitudes
     * @param comunidad comunidad de las solicitudes
     * @param segmento segmento de las solicitudes
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param llamada
     * @param estadSolicitudList lista de los estados de las solicitudes a
     * consultar
     * @return numero de solicitudes
     * @throws ApplicationException
     */
    public int getCountByFiltroParaGestion(String division,
            String comunidad,
            CmtBasicaMgl segmento,
            List<BigDecimal> tipoSolicitudList,
            List<BigDecimal> estadSolicitudList,
            boolean llamada) throws ApplicationException {
        try {
            String queryStr = "SELECT COUNT(1) FROM CmtSolicitudCmMgl s WHERE s.estadoRegistro = :estadoRegistro";
            
            if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
                queryStr += " AND s.tipoSolicitudObj.tipoSolicitudId IN :tipoSolicitudList";
            }
            if (estadSolicitudList != null && !estadSolicitudList.isEmpty()) {
                queryStr += " AND s.estadoSolicitudObj.basicaId IN :estadoSolicitudList";
            }
            if (division != null && !division.trim().isEmpty()) {
                queryStr += " AND s.departamentoGpo.gpoId = :division";
            }
            if (comunidad != null && !comunidad.trim().isEmpty()) {
                queryStr += " AND s.ciudadGpo.gpoId = :comunidad";
            }
            if (segmento != null && segmento.getBasicaId() != null) {
                queryStr += " AND s.tipoSegmento = :tipoSegmento ";
            }
            if (llamada) {
                queryStr += " AND s.numeroIntentosLlamada > 0 ";
            }
            Query query = entityManager.createQuery(queryStr);
            
            query.setParameter("estadoRegistro", 1);
            
            if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
                query.setParameter("tipoSolicitudList", tipoSolicitudList);
            }
            if (estadSolicitudList != null && !estadSolicitudList.isEmpty()) {
                query.setParameter("estadoSolicitudList", estadSolicitudList);
            }
            if (division != null && !division.trim().isEmpty()) {
                query.setParameter("division", new BigDecimal(division));
            }
            if (comunidad != null && !comunidad.trim().isEmpty()) {
                query.setParameter("comunidad", new BigDecimal(comunidad));
            }
            if (segmento != null && segmento.getBasicaId() != null) {
                query.setParameter("tipoSegmento", segmento);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            int result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene las Solicitudes por filtro.Permite realizar el conteo de las
 solicitudes por los parametros de comunidad, division, segmento y lista
 de tipos de solicitudes.
     *
     * @author Johnnatan Ortiz
     * @param firstResult pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param departamento
     * @param ciudad
     * @param segmento segmento de las solicitudes
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param ordenMayorMenor
     * @param estadSolicitudList lista de los estados de las solicitudes a
     * consultar
     * @param llamada
     * @return Solicitudes
     * @throws ApplicationException
     */
    public List<CmtSolicitudCmMgl> findByFiltroParaGestionPaginacion(int firstResult,
            int maxResults,
            String departamento,
            String ciudad,
            CmtBasicaMgl segmento,
            List<BigDecimal> tipoSolicitudList,
            List<BigDecimal> estadSolicitudList,
            boolean llamada, boolean ordenMayorMenor) throws ApplicationException {
        try {
            List<CmtSolicitudCmMgl> resultList;
            
            String queryStr = "SELECT s FROM CmtSolicitudCmMgl s WHERE s.estadoRegistro = :estadoRegistro";
            
            if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
                queryStr += " AND s.tipoSolicitudObj.tipoSolicitudId IN :tipoSolicitudList";
            }
            if (estadSolicitudList != null && !estadSolicitudList.isEmpty()) {
                queryStr += " AND s.estadoSolicitudObj.basicaId IN :estadoSolicitudList";
            }
            if (departamento != null && !departamento.trim().isEmpty()) {
                queryStr += " AND s.departamentoGpo.gpoId = :division";
            }
            if (ciudad != null && !ciudad.trim().isEmpty()) {
                queryStr += " AND s.ciudadGpo.gpoId = :comunidad";
            }
            if (segmento != null && segmento.getBasicaId() != null) {
                queryStr += " AND s.tipoSegmento = :tipoSegmento ";
            }
            if (llamada) {
                queryStr += " AND s.numeroIntentosLlamada > 0 ";
            }
            
            if (!ordenMayorMenor) {
                queryStr += " ORDER BY s.solicitudCmId DESC ";
            } else {
                queryStr += " ORDER BY s.solicitudCmId ASC ";
            }
            
            Query query = entityManager.createQuery(queryStr);
            
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            query.setParameter("estadoRegistro", 1);
            
            if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
                query.setParameter("tipoSolicitudList", tipoSolicitudList);
            }
            if (estadSolicitudList != null && !estadSolicitudList.isEmpty()) {
                query.setParameter("estadoSolicitudList", estadSolicitudList);
            }
            if (departamento != null && !departamento.trim().isEmpty()) {
                query.setParameter("division", new BigDecimal(departamento));
            }
            if (ciudad != null && !ciudad.trim().isEmpty()) {
                query.setParameter("comunidad", new BigDecimal(ciudad));
            }
            if (segmento != null && segmento.getBasicaId() != null) {
                query.setParameter("tipoSegmento", segmento);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtSolicitudCmMgl>) query.getResultList();
             getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta las Solicitudes creadas el dia de la fecha actual. Permite
     * realizar el conteo de las solicitudes por tipos y creadas en la fecha
     * actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountSolicitudCreateDay(List<BigDecimal> tipoSolicitudList) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("CmtSolicitudCmMgl.getCountSolicitudCreateDay");
            query.setParameter("tipoSolicitudList", tipoSolicitudList);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            int result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
             getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta las Solicitudes gestionadas el dia de la fecha actual. Permite
     * realizar el conteo de las solicitudes por tipos y gestionadas en la fecha
     * actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountSolicitudGestionadaDay(List<BigDecimal> tipoSolicitudList) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("CmtSolicitudCmMgl.getCountSolicitudGestionadaDay");
            query.setParameter("tipoSolicitudList", tipoSolicitudList);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            int result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
             getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta las Solicitudes activas el dia de la fecha actual.Permite
 realizar el conteo de las solicitudes por tipos y creadas en la fecha
 actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param estadSolicitudList
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountAllSolicitudActiveDay(List<BigDecimal> tipoSolicitudList,
            List<BigDecimal> estadSolicitudList) throws ApplicationException {
        try {
            String inClauseTipoSol = getStrInClause(tipoSolicitudList);
            String inClauseEstadoSol = getStrInClause(estadSolicitudList);
            
            String queryStr = "SELECT COUNT(1) "
                    + " FROM "+Constant.MGL_DATABASE_SCHEMA+"."+ "CMT_SOLICITUD_CM c "
                    + " INNER JOIN "+Constant.MGL_DATABASE_SCHEMA+"."+ "CMT_TIPO_SOLICITUD tp on tp.TIPO_SOLICITUD_ID = c.TIPO_SOLICITUD_ID "
                    + " WHERE tp.TIPO_SOLICITUD_ID in (" + inClauseTipoSol + ") "
                    + " AND c.BASICA_ID_ESTADO in (" + inClauseEstadoSol + ") "
                    + " AND TRUNC(cast(c.FECHA_CREACION as timestamp)) = TRUNC(SYSDATE) "
                    + " AND TO_NUMBER( "
                    + " extract(day from (sysdate - cast(c.FECHA_CREACION as timestamp)))*24*60 + "
                    + " extract( hour from (sysdate  - cast(c.FECHA_CREACION as timestamp)))*60 + "
                    + " extract( minute from (sysdate - cast(c.FECHA_CREACION as timestamp)))) < tp.ANS_AVISO";
            Query query = entityManager.createNativeQuery(queryStr);
            for (int i = 0; i < tipoSolicitudList.size(); i++) {
                query.setParameter(i + 1, tipoSolicitudList.get(i));
            }
            for (int i = 0; i < estadSolicitudList.size(); i++) {
                query.setParameter(i + 1 + tipoSolicitudList.size(), tipoSolicitudList.get(i));
            }
            int result = query.getSingleResult() == null ? 0 : ((BigDecimal) query.getSingleResult()).intValue();
             getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta las Solicitudes por vencer en la fecha actual.Permite realizar el
 conteo de las solicitudes por tipos y creadas en la fecha actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param estadSolicitudList
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountAllSolicitudPorVencerDay(List<BigDecimal> tipoSolicitudList,
            List<BigDecimal> estadSolicitudList) throws ApplicationException {
        try {
            String inClauseTipoSol = getStrInClause(tipoSolicitudList);
            String inClauseEstadoSol = getStrInClause(estadSolicitudList);
            
            String queryStr = "SELECT COUNT(1) "
                    + " FROM "+Constant.MGL_DATABASE_SCHEMA+"."+ "CMT_SOLICITUD_CM c "
                    + " INNER JOIN "+Constant.MGL_DATABASE_SCHEMA+"."+ "CMT_TIPO_SOLICITUD tp on tp.TIPO_SOLICITUD_ID = c.TIPO_SOLICITUD_ID "
                    + " WHERE tp.TIPO_SOLICITUD_ID in (" + inClauseTipoSol + ") "
                    + " AND c.BASICA_ID_ESTADO in (" + inClauseEstadoSol + ") "
                    + " AND TRUNC(cast(c.FECHA_CREACION as timestamp)) = TRUNC(SYSDATE) "
                    + " AND TO_NUMBER( "
                    + " extract(day from (sysdate - cast(c.FECHA_CREACION as timestamp)))*24*60 + "
                    + " extract( hour from (sysdate  - cast(c.FECHA_CREACION as timestamp)))*60 + "
                    + " extract( minute from (sysdate - cast(c.FECHA_CREACION as timestamp)))) BETWEEN tp.ANS_AVISO AND tp.ANS";
            Query query = entityManager.createNativeQuery(queryStr);
            
            for (int i = 0; i < tipoSolicitudList.size(); i++) {
                query.setParameter(i + 1, tipoSolicitudList.get(i));
            }
            for (int i = 0; i < estadSolicitudList.size(); i++) {
                query.setParameter(i + 1, estadSolicitudList.get(i));
            }

            int result = query.getSingleResult() == null ? 0 : ((BigDecimal) query.getSingleResult()).intValue();
             getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta las Solicitudes vencidas en la fecha actual.Permite realizar el
 conteo de las solicitudes por tipos y creadas en la fecha actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param estadSolicitudList
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountAllSolicitudVencidasDay(List<BigDecimal> tipoSolicitudList,
            List<BigDecimal> estadSolicitudList) throws ApplicationException {
        
        try {
            String inClauseTipoSol = getStrInClause(tipoSolicitudList);
            String inClauseEstadoSol = getStrInClause(estadSolicitudList);
            
            String queryStr = "SELECT COUNT(1) "
                    + " FROM "+Constant.MGL_DATABASE_SCHEMA+"."+ "CMT_SOLICITUD_CM c "
                    + " INNER JOIN "+Constant.MGL_DATABASE_SCHEMA+"."+ "CMT_TIPO_SOLICITUD tp on tp.TIPO_SOLICITUD_ID = c.TIPO_SOLICITUD_ID "
                    + " WHERE tp.TIPO_SOLICITUD_ID in (" + inClauseTipoSol + ") "
                    + " AND c.BASICA_ID_ESTADO in (" + inClauseEstadoSol + ") "
                    + " AND TRUNC(cast(c.FECHA_CREACION as timestamp)) = TRUNC(SYSDATE) "
                    + " AND TO_NUMBER( "
                    + " extract(day from (sysdate - cast(c.FECHA_CREACION as timestamp)))*24*60 + "
                    + " extract( hour from (sysdate  - cast(c.FECHA_CREACION as timestamp)))*60 + "
                    + " extract( minute from (sysdate - cast(c.FECHA_CREACION as timestamp)))) > tp.ANS";
            Query query = entityManager.createNativeQuery(queryStr);
            for (int i = 0; i < tipoSolicitudList.size(); i++) {
                query.setParameter(i + 1, tipoSolicitudList.get(i));
            }
            for (int i = 0; i < estadSolicitudList.size(); i++) {
                query.setParameter(i + 1, estadSolicitudList.get(i));
            }
            int result = query.getSingleResult() == null ? 0 : ((BigDecimal) query.getSingleResult()).intValue();
             getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    private String getStrInClause(List<BigDecimal> inClauseList) {
        String inClause = "";
        if (inClauseList != null && !inClauseList.isEmpty()) {
            if (inClauseList.size() == 1) {
                inClause = "?";
            } else {
                for (int i = 0; i < inClauseList.size() - 1; i++) {
                    inClause += "?,";
                }
                inClause += "?";
            }
        }
        return inClause;
    }

    /**
     * M&eacute;todo para contar las solicitudes de eliminacion existentes en la
     * base de datos
     *
     * @param cuentaMatrizId Identificador de la cuetna matriz asociada a la
     * solicitud
     * @return {@link Integer} cantidad de registros existentes en la base de
     * datos
     * @throws ApplicationException
     */
    public Integer cantidadSolicitudesEliminacionPorCuentaMatriz(BigDecimal cuentaMatrizId) throws ApplicationException {
        try {
            final String SOLICITUD_ELIMINACION = "DEL1";
            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(c.solicitudCmId)")
                    .append(" FROM CmtSolicitudCmMgl c")
                    .append(" WHERE c.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizId")
                    .append("   AND c.origenSolicitud.codigoBasica = :tipoSolicitud");
            return ((Long) entityManager.createQuery(query.toString())
                    .setParameter("cuentaMatrizId", cuentaMatrizId)
                    .setParameter("tipoSolicitud", SOLICITUD_ELIMINACION)
                    .getSingleResult()).intValue();
        } catch (PersistenceException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public int getReporteDetalladoSolicitudesContar(String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, BigDecimal estado) throws ApplicationException {
        
        try {
            boolean control = true;            
            StringBuilder queryStr = new StringBuilder();
            queryStr.append("SELECT Count(1) FROM CmtSolicitudCmMgl s  "
                    + "WHERE s.estadoRegistro = :estadoRegistro ");
            
            if (estado != null) {
                queryStr.append(" AND s.estadoSolicitudObj.basicaId = :estado");
            }
            if (cmtTipoSolicitudMgl != null) {
                queryStr.append(" AND s.tipoSolicitudObj.tipoSolicitudId = :sol");
            }
            
            if (fechaInicio != null && fechaFin != null) {
                if (fechaInicio.before(fechaFin)) {
                    queryStr.append(" AND func('trunc', s.fechaInicioCreacion) BETWEEN :fechaInicio and  :fechaFin");
                } else {
                    queryStr.append(" AND  func('trunc', s.fechaInicioCreacion) =  :fechaInicio");
                    control = false;
                }
            }
            Query query = entityManager.createQuery(queryStr.toString());
            query.setParameter("estadoRegistro", 1);
            
            if (cmtTipoSolicitudMgl != null) {
                query.setParameter("sol", cmtTipoSolicitudMgl.getTipoSolicitudId());
            }
            if (fechaInicio != null) {
                query.setParameter("fechaInicio", fechaInicio);
            }
            if (control) {
                if (fechaFin != null) {
                    query.setParameter("fechaFin", fechaFin);
                }
            }
            
            if (estado != null) {
                query.setParameter("estado", estado);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            
            Long result = (Long) query.getSingleResult();
             getEntityManager().clear();
            
            return result.intValue();
            
        } catch (Exception e) {
            return 0;
        }
    }
    
    public List<CmtReporteDetalladoDto> getSolicitudesSearch
        (String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl,
                Date fechaInicio, Date fechaFin, BigDecimal estado,
                int inicio, int fin, int procesados, int regProcesar) throws ApplicationException {
        try {
            boolean control = true;
            List<CmtReporteDetalladoDto> listcmtReporteDetalladoDto = new ArrayList<>();
            CmtSolicitudSubEdificioMglManager solicitudSubEdificioMglManager = new CmtSolicitudSubEdificioMglManager();
            UsuariosManager usuariosManager = new UsuariosManager();
            CmtValidadorDireccionesManager validadorDireccionesManager = new CmtValidadorDireccionesManager();
            CmtNodosSolicitudMglManager cmtNodosSolicitudMglManager = new CmtNodosSolicitudMglManager();
            CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
            CmtSolictudHhppMglManager cmtSolictudHhppMglManager = new CmtSolictudHhppMglManager();
            CmtModCcmmAudMglManager cmtModCcmmAudMglManager = new CmtModCcmmAudMglManager();
            CmtCuentaMatrizMgl cuentaMatrizMgl;
            CmtSubEdificioMgl subEdificioMglGral;
            CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl;
            AddressService addressService;
            List<CmtSolicitudSubEdificioMgl> lstSolicitudSubEdificios;
            List<CmtNodosSolicitudMgl> listTablaNodosSolicitud;
            List<CmtSolicitudHhppMgl> lsCmtSolicitudHhppMgls;
            CmtTipoSolicitudMgl tipoSolicitudMgl;
            
            
            
            StringBuilder queryStr = new StringBuilder();
            
            queryStr.append("SELECT s "
                    + " FROM CmtSolicitudCmMgl s  "
                    + " WHERE  s.estadoRegistro = :estadoRegistro ");
            
            if (estado != null) {
                queryStr.append(" AND s.estadoSolicitudObj.basicaId = :estado");
            }
            if (cmtTipoSolicitudMgl != null) {
                queryStr.append(" AND s.tipoSolicitudObj.tipoSolicitudId = :sol");
            }
            
            if (fechaInicio != null && fechaFin != null) {
                if (fechaInicio.before(fechaFin)) {
                    queryStr.append(" AND func('trunc', s.fechaInicioCreacion) BETWEEN :fechaInicio and  :fechaFin");
                } else {
                    queryStr.append(" AND  func('trunc', s.fechaInicioCreacion) = :fechaInicio");
                    control = false;
                }
            }
            queryStr.append(" ORDER BY s.fechaInicioCreacion DESC");
            
            Query query = entityManager.createQuery(queryStr.toString());
            query.setParameter("estadoRegistro", 1);
            
            if (cmtTipoSolicitudMgl != null) {
                query.setParameter("sol", cmtTipoSolicitudMgl.getTipoSolicitudId());
            }
            if (fechaInicio != null) {
                query.setParameter("fechaInicio", fechaInicio);
            }
            if (control) {
                if (fechaFin != null) {
                    query.setParameter("fechaFin", fechaFin);
                }
            }
            
            if (estado != null) {
                query.setParameter("estado", estado);
            }
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            @SuppressWarnings("unchecked")
            List<CmtSolicitudCmMgl> listaSolicitudes = query.getResultList();
            
            for (CmtSolicitudCmMgl solicitudCmMgl : listaSolicitudes) {
             

                    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
                    CmtReporteDetalladoDto cmtReporteDetalladoDto = new CmtReporteDetalladoDto();
                    List<CmtSolicitudSubEdificioMgl> lstSolicitudSubEdificioMgl;
                    CmtSolicitudSubEdificioMgl solicitudSubEdificioMglUni = null;
                    List<CmtModificacionesCcmmAudMgl> auditoriaEdiMgl;
                    List<CmtDireccionSolicitudMgl> direccionSolModList;
                    CmtDireccionSolicitudMgl direccionSolicitudMglSol = null;

                    //Datos De la solicitud
                    cmtReporteDetalladoDto.setSolicitudCmId(solicitudCmMgl.getSolicitudCmId());
                    cmtReporteDetalladoDto.setNombreTipoSolicitud(solicitudCmMgl.getTipoSolicitudObj().getNombreTipo());
                    cmtReporteDetalladoDto.setNombreEstadoSolicitud(solicitudCmMgl.getEstadoSolicitudObj().getNombreBasica());
                    cmtReporteDetalladoDto.setFechaIniCreacionSol(solicitudCmMgl.getFechaInicioCreacion());
                    cmtReporteDetalladoDto.setFechaFinCreacionSol(solicitudCmMgl.getFechaCreacion());
                    //Datos De la solicitud

                    //Informacion cuenta matriz 
                    if (solicitudCmMgl.getCuentaMatrizObj() != null) {

                        subEdificioMglGral = solicitudCmMgl.getCuentaMatrizObj().getSubEdificioGeneral();
                        cuentaMatrizMgl = solicitudCmMgl.getCuentaMatrizObj();

                        cmtReporteDetalladoDto.setDepartamentoGpo(cuentaMatrizMgl.getDepartamento().getGpoNombre());
                        cmtReporteDetalladoDto.setCiudad(cuentaMatrizMgl.getMunicipio().getGpoNombre());
                        cmtReporteDetalladoDto.setCentroPobladoGpo(cuentaMatrizMgl.getCentroPoblado().getGpoNombre());

                        if (cuentaMatrizMgl.getDireccionPrincipal() != null) {
                            cmtReporteDetalladoDto.setDireccionCm(cuentaMatrizMgl.getDireccionPrincipal().getDireccionObj().getDirNostandar());
                            cmtReporteDetalladoDto.setBarrio(cuentaMatrizMgl.getDireccionPrincipal().getBarrio());
                        }

                        cmtReporteDetalladoDto.setCuentaMatrizIdRR(cuentaMatrizMgl.getNumeroCuenta());
                        cmtReporteDetalladoDto.setCuentaMatrizId(cuentaMatrizMgl.getCuentaMatrizId());

                        lstSolicitudSubEdificioMgl
                                = solicitudSubEdificioMglManager.findSolicitudSubEdificioBySolicitud(solicitudCmMgl);
                        if (lstSolicitudSubEdificioMgl.size() == 1) {

                            solicitudSubEdificioMglUni = lstSolicitudSubEdificioMgl.get(0);

                        } else {
                            for (CmtSolicitudSubEdificioMgl solEdi : lstSolicitudSubEdificioMgl) {
                                if (solEdi.getSubEdificioObj().getSubEdificioId().
                                        compareTo(solicitudCmMgl.getCuentaMatrizObj().
                                                getSubEdificioGeneral().getSubEdificioId()) == 0) {
                                    solicitudSubEdificioMglUni = solEdi;
                                }

                            }

                        }

                        if (solicitudCmMgl.getEstadoSolicitudObj().getNombreBasica().
                                equalsIgnoreCase("PENDIENTE") && solicitudCmMgl.getModDatosCm() == 1) {
                            //Datos antes CM    
                            if (solicitudSubEdificioMglUni != null) {
                                cmtReporteDetalladoDto.setCuentaMatrizNombre(solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getNombreSubedificio());
                                cmtReporteDetalladoDto.setTipoEdificio(solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getTipoEdificioObj().getNombreBasica());

                                cmtReporteDetalladoDto.setTelefonoPorteria1(solicitudSubEdificioMglUni.getSubEdificioObj().getTelefonoPorteria());
                                cmtReporteDetalladoDto.setTelefonoPorteria2(solicitudSubEdificioMglUni.getSubEdificioObj().getTelefonoPorteria2());
                                cmtReporteDetalladoDto.setContactoAdministracion(solicitudSubEdificioMglUni.getAdministrador());
                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getCompaniaAdministracionObj() != null) {
                                    cmtReporteDetalladoDto.setAdministracion(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getCompaniaAdministracionObj().getNombreCompania());
                                }
                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getCompaniaAscensorObj() != null) {
                                    cmtReporteDetalladoDto.setAscensores(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getCompaniaAscensorObj().getNombreCompania());
                                }

                                cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());

                            }

                            if (solicitudCmMgl.getCantidadTorres() != null) {
                                cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                            }

                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto());
                            }
                            
                            if (subEdificioMglGral != null && subEdificioMglGral.getFechaEntregaEdificio() != null) {
                                cmtReporteDetalladoDto.setFechaEntrega(subEdificioMglGral.getFechaEntregaEdificio());
                            }

                        } else if (solicitudCmMgl.getEstadoSolicitudObj().getNombreBasica().
                                equalsIgnoreCase("FINALIZADO") && solicitudCmMgl.getModDatosCm() == 1) {
                            //Modifico datos CM  
                            auditoriaEdiMgl = cmtModCcmmAudMglManager.findBySolicitud(solicitudCmMgl);

                            if (auditoriaEdiMgl != null && !auditoriaEdiMgl.isEmpty()) {
                                CmtModificacionesCcmmAudMgl modificacionesCcmmAudMgl = auditoriaEdiMgl.get(0);

                                cmtReporteDetalladoDto.setCuentaMatrizNombre(modificacionesCcmmAudMgl.getNombreSubedificioAnt());

                                if (modificacionesCcmmAudMgl.getTipoEdificioObjAnt() != null) {
                                    cmtReporteDetalladoDto.setTipoEdificio(modificacionesCcmmAudMgl.getTipoEdificioObjAnt().getNombreBasica());
                                }

                                cmtReporteDetalladoDto.setTelefonoPorteria1(modificacionesCcmmAudMgl.getTelefonoPorteriaAnt());
                                cmtReporteDetalladoDto.setTelefonoPorteria2(modificacionesCcmmAudMgl.getTelefonoPorteria2Ant());
                                cmtReporteDetalladoDto.setContactoAdministracion(modificacionesCcmmAudMgl.getAdministradorAnt());
                                if (modificacionesCcmmAudMgl.getCompaniaAdministracionObjAnt() != null) {
                                    cmtReporteDetalladoDto.setAdministracion(modificacionesCcmmAudMgl.getCompaniaAdministracionObjAnt()
                                            .getNombreCompania());
                                }
                                if (modificacionesCcmmAudMgl.getCompaniaAscensorObjAnt() != null) {
                                    cmtReporteDetalladoDto.setAscensores(modificacionesCcmmAudMgl.getCompaniaAscensorObjAnt()
                                            .getNombreCompania());
                                }

                            }

                            if (solicitudCmMgl.getCantidadTorres() != null) {
                                cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                            }

                            if (solicitudSubEdificioMglUni != null) {
                                cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getFechaEntregaEdificio() != null) {
                                cmtReporteDetalladoDto.setFechaEntrega(subEdificioMglGral.getFechaEntregaEdificio());
                            }

                        } else if (solicitudCmMgl.getEstadoSolicitudObj().getNombreBasica().
                                equalsIgnoreCase("PENDIENTE") && solicitudCmMgl.getModSubedificios() == 1) {

                            //Datos antes Subedificio   
                            if (lstSolicitudSubEdificioMgl != null && !lstSolicitudSubEdificioMgl.isEmpty()) {

                                String subedificiosInfoAntes = "";

                                for (CmtSolicitudSubEdificioMgl solSub : lstSolicitudSubEdificioMgl) {

                                    String subAntes;
                                    String nombreSub = solSub.getSubEdificioObj().getNombreSubedificio() != null ? solSub.getSubEdificioObj().getNombreSubedificio() : "";
                                    String telPor = solSub.getSubEdificioObj().getTelefonoPorteria() != null ? solSub.getSubEdificioObj().getTelefonoPorteria() : "";
                                    String compaAdm = solSub.getSubEdificioObj().
                                            getCompaniaAdministracionObj() != null ? solSub.getSubEdificioObj().
                                                            getCompaniaAdministracionObj().getNombreCompania() : "";
                                    String admin = solSub.getSubEdificioObj().getAdministrador() != null ? solSub.getSubEdificioObj().getAdministrador() : "";
                                    String fechaEnt = "";
                                    if (solSub.getSubEdificioObj().getFechaEntregaEdificio() != null) {
                                        fechaEnt = formato.format(solSub.getSubEdificioObj().getFechaEntregaEdificio());
                                    }

                                    subAntes = "(Nombre:" + nombreSub + ") "
                                            + "(Telefono:" + telPor + ") "
                                            + "(Compañia Administración: " + compaAdm + ") "
                                            + "(Administrador:" + admin + ")"
                                            + "(Fecha Entrega:" + fechaEnt + ") |";

                                    subedificiosInfoAntes = subedificiosInfoAntes + subAntes;

                                }
                                cmtReporteDetalladoDto.setSubedificiosAntes(subedificiosInfoAntes);

                            }
                            if (solicitudSubEdificioMglUni != null) {
                                cmtReporteDetalladoDto.setCuentaMatrizNombre(solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getNombreSubedificio());
                                cmtReporteDetalladoDto.setTipoEdificio(solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getTipoEdificioObj().getNombreBasica());

                                cmtReporteDetalladoDto.setTelefonoPorteria1(solicitudSubEdificioMglUni.getSubEdificioObj().getTelefonoPorteria());
                                cmtReporteDetalladoDto.setTelefonoPorteria2(solicitudSubEdificioMglUni.getSubEdificioObj().getTelefonoPorteria2());
                                cmtReporteDetalladoDto.setContactoAdministracion(solicitudSubEdificioMglUni.getAdministrador());
                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getCompaniaAdministracionObj() != null) {
                                    cmtReporteDetalladoDto.setAdministracion(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getCompaniaAdministracionObj().getNombreCompania());
                                }
                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getCompaniaAscensorObj() != null) {
                                    cmtReporteDetalladoDto.setAscensores(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getCompaniaAscensorObj().getNombreCompania());
                                }

                                cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());

                            }
                            if (solicitudCmMgl.getCantidadTorres() != null) {
                                cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                            }

                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getFechaEntregaEdificio() != null) {
                                cmtReporteDetalladoDto.setFechaEntrega(subEdificioMglGral.getFechaEntregaEdificio());
                            }
                        } else if (solicitudCmMgl.getEstadoSolicitudObj().getNombreBasica().
                                equalsIgnoreCase("FINALIZADO") && solicitudCmMgl.getModSubedificios() == 1) {

                            //Datos antes Subedificio  
                            auditoriaEdiMgl = cmtModCcmmAudMglManager.findBySolicitud(solicitudCmMgl);
                            String subedificiosInfoAntes = "";
                            if (!auditoriaEdiMgl.isEmpty()) {

                                for (CmtModificacionesCcmmAudMgl modificacionesCcmmAudMgl : auditoriaEdiMgl) {
                                    String subAntes;
                                    String nombreSub = modificacionesCcmmAudMgl.getNombreSubedificioAnt() != null ? modificacionesCcmmAudMgl.getNombreSubedificioAnt() : "";
                                    String telPor = modificacionesCcmmAudMgl.getTelefonoPorteriaAnt() != null ? modificacionesCcmmAudMgl.getTelefonoPorteriaAnt() : "";
                                    String compaAdm = modificacionesCcmmAudMgl.getCompaniaAdministracionObjAnt() != null ? modificacionesCcmmAudMgl.
                                            getCompaniaAdministracionObjAnt().getNombreCompania() : "";
                                    String admin = modificacionesCcmmAudMgl.getAdministradorAnt() != null ? modificacionesCcmmAudMgl.getAdministradorAnt() : "";
                                    String fechaEnt = "";
                                    if (modificacionesCcmmAudMgl.getFechaEntregaEdificioAnt() != null) {
                                        fechaEnt = formato.format(modificacionesCcmmAudMgl.getFechaEntregaEdificioAnt());
                                    }
                                    subAntes = "(Nombre:" + nombreSub + ") "
                                            + "(Telefono:" + telPor + ") "
                                            + "(Compañia Administración: " + compaAdm + ") "
                                            + "(Administrador:" + admin + ")"
                                            + "(Fecha Entrega:" + fechaEnt + ") |";

                                    subedificiosInfoAntes = subedificiosInfoAntes + subAntes;

                                }
                                cmtReporteDetalladoDto.setSubedificiosAntes(subedificiosInfoAntes);
                            }

                            if (solicitudSubEdificioMglUni != null) {
                                cmtReporteDetalladoDto.setCuentaMatrizNombre(solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getNombreSubedificio());

                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getTipoEdificioObj() != null) {
                                    cmtReporteDetalladoDto.setTipoEdificio(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getTipoEdificioObj().getNombreBasica());
                                }

                                cmtReporteDetalladoDto.setTelefonoPorteria1(solicitudSubEdificioMglUni.getSubEdificioObj().getTelefonoPorteria());
                                cmtReporteDetalladoDto.setTelefonoPorteria2(solicitudSubEdificioMglUni.getSubEdificioObj().getTelefonoPorteria2());
                                cmtReporteDetalladoDto.setContactoAdministracion(solicitudSubEdificioMglUni.getSubEdificioObj().getAdministrador());
                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getCompaniaAdministracionObj() != null) {
                                    cmtReporteDetalladoDto.setAdministracion(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getCompaniaAdministracionObj().getNombreCompania());
                                }
                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getCompaniaAscensorObj() != null) {
                                    cmtReporteDetalladoDto.setAscensores(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getCompaniaAscensorObj().getNombreCompania());
                                }

                                cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());

                            }
                            if (solicitudCmMgl.getCantidadTorres() != null) {
                                cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                            }

                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getFechaEntregaEdificio() != null) {
                                cmtReporteDetalladoDto.setFechaEntrega(subEdificioMglGral.getFechaEntregaEdificio());
                            }
                        } else if (solicitudCmMgl.getEstadoSolicitudObj().getNombreBasica().
                                equalsIgnoreCase("PENDIENTE") && solicitudCmMgl.getModDireccion() == 1) {

                            direccionSolModList = solicitudCmMgl.getListCmtDireccionesMgl();
                            if (!direccionSolModList.isEmpty()) {
                                String direccionAntes = "";
                                for (CmtDireccionSolicitudMgl direccionSolicitudMgl : direccionSolModList) {
                                    if (direccionSolicitudMgl.getSolicitudCMObj().getSolicitudCmId().
                                            compareTo(solicitudCmMgl.getSolicitudCmId()) == 0) {
                                        direccionSolicitudMglSol = direccionSolicitudMgl;
                                    }
                                    String dir = direccionSolicitudMgl.getDirFormatoIgac() != null ? direccionSolicitudMgl.getDirFormatoIgac() : "";
                                    String bar = direccionSolicitudMgl.getBarrio() != null ? direccionSolicitudMgl.getBarrio() : "";

                                    String dirAntes = "(Direccion Antes:" + dir + ") "
                                            + "(Barrio Antes:" + bar + ")|";

                                    direccionAntes = direccionAntes + dirAntes;

                                }
                                cmtReporteDetalladoDto.setDireccionCmAntes(direccionAntes);
                            }
                            if (direccionSolicitudMglSol != null) {
                                CmtSubEdificioMgl subEdificioMgl = direccionSolicitudMglSol.getCmtDirObj().getCuentaMatrizObj().getSubEdificioGeneral();
                                cmtReporteDetalladoDto.setCuentaMatrizNombre(subEdificioMgl.
                                        getNombreSubedificio());
                                cmtReporteDetalladoDto.setTipoEdificio(subEdificioMgl.
                                        getTipoEdificioObj().getNombreBasica());
                                if (subEdificioMgl.getTelefonoPorteria() != null) {
                                    cmtReporteDetalladoDto.setTelefonoPorteria1(subEdificioMgl.getTelefonoPorteria());
                                }
                                if (subEdificioMgl.getTelefonoPorteria2() != null) {
                                    cmtReporteDetalladoDto.setTelefonoPorteria2(subEdificioMgl.getTelefonoPorteria2());
                                }
                                if (subEdificioMgl.getAdministrador() != null) {
                                    cmtReporteDetalladoDto.setContactoAdministracion(subEdificioMgl.getAdministrador());
                                }
                                if (subEdificioMgl.
                                        getCompaniaAdministracionObj() != null) {
                                    cmtReporteDetalladoDto.setAdministracion(subEdificioMgl.
                                            getCompaniaAdministracionObj().getNombreCompania());
                                }
                                if (subEdificioMgl.
                                        getCompaniaAscensorObj() != null) {
                                    cmtReporteDetalladoDto.setAscensores(subEdificioMgl.
                                            getCompaniaAscensorObj().getNombreCompania());
                                }
                                if (solicitudSubEdificioMglUni != null) {
                                    cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());
                                }

                            }
                            if (solicitudCmMgl.getCantidadTorres() != null) {
                                cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                            }

                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto());
                            }
                            
                            if (subEdificioMglGral != null && subEdificioMglGral.getFechaEntregaEdificio() != null) {
                                cmtReporteDetalladoDto.setFechaEntrega(subEdificioMglGral.getFechaEntregaEdificio());
                            }
                        } else if (solicitudCmMgl.getEstadoSolicitudObj().getNombreBasica().
                                equalsIgnoreCase("FINALIZADO") && solicitudCmMgl.getModDireccion() == 1) {

                            auditoriaEdiMgl = cmtModCcmmAudMglManager.findBySolicitud(solicitudCmMgl);

                            if (!auditoriaEdiMgl.isEmpty()) {
                                String direccionAntes = "";
                                for (CmtModificacionesCcmmAudMgl modificacionesCcmmAudMgl : auditoriaEdiMgl) {

                                    String dir = modificacionesCcmmAudMgl.getDireccionCcmmAnt() != null ? modificacionesCcmmAudMgl.getDireccionCcmmAnt() : "";
                                    String bar = modificacionesCcmmAudMgl.getBarrioCcmmAnt() != null ? modificacionesCcmmAudMgl.getBarrioCcmmAnt() : "";

                                    String dirAntes = "(Direccion Antes:" + dir + ") "
                                            + "(Barrio Antes:" + bar + ")|";

                                    direccionAntes = direccionAntes + dirAntes;

                                }
                                cmtReporteDetalladoDto.setDireccionCmAntes(direccionAntes);
                            }

                            if (direccionSolicitudMglSol != null) {
                                CmtSubEdificioMgl subEdificioMgl = direccionSolicitudMglSol.getCmtDirObj().getCuentaMatrizObj().getSubEdificioGeneral();
                                cmtReporteDetalladoDto.setCuentaMatrizNombre(subEdificioMgl.
                                        getNombreSubedificio());

                                if (subEdificioMgl.
                                        getTipoEdificioObj() != null) {
                                    cmtReporteDetalladoDto.setTipoEdificio(subEdificioMgl.
                                            getTipoEdificioObj().getNombreBasica());
                                }

                                if (subEdificioMgl.getTelefonoPorteria() != null) {
                                    cmtReporteDetalladoDto.setTelefonoPorteria1(subEdificioMgl.getTelefonoPorteria());
                                }
                                if (subEdificioMgl.getTelefonoPorteria2() != null) {
                                    cmtReporteDetalladoDto.setTelefonoPorteria2(subEdificioMgl.getTelefonoPorteria2());
                                }
                                if (subEdificioMgl.getAdministrador() != null) {
                                    cmtReporteDetalladoDto.setContactoAdministracion(subEdificioMgl.getAdministrador());
                                }
                                if (subEdificioMgl.
                                        getCompaniaAdministracionObj() != null) {
                                    cmtReporteDetalladoDto.setAdministracion(subEdificioMgl.
                                            getCompaniaAdministracionObj().getNombreCompania());
                                }
                                if (subEdificioMgl.
                                        getCompaniaAscensorObj() != null) {
                                    cmtReporteDetalladoDto.setAscensores(subEdificioMgl.
                                            getCompaniaAscensorObj().getNombreCompania());
                                }
                                if (solicitudSubEdificioMglUni != null) {
                                    cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());
                                }
                            }
                            if (solicitudCmMgl.getCantidadTorres() != null) {
                                cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                            }

                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto());
                            }
                            
                            if (subEdificioMglGral != null && subEdificioMglGral.getFechaEntregaEdificio() != null) {
                                cmtReporteDetalladoDto.setFechaEntrega(subEdificioMglGral.getFechaEntregaEdificio());
                            }
                        } else if (solicitudCmMgl.getModCobertura() == 1) {
                            //Datos antes cobertura   
                            if (!lstSolicitudSubEdificioMgl.isEmpty()) {

                                String coberturaInfoAntes = "";

                                for (CmtSolicitudSubEdificioMgl solSub : lstSolicitudSubEdificioMgl) {
                                    String nodoAntes;
                                    String codigo = solSub.getNodoObj() != null ? solSub.getNodoObj().getNodCodigo() : "";

                                    nodoAntes = "(Nodo Antes:" + codigo + ")|";
                                    coberturaInfoAntes = coberturaInfoAntes + nodoAntes;
                                }
                                cmtReporteDetalladoDto.setCoberturaCmAntes(coberturaInfoAntes);
                            }
                            if (solicitudSubEdificioMglUni != null) {
                                cmtReporteDetalladoDto.setCuentaMatrizNombre(solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getNombreSubedificio());

                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getTipoEdificioObj() != null) {
                                    cmtReporteDetalladoDto.setTipoEdificio(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getTipoEdificioObj().getNombreBasica());
                                }

                                cmtReporteDetalladoDto.setTelefonoPorteria1(solicitudSubEdificioMglUni.getSubEdificioObj().getTelefonoPorteria());
                                cmtReporteDetalladoDto.setTelefonoPorteria2(solicitudSubEdificioMglUni.getSubEdificioObj().getTelefonoPorteria2());
                                cmtReporteDetalladoDto.setContactoAdministracion(solicitudSubEdificioMglUni.getAdministrador());
                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getCompaniaAdministracionObj() != null) {
                                    cmtReporteDetalladoDto.setAdministracion(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getCompaniaAdministracionObj().getNombreCompania());
                                }
                                if (solicitudSubEdificioMglUni.getSubEdificioObj().
                                        getCompaniaAscensorObj() != null) {
                                    cmtReporteDetalladoDto.setAscensores(solicitudSubEdificioMglUni.getSubEdificioObj().
                                            getCompaniaAscensorObj().getNombreCompania());
                                }

                                cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());

                            }
                            if (solicitudCmMgl.getCantidadTorres() != null) {
                                cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                            }

                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania());
                            }
                            if (subEdificioMglGral != null && subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto());
                            }
                            
                            if (subEdificioMglGral != null && subEdificioMglGral.getFechaEntregaEdificio() != null) {
                                cmtReporteDetalladoDto.setFechaEntrega(subEdificioMglGral.getFechaEntregaEdificio());
                            }
                        } else {

                            cmtReporteDetalladoDto.setCuentaMatrizNombre(cuentaMatrizMgl.getNombreCuenta());

                            if (subEdificioMglGral != null) {

                                if (subEdificioMglGral.getTipoEdificioObj() != null) {
                                    cmtReporteDetalladoDto.setTipoEdificio(subEdificioMglGral.getTipoEdificioObj().
                                            getNombreBasica());
                                }

                                if (solicitudCmMgl.getCantidadTorres() != null) {
                                    cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                                }
                                if (solicitudSubEdificioMglUni != null) {
                                    cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());
                                }
                                if (subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                    cmtReporteDetalladoDto.setConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreCompania());
                                }
                                if (subEdificioMglGral.getCompaniaConstructoraObj() != null && subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                    cmtReporteDetalladoDto.setContactoConstructora(subEdificioMglGral.getCompaniaConstructoraObj().getNombreContacto());
                                }
                                if (subEdificioMglGral.getCompaniaAdministracionObj() != null && subEdificioMglGral.getCompaniaAdministracionObj().getNombreCompania() != null) {
                                    cmtReporteDetalladoDto.setAdministracion(subEdificioMglGral.getCompaniaAdministracionObj().getNombreCompania());
                                }
                                if (solicitudSubEdificioMglUni != null) {
                                    cmtReporteDetalladoDto.setContactoAdministracion(solicitudSubEdificioMglUni.getAdministrador());
                                }
                                if (subEdificioMglGral.getCompaniaAscensorObj() != null && subEdificioMglGral.getCompaniaAscensorObj().getNombreCompania() != null) {
                                    cmtReporteDetalladoDto.setAscensores(subEdificioMglGral.getCompaniaAscensorObj().getNombreCompania());
                                }
                                if (subEdificioMglGral.getCompaniaAscensorObj() != null && subEdificioMglGral.getCompaniaAscensorObj().getNombreContacto() != null) {
                                    cmtReporteDetalladoDto.setContactoAscensores(subEdificioMglGral.getCompaniaAscensorObj().getNombreContacto());
                                }

                                cmtReporteDetalladoDto.setTelefonoPorteria1(subEdificioMglGral.getTelefonoPorteria());
                                cmtReporteDetalladoDto.setTelefonoPorteria2(subEdificioMglGral.getTelefonoPorteria2());
                                cmtReporteDetalladoDto.setFechaEntrega(subEdificioMglGral.getFechaEntregaEdificio());
                            }
                        }

                    } else {
                        lstSolicitudSubEdificioMgl
                                = solicitudSubEdificioMglManager.findSolicitudSubEdificioBySolicitud(solicitudCmMgl);
                        if (!lstSolicitudSubEdificioMgl.isEmpty()) {

                            solicitudSubEdificioMglUni = lstSolicitudSubEdificioMgl.get(0);

                            cmtReporteDetalladoDto.setDepartamentoGpo(solicitudCmMgl.getDepartamentoGpo().getGpoNombre());
                            cmtReporteDetalladoDto.setCiudad(solicitudCmMgl.getCiudadGpo().getGpoNombre());
                            cmtReporteDetalladoDto.setCentroPobladoGpo(solicitudCmMgl.getCentroPobladoGpo().getGpoNombre());
                            cmtReporteDetalladoDto.setDireccionCm(solicitudSubEdificioMglUni.getDireccion());

                            if (solicitudSubEdificioMglUni != null && solicitudSubEdificioMglUni.getDireccionSolicitudObj() != null) {
                                cmtReporteDetalladoDto.setBarrio(solicitudSubEdificioMglUni.getDireccionSolicitudObj().getBarrio());
                            }
                            cmtReporteDetalladoDto.setCuentaMatrizNombre(solicitudSubEdificioMglUni.getNombreSubedificio());
                            cmtReporteDetalladoDto.setTipoEdificio(solicitudSubEdificioMglUni.getTipoEdificioObj().getNombreBasica());
                            if (solicitudCmMgl.getCantidadTorres() != null) {
                                cmtReporteDetalladoDto.setCantidadTorres(solicitudCmMgl.getCantidadTorres().intValue());
                            }
                            cmtReporteDetalladoDto.setCantidadHhpp(solicitudSubEdificioMglUni.getUnidades());
                            if (solicitudSubEdificioMglUni.getCompaniaConstructoraObj() != null && solicitudSubEdificioMglUni.getCompaniaConstructoraObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setConstructora(solicitudSubEdificioMglUni.getCompaniaConstructoraObj().getNombreCompania());
                            }
                            if (solicitudSubEdificioMglUni.getCompaniaConstructoraObj() != null && solicitudSubEdificioMglUni.getCompaniaConstructoraObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoConstructora(solicitudSubEdificioMglUni.getCompaniaConstructoraObj().getNombreContacto());
                            }
                            if (solicitudSubEdificioMglUni.getCompaniaAdministracionObj() != null && solicitudSubEdificioMglUni.getCompaniaAdministracionObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setAdministracion(solicitudSubEdificioMglUni.getCompaniaAdministracionObj().getNombreCompania());
                            }
                            if (solicitudSubEdificioMglUni.getCompaniaAdministracionObj() != null && solicitudSubEdificioMglUni.getAdministrador() != null) {
                                cmtReporteDetalladoDto.setContactoAdministracion(solicitudSubEdificioMglUni.getAdministrador());
                            }
                            if (solicitudSubEdificioMglUni.getCompaniaAscensorObj() != null && solicitudSubEdificioMglUni.getCompaniaAscensorObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setAscensores(solicitudSubEdificioMglUni.getCompaniaAscensorObj().getNombreCompania());
                            }
                            if (solicitudSubEdificioMglUni.getCompaniaAscensorObj() != null && solicitudSubEdificioMglUni.getCompaniaAscensorObj().getNombreContacto() != null) {
                                cmtReporteDetalladoDto.setContactoAscensores(solicitudSubEdificioMglUni.getCompaniaAscensorObj().getNombreContacto());
                            }

                            cmtReporteDetalladoDto.setTelefonoPorteria1(solicitudSubEdificioMglUni.getTelefonoPorteria());
                            cmtReporteDetalladoDto.setTelefonoPorteria2(solicitudSubEdificioMglUni.getTelefonoPorteria2());
                            cmtReporteDetalladoDto.setFechaEntrega(solicitudSubEdificioMglUni.getFechaEntregaEdificio());
                        }
                        //Informacion cuenta matriz     
                    }
                    //Datos Solicitante
                    cmtReporteDetalladoDto.setOrigenSolicitud(solicitudCmMgl.getOrigenSolicitud().getNombreBasica());
                    cmtReporteDetalladoDto.setAreaSolictud(solicitudCmMgl.getAreaSolictud());
                    cmtReporteDetalladoDto.setAsesor(solicitudCmMgl.getAsesor());
                    cmtReporteDetalladoDto.setCorreoAsesor(solicitudCmMgl.getCorreoAsesor());
                    cmtReporteDetalladoDto.setTelefonoSolicitante(solicitudCmMgl.getTelefonoAsesor());
                    cmtReporteDetalladoDto.setCopiaA(solicitudCmMgl.getCorreoCopiaSolicitud());
                    
                    Usuarios usuarios = null;
                    if(solicitudCmMgl.getUsuarioSolicitudId() != null && 
                            !solicitudCmMgl.getUsuarioSolicitudId().toString().isEmpty()){
                       usuarios = usuariosManager.findUsuarioById(solicitudCmMgl.getUsuarioSolicitudId());
        
                    }
                                 
                    if (usuarios != null) {
                        cmtReporteDetalladoDto.setUsuarioSolicitudNombre(usuarios.getNombre());
                        cmtReporteDetalladoDto.setCorreoUsuarioSolicitante(usuarios.getEmail());
                    }
                    List<CmtNotasSolicitudMgl> notas = solicitudCmMgl.getNotasSolicitudList();
                    StringBuilder infoNotas = new StringBuilder();
                    if (!notas.isEmpty()) {
                        for (CmtNotasSolicitudMgl notasSolicitudMgl : notas) {
                            infoNotas.append(notasSolicitudMgl.getNota());
                            infoNotas.append("|");
                        }
                    }
                    cmtReporteDetalladoDto.setNotasSolicitante(StringUtils.caracteresEspeciales(infoNotas.toString()));
                    //Datos Solicitante
                    //Datos General Gestion
                    cmtReporteDetalladoDto.setTempSolicitud(solicitudCmMgl.getTempSolicitud() != null ? solicitudCmMgl.getTempSolicitud() : "00:00:00");
                    cmtReporteDetalladoDto.setTempGestion(solicitudCmMgl.getTempGestion() != null ? solicitudCmMgl.getTempGestion() : "00:00:00");

                    if (solicitudCmMgl.getResultGestion() != null) {
                        cmtReporteDetalladoDto.setRespuestaActual(solicitudCmMgl.getRespuestaActual());
                        cmtReporteDetalladoDto.setResultadoGestion(solicitudCmMgl.getResultGestion().getNombreBasica());
                    }

                    cmtReporteDetalladoDto.setFechaInicioGestion(solicitudCmMgl.getFechaInicioGestion());
                    cmtReporteDetalladoDto.setFechaGestion(solicitudCmMgl.fechaFinalizacion(solicitudCmMgl.getFechaInicioGestion(), solicitudCmMgl.getTempGestion() != null ? solicitudCmMgl.getTempGestion() : "00:00:00"));
                    
                    Usuarios usuarioGes = null;

                    if (solicitudCmMgl.getUsuarioGestionId() != null
                            && !solicitudCmMgl.getUsuarioGestionId().toString().isEmpty()) {
                        usuarioGes = usuariosManager.findUsuarioById(solicitudCmMgl.getUsuarioGestionId());

                    }
                    if (usuarioGes != null) {
                        cmtReporteDetalladoDto.setUsuarioGestionNombre(usuarioGes.getNombre());
                    }

                    //Datos General Gestion
                    //Datos Creacion CM
                    //Geo
                    if (!solicitudCmMgl.getListCmtDireccionesMgl().isEmpty()) {

                        cmtDireccionSolicitudMgl = solicitudCmMgl.getListCmtDireccionesMgl().get(0);
                        addressService = validadorDireccionesManager.calcularCobertura(cmtDireccionSolicitudMgl);

                        if (addressService.getAddress() != null) {
                            cmtReporteDetalladoDto.setDirTraducida(addressService.getAddress());
                        }
                        if (addressService.getAlternateaddress() != null) {
                            cmtReporteDetalladoDto.setDirAntigua(addressService.getAlternateaddress());
                        }
                        if (addressService.getReliability() != null) {
                            cmtReporteDetalladoDto.setConfiabilidadComplemento(addressService.getReliability() + "%");
                        }
                        if (addressService.getReliabilityPlaca() != null) {
                            cmtReporteDetalladoDto.setConfiabilidad(addressService.getReliabilityPlaca()+ "%");
                        }
                        if (addressService.getSource() != null) {
                            cmtReporteDetalladoDto.setFuente(addressService.getSource());
                        }
                        if (addressService.getNeighborhood() != null) {
                            cmtReporteDetalladoDto.setBarrioGeo(addressService.getNeighborhood());
                        }
                        if (addressService.getLeveleconomic() != null) {
                            cmtReporteDetalladoDto.setNivelSoGeo(addressService.getLeveleconomic());
                        }
                        if (addressService.getNodoUno() != null) {
                            cmtReporteDetalladoDto.setNodoBi(addressService.getNodoUno());
                        }
                        if (addressService.getNodoDos() != null) {
                            cmtReporteDetalladoDto.setNodoUni1(addressService.getNodoDos());
                        }
                        if (addressService.getNodoTres() != null) {
                            cmtReporteDetalladoDto.setNodoUni2(addressService.getNodoTres());
                        }
                        if (addressService.getNodoDth() != null) {
                            cmtReporteDetalladoDto.setNodoDth(addressService.getNodoDth());
                        }
                        if (addressService.getNodoMovil() != null) {
                            cmtReporteDetalladoDto.setNodoMovil(addressService.getNodoMovil());
                        }
                        if (addressService.getNodoFtth() != null) {
                            cmtReporteDetalladoDto.setNodoFtth(addressService.getNodoFtth());
                        }
                        if (addressService.getNodoWifi() != null) {
                            cmtReporteDetalladoDto.setNodoWifi(addressService.getNodoWifi());
                        }
                        if (addressService.getState() != null) {
                            cmtReporteDetalladoDto.setEstado(addressService.getState());
                        }
                        if (addressService.getCx() != null) {
                            cmtReporteDetalladoDto.setCx(addressService.getCx());
                        }
                        if (addressService.getCy() != null) {
                            cmtReporteDetalladoDto.setCy(addressService.getCy());
                        }
                    }
                    //Geo
                    //Gestion Cm
                    if (solicitudCmMgl.getBasicaIdTecnologia() != null) {
                        cmtReporteDetalladoDto.setTecnologiaSolGes(solicitudCmMgl.getBasicaIdTecnologia().getNombreBasica());
                    }
                    listTablaNodosSolicitud = cmtNodosSolicitudMglManager.findBySolicitudId(solicitudCmMgl);

                    if (!listTablaNodosSolicitud.isEmpty()) {
                        for (CmtNodosSolicitudMgl nodosSolicitudMgl : listTablaNodosSolicitud) {
                            if (nodosSolicitudMgl.getNodoMglObj().getNodTipo().
                                    getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)) {

                                cmtReporteDetalladoDto.setTecnoGestionDTH(nodosSolicitudMgl.getNodoMglObj().getNodCodigo());

                            } else if (nodosSolicitudMgl.getNodoMglObj().getNodTipo().
                                    getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_FTTTH)) {

                                cmtReporteDetalladoDto.setTecnoGestionFTTH(nodosSolicitudMgl.getNodoMglObj().getNodCodigo());

                            } else if (nodosSolicitudMgl.getNodoMglObj().getNodTipo().
                                    getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_GPON)) {

                                cmtReporteDetalladoDto.setTecnoGestionFOG(nodosSolicitudMgl.getNodoMglObj().getNodCodigo());

                            } else if (nodosSolicitudMgl.getNodoMglObj().getNodTipo().
                                    getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_UNI)) {

                                cmtReporteDetalladoDto.setTecnoGestionFOU(nodosSolicitudMgl.getNodoMglObj().getNodCodigo());

                            } else if (nodosSolicitudMgl.getNodoMglObj().getNodTipo().
                                    getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {

                                cmtReporteDetalladoDto.setTecnoGestionBID(nodosSolicitudMgl.getNodoMglObj().getNodCodigo());

                            } else if (nodosSolicitudMgl.getNodoMglObj().getNodTipo().
                                    getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)) {

                                cmtReporteDetalladoDto.setTecnoGestionUNI(nodosSolicitudMgl.getNodoMglObj().getNodCodigo());

                            } else if (nodosSolicitudMgl.getNodoMglObj().getNodTipo().
                                    getIdentificadorInternoApp().equalsIgnoreCase(Constant.LTE_INTERNET)) {

                                cmtReporteDetalladoDto.setTecnoGestionLTE(nodosSolicitudMgl.getNodoMglObj().getNodCodigo());

                            } else if (nodosSolicitudMgl.getNodoMglObj().getNodTipo().
                                    getIdentificadorInternoApp().equalsIgnoreCase(Constant.BTS_MOVIL)) {

                                cmtReporteDetalladoDto.setTecnoGestionMOV(nodosSolicitudMgl.getNodoMglObj().getNodCodigo());
                            }
                        }
                    }
                    //Gestion Cm
                    //Datos Creacion CM
                    //Datos Modificacion CM
                    cmtReporteDetalladoDto.setModSubedificios(solicitudCmMgl.getModSubedificios());
                    cmtReporteDetalladoDto.setModDireccion(solicitudCmMgl.getModDireccion());
                    cmtReporteDetalladoDto.setModDatosCm(solicitudCmMgl.getModDatosCm());
                    cmtReporteDetalladoDto.setModCobertura(solicitudCmMgl.getModCobertura());

                    if (solicitudCmMgl.getModDatosCm() == 1) {
                        //Modifico datos CM  
                        if (solicitudSubEdificioMglUni != null) {
                            cmtReporteDetalladoDto.setCuentaMatrizNombreDespues(solicitudSubEdificioMglUni.getNombreSubedificio());
                            if (solicitudSubEdificioMglUni.getTipoEdificioObj() != null && solicitudSubEdificioMglUni.getTipoEdificioObj().getNombreBasica() != null) {
                                cmtReporteDetalladoDto.setTipoEdificioDespues(solicitudSubEdificioMglUni.getTipoEdificioObj().getNombreBasica());
                            }
                            cmtReporteDetalladoDto.setTelefonoPorteria1Despues(solicitudSubEdificioMglUni.getTelefonoPorteria());
                            cmtReporteDetalladoDto.setTelefonoPorteria2Despues(solicitudSubEdificioMglUni.getTelefonoPorteria2());
                            if (solicitudSubEdificioMglUni.getCompaniaAdministracionObj() != null && solicitudSubEdificioMglUni.getCompaniaAdministracionObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setAdministracionDespues(solicitudSubEdificioMglUni.getCompaniaAdministracionObj().getNombreCompania());
                            }
                            if (solicitudSubEdificioMglUni.getAdministrador() != null && solicitudSubEdificioMglUni.getAdministrador() != null) {
                                cmtReporteDetalladoDto.setContactoAdministracionDespues(solicitudSubEdificioMglUni.getAdministrador());
                            }
                            if (solicitudSubEdificioMglUni.getCompaniaAscensorObj() != null && solicitudSubEdificioMglUni.getCompaniaAscensorObj().getNombreCompania() != null) {
                                cmtReporteDetalladoDto.setAscensoresDespues(solicitudSubEdificioMglUni.getCompaniaAscensorObj().getNombreCompania());
                            }

                        }

                    }

                    if (solicitudCmMgl.getModSubedificios() == 1) {
                        //Datos antes Subedificio   
                        if (!lstSolicitudSubEdificioMgl.isEmpty()) {

                            String subedificiosInfoDespues = "";

                            for (CmtSolicitudSubEdificioMgl solSub : lstSolicitudSubEdificioMgl) {

                                String subDespues;
                                String nombreSub = solSub.getNombreSubedificio() != null ? solSub.getNombreSubedificio() : "";
                                String telPor = solSub.getTelefonoPorteria() != null ? solSub.getTelefonoPorteria() : "";
                                String compaAdm = solSub.
                                        getCompaniaAdministracionObj() != null ? solSub.
                                                        getCompaniaAdministracionObj().getNombreCompania() : "";
                                String admin = solSub.getAdministrador() != null ? solSub.getAdministrador() : "";
                                String fechaEnt = "";
                                if (solSub.getFechaEntregaEdificio() != null) {
                                    fechaEnt = formato.format(solSub.getFechaEntregaEdificio());
                                }

                                subDespues = "(Nombre:" + nombreSub + ") "
                                        + "(Telefono:" + telPor + ") "
                                        + "(Compañia Administración: " + compaAdm + ") "
                                        + "(Administrador:" + admin + ")"
                                        + "(Fecha Entrega:" + fechaEnt + ") |";

                                subedificiosInfoDespues = subedificiosInfoDespues + subDespues;

                            }
                            cmtReporteDetalladoDto.setSubedificiosDespues(subedificiosInfoDespues);
                        }
                    }

                    if (solicitudCmMgl.getModDireccion() == 1) {

                        direccionSolModList = solicitudCmMgl.getListCmtDireccionesMgl();
                        if (!direccionSolModList.isEmpty()) {
                            String direccionDespues = "";
                            for (CmtDireccionSolicitudMgl direccionSolicitudMgl : direccionSolModList) {

                                String dir = direccionSolicitudMgl.getCmtDirObj().
                                        getDireccionObj() != null ? direccionSolicitudMgl.getCmtDirObj().
                                                        getDireccionObj().getDirFormatoIgac() : "";
                                String bar = direccionSolicitudMgl.getCmtDirObj() != null ? direccionSolicitudMgl.getCmtDirObj().getBarrio() : "";

                                String dirDespues = "(Direccion Despues:" + dir + ") "
                                        + "(Barrio Despues:" + bar + ")|";

                                direccionDespues = direccionDespues + dirDespues;

                            }
                            cmtReporteDetalladoDto.setDireccionCmDespues(direccionDespues);
                        }
                    }

                    if (solicitudCmMgl.getModCobertura() == 1) {
                        if (!lstSolicitudSubEdificioMgl.isEmpty()) {

                            String coberturaInfoDespues = "";

                            for (CmtSolicitudSubEdificioMgl solSub : lstSolicitudSubEdificioMgl) {
                                String nodoDespues = "";
                                String codigo = solSub.getNodoDisenoObj() != null ? solSub.getNodoDisenoObj()
                                        .getNodCodigo() : "";

                                nodoDespues = "(Nodo Despues:" + codigo + ")|";
                                coberturaInfoDespues = coberturaInfoDespues + nodoDespues;
                            }
                            cmtReporteDetalladoDto.setCoberturaCmDespues(coberturaInfoDespues);
                        }
                    }

                    if (solicitudCmMgl.getResultGestionModDatosCm() != null) {
                        cmtReporteDetalladoDto.setResultGestionModDatosCm(solicitudCmMgl.getResultGestionModDatosCm().getNombreBasica());
                    }
                    if (solicitudCmMgl.getResultGestionModDir() != null) {
                        cmtReporteDetalladoDto.setResultGestionModDir(solicitudCmMgl.getResultGestionModDir().getNombreBasica());
                    }
                    if (solicitudCmMgl.getResultGestionModSubEdi() != null) {
                        cmtReporteDetalladoDto.setResultGestionModSubEdi(solicitudCmMgl.getResultGestionModSubEdi().getNombreBasica());
                    }
                    if (solicitudCmMgl.getResultGestionModCobertura() != null) {
                        cmtReporteDetalladoDto.setResultGestionModCobertura(solicitudCmMgl.getResultGestionModCobertura().getNombreBasica());
                    }

                    cmtReporteDetalladoDto.setFechaInicioGestionCobertura(solicitudCmMgl.getFechaInicioGestionCobertura());
                    cmtReporteDetalladoDto.setFechaGestionCobertura(solicitudCmMgl.getFechaGestionCobertura());
                    
                    Usuarios usuariosCobe = null;

                    if (solicitudCmMgl.getUsuarioGestionCoberturaId() != null
                            && !solicitudCmMgl.getUsuarioGestionCoberturaId().toString().isEmpty()) {
                        usuariosCobe = usuariosManager.findUsuarioById(solicitudCmMgl.getUsuarioGestionCoberturaId());

                    }
                    if (usuariosCobe != null) {
                        cmtReporteDetalladoDto.setUsuarioGestionCobertura(usuariosCobe.getNombre());
                    }

                    tipoSolicitudMgl = cmtTipoSolicitudMglManager.findTipoSolicitudByAbreviatura(Constant.TIPO_SOL_MOD_CM);
                    if (cmtTipoSolicitudMgl != null && tipoSolicitudMgl != null) {
                        if (cmtTipoSolicitudMgl.getTipoSolicitudId().compareTo(tipoSolicitudMgl.getTipoSolicitudId()) == 0 && solicitudCmMgl.getModSubedificios() == 1) {

                            lstSolicitudSubEdificios = solicitudSubEdificioMglManager.findSolicitudSubEdificioBySolicitud(solicitudCmMgl);
                            if (!lstSolicitudSubEdificios.isEmpty()) {
                                cmtReporteDetalladoDto.setCantidadSubedicifiosMod(lstSolicitudSubEdificios.size());
                            }
                        }
                    }
                    //Datos Modificacion CM
                    //Datos creacion/modificacion Hhpp
                    lsCmtSolicitudHhppMgls = cmtSolictudHhppMglManager.findBySolicitud(solicitudCmMgl);
                    if (!lsCmtSolicitudHhppMgls.isEmpty()) {
                        int creados = 0;
                        int mod = 0;
                        String hhppCmMod = "";
                        String hhppCmCre = "";
                        for (CmtSolicitudHhppMgl solicitudHhppMgl : lsCmtSolicitudHhppMgls) {

                            if (solicitudHhppMgl.getTipoSolicitud() == 1) {
                                creados++;
                                String hhppC;
                                cmtReporteDetalladoDto.setCantidadHhppCre(creados);
                                String nivel6 = "";
                                if (solicitudHhppMgl.getOpcionNivel6() != null) {
                                    nivel6 = solicitudHhppMgl.getOpcionNivel6() + " " + solicitudHhppMgl.getValorNivel6();
                                }
                                String aparNuevo = solicitudHhppMgl.getOpcionNivel5() + " " + solicitudHhppMgl.getValorNivel5() + " " + nivel6;
                                hhppC = "(Apartamento Nuevo: " + aparNuevo + ")|";
                                hhppCmCre = hhppCmCre + hhppC;
                            } else {
                                mod++;
                                String hhppM;
                                cmtReporteDetalladoDto.setCantidadHhppMod(mod);
                                auditoriaEdiMgl = cmtModCcmmAudMglManager.findBySolicitud(solicitudCmMgl);
                                if (!auditoriaEdiMgl.isEmpty()) {
                                    for (CmtModificacionesCcmmAudMgl modificacionesCcmmAudMgl : auditoriaEdiMgl) {
                                        String aparAntes = modificacionesCcmmAudMgl.getHhpApartAnt();
                                        String aparDespues = modificacionesCcmmAudMgl.getHhpApartDes();

                                        hhppM = "(Apartamento Antes: " + aparAntes + ")(Apartamento Despues:" + aparDespues + ")|";
                                        hhppCmMod = hhppCmMod + hhppM;
                                    }
                                }
                            }
                        }
                        cmtReporteDetalladoDto.setHhppCmCreados(hhppCmCre);
                        cmtReporteDetalladoDto.setHhppCmModificados(hhppCmMod);
                    }
                    cmtReporteDetalladoDto.setFechaInicioGestionHhpp(solicitudCmMgl.getFechaInicioGestionHhpp());
                    cmtReporteDetalladoDto.setFechaGestionHhpp(solicitudCmMgl.getFechaGestionHhpp());
                    //Datos creacion/modificacion Hhpp

                    //Datos Visita Tecnica
                    if (solicitudCmMgl.getTipoAcondicionamiento() != null) {
                        cmtReporteDetalladoDto.setAcondicionamientoVT(solicitudCmMgl.getTipoAcondicionamiento().getNombreBasica());
                    }
                    if (solicitudCmMgl.getTipoSegmento() != null) {
                        cmtReporteDetalladoDto.setTipoSegmentoVT(solicitudCmMgl.getTipoSegmento().getNombreBasica());
                    }
                    if (solicitudCmMgl.getBasicaIdTecnologia()!= null) {
                        cmtReporteDetalladoDto.setTecnologiaVt(solicitudCmMgl.getBasicaIdTecnologia().getNombreBasica());
                    }
                    if (solicitudCmMgl.getFechaProgramcionVt() != null) {
                    cmtReporteDetalladoDto.setFechaProgramacionVT(solicitudCmMgl.getFechaProgramcionVt());
                }
                    
                    if (solicitudCmMgl.getCantidadTorres() != null) {
                        cmtReporteDetalladoDto.setCantidadTorresVt(solicitudCmMgl.getCantidadTorres().intValue());
                    }

                    cmtReporteDetalladoDto.setAdministradorVT(solicitudCmMgl.getNombreAdministrador());
                    cmtReporteDetalladoDto.setCorreoAdministradorVT(solicitudCmMgl.getCorreoAdministrador());
                    cmtReporteDetalladoDto.setTelAdministradorVT(solicitudCmMgl.getTelFijoAdministrador());
                    cmtReporteDetalladoDto.setNombreContactoVt(solicitudCmMgl.getNombreContacto());
                    cmtReporteDetalladoDto.setTelContactoVt(solicitudCmMgl.getTelefonoContacto());
                    //Datos Visita Tecnica
                    
                    if(solicitudCmMgl.getJustificacion() != null && !solicitudCmMgl.getJustificacion().isEmpty()){
                       cmtReporteDetalladoDto.setNotasSolicitante(solicitudCmMgl.getJustificacion());
                    }

                    listcmtReporteDetalladoDto.add(cmtReporteDetalladoDto);

                

            }
            getEntityManager().clear();
            return listcmtReporteDetalladoDto;
        } catch (Exception e) {
           String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
             throw new ApplicationException(msg,  e);
        }
    }
    
    public CmtCuentaMatrizMgl findByNumeroCM(CmtCuentaMatrizMgl cuentaMatrizId) throws ApplicationException {
        try {
            CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
            Query query = entityManager.createNamedQuery("CmtCuentaMatrizMgl.findByNumeroCuentaMatriz");
            query.setParameter("cuentaMatriz", cuentaMatrizId);
            cmtCuentaMatrizMgl = ((CmtCuentaMatrizMgl) query.getSingleResult());
             getEntityManager().clear();
            return cmtCuentaMatrizMgl;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public boolean findByIdCMTipoSol(CmtCuentaMatrizMgl cuentaMatrizId, BigDecimal cmtTipoSolicitudMgl) throws ApplicationException {
        try {
            boolean encontrada = false;
            List<CmtSolicitudCmMgl> resultList = null;
            Query query = entityManager.createNamedQuery("CmtSolicitudCmMgl.findByIdCmTipoSol");
            query.setParameter("cuentaMatriz", cuentaMatrizId.getNumeroCuenta());
            query.setParameter("origenSolicitud", cmtTipoSolicitudMgl);
            query.setParameter("estadoSol", 80);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                encontrada = true;
            }
             getEntityManager().clear();
            return encontrada;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta solicitud por cuenta matriz y tipo de solicitud
     *
     * @author Victor Bocanegra
     * @param cuentaMatrizId cuenta matriz a consultar
     * @param cmtTipoSolicitudMgl tipo de solicitud a consultar
     * @param estadoSol
     * @return CmtSolicitudCmMgl
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl findBySolCMTipoSol(CmtCuentaMatrizMgl cuentaMatrizId,
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl, CmtBasicaMgl estadoSol) throws ApplicationException {
        try {
            
            List<CmtSolicitudCmMgl> resultList;
            CmtSolicitudCmMgl resultado = null;
            Query query = entityManager.createNamedQuery("CmtSolicitudCmMgl.findByCmTipoSol");
            query.setParameter("cuentaMatriz", cuentaMatrizId.getCuentaMatrizId());
            query.setParameter("tipoSolicitudObj", cmtTipoSolicitudMgl);
            query.setParameter("estadoSol", estadoSol.getBasicaId());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = query.getResultList();
            
            if (!resultList.isEmpty()) {
                resultado = resultList.get(0);
            }
             getEntityManager().clear();
            return resultado;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta listado de solicitudes agrupadas
     *
     * @author Victor Bocanegra
     * @param tipoReporte tipo de reporte
     * @param cmtTipoSolicitudMgl tipo de solicitud a consultar
     * @param fechaInicio fecha inicio rango
     * @param fechaFin fecha fin rango
     * @param estado de la solicitud.
     * @param firstResult
     * @param maxResults
     * @return FiltroReporteSolicitudCMDto
     * @throws ApplicationException
     */
    public FiltroReporteSolicitudCMDto getReporteGeneralSolicitudesSearchFinalCon(String tipoReporte,
            BigDecimal cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, BigDecimal estado,
            int firstResult, int maxResults)
            throws ApplicationException {
        
        FiltroReporteSolicitudCMDto filtroReporteSolicitudCMDto = new FiltroReporteSolicitudCMDto();
        
        filtroReporteSolicitudCMDto.setListaCmtReporteGeneralDto(getReporteGeneralSolicitudesSearchFinal
        (tipoReporte, cmtTipoSolicitudMgl, fechaInicio, fechaFin, estado, firstResult, maxResults));
        
         getEntityManager().clear();
        return filtroReporteSolicitudCMDto;
    }

    /**
     * Consulta listado de solicitudes agrupadas
     *
     * @author Victor Bocanegra
     * @param tipoReporte tipo de reporte
     * @param cmtTipoSolicitudMgl tipo de solicitud a consultar
     * @param fechaInicio fecha inicio rango
     * @param fechaFin fecha fin rango
     * @param estado de la solicitud.
     * @param firstResult
     * @param maxResults
     * @return List<CmtReporteGeneralDto>
     * @throws ApplicationException
     */
    public List<CmtReporteGeneralDto> getReporteGeneralSolicitudesSearchFinal(String tipoReporte,
            BigDecimal cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, BigDecimal estado,
            int firstResult, int maxResults)
            throws ApplicationException {
        try {
            
            List<CmtReporteGeneralDto> listcmtReporteGeneralDto;
            boolean control = true;
            
            
            String queryStr = "SELECT count(s.solicitudCmId), "
                    + "s.estadoSolicitudObj.nombreBasica,"
                    + "s.tipoSolicitudObj.nombreTipo,"
                    + "s.departamentoGpo.gpoNombre, "
                    + "func('trunc',s.fechaInicioCreacion)  "
                    + "FROM CmtSolicitudCmMgl s  "
                    + "where  s.estadoRegistro = :estadoRegistro ";
            
            if (cmtTipoSolicitudMgl != null) {
                queryStr += " AND s.tipoSolicitudObj.tipoSolicitudId = :sol";
            }
            if (estado != null) {
                queryStr += " AND s.estadoSolicitudObj.basicaId = :estado";
            }
            
            if (fechaInicio != null && fechaFin != null) {
                if (fechaInicio.before(fechaFin)) {
                   queryStr += " AND func('trunc', s.fechaInicioCreacion) BETWEEN :fechaInicio and  :fechaFin";
                } else {
                    queryStr += " AND  func('trunc', s.fechaInicioCreacion) = :fechaInicio";
                    control = false;
                }
                
            }
            queryStr += " group by  s.estadoSolicitudObj.nombreBasica,"
                    + "s.tipoSolicitudObj.nombreTipo,s.departamentoGpo.gpoNombre, "
                    + " func('trunc',s.fechaInicioCreacion)";
            
            Query query = entityManager.createQuery(queryStr);
            query.setParameter("estadoRegistro", 1);
            
            if (cmtTipoSolicitudMgl != null) {
                query.setParameter("sol", cmtTipoSolicitudMgl);
            }
            
            if (estado != null) {
                query.setParameter("estado", estado);
            }
            if (fechaInicio != null) {
                query.setParameter("fechaInicio", fechaInicio);
            }
            if (control) {
                if (fechaFin != null) {
                    query.setParameter("fechaFin", fechaFin);
                }
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            
            if (firstResult == 0 && maxResults == 0) {
                LOGGER.info("Consulta sin paginado");
            } else {
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResults);
            }

            
            List<Object[]> rows = query.getResultList();
            listcmtReporteGeneralDto = new ArrayList<CmtReporteGeneralDto>();
            for (Object[] row : rows) {
                CmtReporteGeneralDto cmtReporteGeneralDto = new CmtReporteGeneralDto();
                cmtReporteGeneralDto.setCantSol(((Long) (row[0])).intValue());
                cmtReporteGeneralDto.setEstadoSol(row[1].toString());
                cmtReporteGeneralDto.setDescripcion((row[2]) != null ? (row[2]).toString() : "");
                cmtReporteGeneralDto.setRegional((row[3]) != null ? (row[3]).toString() : "");
                   SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                cmtReporteGeneralDto.setFechaCreacionSol(formato.format((Date) row[4]));
                listcmtReporteGeneralDto.add(cmtReporteGeneralDto);
                
            }
            
            Collections.sort(listcmtReporteGeneralDto, new Comparator<CmtReporteGeneralDto>() {
                @Override
                public int compare(CmtReporteGeneralDto p1, CmtReporteGeneralDto p2) {
                    return (p1.getFechaCreacionSol()).compareTo(p2.getFechaCreacionSol());
                }
            });
            getEntityManager().clear();
            return listcmtReporteGeneralDto;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
     /**
     * Obtiene una Solicitud por rol
     *
     * @author Victor Bocanegra
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param estadSolicitudList lista de los estados de las solicitudes a
     * consultar
     * @param solicitudId id de la solicitud a consultar
     * @return CmtSolicitudCmMgl
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl findBySolicitudPorPermisos(
            List<BigDecimal> tipoSolicitudList,
            List<BigDecimal> estadSolicitudList, BigDecimal solicitudId) throws ApplicationException {
        
         CmtSolicitudCmMgl result;
         
        try {
           
            String queryStr = "SELECT s FROM CmtSolicitudCmMgl s WHERE s.estadoRegistro = :estadoRegistro";

            if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
                queryStr += " AND s.tipoSolicitudObj.tipoSolicitudId IN :tipoSolicitudList";
            }
            if (estadSolicitudList != null && !estadSolicitudList.isEmpty()) {
                queryStr += " AND s.estadoSolicitudObj.basicaId IN :estadoSolicitudList";
            }
            if (solicitudId != null) {
                queryStr += " AND s.solicitudCmId = :solicitudCmId";
            }

            queryStr += " ORDER BY s.fechaCreacion ASC ";

            Query query = entityManager.createQuery(queryStr);


            query.setParameter("estadoRegistro", 1);

            if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
                query.setParameter("tipoSolicitudList", tipoSolicitudList);
            }
            if (estadSolicitudList != null && !estadSolicitudList.isEmpty()) {
                query.setParameter("estadoSolicitudList", estadSolicitudList);
            }
            if (solicitudId != null) {
                query.setParameter("solicitudCmId", solicitudId);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            result = (CmtSolicitudCmMgl) query.getSingleResult();

        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            result = null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            result = null;
        }
        return result;
    }
    
    
    
        public int countReporteGeneralSolicitudesSearchFinal(String tipoReporte,
            BigDecimal cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, BigDecimal estado)
            throws ApplicationException {
        try {           
          
            boolean control = true;            
            
            String queryStr = "SELECT count(s.solicitudCmId) "
                                        + "FROM CmtSolicitudCmMgl s  "
                    + "where  s.estadoRegistro = :estadoRegistro ";
            
            if (cmtTipoSolicitudMgl != null) {
                queryStr += " AND s.tipoSolicitudObj.tipoSolicitudId = :sol";
            }
            if (estado != null) {
                queryStr += " AND s.estadoSolicitudObj.basicaId = :estado";
            }
            
            if (fechaInicio != null && fechaFin != null) {
                if (fechaInicio.before(fechaFin)) {
                   queryStr += " AND func('trunc', s.fechaInicioCreacion) BETWEEN :fechaInicio and  :fechaFin";
                } else {
                    queryStr += " AND  func('trunc', s.fechaInicioCreacion) = :fechaInicio";
                    control = false;
                }
                
            }            
            
            Query query = entityManager.createQuery(queryStr);
            query.setParameter("estadoRegistro", 1);
            
            if (cmtTipoSolicitudMgl != null) {
                query.setParameter("sol", cmtTipoSolicitudMgl);
            }
            
            if (estado != null) {
                query.setParameter("estado", estado);
            }
            if (fechaInicio != null) {
                query.setParameter("fechaInicio", fechaInicio);
            }
            if (control) {
                if (fechaFin != null) {
                    query.setParameter("fechaFin", fechaFin);
                }
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            
            Long result = (Long) query.getSingleResult();
             getEntityManager().clear();
            
            return result.intValue();
           
            
        } catch (Exception e) {
            return 0;
            
        }
    }
        
    public List<CmtSolicitudCmMgl> findSolicitudCMHhppEnCurso(BigDecimal basicaIdTecnologia, BigDecimal subEdificioId,
           String cpTipoNivel5, String cpValorNivel5, String cpTipoNivel6, String cpValorNivel6 )
            throws ApplicationException {
        try {
            List<CmtSolicitudCmMgl> resultList;
                     
            StringBuilder queryStr = new StringBuilder();
     
            queryStr.append("SELECT t FROM CmtSolicitudCmMgl t, CmtSolicitudHhppMgl d  "
                    + " WHERE t.estadoSolicitudObj.identificadorInternoApp = :estadoSolicitud "
                    + " AND t.basicaIdTecnologia.basicaId =:basicaIdTecnologia "
                    + " AND t.solicitudCmId = d.cmtSolicitudCmMglObj.solicitudCmId "
                    + " AND d.cmtSubEdificioMglObj.subEdificioId =:subEdificioId "
                    + " AND t.estadoRegistro = 1 AND d.estadoRegistro = 1");

            if (cpTipoNivel5 != null && !cpTipoNivel5.isEmpty()) {
                queryStr.append(" AND d.opcionNivel5 =:cpTipoNivel5 ");
            } else {
                queryStr.append(" AND d.opcionNivel5 is NULL ");
            }

            if (cpTipoNivel6 != null && !cpTipoNivel6.isEmpty()) {
                queryStr.append(" AND d.opcionNivel6 =:cpTipoNivel6 ");
            } else {
                queryStr.append(" AND d.opcionNivel6 is NULL ");
            }

            if (cpValorNivel5 != null && !cpValorNivel5.isEmpty()) {
                queryStr.append(" AND d.valorNivel5 =:cpValorNivel5 ");
            } else {
                queryStr.append(" AND d.valorNivel5 is NULL ");
            }

            if (cpValorNivel6 != null && !cpValorNivel6.isEmpty()) {
                queryStr.append(" AND d.valorNivel6 =:cpValorNivel6 ");
            } else {
                queryStr.append(" AND d.valorNivel6 is NULL ");
            }
            
            Query q = entityManager.createQuery(queryStr.toString());
  
            q.setParameter("estadoSolicitud", Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE);
            q.setParameter("basicaIdTecnologia", basicaIdTecnologia);
            
            if (cpTipoNivel5 != null && !cpTipoNivel5.isEmpty()) {
                q.setParameter("cpTipoNivel5", cpTipoNivel5);
            }
            if (cpTipoNivel6 != null && !cpTipoNivel6.isEmpty()) {
                q.setParameter("cpTipoNivel6", cpTipoNivel6);
            }
            if (cpValorNivel5 != null && !cpValorNivel5.isEmpty()) {
                q.setParameter("cpValorNivel5", cpValorNivel5);
            }
            if (cpValorNivel6 != null && !cpValorNivel6.isEmpty()) {
                q.setParameter("cpValorNivel6", cpValorNivel6);
            }
            q.setParameter("subEdificioId", subEdificioId);              

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtSolicitudCmMgl>) q.getResultList();
            getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
