/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ResultModFacDirMgl;
/**
 *
 * @author bocanegravm
 */
public interface ResultModFacDirMglFacadeLocal {

    /**
     * Busca un ResultModFacDirMgl por token en el repositorio
     *
     * @author Victor Bocanegra
     * @param token
     * @return ResultModFacDirMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    ResultModFacDirMgl findBytoken(String token)
            throws ApplicationException;
    
        /**
     * Modifica un ResultModFacDirMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param resultModFacDirMgl
     * @return ResultModFacDirMgl modificada en el repositorio
     * @throws ApplicationException
     */
     ResultModFacDirMgl update(ResultModFacDirMgl resultModFacDirMgl)
            throws ApplicationException;
     
         /**
     * Crea un ResultModFacDirMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param resultModFacDirMgl
     * @return ResultModFacDirMgl creado en el repositorio
     * @throws ApplicationException
     */
     ResultModFacDirMgl create(ResultModFacDirMgl resultModFacDirMgl)
            throws ApplicationException; 
    
     
     /**
     * Valida si el perfil es valido para la creacion automatica
     *
     * @author Miguel Barrios
     * @param codPerfil
     * @return Entero con respuesta de la validacion
     * @throws ApplicationException
     */ 
    int valParamHabCreacion(String codPerfil) 
            throws ApplicationException;
}
