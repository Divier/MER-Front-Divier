/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.ArrendatarioMglManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaArrendatarioDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ArrendatarioMgl;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.ofscCapacity.activityBookingOptions.GetActivityBookingResponses;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.ejb.Stateless;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import java.math.BigDecimal;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class ArrendatarioMglFacade implements ArrendatarioMglFacadeLocal {

    /**
     * Autor: victor bocanegra Metodo para consultar todos los arrendatarios
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    @Override
    public List<ArrendatarioMgl> findAll() throws ApplicationException {
        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.findAll();
    }

    /**
     * Autor: victor bocanegra Metodo para crear un arrendatario en la BD
     *
     * @param arrendatarioMgl arrendatario a crear
     * @param mUser usuario que crea el registro
     * @param mPerfil perfil del usuario que crea el registro
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    @Override
    public ArrendatarioMgl create(ArrendatarioMgl arrendatarioMgl, String mUser, int mPerfil)
            throws ApplicationException {

        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.create(arrendatarioMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para actualizar un arrendatario en la BD
     *
     * @param arrendatarioMgl arrendatario a modificar
     * @param mUser usuario que modifica el registro
     * @param mPerfil perfil del usuario que modifica el registro
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    @Override
    public ArrendatarioMgl update(ArrendatarioMgl arrendatarioMgl, String mUser, int mPerfil)
            throws ApplicationException {

        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.update(arrendatarioMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para borrar logicamente un arrendatario en
     * la BD
     *
     * @param arrendatarioMgl arrendatario a borrar
     * @param mUser usuario que borra el registro
     * @param mPerfil perfil del usuario que borra el registro
     * @return boolean
     * @throws ApplicationException
     */
    @Override
    public boolean delete(ArrendatarioMgl arrendatarioMgl, String mUser, int mPerfil)
            throws ApplicationException {

        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.delete(arrendatarioMgl, mUser, mPerfil);

    }
    
    /**
     * Autor: victor bocanegra Metodo consultar la auditoria de un registro
     *
     * @param arrendatarioMgl arrendatario a consultar
     * @return List<AuditoriaDto>
     * @throws ApplicationException
     */
    @Override
    public List<AuditoriaDto> construirAuditoria(ArrendatarioMgl arrendatarioMgl)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.construirAuditoria(arrendatarioMgl);
    }
    
     /**
     * Autor: victor bocanegra Metodo para consultar el 
     * arrendatario por centro poblado
     *
     * @return List<ArrendatarioMgl>
     * @throws ApplicationException
     */
    @Override
    public List<ArrendatarioMgl> findArrendatariosByCentro
        (GeograficoPoliticoMgl centro) throws ApplicationException {

        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.findArrendatariosByCentro(centro);
    }
        
     /**
     * Autor: victor bocanegra Metodo para consultar cuadrantes
     * @param nodo
     * @return GetActivityBookingResponses
     * @throws ApplicationException
     */
    @Override
    public GetActivityBookingResponses consultarCuadrante(NodoMgl nodo) throws ApplicationException {

        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.consultarCuadrante(nodo);

    }

    /**
     * Autor: victor bocanegra Metodo para consultar un arrendatario por
     * centro-ciudad y depto
     *
     * @param centro
     * @param ciudad
     * @param dpto
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    @Override
    public ArrendatarioMgl findArrendatariosByCentroAndCityAndDpto(GeograficoPoliticoMgl centro, GeograficoPoliticoMgl ciudad,
            GeograficoPoliticoMgl dpto) throws ApplicationException {

        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.findArrendatariosByCentroAndCityAndDpto(centro, ciudad, dpto);
    }
    
    /**
     * Autor: victor bocanegra Metodo para consultar un arrendatario por centro-
     * nombre de arrendatario y cuadrante
     *
     * @param centro
     * @param nombreArrenda
     * @param cuadrante
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    @Override
    public ArrendatarioMgl findByCentroAndNombreArrendaAndCuadrante
        (GeograficoPoliticoMgl centro, String nombreArrenda,
            String cuadrante) throws ApplicationException {
        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.findByCentroAndNombreArrendaAndCuadrante(centro, nombreArrenda, cuadrante);
    }
     
    /**
     * Autor: victor bocanegra Metodo para consultar un arrendatario por Id
     *
     * @param id
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    @Override
    public ArrendatarioMgl findArrendatariosById(BigDecimal id)
            throws ApplicationException {
        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.findArrendatariosById(id);
    }
    /**
     * Autor Jeniffer Corredor
     * Método para consultar los Arrendatarios según filtros de consulta
     * 
     * @param filtro
     * @return
     * @throws ApplicationException 
     */
    @Override
    public List<ArrendatarioMgl> findArrendatariosByFiltro(CmtFiltroConsultaArrendatarioDto filtro)
         throws ApplicationException{
        ArrendatarioMglManager manager = new ArrendatarioMglManager();
        return manager.findArrendatariosByFiltro(filtro);
    }

}
