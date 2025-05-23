/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.TecMarcasTecnologiaHabAudDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.TecMarcasTecnologiaHabAud;

import java.util.List;



/**
 *
 * @author espinosadiea
 */
public class TecMarcasTecnologiaHabAudManager {
    TecMarcasTecnologiaHabAudDaoImpl tmthaDao = new TecMarcasTecnologiaHabAudDaoImpl();
    public List<TecMarcasTecnologiaHabAud> obtenerTodos() throws ApplicationException{
        return tmthaDao.obtenerTodos();
    }

    public List<TecMarcasTecnologiaHabAud> obtenerLog(MarcasHhppMgl marcaHomePassSeleccionado) 
            throws ApplicationException {
        return tmthaDao.obtenerLog(marcaHomePassSeleccionado);
    }

}
