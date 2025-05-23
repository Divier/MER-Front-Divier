/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.HomologacionRazonesMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaHomologacionRazonesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HomologacionRazonesMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class HomologacionRazonesMglFacade implements HomologacionRazonesMglFacadeLocal {

    /**
     * Autor: victor bocanegra Metodo para consultar todas las homologaciones
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    @Override
    public List<HomologacionRazonesMgl> findAll() throws ApplicationException {

        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.findAll();
    }

    /**
     * Autor: victor bocanegra Metodo para crear una homologacion en la BD
     *
     * @param homologacionRazonesMgl a crear
     * @param mUser usuario que crea el registro
     * @param mPerfil perfil del usuario que crea el registro
     * @return HomologacionRazonesMgl
     * @throws ApplicationException
     */
    @Override
    public HomologacionRazonesMgl create(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException {

        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.create(homologacionRazonesMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para actualizar una homologacion en la BD
     *
     * @param homologacionRazonesMgl a modificar
     * @param mUser usuario que modifica el registro
     * @param mPerfil perfil del usuario que modifica el registro
     * @return HomologacionRazonesMgl
     * @throws ApplicationException
     */
    @Override
    public HomologacionRazonesMgl update(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException {

        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.update(homologacionRazonesMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para borrar logicamente una homologacion
     * en la BD
     *
     * @param homologacionRazonesMgl arrendatario a borrar
     * @param mUser usuario que borra el registro
     * @param mPerfil perfil del usuario que borra el registro
     * @return boolean
     * @throws ApplicationException
     */
    @Override
    public boolean delete(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException {

        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.delete(homologacionRazonesMgl, mUser, mPerfil);

    }
    
    /**
     * Bocanegra vm metodo para buscar una orden homologacion en BD por codigo
     * razon OFSCS
     *
     * @param razonOFSC
     * @return HomologacionRazonesMgl
     */
    @Override
    public HomologacionRazonesMgl findHomologacionByCodigoOFSC(String razonOFSC)
            throws ApplicationException {

        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.findHomologacionByCodigoOFSC(razonOFSC);
    }
    
    /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo ONIX
     *
     * @param codigoOnix
     * @return HomologacionRazonesMgl
     */
    @Override
    public HomologacionRazonesMgl findHomologacionByCodigoONIX(String codigoOnix)
            throws ApplicationException {
        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.findHomologacionByCodigoONIX(codigoOnix);
    }
    
      /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo razon
     * OFSCS y id homologacion
     *
     * @param razonOFSC
     * @param idHomologacion
     * @return HomologacionRazonesMgl
     */
    @Override
    public HomologacionRazonesMgl findHomologacionByCodigoOFSCAndId(String razonOFSC, BigDecimal idHomologacion)
            throws ApplicationException {
        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.findHomologacionByCodigoOFSCAndId(razonOFSC, idHomologacion);
    }
    
    /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo ONIX y
     * id Homologacion
     *
     * @param codigoOnix
     * @param idHomologacion
     * @return HomologacionRazonesMgl
     */
    @Override
    public HomologacionRazonesMgl findHomologacionByCodigoONIXAndId(String codigoOnix, BigDecimal idHomologacion)
            throws ApplicationException {
        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.findHomologacionByCodigoONIXAndId(codigoOnix, idHomologacion);
    }
    
    /**
     * Autor Jeniffer Corredor
     * Método para consultar las Homologaciones Razones según filtros de consulta
     * 
     * @param filtro
     * @return
     * @throws ApplicationException 
     */
    @Override
    public List<HomologacionRazonesMgl> findHomologacionesByFiltro(FiltroConsultaHomologacionRazonesDto filtro)
         throws ApplicationException{
        HomologacionRazonesMglManager manager = new HomologacionRazonesMglManager();
        return manager.findHomologacionesByFiltro(filtro);
    }
}
