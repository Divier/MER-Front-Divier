/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.MglParametrosTrabajosDetailDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglParametrosTrabajosDetail;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;

/**
 *
 * @author Orlando Velasquez
 */
public class MglParametrosTrabajosDetailManager {

    MglParametrosTrabajosDetailDaoImpl mglParametrosTrabajosDetailDaoImpl = new MglParametrosTrabajosDetailDaoImpl();

    public MglParametrosTrabajosDetail createDetalleTareaProgramada(
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail) throws ApplicationException{
        return mglParametrosTrabajosDetailDaoImpl.createDetalleTareaProgramada(mglParametrosTrabajosDetail);
    }

    public MglParametrosTrabajosDetail updateDetalleTareaProgramada(
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail) throws ApplicationException {
        return mglParametrosTrabajosDetailDaoImpl.updateDetalleTareaProgramada(mglParametrosTrabajosDetail);
    }

    public int contarNumeroDeRegistrosDetalleTareaProgramada() throws ApplicationException {
        return mglParametrosTrabajosDetailDaoImpl.
                contarNumeroDeRegistrosDetalleTareaProgramada();
    }
    
    public MglParametrosTrabajosDetail findUltimoRegistroDetalleTareaProgramada( CmtBasicaMgl estadoTarea ) throws ApplicationException {
        return mglParametrosTrabajosDetailDaoImpl.
                findUltimoRegistroDetalleTareaProgramada( estadoTarea );
    }

}
