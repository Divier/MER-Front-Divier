package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.rest.dtos.CmtCrearSolicitudInspiraDto;
import co.com.claro.mgl.rest.dtos.CmtResponseCreaSolicitudDto;
import co.com.claro.mgl.rest.dtos.RequestSolicitudCambioEstratoDto;
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

import javax.ejb.Local;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Parzifal de León
 */
@Local
public interface SolicitudFacadeLocal extends BaseFacadeLocal<Solicitud> {

    public Solicitud findById(BigDecimal id);

    public List<Solicitud> findSolicitudByIdRol(BigDecimal id,
            List<CmtTipoSolicitudMgl> rolesList);

    public void desbloquearDisponibilidadGestion(BigDecimal idSolicitud) throws ApplicationException;

    public List<Solicitud> findAllSolicitudDthList();

    public Solicitud findSolicitudDthById(BigDecimal id);

    public Solicitud findSolicitudDthByIdPendiente(BigDecimal id);

    public void bloquearDisponibilidadGestionDth(BigDecimal id, String usuarioVerificador);

    public void bloquearDisponibilidadGestionVerificada(BigDecimal idSolicitud) throws ApplicationException;

    public void desbloquearDisponibilidadGestionDth(BigDecimal id);

    public void desbloquearDisponibilidadGestionVerificada(BigDecimal idSolicitud);

    public List<Solicitud> findPendientesParaGestionPaginacion(int page, int filas);

    public String obtenerColorAlerta(CmtTipoSolicitudMgl tipoSolicitud, Date fechaIngreso) throws ApplicationException;

    public boolean gestionSolicitud(Solicitud solicitudDthSeleccionada, DrDireccion drDireccion, DetalleDireccionEntity detalleDireccionEntity,
            DireccionRREntity direccionRREntity, NodoMgl nodo, String usuarioVt, int perfilVt,
            String idUsuario,
            List<UnidadStructPcml> unidadModificadaList, CmtTiempoSolicitudMgl cmtTiempoSolicitudMgl,
            List<UnidadStructPcml> unidadConfictoList, boolean sincronizaRr, boolean desdeMer, boolean habilitarRR,
            GeograficoPoliticoMgl centroPoblado, GeograficoPoliticoMgl ciudad,  GeograficoPoliticoMgl departamento,String nap) throws ApplicationException;

    public boolean validarDisponibilidadSolicitud(BigDecimal id, String tipoTecnologia) throws ApplicationException;

    public List<ResponseValidacionViabilidad> validarMatrizViabilidad(
            ResponseValidacionDireccion responseValidacionDireccion,
            String direccionStr) throws ApplicationException;

    /**
     * Crea Solicitud Dth.Permite crear una solicitud Dth sobre el repositorio
     * para su posterior gestion
     *
     * @param request                 request con la informacion necesaria para la creacion de
     *                                la solicitud Dth
     * @param tipoTecnologia          con la que se debe crear la solicitud
     * @param unidadesList
     * @param tiempoDuracionSolicitud
     * @param idCentroPoblado
     * @param usuarioVt
     * @param perfilVt
     * @param tipoAccionSolicitud
     * @param direccionActual
     * @param habilitarRR
     * @param solicitudDesdeMER
     * @param centroPobladoDireccion
     * @param ciudadDireccion
     * @param departamentoDireccion
     * @param flagMicro
     * @return respuesta con el proceso de creacion de la solicitud Dth
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    ResponseCreaSolicitud crearSolicitudDth(
            RequestCreaSolicitud request, String tipoTecnologia,
            List<UnidadStructPcml> unidadesList,
            String tiempoDuracionSolicitud, BigDecimal idCentroPoblado,
            String usuarioVt, int perfilVt, String tipoAccionSolicitud,
            Solicitud direccionActual, boolean habilitarRR,
            boolean solicitudDesdeMER, GeograficoPoliticoMgl centroPobladoDireccion,
            GeograficoPoliticoMgl ciudadDireccion, GeograficoPoliticoMgl departamentoDireccion, boolean flagMicro)
            throws ApplicationException;
    
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
    public ResponseCreaSolicitud crearSolicitudCambioEstratoDir(
            RequestCreaSolicitud request, String tiempoDuracionSolicitud,
            BigDecimal idCentroPoblado, String usuarioVt, int perfilVt, HhppMgl hhppMgl,
            String tipoAccionSolicitud, String estratoAntiguo, String estratoNuevo)
            throws ApplicationException; 

    /**
     * obtiene listado de solicitudes por las tecnologias que el rol se
     * encuentre habilitado
     *
     * @author Juan David Hernandez
     * @param rolesList listado de roles con los que cuenta el usuario
     * @return listado de solicitudes filtrado por roles
     *
     */
    public List<Solicitud> findAllSolicitudByRolList(List<CmtTipoSolicitudMgl> rolesList);

