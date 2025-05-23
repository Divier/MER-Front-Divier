/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtCompetenciaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class CmtCompetenciaMglFacade implements CmtCompetenciaMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;
    
    @Override
    public List<CmtCompetenciaMgl> findAll() throws ApplicationException {
        CmtCompetenciaMglManager cmtCompetenciaMglManager = new CmtCompetenciaMglManager();
        return cmtCompetenciaMglManager.findAll();
    }

    @Override
    public List<CmtCompetenciaMgl> findCompetenciaId(BigDecimal subEdificiosId) throws ApplicationException {
        CmtCompetenciaMglManager cmtCompetenciaMglManager = new CmtCompetenciaMglManager();
        return cmtCompetenciaMglManager.findCompetenciaId(subEdificiosId);
    }

    @Override
    public CmtCompetenciaMgl create(CmtCompetenciaMgl cmtCompetenciaMgl) throws ApplicationException {
        CmtCompetenciaMglManager cmtCompetenciaMglManager = new CmtCompetenciaMglManager();
        return cmtCompetenciaMglManager.create(cmtCompetenciaMgl, user, perfil);
    }

    @Override
    public CmtCompetenciaMgl update(CmtCompetenciaMgl cmtCompetenciaMgl) throws ApplicationException {
        CmtCompetenciaMglManager cmtCompetenciaMglManager = new CmtCompetenciaMglManager();
        return cmtCompetenciaMglManager.update(cmtCompetenciaMgl, user, perfil);
    }

    @Override
    public boolean delete(CmtCompetenciaMgl cmtCompetenciaMgl) throws ApplicationException {
               CmtCompetenciaMglManager cmtCompetenciaMglManager = new CmtCompetenciaMglManager();
        return cmtCompetenciaMglManager.delete(cmtCompetenciaMgl, user, perfil);
    }

    @Override
    public CmtCompetenciaMgl findById(CmtCompetenciaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Busca las Competencias por SubEdificio. Permite realizar la busqueda de
     * las Compentencias de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public List<CmtCompetenciaMgl> findBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtCompetenciaMglManager manager = new CmtCompetenciaMglManager();
        return manager.findBySubEdificio(subEdificio);
    }
    
    /**
     * Busca las Competencias por SubEdificio. Permite realizar la busqueda de
     * las Compentencias de un mismo SubEdificio paginando el resultado.
     *
     * @author Johnnatan Ortiz
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param subEdificio SubEdificio
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public List<CmtCompetenciaMgl> findBySubEdificioPaginacion(int paginaSelected,
            int maxResults,CmtSubEdificioMgl subEdificio) throws ApplicationException{
        CmtCompetenciaMglManager manager = new CmtCompetenciaMglManager();
        return manager.findBySubEdificioPaginacion(paginaSelected, maxResults, subEdificio);
    }
    
    /**
     * Cuenta las Competencias asociadas a un SubEdificio. Permite realizar el
     * conteo de las Compentencias de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public int getCountBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException{
        CmtCompetenciaMglManager manager = new CmtCompetenciaMglManager();
        return manager.getCountBySubEdificio(subEdificio);
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
     * Metodo para construir la Auditoria de Competencias
     * 
     * @autor Julie Sarmiento
     * @param cmtCompetenciaMgl
     * @return Auditoria Competencias
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException 
     */
    @Override
    public List<AuditoriaDto> construirAuditoria(CmtCompetenciaMgl cmtCompetenciaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (cmtCompetenciaMgl != null) {
            CmtCompetenciaMglManager cmtCompetenciaMglManager = new CmtCompetenciaMglManager();
            return cmtCompetenciaMglManager.construirAuditoria(cmtCompetenciaMgl);
        } else {
            return null;
        }
    }
}
