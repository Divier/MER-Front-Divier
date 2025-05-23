/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroConsultaHomologacionRazonesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HomologacionRazonesMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public interface HomologacionRazonesMglFacadeLocal {

    /**
     * Autor: victor bocanegra Metodo para consultar todas las homologaciones
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    List<HomologacionRazonesMgl> findAll() throws ApplicationException;

    /**
     * Autor: victor bocanegra Metodo para crear una homologacion en la BD
     *
     * @param homologacionRazonesMgl a crear
     * @param mUser usuario que crea el registro
     * @param mPerfil perfil del usuario que crea el registro
     * @return HomologacionRazonesMgl
     * @throws ApplicationException
     */
    HomologacionRazonesMgl create(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException;

    /**
     * Autor: victor bocanegra Metodo para actualizar una homologacion en la BD
     *
     * @param homologacionRazonesMgl a modificar
     * @param mUser usuario que modifica el registro
     * @param mPerfil perfil del usuario que modifica el registro
     * @return HomologacionRazonesMgl
     * @throws ApplicationException
     */
    HomologacionRazonesMgl update(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException;

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
    boolean delete(HomologacionRazonesMgl homologacionRazonesMgl, String mUser, int mPerfil)
            throws ApplicationException;
    
        /**
     * Bocanegra vm metodo para buscar una orden homologacion en BD por codigo
     * razon OFSCS
     *
     * @param razonOFSC
     * @return HomologacionRazonesMgl
     */
    HomologacionRazonesMgl findHomologacionByCodigoOFSC(String razonOFSC)
            throws ApplicationException;
    
        /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo ONIX
     *
     * @param razonOFSC
     * @return HomologacionRazonesMgl
     */
    HomologacionRazonesMgl findHomologacionByCodigoONIX(String codigoOnix)
            throws ApplicationException;
    
          /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo razon
     * OFSCS y id homologacion
     *
     * @param razonOFSC
     * @param idHomologacion
     * @return HomologacionRazonesMgl
     */
    HomologacionRazonesMgl findHomologacionByCodigoOFSCAndId(String razonOFSC, BigDecimal idHomologacion)
            throws ApplicationException;
    
        /**
     * Bocanegra vm metodo para buscar una homologacion en BD por codigo ONIX y
     * id Homologacion
     *
     * @param codigoOnix
     * @param idHomologacion
     * @return HomologacionRazonesMgl
     */
    HomologacionRazonesMgl findHomologacionByCodigoONIXAndId(String codigoOnix, BigDecimal idHomologacion)
            throws ApplicationException;
    
    /**
     * Autor Jeniffer Corredor
     * Método para consultar las Homologaciones Razones según filtros de consulta
     * 
     * @param filtro
     * @return
     * @throws ApplicationException 
     */
    List<HomologacionRazonesMgl> findHomologacionesByFiltro(FiltroConsultaHomologacionRazonesDto filtro)
         throws ApplicationException; 

}
