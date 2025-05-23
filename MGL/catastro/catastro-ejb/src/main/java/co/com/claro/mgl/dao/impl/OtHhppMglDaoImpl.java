/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.FiltroConsultaOtDireccionesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Juan David Hernandez
 */
public class OtHhppMglDaoImpl extends GenericDaoImpl<OtHhppMgl> {

    private static final Logger LOGGER = LogManager.getLogger(OtHhppMglDaoImpl.class);

    public List<OtHhppMgl> findAll() throws ApplicationException {
        List<OtHhppMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "OtHhppMgl c WHERE c.estadoRegistro = :estado "
                + "ORDER BY c.otHhppId DESC");
        query.setParameter("estado", 1);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        query.setFirstResult(0);
        query.setMaxResults(30);
        resultList = (List<OtHhppMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<OtHhppMgl> findOtHhppById(BigDecimal otId)
            throws ApplicationException {
        List<OtHhppMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "OtHhppMgl c WHERE c.otHhppId = :otId ");
        query.setParameter("otId", otId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<OtHhppMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public OtHhppMgl findOtByDireccionAndSubDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccion, CmtBasicaMgl estado)
            throws ApplicationException {
        List<OtHhppMgl> resultList;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c FROM OtHhppMgl c WHERE c.direccionId = :direccion AND c.estadoGeneral= :estado");

        if (subDireccion != null) {
            sql.append(" AND c.subDireccionId = :subDireccion");
        } else {
            sql.append(" AND c.subDireccionId is null");
        }

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("direccion", direccion);
        query.setParameter("estado", estado);
        if (subDireccion != null) {
            query.setParameter("subDireccion", subDireccion);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<OtHhppMgl>) query.getResultList();
        getEntityManager().clear();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public int countAllOtHhpp(List<String> listadoRolesUsuario) throws ApplicationException {
        Query query = entityManager.createQuery("SELECT count(1) FROM "
                + "OtHhppMgl c WHERE c.estadoRegistro = :estado "
                + "AND c.tipoOtHhppId.rolGestion IN :listadoRolesUsuario ");
        query.setParameter("estado", 1);
        query.setParameter("listadoRolesUsuario", listadoRolesUsuario);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        int result = query.getSingleResult() == null ? 0
                : ((Long) query.getSingleResult()).intValue();
        getEntityManager().clear();
        return result;
    }

     public List<OtHhppMgl> findAllOtHhppPaginada(int firstResult,
            int maxResults, List<String> listadoRolesUsuario, FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto, CmtBasicaMgl estadoAbierto) throws ApplicationException {
        try {
            final int MILISEGUNDOS_DIA = 86400000;

            List<OtHhppMgl> resultList;
            List<OtHhppMgl> ordenesAux;

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT c FROM "
                    + "OtHhppMgl c WHERE c.estadoRegistro = :estado ");

            if (filtroConsultaOtDireccionesDto.getId() != null && !filtroConsultaOtDireccionesDto.getId().isEmpty()) {
                sql.append("AND UPPER(c.otHhppId) LIKE :id ");
            }

            if (filtroConsultaOtDireccionesDto.getTipo() != null) {
                sql.append("AND c.tipoOtHhppId = :tipo ");
            }

            if (filtroConsultaOtDireccionesDto.getNombreContacto() != null && !filtroConsultaOtDireccionesDto.getNombreContacto().isEmpty()) {
                sql.append("AND UPPER(c.nombreContacto) LIKE :nombreContacto ");
            }

            if (filtroConsultaOtDireccionesDto.getTelefonoContacto() != null && !filtroConsultaOtDireccionesDto.getTelefonoContacto().isEmpty()) {
                sql.append("AND UPPER(c.telefonoContacto) LIKE :telefonoContacto ");
            }

            if (filtroConsultaOtDireccionesDto.getCorreoContacto() != null && !filtroConsultaOtDireccionesDto.getCorreoContacto().isEmpty()) {
                sql.append("AND c.correoContacto LIKE :correoContacto ");
            }

            if (filtroConsultaOtDireccionesDto.getFechaCreacion() != null) {
                sql.append("AND c.fechaCreacionOt BETWEEN :fechaCreacionInicial AND :fechaCreacionFinal ");
            }
            
            if (filtroConsultaOtDireccionesDto.getId() != null
                    && !filtroConsultaOtDireccionesDto.getId().isEmpty()) {

                if (filtroConsultaOtDireccionesDto.getEstado() != null) {
                    sql.append("AND c.estadoGeneral = :estadoGeneral ");
                } 
            } else {
                if (filtroConsultaOtDireccionesDto.getEstado() != null) {
                    sql.append("AND c.estadoGeneral = :estadoGeneral ");
                } else {
                    if (estadoAbierto != null) {
                        sql.append("AND c.estadoGeneral = :estadoBierto ");
                    }
                }
            }
            
            if (filtroConsultaOtDireccionesDto.getSla() > 0) {
                sql.append("AND c.tipoOtHhppId.ans = :ans ");
            }

            sql.append(" ORDER BY c.otHhppId DESC");
            Query query = entityManager.createQuery(sql.toString());
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            if (filtroConsultaOtDireccionesDto.getId() != null && !filtroConsultaOtDireccionesDto.getId().isEmpty()) {
                query.setParameter("id", "%" + filtroConsultaOtDireccionesDto.getId() + "%");
            }

            if (filtroConsultaOtDireccionesDto.getTipo() != null) {
                query.setParameter("tipo", filtroConsultaOtDireccionesDto.getTipo());
            }

            if (filtroConsultaOtDireccionesDto.getNombreContacto() != null && !filtroConsultaOtDireccionesDto.getNombreContacto().isEmpty()) {
                query.setParameter("nombreContacto", "%" + filtroConsultaOtDireccionesDto.getNombreContacto().toUpperCase() + "%");
            }

            if (filtroConsultaOtDireccionesDto.getTelefonoContacto() != null && !filtroConsultaOtDireccionesDto.getTelefonoContacto().isEmpty()) {
                query.setParameter("telefonoContacto", "%" + filtroConsultaOtDireccionesDto.getTelefonoContacto() + "%");
            }

            if (filtroConsultaOtDireccionesDto.getCorreoContacto() != null && !filtroConsultaOtDireccionesDto.getCorreoContacto().isEmpty()) {
                query.setParameter("correoContacto", "%" + filtroConsultaOtDireccionesDto.getCorreoContacto() + "%");
            }

            if (filtroConsultaOtDireccionesDto.getFechaCreacion() != null) {
                query.setParameter("fechaCreacionInicial", filtroConsultaOtDireccionesDto.getFechaCreacion());

                long fechaEnMilisegundos = filtroConsultaOtDireccionesDto.getFechaCreacion().getTime() + MILISEGUNDOS_DIA - 1;
                query.setParameter("fechaCreacionFinal", new Date(fechaEnMilisegundos));
            }
             
            if (filtroConsultaOtDireccionesDto.getId() != null
                    && !filtroConsultaOtDireccionesDto.getId().isEmpty()) {

                if (filtroConsultaOtDireccionesDto.getEstado() != null) {
                    query.setParameter("estadoGeneral", filtroConsultaOtDireccionesDto.getEstado());
                }
            } else {
                if (filtroConsultaOtDireccionesDto.getEstado() != null) {
                    query.setParameter("estadoGeneral", filtroConsultaOtDireccionesDto.getEstado());
                } else {
                    if (estadoAbierto != null) {
                        query.setParameter("estadoBierto", estadoAbierto);
                    }
                }
            }
            
             if (filtroConsultaOtDireccionesDto.getSla() > 0) {
                query.setParameter("ans", filtroConsultaOtDireccionesDto.getSla());
            }

            query.setParameter("estado", 1);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            ordenesAux = query.getResultList();
            resultList = ordenesReg(ordenesAux, filtroConsultaOtDireccionesDto.getRegional());
            getEntityManager().clear();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    public List<OtHhppMgl> findAllOtHhppByDireccionDetalladaId(int firstResult,
            int maxResults, BigDecimal direccionId, BigDecimal subDireccionId , OtHhppMgl filtro) throws ApplicationException {
        List<OtHhppMgl> resultList;
        try {
            StringBuilder sql = new StringBuilder("SELECT c FROM OtHhppMgl c where c.direccionId.dirId =:direccionId AND ");

            if (subDireccionId != null) {
                sql.append(" c.subDireccionId.sdiId =:subDireccionId AND ");
            } else {
                sql.append(" c.subDireccionId is null AND ");
            }
            sql.append(" c.estadoRegistro = :estado  ");

            if (filtro != null && filtro.getOtHhppId() != null && filtro.getOtHhppId().intValue() != 0) {
                sql.append(" AND c.otHhppId =:idOt ");
            }
            if (filtro != null && filtro.getTipoOtHhppId() != null && filtro.getTipoOtHhppId().getTipoOtId() != null && filtro.getTipoOtHhppId().getTipoOtId().intValue() != 0) {
                sql.append(" AND c.tipoOtHhppId.tipoOtId =:tipoOtId ");
            }
            if (filtro != null && filtro.getFechaCreacion() != null) {
                sql.append(" AND c.fechaCreacion BETWEEN :fechaCreacionI AND :fechaCreacionF ");
            }
            if (filtro != null && filtro.getFechaEdicion() != null) {
                sql.append(" AND c.fechaCreacion BETWEEN :fechaEdicionI AND :fechaEdicionF ");
            }
            if (filtro != null && filtro.getNombreContacto() != null && !filtro.getNombreContacto().isEmpty()) {
                sql.append(" AND c.nombreContacto LIKE :usuario ");
            }
            if (filtro != null && filtro.getEstadoGeneral() != null && filtro.getEstadoGeneral().getNombreBasica() != null && !filtro.getEstadoGeneral().getNombreBasica().isEmpty()) {
                sql.append(" AND c.estadoGeneral.nombreBasica LIKE :estadoGeneral ");
            }

            Query query = entityManager.createQuery(sql.toString());

            query.setParameter("estado", 1);

            if (direccionId != null) {
                query.setParameter("direccionId", direccionId);
            }
            if (subDireccionId != null) {
                query.setParameter("subDireccionId", subDireccionId);
            }

            if (filtro != null && filtro.getOtHhppId() != null && filtro.getOtHhppId().intValue() != 0) {
                query.setParameter("idOt", filtro.getOtHhppId());
            }
            if (filtro != null && filtro.getTipoOtHhppId() != null && filtro.getTipoOtHhppId().getTipoOtId() != null && filtro.getTipoOtHhppId().getTipoOtId().intValue() != 0) {
                query.setParameter("tipoOtId", filtro.getTipoOtHhppId().getTipoOtId());
            }
            if (filtro != null && filtro.getFechaCreacion() != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaCreacionI", filtro.getFechaCreacion());
                String fechaFin = formato.format(filtro.getFechaCreacion());
                fechaFin = fechaFin + " 23:59:59";
                query.setParameter("fechaCreacionF", formatoFin.parse(fechaFin));
            }
            if (filtro != null && filtro.getFechaEdicion() != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaEdicionI", filtro.getFechaEdicion());
                String fechaFin = formato.format(filtro.getFechaEdicion());
                fechaFin = fechaFin + " 23:59:59";
                query.setParameter("fechaEdicionF", formatoFin.parse(fechaFin));
            }
            if (filtro != null && filtro.getNombreContacto() != null && !filtro.getNombreContacto().isEmpty()) {
                query.setParameter("usuario", "%" + filtro.getNombreContacto() + "%");
            }
            if (filtro != null && filtro.getEstadoGeneral() != null && filtro.getEstadoGeneral().getNombreBasica() != null && !filtro.getEstadoGeneral().getNombreBasica().isEmpty()) {
                query.setParameter("estadoGeneral", "%" + filtro.getEstadoGeneral().getNombreBasica() + "%");
            }

            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<OtHhppMgl>) query.getResultList();
            getEntityManager().clear();
        } catch (ParseException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return resultList;
    }

    public int countAllOtHhppByDireccionDetalladaId(BigDecimal direccionId, BigDecimal subDireccionId,OtHhppMgl filtro)
            throws ApplicationException {
        StringBuilder sql = new StringBuilder();
        int result =0;
        try {
            sql.append("SELECT count(c.otHhppId) FROM OtHhppMgl c where c.direccionId.dirId =:direccionId AND ");

            if (subDireccionId != null) {
                sql.append(" c.subDireccionId.sdiId =:subDireccionId AND ");
            } else {
                sql.append(" c.subDireccionId.sdiId is null AND ");
            }
            sql.append("c.estadoRegistro = :estado  ");

            if (filtro != null && filtro.getOtHhppId() != null && filtro.getOtHhppId().intValue() != 0) {
                sql.append(" AND c.otHhppId =:idOt ");
            }
            if (filtro != null && filtro.getTipoOtHhppId() != null && filtro.getTipoOtHhppId().getTipoOtId() != null && filtro.getTipoOtHhppId().getTipoOtId().intValue() != 0) {
                sql.append(" AND c.tipoOtHhppId.tipoOtId =:tipoOtId ");
            }
            if (filtro != null && filtro.getFechaCreacion() != null) {
                sql.append(" AND c.fechaCreacion BETWEEN :fechaCreacionI AND :fechaCreacionF ");
            }
            if (filtro != null && filtro.getFechaEdicion() != null) {
                sql.append(" AND c.fechaCreacion BETWEEN :fechaEdicionI AND :fechaEdicionF ");
            }
            if (filtro != null && filtro.getNombreContacto() != null && !filtro.getNombreContacto().isEmpty()) {
                sql.append(" AND c.nombreContacto LIKE :usuario ");
            }
            if (filtro != null && filtro.getEstadoGeneral() != null && filtro.getEstadoGeneral().getNombreBasica() != null && !filtro.getEstadoGeneral().getNombreBasica().isEmpty()) {
                sql.append(" AND c.estadoGeneral.nombreBasica LIKE :estadoGeneral ");
            }
            
            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("estado", 1);
            query.setParameter("direccionId", direccionId);
            
            if (subDireccionId != null) {
                query.setParameter("subDireccionId", subDireccionId);
            }

            if (filtro != null && filtro.getOtHhppId() != null && filtro.getOtHhppId().intValue() != 0) {
                query.setParameter("idOt", filtro.getOtHhppId());
            }
            if (filtro != null && filtro.getTipoOtHhppId() != null && filtro.getTipoOtHhppId().getTipoOtId() != null && filtro.getTipoOtHhppId().getTipoOtId().intValue() != 0) {
                query.setParameter("tipoOtId", filtro.getTipoOtHhppId().getTipoOtId());
            }
            if (filtro != null && filtro.getFechaCreacion() != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaCreacionI", filtro.getFechaCreacion());
                String fechaFin = formato.format(filtro.getFechaCreacion());
                fechaFin = fechaFin + " 23:59:59";
                query.setParameter("fechaCreacionF", formatoFin.parse(fechaFin));
            }
            if (filtro != null && filtro.getFechaEdicion() != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaEdicionI", filtro.getFechaEdicion());
                String fechaFin = formato.format(filtro.getFechaEdicion());
                fechaFin = fechaFin + " 23:59:59";
                query.setParameter("fechaEdicionF", formatoFin.parse(fechaFin));
            }
            if (filtro != null && filtro.getNombreContacto() != null && !filtro.getNombreContacto().isEmpty()) {
                query.setParameter("usuario", "%" + filtro.getNombreContacto() + "%");
            }
            if (filtro != null && filtro.getEstadoGeneral() != null && filtro.getEstadoGeneral().getNombreBasica() != null && !filtro.getEstadoGeneral().getNombreBasica().isEmpty()) {
                query.setParameter("estadoGeneral", "%" + filtro.getEstadoGeneral().getNombreBasica() + "%");
            }
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();
            getEntityManager().clear();
        }catch (ParseException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return result;
    }

    /**
     * Validar que un tipo de ot no este en uso al estar asociada a una ot
     *
     * @author Juan David Hernandez
     * @param idTipoOtHhpp
     * @return verdadero si se encuentra en uso
     * @throws ApplicationException
     */
    public boolean validarTipoOtHhppEnUso(BigDecimal idTipoOtHhpp)
            throws ApplicationException {
        Query query = entityManager.createQuery("SELECT c FROM "
                + "OtHhppMgl c WHERE c.tipoOtHhppId.tipoOtId =:idTipoOtHhpp "
                + "AND c.estadoRegistro = :estado ");
        query.setParameter("estado", 1);
        query.setParameter("idTipoOtHhpp", idTipoOtHhpp);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List result = query.getResultList();
        getEntityManager().clear();
        if (result != null && !result.isEmpty() && result.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * valbuenayf metodo para buscar una orden de trabajo con el id de la
     * solicitud
     *
     * @param idSolicitud
     * @return
     */
    public OtHhppMgl findOrdenTrabajoByIdSolicitud(BigDecimal idSolicitud) {
        OtHhppMgl otHhppMgl;
        try {
            Query query = entityManager.createNamedQuery("OtHhppMgl.findOrdenTrabajoByIdSolicitud");
            query.setParameter("idSolicitud", idSolicitud);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            otHhppMgl = (OtHhppMgl) query.getSingleResult();
            getEntityManager().clear();
        } catch (NoResultException r) {
            LOGGER.info("No se encontro ningun resultado para orden de trabajo con el id de la solicitud : " + idSolicitud + " " + r);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en findOrdenTrabajoByIdSolicitud de CmtOrdenTrabajoMglDaoImpl: " + e);
            return null;
        }
        return otHhppMgl;
    }
    
    public int countByFiltro(FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto, CmtBasicaMgl estadoAbierto) throws ApplicationException {
        try {
            final int MILISEGUNDOS_DIA = 86400000;

            List<OtHhppMgl> resultList;
            List<OtHhppMgl> ordenesAux;

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT Count (1) FROM "
                    + "OtHhppMgl c WHERE c.estadoRegistro = :estado ");

            if (filtroConsultaOtDireccionesDto.getId() != null && !filtroConsultaOtDireccionesDto.getId().isEmpty()) {
                sql.append("AND UPPER(c.otHhppId) LIKE :id ");
            }

            if (filtroConsultaOtDireccionesDto.getTipo() != null) {
                sql.append("AND c.tipoOtHhppId = :tipo ");
            }

            if (filtroConsultaOtDireccionesDto.getNombreContacto() != null && !filtroConsultaOtDireccionesDto.getNombreContacto().isEmpty()) {
                sql.append("AND UPPER(c.nombreContacto) LIKE :nombreContacto ");
            }

            if (filtroConsultaOtDireccionesDto.getTelefonoContacto() != null && !filtroConsultaOtDireccionesDto.getTelefonoContacto().isEmpty()) {
                sql.append("AND UPPER(c.telefonoContacto) LIKE :telefonoContacto ");
            }

            if (filtroConsultaOtDireccionesDto.getCorreoContacto() != null && !filtroConsultaOtDireccionesDto.getCorreoContacto().isEmpty()) {
                sql.append("AND c.correoContacto LIKE :correoContacto ");
            }

            if (filtroConsultaOtDireccionesDto.getFechaCreacion() != null) {
                sql.append("AND c.fechaCreacionOt BETWEEN :fechaCreacionInicial AND :fechaCreacionFinal ");
            }

            if (filtroConsultaOtDireccionesDto.getEstado() != null) {
                sql.append("AND c.estadoGeneral = :estadoGeneral ");
            } else{
                if (estadoAbierto != null) {
                    sql.append("AND c.estadoGeneral = :estadoBierto ");
                }
            }
            
            if (filtroConsultaOtDireccionesDto.getSla() > 0) {
                sql.append("AND c.tipoOtHhppId.ans = :ans ");
            }    


            sql.append(" ORDER BY c.otHhppId DESC");
            Query query = entityManager.createQuery(sql.toString());

            if (filtroConsultaOtDireccionesDto.getId() != null && !filtroConsultaOtDireccionesDto.getId().isEmpty()) {
                query.setParameter("id", "%" + filtroConsultaOtDireccionesDto.getId() + "%");
            }

            if (filtroConsultaOtDireccionesDto.getTipo() != null) {
                query.setParameter("tipo", filtroConsultaOtDireccionesDto.getTipo());
            }

            if (filtroConsultaOtDireccionesDto.getNombreContacto() != null && !filtroConsultaOtDireccionesDto.getNombreContacto().isEmpty()) {
                query.setParameter("nombreContacto", "%" + filtroConsultaOtDireccionesDto.getNombreContacto().toUpperCase() + "%");
            }

            if (filtroConsultaOtDireccionesDto.getTelefonoContacto() != null && !filtroConsultaOtDireccionesDto.getTelefonoContacto().isEmpty()) {
                query.setParameter("telefonoContacto", "%" + filtroConsultaOtDireccionesDto.getTelefonoContacto() + "%");
            }

            if (filtroConsultaOtDireccionesDto.getCorreoContacto() != null && !filtroConsultaOtDireccionesDto.getCorreoContacto().isEmpty()) {
                query.setParameter("correoContacto", "%" + filtroConsultaOtDireccionesDto.getCorreoContacto() + "%");
            }

            if (filtroConsultaOtDireccionesDto.getFechaCreacion() != null) {
                query.setParameter("fechaCreacionInicial", filtroConsultaOtDireccionesDto.getFechaCreacion());

                long fechaEnMilisegundos = filtroConsultaOtDireccionesDto.getFechaCreacion().getTime() + MILISEGUNDOS_DIA - 1;
                query.setParameter("fechaCreacionFinal", new Date(fechaEnMilisegundos));
            }

            if (filtroConsultaOtDireccionesDto.getEstado() != null) {
                query.setParameter("estadoGeneral", filtroConsultaOtDireccionesDto.getEstado());
            }else {
                if (estadoAbierto != null) {
                    query.setParameter("estadoBierto", estadoAbierto);
                }
            }
            
            if (filtroConsultaOtDireccionesDto.getSla() > 0) {
                query.setParameter("ans", filtroConsultaOtDireccionesDto.getSla());
            }

            query.setParameter("estado", 1);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long result = (Long) query.getSingleResult();
            getEntityManager().clear();
            if(result != null){
                return result.intValue();
            }else{
                return 0;
            }
           // resultList = ordenesReg(ordenesAux, filtroConsultaOtDireccionesDto.getRegional());
            //return resultList.size();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return 0;
        }
    }
    
      public List<OtHhppMgl> ordenesReg(List<OtHhppMgl> ordenesAux, CmtRegionalRr regional) {

        List<OtHhppMgl> retornar = new ArrayList<>();
        
        if (ordenesAux != null && ordenesAux.size() > 0) {
            List<OtHhppTecnologiaMgl> tecnologiaMgls;
            for (OtHhppMgl otHhppMgl : ordenesAux) {
                tecnologiaMgls = otHhppMgl.getTecnologiaBasicaList();
                NodoMgl nod = null;
                if (tecnologiaMgls != null && tecnologiaMgls.size() == 1) {
                     nod = tecnologiaMgls.get(0).getNodo();
                } else if (tecnologiaMgls != null && tecnologiaMgls.size() > 1) {
                    for (OtHhppTecnologiaMgl tecnologiaMgl : tecnologiaMgls) {
                        if (tecnologiaMgl.getTecnoglogiaBasicaId() != null) {
                            //prioridades de tecnologias para extraer location
                            //BI, UNI, DTH, FTTH, GPON, FOU, RFO
                            //BI
                            if (tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                    && !tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                    && tecnologiaMgl.getTecnoglogiaBasicaId()
                                            .getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_BID)) {
                                nod = tecnologiaMgl.getNodo();
                                break;
                            }

                            //UNI
                            if (tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                    && !tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                    && tecnologiaMgl.getTecnoglogiaBasicaId()
                                            .getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)) {
                                nod = tecnologiaMgl.getNodo();
                                break;
                            }

                            //DTH
                            if (tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                    && !tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                    && tecnologiaMgl.getTecnoglogiaBasicaId()
                                            .getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)) {
                                nod = tecnologiaMgl.getNodo();
                                break;
                            }

                            //FTTH
                            if (tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                    && !tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                    && tecnologiaMgl.getTecnoglogiaBasicaId()
                                            .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_FTTTH)) {
                                nod = tecnologiaMgl.getNodo();
                                break;
                            }

                            //GPON
                            if (tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                    && !tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                    && tecnologiaMgl.getTecnoglogiaBasicaId()
                                            .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_GPON)) {
                                nod = tecnologiaMgl.getNodo();
                                break;
                            }

                            //FOU
                            if (tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                    && !tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                    && tecnologiaMgl.getTecnoglogiaBasicaId()
                                            .getIdentificadorInternoApp().equalsIgnoreCase(Constant.FIBRA_OP_UNI)) {
                                nod = tecnologiaMgl.getNodo();
                                break;
                            }

                            //RFO
                            if (tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp() != null
                                    && !tecnologiaMgl.getTecnoglogiaBasicaId().getIdentificadorInternoApp().isEmpty()
                                    && tecnologiaMgl.getTecnoglogiaBasicaId()
                                            .getIdentificadorInternoApp().equalsIgnoreCase(Constant.RED_FO)) {
                                nod = tecnologiaMgl.getNodo();
                                break;
                            }
                        }
                    }
                }
                if (regional != null) {
                   if(regional.getRegionalRrId().compareTo(new BigDecimal("0"))==0){
                        CmtComunidadRr comunidadRr = nod != null ? nod.getComId() : null;
                        if (comunidadRr == null) {
                            retornar.add(otHhppMgl); 
                        } 
                    } else {
                        CmtComunidadRr comunidadRr = nod != null ? nod.getComId() : null;
                        if (comunidadRr != null) {
                            CmtRegionalRr regionCon = nod != null ? comunidadRr.getRegionalRr() : null;
                            if (regionCon != null) {
                                if (regionCon.getRegionalRrId().compareTo(regional.getRegionalRrId()) == 0) {
                                    otHhppMgl.setRegionalRr(regionCon);
                                    retornar.add(otHhppMgl);
                                }
                            }
                        }
                    }

                } else {
                    CmtComunidadRr comunidadRr = nod != null ? nod.getComId() : null;
                    if (comunidadRr != null) {
                        CmtRegionalRr regionCon = nod != null ? comunidadRr.getRegionalRr() : null;
                        if (regionCon != null) {
                            otHhppMgl.setRegionalRr(regionCon);
                            retornar.add(otHhppMgl);
                        }
                    }else{
                        retornar.add(otHhppMgl); 
                    }
                }
            }

        }
        getEntityManager().clear();
        return retornar;
    }
      
      
    /**
     * Bocanegra vm metodo para buscar una orden de trabajo con el id de la
     * orden
     *
     * @param idOt
     * @return OtHhppMgl
     */
    public OtHhppMgl findOtByIdOt(BigDecimal idOt)
            throws ApplicationException {

        OtHhppMgl result;

        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT c FROM OtHhppMgl c WHERE c.otHhppId = :idOt AND "
                    + " c.estadoRegistro= 1");

            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("idOt", idOt);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (OtHhppMgl) query.getSingleResult();
            getEntityManager().clear();

        } catch (NoResultException ex) {
            LOGGER.info("No se encontro ningun resultado para orden de trabajo con el id: " + idOt + " " + ex);
            return null;
        } catch (Exception ex) {
            LOGGER.error("No se encontro ningun resultado para orden de trabajo con el id: " + idOt + "" + ex);
            return null;
        }
        return result;
    }
    
    /**
     * Bocanegra vm metodo para buscar una orden de trabajo de HHPP 
     * por direccion y subdireccion
     * @param direccion
     * @param subDireccion
     * @return OtHhppMgl
     */
    public OtHhppMgl findOtHhppByDireccionAndSubDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccion)
            throws ApplicationException {
        
        List<OtHhppMgl> resultList;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c FROM OtHhppMgl c WHERE c.direccionId = :direccion ");

        if (subDireccion != null) {
            sql.append(" AND c.subDireccionId = :subDireccion");
        } else {
            sql.append(" AND c.subDireccionId is null");
        }

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("direccion", direccion);
        if (subDireccion != null) {
            query.setParameter("subDireccion", subDireccion);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<OtHhppMgl>) query.getResultList();
        getEntityManager().clear();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }
    
       /**
     * Busqueda de todas las Ordenes de trabajo por id Enlace
     *
     * @author Victor Bocanegra
     * @param idEnlace valor id del enlace para la busqueda
     * @param tecnologias lista de tecnologia para la busqueda
     * @return Retorna una lista de Ordenes de trabajo
     * @throws ApplicationException
     */
    public List<OtHhppMgl> findOtHhppByIdEnlaceAndTecnologias(String idEnlace, List<BigDecimal> tecnologias)
            throws ApplicationException {
        try {
            List<OtHhppMgl> resultList;
            Query query = entityManager.createQuery("SELECT c FROM "
                    + " OtHhppMgl c  "
                    + " JOIN OtHhppTecnologiaMgl t  ON  t.otHhppId.otHhppId = c.otHhppId "
                    + " WHERE c.enlaceId = :enlaceId  "
                    + " AND t.tecnoglogiaBasicaId.basicaId IN :tecnologias  "
                    + " AND  c.estadoRegistro = 1 "
                    + " ORDER BY c.fechaCreacion DESC");

            query.setParameter("enlaceId", idEnlace);
            query.setParameter("tecnologias", tecnologias);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<OtHhppMgl>) query.getResultList();
            getEntityManager().clear();
            return resultList;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método "
                    + "'" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método "
                    + "'" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Busqueda de Ordenes de trabajo por direccion y subdireccion y tecnologia
     *
     * @author Victor Bocanegra
     * @param direccion
     * @param subDireccion
     * @param tecnologia tecnologia para la busqueda
     * @return List<OtHhppMgl>
     * @throws ApplicationException
     */
    public List<OtHhppMgl> findOtHhppByDireccionAndTecnologias(DireccionMgl direccion,
            SubDireccionMgl subDireccion, CmtBasicaMgl tecnologia)
            throws ApplicationException {
        try {
            List<OtHhppMgl>  result;
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT c FROM OtHhppMgl c "
                    + " JOIN OtHhppTecnologiaMgl t  ON  t.otHhppId.otHhppId = c.otHhppId "
                    + " WHERE c.direccionId = :direccion");

            if (subDireccion != null) {
                sql.append(" AND c.subDireccionId = :subDireccion");
            } else {
                sql.append(" AND c.subDireccionId is null");
            }

            sql.append(" AND t.tecnoglogiaBasicaId = :tecnologia "
                    + "  AND c.estadoRegistro = 1  "
                    + "  ORDER BY c.fechaCreacion DESC ");

            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("direccion", direccion);
            if (subDireccion != null) {
                query.setParameter("subDireccion", subDireccion);
            }
            query.setParameter("tecnologia", tecnologia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = query.getResultList();
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método "
                    + "'" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método "
                    + "'" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
}
