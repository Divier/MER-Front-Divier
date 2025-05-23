
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroInformacionTecnicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public interface CmtInfoTecnicaMglFacadeLocal extends BaseFacadeLocal<CmtInfoTecnicaMgl> {

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    @Override
    CmtInfoTecnicaMgl create(CmtInfoTecnicaMgl cmtInfoTecnicaMgl) throws ApplicationException;
   
    @Override
    CmtInfoTecnicaMgl update(CmtInfoTecnicaMgl cmtInfoTecnicaMgl) throws ApplicationException;
    
    CmtInfoTecnicaMgl createCm(CmtInfoTecnicaMgl cmtInfoTecnicaMgl) throws ApplicationException;
   
    CmtInfoTecnicaMgl updateCm(CmtInfoTecnicaMgl cmtInfoTecnicaMgl) throws ApplicationException;

    @Override
    List<CmtInfoTecnicaMgl> findAll() throws ApplicationException;

    List<CmtInfoTecnicaMgl> findBySubEdificioId(CmtSubEdificioMgl subEdificioid) throws ApplicationException;

    FiltroInformacionTecnicaDto findInformacionTecnica(BigDecimal subEdificio,
            boolean contar, int firstResult, int maxResults)
            throws ApplicationException;
    
    @Override
    boolean delete(CmtInfoTecnicaMgl cmtInfoTecnicaMgl) throws ApplicationException;

}
