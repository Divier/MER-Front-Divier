package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroConsultaSlaSolicitudDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import co.com.claro.mgl.dtos.FiltroConsultaSolicitudDto;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
public interface CmtTipoSolicitudMglFacadeLocal extends BaseFacadeLocal<CmtTipoSolicitudMgl> {

  List<CmtTipoSolicitudMgl> getByUsuarioRol(FacesContext facesContext) throws ApplicationException;

  FiltroConsultaSlaSolicitudDto findTablasSlaTipoSolicitud(FiltroConsultaSolicitudDto filtro,
            boolean contar, int firstResult, int maxResults) throws ApplicationException;

  /**
   * Obtiene el tipo de solicitud por idSolicitud
   *
   * @author Juan David Hernandez
   * @param idSolicitud
   *
   * @return CmtTipoSolicitudMgl
   *
   * @throws ApplicationException
   */
  CmtTipoSolicitudMgl findTipoSolicitudBySolicitud(BigDecimal idSolicitud)
          throws ApplicationException;

  /**
   * Obtiene el tipo de solicitud por Abreviatura
   *
   * @author Juan David Hernandez
   * @param abreviatura
   *
   * @return CmtTipoSolicitudMgl
   *
   * @throws ApplicationException
   */
  CmtTipoSolicitudMgl findTipoSolicitudByAbreviatura(String abreviatura)
          throws ApplicationException;

   /**
   * Obtiene listado de roles de gestion
   *
   * @author Juan David Hernandez
     * @param facesContext
     * @param tipoAplicacionTipoSolicitud
     * @param isCreacion
   * @return Listado de roles de gestion
   *
   * @throws ApplicationException
     * @throws java.io.IOException
   */
  List<CmtTipoSolicitudMgl> obtenerTipoSolicitudHhppByRolList(FacesContext facesContext, String tipoAplicacionTipoSolicitud, boolean isCreacion)
          throws ApplicationException, IOException;

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
  public String obtenerColorAlerta(
          CmtTipoSolicitudMgl tipoSolicitud,
          Date fechaIngreso) throws ApplicationException;

  /**
   * Buscar el color con la solicitud
   * Se busca el  color según la solicitud que sea ingresada
   * @param solAux solicitud
   * @return un String con el color representativo.
   * @throws ApplicationException si hay algún error en la solicitud
   */  
  public String obtenerColorAlerta(Solicitud solAux) 
          throws ApplicationException;
  
  /**
   * Buscar tipo CmtTipoSolicitudMgl
   * 
   * Se busca CmtTipoSolicitudMgl correspondiente al id
   * 
   * @param id del CmtTipoSolicitudMgl que voy a buscar
   * @return CmtTipoSolicitudMgl o null
   */
  public CmtTipoSolicitudMgl find(BigDecimal id);

  /**
   * Buscar el listado de tipos de solicitudes
   * @author becerraarmr
   * @return un listado de arreglos
   * @throws co.com.claro.mgl.error.ApplicationException
   */
  public List<Object[]> findTipoSolicitudes() throws ApplicationException;
  
  /**
   * Buscar la cantidad de registros.Busca en la base de datos los registros que cumplen con las condiciones
 adecuadas.
   * 
   * 
   * @param fechaInicial Date con el valor correspondiente.
   * @param fechaFinalizacion Date con el valor correspondiente.
   * @param tipoSolicitud Tipo de solicitud.
   * @param estado estado de la solicitud.
   * @return el valor entero de la cantidad de registros encontrados.
     * @throws co.com.claro.mgl.error.ApplicationException
   */
  public int numRegistroReporteDetalle(
          Date fechaInicial, 
          Date fechaFinalizacion, 
          String tipoSolicitud, 
          String estado) throws ApplicationException;

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
  public List<Object[]> buscarReporteDetalle(
          Date fechaInicial, Date fechaFinalizacion, String tipoSolicitud, 
          String estado, int finRegistros, int inicioRegistros, String usuarioLogin) throws  ApplicationException;
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
  public List<Object[]> buscarReporteGeneral(Date fechaI, Date fechaF, 
          String tipoSol, String estado,int[] range) throws ApplicationException ;
          
          /**
     * Consulta de tipo de solicitud de eliminaci&oacute;n
     * 
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud de eliminaci&oacute;n
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    CmtTipoSolicitudMgl obtenerSolicitudEliminacion() throws ApplicationException;

    /**
     * Consulta de tipo de solicitud de escalamiento de acometidas
     *
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud de escalamiento
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    CmtTipoSolicitudMgl obtenerSolicitudEscalamiento() throws ApplicationException;

    /**
     * Metodo privado para consultar las tipo de solicitud de CM Autor:Victor
     * Manuel Bocanegra
     *
     * @return List<CmtTipoSolicitudMgl>
     * @throws ApplicationException Excepcion lanzada en la consulta
     */
    List<CmtTipoSolicitudMgl> findByTipoApplication() throws ApplicationException;
    
      /**
   * Obtiene el tipo de solicitud por Abreviatura
   *
   * @author Juan David Hernandez
   * @param tipoSolicitudBasicaId
   *
   * @return CmtTipoSolicitudMgl
   *
   * @throws ApplicationException
     */
    CmtTipoSolicitudMgl findTipoSolicitudByTipoSolicitudBasicaId(CmtBasicaMgl tipoSolicitudBasicaId)
            throws ApplicationException;

}
