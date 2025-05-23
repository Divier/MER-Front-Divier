/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroInformacionTecnicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtInfotecnicaMglDaoImpl extends GenericDaoImpl<CmtInfoTecnicaMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtCuentaMatrizMglDaoImpl.class);

    public List<CmtInfoTecnicaMgl> findAll() throws ApplicationException {
        List<CmtInfoTecnicaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtInfoTecnicaMgl.findAll");
        resultList = (List<CmtInfoTecnicaMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtInfoTecnicaMgl> findBySubEdificioId(CmtSubEdificioMgl subEdificioId) throws ApplicationException {
        List<CmtInfoTecnicaMgl> resultList;
        Query query = entityManager.createQuery("SELECT c FROM CmtInfoTecnicaMgl c WHERE c.idSubedificio = :subEdificioId and c.estadoRegistro = 1 ");
        query.setParameter("subEdificioId", subEdificioId);
        resultList = (List<CmtInfoTecnicaMgl>) query.getResultList();
        return resultList;
    }

    public FiltroInformacionTecnicaDto findInfoTecnicaSearch(BigDecimal subEdificio, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroInformacionTecnicaDto filtroInformacionTecnicaDto = new FiltroInformacionTecnicaDto();
        if (contar) {
            filtroInformacionTecnicaDto.setNumRegistros(findInfoTecnicaSearchContar(subEdificio));
        } else {
            filtroInformacionTecnicaDto.setListaTablasTipoBasica(findInfoTecnicaSearchDatos(subEdificio, firstResult, maxResults));
        }
        return filtroInformacionTecnicaDto;
    }

    public long findInfoTecnicaSearchContar(BigDecimal subEdificio) throws ApplicationException {
        long resultList;
        Query query = entityManager.createNamedQuery("CmtInfoTecnicaMgl.getCountBySubEdificio");
        query.setParameter("subEdificio", subEdificio);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        return resultList;
    }

    public List<CmtInfoTecnicaMgl> findInfoTecnicaSearchDatos( BigDecimal subEdificio, int firstResult, int maxResults) throws ApplicationException {
        List<CmtInfoTecnicaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtInfoTecnicaMgl.findBySubEdificio");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setParameter("subEdificio", subEdificio);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtInfoTecnicaMgl>) query.getResultList();
        return resultList;
    }
    
     public CmtInfoTecnicaMgl findBySubEdificioIdAndTecnoSub
        (CmtSubEdificioMgl subEdificioId, CmtTecnologiaSubMgl tecnologiaSubMgl) throws ApplicationException {
        CmtInfoTecnicaMgl result;
        try{
        Query query = entityManager.createQuery("SELECT c FROM CmtInfoTecnicaMgl c  "
                + " WHERE c.idSubedificio = :subEdificioId  AND c.tecnologiaSubMglObj = :tecnologiaSubMglObj "
                + " AND c.estadoRegistro = 1 ");

            query.setParameter("subEdificioId", subEdificioId);
            query.setParameter("tecnologiaSubMglObj", tecnologiaSubMgl);
            result = (CmtInfoTecnicaMgl) query.getSingleResult();

        } catch (NoResultException ex) {
            result = null;
            LOGGER.error(ex.getMessage());
        } catch (Exception ex) {
            result = null;
            LOGGER.error(ex.getMessage());
        }
        return result;
    }

}
