/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtDetalleFlujoMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaDetalleFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import java.util.HashMap;
import java.util.List;

/**
 * Manager Detalle Flujo de un tipo de Orden de Trabajo. Contiene la logica de
 * negocio del detalle del flujo de un tipo de ordenes de trabajo en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtDetalleFlujoMglManager {

    /**
     * Busca el datalle del Flujo por el tipo del Flujo.Permite realizar la
 busqueda del detalle del Flujo por medio del tipo de Flujo en el
 repositorio.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByTipoFlujo(CmtBasicaMgl tipoFlujoOt,CmtBasicaMgl tecnologia)
            throws ApplicationException {
        CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
        return dao.findByTipoFlujo(tipoFlujoOt,tecnologia);
    }

    /**
     * Busca el datalle del Flujo por el tipo del Flujo y el estado Inicial.Permite realizar la busqueda del detalle del Flujo por medio del tipo de
 Flujo y el estado Inicial en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt orden de trabajo
     * @param estadoInicial orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoIni(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl estadoInicial,CmtBasicaMgl tecnologia) throws ApplicationException {
        CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
        return dao.findByTipoFlujoAndEstadoIni(tipoFlujoOt, estadoInicial,tecnologia);
    }

    public CmtDetalleFlujoMgl create(CmtDetalleFlujoMgl cmtTipoBasicaMgl, String mUser, int mPerfil) throws ApplicationException {
        CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
        return dao.createCm(cmtTipoBasicaMgl, mUser, mPerfil);
    }

    public CmtDetalleFlujoMgl update(CmtDetalleFlujoMgl cmtTipoBasicaMgl, String mUser, int mPerfil) throws ApplicationException {
        CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
        return dao.updateCm(cmtTipoBasicaMgl, mUser, mPerfil);
    }

    public boolean delete(CmtDetalleFlujoMgl cmtTipoBasicaMgl, String mUser, int mPerfil) throws ApplicationException {
         CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
        return dao.deleteCm(cmtTipoBasicaMgl, mUser, mPerfil);
    }
    
     public FiltroConsultaDetalleFlujoDto findTablasEstadoxFlujo(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtDetalleFlujoMglDaoImpl dao
                = new CmtDetalleFlujoMglDaoImpl();

        FiltroConsultaDetalleFlujoDto filtroConsulta;

        if ((params.get("estadoxFlujoId") != null )) {
                params.put("estadoxFlujoId", params.get("estadoxFlujoId"));
                params.put("tipoFlujoOtObj", params.get("tipoFlujoOtObj"));
                params.put("nombreTipo", params.get("nombreTipo"));
                params.put("codigoTipo", params.get("codigoTipo"));
                params.put("descripcionTipo", params.get("descripcionTipo"));
                params.put("estadoRegistro", params.get("estadoRegistro"));
                params.put("modulo", params.get("modulo"));

            }
                
        

        return dao.findTablasTipoBasicaSearch(params, contar, firstResult, maxResults);
    }
     
    
    /**
     * Busca una lista de  detalle flujo por identificador interno de la app
     *
     * @author Victor Bocanegra
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return List<CmtDetalleFlujoMgl> lista encontrada
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
        return dao.findByCodigoInternoApp(codigoInternoApp);
    }
    
    
    
     /**
     * Valia si existen configuraciones duplicadas
     * tipo de flujo.
     *
     * @author Lenis Cardenas
     * @param params
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    
        public List<CmtDetalleFlujoMgl> findByAllFields(HashMap<String, Object> params) throws ApplicationException {
          CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
                return dao.findByAllFields(params);
        }
   
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
    public List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoProAndTecRazon(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl proximoEstado, CmtBasicaMgl tecnologia) throws ApplicationException {

        CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
        return dao.findByTipoFlujoAndEstadoProAndTecRazon(tipoFlujoOt, proximoEstado, tecnologia);
    }
    
    /**
     * Busca el datalle del Flujo por el tipo del Flujo, estado Inicial,
     * tecnologia que tengan estado razonado.
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt orden de trabajo
     * @param estadoInicial orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoIniAndTecRazon(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl estadoInicial, CmtBasicaMgl tecnologia) throws ApplicationException {

        CmtDetalleFlujoMglDaoImpl dao = new CmtDetalleFlujoMglDaoImpl();
        return dao.findByTipoFlujoAndEstadoIniAndTecRazon(tipoFlujoOt, estadoInicial, tecnologia);
    }
}
