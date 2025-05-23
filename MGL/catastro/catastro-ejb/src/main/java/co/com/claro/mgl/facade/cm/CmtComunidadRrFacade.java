/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;
import co.com.claro.mgl.businessmanager.cm.CmtComunidadRrManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadesRrDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class CmtComunidadRrFacade implements CmtComunidadRrFacadeLocal {
    
     CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
    /**
     * Victor Bocanegra Metodo para traer todas las comunidades
     *
     * @return List<CmtComunidadRr>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override 
    public List<CmtComunidadRr> findAll() throws ApplicationException {
       
        return cmtComunidadRrManager.findAll();
    }
    
    /**
     * *Victor Bocanegra Metodo para buscar los Backlogs paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<CmtComunidadRr>
     * @throws ApplicationException
     */
    @Override 
    public PaginacionDto<CmtComunidadRr> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaComunidadesRrDto consulta) throws ApplicationException {

        return cmtComunidadRrManager.findAllPaginado(paginaSelected, maxResults, consulta);
    }
    
    @Override 
    public CmtComunidadRr findByCodigoRR(String codigo) throws ApplicationException {

        return cmtComunidadRrManager.findComunidadByCodigo(codigo);
    }
    
    /**
     * *Victor Bocanegra Metodo para buscar las comunidades de una region
     *
     * @param idRegional
     * @return List<CmtComunidadRr>
     * @throws ApplicationException
     */
    @Override
    public List<CmtComunidadRr> findByIdRegional(BigDecimal idRegional)
            throws ApplicationException {

        return cmtComunidadRrManager.findByIdRegional(idRegional);

    }
    
    /**
     * *Victor Bocanegra Metodo para buscar una comunidad por id
     *
     * @param idComunidad
     * @return CmtComunidadRr
     * @throws ApplicationException
     */
    @Override
    public CmtComunidadRr findByIdComunidad(BigDecimal idComunidad)
            throws ApplicationException {

        return cmtComunidadRrManager.findByIdComunidad(idComunidad);

    }

    @Override
    public List<CmtComunidadRr> findByListRegional(List<BigDecimal> idRegional) throws ApplicationException {
         return cmtComunidadRrManager.findByListRegional(idRegional);
    }

    @Override
    public List<CmtComunidadRr> findByListComunidad(List<BigDecimal> idComunidad) throws ApplicationException {
          return cmtComunidadRrManager.findByListComunidad(idComunidad);
    }
    
    
}
