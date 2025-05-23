/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ortizjaf
 */
public interface CmtEstadoIntxExtMglFacaceLocal  extends BaseFacadeLocal<CmtEstadoIntxExtMgl>{
    
    public CmtEstadoIntxExtMgl findByEstadoInterno(CmtBasicaMgl estadoInterno) throws ApplicationException;
    public void setUser(String mUser, int mPerfil) throws ApplicationException;
    public List<CmtEstadoIntxExtMgl> findAllEstadoInternoExternoList(int firstResult, int maxResults) throws ApplicationException;
    public int countEstadoInternoExterno() throws ApplicationException;
    public CmtEstadoIntxExtMgl findByEstadoInternoXExterno(BigDecimal estadoInterno, BigDecimal estadoExterno, int estadoRegistro) throws ApplicationException;
    public CmtEstadoIntxExtMgl createEstadoIntxExtMgl(CmtEstadoIntxExtMgl t, String user, int perfil) throws ApplicationException;
    public CmtEstadoIntxExtMgl updateEstadoIntxExtMgl(CmtEstadoIntxExtMgl t, String user, int perfil) throws ApplicationException;
    public List<CmtEstadoIntxExtMgl> findAll() throws ApplicationException ;
}
