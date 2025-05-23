package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtMensajeTipoValidacionManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtMensajeTipoValidacion;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Session bean para la funcionalidad de configuración de mensajes.
 * Implementa la interfaz de validación de mensajes y utiliza los métodos de la
 * logica de negocio del manager.
 * 
 * @author Ricardo Cortés Rodríguez
 * @version 1.0 revision 11/05/2017.
 */
@Stateless
public class CmtMensajeTipoValidacionFacade 
    extends BaseCmFacade<CmtMensajeTipoValidacion>
    implements ICmtMensajeTipoValidacionFacadeLocal{
    private CmtMensajeTipoValidacionManager managerBean = new CmtMensajeTipoValidacionManager();

    @Override
    public List<CmtMensajeTipoValidacion> findAll() throws ApplicationException {
        return managerBean.findAll();
    }

    @Override
    public CmtMensajeTipoValidacion findForId(BigDecimal idMensaje) 
            throws ApplicationException {
        return managerBean.findForId(idMensaje);
    }

    @Override
    public CmtMensajeTipoValidacion create(
            CmtMensajeTipoValidacion mensajesValidacion) 
            throws ApplicationException {
        return managerBean.create(mensajesValidacion, getUser(), getPerfil());
    }

    @Override
    public CmtMensajeTipoValidacion update(
            CmtMensajeTipoValidacion mensajesValidacion)
            throws ApplicationException {
        return managerBean.update(mensajesValidacion, getUser(), getPerfil());
    }

    @Override
    public Boolean delete(
            CmtMensajeTipoValidacion mensajesValidacion) 
            throws ApplicationException {
        return managerBean.delete(mensajesValidacion, getUser(), getPerfil());
    }

    @Override
    public List<CmtBasicaMgl> findAllForTipoBasica(BigDecimal id) 
            throws ApplicationException {
        return managerBean.findAllForTipoBasica(id);
    }

    @Override
    public List<CmtMensajeTipoValidacion> findMensajesPorFiltros(Hashtable<String
            , String> parametros) throws ApplicationException {
        return managerBean.findMensajesPorFiltros(parametros);
    }

    @Override
    public Integer obtenerCantidadPaginas(Hashtable<String, String> parametros) 
            throws ApplicationException {
        return managerBean.obtenerCantidadPaginas(parametros);
    }

    @Override
    public List<CmtMensajeTipoValidacion> obtenerBasicasCreacionMensajes(String tipoValidacion
            , List<CmtBasicaMgl> basicaValidacion) throws ApplicationException {
        return managerBean.obtenerBasicasCreacionMensajes(tipoValidacion, basicaValidacion);
    }

    @Override
    public void createListaMensajes(List<CmtMensajeTipoValidacion> mensajes) 
            throws ApplicationException {
        managerBean.createListaMensajes(mensajes, getUser(), getPerfil());
    }

    @Override
    public List<CmtMensajeTipoValidacion> findMensajesNoConfiguradosPorTipoBasica(BigDecimal idTipoBasica) {
        return managerBean.findMensajesNoConfiguradosPorTipoBasica(idTipoBasica);
    }

    
    
    
}
