/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.businessmanager.address.rr;

import co.com.claro.mgl.dao.impl.rr.As400Dao;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.rr.HhppRR;
import java.util.Date;
import java.util.Map;

/**
 * Controlar el As400Dao y sus acciones a la base de datos
 * <p>
 * Se exponen los métodos que hacen parte de las acciones realizadas a la As400
 *
 * @author becerraarmr
 *
 * @version 2017 revisión 1.0
 * @see As400Dao clase encargada de realizar las acciones directas a la base de
 * datos
 */
public class As400Manager {

    /**
     * dao de acceso para las consultas a la As400
     */
    private final As400Dao as400Dao;

    /**
     * Crear la instancia
     * <p>
     * Crea una instancia
     *
     * @author becerraarmr
     */
    public As400Manager() {
        as400Dao = new As400Dao();
    }

    /**
     * Buscar data completa de cuenta matriz
     * <p>
     * Se busca la cuenta matriz completa teniendo en cuenta el parámetro.
     *
     * @author becerraarmr
     * @version 2017 revisión 1.0
     *
     * @return el Map con los datos de cuenta Matriz
     *
     * @param cuentaMatriz valor para buscar datos de cuenta matriz
     */
    public Map<String, String> findDataCuentaMatriz(String cuentaMatriz) {
        return as400Dao.findDataCuentaMatriz(cuentaMatriz);
    }
    

    /**
     * *Victor Bocanegra Metodo para buscar los nodos paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<HhppRR>
     * @throws ApplicationException
     */
    public PaginacionDto<HhppRR> findAllPaginado(int paginaSelected,
            int maxResults, Date fechaInicioUlt, String fechaInicio,
            String horaInicio, boolean conteo)  {
        
        PaginacionDto<HhppRR> resultado = new PaginacionDto<>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }

        if (conteo) {
            resultado.setNumPaginas(as400Dao.countHhppLogAuditoriaRr(fechaInicioUlt, fechaInicio, horaInicio));
        } else {
            resultado.setListResultado(as400Dao.
                    findHhppLogAuditoriaRr(firstResult, maxResults, fechaInicioUlt, fechaInicio, horaInicio));
        }  
        return resultado;
    }
}
