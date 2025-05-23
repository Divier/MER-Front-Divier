/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.facade.procesomasivo;

import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.vo.cm.DetalleCargaHhppMgl;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Exponer los métodos de cargue
 *
 * Se exponen los métodos para el cargue masivo
 *
 * @author becerraarmr
 */
@Local
public interface CargueMasivoHhppFacadeLocal {

    /**
     * Ejecutar Cargue Masivo según los parámetros establecidos
     * <p>
     * Se solicita la ejecución de la carga masiva.
     *
     * @author becerraarmr
     * @param usuario usuario que realiza el cargue.
     * @param perfil
     * @param filePart Archivo que se quiere procesar.
     * @param session httpsession para control de cookie
     */
     void execute(String usuario, int perfil, Part filePart, HttpSession session);

    /**
     * Activar el timer para el proceso nocturno.
     * <p>
     * Reprogramar el timer de proceso nocturno.
     *
     * @author becerraarmr
     */
     void activarTimerProcesoNocturno();

    /**
     * Referenciar datos según parámetros
     * <p>
     * Se busca en la base de datos en la tabla parámetros los valores que
     * corresponden para procesar la carga
     *
     * @author becerraarmr
     * @return Valor del parametro encontrado
     * @throws ApplicationException
     */
    String verParametros() throws ApplicationException;

    /**
     * Procesar la data contenida en el item.
     *
     * @author bocanegra Vm
     * @param item arreglo con la data a procesar
     * @param estado estado del detalle de la carga
     * @param archivoResumen archivo resumen del cargue
     * @param estadoUnit estado del hhpp
     * @return DetalleCargaHhppMgl con la información deseada
     */
      DetalleCargaHhppMgl procesarDataRevertir(String[] item, String estado,
            HhppCargueArchivoLog archivoResumen, String estadoUnit);
      
}
