/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public interface CmtHhppVtMglFacadeLocal {

    CmtHhppVtMgl createCm(CmtHhppVtMgl tecnologiaHabilitada)
            throws ApplicationException;

    CmtHhppVtMgl updateCm(CmtHhppVtMgl tecnologiaHabilitada)
            throws ApplicationException;

    List<CmtHhppVtMgl> findAll()
            throws ApplicationException;

    List<CmtHhppVtMgl> findBySubEdiVt(CmtSubEdificiosVt subEdificioVtObj)
            throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    List<CmtHhppVtMgl> findByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException;

    boolean deleteCm(CmtHhppVtMgl tecnologiaHabilitada) throws ApplicationException;

    public CmtHhppVtMgl findByIdDrDireccion(BigDecimal drDireccion) throws ApplicationException;

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
            int piso) throws ApplicationException;
}
