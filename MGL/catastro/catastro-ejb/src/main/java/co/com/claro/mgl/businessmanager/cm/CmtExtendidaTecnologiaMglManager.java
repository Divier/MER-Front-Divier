package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtExtendidaTecnologiaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;

/**
 *
 * @author bocanegravm
 */
public class CmtExtendidaTecnologiaMglManager {

    CmtExtendidaTecnologiaMglDaoImpl dao = new CmtExtendidaTecnologiaMglDaoImpl();

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
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        mglExtendidaTecnologia = dao.createCm(mglExtendidaTecnologia, usuarioCrea, perfilCrea);
        return mglExtendidaTecnologia;
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
    public CmtExtendidaTecnologiaMgl update(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia,
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        mglExtendidaTecnologia = dao.updateCm(mglExtendidaTecnologia, usuarioCrea, perfilCrea);
        return mglExtendidaTecnologia;
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
    public boolean delete(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia,
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        return dao.deleteCm(mglExtendidaTecnologia, usuarioCrea, perfilCrea);

    }

    /**
     * Busca el complemento de una tecnologia por la basica de la tecnologia
     *
     * @author Victor Bocanegra
     * @param cmtBasicaMgl
     * @return CmtExtendidaTecnologiaMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtExtendidaTecnologiaMgl findBytipoTecnologiaObj(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        return dao.findBytipoTecnologiaObj(cmtBasicaMgl);
    }

}
