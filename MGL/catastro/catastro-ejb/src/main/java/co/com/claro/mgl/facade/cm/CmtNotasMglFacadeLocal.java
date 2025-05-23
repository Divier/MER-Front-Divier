/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroNotasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtNotasMglFacadeLocal extends BaseFacadeLocal<CmtNotasMgl> {

    public List<CmtNotasMgl> findTipoNotasId(BigDecimal tipoNotasId) throws ApplicationException;

    public CmtNotasMgl findById(BigDecimal id) throws ApplicationException;

    public List<CmtNotasMgl> findNotasBySubEdificioId(BigDecimal subEdificioId, BigDecimal tipoNota) throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    public List<AuditoriaDto> construirAuditoria(CmtNotasMgl cmtNotasMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    FiltroNotasDto findNotas(HashMap<String, Object> params,BigDecimal subEdificio,
            boolean contar, int firstResult, int maxResults)
            throws ApplicationException;
    
    public CmtNotasMgl create(CmtNotasMgl cmtNotasMgl, String usuario, int perfil) throws ApplicationException;
}
