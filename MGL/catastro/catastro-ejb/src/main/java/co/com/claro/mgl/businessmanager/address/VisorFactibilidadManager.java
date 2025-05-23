/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.VisorFactibilidadDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VisorFactibilidad;

import java.math.BigDecimal;

/**
 *  Clase para obtener y guardar la relacion de la factibilidad con el codigo de consulta
 *
 * @author Yasser Leon
 */
public class VisorFactibilidadManager {

    private VisorFactibilidadDaoImpl dao = new VisorFactibilidadDaoImpl();

    /**
     * Metodo para obtener una factibilidad dado el codigo asociado
     *
     * @param codigo codigo asociado a la factibilidad
     * @return {@link BigDecimal}
     * @throws ApplicationException excepcion
     */
    public BigDecimal findIdFactibilidadByCodigo(String codigo)
            throws ApplicationException {
        return dao.findIdFactibilidadByCodigo(codigo);
    }

    /**
     * Metodo para crear o actualizar un visor de fctibilidad
     *
     * @param visorFactibilidad codigo asociado a la factibilidad
     * @return {@link VisorFactibilidad}
     * @throws ApplicationException excepcion
     */
    public VisorFactibilidad update(VisorFactibilidad visorFactibilidad) throws ApplicationException {
        return dao.update(visorFactibilidad);
    }

}
