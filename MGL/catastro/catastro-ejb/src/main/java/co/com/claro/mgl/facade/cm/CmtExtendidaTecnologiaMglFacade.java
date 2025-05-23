/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtExtendidaTecnologiaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class CmtExtendidaTecnologiaMglFacade implements CmtExtendidaTecnologiaMglFacadeLocal {

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
    @Override
    public CmtExtendidaTecnologiaMgl create(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia,
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        CmtExtendidaTecnologiaMglManager manager = new CmtExtendidaTecnologiaMglManager();
        return manager.create(mglExtendidaTecnologia, usuarioCrea, perfilCrea);
    }

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
    @Override
    public CmtExtendidaTecnologiaMgl update(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia,
            String usuarioCrea, int perfilCrea) throws ApplicationException {

        CmtExtendidaTecnologiaMglManager manager = new CmtExtendidaTecnologiaMglManager();
        return manager.update(mglExtendidaTecnologia, usuarioCrea, perfilCrea);
    }

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
    @Override
    public boolean delete(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia,
            String usuarioCrea, int perfilCrea) throws ApplicationException {

        CmtExtendidaTecnologiaMglManager manager = new CmtExtendidaTecnologiaMglManager();
        return manager.delete(mglExtendidaTecnologia, usuarioCrea, perfilCrea);

    }

    /**
     * Busca el complemento de una tecnologia por la basica de la tecnologia
     *
     * @author Victor Bocanegra
     * @param cmtBasicaMgl
     * @return CmtExtendidaTecnologiaMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtExtendidaTecnologiaMgl findBytipoTecnologiaObj(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {

        CmtExtendidaTecnologiaMglManager manager = new CmtExtendidaTecnologiaMglManager();
        return manager.findBytipoTecnologiaObj(cmtBasicaMgl);
    }

}
