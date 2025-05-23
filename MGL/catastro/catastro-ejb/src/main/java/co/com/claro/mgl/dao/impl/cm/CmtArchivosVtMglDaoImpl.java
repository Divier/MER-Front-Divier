/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class CmtArchivosVtMglDaoImpl extends GenericDaoImpl<CmtArchivosVtMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtArchivosVtMglDaoImpl.class);
    
    /**
     * Busca todos los Archivos vt en el repositorio
     *
     * @author Victor Bocanegra
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findAll()
            throws ApplicationException {
        try {
            List<CmtArchivosVtMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtArchivosVtMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * Busca lista de Archivos asociados a una VT en el repositorio
     *
     * @author Victor Bocanegra
     * @param cmtVisitaTecnicaMgl
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findAllByVt(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl)
            throws ApplicationException {
        try {
            List<CmtArchivosVtMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByIdVt");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (cmtVisitaTecnicaMgl != null) {
                query.setParameter("visitaTecnicaMglobj", cmtVisitaTecnicaMgl);
            }
            resultList = (List<CmtArchivosVtMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * Busca lista de Archivos de cierre de OT 
     * asociados a una OT en el repositorio
     *
     * @author Victor Bocanegra
     * @param ordenTrabajoMgl orden de trabajo
     * @param origen origen archivo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findByIdOtOrigenCO(CmtOrdenTrabajoMgl ordenTrabajoMgl, String origen)
            throws ApplicationException {
        try {
            List<CmtArchivosVtMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByIdOtOrigenCO");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (ordenTrabajoMgl != null) {
                query.setParameter("ordenTrabajoMglobj", ordenTrabajoMgl);
            }
            if (origen != null && !origen.equalsIgnoreCase("")) {
                query.setParameter("origenArchivo", origen);
            }
            resultList = (List<CmtArchivosVtMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * Consulta el maximo de la secuencia por Ot y origen de archivo Autor:
     * victor bocanegra
     *
     * @param ot
     * @param origenArchivos origen del archivo
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXOt(CmtOrdenTrabajoMgl ot, String origenArchivos)
            throws ApplicationException {

        try {
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByMaximoSecOt");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (ot != null) {
                query.setParameter("ordenTrabajoMglobj", ot);
            }
            if (origenArchivos != null && !origenArchivos.equalsIgnoreCase("")) {
                query.setParameter("origenArchivos", origenArchivos);
            }
            return query.getSingleResult() == null ? 0
                    : ((Integer) query.getSingleResult()).intValue();
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * Consulta el maximo de la secuencia por Vt Autor: victor bocanegra
     *
     * @param vt visita tecnica
     * @param origen origen del archivo
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXVt(CmtVisitaTecnicaMgl vt, String origen)
            throws ApplicationException {

        try {
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByMaximoSecVt");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (vt != null) {
                query.setParameter("visitaTecnicaMglobj", vt);
            }
            if(origen != null && !origen.equalsIgnoreCase("")){
                query.setParameter("origenArchivos", origen);
            }
            return query.getSingleResult() == null ? 0
                    : ((Integer) query.getSingleResult()).intValue();
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
     /**
     * Busca lista de Archivos de una OT asociados al cierre de una agenda
     * en el repositorio
     * @param agenda agenda a consultar
     * @author Victor Bocanegra
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findByIdOtAndAge(CmtAgendamientoMgl agenda)
            throws ApplicationException {
        try {
            List<CmtArchivosVtMgl> resultList= null;
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByIdOtAndAge");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (agenda != null) {
                query.setParameter("ordenTrabajoMglobj", agenda.getOrdenTrabajo());
                query.setParameter("actividadAge", agenda.getWorkForceId());
                resultList = (List<CmtArchivosVtMgl>) query.getResultList();
            }    
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
       /**
     * Busca lista de Archivos asociados a una solicitud en el repositorio
     *
     * @author Victor Bocanegra
     * @param solicitudCmMgl
     * @return CmtArchivosVtMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtArchivosVtMgl findAllBySolId(CmtSolicitudCmMgl solicitudCmMgl)
            throws ApplicationException {
        try {
            CmtArchivosVtMgl resultList;
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByIdSol");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (solicitudCmMgl != null) {
                query.setParameter("solicitudCmMgl", solicitudCmMgl);
            }
            resultList = (CmtArchivosVtMgl) query.getSingleResult();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
    
    
    /**
     * Busca lista de Archivos asociados a una VT en el repositorio
     *
     * @author Lenis Cardenas
     * @param cmtVisitaTecnicaMgl
     * @param origen
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findAllByVtxOrigen(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl,String origen)
            throws ApplicationException {
        try {
            List<CmtArchivosVtMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByIdVtxOrigen");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (cmtVisitaTecnicaMgl != null) {
                query.setParameter("visitaTecnicaMglobj", cmtVisitaTecnicaMgl);
            }
            if (origen != null) {
                query.setParameter("origenArchivo", origen);
            }
            resultList = (List<CmtArchivosVtMgl>) query.getResultList();
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    /**
     * Busca lista de Archivos de cierre de una OT
     *
     * @author Victor Bocanegra
     * @param ordenTrabajoMgl orden de trabajo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
    public List<CmtArchivosVtMgl> findFilesByIdOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT c FROM CmtArchivosVtMgl c WHERE c.ordenTrabajoMglobj "
                    + " = :ordenTrabajoMglobj AND c.estadoRegistro = 1 "
                    + "AND c.origenArchivos IN :archivosAcoAge ORDER BY c.idArchivosVt ASC ");

            List<String> origenesAcoAge = new ArrayList<>();
            origenesAcoAge.add(Constant.ORIGEN_CARGA_ARCHIVO_CIERRE_OT);

            Query q = entityManager.createQuery(sql.toString());
            
            if (ordenTrabajoMgl != null) {
                q.setParameter("ordenTrabajoMglobj", ordenTrabajoMgl);
            }
            if (!origenesAcoAge.isEmpty()) {
                q.setParameter("archivosAcoAge", origenesAcoAge);
            }
           
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
    /**
     * Busca un archivo por nombre, origen y ot.
     *
     * @author Victor Bocanegra
     * @param nombreArchivo
     * @param origen
     * @param ot
     * @return CmtArchivosVtMgl encontrado en el repositorio
     * @throws ApplicationException
     */
    public CmtArchivosVtMgl findByNombreArchivoAndOrigenAndOt(String nombreArchivo,
            String origen, CmtOrdenTrabajoMgl ot)
            throws ApplicationException {

        CmtArchivosVtMgl cmtArchivosVtMgl = null;
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT c FROM CmtArchivosVtMgl c WHERE c.nombreArchivo "
                    + " = :nombreArchivo AND c.estadoRegistro = 1 "
                    + "AND c.origenArchivos = :origenArchivos"
                    + " AND c.ordenTrabajoMglobj= :ordenTrabajoMglobj");

            Query q = entityManager.createQuery(sql.toString());

            if (nombreArchivo != null) {
                q.setParameter("nombreArchivo", nombreArchivo);
            }
            if (!origen.isEmpty()) {
                q.setParameter("origenArchivos", origen);
            }
            if (ot != null) {
                q.setParameter("ordenTrabajoMglobj", ot);
            }

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            cmtArchivosVtMgl = (CmtArchivosVtMgl) q.getSingleResult();
            return cmtArchivosVtMgl;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return cmtArchivosVtMgl;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return cmtArchivosVtMgl;
        }
    }
    
    
    /**
     * Busca lista de Archivos de la VT 
     *
     * @author cardenaslb
     * @param ordenTrabajoMgl orden de trabajo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findFilesVTByIdOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        CmtArchivosVtMgl cmtArchivosVtMgl = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT c FROM CmtArchivosVtMgl c WHERE c.ordenTrabajoMglobj "
                    + " = :ordenTrabajoMglobj AND c.estadoRegistro = 1 "
                    + "AND c.origenArchivos  IN :archivosAcoAge");

            List<String> origenesAcoAge = new ArrayList<>();
            origenesAcoAge.add(Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);
            origenesAcoAge.add(Constant.ORIGEN_CARGA_ARCHIVO_FINANCIERA);

            Query q = entityManager.createQuery(sql.toString());

            if (ordenTrabajoMgl != null) {
                q.setParameter("ordenTrabajoMglobj", ordenTrabajoMgl);
            }
            if (!origenesAcoAge.isEmpty()) {
                q.setParameter("archivosAcoAge", origenesAcoAge);
            }

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
    /**
     * Busca un archivo por nombre, origen y otHhpp.
     *
     * @author Victor Bocanegra
     * @param nombreArchivo
     * @param origen
     * @param otHhpp
     * @return CmtArchivosVtMgl encontrado en el repositorio
     * @throws ApplicationException
     */
    public CmtArchivosVtMgl findByNombreArchivoAndOrigenAndOtHhpp(String nombreArchivo,
            String origen, OtHhppMgl otHhpp)
            throws ApplicationException {

        CmtArchivosVtMgl cmtArchivosVtMgl = null;
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT c FROM CmtArchivosVtMgl c WHERE c.nombreArchivo "
                    + " = :nombreArchivo AND c.estadoRegistro = 1 "
                    + "AND c.origenArchivos = :origenArchivos"
                    + " AND c.ordenTrabajoHhppMglobj= :ordenTrabajoHhppMglobj");

            Query q = entityManager.createQuery(sql.toString());

            if (nombreArchivo != null) {
                q.setParameter("nombreArchivo", nombreArchivo);
            }
            if (!origen.isEmpty()) {
                q.setParameter("origenArchivos", origen);
            }
            if (otHhpp != null) {
                q.setParameter("ordenTrabajoHhppMglobj", otHhpp);
            }

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            cmtArchivosVtMgl = (CmtArchivosVtMgl) q.getSingleResult();
            return cmtArchivosVtMgl;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return cmtArchivosVtMgl;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return cmtArchivosVtMgl;
        }
    }
    
    /**
     * Consulta el maximo de la secuencia por Othhpp y origen de archivo Autor:
     * victor bocanegra
     *
     * @param otHhpp
     * @param origenArchivos origen del archivo
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXOtHhpp(OtHhppMgl otHhpp, String origenArchivos)
            throws ApplicationException {

        try {
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByMaximoSecOtHhpp");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (otHhpp != null) {
                query.setParameter("ordenTrabajoHhppMglobj", otHhpp);
            }
            if (origenArchivos != null && !origenArchivos.isEmpty()) {
                query.setParameter("origenArchivos", origenArchivos);
            }
            return query.getSingleResult() == null ? 0
                    : ((Integer) query.getSingleResult());
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
    /**
     * Busca lista de Archivos de una OTHhpp asociados al cierre de una agenda
     * en el repositorio
     *
     * @author Victor Bocanegra
     * @param agenda
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findByIdOtHhppAndAge(MglAgendaDireccion agenda)
            throws ApplicationException {
        try {
            List<CmtArchivosVtMgl> resultList = null;
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByIdOtHhppAndAge");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (agenda != null) {
                query.setParameter("ordenTrabajoHhppMglobj", agenda.getOrdenTrabajo());
                query.setParameter("actividadAge", agenda.getOfpsId());
                resultList = (List<CmtArchivosVtMgl>) query.getResultList();
            }
            return resultList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
   
    /**
     * Busca lista de Archivos asociados a una solicitud en el repositorio
     *
     * @author Victor Bocanegra
     * @param solicitudHhpp
     * @return CmtArchivosVtMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtArchivosVtMgl findAllBySolIdHhpp(Solicitud solicitudHhpp)
            throws ApplicationException {
        try {
            List<?> resultList;
            Query query = entityManager.createNamedQuery("CmtArchivosVtMgl.findByIdSolHhpp");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (solicitudHhpp != null) {
                query.setParameter("solicitudHhpp", solicitudHhpp);
            }
            resultList = query.getResultList();
            return resultList.isEmpty()?null:(CmtArchivosVtMgl)resultList.get(0);
        }catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
}
