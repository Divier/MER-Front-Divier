package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoValidacionMglManager;
import co.com.claro.mgl.dtos.CmtTipoValidacionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Clase que implementa operaciones del facade local. 
 * Encargada de exponer la logica del manager {@link CmtTipoValidacionMglManager}.
 * 
 * @author Johnnatan Ortiz.
 * @version 1.0 revision 18/05/2017.
 * @see BaseCmFacade
 * @see CmtTipoValidacionMglFacadeLocal
 */
@Stateless
public class CmtTipoValidacionMglFacade
        extends BaseCmFacade<CmtTipoValidacionMgl>
        implements CmtTipoValidacionMglFacadeLocal {

    @Override
    public List<CmtTipoValidacionMgl> findAll() throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.findAllItemsActive();
    }

    @Override
    public CmtTipoValidacionMgl create(CmtTipoValidacionMgl t)
            throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.create(t, getUser(), getPerfil());
    }

    @Override
    public CmtTipoValidacionMgl update(CmtTipoValidacionMgl t)
            throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.update(t, getUser(), getPerfil());
    }

    @Override
    public boolean delete(CmtTipoValidacionMgl t) throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.delete(t, getUser(), getPerfil());
    }

    @Override
    public CmtTipoValidacionMgl findById(CmtTipoValidacionMgl sqlData)
            throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.findById(sqlData);
    }

    @Override
    public boolean existsTipoBasica(CmtTipoBasicaMgl tipoValidacion)
            throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.existsTipoBasica(tipoValidacion);
    }

    @Override
    public Integer obtenerCantidadPaginas(Hashtable<String, String> parametros)
            throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.obtenerCantidadPaginas(parametros);
    }

    @Override
    public List<CmtTipoValidacionMgl> findMensajesPorFiltros(
            Hashtable<String, String> parametros)
            throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.findMensajesPorFiltros(parametros);
    }

    @Override
    public List<CmtTipoValidacionDto> findReglasByProyecto(BigDecimal proyectoDetalleSelected) throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.findReglasByProyecto(proyectoDetalleSelected);
    }

    @Override
    public boolean findTipoValidacionEnUso(BigDecimal idTipoValSelected) throws ApplicationException {
        CmtTipoValidacionMglManager cmtTipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        return cmtTipoValidacionMglManager.findTipoValidacionEnUso(idTipoValSelected);
    }
}
