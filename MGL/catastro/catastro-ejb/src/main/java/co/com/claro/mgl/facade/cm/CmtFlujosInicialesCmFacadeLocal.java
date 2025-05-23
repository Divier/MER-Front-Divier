/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtFlujosInicialesCm;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public interface CmtFlujosInicialesCmFacadeLocal<T> {
     public CmtFlujosInicialesCm update(CmtFlujosInicialesCm t, String usuario, int perfilUsu) throws ApplicationException;

    public boolean delete(CmtFlujosInicialesCm t, String usuario, int perfilUsu) throws ApplicationException;

    public CmtFlujosInicialesCm create(CmtFlujosInicialesCm t, String usuario, int perfilUsu) throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;
    
    public List<CmtFlujosInicialesCm> getEstadosIniCMByEstadoxFlujoId(CmtEstadoxFlujoMgl tipoFlujoOt)
            throws ApplicationException;
}
