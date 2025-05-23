/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public interface CmtCompetenciaMglFacadeLocal extends BaseFacadeLocal<CmtCompetenciaMgl> {
    
    public List<CmtCompetenciaMgl> findCompetenciaId(BigDecimal subEdificiosId) throws ApplicationException;
    
    List<CmtCompetenciaMgl> findBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException;
    
    int getCountBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException;
    
    List<CmtCompetenciaMgl> findBySubEdificioPaginacion(int paginaSelected,
            int maxResults,CmtSubEdificioMgl subEdificio) throws ApplicationException;
    
    void setUser(String mUser, int mPerfil) throws ApplicationException;
    
    public List<AuditoriaDto> construirAuditoria(CmtCompetenciaMgl cmtCompetenciaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
