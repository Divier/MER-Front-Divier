/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.DireccionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DireccionAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DireccionMglManager {

    private static final Logger LOGGER = LogManager.getLogger(DireccionMglManager.class);
    DireccionMglDaoImpl direccionMglDaoImpl = new DireccionMglDaoImpl();

    public List<DireccionMgl> findAll() throws ApplicationException {
        List<DireccionMgl> result;
        DireccionMglDaoImpl direccionDaoImpl1 = new DireccionMglDaoImpl();
        result = direccionDaoImpl1.findAll();
        return result;
    }

    public DireccionMgl update(DireccionMgl direccionMgl) throws ApplicationException {
        return direccionMglDaoImpl.update(direccionMgl);
    }

    public DireccionMgl create(DireccionMgl direccionMgl) throws ApplicationException {

        return direccionMglDaoImpl.create(direccionMgl);

    }

    public boolean delete(DireccionMgl direccionMgl) throws ApplicationException {

        return direccionMglDaoImpl.delete(direccionMgl);

    }

    public DireccionMgl findById(DireccionMgl direccionMgl) throws ApplicationException {

        return direccionMglDaoImpl.find(direccionMgl.getDirId());

    }

    public List<DireccionMgl> findByDireccion(String direccion, BigDecimal idgpo)  throws ApplicationException {
        return direccionMglDaoImpl.findByDireccion(direccion,idgpo);
    }
	
	public List<DireccionMgl> findByDirId(BigDecimal dirId) throws ApplicationException {
        return direccionMglDaoImpl.findByDirId(dirId);
    }
    
    
    /**
     * @param ubicacionId
     * @return direccionMglDaoImpl
     * @throws ApplicationException
     */
	public List<DireccionMgl> findByDireccionAlterna(BigDecimal ubicacionId)  throws ApplicationException {
        return direccionMglDaoImpl.findByDireccionAlterna(ubicacionId);
    }
    
    /**
     * @param dirServiInfo
     * @param direccion
     * @return direccionMglDaoImpl
     * @throws ApplicationException
     */
	public List<DireccionMgl> findDireccionByServinfoNoStandar(String dirServiInfo, String direccion) throws ApplicationException {
        return direccionMglDaoImpl.findDireccionByServinfoNoStandar(dirServiInfo, direccion);        
    } 
        
    public DireccionMgl updateDireccionMgl(DireccionMgl direccionMgl,
            String usuario, int perfil) throws ApplicationException {
        return direccionMglDaoImpl.updateCm(direccionMgl, usuario, perfil);
    }
        
      /**
     * Se actualiza un registro de la tabla mgl_direccion de malla antigua a nueva
     *
     * @param idDireccion id de la direccion antigua que se desea cambiar de malla
     * @param direccionTextoNueva direccion en texto de la dirección nueva 
     * @param centroPobladoId centro poblado donde se encuentra la direccion nueva
     * @param cambioAntiguaNueva true si se trata de un cambio de malla de direccion
     * @param barrio barrio de la direccion opcional
     * antigua a nueva y se desea dejar traza de la antigua en direccion alterna     * 
     *
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public DireccionMgl actualizarDireccionByDireccionIdTexto(BigDecimal idDireccion,
            String direccionTextoNueva, BigDecimal centroPobladoId, String usuario, int perfil,
            boolean cambioAntiguaNueva, String barrio) throws ApplicationException {
        try {
            DireccionMglManager direccionMglManager = new DireccionMglManager();
            DireccionesValidacionManager direccionesValidacionManager = new DireccionesValidacionManager();
            DireccionMgl direccionMgl = new DireccionMgl();

            direccionMgl.setDirId(idDireccion);
            //buscamos la direccion antigua para actualizar el registro especifico
            DireccionMgl direccionAntigua = direccionMglManager.findById(direccionMgl);

            //se hace la actualización de los datos del registro con georeferenciacion y actualizacion de los valores
            direccionAntigua = direccionesValidacionManager
                    .georeferenciacionMallaAntiguaANueva(direccionAntigua, direccionTextoNueva, centroPobladoId, barrio);
            //actualización del registro de dirección
            DireccionMgl direccionUpdateMgl = direccionMglManager.update(direccionAntigua);

            String msgError = "Direccion actualizada " + direccionUpdateMgl.getDirId();
            LOGGER.error(msgError);
            
            return direccionUpdateMgl;

        } catch (ApplicationException e) {
            String msg = "Error actualizando el registro de la direccion: " + e.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, e);

        }
    }
    
     /**
     * Actualiza un registro de la tabla mgl_direccion en los campos correspondientes
     * a las coberturas de red y otros valores obtenidos de serviInformacion por el Georeferenciador
     *
     * @param direccionMgl objeto con el id del registro que se desea actualizar.
     * @return true si se actualiza el registro correctamente
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
     public boolean actualizarCoberturasGeoDireccionMgl(DireccionMgl direccionMgl) throws ApplicationException {
        return direccionMglDaoImpl.actualizarCoberturasGeoDireccionMgl(direccionMgl);
    }   

     
    public List<AuditoriaDto> construirAuditoria(DireccionMgl direccionMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<DireccionMgl, DireccionAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<>();
        return utilsCMAuditoria.construirAuditoria(direccionMgl);
    }
}
