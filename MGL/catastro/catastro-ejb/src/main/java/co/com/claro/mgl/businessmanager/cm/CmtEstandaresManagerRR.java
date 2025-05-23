/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtEstandaresMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;

/**
 *
 * @author cardenaslb
 */
public class CmtEstandaresManagerRR {

    /**
     * Busca en la tabla mgl_estandares el valor de rr
     *
     * @param valor
     * @return
     * @throws ApplicationException
     */
    public String findByValor(String valor)
            throws ApplicationException {
        CmtEstandaresMglDaoImpl cmtEstandaresMglDaoImpl = new CmtEstandaresMglDaoImpl();
        return cmtEstandaresMglDaoImpl.findByValor(valor);
    }

}
