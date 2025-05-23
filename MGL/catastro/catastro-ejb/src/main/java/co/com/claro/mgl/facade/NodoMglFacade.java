/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
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
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class NodoMglFacade implements NodoMglFacadeLocal {

    private final NodoMglManager nodoMglManager;

    public NodoMglFacade() {
        this.nodoMglManager = new NodoMglManager();
    }

    @Override
    public List<NodoMgl> findAll() throws ApplicationException {
        return nodoMglManager.findAll();
    }

    @Override
    public NodoMgl create(NodoMgl nodoMgl) throws ApplicationException {
        return nodoMglManager.create(nodoMgl);
    }

    @Override
    public NodoMgl update(NodoMgl nodoMgl) throws ApplicationException {
        return nodoMglManager.update(nodoMgl);
    }

    @Override
    public boolean delete(NodoMgl nodoMgl) throws ApplicationException {
        return nodoMglManager.delete(nodoMgl);
    }

    @Override
    public NodoMgl findById(BigDecimal id) throws ApplicationException {
        return nodoMglManager.findById(id);
    }

    @Override
    public NodoMgl findByName(String name) {
        return nodoMglManager.findByName(name);
    }

    @Override
    public List<GeograficoPoliticoMgl> getCitiesNodoByDivArea(BigDecimal divId, BigDecimal areId) throws ApplicationException {
        return nodoMglManager.getCitiesNodoByDivArea(divId, areId);
    }

    @Override
    public boolean vetoNodoByCitiesDivArea(List<GeograficoPoliticoMgl> cities, BigDecimal divisional, BigDecimal area, Date iniVeto, Date finVeto, String politica, String correo) throws ApplicationException {
        return nodoMglManager.vetoNodoByCitiesDivArea(cities, divisional, area, iniVeto, finVeto, politica, correo);
    }

    @Override
    public List<NodoMgl> findNodosByComDivArea(List<BigDecimal> comunidad, BigDecimal divisional, BigDecimal area, List<String> tipos) throws ApplicationException {
        return nodoMglManager.findNodosByComDivArea(comunidad, divisional, area, tipos);
    }

    @Override
    public boolean vetoNodosByCityDivAre(List<NodoMgl> nodoList, BigDecimal divisional, BigDecimal area, Date iniVeto, Date finVeto, String politica, String correo) throws ApplicationException {
        return nodoMglManager.vetoNodosByCityDivAre(nodoList, divisional, area, iniVeto, finVeto, politica, correo);
    }

    @Override
    public List<NodoMgl> findNodosByCitytipos(List<GeograficoPoliticoMgl> cities, BigDecimal divisional, BigDecimal area) throws ApplicationException {
        return nodoMglManager.findNodosByCitytipos(cities, divisional, area);
    }

    @Override
    public boolean isNodoCertificado(String nodo) throws ApplicationException {
        return nodoMglManager.isNodoCertificado(nodo);
    }

    @Override
    public NodoMgl findById(NodoMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NodoMgl> findByLikeCodigo(String codigo) throws ApplicationException {
        return nodoMglManager.findByLikeCodigo(codigo);
    }

    @Override
    public NodoMgl findByCodigo(String codigo) throws ApplicationException {
        return nodoMglManager.findByCodigo(codigo);
    }

    @Override
    public List<NodoMgl> findNodosByCity(BigDecimal gpoId) throws ApplicationException {
        return nodoMglManager.findNodosByCity(gpoId);
    }
    

    @Override
    public CmtNodoValidado validarNodo(String nodo, String userVt, String comunidad) throws ApplicationException {
        return nodoMglManager.validarNodo(nodo, userVt, comunidad);
    }    

     /**
     * Función que obtiene listado de nodos por centro poblado
     * y tipo de tecnología
     * 
     *@author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    @Override
    public List<NodoMgl> findNodosCentroPobladoAndTipoTecnologia(int page,
            int filas, BigDecimal idGpo, CmtBasicaMgl basicaTipoTecnologia)
            throws ApplicationException {
        return nodoMglManager.findNodosCentroPobladoAndTipoTecnologia(page, 
                filas,idGpo, basicaTipoTecnologia);
    }

    /**
     * Función que obtiene listado de nodos por ciudad y tipo de tecnología
     * 
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    @Override
    public List<NodoMgl> findNodosCiudadAndTipoTecnologia(int page, int filas,
            BigDecimal idGpo, CmtBasicaMgl basicaTipoTecnologia) 
            throws ApplicationException {
        return nodoMglManager.findNodosCiudadAndTipoTecnologia(page,
                filas,idGpo, basicaTipoTecnologia);
    }
    
    /**
     * Función que obtiene la cantidad total de nodos por centro poblado y 
     * de resultados.
     * 
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    @Override
      public int countNodosCentroPobladoAndTipoTecnologia
              (BigDecimal idGpo, CmtBasicaMgl basicaTipoTecnologia)
            throws ApplicationException {
        return nodoMglManager.countNodosCentroPobladoAndTipoTecnologia(
                idGpo, basicaTipoTecnologia);
    }
        
     /**
     * Función que obtiene la cantidad total de nodos por ciudad
     * y tipo de tecnología
     * 
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */   
    @Override
        public int countNodosCiudadAndTipoTecnologia(
                BigDecimal idGpo, CmtBasicaMgl basicaTipoTecnologia)
            throws ApplicationException {
        return nodoMglManager.countNodosCiudadAndTipoTecnologia(
                idGpo, basicaTipoTecnologia);
    }
        
        @Override
  public List<NodoMgl> findNodosCentroPobladoAndTipoTecnologia(BigDecimal idGpo, 
          CmtBasicaMgl basicaTipoTecnologia)throws ApplicationException  {
    return nodoMglManager.findNodosCentroPobladoAndTipoTecnologia(idGpo,basicaTipoTecnologia);
  }

  @Override
  public List<NodoMgl> findNodosCiudadAndTipoTecnologia(BigDecimal idGpo, 
          CmtBasicaMgl basicaTipoTecnologia) throws ApplicationException {
   return nodoMglManager.findNodosCiudadAndTipoTecnologia(idGpo,basicaTipoTecnologia);
  }
  
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
  @Override
  public List<NodoMgl> findNodos(CmtBasicaMgl nodTipo, 
          BigDecimal gpoId, String codNodo) throws ApplicationException {
    return nodoMglManager.findNodos(nodTipo,gpoId,codNodo);
  }
  
  /**
     * valbuenayf Metodo para buscar el nodo por el codigo del nodo, id de la
     * ciudad y el id de la tecnologia
     *
     * @param codigo
     * @param idCentroPoblado
     * @param idTecnologia
     * @return
     * @throws ApplicationException
     */
    @Override
    public NodoMgl findByCodigoNodo(String codigo, BigDecimal idCentroPoblado, BigDecimal idTecnologia) throws ApplicationException {
        return nodoMglManager.findByCodigoNodo(codigo, idCentroPoblado, idTecnologia);
    }

    /**
     * *Victor Bocanegra Metodo para buscar los nodos paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<NodoMgl>
     * @throws ApplicationException
     */
    @Override
    public PaginacionDto<NodoMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaNodosDto consulta, 
            GeograficoPoliticoManager geograficoPoliticoManager,
            boolean soloConteoNodos) throws ApplicationException {

        return nodoMglManager.findAllPaginado(paginaSelected, maxResults, consulta,
                geograficoPoliticoManager, soloConteoNodos);
    }
    
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
    @Override
    public CmtNodoValidado validarNodoModCM(String nodoValidar,
            GeograficoPoliticoMgl geograficoPoliticoMgl, CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {

        return nodoMglManager.validarNodoModCM(nodoValidar, geograficoPoliticoMgl, cmtBasicaMgl);
    }
    
    /**
     * *Victor Bocanegra busqueda del nodo por codigo y comunidaddRR.
     *
     * @param codigo
     * @param cmtComunidadRr
     * @return NodoMgl
     * @throws ApplicationException
     */
    @Override
    public NodoMgl findByCodigoAndComunidadRR(String codigo, CmtComunidadRr cmtComunidadRr)
            throws ApplicationException {

        return nodoMglManager.findByCodigoAndComunidadRR(codigo, cmtComunidadRr);

    }
   
    /**
     * *Victor Bocanegra Metodo para buscar los nodos por los filtros
     *
     * @param consulta
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
    @Override
    public List<NodoMgl> findByFiltroExportar(CmtFiltroConsultaNodosDto consulta)
            throws ApplicationException {

        return nodoMglManager.findByFiltroExportar(consulta);
    }
    
    /**
     * Victor Bocanegra Metodo para consultar un nodo por el geo
     *
     * @param ot
     * @return NodoMgl
     * @throws ApplicationException
     */
    @Override
    public NodoMgl consultaGeo(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        return nodoMglManager.consultaGeo(ot);
    }
    
    /**
     * Victor Bocanegra Metodo para consultar lista de codigos de nodo por
     * centro poblado
     *
     * @param centroPoblado
     * @return List<String>
     * @throws ApplicationException
     */
    @Override
    public List<String> findCodigoNodoByCentroP(BigDecimal centroPoblado)
            throws ApplicationException {
       
        return nodoMglManager.findCodigoNodoByCentroP(centroPoblado);
    }
    
      /**
     * cardenaslb Metodo para consultar lista de codigos de nodo por
     * centro poblado
     *
     * @param centroPoblado
     * @return List<String>
     * @throws ApplicationException
     */
    @Override
     public List<NodoMgl> findCodigosByCentroPoblado(BigDecimal centroPoblado)
            throws ApplicationException {
         return  nodoMglManager.findCodigosByCentroPoblado(centroPoblado);
     }
     
      /**
     * Juan David Hernandez para consultar lista de codigos de nodo por
     * centro poblado
     *
     * @param centroPoblado
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
     @Override
    public List<NodoMgl> find5NodosByCentroPobladoList(BigDecimal centroPoblado)
            throws ApplicationException {
         return  nodoMglManager.find5NodosByCentroPobladoList(centroPoblado);
     }

    @Override
    public List<NodoMerDto> findAllPaginadoExport(int paginaSelected, int maxResults, CmtFiltroConsultaNodosDto consulta, boolean soloConteoNodos) throws ApplicationException {
        return nodoMglManager.findAllPaginadoExport(paginaSelected, maxResults, consulta);
    }

    @Override
    public Integer countAllPaginadoExport(CmtFiltroConsultaNodosDto consulta) throws ApplicationException {
        return nodoMglManager.countAllPaginadoExport(consulta);
    }

    /**
     * @author dayver.delahoz@vasslatam.com
     * @version Brief100417
     * @return lista de estados de nodos disponibles
     * @throws ApplicationException
     */
    @Override
    public List<NodoEstadoDTO> consultarEstadosDeNodo() throws ApplicationException {
        return nodoMglManager.consultarEstadoNodos();
    }
}
