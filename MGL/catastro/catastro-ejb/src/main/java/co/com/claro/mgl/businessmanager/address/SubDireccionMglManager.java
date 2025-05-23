/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.SubDireccionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.SubDireccionAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
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
public class SubDireccionMglManager {
    private static final Logger LOGGER = LogManager.getLogger(SubDireccionMglManager.class);

    SubDireccionMglDaoImpl subDireccionMglDaoImpl = new SubDireccionMglDaoImpl();

    public List<SubDireccionMgl> findAll() throws ApplicationException {

        List<SubDireccionMgl> resulList;


        resulList = subDireccionMglDaoImpl.findAll();

        return resulList;
    }

    public List<SubDireccionMgl> findByIdDireccionMgl(BigDecimal dirId) throws ApplicationException {

        List<SubDireccionMgl> resultList;
        resultList = subDireccionMglDaoImpl.findByIdDireccionMgl(dirId);

        return resultList;
    }

    public List<SubDireccionMgl> findByDirId(BigDecimal dirId) throws ApplicationException {
        return subDireccionMglDaoImpl.findByDirId(dirId);
    }
    
    public SubDireccionMgl findById(BigDecimal dirId) throws ApplicationException {
        return subDireccionMglDaoImpl.find(dirId);
    }
    
    public SubDireccionMgl create(SubDireccionMgl subDireccionMgl) throws ApplicationException {

        return subDireccionMglDaoImpl.create(subDireccionMgl);

    }
    
    public SubDireccionMgl update(SubDireccionMgl subDireccionMgl) throws ApplicationException {

        return subDireccionMglDaoImpl.update(subDireccionMgl);

    }
    
      /**
     * Se actualiza un registro de direccion de malla antigua a nueva
     *
     * @param idDireccion id de la direccion antigua que se desea cambiar de malla
     * @param direccionTextoNueva direccion en texto de la dirección nueva 
     * @param centroPobladoId centro poblado donde se encuentra la direccion nueva
     * @param barrio
     *
     * @return
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public SubDireccionMgl actualizarSubDireccionBySubDireccionIdTextoCambioDir(BigDecimal idSubDireccion,
            String direccionTextoNueva, BigDecimal centroPobladoId, String barrio) throws ApplicationException {
        try {
            SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();            
            DireccionesValidacionManager direccionesValidacionManager = new DireccionesValidacionManager();
            
            //buscamos la subDireccion antigua para actualizar el registro especifico
            SubDireccionMgl subDireccionAntigua = subDireccionMglManager.findById(idSubDireccion);     
            
            if (subDireccionAntigua != null) {
                //se hace la actualización de los datos del registro con georeferenciacion y actualizacion de los valores
                subDireccionAntigua = direccionesValidacionManager
                        .georeferenciacionSubDireccionCambioDir
                        (subDireccionAntigua, direccionTextoNueva, centroPobladoId, barrio);

                //actualización del registro de subdireccion
                SubDireccionMgl subDireccionMgl = subDireccionMglManager.update(subDireccionAntigua);
                LOGGER.error("SubDireccion actualizada idSubdireccion: " + subDireccionMgl.getSdiId());
                return subDireccionMgl;
            } else {
                throw new ApplicationException("Ocurrio un error consultando la subdirección con id: "
                        + idSubDireccion + " y no fue posible realizar el cambio de apto");
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en actualizarSubDireccionBySubDireccionIdTextoCambioDir. " +e.getMessage(), e);  
            throw new ApplicationException("Error actualizando la sub direccion");
        }
    }
    
        /**
     * Actualiza un registro de la tabla mgl_direccion en los campos correspondientes
     * a las coberturas de red y otros valores obtenidos de serviInformacion por el Georeferenciador
     *
     * @param subDireccionMgl
     * @param direccionMgl objeto con el id del registro que se desea actualizar.
     * @return true si se actualiza el registro correctamente
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
     public boolean actualizarCoberturasGeoSubDireccionMgl(SubDireccionMgl subDireccionMgl) throws ApplicationException {
        return subDireccionMglDaoImpl.actualizarCoberturasGeoSubDireccionMgl(subDireccionMgl);
    }
     
    public List<AuditoriaDto> construirAuditoria(SubDireccionMgl subDireccionMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<SubDireccionMgl, SubDireccionAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<SubDireccionMgl, SubDireccionAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(subDireccionMgl);
        return listAuditoriaDto;
    }

}
