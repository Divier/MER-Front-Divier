/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.MglSaveMapJsDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglSaveMapJs;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class MglSaveJsManager {
   
     /**
     * Busca un List<MglSaveMapJs>  en el repositorio
     *
     * @author Victor Bocanegra
     * @return MglSaveMapJs encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<MglSaveMapJs> findAll()
            throws ApplicationException {

        MglSaveMapJsDaoImpl dao = new MglSaveMapJsDaoImpl();
        return dao.findAllItems();
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

        MglSaveMapJsDaoImpl dao = new MglSaveMapJsDaoImpl();
        return dao.update(mglSaveMapJs);
    }
}
