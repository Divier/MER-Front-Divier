/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface SubDireccionMglFacadeLocal extends BaseFacadeLocal<SubDireccionMgl> {
    
    public List<SubDireccionMgl> findByIdDireccionMgl(BigDecimal idDireccionMgl) throws ApplicationException;
    public List<SubDireccionMgl> findByDirId(BigDecimal dirId) throws ApplicationException;
    public SubDireccionMgl updateSubDireccionMgl(SubDireccionMgl subDireccionMgl) throws ApplicationException;
    public List<AuditoriaDto> construirAuditoria(SubDireccionMgl subDireccionMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
    
}
