/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglSaveMapJs;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public interface MglSaveMapJsFacadeLocal {

    /**
     * Busca un List<MglSaveMapJs> en el repositorio
     *
     * @author Victor Bocanegra
     * @return MglSaveMapJs encontrada en el repositorio
     * @throws ApplicationException
     */
    List<MglSaveMapJs> findAll()
            throws ApplicationException;

    /**
     * Modifica un MglSaveMapJs en el repositorio
     *
     * @author Victor Bocanegra
     * @param MglSaveMapJs
     * @return MglSaveMapJs modificada en el repositorio
     * @throws ApplicationException
     */
    MglSaveMapJs update(MglSaveMapJs mglSaveMapJs)
            throws ApplicationException;
}
