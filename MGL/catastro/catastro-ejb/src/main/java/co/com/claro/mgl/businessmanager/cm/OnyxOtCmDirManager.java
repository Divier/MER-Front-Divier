/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.OnyxOtCmDirDaoImpl;
import co.com.claro.mgl.dtos.SegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class OnyxOtCmDirManager {
    
    private final OnyxOtCmDirDaoImpl onyxOtCmDirDaoImpl = new OnyxOtCmDirDaoImpl();

    public OnyxOtCmDir create(OnyxOtCmDir onyxOtCmDir) throws ApplicationException {
        return onyxOtCmDirDaoImpl.create(onyxOtCmDir);
    }

    public OnyxOtCmDir update(OnyxOtCmDir onyxOtCmDir) throws ApplicationException {
        return onyxOtCmDirDaoImpl.update(onyxOtCmDir);
    }

    public List<OnyxOtCmDir> findOnyxOtCmById(BigDecimal idOtCm) throws ApplicationException {
        return onyxOtCmDirDaoImpl.findOnyxOtCmById(idOtCm);
    }

    public List<OnyxOtCmDir> findOnyxOtHHppById(BigDecimal idOtHhpp) throws ApplicationException {
        return onyxOtCmDirDaoImpl.findOnyxOtHHppById(idOtHhpp);
    }
    
    /**
     * Busca informacion de Onix por id de la orden y numero ot Hija
     *
     * @author Victor Bocanegra
     * @param idOt numero de ot ccmm
     * @param idOtHija numero de la Ot hija
     * @return List<OnyxOtCmDir>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<OnyxOtCmDir> findOnyxByOtCmAndOtHija(BigDecimal idOt, String idOtHija)
            throws ApplicationException {
        return onyxOtCmDirDaoImpl.findOnyxByOtCmAndOtHija(idOt, idOtHija);
    }
    
     /**
     * Busca informacion de Onix por id de la orden Hhpp y numero ot Hija
     *
     * @author Victor Bocanegra
     * @param idOt numero de ot Hhpp
     * @param idOtHija numero de la Ot hija
     * @return List<OnyxOtCmDir>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<OnyxOtCmDir> findOnyxByOtHhppAndOtHija(BigDecimal idOt, String idOtHija)
            throws ApplicationException {
        return onyxOtCmDirDaoImpl.findOnyxByOtHhppAndOtHija(idOt, idOtHija);
    }

    public List<SegmentoDto> findAllSegmento() throws ApplicationException {
        return onyxOtCmDirDaoImpl.findAllSegmento();

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
        return onyxOtCmDirDaoImpl.findOnyxByIdEnlace(idEnlace);
    }
}
