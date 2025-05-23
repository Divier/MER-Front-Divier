/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public interface SlaEjecucionMglFacadeLocal {

    /**
     * Autor: victor bocanegra Metodo para consultar todos los Sla de ejecucion
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    List<SlaEjecucionMgl> findAll() throws ApplicationException;

    /**
     * Autor: victor bocanegra Metodo para crear un SLA de ejecucion en la BD
     *
     * @param slaEjecucionMgl a crear
     * @param mUser usuario que crea el registro
     * @param mPerfil perfil del usuario que crea el registro
     * @return SlaEjecucionMgl
     * @throws ApplicationException
     */
    SlaEjecucionMgl create(SlaEjecucionMgl slaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException;

    /**
     * Autor: victor bocanegra Metodo para actualizar SLA de ejecucion en la BD
     *
     * @param slaEjecucionMgl a modificar
     * @param mUser usuario que modifica el registro
     * @param mPerfil perfil del usuario que modifica el registro
     * @return SlaEjecucionMgl
     * @throws ApplicationException
     */
    SlaEjecucionMgl update(SlaEjecucionMgl slaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException;

    /**
     * Autor: victor bocanegra Metodo para borrar logicamente un SLA de
     * ejecucion en la BD
     *
     * @param slaEjecucionMgl a borrar
     * @param mUser usuario que borra el registro
     * @param mPerfil perfil del usuario que borra el registro
     * @return boolean
     * @throws ApplicationException
     */
    boolean delete(SlaEjecucionMgl slaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException;

    /**
     * Autor: victor bocanegra Metodo consultar la auditoria de un registro
     *
     * @param slaEjecucionMgl a consultar
     * @return List<AuditoriaDto>
     * @throws ApplicationException
     */
    List<AuditoriaDto> construirAuditoria(SlaEjecucionMgl slaEjecucionMgl)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException;
    
        /**
     * Autor: victor bocanegra Metodo para consultar un sla de ejecucion por
     * tecnologia y tipo de ejecucion activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    SlaEjecucionMgl findByTecnoAndEjecucion(CmtBasicaMgl tecBasicaMgl, String tipoEjecucion)
            throws ApplicationException;

}
