/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.FiltroMarcasMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
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
 * @author Admin
 */
public class MarcasHhppMglDaoImpl extends GenericDaoImpl<MarcasHhppMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(MarcasHhppMglDaoImpl.class);

    public List<MarcasHhppMgl> findAll() throws ApplicationException {
        List<MarcasHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("MarcasHhppMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<MarcasHhppMgl>) query.getResultList();
        return resultList;
    }

    public List<MarcasHhppMgl> findByIdDireccionMgl(String blackLisId) throws ApplicationException {
        List<MarcasHhppMgl> resultList;
        Query query = entityManager.createNamedQuery("MarcasHhppMgl.findByIdMarcasHhppMgl");
        query.setParameter("marId", blackLisId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<MarcasHhppMgl>) query.getResultList();
        return resultList;
    }

    public List<MarcasHhppMgl> findMarcasHhppByHhppMgl(List<HhppMgl> HhppMglList, BigDecimal blackLis) throws ApplicationException {
        try {
            List<MarcasHhppMgl> resultList;

            List<BigDecimal> hhppList = null;
            if (HhppMglList != null && HhppMglList.size() > 0) {
                hhppList = new ArrayList<BigDecimal>();
                for (HhppMgl h : HhppMglList) {
                    hhppList.add(h.getHhpId());
                }
            }

            Query query = entityManager.createQuery("SELECT m FROM MarcasHhppMgl m WHERE m.hhpId IN :hhppList AND m.marId = :marId ");
            query.setParameter("hhppList", hhppList);
            query.setParameter("marId", blackLis);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<MarcasHhppMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    public List<MarcasHhppMgl> findMarcasHhppByHhppAndMarca(HhppMgl hhppMgl, MarcasMgl marcasMgl) throws ApplicationException{
        try {
            List<MarcasHhppMgl> resultList;

            Query query = entityManager.createQuery("SELECT m FROM MarcasHhppMgl m WHERE m.hhpp.hhpId = :hhppMglId AND m.marId.marId = :marId ");
            query.setParameter("hhppMglId", hhppMgl.getHhpId());
            query.setParameter("marId", marcasMgl.getMarId());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList =  query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<MarcasHhppMgl> findByMarcaHhppMgl(HhppMgl hhppMgl, MarcasMgl blackLis) throws ApplicationException {
        try {
            List<MarcasHhppMgl> resultList;

            Query query = entityManager.createQuery("SELECT m FROM MarcasHhppMgl m WHERE m.hhpp = :hhpp AND m.marId = :marId ");
            query.setParameter("hhpp", hhppMgl);
            query.setParameter("marId", blackLis);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<MarcasHhppMgl>) query.getResultList();
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
     * valbuenayf Metodo para buscar las marcar de un hhpp
     * @param idHhpp
     * @return
     * @throws ApplicationException 
     */
    public List<MarcasHhppMgl> findMarcasHhppMglidHhpp(BigDecimal idHhpp) throws ApplicationException {
        List<MarcasHhppMgl> resultList;
        try {
            Query query = entityManager.createNamedQuery("MarcasHhppMgl.findByMarcasHhppMglIdHhpp");
            query.setParameter("hhpId", idHhpp);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<MarcasHhppMgl>) query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
        return resultList;
    }
      /**
     * espinosadiea Metodo para buscar las marcas de un hhpp sin importar estado del registro
     * @param idHhpp
     * @return
     * @throws ApplicationException 
     */
    public List<MarcasHhppMgl> findMarcasHhppMglidHhppSinEstado(List<HhppMgl> idHhpp) throws ApplicationException {
        List<MarcasHhppMgl> resultList;
        try {
            Query query = entityManager.createNamedQuery("MarcasHhppMgl.findByMarcasHhppMglIdHhppSinEstado");
            List<BigDecimal> idHomePass = new ArrayList<BigDecimal>();
            for ( HhppMgl temporal: idHhpp ){
                idHomePass.add(temporal.getHhpId());
            }
            if (idHomePass.size()==0){
                return null;
            }
            query.setParameter("hhpId", idHomePass);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<MarcasHhppMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public List<MarcasHhppMgl> buscarXHhppMarca( BigDecimal homePassId, BigDecimal marcaId) throws ApplicationException {
        List<MarcasHhppMgl> resultList;
        try {
            Query query = entityManager.createNamedQuery("MarcasHhppMgl.buscarMarcasHhppMglIdHhpp");
            query.setParameter("hhpId", homePassId);
            query.setParameter("marid", marcaId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<MarcasHhppMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    public List<MarcasHhppMgl> findResultadosFiltro(FiltroMarcasMglDto filtroMarcasDto) {
        try {
            List<MarcasHhppMgl> resultList = new ArrayList();
            StringBuilder queryStr = new StringBuilder();
            queryStr.append("SELECT m FROM MarcasHhppMgl m where m.hhpp.hhpId IN :hhppIdList  ");             

            if (filtroMarcasDto != null) {
                if (filtroMarcasDto.getCodigoEtiqueta()!= null
                        && !filtroMarcasDto.getCodigoEtiqueta().equals("")) {
                    queryStr.append(" AND UPPER(m.marId.marCodigo) =:codigoEtiqueta ");
                }
                
                if (filtroMarcasDto.getNombreEtiqueta()!= null
                        && !filtroMarcasDto.getNombreEtiqueta().equals("")) {
                    queryStr.append(" AND UPPER(m.marId.marNombre) like :marNombre ");
                }
                
                if (filtroMarcasDto.getTecnologia()!= null
                        && !filtroMarcasDto.getTecnologia().equals("")) {
                    queryStr.append(" AND UPPER(m.hhpp.nodId.nodTipo.nombreBasica) like :nombreBasica ");
                }
                
                if (filtroMarcasDto.getEstado()!= null
                        && !filtroMarcasDto.getEstado().equals("")) {
                    queryStr.append(" AND m.estadoRegistro =:estadoRegistro ");
                }
                
            }
             queryStr.append(" order by m.marId.marNombre DESC ");
            
            
            Query query = entityManager.createQuery(queryStr.toString());
                      
            if (filtroMarcasDto != null) {
                
                query.setParameter("hhppIdList", filtroMarcasDto.getHhppIdList());

                if (filtroMarcasDto.getCodigoEtiqueta() != null
                        && !filtroMarcasDto.getCodigoEtiqueta().equals(BigDecimal.ZERO)) {
                    query.setParameter("codigoEtiqueta", filtroMarcasDto.getCodigoEtiqueta().trim().toUpperCase());
                }
                
                if (filtroMarcasDto.getNombreEtiqueta() != null
                        && !filtroMarcasDto.getNombreEtiqueta().equals("")) {
                    query.setParameter("marNombre", "%"+filtroMarcasDto.getNombreEtiqueta().toUpperCase().trim()+"%");
                }
                
                if (filtroMarcasDto.getTecnologia()!= null
                        && !filtroMarcasDto.getTecnologia().equals("")) {
                    query.setParameter("nombreBasica", "%"+filtroMarcasDto.getTecnologia().toUpperCase().trim()+"%");
                }
                
                if (filtroMarcasDto.getEstado()!= null
                        && !filtroMarcasDto.getEstado().equals("")) {
                    query.setParameter("estadoRegistro", Integer.parseInt(filtroMarcasDto.getEstado()));
                }
            }
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            
            resultList = (List<MarcasHhppMgl>) query.getResultList();
            return resultList;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    
}