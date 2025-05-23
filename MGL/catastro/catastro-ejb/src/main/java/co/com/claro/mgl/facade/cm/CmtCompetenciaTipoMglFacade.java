
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtCompetenciaTipoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaTipoMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Competencia Tipo. Expone la logica de negocio de competencia Tipos en
 * el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtCompetenciaTipoMglFacade implements CmtCompetenciaTipoMglFacadeLocal {
    private String user = "";
    private int perfil = 0;
    /**
     * Busca los todos los tipos Competencias. Permite realizar la busqueda de
     * los tipos de Compentencias activos en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @return Tipo Compentencias activas
     * @throws ApplicationException
     */
    @Override
    public List<CmtCompetenciaTipoMgl> findAllItemsActive() throws ApplicationException {
        CmtCompetenciaTipoMglManager manager = new CmtCompetenciaTipoMglManager();
        return manager.findAllItemsActive();
    }

    /**
     * Busca las Competencias Tipos. Permite realizar la busqueda de las
     * Compentencias Tipos Activas Paginado el resultado.
     *
     * @author Johnnatan Ortiz
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public List<CmtCompetenciaTipoMgl> findAllActiveItemsPaginado(int paginaSelected, int maxResults) throws ApplicationException {
        CmtCompetenciaTipoMglManager manager = new CmtCompetenciaTipoMglManager();
        return manager.findAllActiveItemsPaginado(paginaSelected, maxResults);
    }

    /**
     * Cuenta las Competencias Tipo. Permite realizar el conteo de las
     * Compentencias Tipos Activas.
     *
     * @author Johnnatan Ortiz
     * @return numero de Compentencias Tipos activas
     * @throws ApplicationException
     */
    @Override
    public int getCountAllActiveItems() throws ApplicationException {
        CmtCompetenciaTipoMglManager manager = new CmtCompetenciaTipoMglManager();
        return manager.getCountAllActiveItems();
    }

    /**
     * Crea una Competencia Tipo. Permite crear una Compentencia Tipo en el
     * repositorio.
     *
     * @author Johnnatan Ortiz
     * @param competenciaTipoMgl competencia tipo a crear
     * @return competencia tipo creada
     * @throws ApplicationException
     */
    @Override
    public CmtCompetenciaTipoMgl create(CmtCompetenciaTipoMgl competenciaTipoMgl) throws ApplicationException {
        CmtCompetenciaTipoMglManager manager = new CmtCompetenciaTipoMglManager();
        return manager.create(competenciaTipoMgl, user, perfil);
    }
    
    /**
     * Actualizar una Competencia Tipo. Permite actualizar una Compentencia Tipo
     * en el repositorio dejando el registro estado = 0.
     *
     * @author Johnnatan Ortiz
     * @param competenciaTipoMgl competencia tipo a crear
     * @return competencia tipo creada
     * @throws ApplicationException
     */
    @Override
    public boolean delete(CmtCompetenciaTipoMgl competenciaTipoMgl) throws ApplicationException {
        CmtCompetenciaTipoMglManager manager = new CmtCompetenciaTipoMglManager();
        return manager.delete(competenciaTipoMgl, user, perfil);
    }

    /**
     * Actualiza una Competencia Tipo. Permite actualizar una Compentencia Tipo
     * en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param competenciaTipoMgl competencia tipo a crear
     * @return competencia tipo creada
     * @throws ApplicationException
     */
    @Override
    public CmtCompetenciaTipoMgl update(CmtCompetenciaTipoMgl competenciaTipoMgl) throws ApplicationException {
        CmtCompetenciaTipoMglManager manager = new CmtCompetenciaTipoMglManager();
        return manager.update(competenciaTipoMgl, user, perfil);
    }
    
    /**
     * Actualiza una Competencia Tipo.Permite actualizar una Compentencia Tipo
 en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @return competencia tipo creada
     * @throws ApplicationException
     */
    @Override
    public String calcularCodigoRr() throws ApplicationException{
        CmtCompetenciaTipoMglManager manager = new CmtCompetenciaTipoMglManager();
        return manager.calcularCodigoRr();
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
 public CmtCompetenciaTipoMgl findById(BigDecimal idCompetenciaTipo) throws ApplicationException{
        CmtCompetenciaTipoMglManager manager = new CmtCompetenciaTipoMglManager();
        return manager.findById(idCompetenciaTipo);
    }    
}
