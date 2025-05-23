/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.rest.dtos.ConsultaIdMerResponseDto;
import co.com.claro.mgl.rest.dtos.DireccionCCMMHHPPDto;
import co.com.claro.mgl.rest.dtos.DireccionMerDto;
import co.com.claro.mgl.rest.dtos.getNodeIDsByAccountRequestDto;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author valbuenayf
 */
public class CmtDireccionDetalleMglDaoImpl extends GenericDaoImpl<CmtDireccionDetalladaMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtDireccionDetalleMglDaoImpl.class);

    /**
     * valbuenayf Metodo para consultar la direccion por texto , id de la ciudad
     *
     * @param idCiudad
     * @param direccion
     * @param rowNum
     *
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionTexto(BigDecimal idCiudad, String direccion, Integer rowNum) {
        List<CmtDireccionDetalladaMgl> resultList = new ArrayList<CmtDireccionDetalladaMgl>();
        try {
            Query query = entityManager.createNamedQuery("CmtDireccionDetalladaMgl.findByDireccionDetalladaIdCiudad");
            query.setParameter("direccionTexto", direccion + "%");
            query.setParameter("idCiudad", idCiudad);
            query.setFirstResult(0);
            query.setMaxResults(rowNum);
            query.setParameter("direccionTexto", direccion + "%");
            query.setParameter("idCiudad", idCiudad);
            entityManager.clear();
            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTexto de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * valbuenayf Metodo para crear la direccion detalle
     *
     * @param direccionTabulada
     *
     * @return
     */
    public CmtDireccionDetalladaMgl crearDireccion(CmtDireccionDetalladaMgl direccionTabulada) {

        try {
            entityManager.getTransaction().begin();
            if (direccionTabulada.getDireccion() != null && direccionTabulada.getDireccion().getDirId() != null) {
                DireccionMgl direccionMgl = entityManager.find(DireccionMgl.class, direccionTabulada.getDireccion().getDirId());
                direccionTabulada.setDireccion(direccionMgl);
                direccionTabulada.setDireccionTexto(direccionMgl.getDirFormatoIgac());
            }
            if (direccionTabulada.getSubDireccion() != null && direccionTabulada.getSubDireccion().getSdiId() != null) {
                SubDireccionMgl subDireccionMgl = entityManager.find(SubDireccionMgl.class, direccionTabulada.getSubDireccion().getSdiId());
                direccionTabulada.setSubDireccion(subDireccionMgl);
                direccionTabulada.setDireccionTexto(subDireccionMgl.getSdiFormatoIgac());
            }
            direccionTabulada.setEstadoRegistro(1);
            entityManager.persist(direccionTabulada);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            LOGGER.error("Error en crearDireccion de CmtDireccionDetalleMglDaoImpl " + e);
            return null;

        }
        return direccionTabulada;
    }

    /**
     * valbuenayf Metodo para buscar una direcciondetallada por el codigo
     *
     * @param codigo
     *
     * @return
     */
    public CmtDireccionDetalladaMgl buscarDireccion(String codigo) {
        try {
            Query query = entityManager.createNamedQuery("CmtDireccionDetalladaMgl.findByDireccionIx");
            query.setParameter("direccionIx", codigo);
            List<CmtDireccionDetalladaMgl> resultList = (List<CmtDireccionDetalladaMgl>)query.getResultList();

            if (!Objects.isNull(resultList) && !resultList.isEmpty()){
                return resultList.get(0);
            }
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }finally {
            getEntityManager().clear();
        }
    }

    /**
     * valbuenayf Metodo para buscar una direcciondetallada por el id
     *
     * @param direccionDetalladaId
     * @return
     */
    public CmtDireccionDetalladaMgl buscarDireccionIdDireccion(BigDecimal direccionDetalladaId) {
        CmtDireccionDetalladaMgl result;
        try {

            Query query = entityManager.createNamedQuery("CmtDireccionDetalladaMgl.findByDireccionDetalladaId");
            query.setParameter("direccionDetalladaId", direccionDetalladaId);
            result = (CmtDireccionDetalladaMgl) query.getSingleResult();

        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
        getEntityManager().clear();
        return result;
    }

    /**
     * Autor: Victor Bocanegra Consulta direccion detallada CK (Calle-Carrera)
     *
     * @param cmtDireccionSolicitudMgl
     * @return List<CmtDireccionDetalladaMgl>
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<CmtDireccionDetalladaMgl> findDirDetalladaCK(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl)
            throws ApplicationException {

        List<CmtDireccionDetalladaMgl> result = new ArrayList<>();

        try {

            String sqlQuery = "SELECT dta FROM CmtDireccionDetalladaMgl dta  "
                    + " INNER JOIN  dta.direccion d  "
                    + " INNER JOIN  d.ubicacion u  "
                    + " WHERE  dta.estadoRegistro = 1 "
                    + " AND u.gpoIdObj.gpoId = :gpoIdObj ";

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                sqlQuery += " AND  dta.idTipoDireccion = :idTipoDireccion ";
            }

            if (cmtDireccionSolicitudMgl.getBarrio() != null
                    && !cmtDireccionSolicitudMgl.getBarrio().trim().isEmpty()) {
                sqlQuery += " AND  dta.barrio = :barrio";
            }

            if (cmtDireccionSolicitudMgl.getTipoViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getTipoViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.tipoViaPrincipal = :tipoViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.tipoViaPrincipal is null ";
            }
            if (cmtDireccionSolicitudMgl.getNumViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getNumViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.numViaPrincipal = :numViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.numViaPrincipal is null ";
            }
            if (cmtDireccionSolicitudMgl.getLtViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getLtViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.ltViaPrincipal = :ltViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.ltViaPrincipal  is null ";
            }
            if (cmtDireccionSolicitudMgl.getNlPostViaP() != null
                    && !cmtDireccionSolicitudMgl.getNlPostViaP().isEmpty()) {
                sqlQuery += " AND  dta.nlPostViaP = :nlPostViaP ";
            } else {
                sqlQuery += " AND  dta.nlPostViaP is null ";
            }
            if (cmtDireccionSolicitudMgl.getBisViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getBisViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.bisViaPrincipal = :bisViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.bisViaPrincipal is null ";
            }
            if (cmtDireccionSolicitudMgl.getCuadViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getCuadViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.cuadViaPrincipal = :cuadViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.cuadViaPrincipal is null ";
            }
            if (cmtDireccionSolicitudMgl.getTipoViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getTipoViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.tipoViaGeneradora = :tipoViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.tipoViaGeneradora is null ";
            }
            if (cmtDireccionSolicitudMgl.getNumViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getNumViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.numViaGeneradora = :numViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.numViaGeneradora is null ";
            }
            if (cmtDireccionSolicitudMgl.getLtViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getLtViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.ltViaGeneradora = :ltViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.ltViaGeneradora is null ";
            }
            if (cmtDireccionSolicitudMgl.getNlPostViaG() != null
                    && !cmtDireccionSolicitudMgl.getNlPostViaG().isEmpty()) {
                sqlQuery += " AND  dta.nlPostViaG = :nlPostViaG ";
            } else {
                sqlQuery += " AND  dta.nlPostViaG is null ";
            }
            if (cmtDireccionSolicitudMgl.getLetra3G() != null
                    && !cmtDireccionSolicitudMgl.getLetra3G().isEmpty()) {
                sqlQuery += " AND  dta.letra3G = :letra3G ";
            } else {
                sqlQuery += " AND  dta.letra3G is null ";
            }
            if (cmtDireccionSolicitudMgl.getBisViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getBisViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.bisViaGeneradora = :bisViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.bisViaGeneradora is null ";
            }
            if (cmtDireccionSolicitudMgl.getCuadViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getCuadViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.cuadViaGeneradora = :cuadViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.cuadViaGeneradora is null ";
            }

            if (cmtDireccionSolicitudMgl.getPlacaDireccion() != null
                    && !cmtDireccionSolicitudMgl.getPlacaDireccion().isEmpty()) {
                sqlQuery += " AND  dta.placaDireccion = :placaDireccion ";
            } else {
                sqlQuery += " AND  dta.placaDireccion is null ";
            }

            Query query = entityManager.createQuery(sqlQuery);
            if (cmtDireccionSolicitudMgl.getSolicitudCMObj() != null
                    && cmtDireccionSolicitudMgl.getSolicitudCMObj().getCentroPobladoGpo() != null) {
                query.setParameter("gpoIdObj", cmtDireccionSolicitudMgl.
                        getSolicitudCMObj().getCentroPobladoGpo().getGpoId());
            } else {
                LOGGER.error("El centro poblado llegó nulo para la solicitud de la dirección " + cmtDireccionSolicitudMgl.getDireccionSolId() + ".");
                query.setParameter("gpoIdObj", "");
            }

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                query.setParameter("idTipoDireccion", cmtDireccionSolicitudMgl.getCodTipoDir().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getBarrio() != null
                    && !cmtDireccionSolicitudMgl.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio",
                        cmtDireccionSolicitudMgl.getBarrio().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getTipoViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getTipoViaPrincipal().isEmpty()) {
                query.setParameter("tipoViaPrincipal", cmtDireccionSolicitudMgl.getTipoViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getNumViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getNumViaPrincipal().isEmpty()) {
                query.setParameter("numViaPrincipal", cmtDireccionSolicitudMgl.getNumViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getLtViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getLtViaPrincipal().isEmpty()) {
                query.setParameter("ltViaPrincipal", cmtDireccionSolicitudMgl.getLtViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getNlPostViaP() != null
                    && !cmtDireccionSolicitudMgl.getNlPostViaP().isEmpty()) {
                query.setParameter("nlPostViaP", cmtDireccionSolicitudMgl.getNlPostViaP().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getBisViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getBisViaPrincipal().isEmpty()) {
                query.setParameter("bisViaPrincipal", cmtDireccionSolicitudMgl.getBisViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getCuadViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getCuadViaPrincipal().isEmpty()) {
                query.setParameter("cuadViaPrincipal", cmtDireccionSolicitudMgl.getCuadViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getTipoViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getTipoViaGeneradora().isEmpty()) {
                query.setParameter("tipoViaGeneradora", cmtDireccionSolicitudMgl.getTipoViaGeneradora().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getNumViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getNumViaGeneradora().isEmpty()) {
                query.setParameter("numViaGeneradora", cmtDireccionSolicitudMgl.getNumViaGeneradora().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getLtViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getLtViaGeneradora().isEmpty()) {
                query.setParameter("ltViaGeneradora", cmtDireccionSolicitudMgl.getLtViaGeneradora().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getNlPostViaG() != null
                    && !cmtDireccionSolicitudMgl.getNlPostViaG().isEmpty()) {
                query.setParameter("nlPostViaG", cmtDireccionSolicitudMgl.getNlPostViaG().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getLetra3G() != null
                    && !cmtDireccionSolicitudMgl.getLetra3G().isEmpty()) {
                query.setParameter("letra3G", cmtDireccionSolicitudMgl.getLetra3G().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getBisViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getBisViaGeneradora().isEmpty()) {
                query.setParameter("bisViaGeneradora", cmtDireccionSolicitudMgl.getBisViaGeneradora().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getCuadViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getCuadViaGeneradora().isEmpty()) {
                query.setParameter("cuadViaGeneradora", cmtDireccionSolicitudMgl.getCuadViaGeneradora().toUpperCase());
            }

            if (cmtDireccionSolicitudMgl.getPlacaDireccion() != null
                    && !cmtDireccionSolicitudMgl.getPlacaDireccion().isEmpty()) {
                query.setParameter("placaDireccion", cmtDireccionSolicitudMgl.getPlacaDireccion().toUpperCase());
            }

            result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            LOGGER.error("Error al consultar la dirección CK. EX000: " + e.getMessage(), e);
            return result;
        }
    }

    /**
     * Autor: Victor Bocanegra Consulta direccion detallada BM (Barrio-Manzana)
     *
     * @param cmtDireccionSolicitudMgl
     * @return List<CmtDireccionDetalladaMgl>
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<CmtDireccionDetalladaMgl> findDirDetalladaBM(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl)
            throws ApplicationException {

        List<CmtDireccionDetalladaMgl> result = new ArrayList<>();

        try {
            String sqlQuery = "SELECT dta FROM CmtDireccionDetalladaMgl dta  "
                    + " INNER JOIN  dta.direccion d  "
                    + " INNER JOIN  d.ubicacion u  "
                    + " WHERE  dta.estadoRegistro = 1 "
                    + " AND u.gpoIdObj.gpoId = :gpoIdObj ";

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                sqlQuery += " AND  dta.idTipoDireccion = :idTipoDireccion ";
            }

            if (cmtDireccionSolicitudMgl.getBarrio() != null
                    && !cmtDireccionSolicitudMgl.getBarrio().trim().isEmpty()) {
                sqlQuery += " AND  dta.barrio = :barrio";
            }

            if (cmtDireccionSolicitudMgl.getMzTipoNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel1().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel1 = :mzTipoNivel1 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel1 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel1().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel1 = :mzValorNivel1 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel1 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel2().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel2 = :mzTipoNivel2 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel2 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel2().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel2 = :mzValorNivel2 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel2 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel3().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel3 = :mzTipoNivel3 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel3 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel3().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel3 = :mzValorNivel3 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel3 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel4().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel4 = :mzTipoNivel4 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel4 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel4().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel4 = :mzValorNivel4 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel4 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().isEmpty()) {
                if (cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sqlQuery += " AND  (dta.mzTipoNivel5 =:casa OR dta.mzTipoNivel5 =:lote) ";
                } else {
                    sqlQuery += " AND  dta.mzTipoNivel5 = :mzTipoNivel5";
                }

            } else {
                sqlQuery += " AND  dta.mzTipoNivel5 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel5().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel5 = :mzValorNivel5 ";
            }

            if (cmtDireccionSolicitudMgl.getMzTipoNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel6().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel6 = :mzTipoNivel6 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel6 is null ";
            }

            if (cmtDireccionSolicitudMgl.getMzValorNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel6().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel6 = :mzValorNivel6 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel6 is null ";
            }

            Query query = entityManager.createQuery(sqlQuery);
            query.setParameter("gpoIdObj", cmtDireccionSolicitudMgl.
                    getSolicitudCMObj().getCentroPobladoGpo().getGpoId());

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                query.setParameter("idTipoDireccion", cmtDireccionSolicitudMgl.getCodTipoDir().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getBarrio() != null
                    && !cmtDireccionSolicitudMgl.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio",
                        cmtDireccionSolicitudMgl.getBarrio().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel1().isEmpty()) {
                query.setParameter("mzTipoNivel1", cmtDireccionSolicitudMgl.getMzTipoNivel1().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel1().isEmpty()) {
                query.setParameter("mzValorNivel1", cmtDireccionSolicitudMgl.getMzValorNivel1().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel2().isEmpty()) {
                query.setParameter("mzTipoNivel2", cmtDireccionSolicitudMgl.getMzTipoNivel2().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel2().isEmpty()) {
                query.setParameter("mzValorNivel2", cmtDireccionSolicitudMgl.getMzValorNivel2().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel3().isEmpty()) {
                query.setParameter("mzTipoNivel3", cmtDireccionSolicitudMgl.getMzTipoNivel3().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel3().isEmpty()) {
                query.setParameter("mzValorNivel3", cmtDireccionSolicitudMgl.getMzValorNivel3().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel4().isEmpty()) {
                query.setParameter("mzTipoNivel4", cmtDireccionSolicitudMgl.getMzTipoNivel4().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel4().isEmpty()) {
                query.setParameter("mzValorNivel4", cmtDireccionSolicitudMgl.getMzValorNivel4().toUpperCase());
            }

            if (cmtDireccionSolicitudMgl.getMzTipoNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().isEmpty()) {

                if (cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            cmtDireccionSolicitudMgl.getMzTipoNivel5().toUpperCase());
                }
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel5().isEmpty()) {
                query.setParameter("mzValorNivel5", cmtDireccionSolicitudMgl.getMzValorNivel5().toUpperCase());
            }

            if (cmtDireccionSolicitudMgl.getMzTipoNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel6().isEmpty()) {
                query.setParameter("mzTipoNivel6", cmtDireccionSolicitudMgl.getMzTipoNivel6().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel6().isEmpty()) {
                query.setParameter("mzValorNivel6", cmtDireccionSolicitudMgl.getMzValorNivel6().toUpperCase());
            }

            result = query.getResultList();
            entityManager.clear();
            return result;
        } catch (Exception e) {
            LOGGER.error("Error al consultar la dirección BM. EX000: " + e.getMessage(), e);
            return result;
        }
    }

    /**
     * Autor: Victor Bocanegra Consulta direccion detallada IT(intraducible)
     *
     * @param cmtDireccionSolicitudMgl
     * @return CmtDireccionDetalladaMgl
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<CmtDireccionDetalladaMgl> findDirDetalladaIT(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl)
            throws ApplicationException {

        List<CmtDireccionDetalladaMgl> result = new ArrayList<>();

        try {

            String sqlQuery = "SELECT dta FROM CmtDireccionDetalladaMgl dta  "
                    + " INNER JOIN  dta.direccion d  "
                    + " INNER JOIN  d.ubicacion u  "
                    + " WHERE  dta.estadoRegistro = 1 "
                    + " AND u.gpoIdObj.gpoId = :gpoIdObj";

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                sqlQuery += " AND  dta.idTipoDireccion = :idTipoDireccion ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel1().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel1 = :mzTipoNivel1 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel1 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel1().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel1 = :mzValorNivel1 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel1 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel2().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel2 = :mzTipoNivel2 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel2 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel2().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel2 = :mzValorNivel2 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel2 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel3().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel3 = :mzTipoNivel3 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel3 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel3().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel3 = :mzValorNivel3 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel3 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel4().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel4 = :mzTipoNivel4 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel4 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel4().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel4 = :mzValorNivel4 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel4 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().isEmpty()) {
                if (cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sqlQuery += " AND  (dta.mzTipoNivel5 =:casa OR dta.mzTipoNivel5 =:lote) ";
                } else {
                    sqlQuery += " AND  dta.mzTipoNivel5 = :mzTipoNivel5";
                }

            } else {
                sqlQuery += " AND  dta.mzTipoNivel5 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel5().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel5 = :mzValorNivel5 ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel6().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel6 = :mzTipoNivel6 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel6 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel6().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel6 = :mzValorNivel6 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel6 is null ";
            }
            if (cmtDireccionSolicitudMgl.getItTipoPlaca() != null
                    && !cmtDireccionSolicitudMgl.getItTipoPlaca().isEmpty()) {
                sqlQuery += " AND  dta.itTipoPlaca = :itTipoPlaca ";
            }

            if (cmtDireccionSolicitudMgl.getItValorPlaca() != null
                    && !cmtDireccionSolicitudMgl.getItValorPlaca().isEmpty()) {
                sqlQuery += " AND  dta.itValorPlaca = :itValorPlaca ";
            }

            Query query = entityManager.createQuery(sqlQuery);
            query.setParameter("gpoIdObj", cmtDireccionSolicitudMgl.
                    getSolicitudCMObj().getCentroPobladoGpo().getGpoId());

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                query.setParameter("idTipoDireccion", cmtDireccionSolicitudMgl.getCodTipoDir().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel1().isEmpty()) {
                query.setParameter("mzTipoNivel1", cmtDireccionSolicitudMgl.getMzTipoNivel1().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel1().isEmpty()) {
                query.setParameter("mzValorNivel1", cmtDireccionSolicitudMgl.getMzValorNivel1().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel2().isEmpty()) {
                query.setParameter("mzTipoNivel2", cmtDireccionSolicitudMgl.getMzTipoNivel2().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel2().isEmpty()) {
                query.setParameter("mzValorNivel2", cmtDireccionSolicitudMgl.getMzValorNivel2().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel3().isEmpty()) {
                query.setParameter("mzTipoNivel3", cmtDireccionSolicitudMgl.getMzTipoNivel3().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel3().isEmpty()) {
                query.setParameter("mzValorNivel3", cmtDireccionSolicitudMgl.getMzValorNivel3().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel4().isEmpty()) {
                query.setParameter("mzTipoNivel4", cmtDireccionSolicitudMgl.getMzTipoNivel4().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel4().isEmpty()) {
                query.setParameter("mzValorNivel4", cmtDireccionSolicitudMgl.getMzValorNivel4().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().isEmpty()) {

                if (cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            cmtDireccionSolicitudMgl.getMzTipoNivel5().toUpperCase());
                }
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel5().isEmpty()) {
                query.setParameter("mzValorNivel5", cmtDireccionSolicitudMgl.getMzValorNivel5().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel6().isEmpty()) {
                query.setParameter("mzTipoNivel6", cmtDireccionSolicitudMgl.getMzTipoNivel6().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel6().isEmpty()) {
                query.setParameter("mzValorNivel6", cmtDireccionSolicitudMgl.getMzValorNivel6().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getItTipoPlaca() != null
                    && !cmtDireccionSolicitudMgl.getItTipoPlaca().isEmpty()) {
                query.setParameter("itTipoPlaca", cmtDireccionSolicitudMgl.getItTipoPlaca().toUpperCase());
            }

            if (cmtDireccionSolicitudMgl.getItValorPlaca() != null
                    && !cmtDireccionSolicitudMgl.getItValorPlaca().isEmpty()) {
                query.setParameter("itValorPlaca", cmtDireccionSolicitudMgl.getItValorPlaca().toUpperCase());
            }

            result = query.getResultList();
            getEntityManager().clear();
            return result;

        } catch (Exception e) {
            LOGGER.error("Error al consultar la dirección IT. EX000: " + e.getMessage(), e);
            return result;
        }
    }

    /**
     * Buscar la CmtDireccionDetalladaMgl según el parametro.
     *
     * @author becerraarmr
     * @param hhppMgl hhpp que contiene la dirección y subdirección.
     * @return null si no encontró o un CmtDireccionDetalladaMgl con la data
     * @throws ApplicationException si hay algún error
     */
    public CmtDireccionDetalladaMgl find(HhppMgl hhppMgl) throws ApplicationException {
        try {
            if (hhppMgl == null) {
                return null;
            }

            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT c FROM CmtDireccionDetalladaMgl c WHERE c.direccion = :direccion AND ");

            if (hhppMgl.getSubDireccionObj() != null) {
                sql.append("c.subDireccion = :subDireccion AND c.estadoRegistro = 1");
            } else {
                sql.append("c.subDireccion IS NULL AND c.estadoRegistro = 1");
            }

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            query.setParameter("direccion", hhppMgl.getDireccionObj());

            if (hhppMgl.getSubDireccionObj() != null) {
                query.setParameter("subDireccion",
                        hhppMgl.getSubDireccionObj());
            }

            query.setMaxResults(1);

            Object entity;
            if (!query.getResultList().isEmpty()) {
                entity = query.getResultList().get(0);
            } else {
                entity = null;
            }

            entityManager.getTransaction().commit();
            if (entity instanceof CmtDireccionDetalladaMgl) {
                return (CmtDireccionDetalladaMgl) entity;
            }
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     * @param consumoDireccionGeneralWs
     * @param direccion la dirección construida
     * @param gpo ciudad por la que se desea filtrar
     * @param firstResult
     * @param busquedaPaginada true para que la consulta sea paginada
     *
     * @author Juan David Hernandez
     * @param maxResult
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionTabulada(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada, boolean consumoDireccionGeneralWs, int firstResult, int maxResult) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "AND d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1");

            //SI ES CARRERA BUSCA TAMBIEN POR AVENIDA CARRERA
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {

                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    sql.append(" AND (d.tipoViaPrincipal = :carrera OR d.tipoViaPrincipal = :avenidacarrera) ");
                } else {
                    sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal ");
                }

            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.tipoViaPrincipal is null ");
                }
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.numViaPrincipal is null ");
                }
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.ltViaPrincipal is null ");
                }
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.nlPostViaP is null ");
                }
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.bisViaPrincipal is null ");
                }
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.cuadViaPrincipal is null");
                }
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.tipoViaGeneradora is null ");
                }
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.numViaGeneradora is null ");
                }
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.ltViaGeneradora is null ");
                }
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.nlPostViaG is null ");
                }
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.bisViaGeneradora is null");
                }
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.cuadViaGeneradora is null ");
                }
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND d.placaDireccion is null ");
                }
            }

            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel1 = :mzTipoNivel1");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzTipoNivel1 is null ");
                }
            }

            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel2 = :mzTipoNivel2");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzTipoNivel2 is null ");
                }
            }

            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel3 = :mzTipoNivel3");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzTipoNivel3 is null");
                }
            }

            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel4 = :mzTipoNivel4");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzTipoNivel4 is null ");
                }
            }

            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sql.append(" AND  (d.mzTipoNivel5 =:casa OR d.mzTipoNivel5 =:lote) ");
                } else {
                    sql.append(" AND  d.mzTipoNivel5 = :mzTipoNivel5");
                }

            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzTipoNivel5 is null ");
                }
            }

            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel6 = :mzTipoNivel6");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzTipoNivel6 is null ");
                }
            }

            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel1 = :mzValorNivel1");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzValorNivel1 is null ");
                }
            }

            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel2 = :mzValorNivel2");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzValorNivel2 is null ");
                }
            }

            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel3 = :mzValorNivel3");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzValorNivel3 is null ");
                }
            }

            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel4 = :mzValorNivel4");
            } else {
                if (!consumoDireccionGeneralWs) {
                    sql.append(" AND  d.mzValorNivel4 is null");
                }
            }

            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel5 = :mzValorNivel5");
            }

            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel6 = :mzValorNivel6");
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            }

            //SOLO SI ES MULTIORIGEN INCLUYE EL BARRIO EN LA BUSQUEDA
            if (direccionTabulada.getMultiOrigen() != null
                    && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    sql.append(" AND  d.barrio = :barrio");
                }
            } else {
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        sql.append(" AND  d.barrio = :barrio");
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itTipoPlaca = :itTipoPlaca");
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itValorPlaca = :itValorPlaca");
            }

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            //query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }

            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    query.setParameter("carrera", "CARRERA");
                    query.setParameter("avenidacarrera", "AVENIDA CARRERA");
                } else {
                    query.setParameter("tipoViaPrincipal",
                            direccionTabulada.getTipoViaPrincipal().toUpperCase());
                }
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1",
                        direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2",
                        direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3",
                        direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4",
                        direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {

                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            direccionTabulada.getMzTipoNivel5().toUpperCase());
                }
            }
            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                query.setParameter("mzTipoNivel6",
                        direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1",
                        direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2",
                        direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3",
                        direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4",
                        direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5",
                        direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                query.setParameter("mzValorNivel6",
                        direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getMultiOrigen() != null
                    && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {

                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    query.setParameter("barrio",
                            direccionTabulada.getBarrio().toUpperCase());
                }
            } else {
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        query.setParameter("barrio",
                                direccionTabulada.getBarrio().toUpperCase());
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                query.setParameter("itTipoPlaca",
                        direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                query.setParameter("itValorPlaca",
                        direccionTabulada.getItValorPlaca().toUpperCase());
            }
            getEntityManager().clear();
            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabulada de CmtDireccionDetalleMglDaoImpl " + e);
        }
        return resultList;
    }

    /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     * @param firstResult
     * @param busquedaPaginada true para que la consulta sea paginada
     *
     * @author Juan David Hernandez
     * @param maxResult
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaMer(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada, boolean busquedaPaginada, int firstResult, int maxResult) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "AND d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1");

            //SI LLEGA CARRERA QUE TAMBIEN LO BUSQUE POR AVENIDA CARRERA
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    sql.append(" AND (d.tipoViaPrincipal = :carrera OR d.tipoViaPrincipal =:avenidacarrera) ");
                } else {
                    sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
                }
            } else {
                sql.append(" AND d.tipoViaPrincipal is null ");
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                sql.append(" AND d.numViaPrincipal is null ");
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                sql.append(" AND d.ltViaPrincipal is null ");
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                sql.append(" AND d.nlPostViaP is null ");
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                sql.append(" AND d.bisViaPrincipal is null ");
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                sql.append(" AND d.cuadViaPrincipal is null");
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                sql.append(" AND d.tipoViaGeneradora is null ");
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                sql.append(" AND d.numViaGeneradora is null ");
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                sql.append(" AND d.ltViaGeneradora is null ");
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                sql.append(" AND d.nlPostViaG is null ");
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                sql.append(" AND d.bisViaGeneradora is null");
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                sql.append(" AND d.cuadViaGeneradora is null ");
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            } else {
                sql.append(" AND d.placaDireccion is null ");
            }

            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel1 = :mzTipoNivel1");
            } else {
                sql.append(" AND  d.mzTipoNivel1 is null ");
            }

            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel2 = :mzTipoNivel2");
            } else {
                sql.append(" AND  d.mzTipoNivel2 is null ");
            }

            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel3 = :mzTipoNivel3");
            } else {
                sql.append(" AND  d.mzTipoNivel3 is null");
            }

            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel4 = :mzTipoNivel4");
            } else {
                sql.append(" AND  d.mzTipoNivel4 is null ");
            }

            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sql.append(" AND  (d.mzTipoNivel5 =:casa OR d.mzTipoNivel5 =:lote) ");
                } else {
                    sql.append(" AND  d.mzTipoNivel5 = :mzTipoNivel5");
                }

            } else {
                sql.append(" AND  d.mzTipoNivel5 is null ");
            }

            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel6 = :mzTipoNivel6");
            } else {
                sql.append(" AND  d.mzTipoNivel6 is null ");
            }

            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel1 = :mzValorNivel1");
            } else {
                sql.append(" AND  d.mzValorNivel1 is null ");
            }

            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel2 = :mzValorNivel2");
            } else {
                sql.append(" AND  d.mzValorNivel2 is null ");
            }

            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel3 = :mzValorNivel3");
            } else {
                sql.append(" AND  d.mzValorNivel3 is null ");
            }

            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel4 = :mzValorNivel4");
            } else {
                sql.append(" AND  d.mzValorNivel4 is null");
            }

            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel5 = :mzValorNivel5");
            }

            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel6 = :mzValorNivel6");
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            }

            //SOLO SI EL CENTRO POBLADO ES MULTIORIGEN BUSCA TENIENDO EN CUENTA EL BARRIO
            if (direccionTabulada.getMultiOrigen() != null && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    sql.append(" AND  d.barrio = :barrio");
                }
            } else {
                //Si la direccion es diferente de CK si debe buscar con el barrio
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        sql.append(" AND  d.barrio = :barrio");
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itTipoPlaca = :itTipoPlaca");
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itValorPlaca = :itValorPlaca");
            }

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (busquedaPaginada) {
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResult);
            }

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {

                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    query.setParameter("carrera", "CARRERA");
                    query.setParameter("avenidacarrera", "AVENIDA CARRERA");
                } else {
                    query.setParameter("tipoViaPrincipal",
                            direccionTabulada.getTipoViaPrincipal().toUpperCase());
                }
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1",
                        direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2",
                        direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3",
                        direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4",
                        direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {

                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            direccionTabulada.getMzTipoNivel5().toUpperCase());
                }
            }
            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                query.setParameter("mzTipoNivel6",
                        direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1",
                        direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2",
                        direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3",
                        direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4",
                        direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5",
                        direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                query.setParameter("mzValorNivel6",
                        direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getMultiOrigen() != null && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    query.setParameter("barrio",
                            direccionTabulada.getBarrio().toUpperCase());
                }
            } else {
                //Si la direccion es diferente de CK si debe buscar con el barrio
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        query.setParameter("barrio",
                                direccionTabulada.getBarrio().toUpperCase());
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                query.setParameter("itTipoPlaca",
                        direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                query.setParameter("itValorPlaca",
                        direccionTabulada.getItValorPlaca().toUpperCase());
            }

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabuladaMER de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección para nodo
     * cercano.Encuentra las direcciones que coinciden con los criterios de
     * busqueda
     *
     * @param direccion la dirección construida
     * @param direccionTabulada
     * @param gpo ciudad por la que se desea filtrar
     *
     *
     * @author Juan David Hernandez
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaNodoCercano(BigDecimal centroPobladoId, DrDireccion direccionTabulada) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "and d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "and d.estadoRegistro = 1");

            //SI ES CARRERA LA BUSCA POR AVENIDA CARRERA TAMBIEN
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {

                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    sql.append(" AND (d.tipoViaPrincipal =:carrera OR d.tipoViaPrincipal =:avenidacarrera) ");
                } else {
                    sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
                }
            } else {
                sql.append(" AND d.tipoViaPrincipal is null ");
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                sql.append(" AND d.numViaPrincipal is null ");
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                sql.append(" AND d.ltViaPrincipal is null ");
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                sql.append(" AND d.nlPostViaP is null ");
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                sql.append(" AND d.bisViaPrincipal is null ");
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                sql.append(" AND d.cuadViaPrincipal is null");
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                sql.append(" AND d.tipoViaGeneradora is null ");
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                sql.append(" AND d.numViaGeneradora is null ");
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                sql.append(" AND d.ltViaGeneradora is null ");
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                sql.append(" AND d.nlPostViaG is null ");
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                sql.append(" AND d.bisViaGeneradora is null");
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                sql.append(" AND d.cuadViaGeneradora is null ");
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            }

            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel1 = :mzTipoNivel1");
            } else {
                sql.append(" AND  d.mzTipoNivel1 is null ");
            }

            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel2 = :mzTipoNivel2");
            } else {
                sql.append(" AND  d.mzTipoNivel2 is null ");
            }

            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel3 = :mzTipoNivel3");
            } else {
                sql.append(" AND  d.mzTipoNivel3 is null");
            }

            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel4 = :mzTipoNivel4");
            } else {
                sql.append(" AND  d.mzTipoNivel4 is null ");
            }

            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel5 = :mzTipoNivel5");
            } else {
                sql.append(" AND  d.mzTipoNivel5 is null ");
            }

            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel6 = :mzTipoNivel6");
            } else {
                sql.append(" AND  d.mzTipoNivel6 is null ");
            }

            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel1 = :mzValorNivel1");
            } else {
                sql.append(" AND  d.mzValorNivel1 is null ");
            }

            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel2 = :mzValorNivel2");
            } else {
                sql.append(" AND  d.mzValorNivel2 is null ");
            }

            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel3 = :mzValorNivel3");
            } else {
                sql.append(" AND  d.mzValorNivel3 is null ");
            }

            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel4 = :mzValorNivel4");
            } else {
                sql.append(" AND  d.mzValorNivel4 is null");
            }

            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel5 = :mzValorNivel5");
            }

            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel6 = :mzValorNivel6");
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            }

            if (direccionTabulada.getMultiOrigen() != null
                    && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    sql.append(" AND  d.barrio = :barrio");
                }
            } else {
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        sql.append(" AND  d.barrio = :barrio");
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itTipoPlaca = :itTipoPlaca");
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itValorPlaca = :itValorPlaca");
            }

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {

                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    query.setParameter("carrera", "CARRERA");
                    query.setParameter("avenidacarrera", "AVENIDA CARRERA");
                } else {
                    query.setParameter("tipoViaPrincipal",
                            direccionTabulada.getTipoViaPrincipal().toUpperCase());
                }
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1",
                        direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2",
                        direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3",
                        direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4",
                        direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                query.setParameter("mzTipoNivel5",
                        direccionTabulada.getMzTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                query.setParameter("mzTipoNivel6",
                        direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1",
                        direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2",
                        direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3",
                        direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4",
                        direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5",
                        direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                query.setParameter("mzValorNivel6",
                        direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getMultiOrigen() != null
                    && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    query.setParameter("barrio",
                            direccionTabulada.getBarrio().toUpperCase());
                }
            } else {
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        query.setParameter("barrio",
                                direccionTabulada.getBarrio().toUpperCase());
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                query.setParameter("itTipoPlaca",
                        direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                query.setParameter("itValorPlaca",
                        direccionTabulada.getItValorPlaca().toUpperCase());
            }

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabulada de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección
     *
     * @param direccion la dirección construida
     * @param direccionTabulada
     * @param gpo ciudad por la que se desea filtrar
     *
     *
     * @author Juan David Hernandez
     * @return
     */
    public int countBuscarDireccionTabulada(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) {

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT count(1) FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "and d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "and d.estadoRegistro = 1");

            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
            } else {
                sql.append(" AND d.tipoViaPrincipal is null ");
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                sql.append(" AND d.numViaPrincipal is null ");
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                sql.append(" AND d.ltViaPrincipal is null ");
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                sql.append(" AND d.nlPostViaP is null ");
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                sql.append(" AND d.bisViaPrincipal is null ");
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                sql.append(" AND d.cuadViaPrincipal is null");
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                sql.append(" AND d.tipoViaGeneradora is null ");
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                sql.append(" AND d.numViaGeneradora is null ");
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                sql.append(" AND d.ltViaGeneradora is null ");
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                sql.append(" AND d.nlPostViaG is null ");
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                sql.append(" AND d.bisViaGeneradora is null");
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                sql.append(" AND d.cuadViaGeneradora is null ");
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            } else {
                sql.append(" AND d.placaDireccion is null ");
            }

            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel1 = :mzTipoNivel1");
            } else {
                sql.append(" AND  d.mzTipoNivel1 is null ");
            }

            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel2 = :mzTipoNivel2");
            } else {
                sql.append(" AND  d.mzTipoNivel2 is null ");
            }

            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel3 = :mzTipoNivel3");
            } else {
                sql.append(" AND  d.mzTipoNivel3 is null");
            }

            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel4 = :mzTipoNivel4");
            } else {
                sql.append(" AND  d.mzTipoNivel4 is null ");
            }

            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel5 = :mzTipoNivel5");
            } else {
                sql.append(" AND  d.mzTipoNivel5 is null ");
            }

            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel6 = :mzTipoNivel6");
            } else {
                sql.append(" AND  d.mzTipoNivel6 is null ");
            }

            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel1 = :mzValorNivel1");
            } else {
                sql.append(" AND  d.mzValorNivel1 is null ");
            }

            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel2 = :mzValorNivel2");
            } else {
                sql.append(" AND  d.mzValorNivel2 is null ");
            }

            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel3 = :mzValorNivel3");
            } else {
                sql.append(" AND  d.mzValorNivel3 is null ");
            }

            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel4 = :mzValorNivel4");
            } else {
                sql.append(" AND  d.mzValorNivel4 is null");
            }

            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel5 = :mzValorNivel5");
            }

            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel6 = :mzValorNivel6");
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            }

            if (direccionTabulada.getBarrio() != null
                    && !direccionTabulada.getBarrio().trim().isEmpty()) {
                sql.append(" AND  d.barrio = :barrio");
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itTipoPlaca = :itTipoPlaca");
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itValorPlaca = :itValorPlaca");
            }

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                query.setParameter("tipoViaPrincipal",
                        direccionTabulada.getTipoViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1",
                        direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2",
                        direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3",
                        direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4",
                        direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                query.setParameter("mzTipoNivel5",
                        direccionTabulada.getMzTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                query.setParameter("mzTipoNivel6",
                        direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1",
                        direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2",
                        direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3",
                        direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4",
                        direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5",
                        direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                query.setParameter("mzValorNivel6",
                        direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getBarrio() != null
                    && !direccionTabulada.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio",
                        direccionTabulada.getBarrio().toUpperCase());
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                query.setParameter("itTipoPlaca",
                        direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                query.setParameter("itValorPlaca",
                        direccionTabulada.getItValorPlaca().toUpperCase());
            }

            int result = query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabulada de CmtDireccionDetalleMglDaoImpl " + e);
        }
        return 0;

    }

    /**
     * Obtiene una dirección detallada por id de dirección y id de la
     * subdireccion
     *
     * @param dirId id de la dirección
     * @param sdirId id de la subdireccion
     *
     * @return dirección detalla encontrada
     *
     * @author Juan David Hernandez
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirIdSdirId(BigDecimal dirId, BigDecimal sdirId) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.direccion.dirId = :dirId ");

            if (sdirId != null && !sdirId.equals(Constant.BASICA_ID_DEFAULT)) {
                sql.append(" AND d.subDireccion.sdiId = :sdirId");
            } else {
                sql.append(" AND d.subDireccion is null ");
            }

            sql.append(" and d.estadoRegistro =:estado ");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("estado", 1);
            query.setParameter("dirId", dirId);
            if (sdirId != null) {
                query.setParameter("sdirId", sdirId);
            }

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabulada de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * valbuenayf Metodo para consultar la direccion tabulada , id de la ciudad
     *
     * @param idCiudad
     * @param direccionTabulada
     * @param rowNum
     *
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarTodasDireccionTabulada(BigDecimal idCiudad, DrDireccion direccionTabulada, Integer rowNum) {
        List<CmtDireccionDetalladaMgl> resultList1 = new ArrayList<CmtDireccionDetalladaMgl>();
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "and d.direccion.ubicacion.gpoIdObj.gpoId = :idCiudad and d.estadoRegistro = 1");

            Query query = entityManager.createQuery(sql.toString(), CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (direccionTabulada.getIdTipoDireccion() != null && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion", direccionTabulada.getIdTipoDireccion());
            }

            if (idCiudad != null) {
                query.setParameter("idCiudad", idCiudad);
            }

            resultList1 = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabulada de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList1;
    }

    /**
     * Obtiene hhpp por codigo dane
     *
     * @param coordenadasDto contiene el codigo dane con el que se desea filtrar
     *
     * return true si se actualiza correctamente el registro.
     *
     * @author Juan David Hernandez
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByCoordenadas(double latitudRestada,
            double longitudRestada, double latitudAumentada, double longitudAumentada, String codigoDane)
            throws ApplicationException {
        List<CmtDireccionDetalladaMgl> direccionDetalladaList;
        try {
            Query query = entityManager.createQuery("SELECT d FROM CmtDireccionDetalladaMgl d "
                    + "WHERE d.direccion.ubicacion.gpoIdObj.geoCodigoDane = :codigoDane AND "
                    + "d.direccion.ubicacion.ubiLatitudNum BETWEEN :latitudRestada AND :latitudAumentada AND "
                    + "d.direccion.ubicacion.ubiLongitudNum BETWEEN :longitudRestada AND :longitudAumentada AND "
                    + "d.estadoRegistro = 1 ");
            query.setParameter("codigoDane", codigoDane);
            query.setParameter("latitudRestada", latitudRestada);
            query.setParameter("longitudRestada", longitudRestada);
            query.setParameter("latitudAumentada", latitudAumentada);
            query.setParameter("longitudAumentada", longitudAumentada);

            direccionDetalladaList = (List<CmtDireccionDetalladaMgl>) query.getResultList();
            getEntityManager().clear();
            return direccionDetalladaList;

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error buscando direccion detallada por coordenadas"
                    + " debido a: " + ex.getMessage());
        }
    }

    /**
     * Obtiene listado el conteo de direccion detalladas por dirección
     * actualemente se utuliza en la pantalla de modelo de hhpp
     *
     * @param direccion la dirección construida
     * @param gpo ciudad por la que se desea filtrar *
     *
     * @author Juan David Hernandez
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countDireccionTabulada(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT count(1) FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "AND d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1");

            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
            } else {
                sql.append(" AND d.tipoViaPrincipal is null ");
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                sql.append(" AND d.numViaPrincipal is null ");
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                sql.append(" AND d.ltViaPrincipal is null ");
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                sql.append(" AND d.nlPostViaP is null ");
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                sql.append(" AND d.bisViaPrincipal is null ");
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                sql.append(" AND d.cuadViaPrincipal is null");
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                sql.append(" AND d.tipoViaGeneradora is null ");
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                sql.append(" AND d.numViaGeneradora is null ");
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                sql.append(" AND d.ltViaGeneradora is null ");
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                sql.append(" AND d.nlPostViaG is null ");
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                sql.append(" AND d.bisViaGeneradora is null");
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                sql.append(" AND d.cuadViaGeneradora is null ");
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            } else {
                sql.append(" AND d.placaDireccion is null ");
            }

            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel1 = :mzTipoNivel1");
            } else {
                sql.append(" AND  d.mzTipoNivel1 is null ");
            }

            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel2 = :mzTipoNivel2");
            } else {
                sql.append(" AND  d.mzTipoNivel2 is null ");
            }

            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel3 = :mzTipoNivel3");
            } else {
                sql.append(" AND  d.mzTipoNivel3 is null");
            }

            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel4 = :mzTipoNivel4");
            } else {
                sql.append(" AND  d.mzTipoNivel4 is null ");
            }

            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sql.append(" AND  (d.mzTipoNivel5 =:casa OR d.mzTipoNivel5 =:lote) ");
                } else {
                    sql.append(" AND  d.mzTipoNivel5 = :mzTipoNivel5");
                }

            } else {
                sql.append(" AND  d.mzTipoNivel5 is null ");
            }

            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel6 = :mzTipoNivel6");
            } else {
                sql.append(" AND  d.mzTipoNivel6 is null ");
            }

            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel1 = :mzValorNivel1");
            } else {
                sql.append(" AND  d.mzValorNivel1 is null ");
            }

            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel2 = :mzValorNivel2");
            } else {
                sql.append(" AND  d.mzValorNivel2 is null ");
            }

            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel3 = :mzValorNivel3");
            } else {
                sql.append(" AND  d.mzValorNivel3 is null ");
            }

            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel4 = :mzValorNivel4");
            } else {
                sql.append(" AND  d.mzValorNivel4 is null");
            }

            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel5 = :mzValorNivel5");
            }

            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel6 = :mzValorNivel6");
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            }

            if (direccionTabulada.getBarrio() != null
                    && !direccionTabulada.getBarrio().trim().isEmpty()) {
                sql.append(" AND  d.barrio = :barrio");
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itTipoPlaca = :itTipoPlaca");
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itValorPlaca = :itValorPlaca");
            }

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                query.setParameter("tipoViaPrincipal",
                        direccionTabulada.getTipoViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1",
                        direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2",
                        direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3",
                        direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4",
                        direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {

                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            direccionTabulada.getMzTipoNivel5().toUpperCase());
                }
            }
            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                query.setParameter("mzTipoNivel6",
                        direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1",
                        direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2",
                        direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3",
                        direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4",
                        direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5",
                        direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                query.setParameter("mzValorNivel6",
                        direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getBarrio() != null
                    && !direccionTabulada.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio",
                        direccionTabulada.getBarrio().toUpperCase());
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                query.setParameter("itTipoPlaca",
                        direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                query.setParameter("itValorPlaca",
                        direccionTabulada.getItValorPlaca().toUpperCase());
            }

            int result = query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabulada de CmtDireccionDetalleMglDaoImpl " + e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public CmtDireccionDetalladaMgl findById(BigDecimal idDireccionDetallada) throws ApplicationException {
        try {
            CmtDireccionDetalladaMgl result;
            Query query = entityManager.createQuery("select s from CmtDireccionDetalladaMgl s where s.direccionDetalladaId = :idDireccionDetallada AND s.estadoRegistro = 1");
            query.setParameter("idDireccionDetallada", idDireccionDetallada);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (CmtDireccionDetalladaMgl) query.getSingleResult();
            getEntityManager().clear();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No fue encontrada la solicitud: " + idDireccionDetallada);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Función que encuentra una dirección por la columna direccion texto, la
     * direccion debe ser la que entrega el geo si es CK o si es Bm o IT la que
     * es estandarizada
     *
     * @param direccionTexto
     * @param centroPobladoId
     * @param resultadoUnico
     * @param barrio
     * @param ciudadGpo
     * @return direcciones detallas que cumplan el criterio de busqueda
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByDireccionTexto(String direccionTexto,
            BigDecimal centroPobladoId, boolean resultadoUnico, String barrio, String tipoDireccion) throws ApplicationException {
        try {
            List<CmtDireccionDetalladaMgl> result;
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPobladoId);

            StringBuilder sql = new StringBuilder();
            sql.append("select s from CmtDireccionDetalladaMgl s "
                    + "where s.direccionTexto like :direccionTexto AND "
                    + "s.direccion.ubicacion.gpoIdObj.gpoId =:centroPobladoId and s.estadoRegistro = 1 ");

            //SOLO BUSCA CON BARRIO SI ES MULTIORIGEN
            if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                    && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")
                    && tipoDireccion != null && tipoDireccion.equalsIgnoreCase("CK")) {
                if (barrio != null
                        && !barrio.isEmpty()) {
                    sql.append(" AND s.barrio =:barrio ");
                }
            } else {
                if (tipoDireccion != null && !tipoDireccion.equalsIgnoreCase("CK")) {
                    if (barrio != null
                            && !barrio.isEmpty()) {
                        sql.append(" AND s.barrio =:barrio ");
                    }
                }
            }

            Query query = entityManager.createQuery(sql.toString());

            query.setParameter("centroPobladoId", centroPobladoId);
            //Si es true busca una unica direccion, sino todas las que la contengan
            if (resultadoUnico) {
                query.setParameter("direccionTexto", direccionTexto.trim());
            } else {
                query.setParameter("direccionTexto", direccionTexto.trim() + "%");
            }

            if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                    && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")
                    && tipoDireccion != null && tipoDireccion.equalsIgnoreCase("CK")) {
                if (barrio != null
                        && !barrio.isEmpty()) {
                    query.setParameter("barrio", barrio.toUpperCase());
                }
            } else {
                if (tipoDireccion != null && !tipoDireccion.equalsIgnoreCase("CK")) {
                    if (barrio != null
                            && !barrio.isEmpty()) {
                        query.setParameter("barrio", barrio.toUpperCase());
                    }
                }
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//j pa 2.0
            result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección
     *
     * @param direccionTabulada la dirección construida
     * @param centroPobladoId ciudad por la que se desea filtrar
     * @param idDireccionDetalladaList ids que fueron resultado de la busqueda
     * por texto de la direccion hasta la placa
     *
     * @author Juan David Hernandez
     * @param maxResult
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaNivelesComplementosById(List<BigDecimal> idDireccionDetalladaList,
            BigDecimal centroPobladoId, DrDireccion direccionTabulada) throws ApplicationException {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<CmtDireccionDetalladaMgl>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1 ");

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            }

            sql.append(" AND  d.direccionDetalladaId IN :idDireccionDetalladaList ");

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            query.setParameter("idDireccionDetalladaList", idDireccionDetalladaList);

            resultList = query.getResultList();
        } catch (NoResultException e) {
            String msg = "No se encontraron resultados en la busqueda de direccion por texto por ids'" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección texto
     *
     * @param direccionTabulada la dirección construida
     * @param centroPobladoId ciudad por la que se desea filtrar
     * @param idDireccionDetalladaList ids que fueron resultado de la busqueda
     * por texto de la direccion hasta la placa
     *
     * @author Juan David Hernandez
     * @param direccionTexto
     * @param maxResult
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaNivelesComplementosByDireccionTexto(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada, String direccionTexto) throws ApplicationException {
        List<CmtDireccionDetalladaMgl> resultList = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1 AND d.direccionTexto like :direccionTexto  ");

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            }

            //SOLO BUSCA CON BARRIO SI ES MULTIORIGEN
            if (direccionTabulada.getMultiOrigen() != null
                    && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    sql.append(" AND  d.barrio = :barrio");
                }
            } else {
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        sql.append(" AND  d.barrio = :barrio");
                    }
                }
            }

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getMultiOrigen() != null
                    && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {

                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    query.setParameter("barrio",
                            direccionTabulada.getBarrio().toUpperCase());
                }
            } else {
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        query.setParameter("barrio",
                                direccionTabulada.getBarrio().toUpperCase());
                    }
                }
            }

            query.setParameter("direccionTexto", direccionTexto.trim() + "%");

            resultList = query.getResultList();
        } catch (NoResultException e) {
            String msg = "No se encontraron resultados en la busqueda de direccion por texto, '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Obtiene listado direcciones consultada por barrio
     *
     * @param centroPobladoId la dirección construida
     * @param barrio ciudad por la que se desea filtrar
     *
     * @author Juan David Hernandez
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaByBarrioNodo(BigDecimal centroPobladoId,
            String barrio, String tipoDireccion, String codigoNodo) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl d ");
            if (codigoNodo != null && !codigoNodo.isEmpty()) {
                sql.append(" ,HhppMgl h ");
            }
            sql.append(" WHERE d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId ");

            if (codigoNodo != null && !codigoNodo.isEmpty()) {
                sql.append(" and d.direccion.dirId = h.direccionObj.dirId ");
                sql.append(" and d.subDireccion.sdiId = h.SubDireccionObj.sdiId ");
                sql.append(" and h.nodId.nodCodigo =:codigoNodo ");
                sql.append(" and h.estadoRegistro = 1 ");
            }
            sql.append(" AND d.estadoRegistro = 1 ");

            if (tipoDireccion != null && !tipoDireccion.isEmpty()) {
                sql.append(" AND d.idTipoDireccion = :idTipoDireccion ");
            }

            if (barrio != null
                    && !barrio.isEmpty()) {
                sql.append(" AND d.barrio = :barrio ");
            }

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (tipoDireccion != null && !tipoDireccion.isEmpty()) {
                query.setParameter("idTipoDireccion", tipoDireccion);
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }

            if (barrio != null && !barrio.isEmpty()) {
                query.setParameter("barrio", barrio);
            }

            if (codigoNodo != null && !codigoNodo.isEmpty()) {
                query.setParameter("codigoNodo", codigoNodo);
            }

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabulada de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     * @author bocanegravm
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaUnica(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "AND d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1");

            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
            } else {
                sql.append(" AND d.tipoViaPrincipal is null ");
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                sql.append(" AND d.numViaPrincipal is null ");
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                sql.append(" AND d.ltViaPrincipal is null ");
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                sql.append(" AND d.nlPostViaP is null ");
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                sql.append(" AND d.bisViaPrincipal is null ");
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                sql.append(" AND d.cuadViaPrincipal is null");
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                sql.append(" AND d.tipoViaGeneradora is null ");
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                sql.append(" AND d.numViaGeneradora is null ");
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                sql.append(" AND d.ltViaGeneradora is null ");
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                sql.append(" AND d.nlPostViaG is null ");
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                sql.append(" AND d.bisViaGeneradora is null");
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                sql.append(" AND d.cuadViaGeneradora is null ");
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            } else {
                sql.append(" AND d.placaDireccion is null ");
            }

            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel1 = :mzTipoNivel1");
            } else {
                sql.append(" AND  d.mzTipoNivel1 is null ");
            }

            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel2 = :mzTipoNivel2");
            } else {
                sql.append(" AND  d.mzTipoNivel2 is null ");
            }

            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel3 = :mzTipoNivel3");
            } else {
                sql.append(" AND  d.mzTipoNivel3 is null");
            }

            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel4 = :mzTipoNivel4");
            } else {
                sql.append(" AND  d.mzTipoNivel4 is null ");
            }

            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sql.append(" AND  (d.mzTipoNivel5 = :casa OR d.mzTipoNivel5 =:lote) ");
                } else {
                    sql.append(" AND  d.mzTipoNivel5 = :mzTipoNivel5");
                }

            } else {
                sql.append(" AND  d.mzTipoNivel5 is null ");
            }

            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel6 = :mzTipoNivel6");
            } else {
                sql.append(" AND  d.mzTipoNivel6 is null ");
            }

            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel1 = :mzValorNivel1");
            } else {
                sql.append(" AND  d.mzValorNivel1 is null ");
            }

            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel2 = :mzValorNivel2");
            } else {
                sql.append(" AND  d.mzValorNivel2 is null ");
            }

            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel3 = :mzValorNivel3");
            } else {
                sql.append(" AND  d.mzValorNivel3 is null ");
            }

            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel4 = :mzValorNivel4");
            } else {
                sql.append(" AND  d.mzValorNivel4 is null");
            }

            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel5 = :mzValorNivel5");
            } else {
                sql.append(" AND  d.mzValorNivel5 is null");
            }

            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel6 = :mzValorNivel6");
            } else {
                sql.append(" AND  d.mzValorNivel6 is null");
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            } else {
                sql.append(" AND  d.cpTipoNivel1 is null");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            } else {
                sql.append(" AND  d.cpTipoNivel2 is null");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            } else {
                sql.append(" AND  d.cpTipoNivel3 is null");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            } else {
                sql.append(" AND  d.cpTipoNivel4 is null");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            } else {
                sql.append(" AND (d.cpTipoNivel5 is null OR d.cpTipoNivel5 = 'CASA')");

            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            } else {
                sql.append(" AND  d.cpTipoNivel6 is null");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            } else {
                sql.append(" AND  d.cpValorNivel1 is null");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            } else {
                sql.append(" AND  d.cpValorNivel2 is null");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            } else {
                sql.append(" AND  d.cpValorNivel3 is null");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            } else {
                sql.append(" AND  d.cpValorNivel4 is null");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            } else {
                sql.append(" AND  d.cpValorNivel5 is null");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            } else {
                sql.append(" AND  d.cpValorNivel6 is null");
            }

            if (direccionTabulada.getBarrio() != null
                    && !direccionTabulada.getBarrio().trim().isEmpty()) {
                sql.append(" AND  d.barrio = :barrio");
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itTipoPlaca = :itTipoPlaca");
            } else {
                sql.append(" AND  d.itTipoPlaca is null");
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itValorPlaca = :itValorPlaca");
            } else {
                sql.append(" AND  d.itValorPlaca is null");
            }

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                query.setParameter("tipoViaPrincipal",
                        direccionTabulada.getTipoViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1",
                        direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2",
                        direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3",
                        direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4",
                        direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {

                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            direccionTabulada.getMzTipoNivel5().toUpperCase());
                }
            }
            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                query.setParameter("mzTipoNivel6",
                        direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1",
                        direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2",
                        direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3",
                        direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4",
                        direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5",
                        direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                query.setParameter("mzValorNivel6",
                        direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getBarrio() != null
                    && !direccionTabulada.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio",
                        direccionTabulada.getBarrio().toUpperCase());
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                query.setParameter("itTipoPlaca",
                        direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                query.setParameter("itValorPlaca",
                        direccionTabulada.getItValorPlaca().toUpperCase());
            }

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabuladaUnica de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Obtiene Lista de dirección detallada por id de dirección
     *
     * @param dirId id de la dirección
     * @param idDetallada id de la dirección
     * @return List<CmtDireccionDetalladaMgl>
     *
     * @author Victor Bocanegra
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirId(BigDecimal dirId,
            BigDecimal idDetallada) {
        List<CmtDireccionDetalladaMgl> resultList = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.direccion.dirId = :dirId  AND "
                    + "d.direccionDetalladaId != :idDetallada");

            sql.append(" and d.estadoRegistro =:estado ");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("estado", 1);
            query.setParameter("dirId", dirId);
            query.setParameter("idDetallada", idDetallada);

            resultList = query.getResultList();
        } catch (NoResultException e) {
            LOGGER.error("Error en findDireccionDetallaByDirId de CmtDireccionDetalleMglDaoImpl: " + e.getMessage() + " " + e);
        } catch (Exception ex) {
            LOGGER.error("Error en findDireccionDetallaByDirId de CmtDireccionDetalleMglDaoImpl: " + ex.getMessage() + " " + ex);
        }
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Obtiene listado de barrios por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     *
     * @author Victor Bocanegra
     * @return
     */
    public List<String> findBarriosDireccionTabuladaMer(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) {
        List<String> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT distinct d.barrio FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "AND d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1");

            //SI LLEGA CARRERA QUE TAMBIEN LO BUSQUE POR AVENIDA CARRERA
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    sql.append(" AND (d.tipoViaPrincipal = :carrera OR d.tipoViaPrincipal =:avenidacarrera) ");
                } else {
                    sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
                }
            } else {
                sql.append(" AND d.tipoViaPrincipal is null ");
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                sql.append(" AND d.numViaPrincipal is null ");
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                sql.append(" AND d.ltViaPrincipal is null ");
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                sql.append(" AND d.nlPostViaP is null ");
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                sql.append(" AND d.bisViaPrincipal is null ");
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                sql.append(" AND d.cuadViaPrincipal is null");
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                sql.append(" AND d.tipoViaGeneradora is null ");
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                sql.append(" AND d.numViaGeneradora is null ");
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                sql.append(" AND d.ltViaGeneradora is null ");
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                sql.append(" AND d.nlPostViaG is null ");
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                sql.append(" AND d.bisViaGeneradora is null");
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                sql.append(" AND d.cuadViaGeneradora is null ");
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            } else {
                sql.append(" AND d.placaDireccion is null ");
            }

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }

            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {

                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    query.setParameter("carrera", "CARRERA");
                    query.setParameter("avenidacarrera", "AVENIDA CARRERA");
                } else {
                    query.setParameter("tipoViaPrincipal",
                            direccionTabulada.getTipoViaPrincipal().toUpperCase());
                }
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabuladaMER de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    public CmtDireccionDetalladaMgl findByIdDetallada(BigDecimal idDireccionDetallada) throws ApplicationException {
        try {
            CmtDireccionDetalladaMgl result;
            Query query = entityManager.createQuery("select s from CmtDireccionDetalladaMgl s where s.direccionDetalladaId = :idDireccionDetallada AND s.estadoRegistro = 1");
            query.setParameter("idDireccionDetallada", idDireccionDetallada);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = query.getResultList().isEmpty() ? null : (CmtDireccionDetalladaMgl) query.getResultList().get(0);
            getEntityManager().clear();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No fue encontrada la solicitud: " + idDireccionDetallada);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<DireccionMerDto> findDirByIdDetallada(BigDecimal idDireccionDetallada) throws ApplicationException {
        try {

            String queryTipo = "SELECT DISTINCT A1.DIRECCION_DETALLADA_ID, C1.NOMBRE, C2.NOMBRE, T0.CUENTAMATRIZ_ID, T0.NUMERO_CUENTA, T0.NOMBRE_CUENTA, T1.DIRECCION_ID, E1.NOMBRE_SUBEDIFICIO, \n" +
                    "    A2.CP_TIPO_NIVEL1, A2.CP_TIPO_NIVEL2, A2.CP_TIPO_NIVEL3, A2.CP_TIPO_NIVEL4, A2.CP_TIPO_NIVEL5, A2.CP_TIPO_NIVEL6, A2.CP_VALOR_NIVEL1, A2.CP_VALOR_NIVEL2, A2.CP_VALOR_NIVEL3, A2.CP_VALOR_NIVEL4, A2.CP_VALOR_NIVEL5, A2.CP_VALOR_NIVEL6\n" +
                    "    FROM MGL_SCHEME.MGL_FACTIBILIDAD A1 \n" +
                    "    INNER JOIN MGL_SCHEME.MGL_DIRECCION_DETALLADA A2 ON A1.DIRECCION_DETALLADA_ID = A2.DIRECCION_DETALLADA_ID    \n" +
                    "    LEFT JOIN MGL_SCHEME.CMT_DIRECCION T1 ON A2.DIRECCION_ID = T1.DIRECCION_ID AND T1.ESTADO_REGISTRO = 1\n" +
                    "    LEFT JOIN MGL_SCHEME.CMT_CUENTA_MATRIZ T0 ON T0.CUENTAMATRIZ_ID = T1.CUENTAMATRIZ_ID AND T0.ESTADO_REGISTRO = 1\n" +
                    "    LEFT JOIN MGL_SCHEME.CMT_SUBEDIFICIO E1 ON E1.CUENTAMATRIZ_ID = T0.CUENTAMATRIZ_ID AND E1.ESTADO_REGISTRO = 1\n" +
                    "    LEFT JOIN MGL_SCHEME.MGL_GEOGRAFICO_POLITICO C1 ON T0.GPO_ID_DEPARTAMENTO = C1.GEOGRAFICO_POLITICO_ID\n" +
                    "    LEFT JOIN MGL_SCHEME.MGL_GEOGRAFICO_POLITICO C2 ON T0.GPO_ID_MUNICIPIO = C2.GEOGRAFICO_POLITICO_ID\n" +
                    "    WHERE A1.FACTIBILIDAD_ID = ? ";

            Query query = entityManager.createNativeQuery(queryTipo);
            query.setParameter(1, idDireccionDetallada);

            List<Object[]> listaR = query.getResultList();

            return resultToDirreccionMer(listaR);

        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No fue encontrada la solicitud: " + idDireccionDetallada);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    private List<DireccionMerDto> resultToDirreccionMer(List<Object[]> listaR) {

        List<DireccionMerDto> listDireccionMerDtos = new ArrayList<>();

        for(Object[] dir : listaR){
            DireccionMerDto direccionMerDto = DireccionMerDto.builder()
                    .area(dir[1] != null ? dir[1].toString() : "")
                    .centroPoblado(dir[2] != null ? dir[2].toString() : "")
                    .ciudad(dir[3] != null ? dir[3].toString() : "")
                    .departamento(dir[4] != null ? dir[4].toString() : "")
                    .distrito(dir[5] != null ? dir[5].toString() : "")
                    .division(dir[6] != null ? dir[6].toString() : "")
                    .build();
            listDireccionMerDtos.add(direccionMerDto);
        }
        return listDireccionMerDtos;
    }


    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaCcmmByDirId(BigDecimal dirId) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.direccion.dirId = :dirId AND d.subDireccion is null "
                    + "AND d.cpTipoNivel5 is null and d.estadoRegistro = 1");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            query.setParameter("dirId", dirId);

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabulada de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaMerExacta(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "AND d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1");

            //SI LLEGA CARRERA QUE TAMBIEN LO BUSQUE POR AVENIDA CARRERA
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    sql.append(" AND (d.tipoViaPrincipal = :carrera OR d.tipoViaPrincipal =:avenidacarrera) ");
                } else {
                    sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
                }
            } else {
                sql.append(" AND d.tipoViaPrincipal is null ");
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                sql.append(" AND d.numViaPrincipal is null ");
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                sql.append(" AND d.ltViaPrincipal is null ");
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                sql.append(" AND d.nlPostViaP is null ");
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                sql.append(" AND d.bisViaPrincipal is null ");
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                sql.append(" AND d.cuadViaPrincipal is null");
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                sql.append(" AND d.tipoViaGeneradora is null ");
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                sql.append(" AND d.numViaGeneradora is null ");
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                sql.append(" AND d.ltViaGeneradora is null ");
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                sql.append(" AND d.nlPostViaG is null ");
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                sql.append(" AND d.bisViaGeneradora is null");
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                sql.append(" AND d.cuadViaGeneradora is null ");
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            } else {
                sql.append(" AND d.placaDireccion is null ");
            }

            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel1 = :mzTipoNivel1");
            } else {
                sql.append(" AND  d.mzTipoNivel1 is null ");
            }

            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel2 = :mzTipoNivel2");
            } else {
                sql.append(" AND  d.mzTipoNivel2 is null ");
            }

            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel3 = :mzTipoNivel3");
            } else {
                sql.append(" AND  d.mzTipoNivel3 is null");
            }

            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel4 = :mzTipoNivel4");
            } else {
                sql.append(" AND  d.mzTipoNivel4 is null ");
            }

            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sql.append(" AND  (d.mzTipoNivel5 =:casa OR d.mzTipoNivel5 =:lote) ");
                } else {
                    sql.append(" AND  d.mzTipoNivel5 = :mzTipoNivel5");
                }

            } else {
                sql.append(" AND  d.mzTipoNivel5 is null ");
            }

            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel6 = :mzTipoNivel6");
            } else {
                sql.append(" AND  d.mzTipoNivel6 is null ");
            }

            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel1 = :mzValorNivel1");
            } else {
                sql.append(" AND  d.mzValorNivel1 is null ");
            }

            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel2 = :mzValorNivel2");
            } else {
                sql.append(" AND  d.mzValorNivel2 is null ");
            }

            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel3 = :mzValorNivel3");
            } else {
                sql.append(" AND  d.mzValorNivel3 is null ");
            }

            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel4 = :mzValorNivel4");
            } else {
                sql.append(" AND  d.mzValorNivel4 is null");
            }

            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel5 = :mzValorNivel5");
            } else {
                sql.append(" AND  d.mzValorNivel5 is null");
            }

            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel6 = :mzValorNivel6");
            } else {
                sql.append(" AND  d.mzValorNivel6 is null");
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            } else {
                sql.append(" AND  d.cpTipoNivel1 is null");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            } else {
                sql.append(" AND  d.cpTipoNivel2 is null");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            } else {
                sql.append(" AND  d.cpTipoNivel3 is null");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            } else {
                sql.append(" AND  d.cpTipoNivel4 is null");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            } else {
                sql.append(" AND  d.cpTipoNivel5 is null");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            } else {
                sql.append(" AND  d.cpTipoNivel6 is null");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            } else {
                sql.append(" AND  d.cpValorNivel1 is null");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            } else {
                sql.append(" AND  d.cpValorNivel2 is null");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            } else {
                sql.append(" AND  d.cpValorNivel3 is null");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            } else {
                sql.append(" AND  d.cpValorNivel4 is null");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            } else {
                sql.append(" AND  d.cpValorNivel5 is null");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            } else {
                sql.append(" AND  d.cpValorNivel6 is null");
            }

            //SOLO SI EL CENTRO POBLADO ES MULTIORIGEN BUSCA TENIENDO EN CUENTA EL BARRIO
            if (direccionTabulada.getMultiOrigen() != null && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    sql.append(" AND  d.barrio = :barrio");
                }
            } else {
                //Si la direccion es diferente de CK si debe buscar con el barrio
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        sql.append(" AND  d.barrio = :barrio");
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itTipoPlaca = :itTipoPlaca");
            } else {
                sql.append(" AND  d.itTipoPlaca is null");
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itValorPlaca = :itValorPlaca");
            } else {
                sql.append(" AND  d.itValorPlaca is null");
            }

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {

                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    query.setParameter("carrera", "CARRERA");
                    query.setParameter("avenidacarrera", "AVENIDA CARRERA");
                } else {
                    query.setParameter("tipoViaPrincipal",
                            direccionTabulada.getTipoViaPrincipal().toUpperCase());
                }
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1",
                        direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2",
                        direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3",
                        direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4",
                        direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {

                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            direccionTabulada.getMzTipoNivel5().toUpperCase());
                }
            }
            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                query.setParameter("mzTipoNivel6",
                        direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1",
                        direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2",
                        direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3",
                        direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4",
                        direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5",
                        direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                query.setParameter("mzValorNivel6",
                        direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getMultiOrigen() != null && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    query.setParameter("barrio",
                            direccionTabulada.getBarrio().toUpperCase());
                }
            } else {
                //Si la direccion es diferente de CK si debe buscar con el barrio
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        query.setParameter("barrio",
                                direccionTabulada.getBarrio().toUpperCase());
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                query.setParameter("itTipoPlaca",
                        direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                query.setParameter("itValorPlaca",
                        direccionTabulada.getItValorPlaca().toUpperCase());
            }

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabuladaMER de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaMerPrincipalExacta(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) {
        List<CmtDireccionDetalladaMgl> resultList
                = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl "
                    + "d WHERE d.idTipoDireccion = :idTipoDireccion "
                    + "AND d.direccion.ubicacion.gpoIdObj.gpoId = :centroPobladoId "
                    + "AND d.estadoRegistro = 1");

            //SI LLEGA CARRERA QUE TAMBIEN LO BUSQUE POR AVENIDA CARRERA
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    sql.append(" AND (d.tipoViaPrincipal = :carrera OR d.tipoViaPrincipal =:avenidacarrera) ");
                } else {
                    sql.append(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
                }
            } else {
                sql.append(" AND d.tipoViaPrincipal is null ");
            }

            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.numViaPrincipal = :numViaPrincipal");
            } else {
                sql.append(" AND d.numViaPrincipal is null ");
            }

            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.ltViaPrincipal = :ltViaPrincipal");
            } else {
                sql.append(" AND d.ltViaPrincipal is null ");
            }

            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaP = :nlPostViaP");
            } else {
                sql.append(" AND d.nlPostViaP is null ");
            }

            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.bisViaPrincipal = :bisViaPrincipal");
            } else {
                sql.append(" AND d.bisViaPrincipal is null ");
            }

            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                sql.append(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            } else {
                sql.append(" AND d.cuadViaPrincipal is null");
            }

            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            } else {
                sql.append(" AND d.tipoViaGeneradora is null ");
            }

            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.numViaGeneradora = :numViaGeneradora");
            } else {
                sql.append(" AND d.numViaGeneradora is null ");
            }

            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.ltViaGeneradora = :ltViaGeneradora");
            } else {
                sql.append(" AND d.ltViaGeneradora is null ");
            }

            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                sql.append(" AND d.nlPostViaG = :nlPostViaG");
            } else {
                sql.append(" AND d.nlPostViaG is null ");
            }

            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.bisViaGeneradora = :bisViaGeneradora");
            } else {
                sql.append(" AND d.bisViaGeneradora is null");
            }

            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                sql.append(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            } else {
                sql.append(" AND d.cuadViaGeneradora is null ");
            }

            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                sql.append(" AND d.placaDireccion = :placaDireccion");
            } else {
                sql.append(" AND d.placaDireccion is null ");
            }

            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel1 = :mzTipoNivel1");
            } else {
                sql.append(" AND  d.mzTipoNivel1 is null ");
            }

            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel2 = :mzTipoNivel2");
            } else {
                sql.append(" AND  d.mzTipoNivel2 is null ");
            }

            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel3 = :mzTipoNivel3");
            } else {
                sql.append(" AND  d.mzTipoNivel3 is null");
            }

            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel4 = :mzTipoNivel4");
            } else {
                sql.append(" AND  d.mzTipoNivel4 is null ");
            }

            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sql.append(" AND  (d.mzTipoNivel5 =:casa OR d.mzTipoNivel5 =:lote) ");
                } else {
                    sql.append(" AND  d.mzTipoNivel5 = :mzTipoNivel5");
                }

            } else {
                sql.append(" AND  d.mzTipoNivel5 is null ");
            }

            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzTipoNivel6 = :mzTipoNivel6");
            } else {
                sql.append(" AND  d.mzTipoNivel6 is null ");
            }

            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel1 = :mzValorNivel1");
            } else {
                sql.append(" AND  d.mzValorNivel1 is null ");
            }

            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel2 = :mzValorNivel2");
            } else {
                sql.append(" AND  d.mzValorNivel2 is null ");
            }

            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel3 = :mzValorNivel3");
            } else {
                sql.append(" AND  d.mzValorNivel3 is null ");
            }

            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel4 = :mzValorNivel4");
            } else {
                sql.append(" AND  d.mzValorNivel4 is null");
            }

            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel5 = :mzValorNivel5");
            } else {
                sql.append(" AND  d.mzValorNivel5 is null");
            }

            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.mzValorNivel6 = :mzValorNivel6");
            }

            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel1 = :cpTipoNivel1");
            }

            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel2 = :cpTipoNivel2");
            }

            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel3 = :cpTipoNivel3");
            }

            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel4 = :cpTipoNivel4");
            }

            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel5 = :cpTipoNivel5");
            }

            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpTipoNivel6 = :cpTipoNivel6");
            }

            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel1 = :cpValorNivel1");
            }

            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel2 = :cpValorNivel2");
            }

            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel3 = :cpValorNivel3");
            }

            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel4 = :cpValorNivel4");
            }

            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel5 = :cpValorNivel5");
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                sql.append(" AND  d.cpValorNivel6 = :cpValorNivel6");
            }

            //SOLO SI EL CENTRO POBLADO ES MULTIORIGEN BUSCA TENIENDO EN CUENTA EL BARRIO
            if (direccionTabulada.getMultiOrigen() != null && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    sql.append(" AND  d.barrio = :barrio");
                }
            } else {
                //Si la direccion es diferente de CK si debe buscar con el barrio
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        sql.append(" AND  d.barrio = :barrio");
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itTipoPlaca = :itTipoPlaca");
            } else {
                sql.append(" AND  d.itTipoPlaca is null");
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                sql.append(" AND  d.itValorPlaca = :itValorPlaca");
            } else {
                sql.append(" AND  d.itValorPlaca is null");
            }

            //QUE SEA DIRECCION PRINCIPAL CON SUBDIRECCION NULL
            sql.append(" AND d.subDireccion is null");

            sql.append(" order by d.direccionDetalladaId DESC");

            Query query = entityManager.createQuery(sql.toString(),
                    CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (direccionTabulada.getIdTipoDireccion() != null
                    && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion",
                        direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (centroPobladoId != null) {
                query.setParameter("centroPobladoId", centroPobladoId);
            }
            if (direccionTabulada.getTipoViaPrincipal() != null
                    && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {

                if (direccionTabulada.getTipoViaPrincipal().equalsIgnoreCase("CARRERA")) {
                    query.setParameter("carrera", "CARRERA");
                    query.setParameter("avenidacarrera", "AVENIDA CARRERA");
                } else {
                    query.setParameter("tipoViaPrincipal",
                            direccionTabulada.getTipoViaPrincipal().toUpperCase());
                }
            }
            if (direccionTabulada.getNumViaPrincipal() != null
                    && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal",
                        direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null
                    && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal",
                        direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null
                    && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP",
                        direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null
                    && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal",
                        direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null
                    && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal",
                        direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null
                    && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora",
                        direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null
                    && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora",
                        direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null
                    && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora",
                        direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null
                    && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null
                    && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora",
                        direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null
                    && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora",
                        direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null
                    && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion",
                        direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null
                    && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1",
                        direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null
                    && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2",
                        direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null
                    && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3",
                        direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null
                    && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4",
                        direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null
                    && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {

                if (direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || direccionTabulada.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            direccionTabulada.getMzTipoNivel5().toUpperCase());
                }
            }
            if (direccionTabulada.getMzTipoNivel6() != null
                    && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                query.setParameter("mzTipoNivel6",
                        direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null
                    && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1",
                        direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null
                    && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2",
                        direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null
                    && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3",
                        direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null
                    && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4",
                        direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null
                    && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5",
                        direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null
                    && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                query.setParameter("mzValorNivel6",
                        direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null
                    && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1",
                        direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null
                    && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2",
                        direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null
                    && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3",
                        direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null
                    && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4",
                        direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null
                    && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5",
                        direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null
                    && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6",
                        direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null
                    && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1",
                        direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null
                    && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2",
                        direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null
                    && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3",
                        direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null
                    && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4",
                        direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null
                    && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5",
                        direccionTabulada.getCpValorNivel5().toUpperCase());
            }

            if (direccionTabulada.getCpValorNivel6() != null
                    && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6",
                        direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getMultiOrigen() != null && direccionTabulada.getMultiOrigen().equalsIgnoreCase("1")
                    && direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                if (direccionTabulada.getBarrio() != null
                        && !direccionTabulada.getBarrio().trim().isEmpty()) {
                    query.setParameter("barrio",
                            direccionTabulada.getBarrio().toUpperCase());
                }
            } else {
                //Si la direccion es diferente de CK si debe buscar con el barrio
                if (!direccionTabulada.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (direccionTabulada.getBarrio() != null
                            && !direccionTabulada.getBarrio().trim().isEmpty()) {
                        query.setParameter("barrio",
                                direccionTabulada.getBarrio().toUpperCase());
                    }
                }
            }

            if (direccionTabulada.getItTipoPlaca() != null
                    && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                query.setParameter("itTipoPlaca",
                        direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null
                    && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                query.setParameter("itValorPlaca",
                        direccionTabulada.getItValorPlaca().toUpperCase());
            }

            resultList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionTabuladaMER de CmtDireccionDetalleMglDaoImpl " + e);
        }
        getEntityManager().clear();
        return resultList;
    }

    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirIdCCMM(BigDecimal dirIdCuentaMatriz) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT d FROM CmtDireccionDetalladaMgl d, CmtDireccionMgl ct "
                    + "WHERE ct.cuentaMatrizObj.cuentaMatrizId =:dirIdCuentaMatriz "
                    + "AND ct.cuentaMatrizObj.estadoRegistro = 1 "
                    + "AND ct.tdiId = 2 AND ct.direccionObj = d.direccion "
                    + "AND d.estadoRegistro = 1 AND ct.estadoRegistro = 1 ");

            Query query = entityManager.createQuery(sql.toString(), CmtDireccionDetalladaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("dirIdCuentaMatriz", dirIdCuentaMatriz);

            getEntityManager().clear();
            List<CmtDireccionDetalladaMgl> resultList = new ArrayList<>();
            resultList = query.getResultList();
            return resultList;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en findDireccionDetallaByDirIdCCMM de CmtDireccionDetalleMglDaoImpl " + e);
            return null;
        }
    }

    public ConsultaIdMerResponseDto consultaIdDireccionMER(int idRr) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("MGL_SCHEME.BUSCAR_ID_MER");
            query.registerStoredProcedureParameter("ID_RR", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("ID_MER", Integer.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("TIPO_UBICACION", String.class, ParameterMode.OUT);
            query.setParameter("ID_RR", idRr);
            query.execute();
            ConsultaIdMerResponseDto response = new ConsultaIdMerResponseDto();
            response.setIdMER((Integer) query.getOutputParameterValue("ID_MER"));
            response.setTipoUbicacion((String) query.getOutputParameterValue("TIPO_UBICACION"));
            return response;
        } catch (NoResultException ex) {
            LOGGER.error("Error en el Procedimiento almacenado BUSCAR_ID_MER" + ex);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en el Procedimiento almacenado BUSCAR_ID_MER" + e);
            return null;
        }

    }
    
    /**
     * Autor: Manuel Hernández 
     * 
     * Metodo para ejecutar PL y buscar direccionDetalladaId
     *
     * @param request
     * @return String
     */
    public String storedProcedureGetNodeIDsByAccount(getNodeIDsByAccountRequestDto request){
        StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("MGL_SCHEME.MER_BUSCAR_ID_DIRECCION_SP");
        
        procedureQuery.registerStoredProcedureParameter("E_NUM_CUENTA", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("E_TECNOLOGIA", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("E_EST_TECNO", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("S_DIRECCION_DETALLADA_ID", String.class, ParameterMode.OUT);
        
        procedureQuery.setParameter("E_NUM_CUENTA", request.getNumCuenta());
        procedureQuery.setParameter("E_TECNOLOGIA", request.getTecnologia());
        procedureQuery.setParameter("E_EST_TECNO", request.getEstadoTecnologia());
        
        procedureQuery.execute();
        
        String result = (String) procedureQuery.getOutputParameterValue("S_DIRECCION_DETALLADA_ID");
        getEntityManager().clear();
        
        return result;
    }
    
    public List<DireccionCCMMHHPPDto> buscarDireccionCCMMHHPP(BigDecimal direccionDetalladaId) {
        
        try {
            String queryTipo = "SELECT MGLDD.direccion_id, MGLDD.sub_direccion_id, CM.CUENTAMATRIZ_ID, " +
                                " CASE WHEN COUNT(CMTD.direccion_ccmm_id) >= 1 AND COUNT(CM.CUENTAMATRIZ_ID) >= 1 THEN 'CCMM' ELSE 'HHPP' END TIPO " +
                                " FROM MGL_SCHEME.MGL_DIRECCION_DETALLADA MGLDD " +
                                " LEFT JOIN MGL_SCHEME.CMT_DIRECCION CMTD ON CMTD.direccion_id = MGLDD.direccion_id AND CMTD.ESTADO_REGISTRO = '1' " +
                                " LEFT JOIN MGL_SCHEME.CMT_CUENTA_MATRIZ CM ON CMTD.CUENTAMATRIZ_ID = CM.CUENTAMATRIZ_ID AND CM.ESTADO_REGISTRO = 1 " +
                                " WHERE MGLDD.direccion_detallada_id = ? " +
                                " AND MGLDD.ESTADO_REGISTRO = '1' " +
                                " GROUP BY MGLDD.direccion_id, MGLDD.sub_direccion_id, CM.CUENTAMATRIZ_ID";
            Query query = entityManager.createNativeQuery(queryTipo);
            query.setParameter(1, direccionDetalladaId);
            List<Object[]> listaR = query.getResultList();
            return resultToDireccionCCMMHHPP(listaR);
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    private List<DireccionCCMMHHPPDto> resultToDireccionCCMMHHPP(List<Object[]> listaR) {

        List<DireccionCCMMHHPPDto> lsDireccionCCMMHHPPDto = new ArrayList<>();
        for(Object[] dir : listaR){
            DireccionCCMMHHPPDto direccionCCMMHHPPDto = DireccionCCMMHHPPDto.builder()
                    .direccionId(dir[0] != null ? dir[0].toString() : "")
                    .subDireccionId(dir[1] != null ? dir[1].toString() : "")
                    .cuentaMatrizId(dir[2] != null ? dir[2].toString() : "")
                    .tipoCCMMHHPP(dir[3] != null ? dir[3].toString() : "")
                    .build();
            lsDireccionCCMMHHPPDto.add(direccionCCMMHHPPDto);
        }
        return lsDireccionCCMMHHPPDto;
    }

}
