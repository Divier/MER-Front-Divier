/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.CmtReglaEstadoCmMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaEstadoCmMgl;

import java.util.List;

/**
 *
 * @author Juan David Hernandez 
 */
public class CmtReglaEstadoCmMglManager {
    
    CmtReglaEstadoCmMglDaoImpl CmtReglaEstadoCmMglDaoImpl = new CmtReglaEstadoCmMglDaoImpl();
    
    
     /**
     * Obtiene el listado de numero de reglas existentes.
     * 
     * @author Juan David Hernandez
     * @return Listado de numeros de id
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<String> findNumeroReglaList()
            throws ApplicationException {
        List<String> result = CmtReglaEstadoCmMglDaoImpl.findNumeroReglaList();
        return result;
    }
    
            /**
     * Obtiene una regla por numero de regla.
     * 
     * @author Juan David Hernandez
     * @param numeroRegla
     * @return Listado de numeros de id
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtReglaEstadoCmMgl> findReglaByNumeroRegla(String numeroRegla)
            throws ApplicationException {
        List<CmtReglaEstadoCmMgl> result = CmtReglaEstadoCmMglDaoImpl.findReglaByNumeroRegla(numeroRegla);
        return result;
    }

        /**
     * Obtiene lista de todas las reglas de acuerdo al estado como parametro
     *
     * @param state
     * @return
     * @throws ApplicationException
     */
    public List<CmtReglaEstadoCmMgl> findAllByState(int ruleState) throws ApplicationException {
        List<CmtReglaEstadoCmMgl> result = CmtReglaEstadoCmMglDaoImpl.findAllByState(ruleState);
        return result;
    }

    /**
     * Obtiene un conteo de todas las reglas de acuerdo al estado como parametro
     *
     * @param state
     * @return
     * @throws ApplicationException
     */
    public int countRules(String tipoFiltro, String contentFiltro) throws ApplicationException {
        return CmtReglaEstadoCmMglDaoImpl.countRules(tipoFiltro, contentFiltro);
    }

    /**
     * Metodo encargado de recorrer la lista y registrar
     *
     * @param combinedState
     * @return
     * @throws ApplicationException
     */
    public String addRule(List<CmtReglaEstadoCmMgl> combinedState, String usuario, int perfil) throws ApplicationException {
        int count = 0;
        // obtener la secuencia de la regla
        String numeroRegla = findMaxIdRule();
        for (CmtReglaEstadoCmMgl cmtReglaEstadoCmMgl : combinedState) {
            cmtReglaEstadoCmMgl.setNumeroRegla(numeroRegla);
            CmtReglaEstadoCmMglDaoImpl.createCm(cmtReglaEstadoCmMgl, usuario, perfil);
            count++;
        }

        return count == combinedState.size() ? numeroRegla : "";
    }

    public String findMaxIdRule() throws ApplicationException {
        return CmtReglaEstadoCmMglDaoImpl.findMaxIdRule();
    }

    public Boolean disabledRule(String ruleNumber) throws ApplicationException {
        return CmtReglaEstadoCmMglDaoImpl.disabledRule(ruleNumber);
    }

    public List<CmtReglaEstadoCmMgl> findRulesPaginacion(int paginaSelected, int maxResults, int state, String tipoFiltro, String contentFiltro) throws ApplicationException {
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return CmtReglaEstadoCmMglDaoImpl.findRulesPaginacion(firstResult, maxResults, state, tipoFiltro, contentFiltro);
    }
}
