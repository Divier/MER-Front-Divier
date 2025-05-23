/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.facade.procesomasivo;

import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.manager.procesomasivo.CargueMasivoHhppManager;
import co.com.claro.mgl.vo.cm.DetalleCargaHhppMgl;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Implementar acciones de cargue masivo
 *
 * Se implementan las acciones de cargue masivo, la cual serían ejecutar el
 * cargue masivo, activar el tiempo de proceso nocturno y ver parámetros del
 * proceso.
 *
 * @author becerraarmr
 */
@Stateless
public class CargueMasivoHhppFacade implements CargueMasivoHhppFacadeLocal {

    /**
     * Variable para procesar proceso nocturno.
     */
    private CargueMasivoHhppManager cargueMasivoManager;

    /**
     * Ejecutar Cargue Masivo según los parámetros establecidos
     *
     * Se solicita la ejecución de la carga masiva, iniciando un la ejecución
     * del del hilo que realizará el proceso.
     *
     * @author becerraarmr
     * @param usuario usuario que realiza el cargue.
     * @param perfil
     * @param filePart Archivo que se quiere procesar.
     * @param session httpsession para control de cookie
     */
    @Override
    public void execute(String usuario, int perfil, Part filePart, HttpSession session) {
        //Para el hilo que está en proceso.
        cargueMasivoManager = null;
        //Carga el nuevo hilo para procesar.
        cargueMasivoManager = new CargueMasivoHhppManager(usuario, perfil, filePart);
        cargueMasivoManager.start();
        session.setAttribute("THREADCARGAMASIVA_MGL", cargueMasivoManager);
    }

    /**
     * Activar el timer para el proceso nocturno.
     *
     * Cada dia a las 18:00:00 carga los datos de proceso nocturno con el fin de
     * actualizar los datos de procesamiento; como la hora de inicio, de fin y
     * el tiempo de programción.
     *
     * @author becerraarmr
     */
    @Schedule(second = "0", minute = "0", hour = "18", persistent = true)
    @Override
    public void activarTimerProcesoNocturno() {
        //getCargueMasivoManager().activarTimer();
    }

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
    @Override
    public String verParametros() throws ApplicationException {
        return getCargueMasivoManager().verParametros();
    }

    /**
     * Consigue el manager CargueMasivoHhppManager
     *
     * @author becerraarmr
     * @return el objeto.
     */
    public CargueMasivoHhppManager getCargueMasivoManager() {
        return new CargueMasivoHhppManager();
    }

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
    @Override
    public DetalleCargaHhppMgl procesarDataRevertir(String[] item, String estado,
            HhppCargueArchivoLog archivoResumen, String estadoUnit) {

        return getCargueMasivoManager().procesarDataRevertir(item, estado, archivoResumen, estadoUnit);
    }
}
