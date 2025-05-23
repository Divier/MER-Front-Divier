/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaHomologacionRazonesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HomologacionRazonesMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class HomologacionRazonesMglDaoImpl extends GenericDaoImpl<HomologacionRazonesMgl> {

    private static final Logger LOGGER = LogManager.getLogger(HomologacionRazonesMglDaoImpl.class);

    /**
     * Autor: victor bocanegra Metodo para consultar todas las homologaciones
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<HomologacionRazonesMgl> findAll() throws ApplicationException {
        List<HomologacionRazonesMgl> homologacionRazonesMglResult = new ArrayList();

        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT a FROM HomologacionRazonesMgl a WHERE a.estadoRegistro=1 "
                    + "ORDER BY a.homologacionRazonesId ASC  ");

            Query query = entityManager.createQuery(sql.toString());

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return homologacionRazonesMglResult = query.getResultList();

        } catch (NoResultException ex) {
            return null;
        }
    }
    
    /**
     * Bocanegra vm metodo para buscar una  homologacion en BD por codigo
     * razon OFSCS
     *
     * @param razonOFSC
     * @return HomologacionRazonesMgl
     */
    public HomologacionRazonesMgl findHomologacionByCodigoOFSC(String razonOFSC)
            throws ApplicationException {

        HomologacionRazonesMgl result;
         
        try{
            
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM HomologacionRazonesMgl a WHERE a.estadoRegistro = 1  "
                + " AND a.codRazonOfscMer = :codRazonOfscMer");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codRazonOfscMer", razonOFSC);

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = (HomologacionRazonesMgl) query.getSingleResult();
        
        }catch(NoResultException ex){
            result= null;
        }
        return result;
    }
    
     /**
     * Bocanegra vm metodo para buscar una  
     * homologacion en BD por codigo ONIX
     *
     * @param codigoOnix
     * @return HomologacionRazonesMgl
     */
    public HomologacionRazonesMgl findHomologacionByCodigoONIX(String codigoOnix)
            throws ApplicationException {

        HomologacionRazonesMgl result;
        
        try{
            
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM HomologacionRazonesMgl a WHERE a.estadoRegistro = 1  "
                + " AND a.codResOnix = :codResOnix");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codResOnix", codigoOnix);

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = (HomologacionRazonesMgl) query.getSingleResult();
        
        }catch(NoResultException ex){
            result= null;
        }
        return result;
    }
    
     /**
     * Bocanegra vm metodo para buscar una  homologacion en BD por codigo
     * razon OFSCS y id homologacion
     *
     * @param razonOFSC
     * @param idHomologacion
     * @return HomologacionRazonesMgl
     */
    public HomologacionRazonesMgl findHomologacionByCodigoOFSCAndId(String razonOFSC, BigDecimal idHomologacion)
            throws ApplicationException {

        HomologacionRazonesMgl result;
        
        try{
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM HomologacionRazonesMgl a WHERE a.estadoRegistro = 1  "
                + " AND a.codRazonOfscMer = :codRazonOfscMer  "
                + " AND  a.homologacionRazonesId != :homologacionRazonesId");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codRazonOfscMer", razonOFSC);
        query.setParameter("homologacionRazonesId", idHomologacion);

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = (HomologacionRazonesMgl) query.getSingleResult();
        
        }catch(NoResultException ex){
             result= null;
        }
        return result;
    }
   
    /**
     * Bocanegra vm metodo para buscar una homologacion 
     * en BD por codigo ONIX y id Homologacion
     *
     * @param codigoOnix
     * @param idHomologacion
     * @return HomologacionRazonesMgl
     */
    public HomologacionRazonesMgl findHomologacionByCodigoONIXAndId(String codigoOnix, BigDecimal idHomologacion)
            throws ApplicationException {

        HomologacionRazonesMgl result;
        
        try{
            
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM HomologacionRazonesMgl a WHERE a.estadoRegistro = 1  "
                + " AND a.codResOnix = :codResOnix "
                + " AND a.homologacionRazonesId != :homologacionRazonesId ");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codResOnix", codigoOnix);
        query.setParameter("homologacionRazonesId", idHomologacion);

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        result = (HomologacionRazonesMgl) query.getSingleResult();
       
        }catch(NoResultException ex){
             result= null;
        }  
        return result;
    }
    
    /**
     * Autor Jeniffer Corredor
     * Método para consultar las Homologaciones Razones según filtros de consulta
     * 
     * @param filtro
     * @return
     * @throws ApplicationException 
     */    
    public List<HomologacionRazonesMgl> findHomologacionesByFiltro(FiltroConsultaHomologacionRazonesDto filtro)
            throws ApplicationException {
        try {
            String queryStr = "SELECT a FROM HomologacionRazonesMgl a WHERE a.estadoRegistro = :estadoRegistro ";             
                    
            if (filtro.getIdHomologacionSeleccionado() != 0){
                queryStr += " AND a.homologacionRazonesId = :homologacionRazonesId ";
            }
            
            if (filtro.getCodOFSCSeleccionado() != null && !filtro.getCodOFSCSeleccionado().isEmpty()) {
               queryStr += " AND UPPER(a.codRazonOfscMer) LIKE :codRazonOfscMer ";
            }
            
            if (filtro.getDescOFSCSeleccionado() != null && !filtro.getDescOFSCSeleccionado().isEmpty()) {
               queryStr += " AND UPPER(a.descripcionOfscMer) LIKE :descripcionOfscMer ";
            }
            
            if (filtro.getCodONYXSeleccionado() != null && !filtro.getCodONYXSeleccionado().isEmpty()) {
               queryStr += " AND UPPER(a.codResOnix) LIKE :codResOnix ";
            }
            
            if (filtro.getDescONYXSeleccionado() != null && !filtro.getDescONYXSeleccionado().isEmpty()) {
               queryStr += " AND UPPER(a.descripcionOnyx) LIKE :descripcionOnyx ";
            }            
                        
            queryStr += " ORDER BY a.homologacionRazonesId ASC ";

            Query query = entityManager.createQuery(queryStr);            
            query.setParameter("estadoRegistro", 1);
            
            if (filtro.getIdHomologacionSeleccionado()!= 0) {
                query.setParameter("homologacionRazonesId", filtro.getIdHomologacionSeleccionado());
            }
                        
            if (filtro.getCodOFSCSeleccionado() != null && !filtro.getCodOFSCSeleccionado().isEmpty()) {
                query.setParameter("codRazonOfscMer", "%"+filtro.getCodOFSCSeleccionado().trim().toUpperCase()+"%");
            }
            
            if (filtro.getDescOFSCSeleccionado() != null && !filtro.getDescOFSCSeleccionado().isEmpty()) {
                query.setParameter("descripcionOfscMer", "%"+filtro.getDescOFSCSeleccionado().trim().toUpperCase()+"%");
            }
            
            if (filtro.getCodONYXSeleccionado() != null && !filtro.getCodONYXSeleccionado().isEmpty()) {
                query.setParameter("codResOnix",  "%"+filtro.getCodONYXSeleccionado().trim().toUpperCase()+"%");
            } 
            
            if (filtro.getDescONYXSeleccionado() != null && !filtro.getDescONYXSeleccionado().isEmpty()) {
                query.setParameter("descripcionOnyx", "%"+filtro.getDescONYXSeleccionado().trim().toUpperCase()+"%");
            }           
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HomologacionRazonesMgl> result = query.getResultList();
            getEntityManager().clear();
            return result;
                       
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }        
    }

}
