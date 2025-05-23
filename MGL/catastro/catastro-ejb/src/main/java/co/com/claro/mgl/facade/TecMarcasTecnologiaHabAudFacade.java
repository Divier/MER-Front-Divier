/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TecMarcasTecnologiaHabAudManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.TecMarcasTecnologiaHabAud;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author espinosadiea
 */
@Stateless
public class TecMarcasTecnologiaHabAudFacade implements TecMarcasTecnologiaHabAudFacadeLocal {
    private final TecMarcasTecnologiaHabAudManager tmtham;
    public TecMarcasTecnologiaHabAudFacade() {
        this.tmtham = new TecMarcasTecnologiaHabAudManager();
    }

    @Override
    public List<TecMarcasTecnologiaHabAud> obtenerTodos() throws ApplicationException{
        return tmtham.obtenerTodos();
    }

    @Override
    public List<TecMarcasTecnologiaHabAud> obtenerLog(MarcasHhppMgl marcaHomePassSeleccionado) 
            throws ApplicationException {
        return tmtham.obtenerLog(marcaHomePassSeleccionado);
    }
    
}
