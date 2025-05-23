/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.MglSaveJsManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglSaveMapJs;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class MglSaveMapJsFacade implements MglSaveMapJsFacadeLocal {

    /**
     * Busca un List<MglSaveMapJs> en el repositorio
     *
     * @author Victor Bocanegra
     * @return MglSaveMapJs encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<MglSaveMapJs> findAll()
            throws ApplicationException {
        MglSaveJsManager manager = new MglSaveJsManager();
        return manager.findAll();
    }

    /**
     * Modifica un MglSaveMapJs en el repositorio
     *
     * @author Victor Bocanegra
     * @param MglSaveMapJs
     * @return MglSaveMapJs modificada en el repositorio
     * @throws ApplicationException
     */
    public MglSaveMapJs update(MglSaveMapJs mglSaveMapJs)
            throws ApplicationException {
        MglSaveJsManager manager = new MglSaveJsManager();
        return manager.update(mglSaveMapJs);
    }

}
