package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.SolicitudManager;
import co.com.claro.mgl.dao.impl.cm.CmtTipoSolicitudMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaSlaSolicitudDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.dtos.FiltroConsultaSolicitudDto;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
public class CmtTipoSolicitudMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtTipoSolicitudMglManager.class);

    public List<CmtTipoSolicitudMgl> findAll() throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.findAll();
    }

    public CmtTipoSolicitudMgl create(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.create(cmtTipoSolicitudMgl);
    }

    public CmtTipoSolicitudMgl update(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.update(cmtTipoSolicitudMgl);
    }

    public boolean delete(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.delete(cmtTipoSolicitudMgl);
    }

    public CmtTipoSolicitudMgl findById(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.find(cmtTipoSolicitudMgl.getTipoSolicitudId());
    }

    public List<CmtTipoSolicitudMgl> getByUsuarioRol(FacesContext facesContext) throws ApplicationException {
        List<CmtTipoSolicitudMgl> allTiposSolicitudes = findByTipoApplication();
        List<CmtTipoSolicitudMgl> allTiposSolicitudesUser = null;
        SecurityLogin securityLogin;
        try {
            if (allTiposSolicitudes != null && !allTiposSolicitudes.isEmpty()) {
                allTiposSolicitudesUser = new ArrayList<>();
                securityLogin = new SecurityLogin(facesContext);
                for (CmtTipoSolicitudMgl t : allTiposSolicitudes) {
                    if (securityLogin.usuarioTieneRoll(t.getGestionRol())) {
                        allTiposSolicitudesUser.add(t);
                    }
                }
            }
            if (allTiposSolicitudesUser != null && allTiposSolicitudesUser.isEmpty()) {
                allTiposSolicitudesUser = null;
            }
        } catch (IOException e) {
            LOGGER.error("Error al momento de obtener el tipo de solicitud. EX000: " + e.getMessage(), e);
        }
        return allTiposSolicitudesUser;
    }

    /**
     * Obtiene tipo de solicitud por solicitud
     *
     * @author Juan David Hernandez
     * @param idSolicitud
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTipoSolicitudMgl findTipoSolicitudBySolicitud(BigDecimal idSolicitud)
            throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl
                .findTipoSolicitudBySolicitud(idSolicitud);
    }

    public FiltroConsultaSlaSolicitudDto findTablasSlaTipoSolicitud(FiltroConsultaSolicitudDto filtro, boolean contar, int firstResult, int maxResults) throws ApplicationException {

        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.findTablasSlaTipoSolicitud(filtro, contar, firstResult, maxResults);
    }

    /**
     * Obtiene el tipo de solicitud por Abreviatura
     *
     * @author Juan David Hernandez
     * @param abreviatura
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTipoSolicitudMgl findTipoSolicitudByAbreviatura(String abreviatura)
            throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl
                .findTipoSolicitudByAbreviatura(abreviatura);
    } 

    /**
     * Obtiene listado de roles de gestion
     *
     * @author Juan David Hernandez
     * @param facesContext
     * @param tipoAplicacionTipoSolicitud
     * @return Listado de roles de gestion
     * @throws ApplicationException
     * @throws java.io.IOException
     */
    public List<CmtTipoSolicitudMgl> obtenerTipoSolicitudHhppByRolList(FacesContext facesContext, String tipoAplicacionTipoSolicitud, boolean isCreacion)
            throws ApplicationException, IOException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        //obtiene listado de tipo de solicitudes para Hhpp (VT (VISITA TECNICA))
        List<CmtTipoSolicitudMgl> resulList = cmtTipoSolicitudMglDaoImpl.obtenerTipoSolicitudHhppList(tipoAplicacionTipoSolicitud);
        SecurityLogin securityLoginUser = new SecurityLogin(facesContext);
        List<CmtTipoSolicitudMgl> finalResultList = new ArrayList();
        if (!resulList.isEmpty()) {
            for (CmtTipoSolicitudMgl rol : resulList) {
                //Se valida si el usuario tiene el rol para ese tipo de solicitud
                if (isCreacion) {
                    if (securityLoginUser.usuarioTieneRoll(rol.getCreacionRol())) {
                        //listado final con los roles permitidos para la creacion.
                        finalResultList.add(rol);
                    }
                } else {
                    if (securityLoginUser.usuarioTieneRoll(rol.getGestionRol())) {
                        //listado final con los roles permitidos para la gestion.
                        finalResultList.add(rol);
                    }
                }
                
            }
        }
        return finalResultList;
    }
    /**
     * Consulta de tipo de solicitud de eliminaci&oacute;n
     * 
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud de eliminaci&oacute;n
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    public CmtTipoSolicitudMgl obtenerSolicitudEliminacion() throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.obtenerSolicitudEliminacion();
    }

    /**
     * Consulta de tipo de solicitud de escalamiento de acometidas
     *
     * @return {@link CmtTipoSolicitudMgl} Tipo de solicitud de escalamiento
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    public CmtTipoSolicitudMgl obtenerSolicitudEscalamiento() throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.obtenerSolicitudEscalamiento();
    }
       /**
   * Solicituar al dao el reporte general
   * 
   * Solicita el reporte general al dao
   * 
   * @author becerraarmr
   * 
   * @param fechaI fecha inicial
   * @param fechaF fecha final
   * @param tipoSol tipo de solicitud
   * @param estado estado
     * @param range
   * @return uns listado con los objetos que cumplen la condición.
     * @throws co.com.claro.mgl.error.ApplicationException
   */
  public List<Object[]> buscarReporteGeneral(Date fechaI, Date fechaF, 
          String tipoSol, String estado,int[] range) throws ApplicationException {
    
    SolicitudManager solicitudManager = new SolicitudManager();
    
    if(fechaI== null || fechaF==null || tipoSol==null){
      return new ArrayList<>();
    }
    
    return solicitudManager.buscarReporteGeneralSolicitudesHhpp
            (fechaI, fechaF, tipoSol, estado, range);
  }
  
    /**
     * Buscar los registros de la consulta de reporte detalle de visitas
     * técnicas
     *
     * Busca en la base de datos los registros que corresponden a la información
     * dada.
     *
     * @param fechaInicial
     * @param fechaFinalizacion
     * @param tipoSolicitud
     * @param estado
     * @param finRegistros
     * @param inicioRegsitros
     * @return listado con la solicitud pedida.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<Object[]> buscarReporteDetalle(Date fechaInicial,
            Date fechaFinalizacion, String tipoSolicitud, String estado,
            int finRegistros, int inicioRegsitros, String usuarioLogin) throws ApplicationException {

        SolicitudManager solicitudManager = new SolicitudManager();

        if (fechaInicial == null || fechaFinalizacion == null || tipoSolicitud == null) {
            return new ArrayList<>();
        }

        return solicitudManager.buscarReporteDetalladoSolicitudesHhpp(fechaInicial, fechaFinalizacion, tipoSolicitud, estado, inicioRegsitros, finRegistros, usuarioLogin);

    }

    public int numRegistroReporteDetalle(Date fechaInicial,
            Date fechaFinalizacion, String idTipoSolicitud, String estado) throws ApplicationException{

        SolicitudManager solicitudManager = new SolicitudManager();

        if (fechaInicial == null || fechaFinalizacion == null || idTipoSolicitud == null) {
            return 0;
        }

        return solicitudManager.numRegistroReporteDetalleSolicitudesHhpp
                (fechaInicial, fechaFinalizacion, idTipoSolicitud, estado);
    }

  public String obtenerColorAlerta(Solicitud solAux) {
    String colorResult = "blue";
    if (solAux != null && solAux.getFechaIngreso() != null) {
      long diffDate = (new Date().getTime()) - (solAux.getFechaIngreso().getTime());
      //Diferencia de las Fechas en Minutos
      long diffMinutes = Math.abs(diffDate / (60 * 1000));
      if ((int) diffMinutes >= 180) {
        colorResult = "red";
      } else if ((int) diffMinutes < 180
              && (int) diffMinutes >= 120) {
        colorResult = "yellow";
      } else if ((int) diffMinutes < 120) {
        colorResult = "green";
      }
    }
    return colorResult;
  }

  public CmtTipoSolicitudMgl find(BigDecimal idSolicitud){
    try {
      CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = 
              new CmtTipoSolicitudMglDaoImpl();
      return cmtTipoSolicitudMglDaoImpl.find(idSolicitud);
    } catch (ApplicationException e) {
      String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
      LOGGER.error(msg);
      throw new Error(e.getMessage());
    }
  }

  public List<Object[]> findTipoSolicitudes() throws ApplicationException {
    CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = 
              new CmtTipoSolicitudMglDaoImpl();
    return cmtTipoSolicitudMglDaoImpl.findTipoSolicitudes();
  }
  
   /**
   * Función que realiza validación de color de alerta correspondiente
   * según el valor de ANS del tipo de solicitud
   *
   * @param tipoSolicitud
   * @param fechaIngreso
   *
   * @author Juan David Hernandez
   * @return algunos de estos valores (blue,yellow,red,green)
     * @throws co.com.claro.mgl.error.ApplicationException
   * @throws Error 
   */
  public String obtenerColorAlerta(CmtTipoSolicitudMgl tipoSolicitud,Date fechaIngreso) {
    String colorResult = "blue";
    if (fechaIngreso != null) {
      long diffDate = (new Date().getTime()) - (fechaIngreso.getTime());
      //Diferencia de las Fechas en Minutos
      long diffMinutes = Math.abs(diffDate / (60 * 1000));
      if ((int) diffMinutes >= tipoSolicitud.getAns()) {
        colorResult = "red";
      } else if ((int) diffMinutes < tipoSolicitud.getAns()
              && (int) diffMinutes >= tipoSolicitud.getAnsAviso()) {
        colorResult = "yellow";
      } else if ((int) diffMinutes < tipoSolicitud.getAnsAviso()) {
        colorResult = "green";
      }
    }
    return colorResult;
  }
    /**
     * Metodo privado para consultar las tipo de solicitud de CM Autor:Victor
     * Manuel Bocanegra
     *
     * @return List<CmtTipoSolicitudMgl>
     * @throws ApplicationException Excepcion lanzada en la consulta
     */
    public List<CmtTipoSolicitudMgl> findByTipoApplication() throws ApplicationException {

        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl =
                new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl.findByTipoApplication();
         
    }
    
    
      /**
     * Obtiene el tipo de solicitud por tipo solicitud basica id
     *
     * @author Juan David Hernandez
     * @param basicaIdTipoSolicitud
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTipoSolicitudMgl findTipoSolicitudByBasicaIdTipoSolicitud(BigDecimal basicaIdTipoSolicitud)
            throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl
                .findTipoSolicitudByBasicaIdTipoSolicitud(basicaIdTipoSolicitud);
    }
    
        /**
     * Obtiene el tipo de solicitud por tipoSolicitud basica id
     *
     * @author Juan David Hernandez
     * @param tipoSolicitudBasicaId
     * @return CmtTipoSolicitudMgl
     * @throws ApplicationException
     */
    public CmtTipoSolicitudMgl findTipoSolicitudByTipoSolicitudBasicaId(CmtBasicaMgl tipoSolicitudBasicaId)
            throws ApplicationException {
        CmtTipoSolicitudMglDaoImpl cmtTipoSolicitudMglDaoImpl = new CmtTipoSolicitudMglDaoImpl();
        return cmtTipoSolicitudMglDaoImpl
                .findTipoSolicitudByTipoSolicitudBasicaId(tipoSolicitudBasicaId);
    } 
}
