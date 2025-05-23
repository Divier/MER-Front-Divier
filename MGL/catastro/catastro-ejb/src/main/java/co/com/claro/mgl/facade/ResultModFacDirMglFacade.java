/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.ResultModFacDirMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ResultModFacDirMgl;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class ResultModFacDirMglFacade implements ResultModFacDirMglFacadeLocal {

    /**
     * Busca un ResultModFacDirMgl por token en el repositorio
     *
     * @author Victor Bocanegra
     * @param token
     * @return ResultModFacDirMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public ResultModFacDirMgl findBytoken(String token)
            throws ApplicationException {

        ResultModFacDirMglManager manager = new ResultModFacDirMglManager();
        return manager.findBytoken(token);
    }

    /**
     * Modifica un ResultModFacDirMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param resultModFacDirMgl
     * @return ResultModFacDirMgl modificada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public ResultModFacDirMgl update(ResultModFacDirMgl resultModFacDirMgl)
            throws ApplicationException {

        ResultModFacDirMglManager manager = new ResultModFacDirMglManager();
        return manager.update(resultModFacDirMgl);
    }

    /**
     * Crea un ResultModFacDirMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param resultModFacDirMgl
     * @return ResultModFacDirMgl creado en el repositorio
     * @throws ApplicationException
     */
    @Override
    public ResultModFacDirMgl create(ResultModFacDirMgl resultModFacDirMgl)
            throws ApplicationException {

        ResultModFacDirMglManager manager = new ResultModFacDirMglManager();
        return manager.create(resultModFacDirMgl);
    }
    
    /**
     * Valida si el perfil es valido para la creacion automatica
     *
     * @author Miguel Barrios
     * @param codPerfil
     * @return Entero con respuesta de la validacion
     * @throws ApplicationException
     */
    @Override
    public int valParamHabCreacion(String codPerfil) throws ApplicationException {
        ResultModFacDirMglManager manager = new ResultModFacDirMglManager();
        return manager.valParamHabCreacion(codPerfil);
    }
    
   
}
