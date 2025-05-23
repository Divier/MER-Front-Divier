/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroConsultaDetalleFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ortizjaf
 */
@Local
public interface CmtDetalleFlujoMglFacadeLocal extends BaseFacadeLocal<CmtDetalleFlujoMgl> {

    List<CmtDetalleFlujoMgl> findByTipoFlujo(CmtBasicaMgl tipoFlujoOt,CmtBasicaMgl tecnologia) throws ApplicationException;

    List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoIni(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl estadoInicial,CmtBasicaMgl tecnologia) throws ApplicationException;

    FiltroConsultaDetalleFlujoDto findTablasDetalleFlujo(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults) throws ApplicationException;
    public void setUser(String user, int perfil) throws ApplicationException;
    
    /**
     * Busca una lista de  detalle flujo por identificador interno de la app
     *
     * @author Victor Bocanegra
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return List<CmtDetalleFlujoMgl> lista encontrada
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException;
    
    
    
    
                 /**
     * Validacion para evitar registros duplicados
     *
     * @author Lenis Cardenas
     * @param params
     * @param tipoFlujoOt Tipo de Flujo
     * @param estadoInterno Estado
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    List<CmtDetalleFlujoMgl> findByAllFields( HashMap<String, Object> params) throws ApplicationException;
    
        /**
     * Busca el datalle del Flujo por el tipo del Flujo, estado Proximo,
     * tecnologia que tengan estado razonado.
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt orden de trabajo
     * @param proximoEstado orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
   List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoProAndTecRazon(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl proximoEstado, CmtBasicaMgl tecnologia) throws ApplicationException;
}
