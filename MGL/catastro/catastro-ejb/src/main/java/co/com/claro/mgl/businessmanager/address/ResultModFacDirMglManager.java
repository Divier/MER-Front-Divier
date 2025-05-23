/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.ResultModFacDirMglDaoImpl;
import co.com.claro.mgl.dao.impl.storedprocedures.ResultModFacDirImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ResultModFacDirMgl;
import co.com.claro.mgl.rest.dtos.CmtRequestConsByTokenDto;
import co.com.claro.mgl.rest.dtos.CmtResponseDirByToken;

/**
 *
 * @author bocanegravm
 */
public class ResultModFacDirMglManager {

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

        ResultModFacDirMglDaoImpl dao = new ResultModFacDirMglDaoImpl();
        return dao.findBytoken(token);
    }

    /**
     * Modifica un ResultModFacDirMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param resultModFacDirMgl
     * @return ResultModFacDirMgl modificada en el repositorio
     * @throws ApplicationException
     */
    public ResultModFacDirMgl update(ResultModFacDirMgl resultModFacDirMgl)
            throws ApplicationException {

        ResultModFacDirMglDaoImpl dao = new ResultModFacDirMglDaoImpl();
        return dao.update(resultModFacDirMgl);
    }

    /**
     * Crea un ResultModFacDirMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param resultModFacDirMgl
     * @return ResultModFacDirMgl creado en el repositorio
     * @throws ApplicationException
     */
    public ResultModFacDirMgl create(ResultModFacDirMgl resultModFacDirMgl)
            throws ApplicationException {

        ResultModFacDirMglDaoImpl dao = new ResultModFacDirMglDaoImpl();
        return dao.create(resultModFacDirMgl);
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

        ResultModFacDirMglDaoImpl dao = new ResultModFacDirMglDaoImpl();
        return dao.findDirByToken(cmtRequestConsByTokenDto);
    }
    
    /**
     * Valida si el perfil es valido para la creacion automatica
     *
     * @author Miguel Barrios
     * @param codPerfil
     * @return Entero con respuesta de la validacion
     * @throws ApplicationException
     */
    public int valParamHabCreacion(String codPerfil)
            throws ApplicationException {
        ResultModFacDirImpl sp = new ResultModFacDirImpl();
        return sp.valParamHabCreacion(codPerfil);
    }
    
}
