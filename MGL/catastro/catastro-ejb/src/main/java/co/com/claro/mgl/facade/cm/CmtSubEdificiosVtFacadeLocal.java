package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
public interface CmtSubEdificiosVtFacadeLocal {

    public CmtSubEdificiosVt createCm(CmtSubEdificiosVt subEdificioVt)
            throws ApplicationException;

    public CmtSubEdificiosVt update(CmtSubEdificiosVt cmtSubEdificiosVt, String usuario, int perfil) throws ApplicationException;

    public boolean delete(CmtSubEdificiosVt cmtSubEdificiosVt) throws ApplicationException;

    public List<CmtSubEdificiosVt> findByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException;

    public CmtSubEdificiosVt findById(BigDecimal idSubEdificiosVt) throws ApplicationException;

    public int getCountByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException;

    public List<CmtSubEdificiosVt> findByVtPaginado(int paginaSelected, int maxResults, CmtVisitaTecnicaMgl vtObj) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;
    
    /**
     * Crea un SubEdificioVt a partir de un SubEdificio. Permite realizar la
     * creacion de un SubEdificiVT a partir de un Subedificios.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio a adicionar a la visita tecnica
     * @param visitaTecnica visita tecnica
     * @param nodSub nodo de la tecnologia del subedificio
     * @return SubEdificioVt Creado
     * @throws ApplicationException
     */
    CmtSubEdificiosVt creaSubEdVtfromSubEd(
            CmtSubEdificioMgl subEdificio,
            CmtVisitaTecnicaMgl visitaTecnica, NodoMgl nodSub) throws ApplicationException;
    
    public CmtSubEdificiosVt findByIdSubEdificio(BigDecimal idSubEdificiosId) throws ApplicationException;
    
        /**
     * Busca los Sub Edificio creados en una VisitaTecnica que no tienen orden
     * de acometida
     *
     * @author Victor Bocanegra
     * @param vtObj VisitaTecnica
     * @return List<CmtSubEdificiosVt>
     * @throws ApplicationException
     */

      List<CmtSubEdificiosVt> findByVtAndAcometida(CmtVisitaTecnicaMgl vtObj) throws ApplicationException;
}
