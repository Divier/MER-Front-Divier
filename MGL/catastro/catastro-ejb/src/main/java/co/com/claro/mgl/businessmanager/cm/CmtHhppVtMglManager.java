package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtHhppVtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
public class CmtHhppVtMglManager {

    CmtHhppVtMglDaoImpl dao = new CmtHhppVtMglDaoImpl();

    public CmtHhppVtMgl createCm(CmtHhppVtMgl tecnologiaHabilitada,
            String usuario, int perfil) throws ApplicationException {
        tecnologiaHabilitada = dao.createCm(tecnologiaHabilitada, usuario, perfil);
        return tecnologiaHabilitada;
    }

    public CmtHhppVtMgl updateCmt(CmtHhppVtMgl tecnologiaHabilitada,
            String usuario, int perfil) throws ApplicationException {
        tecnologiaHabilitada = dao.updateCm(tecnologiaHabilitada, usuario, perfil);
        return tecnologiaHabilitada;
    }

    public boolean deleteCm(CmtHhppVtMgl cmtHhppVtObj, String usuario, int perfil) throws ApplicationException {
         cmtHhppVtObj = dao.find(cmtHhppVtObj.getIdHhppVt());        
        if (cmtHhppVtObj.getProcesado() != 1) {
            return dao.deleteCm(cmtHhppVtObj, usuario, perfil);
        } else {
            return false;
        }
    }

    public List<CmtHhppVtMgl> findAll() throws ApplicationException {
        return dao.findAll();
    }

    public List<CmtHhppVtMgl> findBySubEdiVt(CmtSubEdificiosVt subEdificioVtObj) throws ApplicationException {
        return dao.findBySubEdiVt(subEdificioVtObj);
    }

    public List<CmtHhppVtMgl> findByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        return dao.findByVt(vtObj);
    }
    
    public CmtHhppVtMgl findByIdDrDireccion(BigDecimal drDireccion) throws ApplicationException {
        CmtHhppVtMgl tecnologiaHabilitada;
        tecnologiaHabilitada = dao.findByIdDrDireccion(drDireccion);
        return tecnologiaHabilitada;
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
    public List<CmtHhppVtMgl> findBySubedificioVtAndPiso(CmtSubEdificiosVt subEdificioVtObj,
            int piso) throws ApplicationException {

        return dao.findBySubedificioVtAndPiso(subEdificioVtObj, piso);

    }
}