    /**
     * obtiene listado de solicitudes por las tecnologias que el rol se
     * encuentre habilitado en paginación
     *
     * @author Juan David Hernandez
     * @param page
     * @param filas
     * @param rolesList listado de roles con los que cuenta el usuario
     * @param ordenMayorMenor
     * @param tipoSolicitudFiltro
     * @param divisional
     * @return listado de solicitudes filtrado por roles
     *
     */
    public List<Solicitud> findPendientesParaGestionPaginacionByRol(int page,
            int filas, List<CmtTipoSolicitudMgl> rolesList, String tipoSolicitudFiltro,
            boolean ordenMayorMenor, BigDecimal divisional);

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
    public int countAllSolicitudByRolList(List<CmtTipoSolicitudMgl> rolesList,
            String tipoSolicitudFiltro);

    /**
     * Buscar una solicitud por id de solicitud
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param solicitud objeto solicitud con el idSolicitud o idTcrm seteado
     * para la busqueda
     * @return Retorna el objeto solicitud encontrado o un objeto vacio
     */
    public EstadoSolicitud findBySolicitud(Solicitud solicitud);

    /**
     * Buscar una solicitud por id del caso tcrm
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param solicitud objeto solicitud con el idSolicitud o idTcrm seteado
     * para la busqueda
     * @return Retorna el objeto solicitud encontrado o un objeto vacio
     */
    public EstadoSolicitud findByIdCasoTcrm(Solicitud solicitud);

    /**
     * Crea Solicitud. Permite crear una solicitud sobre el repositorio para su
     * posterior gestion
     *
     * @author Victor Bocanegra
     * @param cmtCrearSolicitudInspiraDto con la que se debe crear la solicitud
     * @return CmtResponseCreaSolicitudDto respuesta con el proceso de creacion
     * de la solicitud
     *
     */
    public CmtResponseCreaSolicitudDto crearGestionarSolicitudinspira(CmtCrearSolicitudInspiraDto cmtCrearSolicitudInspiraDto);

    /**
     * valbuenayf metodo para crear una solicitud de cambio de estrato TRCM
     *
     * @param request
     * @return
     * @throws ApplicationException
     */
    public ResponseCreaSolicitudCambioEstrato crearSolitudCambioEstratoTcrm(RequestSolicitudCambioEstratoDto request) throws ApplicationException;

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
     */
    public Solicitud findSolicitud(BigDecimal idSolicitud,
            String estado, String tipoSol);

    /**
     * Contar la cantidad de registros
     *
     * Cuenta la cantida de solicitudes según el tipo y el estado
     *
     * @author becerraarmr
     * @version 2017 revision 1.0
     * @param tipo tipo de la solicitud
     * @param estado estado de la solicitud
     * @return un valor entero con el dato encontrado
     */
    public int count(String tipo, String estado);

    /**
     * Busscar Solicitudes
     *
     * Hace una consulta segun el rango i y el tipo de soliciutd
     *
     * @author becerraarmr
     * @version 2017 revision 1.0
     * @param rango valor que determina de donde hasta donde se debe hacer la
     * busqueda
     * @param tipo tipo de la solicitud
     * @param estado estado de la solicitud
     * @param orderDESC true descendiente false ascendiente
     * @param atributoOrdenar atributo a ordenar
     * @return un Listado con las solicitudes encontradas, según los criterios
     */
    public List<Solicitud> findRange(int[] rango, String tipo, String estado, boolean orderDESC, String atributoOrdenar);

    /**
     * Gestionar el cambio de estrato de una solicitud
     *
     * @author Victor Bocanegra
     * @param solicitudGestion
     * @param estratoNew
     * @param usuario
     * @param perfil
     * @return String
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Error si hay problemas en la actualizacion
     */
    public String gestionarCambioEstrato(Solicitud solicitudGestion,
            String estratoNew, String usuario, int perfil) throws ApplicationException;

