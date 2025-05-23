/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.facade.procesomasivo;

import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author becerraarmr
 */
@Local
public interface HhppCargueArchivoLogFacadeLocal  
        extends BaseFacadeLocal<HhppCargueArchivoLog>{

    List<HhppCargueArchivoLog> findAllEstado(short estado)
            throws ApplicationException;

    List<HhppCargueArchivoLog> findRangeEstado(
            int[] rango, short estado, String usuario)
            throws ApplicationException;

    List<HhppCargueArchivoLog> findRangeEstadoByOrigen(
            int[] rango, short estado, String usuario)
            throws ApplicationException;

    int countEstado(short estado, String usuario)
            throws ApplicationException;

    /**
     * Buscar en la base de datos el registro del archivo.
     *
     * @author bocanegra vm
     * @param nombreArchivo nombre del archivo
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    HhppCargueArchivoLog findArchivoByNombre(String nombreArchivo)
            throws ApplicationException;

    /**
     * Buscar en la base de datos el registro del archivo resumen con el nombre
     * del archivo TCRM.
     *
     * @author bocanegra vm
     * @param nombreArchivo nombre del archivo
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    HhppCargueArchivoLog findByNombreArchivoTcrm(String nombreArchivo)
            throws ApplicationException;
}