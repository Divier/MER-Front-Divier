package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtItemVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 * La clase funciona como interfaz para los metodos implementados en
 * CmtItemVtMglFacade.
 *
 * @author alejandro.martine.ext@claro.com.co
 * @version 1.0
 */
public interface CmtItemVtMglFacadeLocal extends BaseFacadeLocal<CmtItemVtMgl> {

    public CmtItemVtMgl createCm(CmtItemVtMgl t) throws ApplicationException;

    @Override
    public CmtItemVtMgl update(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException;

    @Override
    public boolean delete(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException;

    @Override
    public CmtItemVtMgl findById(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    public int getCountByVt(CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException;

    public List<CmtItemVtMgl> findItemByVtPaginado(int paginaSelected, int maxResults, CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException;

    public List<CmtItemVtMgl> findItemVtByVt(CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException;

    public CmtItemVtMgl findItemVtByItem(CmtVisitaTecnicaMgl vtObj, CmtItemMgl idItem) throws ApplicationException;

    /**
     * Calcula costo total de Items. Calcula el costo total de los items de una
     * VT por medio del tipo del items
     *
     * @author Johnnatan Ortiz
     * @param vt Visita tecnica
     * @param tipoItem tipo de items
     * @return Costo total de todos los items de la VT
     * @throws ApplicationException
     */
    BigDecimal getTotalCosto(CmtVisitaTecnicaMgl vt, String tipoItem) 
            throws ApplicationException;

    /**
     * Calcula el total de Items. Calcula el total de los items de una VT por
     * medio del tipo del items
     *
     * @author Johnnatan Ortiz
     * @param vt Visita tecnica
     * @param tipoItem tipo de items
     * @return Total de todos los items de la VT
     * @throws ApplicationException
     */
    BigDecimal getTotalUnidades(CmtVisitaTecnicaMgl vt, String tipoItem)
            throws ApplicationException;
}
