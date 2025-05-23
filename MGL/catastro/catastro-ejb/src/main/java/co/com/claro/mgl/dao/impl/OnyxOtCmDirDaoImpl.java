/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.SegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.utils.ClassUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author cardenaslb
 */
public class OnyxOtCmDirDaoImpl extends GenericDaoImpl<OnyxOtCmDir>{

    private static final Logger LOGGER = LogManager.getLogger(OnyxOtCmDirDaoImpl.class);

       public List<OnyxOtCmDir> findOnyxOtCmById(BigDecimal id_OnyxCmDir)
            throws ApplicationException {
         List<OnyxOtCmDir> onyxOtCmDir;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "OnyxOtCmDir c WHERE c.ot_Id_Cm.idOt = :ot_Id_Cm AND c.estadoRegistro = 1 ");
        query.setParameter("ot_Id_Cm", id_OnyxCmDir);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        onyxOtCmDir = (List<OnyxOtCmDir>) query.getResultList();
        return onyxOtCmDir;
    }
       
       
    public List<OnyxOtCmDir> findOnyxOtHHppById(BigDecimal ot_Direccion_Id)
            throws ApplicationException {
        List<OnyxOtCmDir> onyxOtCmDir;
        Query query = entityManager.createQuery("SELECT c FROM "
                + "OnyxOtCmDir c WHERE c.ot_Direccion_Id.otHhppId = :ot_Direccion_Id AND c.estadoRegistro = 1 ");
        query.setParameter("ot_Direccion_Id", ot_Direccion_Id);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        onyxOtCmDir = (List<OnyxOtCmDir>) query.getResultList();
        return onyxOtCmDir;
    }
    
    public List<SegmentoDto> findAllSegmento()
            throws ApplicationException {
        List<String> onyxOtCmDir;
        List<SegmentoDto> listSegmentoDto = new ArrayList<>();
        Query query = entityManager.createQuery("SELECT DISTINCT(c.segmento_Onyx) FROM "
                + "OnyxOtCmDir c  WHERE  c.estadoRegistro = 1 ");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        onyxOtCmDir = (List<String>) query.getResultList();
        int idSeg = 0;
        for(String seg : onyxOtCmDir){
            idSeg++;
            BigDecimal segmentoId = new BigDecimal(idSeg);
            SegmentoDto segmentoDto = new SegmentoDto();
            segmentoDto.setNombreSegmento(seg);
            segmentoDto.setIdSegmento(segmentoId);
            listSegmentoDto.add(segmentoDto);
        }
        return listSegmentoDto;
    }
     /**
     * Busca informacion de Onix por id de la orden
     * y numero ot Hija
     *
     * @author Victor Bocanegra
     * @param idOt numero de ot ccmm
     * @param idOtHija numero de la Ot hija
     * @return List<OnyxOtCmDir>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<OnyxOtCmDir> findOnyxByOtCmAndOtHija(BigDecimal idOt, String idOtHija)
            throws ApplicationException {
        List<OnyxOtCmDir> onyxOtCmDir;
        Query query = entityManager.createQuery("SELECT c FROM OnyxOtCmDir c "
                + " WHERE c.ot_Id_Cm.idOt = :idOt "
                + " AND c.Onyx_Ot_Hija_Cm = :idOtHija ");
        query.setParameter("idOt", idOt);
        query.setParameter("idOtHija", idOtHija);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        onyxOtCmDir = (List<OnyxOtCmDir>) query.getResultList();
        return onyxOtCmDir;
    }
    
    /**
     * Busca informacion de Onix por id de la orden Hhpp
     * y numero ot Hija
     *
     * @author Victor Bocanegra
     * @param idOt numero de ot Hhpp
     * @param idOtHija numero de la Ot hija
     * @return List<OnyxOtCmDir>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<OnyxOtCmDir> findOnyxByOtHhppAndOtHija(BigDecimal idOt, String idOtHija)
            throws ApplicationException {
        List<OnyxOtCmDir> onyxOtCmDir;
        Query query = entityManager.createQuery("SELECT c FROM OnyxOtCmDir c "
                + " WHERE c.ot_Direccion_Id.otHhppId = :otHhppId "
                + " AND c.Onyx_Ot_Hija_Dir = :idOtHija ");
        query.setParameter("otHhppId", idOt);
        query.setParameter("idOtHija", idOtHija);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        onyxOtCmDir = (List<OnyxOtCmDir>) query.getResultList();
        return onyxOtCmDir;
    }
   
    /**
     * Busca informacion de Onix por id Enlace de APP EXT
     *
     * @author Victor Bocanegra
     * @param idEnlace
     * @return List<OnyxOtCmDir>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<OnyxOtCmDir> findOnyxByIdEnlace(String idEnlace)
            throws ApplicationException {
        List<OnyxOtCmDir> onyxOtCmDir;
        try {
            Query query = entityManager.createQuery("SELECT c FROM OnyxOtCmDir c "
                    + " WHERE c.codigo_Servicio_Onyx = :idEnlace "
                    + " AND c.estadoRegistro = 1 ORDER BY c.fechaCreacion ASC");
            query.setParameter("idEnlace", idEnlace);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            onyxOtCmDir = (List<OnyxOtCmDir>) query.getResultList();
            return onyxOtCmDir;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" +
                    ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + 
                    ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

}
