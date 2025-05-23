/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.MglParametrosTrabajosDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglParametrosTrabajos;
import java.util.ArrayList;

/**
 *
 * @author Orlando Velasquez
 */
public class MglParametrosTrabajosManager {

    MglParametrosTrabajosDaoImpl mglParametrosTrabajosDaoImpl = new MglParametrosTrabajosDaoImpl();

    public ArrayList<MglParametrosTrabajos> findMglParametrosTrabajosByName(String nombre) throws ApplicationException {
        return mglParametrosTrabajosDaoImpl.findMglParametrosTrabajosByName(nombre);
    }
    
    public MglParametrosTrabajos findMglParametrosTrabajosByName(String nombre, String server) throws ApplicationException {
        return mglParametrosTrabajosDaoImpl.findMglParametrosTrabajosByName(nombre, server);
    }
    
    public MglParametrosTrabajos updateMglParametrosTrabajos(MglParametrosTrabajos mglParametrosTrabajos) throws ApplicationException {
        return mglParametrosTrabajosDaoImpl.updateMglParametrosTrabajos(mglParametrosTrabajos);
    }

}
