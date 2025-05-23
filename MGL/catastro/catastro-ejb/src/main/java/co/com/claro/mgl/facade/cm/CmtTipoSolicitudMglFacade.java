package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoSolicitudMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaSlaSolicitudDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import co.com.claro.mgl.dtos.FiltroConsultaSolicitudDto;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtTipoSolicitudMglFacade implements CmtTipoSolicitudMglFacadeLocal {

    @Override
    public List<CmtTipoSolicitudMgl> findAll() throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.findAll();
    }

    @Override
    public CmtTipoSolicitudMgl create(CmtTipoSolicitudMgl t) throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.create(t);
    }

    @Override
    public CmtTipoSolicitudMgl update(CmtTipoSolicitudMgl t) throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.update(t);
    }

    @Override
    public boolean delete(CmtTipoSolicitudMgl t) throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.delete(t);
    }

    @Override
    public CmtTipoSolicitudMgl findById(CmtTipoSolicitudMgl sqlData) throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.findById(sqlData);
    }

    @Override
    public List<CmtTipoSolicitudMgl> getByUsuarioRol(FacesContext facesContext) throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.getByUsuarioRol(facesContext);
    }

    @Override
    public CmtTipoSolicitudMgl findTipoSolicitudBySolicitud(BigDecimal idSolicitud) throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.findTipoSolicitudBySolicitud(idSolicitud);
    }

    @Override
    public FiltroConsultaSlaSolicitudDto findTablasSlaTipoSolicitud(FiltroConsultaSolicitudDto filtro, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.findTablasSlaTipoSolicitud(filtro, contar, firstResult, maxResults);
    }

    /**
     * Obtiene el tipo de solicitud por Abreviatura
     *
     * @author Juan David Hernandez
     * @param abreviatura
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    @Override
    public CmtTipoSolicitudMgl findTipoSolicitudByAbreviatura(String abreviatura)
            throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager
                .findTipoSolicitudByAbreviatura(abreviatura);
    } 

    /**
     * Obtiene listado de roles de gestion
     *
     * @author Juan David Hernandez
     * @return Listado de roles de gestion
     * @throws ApplicationException
     * @throws java.io.IOException
     */
    @Override
    public List<CmtTipoSolicitudMgl> obtenerTipoSolicitudHhppByRolList(FacesContext facesContext,  String tipoAplicacionTipoSolicitud, boolean isCreacion)
            throws ApplicationException, IOException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.obtenerTipoSolicitudHhppByRolList(facesContext, tipoAplicacionTipoSolicitud, isCreacion);
    }
    /**
     * {@inheritDoc }
     * 
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud de eliminaci&oacute;n
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    @Override
    public CmtTipoSolicitudMgl obtenerSolicitudEliminacion() throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.obtenerSolicitudEliminacion();
    }

    /**
     * {@inheritDoc }
     * 
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud de escalamiento
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    @Override
    public CmtTipoSolicitudMgl obtenerSolicitudEscalamiento() throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.obtenerSolicitudEscalamiento();
    }


  /**
   * Buscar el color.
   * 
   * Muestra el color según los parámetros establecidos.
   *
   * @author becerraarmr
   * @param tipoSolicitud tipo de solicitud
   * @param fechaIngreso  fecha en la que se registró
   * @return un String con el nombre del color
   * @throws co.com.claro.mgl.error.ApplicationException
   */
  @Override
  public String obtenerColorAlerta(CmtTipoSolicitudMgl tipoSolicitud, 
          Date fechaIngreso) throws ApplicationException {
    CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager
            = new CmtTipoSolicitudMglManager();
    return cmtTipoSolicitudMglManager.obtenerColorAlerta(
            tipoSolicitud, fechaIngreso);
  }

  /**
   * Buscar el color con la solicitud
   * Se busca el  color según la solicitud que sea ingresada
   * @param solAux solicitud
   * @return un String con el color representativo.
   * @throws ApplicationException si hay algún error en la solicitud
   */  
  @Override
  public String obtenerColorAlerta(Solicitud solAux) 
          throws ApplicationException {
    CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager
            = new CmtTipoSolicitudMglManager();
    return cmtTipoSolicitudMglManager.obtenerColorAlerta(solAux);
  }

  /**
   * Buscar tipo CmtTipoSolicitudMgl
   * 
   * Se busca CmtTipoSolicitudMgl correspondiente al id
   * 
   * @param id del CmtTipoSolicitudMgl que voy a buscar
   * @return CmtTipoSolicitudMgl o null
   */
  @Override
  public CmtTipoSolicitudMgl find(BigDecimal id) {
    CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager
            = new CmtTipoSolicitudMglManager();
    return cmtTipoSolicitudMglManager.find(id);
  }

  /**
   * Buscar el listado de tipos de solicitudes
   * @author becerraarmr
   * @return un listado de arreglos
     * @throws co.com.claro.mgl.error.ApplicationException
   */
  @Override
  public List<Object[]> findTipoSolicitudes() throws ApplicationException {
    CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = 
            new CmtTipoSolicitudMglManager();
    return cmtTipoSolicitudMglManager.findTipoSolicitudes();
  }

  /**
   * Buscar la cantidad de registros.Busca en la base de datos los registros que cumplen con las condiciones
 adecuadas.
   * 
   * 
   * @param fechaInicial Date con el valor correspondiente.
   * @param fechaFinalizacion Date con el valor correspondiente.
   * @param idTipoSolicitud Tipo de solicitud.
   * @param estado estado de la solicitud.
   * @return el valor entero de la cantidad de registros encontrados.
     * @throws co.com.claro.mgl.error.ApplicationException
   */
  @Override
  public int numRegistroReporteDetalle(Date fechaInicial, Date fechaFinalizacion, 
          String idTipoSolicitud, String estado) throws ApplicationException{
      
    CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = 
           new CmtTipoSolicitudMglManager();
    
    return cmtTipoSolicitudMglManager.numRegistroReporteDetalle(
           fechaInicial, 
           fechaFinalizacion, 
           idTipoSolicitud, 
           estado);
  }

  /**
   * Buscar listado de registros para reporte detalle
   * 
   * Busca en la base de datos los registros que cumplen con las condiciones 
   * adecuadas.
   * 
   * @author becerraarmr
   * @param fechaInicial Date con el valor correspondiente.
   * @param fechaFinalizacion Date con el valor correspondiente.
   * @param tipoSolicitud tipo de solicitud
   * @param estado estado de la solicitud.
   * @param finRegistros
   * @param inicioRegistros
   * @return el valor entero de la cantidad de registros encontrados.
     * @throws co.com.claro.mgl.error.ApplicationException
   */
  @Override
  public List<Object[]> buscarReporteDetalle(Date fechaInicial, 
          Date fechaFinalizacion, String tipoSolicitud, 
          String estado, int finRegistros, int inicioRegistros, String usuarioLogin) throws  ApplicationException{
      
    CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = 
           new CmtTipoSolicitudMglManager();
    
    return cmtTipoSolicitudMglManager.buscarReporteDetalle(
           fechaInicial,fechaFinalizacion,tipoSolicitud,estado,
            finRegistros,inicioRegistros, usuarioLogin);
  }

  /**
   * Buscar data reporte General
   * 
   * Busca la data que corresponde según los datos enviados para el reporte
   * general.
   * @author becerraarmr
   * @param fechaI fecha de inicio
   * @param fechaF fecha final
   * @param tipoSol tipo de solicitud
   * @param estado estado del reporte
   * @param range rango de busqueda
   * @return un listado con los arreglos encontrados
     * @throws co.com.claro.mgl.error.ApplicationException
   */
  @Override
  public List<Object[]> buscarReporteGeneral(Date fechaI, Date fechaF, 
          String tipoSol, String estado, int[] range) throws ApplicationException  {
    CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = 
           new CmtTipoSolicitudMglManager();
    return cmtTipoSolicitudMglManager.buscarReporteGeneral(
           fechaI,fechaF,tipoSol,estado,range);
  }

    /**
     * Metodo privado para consultar las tipo de solicitud de CM Autor:Victor
     * Manuel Bocanegra
     *
     * @return List<CmtTipoSolicitudMgl>
     * @throws ApplicationException Excepcion lanzada en la consulta
     */
    @Override
    public List<CmtTipoSolicitudMgl> findByTipoApplication() throws ApplicationException {

        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager =
                new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager.findByTipoApplication();
    }
    
        /**
     * Obtiene el tipo de solicitud por tipoSolicitudBasicaId
     *
     * @author Juan David Hernandez
     * @param tipoSolicitudBasicaId
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    @Override
    public CmtTipoSolicitudMgl findTipoSolicitudByTipoSolicitudBasicaId(CmtBasicaMgl tipoSolicitudBasicaId)
            throws ApplicationException {
        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
        return cmtTipoSolicitudMglManager
                .findTipoSolicitudByTipoSolicitudBasicaId(tipoSolicitudBasicaId);
    } 
}
