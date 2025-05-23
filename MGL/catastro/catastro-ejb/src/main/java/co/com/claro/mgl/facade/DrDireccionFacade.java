/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.rest.dtos.CmtRequestConstruccionDireccionDto;
import co.com.claro.mgl.rest.dtos.CmtResponseConstruccionDireccionDto;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Parzifal de León
 */
@Stateless
public class DrDireccionFacade implements DrDireccionFacadeLocal {

    private final DrDireccionManager drDireccionManager;
    private static final Logger LOGGER = LogManager.getLogger(DrDireccionFacade.class);

    public DrDireccionFacade() {
        this.drDireccionManager = new DrDireccionManager();
    }

    @Override
    public DrDireccion create(DrDireccion t) throws ApplicationException {
        try {
            return drDireccionManager.create(t);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public DrDireccion update(DrDireccion t) throws ApplicationException {
        try {
            return drDireccionManager.update(t);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public boolean delete(DrDireccion t) throws ApplicationException {
        try {
            return drDireccionManager.delete(t);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public DrDireccion findById(DrDireccion sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DrDireccion findById(BigDecimal id) {
        try {
            return drDireccionManager.findById(id);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public DrDireccion findByIdSolicitud(BigDecimal id) {
        try {
            return drDireccionManager.findByIdSolicitud(id);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    /**
     * Busca la informacion de idsolicitud en direcciones
     *
     * @author gilaj
     * @param id
     * @return DrDireccion
     * @throws co.com.claro.mgl.error.ApplicationException
     */
   
    @Override
    public DrDireccion findSolicitudCm(BigDecimal id)
            throws ApplicationException {
      
        return drDireccionManager.findSolicitudCm(id);
       
    }

    @Override
    public List<DrDireccion> findAll() throws ApplicationException {
        return drDireccionManager.findAll();
    }

    @Override
    public DrDireccion findByRequest(String idRequest) throws ApplicationException {
        return drDireccionManager.findByRequest(idRequest);
    }

    @Override
    public String getDireccion(DrDireccion drDirec) {
        return drDireccionManager.getDireccion(drDirec);
    }

    @Override
    public String obtenerBarrio(DrDireccion drDirecion) {
        return drDireccionManager.obtenerBarrio(drDirecion);
    }

    /**
     * @param drDirecion
     * @return
     */
    @Override
    public DetalleDireccionEntity fillDetalleDireccionEntity(DrDireccion drDirecion) {
        return drDireccionManager.fillDetalleDireccionEntity(drDirecion);
    }

    /**
     * @param cmtDireccion
     * @param drDirecion
     * @return cmtDireccion
     */
    @Override
    public CmtDireccionMgl createEntityDirDetalleAlterna(CmtDireccionMgl cmtDireccion, DrDireccion drDirecion) {
        return drDireccionManager.createEntityDirDetalleAlterna(cmtDireccion, drDirecion);
    }
    
    @Override
    public DrDireccion agregaNivelCompAptoDireccion(DrDireccion request,String tipoAdicion, String tipoNivel, String valorNivel)throws  ApplicationException{
        return drDireccionManager.agregaNivelCompAptoDireccion(request,tipoAdicion,tipoNivel,valorNivel);
    }
    
    
    /**
     * Construye la direccion para una Solicitud. Permite crear una direccion
     * detallada campo a campo para luego se usada en una solicitud.
     *
     * @author Victor Bocanegra
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud
     * @return respuesta con el proceso de creacion de la solicitud
     *
     */
    @Override
    public CmtResponseConstruccionDireccionDto construirDireccionSolicitudHhpp(CmtRequestConstruccionDireccionDto request)
            {
        Initialized.getInstance();
        DrDireccionManager manager = new DrDireccionManager();
        return manager.construirDireccionSolicitudHhpp(request);
    }
    
    /**
     * Metodo para tabular una direccion texto CK y convertirla en Drdireccion
     *
     * @author Victor Bocanegra
     * @param direccionTexto
     * @return DrDireccion
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DrDireccion tabularDireccionTextoCK(String direccionTexto)
            throws ApplicationException {

        DrDireccionManager manager = new DrDireccionManager();
        return manager.tabularDireccionTextoCK(direccionTexto);
    }
    
    
    /**
     * Construye la direccion para una Solicitud.Permite crear una direccion
     * detallada campo a campo para luego se usada en una solicitud.
     *
     * @param request Request con la informacion necesaria para la creacion de
     * la solicitud.
     * @return Respuesta con el proceso de creacion de la solicitud.
     * @throws ApplicationException
     */
    @Override
    public ResponseConstruccionDireccion construirDireccionSolicitud(
            RequestConstruccionDireccion request) throws ApplicationException {
        DrDireccionManager manager = new DrDireccionManager();
        return manager.construirDireccionSolicitud(request);
    }
}
