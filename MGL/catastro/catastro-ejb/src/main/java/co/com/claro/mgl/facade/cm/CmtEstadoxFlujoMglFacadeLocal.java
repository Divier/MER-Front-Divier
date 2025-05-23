/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroConsultaEstadosxFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ortizjaf
 */
public interface CmtEstadoxFlujoMglFacadeLocal {

    public CmtEstadoxFlujoMgl getEstadoInicialFlujo(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl tegnologia) throws ApplicationException;

    public List<CmtEstadoxFlujoMgl> getEstadosByTipoFlujo(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl tegnologia) throws ApplicationException;

    public FiltroConsultaEstadosxFlujoDto findTablasEstadoxFlujo(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults) throws ApplicationException;
    
    public boolean finalizoEstadosxFlujoDto(CmtBasicaMgl tipoFlujoOt,CmtBasicaMgl estadoInterno, CmtBasicaMgl tegnologia) throws ApplicationException;
    
    public void setUser(String user, int perfil) throws ApplicationException;
    
               /**
     * Obtiene un estado de un tipo de FLujo. Permite obtener un estados de un
     * tipo de flujo.
     *
     * @author Victor Bocanegra 
     * @param id Tipo de Flujo
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
     public CmtEstadoxFlujoMgl findById(BigDecimal id) throws ApplicationException;
    
            /**
     * Obtiene un estado por tipo de flujo y el estado.Permite obtener el
 detalle de un estado dentro de los estados de un Tipo de Flujo, filtrando
 por el Tipo de Flujo y el estado.
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt Tipo de Flujo
     * @param estadoInterno Estado
     * @param tegnologia
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
     public CmtEstadoxFlujoMgl findByTipoFlujoAndEstadoInt(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl estadoInterno,CmtBasicaMgl tegnologia) throws ApplicationException;
     
                 /**
     * Validacion para evitar registros duplicados
     *
     * @author Lenis Cardenas
     * @param params
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    public List<CmtEstadoxFlujoMgl> findByAllFields( HashMap<String, Object> params) throws ApplicationException;
    
    /**
     * Obtiene los estados del tipo de flujo ot Vt con estados internos de
     * cierre.
     *
     * @author Victor Bocanegra Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @param estadosInternosCerrarOt estados de cierre comercial
     * @return List<CmtEstadoxFlujoMgl>
     * @throws ApplicationException
     */
    public List<CmtEstadoxFlujoMgl> getEstadosByTipoFlujoAndTecAndCieCom(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl tegnologia, List<BigDecimal> estadosInternosCerrarOt)
            throws ApplicationException;
    /**
     * Obtiene un estado de un tipo de FLujo con estado inicial 'C'
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia tipo tecnologia
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl findByTipoFlujoAndTecnoAndCancelado(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl tegnologia) throws ApplicationException;
    
     /**
     * Crea un estado X FLujo en el repositorio
     *
     * @author Victor Bocanegra
     * @param t
     * @param usuario
     * @param perfilUsu
     * @return CmtEstadoxFlujoMgl
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl create(CmtEstadoxFlujoMgl t, String usuario, int perfilUsu) throws ApplicationException; 
    
     /**
     * Actualiza un estado X FLujo en el repositorio
     *
     * @author Victor Bocanegra
     * @param t
     * @param usuario
     * @param perfilUsu
     * @return CmtEstadoxFlujoMgl
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl update(CmtEstadoxFlujoMgl t, String usuario, int perfilUsu) throws ApplicationException; 
    
     /**
     * elimina un estado X FLujo en el repositorio
     *
     * @author Victor Bocanegra
     * @param t
     * @param usuario
     * @param perfilUsu
     * @return boolean
     * @throws ApplicationException
     */
    public boolean delete(CmtEstadoxFlujoMgl t, String usuario, int perfilUsu) throws ApplicationException; 
}
