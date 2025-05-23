
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtHhppVtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * alejandro.martinez.ext@claro.com.co
 */
@Stateless
public class CmtHhppVtMglFacade implements CmtHhppVtMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public CmtHhppVtMgl createCm(CmtHhppVtMgl tecnologiaHabilitada)
            throws ApplicationException {
        CmtHhppVtMglManager manager = new CmtHhppVtMglManager();
        return manager.createCm(tecnologiaHabilitada, user, perfil);
    }

    @Override
    public CmtHhppVtMgl updateCm(CmtHhppVtMgl tecnologiaHabilitada)
            throws ApplicationException {
        CmtHhppVtMglManager manager = new CmtHhppVtMglManager();
        return manager.updateCmt(tecnologiaHabilitada, user, perfil);
    }

    @Override
    public boolean deleteCm(CmtHhppVtMgl tecnologiaHabilitada) throws ApplicationException {
        CmtHhppVtMglManager manager = new CmtHhppVtMglManager();
        return manager.deleteCm(tecnologiaHabilitada, user, perfil);
    }

    @Override
    public List<CmtHhppVtMgl> findAll() throws ApplicationException {
        CmtHhppVtMglManager manager = new CmtHhppVtMglManager();
        return manager.findAll();
    }

    @Override
    public List<CmtHhppVtMgl> findBySubEdiVt(CmtSubEdificiosVt subEdificioVtObj)
            throws ApplicationException {
        CmtHhppVtMglManager manager = new CmtHhppVtMglManager();
        return manager.findBySubEdiVt(subEdificioVtObj);
    }

    @Override
    public List<CmtHhppVtMgl> findByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        CmtHhppVtMglManager manager = new CmtHhppVtMglManager();
        return manager.findByVt(vtObj);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public CmtHhppVtMgl findByIdDrDireccion(BigDecimal drDireccion) throws ApplicationException {
        CmtHhppVtMglManager manager = new CmtHhppVtMglManager();
        return manager.findByIdDrDireccion(drDireccion);
    }

    /**
     * Busca Lista de CmtHhppVtMgl en repositorio por suedificio Vt y piso
     *
     * @param subEdificioVtObj subedificio vt
     * @param piso
     * @return List<CmtHhppVtMgl> encontrada en el repositorio
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<CmtHhppVtMgl> findBySubedificioVtAndPiso(CmtSubEdificiosVt subEdificioVtObj,
            int piso) throws ApplicationException {

        CmtHhppVtMglManager manager = new CmtHhppVtMglManager();
        return manager.findBySubedificioVtAndPiso(subEdificioVtObj, piso);
    }
}
