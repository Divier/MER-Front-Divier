/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.HomologacionRazonesMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaHomologacionRazonesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HomologacionRazonesMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class HomologacionRazonesMglManager {

    /**
     * Autor: victor bocanegra Metodo para consultar todas las homologaciones
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<HomologacionRazonesMgl> findAll() throws ApplicationException {
        HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.findAll();
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
    public HomologacionRazonesMgl create(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException {

        HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.createCm(homologacionRazonesMgl, mUser, mPerfil);

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
    public HomologacionRazonesMgl update(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException {

        HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.updateCm(homologacionRazonesMgl, mUser, mPerfil);

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
    public boolean delete(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException {

        HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.deleteCm(homologacionRazonesMgl, mUser, mPerfil);

    }
    
    /**
     * Bocanegra vm metodo para buscar una orden homologacion en BD por codigo
     * razon OFSCS
     *
     * @param razonOFSC
     * @return HomologacionRazonesMgl
     */
    public HomologacionRazonesMgl findHomologacionByCodigoOFSC(String razonOFSC)
            throws ApplicationException {

        HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.findHomologacionByCodigoOFSC(razonOFSC);
    }
  
    /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo ONIX
     *
     * @param codigoOnix
     * @return HomologacionRazonesMgl
     */
    public HomologacionRazonesMgl findHomologacionByCodigoONIX(String codigoOnix)
            throws ApplicationException {
        HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.findHomologacionByCodigoONIX(codigoOnix);
    }
    
    /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo razon
     * OFSCS y id homologacion
     *
     * @param razonOFSC
     * @param idHomologacion
     * @return HomologacionRazonesMgl
     */
    public HomologacionRazonesMgl findHomologacionByCodigoOFSCAndId(String razonOFSC, BigDecimal idHomologacion)
            throws ApplicationException {
        HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.findHomologacionByCodigoOFSCAndId(razonOFSC, idHomologacion);
    }
    
    /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo ONIX y
     * id Homologacion
     *
     * @param codigoOnix
     * @param idHomologacion
     * @return HomologacionRazonesMgl
     */
    public HomologacionRazonesMgl findHomologacionByCodigoONIXAndId(String codigoOnix, BigDecimal idHomologacion)
            throws ApplicationException {

        HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.findHomologacionByCodigoONIXAndId(codigoOnix, idHomologacion);
    }
    
    /**
     * Autor: Jeniffer Corredor
     * Método para consultar las Homologaciones Razones según filtros de consulta
     *
     * @param filtro
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public List<HomologacionRazonesMgl> findHomologacionesByFiltro(FiltroConsultaHomologacionRazonesDto filtro) 
            throws ApplicationException {
                HomologacionRazonesMglDaoImpl dao = new HomologacionRazonesMglDaoImpl();
        return dao.findHomologacionesByFiltro(filtro);
    }
}
