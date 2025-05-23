/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.rest.dtos.CmtRequestConstruccionDireccionDto;
import co.com.claro.mgl.rest.dtos.CmtResponseConstruccionDireccionDto;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Parzifal de León
 */
@Local
public interface DrDireccionFacadeLocal extends BaseFacadeLocal<DrDireccion> {

    public DrDireccion findById(BigDecimal id);

    public DrDireccion findByIdSolicitud(BigDecimal id);

    public DrDireccion findSolicitudCm(BigDecimal id) throws ApplicationException;

    @Override
    public List<DrDireccion> findAll() throws ApplicationException;

    public DrDireccion findByRequest(String idRequest) throws ApplicationException;

    public String getDireccion(DrDireccion drDirec);

    public String obtenerBarrio(DrDireccion drDirecion);

    /**
     * @param drDirecion
     * @return
     */
    public DetalleDireccionEntity fillDetalleDireccionEntity(DrDireccion drDirecion);

    /**
     * @param cmtDireccion
     * @param drDirecion
     * @return cmtDireccion
     */
    public CmtDireccionMgl createEntityDirDetalleAlterna(CmtDireccionMgl cmtDireccion, DrDireccion drDirecion);
    
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
    CmtResponseConstruccionDireccionDto construirDireccionSolicitudHhpp(
            CmtRequestConstruccionDireccionDto request);
    
    DrDireccion agregaNivelCompAptoDireccion(DrDireccion request,String tipoAdicion, String tipoNivel, String valorNivel) throws  ApplicationException;
    
     /**
     * Metodo para tabular una direccion texto CK y convertirla en Drdireccion
     *
     * @author Victor Bocanegra
     * @param direccionTexto
     * @return DrDireccion
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    DrDireccion tabularDireccionTextoCK(String direccionTexto)
            throws ApplicationException;
    
    
    /**
     * Construye la direccion para una Solicitud.Permite crear una direccion
     * detallada campo a campo para luego se usada en una solicitud.
     *
     * @param request Request con la informacion necesaria para la creacion de
     * la solicitud.
     * @return Respuesta con el proceso de creacion de la solicitud.
     * @throws ApplicationException
     */
    ResponseConstruccionDireccion construirDireccionSolicitud(
            RequestConstruccionDireccion request) throws ApplicationException;
}
