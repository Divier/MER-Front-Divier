/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface CmtEstablecimientoMglFacadeLocal {

    public CmtEstablecimientoCmMgl create(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl) throws ApplicationException;

    public CmtEstablecimientoCmMgl update(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl) throws ApplicationException;

    public boolean delete(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;
    
    public CmtEstablecimientoCmMgl finById(BigDecimal id)throws ApplicationException;
    
    public List<CmtEstablecimientoCmMgl> finBySubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl);
    
    public List<AuditoriaDto> construirAuditoria(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;    
}
