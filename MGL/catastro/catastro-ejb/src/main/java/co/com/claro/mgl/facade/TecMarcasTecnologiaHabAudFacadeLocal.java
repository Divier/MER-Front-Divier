/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.TecMarcasTecnologiaHabAud;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author espinosadiea
 */
@Local
public interface TecMarcasTecnologiaHabAudFacadeLocal {
    public List<TecMarcasTecnologiaHabAud> obtenerTodos() throws ApplicationException;

    public List<TecMarcasTecnologiaHabAud> obtenerLog(MarcasHhppMgl marcaHomePassSeleccionado)
            throws ApplicationException;
}
