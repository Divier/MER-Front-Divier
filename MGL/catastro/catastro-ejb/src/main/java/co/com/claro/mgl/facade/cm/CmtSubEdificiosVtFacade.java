package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtSubEdificiosVtManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
@Stateless
public class CmtSubEdificiosVtFacade implements CmtSubEdificiosVtFacadeLocal {

    private String user = "";
    private int perfil = 0;
    private static final Logger LOGGER = LogManager.getLogger(CmtSubEdificiosVtFacade.class);

    /**
     *
     * @param subEdificioVt
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtSubEdificiosVt createCm(CmtSubEdificiosVt subEdificioVt)
            throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.createCm(subEdificioVt, user, perfil);
    }

    /**
     *
     * @param cmtSubEdificiosVt
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtSubEdificiosVt update(CmtSubEdificiosVt cmtSubEdificiosVt, String user , int perfil) throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.update(cmtSubEdificiosVt, user, perfil);
    }

    /**
     *
     * @param cmtSubEdificiosVt
     * @return
     * @throws ApplicationException
     */
    @Override
    public boolean delete(CmtSubEdificiosVt cmtSubEdificiosVt) throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.delete(cmtSubEdificiosVt, user, perfil);
    }

    /**
     *
     * @param vtObj
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<CmtSubEdificiosVt> findByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.findByVt(vtObj);
    }

    @Override
    public CmtSubEdificiosVt findById(BigDecimal idSubEdificiosVt) throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.findById(idSubEdificiosVt);
    }

    /**
     * Cuenta los Sub Edificio creados en una visita tecnica. Permite realizar
     * el conteo de las Compentencias de un mismo SubEdificio.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @param vtObj VisitaTecnica
     * @return numero de Sub Edificios asociadas a una VisitaTecnica
     * @throws ApplicationException
     */
    @Override
    public int getCountByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.getCountByVt(vtObj);
    }

    /**
     * Busca los Sub Edificio creados en una VisitaTecnica. Permite realizar la
     * busqueda de los Sub Edificio de un mismo VisitaTecnica.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param vtObj
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public List<CmtSubEdificiosVt> findByVtPaginado(int paginaSelected,
            int maxResults, CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.findByVtPaginado(paginaSelected, maxResults, vtObj);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

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
    @Override
    public CmtSubEdificiosVt creaSubEdVtfromSubEd(
            CmtSubEdificioMgl subEdificio,
            CmtVisitaTecnicaMgl visitaTecnica, NodoMgl nodSub) throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        try {
            return manager.createSubEdVtfromSubEd(subEdificio, visitaTecnica, user, perfil,nodSub);
        } catch (ApplicationExceptionCM ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public CmtSubEdificiosVt findByIdSubEdificio(BigDecimal idSubEdificiosId) throws ApplicationException {
          CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.findByIdSubEdificio(idSubEdificiosId);
    }
    
    /**
     * Busca los Sub Edificio creados en una VisitaTecnica que no tienen orden
     * de acometida
     *
     * @author Victor Bocanegra
     * @param vtObj VisitaTecnica
     * @return List<CmtSubEdificiosVt>
     * @throws ApplicationException
     */
    @Override
    public List<CmtSubEdificiosVt> findByVtAndAcometida(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        CmtSubEdificiosVtManager manager = new CmtSubEdificiosVtManager();
        return manager.findByVtAndAcometida(vtObj);

    }
}
