/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.FiltroMarcasMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface MarcasHhppMglFacadeLocal extends BaseFacadeLocal<MarcasHhppMgl> {
    public boolean asignarMarcaHhppMgl(HhppMgl hhppMgl, MarcasHhppMgl marcasHhppMgl, String blackLis, String usuario) throws ApplicationException;
    public List<MarcasHhppMgl> findMarcasHhppMglidHhpp(BigDecimal hhppId) throws ApplicationException;
    public List<MarcasHhppMgl> findMarcasHhppMglidHhppSinEstado(List<HhppMgl> hhppId) throws ApplicationException;
    public MarcasMgl findMarcasMglByCodigo(String marca) throws ApplicationException;
    public List<MarcasHhppMgl> buscarXHhppMarca(BigDecimal homePass, BigDecimal marca) throws ApplicationException;
    public List<MarcasHhppMgl> findResultadosFiltro(FiltroMarcasMglDto filtroMarcasDto) throws ApplicationException;
}