    /**
     * Valida si se Crea OT.Función que realiza validación de la respuesta de
 la gestion solicitud para determinar si se debe crear una ot
     *
     * @author ortizjaf
     * @param rptGestion respuesta con la que se gestiona la solicitud
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    boolean validaRptParaCreacionOt(String rptGestion) throws ApplicationException;

    /**
     * Metodo que crea en base de datos las urls refernes a los achivos 
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
    boolean crearUrlsLista(List<String> Urls, Solicitud solicitud, String usuario,
            int perfil )throws ApplicationException;
    
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
     String uploadArchivoCambioEstrato(File uploadedFile, String fileName)
            throws IOException, ApplicationException, Exception;
     
     public String obtenerCarpetaTipoDireccion(CmtBasicaMgl tecnologiaBasicaId) throws ApplicationException;
     
     public String validarEstrato(DrDireccion drDireccion);
     
     public List<Solicitud> consultarOrdenesDeSolicitudHHPP(int firstResult,int maxResults,CmtDireccionDetalladaMgl direccionDetalladaMgl,Solicitud filtro)throws ApplicationException;
     
    public int countOrdenesDeSolicitudHHPP(CmtDireccionDetalladaMgl direccionDetalladaMgl, Solicitud filtro) throws ApplicationException;

    public List<Solicitud> solictudesHhppEnCurso(BigDecimal idDireccionDetallada,
            BigDecimal idCentroPoblado, BigDecimal tecnologiaBasicaId,
            String tipoAccionSolicitud) throws ApplicationException;
    
    public int solictudesByUsuario(String usuario) throws ApplicationException;
    
    
    public StringBuffer mensajeCorreoSolicitud(Solicitud soliciti,
            String tipoSolicitudNombre,
            String departamento, String ciudad, String centroPoblado, String direccionSolicitud);
    
    /**
     * Método para crear una solicitud de escalamientos HHPP
     * @param request bean con los datos que se va a crear la solicitud
     * @return bean con los datos de la solicitud creada
     */
    ResponseCreaSolicitud crearSolicitudEscalamientosHHPP(final RequestCreaSolicitudEscalamientoHHPP request) throws ApplicationException;

    /**
     * Método para guardar los archivos adjuntos del escalamiento HHPP
     * @param uploadedFile archivo a guardar
     * @param fileName nombre del archivo
     * @return
     * @throws IOException
     * @throws ApplicationException
     * @throws Exception
     */
    String uploadArchivoEscalamientoHHPP(File uploadedFile, String fileName)
            throws IOException, ApplicationException, Exception;

    /**
     * Busca la solicitud de escalamiento de cobertura más reciente a
     * partir del ID de la dirección.
     *
     * @param idDireccion {@link BigDecimal} ID de dirección MGL_DIRECCION
     * @param idBasica {@link BigDecimal}
     * @return {@link Solicitud}
     * @throws ApplicationException Excepción de la app
     */
    Solicitud findUltimaSolicitudEscalamientoCobertura(BigDecimal idDireccion, BigDecimal idBasica)
            throws ApplicationException;

    /**
     * Realiza la gestión de la solicitud de traslado de HHPP bloqueado
     *
     * @param solicitud        {@link Solicitud} Datos de la solicitud de traslado de HHPP bloqueado en proceso
     * @param solicitudCreated {@link Solicitud} Datos de la solicitud de traslado de HHPP bloqueado creada
     * @param request          {@link RequestCreaSolicitudTrasladoHhppBloqueado} Datos de request general.
     * @return Retorna true cuando se realizó correctamente la gestión de la solicitud
     * @author Gildardo Mora
     */
    Map<String,Object> gestionarSolicitudTrasladoHhppBloqueado(Solicitud solicitud, Solicitud solicitudCreated, RequestCreaSolicitudTrasladoHhppBloqueado request) throws ApplicationException;

    /**
     * Realiza la creación de la solicitud de traslado de HHPP bloqueado.
     *
     * @param request {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @return {@link ResponseCreaSolicitud}
     * @throws ApplicationException Excepción de la App
     * @author Gildardo Mora
     */
    Optional<ResponseCreaSolicitud> crearSolicitudTrasladoHhppBloqueado(RequestCreaSolicitudTrasladoHhppBloqueado request) throws ApplicationException;

    List<CmtTipoSolicitudMgl> consultarTiposSolicitudesCreacion(FacesContext facesContext, String tipoAplicacionSolicitudHhpp) throws ApplicationException, IOException;

}
