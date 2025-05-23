/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.HhppMglLogRrDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMglLogRr;
import co.com.claro.mgl.jpa.entities.PaginacionDto;

/**
 *
 * @author bocanegravm
 */
public class HhppMglLogRrManager {

    /**
     * Metodo para crear un registro de hhpp en la tabla de logg de RR en MGL
     *
     * Autor:Victor Bocanegra
     *
     * @param hhppMglLogRr
     * @return HhppMglLogRr
     * @throws ApplicationException
     */
    public HhppMglLogRr create(HhppMglLogRr hhppMglLogRr) throws ApplicationException {

        HhppMglLogRrDaoImpl dao = new HhppMglLogRrDaoImpl();
        return dao.create(hhppMglLogRr);
    }

    /**
     * *Victor Bocanegra Metodo para buscar los hhpp paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @return PaginacionDto<HhppMglLogRr>
     * @throws ApplicationException
     */
    public PaginacionDto<HhppMglLogRr> findAllPaginado(int paginaSelected,
            int maxResults, boolean conteo)
            throws ApplicationException {

        PaginacionDto<HhppMglLogRr> resultado = new PaginacionDto<>();
        HhppMglLogRrDaoImpl dao = new HhppMglLogRrDaoImpl();

        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }

        if (conteo) {
            resultado.setNumPaginas(dao.countHhppMglLogRr());
        } else {
            resultado.setListResultado(dao.findHhppMglLogRr(firstResult, maxResults));
        }
        return resultado;
    }

    /**
     * Funcion para el truncate de la tabla que alberga el registro de los hhpp
     * a modificar en MGL
     *
     * Autor:Victor Bocanegra
     *
     * @return true
     * @throws ApplicationException
     */
    public boolean truncateTable() throws ApplicationException {
        HhppMglLogRrDaoImpl dao = new HhppMglLogRrDaoImpl();
        return dao.truncateTable();

    }

}
