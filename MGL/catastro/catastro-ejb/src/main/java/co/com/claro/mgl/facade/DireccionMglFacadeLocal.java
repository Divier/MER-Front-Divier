/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface  DireccionMglFacadeLocal extends BaseFacadeLocal<DireccionMgl> {

    public List<DireccionMgl> findByDireccion(String direccion, BigDecimal idgpo) throws ApplicationException;
    public List<DireccionMgl> findByDirId(BigDecimal dirId) throws ApplicationException;
    /**
     * @param ubicacionId
     * @return 
     * @throws ApplicationException
     */
    public List<DireccionMgl> findByDireccionAlterna(BigDecimal ubicacionId) throws ApplicationException;       

    /**
     * @param dirServiInfo
     * @param direccion
     * @return 
     * @throws ApplicationException
     */
    public List<DireccionMgl> findDireccionByServinfoNoStandar(String dirServiInfo, String direccion) throws ApplicationException;

    public DireccionMgl updateDireccionMgl(DireccionMgl t) throws ApplicationException;
    
    public List<AuditoriaDto> construirAuditoria(DireccionMgl direccionMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

}
