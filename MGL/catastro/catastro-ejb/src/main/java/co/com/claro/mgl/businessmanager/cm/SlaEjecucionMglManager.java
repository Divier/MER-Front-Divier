/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.SlaEjecucionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.SlaEjecucionAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class SlaEjecucionMglManager {

    /**
     * Autor: victor bocanegra Metodo para consultar todos los Sla de ejecucion
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<SlaEjecucionMgl> findAll() throws ApplicationException {
        List<SlaEjecucionMgl> resulList;
        SlaEjecucionMglDaoImpl dao = new SlaEjecucionMglDaoImpl();
        resulList = dao.findAll();
        return resulList;
    }

    /**
     * Autor: victor bocanegra Metodo para crear un SLA de ejecucion en la BD
     *
     * @param slaEjecucionMgl a crear
     * @param mUser usuario que crea el registro
     * @param mPerfil perfil del usuario que crea el registro
     * @return SlaEjecucionMgl
     * @throws ApplicationException
     */
    public SlaEjecucionMgl create(SlaEjecucionMgl slaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException {

        SlaEjecucionMglDaoImpl dao = new SlaEjecucionMglDaoImpl();
        return dao.createCm(slaEjecucionMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para actualizar SLA de ejecucion en la BD
     *
     * @param slaEjecucionMgl a modificar
     * @param mUser usuario que modifica el registro
     * @param mPerfil perfil del usuario que modifica el registro
     * @return SlaEjecucionMgl
     * @throws ApplicationException
     */
    public SlaEjecucionMgl update(SlaEjecucionMgl slaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException {

        SlaEjecucionMglDaoImpl dao = new SlaEjecucionMglDaoImpl();
        return dao.updateCm(slaEjecucionMgl, mUser, mPerfil);

    }

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
    public boolean delete(SlaEjecucionMgl slaEjecucionMgl, String mUser, int mPerfil)
            throws ApplicationException {

        SlaEjecucionMglDaoImpl dao = new SlaEjecucionMglDaoImpl();
        return dao.deleteCm(slaEjecucionMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo consultar la auditoria de un registro
     *
     * @param slaEjecucionMgl a consultar
     * @return List<AuditoriaDto>
     * @throws ApplicationException
     */
    public List<AuditoriaDto> construirAuditoria(SlaEjecucionMgl slaEjecucionMgl)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        UtilsCMAuditoria<SlaEjecucionMgl, SlaEjecucionAuditoriaMgl> utilsCMAuditoria
                = new UtilsCMAuditoria<>();
        List<AuditoriaDto> listAuditoriaDto
                = utilsCMAuditoria.construirAuditoria(slaEjecucionMgl);
        return listAuditoriaDto;

    }

    /**
     * Autor: victor bocanegra Metodo para consultar un sla de ejecucion por
     * tecnologia y tipo de ejecucion activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public SlaEjecucionMgl findByTecnoAndEjecucion(CmtBasicaMgl tecBasicaMgl, String tipoEjecucion)
            throws ApplicationException {

        SlaEjecucionMglDaoImpl dao = new SlaEjecucionMglDaoImpl();
        return dao.findByTecnoAndEjecucion(tecBasicaMgl, tipoEjecucion);
    }

}
