/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoInternoGaMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author User
 */
public interface CmtEstadoInternoGaMglFacadeLocal extends BaseFacadeLocal<CmtEstadoInternoGaMgl> {

    List<CmtEstadoInternoGaMgl> findByFiltro(CmtBasicaMgl estadoIniCmFiltro,
            CmtBasicaMgl estadoExternoAntGaFiltro,
            CmtBasicaMgl estadoInternoGaFiltro,
            CmtBasicaMgl estadoExternoPostGaFiltro,
            CmtBasicaMgl estadoFinalCmFiltro,
            CmtBasicaMgl tipoGaFiltro) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    public boolean existeCodigo(String codigo) throws ApplicationException;

    public List<AuditoriaDto> construirAuditoria(CmtEstadoInternoGaMgl cmtEstadoInternoGaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
