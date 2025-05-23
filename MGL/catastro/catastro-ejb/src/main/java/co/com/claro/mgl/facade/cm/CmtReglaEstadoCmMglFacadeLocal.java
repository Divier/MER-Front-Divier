/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaEstadoCmMgl;

/**
 *
 * @author Juan David Hernandez
 */
import java.util.List;

public interface CmtReglaEstadoCmMglFacadeLocal extends BaseFacadeLocal<CmtReglaEstadoCmMgl> {

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    List<CmtReglaEstadoCmMgl> findAllByStates(int ruleState) throws ApplicationException;

    int countRules(String tipoFiltro, String contentFiltro) throws ApplicationException;

    String addRule(List<CmtReglaEstadoCmMgl> combinedState) throws ApplicationException;

    Boolean disabledRule(String ruleNumber) throws ApplicationException;

        /**
     * obtiene listado de reglas estados combinados que se encuentre
     * habilitadoen paginación
     *
     * @param page
     * @param filas
     * @param state
     * @return listado de reglas filtrado por roles
     *
     */
    public List<CmtReglaEstadoCmMgl> findRulesPaginacion(int page, int filas, int state, String tipoFiltro, String contentFiltro) throws ApplicationException;

}