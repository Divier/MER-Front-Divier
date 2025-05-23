/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtBasicaExtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtBasicaExtMglManager {
    
    
    public  CmtBasicaExtMglManager() {
        
    }

    public List<CmtBasicaExtMgl> findAll()
            throws ApplicationException {
        List<CmtBasicaExtMgl> resulList;
        CmtBasicaExtMglDaoImpl cmtBasicaExtMglDaoImpl = new CmtBasicaExtMglDaoImpl();
        resulList = cmtBasicaExtMglDaoImpl.findAllItems();
        return resulList;
    }

    public CmtBasicaExtMgl createCamposAdic(CmtBasicaExtMgl cmtBasicaExtMgl, String mUser, int mPerfil)
            throws ApplicationException {
        CmtBasicaExtMglDaoImpl cmtBasicaExtMglDaoImpl = new CmtBasicaExtMglDaoImpl();
        if ((cmtBasicaExtMgl == null)) {
            return cmtBasicaExtMglDaoImpl.createCm(cmtBasicaExtMgl, mUser, mPerfil);
        } else {
            throw new ApplicationException("No se pudo crear el registro "
                    + " en RR, de tipo " + cmtBasicaExtMgl.getIdTipoBasicaExt().getIdTipoBasica().getNombreTipo());
        }

    }

    public CmtBasicaExtMgl createUpdateAdic(CmtBasicaExtMgl cmtBasicaExtMgl, String mUser, int mPerfil)
            throws ApplicationException {
        CmtBasicaExtMglDaoImpl cmtBasicaExtMglDaoImpl = new CmtBasicaExtMglDaoImpl();
        if ((cmtBasicaExtMgl == null)) {
            return cmtBasicaExtMglDaoImpl.updateCm(cmtBasicaExtMgl, mUser, mPerfil);
        } else {
            throw new ApplicationException("No se pudo actualizar el registro "
                    + " en RR, de tipo " + cmtBasicaExtMgl.getIdTipoBasicaExt().getIdTipoBasica().getNombreTipo());
        }

    }

    public List<CmtBasicaExtMgl> getCamposBasicaExt(CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {
        CmtBasicaExtMglDaoImpl cmtBasicaExtMglDaoImpl = new CmtBasicaExtMglDaoImpl();
        return cmtBasicaExtMglDaoImpl.findByBasicaId(cmtBasicaMgl);
    }
    
    /**
     * Consulta lista de extendidas de una basica por el id del tipo basica ext
     *
     * @author Victor Bocanegra
     * @param tipoBasicaExtMgl
     * @return List<CmtBasicaExtMgl>
     * @throws ApplicationException
     */
    public List<CmtBasicaExtMgl> findByTipoBasicaExt(CmtTipoBasicaExtMgl tipoBasicaExtMgl)
            throws ApplicationException {

        CmtBasicaExtMglDaoImpl cmtBasicaExtMglDaoImpl = new CmtBasicaExtMglDaoImpl();
        return cmtBasicaExtMglDaoImpl.findByTipoBasicaExt(tipoBasicaExtMgl);

    }
  
    /**
     * Consulta lista de extendidas de una basica por el id del tipo basica ext
     * y por la basica
     *
     * @author Victor Bocanegra
     * @param tipoBasicaExtMgl
     * @param basicaMgl
     * @return List<CmtBasicaExtMgl>
     * @throws ApplicationException
     */
    public List<CmtBasicaExtMgl> findByTipoBasicaExtByBasica(List<CmtTipoBasicaExtMgl> lstTipoBasicaExtMgl,
            CmtBasicaMgl basicaMgl)
            throws ApplicationException {

        CmtBasicaExtMglDaoImpl cmtBasicaExtMglDaoImpl = new CmtBasicaExtMglDaoImpl();
        return cmtBasicaExtMglDaoImpl.findByTipoBasicaExtByBasica(lstTipoBasicaExtMgl, basicaMgl);

    }
}
