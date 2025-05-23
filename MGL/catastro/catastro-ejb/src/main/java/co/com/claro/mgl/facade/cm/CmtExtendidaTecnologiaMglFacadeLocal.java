/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;

/**
 *
 * @author bocanegravm
 */
public interface CmtExtendidaTecnologiaMglFacadeLocal {

    /**
     * Crea un complemento de tecnologia en el repositorio.
     *
     * @author Victor Bocanegra
     * @param mglExtendidaTecnologia
     * @param cmtExtendidaTecnologiaMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return un complemento de tecnologia
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public CmtExtendidaTecnologiaMgl create(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia,
            String usuarioCrea, int perfilCrea) throws ApplicationException;

    /**
     * Actualiza un complemento de tecnologia en el repositorio.
     *
     * @author Victor Bocanegra
     * @param mglExtendidaTecnologia
     * @param cmtExtendidaTecnologiaMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return un complemento de tecnologia
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public CmtExtendidaTecnologiaMgl update(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia,
            String usuarioCrea, int perfilCrea) throws ApplicationException;

    /**
     * Elimina un complemento de tecnologia en el repositorio.
     *
     * @author Victor Bocanegra
     * @param mglExtendidaTecnologia
     * @param cmtExtendidaTecnologiaMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return true si la transacion es satisfatoria false si no
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public boolean delete(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia,
            String usuarioCrea, int perfilCrea) throws ApplicationException;

    /**
     * Busca el complemento de una tecnologia por la basica de la tecnologia
     *
     * @author Victor Bocanegra
     * @param cmtBasicaMgl
     * @return CmtExtendidaTecnologiaMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtExtendidaTecnologiaMgl findBytipoTecnologiaObj(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException;

}
