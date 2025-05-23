/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaTipoMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ortizjaf
 */
public interface CmtCompetenciaTipoMglFacadeLocal{
    
    List<CmtCompetenciaTipoMgl> findAllItemsActive() throws ApplicationException;
    List<CmtCompetenciaTipoMgl> findAllActiveItemsPaginado(int paginaSelected,
            int maxResults) throws ApplicationException;
    int getCountAllActiveItems() throws ApplicationException;
    CmtCompetenciaTipoMgl create(CmtCompetenciaTipoMgl competenciaTipoMgl) throws ApplicationException;
    CmtCompetenciaTipoMgl update(CmtCompetenciaTipoMgl competenciaTipoMgl) throws ApplicationException;
    boolean delete(CmtCompetenciaTipoMgl competenciaTipoMgl) throws ApplicationException;
    public String calcularCodigoRr() throws ApplicationException;
    void setUser(String mUser, int mPerfil) throws ApplicationException;
    public CmtCompetenciaTipoMgl findById(BigDecimal idCompetenciaTipo) throws ApplicationException;
}
