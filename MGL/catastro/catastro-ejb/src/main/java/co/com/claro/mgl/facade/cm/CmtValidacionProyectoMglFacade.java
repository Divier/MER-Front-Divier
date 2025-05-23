package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtValidacionProyectoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtValidacionProyectoMgl;
import java.util.List;
import javax.ejb.Stateless;

/**

 * 
 * @author Lenis Cardenas
 * @version 1.0 revision 03/08/2017.
 * @see BaseCmFacade.
 * @see CmtValidacionProyectoMglFacadeLocal.
 */
@Stateless
public class CmtValidacionProyectoMglFacade
        extends BaseCmFacade<CmtValidacionProyectoMgl>
        implements CmtValidacionProyectoMglFacadeLocal {

    /**
     * Buscar todas las validaciones.
     * Busca todas las validaciones de los proyectos.
     * 
     * @author Lenis Cardenas
     * @version 1.0 revision 03/08/2017.
     * @return La lista de validaciones.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public List<CmtValidacionProyectoMgl> findAll() throws ApplicationException {
        CmtValidacionProyectoMglManager cmtValidacionProyectoMglManager = 
                new CmtValidacionProyectoMglManager();
        return cmtValidacionProyectoMglManager.findAll();
    }

    @Override
    public CmtValidacionProyectoMgl create(CmtValidacionProyectoMgl t) 
            throws ApplicationException {
        CmtValidacionProyectoMglManager cmtValidacionProyectoMglManager = 
                new CmtValidacionProyectoMglManager();
        return cmtValidacionProyectoMglManager.create(t);
    }

    @Override
    public CmtValidacionProyectoMgl update(CmtValidacionProyectoMgl t) 
            throws ApplicationException {
        CmtValidacionProyectoMglManager cmtValidacionProyectoMglManager = 
                new CmtValidacionProyectoMglManager();
        return cmtValidacionProyectoMglManager.update(t);
    }

    @Override
    public boolean delete(CmtValidacionProyectoMgl t) throws ApplicationException {
        CmtValidacionProyectoMglManager cmtValidacionProyectoMglManager = 
                new CmtValidacionProyectoMglManager();
        return cmtValidacionProyectoMglManager.delete(t);
    }

    /**
     * Consultar la validación del proyecto.
     * Consulta la validación del proyecto.
     * 
     * @author Lenis Cardenas
     * @version 1.0 revision 03/08/2017.
     * @param sqlData Validación a buscar.
     * @return Validacion de proyecto si coincide con los filtros proporcionados.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public CmtValidacionProyectoMgl findById(CmtValidacionProyectoMgl sqlData) throws ApplicationException {
        CmtValidacionProyectoMglManager cmtValidacionProyectoMglManager = new CmtValidacionProyectoMglManager();
        return cmtValidacionProyectoMglManager.findById(sqlData);
    }

    @Override
    public List<CmtValidacionProyectoMgl>
            cargarConfiguracionPrevia(CmtBasicaMgl proyecto) 
            throws ApplicationException {
        CmtValidacionProyectoMglManager cmtValidacionProyectoMglManager =
                new CmtValidacionProyectoMglManager();
        return cmtValidacionProyectoMglManager.cargarConfiguracionPrevia(proyecto);
        
    }

    @Override
    public List<CmtValidacionProyectoMgl> crearConfiguracion(
            List<CmtValidacionProyectoMgl> listToCreate) 
            throws ApplicationException {
        CmtValidacionProyectoMglManager manager =
                new CmtValidacionProyectoMglManager();
        return manager.crearConfiguracion(listToCreate, getUser(), getPerfil());
    }

    @Override
    public List<CmtValidacionProyectoMgl> findByFiltroPaginado(
            int paginaSelected,
            int maxResults, CmtBasicaMgl proyecto) 
            throws ApplicationException {
        CmtValidacionProyectoMglManager manager =
                new CmtValidacionProyectoMglManager();
        return manager.findByFiltroPaginado(paginaSelected, maxResults, proyecto);
    }

    @Override
    public int countByFiltroPaginado(CmtBasicaMgl proyecto) 
            throws ApplicationException {
        CmtValidacionProyectoMglManager manager =
                new CmtValidacionProyectoMglManager();
        return manager.countByFiltroPaginado(proyecto);
    }
}
