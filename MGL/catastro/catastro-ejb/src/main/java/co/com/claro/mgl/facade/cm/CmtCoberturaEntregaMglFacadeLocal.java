/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.CmtFiltroCoberturasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtCoberturaEntregaMgl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface CmtCoberturaEntregaMglFacadeLocal extends BaseFacadeLocal<CmtCoberturaEntregaMgl> {
    public void setUser(String mUser, int mPerfil) throws ApplicationException;
    public List<String> buscarListaCoberturaEntregaIdCentroPoblado(BigDecimal centroPobladoId) throws ApplicationException;
    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoId(BigDecimal centroPobladoId) throws ApplicationException;
    public CmtFiltroCoberturasDto findCoberturaEntregaListByCentroPoblado(
            HashMap<String, Object> filtro, boolean contar, int firstResult, int maxResults)throws ApplicationException;    
    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoIdOperadorId(BigDecimal gpoId, BigDecimal basicaId);
}
