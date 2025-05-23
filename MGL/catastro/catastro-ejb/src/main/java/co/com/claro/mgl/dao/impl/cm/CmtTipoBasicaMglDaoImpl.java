package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaTipoBasicaDto;
import co.com.claro.mgl.dtos.FiltroTablaTipoBasicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtTipoBasicaMglDaoImpl extends GenericDaoImpl<CmtTipoBasicaMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtCuentaMatrizMglDaoImpl.class);

    public List<CmtTipoBasicaMgl> findAll() throws ApplicationException {
        Query query = entityManager.createNamedQuery("CmtTipoBasicaMgl.findAll");
        return query.getResultList();
    }
    
    public CmtTipoBasicaMgl findByTipoBasicaId(BigDecimal id) throws ApplicationException{
        CmtTipoBasicaMgl result;
        try{
        Query query = entityManager.createNamedQuery("CmtTipoBasicaMgl.findByTipoBasicaId");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        query.setParameter("tipoBasicaId", id);
        result = (CmtTipoBasicaMgl) query.getSingleResult();
        return result;
        }catch(NoResultException ex){
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para el id " + id + " del tipo basica");
        }
    }

    /**
     * Busca un tipo de basica teniendo en cuentra el nombre del tipo basica.
     * @author becerraarmr
     * @param nombreTipoBasica nombre del tipo basica a buscar.
     * @return la instancia del tipo basica solicitada o null en caso que no se
     * encuentre.
     * @throws ApplicationException
     */
    @Override
    public CmtTipoBasicaMgl find(String nombreTipoBasica) 
            throws ApplicationException {
        try {
          if(entityManager==null)return null;
          
          if ( !entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            String name = "CmtTipoBasicaMgl.findNombre";
            Query query = entityManager.createNamedQuery(name);
            query.setParameter("nombreTipo",nombreTipoBasica);
            query.setMaxResults(1);
            List<CmtTipoBasicaMgl> list=query.getResultList();
            entityManager.getTransaction().commit();
            if(!list.isEmpty()){
              return list.get(0);
            }
        } catch (Exception e) {
            LOGGER.error("Problemas para buscar en CmtTipoBasicaMgl: ", e);
        }
        return null;
    }

    /**
     * Buscar tipo de basica por proyecto. Busca tipo de basica por proyecto.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param proyectoDetalleSelected Datos del detalle del proyecto
     * seleccionado.
     * @return Lista de las validaciones por proyecto.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public List<CmtTipoBasicaMgl> findTipoBasicasByProyecto(BigDecimal proyectoDetalleSelected)
            throws ApplicationException {
        CmtBasicaMgl proyectoMgl = new CmtBasicaMgl();
        proyectoMgl.setBasicaId(proyectoDetalleSelected);

        String sql = " SELECT DISTINCT(t) "
                + "FROM CmtValidacionProyectoMgl v "
                + "INNER JOIN v.tipoBasicaValidacionId b "
                + "INNER JOIN b.tipoBasicaObj t "
                + " WHERE "
                + "v.tipoBasicaProyectoId =:proyecto AND "
                + "v.estadoRegistro =:estado ";

        Query q = entityManager.createQuery(sql);
        q.setParameter("proyecto", proyectoMgl);
        q.setParameter("estado", 1);
        return q.getResultList();
    }


    public FiltroConsultaTipoBasicaDto findTablasTipoBasicaSearch(FiltroTablaTipoBasicaDto filtro, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroConsultaTipoBasicaDto filtroConsultaTipoBasicaDto = new FiltroConsultaTipoBasicaDto();
        if (contar) {
            filtroConsultaTipoBasicaDto.setNumRegistros(findTablasTipoBasicasSearchContar(filtro));
        } else {
            filtroConsultaTipoBasicaDto.setListaTablasTipoBasica(findablasTipoBasicasSearchDatos(filtro, firstResult, maxResults));
        }
        return filtroConsultaTipoBasicaDto;
    }

    public long findTablasTipoBasicasSearchContar(FiltroTablaTipoBasicaDto filtro) throws ApplicationException {
        try {
            String queryStr = "SELECT COUNT(DISTINCT c) FROM CmtTipoBasicaMgl c WHERE 1=1 ";

            if (filtro.getTipoBasicaId() != null
                    && !filtro.getTipoBasicaId().equals(BigDecimal.ZERO)) {
                queryStr += " AND c.tipoBasicaId = :tipoBasicaId ";
            }

            if (filtro.getCodigoTipo() != null && !filtro.getCodigoTipo().isEmpty()) {
                queryStr += " AND UPPER(c.codigoTipo) LIKE :codigoTipo ";
            }

            if (filtro.getNombreTipo() != null && !filtro.getNombreTipo().isEmpty()) {
                queryStr += " AND UPPER(c.nombreTipo) LIKE :nombreTipo ";
            }

            if (filtro.getDescripcion() != null && !filtro.getDescripcion().isEmpty()) {
                queryStr += " AND UPPER(c.descripcionTipo) LIKE :descripcionTipo ";
            }

            if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
                queryStr += "AND c.estadoRegistro = :estadoRegistro ";
            }

            queryStr += "ORDER BY  c.fechaCreacion  desc";

            Query query = entityManager.createQuery(queryStr);

            if (filtro.getTipoBasicaId() != null
                    && !filtro.getTipoBasicaId().equals(BigDecimal.ZERO)) {
                query.setParameter("tipoBasicaId", filtro.getTipoBasicaId());
            }

            if (filtro.getCodigoTipo() != null && !filtro.getCodigoTipo().isEmpty()) {
                query.setParameter("codigoTipo", "%" + filtro.getCodigoTipo().trim().toUpperCase() + "%");
            }

            if (filtro.getNombreTipo() != null && !filtro.getNombreTipo().isEmpty()) {
                query.setParameter("nombreTipo", "%" + filtro.getNombreTipo().trim().toUpperCase() + "%");
            }

            if (filtro.getDescripcion() != null && !filtro.getDescripcion().isEmpty()) {
                query.setParameter("descripcionTipo", "%" + filtro.getDescripcion().trim().toUpperCase() + "%");
            }

            if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
                String est = filtro.getEstado().trim().toUpperCase();
                int e;
                if (est.equalsIgnoreCase("ACTIVADO")) {
                    e = 1;
                } else if (est.equalsIgnoreCase("DESACTIVADO")) {
                    e = 0;
                } else {
                    e = 3;
                }
                query.setParameter("estadoRegistro", e);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (Long) query.getSingleResult();
        } catch (NoResultException nre) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + nre.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }

    public List<CmtTipoBasicaMgl> findablasTipoBasicasSearchDatos(FiltroTablaTipoBasicaDto filtro,
            int firstResult, int maxResults) throws ApplicationException {
        try {
            String queryStr = "SELECT DISTINCT c FROM CmtTipoBasicaMgl c WHERE 1=1 ";

            if (filtro.getTipoBasicaId() != null
                    && !filtro.getTipoBasicaId().equals(BigDecimal.ZERO)) {
                queryStr += " AND c.tipoBasicaId = :tipoBasicaId ";
            }

            if (filtro.getCodigoTipo() != null && !filtro.getCodigoTipo().isEmpty()) {
                queryStr += " AND UPPER(c.codigoTipo) LIKE :codigoTipo ";
            }

            if (filtro.getNombreTipo() != null && !filtro.getNombreTipo().isEmpty()) {
                queryStr += " AND UPPER(c.nombreTipo) LIKE :nombreTipo ";
            }

            if (filtro.getDescripcion() != null && !filtro.getDescripcion().isEmpty()) {
                queryStr += " AND UPPER(c.descripcionTipo) LIKE :descripcionTipo ";
            }

            if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
                queryStr += "AND c.estadoRegistro = :estadoRegistro ";
            }
            
            queryStr += "ORDER BY  c.fechaCreacion  desc";

            Query query = entityManager.createQuery(queryStr);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            if (filtro.getTipoBasicaId() != null
                    && !filtro.getTipoBasicaId().equals(BigDecimal.ZERO)) {
                query.setParameter("tipoBasicaId", filtro.getTipoBasicaId());
            }

            if (filtro.getCodigoTipo() != null && !filtro.getCodigoTipo().isEmpty()) {
                query.setParameter("codigoTipo", "%" + filtro.getCodigoTipo().trim().toUpperCase() + "%");
            }

            if (filtro.getNombreTipo() != null && !filtro.getNombreTipo().isEmpty()) {
                query.setParameter("nombreTipo", "%" + filtro.getNombreTipo().trim().toUpperCase() + "%");
            }

            if (filtro.getDescripcion() != null && !filtro.getDescripcion().isEmpty()) {
                query.setParameter("descripcionTipo", "%" + filtro.getDescripcion().trim().toUpperCase() + "%");
            }

            if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
                String est = filtro.getEstado().trim().toUpperCase();
                int e;
                if (est.equalsIgnoreCase("ACTIVADO")) {
                    e = 1;
                } else if (est.equalsIgnoreCase("DESACTIVADO")) {
                    e = 0;
                } else {
                    e = 3;
                }
                query.setParameter("estadoRegistro", e);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } catch (NoResultException nre) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + nre.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }

    /**
     * M&eacute;todo para consultar los tipos para eliminaci&oacute;n de CM
     * 
     * @return {@link List}&lt;{@link CmtTipoBasicaMgl}> listado de tipos encontrados
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtTipoBasicaMgl> encontrarTiposEliminacionCM() throws ApplicationException {
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT tipo")
                    .append(" FROM CmtTipoBasicaMgl tipo")
                    .append(" WHERE tipo.identificadorInternoApp IN :tipos");
            List<String> tipos = new ArrayList<>();
            tipos.add(Constant.TIPO_BASICA_ELIMINAR_CM);
            tipos.add(Constant.TIPO_BASICA_ESCALAR_ACOMETIDAS);
            tipos.add(Constant.TIPO_BASICA_ESCALAR_HHPP);
            tipos.add(Constant.TIPO_BASICA_RECHAZAR_ELIMINACION_CM);
            return entityManager.createQuery(consulta.toString())
                    .setParameter("tipos", tipos)
                    .getResultList();
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando los tipos para eliminacion de CM");
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
        /**
     * Busca lista de CmtTipoBasicaMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param complemento
     * @return List<CmtTipoBasicaMgl> encontrada en el repositorio
     * @throws ApplicationException
     * 
     **/
    
    
        public List<CmtTipoBasicaMgl> findByComplemento(String complemento) throws ApplicationException {
        
         List<CmtTipoBasicaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTipoBasicaMgl.findByComplemento");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (!complemento.equals("")|| !complemento.isEmpty()) {
                query.setParameter("complemento", complemento);
            }
        resultList = (List<CmtTipoBasicaMgl>) query.getResultList();
        return resultList;
    }
        
    /**
     * Busca toda la lista de CmtTipoBasicaMgl en el repositorio
     * sin las que tiene informacion adicional
     *
     * @author Victor Bocanegra
     * @param complemento 
     * @return List<CmtTipoBasicaMgl> encontrada en el repositorio
     * @throws ApplicationException
     * 
     **/
    
    
        public List<CmtTipoBasicaMgl> findAllSinInfoAdi(String complemento) throws ApplicationException {
        
        List<CmtTipoBasicaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTipoBasicaMgl.findAllSinInfoAdi");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (!complemento.equals("")|| !complemento.isEmpty()) {
                query.setParameter("complemento", complemento);
            }
        resultList = (List<CmtTipoBasicaMgl>) query.getResultList();
        return resultList;
    }
        
    /**
     * Busca el tipo basica por identificador interno de la app
     *
     * @author Carlos Villamil Hitss
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return CmtTipoBasicaMgl tipo basica encontrado
     * @throws ApplicationException
     */
        
    public CmtTipoBasicaMgl findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        CmtTipoBasicaMgl result;
        try{
            Query query = entityManager.createNamedQuery("CmtTipoBasicaMgl.findByCodigoInternoApp");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigoInternoApp", codigoInternoApp);
            result = (CmtTipoBasicaMgl) query.getSingleResult();
            return result;
        } catch(NoResultException ex){
            String msg = "No se encontraron registros para el codigoApp " + codigoInternoApp + " del tipo basica.";
            LOGGER.error(msg, ex);
            throw new ApplicationException(msg, ex);
        } catch (NonUniqueResultException ex) {
            String msg = "Se encontró más de un resultado para la consulta.";
            LOGGER.error(msg, ex);
            throw new ApplicationException(msg, ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de realizar la consulta: " +  ex.getMessage();
            LOGGER.error(msg, ex);
            throw new ApplicationException(msg, ex);
        }
    }
    
     /**
     * Busca el maximo codigo
     *
     * @author Victor Bocanegra
     * @return String
     * @throws ApplicationException
     */
        
    public String selectMaxCodigo() throws ApplicationException {
        String  result;
        try{
            Query query = entityManager.createNamedQuery("CmtTipoBasicaMgl.selectMaxCodigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result =  (String) query.getSingleResult();
            if(result != null){
                int valor= Integer.parseInt(result);
                valor++;
                result=String.valueOf(valor);
            }
            return result;
        }catch(NoResultException ex){
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros:"+ex.getMessage()+"");
        }
    }
    
          public List<CmtTipoBasicaMgl> findByAllFields(HashMap<String, Object> params) throws ApplicationException {
        try {
            String queryTipo = "SELECT n FROM CmtTipoBasicaMgl n WHERE n.estadoRegistro = :estadoRegistro  ";

           
            if (params.get("nombreTipo") != null) {
                queryTipo += "AND n.nombreTipo= :nombreTipo ";
            }
       
            Query q = entityManager.createQuery(queryTipo);

           
            if (params.get("nombreTipo") != null && !params.get("nombreTipo").toString().trim().isEmpty()) {
                q.setParameter("nombreTipo", params.get("nombreTipo"));
            }
            
            if (params.get("estadoRegistro") != null) {
                q.setParameter("estadoRegistro", params.get("estadoRegistro"));
            }
         
   
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();

        } catch (NumberFormatException e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }
        
}
