/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVigenciaCostoItemMgl;

/**
 *
 * @author User
 */
public interface CmtVigenciaCostoItemMglFacadeLocal extends BaseFacadeLocal<CmtVigenciaCostoItemMgl> {

    public CmtVigenciaCostoItemMgl findByItemVigencia(CmtItemMgl itemObj) throws ApplicationException;

    @Override
    public CmtVigenciaCostoItemMgl create(CmtVigenciaCostoItemMgl t) throws ApplicationException;

    @Override
    public CmtVigenciaCostoItemMgl update(CmtVigenciaCostoItemMgl t) throws ApplicationException;

    @Override
    public boolean delete(CmtVigenciaCostoItemMgl t) throws ApplicationException;

    @Override
    public CmtVigenciaCostoItemMgl findById(CmtVigenciaCostoItemMgl sqlData) throws ApplicationException;
}