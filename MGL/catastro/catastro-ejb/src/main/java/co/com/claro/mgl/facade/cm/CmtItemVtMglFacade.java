
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtItemVtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtItemVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 *
 * @author User
 */
@Stateless
public class CmtItemVtMglFacade implements CmtItemVtMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
    private static final Logger LOGGER = LogManager.getLogger(CmtItemVtMglFacade.class);

    /**
     *
     * @return @throws ApplicationException
     */
    @Override
    public List<CmtItemVtMgl> findAll() throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.findAll();
    }

    /**
     *
     * @param t
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtItemVtMgl createCm(CmtItemVtMgl t) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.createCm(t, user, perfil);
    }

    /**
     *
     * @param cmtItemVtMgl
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtItemVtMgl create(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.create(cmtItemVtMgl);
    }

    /**
     *
     * @param cmtItemVtMgl
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtItemVtMgl update(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.update(cmtItemVtMgl, user, perfil);
    }

    /**
     *
     * @param cmtItemVtMgl
     * @return
     * @throws ApplicationException
     */
    @Override
    public boolean delete(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.delete(cmtItemVtMgl, user, perfil);
    }

    /**
     *
     * @param cmtItemVtMgl
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtItemVtMgl findById(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.findById(cmtItemVtMgl);
    }

    @Override
    public List<CmtItemVtMgl> findItemVtByVt(CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.findItemVtByVt(vtObj, tipoItem);
    }

    @Override
    public CmtItemVtMgl findItemVtByItem(CmtVisitaTecnicaMgl vtObj, CmtItemMgl idItem) throws ApplicationException {
        try {
            CmtItemVtMglManager manager = new CmtItemVtMglManager();
            return manager.findItemVtByItem(vtObj, idItem);
        } catch (ApplicationException e) {
            if (e.getCause() instanceof NoResultException) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                return null;
            } else if (e.getCause() instanceof NonUniqueResultException) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(e.getMessage(), e);
            } else {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(e.getMessage(), e);
            }
        }
    }

    @Override
    public int getCountByVt(CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.getCountByVt(vtObj, tipoItem);
    }

    @Override
    public List<CmtItemVtMgl> findItemByVtPaginado(int paginaSelected,
            int maxResults, CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.findItemByVtPaginado(paginaSelected, maxResults, vtObj, tipoItem);
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
     * Calcula costo total de Items. Calcula el costo total de los items de una
     * VT por medio del tipo del items
     *
     * @author Johnnatan Ortiz
     * @param vt Visita tecnica
     * @param tipoItem tipo de items
     * @return Costo total de todos los items de la VT
     * @throws ApplicationException
     */
    @Override
    public BigDecimal getTotalCosto(CmtVisitaTecnicaMgl vt, String tipoItem) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.getTotalCosto(vt, tipoItem);
    }
    
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
    @Override
    public BigDecimal getTotalUnidades(CmtVisitaTecnicaMgl vt, String tipoItem) throws ApplicationException {
        CmtItemVtMglManager manager = new CmtItemVtMglManager();
        return manager.getTotalUnidades(vt, tipoItem);
    }
    
    
}
