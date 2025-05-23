/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface MarcasMglFacadeLocal extends BaseFacadeLocal<MarcasMgl> {
    
    public List<MarcasMgl> findMarcasMglByHhpp(HhppMgl hhppMgl) throws ApplicationException;
    
     public MarcasMgl findById(BigDecimal idMarcasMgl) throws ApplicationException;
     
     public List<MarcasMgl> findByGrupoCodigo(String codigo) throws ApplicationException;
     
     public List<MarcasMgl> findAllMarcasMgl() throws ApplicationException;
}
