/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtEstadoxFlujoMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaEstadosxFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Manager Estados del Flujo de un tipo de Orden de Trabajo. Contiene la logica
 * de negocio de los estados del flujo de un tipo de ordenes de trabajo en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtEstadoxFlujoMglManager {

    CmtEstadoxFlujoMglDaoImpl dao = new CmtEstadoxFlujoMglDaoImpl();

    /**
     * Obtiene el estado inicial del flujo.Permite obtener el estado incial del
 flujo.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @return Estado Inicial del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl getEstadoInicialFlujo(CmtBasicaMgl tipoFlujoOt,CmtBasicaMgl tegnologia)
            throws ApplicationException {
        return dao.getEstadoInicialFlujo(tipoFlujoOt,tegnologia);
    }

    /**
     * Obtiene los estados de un tipo de flujo.Permite obtener los estados de
 un tipo de flujo.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @return Estados del Tipo de Flujo
     * @throws ApplicationException
     */
    public List<CmtEstadoxFlujoMgl> getEstadosByTipoFlujo(CmtBasicaMgl tipoFlujoOt,CmtBasicaMgl tegnologia)
            throws ApplicationException {
        return dao.getEstadosByTipoFlujo(tipoFlujoOt,tegnologia);
    }

    /**
     * Obtiene un estado por tipo de flujo y el estado.Permite obtener el
 detalle de un estado dentro de los estados de un Tipo de Flujo, filtrando
 por el Tipo de Flujo y el estado.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param estadoInterno Estado
     * @param tegnologia
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl findByTipoFlujoAndEstadoInt(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl estadoInterno,CmtBasicaMgl tegnologia) throws ApplicationException {
        return dao.findByTipoFlujoAndEstadoInt(tipoFlujoOt, estadoInterno,tegnologia);
    }
    
    
    public CmtEstadoxFlujoMgl create(CmtEstadoxFlujoMgl cmtTipoBasicaMgl,String mUser, int mPerfil) throws ApplicationException {
        return dao.createCm(cmtTipoBasicaMgl, mUser, mPerfil);
    }

    public CmtEstadoxFlujoMgl update(CmtEstadoxFlujoMgl cmtTipoBasicaMgl,String mUser, int mPerfil) throws ApplicationException {
        return dao.updateCm(cmtTipoBasicaMgl, mUser, mPerfil);
    }

    public boolean delete(CmtEstadoxFlujoMgl cmtTipoBasicaMgl,String mUser, int mPerfil) throws ApplicationException {
        return dao.deleteCm(cmtTipoBasicaMgl, mUser, mPerfil);
    }


    /**
     * Obtiene todos los estados X flujo activos. Permite obtener todos los
     * estados X flujo activos.
     *
     * @author Johnnatan Ortiz
     * @return Estados X Flujo
     * @throws ApplicationException
     */
    public List<CmtEstadoxFlujoMgl> findAllItemsActive() throws ApplicationException {
        return dao.findAllItemsActive();
    }

    /**
     * Obtiene los estados X flujo por ROL. Permite obtener los estados X flujo
     * por una lista de roles.
     *
     * @author Johnnatan Ortiz
     * @param gestionRolList lista de roles
     * @return estados X flujo
     * @throws ApplicationException
     */
    public List<CmtEstadoxFlujoMgl> findByInGestionRol(ArrayList<String> gestionRolList)
            throws ApplicationException {
        return dao.findByInGestionRol(gestionRolList);
    }

    public FiltroConsultaEstadosxFlujoDto findTablasEstadoxFlujo(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtEstadoxFlujoMglDaoImpl cmtEstadoxFlujoMglDaoImpl
                = new CmtEstadoxFlujoMglDaoImpl();

        if (params != null && (params.get("estadoxFlujoId") != null)) {
            params.put("estadoxFlujoId", params.get("estadoxFlujoId"));
            params.put("tipoFlujoOtObj", params.get("tipoFlujoOtObj"));
            params.put("nombreTipo", params.get("nombreTipo"));
            params.put("codigoTipo", params.get("codigoTipo"));
            params.put("descripcionTipo", params.get("descripcionTipo"));
            params.put("estadoRegistro", params.get("estadoRegistro"));
            params.put("modulo", params.get("modulo"));

        }
        return cmtEstadoxFlujoMglDaoImpl.findTablasTipoBasicaSearch(params, contar, firstResult, maxResults);
    }
    
    public boolean finalizoEstadosxFlujoDto(CmtBasicaMgl tipoFlujoOt,CmtBasicaMgl estadoInterno,CmtBasicaMgl tegnologia) 
            throws ApplicationException{
        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = dao.findByTipoFlujoAndEstadoInt(tipoFlujoOt, estadoInterno,tegnologia);
        if(cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getEsEstadoInicial().equals(Constant.ESTADO_FLUJO_CANCELADO)){
            return true;
        }
        if(cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getEsEstadoInicial().equals(Constant.ESTADO_FLUJO_FINALIZADO)){
            return true;
        }
        return false;
    }
    
           /**
     * Obtiene un estado de un tipo de FLujo. Permite obtener un estados de un
     * tipo de flujo.
     *
     * @author Victor Bocanegra 
     * @param id Tipo de Flujo
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl findById(BigDecimal id) throws ApplicationException {
        
          CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = 
                  dao.findById(id);
          
          return cmtEstadoxFlujoMgl;
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
    
        public List<CmtEstadoxFlujoMgl> findByAllFields(HashMap<String, Object> params) throws ApplicationException {
        
            List<CmtEstadoxFlujoMgl> cmtEstadoxFlujoMglList =
                    dao.findByAllFields(params);
          
          return cmtEstadoxFlujoMglList;
    }
        
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
            throws ApplicationException {

        List<CmtEstadoxFlujoMgl> cmtEstadoxFlujoMglList =
                dao.getEstadosByTipoFlujoAndTecAndCieCom(tipoFlujoOt, tegnologia, estadosInternosCerrarOt);

        return cmtEstadoxFlujoMglList;
    }
    
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
            CmtBasicaMgl tegnologia) throws ApplicationException {

        return dao.findByTipoFlujoAndTecnoAndCancelado(tipoFlujoOt, tegnologia);
    }
    
    /**
     * Obtiene el estado inicial del flujo.Permite obtener el estado incial del
     * flujo.
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @return Estado Inicial del Tipo de Flujo
     * @throws ApplicationException
     */
    public CmtEstadoxFlujoMgl findEstadoInicialFlujo(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl tegnologia)
            throws ApplicationException {
        return dao.findEstadoInicialFlujo(tipoFlujoOt, tegnologia);
    }
}
