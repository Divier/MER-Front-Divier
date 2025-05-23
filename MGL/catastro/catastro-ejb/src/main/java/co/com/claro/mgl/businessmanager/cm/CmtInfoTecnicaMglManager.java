/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtInfotecnicaMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroInformacionTecnicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtInfoTecnicaMglManager {

    public List<CmtInfoTecnicaMgl> findAll() throws ApplicationException {

        List<CmtInfoTecnicaMgl> resulList;
        CmtInfotecnicaMglDaoImpl cmtInfotecnicaMglDaoImpl = new CmtInfotecnicaMglDaoImpl();
        resulList = cmtInfotecnicaMglDaoImpl.findAll();
        return resulList;
    }

    public CmtInfoTecnicaMgl create(CmtInfoTecnicaMgl cmtInfoTecnicaMgl, String usuario, int perfil) throws ApplicationException {
        CmtInfotecnicaMglDaoImpl cmtInfotecnicaMglDaoImpl = new CmtInfotecnicaMglDaoImpl();
        return cmtInfotecnicaMglDaoImpl.createCm(cmtInfoTecnicaMgl, usuario, perfil);
    }

    public CmtInfoTecnicaMgl update(CmtInfoTecnicaMgl horario, String usuario, int perfil) throws ApplicationException {
        CmtInfotecnicaMglDaoImpl cmtInfotecnicaMglDaoImpl = new CmtInfotecnicaMglDaoImpl();
        return cmtInfotecnicaMglDaoImpl.updateCm(horario, usuario, perfil);
    }

    public boolean deleteCm(CmtInfoTecnicaMgl cmtInfoTecnicaMgl, String usuario, int perfil) throws ApplicationException {
        CmtInfotecnicaMglDaoImpl cmtInfotecnicaMglDaoImpl = new CmtInfotecnicaMglDaoImpl();
        return cmtInfotecnicaMglDaoImpl.deleteCm(cmtInfoTecnicaMgl, usuario, perfil);
    }

    public List<CmtInfoTecnicaMgl> findBySubEdificioId(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        List<CmtInfoTecnicaMgl> resulList;
        CmtInfotecnicaMglDaoImpl cmtInfotecnicaMglDaoImpl = new CmtInfotecnicaMglDaoImpl();
        resulList = cmtInfotecnicaMglDaoImpl.findBySubEdificioId(subEdificio);
        return resulList;
    }

    public FiltroInformacionTecnicaDto getInfoTecnicaSearch(BigDecimal subEdificioMgl, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtInfotecnicaMglDaoImpl cmtInfotecnicaMglDaoImpl = new CmtInfotecnicaMglDaoImpl();
        FiltroInformacionTecnicaDto filtroConsulta;

        filtroConsulta = cmtInfotecnicaMglDaoImpl.findInfoTecnicaSearch(subEdificioMgl, contar, firstResult, maxResults);
        return filtroConsulta;
    }
    
    public CmtInfoTecnicaMgl findBySubEdificioIdAndTecnoSub(CmtSubEdificioMgl subEdificioId, 
            CmtTecnologiaSubMgl tecnologiaSubMgl) throws ApplicationException {
        CmtInfotecnicaMglDaoImpl cmtInfotecnicaMglDaoImpl = new CmtInfotecnicaMglDaoImpl();
        return cmtInfotecnicaMglDaoImpl.findBySubEdificioIdAndTecnoSub(subEdificioId, tecnologiaSubMgl);

    }
}
