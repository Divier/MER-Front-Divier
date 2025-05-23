/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ResultModFacDirMgl;
import co.com.claro.mgl.rest.dtos.CmtRequestConsByTokenDto;
import co.com.claro.mgl.rest.dtos.CmtResponseDirByToken;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class ResultModFacDirMglDaoImpl extends GenericDaoImpl<ResultModFacDirMgl> {
    
   private static final Logger LOGGER = LogManager.getLogger(ResultModFacDirMglDaoImpl.class);
   
   
   
    /**
     * Busca un ResultModFacDirMgl por token en el repositorio
     *
     * @author Victor Bocanegra
     * @param token
     * @return ResultModFacDirMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public ResultModFacDirMgl findBytoken(String token)
            throws ApplicationException {
        try {
            ResultModFacDirMgl result;
            Query query = entityManager.createNamedQuery("ResultModFacDirMgl.findBytoken");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (token != null && !token.isEmpty()) {
                query.setParameter("tokenConsulta", token);
            }
            result = (ResultModFacDirMgl) query.getSingleResult();
            return result;

        } catch (NoResultException e) {
            String msg = "No se encontraron registros con el token:"+token+": "
                    + e.getMessage();
            LOGGER.warn(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
       
    /**
     * Busca un ResultModFacDirMgl por token en el repositorio
     *
     * @author cardenaslb
     * @param cmtRequestConsByTokenDto
     * @return ResultModFacDirMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtResponseDirByToken findDirByToken(CmtRequestConsByTokenDto cmtRequestConsByTokenDto)
            throws ApplicationException {
        try {
            ResultModFacDirMgl result;
            Query query = entityManager.createNamedQuery("ResultModFacDirMgl.findDirByToken");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (cmtRequestConsByTokenDto != null && cmtRequestConsByTokenDto.getTokenConsulta()!= null) {
                query.setParameter("tokenConsulta", cmtRequestConsByTokenDto.getTokenConsulta());
            }
            result = (ResultModFacDirMgl) query.getSingleResult();
            CmtResponseDirByToken cmtResponseDirByToken = new CmtResponseDirByToken();
            if(result != null && result.getDireccionDetallada() != null){
                 cmtResponseDirByToken.setDireccionDetalladaId(result.getDireccionDetallada());
            }
            return cmtResponseDirByToken;

        } catch (NoResultException e) {
            String msg = "No se encontraron registros con el token:"+cmtRequestConsByTokenDto.getTokenConsulta()+": "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

}
