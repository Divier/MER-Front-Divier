package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.SolicitudManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.dao.impl.SolicitudDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.rest.dtos.CmtCrearSolicitudInspiraDto;
import co.com.claro.mgl.rest.dtos.CmtResponseCreaSolicitudDto;
import co.com.claro.mgl.rest.dtos.RequestSolicitudCambioEstratoDto;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitud;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitudEscalamientoHHPP;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitudTrasladoHhppBloqueado;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitud;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitudCambioEstrato;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionViabilidad;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.EstadoSolicitud;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

/**
 *
 * @author Parzifal de León
 */
@Stateless
public class SolicitudFacade implements SolicitudFacadeLocal {

    private final SolicitudDaoImpl solicitudDao;
    private final SolicitudManager solicitudManager;
    private static final Logger LOGGER = LogManager.getLogger(SolicitudFacade.class);
    @EJB
    private CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal;

    public SolicitudFacade() {
        this.solicitudDao = new SolicitudDaoImpl();
        this.solicitudManager = new SolicitudManager();
    }

    @Override
    public Solicitud create(Solicitud sol) throws ApplicationException {
        try {
            return solicitudDao.create(sol);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public Solicitud findById(BigDecimal id) {
        try {
            return solicitudDao.findById(id);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public List<Solicitud> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Solicitud update(Solicitud sol) throws ApplicationException {
        try {
            return solicitudDao.update(sol);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public boolean delete(Solicitud t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Solicitud findById(Solicitud sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void desbloquearDisponibilidadGestion(BigDecimal idSolicitud) throws ApplicationException {
        solicitudDao.desbloquearDisponibilidadGestion(idSolicitud);
    }

    /**
     * Buscar una solicitud por id de solicitud
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param solicitud objeto solicitud con el idSolicitud o idTcrm seteado
     * para la busqueda
     * @return Retorna el objeto solicitud encontrado o un objeto vacio
     */
    @Override
    public EstadoSolicitud findBySolicitud(Solicitud solicitud) {
        return solicitudManager.findBySolicitud(solicitud);
    }

    /**
     * Buscar una solicitud por id del caso tcrm
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param solicitud objeto solicitud con el idSolicitud o idTcrm seteado
     * para la busqueda
     * @return Retorna el objeto solicitud encontrado o un objeto vacio
     */
    @Override
    public EstadoSolicitud findByIdCasoTcrm(Solicitud solicitud) {
        return solicitudManager.findByIdCasoTcrm(solicitud);
    }

    @Override
    public List<Solicitud> findAllSolicitudDthList() {
        try {
            return solicitudManager.findAllSolicitudDthList();
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public Solicitud findSolicitudDthById(BigDecimal id) {
        try {
            return solicitudManager.findSolicitudDTHById(id);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public Solicitud findSolicitudDthByIdPendiente(BigDecimal id) {
        try {
            return solicitudManager.findSolicitudDTHByIdPendiente(id);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public void bloquearDisponibilidadGestionDth(BigDecimal idSolicitud, String usuarioVerificador) {
        try {
            solicitudDao.bloquearDisponibilidadGestionDth(idSolicitud, usuarioVerificador);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
    }

    @Override
    public void bloquearDisponibilidadGestionVerificada(BigDecimal idSolicitud) throws ApplicationException {

        try {
            solicitudDao.bloquearDisponibilidadGestionVerificada(idSolicitud);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
    }

    @Override
    public void desbloquearDisponibilidadGestionDth(BigDecimal idSolicitud) {
        try {
            solicitudDao.desbloquearDisponibilidadGestionDth(idSolicitud);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
    }

    @Override
    public void desbloquearDisponibilidadGestionVerificada(BigDecimal idSolicitud) {
        try {
            solicitudDao.desbloquearDisponibilidadGestionVerificada(idSolicitud);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
    }

    @Override
    public List<Solicitud> findPendientesParaGestionPaginacion(int page, int filasPag) {
        try {
           return solicitudManager.findPendientesParaGestionPaginacion(page, filasPag);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Crea Solicitud Dth.Permite crear una solicitud Dth sobre el repositorio
     * para su posterior gestion
     *
     * @param request         request con la informacion necesaria para la creacion de
     *                        la solicitud Dth
     * @param tipoTecnologia  con la que se debe crear la solicitud
     * @param direccionActual
     * @param flagMicro
     * @return respuesta con el proceso de creacion de la solicitud Dth
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    @Override
    public ResponseCreaSolicitud crearSolicitudDth(RequestCreaSolicitud request,
                                                   String tipoTecnologia, List<UnidadStructPcml> unidadesList,
                                                   String tiempoDuracionSolicitud, BigDecimal idCentroPoblado,
                                                   String usuarioVt, int perfilVt, String tipoAccionSolicitud,
                                                   Solicitud direccionActual, boolean habilitarRR,
                                                   boolean solicitudDesdeMER, GeograficoPoliticoMgl centroPobladoDireccion,
                                                   GeograficoPoliticoMgl ciudadDireccion, GeograficoPoliticoMgl departamentoDireccion, boolean flagMicro)
            throws ApplicationException {
        try {
            return solicitudManager.crearSolicitudDth(request, tipoTecnologia,
                    unidadesList, tiempoDuracionSolicitud,
                    idCentroPoblado, usuarioVt, perfilVt, tipoAccionSolicitud,
                    direccionActual, habilitarRR, solicitudDesdeMER, centroPobladoDireccion, ciudadDireccion, departamentoDireccion, flagMicro);
        } catch (ExceptionDB ex) {
            String msgError = "Error al crear solicitud: " + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
        return null;

    }
    
        /**
     * Crea una solicitud de cambio de estrato a una direccion
     *
     * @author Juan David Hernandez
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud     
     * @param tiempoDuracionSolicitud     
     * @param idCentroPoblado     
     * @param usuarioVt     
     * @param perfilVt     
     * @param hhppMgl     
     * @param tipoAccionSolicitud     
     * @param estratoAntiguo     
     * @param estratoNuevo     
     * @return  ResponseCreaSolicitud   
     * @throws co.com.claro.mgl.error.ApplicationException      
     *
     */
    @Override
    public ResponseCreaSolicitud crearSolicitudCambioEstratoDir(
            RequestCreaSolicitud request, String tiempoDuracionSolicitud,
            BigDecimal idCentroPoblado, String usuarioVt, int perfilVt, HhppMgl hhppMgl,
            String tipoAccionSolicitud, String estratoAntiguo, String estratoNuevo)
            throws ApplicationException {
        try {
            return solicitudManager.crearSolicitudCambioEstratoDir(request, tiempoDuracionSolicitud,
                    idCentroPoblado, usuarioVt, perfilVt, hhppMgl, tipoAccionSolicitud,
                    estratoAntiguo, estratoNuevo);
        } catch (CloneNotSupportedException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public String obtenerColorAlerta(CmtTipoSolicitudMgl tipoSolicitud, Date fechaIngreso) {
        try {
            return solicitudManager.obtenerColorAlerta(tipoSolicitud, fechaIngreso);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    @Override
    public boolean gestionSolicitud(Solicitud solicitudDthSeleccionada,
            DrDireccion drDireccion, DetalleDireccionEntity detalleDireccionEntity,
            DireccionRREntity direccionRREntity,
            NodoMgl nodo, String usuarioVT, int perfilVt,
            String idUsuario,
            List<UnidadStructPcml> unidadModificadaList,
            CmtTiempoSolicitudMgl cmtTiempoSolicitudMgl,
            List<UnidadStructPcml> unidadConflictoList, boolean sincronizaRr, boolean desdeMer, boolean habilitarRR,
            GeograficoPoliticoMgl centroPoblado, GeograficoPoliticoMgl ciudad,  GeograficoPoliticoMgl departamento,String nap) throws ApplicationException {
        boolean solicitudGestionada = false;
        try {
            solicitudGestionada = solicitudManager.gestionSolicitud(solicitudDthSeleccionada,
                    drDireccion, detalleDireccionEntity, direccionRREntity,
                    nodo, usuarioVT, perfilVt,
                    idUsuario, unidadModificadaList, cmtTiempoSolicitudMgl,
                    unidadConflictoList,  sincronizaRr, desdeMer, habilitarRR,
                    centroPoblado, ciudad,  departamento,nap);
        } catch (ApplicationException e) {
            String msg = "Error: " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return solicitudGestionada;
    }

    @Override
    public boolean validarDisponibilidadSolicitud(BigDecimal idSolicitud, String tipoTecnologia)
            throws ApplicationException {
        try {
            return solicitudManager.validarDisponibilidadSolicitud(idSolicitud, tipoTecnologia);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Validacion Matriz Viabilidad.Permite conocer si una solicitud cumple los
 criterios de venta para su creacion.
     *
     * @author Juan David Hernandez
     * @param direccionStr
     *
     * @return respuesta con el proceso de creacion de la solicitud Dth
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public List<ResponseValidacionViabilidad> validarMatrizViabilidad(
            ResponseValidacionDireccion responseValidacionDireccion,
            String direccionStr)
            throws ApplicationException {
        
        return solicitudManager.validarMatrizViabilidad(
                responseValidacionDireccion,
                direccionStr);
    }

    @Override
    public List<Solicitud> findAllSolicitudByRolList(List<CmtTipoSolicitudMgl> rolesList) {
        try {
            return solicitudManager.findAllSolicitudByRolList(rolesList);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public List<Solicitud> findPendientesParaGestionPaginacionByRol(int page,
              int filasPag, List <CmtTipoSolicitudMgl> rolesList, 
              String tipoSolicitudFiltro, boolean ordenMayorMenor, BigDecimal divisional)  {
        try {
            SolicitudManager solicitudMgr = new SolicitudManager();
            return solicitudMgr.findPendientesParaGestionPaginacionByRol(page,
                    filasPag, rolesList, tipoSolicitudFiltro, ordenMayorMenor,divisional);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    /**
     * obtiene el valor de total de solicitudes para realizar la paginacion del
     * listado de solicitudes
     *
     * @author Juan David Hernandez
     * @param rolesList listado de roles con los que cuenta el usuario
     * @param tipoSolicitudFiltro
     * @return numero total de solicitudes
     *
     */
    @Override
    public int countAllSolicitudByRolList(List<CmtTipoSolicitudMgl> rolesList,
            String tipoSolicitudFiltro) {
        try {            
            return solicitudManager.countAllSolicitudByRolList(rolesList, tipoSolicitudFiltro);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return 0;
    }

    /**
     * Crea Solicitud.Permite crear una solicitud sobre el repositorio para su
     * posterior gestion
     *
     * @author Victor Bocanegra
     *
     * @return respuesta con el proceso de creacion de la solicitud
     *
     */
    @Override
    public CmtResponseCreaSolicitudDto crearGestionarSolicitudinspira(
            CmtCrearSolicitudInspiraDto cmtCrearSolicitudInspiraDto) {
        Initialized.getInstance();
        CmtResponseCreaSolicitudDto cmtResponseCreaSolicitudDto = new CmtResponseCreaSolicitudDto();  
        try {
             

            if (cmtCrearSolicitudInspiraDto == null) {
                cmtResponseCreaSolicitudDto.setMessageType("E");
                cmtResponseCreaSolicitudDto.setMessage("Es necesario crear una peticion para construir el servicio");
                return cmtResponseCreaSolicitudDto;
            }
            
            if(cmtCrearSolicitudInspiraDto.getTipoTecnologia() == null || cmtCrearSolicitudInspiraDto.getTipoTecnologia().isEmpty()){
                cmtResponseCreaSolicitudDto.setMessageType("E");
                cmtResponseCreaSolicitudDto.setMessage("Es necesario ingresar codigo del tipoTecnologia por favor");
                return cmtResponseCreaSolicitudDto;
            }

            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            CmtBasicaMgl tecnologiaSolicitada;
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager
                    = new CmtTipoBasicaMglManager();
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            tecnologiaSolicitada = basicaMglManager.findByBasicaCode(
                    cmtCrearSolicitudInspiraDto.getTipoTecnologia(),
                    cmtTipoBasicaMgl.getTipoBasicaId());
            
            if(tecnologiaSolicitada != null){                
            }else{
                cmtResponseCreaSolicitudDto.setMessageType("E");
                cmtResponseCreaSolicitudDto.setMessage("El código de la tecnología ingresado no se encuentra parametrizado en el sistema");
                return cmtResponseCreaSolicitudDto;
            }

            return solicitudManager.crearGestionarSolicitudinspira(
                    cmtCrearSolicitudInspiraDto.getCmtRequestCrearSolicitudInspira(),
                    tecnologiaSolicitada.getAbreviatura(),
                    cmtCrearSolicitudInspiraDto.getTiempoDuracionSolicitud(),
                    cmtCrearSolicitudInspiraDto.getCodigoDane(), cmtCrearSolicitudInspiraDto.getUser(),
                    cmtCrearSolicitudInspiraDto.getNodoGestion(), cmtCrearSolicitudInspiraDto.getNodoCercano(),
                    cmtCrearSolicitudInspiraDto.getRespuestaGestion(), cmtCrearSolicitudInspiraDto.getRespuesta(),
                    cmtCrearSolicitudInspiraDto.getIdCasoTcrm());
            
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Se produjo un error intentando crear la solicitud: " + e.getMessage());
            return cmtResponseCreaSolicitudDto;
        } catch (ExceptionDB ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
            LOGGER.error(msgError, ex);
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Se produjo un error intentando crear la solicitud: " + ex.getMessage());
            return cmtResponseCreaSolicitudDto;
        } catch (CloneNotSupportedException ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
            LOGGER.error(msgError, ex);
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Se produjo un error intentando crear la solicitud: " + ex.getMessage());
            return cmtResponseCreaSolicitudDto;
        }
    }


    @Override
    public List<Solicitud> findSolicitudByIdRol(BigDecimal id,
            List<CmtTipoSolicitudMgl> rolesList) {
        try {
            return solicitudManager.findSolicitudByIdRol(id, rolesList);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    /**
     * Contar los registros según las condiciones
     * <p>
     * Cuenta cuentos registros hay en la base de datos según los parámetros.
     *
     * @author becerraarmr
     *
     * @param tipo tipo de la solicitud.
     * @param estado estado de la solicitud.
     *
     * @return un entero de la cantidad encontrada
     */
    @Override
    public int count(String tipo, String estado) {
        return solicitudManager.count(tipo, estado);
    }

    /**
     * Buscar le listado de solicitudes
     * <p>
     * Busca el listado de solicitudes según el rango, el tipo de solicitu, el
     * estado de la solicitud.
     *
     * @author becerraarmr
     * @version 2017 revision 1.0
     * @param rango valor inicial y final de busqueda
     * @param estado estado de la solicitud
     * @param orderDESC true descendiente false ascendiente
     * @param atributoOrdenar atributo a ordenar
     * @return Listado con los valor solicitudos
     *
     */
    @Override
    public List<Solicitud> findRange(
            int[] rango, String tipo, String estado,  boolean orderDESC, String atributoOrdenar) {
       
        return solicitudManager.findRange(rango, tipo, estado, orderDESC, atributoOrdenar);
    }

    /**
     * Actualizar Solicitud
     *
     * Actualiza la solicitud
     *
     * @author becerraarmr
     *
     * @param solicitud solicitud a
     * @return Solicitud creada
     */
    public Solicitud updateSolicitud(Solicitud solicitud) {
        return solicitudManager.update(solicitud);
    }

    /**
     * Buscar Solicitud
     * <p>
     * Busca la Solicitud según los parámetros establecidos
     *
     * @author becerraarmr
     *
     * @param idSolicitud identificador único de la solicitud
     * @param estado estado en el que se encuentra la solicitud
     * @param tipoSol tipo de solicitud. Si es: Visitas en casa, de
     * replanteamien to, edificio conjunto, creación de cuentra matriz, etc.
     *
     * @return el valor de solicitud encontrado en la base de datos.
     *
     * @throws Error si hay algún problema al realizar la consulta.
     */
    @Override
    public Solicitud findSolicitud(
            BigDecimal idSolicitud, String estado, String tipoSol) {
        
        return solicitudManager.findSolicitud(idSolicitud, estado, tipoSol);
    }

    @Override
    public ResponseCreaSolicitudCambioEstrato crearSolitudCambioEstratoTcrm(RequestSolicitudCambioEstratoDto request) throws ApplicationException {

        try {
            ResponseCreaSolicitudCambioEstrato response;
            response = solicitudManager.crearSolitudCambioEstratoTcrm(request);
            return response;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            ResponseCreaSolicitudCambioEstrato error = new ResponseCreaSolicitudCambioEstrato();
            error.setTipoRespuesta("E");
            error.setMensaje(e.getMessage());
            error.setIdSolicitud("0");
            return error;
        }
    }

    /**
     * Gestionar el cambio de estrato de una solicitud
     *
     * @author Victor Bocanegra
     * @param solicitudGestion solicitud de cambio de estrato
     * @param estratoNew
     * @param usuario
     * @param perfil
     * @return String
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Error si hay problemas en la actualizacion
     */
    @Override
    public String gestionarCambioEstrato(Solicitud solicitudGestion,
            String estratoNew, String usuario, int perfil)
            throws ApplicationException {

        return solicitudManager.gestionarCambioEstrato(solicitudGestion, estratoNew, usuario, perfil);

    }

    /**
     * Valida si se Crea OT.Función que realiza validación de la respuesta de
 la gestion solicitud para determinar si se debe crear una ot
     *
     * @author ortizjaf
     * @param rptGestion respuesta con la que se gestiona la solicitud
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    @Override
    public boolean validaRptParaCreacionOt(String rptGestion)
            throws ApplicationException {
        
        return solicitudManager.validaRptParaCreacionOt(rptGestion);
    }

    /**
     * Metodo que ordena la creacion las urls refernes a los achivos 
     * relacionados a la solicitud
     *
     * @author Jonathan Peña
     * @param Urls
     * @param solicitud
     * @param usuario
     * @param perfil
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public boolean crearUrlsLista(List<String> Urls, Solicitud solicitud, String usuario,
            int perfil)
            throws ApplicationException {
       
        return solicitudManager.crearUrlsLista(Urls, solicitud, usuario, perfil);
    }
    
    /**
     * Metodo para el almacenamiento de un archivo de cambio de estrato en el
     * servidor.
     *
     * @author Victor Bocanegra
     * @param uploadedFile componente que carga el archivo
     * @param fileName
     * @return String
     * @throws ApplicationException
     * @throws java.io.IOException
     */
    @Override
    public String uploadArchivoCambioEstrato(File uploadedFile, String fileName)
            throws Exception {

        return solicitudManager.uploadArchivoSolicitud(uploadedFile, fileName);
    }

    @Override
    public String obtenerCarpetaTipoDireccion(CmtBasicaMgl tecnologiaBasicaId) throws ApplicationException {
       return solicitudManager.obtenerCarpetaTipoDireccion(tecnologiaBasicaId);
    
    }

    @Override
    public String validarEstrato(DrDireccion drDireccion) {
       return solicitudManager.validarEstrato(drDireccion);
    }
    
    @Override
    public List<Solicitud> consultarOrdenesDeSolicitudHHPP(int firstResult,int maxResults,CmtDireccionDetalladaMgl direccionDetalladaMgl,Solicitud filtro)throws ApplicationException{
        return solicitudManager.consultarOrdenesDeSolicitudHHPP(firstResult,maxResults,direccionDetalladaMgl,filtro);
    }
    
    @Override
    public int countOrdenesDeSolicitudHHPP(CmtDireccionDetalladaMgl direccionDetalladaMgl, Solicitud filtro)throws ApplicationException{
        return solicitudManager.countOrdenesDeSolicitudHHPP(direccionDetalladaMgl,filtro);
    }
    
    @Override
     public List<Solicitud> solictudesHhppEnCurso(BigDecimal idDireccionDetallada,
            BigDecimal idCentroPoblado, BigDecimal tecnologiaBasicaId,
            String tipoAccionSolicitud) throws ApplicationException{
          return solicitudManager.solictudesHhppEnCurso(idDireccionDetallada,idCentroPoblado,tecnologiaBasicaId,tipoAccionSolicitud);
    }
     
    @Override
    public int solictudesByUsuario(String usuario) throws ApplicationException {
        return solicitudManager.solictudesByUsuario(usuario);
    }

    @Override
    public StringBuffer mensajeCorreoSolicitud(Solicitud soliciti,
            String tipoSolicitudNombre,
            String departamento, String ciudad, String centroPoblado, String direccionSolicitud) {
        return solicitudManager.mensajeCorreoSolicitud(soliciti, tipoSolicitudNombre,
                departamento, ciudad, centroPoblado, direccionSolicitud);
    }

	@Override
	public ResponseCreaSolicitud crearSolicitudEscalamientosHHPP(RequestCreaSolicitudEscalamientoHHPP request) throws ApplicationException {
        try {
            return solicitudManager.crearSolicitudEscalamientoHHPP(request);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            ResponseCreaSolicitud error = new ResponseCreaSolicitud();
            error.setTipoRespuesta("E");
            error.setMensaje(e.getMessage());
            error.setIdSolicitud("0");
            return error;
        }
    }

    @Override
    public String uploadArchivoEscalamientoHHPP(File uploadedFile, String fileName)
            throws Exception {

        return solicitudManager.uploadArchivoSolicitud(uploadedFile, fileName);
    }

    @Override
    public Solicitud findUltimaSolicitudEscalamientoCobertura(BigDecimal idDireccion, BigDecimal idBasica)
            throws ApplicationException {
        return solicitudManager.findUltimaSolicitudEscalamientoCobertura(idDireccion, idBasica);

    }

    @Override
    public Map<String, Object> gestionarSolicitudTrasladoHhppBloqueado(Solicitud solicitud, Solicitud solicitudCreated, RequestCreaSolicitudTrasladoHhppBloqueado request) throws ApplicationException {
        return solicitudManager.gestionarSolicitudTrasladoHhppBloquedo(solicitud, solicitudCreated, request);
    }

    @Override
    public Optional<ResponseCreaSolicitud> crearSolicitudTrasladoHhppBloqueado(RequestCreaSolicitudTrasladoHhppBloqueado request) throws ApplicationException {
        return solicitudManager.crearSolicitudTrasladoHhppBloqueado(request);
    }

    @Override
    public List<CmtTipoSolicitudMgl> consultarTiposSolicitudesCreacion(FacesContext facesContext, String tipoAplicacionSolicitudHhpp) throws ApplicationException, IOException {
        return cmtTipoSolicitudMglFacadeLocal.obtenerTipoSolicitudHhppByRolList(facesContext,
                Constant.TIPO_APLICACION_SOLICITUD_HHPP, true);
    }

}
