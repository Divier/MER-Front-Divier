/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtTipoBasicaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.UsuariosDaoImpl;
import co.com.claro.mgl.dtos.AtributoValorDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.EntityManagerUtils;
import co.com.claro.mgl.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Parzifal de León
 */
public class SolicitudDaoImpl extends GenericDaoImpl<Solicitud> {

    private static final Logger LOGGER = LogManager.getLogger(SolicitudDaoImpl.class);


    public void desbloquearDisponibilidadGestion(BigDecimal idSolicitud) {
        String query = "update TM_MAS_VT_SOLICITUDES_VT "
                //+ "set disponibilidad_gestion ='0' "
                + "set disponibilidad_gestion = ? "
                + "where IDSOLICITUD = ? ";

        Query q = entityManager.createNativeQuery(query);
        q.setParameter(1, "0");
        q.setParameter(2, idSolicitud);
        entityManager.getTransaction().begin();
        q.executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * Consulta el listado de solicitudes existentes.
     *
     * @return {@link List<Solicitud>} Lista de solicitudes
     * @throws ApplicationException Excepción en caso de error al consultar las solicitudes.
     */
    public List<Solicitud> findAll() throws ApplicationException {
        try {
            TypedQuery<Solicitud> query = entityManager.createNamedQuery("Solicitud.findAll", Solicitud.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public Solicitud findById(BigDecimal idSolicitud) throws ApplicationException {
        try {
            Solicitud result;
            Query query = entityManager.createQuery("select s from Solicitud s where s.idSolicitud = :idSolicitud AND s.estadoRegistro = 1 ");
            query.setParameter("idSolicitud", idSolicitud);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (Solicitud) query.getSingleResult();

            return result;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public Solicitud findByIdCasoTcrm(String idCasoTcrm) throws ApplicationException {
        try {
            Solicitud result;
            Query query = entityManager.createNamedQuery("Solicitud.findByCasoTcrm");
            query.setParameter("casoTcrmId", idCasoTcrm);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.
            result = (Solicitud) query.getSingleResult();

            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No fue encontrada la solicitud: " + idCasoTcrm);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public List<Solicitud> findAllSolicitudDthList() throws ApplicationException {
        try {

            Query query = entityManager.createQuery("SELECT s FROM Solicitud s WHERE "
                    + " s.estado in :estado AND s.tipo =:tipoTecnologia ORDER BY s.idSolicitud DESC ");

            List<String> estados = new ArrayList<>();
            estados.add("PENDIENTE");
            estados.add("VERIFICADO");

            query.setParameter("estado", estados);
            query.setParameter("tipoTecnologia", "DTH");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return Collections.<List<Solicitud>>singletonList(query.getResultList());
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Función que valida la disponibilidad de la solicitud para ser
     * gestionada
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public Solicitud findSolicitudDTHById(BigDecimal idSolicitud) throws ApplicationException {
        try {

            Query query = entityManager.createQuery("SELECT s FROM Solicitud s WHERE "
                    + " s.idSolicitud = :idSolicitud ");

            query.setParameter("idSolicitud", idSolicitud);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0           
            return (Solicitud) query.getSingleResult();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Función obtiene
     * gestionada
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public Solicitud findSolicitudDTHByIdPendiente(BigDecimal idSolicitud) throws ApplicationException {
        try {
            Query query = entityManager.createQuery("SELECT s FROM Solicitud s WHERE "
                    + " s.idSolicitud = :idSolicitud AND s.estado in :estado");

            query.setParameter("idSolicitud", idSolicitud);
            List<String> estados = new ArrayList<>();
            estados.add("PENDIENTE");
            estados.add("VERIFICADO");

            query.setParameter("estado", estados);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0           
            Solicitud sol = (Solicitud) query.getSingleResult();

            return sol;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public void bloquearDisponibilidadGestionDth(BigDecimal idSolicitud, String usuarioVerificador) {

        entityManager.getTransaction().begin();

        String query = "update " + Constant.MGL_DATABASE_SCHEMA + "." + "TEC_SOL_TEC_HABILITADA "
                + " set disponibilidad_gestion = ? "
                + " where SOL_TEC_HABILITADA_ID = ? ";

        Query q = entityManager.createNativeQuery(query);
        q.setParameter(1, "1");
        q.setParameter(2, idSolicitud);
        q.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void bloquearDisponibilidadGestionVerificada(BigDecimal idSolicitud) {

        entityManager.getTransaction().begin();

        String query = "update " + Constant.MGL_DATABASE_SCHEMA + "." + "TEC_SOL_TEC_HABILITADA "
                + " set disponibilidad_gestion = ? "
                + " where SOL_TEC_HABILITADA_ID = ? ";

        Query q = entityManager.createNativeQuery(query);
        q.setParameter(1, "1");
        q.setParameter(2, idSolicitud);
        q.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void desbloquearDisponibilidadGestionDth(BigDecimal idSolicitud) {

        entityManager.getTransaction().begin();

        String query = "update " + Constant.MGL_DATABASE_SCHEMA + "." + "TEC_SOL_TEC_HABILITADA "
                + "set disponibilidad_gestion = ?, USUARIO_VERIFICADOR = ? "
                + "where SOL_TEC_HABILITADA_ID = ? ";

        Query q = entityManager.createNativeQuery(query);
        q.setParameter(1, "0");
        q.setParameter(2, "");
        q.setParameter(3, idSolicitud);

        q.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void desbloquearDisponibilidadGestionVerificada(BigDecimal idSolicitud) {

        entityManager.getTransaction().begin();

        String query = "update " + Constant.MGL_DATABASE_SCHEMA + "." + "TEC_SOL_TEC_HABILITADA "
                + "set disponibilidad_gestion = ? "
                + "where SOL_TEC_HABILITADA_ID = ? ";

        Query q = entityManager.createNativeQuery(query);
        q.setParameter(1, "0");
        q.setParameter(2, idSolicitud);

        q.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public List<Solicitud> findPendientesParaGestionPaginacion(int firstResult,
                                                               int maxResults) throws ApplicationException {
        try {
            String queryStr = "SELECT s FROM Solicitud s WHERE "
                    + " s.estado in :estado AND s.tipo =:tipo ORDER BY s.idSolicitud DESC ";

            Query query = entityManager.createQuery(queryStr);

            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            List<String> estados = new ArrayList<>();
            estados.add("PENDIENTE");
            estados.add("VERIFICADO");

            query.setParameter("estado", estados);
            query.setParameter("tipo", "DTH");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (List<Solicitud>) query.getResultList();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public List<Solicitud> findAllSolicitudByRolList(List<String> tipoTecnologiaList) throws ApplicationException {
        try {

            StringBuilder querySql = new StringBuilder();

            querySql.append("SELECT s FROM Solicitud s WHERE  ");
            int cont = 0;
            querySql.append("( ");
            for (String tipoTecnologia : tipoTecnologiaList) {
                cont++;

                querySql.append(" s.tipo =:tipoTecnologia").append(cont);

                if (cont < tipoTecnologiaList.size()) {
                    querySql.append(" OR ");
                } else {
                    querySql.append(" ) AND ");
                }
            }
            querySql.append(" s.estado in :estado");
            querySql.append(" ORDER BY s.fechaIngreso DESC ");
            Query query = entityManager.createQuery(querySql.toString());

            List<String> estados = new ArrayList<>();
            estados.add("PENDIENTE");
            estados.add("VERIFICADO");

            query.setParameter("estado", estados);
            int cont2 = 0;
            for (String tipoTecnologia : tipoTecnologiaList) {
                cont2++;
                query.setParameter("tipoTecnologia" + cont2, tipoTecnologia);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            List<Solicitud> result = query.getResultList();

            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public List<Solicitud> findPendientesParaGestionPaginacionByRol(int firstResult,
            int maxResults, List<String> tipoSolicitudHhppList, String tipoSolicitudFiltro,
            boolean ordenMayorMenor, BigDecimal divisional) throws ApplicationException {
        try {
            StringBuilder querySql = new StringBuilder();

            if ((tipoSolicitudFiltro != null && !tipoSolicitudFiltro.isEmpty())) {
                querySql.append("Select s From Solicitud s Where "
                        + "s.cambioDir =:tipoSolicitudFiltro AND s.estadoRegistro = 1 AND ");
            } else {
                querySql.append("SELECT s FROM Solicitud s WHERE s.estadoRegistro = 1 AND ");
            }

            int cont = 0;
            querySql.append("( ");
            for (String tipoSolicitud : tipoSolicitudHhppList) {
                cont++;
                querySql.append(" s.cambioDir =:tipoSolicitud");
                querySql.append(cont);
                if (cont < tipoSolicitudHhppList.size()) {
                    querySql.append(" OR ");
                } else {
                    querySql.append(" ) AND ");
                }
            }
            if (divisional != null) {
                querySql.append(" s.idBasicaDivi.basicaId =:idBasicaDivi  AND ");
            }
            List<String> estados = new ArrayList<>();
            estados.add("PENDIENTE");
            estados.add("VERIFICADO");

            querySql.append(" s.estado in :estado");
            if (!ordenMayorMenor) {
                querySql.append(" ORDER BY s.idSolicitud DESC ");
            } else {
                querySql.append(" ORDER BY s.idSolicitud ASC ");
            }
            Query query = entityManager.createQuery(querySql.toString());
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            query.setParameter("estado", estados);
            int cont2 = 0;
            for (String tipoSolicitud : tipoSolicitudHhppList) {
                cont2++;
                query.setParameter("tipoSolicitud" + cont2, tipoSolicitud);
            }
            if (tipoSolicitudFiltro != null && !tipoSolicitudFiltro.isEmpty()) {
                query.setParameter("tipoSolicitudFiltro", tipoSolicitudFiltro);
            }

            if (divisional != null) {
                query.setParameter("idBasicaDivi", divisional);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            List<Solicitud> result = query.getResultList();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public List<Solicitud> findSolicitudByIdRol(BigDecimal idSolicitud,
                                                List<String> tipoSolicitudByRolList) throws ApplicationException {
        try {
            StringBuilder querySql = new StringBuilder();

            querySql.append("SELECT s FROM Solicitud s WHERE s.estadoRegistro = 1 AND s.idSolicitud = "
                    + ":idSolicitud AND  ");

            int cont = 0;
            querySql.append("( ");
            for (String tipoSolicitudRol : tipoSolicitudByRolList) {
                cont++;
                querySql.append(" s.cambioDir =:tipoSolicitudRol");
                querySql.append(cont);
                if (cont < tipoSolicitudByRolList.size()) {
                    querySql.append(" OR ");
                } else {
                    querySql.append(" ) AND ");
                }
            }

            List<String> estados = new ArrayList<>();
            estados.add("PENDIENTE");
            estados.add("VERIFICADO");
            estados.add("FINALIZADO");
            estados.add("RECHAZADO");

            querySql.append(" s.estado in :estado");
            querySql.append(" ORDER BY s.idSolicitud DESC ");
            Query query = entityManager.createQuery(querySql.toString());
            query.setParameter("estado", estados);
            query.setParameter("idSolicitud", idSolicitud);

            int cont2 = 0;
            for (String tipoSolicitudRol : tipoSolicitudByRolList) {
                cont2++;
                query.setParameter("tipoSolicitudRol" + cont2, tipoSolicitudRol);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            List<Solicitud> result = query.getResultList();

            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }


    public int countAllSolicitudByRolList(List<String> tipoSolicitudHhppList, String tipoSolicitudFiltro)
            throws ApplicationException {
        try {

            StringBuilder querySql = new StringBuilder();

            if (tipoSolicitudFiltro != null && !tipoSolicitudFiltro.isEmpty()) {
                querySql.append("Select Count (1) From Solicitud s Where "
                        + "s.cambioDir =:tipoSolicitudFiltro AND ");
            } else {
                querySql.append("SELECT Count (1) FROM Solicitud s WHERE  ");
            }


            int cont = 0;
            querySql.append("( ");
            for (String tipoSolicitudHhpp : tipoSolicitudHhppList) {
                cont++;
                querySql.append(" s.cambioDir =:tipoSolicitudHhpp").append(cont);

                if (cont < tipoSolicitudHhppList.size()) {
                    querySql.append(" OR ");
                } else {
                    querySql.append(" ) AND ");
                }
            }
            querySql.append(" s.estado in :estado");
            querySql.append(" ORDER BY s.fechaIngreso DESC ");
            Query query = entityManager.createQuery(querySql.toString());

            List<String> estados = new ArrayList<>();
            estados.add("PENDIENTE");
            estados.add("VERIFICADO");

            query.setParameter("estado", estados);
            int cont2 = 0;
            for (String tipoSolicitudHhpp : tipoSolicitudHhppList) {
                cont2++;
                query.setParameter("tipoSolicitudHhpp" + cont2, tipoSolicitudHhpp);
            }
            if (tipoSolicitudFiltro != null && !tipoSolicitudFiltro.isEmpty()) {
                query.setParameter("tipoSolicitudFiltro", tipoSolicitudFiltro);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * valbuenayf metodo para buscar si existen solicitudes pendientes o
     * verificados
     *
     * @param direccionDetalladaId
     * @return
     */
    public List<Solicitud> solicitudesPendientesVerificadas(BigDecimal direccionDetalladaId) {
        List<Solicitud> lista = null;
        try {
            Query query = entityManager.createNamedQuery("Solicitud.findBySolicitudes");
            query.setParameter("direccionDetalladaId", direccionDetalladaId);
            lista = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en solicitudesPendientesVerificadas. ".concat(e.getMessage()), e);
        }
        return lista;
    }

    /**
     * Buscar una solicitud en la base de datos
     * <p>
     * Busca la solicitud según los datos correspondientes
     *
     * @param item atributo a buscar
     * @return el valor de solicitud encontrado.
     * @author becerraarmr
     */
    public Solicitud findSolicitud(AtributoValorDto item) {
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Solicitud> cq = cb.createQuery(Solicitud.class);
            Root<Solicitud> c = cq.from(Solicitud.class);

            cq.select(c).where(cb.equal(c.get(item.getNombreAtributo()),
                    item.getValorAtributo()));
            Query q = entityManager.createQuery(cq);
            List<Solicitud> list = q.getResultList();
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
            LOGGER.error("Error en findSolicitud. " + e.getMessage());
            throw new Error(e.getMessage());
        }
        return null;
    }


    public List<Solicitud> solictudesHhppEnCurso(BigDecimal idDireccionDetallada,
                                                 BigDecimal idCentroPoblado, BigDecimal tecnologiaBasicaId,
                                                 String tipoAccionSolicitud)
            throws ApplicationException {
        try {

            StringBuilder querySql = new StringBuilder();
            querySql.append("Select s From Solicitud s Where s.estadoRegistro = 1 AND "
                    + "s.cambioDir = :tipoAccionSolicitud AND "
                    + "s.direccionDetallada.direccionDetalladaId = :idDireccionDetallada AND "
                    + "s.centroPobladoId = :idCentroPoblado AND "
                    + "s.tecnologiaId.basicaId = :tecnologiaBasicaId AND "
                    + "s.estado = :estado ");

            Query query = entityManager.createQuery(querySql.toString());
            query.setParameter("tipoAccionSolicitud", tipoAccionSolicitud);
            query.setParameter("idDireccionDetallada", idDireccionDetallada);
            query.setParameter("idCentroPoblado", idCentroPoblado);
            query.setParameter("tecnologiaBasicaId", tecnologiaBasicaId);
            query.setParameter("estado", "PENDIENTE");

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            List<Solicitud> result = query.getResultList();

            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }


    public List<Solicitud> solictudesCambioEstratoHhppEnCurso(BigDecimal idDireccionDetallada, String tipoAccionSolicitud)
            throws ApplicationException {
        try {
            StringBuilder querySql = new StringBuilder();
            querySql.append("Select s From Solicitud s Where s.estadoRegistro = 1 and "
                    + "s.cambioDir = :tipoAccionSolicitud AND "
                    + "s.direccionDetallada.direccionDetalladaId = :idDireccionDetallada AND "
                    + " (s.estado = :estado OR "
                    + "s.estado = :estadoVerificada )");

            Query query = entityManager.createQuery(querySql.toString());
            query.setParameter("tipoAccionSolicitud", tipoAccionSolicitud);
            query.setParameter("idDireccionDetallada", idDireccionDetallada);
            query.setParameter("estado", "PENDIENTE");
            query.setParameter("estadoVerificada", "VERIFICADO");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            List<Solicitud> result = query.getResultList();

            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }


    /**
     * Buscar en la base de datos el reporte general
     * <p>
     * Busca en la base de datos los registros que cumplen las condiciones
     *
     * @param fechaInicial Fecha Inicial
     * @param fechaFinal  Fecha Final
     * @param tipoSol     Tipo solicitud
     * @param estado      estado
     * @param range
     * @return un listado con los registros encontrados de la base de datos.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Victor Bocanegra
     */
    public List<Object[]> buscarReporteGeneralSolicitudesHhpp(Date fechaInicial,
                                                              Date fechaFinal, String tipoSol, String estado, int[] range)
            throws ApplicationException {

        List<Object[]> list = new ArrayList<>();

        try {

            boolean control = true;

            String queryStr = "SELECT count(s.idSolicitud), func('trunc',s.fechaIngreso), "
                    + "s.estado, s.tipo, s.regional "
                    + "FROM Solicitud s WHERE s.estadoRegistro = 1 ";

            if (tipoSol != null && !tipoSol.equalsIgnoreCase("")) {
                queryStr += " AND s.cambioDir = :cambioDir";
            }

            if (estado != null && !estado.equalsIgnoreCase("")) {
                queryStr += " AND s.estado = :estado";
            }

            if (fechaInicial != null && fechaFinal != null) {
                if (fechaInicial.before(fechaFinal)) {
                    queryStr += " AND s.fechaIngreso BETWEEN :fechaInicio and  :fechaFin";
                } else {
                    queryStr += " AND  func('trunc', s.fechaIngreso) = func('to_date', :fechaInicio)";
                    control = false;
                }

            }

            queryStr += " group by  s.estado, s.tipo, s.regional, func('trunc',s.fechaIngreso)";

            Query query = entityManager.createQuery(queryStr);

            if (tipoSol != null && !tipoSol.equalsIgnoreCase("")) {
                query.setParameter("cambioDir", tipoSol);
            }

            if (estado != null && !estado.equalsIgnoreCase("")) {
                query.setParameter("estado", estado);
            }
            if (fechaInicial != null) {
                java.sql.Date fInicio = new java.sql.Date(fechaInicial.getTime());
                query.setParameter("fechaInicio", fInicio);
            }
            if (control) {
                if (fechaFinal != null) {
                    java.sql.Date fFin = new java.sql.Date(fechaFinal.getTime());
                    query.setParameter("fechaFin", fFin);
                }
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (range != null) {
                query.setMaxResults(range[1] - range[0] + 1);
                query.setFirstResult(range[0]);
            }

            list = query.getResultList();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return list;
    }

    /**
     * Buscar en la base de datos el reporte detallado
     * <p>
     * Busca en la base de datos los registros que cumplen las condiciones
     *
     * @param fechaInicial
     * @param fechaFinal   Fecha Final
     * @param tipoSol      Tipo solicitud
     * @param estado       estado
     * @param minReg       minimo consulta
     * @param maxReg       maximo consulta
     * @return un listado con los registros encontrados de la base de datos.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Victor Bocanegra
     */
    public List<Object[]> buscarReporteDetalladoSolicitudesHhpp(Date fechaInicial,
            Date fechaFinal, String tipoSol, String estado, int minReg, int maxReg)
            throws ApplicationException {

        List<Object[]> list = new ArrayList<>();
        List<Solicitud> listResultado;
        String tipoSolName = retornaTipoSol(tipoSol);
        NodoMglDaoImpl nodoMglDaoImpl = new NodoMglDaoImpl();
        TipoHhppConexionMglDaoImpl tipoHhppConexionMglDaoImpl = new TipoHhppConexionMglDaoImpl();
        CmtTipoBasicaMgl tipoBasicaMgl = null;
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        CmtBasicaMgl estadoHHppBas;
        CmtBasicaMglDaoImpl basicaMglDaoImpl1 = new CmtBasicaMglDaoImpl();
        GeograficoPoliticoDaoImpl geograficoPoliticoDaoImpl = new GeograficoPoliticoDaoImpl();
        try {

            boolean control = true;

            String queryStr = "SELECT s "
                    + "FROM Solicitud s WHERE s.estadoRegistro = 1 ";

            if (tipoSol != null && !tipoSol.isEmpty()) {
                queryStr += " AND s.cambioDir = :cambioDir";
            }
            if (estado != null && !estado.isEmpty()) {
                queryStr += " AND s.estado = :estado";
            } else {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(tipoSol) && tipoSol.equals(Constant.RR_DIR_ESCALAMIENTO_HHPP)) {
                    queryStr += " AND s.estado IN ('PENDIENTE', 'FINALIZADO')";
                }
            }

            if (fechaInicial != null && fechaFinal != null) {
                if (fechaInicial.before(fechaFinal)) {
                    queryStr += " AND func('trunc', s.fechaIngreso) BETWEEN :fechaInicio and  :fechaFin";
                } else {
                    queryStr += " AND  func('trunc', s.fechaIngreso) = :fechaInicio";
                    control = false;
                }
            }


            Query query = entityManager.createQuery(queryStr);

            if (tipoSol != null && !tipoSol.isEmpty()) {
                query.setParameter("cambioDir", tipoSol);
            }
            if (estado != null && !estado.isEmpty()) {
                query.setParameter("estado", estado);
            }
            if (fechaInicial != null) {
                java.sql.Date fInicio = new java.sql.Date(fechaInicial.getTime());
                query.setParameter("fechaInicio", fInicio);
            }

            if (control && fechaFinal != null) {
                query.setParameter("fechaFin", fechaFinal);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setFirstResult(minReg);
            query.setMaxResults(maxReg);

            listResultado = query.getResultList();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");


            HhppMglManager hhppMglManager
                    = new HhppMglManager();


            final UsuariosDaoImpl usuariosDao = new UsuariosDaoImpl();

            for (Solicitud solicitudHhpp : listResultado) {
                String usuarioCreacionHhpp = "";

                if (solicitudHhpp.getIdHhppGestionado() != null) {
                    solicitudHhpp.setHhppMgl(solicitudHhpp.getIdHhppGestionado());
                }

                //JDHT
                if (tipoSolName.equalsIgnoreCase("CREACION HHPP")) {

                    if (solicitudHhpp.getIdHhppGestionado() != null) {
                        solicitudHhpp.setHhppMgl(solicitudHhpp.getIdHhppGestionado());
                    } else {
                        //se resetea el campo para guardar el nuevo valor
                        solicitudHhpp.setHhppMgl(null);
                        //si la solicitud tiene dirección detallada
                        if (solicitudHhpp.getDireccionDetallada() != null) {
                            BigDecimal subDirId = null;
                            if (solicitudHhpp.getDireccionDetallada().getSubDireccion() != null) {
                                subDirId = solicitudHhpp.getDireccionDetallada().getSubDireccion().getSdiId();
                            }
                            //se obtienen los hhpp por direccion detallada asociados
                            List<HhppMgl> hhppList = hhppMglManager.findHhppByDirIdSubDirId(solicitudHhpp.getDireccionDetallada().getDireccion().getDirId(), subDirId);
                            //se relaciona con la solicitud el que tenga la misma tecnologia
                            if (hhppList != null && !hhppList.isEmpty()) {
                                solicitudHhpp.setHhppMgl(hhppList.get(0));
                            }
                        }
                    }
                }
                String userLogin = null;
                String solicitante = null;
                String cuentaMatriz = solicitudHhpp.getCuentaMatriz() != null ? solicitudHhpp.getCuentaMatriz() : "";
                String cuentaSuscriptor = solicitudHhpp.getCuentaSuscriptor() != null ? solicitudHhpp.getCuentaSuscriptor() : "";
                String calle = solicitudHhpp.getStreetName() != null ? solicitudHhpp.getStreetName() : "";
                String placa = solicitudHhpp.getHouseNumber() != null ? solicitudHhpp.getHouseNumber() : "";
                String tipoViv = solicitudHhpp.getTipoVivienda() != null ? solicitudHhpp.getTipoVivienda() : "";
                String complemento = solicitudHhpp.getComplemento() != null ? solicitudHhpp.getComplemento() : "";
                String contacto = solicitudHhpp.getContacto() != null ? solicitudHhpp.getContacto() : "";
                String regional = solicitudHhpp.getIdBasicaDivi() != null ? solicitudHhpp.getIdBasicaDivi().getNombreBasica().trim().substring(3, solicitudHhpp.getIdBasicaDivi().getNombreBasica().trim().length()) : "";
                String ciudad = solicitudHhpp.getCiudad() != null ? solicitudHhpp.getCiudad() : "";
                String nodo = solicitudHhpp.getNodo() != null ? solicitudHhpp.getNodo() : "";
                String area = solicitudHhpp.getTipoSol() != null ? solicitudHhpp.getTipoSol() : tipoSolName;
                String fechaIngreso = solicitudHhpp.getFechaIngreso() != null ? formato.format(solicitudHhpp.getFechaIngreso()) : "";
                String fechaMod = solicitudHhpp.getFechaModificacion() != null ? formato.format(solicitudHhpp.getFechaModificacion()) : "";
                String fechaCan = solicitudHhpp.getFechaCancelacion() != null ? formato.format(solicitudHhpp.getFechaCancelacion()) : "";
                String tiempo = "";
                if (solicitudHhpp.getFechaIngreso() != null && solicitudHhpp.getFechaCancelacion() != null) {
                    tiempo = solicitudHhpp.getTiempo(solicitudHhpp.getFechaIngreso(), solicitudHhpp.getFechaCancelacion());
                } else {
                    if (solicitudHhpp.getFechaIngreso() != null && solicitudHhpp.getFechaModificacion() != null) {
                        tiempo = solicitudHhpp.getTiempo(solicitudHhpp.getFechaIngreso(), solicitudHhpp.getFechaModificacion());
                    }
                }
                String motivo = solicitudHhpp.getMotivo() != null ? StringUtils.caracteresEspeciales(solicitudHhpp.getMotivo()) : "";
                String estadoSol = solicitudHhpp.getEstado() != null ? solicitudHhpp.getEstado() : "";
                String tipoSolicitud = !tipoSolName.equals("") ? tipoSolName : "";
                String usuario = solicitudHhpp.getUsuarioGestionador() != null ? solicitudHhpp.getUsuarioGestionador() : "";
                String usuarioVer = solicitudHhpp.getUsuarioVerificador() != null ? solicitudHhpp.getUsuarioVerificador() : "";
                String rptGestion = solicitudHhpp.getRptGestion() != null ? solicitudHhpp.getRptGestion() : "";
                String respuesta = solicitudHhpp.getRespuesta() != null ? StringUtils.caracteresEspeciales(solicitudHhpp.getRespuesta()) : "";
                String corregirH = solicitudHhpp.getCorregirHHPP() != null ? solicitudHhpp.getCorregirHHPP() : "";
                String cambioN = solicitudHhpp.getCambioNodo() != null ? solicitudHhpp.getCambioNodo() : "";
                String nombreNueEdi = solicitudHhpp.getNombreNuevoEdificio() != null ? solicitudHhpp.getNombreNuevoEdificio() : "";
                String nuevoPro = solicitudHhpp.getNuevoProducto() != null ? solicitudHhpp.getNuevoProducto() : "";
                String estratoA = solicitudHhpp.getEstratoAntiguo() != null ? solicitudHhpp.getEstratoAntiguo() : "";
                String estratoN = solicitudHhpp.getEstratoNuevo() != null ? solicitudHhpp.getEstratoNuevo() : "";
                String hhppId = solicitudHhpp.getHhppMgl() != null ? solicitudHhpp.getHhppMgl().getHhpId().toString() : "";
                String nodoNombre = "";
                if (!nodo.isEmpty()) {
                    NodoMgl nodoMgl = nodoMglDaoImpl.findByCodigo(nodo);
                    if (nodoMgl != null) {
                        nodoNombre = nodoMgl.getNodNombre();
                    }
                }

                HhppMgl hhppMglSol;
                String tipoUnidad = "";
                String fomatoIgacSubDir = "";
                String tipoConexion = null;
                String tipoRed = "";
                String fechaCreacionHhpp = "";
                String fechaModHhpp = "";
                String usuarioModHhpp = "";
                String estadoHhpp = "";
                String hhppIdrr = "";
                String hhppCalle = "";
                String hhppPlaca = "";
                String hhppApart = "";
                String hhppCom = "";
                String hhppDiv = "";
                String hhppEstUnit = "";
                String hhppVendedor = "";
                String hhppCodpostal = "";
                String hhppEdificio = "";
                String hhppTipoAco = "";
                String hhppUltUbi = "";
                String hhppHeadEnd = "";
                String hhppTipo = "";
                String hhppTipoUni = "";
                String hhppTipoCblAco = "";
                String hhppfechaAud = "";
                String notasSol;


                if (solicitudHhpp.getHhppMgl() != null) {
                    hhppMglSol = solicitudHhpp.getHhppMgl();
                    tipoUnidad = hhppMglSol.getHhpTipoUnidad() != null ? hhppMglSol.getHhpTipoUnidad() : "";

                    if (hhppMglSol.getSubDireccionObj() != null && hhppMglSol.getSubDireccionObj().getSdiFormatoIgac() != null) {
                        fomatoIgacSubDir = hhppMglSol.getSubDireccionObj().getSdiFormatoIgac();
                    } else {
                        if (hhppMglSol.getDireccionObj() != null && hhppMglSol.getDireccionObj().getDirFormatoIgac() != null) {
                            fomatoIgacSubDir = hhppMglSol.getDireccionObj().getDirFormatoIgac();
                        } else {
                            fomatoIgacSubDir = "";
                        }
                    }

                    if (hhppMglSol.getThcId() != null) {
                        TipoHhppConexionMgl tipoHhppConexionMgl = tipoHhppConexionMglDaoImpl.find(hhppMglSol.getThcId());
                        if (tipoHhppConexionMgl != null) {
                            tipoConexion = tipoHhppConexionMgl.getThcNombre();
                        }
                    }

                    if (hhppMglSol.getNodId() != null && hhppMglSol.getNodId().getNodTipo() != null) {
                        tipoRed = hhppMglSol.getNodId().getNodTipo().getNombreBasica();
                    }

                    fechaCreacionHhpp = hhppMglSol.getFechaCreacion() != null ? formato.format(hhppMglSol.getFechaCreacion()) : "";
                    usuarioCreacionHhpp = hhppMglSol.getUsuarioCreacion() != null ? hhppMglSol.getUsuarioCreacion() : "";
                    fechaModHhpp = hhppMglSol.getFechaEdicion() != null ? formato.format(hhppMglSol.getFechaEdicion()) : "";
                    usuarioModHhpp = hhppMglSol.getUsuarioEdicion() != null ? hhppMglSol.getUsuarioEdicion() : "";

                    if (hhppMglSol.getEhhId() != null && hhppMglSol.getEhhId().getEhhID() != null) {
                        tipoBasicaMgl = cmtTipoBasicaMglDaoImpl.findByCodigoInternoApp(
                                co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADOS_HHPP);
                    }

                    if (tipoBasicaMgl != null) {
                        estadoHHppBas = basicaMglDaoImpl1.findByTipoBasicaAndCodigo(tipoBasicaMgl.getTipoBasicaId(), hhppMglSol.getEhhId().getEhhID());

                        if (estadoHHppBas != null) {
                            estadoHhpp = estadoHHppBas.getDescripcion();
                            hhppEstUnit = estadoHHppBas.getNombreBasica();
                        }
                    }
                    hhppIdrr = hhppMglSol.getHhpIdrR() != null ? hhppMglSol.getHhpIdrR() : "";
                    hhppCalle = hhppMglSol.getHhpCalle() != null ? hhppMglSol.getHhpCalle() : "";
                    hhppPlaca = hhppMglSol.getHhpPlaca() != null ? hhppMglSol.getHhpPlaca() : "";
                    hhppApart = hhppMglSol.getHhpApart() != null ? hhppMglSol.getHhpApart() : "";
                    hhppCom = hhppMglSol.getHhpComunidad() != null ? hhppMglSol.getHhpComunidad() : "";
                    hhppDiv = hhppMglSol.getHhpDivision() != null ? hhppMglSol.getHhpDivision() : "";
                    hhppVendedor = hhppMglSol.getHhpVendedor() != null ? hhppMglSol.getHhpVendedor() : "";
                    hhppCodpostal = hhppMglSol.getHhpCodigoPostal() != null ? hhppMglSol.getHhpCodigoPostal() : "";

                    if (hhppMglSol.getHhppSubEdificioObj() != null) {

                        hhppEdificio = hhppMglSol.getHhppSubEdificioObj() != null
                                ? hhppMglSol.getHhppSubEdificioObj().getNombreSubedificio() : "";

                    }
                    hhppTipoAco = hhppMglSol.getHhpTipoAcomet() != null ? hhppMglSol.getHhpTipoAcomet() : "";
                    hhppUltUbi = hhppMglSol.getHhpUltUbicacion() != null ? hhppMglSol.getHhpUltUbicacion() : "";
                    hhppHeadEnd = hhppMglSol.getHhpHeadEnd() != null ? hhppMglSol.getHhpHeadEnd() : "";
                    hhppTipo = hhppMglSol.getHhpTipo() != null ? hhppMglSol.getHhpTipo() : "";
                    hhppTipoUni = hhppMglSol.getHhpTipoUnidad() != null ? hhppMglSol.getHhpTipoUnidad() : "";
                    hhppTipoCblAco = hhppMglSol.getHhpTipoCblAcometida() != null ? hhppMglSol.getHhpTipoCblAcometida() : "";
                    hhppfechaAud = hhppMglSol.getHhpFechaAudit() != null ? formato.format(hhppMglSol.getHhpFechaAudit()) : "";

                }
                notasSol = solicitudHhpp.getRespuesta() != null ? StringUtils.caracteresEspeciales(solicitudHhpp.getRespuesta()) : "";


                boolean isValidacion = solicitudHhpp.getCambioDir() != null
                        && (solicitudHhpp.getCambioDir().equals(""))
                        && solicitudHhpp.getCambioDir().equals(Constant.VALIDACION_COBERTURA_12);
                if (isValidacion) {
                    tipoRed = solicitudHhpp.getTecnologiaId().getNombreBasica();
                }
                String direccion = "";
                String departamento = "";
                String ciudadGeo = "";
                String centroPoblado = "";
                String confiabilidadDireccion = "";
                String confiabilidadComplemento = "";
                String estadoDir = "";
                String barrio = "";
                String direccionNoEst = "";

                if (solicitudHhpp.getDireccionDetallada() != null) {
                    direccion = solicitudHhpp.getDireccionDetallada().getDireccionTexto() != null
                            ? solicitudHhpp.getDireccionDetallada().getDireccionTexto() : "";
                }


                if (solicitudHhpp.getCentroPobladoId() != null) {
                    GeograficoPoliticoMgl centro = geograficoPoliticoDaoImpl.find(solicitudHhpp.getCentroPobladoId());
                    if (centro != null) {
                        centroPoblado = centro.getGpoNombre() != null ? org.apache.commons.lang3.StringUtils.stripAccents(centro.getGpoNombre()) : "";
                        if (centro.getGeoGpoId() != null) {
                            GeograficoPoliticoMgl ciudadCon =
                                    geograficoPoliticoDaoImpl.find(centro.getGeoGpoId());
                            if (ciudadCon != null) {
                                ciudadGeo = ciudadCon.getGpoNombre() != null ? org.apache.commons.lang3.StringUtils.stripAccents(ciudadCon.getGpoNombre()) : "";
                                if (ciudadCon.getGeoGpoId() != null) {
                                    GeograficoPoliticoMgl depCon =
                                            geograficoPoliticoDaoImpl.find(ciudadCon.getGeoGpoId());
                                    if (depCon != null) {
                                        departamento = depCon.getGpoNombre() != null ? depCon.getGpoNombre() : "";
                                    }
                                }
                            }
                        }
                    }
                }

                //Se agrega Confiabilidad Dirección, Confiabilidad Placa y Estado Dir
                if (solicitudHhpp.getDireccionDetallada() != null) {
                    confiabilidadDireccion = solicitudHhpp.getDireccionDetallada().getDireccion().getDirConfiabilidad() != null
                            ? solicitudHhpp.getDireccionDetallada().getDireccion().getDirConfiabilidad().toString() + "%" : "";

                    //Subdirección
                    if (solicitudHhpp.getDireccionDetallada().getSubDireccion() != null) {
                        confiabilidadComplemento = solicitudHhpp.getDireccionDetallada().getSubDireccion().getSdirConfiabilidad() != null
                                ? solicitudHhpp.getDireccionDetallada().getSubDireccion().getSdirConfiabilidad().toString() + "%" : "";
                    }

                    //Estado_Dir
                    estadoDir = solicitudHhpp.getDireccionDetallada().getEstadoDirGeo() != null
                            ? solicitudHhpp.getDireccionDetallada().getEstadoDirGeo() : "";

                    barrio = solicitudHhpp.getDireccionDetallada().getBarrio();
                    direccionNoEst = solicitudHhpp.getDireccionDetallada().getDireccion() != null
                            && solicitudHhpp.getDireccionDetallada().getDireccion().getDirNostandar() != null ? solicitudHhpp.getDireccionDetallada().getDireccion().getDirNostandar() : "";

                }
                String telContacto = null;

                final Usuarios usuarioSol = usuariosDao.findUsuarioById(solicitudHhpp.getIdSolicitante());
                if (usuarioSol != null) {
                    userLogin = usuarioSol.getUsuario();
                    solicitante = usuarioSol.getNombre().toUpperCase();
                }
                final Usuarios usuarioGestion = usuariosDao.findUsuarioById(solicitudHhpp.getIdUsuarioGestion());
                if (usuarioGestion != null) {
                    usuarioCreacionHhpp = usuarioGestion.getUsuario();
                }
                telContacto = solicitudHhpp.getTelSolicitante();


                final String tipo = org.apache.commons.lang3.StringUtils.isNotBlank(solicitudHhpp.getTipoHHPP()) ? solicitudHhpp.getTipoHHPP() : "";
                final String ccmm = org.apache.commons.lang3.StringUtils.isNotBlank(solicitudHhpp.getCcmm()) ? solicitudHhpp.getCcmm() : "";
                final String solOT = org.apache.commons.lang3.StringUtils.isNotBlank(solicitudHhpp.getSolicitudOT()) ? solicitudHhpp.getSolicitudOT() : "";
                final String tipoGestion = org.apache.commons.lang3.StringUtils.isNotBlank(solicitudHhpp.getTipoGestion()) ? solicitudHhpp.getTipoGestion() : "";

                Object[] ob1=new Object[]{};
                if(tipoSolName.equals("VALIDACION DE COBERTURA")){
                    String tipoDeSolicitud = solicitudHhpp.getTipoDeSolicitud() != null ? solicitudHhpp.getTipoDeSolicitud(): "";
                    String tipoDeServicio = solicitudHhpp.getTipoDeServicio() != null ? solicitudHhpp.getTipoDeServicio() :"";
                    String tipoDeValidacion = solicitudHhpp.getTipoDeValidacion() != null ? solicitudHhpp.getTipoDeValidacion() :"";
                    String bw = solicitudHhpp.getBw() != null ? solicitudHhpp.getBw() :"";
                    String tipoDeSitio = solicitudHhpp.getTipoDeSitio() != null ? solicitudHhpp.getTipoDeSitio(): "";
                    
                    ob1 = new Object[]{userLogin, solicitudHhpp.getIdSolicitud(),
                        cuentaMatriz, cuentaSuscriptor,
                        calle, placa, tipoViv,
                        complemento, solicitante, contacto,
                        telContacto, regional, ciudad,
                        nodo, tipoSolicitud, fechaIngreso, fechaMod, fechaCan,
                        tiempo, motivo, estadoSol, area, usuario, usuarioVer, rptGestion,
                        respuesta, corregirH, cambioN, nombreNueEdi, nuevoPro,
                        estratoA, estratoN, barrio, hhppId,
                        direccionNoEst, nodoNombre, tipoUnidad, fomatoIgacSubDir, tipoConexion, tipoRed,
                        fechaCreacionHhpp, usuarioCreacionHhpp, fechaModHhpp, usuarioModHhpp,
                        estadoHhpp, hhppIdrr, hhppCalle, hhppPlaca, hhppApart, hhppCom,
                        hhppDiv, hhppEstUnit, hhppVendedor, hhppCodpostal, hhppEdificio,
                        hhppTipoAco, hhppUltUbi, hhppHeadEnd, hhppTipo, hhppTipoUni, hhppTipoCblAco,
                        hhppfechaAud, confiabilidadDireccion, confiabilidadComplemento, estadoDir,
                        notasSol, direccion, departamento, ciudadGeo, centroPoblado, solicitudHhpp.getTipoDoc(),
                        solicitudHhpp.getNumDocCliente(), tipo, ccmm, solOT,
                        tipoGestion,tipoDeSolicitud,tipoDeServicio,tipoDeValidacion,bw,tipoDeSitio};
                }else{
                
                    ob1 = new Object[]{userLogin, solicitudHhpp.getIdSolicitud(),
                        cuentaMatriz, cuentaSuscriptor,
                        calle, placa, tipoViv,
                        complemento, solicitante, contacto,
                        telContacto, regional, ciudad,
                        nodo, tipoSolicitud, fechaIngreso, fechaMod, fechaCan,
                        tiempo, motivo, estadoSol, area, usuario, usuarioVer, rptGestion,
                        respuesta, corregirH, cambioN, nombreNueEdi, nuevoPro,
                        estratoA, estratoN, barrio, hhppId,
                        direccionNoEst, nodoNombre, tipoUnidad, fomatoIgacSubDir, tipoConexion, tipoRed,
                        fechaCreacionHhpp, usuarioCreacionHhpp, fechaModHhpp, usuarioModHhpp,
                        estadoHhpp, hhppIdrr, hhppCalle, hhppPlaca, hhppApart, hhppCom,
                        hhppDiv, hhppEstUnit, hhppVendedor, hhppCodpostal, hhppEdificio,
                        hhppTipoAco, hhppUltUbi, hhppHeadEnd, hhppTipo, hhppTipoUni, hhppTipoCblAco,
                        hhppfechaAud, confiabilidadDireccion, confiabilidadComplemento, estadoDir,
                        notasSol, direccion, departamento, ciudadGeo, centroPoblado, solicitudHhpp.getTipoDoc(),
                        solicitudHhpp.getNumDocCliente(), tipo, ccmm, solOT,
                        tipoGestion};
                }
                list.add(ob1);

            }

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        getEntityManager().clear();
        return list;
    }

    /**
     * Contar en la base de datos los registros del reporte detallado
     *
     * @param fechaInicial
     * @param fechaFinal   Fecha Final
     * @param tipoSol      Tipo solicitud
     * @param estado       estado
     * @return un listado con los registros encontrados de la base de datos.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Victor Bocanegra
     */
    public int numRegistroReporteDetalleSolicitudesHhpp(Date fechaInicial,
                                                        Date fechaFinal, String tipoSol, String estado)
            throws ApplicationException {

        int total = 0;
        Long totalResult;

        try {

            boolean control = true;

            String queryStr = "SELECT Count(1) "
                    + "FROM Solicitud s WHERE s.estadoRegistro = 1 ";

            if (tipoSol != null && !tipoSol.isEmpty()) {
                queryStr += " AND s.cambioDir = :cambioDir";
            }
            if (estado != null && !estado.isEmpty()) {
                queryStr += " AND s.estado = :estado";
            } else {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(tipoSol) && tipoSol.equals(Constant.RR_DIR_ESCALAMIENTO_HHPP)) {
                    queryStr += " AND s.estado IN ('PENDIENTE', 'FINALIZADO')";
                }
            }

            if (fechaInicial != null && fechaFinal != null) {
                if (fechaInicial.before(fechaFinal)) {
                    queryStr += " AND func('trunc', s.fechaIngreso) BETWEEN :fechaInicio and  :fechaFin";
                } else {
                    queryStr += " AND  func('trunc', s.fechaIngreso) = :fechaInicio";
                    control = false;
                }
            }

            //JDHT
            Query query = entityManager.createQuery(queryStr);

            if (tipoSol != null && !tipoSol.isEmpty()) {
                query.setParameter("cambioDir", tipoSol);
            }
            if (estado != null && !estado.isEmpty()) {
                query.setParameter("estado", estado);
            }
            if (fechaInicial != null) {
                java.sql.Date fInicio = new java.sql.Date(fechaInicial.getTime());
                query.setParameter("fechaInicio", fInicio);
            }

            if (control && fechaFinal != null) {
                 query.setParameter("fechaFin", fechaFinal);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            totalResult = (Long) query.getSingleResult();
            if (totalResult != null) {
                total = totalResult.intValue();
            }

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return total;
    }

    private String retornaTipoSol(String valor) {

        String tipo = "";

        if (valor.equalsIgnoreCase("0")) {
            tipo = "CREACION HHPP";
        } else if (valor.equalsIgnoreCase("1")) {
            tipo = "CAMBIO DIRECCION HHPP";
        } else if (valor.equalsIgnoreCase("2")) {
            tipo = "CAMBIO ESTRATO";
        } else if (valor.equalsIgnoreCase("4")) {
            tipo = "VT REPLANTEAMIENTO";
        } else if (valor.equalsIgnoreCase("5")) {
            tipo = "VIABILIDAD INTERNET";
        } else if (valor.equalsIgnoreCase("12")) {
            tipo = "VALIDACION DE COBERTURA";
        } else if (valor.equalsIgnoreCase("6")) {
            tipo = "CAMBIO ESTRATO MIG";
        } else if (valor.equalsIgnoreCase("13")) {
            tipo = "ESCALAMIENTO HHPP";
        }
        return tipo;

    }

    public List<Solicitud> consultarOrdenesDeSolicitudHHPP(int firstResult, int maxResults, CmtDireccionDetalladaMgl direccionDetalladaMgl, Solicitud filtro) throws ApplicationException {
        List<Solicitud> ordenesSolicitiudList = new ArrayList<>();
        try {
            StringBuilder consulta = new StringBuilder("SELECT s FROM Solicitud s WHERE  (s.direccionDetallada.direccionDetalladaId =:direccionDetallada or s.direccionDetalladaOrigenSolicitud.direccionDetalladaId =:direccionDetalladaOriginal) ");
            if (filtro != null && filtro.getIdSolicitud() != null) {
                consulta.append(" AND s.idSolicitud =:id ");
            }
            if (filtro != null && filtro.getCambioDir() != null && !filtro.getCambioDir().isEmpty()) {
                consulta.append(" AND s.cambioDir =:tipoSolicitud ");
            }
            if (filtro != null && filtro.getTecnologiaId() != null && filtro.getTecnologiaId().getBasicaId() != null && filtro.getTecnologiaId().getBasicaId().intValue() != 0) {
                consulta.append(" AND s.tecnologiaId.basicaId =:tecnologia ");
            }
            if (filtro != null && filtro.getFechaIngreso() != null) {
                consulta.append(" AND s.fechaIngreso BETWEEN  :fechaCreacionI AND :fechaCreacionF ");
            }
            if (filtro != null && filtro.getFechaModificacion() != null) {
                consulta.append(" AND s.fechaModificacion BETWEEN  :fechaModI AND :fechaModF ");
            }
            if (filtro != null && filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
                consulta.append(" AND s.estado LIKE :estado ");
            }
            if (filtro != null && filtro.getUsuario() != null && !filtro.getUsuario().isEmpty()) {
                consulta.append(" AND s.usuario LIKE :usuario ");
            }
            Query query = entityManager.createQuery(consulta.toString());
            query.setParameter("direccionDetallada", direccionDetalladaMgl.getDireccionDetalladaId());
            query.setParameter("direccionDetalladaOriginal", direccionDetalladaMgl.getDireccionDetalladaId());

            if (filtro != null && filtro.getIdSolicitud() != null) {
                query.setParameter("id", filtro.getIdSolicitud());
            }
            if (filtro != null && filtro.getCambioDir() != null && !filtro.getCambioDir().isEmpty()) {
                query.setParameter("tipoSolicitud", filtro.getCambioDir());
            }
            if (filtro != null && filtro.getTecnologiaId() != null && filtro.getTecnologiaId().getBasicaId() != null && filtro.getTecnologiaId().getBasicaId().intValue() != 0) {
                query.setParameter("tecnologia", filtro.getTecnologiaId().getBasicaId());
            }
            if (filtro != null && filtro.getFechaIngreso() != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaCreacionI", filtro.getFechaIngreso());
                String fechaFin = formato.format(filtro.getFechaIngreso());
                fechaFin = fechaFin + " 23:59:59";
                query.setParameter("fechaCreacionF", formatoFin.parse(fechaFin));
            }
            if (filtro != null && filtro.getFechaModificacion() != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaModI", filtro.getFechaModificacion());
                String fechaFin = formato.format(filtro.getFechaModificacion());
                fechaFin = fechaFin + " 23:59:59";
                query.setParameter("fechaModF", formatoFin.parse(fechaFin));
            }
            if (filtro != null && filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
                query.setParameter("estado", "%" + filtro.getEstado() + "%");
            }
            if (filtro != null && filtro.getUsuario() != null && !filtro.getUsuario().isEmpty()) {
                query.setParameter("usuario", "%" + filtro.getUsuario() + "%");
            }
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            ordenesSolicitiudList = query.getResultList();
        } catch (ParseException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return ordenesSolicitiudList;
    }

    public int countOrdenesDeSolicitudHHPP(CmtDireccionDetalladaMgl direccionDetalladaMgl, Solicitud filtro) throws ApplicationException {
        int cantidad = 0;
        try {
            StringBuilder consulta = new StringBuilder("SELECT count(s) FROM Solicitud s WHERE  (s.direccionDetallada.direccionDetalladaId =:direccionDetallada or s.direccionDetalladaOrigenSolicitud.direccionDetalladaId =:direccionDetallada) ");
            if (filtro != null && filtro.getIdSolicitud() != null) {
                consulta.append(" AND s.idSolicitud =:id ");
            }
            if (filtro != null && filtro.getCambioDir() != null && !filtro.getCambioDir().isEmpty()) {
                consulta.append(" AND s.cambioDir =:tipoSolicitud ");
            }
            if (filtro != null && filtro.getTecnologiaId() != null && filtro.getTecnologiaId().getBasicaId() != null && filtro.getTecnologiaId().getBasicaId().intValue() != 0) {
                consulta.append(" AND s.tecnologiaId.basicaId =:tecnologia ");
            }
            if (filtro != null && filtro.getFechaIngreso() != null) {
                consulta.append(" AND s.fechaIngreso BETWEEN  :fechaCreacionI AND :fechaCreacionF ");
            }
            if (filtro != null && filtro.getFechaModificacion() != null) {
                consulta.append(" AND s.fechaModificacion BETWEEN  :fechaModI AND :fechaModF ");
            }
            if (filtro != null && filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
                consulta.append(" AND s.estado LIKE :estado ");
            }
            if (filtro != null && filtro.getUsuario() != null && !filtro.getUsuario().isEmpty()) {
                consulta.append(" AND s.usuario LIKE :usuario ");
            }
            Query query = entityManager.createQuery(consulta.toString());
            query.setParameter("direccionDetallada", direccionDetalladaMgl.getDireccionDetalladaId());

            if (filtro != null && filtro.getIdSolicitud() != null) {
                query.setParameter("id", filtro.getIdSolicitud());
            }
            if (filtro != null && filtro.getCambioDir() != null && !filtro.getCambioDir().isEmpty()) {
                query.setParameter("tipoSolicitud", filtro.getCambioDir());
            }
            if (filtro != null && filtro.getTecnologiaId() != null && filtro.getTecnologiaId().getBasicaId() != null && filtro.getTecnologiaId().getBasicaId().intValue() != 0) {
                query.setParameter("tecnologia", filtro.getTecnologiaId().getBasicaId());
            }
            if (filtro != null && filtro.getFechaIngreso() != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaCreacionI", filtro.getFechaIngreso());
                String fechaFin = formato.format(filtro.getFechaIngreso());
                fechaFin = fechaFin + " 23:59:59";
                query.setParameter("fechaCreacionF", formatoFin.parse(fechaFin));
            }
            if (filtro != null && filtro.getFechaModificacion() != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaModI", filtro.getFechaModificacion());
                String fechaFin = formato.format(filtro.getFechaModificacion());
                fechaFin = fechaFin + " 23:59:59";
                query.setParameter("fechaModF", formatoFin.parse(fechaFin));
            }
            if (filtro != null && filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
                query.setParameter("estado", "%" + filtro.getEstado() + "%");
            }
            if (filtro != null && filtro.getUsuario() != null && !filtro.getUsuario().isEmpty()) {
                query.setParameter("usuario", "%" + filtro.getUsuario() + "%");
            }
            cantidad = ((Long) (query.getSingleResult())).intValue();
        } catch (ParseException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return cantidad;
    }

    public int solictudesByUsuario(String usuario) throws ApplicationException {
        int numeroSolicitudes = 0;
        try {
            Query query = entityManager.createNativeQuery("SELECT count(1) FROM " + Constant.MGL_DATABASE_SCHEMA + "." + "TEC_SOL_TEC_HABILITADA s WHERE "
                    + " s.USUARIO = ?  AND  EXTRACT(month from s.FECHAINGRESO) = EXTRACT(month FROM sysdate) "
                    + " AND s.CAMBIO_DIR = 4 ");

            query.setParameter(1, usuario);

            numeroSolicitudes = query.getSingleResult() == null ? 0 : ((BigDecimal) query.getSingleResult()).intValue();
            return numeroSolicitudes;

          } catch (Exception e) {
              String msg = "Se produjo un error al momento de ejecutar el método '"
                      + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                      + e.getMessage();
              LOGGER.error(msg);
              throw new ApplicationException(msg, e);
          }
      }

    /**
     * Busca la solicitud de escalamiento de cobertura más reciente a
     * partir del ID de la dirección.
     *
     * @param idDireccion {@link BigDecimal} ID de dirección
     * @param idBasica
     * @return {@link Solicitud} Solicitud de escalamiento encontrada
     * @throws ApplicationException Excepción de la app
     * @author Gildardo Mora
     */
    public Solicitud findUltimaSolicitudEscalamientoCobertura(BigDecimal idDireccion, BigDecimal idBasica) throws ApplicationException {
        EntityManager manager = null;
        try {
            manager = EntityManagerUtils.getEntityManager(this.getClass().getName());
            manager.getTransaction().begin(); //iniciar transacción
            StoredProcedureQuery storedProcedureQuery = manager.createStoredProcedureQuery(
                    Constant.MGL_DATABASE_SCHEMA + "." + "sp_ult_solicitud_dir");
            storedProcedureQuery.registerStoredProcedureParameter(
                    "PI_DIRECCION_ID", BigDecimal.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter(
                    "PI_BASICA_ID", BigDecimal.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter(
                    "PO_CODIGO", BigDecimal.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter(
                    "PO_DESCRIPCION", String.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter(
                    "PO_RESPUESTA", Class.class, ParameterMode.REF_CURSOR);

            storedProcedureQuery.setParameter("PI_DIRECCION_ID", idDireccion);
            storedProcedureQuery.setParameter("PI_BASICA_ID", idBasica);
            storedProcedureQuery.execute();

            int codError = Integer.parseInt(
                    (storedProcedureQuery.getOutputParameterValue("PO_CODIGO")).toString());
            String msgError = (String) storedProcedureQuery.getOutputParameterValue("PO_DESCRIPCION");

            if (codError == 1) {
                throw new ApplicationException("Error al consultar la solicitud: " + msgError);
            }

            Solicitud solicitud = new Solicitud();
            Object[] resultado = (Object[]) storedProcedureQuery.getSingleResult();
            manager.getTransaction().commit();
            solicitud = convertToSolicitud(resultado);

            return solicitud;

        } catch (NoResultException nre) {
            return new Solicitud();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } finally {
            if (Objects.nonNull(manager) && manager.isOpen()) {
                manager.clear();
                manager.close();
            }
        }
    }

    /**
     *Convierte la respuesta del procedimiento al objeto Solicitúd válido para las operaciones.
     *
     * @param resultado {@link Object[]}
     * @return {@link Solicitud}
     * @throws ApplicationException Excepción de la App
     */
    private Solicitud convertToSolicitud(Object[] resultado) throws ApplicationException {
        Solicitud solicitud = new Solicitud();
        try {
                solicitud.setIdSolicitud((BigDecimal) resultado[0]);
                solicitud.setCuentaMatriz((String) resultado[1]);
                solicitud.setContacto((String) resultado[6]);
                solicitud.setTelContacto((String) resultado[7]);
                solicitud.setSolicitante((String) resultado[10]);
                solicitud.setCorreo((String) resultado[11]);
                solicitud.setMotivo((String) resultado[14]);
                solicitud.setEstado((String) resultado[16]);
                solicitud.setTipo((String) resultado[17]);
                solicitud.setFechaIngreso((Date) resultado[18]);
                solicitud.setFechaCancelacion(resultado[20] != null ? (Date) resultado[20] : null);
                solicitud.setTipoSol((String) resultado[33]);
                solicitud.setUsuario((String) resultado[34]);
                solicitud.setRptGestion(resultado[35] != null ? (String) resultado[35] : null);
                solicitud.setCambioDir((String) resultado[49]);
                solicitud.setDisponibilidadGestion(resultado[54] != null ? (String) resultado[54] : null);
                CmtDireccionDetalladaMgl direccionDetalladaMgl = new CmtDireccionDetalladaMgl();
                direccionDetalladaMgl.setDireccionDetalladaId((BigDecimal) resultado[60]);
                solicitud.setDireccionDetallada(direccionDetalladaMgl);
                CmtBasicaMgl cmtBasicaMgl = new CmtBasicaMgl();
                cmtBasicaMgl.setBasicaId((BigDecimal) resultado[61]);
                solicitud.setTecnologiaId(cmtBasicaMgl);
                solicitud.setCentroPobladoId((BigDecimal) resultado[62]);
                solicitud.setIdFactibilidad(resultado[71] != null ? (BigDecimal) resultado[71] : null);
                cmtBasicaMgl.setBasicaId(resultado[72] != null ? (BigDecimal) resultado[72] : null);
                solicitud.setIdBasicaDivi(cmtBasicaMgl);
                solicitud.setCoordX((String) resultado[73]);
                solicitud.setCoordY((String) resultado[74]);
                solicitud.setEstadoRegistro(Integer.parseInt(resultado[78].toString()));
                solicitud.setUsuarioGestionador(resultado[79] != null ? (String) resultado[79] : null);

            return solicitud;

        } catch (NullPointerException ne){
            throw new ApplicationException(ne);
        }catch (Exception e){
            throw new ApplicationException(e);
        }
    }

}