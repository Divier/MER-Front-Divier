package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.EstadosOtCmDirDto;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author User
 */
public class CmtBasicaMglDaoImpl extends GenericDaoImpl<CmtBasicaMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtBasicaMglDaoImpl.class);

    public List<CmtBasicaMgl> findAll() throws ApplicationException {
        TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findAll",
                CmtBasicaMgl.class);
        List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }

    public List<CmtBasicaMgl> findAllConEliminados() throws ApplicationException {
        Query query = entityManager.createNamedQuery("CmtBasicaMgl.findAllConEliminados");
         List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }

    public List<CmtBasicaMgl> findByTipoBasica(CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException {
        TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByTipoBasica",
                CmtBasicaMgl.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        query.setParameter("tipoBasicaObj", tipoBasicaId);
        List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }
    
    public List<CmtBasicaMgl> findByTipoBasicaEstadosCombinados(CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException {
        TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByTipoBasicaByOrderRR",
                CmtBasicaMgl.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        query.setParameter("tipoBasicaObj", tipoBasicaId);
        List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }

    public List<CmtBasicaMgl> findByTipoBasicaEstado1(CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException {
        TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByTipoBasicaEstado1",
                CmtBasicaMgl.class);
        query.setParameter("tipoBasicaObj", tipoBasicaId);
         List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }

    public CmtBasicaMgl findByTipoBasicaAndAbreviatura(CmtBasicaMgl basica) throws ApplicationException {
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByTipoBasicaAndAbreviatura",
                    CmtBasicaMgl.class);
            query.setParameter("tipoBasicaid", basica.getTipoBasicaObj().getTipoBasicaId());
            query.setParameter("abreviatura", basica.getAbreviatura());
            return query.getSingleResult();
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Listar los tipos de basica. Lista los diferentes tipos por el nombre.
     *
     * @author Antonio Gil.
     * @version 1.0 revision 17/05/2017.
     * @param tipoBasicaName Nombre del tipo de basica.
     * @return Lista de basica.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public List<CmtBasicaMgl> findTipoBasicaName(String tipoBasicaName) throws ApplicationException {
        String queryTipo = "SELECT c FROM CmtBasicaMgl c WHERE EXISTS("
                + "SELECT u FROM CmtTipoBasicaMgl u "
                + "where u.tipoBasicaId = c.tipoBasicaId  "
                + "and u.nombreTipo = :nameTipo) ";
        TypedQuery<CmtBasicaMgl> query = entityManager.createQuery(queryTipo, CmtBasicaMgl.class);
        query.setParameter("nameTipo", tipoBasicaName.toUpperCase().trim());
         List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }

    public List<CmtBasicaMgl> findByBasicaCode(CmtBasicaMgl basica) throws ApplicationException {
        TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByBasicaCode",
                CmtBasicaMgl.class);
        query.setParameter("codigoBasica", basica.getCodigoBasica());
        query.setParameter("tipoBasicaId", basica.getTipoBasicaObj().getTipoBasicaId());
         List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }

    public List<CmtBasicaMgl> findByFiltro(FiltroConjsultaBasicasDto consulta) throws ApplicationException {
        if (consulta.getIdTipoBasica() == null || consulta.getIdTipoBasica().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("el filtro de basica debe tener un valor para TipoBasica");
        }
        String queryTipo = "SELECT c FROM CmtBasicaMgl c WHERE c.estadoRegistro=1 ";
        queryTipo += "AND c.tipoBasicaObj.tipoBasicaId = :tipoBasicaId ";

        if (consulta.getCodigo() != null && !consulta.getCodigo().isEmpty()) {
            queryTipo += "AND UPPER(c.codigoBasica) LIKE :codigoBasica ";
        }
        if (consulta.getNombre() != null && !consulta.getNombre().isEmpty()) {
            queryTipo += "AND UPPER(c.nombreBasica) LIKE :nombreBasica ";
        }
        if (consulta.getAbreviatura() != null && !consulta.getAbreviatura().isEmpty()) {
            queryTipo += "AND UPPER(c.abreviatura) LIKE :abreviatura ";
        }
        if (consulta.getDecripcion() != null && !consulta.getDecripcion().isEmpty()) {
            queryTipo += "AND UPPER(c.descripcion) LIKE :descripcion ";
        }
        if (consulta.getEstado() != null && !consulta.getEstado().isEmpty()) {
            queryTipo += "AND c.activado = :activado ";
        }
        
        CmtTipoBasicaMglManager manager = new CmtTipoBasicaMglManager();
        
        CmtTipoBasicaMgl tipoBasicaMgl = manager.findByTipoBasicaId(consulta.getIdTipoBasica());
        
        if (tipoBasicaMgl != null) {
            if (tipoBasicaMgl.getIdentificadorInternoApp()
                    .equals(Constant.TIPO_BASICA_TECNOLOGIA)) {
                
               queryTipo+="ORDER BY c.abreviatura ASC "; 
            }else{
               queryTipo+="ORDER BY c.nombreBasica ASC ";
            }
        }

        TypedQuery<CmtBasicaMgl> query = entityManager.createQuery(queryTipo, CmtBasicaMgl.class);
        query.setParameter("tipoBasicaId", consulta.getIdTipoBasica());

        if (consulta.getCodigo() != null && !consulta.getCodigo().isEmpty()) {
            query.setParameter("codigoBasica", consulta.getCodigo().toUpperCase() + "%");
        }
        if (consulta.getNombre() != null && !consulta.getNombre().isEmpty()) {
            query.setParameter("nombreBasica", "%" + consulta.getNombre().toUpperCase() + "%");
        }
        if (consulta.getAbreviatura() != null && !consulta.getAbreviatura().isEmpty()) {
            query.setParameter("abreviatura", consulta.getAbreviatura().toUpperCase() + "%");
        }
        if (consulta.getDecripcion() != null && !consulta.getDecripcion().isEmpty()) {
            query.setParameter("descripcion", "%" + consulta.getDecripcion().toUpperCase() + "%");
        }
        if (consulta.getEstado() != null && !consulta.getEstado().isEmpty()) {
            query.setParameter("activado", consulta.getEstado());
        }
        List<CmtBasicaMgl> resultList = query.getResultList();

        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }         
        getEntityManager().clear();     
        return resultList;
    }

    public String buscarUltimoCodigoNumerico(CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException {
        if (!tipoBasicaId.getTipoDatoCodigo().equalsIgnoreCase("N")) {
            return "AAA";
        }

        List<CmtBasicaMgl> listCmtBasicaMgl = findByTipoBasicaEstado1(tipoBasicaId);
        if (listCmtBasicaMgl != null && !listCmtBasicaMgl.isEmpty()) {
            Collections.sort(listCmtBasicaMgl, (CmtBasicaMgl f1, CmtBasicaMgl f2) -> ("0000" + f1.getCodigoBasica()).
                    substring(("0000" + f1.getCodigoBasica()).length() - 4).
                    compareTo(("0000" + f2.getCodigoBasica()).
                            substring(("0000" + f2.getCodigoBasica()).length() - 4)));
            
            getEntityManager().clear();       
            return listCmtBasicaMgl.get(listCmtBasicaMgl.size() - 1).getCodigoBasica();
        }
        return "";
    }

    public CmtBasicaMgl findByNombre(BigDecimal tipoBasica, String nombre) {
        try {
            Query query = entityManager.createNamedQuery("CmtBasicaMgl.findBynombre");
            query.setParameter("tipoBasica", tipoBasica);
            query.setParameter("nombre", nombre);
            return (CmtBasicaMgl) query.getSingleResult();
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    public CmtBasicaMgl findByTipoBasicaAndCodigo(BigDecimal tipoBasica, String codigoBasica) {
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByTipoBasicaAndCodigo",
                    CmtBasicaMgl.class);
            query.setParameter("tipoBasica", tipoBasica);
            query.setParameter("codigoBasica", codigoBasica);
            return query.getSingleResult();
        } catch (NoResultException ne) {
            String errorMsg = ne.getMessage() + " - Tipo Basica: " + tipoBasica + " Codigo:" + codigoBasica;
            LOGGER.error(errorMsg);
            return null;
        }
    }

    /**
     * Buscar una instancia de basica. Se busca una instancia de basica teniendo
     * en cuenta el nombre de la basica y el nombre de tipo de basica.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param codigoBasica nombre de la basica que sa va a buscar.
     * @param identificadorInternoApp nombre del tipo de basica con la que se relaciona
     * la basica.
     * @return una instancia de basica si la encuentra o null en caso contrario.
     */
    public CmtBasicaMgl findBasica(String codigoBasica, String identificadorInternoApp) {
        if (codigoBasica == null || identificadorInternoApp == null) {
            return null;
        }
        try {
            codigoBasica = codigoBasica.toUpperCase();
            identificadorInternoApp = identificadorInternoApp.toUpperCase();
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findBynombreBasicaTipo",
                    CmtBasicaMgl.class);
            query.setParameter("identificadorInternoApp",identificadorInternoApp);
            query.setParameter("codigoBasica",codigoBasica);
            List<CmtBasicaMgl> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    public CmtBasicaMgl findBasica1(String nombreBasica, String nombreTipoBasica) {
        if (nombreBasica == null || nombreTipoBasica == null) {
            return null;
        }
        try {
            nombreBasica = nombreBasica.toUpperCase();
            nombreTipoBasica = nombreTipoBasica.toUpperCase();
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findBynombreBasicaTipo",
                    CmtBasicaMgl.class);
            query.setParameter("nombreTipoBasica", "%" + nombreTipoBasica + "%");
            query.setParameter("nombreBasica", nombreBasica);
            List<CmtBasicaMgl> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Buscar una basica. Busca una basica por nombre tipo basica y por su
     * codigo.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param codigoBasica Codigo a buscar en tipo basica.
     * @param nombreTipoBasica Nombre a buscar en tipo basica.
     * @return Los datos de la entidad basica.
     */
    public CmtBasicaMgl findBasicaCodigo(String codigoBasica, String nombreTipoBasica) {
        if (codigoBasica == null || nombreTipoBasica == null) {
            return null;
        }
        try {
            codigoBasica = codigoBasica.toUpperCase();
            nombreTipoBasica = nombreTipoBasica.toUpperCase();
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findBynombreBasicaTipoCode",
                    CmtBasicaMgl.class);
            query.setParameter("nombreTipoBasica", "%" + nombreTipoBasica + "%");
            query.setParameter("codigoBasica", codigoBasica);

            List<CmtBasicaMgl> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Buscar grupo proyecto. Busca un grupo de basica por grupo de proyecto.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param idGrupoProyectoBasica Codigo grupo tipo basica.
     * @return Los datos de la entidad basica.
     */
    public List<CmtBasicaMgl> findGrupoProyectoBasicaList(BigDecimal idGrupoProyectoBasica) {
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findGrupoProyectoBasicaList",
                    CmtBasicaMgl.class);
            query.setParameter("idGrupoProyectoBasica", idGrupoProyectoBasica);
            query.setParameter("estado", 1);
             List<CmtBasicaMgl> result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Buscar tipo de validacion proyecto.Busca el grupo de basica tipo
 validacion por proyecto.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param idProyectoBasica Codigo proyecto.
     * @return Los datos de la entidad basica.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtBasicaMgl> findProyectoBasicaList(BigDecimal idProyectoBasica) throws ApplicationException {
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findProyectoBasicaList",
                    CmtBasicaMgl.class);
            query.setParameter("idProyectoBasica", idProyectoBasica);
            query.setParameter("estado", 1);
            List<CmtBasicaMgl> result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Buscar una basica. Busca una basica por idBasica
     *
     * @author cardenaslb.
     * @version 1.0 revision 03/10/2017.
     * @param cmtBasicaMgl Codigo a buscar en tipo basica.
     * @return Los datos de la entidad basica.
     */
    public CmtBasicaMgl findByBasicaId(CmtBasicaMgl cmtBasicaMgl) {
        if (cmtBasicaMgl == null) {
            return null;
        }
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByBasicaId",
                    CmtBasicaMgl.class);
            query.setParameter("codigoBasica", cmtBasicaMgl.getBasicaId());
            List<CmtBasicaMgl> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
        
    /**
     * Busca el codigo basica por identificador interno de la app
     *
     * @author Lenis Cardenas
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return CmtBasicaMgl  basica encontrado
     * @throws ApplicationException
     */
        
    public CmtBasicaMgl findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        CmtBasicaMgl result;
        try{
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByCodigoInternoApp",
                    CmtBasicaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigoInternoApp", codigoInternoApp);
            result = query.getSingleResult();
            return result;
        } catch(NoResultException ex) {
            String msg = "No se encontraron registros para el codigoApp de básica '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para el codigoApp de básica '" + codigoInternoApp + "'.", ex);
        } catch (NonUniqueResultException ex) {
            String msg = "La consulta para el código interno de básica '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("La consulta para el código interno de básica '"+ codigoInternoApp + "' arrojó más de un resultado.", ex);
        }
    }
    
    /**
     * valbuenayf metodo para listar CmtBasicaMgl por identificador interno de
     * la app
     *
     * @param codigoInternoApp
     * @return
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findListCmtBasicaMglByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        List<CmtBasicaMgl> result;
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByCodigoInternoApp",
                    CmtBasicaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigoInternoApp", codigoInternoApp);
            result = query.getResultList();
        } catch (Exception ex) {
            String msg = "No se encontraron registros para el codigoApp del tipo basica '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para el codigoApp del tipo basica");
        }
       
        getEntityManager().clear();        
        return result;
    }
    
    public List<CmtBasicaMgl> findByNombreBasica(String nombreBasica) throws ApplicationException{
        List<CmtBasicaMgl> result;
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByNombreLike",
                    CmtBasicaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("nombreBasica", "%" + nombreBasica + "%");
            result = query.getResultList();
        } catch (Exception ex) {
            String msg = "No se encontraron registros para el codigoApp del tipo basica '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros");
        }
        return result;
    }
    
     /**
     * Busca Basica que contenga el prefijo en el nombre.
     *
     * @author Juan David Hernandez
     * @param prefijo que debe contener el registro 
     * @param tipoBasicaId 
     * @return List de CmtBasicaMgl encontrados
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByPreFijo(String prefijo, BigDecimal tipoBasicaId) throws ApplicationException {
        List<CmtBasicaMgl> result = null;
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByPrefijo",
                    CmtBasicaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("prefijo", "%" + prefijo + "%");
            query.setParameter("tipoBasicaId", tipoBasicaId);
            result = query.getResultList();           
            getEntityManager().clear();           
            return result;
        } catch (Exception ex) {
            String msg = "No se encontraron registros  '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return result;
        }
    }
    
   
     public List<CmtBasicaMgl> findEstadoResultadoOTRR (List<String> tipoBasicaExt ,CmtTipoBasicaMgl tipoBasica)throws ApplicationException{
         List<CmtBasicaMgl> listadoResultado = new ArrayList<>();
         try {
             TypedQuery<CmtBasicaMgl> query =  entityManager.createQuery("SELECT b from CmtBasicaMgl b WHERE b.tipoBasicaObj = :idTipoBasica AND b.basicaId IN (SELECT e.idBasicaObj.basicaId FROM CmtBasicaExtMgl e WHERE e.idTipoBasicaExt.labelCampo IN :tipos AND e.valorExtendido = :valor) AND b.estadoRegistro=1 AND b.activado='Y'", CmtBasicaMgl.class);
             query.setParameter("idTipoBasica", tipoBasica);
             query.setParameter("tipos", tipoBasicaExt);
             query.setParameter("valor", "Y");
             listadoResultado = query.getResultList();
             getEntityManager().clear();           
             return listadoResultado;
         } catch (Exception ex) {
             String msg = "No se encontraron registros  '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
             LOGGER.error(msg);
             return listadoResultado;
         }
     }
     
     public List<CmtBasicaMgl> findByCodigoBasicaList(List <String> codigoBasicaList, CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException {
        TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByCodigoBasicaList",
                CmtBasicaMgl.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        query.setParameter("tipoBasicaObj", tipoBasicaId);
        query.setParameter("codigoBasicaList", codigoBasicaList);
         List<CmtBasicaMgl> result = query.getResultList();
         getEntityManager().clear();
         return result;
    }
     
     
        public List<CmtBasicaMgl> findByAllFields(HashMap<String, Object> params) throws ApplicationException {
                  String queryTipo = "SELECT n FROM CmtBasicaMgl n WHERE n.estadoRegistro = 1  ";

           if (params.get("nombreBasica") != null) {
                queryTipo += "AND UPPER(n.nombreBasica) = :nombreBasica ";
            }
            if (params.get("tipoBasicaId") != null) {
                queryTipo += "AND n.tipoBasicaObj.tipoBasicaId= :tipoBasicaId ";
            }
             if (params.get("activado") != null) {
                queryTipo += "AND n.activado= :activado ";
            }

            TypedQuery<CmtBasicaMgl> q = entityManager.createQuery(queryTipo, CmtBasicaMgl.class);

            if (params.get("nombreBasica") != null && !params.get("nombreBasica").toString().trim().isEmpty()) {
                q.setParameter("nombreBasica",  params.get("nombreBasica").toString().toUpperCase().trim());
            }
            if (params.get("tipoBasicaId") != null && !params.get("tipoBasicaId").toString().trim().isEmpty()) {
                q.setParameter("tipoBasicaId", new BigDecimal(params.get("tipoBasicaId").toString()));
            }
            if (params.get("activado") != null) {
                q.setParameter("activado", params.get("activado"));
            }

            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<CmtBasicaMgl> result = q.getResultList();
            getEntityManager().clear();
            return result;
        }
        
    public List<CmtBasicaMgl> findByTipoBasicaTecno(CmtBasicaMgl basicaTecnologia)
            throws ApplicationException {
          List<CmtBasicaMgl> listadoResultado = new ArrayList<>();
               try {
             TypedQuery<CmtBasicaMgl> query =  entityManager.createQuery("SELECT DISTINCT(b.basicaIdTipoOt) from CmtTipoOtMgl b "
                     + "  where b.estadoRegistro=1 AND b.tipoFlujoOt.basicaId in "
                     + "( SELECT  t.tipoFlujoOtObj.basicaId from CmtEstadoxFlujoMgl t "
                     + "WHERE  t.estadoRegistro=1 and  t.basicaTecnologia.basicaId = :basicaTecnologia ) ", CmtBasicaMgl.class);
             query.setParameter("basicaTecnologia", basicaTecnologia.getBasicaId());
             listadoResultado = query.getResultList();            
             getEntityManager().clear();            
             return listadoResultado;
         } catch (Exception ex) {
             String msg = "No se encontraron registros  '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
             LOGGER.error(msg);
             return listadoResultado;
         }
    }  

    public List<CmtBasicaMgl> findTecnolosgias()
            throws ApplicationException {
        List<CmtBasicaMgl> listadoResultado = new ArrayList<>();
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createQuery("SELECT b from CmtBasicaMgl b WHERE b.tipoBasicaObj.tipoBasicaId = :idTipoBasica "
                    + "AND b.basicaId IN (SELECT e.idBasicaObj.basicaId FROM CmtBasicaExtMgl e WHERE  "
                    + "e.valorExtendido = :valor  AND e.idTipoBasicaExt.idTipoBasicaExt = :idTipoBasicaExt )  and b.estadoRegistro=1 AND b.activado='Y'", CmtBasicaMgl.class);
            query.setParameter("idTipoBasicaExt", new BigDecimal(Constant.VALIDACION_COBERTURA));
            query.setParameter("idTipoBasica", new BigDecimal(Constant.TIPO_BASICA_ID));
            query.setParameter("valor", "Y");
            listadoResultado = query.getResultList();            
            getEntityManager().clear();          
            return listadoResultado;
        } catch (Exception ex) {
            String msg = "No se encontraron registros  '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return listadoResultado;
        }
    }

    public List<CmtBasicaMgl> findByTipoBasica(BigDecimal tipoBasicaId) throws ApplicationException {
        TypedQuery<CmtBasicaMgl> query = entityManager.createQuery("SELECT b from CmtBasicaMgl b WHERE b.tipoBasicaObj.tipoBasicaId = :idTipoBasica "
                + "and b.estadoRegistro=1 AND b.activado='Y'", CmtBasicaMgl.class);
        query.setParameter("idTipoBasica", tipoBasicaId);
        List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }
    
    public CmtBasicaMgl findByTipoBasicaAndNombre(CmtTipoBasicaMgl tipoBasicaMgl,
            String nombre) throws ApplicationException {
        CmtBasicaMgl resultado = null;
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createQuery(" SELECT c FROM CmtBasicaMgl c "
                    + "WHERE c.tipoBasicaObj = :tipoBasica and c.estadoRegistro=1 AND  "
                    + "c.nombreBasica LIKE :nombre", CmtBasicaMgl.class);

            query.setParameter("tipoBasica", tipoBasicaMgl);
            query.setParameter("nombre", "%" + nombre.trim().toUpperCase() + "%");
            List<CmtBasicaMgl> lista = query.getResultList();
            if (lista != null && !lista.isEmpty()) {
                resultado = lista.get(0);
            }
            return resultado;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    public List<CmtBasicaMgl> findLstByTipoBasicaAndNombre(CmtTipoBasicaMgl tipoBasicaMgl,
            String nombre) throws ApplicationException {
        List<CmtBasicaMgl> resultado;
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createQuery(" SELECT c FROM CmtBasicaMgl c "
                    + "WHERE c.tipoBasicaObj = :tipoBasica and c.estadoRegistro=1 AND  "
                    + "c.nombreBasica LIKE :nombre", CmtBasicaMgl.class);

            query.setParameter("tipoBasica", tipoBasicaMgl);
            query.setParameter("nombre", "%" + nombre.trim().toUpperCase() + "%");
            resultado = query.getResultList();
            return resultado;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    /**
     * Busca lista de Basicas por tipo basica y abreviatura
     *
     * @author Victor Bocanegra
     * @param tipoBasica
     * @param abreviatura
     * @return List de CmtBasicaMgl encontrados
     * @throws ApplicationException
     */
     public List<CmtBasicaMgl> findByTipoBAndAbreviatura(BigDecimal tipoBasica, String abreviatura)
            throws ApplicationException {
         List<CmtBasicaMgl> resulList;
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByTipoBasicaAndAbreviatura",
                    CmtBasicaMgl.class);
            query.setParameter("tipoBasicaid", tipoBasica);
            query.setParameter("abreviatura", abreviatura);
            resulList = query.getResultList();
            return resulList;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    public List<EstadosOtCmDirDto> findListEstadosCmDir(CmtTipoBasicaMgl tipoBasicaTipoOtCmId, CmtTipoBasicaMgl tipoBasicaTipoOtDirId)
            throws ApplicationException {
        List<EstadosOtCmDirDto> listEstadoOtCmDir = new ArrayList<>();
        try {
            List<CmtBasicaMgl> listEstadoOtCm = null;
            List<CmtBasicaMgl> listEstadoOtDir = null;
           
            TypedQuery<CmtBasicaMgl> query1 = entityManager.createNamedQuery("CmtBasicaMgl.findByTipoBasica",
                    CmtBasicaMgl.class);
            query1.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query1.setParameter("tipoBasicaObj", tipoBasicaTipoOtCmId);
            listEstadoOtCm = query1.getResultList();
            for (CmtBasicaMgl basicaestadoOt : listEstadoOtCm) {
                EstadosOtCmDirDto estadosOtCmDirDto = new EstadosOtCmDirDto();
                estadosOtCmDirDto.setIdEstados(basicaestadoOt.getBasicaId());
                estadosOtCmDirDto.setNombreEstado(basicaestadoOt.getNombreBasica());
                listEstadoOtCmDir.add(estadosOtCmDirDto);
            }

            TypedQuery<CmtBasicaMgl> query2 = entityManager.createNamedQuery("CmtBasicaMgl.findByTipoBasica",
                    CmtBasicaMgl.class);
            query2.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query2.setParameter("tipoBasicaObj", tipoBasicaTipoOtDirId);
            listEstadoOtDir = query2.getResultList();
            for (CmtBasicaMgl basicaestadoOt : listEstadoOtDir) {
                EstadosOtCmDirDto estadosOtCmDirDto = new EstadosOtCmDirDto();
                estadosOtCmDirDto.setIdEstados(basicaestadoOt.getBasicaId());
                estadosOtCmDirDto.setNombreEstado(basicaestadoOt.getNombreBasica());
                listEstadoOtCmDir.add(estadosOtCmDirDto);
            }

            return listEstadoOtCmDir;
        } catch (Exception ex) {
            String msg = "No se encontraron registros  '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            return listEstadoOtCmDir;
        }
    }
    
    
    public List<CmtBasicaMgl> findBasicaListByIdentificadorInternoApp (String identificadorInternoApp) throws ApplicationException {
        String queryTipo = "SELECT c FROM CmtBasicaMgl c WHERE c.identificadorInternoApp =:identificadorInternoApp AND c.estadoRegistro = 1 ";
        TypedQuery<CmtBasicaMgl> query = entityManager.createQuery(queryTipo, CmtBasicaMgl.class);
        query.setParameter("identificadorInternoApp", identificadorInternoApp);
         List<CmtBasicaMgl> result = query.getResultList();
        getEntityManager().clear();
        return result;
    }
    
    /**
     * Busca Basica por codigo de basica e identificadorInternoApp
     *
     * @author Miguel Barrios Hitss
     * @param identificadorInternoApp
     * @param codigoBasica
     * @return CmtBasicaMgl encontrada
    */
    public CmtBasicaMgl findByCodigoInternoAppAndCodigo(String identificadorInternoApp, String codigoBasica) {
        if (codigoBasica == null || identificadorInternoApp == null) {
            return null;
        }
        try {
            TypedQuery<CmtBasicaMgl> query = entityManager.createNamedQuery("CmtBasicaMgl.findByCodigoInternoAppAndCodigo",
                    CmtBasicaMgl.class);
            query.setParameter("codigoInternoApp", identificadorInternoApp);
            query.setParameter("codigoBasica", codigoBasica);

            List<CmtBasicaMgl> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

}
