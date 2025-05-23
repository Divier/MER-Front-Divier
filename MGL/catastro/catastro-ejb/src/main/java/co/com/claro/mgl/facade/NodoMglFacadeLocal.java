/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.dtos.CmtFiltroConsultaNodosDto;
import co.com.claro.mgl.dtos.CmtNodoValidado;
import co.com.claro.mgl.dtos.NodoEstadoDTO;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.rest.dtos.NodoMerDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public interface NodoMglFacadeLocal extends BaseFacadeLocal<NodoMgl> {

    List<GeograficoPoliticoMgl> getCitiesNodoByDivArea(BigDecimal divId, BigDecimal areId) throws ApplicationException;

    boolean vetoNodoByCitiesDivArea(List<GeograficoPoliticoMgl> cities, BigDecimal divisional, BigDecimal area, Date iniVeto, Date finVeto, String politica, String correo) throws ApplicationException;

    List<NodoMgl> findNodosByComDivArea(List<BigDecimal> comunidad, BigDecimal divisional, BigDecimal area, List<String> tipos) throws ApplicationException;

    boolean vetoNodosByCityDivAre(List<NodoMgl> nodoList, BigDecimal divisional, BigDecimal area, Date iniVeto, Date finVeto, String politica, String correo) throws ApplicationException;

    List<NodoMgl> findNodosByCitytipos(List<GeograficoPoliticoMgl> cities, BigDecimal divisional, BigDecimal area) throws ApplicationException;

    boolean isNodoCertificado(String nodo) throws ApplicationException;

    NodoMgl findById(BigDecimal id) throws ApplicationException;

    NodoMgl findByName(String name);

    List<NodoMgl> findByLikeCodigo(String codigo) throws ApplicationException;

    NodoMgl findByCodigo(String codigo) throws ApplicationException;

    List<NodoMgl> findNodosByCity(BigDecimal gpoId) throws ApplicationException;
    
    CmtNodoValidado validarNodo(String nodo,String userVt, String comunidad)throws ApplicationException;
    
     /**
     * Función que obtiene listado de nodos por centro poblado
     * y tipo de tecnología
     * 
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public List<NodoMgl> findNodosCentroPobladoAndTipoTecnologia(
            int page, int filas,BigDecimal idGpo, CmtBasicaMgl basicaTipoTecnologia) 
            throws ApplicationException;

     /**
     * Función que obtiene listado de nodos por ciudad y tipo de tecnología
     * 
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public List<NodoMgl> findNodosCiudadAndTipoTecnologia(int page, 
            int filas,BigDecimal idGpo,
            CmtBasicaMgl basicaTipoTecnologia) throws ApplicationException;
    
     /**
     * Función que obtiene la cantidad total de nodos por centro poblado y 
     * de resultados.
     * 
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public int countNodosCentroPobladoAndTipoTecnologia(
            BigDecimal idGpo, CmtBasicaMgl basicaTipoTecnologia) throws ApplicationException;

     /**
     * Función que obtiene la cantidad total de nodos por ciudad
     * y tipo de tecnología
     * 
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public int countNodosCiudadAndTipoTecnologia(BigDecimal idGpo,
            CmtBasicaMgl basicaTipoTecnologia) throws ApplicationException;
    
    public List<NodoMgl> findNodosCentroPobladoAndTipoTecnologia(
          BigDecimal idGpo, CmtBasicaMgl basicaTipoTecnologia)throws ApplicationException ;

  public List<NodoMgl> findNodosCiudadAndTipoTecnologia(BigDecimal idGpo, 
          CmtBasicaMgl basicaTipoTecnologia)throws ApplicationException ;
  
  /**
   * Buscar nodos
   * Solicita la busqueda de los nodos según los parámetros
   * @author becerraarmr
   * @param nodTipo tipo de tecnología
   * @param gpoId centro poblado
   * @param codNodo código del nodo.
   * @return listado de nodos encontrados
   * @throws ApplicationException si hay un error en la busqueda
   */
  public List<NodoMgl> findNodos(CmtBasicaMgl nodTipo, BigDecimal gpoId, String codNodo)
          throws ApplicationException ;
          
  NodoMgl findByCodigoNodo(String codigo, BigDecimal idCentroPoblado, BigDecimal idTecnologia) throws ApplicationException;
    
     /**
     * *Victor Bocanegra Metodo para buscar los nodos paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @param geograficoPoliticoManager
     * @param soloConteoNodos
     * @return PaginacionDto<NodoMgl>
     * @throws ApplicationException
     */
    public PaginacionDto<NodoMgl> findAllPaginado(int paginaSelected,
            int maxResults,CmtFiltroConsultaNodosDto consulta,
            GeograficoPoliticoManager geograficoPoliticoManager,
            boolean soloConteoNodos) throws ApplicationException;

    /**
     * *Victor Bocanegra Metodo para validar los nuevos nodos para modificar
     * CM.
     *
     * @param nodoValidar
     * @param geograficoPoliticoMgl
     * @param cmtBasicaMgl
     * @return CmtNodoValidado
     * @throws ApplicationException
     */
    public CmtNodoValidado validarNodoModCM(String nodoValidar,
            GeograficoPoliticoMgl geograficoPoliticoMgl, CmtBasicaMgl cmtBasicaMgl) 
            throws ApplicationException;
    
    /**
     * *Victor Bocanegra busqueda del nodo por codigo y comunidaddRR.
     *
     * @param codigo
     * @param cmtComunidadRr
     * @return NodoMgl
     * @throws ApplicationException
     */
    public NodoMgl findByCodigoAndComunidadRR(String codigo, CmtComunidadRr cmtComunidadRr)
            throws ApplicationException;
    
   /**
     * Victor Bocanegra Metodo para buscar los nodos por los filtros
     *
     * @param consulta
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
    List<NodoMgl> findByFiltroExportar(CmtFiltroConsultaNodosDto consulta)
            throws ApplicationException;
    
    /**
     * Victor Bocanegra Metodo para consultar un nodo por el geo
     *
     * @param ot
     * @return NodoMgl
     * @throws ApplicationException
     */
    NodoMgl consultaGeo(CmtOrdenTrabajoMgl ot) throws ApplicationException; 
    
    /**
     * Victor Bocanegra Metodo para consultar lista de codigos de nodo por
     * centro poblado
     *
     * @param centroPoblado
     * @return List<String>
     * @throws ApplicationException
     */
     List<String> findCodigoNodoByCentroP(BigDecimal centroPoblado)
            throws ApplicationException;
     
     List<NodoMgl> findCodigosByCentroPoblado(BigDecimal centroPoblado)
            throws ApplicationException ;
     
     /**
     * Juan David Hernandez para consultar lista de codigos de nodo por
     * centro poblado
     *
     * @param centroPoblado
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
      public List<NodoMgl> find5NodosByCentroPobladoList(BigDecimal centroPoblado)
            throws ApplicationException;


    public List<NodoMerDto> findAllPaginadoExport(int paginaSelected, int maxResults, CmtFiltroConsultaNodosDto consulta,
                                                  boolean soloConteoNodos) throws ApplicationException;

    public Integer countAllPaginadoExport(CmtFiltroConsultaNodosDto consulta) throws ApplicationException;

    List<NodoEstadoDTO> consultarEstadosDeNodo() throws ApplicationException;
}