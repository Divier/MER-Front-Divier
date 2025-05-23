/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *Fabian Barrera
 * @author User
 */
public interface CmtBlackListMglFacadeLocal {
    
    List<CmtBlackListMgl> findAll() throws ApplicationException;
    List<CmtBlackListMgl> findBySubEdificioPaginado(int paginaSelected,
            int maxResults, CmtSubEdificioMgl subEdificio) throws ApplicationException;
    int getCountBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException;
    CmtBlackListMgl create(CmtBlackListMgl cmtBlackListMgl, String usuario, int perfil) throws ApplicationException;
    CmtBlackListMgl update(CmtBlackListMgl cmtBlackListMgl, String usuario, int perfil) throws ApplicationException;
    public List<AuditoriaDto> construirAuditoria(CmtBlackListMgl cmtBlackListMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
     List<CmtBlackListMgl> findBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException;
    
}
