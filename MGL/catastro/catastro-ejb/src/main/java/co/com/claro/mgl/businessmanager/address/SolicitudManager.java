package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mer.utils.DateToolUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.com.claro.mer.utils.FileToolUtils;
import co.com.claro.mer.utils.SesionUtil;
import co.com.claro.mer.utils.constants.ConstantsDirecciones;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mgl.utils.CmtUtilidadesAgenda;
import co.com.claro.mgl.ws.cm.request.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.jlcg.db.exept.ExceptionDB;

import co.com.claro.mgl.businessmanager.cm.CmtAvisoProgramadoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtComunidadRrManager;
import co.com.claro.mgl.businessmanager.cm.CmtCuentaMatrizMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionSolicitudMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtGrupoProyectoValidacionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtNotasSolicitudMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSolicitudCmMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSolicitudTipoSolicitudMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSolictudHhppMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTiempoSolicitudMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoSolicitudMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoValidacionMglManager;
import co.com.claro.mgl.businessmanager.ptlus.UsuariosServicesManager;
import co.com.claro.mgl.dao.impl.MarcasMglDaoImpl;
import co.com.claro.mgl.dao.impl.SolicitudDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtSolicitudCmMglDaoImpl;
import co.com.claro.mgl.dtos.AtributoValorDto;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppDetalleVtMgl;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppVtMgl;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.ModificacionDir;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TecArchivosCambioEstrato;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtAvisosProgramadosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtGrupoProyectoValidacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudTipoSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.rest.dtos.CmtRequestCrearSolicitudInspira;
import co.com.claro.mgl.rest.dtos.CmtResponseCreaSolicitudDto;
import co.com.claro.mgl.rest.dtos.RequestSolicitudCambioEstratoDto;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.FileServer;
import co.com.claro.mgl.utils.MailSender;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitud;
import co.com.claro.mgl.ws.cm.response.ResponseCreaSolicitudCambioEstrato;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionViabilidad;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.business.SolicitudCambioEstratoBusiness;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.EstadoSolicitud;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import co.com.telmex.catastro.utilws.ResponseMessage;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Administra los casos de uso relacionados con solicitudes sobre HHPP
 *
 * @author Admin
 */
@Log4j2
public class SolicitudManager {

    public static final String USUARIO_NO_FUE_ENCONTRADO = "El usuario no fue encontrado.";
    SolicitudDaoImpl solicitudDaoImpl = new SolicitudDaoImpl();
    private CmtAvisoProgramadoMglManager cmtAvisoProgramadoMglManager;
    private final CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
    private String casoTcrmId = "";

    /**
     * Consulta el listado de solicitudes existentes.
     *
     * @return {@link List<Solicitud>} Lista de solicitudes
     * @throws ApplicationException Excepción en caso de error al consultar las solicitudes.
     */
    public List<Solicitud> findAll() throws ApplicationException {
        SolicitudDaoImpl solicitudDaoImpl1 = new SolicitudDaoImpl();
        return solicitudDaoImpl1.findAll();
    }

    /**
     * Consulta una solicitud a partir de su ID.
     *
     * @param idSolicitud {@link BigDecimal}
     * @return {@link Solicitud}
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    public Solicitud findById(BigDecimal idSolicitud) throws ApplicationException {
        return solicitudDaoImpl.findById(idSolicitud);
    }

    /**
     * Registra una solicitud en la base de datos
     *
     * @param solicitud {@link Solicitud}
     * @return {@link Solicitud}
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    public Solicitud crear(Solicitud solicitud) throws ApplicationException {
        return solicitudDaoImpl.create(solicitud);
    }

    /**
     * Crea Solicitud de cambio de estrato.
     * <p>
     * Permite crear una solicitud de cambio de estrato cuenta suscriptor
     * sobre el repositorio para su posterior gestión
     *
     * @param request request con la información necesaria para la creación de la solicitud
     * @return respuesta con el proceso de creación de la solicitud
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Johnnatan Ortiz
     */
    public ResponseCreaSolicitudCambioEstrato crearSolitudCambioEstrato(
            RequestCreaSolicitudCambioEstrato request) throws ApplicationException {

        try {
            validarDataCreacionSolCambioEstrato(request);
            PcmlManager pcmlManager = new PcmlManager();
            //JDHT
            String idUsuario = request.getIdUsuario() != null ? request.getIdUsuario() : null;
            UsuariosServicesDTO usuario = obtenerUsuariosServicesDTO(idUsuario);
            ArrayList<UnidadStructPcml> resultUnidadStruct = pcmlManager.getUnidades(request.getCalleRr(),
                    request.getUnidadRr(),
                    request.getApartamentoRr(),
                    request.getComunidad());

            if (CollectionUtils.isEmpty(resultUnidadStruct)) {
                throw new ApplicationException("Unidad no encontrada en RR");
            }

            if (resultUnidadStruct.get(0).getEstratoUnidad() != null
                    && resultUnidadStruct.get(0).
                    getEstratoUnidad().equalsIgnoreCase(request.getEstratoNuevo())) {
                throw new ApplicationException(
                        "El estrato debe ser diferente al estrato actual de la unidad");
            }

            validarExistenciaSolicitudEnCurso(request);
            String cambioDir = "2";
            String dirCompleta = request.getCalleRr() + " "
                    + request.getUnidadRr() + " "
                    + request.getApartamentoRr();

            Solicitud solicitudToCreate = new Solicitud();
            solicitudToCreate.setUsuario(usuario.getUsuario());
            solicitudToCreate.setSolicitante(usuario.getNombre());
            solicitudToCreate.setCorreo(usuario.getEmail());
            solicitudToCreate.setTelSolicitante(usuario.getTelefono());

            if (usuario.getCedula() != null) {
                solicitudToCreate.setIdSolicitante(new BigDecimal(usuario.getCedula()));
            }

            solicitudToCreate.setEstado(ConstantsSolicitudHhpp.SOL_PENDIENTE);
            solicitudToCreate.setTipo(ConstantsSolicitudHhpp.TIPO_VTCECSUS);
            solicitudToCreate.setCambioDir(cambioDir);
            solicitudToCreate.setFechaIngreso(new Date());
            solicitudToCreate.setCiudad(request.getComunidad());
            solicitudToCreate.setRegional(request.getDivision());
            solicitudToCreate.setStreetName(request.getCalleRr());
            solicitudToCreate.setHouseNumber(request.getUnidadRr());
            solicitudToCreate.setAparmentNumber(request.getApartamentoRr());
            solicitudToCreate.setDireccion(dirCompleta);
            solicitudToCreate.setTipoVivienda(dirCompleta);
            solicitudToCreate.setComplemento(dirCompleta);
            solicitudToCreate.setNumPuerta(request.getUnidadRr());
            solicitudToCreate.setEstratoAntiguo(resultUnidadStruct.get(0).getEstratoUnidad());
            solicitudToCreate.setEstratoNuevo(request.getEstratoNuevo());
            solicitudToCreate.setMotivo(request.getObservaciones());

            if (StringUtils.isNotBlank(request.getCuentaSuscriptor())) {
                solicitudToCreate.setCuentaSuscriptor(request.getCuentaSuscriptor());
            }

            solicitudToCreate.setContacto(request.getContacto());
            solicitudToCreate.setTelContacto(request.getTelefonoContacto());
            solicitudToCreate.setTipoSol(request.getCanalVentas());
            //creacion de solicitud
            solicitudToCreate = solicitudDaoImpl.create(solicitudToCreate);
            //Asignación de datos de respuesta
            ResponseCreaSolicitudCambioEstrato response = new ResponseCreaSolicitudCambioEstrato();
            response.setTipoRespuesta("I");
            response.setMensaje(String.format("Solicitud %s creada con exito ", solicitudToCreate.getIdSolicitud()));
            response.setIdSolicitud(solicitudToCreate.getIdSolicitud().toString());
            //carga del archivo de soporte del cambio de estrato
            response =  cargarArchivoSoporteCambioEstrato(request, solicitudToCreate.getIdSolicitud().toString(), response);
            return response;
        } catch (ApplicationException e) {
            LOGGER.error("Error en crearSolicitudCambioEstado. " + e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Realiza el cargue del archivo de soporte de la solicitud
     * de cambio de estrato.
     *
     * @param request     {@link RequestCreaSolicitudCambioEstrato}
     * @param idSolicitud {@link String}
     * @param response    {@link ResponseCreaSolicitudCambioEstrato}
     * @return {@link ResponseCreaSolicitudCambioEstrato}
     */
    private ResponseCreaSolicitudCambioEstrato cargarArchivoSoporteCambioEstrato(RequestCreaSolicitudCambioEstrato request,
            String idSolicitud, ResponseCreaSolicitudCambioEstrato response) throws ApplicationException {
        File archive = null;
        try {
            SolicitudCambioEstratoBusiness solicitudCambioEstrato = new SolicitudCambioEstratoBusiness();
            Date fecha = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
            String fileName = format.format(fecha);
            fileName += FilenameUtils.getName(request.getFileName());
            File file = new File(System.getProperty("user.dir"));
            String extension = FilenameUtils.getExtension(request.getFileName());
            archive = File.createTempFile(fileName + "-", "." + extension, file);
            escribirFicheroSolicitudCambioEstrato(request, archive);
            boolean responseCarga = solicitudCambioEstrato.uploadFile(
                    archive, request.getIdUsuario(), idSolicitud, fileName, fecha);
            String strMsn = response.getMensaje();

            if (responseCarga) {
                strMsn += ", el archivo ha sido cargado correctamente";
            } else {
                strMsn += ", no fue posible cargar el archivo";
            }

            response.setMensaje(strMsn);
            return response;

        } catch (Exception e) {
            LOGGER.error("Error al momento de realizar el cargue del archivo de soporte en la solicitud de cambio de estrato.");
            throw new ApplicationException(e);
        } finally {
            if (Objects.nonNull(archive)) {
                FileToolUtils.deleteFile(archive);
            }
        }
    }

    /**
     * Escribe el fichero de solicitud de cambio de estrato.
     *
     * @param request {@link RequestCreaSolicitudCambioEstrato}
     * @param archive {@link File}
     */
    private void escribirFicheroSolicitudCambioEstrato(RequestCreaSolicitudCambioEstrato request, File archive) {
        try (FileOutputStream output = new FileOutputStream(archive)) {
            output.write(request.getFileBytes());
        } catch (IOException e) {
            String msgError = "Ocurrió un error al procesar el archivo "
                    + "en el proceso de solcitud de cambio de estrato. : " + e.getMessage();
            LOGGER.error(msgError, e);
        }
    }

    /**
     * Comprueba si ya existe una solicitud en proceso para la unidad.
     *
     * @param request {@link RequestCreaSolicitudCambioEstrato}
     * @throws ApplicationException Excepción personalizada de aplicación.
     * @author Gildardo Mora
     */
    private static void validarExistenciaSolicitudEnCurso(RequestCreaSolicitudCambioEstrato request) throws ApplicationException {
        NegocioParamMultivalor param = new NegocioParamMultivalor();
        try {
            BigDecimal idSolicitud = param.getIsSolicitudInProcess(request.getCalleRr(), request.getUnidadRr(),
                    request.getApartamentoRr(), request.getComunidad());

            if (Objects.nonNull(idSolicitud)) {
                throw new ApplicationException("La unidad cuenta con la solitud " + idSolicitud + " activa");
            }
        } catch (Exception e) {
            String msgError = "Error al validar existencia de solicitud en curso " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Permite validar el request para la creación de la
     * solicitud de cambio de estrato para verificar que la información
     * necesaria para el proceso este completa
     *
     * @param request request con la información necesaria para la creación de la solicitud
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Johnnatan Ortiz
     */
    public void validarDataCreacionSolCambioEstrato(RequestCreaSolicitudCambioEstrato request)
            throws ApplicationException {

        if (StringUtils.isBlank(request.getIdUsuario())) {
            throw new ApplicationException("El id del usuario no puede ser vacio");
        }

        if (StringUtils.isBlank(request.getObservaciones())) {
            throw new ApplicationException("Las observaciones son requeridas");
        }

        if (StringUtils.isBlank(request.getCalleRr())) {
            throw new ApplicationException("La calle de la unidad es requerida");
        }

        if (StringUtils.isBlank(request.getUnidadRr())) {
            throw new ApplicationException("La placa de la unidad es requerida");
        }

        if (StringUtils.isBlank(request.getApartamentoRr())) {
            throw new ApplicationException("El apartamento de la unidad es requerido");
        }
        if (StringUtils.isBlank(request.getComunidad())) {
            throw new ApplicationException("La comunidad de la unidad es requerida");
        }

        if (StringUtils.isBlank(request.getDivision())) {
            throw new ApplicationException("La division de la unidad es requerida");
        }

        if (StringUtils.isBlank(request.getEstratoNuevo())) {
            throw new ApplicationException("El Estrato nuevo de la unidad es requerido");
        }

        if (StringUtils.isBlank(request.getCanalVentas())) {
            throw new ApplicationException("El canal de Ventas es requerido");
        }

        if (request.getFileBytes() == null || request.getFileBytes().length == 0) {
            throw new ApplicationException("El archivo es requerido");
        }

        if (StringUtils.isBlank(request.getFileName())) {
            throw new ApplicationException("El Nombre del archivo es requerido");
        }

        if (StringUtils.isBlank(request.getContacto())) {
            throw new ApplicationException("El Contacto es requerido");
        }

        if (StringUtils.isBlank(request.getTelefonoContacto())) {
            throw new ApplicationException("El Telefono de contacto es requerido");
        }
    }

    /**
     * Permite cambiar los HHPP de una direccion antigua. Permite cambiar de
     * forma automatica los HHPP de una direccion antiga a la direccion nueva
     *
     * @param request   request con la informacion necesaria para la modificacion
     *                  de la direccion
     * @param solicitud solicitud sobre la cual se realizan los cambios
     * @param usuario   usuario de la solicitud
     * @return proceso de cambio exitoso o no
     * @throws Exception
     * @author Juan David Hernandez
     */
    private boolean cambiarHhppDireccionAntiguaANueva(RequestCreaSolicitud request,
                                                      Solicitud solicitud, boolean cambioApto, boolean cambioAutomatico,
                                                      String usuario, int perfil, BigDecimal centroPobladoId,
                                                      List<UnidadStructPcml> unidadesConflictos, boolean habilitarRR,
                                                      BigDecimal solicitudId, CityEntity cityEntity) throws ApplicationException {
        try {
            /*author Juan David Hernandez*/
            if (request.getCityEntity() != null && request.getCityEntity().getUnidadesAsociadasPredioAntiguasList() != null
                    && !request.getCityEntity().getUnidadesAsociadasPredioAntiguasList().isEmpty()
                    && request.getCityEntity().getDireccionNuevaGeo() != null && centroPobladoId != null) {

                HhppMglManager hhppMglManager = new HhppMglManager();
                CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
                DireccionRRManager direccionRRManager = new DireccionRRManager(true);

                CmtDireccionDetalladaMgl cmtDireccionDetalladaNueva = cmtDireccionDetalleMglManager
                        .parseDrDireccionToCmtDireccionDetalladaMgl(request.getCityEntity().getDireccionNuevaGeo());

                /*Si el listado de unidades con conflicto contiene algun elemento es necesario excluirlo
                 * del cambio de dirección de antigua a nueva*/
                if (unidadesConflictos != null && !unidadesConflictos.isEmpty()) {

                    for (HhppMgl hhppCambioApto : request.getCityEntity().getUnidadesAsociadasPredioAntiguasList()) {

                        /*se realiza validacion de cambios de direccion de antigua a nueva a unidades que
                         * no presenten conflictos*/
                        boolean unidadEnConflicto = false;
                        for (UnidadStructPcml unidadStructPcml : unidadesConflictos) {
                            if (unidadStructPcml.getAptoUnidad().equalsIgnoreCase(hhppCambioApto.getHhpApart())) {
                                unidadEnConflicto = true;
                            }
                        }

                        //Si el hhpp no tiene conflicto se realiza el cambio de dirección de antigua a nueva       
                        if (!unidadEnConflicto) {
                            //Si es una subDireccion y esta encendido RR
                            if (habilitarRR && hhppCambioApto != null && hhppCambioApto.getHhpIdrR() != null
                                    && !hhppCambioApto.getHhpIdrR().isEmpty() && hhppCambioApto.getSubDireccionObj() != null
                                    && hhppCambioApto.getSubDireccionObj().getSdiId() != null
                                    && hhppCambioApto.getEstadoRegistro() != 0) {

                                /*Se realiza busqueda de todas las subdireccion del Hhpp para hacer actualización 
                             de datos del hhpp en todas sus tecnologias*/
                                List<HhppMgl> hhhpSubDirList = hhppMglManager
                                        .findHhppSubDireccion(hhppCambioApto.getSubDireccionObj().getSdiId());

                                if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                                    for (HhppMgl hhppMglSubDireccionCambioApto : hhhpSubDirList) {

                                        if (hhppMglSubDireccionCambioApto.getHhpIdrR() != null
                                                && !hhppMglSubDireccionCambioApto.getHhpIdrR().isEmpty()) {

                                            //Consume servicio que busca hhpp por Id de RR
                                            HhppResponseRR hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglSubDireccionCambioApto.getHhpIdrR());

                                            if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {

                                                /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                                if (hhppResponseRR.getComunidad() != null
                                                        && !hhppResponseRR.getComunidad().isEmpty()
                                                        && hhppResponseRR.getDivision() != null
                                                        && !hhppResponseRR.getDivision().isEmpty()
                                                        && hhppMglSubDireccionCambioApto.getNodId().getNodTipo() != null) {

                                                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                                    if (hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                            && !hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                                : hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                                    equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                                //Cambio de direccion antigua a nueva en RR
                                                                direccionRRManager.cambiarDirHHPPRR_Inspira(
                                                                        hhppResponseRR.getComunidad(),
                                                                        hhppResponseRR.getDivision(),
                                                                        hhppResponseRR.getHouse(),
                                                                        hhppResponseRR.getStreet(),
                                                                        hhppResponseRR.getApartamento(),
                                                                        hhppResponseRR.getComunidad(),
                                                                        hhppResponseRR.getDivision(),
                                                                        request.getCityEntity().getDireccionRREntityNueva().getNumeroUnidad(),
                                                                        request.getCityEntity().getDireccionRREntityNueva().getCalle(),
                                                                        hhppResponseRR.getApartamento(),
                                                                        solicitud.getIdSolicitud().toString(), usuario,
                                                                        request.getCanalVentas(),
                                                                        request.getCityEntity().getDirIgacAntiguaStr(),
                                                                        request.getDrDireccion().getIdTipoDireccion(),
                                                                        hhppMglSubDireccionCambioApto.getNodId().getComId());
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    throw new ApplicationException("RR se encuentra encendido y la subdirección tiene IdRR pero el nodo de la subdirección"
                                                            + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnologia "
                                                            + hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getNombreBasica()
                                                            + " No es posible realizar el cambio de apto a las unidades asociadas al predio.");
                                                }

                                            } else {
                                                if (hhppResponseRR != null
                                                        && hhppResponseRR.getTipoMensaje() != null
                                                        && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                                    throw new ApplicationException(hhppResponseRR.getMensaje());
                                                } else {
                                                    throw new ApplicationException("Ocurrio un error intentando "
                                                            + "consumir el servicio de consulta de hhpp en RR ");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    throw new ApplicationException("Ocurrio un error consultando el "
                                            + "listado de subDirecciones del hhpp y no es posible realizar "
                                            + "el cambio de apto de las unidades asociadas al predio");
                                }
                            } else {
                                //Obtenemos los Hhpp de la Direccion principal    
                                if (habilitarRR && hhppCambioApto != null && hhppCambioApto.getHhpIdrR() != null
                                        && hhppCambioApto.getDireccionObj() != null
                                        && hhppCambioApto.getDireccionObj().getDirId() != null && habilitarRR
                                        && hhppCambioApto.getEstadoRegistro() != 0) {

                                    List<HhppMgl> hhhpDirList = hhppMglManager.findHhppDireccion(hhppCambioApto.getDireccionObj().getDirId());

                                    if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                        for (HhppMgl hhppMglDireccionCambioApto : hhhpDirList) {

                                            if (hhppMglDireccionCambioApto.getHhpIdrR() != null
                                                    && !hhppMglDireccionCambioApto.getHhpIdrR().isEmpty()) {

                                                HhppResponseRR hhppResponseRR = new HhppResponseRR();
                                                //Consume servicio que busca hhpp por Id de RR
                                                hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglDireccionCambioApto.getHhpIdrR());

                                                if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                                    /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                                    if (hhppResponseRR.getComunidad() != null
                                                            && !hhppResponseRR.getComunidad().isEmpty()
                                                            && hhppResponseRR.getDivision() != null
                                                            && !hhppResponseRR.getDivision().isEmpty()
                                                            && hhppMglDireccionCambioApto.getNodId() != null) {

                                                        //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                                        if (hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                                && !hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                                            for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                                    : hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                                if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                                        equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                                        && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                                    //Cambio de direccion antigua a nueva en RR
                                                                    direccionRRManager.cambiarDirHHPPRR_Inspira(
                                                                            hhppResponseRR.getComunidad(),
                                                                            hhppResponseRR.getDivision(),
                                                                            hhppResponseRR.getHouse(),
                                                                            hhppResponseRR.getStreet(),
                                                                            hhppResponseRR.getApartamento(),
                                                                            hhppResponseRR.getComunidad(),
                                                                            hhppResponseRR.getDivision(),
                                                                            request.getCityEntity().getDireccionRREntityNueva().getNumeroUnidad(),
                                                                            request.getCityEntity().getDireccionRREntityNueva().getCalle(),
                                                                            hhppResponseRR.getApartamento(),
                                                                            solicitud.getIdSolicitud().toString(), usuario,
                                                                            request.getCanalVentas(),
                                                                            request.getCityEntity().getDirIgacAntiguaStr(),
                                                                            request.getDrDireccion().getIdTipoDireccion(),
                                                                            hhppMglDireccionCambioApto.getNodId().getComId());
                                                                }
                                                            }
                                                        } else {
                                                            throw new ApplicationException("Ocurrio un error obteniendo el listado de"
                                                                    + " tecnologia extendida para sincronizar con RR para la tecnología "
                                                                    + hhppMglDireccionCambioApto.getNodId().getNodTipo().getNombreBasica()
                                                                    + " y no es posible realizar "
                                                                    + "el cambio de apto de unidades asociadas al predio. ");
                                                        }
                                                    } else {
                                                        throw new ApplicationException("RR se encuentra encendido y la dirección tiene IdRR pero el nodo de la dirección"
                                                                + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnologia "
                                                                + hhppMglDireccionCambioApto.getNodId().getNodTipo().getNombreBasica());
                                                    }
                                                } else {
                                                    if (hhppResponseRR != null
                                                            && hhppResponseRR.getTipoMensaje() != null
                                                            && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                                        throw new ApplicationException(hhppResponseRR.getMensaje());
                                                    } else {
                                                        throw new ApplicationException("Ocurrio un error intentando "
                                                                + "consumir el servicio de consulta de hhpp en RR ");
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        throw new ApplicationException("Ocurrio un error consultando el "
                                                + "listado de firecciones del hhpp y no es posible realizar "
                                                + "el cambio de apto de las unidades asociadas al predio");
                                    }
                                }
                            }

                            if (hhppCambioApto.getEstadoRegistro() != 0) {
                                //Cambio de direccion de antigua a nueva en MGL
                                cmtDireccionDetalleMglManager.cambiarDireccionDetalladaHhppAntiguaANueva(hhppCambioApto,
                                        cmtDireccionDetalladaNueva, centroPobladoId, usuario, perfil);
                            }
                        }

                    }
                } else {
                    //cambios de dirección de antigua a nueva de manera normal      
                    for (HhppMgl hhppCambioApto : request.getCityEntity().getUnidadesAsociadasPredioAntiguasList()) {

                        //Si es una subDireccion y esta encendido RR
                        if (habilitarRR && hhppCambioApto != null && hhppCambioApto.getHhpIdrR() != null
                                && !hhppCambioApto.getHhpIdrR().isEmpty() && hhppCambioApto.getSubDireccionObj() != null
                                && hhppCambioApto.getSubDireccionObj().getSdiId() != null
                                && hhppCambioApto.getEstadoRegistro() != 0) {

                            /*Se realiza busqueda de todas las subdireccion del Hhpp para hacer actualización 
                            de datos del hhpp en todas sus tecnologias*/
                            List<HhppMgl> hhhpSubDirList = hhppMglManager
                                    .findHhppSubDireccion(hhppCambioApto.getSubDireccionObj().getSdiId());

                            if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                                for (HhppMgl hhppMglSubDireccionCambioApto : hhhpSubDirList) {

                                    if (hhppMglSubDireccionCambioApto.getHhpIdrR() != null
                                            && !hhppMglSubDireccionCambioApto.getHhpIdrR().isEmpty()) {
                                        /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                        if (hhppMglSubDireccionCambioApto.getNodId() != null
                                                && hhppMglSubDireccionCambioApto.getNodId().getComId().getCodigoRr() != null
                                                && !hhppMglSubDireccionCambioApto.getNodId().getComId().getCodigoRr().isEmpty()
                                                && hhppMglSubDireccionCambioApto.getNodId().getComId().getRegionalRr().getCodigoRr() != null
                                                && !hhppMglSubDireccionCambioApto.getNodId().getComId().getRegionalRr().getCodigoRr().isEmpty()
                                                && hhppMglSubDireccionCambioApto.getNodId().getNodTipo() != null) {

                                            //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                            if (hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                    && !hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                                for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                        : hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                    if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                            equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                            && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                        //Cambio de direccion antigua a nueva en RR
                                                        direccionRRManager.cambiarDirHHPPRR_Inspira(
                                                                hhppMglSubDireccionCambioApto.getNodId().getComId().getCodigoRr(),
                                                                hhppMglSubDireccionCambioApto.getNodId().getComId().getRegionalRr().getCodigoRr(),
                                                                hhppMglSubDireccionCambioApto.getHhpPlaca(),
                                                                hhppMglSubDireccionCambioApto.getHhpCalle(),
                                                                hhppMglSubDireccionCambioApto.getHhpApart(),
                                                                hhppMglSubDireccionCambioApto.getNodId().getComId().getCodigoRr(),
                                                                hhppMglSubDireccionCambioApto.getNodId().getComId().getRegionalRr().getCodigoRr(),
                                                                request.getCityEntity().getDireccionRREntityNueva().getNumeroUnidad(),
                                                                request.getCityEntity().getDireccionRREntityNueva().getCalle(),
                                                                hhppMglSubDireccionCambioApto.getHhpApart(),
                                                                solicitud.getIdSolicitud().toString(), usuario,
                                                                request.getCanalVentas(),
                                                                request.getCityEntity().getDirIgacAntiguaStr(),
                                                                request.getDrDireccion().getIdTipoDireccion(),
                                                                hhppMglSubDireccionCambioApto.getNodId().getComId());

                                                    }
                                                }
                                            }
                                        } else {
                                            throw new ApplicationException("RR se encuentra encendido y la subdirección tiene IdRR pero el nodo de la subdirección"
                                                    + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnologia "
                                                    + hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getNombreBasica()
                                                    + " No es posible realizar el cambio de apto a las unidades asociadas al predio.");
                                        }
                                    }
                                }
                            }
                        } else {
                            //Obtenemos los Hhpp de la Direccion principal    
                            if (habilitarRR && hhppCambioApto != null && hhppCambioApto.getHhpIdrR() != null
                                    && hhppCambioApto.getDireccionObj() != null
                                    && hhppCambioApto.getDireccionObj().getDirId() != null
                                    && hhppCambioApto.getEstadoRegistro() != 0) {

                                List<HhppMgl> hhhpDirList = hhppMglManager.findHhppDireccion(hhppCambioApto.getDireccionObj().getDirId());

                                if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                    for (HhppMgl hhppMglDireccionCambioApto : hhhpDirList) {

                                        if (hhppMglDireccionCambioApto.getHhpIdrR() != null
                                                && !hhppMglDireccionCambioApto.getHhpIdrR().isEmpty()) {
                                            /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                            if (hhppMglDireccionCambioApto.getNodId() != null
                                                    && hhppMglDireccionCambioApto.getNodId().getComId().getCodigoRr() != null
                                                    && !hhppMglDireccionCambioApto.getNodId().getComId().getCodigoRr().isEmpty()
                                                    && hhppMglDireccionCambioApto.getNodId().getComId().getRegionalRr().getCodigoRr() != null
                                                    && !hhppMglDireccionCambioApto.getNodId().getComId().getRegionalRr().getCodigoRr().isEmpty()) {

                                                //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                                if (hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                        && !hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                                    for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                            : hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                        if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                                equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                                && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                            //Cambio de direccion antigua a nueva en RR
                                                            direccionRRManager.cambiarDirHHPPRR_Inspira(
                                                                    hhppMglDireccionCambioApto.getNodId().getComId().getCodigoRr(),
                                                                    hhppMglDireccionCambioApto.getNodId().getComId().getRegionalRr().getCodigoRr(),
                                                                    hhppMglDireccionCambioApto.getHhpPlaca(),
                                                                    hhppMglDireccionCambioApto.getHhpCalle(),
                                                                    hhppMglDireccionCambioApto.getHhpApart(),
                                                                    hhppMglDireccionCambioApto.getNodId().getComId().getCodigoRr(),
                                                                    hhppMglDireccionCambioApto.getNodId().getComId().getRegionalRr().getCodigoRr(),
                                                                    request.getCityEntity().getDireccionRREntityNueva().getNumeroUnidad(),
                                                                    request.getCityEntity().getDireccionRREntityNueva().getCalle(),
                                                                    hhppMglDireccionCambioApto.getHhpApart(),
                                                                    solicitud.getIdSolicitud().toString(), usuario,
                                                                    request.getCanalVentas(),
                                                                    request.getCityEntity().getDirIgacAntiguaStr(),
                                                                    request.getDrDireccion().getIdTipoDireccion(),
                                                                    hhppMglDireccionCambioApto.getNodId().getComId());
                                                        }
                                                    }
                                                } else {
                                                    throw new ApplicationException("Ocurrio un error obteniendo el listado de"
                                                            + " tecnologia extendida para sincronizar con RR para la tecnología "
                                                            + hhppMglDireccionCambioApto.getNodId().getNodTipo().getNombreBasica()
                                                            + " y no es posible realizar "
                                                            + "el cambio de apto de unidades asociadas al predio. ");
                                                }
                                            } else {
                                                throw new ApplicationException("RR se encuentra encendido y la dirección tiene IdRR pero el nodo de la dirección"
                                                        + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnologia "
                                                        + hhppMglDireccionCambioApto.getNodId().getNodTipo().getNombreBasica());
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        if (hhppCambioApto.getEstadoRegistro() != 0) {
                            //Cambio de direccion de antigua a nueva en MGL
                            cmtDireccionDetalleMglManager.cambiarDireccionDetalladaHhppAntiguaANueva(hhppCambioApto,
                                    cmtDireccionDetalladaNueva, centroPobladoId, usuario, perfil);
                            LOGGER.error("cambio de apto con unidades de manera normal");
                        }
                    }

                }
            }

            //si se trata de un cambio automatico debemos cerrar la solicitud
            if (!cambioApto && cambioAutomatico
                    && !request.getCityEntity().isExisteHhppAntiguoNuevo()) {

                //Validación de que la dirección tenga antigua y nueva para poderla cambiar
                if (request.getCityEntity().getDireccionAntiguaGeo() != null
                        && request.getCityEntity().getDireccionNuevaGeo() != null) {

                    //TODO REALIZAR CAMBIO DE DIRECCION ANTIGUA A NUEVA              
                    String razon = Constant.RZ_CAMBIO_DE_DIRECCION_REALIZADO;
                    StringBuilder respuestaGestion = new StringBuilder();
                    respuestaGestion.append("SE REALIZA ACTUALIZACION DE LA DIRECCION ACTUAL EN MGL: [ ");
                    respuestaGestion.append(request.getCityEntity().getDireccionRREntityAntigua().getCalle());
                    respuestaGestion.append(",");
                    respuestaGestion.append(request.getCityEntity().getDireccionRREntityAntigua().getNumeroUnidad());
                    respuestaGestion.append(",");
                    respuestaGestion.append(request.getCityEntity().getDireccionRREntityAntigua().getNumeroApartamento());
                    respuestaGestion.append(" ].  A LA DIRECCION: [ ");
                    respuestaGestion.append(request.getCityEntity().getDireccionRREntityNueva().getCalle());
                    respuestaGestion.append(",");
                    respuestaGestion.append(request.getCityEntity().getDireccionRREntityNueva().getNumeroUnidad());
                    respuestaGestion.append(",");
                    respuestaGestion.append(request.getCityEntity().getDireccionRREntityNueva().getNumeroApartamento());
                    respuestaGestion.append(" ]. ");

                    solicitud.setEstado(ConstantsSolicitudHhpp.SOL_FINALIZADO);
                    Date dateGestion = new Date();
                    solicitud.setFechaCancelacion(dateGestion);
                    solicitud.setRptGestion(razon);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    solicitud.setRespuesta("|" + format.format(dateGestion) + "| "
                                    + razon + " " + respuestaGestion);
                    solicitud.setDisponibilidadGestion("0");
                    solicitud.setUsuario(usuario);
                    solicitudDaoImpl.update(solicitud);
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error en cambiarHhppDireccionAntiguaNueva. " + e.getMessage());
            throw new ApplicationException("Error al momento de cambiar la dirección. EX000: " + e.getMessage(), e);
        }
        return true;
    }

    /**
     * Permite validar el request para la creación de la
     * solicitud de cambio de estrato para verificar que la información
     * necesaria para el proceso este completa
     *
     * @param request {@link RequestCreaSolicitud} Request con la información
     *                                            necesaria para la creación de la solicitud
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Johnnatan Ortiz
     */
    public void validarDataCreacionSolicitud(RequestCreaSolicitud request) throws ApplicationException {
        if (StringUtils.isBlank(request.getIdUsuario())) {
            throw new ApplicationException("El id del usuario es requerido");
        }

        if (StringUtils.isBlank(request.getObservaciones())) {
            throw new ApplicationException("Las observaciones son requeridas");
        }

        if (request.getDrDireccion() == null) {
            throw new ApplicationException("Las direccion es requerida");
        }

        if (StringUtils.isBlank(request.getComunidad())) {
            throw new ApplicationException("La comunidad de la unidad es requerida");
        }

        if (StringUtils.isBlank(request.getCanalVentas())) {
            throw new ApplicationException("El canal de Ventas es requerido");
        }

        if (StringUtils.isBlank(request.getContacto())) {
            throw new ApplicationException("El Contacto es requerido");
        }

        if (StringUtils.isBlank(request.getTelefonoContacto())) {
            throw new ApplicationException("El Telefono de contacto es requerido");
        }
    }

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    /**
     * Crea Solicitud Dth.Permite crear una solicitud Dth sobre el repositorio
     * para su posterior gestion
     *
     * @param request                 request con la informacion necesaria para la creacion de
     *                                la solicitud Dth
     * @param tipoTecnologia          indica el tipo de tecnologia con el que se crea la
     *                                solicitud
     * @param unidadesList
     * @param tiempoDuracionSolicitud
     * @param idCentroPoblado
     * @param usuarioVt
     * @param perfilVt
     * @param tipoAccionSolicitud
     * @param direccionActual         se utiliza para recibir la direccion antigua en
     *                                caso de existir
     * @param habilitarRR_MER
     * @param solicitudDesdeMER
     * @param centroPobladoDireccion
     * @param ciudadDireccion
     * @param departamentoDireccion
     * @param flagMicro
     * @return respuesta con el proceso de creacion de la solicitud Dth
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public ResponseCreaSolicitud crearSolicitudDth(
            RequestCreaSolicitud request, String tipoTecnologia,
            List<UnidadStructPcml> unidadesList,
            String tiempoDuracionSolicitud, BigDecimal idCentroPoblado,
            String usuarioVt, int perfilVt, String tipoAccionSolicitud,
            Solicitud direccionActual, boolean habilitarRR_MER,
            boolean solicitudDesdeMER, GeograficoPoliticoMgl centroPobladoDireccion,
            GeograficoPoliticoMgl ciudadDireccion, GeograficoPoliticoMgl departamentoDireccion, boolean flagMicro)
            throws ApplicationException, ExceptionDB {
        try {
            boolean habilitarRR = false;
            CmtTipoSolicitudMgl tipoSolicitudMgl = new CmtTipoSolicitudMgl();
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            if (!solicitudDesdeMER) {
                //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR                 
                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                    habilitarRR = true;
                }
            } else {
                habilitarRR = habilitarRR_MER;
            }

            GeograficoPoliticoMgl centroPoblado = null;
            GeograficoPoliticoMgl ciudad = null;
            GeograficoPoliticoMgl departamento = null;

            if (!habilitarRR_MER) {
                DireccionesValidacionManager direccionesValidacionManager = new DireccionesValidacionManager();
                //Validación de la dirección con el minimo de partes que la construyan
                direccionesValidacionManager.validarEstructuraDireccion(request.getDrDireccion(), Constant.TIPO_VALIDACION_DIR_HHPP);
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                centroPoblado = geograficoPoliticoManager.findById(idCentroPoblado);
                ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
                departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
                validarDataCreacionSolicitud(request);
            } else {
                centroPoblado = centroPobladoDireccion;
                ciudad = ciudadDireccion;
                departamento = departamentoDireccion;
            }
            //JDHT
            String idUsuario = request.getIdUsuario() != null ? request.getIdUsuario() : null;
            UsuariosServicesDTO usuario = null;

            if (StringUtils.isNotBlank(idUsuario)) {
                UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
                // Buscar al usuario a traves de la cédula. 
                // Si no se encuentra, realiza la búsqueda por medio del parametro de VISOR.
                if (flagMicro) {
                    usuario = SesionUtil.getUserDataMicroSitio();
                } else {
                    usuario = usuariosManager.consultaInfoUserPorCedulaVISOR(idUsuario);
                }
            }

            if (usuario == null) {
                throw new ApplicationException("El usuario no fue encontrado");
            }

            //Asigmanos el tipo de direccion al drdireccion P:Principal
            request.getDrDireccion().setDirPrincAlt("P");
            //Asigmanos el estado GEO de la direccion al drdireccion
            String estadoDireccion = Optional.of(request)
                    .map(RequestCreaSolicitud::getCityEntity)
                    .map(CityEntity::getEstadoDir)
                    .orElse(null);

            if (StringUtils.isNotBlank(estadoDireccion)) {
                request.getDrDireccion().setEstadoDirGeo(request.getCityEntity().getEstadoDir());
            }
            //Asigmanos el estrato GEO de la direccion al drdireccion 
            if (request.getCityEntity() != null && request.getCityEntity().getEstratoDir() != null
                    && !request.getCityEntity().getEstratoDir().trim().isEmpty()) {
                request.getDrDireccion().setEstrato(request.getCityEntity().getEstratoDir());
            }

            //Realiza busqueda de tipo de la basica del tipo de solicitud
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();

            CmtTipoBasicaMgl tipoBasicaTipoAccion = cmtTipoBasicaMglManager.findByCodigoInternoApp
                    (Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            //obtiene listado de tipo de solicitud basica
            List<CmtBasicaMgl> tipoAccionSolicitudBasicaList = cmtBasicaMglManager.findByTipoBasica(tipoBasicaTipoAccion);

            CmtBasicaMgl tipoSolicitudBasicaId = new CmtBasicaMgl();

            if (tipoAccionSolicitudBasicaList != null && !tipoAccionSolicitudBasicaList.isEmpty()) {
                for (CmtBasicaMgl cmtBasicaMgl : tipoAccionSolicitudBasicaList) {
                    if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(tipoAccionSolicitud)) {
                        //obtiene la basica de tipo de solicitud ingresada
                        tipoSolicitudBasicaId = cmtBasicaMgl.clone();
                        break;
                    }
                }
            } else {
                throw new ApplicationException(ConstantsSolicitudHhpp.MSG_TIPO_SOL_NO_VALIDO);
            }

            //Se asigna por default VTCASA pero se sobreescribe si llega a ser otro valor.
            String tipo = ConstantsSolicitudHhpp.TIPO_VTCASA;
            if (tipoSolicitudBasicaId != null && tipoSolicitudBasicaId.getBasicaId() != null) {
                CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
                //Se obtiene el tipo de solicitud de la tabla de tipo de solicitud apartir de la basica
                tipoSolicitudMgl = cmtTipoSolicitudMglManager
                        .findTipoSolicitudByBasicaIdTipoSolicitud(tipoSolicitudBasicaId.getBasicaId());
                //se reasigna el valor para RR
                if (tipoSolicitudMgl != null && tipoSolicitudMgl.getTipoSolicitudId() != null) {
                    tipo = tipoSolicitudMgl.getAbreviatura();
                }
            } else {
                throw new ApplicationException("El tipo de solicitud ingresado no se encuentra configurado.");
            }

            String cambioDir = tipoAccionSolicitud;//0-Creacion, 1-CambioDir, 2-reactivar, 3-cambioestrato
            boolean convertToCambioDir = request.getCityEntity() != null
                    && request.getCityEntity().getExisteRr() != null
                    && !request.getCityEntity().getExisteRr().trim().isEmpty()
                    && request.getCityEntity().getExisteRr().contains("(C)");
            if (convertToCambioDir) {
                cambioDir = Constant.RR_DIR_CAMB_HHPP_1;
            }

            //validar si es una ciudad especial para solicitud DTH
            /*@author Juan David Hernandez*/
            if (tipoTecnologia.equalsIgnoreCase(Constant.TIPO_SOLICITUD_DTH)) {
                List<ParametrosMgl> ciudadesEspecialList
                        = parametrosMglManager
                        .findByAcronimo(Constant.CIUDADES_ESPECIALES);
                if (ciudadesEspecialList != null && !ciudadesEspecialList.isEmpty()
                        && ciudadesEspecialList.get(0).getParValor().contains(centroPoblado.getGeoCodigoDane())) {
                    throw new ApplicationException("No se puede crear el HHPP porque la ciudad es especial. ");
                }
            }

            //SI LA SOLICITUD VIENE DESDE WEB SERVICE NO CREA LA DIRECCIÓN PORQUE YA SE REALIZÓ EN EL SERVICIO
            ResponseMessage responseMessageCreateDir = new ResponseMessage();
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String address = drDireccionManager.getDireccion(request.getDrDireccion());
            String barrioStr = drDireccionManager.obtenerBarrio(request.getDrDireccion());

            if (!request.isCreacionDesdeWebService()) {
                AddressRequest addressRequest = new AddressRequest();
                addressRequest.setCodDaneVt(centroPobladoDireccion.getGeoCodigoDane());
                addressRequest.setAddress(address);
                addressRequest.setCity(ciudad.getGpoNombre());
                addressRequest.setState(departamento.getGpoNombre());
                addressRequest.setNeighborhood(barrioStr);

                AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
                responseMessageCreateDir = addressEJBRemote.createAddress(addressRequest,
                        usuario.getUsuario(), "MGL", "", request.getDrDireccion());

                if (responseMessageCreateDir.getIdaddress() != null
                        && !responseMessageCreateDir.getIdaddress().trim().isEmpty()) {
                    request.getDrDireccion().setIdDirCatastro(
                            responseMessageCreateDir.getIdaddress());
                } else {
                    throw new ApplicationException(responseMessageCreateDir.getMessageText());
                }

            } else {
                responseMessageCreateDir.setNuevaDireccionDetallada(request.getDireccionDetallada());
            }

            Solicitud solicitudToCreate = new Solicitud();
            /*@Juan David Hernandez*/
            /*ajuste realizado para obtener la tecnologia y asignarla a la
             * solicitud apartir de la abreviatura de la tecnologia. */
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            CmtBasicaMgl cmtBasicaMgl = new CmtBasicaMgl();
            cmtBasicaMgl.setTipoBasicaObj(cmtTipoBasicaMgl);
            cmtBasicaMgl.setAbreviatura(tipoTecnologia);
            solicitudToCreate.setTecnologiaId(cmtBasicaMglManager.findByAbreviaAndTipoBasica(cmtBasicaMgl));
            /*@Juan David Hernandez*/

            if (responseMessageCreateDir.getNuevaDireccionDetallada() != null
                    && responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionDetalladaId() != null) {
                solicitudToCreate.setDireccionDetallada(responseMessageCreateDir.getNuevaDireccionDetallada());

                List<Solicitud> solicitudEnCursoList = null;

                if (solicitudToCreate.getTecnologiaId() != null) {
                    //Validacion si existe una solicitud en curso para la unidad            
                    solicitudEnCursoList = solictudesHhppEnCurso(responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionDetalladaId(),
                            idCentroPoblado, solicitudToCreate.getTecnologiaId().getBasicaId(),
                            tipoAccionSolicitud);
                } else {
                    throw new ApplicationException("No existe una tecnologia con abreviatura:" + tipoTecnologia + "");
                }

                if (solicitudEnCursoList != null && !solicitudEnCursoList.isEmpty()) {
                    throw new ApplicationException("Ya existe una solicitud en curso que se encuentra PENDIENTE.");
                }

                if (!solicitudDesdeMER) {
                    if (cambioDir.equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)) {
                        validarDireccionEnCuentaMatriz(request.getDrDireccion(), centroPoblado, tipoAccionSolicitud);
                    }
                }
            } else {
                throw new ApplicationException("No fue posible obtener el valor de la direccion detallada. "
                        + "Intente de nuevo por favor");
            }

            //Si tiene direccion antigua se establecen los valores a la solicitud
            if (direccionActual != null && direccionActual.getDireccionAntiguaIgac() != null
                    && !direccionActual.getDireccionAntiguaIgac().isEmpty()) {
                solicitudToCreate.setDireccionAntiguaIgac(direccionActual.getDireccionAntiguaIgac());
            }

            /*@author Juan David Hernandez*/
            if (cambioDir.equals(Constant.RR_DIR_CREA_HHPP_0)) {
                String multiOrigen = null;
                if (centroPoblado.getGpoId() != null) {
                    multiOrigen = centroPoblado.getGpoMultiorigen();
                }
            }

            /* Al realizar cambio de dirección se realiza se establece
             la direccion ingresada para guardarla como antigua */
            /*@author Juan David Hernandez*/
            if (cambioDir.equals(Constant.RR_DIR_CAMB_HHPP_1)) {
                if (direccionActual != null) {
                    /*Si tiene direccion antigua se establecen los
                     * valores a la solicitud */
                    if (direccionActual.getStreetName() != null
                            && !direccionActual.getStreetName().isEmpty()
                            && direccionActual.getHouseNumber() != null
                            && !direccionActual.getHouseNumber().isEmpty()
                            && direccionActual.getAparmentNumber() != null
                            && !direccionActual.getAparmentNumber().isEmpty()) {

                        solicitudToCreate.setStreetName(direccionActual.getStreetName());
                        solicitudToCreate.setHouseNumber(direccionActual.getHouseNumber());
                        solicitudToCreate.setAparmentNumber(direccionActual.getAparmentNumber());
                        solicitudToCreate.setCpTipoNivel5(request.getDrDireccion().getCpTipoNivel5() != null ? request.getDrDireccion().getCpTipoNivel5() : null);
                        solicitudToCreate.setCpTipoNivel6(request.getDrDireccion().getCpTipoNivel6() != null ? request.getDrDireccion().getCpTipoNivel6() : null);
                        solicitudToCreate.setCpValorNivel5(request.getDrDireccion().getCpValorNivel5() != null ? request.getDrDireccion().getCpValorNivel5() : null);
                        solicitudToCreate.setCpValorNivel6(request.getDrDireccion().getCpValorNivel6() != null ? request.getDrDireccion().getCpValorNivel6() : null);

                        HhppMglManager hhppMglManager = new HhppMglManager();
                        CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
                        HhppMgl hhppCambioApto = hhppMglManager.findById(direccionActual.getHhppMgl().getHhpId());
                        BigDecimal sdirId = null;
                        if (hhppCambioApto.getSubDireccionObj() != null) {
                            sdirId = hhppCambioApto.getSubDireccionObj().getSdiId();
                        }
                        List<CmtDireccionDetalladaMgl> direccionDetalladaList
                                = direccionDetalladaManager.findDireccionDetallaByDirIdSdirId(hhppCambioApto.getDireccionObj().getDirId(), sdirId);

                        solicitudToCreate.setDireccionDetalladaOrigenSolicitud(direccionDetalladaList.get(0));

                    }
                }
            }

            //se valida que el id del hhpp este cargado para guardarlo, se utiliza para Reactivación
            if (direccionActual != null && direccionActual.getHhppMgl() != null
                    && direccionActual.getHhppMgl().getHhpId() != null) {
                solicitudToCreate.setHhppMgl(direccionActual.getHhppMgl());
            }

            String complementoDir = drDireccionManager.getComplementoDireccion();
            String placaDir = request.getDrDireccion().getPlacaDireccion();

            solicitudToCreate.setSolicitante(usuario.getNombre().toUpperCase());
            solicitudToCreate.setCorreo(usuario.getEmail());
            solicitudToCreate.setTelSolicitante(usuario.getTelefono());
            if (usuario.getCedula() != null) {
                solicitudToCreate.setIdSolicitante(new BigDecimal(usuario.getCedula()));
            }
            solicitudToCreate.setEstado(ConstantsSolicitudHhpp.SOL_PENDIENTE);
            solicitudToCreate.setTipo(tipo);
            solicitudToCreate.setCambioDir(cambioDir);
            solicitudToCreate.setFechaIngreso(new Date());
            solicitudToCreate.setCentroPobladoId(idCentroPoblado);
            if (convertToCambioDir) {
                solicitudToCreate.setStreetName(request.getCityEntity().getDireccionRREntityAntigua().getCalle());
                solicitudToCreate.setHouseNumber(request.getCityEntity().getDireccionRREntityAntigua().getNumeroUnidad());
                solicitudToCreate.setAparmentNumber(request.getCityEntity().getDireccionRREntityAntigua().getNumeroApartamento());
                request.getCityEntity().setDirIgacAntiguaStr(request.getCityEntity().getDireccionRREntityAntigua()
                        .getCalle() + " " + request.getCityEntity().getDireccionRREntityAntigua().getNumeroUnidad()
                        + " " + request.getCityEntity().getDireccionRREntityAntigua().getNumeroApartamento());
            }
            solicitudToCreate.setDireccion(address);
            solicitudToCreate.setTipoVivienda(complementoDir);
            solicitudToCreate.setNumPuerta(placaDir);
            solicitudToCreate.setMotivo(request.getObservaciones().toUpperCase());
            solicitudToCreate.setContacto(request.getContacto().toUpperCase());
            solicitudToCreate.setTelContacto(request.getTelefonoContacto());
            solicitudToCreate.setTipoSol(request.getCanalVentas());
            solicitudToCreate.setCuentaMatriz("0");
            solicitudToCreate.setDisponibilidadGestion("0");
            solicitudToCreate.setCasoTcrmId(casoTcrmId);
            solicitudToCreate.setEstadoRegistro(1);
            if (request.getCityEntity() != null) {
                solicitudToCreate.setTipoTecHabilitadaId(request.getCityEntity().getMessage());
            }
            if (request.getDrDireccion() != null && request.getDrDireccion().getDireccionRespuestaGeo() != null
                    && !request.getDrDireccion().getDireccionRespuestaGeo().isEmpty()) {
                solicitudToCreate.setDireccionRespuestaGeo(request.getDrDireccion().getDireccionRespuestaGeo());
            }

            //creacion de solicitud
            solicitudToCreate.setNumDocCliente(request.getNumDocCli());
            solicitudToCreate.setTipoDoc(request.getTipoDocumento());
            solicitudToCreate = solicitudDaoImpl.create(solicitudToCreate);
            if (solicitudToCreate != null
                    && solicitudToCreate.getIdSolicitud() != null) {
                request.getDrDireccion().setIdSolicitud(solicitudToCreate.getIdSolicitud());
                //se comenta ya que esta funcionalidad la hace la tabla direccionDetallada
            }

            boolean modificacionUnidades = false;
            List<UnidadStructPcml> unidadesConflictos = null;
            if (unidadesList != null && !unidadesList.isEmpty()) {
                modificacionUnidades = agregarNuevosApartamentosModificados(unidadesList,
                        solicitudToCreate, usuario.getUsuario(), perfilVt);
                /*Se obtienen las unidades que presentan conflictos para excluirlas del
                 * cambio de dirección antigua a nueva */
                unidadesConflictos = obtenerUnidadesConflictos(unidadesList);
            }

            /*author Juan David Hernandez*/
            if (request.getCityEntity() != null && request.getCityEntity().getUnidadesAsociadasPredioAntiguasList() != null
                    && !request.getCityEntity().getUnidadesAsociadasPredioAntiguasList().isEmpty()
                    && request.getCityEntity().getDireccionNuevaGeo() != null && idCentroPoblado != null) {
                //si existen unidades en la direccion antigua cambiamos esas unidades
                cambiarHhppDireccionAntiguaANueva(request, solicitudToCreate, modificacionUnidades,
                        convertToCambioDir, usuario.getUsuario(), perfilVt, idCentroPoblado,
                        unidadesConflictos, habilitarRR, solicitudToCreate.getIdSolicitud(),
                        request.getCityEntity());
            }


            if (tipoSolicitudMgl != null && tipoSolicitudMgl.getTipoSolicitudId() != null &&
                    solicitudToCreate != null && solicitudToCreate.getIdSolicitud() != null) {
                /*Se guarda el centro poblado en la entidad que contiene la relacion de solicitud-tiposolicitud */
                CmtSolicitudTipoSolicitudMgl cmtSolicitudTipoSolicitudMgl = new CmtSolicitudTipoSolicitudMgl();
                CmtSolicitudTipoSolicitudMglManager cmtSolicitudTipoSolicitudMglManager = new CmtSolicitudTipoSolicitudMglManager();
                cmtSolicitudTipoSolicitudMgl.setSolicitudObj(solicitudToCreate);
                cmtSolicitudTipoSolicitudMgl.setTipoSolicitudObj(tipoSolicitudMgl);
                cmtSolicitudTipoSolicitudMglManager.create(cmtSolicitudTipoSolicitudMgl, usuarioVt, perfilVt);
            }

            CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();

            CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();
            //asigna tiempo transcurrido en la solicitud (cronómetro en pantalla)

            if (tiempoDuracionSolicitud == null) {
                tiempoDuracionSolicitud = ConstantsSolicitudHhpp.DEFAULT_TIME;
            }
            cmtTiempoSolicitudMglToCreate.setTiempoSolicitud(tiempoDuracionSolicitud);
            /*asigna el objeto solicitud con el cual se relacionaran los tiempos guardados */
            cmtTiempoSolicitudMglToCreate.setSolicitudObj(solicitudToCreate);
            /*Realiza la actualización de los tiempos de la solicitud en la base de datos.*/
            cmtTiempoSolicitudMglToCreate.setTiempoEspera(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setTiempoGestion(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setTiempoTotal(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setArchivoSoporte("NA");
            //guarda en la base de datos el track de tiempos.
            cmtTiempoSolicitudMglManager.create(cmtTiempoSolicitudMglToCreate, usuarioVt, perfilVt);

            //DATOS PARA ENVIAR AL CORREO
            String tipoSolicitudNombre = "";
            CmtBasicaMgl tipoSolicitudBasica = obtenerTipoAccionSolicitud(solicitudToCreate.getCambioDir());
            if (tipoSolicitudBasica != null && tipoSolicitudBasica.getNombreBasica() != null
                    && !tipoSolicitudBasica.getNombreBasica().isEmpty()) {
                tipoSolicitudNombre = tipoSolicitudBasica.getNombreBasica();
            }

            //JDHT Envio de correo a solicitante
            enviarCorreoGestionSolicitud(usuario.getEmail(), "", "Creacion solicitud de Hhpp", mensajeCorreoSolicitud(solicitudToCreate, tipoSolicitudNombre,
                    departamento.getGpoNombre(), ciudad.getGpoNombre(), centroPoblado.getGpoNombre(), address));

            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("I");
            response.setMensaje(String.format("Solicitud %s creada con exito" , solicitudToCreate.getIdSolicitud()));
            response.setIdSolicitud(solicitudToCreate.getIdSolicitud().toString());
            return response;

        } catch (Exception e) {
            LOGGER.error("Error en crearSolicitudDth. " + e.getMessage(), e);
            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("E");
            response.setMensaje(e.getMessage());
            response.setIdSolicitud(null);
            return response;
        }
    }

    public ResponseCreaSolicitud crearGestionarSolicitudVisor(
            RequestCreaSolicitud request, String tipoTecnologia,
            String tiempoDuracionSolicitud, BigDecimal idCentroPoblado,
            String usuarioVt, int perfilVt, String nodoGestion,
            String nodoCercano, String respuestaGestion,
            String respuesta)
            throws ApplicationException, ExceptionDB {

        DireccionesValidacionManager direccionesValidacionManager = new DireccionesValidacionManager();
        RequestConstruccionDireccion requestConstruccionDireccion = new RequestConstruccionDireccion();
        requestConstruccionDireccion.setDrDireccion(request.getDrDireccion());
        requestConstruccionDireccion.setIdUsuario(usuarioVt);
        requestConstruccionDireccion.setComunidad(request.getComunidad());
        requestConstruccionDireccion.setDireccionStr(request.getCityEntity().getAddress());

        boolean habilitarRR = false;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR                 
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }

        CityEntity cityEntity = null;
        //Se realiza validación de la dirección para obtener la dirección Antigua
        cityEntity = direccionesValidacionManager.validaDireccion(requestConstruccionDireccion, false);
        /*Si tiene direccion antigua se asigna y se guarda en el campo OFICINA de la solicitud
            para en la gestión extraerla*/
        Solicitud solicitudDireccionAntigua = new Solicitud();
        if (cityEntity.getDireccionRREntityAntigua() != null) {

            String direccionAntigua = cityEntity.getDireccionRREntityAntigua()
                    .getCalle() + "&"
                    + cityEntity.getDireccionRREntityAntigua()
                    .getNumeroUnidad() + "&"
                    + cityEntity.getDireccionRREntityAntigua()
                    .getNumeroApartamento();

            solicitudDireccionAntigua.setDireccionAntiguaIgac(direccionAntigua);
        }

        ResponseCreaSolicitud responseCreaSolicitud = crearSolicitudDth(request, tipoTecnologia,
                null, tiempoDuracionSolicitud, idCentroPoblado,
                usuarioVt, perfilVt, "0", solicitudDireccionAntigua, habilitarRR, false, null,
                null, null, false);

        if (responseCreaSolicitud != null
                && responseCreaSolicitud.getIdSolicitud()
                != null && !responseCreaSolicitud.getIdSolicitud().trim().isEmpty()) {
            Solicitud solicitudToCreate = findById(new BigDecimal(responseCreaSolicitud.getIdSolicitud()));

            if (solicitudToCreate != null
                    && solicitudToCreate.getIdSolicitud() != null) {
                solicitudToCreate.setCentroPobladoId(idCentroPoblado);
                gestionSolicitudVisor(request, usuarioVt, perfilVt,
                        nodoGestion, nodoCercano, respuestaGestion,
                        respuesta, solicitudToCreate, tiempoDuracionSolicitud);

                ResponseCreaSolicitud response = new ResponseCreaSolicitud();
                response.setTipoRespuesta("I");
                response.setMensaje("Solicitud "
                        + solicitudToCreate.getIdSolicitud() + " creada con exito");
                response.setIdSolicitud(solicitudToCreate.getIdSolicitud().toString());
                return response;
            }
        }
        return responseCreaSolicitud;
    }

    public void gestionSolicitudVisor(RequestCreaSolicitud request,
                                      String usuarioVt, int perfilVt,
                                      String nodoGestion, String nodoCercano, String respuestaGestion,
                                      String respuesta, Solicitud solicitudToCreate,
                                      String tiempoDuracionSolicitud) throws ApplicationException {
        try {
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                habilitarRR = true;
            }
            DetalleDireccionEntity detalleDireccionEntity = request.getDrDireccion()
                    .convertToDetalleDireccionEntity();

            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPoblado
                    = geograficoPoliticoManager.findById(solicitudToCreate.getCentroPobladoId());
            GeograficoPoliticoMgl ciudad
                    = geograficoPoliticoManager.findById(solicitudToCreate.getCentroPobladoId());
            GeograficoPoliticoMgl departamento
                    = geograficoPoliticoManager.findById(solicitudToCreate.getCentroPobladoId());

            String multiOrigen = null;

            //Conocer si es multi-origen
            if (centroPoblado.getGpoId() != null) {
                multiOrigen = centroPoblado.getGpoMultiorigen();
            }
            NodoMglManager nodoMglManager = new NodoMglManager();
            NodoMgl nodoMgl = nodoMglManager.findByCodigoAndCity(nodoGestion, centroPoblado.getGpoId());

            detalleDireccionEntity.setMultiOrigen(multiOrigen);
            //obtener la direccion en formato RR
            DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity, multiOrigen, centroPoblado);
            DireccionRREntity direccionRREntity = direccionRRManager.getDireccion();

            solicitudToCreate.setRespuesta(respuesta);
            solicitudToCreate.setRptGestion(respuestaGestion);

            //Se guardan los tiempo de traza de la solicitud en la base de datos
            CmtTiempoSolicitudMglManager CmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
            CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = solicitudToCreate.getTiempoSolicitudMgl();

            //Tiempo de tardo en hacerse la solicitud
            //Tiempo de tardo en hacerse la gestión de la solicitud
            cmtTiempoSolicitudMglToCreate.setTiempoGestion(ConstantsSolicitudHhpp.DEFAULT_TIME);
            //Tiempo de espera que se tardo la solicitud en ser gestionada
            cmtTiempoSolicitudMglToCreate.setTiempoEspera(getTiempo(solicitudToCreate.getFechaIngreso(),
                    new Date()));
            //Tiempo total desde que se creo la solicitud de la gestión
            cmtTiempoSolicitudMglToCreate.setTiempoTotal(getTiempo(solicitudToCreate.getFechaIngreso(),
                    new Date()));

            gestionSolicitud(solicitudToCreate,
                    request.getDrDireccion(), detalleDireccionEntity,
                    direccionRREntity,
                    nodoMgl, usuarioVt, perfilVt,
                    request.getIdUsuario(), null,
                    cmtTiempoSolicitudMglToCreate, null, true, false, habilitarRR,
                    centroPoblado, ciudad, departamento,null);
        } catch (ApplicationException e) {
            LOGGER.error("Error en gestionSolicitudVisor. " + e.getMessage(), e);
            throw new ApplicationException("Error gestion solicitud DTH desde visor " + e.getMessage());
        }
    }

    /**
     * Función que realiza obtiene el listado de unidades en conflicto
     * extrayendolas del listado de unidades con cambios que se realizaron en la
     * pantalla de la solicitud
     *
     * @return {@link List<UnidadStructPcml>}
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public List<UnidadStructPcml> obtenerUnidadesConflictos(List<UnidadStructPcml> unidadCambiosCoflictosList) {
        try {
            List<UnidadStructPcml> unidadesConflicto = new ArrayList<>();
            //Se valida cuales de las unidades asociadas con cambios es una unidad de conflicto
            if (CollectionUtils.isNotEmpty(unidadCambiosCoflictosList)) {
                for (UnidadStructPcml unidad : unidadCambiosCoflictosList) {
                    if (StringUtils.isNotBlank(unidad.getConflictApto())
                            && unidad.getConflictApto().equalsIgnoreCase("1")) {
                        unidadesConflicto.add(unidad);
                    }
                }
            }

            return unidadesConflicto;
        } catch (Exception e) {
            LOGGER.error("Error al intentar agregar obtener las unidades con conflictos. ", e);
            String msn = "Error al intentar agregar obtener las unidades con conflictos.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn + e.getMessage(), ""));
            return Collections.emptyList();
        }
    }

    /**
     * Función que realiza guarda las unidades asociadas a la dirección que
     * tuvieron cambios de apto en la pantalla de solicitud para ser los
     * respectivos cambios de apartamento en la gestión de la solicitud.
     *
     * @return {@code boolean}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Juan David Hernandez
     */
    public boolean agregarNuevosApartamentosModificados(List<UnidadStructPcml> unidadAuxiliarList,
                                                        Solicitud solicitudCreated, String usuario, int perfil) throws ApplicationException {
        try {
            boolean modificacionUnidades = false;
            DireccionRRManager direccionRRManager = new DireccionRRManager(false);
            ModificacionDirManager modificacionDirManager = new ModificacionDirManager();

            if (CollectionUtils.isEmpty(unidadAuxiliarList)) {
                return modificacionUnidades;
            }

            //Se valida cuales de las unidades asociadas al predio tuvo cambios de apto.
                for (UnidadStructPcml unidad : unidadAuxiliarList) {
                    //verificamos que se tenga algo seleccionado en el nivel 5
                    if (StringUtils.isNotBlank(unidad.getTipoNivel5())) {
                        DetalleDireccionEntity direccion = new DetalleDireccionEntity();
                        direccion.setCptiponivel5(unidad.getTipoNivel5());

                        if (StringUtils.isNotBlank(unidad.getValorNivel5())) {
                            direccion.setCpvalornivel5(unidad.getValorNivel5().trim());
                        }

                        if (unidad.getTipoNivel5().contains("+")
                                && StringUtils.isNotBlank(unidad.getTipoNivel6())
                                && StringUtils.isNotBlank(unidad.getValorNivel6())) {
                            direccion.setCptiponivel6(unidad.getTipoNivel6());
                            direccion.setCpvalornivel6(unidad.getValorNivel6().trim());
                        }

                        String newApto = direccionRRManager.generarNumAptoBMRR(direccion);

                        if (StringUtils.isNotBlank(newApto) && newApto.contains("OTR")) {
                            newApto = newApto.substring(3);
                        }

                        unidad.setNewAparment(newApto);

                        if (StringUtils.isNotBlank(unidad.getNewAparment())) {
                            ModificacionDir modDir = new ModificacionDir();
                            modDir.setSolicitud(new BigDecimal(solicitudCreated.getIdSolicitud().longValue()));
                            modDir.setOldApto(unidad.getAptoUnidad());
                            modDir.setNewApto(unidad.getNewAparment());
                            modDir.setConflictApto("0");
                            modDir.setCpTipoNivel5(unidad.getTipoNivel5() != null
                                    ? unidad.getTipoNivel5() : null);
                            modDir.setCpTipoNivel6(unidad.getTipoNivel6() != null
                                    ? unidad.getTipoNivel6() : null);
                            modDir.setCpValorNivel5(unidad.getValorNivel5().trim() != null
                                    ? unidad.getValorNivel5().trim() : null);
                            modDir.setCpValorNivel6(unidad.getValorNivel6().trim() != null
                                    ? unidad.getValorNivel6().trim() : null);
                            modDir.setTecnologiaHabilitadaId(unidad.getTecnologiaHabilitadaId() != null
                                    ? unidad.getTecnologiaHabilitadaId() : null);
                            modificacionDirManager.create(modDir, usuario, perfil);
                            modificacionUnidades = true;
                        }
                    } else {
                        /*Si la unidad tiene conflicto es agregada al listado 
                        de cambios pero en la gestión se filtra del listado de
                        * cambios y se pasa a listado de conflictos*/
                        if (StringUtils.isNotBlank(unidad.getConflictApto())
                                && unidad.getConflictApto().equalsIgnoreCase("1")) {

                            ModificacionDir modDir = new ModificacionDir();
                            modDir.setSolicitud(new BigDecimal(solicitudCreated.getIdSolicitud().longValue()));
                            modDir.setOldApto(unidad.getAptoUnidad());
                            modDir.setNewApto(unidad.getAptoUnidad());
                            modDir.setConflictApto("1");
                            modDir.setTecnologiaHabilitadaId(unidad.getTecnologiaHabilitadaId() != null
                                    ? unidad.getTecnologiaHabilitadaId() : null);
                            modDir.setTecHabilitadaIdNuevaDireccion(unidad.getTecHabilitadaIdNuevaDireccion() != null
                                    ? unidad.getTecHabilitadaIdNuevaDireccion() : null);
                            modificacionDirManager.create(modDir, usuario, perfil);
                        }
                    }
                }
            return modificacionUnidades;
        } catch (ApplicationException e) {
            LOGGER.error("Error al intentar agregar los nuevos apartamentos a modificaciones. ", e);
            String msn = "Error al intentar agregar los nuevos apartamentos a modificaciones.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + e.getMessage(), ""));
            return false;
        }
    }

    /**
     * Función utilizada validar el tipo de solicitud y la dirección versus la
     * matriz de viabilidad
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public List<ResponseValidacionViabilidad> validarMatrizViabilidad(
            ResponseValidacionDireccion responseValidacionDireccion,
            String direccionStr) throws ApplicationException {
        List<ResponseValidacionViabilidad> respuestaValidacionViabilidadList = new ArrayList<>();
        try {
            CmtGrupoProyectoValidacionMglManager cmtGrupoProyectoValidacionMglManager
                    = new CmtGrupoProyectoValidacionMglManager();

            //cargue de de proyectos configurados 
            List<CmtGrupoProyectoValidacionMgl> grupoProyectoList
                    = cmtGrupoProyectoValidacionMglManager.findByProyectoGeneral();

            if (grupoProyectoList != null && !grupoProyectoList.isEmpty()) {

                for (CmtGrupoProyectoValidacionMgl cmtGrupoProyectoValidacionMgl : grupoProyectoList) {

                    CmtTipoValidacionMglManager cmtTipoValidacionMglManager
                            = new CmtTipoValidacionMglManager();

                    RequestValidacionViabilidad requestValidacionViabilidad
                            = new RequestValidacionViabilidad();
                    //Asignamos el tipo de direccion
                    requestValidacionViabilidad.setTipoDireccion(
                            responseValidacionDireccion.getDrDireccion().
                                    getIdTipoDireccion());

                    //Cargue de la dirección que se desea enviar a la matriz de viabilidad
                    if (responseValidacionDireccion.getCityEntity().
                            getDireccionRREntityNueva() != null) {
                        //nueva
                        requestValidacionViabilidad.setCalle(
                                responseValidacionDireccion.getCityEntity().
                                        getDireccionRREntityNueva().getCalle());

                        requestValidacionViabilidad.setApartamento(
                                responseValidacionDireccion.getCityEntity().
                                        getDireccionRREntityNueva().getNumeroApartamento());

                        requestValidacionViabilidad.setUnidad(
                                responseValidacionDireccion.getCityEntity().
                                        getDireccionRREntityNueva().getNumeroUnidad());
                    } else {
                        //antigua
                        requestValidacionViabilidad.setCalle(
                                responseValidacionDireccion.getCityEntity().
                                        getDireccionRREntityAntigua().getCalle());

                        requestValidacionViabilidad.setApartamento(
                                responseValidacionDireccion.getCityEntity().
                                        getDireccionRREntityAntigua().getNumeroApartamento());

                        requestValidacionViabilidad.setUnidad(
                                responseValidacionDireccion.getCityEntity().
                                        getDireccionRREntityAntigua().getNumeroUnidad());
                    }

                    requestValidacionViabilidad.setComunidad(
                            responseValidacionDireccion.getCityEntity().
                                    getCodCity());
                    requestValidacionViabilidad.setSegmento("Residencial");
                    requestValidacionViabilidad.setProyecto(
                            cmtGrupoProyectoValidacionMgl.
                                    getNombreProyectoBasica());

                    requestValidacionViabilidad.setDireccion(direccionStr);

                    if (responseValidacionDireccion.getCityEntity().getBarrio() != null) {
                        requestValidacionViabilidad.setBarrio(responseValidacionDireccion.getCityEntity().getBarrio());
                    }

                    //Método que consume la matriz de viabilidad y obtiene la respuesta.
                    ResponseValidacionViabilidad responseValidacionViabilidad
                            = cmtTipoValidacionMglManager.getViabilidad(requestValidacionViabilidad);

                    if (responseValidacionViabilidad != null) {

                        if (!responseValidacionViabilidad.isResultadoValidacion()) {

                            ResponseValidacionViabilidad respuestaValidacionViabilidad
                                    = new ResponseValidacionViabilidad();

                            respuestaValidacionViabilidad
                                    .setResultadoValidacion(responseValidacionViabilidad.isResultadoValidacion());
                            respuestaValidacionViabilidad
                                    .setNombreProyecto(cmtGrupoProyectoValidacionMgl.getNombreProyectoBasica());

                            if (responseValidacionViabilidad.getMensajes() != null
                                    && !responseValidacionViabilidad.getMensajes().isEmpty()) {
                                respuestaValidacionViabilidad.setMensajes(responseValidacionViabilidad.getMensajes());
                            } else {
                                respuestaValidacionViabilidad
                                        .setMensajeRespuesta("Solicitud rechazada, No hay viabilidad. ");
                            }
                            respuestaValidacionViabilidadList
                                    .add(respuestaValidacionViabilidad);
                        } //Es viable
                        else {
                            ResponseValidacionViabilidad respuestaValidacionViabilidad
                                    = new ResponseValidacionViabilidad();

                            respuestaValidacionViabilidad.setResultadoValidacion(responseValidacionViabilidad.isResultadoValidacion());
                            respuestaValidacionViabilidad.setNombreProyecto(cmtGrupoProyectoValidacionMgl.getNombreProyectoBasica());
                            respuestaValidacionViabilidad.setMensajeRespuesta("Validación de Matriz de viabilidad exitosa. "
                                    + "Puede continuar con la creación de la solicitud");
                            respuestaValidacionViabilidadList
                                    .add(respuestaValidacionViabilidad);
                            return respuestaValidacionViabilidadList;
                        }
                    }
                }

                return respuestaValidacionViabilidadList;
            }
            ResponseValidacionViabilidad respuestaValidacionViabilidad
                    = new ResponseValidacionViabilidad();
            respuestaValidacionViabilidad.setResultadoValidacion(false);
            respuestaValidacionViabilidad.setMensajeRespuesta("No fue posible realizar la validación en la matriz"
                    + " de viablidad por falta de proyectos configurados");
            respuestaValidacionViabilidadList.add(respuestaValidacionViabilidad);

            return respuestaValidacionViabilidadList;
        } catch (ApplicationException e) {
            LOGGER.error("Error en validarMatrizViabilidad. " + e.getMessage(), e);
        }
        ResponseValidacionViabilidad respuestaValidacionViabilidad = new ResponseValidacionViabilidad();
        respuestaValidacionViabilidad.setResultadoValidacion(false);
        respuestaValidacionViabilidad.setMensajeRespuesta("No fue posible realizar la validación en la matriz de viablidad, Error. ");
        respuestaValidacionViabilidadList.add(respuestaValidacionViabilidad);
        return respuestaValidacionViabilidadList;
    }

    /**
     * Consulta todas solicitudes creadas sobre la tecnología DTH
     *
     * @return {@link List<Solicitud>}
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    public List<Solicitud> findAllSolicitudDthList() throws ApplicationException {
        return solicitudDaoImpl.findAllSolicitudDthList();
    }

    /**
     * Consulta una solicitud sobre tecnología DTH a partir de su ID
     *
     * @param idSolicitud {@link BigDecimal}
     * @return {@link Solicitud}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public Solicitud findSolicitudDTHById(BigDecimal idSolicitud)
            throws ApplicationException {
        return solicitudDaoImpl.findSolicitudDTHById(idSolicitud);
    }

    /**
     * Consulta una solicitud de tecnología DTH a partir de su ID
     * y que se encuentra en estado verificado o pendiente.
     *
     * @param idSolicitud {@link BigDecimal}
     * @return {@link Solicitud}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public Solicitud findSolicitudDTHByIdPendiente(BigDecimal idSolicitud)
            throws ApplicationException {
        return solicitudDaoImpl.findSolicitudDTHByIdPendiente(idSolicitud);
    }

    /**
     * Busca las solicitudes pendientes, para procesar la paginación.
     *
     * @param paginaSelected {@code int}
     * @param maxResults {@code int}
     * @return {@link List<Solicitud>}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public List<Solicitud> findPendientesParaGestionPaginacion(int paginaSelected, int maxResults) throws ApplicationException {
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return solicitudDaoImpl.findPendientesParaGestionPaginacion(firstResult, maxResults);
    }

    /**
     * Función que realiza validación de color de alerta correspondiente según
     * el valor de ANS del tipo de solicitud
     *
     * @param tipoSolicitud {@link CmtTipoSolicitudMgl}
     * @param fechaIngreso  {@link Date}
     * @return {@link String}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Juan David Hernandez
     */
    public String obtenerColorAlerta(CmtTipoSolicitudMgl tipoSolicitud, Date fechaIngreso) throws ApplicationException {
        try {
            String colorResult = "blue";

            if (Objects.isNull(tipoSolicitud) || Objects.isNull(fechaIngreso)) {
                return "blue";
            }

            long diffDate = (new Date().getTime()) - (fechaIngreso.getTime());
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));

            if ((int) diffMinutes >= tipoSolicitud.getAns()) {
                return "red";
            }

            if ((int) diffMinutes < tipoSolicitud.getAns()  && (int) diffMinutes >= tipoSolicitud.getAnsAviso()) {
                return "yellow";
            }

            if ((int) diffMinutes < tipoSolicitud.getAnsAviso()) {
                return "green";
            }

            return colorResult;
        } catch (Exception e) {
            LOGGER.error("Error al momento de obtener el color de Alerta {}", e.getMessage());
            throw new ApplicationException(e);
        }
    }

    /**
     * Función que realiza la gestión de la solicitud, cambios de apto,
     * resolucion de conflictos, reactivacion de hhpp.
     *
     * @param solicitudDthSeleccionada
     * @param drDireccion
     * @param detalleDireccionEntity
     * @param direccionRREntity
     * @param nodo
     * @param cmtTiempoSolicitudMgl
     * @param perfilVt
     * @param usuarioVt
     * @param idUsuario
     * @param unidadList
     * @param unidadConflictoList
     * @param sincronizaRr
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public boolean gestionSolicitud(Solicitud solicitudDthSeleccionada,
                                    DrDireccion drDireccion, DetalleDireccionEntity detalleDireccionEntity,
                                    DireccionRREntity direccionRREntity, NodoMgl nodo, String usuarioVt, int perfilVt,
                                    String idUsuario, List<UnidadStructPcml> unidadList,
                                    CmtTiempoSolicitudMgl cmtTiempoSolicitudMgl,
                                    List<UnidadStructPcml> unidadConflictoList, boolean sincronizaRr, boolean desdeMer, boolean habilitarr,
                                    GeograficoPoliticoMgl centroPoblado, GeograficoPoliticoMgl ciudad, GeograficoPoliticoMgl departamento,String nap) throws ApplicationException {
        boolean solicitudGestionada = false;
        try {
            //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
            boolean habilitarRR = false;

            if (desdeMer) {
                habilitarRR = habilitarr;
            } else {
                ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                    habilitarRR = true;
                }
            }

            HhppMglManager hhppMglManager = new HhppMglManager();
            DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity);
            DireccionRREntity dirRREntity;
            String idCastro = drDireccion.getIdDirCatastro();

            //obtiene el estrato de la direccion
            String estrato = validarEstrato(drDireccion);
            //obtiene el tipo de direccion que se esta gestionando
            String tipoDir = drDireccion.getIdTipoDireccion() != null ? drDireccion.getIdTipoDireccion() : "";
            //Si tiene Antigua, se crea el String de formato IGAC
            String dirAntiguaFormatoIgac = obtenerDireccionAntiguaFormatoIgacSolicitud(solicitudDthSeleccionada);

            String tipoSolicitud = "";
            //Obtiene el tipo de Solicitud (carpeta RR)
            if (nodo != null) {
                tipoSolicitud = obtenerCarpetaTipoDireccion(nodo.getNodTipo());
            } else {
                tipoSolicitud = obtenerCarpetaTipoDireccion(solicitudDthSeleccionada.getTecnologiaId());
            }

            //Si es diferente a un cambio de dirección valida el Nodo ingresado
            if (!solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CAMB_HHPP_1)) {

                if (nodo != null && nodo.getNodCodigo() != null && !nodo.getNodCodigo().isEmpty()) {
                    if (!desdeMer) {
                        //Valida que el nodo ingresado se encuentre certificado
                        NodoMglManager nodoMglManager = new NodoMglManager();
                        NodoMgl cmtNodoValidado = nodoMglManager.findByCodigo(nodo.getNodCodigo().trim());
                        String niac = Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO;
                        if (cmtNodoValidado == null || (cmtNodoValidado != null && cmtNodoValidado.getEstado() != null
                                && cmtNodoValidado.getEstado().getIdentificadorInternoApp() != null
                                && cmtNodoValidado.getEstado().getIdentificadorInternoApp().equalsIgnoreCase(niac))) {
                            throw new ApplicationException("El nodo " + nodo.getNodCodigo() + " no se encuentra certificado. Intente con otro por favor");
                        }
                        tipoSolicitud = obtenerCarpetaTipoDireccion(cmtNodoValidado.getNodTipo());
                    } else {
                        tipoSolicitud = obtenerCarpetaTipoDireccion(nodo.getNodTipo());
                    }
                    //Si es solicitud de creación de hhpp valida si existe nodo vetado
                    if (solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)) {
                        //Validacion a<para saber si el nodo se encuentra Vetado con fecha vigente
                        validarVetoVigente(nodo.getNodCodigo().trim(), null, solicitudDthSeleccionada.getTipoSol(), Constant.VETO_TIPO_CREACION_HHPP);
                    }
                }
            }

            //Validacion para saber si la ciudad o el canal se encuentran Vetados con fecha vigente
            if (solicitudDthSeleccionada.getCentroPobladoId() != null
                    && solicitudDthSeleccionada.getTipoSol() != null
                    && solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)
                    && solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)) {
                validarVetoVigente(null, solicitudDthSeleccionada.getCentroPobladoId(),
                        solicitudDthSeleccionada.getTipoSol(), Constant.VETO_TIPO_CREACION_HHPP);
            }

            //Tipo Accion "1" indica cambio de direccion del HHPP   
            if (solicitudDthSeleccionada.getCambioDir() != null
                    && solicitudDthSeleccionada.getCambioDir()
                    .equalsIgnoreCase(Constant.RR_DIR_CAMB_HHPP_1)) {

                if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)
                        || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_REGIONAL_SIN_CUPOS_DE_VERIFICACION)
                        || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_VERIFICACION_AGENDADA)
                        || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_REACTIVACION_HHPP_REALIZADO)) {
                    throw new ApplicationException("Tipo de respuesta no válida para este tipo de acción de solicitud.");
                }

                if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_CAMBIO_DE_DIRECCION_REALIZADO)) {

                    //Gestion de solicitud de cambio de Apto en RR y MGL
                    gestionCambioAptoHhppDireccionDetallada(solicitudDthSeleccionada, habilitarRR,
                            detalleDireccionEntity, usuarioVt, perfilVt, direccionRREntity, tipoDir, dirAntiguaFormatoIgac);

                    //se guardará el IdHHPP cambiado en la solicitud para los reportes
                    if (solicitudDthSeleccionada.getHhppMgl() != null
                            && solicitudDthSeleccionada.getHhppMgl().getHhpId() != null) {
                        HhppMgl hhppCreado = new HhppMgl();
                        hhppCreado.setHhpId(solicitudDthSeleccionada.getHhppMgl().getHhpId());
                        solicitudDthSeleccionada.setIdHhppGestionado(hhppCreado);
                    }

                    //Cambios de apto a unidades que se hayan confirmado como cambios en la gestion de la solicitud.
                    cambiosAptoUnidadesAsociadas(unidadList, solicitudDthSeleccionada, usuarioVt, perfilVt,
                            detalleDireccionEntity, habilitarRR, direccionRREntity);

                    //se agregan las marcas seleccionadas por el usuario.
                    if (solicitudDthSeleccionada.getHhppMgl() != null && solicitudDthSeleccionada.getAgregarMarcasHhppList() != null
                            && !solicitudDthSeleccionada.getAgregarMarcasHhppList().isEmpty()) {

                        //listado de marcas agregado en la solicitud de creacion por el usuario.
                        CmtDefaultBasicResponse responseAgregarMarcasHhpp =
                                hhppMglManager.agregarMarcasHhpp(solicitudDthSeleccionada.getHhppMgl(),
                                        solicitudDthSeleccionada.getAgregarMarcasHhppList(), usuarioVt);
                    
                      /*Si ocurre un error diferente a que la marca ya se encuentra registrada en el HHPP muestra el error al usuario,
                      de lo contrario simplemente no la agrega*/
                        if (responseAgregarMarcasHhpp != null && responseAgregarMarcasHhpp.getMessageType() != null
                                && !responseAgregarMarcasHhpp.getMessageType().isEmpty()
                                && responseAgregarMarcasHhpp.getMessage() != null
                                && !responseAgregarMarcasHhpp.getMessage().isEmpty()
                                && !responseAgregarMarcasHhpp.getMessageType().equalsIgnoreCase("I")
                                && responseAgregarMarcasHhpp.getMessageType().equalsIgnoreCase("E")
                                && responseAgregarMarcasHhpp.getMessage().contains("(RPTD)")) {
                            throw new ApplicationException(responseAgregarMarcasHhpp.getMessage());
                        }
                    }
                }

            } else {
                if ((solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)
                        || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_REGIONAL_SIN_CUPOS_DE_VERIFICACION)
                        || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_VERIFICACION_AGENDADA)
                        || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_REACTIVACION_HHPP_REALIZADO))) {

                    if (solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_HHPP_CREADO)
                            || solicitudDthSeleccionada.getRptGestion().equalsIgnoreCase(Constant.RZ_REGIONAL_SIN_CUPOS_DE_VERIFICACION)
                            && solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.RR_DIR_CREA_HHPP_0)) {

                        if (unidadList != null && !unidadList.isEmpty()) {
                            //Cambios de apto a unidades que se hayan confirmado como cambios en la gestion de la soclitud.
                            cambiosAptoUnidadesAsociadas(unidadList, solicitudDthSeleccionada,
                                    usuarioVt, perfilVt, detalleDireccionEntity, habilitarRR, direccionRREntity);
                        }
                        
                        boolean flujoAutomatico = false;
                        if(solicitudDthSeleccionada.isCreacionAutomatica()){
                            solicitudDthSeleccionada.setEstado(Constant.CREADO_AUTOMATICAMENTE);
                            flujoAutomatico = true;
                        }
                        
                        dirRREntity = direccionRRManager.registrarHHPP_Inspira(
                                nodo,
                                usuarioVt, tipoSolicitud,
                                Constant.FUNCIONALIDAD_DIRECCIONES,
                                estrato, false,
                                solicitudDthSeleccionada.getIdSolicitud().toString(),
                                solicitudDthSeleccionada.getTipoSol(),
                                solicitudDthSeleccionada.getCiudad(),
                                solicitudDthSeleccionada.getRespuesta(),
                                solicitudDthSeleccionada.getSolicitante(),
                                solicitudDthSeleccionada.getRptGestion(),
                                idUsuario,
                                solicitudDthSeleccionada.getContacto(),
                                solicitudDthSeleccionada.getTelContacto(),
                                dirAntiguaFormatoIgac, centroPoblado.getGpoId(),
                                sincronizaRr, solicitudDthSeleccionada.getTipoHhpp(),
                                solicitudDthSeleccionada.getAgregarMarcasHhppList(),
                                habilitarRR,nap,flujoAutomatico);

                        //se guardará el IdHHPP creado en la solicitud para los reportes
                        if (dirRREntity != null && dirRREntity.getIdHhpp() != null && !dirRREntity.getIdHhpp().isEmpty()) {
                            HhppMgl hhppCreado = new HhppMgl();
                            hhppCreado.setHhpId(new BigDecimal(dirRREntity.getIdHhpp()));
                            solicitudDthSeleccionada.setIdHhppGestionado(hhppCreado);
                            
                            if (flujoAutomatico) {
                                //AgregarNotasMer                                
                                String fechaHoraSol = DateToolUtils.formatoFechaLocalDateTime(LocalDateTime.now(),"yyyy-MM-dd HH:mm");
                                String nota = "NUMERO RADICADO: " + solicitudDthSeleccionada.getIdSolicitud().toString()
                                + "; FECHA Y HORA: " + fechaHoraSol
                                + "; ESTADO: " + co.com.claro.mgl.utils.Constant.CREADO_AUTOMATICAMENTE;                              
                                HhppMgl hhpp = hhppMglManager.findById(new BigDecimal(dirRREntity.getIdHhpp()));
                                agregarNotasMer(hhpp, nota, usuarioVt, perfilVt);
                            }
                        }

                    } else if (solicitudDthSeleccionada.getCambioDir() != null && solicitudDthSeleccionada.getCambioDir()
                            .equalsIgnoreCase(Constant.RR_DIR_REAC_HHPP_3)
                            && solicitudDthSeleccionada.getRptGestion()
                            .equalsIgnoreCase(Constant.RZ_REACTIVACION_HHPP_REALIZADO)) {

                        //Si RR se encuentra habilitado y se encontro la comunidad y la regional en MGL
                        if (habilitarRR && solicitudDthSeleccionada.getHhppMgl().getNodId() != null
                                && solicitudDthSeleccionada.getHhppMgl().getNodId().getComId() != null
                                && solicitudDthSeleccionada.getHhppMgl().getNodId().getComId().getRegionalRr() != null) {

                            //Si el hhpp que se desea reactivar tiene ID RR
                            if (solicitudDthSeleccionada.getHhppMgl().getHhpIdrR() != null && !solicitudDthSeleccionada.getHhppMgl().getHhpIdrR().isEmpty()) {
                                HhppResponseRR hhppResponseRR = new HhppResponseRR();
                                //Consume servicio que busca hhpp por Id de RR
                                hhppResponseRR = direccionRRManager.getHhppByIdRR(solicitudDthSeleccionada.getHhppMgl().getHhpIdrR());

                                if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {

                                    //Reactivacion de hhpp en RR
                                    dirRREntity = direccionRRManager.reactivarHHPPRR(
                                            hhppResponseRR.getHouse(),
                                            hhppResponseRR.getStreet(),
                                            hhppResponseRR.getApartamento(),
                                            hhppResponseRR.getComunidad(),
                                            hhppResponseRR.getDivision(),
                                            nodo.getNodCodigo(),
                                            solicitudDthSeleccionada.getIdSolicitud().toString(),
                                            solicitudDthSeleccionada.getSolicitante(),
                                            solicitudDthSeleccionada.getTipoSol(),
                                            solicitudDthSeleccionada.getRespuesta());
                                }
                            }
                        }
                        //Reactivacion de hhpp en MGL
                        if (solicitudDthSeleccionada.getHhppMgl() != null && solicitudDthSeleccionada.getHhppMgl().getHhpId() != null) {
                            hhppMglManager.reactivarHhpp(solicitudDthSeleccionada.getHhppMgl().getHhpId(), usuarioVt, perfilVt);
                            //Se guarda el id del hhpp reactivado para los reportes
                            HhppMgl hhppCreado = new HhppMgl();
                            hhppCreado.setHhpId(solicitudDthSeleccionada.getHhppMgl().getHhpId());
                            solicitudDthSeleccionada.setIdHhppGestionado(hhppCreado);
                        } else {
                            throw new ApplicationException("No fue posible realizar la Reactivación del Hhpp. "
                                    + "Intente nuevamente realizar la solicitud por favor.");
                        }
                    }
                }
            }

            if (unidadConflictoList != null && !unidadConflictoList.isEmpty()) {
                //Se realizan los cambios de apto realizados por el usuario como resolución de conflictos
                cambiosAptoUnidadesAsociadasConflictos(unidadConflictoList, solicitudDthSeleccionada,
                        usuarioVt, perfilVt, drDireccion, tipoDir, dirAntiguaFormatoIgac, direccionRREntity, habilitarRR);
            }
            //Gestion de la respuesta de la solicitud
            if (nodo != null) {
                solicitudDthSeleccionada.setNodo(nodo.getNodCodigo());
            } else {
                solicitudDthSeleccionada.setNodo("");
            }
            solicitudDthSeleccionada.setFechaCancelacion(new Date());
            solicitudDthSeleccionada.setEstado(ConstantsSolicitudHhpp.SOL_FINALIZADO);
            //se valida si la gestion viene de una creación automática para cambiar estado
            if(solicitudDthSeleccionada.isCreacionAutomatica()){
                solicitudDthSeleccionada.setEstado(Constant.CREADO_AUTOMATICAMENTE);
            }
            SolicitudDaoImpl solicitudDao = new SolicitudDaoImpl();
            solicitudDthSeleccionada.setTiempoSolicitudMgl(cmtTiempoSolicitudMgl);
            solicitudDao.update(solicitudDthSeleccionada);

            //Se guardan los tiempos consumidos por la solicitud
            CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
            if (cmtTiempoSolicitudMgl != null
                    && (cmtTiempoSolicitudMgl.getArchivoSoporte().isEmpty()
                    || cmtTiempoSolicitudMgl.getArchivoSoporte() == null)) {
                cmtTiempoSolicitudMgl.setArchivoSoporte("NA");
            }

            cmtTiempoSolicitudMglManager.update(cmtTiempoSolicitudMgl, usuarioVt, perfilVt);
            solicitudGestionada = true;

            //ENVIO DE CORREO DE GESTION  
            UsuariosServicesDTO usuario = null;

            if (idUsuario != null && !idUsuario.isEmpty()) {
                UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
                // Buscar al usuario a traves de la cédula. 
                // Si no se encuentra, realiza la búsqueda por medio del parametro de VISOR.
                usuario = usuariosManager.consultaInfoUserPorCedulaVISOR(idUsuario);
            }

            GeograficoPoliticoMgl centroPobladoDireccion = new GeograficoPoliticoMgl();
            GeograficoPoliticoMgl ciudadDireccion = new GeograficoPoliticoMgl();
            GeograficoPoliticoMgl departamentoDireccion = new GeograficoPoliticoMgl();
            if (usuario != null) {
                if (desdeMer) {
                    centroPobladoDireccion = centroPoblado;
                    ciudadDireccion = ciudad;
                    departamentoDireccion = departamento;
                } else {
                    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                    centroPoblado = geograficoPoliticoManager.findById(solicitudDthSeleccionada.getCentroPobladoId());
                    ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
                    departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
                }


                //DATOS PARA ENVIAR AL CORREO
                String tipoSolicitudNombre = "";
                CmtBasicaMgl tipoSolicitudBasica = obtenerTipoAccionSolicitud(solicitudDthSeleccionada.getCambioDir());
                if (tipoSolicitudBasica != null
                        && tipoSolicitudBasica.getNombreBasica() != null
                        && !tipoSolicitudBasica.getNombreBasica().isEmpty()) {
                    tipoSolicitudNombre = tipoSolicitudBasica.getNombreBasica();
                }
                String subjet = null;
                if (!solicitudDthSeleccionada.getCambioDir().equalsIgnoreCase(Constant.VALIDACION_COBERTURA_12)) {
                    subjet = "Gestion solicitud de Hhpp";
                } else {
                    subjet = "Gestion validacion de Cobertura";
                }
                //JDHT Envio de correo a solicitante
                String emailDestinatario = "";
                if (solicitudDthSeleccionada.getCorreoCopia() != null
                        && !solicitudDthSeleccionada.getCorreoCopia().isEmpty()) {
                    emailDestinatario = solicitudDthSeleccionada.getCorreoCopia();
                }

                enviarCorreoGestionSolicitud(emailDestinatario, solicitudDthSeleccionada.getCorreo() != null ? solicitudDthSeleccionada.getCorreo() : "", subjet, mensajeCorreoGestionSolicitud(solicitudDthSeleccionada, tipoSolicitudNombre,
                        departamentoDireccion.getGpoNombre(), ciudadDireccion.getGpoNombre(), centroPobladoDireccion.getGpoNombre(),
                        estrato, solicitudDthSeleccionada.getRespuesta(),
                        solicitudDthSeleccionada.getRptGestion(),
                        usuarioVt, unidadList));
            }

        } catch (Exception e) {
            LOGGER.error("Error en gestionSolicitud. " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
        return solicitudGestionada;
    }
    
    public void agregarNotasMer(HhppMgl hhpp,String notas, String usuarioVt, int perfilVt) {        
        try {
            NotasAdicionalesMglManager namm = new NotasAdicionalesMglManager();
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager = new CmtNotasHhppVtMglManager();
            CmtNotasHhppDetalleVtMglManager cmtNotasDetalleMglMglManager = new CmtNotasHhppDetalleVtMglManager();
            
            if (hhpp != null && notas != null) {
                if (!notas.isEmpty()) {
                    NotasAdicionalesMgl notasMgl = new NotasAdicionalesMgl();
                    notasMgl.setHhppId(hhpp.getHhpId() + "");
                    notasMgl.setEstadoRegistro(1);
                    notasMgl.setFechaCreacion(new Date());
                    notasMgl.setNota(notas);
                    namm.create(notasMgl);

                    //Persistir nota en MGL
                    CmtBasicaMgl tipoNota = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_NOTA_HHHPP);
                    CmtNotasHhppVtMgl cmtNotasHhppVtMgl = new CmtNotasHhppVtMgl();
                    cmtNotasHhppVtMgl.setNota(notas);
                    cmtNotasHhppVtMgl.setDescripcion(notas);
                    cmtNotasHhppVtMgl.setTipoNotaObj(tipoNota);
                    cmtNotasHhppVtMgl.setHhppId(hhpp);

                    cmtNotasHhppVtMgl = cmtNotasHhppVtMglManager.create(cmtNotasHhppVtMgl, usuarioVt, perfilVt);

                    CmtNotasHhppDetalleVtMgl cmtNotasHhppDetalleVtMgl = new CmtNotasHhppDetalleVtMgl();
                    cmtNotasHhppDetalleVtMgl.setNotaHhpp(cmtNotasHhppVtMgl);
                    cmtNotasHhppDetalleVtMgl.setNota(notas);
                    cmtNotasDetalleMglMglManager.create(cmtNotasHhppDetalleVtMgl, usuarioVt, perfilVt);
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en AgregarNotas. EX. " + e.getMessage(), e);            
        } catch (Exception e) {
            LOGGER.error("Error en AgregarNotas. EX. " + e.getMessage(), e);            
        }
    }
    
    /**
     * Función que realiza cambios de apto realizando la actualizacion en la
     * direccionDetallada, direccion y subdireccion con su respectiva
     * georeferenciacion.De igual manera realiza los cambios de apto en RR
     *
     * @param dirAntiguaFormatoIgac
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void gestionCambioAptoHhppDireccionDetallada(Solicitud solicitudDthSeleccionada, boolean habilitarRR,
                                                        DetalleDireccionEntity detalleDireccionEntity, String usuarioVt, int perfilVt, DireccionRREntity direccionRREntity,
                                                        String tipoDir, String dirAntiguaFormatoIgac) throws ApplicationException {

        try {
            HhppMglManager hhppMglManager = new HhppMglManager();
            CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            DireccionRREntity dirRREntity;

            //Para realizar cambio de direccion la solicitud debe traer asociado el Hhpp que se le desea cambiar la dir
            if (solicitudDthSeleccionada.getHhppMgl().getHhpId() != null) {

                HhppMgl hhppAntiguo = hhppMglManager.findById(solicitudDthSeleccionada.getHhppMgl().getHhpId());
                //cardenaslb
                if (solicitudDthSeleccionada.getHhppMgl().getThhId() != null
                        && !solicitudDthSeleccionada.getHhppMgl().getThhId().isEmpty()) {
                    hhppAntiguo.setThhId(solicitudDthSeleccionada.getHhppMgl().getThhId());
                }
                //CAMBIO DE APTO EN RR
                //Cambio a subDirecciones del Hhpp y RR debe estar encendido 
                if (habilitarRR && hhppAntiguo != null && hhppAntiguo.getSubDireccionObj() != null
                        && hhppAntiguo.getSubDireccionObj().getSdiId() != null) {

                    //Se realiza busqueda de todas las subdireccion del Hhpp para hacer actualización de datos del hhpp en todas sus tecnologias
                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(hhppAntiguo.getSubDireccionObj().getSdiId());

                    if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                        for (HhppMgl hhppMglSubDireccion : hhhpSubDirList) {

                            //Si la subDireccion tiene id RR se procede a hacer el cambio de Dir
                            if (hhppMglSubDireccion.getHhpIdrR() != null
                                    && !hhppMglSubDireccion.getHhpIdrR().isEmpty()) {

                                HhppResponseRR hhppResponseRR = new HhppResponseRR();
                                //Consume servicio que busca hhpp por Id de RR
                                hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglSubDireccion.getHhpIdrR());

                                if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                    /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                    if (hhppResponseRR.getComunidad() != null
                                            && !hhppResponseRR.getComunidad().isEmpty()
                                            && hhppResponseRR.getDivision() != null
                                            && !hhppResponseRR.getDivision().isEmpty()
                                            && hhppMglSubDireccion.getNodId().getNodTipo() != null) {
                                        //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                        if (hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                && !hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                            for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                    : hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                        equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                        && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                    //cardenaslb
                                                    HhppMgl hhppCambioTipoVivienda = new HhppMgl();
                                                    hhppCambioTipoVivienda.setHhpCalle(hhppResponseRR.getStreet());
                                                    hhppCambioTipoVivienda.setHhpApart(hhppResponseRR.getApartamento());
                                                    hhppCambioTipoVivienda.setHhpPlaca(hhppResponseRR.getHouse());
                                                    hhppCambioTipoVivienda.setHhpDivision(hhppResponseRR.getDivision());
                                                    hhppCambioTipoVivienda.setHhpComunidad(hhppResponseRR.getComunidad());
                                                    hhppCambioTipoVivienda.setThhId(hhppAntiguo.getThhId());

                                                    direccionRRManager.cambioTipoUnidadViviendaHhppRR(hhppCambioTipoVivienda);

                                                    //mover de apartamento 
                                                    direccionRRManager.cambiarEdificioHHPPRR_Inspira(
                                                            solicitudDthSeleccionada.getIdSolicitud().toString(),
                                                            hhppResponseRR.getComunidad().trim(),
                                                            hhppResponseRR.getDivision().trim(),
                                                            hhppResponseRR.getStreet(),
                                                            hhppResponseRR.getHouse(),
                                                            hhppResponseRR.getApartamento(), //apartamento antiguo
                                                            direccionRREntity.getNumeroApartamento(), //apartamento nuevo
                                                            solicitudDthSeleccionada.getSolicitante(),
                                                            solicitudDthSeleccionada.getTipoSol(),
                                                            hhppMglSubDireccion.getNodId().getComId());
                                                }
                                            }
                                        }
                                    } else {
                                        throw new ApplicationException("RR se encuentra encendido y la subdirección tiene IdRR pero el nodo de la subdirección"
                                                + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnología "
                                                + hhppMglSubDireccion.getNodId().getNodTipo().getNombreBasica());
                                    }
                                } else {
                                    if (hhppResponseRR != null
                                            && hhppResponseRR.getTipoMensaje() != null
                                            && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                        throw new ApplicationException(hhppResponseRR.getMensaje());
                                    } else {
                                        throw new ApplicationException("Ocurrio un error intentando "
                                                + "consumir el servicio de consulta de hhpp en RR ");
                                    }
                                }
                            } else {
                                LOGGER.error("La subdireccion no cuenta con idRR para realizar operaciones en RR");
                            }
                        }
                    } else {
                        throw new ApplicationException("Ocurrio un error en la consulta de subdirecciones del hhpp, "
                                + "no es posible realizar el cambio de dirección en RR");
                    }
                } else {
                    //Obtenemos los Hhpp de la Direccion principal y RR debe estar encendido   
                    if (hhppAntiguo != null && hhppAntiguo.getDireccionObj() != null
                            && hhppAntiguo.getDireccionObj().getDirId() != null && habilitarRR) {

                        //Obtenemos los Hhpp de la Direccion principal  
                        List<HhppMgl> hhhpDirList = hhppMglManager.findHhppDireccion(hhppAntiguo.getDireccionObj().getDirId());

                        if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                            for (HhppMgl hhppMglDireccion : hhhpDirList) {

                                /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                if (hhppMglDireccion.getHhpIdrR() != null && !hhppMglDireccion.getHhpIdrR().isEmpty()) {

                                    HhppResponseRR hhppResponseRR = new HhppResponseRR();
                                    //Consume servicio que busca hhpp por Id de RR
                                    hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglDireccion.getHhpIdrR());

                                    if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {

                                        if (hhppResponseRR.getComunidad() != null
                                                && !hhppResponseRR.getComunidad().isEmpty()
                                                && hhppResponseRR.getDivision() != null
                                                && !hhppResponseRR.getDivision().isEmpty()
                                                && hhppMglDireccion.getNodId() != null) {

                                            //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                            if (hhppMglDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                    && !hhppMglDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {
                                                //recorrido sobre la basica extendida de la tecnologia del nodo del hhpp de la subdirección.
                                                for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                        : hhppMglDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                    //Validación para conocer si la tecnoligia sincroniza con RR
                                                    if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                            equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                            && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                        //cardenaslb
                                                        HhppMgl hhppCambioTipoVivienda = new HhppMgl();
                                                        hhppCambioTipoVivienda.setHhpCalle(hhppResponseRR.getStreet());
                                                        hhppCambioTipoVivienda.setHhpApart(hhppResponseRR.getApartamento());
                                                        hhppCambioTipoVivienda.setHhpPlaca(hhppResponseRR.getHouse());
                                                        hhppCambioTipoVivienda.setHhpDivision(hhppResponseRR.getDivision());
                                                        hhppCambioTipoVivienda.setHhpComunidad(hhppResponseRR.getComunidad());
                                                        hhppCambioTipoVivienda.setThhId(hhppAntiguo.getThhId());

                                                        direccionRRManager.cambioTipoUnidadViviendaHhppRR(hhppCambioTipoVivienda);

                                                        //antigua a nueva
                                                        dirRREntity = direccionRRManager
                                                                .cambiarDirHHPPRR_Inspira(
                                                                        hhppResponseRR.getComunidad(),
                                                                        hhppResponseRR.getDivision(),
                                                                        hhppResponseRR.getHouse(),
                                                                        hhppResponseRR.getStreet(),
                                                                        hhppResponseRR.getApartamento(),
                                                                        hhppResponseRR.getComunidad(),
                                                                        hhppResponseRR.getDivision(),
                                                                        hhppResponseRR.getHouse(),
                                                                        hhppResponseRR.getStreet(),
                                                                        direccionRREntity.getNumeroApartamento(), //nuevo apartamento
                                                                        solicitudDthSeleccionada.getIdSolicitud().toString(),
                                                                        solicitudDthSeleccionada.getSolicitante(),
                                                                        solicitudDthSeleccionada.getTipoSol(),
                                                                        dirAntiguaFormatoIgac, tipoDir, hhppMglDireccion.getNodId().getComId());

                                                    } else {
                                                        LOGGER.error("La tecnología del hhpp de la subdireccion no sincroniza con RR");
                                                    }
                                                }
                                            } else {
                                                LOGGER.error("El listado de basica extendida de la tecnologia no se encuentra cargado");
                                            }
                                        } else {
                                            throw new ApplicationException("RR se encuentra encendido y la subdirección tiene IdRR pero el nodo de la subdirección"
                                                    + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnología "
                                                    + hhppMglDireccion.getNodId().getNodTipo().getNombreBasica());
                                        }

                                    } else {
                                        if (hhppResponseRR != null
                                                && hhppResponseRR.getTipoMensaje() != null
                                                && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                            throw new ApplicationException(hhppResponseRR.getMensaje());
                                        } else {
                                            throw new ApplicationException("Ocurrio un error intentando "
                                                    + "consumir el servicio de consulta de hhpp en RR ");
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

                // CAMBIO DE APTO EN MGL
                if (hhppAntiguo != null) {
                    //Cargue de los niveles del nuevo apto
                    UnidadStructPcml unidadModificadaNuevoApto = new UnidadStructPcml();
                    unidadModificadaNuevoApto.setTipoNivel5(solicitudDthSeleccionada.getCpTipoNivel5() != null
                            ? solicitudDthSeleccionada.getCpTipoNivel5() : null);
                    unidadModificadaNuevoApto.setTipoNivel6(solicitudDthSeleccionada.getCpTipoNivel6() != null
                            ? solicitudDthSeleccionada.getCpTipoNivel6() : null);
                    unidadModificadaNuevoApto.setValorNivel5(solicitudDthSeleccionada.getCpValorNivel5() != null
                            ? solicitudDthSeleccionada.getCpValorNivel5() : null);
                    unidadModificadaNuevoApto.setValorNivel6(solicitudDthSeleccionada.getCpValorNivel6() != null
                            ? solicitudDthSeleccionada.getCpValorNivel6() : null);

                    //Cambio de Apto MGL
                    cmtDireccionDetalleMglManager.cambiarAptoDireccionDetalladaHhpp(hhppAntiguo, unidadModificadaNuevoApto,
                            solicitudDthSeleccionada.getCentroPobladoId(), usuarioVt, perfilVt);
                } else {
                    throw new ApplicationException("No fue posible realizar el cambio de dirección en MGL, "
                            + "por favor intente de nuevo crear la solicitud");
                }
            } else {
                throw new ApplicationException("No fue posible realizar el cambio de apto debido a que "
                        + "no se encuentra el idHhpp asociado. Por favor finalizar la solicitud "
                        + "y crear una nueva e intentarlo de nuevo.");
            }
        } catch (Exception e) {
            LOGGER.error("Error en gestionCambioAptoHhppDireccionDetallada. " + e.getMessage(), e);
            throw new ApplicationException("Error al intentar realizar los cambios de apto"
                    + " de la dirección debido a: " + e.getMessage());
        }
    }

    /*@ author Juan David Hernandez */
    public String obtenerCarpetaTipoDireccion(CmtBasicaMgl tecnologiaBasicaId) throws ApplicationException {
        try {
            if (Objects.isNull(tecnologiaBasicaId) || Objects.isNull(tecnologiaBasicaId.getBasicaId())) {
                throw new ApplicationException("No es posible obtener el tipo de carpeta de la solicitud "
                        + " ya que no se encuentra la tecnologia relacionada");
            }

            CmtBasicaMglManager basicaManager = new CmtBasicaMglManager();
            CmtBasicaMgl tecnologiaBasica = basicaManager.findById(tecnologiaBasicaId.getBasicaId());

            if (tecnologiaBasica == null) {
                throw new ApplicationException("No es posible obtener el tipo de carpeta de la solicitud "
                        + " ya que no se encuentra relacionada la tecnología a la solicitud");
            }

            if (tecnologiaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)
                    || tecnologiaBasica.getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)) {
                return "UNIDIRECCIONAL";
            }

            return "VERIFICACION_CASAS";

        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerCarpetaTipoDireccion. " + e.getMessage(), e);
            throw new ApplicationException("La Solicitud no cuenta con el Id de la tecnologia que se desea gestionar");
        }
    }

    /*author Juan David Hernandez */
    public String obtenerDireccionAntiguaFormatoIgacSolicitud(Solicitud solicitudSeleccionada) {
        String dirAntiguaFormatoIgac = "";
        try {
            if (solicitudSeleccionada != null && solicitudSeleccionada.getDireccionAntiguaIgac() != null
                    && !solicitudSeleccionada.getDireccionAntiguaIgac().isEmpty()) {
                String[] direccionAntigua = solicitudSeleccionada
                        .getDireccionAntiguaIgac().trim().split("&");
                String calleAntigua = direccionAntigua[0];
                String casaAntigua = direccionAntigua[1];

                dirAntiguaFormatoIgac = calleAntigua
                        + " " + casaAntigua;
            }
        } catch (Exception e) {
            LOGGER.error("Error en obtenerDireccionAntiguaFormatoIgacSolicitud. " + e.getMessage(), e);
            return "";
        }
        return dirAntiguaFormatoIgac;
    }

    /**
     * Función que realiza cambios de apto realizado en las unidades asociadas
     * al predio.Replica los cambio en RR.
     *
     * @param direccionRREntity
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void cambiosAptoUnidadesAsociadas(List<UnidadStructPcml> unidadList, Solicitud solicitudDthSeleccionada,
                                             String usuarioVt, int perfilVt, DetalleDireccionEntity detalleDireccionEntity, boolean habilitarRR,
                                             DireccionRREntity direccionRREntity) throws ApplicationException {
        try {
            //Verificamos si se deben realizar cambio de direccion
            if (CollectionUtils.isEmpty(unidadList)){
                LOGGER.info("No se requiere cambio de apartamentos en las unidades asociadas.");
                return;
            }

            HhppMglManager hhppMglManager = new HhppMglManager();
            DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity);

            for (UnidadStructPcml unidad : unidadList) {
                if (!unidad.isSelected() && Objects.isNull(unidad.getHhppMgl())) {
                    LOGGER.warn("La unidad no requiere ser procesada");
                    return;
                }

                /*@author Juan David Hernandez */
                HhppMgl hhppCambioApto = hhppMglManager.findById(unidad.getHhppMgl().getHhpId());

                //Si es una subDireccion y esta encendido RR
                if (hhppCambioApto != null && hhppCambioApto.getSubDireccionObj() != null
                        && hhppCambioApto.getSubDireccionObj().getSdiId() != null && habilitarRR) {

                     /*Se realiza busqueda de todas las subdireccion del Hhpp para hacer actualización
                       de datos del hhpp en todas sus tecnologias*/
                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(hhppCambioApto.getSubDireccionObj().getSdiId());

                    if (CollectionUtils.isEmpty(hhhpSubDirList)) {
                        throw new ApplicationException("Ocurrio un error consultando el "
                                + "listado de subDirecciones del hhpp y no es posible realizar "
                                + "el cambio de apto de las unidades asociadas al predio");
                    }

                    for (HhppMgl hhppMglSubDireccionCambioApto : hhhpSubDirList) {

                        if (hhppMglSubDireccionCambioApto.getHhpIdrR() != null
                                && !hhppMglSubDireccionCambioApto.getHhpIdrR().isEmpty()) {

                            //Consume servicio que busca hhpp por Id de RR
                            HhppResponseRR hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglSubDireccionCambioApto.getHhpIdrR());

                            if (Objects.isNull(hhppResponseRR) || !hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                if (hhppResponseRR == null
                                        || !StringUtils.isNotBlank(hhppResponseRR.getTipoMensaje())) {
                                    throw new ApplicationException("Ocurrio un error intentando "
                                            + "consumir el servicio de consulta de hhpp en RR ");
                                }

                                throw new ApplicationException(hhppResponseRR.getMensaje());
                            }

                            /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                            if (hhppResponseRR.getComunidad() != null
                                    && !hhppResponseRR.getComunidad().isEmpty()
                                    && hhppResponseRR.getDivision() != null
                                    && !hhppResponseRR.getDivision().isEmpty()
                                    && hhppMglSubDireccionCambioApto.getNodId().getNodTipo() != null) {

                                //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                if (hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                        && !hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                    for (CmtBasicaExtMgl cmtBasicaExtMgl
                                            : hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                        if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                            //mover de apartamento
                                            direccionRRManager.cambiarEdificioHHPPRR_Inspira(
                                                    solicitudDthSeleccionada.getIdSolicitud().toString(),
                                                    hhppResponseRR.getComunidad(),
                                                    hhppResponseRR.getDivision(),
                                                    hhppResponseRR.getStreet(),
                                                    hhppResponseRR.getHouse(),
                                                    hhppResponseRR.getApartamento(), //apartamento actual
                                                    unidad.getNewAparment(), //nuevo apartamento
                                                    solicitudDthSeleccionada.getSolicitante(),
                                                    solicitudDthSeleccionada.getTipoSol(),
                                                    hhppMglSubDireccionCambioApto.getNodId().getComId());
                                        }
                                    }
                                }
                            } else {
                                throw new ApplicationException("RR se encuentra encendido y la subdirección tiene IdRR pero el nodo de la subdirección"
                                        + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnologia "
                                        + hhppMglSubDireccionCambioApto.getNodId().getNodTipo().getNombreBasica()
                                        + " No es posible realizar el cambio de apto a las unidades asociadas al predio.");
                            }

                        }
                    }
                } else {
                            //Obtenemos los Hhpp de la Direccion principal
                            if (hhppCambioApto != null && hhppCambioApto.getDireccionObj() != null && hhppCambioApto.getDireccionObj().getDirId() != null && habilitarRR) {
                                List<HhppMgl> hhhpDirList = hhppMglManager.findHhppDireccion(hhppCambioApto.getDireccionObj().getDirId());

                                if (CollectionUtils.isEmpty(hhhpDirList)) {
                                    throw new ApplicationException("Ocurrio un error consultando el "
                                            + "listado de firecciones del hhpp y no es posible realizar "
                                            + "el cambio de apto de las unidades asociadas al predio");
                                }

                                for (HhppMgl hhppMglDireccionCambioApto : hhhpDirList) {

                                    if (hhppMglDireccionCambioApto.getHhpIdrR() != null
                                            && !hhppMglDireccionCambioApto.getHhpIdrR().isEmpty()) {

                                        //Consume servicio que busca hhpp por Id de RR
                                        HhppResponseRR hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglDireccionCambioApto.getHhpIdrR());

                                        if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {

                                            /* si tiene id RR el hhpp y cuenta con la información poblada en comunidad rr para el nodo*/
                                            if (hhppResponseRR.getComunidad() != null
                                                    && !hhppResponseRR.getComunidad().isEmpty()
                                                    && hhppResponseRR.getDivision() != null
                                                    && !hhppResponseRR.getDivision().isEmpty()
                                                    && hhppMglDireccionCambioApto.getNodId().getNodTipo() != null) {

                                                //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                                if (hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                        && !hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                                    for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                            : hhppMglDireccionCambioApto.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                        if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                                equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                                && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                            //mover de apartamento
                                                            direccionRRManager.cambiarEdificioHHPPRR_Inspira(
                                                                    solicitudDthSeleccionada.getIdSolicitud().toString(),
                                                                    hhppResponseRR.getComunidad(),
                                                                    hhppResponseRR.getDivision(),
                                                                    hhppResponseRR.getStreet(),
                                                                    hhppResponseRR.getHouse(),
                                                                    hhppResponseRR.getApartamento(),
                                                                    unidad.getNewAparment(),
                                                                    solicitudDthSeleccionada.getSolicitante(),
                                                                    solicitudDthSeleccionada.getTipoSol(),
                                                                    hhppMglDireccionCambioApto.getNodId().getComId());
                                                        }
                                                    }
                                                } else {
                                                    throw new ApplicationException("Ocurrio un error obteniendo el listado de"
                                                            + " tecnologia extendida para sincronizar con RR para la tecnología "
                                                            + hhppMglDireccionCambioApto.getNodId().getNodTipo().getNombreBasica()
                                                            + " y no es posible realizar "
                                                            + "el cambio de apto de unidades asociadas al predio. ");
                                                }
                                            } else {
                                                throw new ApplicationException("RR se encuentra encendido y la dirección tiene IdRR pero el nodo de la dirección"
                                                        + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnologia "
                                                        + hhppMglDireccionCambioApto.getNodId().getNodTipo().getNombreBasica());
                                            }
                                        } else {
                                            if (hhppResponseRR != null
                                                    && hhppResponseRR.getTipoMensaje() != null
                                                    && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                                throw new ApplicationException(hhppResponseRR.getMensaje());
                                            } else {
                                                throw new ApplicationException("Ocurrio un error intentando "
                                                        + "consumir el servicio de consulta de hhpp en RR ");
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //Cambio de Apto en MGL
                        if (hhppCambioApto != null) {
                            cmtDireccionDetalleMglManager.cambiarAptoDireccionDetalladaHhpp(hhppCambioApto, unidad,
                                    solicitudDthSeleccionada.getCentroPobladoId(), usuarioVt, perfilVt);
                        } else {
                            throw new ApplicationException("Ocurrió un error consultando el hhpp antiguo para realizar"
                                    + " los cambios de apto de las unidades asociadas al predio.");
                        }
                }
        } catch (Exception e) {
            LOGGER.error("Error en cambiosAptoUnidadAsociadas. " + e.getMessage(), e);
            throw new ApplicationException("Ocurrió un error intentando realizar el cambio de Apto a las unidades asociadas al predio debido a: " + e.getMessage());
        }
    }

    /* Juan David Hernandez */
    public void cambiosAptoUnidadesAsociadasConflictos(List<UnidadStructPcml> unidadConflictoList, Solicitud solicitudDthSeleccionada,
                                                       String usuarioVt, int perfilVt, DrDireccion drDireccion, String tipoDir, String dirAntiguaFormatoIgac,
                                                       DireccionRREntity direccionRREntity, boolean habilitarRr) throws ApplicationException {
        try {
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            //Verificamos si hay unidades con conflicto por resolver con cambios de dirección. (cambios de apto)
            if (unidadConflictoList != null && !unidadConflictoList.isEmpty()) {
                for (UnidadStructPcml u : unidadConflictoList) {

                    if (u.getNewAparment() != null && !u.getNewAparment().isEmpty() && u.getHhppMgl() != null) {

                        //validación para realizar cambio en RR
                        if (habilitarRr && u.getHhppMgl() != null && u.getHhppMgl().getHhpIdrR() != null) {

                            HhppResponseRR hhppResponseRR = new HhppResponseRR();
                            //Consume servicio que busca hhpp por Id de RR
                            hhppResponseRR = direccionRRManager.getHhppByIdRR(u.getHhppMgl().getHhpIdrR());

                            if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {

                                /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                if (hhppResponseRR.getComunidad() != null
                                        && !hhppResponseRR.getComunidad().isEmpty()
                                        && hhppResponseRR.getDivision() != null
                                        && !hhppResponseRR.getDivision().isEmpty()
                                        && u.getHhppMgl().getNodId().getNodTipo() != null) {

                                    //mover de apartamento
                                    direccionRRManager.cambiarEdificioHHPPRR_Inspira(
                                            solicitudDthSeleccionada.getIdSolicitud().toString(),
                                            u.getHhppMgl().getNodId().getComId().getCodigoRr(),
                                            u.getHhppMgl().getNodId().getComId().getRegionalRr().getCodigoRr(),
                                            u.getCalleUnidad(),
                                            u.getCasaUnidad(),
                                            u.getAptoUnidad(), u.getNewAparment(),
                                            solicitudDthSeleccionada.getSolicitante(),
                                            solicitudDthSeleccionada.getTipoSol(),
                                            u.getHhppMgl().getNodId().getComId());
                                } else {
                                    throw new ApplicationException("RR se encuentra encendido y la dirección tiene IdRR pero el nodo de la dirección"
                                            + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnologia "
                                            + u.getHhppMgl().getNodId().getNodTipo().getNombreBasica());
                                }
                            } else {
                                if (hhppResponseRR != null
                                        && hhppResponseRR.getTipoMensaje() != null
                                        && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                    throw new ApplicationException(hhppResponseRR.getMensaje());
                                } else {
                                    throw new ApplicationException("Ocurrio un error intentando "
                                            + "consumir el servicio de consulta de hhpp en RR ");
                                }
                            }
                        }

                        //Cambio de apto en MGL
                        if (u.getHhppMgl() != null) {
                            cmtDireccionDetalleMglManager.cambiarAptoDireccionDetalladaHhpp(u.getHhppMgl(), u,
                                    solicitudDthSeleccionada.getCentroPobladoId(), usuarioVt, perfilVt);
                        }

                    }
                }

                CmtDireccionDetalladaMgl cmtDireccionDetallada = cmtDireccionDetalleMglManager
                        .parseDrDireccionToCmtDireccionDetalladaMgl(drDireccion);

                //Cambios de direccion de antigua a nueva a los conflictos que quedaron con dir antigua
                for (UnidadStructPcml u : unidadConflictoList) {
                    if (u.getHhppMgl() != null && u.getMallaDireccion().equalsIgnoreCase("antigua")) {

                        //validación para realizar cambio en RR
                        if (habilitarRr && u.getHhppMgl() != null && u.getHhppMgl().getHhpIdrR() != null) {

                            HhppResponseRR hhppResponseRR = new HhppResponseRR();
                            //Consume servicio que busca hhpp por Id de RR
                            hhppResponseRR = direccionRRManager.getHhppByIdRR(u.getHhppMgl().getHhpIdrR());

                            if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {

                                /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                if (hhppResponseRR.getComunidad() != null
                                        && !hhppResponseRR.getComunidad().isEmpty()
                                        && hhppResponseRR.getDivision() != null
                                        && !hhppResponseRR.getDivision().isEmpty()
                                        && u.getHhppMgl().getNodId().getNodTipo() != null) {

                                    if (u.getNewAparment() != null && !u.getNewAparment().isEmpty()) {
                                        //antigua a nueva con la nueva unidad RR cuando tiene apartamento nuevo
                                        direccionRRManager
                                                .cambiarDirHHPPRR_Inspira(
                                                        hhppResponseRR.getComunidad(),
                                                        hhppResponseRR.getDivision(),
                                                        hhppResponseRR.getHouse(),
                                                        hhppResponseRR.getStreet(),
                                                        u.getNewAparment(),
                                                        hhppResponseRR.getComunidad(),
                                                        hhppResponseRR.getDivision(),
                                                        direccionRREntity.getNumeroUnidad(),
                                                        direccionRREntity.getCalle(),
                                                        u.getNewAparment(),
                                                        solicitudDthSeleccionada.getIdSolicitud().toString(),
                                                        solicitudDthSeleccionada.getSolicitante(),
                                                        solicitudDthSeleccionada.getTipoSol(),
                                                        dirAntiguaFormatoIgac, tipoDir, u.getHhppMgl().getNodId().getComId());
                                    } else {
                                        //antigua a nueva con la nueva unidad RR cuando NO tiene apto nuevo.
                                        direccionRRManager
                                                .cambiarDirHHPPRR_Inspira(
                                                        hhppResponseRR.getComunidad(),
                                                        hhppResponseRR.getDivision(),
                                                        hhppResponseRR.getHouse(),
                                                        hhppResponseRR.getStreet(),
                                                        hhppResponseRR.getApartamento(),
                                                        hhppResponseRR.getComunidad(),
                                                        hhppResponseRR.getDivision(),
                                                        direccionRREntity.getNumeroUnidad(),
                                                        direccionRREntity.getCalle(),
                                                        u.getAptoUnidad(),
                                                        solicitudDthSeleccionada.getIdSolicitud().toString(),
                                                        solicitudDthSeleccionada.getSolicitante(),
                                                        solicitudDthSeleccionada.getTipoSol(),
                                                        dirAntiguaFormatoIgac, tipoDir, u.getHhppMgl().getNodId().getComId());
                                    }
                                } else {
                                    throw new ApplicationException("RR se encuentra encendido y la dirección tiene IdRR pero el nodo de la dirección"
                                            + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnologia "
                                            + u.getHhppMgl().getNodId().getNodTipo().getNombreBasica());
                                }

                            } else {
                                if (hhppResponseRR != null
                                        && hhppResponseRR.getTipoMensaje() != null
                                        && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                    throw new ApplicationException(hhppResponseRR.getMensaje());
                                } else {
                                    throw new ApplicationException("Ocurrio un error intentando "
                                            + "consumir el servicio de consulta de hhpp en RR ");
                                }
                            }
                        }

                        //Cambios de direccion de antigua a nueva a los conflictos que quedaron con dir antigua en MGL
                        cmtDireccionDetalleMglManager.cambiarDireccionDetalladaHhppAntiguaANueva(u.getHhppMgl(),
                                cmtDireccionDetallada, solicitudDthSeleccionada.getCentroPobladoId(), usuarioVt, perfilVt);

                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en cambiosAptoUnidadesAsociadasConflictos. " + e.getMessage(), e);
            throw new ApplicationException("Error al intentar realizar el cambio"
                    + " de direccion de antigua a nueva en el listado de conflictos: "
                    + e.getMessage());
        }
    }


    /* Juan David Hernandez */
    public void validarVetoVigente(String nodoCodigo, BigDecimal centroPobladoId, String canal, String tipoVeto)
            throws ApplicationException {
        VetoMglManager vetoMglManager = new VetoMglManager();

        boolean canalVetado = false;
        if (canal != null) {
            //Validacion para saber si el canal se encuentra Vetado con fecha vigente
            canalVetado = vetoMglManager.validarVetoVigente(null, null, canal, tipoVeto);

        }

        //si el canal no se encuentra vetado(seleccionado) no se tiene en cuenta los demas vetos  
        if (canalVetado) {
            if (nodoCodigo != null) {
                boolean nodoVetado = false;
                //Validacion para saber si el nodo se encuentra Vetado con fecha vigente
                nodoVetado = vetoMglManager.validarVetoVigente(nodoCodigo.trim(), null, null, Constant.VETO_TIPO_CREACION_HHPP);
                if (nodoVetado) {
                    throw new ApplicationException("El nodo " + nodoCodigo + " se encuentra vetado para Creacion de Hhpp con fecha vigente");
                }
            }

            if (centroPobladoId != null) {
                boolean ciudadVetada = false;
                //Validacion para saber si la ciudad se encuentra Vetado con fecha vigente
                ciudadVetada = vetoMglManager.validarVetoVigente(null, centroPobladoId, null, tipoVeto);
                if (ciudadVetada) {
                    throw new ApplicationException("El centro poblado se encuentra Vetado para Creacion de Hhpp con fecha vigente.");
                }
            }
        }

    }

    /**
     * Función que valida la disponibilidad de la solicitud para ser gestionada
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public boolean validarDisponibilidadSolicitud(BigDecimal idSolicitud,
                                                  String tipoTecnologia) throws ApplicationException {
        try {
            Solicitud solicitud = solicitudDaoImpl
                    .findSolicitudDTHById(idSolicitud);
            if (solicitud != null) {
                if (solicitud.getDisponibilidadGestion() == null || solicitud.getDisponibilidadGestion().equals("0")) {
                    return true;
                }
            }
            return false;
        } catch (ApplicationException e) {
            LOGGER.error("Error en validarDisponibilidadSolicitud. " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public String validarEstrato(DrDireccion drDireccion) {
        try {
            if (drDireccion != null) {
                if (drDireccion.getEstrato() != null && drDireccion.getEstrato().equals("-1")) {
                    return "NG";
                } else {
                    if (drDireccion.getEstrato() != null && drDireccion.getEstrato().equals("0")) {
                        return "NR";
                    } else {
                        return drDireccion.getEstrato();
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Error en validarEstrato. " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Función que válida si la dirección es multi-origen
     *
     * @param codComunidad
     * @return
     * @author Juan David Hernandez
     */
    public String validarMultiOrigen(String codComunidad) {
        try {
            GeograficoPoliticoManager geograficoPoliticoMglManager
                    = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl politicoMgl
                    = geograficoPoliticoMglManager
                    .findCityByCodComunidad(codComunidad);
            return politicoMgl.getGpoMultiorigen();
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar si la dirección es multi-origen ", e);
            String msn = "Error al validar si la dirección es multi-origen";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + e.getMessage(), ""));
            return null;
        }
    }

    /**
     * Función que se utiliza para realizar el cronómetro en pantalla
     *
     * @param dateInicio
     * @param dateFin
     * @return result
     * @author Juan David Hernandez
     */
    public String getTiempo(Date dateInicio, Date dateFin) {
        String result = ConstantsSolicitudHhpp.DEFAULT_TIME;
        if (dateInicio != null) {
            Date fechaG = new Date();
            if (dateFin != null) {
                fechaG = dateFin;
            }
            long diffDate = fechaG.getTime() - dateInicio.getTime();
            //Diferencia de las Fechas en Segundos
            long diffSeconds = Math.abs(diffDate / 1000);
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));
            long seconds = diffSeconds % 60;
            long minutes = diffMinutes % 60;
            long hours = Math.abs(diffDate / (60 * 60 * 1000));

            String hoursStr = (hours < 10 ? "0" + String.valueOf(hours)
                    : String.valueOf(hours));
            String minutesStr = (minutes < 10 ? "0" + String.valueOf(minutes)
                    : String.valueOf(minutes));
            String secondsStr = (seconds < 10 ? "0" + String.valueOf(seconds)
                    : String.valueOf(seconds));
            result = hoursStr + ":" + minutesStr + ":" + secondsStr;
        }
        return result;
    }

    public List<Solicitud> findAllSolicitudByRolList(List<CmtTipoSolicitudMgl> rolesUsuarioList) throws ApplicationException {

        List<Solicitud> solicitudesDthList;
        List<String> tipoTecnologiaList = new ArrayList();
        if (rolesUsuarioList != null && !rolesUsuarioList.isEmpty()) {
            for (CmtTipoSolicitudMgl rol : rolesUsuarioList) {
                tipoTecnologiaList.add(rol.getAbreviatura());
            }
            solicitudesDthList
                    = solicitudDaoImpl
                    .findAllSolicitudByRolList(tipoTecnologiaList);
        } else {
            return null;
        }
        return solicitudesDthList;
    }

    public List<Solicitud> findPendientesParaGestionPaginacionByRol(int paginaSelected,
                                                                    int maxResults, List<CmtTipoSolicitudMgl> rolesUsuarioList,
                                                                    String tipoSolicitudFiltro, boolean ordenMayorMenor, BigDecimal divisional) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        List<Solicitud> solicitudesDthList;
        List<String> tipoSolicitudHhppList = new ArrayList<>();
        if (rolesUsuarioList != null && !rolesUsuarioList.isEmpty()) {
            for (CmtTipoSolicitudMgl rol : rolesUsuarioList) {
                if (rol.getTipoSolicitudBasicaId() != null &&
                        rol.getTipoSolicitudBasicaId().getCodigoBasica() != null) {
                    tipoSolicitudHhppList.add(rol.getTipoSolicitudBasicaId().getCodigoBasica());
                }
            }
            solicitudesDthList
                    = solicitudDaoImpl
                    .findPendientesParaGestionPaginacionByRol(firstResult, maxResults, tipoSolicitudHhppList, tipoSolicitudFiltro,
                            ordenMayorMenor, divisional);
        } else {
            return null;
        }
        return solicitudesDthList;
    }

    public List<Solicitud> findSolicitudByIdRol(BigDecimal idSolicitud,
                                                List<CmtTipoSolicitudMgl> tipoSolicitudByRol) throws ApplicationException {
        List<String> tipoSolicitudByRolList = new ArrayList<>();
        //extrae las solicitudes que el rol le permite visualizar
        for (CmtTipoSolicitudMgl tipoSolicitudRol : tipoSolicitudByRol) {
            if (!Objects.isNull(tipoSolicitudRol.getTipoSolicitudBasicaId()) && !Objects.isNull(tipoSolicitudRol.getTipoSolicitudBasicaId().getCodigoBasica())) {
                //listado de tipo de solicitudes que el rol le permite visualizar
                tipoSolicitudByRolList.add(tipoSolicitudRol.getTipoSolicitudBasicaId().getCodigoBasica());
            }
        }
        return solicitudDaoImpl.findSolicitudByIdRol(idSolicitud, tipoSolicitudByRolList);
    }

    /**
     * Función que realiza conteo de las solicitudes para realizar paginación
     *
     * @param tipoSolicitudByRolList
     * @param tipoSolicitudFiltro
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public int countAllSolicitudByRolList(List<CmtTipoSolicitudMgl> tipoSolicitudByRolList,
                                          String tipoSolicitudFiltro) throws ApplicationException {
        int result;
        List<String> tipoSolicitudHhppList = new ArrayList();
        if (tipoSolicitudByRolList != null && !tipoSolicitudByRolList.isEmpty()) {
            for (CmtTipoSolicitudMgl tipoSolicitudRol : tipoSolicitudByRolList) {
                if (tipoSolicitudRol.getTipoSolicitudBasicaId() != null &&
                        tipoSolicitudRol.getTipoSolicitudBasicaId().getCodigoBasica() != null) {
                    tipoSolicitudHhppList.add(tipoSolicitudRol.getTipoSolicitudBasicaId().getCodigoBasica());
                }
            }
            result = solicitudDaoImpl.countAllSolicitudByRolList(tipoSolicitudHhppList, tipoSolicitudFiltro);
        } else {
            return 0;
        }
        return result;
    }

    /**
     * Buscar una solicitud por id de solicitud
     *
     * @param solicitud objeto solicitud con el idSolicitud o idTcrm seteado
     *                  para la busqueda
     * @return Retorna el objeto solicitud encontrado o un objeto vacio
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     */
    public EstadoSolicitud findBySolicitud(Solicitud solicitud) {
        EstadoSolicitud estado = new EstadoSolicitud();
        Solicitud solicitudBuscada;
        try {

            if (solicitud.getTipoSol() != null && !solicitud.getTipoSol().isEmpty()) {
                if (solicitud.getTipoSol().equalsIgnoreCase("HHPP")) {

                    if (solicitud.getIdSolicitud() != null && solicitud.getIdSolicitud() != BigDecimal.ZERO) {
                        solicitudBuscada = solicitudDaoImpl.findById(solicitud.getIdSolicitud());
                    } else {
                        estado.setMessageType("ERROR");
                        estado.setMessage("Los datos enviados no son válidos para la búsqueda");
                        return estado;
                    }

                    if (solicitudBuscada != null) {
                        estado.setEstado(solicitudBuscada.getEstado());
                        if (estado.getEstado().equals(ConstantsSolicitudHhpp.SOL_FINALIZADO) || estado.getEstado().equals("FINALIZADA")) {
                            estado.setResultado(solicitudBuscada.getRptGestion() != null ? solicitudBuscada.getRptGestion() : "");
                        } else {
                            estado.setResultado("");
                        }
                    } else {
                        estado.setMessageType("ERROR");
                        estado.setMessage("Solicitud de HHPP de barrio abierto digitada no fue encontrada en la base de datos.");
                    }

                } else {
                    if (solicitud.getTipoSol().equalsIgnoreCase("CCMM")) {
                        CmtSolicitudCmMglDaoImpl cmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
                        CmtSolicitudCmMgl cmtSolicitudCmMgl = cmtSolicitudCmMglDaoImpl.find(solicitud.getIdSolicitud());

                        if (cmtSolicitudCmMgl != null) {
                            estado.setEstado(cmtSolicitudCmMgl.getEstadoSolicitudObj() != null ? cmtSolicitudCmMgl.getEstadoSolicitudObj().getNombreBasica() : "NO DISPONIBLE");

                            if (cmtSolicitudCmMgl.getResultGestion() != null) {
                                estado.setResultado(cmtSolicitudCmMgl.getResultGestion().getNombreBasica());
                            } else {
                                estado.setResultado("");
                            }
                        } else {
                            estado.setMessageType("ERROR");
                            estado.setMessage("Solicitud de HHPP sobre CCMM digitada no fue encontrada.");
                        }
                    }
                }
            }

        } catch (ApplicationException e) {
            estado.setMessageType("ERROR");
            estado.setMessage(e.getMessage());
        }
        return estado;
    }

    /**
     * Buscar una solicitud por id del caso tcrm
     *
     * @param solicitud objeto solicitud con el idSolicitud o idTcrm seteado
     *                  para la busqueda
     * @return Retorna el objeto solicitud encontrado o un objeto vacio
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     */
    public EstadoSolicitud findByIdCasoTcrm(Solicitud solicitud) {
        EstadoSolicitud estado = new EstadoSolicitud();
        try {
            Solicitud solicitudBuscada;
            if (solicitud != null) {
                if (solicitud.getIdCasoTcrm() != null && !solicitud.getIdCasoTcrm().equals("")) {
                    solicitudBuscada = solicitudDaoImpl.findByIdCasoTcrm(solicitud.getIdCasoTcrm());
                } else {
                    estado.setMessageType("ERROR");
                    estado.setMessage("Los datos enviados no san validos para la busqueda");
                    return estado;
                }
                if (solicitudBuscada != null) {
                    estado.setEstado(solicitudBuscada.getEstado());
                    if (estado.getEstado().equals(ConstantsSolicitudHhpp.SOL_FINALIZADO) || estado.getEstado().equals("FINALIZADA")) {
                        estado.setResultado(solicitudBuscada.getRptGestion());
                    } else {
                        estado.setResultado("");
                    }
                } else {
                    estado.setMessageType("ERROR");
                    estado.setMessage("Solicitud no encontrada");
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en findByIdCasoTcrm. " + e.getMessage(), e);
            estado.setMessageType("ERROR");
            estado.setMessage(e.getMessage());
        }
        return estado;
    }

    /**
     * Crea Solicitud.Permite crear una solicitud sobre el repositorio para su
     * posterior gestion
     *
     * @param request                 con la que se debe crear la solicitud
     * @param tipoTecnologia
     * @param tiempoDuracionSolicitud
     * @param codigoDane
     * @param user
     * @param nodoGestion
     * @param nodoCercano
     * @param respuestaGestion
     * @param idCasoTcrm
     * @param respuesta
     * @return CmtResponseCreaSolicitudDto respuesta con el proceso de creacion
     * de la solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Victor Bocanegra
     */
    public CmtResponseCreaSolicitudDto crearGestionarSolicitudinspira(
            CmtRequestCrearSolicitudInspira request, String tipoTecnologia,
            String tiempoDuracionSolicitud, String codigoDane,
            String user, String nodoGestion,
            String nodoCercano, String respuestaGestion,
            String respuesta, String idCasoTcrm) throws ApplicationException, ExceptionDB, CloneNotSupportedException {

        CmtResponseCreaSolicitudDto cmtResponseCreaSolicitudDto = new CmtResponseCreaSolicitudDto();

        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtBasicaMgl tecnologiaSolicitada = new CmtBasicaMgl();
        CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
        HhppMglManager hhppMglManager = new HhppMglManager();


        if (request == null) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Es necesario crear una peticion para construir el servicio");
            return cmtResponseCreaSolicitudDto;
        }

        if (codigoDane == null || codigoDane.isEmpty()) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Es necesario ingresar el codigo dane");
            return cmtResponseCreaSolicitudDto;
        }

        if (request.getDrDireccion() == null) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Es necesario construir un DrDireccion");
            return cmtResponseCreaSolicitudDto;
        }

        //VALIDACIÓN DE DETALLE DE HHPP EN LA DIRECCIÓN YA SEA PARA CCMM O HHPP
        if (request.getDrDireccion().getCpTipoNivel5() != null
                && !request.getDrDireccion().getCpTipoNivel5().isEmpty()) {
        } else {
            cmtResponseCreaSolicitudDto = new CmtResponseCreaSolicitudDto();
            cmtResponseCreaSolicitudDto.setIdSolicitud("0");
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("La dirección no cuenta con el cpTipoNivel5 y cpValorNivel5 los cuales indican el detalle del HHPP que se desea crear Ej: APARTAMENTO 401, CASA 45, etc...");
            return cmtResponseCreaSolicitudDto;
        }

        if (request.getDrDireccion().getIdTipoDireccion() == null
                || request.getDrDireccion().getIdTipoDireccion().isEmpty()) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Debe ingresar el tipo de direccion CK, BM, o IT en IdTipoDireccion "
                    + "del DrDireccion");
            return cmtResponseCreaSolicitudDto;
        } else {
            if (!request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")
                    && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")
                    && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")) {
                cmtResponseCreaSolicitudDto.setMessageType("E");
                cmtResponseCreaSolicitudDto.setMessage("Debe ingresar el tipo de direccion CK, BM, o IT en IdTipoDireccion "
                        + "del DrDireccion");
                return cmtResponseCreaSolicitudDto;
            }
        }

        if (request.getCmtCityEntityDto() == null) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Debe construir el CmCityEntityDto");
            return cmtResponseCreaSolicitudDto;
        }

        if (tipoTecnologia == null || tipoTecnologia.isEmpty()) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Debe ingresar el tipoTecnologia válido por favor.");
            return cmtResponseCreaSolicitudDto;
        } else {
            CmtTipoBasicaMgl cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
            basicaMgl.setAbreviatura(tipoTecnologia);
            basicaMgl.setTipoBasicaObj(cmtTipoBasicaMgl);
            tecnologiaSolicitada = null;
            tecnologiaSolicitada = cmtBasicaMglManager.findByAbreviaAndTipoBasica(basicaMgl);

            if (!(tecnologiaSolicitada != null && tecnologiaSolicitada.getBasicaId() != null)) {
                cmtResponseCreaSolicitudDto.setMessageType("E");
                cmtResponseCreaSolicitudDto.setMessage("El código de la tecnología ingresado no se encuentra parametrizado en el sistema");
                return cmtResponseCreaSolicitudDto;
            }
        }

        String idUsuario = null;
        int perfilVt = 1;
        String userLo = "";

        UsuariosServicesDTO usuario = null;
        if (user != null && !user.isEmpty()) {
            UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
            usuario = usuariosManager.consultaInfoUserPorUsuario(user);

            if (usuario != null) {
                idUsuario = usuario.getCedula() != null ? usuario.getCedula() : null;
                perfilVt = usuario.getIdPerfil() != null ? usuario.getIdPerfil().intValue() : 1;
                userLo = usuario.getUsuario();

            } else {
                cmtResponseCreaSolicitudDto.setMessageType("E");
                cmtResponseCreaSolicitudDto.setMessage("El usuario ingresado no se encuentra en la base de datos");
                return cmtResponseCreaSolicitudDto;
            }
        }

        request.setIdUsuario(idUsuario);

        if (request.getObservaciones() == null || request.getObservaciones().isEmpty()) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Debe ingresar Observaciones");
            return cmtResponseCreaSolicitudDto;
        }

        if (request.getContacto() == null || request.getContacto().isEmpty()) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Debe ingresar Nombre de Contacto");
            return cmtResponseCreaSolicitudDto;
        }

        if (request.getTelefonoContacto() == null || request.getTelefonoContacto().isEmpty()) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Debe ingresar un telefono de contacto");
            return cmtResponseCreaSolicitudDto;
        }

        if (request.getCanalVentas() == null || request.getCanalVentas().isEmpty()) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Debe ingresar un Canal de Ventas");
            return cmtResponseCreaSolicitudDto;
        }


        GeograficoPoliticoMgl centroPoblado = buscarCentroPoblado(codigoDane);
        String comunidad = "";
        String division = "";
        casoTcrmId = idCasoTcrm;

        BigDecimal idCentroPoblado = null;

        if (centroPoblado != null) {
            idCentroPoblado = centroPoblado.getGpoId();
            if (centroPoblado.getGpoMultiorigen().equalsIgnoreCase("1") && request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK"))
                if (request.getDrDireccion().getBarrio() != null && !request.getDrDireccion().getBarrio().isEmpty()) {
                } else {
                    cmtResponseCreaSolicitudDto.setMessageType("E");
                    cmtResponseCreaSolicitudDto.setMessage("La direccion calle-carrera pertenece a una ciudad Multiorigen, es necesario ingresar el "
                            + "barrio en el drDireccion");
                    return cmtResponseCreaSolicitudDto;
                }
        }
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
        GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());

        boolean direccionCCMM = false;
        boolean direccionHHPP = false;
        boolean direccionAmbiguaCCMMHHPP = false;


        DireccionesValidacionManager direccionesValidacionManager = new DireccionesValidacionManager();
        boolean estructuraDireccionMinima = direccionesValidacionManager.validarEstructuraDireccionBoolean(request.getDrDireccion(), Constant.TIPO_VALIDACION_DIR_HHPP);

        if (!estructuraDireccionMinima) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("La direccion ingresada no cuenta con la estructura minima de una dirección correcta");
            return cmtResponseCreaSolicitudDto;
        }


        //VALIDACION PARA DETERMINAR SI LA DIRECCIÓN ES DE TIPO CCMM AL TENER COMPLEMENTOS
        if ((request.getDrDireccion().getCpTipoNivel1() != null
                && !request.getDrDireccion().getCpTipoNivel1().isEmpty())
                || (request.getDrDireccion().getCpTipoNivel2() != null
                && !request.getDrDireccion().getCpTipoNivel2().isEmpty())
                || (request.getDrDireccion().getCpTipoNivel3() != null
                && !request.getDrDireccion().getCpTipoNivel3().isEmpty())
                || (request.getDrDireccion().getCpTipoNivel4() != null
                && !request.getDrDireccion().getCpTipoNivel4().isEmpty())) {

            direccionCCMM = true;
            direccionHHPP = false;
            direccionAmbiguaCCMMHHPP = false;

            //TECNOLOGIAS QUE PUEDEN TENER COMPLEMENTOS Y NO SON CCMM, SERIAN HHPPS
            if (tecnologiaSolicitada.getIdentificadorInternoApp() != null
                    && (tecnologiaSolicitada.getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)
                    || tecnologiaSolicitada.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)
                    || tecnologiaSolicitada.getIdentificadorInternoApp().equalsIgnoreCase(Constant.INTERNET_SOCIAL_MINTIC))) {

                direccionAmbiguaCCMMHHPP = true;
                direccionCCMM = false;
                direccionHHPP = false;
            }
        } else {
            //DIRECCION QUE ES HHPP O CCMM ÚNICO EDIFICIO
            direccionHHPP = true;
            direccionAmbiguaCCMMHHPP = true;
            direccionCCMM = false;
        }

        //VALIDACION PARA HHPP CASA CON UN VALOR SE TOMA COMO CCMM
        if (request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")
                && request.getDrDireccion().getCpTipoNivel5().contains("CASA")
                && request.getDrDireccion().getCpValorNivel5() != null && !request.getDrDireccion().getCpValorNivel5().isEmpty()) {
            direccionHHPP = false;
            direccionAmbiguaCCMMHHPP = false;
            direccionCCMM = true;
        }


        /**
         * AJUSTE IDENTIFICAR DIRECCION SOBRE CCMM o HHPP ***
         */
        DrDireccionManager drDireccionManager = new DrDireccionManager();
        String address = drDireccionManager.getDireccion(request.getDrDireccion());
        String barrioStr = drDireccionManager.obtenerBarrio(request.getDrDireccion());
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCodDaneVt(centroPoblado.getGeoCodigoDane());
        addressRequest.setAddress(address);
        addressRequest.setCity(ciudad.getGpoNombre());
        addressRequest.setState(departamento.getGpoNombre());
        addressRequest.setNeighborhood(barrioStr);

        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
        ResponseMessage responseMessageCreateDir
                = addressEJBRemote.createAddress(addressRequest,
                userLo, "MGL", "", request.getDrDireccion());

        if (responseMessageCreateDir != null && responseMessageCreateDir.getNuevaDireccionDetallada() != null
                && responseMessageCreateDir.getNuevaDireccionDetallada().getDireccion() != null) {
        } else {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("Ocurrio un error intentando crear la dirección, "
                    + "no es posible continuar con la creación de la solicitud" + responseMessageCreateDir.getMessageText());
            return cmtResponseCreaSolicitudDto;
        }

        //VALIDACION PARA DETERMINAR SI LA TECNOLOGIA INGRESADA YA EXISTE EN EL HHPP
        SubDireccionMgl subDirId = new SubDireccionMgl();
        DireccionMgl dirId = new DireccionMgl();
        dirId.setDirId(responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionId());

        if (responseMessageCreateDir.getNuevaDireccionDetallada().getSubdireccionId() != null) {
            subDirId.setSdiId(responseMessageCreateDir.getNuevaDireccionDetallada().getSubdireccionId());
        } else {
            subDirId = null;
        }

        //validacion existencia hhpp
        List<HhppMgl> hhppList = hhppMglManager.findByDirAndSubDir(dirId, subDirId);
        int tecnologiaExistenteCont = 0;
        if (hhppList != null && !hhppList.isEmpty()) {
            for (HhppMgl hhppMgl : hhppList) {
                if (hhppMgl.getNodId() != null && hhppMgl.getNodId().getNodTipo() != null) {
                    if (hhppMgl.getNodId().getNodTipo().getBasicaId().equals(tecnologiaSolicitada.getBasicaId())) {
                        tecnologiaExistenteCont++;
                    }
                }
            }
        }

        if (tecnologiaExistenteCont > 0) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("El hhpp que desea crear en la tecnología solicitada ya existe.");
            return cmtResponseCreaSolicitudDto;
        }

        boolean solicitudCreacionHhppCcmm = false;

        //SI LA DIRECCIÓN ES DE CCMM O POSIBLEMENTE DE CCMM POR TENER COMPLEMENTOS        
        if (direccionCCMM || direccionAmbiguaCCMMHHPP) {

            CmtCuentaMatrizMgl cuentaMatrizEncontrada = new CmtCuentaMatrizMgl();
            //Validación para determinar si es una CCMM la dirección
            CmtDireccionMgl direccionEnCuentaMatriz = cmtDireccionMglManager.findCmtIdDireccion(responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionId());

            //La direción existe como CCMM en MER
            if (direccionEnCuentaMatriz != null && direccionEnCuentaMatriz.getCuentaMatrizObj() != null) {

                CmtSolicitudCmMglManager cmtSolicitudCmMglManager = new CmtSolicitudCmMglManager();
                CmtTipoBasicaMgl tipoBasicaOrigenSolicitud;
                tipoBasicaOrigenSolicitud = cmtTipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_ORIGEN_SOLICITUD);
                CmtBasicaMgl origenSolicitudBasica = cmtBasicaMglManager.findByNombre(tipoBasicaOrigenSolicitud.getTipoBasicaId(), request.getCanalVentas().trim());

                if (Objects.isNull(origenSolicitudBasica)) {
                    List<CmtBasicaMgl> origenSolicitudList = cmtBasicaMglManager.findByTipoBasica(tipoBasicaOrigenSolicitud);
                    StringBuilder origenSolicitudCcmm = new StringBuilder();

                    if (CollectionUtils.isNotEmpty(origenSolicitudList)) {
                        for (CmtBasicaMgl cmtBasicaMgl : origenSolicitudList) {
                            origenSolicitudCcmm.append(cmtBasicaMgl.getNombreBasica());
                            origenSolicitudCcmm.append(", ");
                        }
                    }

                    cmtResponseCreaSolicitudDto.setMessageType("E");
                    cmtResponseCreaSolicitudDto.setMessage("La dirección ingresada es de CCMM, para la solicitud "
                            + "de creación de hhpp debe ingresar uno de los siguientes canales de venta: " + origenSolicitudCcmm);
                    return cmtResponseCreaSolicitudDto;
                }

                //Solicitud de creación de HHPP sobre CCMM
                solicitudCreacionHhppCcmm = true;
                cuentaMatrizEncontrada = direccionEnCuentaMatriz.getCuentaMatrizObj();
                CmtSubEdificioMgl subEdificioSolicitud = null;

                if (cuentaMatrizEncontrada.getSubEdificioGeneral() != null) {

                    if (cuentaMatrizEncontrada.getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null) {
                        //cargue de estados aptos para venta en un edificio.
                        List<CmtBasicaMgl> estadosCableList = cmtBasicaMglManager.findBasicaListByIdentificadorInternoApp(Constant.BASICA_TIPO_TEC_CABLE);

                        boolean esMultiEficio = false;
                        //VALIDACIÓN PARA CONOCER SI EL EDIFICIO GENERAL DE LA CCMM ES MULTIEDIFICIO O ÚNICO EDIFICIO
                        if (cuentaMatrizEncontrada.getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                            esMultiEficio = true;
                        }
                        //MULTIEDIFICIO
                        if (cuentaMatrizEncontrada.getListCmtSubEdificioMglActivos() != null && !cuentaMatrizEncontrada.getListCmtSubEdificioMglActivos().isEmpty()) {

                            if (esMultiEficio) {
                                //Se busca la torre a la cual pertenece la dirección
                                DrDireccion drDireccionSoloComplementos = new DrDireccion();
                                drDireccionSoloComplementos.setIdTipoDireccion(request.getDrDireccion().getIdTipoDireccion());
                                drDireccionSoloComplementos.setCpTipoNivel1(responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel1() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel1() : null);
                                drDireccionSoloComplementos.setCpTipoNivel2(responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel2() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel2() : null);
                                drDireccionSoloComplementos.setCpTipoNivel3(responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel3() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel3() : null);
                                drDireccionSoloComplementos.setCpTipoNivel4(responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel4() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel4() : null);

                                drDireccionSoloComplementos.setCpValorNivel1(responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel1() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel1() : null);
                                drDireccionSoloComplementos.setCpValorNivel2(responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel2() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel2() : null);
                                drDireccionSoloComplementos.setCpValorNivel3(responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel3() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel3() : null);
                                drDireccionSoloComplementos.setCpValorNivel4(responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel4() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel4() : null);
                                String valorComplementosDireccion = drDireccionManager.getDireccion(drDireccionSoloComplementos);

                                for (CmtSubEdificioMgl subedificiosActivo : cuentaMatrizEncontrada.getListCmtSubEdificioMglActivos()) {
                                    if (subedificiosActivo.getNombreSubedificio().trim().equalsIgnoreCase(valorComplementosDireccion.trim())) {
                                        subEdificioSolicitud = subedificiosActivo.clone();
                                        break;
                                    }
                                }
                            } else {

                                //Se busca la direccion detallada de la cuenta matriz unico edificio para comparar lo niveles.
                                List<CmtDireccionDetalladaMgl> direccionDetalladaCmList = cmtDireccionDetalleMglManager.findDireccionDetalladaCcmmByDirId(responseMessageCreateDir.getNuevaDireccionDetallada().getDireccion().getDirId());
                                if (direccionDetalladaCmList != null && !direccionDetalladaCmList.isEmpty()) {

                                    String cpTipoNivelOriginal1 = (responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel1() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel1() : "");
                                    String cpTipoNivelOriginal2 = (responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel2() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel2() : "");
                                    String cpTipoNivelOriginal3 = (responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel3() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel3() : "");
                                    String cpTipoNivelOriginal4 = (responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel4() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel4() : "");

                                    String cpValorNivelOriginal1 = (responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel1() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel1() : "");
                                    String cpValorNivelOriginal2 = (responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel2() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel2() : "");
                                    String cpValorNivelOriginal3 = (responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel3() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel3() : "");
                                    String cpValorNivelOriginal4 = (responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel4() != null ? responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel4() : "");

                                    for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : direccionDetalladaCmList) {
                                        String cpTipoNivel1 = (cmtDireccionDetalladaMgl.getCpTipoNivel1() != null ? cmtDireccionDetalladaMgl.getCpTipoNivel1() : "");
                                        String cpTipoNivel2 = (cmtDireccionDetalladaMgl.getCpTipoNivel2() != null ? cmtDireccionDetalladaMgl.getCpTipoNivel2() : "");
                                        String cpTipoNivel3 = (cmtDireccionDetalladaMgl.getCpTipoNivel3() != null ? cmtDireccionDetalladaMgl.getCpTipoNivel3() : "");
                                        String cpTipoNivel4 = (cmtDireccionDetalladaMgl.getCpTipoNivel4() != null ? cmtDireccionDetalladaMgl.getCpTipoNivel4() : "");

                                        String cpValorNivel1 = (cmtDireccionDetalladaMgl.getCpValorNivel1() != null ? cmtDireccionDetalladaMgl.getCpValorNivel1() : "");
                                        String cpValorNivel2 = (cmtDireccionDetalladaMgl.getCpValorNivel2() != null ? cmtDireccionDetalladaMgl.getCpValorNivel2() : "");
                                        String cpValorNivel3 = (cmtDireccionDetalladaMgl.getCpValorNivel3() != null ? cmtDireccionDetalladaMgl.getCpValorNivel3() : "");
                                        String cpValorNivel4 = (cmtDireccionDetalladaMgl.getCpValorNivel4() != null ? cmtDireccionDetalladaMgl.getCpValorNivel4() : "");

                                        if (cpTipoNivelOriginal1.equalsIgnoreCase(cpTipoNivel1) && cpTipoNivelOriginal2.equalsIgnoreCase(cpTipoNivel2)
                                                && cpTipoNivelOriginal3.equalsIgnoreCase(cpTipoNivel3) && cpTipoNivelOriginal4.equalsIgnoreCase(cpTipoNivel4)
                                                && cpValorNivelOriginal1.equalsIgnoreCase(cpValorNivel1) && cpValorNivelOriginal2.equalsIgnoreCase(cpValorNivel2)
                                                && cpValorNivelOriginal3.equalsIgnoreCase(cpValorNivel3) && cpValorNivelOriginal4.equalsIgnoreCase(cpValorNivel4)) {
                                            //Si es unico edificio se asigna como edificio de la solicitud el edificio principal
                                            subEdificioSolicitud = cuentaMatrizEncontrada.getSubEdificioGeneral();
                                        }
                                    }
                                }
                            }

                            if (subEdificioSolicitud != null) {

                                //VALIDACION PARA DETECTAR SI LA SOLICITUD YA EXISTE Y SE ENCUENTRA PENDIENTE DE GESTIÓN
                                List<CmtSolicitudCmMgl> solicitudesPendientesList = cmtSolicitudCmMglManager.findSolicitudCMHhppEnCurso(tecnologiaSolicitada.getBasicaId(),
                                        subEdificioSolicitud.getSubEdificioId(),
                                        request.getDrDireccion().getCpTipoNivel5() != null ? request.getDrDireccion().getCpTipoNivel5().toUpperCase() : null,
                                        request.getDrDireccion().getCpValorNivel5() != null ? request.getDrDireccion().getCpValorNivel5().toUpperCase() : null,
                                        request.getDrDireccion().getCpTipoNivel6() != null ? request.getDrDireccion().getCpTipoNivel6().toUpperCase() : null,
                                        request.getDrDireccion().getCpValorNivel6() != null ? request.getDrDireccion().getCpValorNivel6().toUpperCase() : null);

                                if (solicitudesPendientesList != null && !solicitudesPendientesList.isEmpty()) {
                                    cmtResponseCreaSolicitudDto.setMessageType("E");
                                    cmtResponseCreaSolicitudDto.setMessage("Ya se encuentra una solicitud de número: " + solicitudesPendientesList.get(0).getSolicitudCmId() + " para esta dirección en esta tecnología pendiente de gestión en la CCMM número: " + cuentaMatrizEncontrada.getCuentaMatrizId());
                                    return cmtResponseCreaSolicitudDto;
                                }


                                if (subEdificioSolicitud.getListTecnologiasSub() != null && !subEdificioSolicitud.getListTecnologiasSub().isEmpty()) {

                                    int contieneTecnologia = 0;
                                    int estadoCableAptoVenta = 0;

                                    //se recorren todas las tecnologias sub del subedificio para identificar si tiene la tecnologia solicitada
                                    for (CmtTecnologiaSubMgl tecnologiasSubEdificio : subEdificioSolicitud.getListTecnologiasSub()) {
                                        if (tecnologiasSubEdificio.getBasicaIdTecnologias() != null && tecnologiasSubEdificio.getBasicaIdTecnologias().getIdentificadorInternoApp() != null
                                                && tecnologiaSolicitada.getIdentificadorInternoApp() != null) {

                                            if (tecnologiasSubEdificio.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(tecnologiaSolicitada.getIdentificadorInternoApp())) {
                                                contieneTecnologia++;
                                                if (estadosCableList != null && !estadosCableList.isEmpty()) {
                                                    //si tiene la tecnologia solicitada se recorre el listado de estados aptos para venta en dicha tecnologia
                                                    for (CmtBasicaMgl estadoCable : estadosCableList) {
                                                        if (estadoCable.getBasicaId().equals(tecnologiasSubEdificio.getBasicaIdEstadosTec().getBasicaId())) {
                                                            estadoCableAptoVenta++;
                                                            break;
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                    }

                                    if (contieneTecnologia == 0) {
                                        cmtResponseCreaSolicitudDto.setMessageType("E");
                                        cmtResponseCreaSolicitudDto.setMessage("El edificio de la cuenta matriz número " + cuentaMatrizEncontrada.getCuentaMatrizId() + " no cuenta con la tecnologia habilitada apta para venta en el momento.");
                                        return cmtResponseCreaSolicitudDto;
                                    }

                                    if (estadoCableAptoVenta == 0) {
                                        cmtResponseCreaSolicitudDto.setMessageType("E");
                                        cmtResponseCreaSolicitudDto.setMessage("La tecnología del edificio de la cuenta matriz número " + cuentaMatrizEncontrada.getCuentaMatrizId() + " no cuenta con el Estado apto para venta en el momento.");
                                        return cmtResponseCreaSolicitudDto;
                                    } else {
                                        //CREAR SOLICITUD DE HHPP SOBRE CCMM
                                        CmtSolicitudHhppMgl cmtSolicitudHhppMgl = new CmtSolicitudHhppMgl();
                                        cmtSolicitudHhppMgl.setTipoSolicitud(1);
                                        cmtSolicitudHhppMgl.setCmtSubEdificioMglObj(subEdificioSolicitud);

                                        //DETALLE DEL HHPP SOBRE LA SOLICITUD
                                        if (responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel5() != null
                                                && !responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel5().isEmpty()) {
                                            cmtSolicitudHhppMgl.setOpcionNivel5(responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel5());
                                        }
                                        if (responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel5() != null
                                                && !responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel5().isEmpty()) {
                                            cmtSolicitudHhppMgl.setValorNivel5(responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel5());
                                        }

                                        if (responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel6() != null
                                                && !responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel6().isEmpty()) {
                                            cmtSolicitudHhppMgl.setOpcionNivel6(responseMessageCreateDir.getNuevaDireccionDetallada().getCpTipoNivel6());
                                        }
                                        if (responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel6() != null
                                                && !responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel6().isEmpty()) {
                                            cmtSolicitudHhppMgl.setValorNivel6(responseMessageCreateDir.getNuevaDireccionDetallada().getCpValorNivel6());
                                        }

                                        List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges = new ArrayList<>();
                                        cmtSolicitudHhppMglListToChanges.add(cmtSolicitudHhppMgl);

                                        CmtSolicitudCmMgl solicitudModCM = new CmtSolicitudCmMgl();

                                        solicitudModCM.setOrigenSolicitud(origenSolicitudBasica);
                                        solicitudModCM.setAsesor(request.getContacto().toUpperCase());
                                        solicitudModCM.setCorreoAsesor(usuario.getEmail().toUpperCase());
                                        solicitudModCM.setTelefonoAsesor(request.getTelefonoContacto());
                                        solicitudModCM.setBasicaIdTecnologia(tecnologiaSolicitada);


                                        CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();

                                        String tipoSol = "CREACION HHPP UNIDI";
                                        CmtTipoSolicitudMgl cmtTipoSolicitudMgl = cmtTipoSolicitudMglManager.findTipoSolicitudByAbreviatura(tipoSol);
                                        CmtTipoSolicitudMgl tipoSolicitudCcmmMgl = cmtTipoSolicitudMglManager.find(cmtTipoSolicitudMgl.getTipoSolicitudId());
                                        if (tipoSolicitudCcmmMgl != null) {
                                            solicitudModCM.setTipoSolicitudObj(tipoSolicitudCcmmMgl);
                                        }
                                        solicitudModCM.setUnidad("1");
                                        solicitudModCM.setCuentaMatrizObj(direccionEnCuentaMatriz.getCuentaMatrizObj());
                                        solicitudModCM.setComunidad(direccionEnCuentaMatriz.getCuentaMatrizObj().getComunidad());
                                        solicitudModCM.setDivision(direccionEnCuentaMatriz.getCuentaMatrizObj().getDivision());
                                        solicitudModCM.setCiudadGpo(direccionEnCuentaMatriz.getCuentaMatrizObj().getMunicipio());
                                        solicitudModCM.setDepartamentoGpo(direccionEnCuentaMatriz.getCuentaMatrizObj().getDepartamento());
                                        solicitudModCM.setCentroPobladoGpo(direccionEnCuentaMatriz.getCuentaMatrizObj().getCentroPoblado());
                                        CmtBasicaMgl estadoSolicitud = new CmtBasicaMgl();
                                        estadoSolicitud.setBasicaId(cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE).getBasicaId());
                                        solicitudModCM.setEstadoSolicitudObj(estadoSolicitud);
                                        solicitudModCM.setModCobertura(Short.parseShort("0"));
                                        solicitudModCM.setModDatosCm(Short.parseShort("0"));
                                        solicitudModCM.setModDireccion(Short.parseShort("0"));
                                        solicitudModCM.setModSubedificios(Short.parseShort("0"));
                                        solicitudModCM.setFechaInicioCreacion(new Date());

                                        //asigno todos los datos para guardar
                                        solicitudModCM.setListCmtSolicitudHhppMgl(null);
                                        String tiempoSolicitudDefault = "00:00:30";
                                        solicitudModCM.setTempSolicitud(tiempoSolicitudDefault);
                                        solicitudModCM.setDisponibilidadGestion("1");
                                        solicitudModCM.setUsuarioCreacion(userLo);
                                        if (idUsuario != null && !idUsuario.isEmpty()) {
                                            solicitudModCM.setUsuarioSolicitudId(new BigDecimal(idUsuario));
                                        }

                                        solicitudModCM = cmtSolicitudCmMglManager.create(solicitudModCM, userLo, perfilVt);

                                        if (solicitudModCM.getSolicitudCmId() != null) {
                                            CmtSolictudHhppMglManager cmtSolictudHhppMglManager = new CmtSolictudHhppMglManager();
                                            //Creacion de las solicitudes
                                            String validacionHhpp;
                                            validacionHhpp = cmtSolictudHhppMglManager.GuardaListadoHHPP(cmtSolicitudHhppMglListToChanges, solicitudModCM, userLo, perfilVt);
                                            if (validacionHhpp == null) {
                                                cmtResponseCreaSolicitudDto.setMessageType("E");
                                                cmtResponseCreaSolicitudDto.setMessage("Ocurrio un error al guardar los HHPP en la solicitud, por favor intente de nuevo crear la Solicitud.");
                                                return cmtResponseCreaSolicitudDto;

                                            }
                                            //Creacion de nota de la solicitud de ccmm
                                            solicitudModCM = cmtSolicitudCmMglManager.findById(solicitudModCM.getSolicitudCmId());
                                            CmtNotasSolicitudMgl notaSolicitudMgl = new CmtNotasSolicitudMgl();
                                            BigDecimal tipoNotaId = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_NOTA_HHHPP).getBasicaId();
                                            CmtBasicaMgl tipoNota = cmtBasicaMglManager.findById(tipoNotaId);
                                            notaSolicitudMgl.setTipoNotaObj(tipoNota);
                                            notaSolicitudMgl.setTelefonoUsuario(request.getTelefonoContacto());
                                            notaSolicitudMgl.setNota(request.getObservaciones());
                                            notaSolicitudMgl.setSolicitudCm(solicitudModCM);
                                            notaSolicitudMgl.setDescripcion("Creacion Solicitud HHPP");
                                            CmtNotasSolicitudMglManager cmtNotasMglMglManager = new CmtNotasSolicitudMglManager();
                                            cmtNotasMglMglManager.create(notaSolicitudMgl, userLo, perfilVt);

                                            cmtResponseCreaSolicitudDto.setIdSolicitud(solicitudModCM.getSolicitudCmId().toString());
                                            cmtResponseCreaSolicitudDto.setMessageType("I");
                                            cmtResponseCreaSolicitudDto.setMessage("Solicitud creada con éxito sobre la CCMM número: " + cuentaMatrizEncontrada.getCuentaMatrizId());
                                            cmtResponseCreaSolicitudDto.setTipoSolicitud("CCMM");
                                            return cmtResponseCreaSolicitudDto;
                                        }
                                    }

                                } else {
                                    cmtResponseCreaSolicitudDto.setMessageType("E");
                                    cmtResponseCreaSolicitudDto.setMessage("El edificio al que se le desea crear la solicitud no cuenta con el estado Apto para venta.");
                                    return cmtResponseCreaSolicitudDto;
                                }
                            } else {
                                cmtResponseCreaSolicitudDto.setMessageType("E");
                                cmtResponseCreaSolicitudDto.setMessage("La dirección ingresada pertenece a CCMM pero no se encontró el Edificio ingresado para la creación de la solicitud.");
                                return cmtResponseCreaSolicitudDto;
                            }

                        } else {
                            cmtResponseCreaSolicitudDto.setMessageType("E");
                            cmtResponseCreaSolicitudDto.setMessage("La dirección es de CCMM y no cuenta con edificios activos para realizar la solicitud.");
                            return cmtResponseCreaSolicitudDto;
                        }


                    } else {
                        cmtResponseCreaSolicitudDto.setMessageType("E");
                        cmtResponseCreaSolicitudDto.setMessage("El estado del edificio general de la CCMM " + direccionEnCuentaMatriz.getCuentaMatrizObj().getCuentaMatrizId() + "ingresada no cuenta con un IdentificadorInternoApp parametrizado");
                        return cmtResponseCreaSolicitudDto;
                    }
                } else {
                    cmtResponseCreaSolicitudDto.setMessageType("E");
                    cmtResponseCreaSolicitudDto.setMessage("La dirección de la CCMM " + direccionEnCuentaMatriz.getCuentaMatrizObj().getCuentaMatrizId() + "ingresada no cuenta con un edificio general asociado.");
                    return cmtResponseCreaSolicitudDto;
                }
            } else {
                if (direccionCCMM) {
                    cmtResponseCreaSolicitudDto.setMessageType("E");
                    cmtResponseCreaSolicitudDto.setMessage("La dirección de la CCMM ingresada no existe en MER. Debe solicitar la creación.");
                    return cmtResponseCreaSolicitudDto;
                }
            }
        }


        if (direccionCCMM) {
            cmtResponseCreaSolicitudDto.setMessageType("E");
            cmtResponseCreaSolicitudDto.setMessage("La dirección es de tipo CCMM pero no existe en MER. Debe solicitar la creación de la CCMM en la dirección.");
            return cmtResponseCreaSolicitudDto;
        }


        if (direccionAmbiguaCCMMHHPP || direccionHHPP) {

            //CREACIÓN DE SOLICITUD DE HHPP EN BARRIO ABIERTO-
            //SI ESTA EN FALSE Y ES AMBIGUA PUEDE QUE YA SE HAYA TOMADO COMO CCMM LA DIRECCIÓN
            if (!solicitudCreacionHhppCcmm) {

                //VALIDACION PARA DETERMINAR SI LA DIRECCIÓN ES DE TIPO CCMM AL TENER COMPLEMENTOS
                if ((request.getDrDireccion().getCpTipoNivel1() != null
                        && !request.getDrDireccion().getCpTipoNivel1().isEmpty())
                        || (request.getDrDireccion().getCpTipoNivel2() != null
                        && !request.getDrDireccion().getCpTipoNivel2().isEmpty())
                        || (request.getDrDireccion().getCpTipoNivel3() != null
                        && !request.getDrDireccion().getCpTipoNivel3().isEmpty())
                        || (request.getDrDireccion().getCpTipoNivel4() != null
                        && !request.getDrDireccion().getCpTipoNivel4().isEmpty())) {

                    //TECNOLOGIAS QUE PUEDEN TENER COMPLEMENTOS Y NO SON CCMM, SERIAN HHPPS
                    if (tecnologiaSolicitada.getIdentificadorInternoApp() != null
                            && (tecnologiaSolicitada.getIdentificadorInternoApp().equalsIgnoreCase(Constant.DTH)
                            || tecnologiaSolicitada.getIdentificadorInternoApp().equalsIgnoreCase(Constant.HFC_UNI)
                            || tecnologiaSolicitada.getIdentificadorInternoApp().equalsIgnoreCase(Constant.INTERNET_SOCIAL_MINTIC))) {
                    } else {
                        cmtResponseCreaSolicitudDto.setMessageType("E");
                        cmtResponseCreaSolicitudDto.setMessage("La dirección es tipo CCMM pero no existe en MER. Debe solicitar la creación de la CCMM ");
                        return cmtResponseCreaSolicitudDto;
                    }
                }


                if (!tipoTecnologia.equalsIgnoreCase(Constant.TIPO_SOLICITUD_DTH)) {
                    comunidad = centroPoblado.getGpoCodigo();
                    CmtComunidadRrManager cmtComunidarRrManager = new CmtComunidadRrManager();
                    CmtComunidadRr cmtComunidadRr = cmtComunidarRrManager.findComunidadByCodigo(comunidad);
                    division = cmtComunidadRr.getRegionalRr().getCodigoRr();
                } else {

                    if (nodoGestion == null || nodoGestion.isEmpty()) {
                        cmtResponseCreaSolicitudDto.setMessageType("E");
                        cmtResponseCreaSolicitudDto.setMessage("Si la solicitud es DTH debe ingresar el nodoGestion por favor.");
                        return cmtResponseCreaSolicitudDto;
                    }

                    NodoMglManager nodoMglManager = new NodoMglManager();
                    NodoMgl nodoMgl = nodoMglManager.findByCodigo(nodoGestion);
                    if (nodoMgl != null) {
                        if (nodoMgl.getNodId() != null) {
                            comunidad = nodoMgl.getComId().getCodigoRr();
                            division = nodoMgl.getComId().getRegionalRr().getCodigoRr();
                        }
                    }
                }

                ResponseCreaSolicitud responseCreaSolicitud;
                RequestCreaSolicitud requestCreaSolicitud = new RequestCreaSolicitud();

                requestCreaSolicitud.setIdUsuario(request.getIdUsuario());
                requestCreaSolicitud.setComunidad(comunidad);
                requestCreaSolicitud.setDivision(division);
                requestCreaSolicitud.setObservaciones(request.getObservaciones());
                requestCreaSolicitud.setContacto(request.getContacto());
                requestCreaSolicitud.setTelefonoContacto(request.getTelefonoContacto());
                requestCreaSolicitud.setCanalVentas(request.getCanalVentas());
                requestCreaSolicitud.setDrDireccion(request.getDrDireccion());

                CityEntity cityEntity = new CityEntity();
                cityEntity.setCodDane(codigoDane);
                cityEntity.setEstratoDir(request.getCmtCityEntityDto().getEstratoDir());
                cityEntity.setEstadoDir(request.getCmtCityEntityDto().getEstadoDir());

                TipoHhppMglManager tipoHhppMglManager = new TipoHhppMglManager();

                if (request.getCmtCityEntityDto().getMessage() != null && !request.getCmtCityEntityDto().getMessage().isEmpty()) {
                    List<TipoHhppMgl> listaTipoHhpp = tipoHhppMglManager.findAll();

                    boolean tipoTecRequest = false;
                    if (listaTipoHhpp != null && !listaTipoHhpp.isEmpty()) {
                        for (TipoHhppMgl item : listaTipoHhpp) {
                            if (request.getCmtCityEntityDto().getMessage().toUpperCase().equalsIgnoreCase(item.getThhID())) {
                                tipoTecRequest = true;
                                break;
                            }
                        }
                    }
                    if (!tipoTecRequest) {
                        cmtResponseCreaSolicitudDto.setMessageType("E");
                        cmtResponseCreaSolicitudDto.setMessage("El valor de Tipo de vivienda "
                                + "enviado en el campo message no se encuentra parametrizado, "
                                + "los valores posibles para ese campo son: A, C, F, G, H, I,"
                                + " K, L, N, O, P, S, U, X.");
                        return cmtResponseCreaSolicitudDto;
                    }
                    cityEntity.setMessage(request.getCmtCityEntityDto().getMessage().toUpperCase());
                }

                requestCreaSolicitud.setCityEntity(cityEntity);

                //valida si el tipo de tecnologia es DTH
                /*@Victor Bocanegra*/
                requestCreaSolicitud.setCreacionDesdeWebService(true);
                requestCreaSolicitud.setDireccionDetallada(responseMessageCreateDir.getNuevaDireccionDetallada());

                if (tipoTecnologia.equalsIgnoreCase(Constant.TIPO_SOLICITUD_DTH)) {
                    responseCreaSolicitud = crearGestionarSolicitudVisor(
                            requestCreaSolicitud, tipoTecnologia, tiempoDuracionSolicitud,
                            idCentroPoblado, idUsuario, perfilVt, nodoGestion, nodoCercano,
                            respuestaGestion, respuesta);

                    cmtResponseCreaSolicitudDto.setIdSolicitud(responseCreaSolicitud.getIdSolicitud());
                    cmtResponseCreaSolicitudDto.setMessageType(responseCreaSolicitud.getTipoRespuesta());
                    cmtResponseCreaSolicitudDto.setMessage(responseCreaSolicitud.getMensaje() + " de barrio abierto.");
                    cmtResponseCreaSolicitudDto.setTipoSolicitud("HHPP");
                    return cmtResponseCreaSolicitudDto;

                } else {
                    responseCreaSolicitud = crearSolicitudDth(requestCreaSolicitud, tipoTecnologia, null,
                            tiempoDuracionSolicitud, idCentroPoblado,
                            userLo, perfilVt, Constant.RR_DIR_CREA_HHPP_0, null, false, true, centroPoblado,
                            ciudad, departamento, false);

                    cmtResponseCreaSolicitudDto.setIdSolicitud(responseCreaSolicitud.getIdSolicitud());
                    cmtResponseCreaSolicitudDto.setMessageType(responseCreaSolicitud.getTipoRespuesta());
                    cmtResponseCreaSolicitudDto.setTipoSolicitud("HHPP");
                    cmtResponseCreaSolicitudDto.setMessage(responseCreaSolicitud.getMensaje() + " de barrio abierto.");

                    //valbuenayf  inicio ajuste crear aviso programado para tecnologias diferentes de  DTH
                    if (responseCreaSolicitud != null && !responseCreaSolicitud.getTipoRespuesta().equalsIgnoreCase("E")) {
                        //Autor: Victor Bocanegra
                        //inserto el registro en avisos programados
                        if (!idCasoTcrm.isEmpty() || !idCasoTcrm.equalsIgnoreCase("")) {
                            CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl = new CmtAvisosProgramadosMgl();
                            cmtAvisosProgramadosMgl.setCasoTcrmId(idCasoTcrm);
                            BigDecimal idSolicitud = new BigDecimal(responseCreaSolicitud.getIdSolicitud());
                            cmtAvisosProgramadosMgl.setSolicitudId(idSolicitud);
                            cmtAvisosProgramadosMgl.setTecnologia(tipoTecnologia);
                            cmtAvisosProgramadosMgl.setFechaCreacion(new Date());
                            cmtAvisosProgramadosMgl.setUsuarioCreacion(userLo);
                            cmtAvisosProgramadosMgl.setPerfilCreacion(perfilVt);
                            cmtAvisosProgramadosMgl.setEstadoRegistro(1);
                            cmtAvisoProgramadoMglManager = new CmtAvisoProgramadoMglManager();
                            CmtDefaultBasicResponse cmtDefaultBasicResponse = cmtAvisoProgramadoMglManager.
                                    crearAvisoProgramadoHhpp(cmtAvisosProgramadosMgl);
                            if (cmtDefaultBasicResponse.getMessageType().equals("I")) {
                                LOGGER.info("Se crea un aviso programado sobre la solicitud" + " " + responseCreaSolicitud.getIdSolicitud());
                            }
                        }
                    }
                    return cmtResponseCreaSolicitudDto;
                }
            }
        }
        return cmtResponseCreaSolicitudDto;
    }

    public List<CmtSubEdificioMgl> getSelectedCmtSubEdificioMgl(CmtCuentaMatrizMgl cuentaMatrizSeleccionada) {
        try {
            List<CmtSubEdificioMgl> listCmtSubEdificioMgl = null;
            if (cuentaMatrizSeleccionada != null) {
                listCmtSubEdificioMgl = cuentaMatrizSeleccionada.getSubEdificiosValidacionesHhpp();
            }
            return listCmtSubEdificioMgl;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * Permite el almacenamiento de una direccion o subdireccion en la
     * estructura Cmt_direccion_detallada
     *
     * @author Victor Bocanegra
     * @param idDireccion
     * @param drDireccion
     * @return mensaje con la respuesta de la operacion.
     *
     * espinosadiea se elimina esta funcionalidad para centralizarla en
     * CreateAddressEJB en el metodo de Created Direccion para la creacion de la
     * tabla Direccion Detallada
     *
     */
    /**
     * valbuenayf Metodo para buscar el Centro Poblado por el codigo dane
     *
     * @param codigoDane
     * @return
     */
    private GeograficoPoliticoMgl buscarCentroPoblado(String codigoDane) {
        GeograficoPoliticoManager geograficoPoliticoManager;
        GeograficoPoliticoMgl centroPobladoGpo = null;
        try {
            geograficoPoliticoManager = new GeograficoPoliticoManager();
            centroPobladoGpo = geograficoPoliticoManager.findCentroPobladoCodDane(codigoDane);
        } catch (Exception ex) {
            LOGGER.error("Error buscarCentroPoblado de CmtDireccionDetalleMglManager " + ex.getMessage());
        }
        return centroPobladoGpo;
    }

    /**
     * valbuenayf Metodo para buscar la ciudad por el codigo dane
     *
     * @param codigoDane
     * @return
     */
    private GeograficoPoliticoMgl buscarCiudad(String codigoDane) {
        GeograficoPoliticoManager geograficoPoliticoManager;
        GeograficoPoliticoMgl ciudadGpo = null;
        try {
            geograficoPoliticoManager = new GeograficoPoliticoManager();
            ciudadGpo = geograficoPoliticoManager.findCiudadCodDane(codigoDane);
        } catch (ApplicationException e) {
            LOGGER.error("Error buscarCiudad de CmtDireccionDetalleMglManager " + e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Error buscarCiudad de CmtDireccionDetalleMglManager " + ex.getMessage());
        }
        return ciudadGpo;
    }

    /**
     * valbuenayf metodo para crear una solicitud de cambio de estrato TRCM
     *
     * @param request
     * @return
     * @throws ApplicationException
     */
    public ResponseCreaSolicitudCambioEstrato crearSolitudCambioEstratoTcrm(RequestSolicitudCambioEstratoDto request)
            throws ApplicationException {
        try {
            ResponseCreaSolicitudCambioEstrato response = new ResponseCreaSolicitudCambioEstrato();
            if (request != null) {

                if (request.getIdUsuario() == null || request.getIdUsuario().isEmpty()) {
                    throw new ApplicationException("Debe ingresar el usuario por favor.");
                }

                if (request.getEstratoNuevo() == null || request.getEstratoNuevo().isEmpty()) {
                    throw new ApplicationException("Debe ingresar el estrato nuevo por favor");
                }

                if (request.getCasoTcrmId() == null || request.getCasoTcrmId().isEmpty()) {
                    throw new ApplicationException("Debe ingresar el CasoTcrmId por favor");
                }

                if (request.getDireccionDetalladaId() == null
                        || request.getDireccionDetalladaId().equals(BigDecimal.ZERO)) {
                    throw new ApplicationException("Debe ingresar el Id de la direccion detallada por favor");
                }

                if (request.getFileName() == null || request.getFileName().trim().isEmpty()) {
                    throw new ApplicationException("Las urls de los archivos son obligatorio en el filename");
                }

                Initialized.getInstance();
                UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
                UsuariosServicesDTO usuario
                        = usuariosManager.consultaInfoUserPorUsuario(request.getIdUsuario());
                if (usuario == null) {
                    throw new ApplicationException(" El usuario no fue encontrado");
                }

                //Se  valida la direccion
                DireccionMgl direccion;
                SubDireccionMgl subDireccion;
                CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();
                CmtDireccionDetalladaMgl direccionDetalladaMgl = direccionDetalleMglManager.buscarDireccionIdDireccion(request.getDireccionDetalladaId());

                if (direccionDetalladaMgl == null || direccionDetalladaMgl.getDireccionDetalladaId() == null) {
                    throw new ApplicationException(" El id de la direccion detallada no tiene registros asociados");
                }

                direccion = direccionDetalladaMgl.getDireccion();
                subDireccion = direccionDetalladaMgl.getSubDireccion();
                BigDecimal estratoActual;

                if (subDireccion != null && subDireccion.getDirId() != null) {
                    estratoActual = asignarEstratoSubDireccion(subDireccion);

                } else {
                    if (direccion == null || direccion.getDirId() == null) {
                        throw new ApplicationException(" El id de la direccion no tiene ningun registro asociado");
                    }

                    estratoActual = asignarEstratoDireccion(direccion);
                }

                // Se valida el estrato
                if (estratoActual.compareTo(new BigDecimal(request.getEstratoNuevo())) == 0) {
                    throw new ApplicationException(" El estrato debe ser diferente al estrato actual");
                }

                String cambioDir = "2";
                //Validacion si existe una solicitid en curso para la unidad
                List<Solicitud> lista = solicitudesPendientesVerificadas(request.getDireccionDetalladaId());
                if (lista != null && !lista.isEmpty()) {
                    throw new ApplicationException(" No se puede crear la solicitud, porque ya existe una solicitudId: "
                            + lista.get(0).getIdSolicitud() + ", con el casoTcrmId: " + lista.get(0).getCasoTcrmId());
                }

                String dirCompleta = null;

                if (direccion != null && direccion.getDirId() != null) {
                    dirCompleta = direccion.getDirFormatoIgac();
                } else if (subDireccion != null && subDireccion.getSdiId() != null) {
                    dirCompleta = subDireccion.getSdiFormatoIgac();
                }

                Solicitud solicitudToCreate = new Solicitud();
                solicitudToCreate.setUsuario(usuario.getUsuario());
                solicitudToCreate.setSolicitante(usuario.getNombre());
                solicitudToCreate.setCorreo(usuario.getEmail());
                solicitudToCreate.setTelSolicitante(usuario.getTelefono());
                if (usuario.getCedula() != null) {
                    solicitudToCreate.setIdSolicitante(new BigDecimal(usuario.getCedula()));
                }
                solicitudToCreate.setEstado(ConstantsSolicitudHhpp.SOL_PENDIENTE);
                solicitudToCreate.setTipo(ConstantsSolicitudHhpp.TIPO_VTCECSUS);
                solicitudToCreate.setCambioDir(cambioDir);
                solicitudToCreate.setFechaIngreso(new Date());
                solicitudToCreate.setDireccion(dirCompleta);
                solicitudToCreate.setTipoVivienda(dirCompleta);
                solicitudToCreate.setComplemento(dirCompleta);
                solicitudToCreate.setEstratoAntiguo(estratoActual.toString());
                solicitudToCreate.setEstratoNuevo(request.getEstratoNuevo());
                solicitudToCreate.setMotivo(request.getObservaciones());
                CmtDireccionDetalladaMgl direccionDetallada = new CmtDireccionDetalladaMgl();
                direccionDetallada.setDireccionDetalladaId(request.getDireccionDetalladaId());
                solicitudToCreate.setDireccionDetallada(direccionDetallada);
                if (request.getCuentaSuscriptor() != null && !request.getCuentaSuscriptor().trim().isEmpty()) {
                    solicitudToCreate.setCuentaSuscriptor(request.getCuentaSuscriptor());
                }
                solicitudToCreate.setContacto(request.getContacto());
                solicitudToCreate.setTelContacto(request.getTelefonoContacto());
                solicitudToCreate.setTipoSol(request.getCanalVentas());
                solicitudToCreate.setCasoTcrmId(request.getCasoTcrmId());
                //JDHT
                if (direccionDetalladaMgl.getDireccion()
                        != null && direccionDetalladaMgl.getDireccion().getUbicacion().getGpoIdObj().getGpoId() != null) {
                    solicitudToCreate.setCentroPobladoId(direccionDetalladaMgl.getDireccion().getUbicacion().getGpoIdObj().getGpoId());
                }
                //creacion de solicitud
                solicitudToCreate = solicitudDaoImpl.create(solicitudToCreate);

                //yfvalbeuna inicio ajuste  crear aviso programado
                CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl = new CmtAvisosProgramadosMgl();
                cmtAvisosProgramadosMgl.setCasoTcrmId(solicitudToCreate.getIdCasoTcrm());
                cmtAvisosProgramadosMgl.setSolicitudId(solicitudToCreate.getIdSolicitud());
                cmtAvisosProgramadosMgl.setTecnologia(solicitudToCreate.getTipo());
                cmtAvisosProgramadosMgl.setFechaCreacion(new Date());
                cmtAvisosProgramadosMgl.setUsuarioCreacion(usuario.getUsuario());
                cmtAvisosProgramadosMgl.setPerfilCreacion(usuario != null && usuario.getIdPerfil() != null ? usuario.getIdPerfil().intValue() : 0);
                cmtAvisosProgramadosMgl.setEstadoRegistro(1);
                cmtAvisoProgramadoMglManager = new CmtAvisoProgramadoMglManager();
                CmtDefaultBasicResponse cmtDefaultBasicResponse = cmtAvisoProgramadoMglManager.
                        crearAvisoProgramadoHhpp(cmtAvisosProgramadosMgl);
                if (cmtDefaultBasicResponse.getMessageType().equals("I")) {
                    LOGGER.info("Se crea un aviso programado sobre la solicitud" + " " + solicitudToCreate.getIdSolicitud());
                }
                //yfvalbeuna fin ajuste  crear aviso programado


                response.setTipoRespuesta("I");
                response.setMensaje("Solicitud " + solicitudToCreate.getIdSolicitud() + " creada con exito");
                response.setIdSolicitud(solicitudToCreate.getIdSolicitud().toString());

                //Almaceno la lista de urls
                TecArcCamEstratoManager tecArcCamEstratoManager = new TecArcCamEstratoManager();
                String[] partsUrls = request.getFileName().split("\\|");
                for (String url : partsUrls) {
                    String nombre;
                    String urlValida;

                    if (url.length() == (url.lastIndexOf('/') + 1)) {
                        urlValida = url.substring(0, url.length() - 1);
                        nombre = urlValida.substring(urlValida.lastIndexOf('/') + 1);
                    } else {
                        nombre = url.substring(url.lastIndexOf('/') + 1);
                    }
                    LOGGER.info("Path: " + url + " -- File: " + nombre);

                    TecArchivosCambioEstrato tecArchivosCambioEstrato = new TecArchivosCambioEstrato();
                    tecArchivosCambioEstrato.setSolicitudObj(solicitudToCreate);
                    tecArchivosCambioEstrato.setUrlArchivoSoporte(url);
                    tecArchivosCambioEstrato.setNombreArchivo(nombre);

                    int idPerfil = usuario != null && usuario.getIdPerfil() != null ? usuario.getIdPerfil().intValue() : 0;
                    tecArchivosCambioEstrato = tecArcCamEstratoManager.crear(tecArchivosCambioEstrato,
                            usuario.getUsuario(), idPerfil);

                    if (tecArchivosCambioEstrato.getIdArchivoscambioEstrato() != null) {
                        LOGGER.info("Url de archivo de soporte almacenada. ");
                    } else {
                        LOGGER.error("Ocurrio un error al momento de guardar la url.");
                    }
                }
            } else {
                throw new ApplicationException(" Debe crear una peticion para consumir el servicio.");
            }

            /**
             * TODO pendiente por definir deuda tecnica //Inicio carga del
             * archivo de soporte del cambio de estrato //Fin carga del archivo
             * de soporte del cambio de estrato
             *
             */
            return response;
        } catch (ApplicationException e) {
            LOGGER.error("Error en crearSolitudCambioEstratoTcrm de  SolicitudManager " + e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * valbuenayf metodo para validar los datos obligatorios
     *
     * @param request
     * @throws ApplicationException
     */
    public void validarDataSolicitudCambioEstratoTcrm(RequestSolicitudCambioEstratoDto request) throws ApplicationException {

        if (request.getIdUsuario() == null || request.getIdUsuario().trim().isEmpty()) {
            throw new ApplicationException("El id del usuario es obligatorio");
        }

        if (request.getEstratoNuevo() == null || request.getEstratoNuevo().trim().isEmpty()) {
            throw new ApplicationException("El estrato nuevo es obligatorio");

        } else if (!request.getEstratoNuevo().matches("^[0-7]")) {
            throw new ApplicationException("El estrato debe ser un numero de 0 al 7");
        }

        if (request.getContacto() == null || request.getContacto().trim().isEmpty()) {
            throw new ApplicationException("El contacto es obligatorio");
        }
        if (request.getTelefonoContacto() == null || request.getTelefonoContacto().trim().isEmpty()) {
            throw new ApplicationException("El telefono de contacto es obligatorio");
        }

        if (request.getDireccionDetalladaId() == null) {
            throw new ApplicationException("El id de la direccion detallada es obligatorio");
        }

        if (request.getCasoTcrmId() == null || request.getCasoTcrmId().trim().isEmpty()) {
            throw new ApplicationException("El caso tcrm id es obligatorio");
        }
    }

    /**
     * valbuenayf metodo para buscar si existen solicitudes pendientes o
     * verificados
     *
     * @param direccionDetalladaId
     * @return
     */
    public List<Solicitud> solicitudesPendientesVerificadas(BigDecimal direccionDetalladaId) {
        List<Solicitud> lista;
        SolicitudDaoImpl solicitudDao = new SolicitudDaoImpl();
        lista = solicitudDao.solicitudesPendientesVerificadas(direccionDetalladaId);
        try {
        } catch (Exception e) {
            LOGGER.error("Error en isExisteSolicitudes de  SolicitudManager" + e.getMessage());
        }
        return lista;
    }

    /**
     * valbuenayf metodo pasa asignar el estrato de direccion
     *
     * @param direccion
     * @return
     */
    private BigDecimal asignarEstratoDireccion(DireccionMgl direccion) {
        BigDecimal estratoActual;
        if (direccion.getDirEstrato() != null) {
            estratoActual = direccion.getDirEstrato();
        } else if (direccion.getDirNivelSocioecono() != null) {
            estratoActual = direccion.getDirNivelSocioecono();
        } else {
            //TODO se debe validar si no hay estrato asignado que se debe poner por defecto
            estratoActual = new BigDecimal(BigInteger.ONE);
        }
        return estratoActual;
    }

    /**
     * valbuenayf metodo pasa asignar el estrato de sudDireccion
     *
     * @param subDireccion
     * @return
     */
    private BigDecimal asignarEstratoSubDireccion(SubDireccionMgl subDireccion) {
        BigDecimal estratoActual;
        if (subDireccion.getSdiEstrato() != null) {
            estratoActual = subDireccion.getSdiEstrato();
        } else if (subDireccion.getSdiNivelSocioecono() != null) {
            estratoActual = subDireccion.getSdiNivelSocioecono();
        } else {
            //TODO se debe validar si no hay estrato asignado que se debe poner por defecto
            estratoActual = new BigDecimal(BigInteger.ONE);
        }
        return estratoActual;
    }

    /**
     * Actualizar la solicitud
     * <p>
     * Se actualiza la solicitud
     *
     * @param solicitud solicitud para actualizar
     * @return la Solicitud actualizada
     * @author becerraarmr
     */
    public Solicitud update(Solicitud solicitud) {
        try {
            return solicitudDaoImpl.update(solicitud);
        } catch (ApplicationException e) {
            LOGGER.error("Error en update. " + e.getMessage());
            throw new Error(e.getMessage());
        }
    }

    /**
     * Buscar el listado según el rango.
     * <p>
     * Busca el listado de solicitudes según un rango establecido, un tipo de
     * solicitud, un estado,
     *
     * @param rango           el rango de busqueda
     * @param tipo            tipo de solicitud
     * @param estado          estado de las solicitudes a buscar.
     * @param orderDESC       true descendiente false ascendiente
     * @param atributoOrdenar atributo a ordenar
     * @return el valor que representa el atributo
     * @throws Error si hay problemas en la busqueda.
     * @author becerraarmr
     */
    public List<Solicitud> findRange(
            int[] rango, String tipo, String estado, boolean orderDESC, String atributoOrdenar) {
        List<AtributoValorDto> list = new ArrayList<>();
        list.add(new AtributoValorDto("tipo", tipo));
        list.add(new AtributoValorDto("estado", estado));

        try {
            return solicitudDaoImpl.findRange(rango, atributoOrdenar, list, orderDESC);
        } catch (ApplicationException e) {
            LOGGER.error("Error en findRange. " + e.getMessage(), e);
            throw new Error(e.getMessage());
        }
    }

    /**
     * Buscar cuantos registros hay según los parámetros establecidos
     * <p>
     * Busca en la base de datos los registros que cumplan con los parámetros
     * establecidos.
     *
     * @param tipo   tipo de solicitud.
     * @param estado estado de la solicitud.
     * @return cantidad de registros encontrados
     * @throws Error Si hay errores en la consulta con la base de datos
     * @author becerraarmr
     */
    public int count(String tipo, String estado) {
        List<AtributoValorDto> list = new ArrayList<>();
        list.add(new AtributoValorDto("tipo", tipo));
        list.add(new AtributoValorDto("estado", estado));
        try {
            return solicitudDaoImpl.count(list);
        } catch (ApplicationException e) {
            LOGGER.error("Error en count. " + e.getMessage(), e);
            throw new Error(e.getMessage());
        }
    }

    /**
     * Buscar la solicitud
     * <p>
     * Busca la solicitud en la base de datos según los datos correspondientes.
     *
     * @param idSolicitud identificador único de la solicitud
     * @param estado      estado de la solicitud
     * @param tipo        tipo de solicitudi
     * @return la solicitud encontrada en la base de datos.
     * @throws Error si hay problemas para la busqueda
     * @author becerraarmr
     */
    public Solicitud findSolicitud(
            BigDecimal idSolicitud, String estado, String tipo) {
        try {
            if (idSolicitud != null) {
                Solicitud sol = solicitudDaoImpl.findSolicitud(new AtributoValorDto("idSolicitud",
                                idSolicitud.toString()));
                if (sol != null && sol.getEstado().equalsIgnoreCase(estado)
                        && sol.getTipo().equalsIgnoreCase(tipo)) {
                    return sol;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en findSolicitud. " + e.getMessage(), e);
            throw new Error(e.getMessage());
        }
        return null;
    }

    /**
     * Gestionar el cambio de estrato de una solicitud
     *
     * @param solicitudGestion
     * @param estratoNew
     * @param usuario
     * @param perfil
     * @return String
     * @throws Error si hay problemas en la actualizacion
     * @author Victor Bocanegra
     */
    public String gestionarCambioEstrato(Solicitud solicitudGestion,
                                         String estratoNew, String usuario, int perfil) throws ApplicationException {

        CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl = null;
        HhppMglManager hhppMglManager = new HhppMglManager();
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        DireccionMgl direccionMgl = null;
        SubDireccionMgl subDireccionMgl = null;
        List<HhppMgl> lstHhppMgls = null;
        boolean respuesta = false;
        int controlDir = 0;
        int controlSub = 0;
        int controlRr = 0;
        String mensaje = "";
        Map<String, List<String>> mensajesExcepcionesRR = null;
        String estAntDir = "";
        String estAntSubDir = "";
        List<String> unidadesMod = new ArrayList<String>();

        DireccionRRManager direccionRRManager = new DireccionRRManager(true);
        MarcasMglDaoImpl marcasMglDaoImpl = new MarcasMglDaoImpl();

        boolean habilitarRR = false;
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
            habilitarRR = true;
        }
        try {

            cmtDireccionDetalladaMgl = cmtDireccionDetalleMglManager.
                    buscarDireccionIdDireccion(solicitudGestion.getDireccionDetallada().getDireccionDetalladaId());

            if (cmtDireccionDetalladaMgl.getDireccionDetalladaId() == null) {
                LOGGER.error("No hay direccion detallada para realizar la gestion de cambio");
                mensaje = "Error: No hay direccion detallada para realizar la gestion de cambio";
                return mensaje;
            }

                if (cmtDireccionDetalladaMgl.getDireccion() != null
                        && cmtDireccionDetalladaMgl.getSubDireccion() == null) {
                    //Actualizo en direccion
                    direccionMgl = cmtDireccionDetalladaMgl.getDireccion();
                    if (direccionMgl.getDirEstrato() != null
                            && direccionMgl.getDirEstrato().intValue() > 0) {
                        estAntDir = direccionMgl.getDirEstrato().toString();
                    } else {
                        estAntDir = "NG";
                    }
                    BigDecimal estratoNuevo = new BigDecimal(estratoNew);
                    direccionMgl.setDirEstrato(estratoNuevo);
                    direccionMgl.setFechaEdicion(new Date());
                    direccionMgl.setUsuarioEdicion(usuario);
                    DireccionMglManager direccionMglManager = new DireccionMglManager();
                    direccionMgl = direccionMglManager.update(direccionMgl);
                    if (direccionMgl.getDirEstrato().
                            compareTo(estratoNuevo) == 0) {
                        LOGGER.info("Estrato en MGL_DIRECCION cambiado");
                        controlDir++;
                    } else {
                        LOGGER.error("Estrato en MGL_DIRECCION no fue cambiado");
                    }
                    //Busco los hhpp asociados a la direccion para modificarlos  
                    lstHhppMgls = hhppMglManager.findHhppDireccion(direccionMgl.getDirId());
                    //Recorro los hhpp para actualizar en RR solos los que tengan subdireccion null
                    //y idrr diferente de null
                    if (CollectionUtils.isNotEmpty(lstHhppMgls)) {
                        solicitudGestion.setIdHhppGestionado(lstHhppMgls.get(0));
                        DireccionRRManager manager = new DireccionRRManager(true);
                        for (HhppMgl hhppMgl : lstHhppMgls) {

                            String comunidad = "";
                            String division = "";
                            String calle = "";
                            String placa = "";
                            String apartamento = "";

                            if (hhppMgl.getSubDireccionObj() == null
                                    && hhppMgl.getHhpIdrR() != null && habilitarRR) {

                                HhppResponseRR responseHhppRR = manager.getHhppByIdRR(hhppMgl.getHhpIdrR());
                                if (responseHhppRR.getTipoMensaje() != null
                                        && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                    comunidad = responseHhppRR.getComunidad();
                                    division = responseHhppRR.getDivision();
                                    calle = responseHhppRR.getStreet();
                                    placa = responseHhppRR.getHouse();
                                    apartamento = responseHhppRR.getApartamento();
                                } else {
                                    LOGGER.error("Ocurrio un error consultando la data del hhpp en RR");
                                    comunidad = hhppMgl.getHhpComunidad();
                                    division = hhppMgl.getHhpDivision();
                                    calle = hhppMgl.getHhpCalle();
                                    placa = hhppMgl.getHhpPlaca();
                                    apartamento = hhppMgl.getHhpApart();
                                }

                                respuesta = direccionRRManager.cambioEstrato(comunidad,
                                        division, estratoNew, solicitudGestion.getIdSolicitud().toString(),
                                        usuario, solicitudGestion.getRespuesta(), placa, calle,
                                        apartamento, Constant.CAMBIO_ESTRATO, solicitudGestion.getTipoDocSoporteCamEstarto());
                            }

                            MarcasMgl ESO = marcasMglDaoImpl.findMarcasMglByCodigo("ESO");
                            //Se asocia la marca en MGL
                            //Creacion RR                                
                            List<MarcasMgl> listaMarcasMgl = new ArrayList<>();
                            listaMarcasMgl.add(ESO);
                            //JDHT                            
                            if (solicitudGestion.getAgregarMarcasHhppList() != null
                                    && !solicitudGestion.getAgregarMarcasHhppList().isEmpty()) {
                                listaMarcasMgl.addAll(solicitudGestion.getAgregarMarcasHhppList());
                            }
                            try {
                                hhppMglManager.agregarMarcasHhpp(hhppMgl, listaMarcasMgl, usuario);
                                LOGGER.error("Se realizo actualización de marcas en RR ");

                            } catch (Exception e) {
                                LOGGER.error("Se produjo error al realizar la actualización de marcas en RR " + e.getMessage());

                            }

                            String direccion = calle + "  "
                                    + " " + placa + " " + apartamento;
                            unidadesMod.add(direccion);
                            controlRr++;

                        }
                    } else {
                        LOGGER.error("No hay hhpp para actualizar estrato");
                    }

                    if (controlDir > 0) {
                        //se trasfroma el -1 a NG del estrato nuevo 
                        String estratoActual = direccionMgl.getDirEstrato().intValue() < 0
                                ? "NG" : direccionMgl.getDirEstrato().toString();
                        if (controlRr > 0) {
                            solicitudGestion.setEstado(Constant.ESTADO_SOL_FINALIZADO);
                            solicitudGestion.setFechaModificacion(new Date());
                            solicitudGestion.setFechaCancelacion(new Date());
                            solicitudGestion = update(solicitudGestion);
                            if (solicitudGestion.getEstado().
                                    equalsIgnoreCase(Constant.ESTADO_SOL_FINALIZADO)) {

                                mensaje = "Se finaliza la solicitud:  " + solicitudGestion.getIdSolicitud() + " "
                                        + " cambiando el estrato:  " + estAntDir + "  a  "
                                        + "estrato:  " + estratoActual + " "
                                        + " de la direccion:  " + direccionMgl.getDirFormatoIgac() + "  "
                                        + "y se modifican " + controlRr + "  hhpp en RR ";
                            }

                        } else {
                            solicitudGestion.setEstado(Constant.ESTADO_SOL_FINALIZADO);
                            solicitudGestion.setFechaModificacion(new Date());
                            solicitudGestion.setFechaCancelacion(new Date());
                            solicitudGestion = update(solicitudGestion);
                            if (solicitudGestion.getEstado().
                                    equalsIgnoreCase(Constant.ESTADO_SOL_FINALIZADO)) {

                                mensaje = "Se finaliza la solicitud:  " + solicitudGestion.getIdSolicitud() + "  "
                                        + "cambiando el estrato:  " + estAntDir + "  a  "
                                        + "estrato:  " + estratoActual + " "
                                        + " de la direccion:  " + direccionMgl.getDirFormatoIgac() + "  ";
                            }

                        }
                    }

                } else if (cmtDireccionDetalladaMgl.getDireccion() != null
                        && cmtDireccionDetalladaMgl.getSubDireccion() != null) {
                    //solo se actualiza subdireccion          
                    subDireccionMgl = cmtDireccionDetalladaMgl.getSubDireccion();
                    if (subDireccionMgl.getSdiEstrato() != null
                            && subDireccionMgl.getSdiEstrato().intValue() > 0) {
                        estAntSubDir = subDireccionMgl.getSdiEstrato().toString();
                    } else {
                        estAntSubDir = "NG";
                    }
                    BigDecimal estratoNuevo = new BigDecimal(estratoNew);
                    subDireccionMgl.setSdiEstrato(estratoNuevo);
                    subDireccionMgl.setUsuarioEdicion(usuario);
                    subDireccionMgl.setFechaEdicion(new Date());
                    SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
                    subDireccionMgl = subDireccionMglManager.update(subDireccionMgl);

                    if (subDireccionMgl.getSdiEstrato().
                            compareTo(estratoNuevo) == 0) {
                        LOGGER.info("Estrato en MGL_SUB_DIRECCION cambiado");
                        controlSub++;
                    } else {
                        LOGGER.error("Estrato en MGL_SUB_DIRECCION no fue cambiado");
                    }
                    //Busco los hhpp asociados a la Subdireccion para modificarlos  
                    lstHhppMgls = hhppMglManager.findHhppSubDireccion(subDireccionMgl.getSdiId());
                    //Recorro los hhpp para actualizar en RR solos los que tengan idrr diferente de null
                    if (CollectionUtils.isNotEmpty(lstHhppMgls)) {
                        for (HhppMgl hhppMgl : lstHhppMgls) {
                            String comunidad = "";
                            String division = "";
                            String calle = "";
                            String placa = "";
                            String apartamento = "";

                            if (hhppMgl.getHhpIdrR() != null && habilitarRR) {
                                DireccionRRManager manager = new DireccionRRManager(true);

                                HhppResponseRR responseHhppRR = manager.getHhppByIdRR(hhppMgl.getHhpIdrR());
                                if (responseHhppRR.getTipoMensaje() != null
                                        && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                    comunidad = responseHhppRR.getComunidad();
                                    division = responseHhppRR.getDivision();
                                    calle = responseHhppRR.getStreet();
                                    placa = responseHhppRR.getHouse();
                                    apartamento = responseHhppRR.getApartamento();
                                } else {
                                    LOGGER.error("Ocurrio un error consultando la data del hhpp en RR");
                                    comunidad = hhppMgl.getHhpComunidad();
                                    division = hhppMgl.getHhpDivision();
                                    calle = hhppMgl.getHhpCalle();
                                    placa = hhppMgl.getHhpPlaca();
                                    apartamento = hhppMgl.getHhpApart();
                                }

                                respuesta = direccionRRManager.cambioEstrato(comunidad,
                                        division, estratoNew, solicitudGestion.getIdSolicitud().toString(),
                                        usuario, solicitudGestion.getRespuesta(), placa, calle,
                                        apartamento, Constant.CAMBIO_ESTRATO, solicitudGestion.getTipoDocSoporteCamEstarto());

                            }

                            MarcasMgl ESO = marcasMglDaoImpl.findMarcasMglByCodigo("ESO");
                            //Se asocia la marca en MGL
                            //Creacion RR                                
                            List<MarcasMgl> listaMarcasMgl = new ArrayList<>();
                            listaMarcasMgl.add(ESO);
                            //JDHT                            
                            if (solicitudGestion.getAgregarMarcasHhppList() != null
                                    && !solicitudGestion.getAgregarMarcasHhppList().isEmpty()) {
                                listaMarcasMgl.addAll(solicitudGestion.getAgregarMarcasHhppList());
                            }
                            try {
                                hhppMglManager.agregarMarcasHhpp(hhppMgl, listaMarcasMgl, usuario);
                                LOGGER.error("Se realizo actualización de marcas en RR ");

                            } catch (Exception e) {
                                LOGGER.error("Se produjo error al realizar la actualización de marcas en RR " + e.getMessage());

                            }
                            String direccion = calle + "  "
                                    + " " + placa + " " + apartamento;
                            unidadesMod.add(direccion);
                            controlRr++;
                        }
                    } else {
                        LOGGER.error("No hay hhpp para actualizar estrato");
                    }
                    if (controlSub > 0) {

                        String estratoActual = subDireccionMgl.getSdiEstrato().intValue() < 0
                                ? "NG" : subDireccionMgl.getSdiEstrato().toString();

                        if (controlRr > 0) {
                            solicitudGestion.setEstado(Constant.ESTADO_SOL_FINALIZADO);
                            solicitudGestion.setFechaModificacion(new Date());
                            solicitudGestion.setFechaCancelacion(new Date());
                            solicitudGestion = update(solicitudGestion);
                            if (solicitudGestion.getEstado().
                                    equalsIgnoreCase(Constant.ESTADO_SOL_FINALIZADO)) {

                                mensaje = "Se finaliza la solicitud:  " + solicitudGestion.getIdSolicitud() + "  "
                                        + "cambiando el estrato:  " + estAntSubDir + "  a  "
                                        + "estrato:  " + estratoActual + " "
                                        + " de la direccion:  " + subDireccionMgl.getSdiFormatoIgac() + "  "
                                        + "y  se modifican" + controlRr + "  hhpp en RR ";
                            }
                        } else {

                            solicitudGestion.setEstado(Constant.ESTADO_SOL_FINALIZADO);
                            solicitudGestion.setFechaModificacion(new Date());
                            solicitudGestion.setFechaCancelacion(new Date());
                            solicitudGestion = update(solicitudGestion);
                            if (solicitudGestion.getEstado().
                                    equalsIgnoreCase(Constant.ESTADO_SOL_FINALIZADO)) {

                                mensaje = "Se finaliza la solicitud:  " + solicitudGestion.getIdSolicitud() + "  "
                                        + " cambiando el estrato:  " + estAntSubDir + "  a  "
                                        + "estrato:  " + estratoActual + " "
                                        + " de la direccion:  " + subDireccionMgl.getSdiFormatoIgac() + "  ";
                            }

                            //Envio de correo a solicitante
                            UsuariosServicesDTO usuarioVt = null;

                            if (usuario != null && !usuario.isEmpty()) {
                                UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
                                // Buscar al usuario a traves de la cédula. 
                                // Si no se encuentra, realiza la búsqueda por medio del parametro de VISOR.
                                usuarioVt
                                        = usuariosManager.consultaInfoUserPorCedulaVISOR(usuario);
                            }

                            if (usuarioVt != null) {
                                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                                GeograficoPoliticoMgl centroPoblado = geograficoPoliticoManager.findById(solicitudGestion.getCentroPobladoId());
                                GeograficoPoliticoMgl ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
                                GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());

                                enviarCorreoGestionSolicitud("", solicitudGestion.getCorreo() != null ? solicitudGestion.getCorreo() : "", "Gestion Solicitud de Cambio de Estrato Hhpp", mensajeCorreoGestionSolicitudCambioEstrato(solicitudGestion, "Cambio estrato",
                                        departamento.getGpoNombre(), ciudad.getGpoNombre(),
                                        centroPoblado.getGpoNombre(), solicitudGestion.getRespuesta(), usuario,
                                        solicitudGestion.getRptGestion(), estAntDir, estratoNew));
                            }
                        }
                    }

                } else {
                    LOGGER.error("No se encuentra ni direccion ni subdireccion para actualizar");
                    mensaje = "Error: No se encuentra ni direccion ni subdireccion para actualizar";
                }

        } catch (Exception e) {
            LOGGER.error("Error en gestinoarCambioEstrato. " + e.getMessage(), e);
            if (controlRr > 0) {
                mensaje = "WARNING: Se modifican en MGL y RR las direcciones: ";
                for (String msg : unidadesMod) {
                    mensaje = mensaje + "|" + msg;
                }
                mensaje = mensaje + " " + "satisfactoriamente: Pero Ocurre"
                        + " un error procesando la gestion de cambio de estrato de la dirección:";
            } else {
                mensaje = "WARNING: Ocurrio un error procesando la gestion de cambio de estrato de la dirección:";
            }
            mensajesExcepcionesRR = direccionRRManager.retornaMensajes();
            for (Map.Entry<String, List<String>> n : mensajesExcepcionesRR.entrySet()) {
                String messages = "";
                for (String mensajes : n.getValue()) {
                    messages = messages + mensajes;
                }
                mensaje = mensaje + "" + n.getKey() + ":  " + messages;
            }
            if (controlDir > 0) {
                solicitudGestion.setEstado(Constant.ESTADO_SOL_FINALIZADO);
                solicitudGestion.setFechaModificacion(new Date());
                solicitudGestion.setFechaCancelacion(new Date());
                solicitudGestion.setRespuesta(solicitudGestion.getRespuesta() + " " + mensaje);
                update(solicitudGestion);
            } else if (controlSub > 0) {
                solicitudGestion.setEstado(Constant.ESTADO_SOL_FINALIZADO);
                solicitudGestion.setFechaModificacion(new Date());
                solicitudGestion.setFechaCancelacion(new Date());
                solicitudGestion.setRespuesta(solicitudGestion.getRespuesta() + " " + mensaje);
                update(solicitudGestion);
            }
        }
        return mensaje;
    }

    /**
     * Metodo para enviar correo de gestion de solicitud
     * @param mailToAddress Dirección de correo a la que se envía el correo
     * @param solicitudGestion Solicitud que se está gestionando
     * @param mailSubject Asunto del correo
     * @param mailBody Cuerpo del correo
     * @author Gildardo Mora
     */
    private void enviarCorreoGestionSolicitud(String mailToAddress, String solicitudGestion, String mailSubject, StringBuffer mailBody) {
        CompletableFuture.runAsync(() -> {
            try {
                MailSender.send(mailToAddress, solicitudGestion, "", mailSubject, mailBody);
            } catch (Exception e) {
                LOGGER.error("Error al enviar el correo de la gestión de la solicitud {} {}", mailSubject, e.getMessage());
            }
        });
    }

    public List<Solicitud> solictudesHhppEnCurso(BigDecimal idDireccionDetallada,
                                                 BigDecimal idCentroPoblado, BigDecimal tecnologiaBasicaId,
                                                 String tipoAccionSolicitud) throws ApplicationException {
        try {
            return solicitudDaoImpl.solictudesHhppEnCurso(idDireccionDetallada,
                    idCentroPoblado, tecnologiaBasicaId, tipoAccionSolicitud);

        } catch (ApplicationException e) {
            LOGGER.error("Error en solicidutedHhppEnCurso. " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Valida si se Crea OT.Función que realiza validación de la respuesta de la
     * gestion solicitud para determinar si se debe crear una ot
     *
     * @param rptGestion respuesta con la que se gestiona la solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author ortizjaf
     */
    public boolean validaRptParaCreacionOt(String rptGestion)
            throws ApplicationException {
        try {
            return rptGestion != null && !rptGestion.isEmpty()
                    && rptGestion.equalsIgnoreCase(
                    Constant.RZ_VERIFICACION_AGENDADA);
        } catch (Exception e) {
            LOGGER.error("Error en validarRptParCreacionOt. " + e.getMessage(), e);
            String msn = "error Validando si se debe Crear OT HHPP "
                    + "para la solicitud. " + e.getMessage();
            throw new ApplicationException(msn);
        }
    }

    /**
     * Valida si la dirección de la solicitud ya se encuentra asociada a una
     * cuenta matriz
     *
     * @param tipoAccionSolicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
    public void validarDireccionEnCuentaMatriz(
            DrDireccion drDireccion, GeograficoPoliticoMgl centroPoblado, String tipoAccionSolicitud)
            throws ApplicationException {
        String msn = "";
        try {
            if (drDireccion != null && centroPoblado != null) {
                CmtCuentaMatrizMglManager cmtCuentMatrizMglManager = new CmtCuentaMatrizMglManager();
                DrDireccion DrDireccionSinComplemento = drDireccion.clone();
                DrDireccionSinComplemento.setCpTipoNivel1(null);
                DrDireccionSinComplemento.setCpValorNivel1(null);
                DrDireccionSinComplemento.setCpTipoNivel2(null);
                DrDireccionSinComplemento.setCpValorNivel2(null);
                DrDireccionSinComplemento.setCpTipoNivel3(null);
                DrDireccionSinComplemento.setCpValorNivel3(null);
                DrDireccionSinComplemento.setCpTipoNivel4(null);
                DrDireccionSinComplemento.setCpValorNivel4(null);
                DrDireccionSinComplemento.setCpTipoNivel5(null);
                DrDireccionSinComplemento.setCpValorNivel5(null);
                DrDireccionSinComplemento.setCpTipoNivel6(null);
                DrDireccionSinComplemento.setCpValorNivel6(null);

                List<CmtCuentaMatrizMgl> listaCmtCuentaMatrizMgl
                        = cmtCuentMatrizMglManager.findCuentasMatricesByDrDireccion(centroPoblado, DrDireccionSinComplemento);

                if (listaCmtCuentaMatrizMgl != null && !listaCmtCuentaMatrizMgl.isEmpty()
                        && (!tipoAccionSolicitud.equals(Constant.RR_DIR_CAMBIO_ESTRATO_2))) {
                    listaCmtCuentaMatrizMgl.get(0);

                    msn = "La dirección se encuentra asociada a una "
                            + "cuenta matriz. "
                            + "Número de cuenta: " + listaCmtCuentaMatrizMgl.get(0)
                            .getNumeroCuenta()
                            + " No es posible realizar la solicitud. ";
                    throw new ApplicationException(msn);
                }
            } else {
                msn = "La solicitud no cuenta con el DrDireccion ni el centro poblado"
                        + " para realizar la validacion de la dirección "
                        + "sobre una cuenta matriz ";
                throw new ApplicationException(msn);
            }
        } catch (ApplicationException | CloneNotSupportedException e) {
            LOGGER.error("Error en validarDireccionEnCuentaMatriz. " + e.getMessage(), e);
            throw new ApplicationException(msn + e);
        }
    }

    /**
     * Metodo que crea en base de datos las urls refernes a los achivos
     * relacionados a la solicitud
     *
     * @param Urls
     * @param solicitud
     * @param usuario
     * @param perfil
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Jonathan Peña
     */
    public boolean crearUrlsLista(List<String> Urls, Solicitud solicitud,
                                  String usuario, int perfil) throws ApplicationException {
        boolean exito = false;
        TecArcCamEstratoManager tecArcCamEstratoManager = new TecArcCamEstratoManager();
        try {
            for (int i = 0; i < Urls.size(); i++) {
                TecArchivosCambioEstrato tecArchivosCambioEstrato = new TecArchivosCambioEstrato();
                tecArchivosCambioEstrato.setSolicitudObj(solicitud);
                tecArchivosCambioEstrato.setUrlArchivoSoporte(Urls.get(i));
                tecArcCamEstratoManager.crear(tecArchivosCambioEstrato, usuario, perfil);
                exito = true;
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en crearUrlsLista. " + e.getMessage(), e);
            String msn = "ocurrio un error al insertar la lista " + e;
            throw new ApplicationException(msn);

        }

        return exito;
    }

    /**
     * Crea una solicitud de cambio de estrato a una dirección
     *
     * @param request                 request con la información necesaria para la creación de la solicitud
     * @param tiempoDuracionSolicitud
     * @param idCentroPoblado
     * @param usuarioVt
     * @param perfilVt
     * @param hhppMgl
     * @param tipoAccionSolicitud
     * @param estratoAntiguo
     * @param estratoNuevo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws java.lang.CloneNotSupportedException
     * @author Juan David Hernandez
     */
    public ResponseCreaSolicitud crearSolicitudCambioEstratoDir(
            RequestCreaSolicitud request, String tiempoDuracionSolicitud,
            BigDecimal idCentroPoblado, String usuarioVt, int perfilVt, HhppMgl hhppMgl,
            String tipoAccionSolicitud, String estratoAntiguo, String estratoNuevo)
            throws ApplicationException, CloneNotSupportedException {
        try {

            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPoblado = geograficoPoliticoManager.findById(idCentroPoblado);
            GeograficoPoliticoMgl ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
            GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());

            //JDHT
            String idUsuario = request.getIdUsuario() != null ? request.getIdUsuario() : null;
            UsuariosServicesDTO usuario = null;

            if (idUsuario != null && !idUsuario.isEmpty()) {
                UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
                // Buscar al usuario a traves de la cédula. 
                // Si no se encuentra, realiza la búsqueda por medio del parametro de VISOR.
                usuario
                        = usuariosManager.consultaInfoUserPorCedulaVISOR(idUsuario);
            }

            if (usuario == null) {
                throw new ApplicationException(USUARIO_NO_FUE_ENCONTRADO);
            }

            //Asigmanos el tipo de direccion al drdireccion P:Principal
            request.getDrDireccion().setDirPrincAlt("P");

            NegocioParamMultivalor param = new NegocioParamMultivalor();

            //Realiza busqueda de tipo de la basica del tipo de solicitud
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();

            CmtTipoBasicaMgl tipoBasicaTipoAccion
                    = cmtTipoBasicaMglManager.findByCodigoInternoApp
                    (Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            //obtiene listado de tipo de solicitud basica
            List<CmtBasicaMgl> tipoAccionSolicitudBasicaList
                    = cmtBasicaMglManager
                    .findByTipoBasica(tipoBasicaTipoAccion);

            CmtBasicaMgl tipoSolicitudBasicaId = new CmtBasicaMgl();

            if (CollectionUtils.isEmpty(tipoAccionSolicitudBasicaList)) {
                throw new ApplicationException(ConstantsSolicitudHhpp.MSG_TIPO_SOL_NO_VALIDO);
            }

            for (CmtBasicaMgl cmtBasicaMgl : tipoAccionSolicitudBasicaList) {
                if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(tipoAccionSolicitud)) {
                    //obtiene la basica de tipo de solicitud ingresada
                    tipoSolicitudBasicaId = cmtBasicaMgl.clone();
                    break;
                }
            }
            //Se asigna por default VTCECSUS pero se sobreescribe si llega a ser otro valor.
            String tipo = ConstantsSolicitudHhpp.TIPO_VTCECSUS;

            if (tipoSolicitudBasicaId == null || tipoSolicitudBasicaId.getBasicaId() == null) {
                throw new ApplicationException("El tipo de solicitud ingresado no se encuentra configurado.");
            }

            CmtTipoSolicitudMglManager cmtTipoSolMglManager = new CmtTipoSolicitudMglManager();
            //Se obtiene el tipo de solicitud de la tabla de tipo de solicitud apartir de la basica
            CmtTipoSolicitudMgl tipoSolicitudMgl = cmtTipoSolMglManager
                    .findTipoSolicitudByBasicaIdTipoSolicitud(tipoSolicitudBasicaId.getBasicaId());
            //se reasigna el valor para RR
            if (tipoSolicitudMgl != null && tipoSolicitudMgl.getTipoSolicitudId() != null) {
                tipo = tipoSolicitudMgl.getAbreviatura();
            }

            //Si valida si la direccion existe en el repositorio
            if (request.getDrDireccion().getIdDirCatastro() == null
                    || request.getDrDireccion().getIdDirCatastro().trim().isEmpty()) {

                CityEntity cityEntityCreaDir = param.consultaDptoCiudadGeo(centroPoblado.getGeoCodigoDane());
                //consultaDptoCiudad(request.getComunidad()
                if (cityEntityCreaDir == null
                        || cityEntityCreaDir.getCityName() == null
                        || cityEntityCreaDir.getDpto() == null
                        || cityEntityCreaDir.getCityName().isEmpty()
                        || cityEntityCreaDir.getDpto().isEmpty()) {
                    throw new ApplicationException("La Ciudad no esta"
                            + " configurada en Direcciones");
                }
            }

            Solicitud solicitudToCreate = new Solicitud();

            if (hhppMgl != null) {
                CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
                SolicitudDaoImpl solImpl = new SolicitudDaoImpl();
                BigDecimal dirId = null;
                BigDecimal sdirId = null;
                if (hhppMgl.getDireccionObj() != null) {
                    dirId = hhppMgl.getDireccionObj().getDirId();
                }
                if (hhppMgl.getSubDireccionObj() != null) {
                    sdirId = hhppMgl.getSubDireccionObj().getSdiId();
                }
                List<CmtDireccionDetalladaMgl> direccionDetalladaList
                        = direccionDetalladaManager.findDireccionDetallaByDirIdSdirId(dirId, sdirId);

                if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                    //Validacion si existe una solicitud en curso para la unidad            
                    List<Solicitud> solicitudEnCursoList
                            = solImpl.solictudesCambioEstratoHhppEnCurso(direccionDetalladaList.get(0).getDireccionDetalladaId(), tipoAccionSolicitud);
                    if (solicitudEnCursoList != null && !solicitudEnCursoList.isEmpty()) {
                        throw new ApplicationException("Ya existe una solicitud de cambio de estrato "
                                + "en curso que se encuentra PENDIENTE "
                                + " o VERIFICADA para esta dirección.");
                    } else {
                        solicitudToCreate.setDireccionDetallada(direccionDetalladaList.get(0));
                    }
                }
            }

            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String dirCompleta = drDireccionManager.getDireccion(request.getDrDireccion());
            String complementoDir = drDireccionManager.getComplementoDireccion();
            String placaDir = request.getDrDireccion().getPlacaDireccion();

            solicitudToCreate.setSolicitante(usuario.getNombre().toUpperCase());
            solicitudToCreate.setCorreo(usuario.getEmail());
            solicitudToCreate.setTelSolicitante(usuario.getTelefono());
            if (usuario.getCedula() != null) {
                solicitudToCreate.setIdSolicitante(new BigDecimal(usuario.getCedula()));
            }
            solicitudToCreate.setEstado(ConstantsSolicitudHhpp.SOL_PENDIENTE);
            solicitudToCreate.setTipo(tipo);
            solicitudToCreate.setCambioDir(tipoAccionSolicitud); //Cambio de Estrato
            solicitudToCreate.setFechaIngreso(new Date());
            solicitudToCreate.setCentroPobladoId(idCentroPoblado);

            solicitudToCreate.setDireccion(dirCompleta);
            solicitudToCreate.setTipoVivienda(complementoDir);
            solicitudToCreate.setNumPuerta(placaDir);
            solicitudToCreate.setMotivo(request.getObservaciones().toUpperCase());
            solicitudToCreate.setContacto(request.getContacto().toUpperCase());
            solicitudToCreate.setTelContacto(request.getTelefonoContacto());
            solicitudToCreate.setTipoSol(request.getCanalVentas());
            solicitudToCreate.setCuentaMatriz("0");
            solicitudToCreate.setDisponibilidadGestion("0");
            solicitudToCreate.setCasoTcrmId(casoTcrmId);
            solicitudToCreate.setEstratoAntiguo(estratoAntiguo);
            solicitudToCreate.setEstratoNuevo(estratoNuevo);
            solicitudToCreate.setHhppMgl(hhppMgl);
            solicitudToCreate.setTipoTecHabilitadaId(request.getCityEntity().getMessage());
            solicitudToCreate.setEstadoRegistro(1);
            solicitudToCreate.setTipoDoc(request.getTipoDocumento());//asigna el tipo de documento del cliente
            solicitudToCreate.setNumDocCliente(request.getNumDocCli()); //asigna el numero de documento del cliente

            //creacion de solicitud
            solicitudToCreate = solicitudDaoImpl.create(solicitudToCreate);
            if (solicitudToCreate != null
                    && solicitudToCreate.getIdSolicitud() != null) {
                request.getDrDireccion().setIdSolicitud(solicitudToCreate.getIdSolicitud());
            }

            // obtiene el tipo de solicitud que se esta creando por abreviatura
            CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl = null;

            if (Optional.ofNullable(solicitudToCreate).isPresent()) {
                cmtTipoSolicitudMgl = cmtTipoSolicitudMglManager
                        .findTipoSolicitudByAbreviatura(solicitudToCreate.getTipo());
            }

            if (cmtTipoSolicitudMgl != null && solicitudToCreate.getIdSolicitud() != null) {
                /*Se guarda el centro poblado en la entidad que contiene la
                 relacion de solicitud-tiposolicitud */
                CmtSolicitudTipoSolicitudMgl cmtSolicitudTipoSolicitudMgl = new CmtSolicitudTipoSolicitudMgl();
                CmtSolicitudTipoSolicitudMglManager cmtSolicitudTipoSolicitudMglManager
                        = new CmtSolicitudTipoSolicitudMglManager();
                cmtSolicitudTipoSolicitudMgl.setSolicitudObj(solicitudToCreate);
                cmtSolicitudTipoSolicitudMgl.setTipoSolicitudObj(cmtTipoSolicitudMgl);

                cmtSolicitudTipoSolicitudMglManager.create(cmtSolicitudTipoSolicitudMgl,
                        usuarioVt, perfilVt);
            }

            CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();

            CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();
            //asigna tiempo transcurrido en la solicitud (cronómetro en pantalla)

            if (tiempoDuracionSolicitud == null) {
                tiempoDuracionSolicitud = ConstantsSolicitudHhpp.DEFAULT_TIME;
            }

            cmtTiempoSolicitudMglToCreate.setTiempoSolicitud(tiempoDuracionSolicitud);
            /*asigna el objeto solicitud con el cual se relacionaran
             * los tiempos guardados */
            cmtTiempoSolicitudMglToCreate.setSolicitudObj(solicitudToCreate);
            /*Realiza la actualización de los tiempos de la solicitud en 
             la base de datos.*/
            cmtTiempoSolicitudMglToCreate.setTiempoEspera(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setTiempoGestion(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setTiempoTotal(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setArchivoSoporte("NA");
            //guarda en la base de datos el track de tiempos.
            cmtTiempoSolicitudMglManager.create(cmtTiempoSolicitudMglToCreate,
                    usuarioVt, perfilVt);

            // JDHT Envio de correo a solicitante
            //DATOS PARA ENVIAR AL CORREO
            String tipoSolicitudNombre = "";
            CmtBasicaMgl tipoSolicitudBasica = null;

            if (Optional.ofNullable(solicitudToCreate).isPresent()) {
                tipoSolicitudBasica = obtenerTipoAccionSolicitud(solicitudToCreate.getCambioDir());
            }

            if (tipoSolicitudBasica != null
                    && tipoSolicitudBasica.getNombreBasica() != null
                    && !tipoSolicitudBasica.getNombreBasica().isEmpty()) {
                tipoSolicitudNombre = tipoSolicitudBasica.getNombreBasica();
            }
            enviarCorreoGestionSolicitud(usuario.getEmail(), "", "Creacion Solicitud Cambio de Estrato Hhpp", mensajeCorreoSolicitud(solicitudToCreate, tipoSolicitudNombre,
                    departamento.getGpoNombre(), ciudad.getGpoNombre(),
                    centroPoblado.getGpoNombre(), dirCompleta));

            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("I");
            response.setMensaje("Solicitud cambio de estrato "
                    + solicitudToCreate.getIdSolicitud() + " creada con exito");
            response.setIdSolicitud(solicitudToCreate.getIdSolicitud().toString());
            return response;

        } catch (ApplicationException e) {
            LOGGER.error("Error en crearSolicitudCambioEstratoDir. " + e.getMessage(), e);
            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("E");
            response.setMensaje("Error debido a: " + e.getMessage());
            response.setIdSolicitud(null);
            return response;
        }
    }

    /**
     * Metodo para el almacenamiento de un archivo de cambio de estrato en el
     * servidor.
     *
     * @param uploadedFile componente que carga el archivo
     * @param fileName
     * @return String
     * @throws ApplicationException
     * @throws java.io.IOException
     * @author Victor Bocanegra
     */
    public String uploadArchivoSolicitud(File uploadedFile, String fileName)
            throws Exception {

        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        String rutaDoc = "";
        String server = "";
        String path = "";
        String url = "";

        if ((FileServer.uploadFileMultiServer(uploadedFile, fileName))) {

            ParametrosMgl paramsServer
                    = parametrosMglManager.findByAcronimoName(
                    Constant.PROPERTY_SERVER_PATH);

            if (paramsServer != null) {
                server = paramsServer.getParValor();
            }

            ParametrosMgl paramsPath
                    = parametrosMglManager.findByAcronimoName(
                    Constant.PROPERTY_PATH_PATH);
            if (paramsPath != null) {
                path = paramsPath.getParValor();
            }

            ParametrosMgl paramsUrl
                    = parametrosMglManager.findByAcronimoName(
                    Constant.PROPERTY_URL_PATH);

            if (paramsUrl != null) {
                url = paramsUrl.getParValor();
            }
            rutaDoc = url
                    + (int) Math.floor(Math.random() * (-1000000000))
                    + server + path
                    + fileName;

        }
        return rutaDoc;

    }

    /**
     * Crea Solicitud.
     * <p>
     * Permite crear una solicitud de Bidireccional sobre el
     * repositorio para su posterior gestión
     *
     * @param request {@link RequestCreaSolicitud} Request con la información necesaria para la creación de la solicitud
     * @return {@link ResponseCreaSolicitud} Respuesta con el proceso de creación de la solicitud //METODO
     * UTULIZADO POR LA APLICACION VISOR
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Johnnatan Ortiz
     */
    public ResponseCreaSolicitud crearSolicitud(RequestCreaSolicitud request) throws ApplicationException {
        try {
            validarDataCreacionSolicitud(request);
            //JDHT
            String idUsuario = request.getIdUsuario() != null ? request.getIdUsuario() : null;
            UsuariosServicesDTO usuario = obtenerUsuariosServicesDTO(idUsuario);
            //Asigmanos el tipo de direccion al drdireccion P:Principal
            request.getDrDireccion().setDirPrincAlt("P");
            //Asigmanos el estado GEO de la direccion al drdireccion 
            if (StringUtils.isNotBlank(request.getCityEntity().getEstadoDir())) {
                request.getDrDireccion().setEstadoDirGeo(request.getCityEntity().getEstadoDir());
            }
            //Asigmanos el estrato GEO de la direccion al drdireccion 
            if (StringUtils.isNotBlank(request.getCityEntity().getEstratoDir())) {
                request.getDrDireccion().setEstrato(request.getCityEntity().getEstratoDir());
            }

            //Validacion si existe una solicitid en curso para la unidad
            CmtDireccionSolicitudMglManager cmtSolicitudCmMglManager = new CmtDireccionSolicitudMglManager();
            cmtSolicitudCmMglManager.siExistenSolictudesEnCurso(request.getDrDireccion(), request.getComunidad());
            NegocioParamMultivalor param = new NegocioParamMultivalor();
            //Si valida si la direccion existe en el repositorio
            verificarExistenciaDireccion(request, param, usuario);
            String tipo = ConstantsSolicitudHhpp.TIPO_VTCASA;
            String cambioDir = Constant.RR_DIR_CREA_HHPP_0;//0-Creacion 1-Cambio Dir
            boolean convertToCambioDir = StringUtils.isNotBlank(request.getCityEntity().getExisteRr())
                    && request.getCityEntity().getExisteRr().contains("(C)");

            if (convertToCambioDir) {
                cambioDir = Constant.RR_DIR_CAMB_HHPP_1;
            }

            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String dirCompleta = drDireccionManager.getDireccion(request.getDrDireccion());
            String complementoDir = drDireccionManager.getComplementoDireccion();
            String placaDir = request.getDrDireccion().getPlacaDireccion();
            Solicitud solicitudToCreate = new Solicitud();
            solicitudToCreate.setUsuario(usuario.getUsuario());
            solicitudToCreate.setSolicitante(usuario.getNombre());
            solicitudToCreate.setCorreo(usuario.getEmail());
            solicitudToCreate.setTelSolicitante(usuario.getTelefono());

            if (usuario.getCedula() != null) {
                solicitudToCreate.setIdSolicitante(new BigDecimal(usuario.getCedula()));
            }

            solicitudToCreate.setEstado(ConstantsSolicitudHhpp.SOL_PENDIENTE);
            solicitudToCreate.setTipo(tipo);
            solicitudToCreate.setCambioDir(cambioDir);
            solicitudToCreate.setFechaIngreso(new Date());
            solicitudToCreate.setCiudad(request.getComunidad());
            solicitudToCreate.setRegional(request.getDivision());

            if (convertToCambioDir) {
                solicitudToCreate.setStreetName(
                        request.getCityEntity().getDireccionRREntityAntigua().getCalle());
                solicitudToCreate.setHouseNumber(
                        request.getCityEntity().getDireccionRREntityAntigua().getNumeroUnidad());
                solicitudToCreate.setAparmentNumber(
                        request.getCityEntity().getDireccionRREntityAntigua().getNumeroApartamento());
            }

            solicitudToCreate.setDireccion(dirCompleta);
            solicitudToCreate.setTipoVivienda(complementoDir);
            solicitudToCreate.setNumPuerta(placaDir);
            solicitudToCreate.setMotivo(request.getObservaciones());
            solicitudToCreate.setContacto(request.getContacto());
            solicitudToCreate.setTelContacto(request.getTelefonoContacto());
            solicitudToCreate.setTipoSol(request.getCanalVentas());
            solicitudToCreate.setCuentaMatriz("0");
            //creacion de solicitud
            solicitudToCreate = solicitudDaoImpl.create(solicitudToCreate);
            Objects.requireNonNull(solicitudToCreate, "La solicitud creada es nula");

            if (solicitudToCreate.getIdSolicitud() != null) {
                request.getDrDireccion().setIdSolicitud(solicitudToCreate.getIdSolicitud());
                DrDireccion direccionCreate = drDireccionManager.create(request.getDrDireccion());
                if (Objects.isNull(direccionCreate) || Objects.isNull(direccionCreate.getId())) {
                    solicitudDaoImpl.delete(solicitudToCreate);
                    throw new ApplicationException("No fue posible crear la solicitud");
                }
            }

            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("I");
            response.setMensaje("Solicitud " + solicitudToCreate.getIdSolicitud() + " creada con exito");
            response.setIdSolicitud(solicitudToCreate.getIdSolicitud().toString());
            return response;
        } catch (Exception e) {
            LOGGER.error("Error en crearSolicitud. " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Verifica si existe la dirección en la solicitud (ID dir catastro)
     * y la crea de ser necesario.
     *
     * @param request {@link RequestCreaSolicitud}
     * @param param   {@link NegocioParamMultivalor}
     * @param usuario {@link UsuariosServicesDTO}
     * @throws ApplicationException Excepción personalizada de aplicación.
     * @throws ExceptionDB          Excepción en caso de error en la creación en la BD.
     * @author Gildardo Mora
     */
    private void verificarExistenciaDireccion(RequestCreaSolicitud request, NegocioParamMultivalor param, UsuariosServicesDTO usuario)
            throws ApplicationException, ExceptionDB {

        Objects.requireNonNull(request, "El request de creación de solicitud es nulo, no se puede validar la dirección.");

        if (StringUtils.isNotBlank(request.getDrDireccion().getIdDirCatastro())) {
            String msgWarn = "Ya existe un idDirCatastro en el request de la solicitud " +
                    "No se requiere crear la dirección.";
            LOGGER.warn(msgWarn);
            return;
        }

        CityEntity cityEntityCreaDir = param.consultaDptoCiudad(request.getComunidad());

        if (Objects.isNull(cityEntityCreaDir) || StringUtils.isBlank(cityEntityCreaDir.getCityName())
                || StringUtils.isBlank(cityEntityCreaDir.getDpto())) {
            throw new ApplicationException("La Ciudad no esta configurada en Direcciones");
        }

        DrDireccionManager drDireccionManager = new DrDireccionManager();
        String address = drDireccionManager.getDireccion(request.getDrDireccion());
        String barrioStr = drDireccionManager.obtenerBarrio(request.getDrDireccion());
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCodDaneVt(cityEntityCreaDir.getCodDane());
        addressRequest.setAddress(address);
        addressRequest.setCity(cityEntityCreaDir.getCityName());
        addressRequest.setState("");
        addressRequest.setNeighborhood(barrioStr);

        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
        ResponseMessage responseMessageCreateDir = addressEJBRemote.createAddress(addressRequest,
                usuario.getUsuario(), "MGL", "", request.getDrDireccion());

        if (StringUtils.isBlank(responseMessageCreateDir.getIdaddress())) {
            throw new ApplicationException("No fue posible crear la direccion en el repositorio");
        }

        request.getDrDireccion().setIdDirCatastro(responseMessageCreateDir.getIdaddress());
    }

    /**
     * Obtiene la información del usuario
     *
     * @param idUsuario {@link String}
     * @return {@link UsuariosServicesDTO}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private UsuariosServicesDTO obtenerUsuariosServicesDTO(String idUsuario) throws ApplicationException {
        UsuariosServicesDTO usuario = null;

        if (StringUtils.isNotBlank(idUsuario)) {
            UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
            // Buscar al usuario a traves de la cédula.
            // Si no se encuentra, realiza la búsqueda por medio del parametro de VISOR.
            usuario = usuariosManager.consultaInfoUserPorCedulaVISOR(idUsuario);
        }

        if (usuario == null) {
            throw new ApplicationException(USUARIO_NO_FUE_ENCONTRADO);
        }
        return usuario;
    }

    public StringBuffer mensajeCorreoSolicitud(Solicitud soliciti,
                                               String tipoSolicitudNombre,
                                               String departamento, String ciudad, String centroPoblado, String direccionSolicitud) {
        StringBuffer mensaje = new StringBuffer();
        if (soliciti != null) {
            if (!soliciti.getCambioDir().equals(Constant.TIPO_SOLICITUD_VALIDACION_COBERTURA)) {
                if (soliciti.getIdSolicitud() != null) {
                    mensaje.append("<b>N&uacute;mero Solicitud\t:</b>" + soliciti.getIdSolicitud() + "<br/>");
                }
                if (tipoSolicitudNombre != null && !tipoSolicitudNombre.isEmpty()) {
                    mensaje.append("<b>Tipo Solicitud:</b>" + tipoSolicitudNombre + "<br/>");
                }
                if (soliciti.getEstado() != null && !soliciti.getEstado().isEmpty()) {
                    mensaje.append("<b>Estado Solicitud:</b>" + soliciti.getEstado() + "<br/>");
                }
                if (direccionSolicitud != null && !direccionSolicitud.isEmpty()) {
                    mensaje.append("<b>Direcci&oacute;n Principal:</b>" + direccionSolicitud + "<br/>");
                } else {
                    if (soliciti.getDireccion() != null && !soliciti.getDireccion().isEmpty()) {
                        mensaje.append("<b>Direcci&oacute;n Principal:</b>" + soliciti.getDireccion() + "<br/>");
                    }
                }
                if (soliciti.getDireccionAntiguaIgac() != null && !soliciti.getDireccionAntiguaIgac().isEmpty()) {
                    String direccionAntigua = soliciti.getDireccionAntiguaIgac().replace('&', ' ');
                    mensaje.append("<b>Dirección Antigua:</b>" + direccionAntigua + "<br/>");
                }
                if (departamento != null && !departamento.isEmpty()) {
                    mensaje.append("<b>Departamento:</b>" + departamento + "<br/>");
                }
                if (ciudad != null && !ciudad.isEmpty()) {
                    mensaje.append("<b>Ciudad:</b>" + ciudad + "<br/>");
                }
                if (centroPoblado != null && !centroPoblado.isEmpty()) {
                    mensaje.append("<b>Centro Poblado:</b>" + centroPoblado + "<br/>");
                }
                if (soliciti.getUsuario() != null && !soliciti.getUsuario().isEmpty()) {
                    mensaje.append("<b>Usuario:</b>" + soliciti.getUsuario() + "<br/>");
                }
            } else {
                mensaje.append("<b>Se ha generado una nueva solicitud de verificaci&oacute;n de cobertura sobre la direcci&oacute;n :</b>" + " " + soliciti.getDireccion() + ", ");
                if (soliciti.getTecnologiaId() != null) {
                    mensaje.append("<b> para la tecnolog&iacute;a </b>" + soliciti.getTecnologiaId().getNombreBasica() + ", ");
                }
                mensaje.append("<b> con ID </b>" + soliciti.getIdSolicitud());

            }

        }

        return mensaje;
    }

    public StringBuffer mensajeCorreoGestionSolicitud(Solicitud soliciti,
                                                      String tipoSolicitudNombre,
                                                      String departamento, String ciudad, String centroPoblado,
                                                      String estrato, String respuestaActual, String respuestaGestion, String usuario, List<UnidadStructPcml> unidadesModificadas) {
        StringBuffer mensaje = new StringBuffer();
        if (soliciti != null) {

            if (soliciti.getIdSolicitud() != null) {
                mensaje.append("<b>N&uacute;mero Solicitud\t:</b>" + soliciti.getIdSolicitud() + "<br/>");
            }
            if (tipoSolicitudNombre != null && !tipoSolicitudNombre.isEmpty()) {
                mensaje.append("<b>Tipo Solicitud:</b>" + tipoSolicitudNombre + "<br/>");
            }
            if (soliciti.getEstado() != null && !soliciti.getEstado().isEmpty()) {
                mensaje.append("<b>Estado Solicitud:</b>" + soliciti.getEstado() + "<br/>");
            }
            if (soliciti.getDireccion() != null && !soliciti.getDireccion().isEmpty()) {
                mensaje.append("<b>Direcci&oacute;n Principal:</b>" + StringUtils.stripAccents(soliciti.getDireccion()) + "<br/>");
            }
            if (estrato != null && !estrato.isEmpty()) {
                mensaje.append("<b>Estrato:</b>" + estrato + "<br/>");
            }

            if (departamento != null && !departamento.isEmpty()) {
                mensaje.append("<b>Departamento:</b>" + StringUtils.stripAccents(departamento) + "<br/>");
            }
            if (ciudad != null && !ciudad.isEmpty()) {
                mensaje.append("<b>Ciudad:</b>" + StringUtils.stripAccents(ciudad) + "<br/>");
            }
            if (centroPoblado != null && !centroPoblado.isEmpty()) {
                mensaje.append("<b>Centro Poblado:</b>" + StringUtils.stripAccents(centroPoblado) + "<br/>");
            }
            if (respuestaActual != null && !respuestaActual.isEmpty()) {
                mensaje.append("<b>Respuesta Actual:</b>" + respuestaActual + "<br/>");
            }
            if (respuestaGestion != null && !respuestaGestion.isEmpty()) {
                mensaje.append("<b>Respuesta Gesti&oacute;n:</b>" + respuestaGestion + "<br/>");
            }
            if (soliciti.getUsuario() != null && !soliciti.getUsuario().isEmpty()) {
                mensaje.append("<b>Usuario gestionador:</b>" + soliciti.getUsuario() + "<br/>");
            }

            if (unidadesModificadas != null && !unidadesModificadas.isEmpty()) {
                for (UnidadStructPcml unidadesModificada : unidadesModificadas) {
                    mensaje.append("<b>Nueva unidad modificada: </b>" + "De la unidad: " + unidadesModificada.getAptoUnidad() + " A la Unidad: " + unidadesModificada.getNewAparment() + "<br/>");
                }
            }

            if (soliciti.getDireccion() != null && !soliciti.getDireccion().isEmpty()) {
                mensaje.append("<b>Direcci&oacute;n hhpp reactivado: </b>" + soliciti.getDireccion() + "<br/>");
            }

            if (soliciti.getUsuario() != null && !soliciti.getUsuario().isEmpty()) {
                mensaje.append("<b>Correo:</b> soportehhpp@claro.com.co <br/>");
            }
        }

        return mensaje;
    }

    public StringBuffer mensajeCorreoGestionSolicitudCambioEstrato(Solicitud soliciti,
                                                                   String tipoSolicitudNombre,
                                                                   String departamento, String ciudad, String centroPoblado, String respuestaActual, String respuestaGestion, String usuario,
                                                                   String estratoAntiguo, String estratoNuevo) {
        StringBuffer mensaje = new StringBuffer();
        if (soliciti != null) {

            if (soliciti.getIdSolicitud() != null) {
                mensaje.append("<b>N&uacute;mero Solicitud\t:</b>" + soliciti.getIdSolicitud() + "<br/>");
            }
            if (tipoSolicitudNombre != null && !tipoSolicitudNombre.isEmpty()) {
                mensaje.append("<b>Tipo Solicitud:</b>" + tipoSolicitudNombre + "<br/>");
            }
            if (soliciti.getEstado() != null && !soliciti.getEstado().isEmpty()) {
                mensaje.append("<b>Estado Solicitud:</b>" + soliciti.getEstado() + "<br/>");
            }
            if (soliciti.getDireccion() != null && !soliciti.getDireccion().isEmpty()) {
                mensaje.append("<b>Direcci&oacute;n Principal:</b>" + soliciti.getDireccion() + "<br/>");
            }

            if (departamento != null && !departamento.isEmpty()) {
                mensaje.append("<b>Departamento:</b>" + departamento + "<br/>");
            }
            if (ciudad != null && !ciudad.isEmpty()) {
                mensaje.append("<b>Ciudad:</b>" + ciudad + "<br/>");
            }
            if (centroPoblado != null && !centroPoblado.isEmpty()) {
                mensaje.append("<b>Centro Poblado:</b>" + centroPoblado + "<br/>");
            }
            if (respuestaActual != null && !respuestaActual.isEmpty()) {
                mensaje.append("<b>Respuesta Actual:</b>" + respuestaActual + "<br/>");
            }
            if (respuestaGestion != null && !respuestaGestion.isEmpty()) {
                mensaje.append("<b>Respuesta Gesti&oacute;n:</b>" + respuestaGestion + "<br/>");
            }
            if (soliciti.getUsuario() != null && !soliciti.getUsuario().isEmpty()) {
                mensaje.append("<b>Usuario gestionador:</b>" + soliciti.getUsuario() + "<br/>");
            }

            if (estratoAntiguo != null && !estratoAntiguo.isEmpty()) {
                mensaje.append("<b>Estrato Antiguo:</b> " + estratoAntiguo + "<br/>");
            }

            if (estratoNuevo != null && !estratoNuevo.isEmpty()) {
                mensaje.append("<b>Estrato Nuevo:</b> " + estratoNuevo + "<br/>");
            }

            if (soliciti.getUsuario() != null && !soliciti.getUsuario().isEmpty()) {
                mensaje.append("<b>Correo:</b> soportehhpp@claro.com.co <br/>");
            }
        }

        return mensaje;
    }

    private StringBuffer mensajeCorreoEscalamientosHHPP(final Solicitud solicitud, final String departamento, final String ciudad, final String centroPoblado, final String tipoSolicitudStr) {
        final StringBuffer mensaje = new StringBuffer();
        if (solicitud != null) {

            if (solicitud.getIdSolicitud() != null) {
                mensaje.append("<b>N&uacute;mero Solicitud:</b> " + solicitud.getIdSolicitud() + "<br/>");
            }
            if (StringUtils.isNotEmpty(tipoSolicitudStr)) {
                mensaje.append("<b>Tipo Solicitud:</b> " + tipoSolicitudStr + "<br/>");
            }
            if (solicitud.getTecnologiaId() != null) {
                mensaje.append("<b>Tipo Tecnología:</b> " + solicitud.getTecnologiaId().getDescripcion() + "<br/>");
            }
            if (StringUtils.isNotEmpty(departamento)) {
                mensaje.append("<b>Departamento:</b> " + departamento + "<br/>");
            }
            if (StringUtils.isNotEmpty(ciudad)) {
                mensaje.append("<b>Ciudad:</b> " + ciudad + "<br/>");
            }
            if (StringUtils.isNotEmpty(centroPoblado)) {
                mensaje.append("<b>Centro Poblado:</b> " + centroPoblado + "<br/>");
            }
            if (StringUtils.isNotEmpty(solicitud.getEstado())) {
                mensaje.append("<b>Estado Solicitud:</b> " + solicitud.getEstado() + "<br/>");
            }
        }

        return mensaje;
    }


    /**
     * Buscar en la base de datos el reporte general
     * <p>
     * Busca en la base de datos los registros que cumplen las condiciones
     *
     * @param fechaInicial Fecha Inicial
     * @param fechaFinal  Fecha Final
     * @param tipoSol     Tipo solicitud
     * @param estado      estado
     * @param range
     * @return un listado con los registros encontrados de la base de datos.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Victor Bocanegra
     */
    public List<Object[]> buscarReporteGeneralSolicitudesHhpp(Date fechaInicial,
                                                              Date fechaFinal, String tipoSol, String estado, int[] range)
            throws ApplicationException {

        return solicitudDaoImpl.buscarReporteGeneralSolicitudesHhpp(fechaInicial, fechaFinal, tipoSol, estado, range);
    }

    /**
     * Buscar en la base de datos el reporte detallado
     * <p>
     * Busca en la base de datos los registros que cumplen las condiciones
     *
     * @param fechaInicial  Fecha Inicial
     * @param fechaFinal   Fecha Final
     * @param tipoSol      Tipo solicitud
     * @param estado       estado
     * @param minReg       minimo consulta
     * @param maxReg       maximo consulta
     * @return un listado con los registros encontrados de la base de datos.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Victor Bocanegra
     */
    public List<Object[]> buscarReporteDetalladoSolicitudesHhpp(Date fechaInicial,
                                                                Date fechaFinal, String tipoSol, String estado, int minReg, int maxReg, String usuarioLogin)
            throws ApplicationException {

        return solicitudDaoImpl.buscarReporteDetalladoSolicitudesHhpp(fechaInicial, fechaFinal,
                tipoSol, estado, minReg, maxReg);
    }

    /**
     * Contar en la base de datos los registros del reporte detallado
     *
     * @param fechaInicial  Fecha Inicial
     * @param fechaFinal   Fecha Final
     * @param tipoSol      Tipo solicitud
     * @param estado       estado
     * @return un listado con los registros encontrados de la base de datos.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Victor Bocanegra
     */
    public int numRegistroReporteDetalleSolicitudesHhpp(Date fechaInicial,
                                                        Date fechaFinal, String tipoSol, String estado)
            throws ApplicationException {

        return solicitudDaoImpl.numRegistroReporteDetalleSolicitudesHhpp(fechaInicial, fechaFinal, tipoSol, estado);
    }

    /**
     * Función utilizada para obtiener valores de tipo de acción de solicitud
     * básica.
     *
     * @param cambioDir
     * @return
     * @author Juan David Hernandez
     */
    public CmtBasicaMgl obtenerTipoAccionSolicitud(String cambioDir) {
        try {
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();

            CmtTipoBasicaMgl tipoBasicaTipoAccion;
            tipoBasicaTipoAccion = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
            List<CmtBasicaMgl> tipoAccionSolicitudBasicaList
                    = cmtBasicaMglManager
                    .findByTipoBasica(tipoBasicaTipoAccion);

            if (tipoAccionSolicitudBasicaList != null && !tipoAccionSolicitudBasicaList.isEmpty()) {
                for (CmtBasicaMgl cmtBasicaMgl : tipoAccionSolicitudBasicaList) {
                    if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(cambioDir)) {
                        return cmtBasicaMgl;
                    }
                }
            }
            return null;
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener cambio dir. " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al obtener cambio dir. " + e.getMessage(), e);
            return null;
        }
    }

    public List<Solicitud> consultarOrdenesDeSolicitudHHPP(int paginaSelected, int maxResults, CmtDireccionDetalladaMgl direccionDetalladaMgl, Solicitud filtro) throws ApplicationException {
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return solicitudDaoImpl.consultarOrdenesDeSolicitudHHPP(firstResult, maxResults, direccionDetalladaMgl, filtro);
    }

    public int countOrdenesDeSolicitudHHPP(CmtDireccionDetalladaMgl direccionDetalladaMgl, Solicitud filtro) throws ApplicationException {
        return solicitudDaoImpl.countOrdenesDeSolicitudHHPP(direccionDetalladaMgl, filtro);
    }

    public int solictudesByUsuario(String usuario) throws ApplicationException {
        return solicitudDaoImpl.solictudesByUsuario(usuario);
    }

    public ResponseCreaSolicitud crearSolicitudEscalamientoHHPP(final RequestCreaSolicitudEscalamientoHHPP request) throws ApplicationException {
        ResponseCreaSolicitud response = null;
        try {
            Objects.requireNonNull(request, "El request para crear solicitud de escalamiento de HHPP es nulo.");
            UsuariosServicesDTO usuario = consultarUsuario(request);
            List<CmtBasicaMgl> tipoAccionSolicitudBasicaList = listarTiposAccionSolicitud();

            if (CollectionUtils.isEmpty(tipoAccionSolicitudBasicaList)) {
                throw new ApplicationException(ConstantsSolicitudHhpp.MSG_TIPO_SOL_NO_VALIDO);
            }

            CmtBasicaMgl tipoSolicitudBasicaId = obtenerTipoSolicitudBasicaId(request, tipoAccionSolicitudBasicaList);

            if (Objects.isNull(tipoSolicitudBasicaId.getBasicaId())) {
                throw new ApplicationException("El tipo de solicitud ingresado no se encuentra configurado.");
            }

            String tipo = "ESCALAMIENTOHHPP";
            CmtTipoSolicitudMglManager cmtTipoSolMglManager = new CmtTipoSolicitudMglManager();
            CmtTipoSolicitudMgl tipoSolicitudMgl = cmtTipoSolMglManager
                    .findTipoSolicitudByBasicaIdTipoSolicitud(tipoSolicitudBasicaId.getBasicaId());

            BigDecimal tipoSolId = Optional.ofNullable(tipoSolicitudMgl)
                    .map(CmtTipoSolicitudMgl::getTipoSolicitudId).orElse(null);

            if (Objects.nonNull(tipoSolId)) {
                tipo = tipoSolicitudMgl.getAbreviatura();
            }

            Solicitud solicitudToCreate = new Solicitud();
            solicitudToCreate.setCambioDir(request.getTipoSolicitud());
            solicitudToCreate.setSolicitante(usuario.getNombre().toUpperCase());
            solicitudToCreate.setCorreo(usuario.getEmail());
            solicitudToCreate.setTelSolicitante(request.getTelefono());

            if (usuario.getCedula() != null) {
                solicitudToCreate.setIdSolicitante(new BigDecimal(usuario.getCedula()));
            }

            solicitudToCreate.setEstado(ConstantsSolicitudHhpp.SOL_PENDIENTE);
            solicitudToCreate.setTipo(tipo);
            solicitudToCreate.setFechaIngreso(new Date());
            solicitudToCreate.setCentroPobladoId(request.getCentroPoblado());
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPoblado = geograficoPoliticoManager.findById(request.getCentroPoblado());
            GeograficoPoliticoMgl ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
            GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String address = drDireccionManager.getDireccion(request.getDrDireccion());
            String barrioStr = drDireccionManager.obtenerBarrio(request.getDrDireccion());
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCodDaneVt(centroPoblado.getGeoCodigoDane());
            addressRequest.setAddress(address);
            addressRequest.setCity(ciudad.getGpoNombre());
            addressRequest.setState(departamento.getGpoNombre());
            addressRequest.setNeighborhood(barrioStr);
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            ResponseMessage responseMessageCreateDir = addressEJBRemote.createAddress(addressRequest,
                    usuario.getUsuario(), "MGL", "", request.getDrDireccion());

            Objects.requireNonNull(responseMessageCreateDir, "La respuesta de creación de dirección es nula");
            Optional<BigDecimal> tieneDireccionNueva = Optional.ofNullable(responseMessageCreateDir.getNuevaDireccionDetallada())
                    .map(CmtDireccionDetalladaMgl::getDireccionDetalladaId);

            if (tieneDireccionNueva.isPresent()) {
                solicitudToCreate.setDireccionDetallada(responseMessageCreateDir.getNuevaDireccionDetallada());
            }

            if (StringUtils.isBlank(responseMessageCreateDir.getIdaddress())) {
                response = new ResponseCreaSolicitud();
                response.setTipoRespuesta("E");
                response.setMensaje(responseMessageCreateDir.getMessageText());
                response.setIdSolicitud(null);
                return response;
            }

            request.getDrDireccion().setIdDirCatastro(responseMessageCreateDir.getIdaddress());
            solicitudToCreate.setDireccion(address);
            solicitudToCreate.setMotivo(request.getObservaciones().toUpperCase());
            solicitudToCreate.setTipoSol(request.getArea());
            solicitudToCreate.setDisponibilidadGestion("0");
            solicitudToCreate.setEstadoRegistro(1);
            solicitudToCreate.setComplemento(request.getComplemento());
            solicitudToCreate.setCcmm(request.getCcmm());
            solicitudToCreate.setSolicitudOT(request.getSolicitudOT());
            solicitudToCreate.setTipoHHPP(request.getTipoHHPP());
            solicitudToCreate.setTipoGestion(request.getTipoGestion());
            solicitudToCreate.setContacto("N/A");
            solicitudToCreate.setTelContacto("N/A");
            solicitudToCreate.setTecnologiaId(request.getCmtBasicaMgl());
            solicitudToCreate.setUsuario(usuario.getNombre());

            if (request.getCityEntity() != null) {
                solicitudToCreate.setTipoTecHabilitadaId(request.getCityEntity().getMessage());
            }

            // Registra la solicitud de escalamiento de HHPP
            solicitudToCreate = solicitudDaoImpl.create(solicitudToCreate);

            CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl = cmtTipoSolicitudMglManager
                    .findTipoSolicitudByAbreviatura(solicitudToCreate.getTipo());

            if (cmtTipoSolicitudMgl != null && solicitudToCreate.getIdSolicitud() != null) {
                CmtSolicitudTipoSolicitudMgl cmtSolicitudTipoSolicitudMgl = new CmtSolicitudTipoSolicitudMgl();
                CmtSolicitudTipoSolicitudMglManager cmtSolicitudTipoSolicitudMglManager
                        = new CmtSolicitudTipoSolicitudMglManager();
                cmtSolicitudTipoSolicitudMgl.setSolicitudObj(solicitudToCreate);
                cmtSolicitudTipoSolicitudMgl.setTipoSolicitudObj(cmtTipoSolicitudMgl);

                cmtSolicitudTipoSolicitudMglManager.create(cmtSolicitudTipoSolicitudMgl,
                        request.getUsuarioVT(), request.getPerfillVT());
            }

            CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();

            CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();

            if (request.getFechaInicioSolicitud() != null && !request.getFechaInicioSolicitud().equals(ConstantsSolicitudHhpp.DEFAULT_TIME)) {
                cmtTiempoSolicitudMglToCreate.setTiempoSolicitud(request.getFechaInicioSolicitud());
            } else {
                cmtTiempoSolicitudMglToCreate.setTiempoSolicitud(ConstantsSolicitudHhpp.DEFAULT_TIME);
            }

            cmtTiempoSolicitudMglToCreate.setSolicitudObj(solicitudToCreate);
            cmtTiempoSolicitudMglToCreate.setTiempoEspera(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setTiempoGestion(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setTiempoTotal(ConstantsSolicitudHhpp.DEFAULT_TIME);
            cmtTiempoSolicitudMglToCreate.setArchivoSoporte("NA");
            cmtTiempoSolicitudMglManager.create(cmtTiempoSolicitudMglToCreate,
                    request.getUsuarioVT(), request.getPerfillVT());
            Solicitud finalSolicitudToCreate = solicitudToCreate;
            enviarCorreoCreacionSolEscalamientoHomePassed(request, usuario, finalSolicitudToCreate, departamento, ciudad, centroPoblado);
            response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("I");
            response.setMensaje("Solicitud Escalamiento HHPP "
                    + solicitudToCreate.getIdSolicitud() + " para la dirección " + address + " creada correctamente. Proceso exitoso");
            response.setIdSolicitud(solicitudToCreate.getIdSolicitud().toString());
            return response;
        } catch (Exception e) {
            LOGGER.error("Error en crearSolicitudEscalamientosHHPP. " + e.getMessage(), e);
            response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("E");
            response.setMensaje("Error debido a: " + e.getMessage());
            response.setIdSolicitud(null);
            return response;
        }
    }

    private CmtBasicaMgl obtenerTipoSolicitudBasicaId(RequestCreaSolicitudEscalamientoHHPP request, List<CmtBasicaMgl> tipoAccionSolicitudBasicaList) throws CloneNotSupportedException {
        CmtBasicaMgl tipoSolicitudBasicaId = new CmtBasicaMgl();

        for (CmtBasicaMgl cmtBasicaMgl : tipoAccionSolicitudBasicaList) {
            if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(request.getTipoSolicitud())) {
                tipoSolicitudBasicaId = cmtBasicaMgl.clone();
                break;
            }
        }

        return tipoSolicitudBasicaId;
    }

    private List<CmtBasicaMgl> listarTiposAccionSolicitud() throws ApplicationException {
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtTipoBasicaMgl tipoBasicaTipoAccion = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
        return cmtBasicaMglManager.findByTipoBasica(tipoBasicaTipoAccion);
    }

    private UsuariosServicesDTO consultarUsuario(RequestCreaSolicitudEscalamientoHHPP request) throws ApplicationException {
        UsuariosServicesDTO usuario = null;

        if (StringUtils.isNotEmpty(request.getIdUsuario())) {
            UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
            usuario = usuariosManager.consultaInfoUserPorCedulaVISOR(request.getIdUsuario());
        }

        if (usuario == null) {
            throw new ApplicationException(USUARIO_NO_FUE_ENCONTRADO);
        }
        return usuario;
    }

    /**
     * Envia el correo de creación de solicitud de escalamiento de HHPP
     *
     * @param request           {@link RequestCreaSolicitudEscalamientoHHPP}
     * @param usuario           {@link UsuariosServicesDTO}
     * @param solicitudToCreate {@link Solicitud}
     * @param departamento      {@link GeograficoPoliticoMgl}
     * @param ciudad            {@link GeograficoPoliticoMgl}
     * @param centroPoblado     {@link GeograficoPoliticoMgl}
     */
    private void enviarCorreoCreacionSolEscalamientoHomePassed(RequestCreaSolicitudEscalamientoHHPP request,
            UsuariosServicesDTO usuario, Solicitud solicitudToCreate, GeograficoPoliticoMgl departamento,
            GeograficoPoliticoMgl ciudad, GeograficoPoliticoMgl centroPoblado) {

        enviarCorreoGestionSolicitud(usuario.getEmail(), "",
                "Solicitudes Escalamientos HHPP su número de Solicitud es " + solicitudToCreate.getIdSolicitud(),
                mensajeCorreoEscalamientosHHPP(solicitudToCreate,
                departamento.getGpoNombre(), ciudad.getGpoNombre(),
                centroPoblado.getGpoNombre(), request.getTipoSolicitudStr()));
    }

    /**
     * Busca la solicitud de escalamiento de cobertura más reciente a
     * partir del ID de la dirección.
     *
     * @param idDireccion {@link BigDecimal} ID de dirección MGL_DIRECCION
     * @param idBasica {@link BigDecimal}
     * @return {@link Solicitud}
     * @throws ApplicationException Excepción de la app
     */
    public Solicitud findUltimaSolicitudEscalamientoCobertura(BigDecimal idDireccion, BigDecimal idBasica) throws ApplicationException {
        return solicitudDaoImpl.findUltimaSolicitudEscalamientoCobertura(idDireccion, idBasica);
    }

    /**
     * Realiza la creación de la solicitud de traslado de HHPP bloqueado.
     *
     * @param requestCreaSolicitud {@link ResponseCreaSolicitud}
     * @return {@link Optional<ResponseCreaSolicitud>}
     * @throws ApplicationException Excepción personalizada de la App
     * @author Gildardo Mora
     */
    public Optional<ResponseCreaSolicitud> crearSolicitudTrasladoHhppBloqueado(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud) throws ApplicationException {

        try {
            if (Objects.isNull(requestCreaSolicitud)) {
                return Optional.empty();
            }

            CmtTipoSolicitudMgl tipoSolicitudMgl;
            boolean habilitarRR = requestCreaSolicitud.isHabilitarRrMer();
            Map<String, GeograficoPoliticoMgl> datosGeograficos = obtenerDatosGeograficos(habilitarRR, requestCreaSolicitud);//corregir parametro que está interno (habilitarRR)
            GeograficoPoliticoMgl centroPoblado = datosGeograficos.getOrDefault(ConstantsDirecciones.CENTRO_POBLADO, null);
            GeograficoPoliticoMgl ciudad = datosGeograficos.getOrDefault(ConstantsDirecciones.CIUDAD, null);
            GeograficoPoliticoMgl departamento = datosGeograficos.getOrDefault(ConstantsDirecciones.DEPARTAMENTO, null);
            UsuariosServicesDTO usuario = buscarUsuario(Optional.of(requestCreaSolicitud).map(RequestCreaSolicitud::getIdUsuario).orElse(null));
            asignarDatosSobreRequestDireccion(requestCreaSolicitud);
            //Realiza busqueda de tipo de la basica del tipo de solicitud
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtBasicaMgl tipoSolicitudBasicaId = buscarTipoSolicitud(requestCreaSolicitud, cmtBasicaMglManager);
            //Se asigna por default VTCASA pero se sobreescribe si llega a ser otro valor.
            String tipo = ConstantsSolicitudHhpp.TIPO_VTCASA;
            CmtTipoSolicitudMglManager cmtTipoSolicitudMglManager = new CmtTipoSolicitudMglManager();
            //Se obtiene el tipo de solicitud de la tabla de tipo de solicitud a partir de la basica
            tipoSolicitudMgl = cmtTipoSolicitudMglManager.findTipoSolicitudByBasicaIdTipoSolicitud(
                    Optional.ofNullable(tipoSolicitudBasicaId).map(CmtBasicaMgl::getBasicaId).orElse(null));
            //se reasigna el valor para RR
            if (Objects.nonNull(Optional.of(tipoSolicitudMgl).map(CmtTipoSolicitudMgl::getTipoSolicitudId).orElse(null))) {
                tipo = tipoSolicitudMgl.getAbreviatura();
            }

            String cambioDir = requestCreaSolicitud.getTipoAccionSolicitud();//0-Creacion, 1-CambioDir, 2-reactivar, 3-cambioestrato
            String cityEntityExisteRr = Optional.of(requestCreaSolicitud).map(RequestCreaSolicitud::getCityEntity).map(CityEntity::getExisteRr).orElse("");
            boolean convertToCambioDir = StringUtils.isNotBlank(cityEntityExisteRr) && cityEntityExisteRr.contains("(C)");
            if (convertToCambioDir) {
                cambioDir = Constant.RR_DIR_CAMB_HHPP_1;
            }

            validarSiCiudadEsEspecial(requestCreaSolicitud, centroPoblado);
            //SI LA SOLICITUD VIENE DESDE WEB SERVICE NO CREA LA DIRECCIÓN PORQUE YA SE REALIZÓ EN EL SERVICIO
            ResponseMessage responseMessageCreateDir;
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String address = drDireccionManager.getDireccion(requestCreaSolicitud.getDrDireccion());
            String barrioStr = drDireccionManager.obtenerBarrio(requestCreaSolicitud.getDrDireccion());
            Map<String, Object> datosCrearDireccion = new HashMap<>();
            datosCrearDireccion.put(ConstantsDirecciones.CIUDAD, ciudad);
            datosCrearDireccion.put(ConstantsDirecciones.DEPARTAMENTO, departamento);
            datosCrearDireccion.put(ConstantsDirecciones.ADDRESS, address);
            datosCrearDireccion.put(ConstantsDirecciones.BARRIO_STR, barrioStr);
            //Creardirección con RR y MER
            responseMessageCreateDir = validarYcrearDireccion(requestCreaSolicitud, datosCrearDireccion, usuario);
            requestCreaSolicitud.getDrDireccion().setIdDirCatastro(responseMessageCreateDir.getIdaddress());
            Map<String, Object> datosAsignarSolicitud = new HashMap<>();
            datosAsignarSolicitud.put("usuario", usuario);
            datosAsignarSolicitud.put(ConstantsSolicitudHhpp.ESTADO_SOL, ConstantsSolicitudHhpp.SOL_PENDIENTE);
            datosAsignarSolicitud.put("tipo", tipo);
            datosAsignarSolicitud.put("cambioDir", cambioDir);
            datosAsignarSolicitud.put("convertToCambioDir", convertToCambioDir);
            datosAsignarSolicitud.put(ConstantsDirecciones.ADDRESS, address);
            datosAsignarSolicitud.put("drDireccionManager", drDireccionManager);
            datosAsignarSolicitud.put("cmtBasicaMglManager", cmtBasicaMglManager);
            Solicitud solicitudToCreate = asignarDatosSolicitudTrasladoHhppBloqueadoPorCrear(requestCreaSolicitud,
                    datosAsignarSolicitud, responseMessageCreateDir);
            //creación de solicitud traslado HHPP Bloqueado
            solicitudToCreate = solicitudDaoImpl.create(solicitudToCreate);

            if (Objects.nonNull(Optional.of(solicitudToCreate).map(Solicitud::getIdSolicitud).orElse(null))) {
                requestCreaSolicitud.getDrDireccion().setIdSolicitud(solicitudToCreate.getIdSolicitud());
            }

            boolean modificacionUnidades = false;
            List<UnidadStructPcml> unidadesConflictos = null;

            if (CollectionUtils.isNotEmpty(requestCreaSolicitud.getUnidadesList())) {
                modificacionUnidades = agregarNuevosApartamentosModificados(requestCreaSolicitud.getUnidadesList(),
                        solicitudToCreate, usuario.getUsuario(), requestCreaSolicitud.getPerfilVt());
                /*Se obtienen las unidades que presentan conflictos para excluirlas del
                 * cambio de dirección antigua a nueva */
                unidadesConflictos = obtenerUnidadesConflictos(requestCreaSolicitud.getUnidadesList());
            }

            CityEntity requestCityEntity = Optional.of(requestCreaSolicitud).map(RequestCreaSolicitud::getCityEntity).orElse(null);
            if (Objects.nonNull(requestCityEntity) && CollectionUtils.isNotEmpty(requestCityEntity.getUnidadesAsociadasPredioAntiguasList())
                    && Objects.nonNull(requestCityEntity.getDireccionNuevaGeo()) && Objects.nonNull(requestCreaSolicitud.getIdCentroPoblado())) {
                //si existen unidades en la direccion antigua cambiamos esas unidades
                cambiarHhppDireccionAntiguaANueva(requestCreaSolicitud, solicitudToCreate, modificacionUnidades,
                        convertToCambioDir, usuario.getUsuario(), requestCreaSolicitud.getPerfilVt(), requestCreaSolicitud.getIdCentroPoblado(),
                        unidadesConflictos, habilitarRR, null, requestCityEntity);
            }

            /*Se guarda el centro poblado en la entidad que contiene la relacion de solicitud-tiposolicitud */
            registrarCentroPobaldoTipoSolicitud(requestCreaSolicitud, tipoSolicitudMgl, solicitudToCreate);
            registrarTrackDeTiemposSolicitud(requestCreaSolicitud, solicitudToCreate);
            // Envío de correo a solicitante
            requestCreaSolicitud.setAddressDir(address);
            enviarCorreoSolicitudTrasladoHhppBloqueado(requestCreaSolicitud, solicitudToCreate, false);
            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("I");
            BigDecimal idSolicitud = Optional.of(solicitudToCreate).map(Solicitud::getIdSolicitud).orElse(new BigDecimal(0));
            response.setMensaje("Solicitud " + idSolicitud + " creada con exito");
            response.setIdSolicitud(String.valueOf(idSolicitud));
            return Optional.of(response);

        } catch (Exception e) {
            String msgError = "Error en crearSolicitudDth. " + e.getMessage();
            LOGGER.error(msgError, e);
            ResponseCreaSolicitud response = new ResponseCreaSolicitud();
            response.setTipoRespuesta("E");
            response.setMensaje(e.getMessage());
            response.setIdSolicitud(null);
            return Optional.of(response);
        }
    }

    /**
     * Asigna los datos de la solicitud que será procesada.
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param datosAsignarSolicitud {@code Map<String, Object>}
     * @param responseMessageCreateDir {@link ResponseMessage}
     * @return {@link Solicitud}
     * @throws ApplicationException Excepción de la App.
     * @author Gildardo Mora
     */
    private Solicitud asignarDatosSolicitudTrasladoHhppBloqueadoPorCrear(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud,
            Map<String, Object> datosAsignarSolicitud, ResponseMessage responseMessageCreateDir) throws ApplicationException {

        UsuariosServicesDTO usuario = (UsuariosServicesDTO) datosAsignarSolicitud.get("usuario");
        String estadoSol = (String) datosAsignarSolicitud.get(ConstantsSolicitudHhpp.ESTADO_SOL);
        String tipo = (String) datosAsignarSolicitud.get("tipo");
        String cambioDir = (String) datosAsignarSolicitud.get("cambioDir");
        boolean convertToCambioDir = (boolean) datosAsignarSolicitud.get("convertToCambioDir");
        String address = (String) datosAsignarSolicitud.get(ConstantsDirecciones.ADDRESS);
        DrDireccionManager drDireccionManager = (DrDireccionManager) datosAsignarSolicitud.get("drDireccionManager");
        CmtBasicaMglManager cmtBasicaMglManager = (CmtBasicaMglManager) datosAsignarSolicitud.get("cmtBasicaMglManager");
        Solicitud solicitudToCreate = new Solicitud();
        /*ajuste realizado para obtener la tecnologia y asignarla a la solicitud a partir de la abreviatura de la tecnologia. */
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtTipoBasicaMgl cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
        CmtBasicaMgl cmtBasicaMgl = new CmtBasicaMgl();
        cmtBasicaMgl.setTipoBasicaObj(cmtTipoBasicaMgl);
        cmtBasicaMgl.setAbreviatura(requestCreaSolicitud.getTipoTecnologia());
        solicitudToCreate.setTecnologiaId(cmtBasicaMglManager.findByAbreviaAndTipoBasica(cmtBasicaMgl));
        validarInfoSolicitudConDireccion(requestCreaSolicitud, responseMessageCreateDir, solicitudToCreate);
        //Si tiene direccion antigua se establecen los valores a la solicitud
        String dirAntiguaIgac = Optional.of(requestCreaSolicitud).map(RequestCreaSolicitudTrasladoHhppBloqueado::getDireccionActual)
                .map(Solicitud::getDireccionAntiguaIgac).orElse("");

        if (StringUtils.isNotBlank(dirAntiguaIgac)) {
            solicitudToCreate.setDireccionAntiguaIgac(dirAntiguaIgac);
        }

        /* Al realizar cambio de dirección  se establece la direccion ingresada para guardarla como antigua */
        Solicitud solicitudDirActual = Optional.of(requestCreaSolicitud).map(RequestCreaSolicitudTrasladoHhppBloqueado::getDireccionActual).orElse(null);
        /*Si tiene direccion antigua se establecen los valores a la solicitud */
        validarSiAplicaCambioDireccion(requestCreaSolicitud, cambioDir, solicitudToCreate, solicitudDirActual);
        //se valida que el id del hhpp este cargado para guardarlo, se utiliza para Reactivación
        BigDecimal hhppId = Optional.of(solicitudDirActual).map(Solicitud::getHhppMgl).map(HhppMgl::getHhpId).orElse(null);
        if (Objects.nonNull(hhppId)) {
            solicitudToCreate.setHhppMgl(solicitudDirActual.getHhppMgl());
        }

        solicitudToCreate.setSolicitante(usuario.getNombre().toUpperCase());
        solicitudToCreate.setCorreo(usuario.getEmail());
        solicitudToCreate.setTelSolicitante(usuario.getTelefono());

        if (usuario.getCedula() != null) {
            solicitudToCreate.setIdSolicitante(new BigDecimal(usuario.getCedula()));
        }

        solicitudToCreate.setEstado(estadoSol);
        solicitudToCreate.setTipo(tipo);
        solicitudToCreate.setCambioDir(cambioDir);
        solicitudToCreate.setFechaIngreso(new Date());
        solicitudToCreate.setCentroPobladoId(requestCreaSolicitud.getIdCentroPoblado());
        DireccionRREntity direccionRREntityAntigua = Optional.of(requestCreaSolicitud).map(RequestCreaSolicitud::getCityEntity)
                .map(CityEntity::getDireccionRREntityAntigua).orElse(null);

        if (convertToCambioDir && Objects.nonNull(direccionRREntityAntigua)) {
            solicitudToCreate.setStreetName(Optional.of(direccionRREntityAntigua).map(DireccionRREntity::getCalle).orElse(""));
            solicitudToCreate.setHouseNumber(direccionRREntityAntigua.getNumeroUnidad());
            solicitudToCreate.setAparmentNumber(direccionRREntityAntigua.getNumeroApartamento());
            requestCreaSolicitud.getCityEntity().setDirIgacAntiguaStr(direccionRREntityAntigua.getCalle() + StringUtils.SPACE
                    + direccionRREntityAntigua.getNumeroUnidad() + StringUtils.SPACE + direccionRREntityAntigua.getNumeroApartamento());
        }

        String complementoDir = drDireccionManager.getComplementoDireccion();
        String placaDir = requestCreaSolicitud.getDrDireccion().getPlacaDireccion();
        //TODO: verificar paso de campo Apart con la letra "V"
        solicitudToCreate.setDireccion(requestCreaSolicitud.getTipoAccionSolicitud().equalsIgnoreCase(Constant.TRASLADO_HHPP_BLOQUEADO_5) ? address + "V" : address);
        solicitudToCreate.setTipoVivienda(complementoDir);
        solicitudToCreate.setNumPuerta(placaDir);
        solicitudToCreate.setMotivo(requestCreaSolicitud.getObservaciones().toUpperCase());
        solicitudToCreate.setContacto(requestCreaSolicitud.getContacto().toUpperCase());
        solicitudToCreate.setTelContacto(requestCreaSolicitud.getTelefonoContacto());
        solicitudToCreate.setTipoSol(requestCreaSolicitud.getCanalVentas());
        solicitudToCreate.setCuentaMatriz("0");
        solicitudToCreate.setDisponibilidadGestion("0");
        solicitudToCreate.setCasoTcrmId(casoTcrmId);
        solicitudToCreate.setEstadoRegistro(1);

        if (requestCreaSolicitud.getCityEntity() != null) {
            solicitudToCreate.setTipoTecHabilitadaId(requestCreaSolicitud.getCityEntity().getMessage());
        }

        String dirRespuestGeo = Optional.of(requestCreaSolicitud).map(RequestCreaSolicitud::getDrDireccion).map(DrDireccion::getDireccionRespuestaGeo).orElse(null);
        if (StringUtils.isNotBlank(dirRespuestGeo)) {
            solicitudToCreate.setDireccionRespuestaGeo(dirRespuestGeo);
        }

        solicitudToCreate.setNumDocCliente(requestCreaSolicitud.getNumDocCli());
        solicitudToCreate.setTipoDoc(requestCreaSolicitud.getTipoDocumento());
        solicitudToCreate.setNumeroCuentaClienteTrasladar(requestCreaSolicitud.getNumeroCuentaClienteTrasladar());
        return solicitudToCreate;
    }

    /**
     * Realiza la búsqueda de los datos geográficos de la dirección (Departamento, Centro Poblado, Ciudad).
     *
     * @param habilitarRR          {@code boolean}
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @return {@code Map<String, GeograficoPoliticoMgl>}
     * @throws ApplicationException Excepción personalizada de la App.
     * @author Gildardo Mora
     */
    private Map<String, GeograficoPoliticoMgl> obtenerDatosGeograficos(boolean habilitarRR,
            RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud) throws ApplicationException {

        Map<String, GeograficoPoliticoMgl> datosGeograficos = new HashMap<>();

        if (!habilitarRR) {
            DireccionesValidacionManager direccionesValidacionManager = new DireccionesValidacionManager();
            //Validación de la dirección con el minimo de partes que la construyan
            direccionesValidacionManager.validarEstructuraDireccion(requestCreaSolicitud.getDrDireccion(), Constant.TIPO_VALIDACION_DIR_HHPP);
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            datosGeograficos.put(ConstantsDirecciones.CENTRO_POBLADO, geograficoPoliticoManager.findById(requestCreaSolicitud.getIdCentroPoblado()));
            datosGeograficos.put(ConstantsDirecciones.CIUDAD, geograficoPoliticoManager.findById(datosGeograficos.get(ConstantsDirecciones.CENTRO_POBLADO).getGeoGpoId()));
            datosGeograficos.put(ConstantsDirecciones.DEPARTAMENTO, geograficoPoliticoManager.findById(datosGeograficos.get(ConstantsDirecciones.CIUDAD).getGeoGpoId()));
            validarDataCreacionSolicitud(requestCreaSolicitud);
        } else {
            datosGeograficos.put(ConstantsDirecciones.CENTRO_POBLADO, requestCreaSolicitud.getCentroPobladoDireccion());
            datosGeograficos.put(ConstantsDirecciones.CIUDAD, requestCreaSolicitud.getCiudadDireccion());
            datosGeograficos.put(ConstantsDirecciones.DEPARTAMENTO, requestCreaSolicitud.getDepartamentoDireccion());
        }

        return datosGeograficos;
    }

    /**
     * Registra el tipo de solicitud sobre la solicitud de traslado.
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param tipoSolicitudMgl {@link CmtTipoSolicitudMgl}
     * @param solicitudToCreate {@link Solicitud}
     * @throws ApplicationException Excepción de la App.
     * @author Gildardo Mora
     */
    private void registrarCentroPobaldoTipoSolicitud(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud,
            CmtTipoSolicitudMgl tipoSolicitudMgl, Solicitud solicitudToCreate) throws ApplicationException {

        if (Objects.nonNull(Optional.ofNullable(tipoSolicitudMgl).map(CmtTipoSolicitudMgl::getTipoSolicitudId).orElse(null))
                && Objects.nonNull(Optional.of(solicitudToCreate).map(Solicitud::getIdSolicitud).orElse(null))) {
            CmtSolicitudTipoSolicitudMgl cmtSolicitudTipoSolicitudMgl = new CmtSolicitudTipoSolicitudMgl();
            CmtSolicitudTipoSolicitudMglManager cmtSolicitudTipoSolicitudMglManager = new CmtSolicitudTipoSolicitudMglManager();
            cmtSolicitudTipoSolicitudMgl.setSolicitudObj(solicitudToCreate);
            cmtSolicitudTipoSolicitudMgl.setTipoSolicitudObj(tipoSolicitudMgl);
            //Crea el registro que relaciona el tipo de solicitud con la solicitud de traslado.
            cmtSolicitudTipoSolicitudMglManager.create(cmtSolicitudTipoSolicitudMgl, requestCreaSolicitud.getUsuarioVt(), requestCreaSolicitud.getPerfilVt());
        }
    }

    /**
     * Registra los tiempos de duración de la solicitud procesada.
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param solicitudToCreate    {@link Solicitud}
     * @throws ApplicationException Excepción personalizada de la App.
     * @author Gildardo Mora
     */
    private void registrarTrackDeTiemposSolicitud(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud, Solicitud solicitudToCreate) throws ApplicationException {
        CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
        CmtTiempoSolicitudMgl cmtTiempoSolicitudMglToCreate = new CmtTiempoSolicitudMgl();
        //asigna tiempo transcurrido en la solicitud (cronómetro en pantalla)
        if (requestCreaSolicitud.getTiempoDuracionSolicitud() == null) {
            requestCreaSolicitud.setTiempoDuracionSolicitud(ConstantsSolicitudHhpp.DEFAULT_TIME);
        }

        cmtTiempoSolicitudMglToCreate.setTiempoSolicitud(requestCreaSolicitud.getTiempoDuracionSolicitud());
        /*asigna el objeto solicitud con el cual se relacionaran los tiempos guardados */
        cmtTiempoSolicitudMglToCreate.setSolicitudObj(solicitudToCreate);
        /*Realiza la actualización de los tiempos de la solicitud en la base de datos.*/
        cmtTiempoSolicitudMglToCreate.setTiempoEspera(ConstantsSolicitudHhpp.DEFAULT_TIME);
        cmtTiempoSolicitudMglToCreate.setTiempoGestion(ConstantsSolicitudHhpp.DEFAULT_TIME);
        cmtTiempoSolicitudMglToCreate.setTiempoTotal(ConstantsSolicitudHhpp.DEFAULT_TIME);
        cmtTiempoSolicitudMglToCreate.setArchivoSoporte("NA");
        //guarda en la base de datos el track de tiempos.
        cmtTiempoSolicitudMglManager.create(cmtTiempoSolicitudMglToCreate, requestCreaSolicitud.getUsuarioVt(), requestCreaSolicitud.getPerfilVt());
    }

    /**
     * Verifica si requiere cambio de dirección para agregar al proceso principal.
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param cambioDir            {@link String}
     * @param solicitudToCreate    {@link Solicitud}
     * @param solicitudDirActual   {@link Solicitud}
     * @throws ApplicationException Excepción personalizada de App.
     * @author Gildardo Mora
     */
    private void validarSiAplicaCambioDireccion(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud,
            String cambioDir, Solicitud solicitudToCreate, Solicitud solicitudDirActual) throws ApplicationException {

        if (cambioDir.equals(Constant.RR_DIR_CAMB_HHPP_1) && Objects.nonNull(solicitudDirActual)
                && StringUtils.isNotBlank(solicitudDirActual.getStreetName())
                && StringUtils.isNotBlank(solicitudDirActual.getHouseNumber())
                && StringUtils.isNotBlank(solicitudDirActual.getAparmentNumber())) {

            solicitudToCreate.setStreetName(solicitudDirActual.getStreetName());
            solicitudToCreate.setHouseNumber(solicitudDirActual.getHouseNumber());
            solicitudToCreate.setAparmentNumber(solicitudDirActual.getAparmentNumber());
            DrDireccion requestDrDireccion = requestCreaSolicitud.getDrDireccion();
            solicitudToCreate.setCpTipoNivel5(Optional.of(requestDrDireccion).map(DrDireccion::getCpTipoNivel5).orElse(null));
            solicitudToCreate.setCpTipoNivel6(Optional.of(requestDrDireccion).map(DrDireccion::getCpTipoNivel6).orElse(null));
            solicitudToCreate.setCpValorNivel5(Optional.of(requestDrDireccion).map(DrDireccion::getCpValorNivel5).orElse(null));
            solicitudToCreate.setCpValorNivel6(Optional.of(requestDrDireccion).map(DrDireccion::getCpValorNivel6).orElse(null));
            HhppMglManager hhppMglManager = new HhppMglManager();
            CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
            HhppMgl hhppCambioApto = hhppMglManager.findById(solicitudDirActual.getHhppMgl().getHhpId());
            BigDecimal sdirId = Optional.of(hhppCambioApto).map(HhppMgl::getSubDireccionObj).map(SubDireccionMgl::getSdiId).orElse(null);
            List<CmtDireccionDetalladaMgl> direccionDetalladaList = direccionDetalladaManager
                    .findDireccionDetallaByDirIdSdirId(hhppCambioApto.getDireccionObj().getDirId(), sdirId);
            solicitudToCreate.setDireccionDetalladaOrigenSolicitud(direccionDetalladaList.get(0));
        }
    }

    /**
     * Realiza la validación y creación de la dirección asociada a la solicitud.
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param datosCrearDireccion {@code Map<String,Object>}
     * @param usuario {@link UsuariosServicesDTO}
     * @return {@link ResponseMessage}
     * @throws ExceptionDB Excepción de BD.
     * @throws ApplicationException Excepción personalizada de la App.
     * @author Gildardo Mora
     */
    private ResponseMessage validarYcrearDireccion(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud,
            Map<String,Object> datosCrearDireccion, UsuariosServicesDTO usuario) throws ExceptionDB, ApplicationException {

        ResponseMessage responseMessageCreateDir;
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCodDaneVt(requestCreaSolicitud.getCentroPobladoDireccion().getGeoCodigoDane());
        addressRequest.setAddress((String) datosCrearDireccion.getOrDefault(ConstantsDirecciones.ADDRESS, ""));
        GeograficoPoliticoMgl ciudad = (GeograficoPoliticoMgl) datosCrearDireccion.getOrDefault(ConstantsDirecciones.CIUDAD, null);
        addressRequest.setCity(Optional.ofNullable(ciudad).map(GeograficoPoliticoMgl::getGpoNombre).orElse(null));
        GeograficoPoliticoMgl departamento = (GeograficoPoliticoMgl) datosCrearDireccion.getOrDefault(ConstantsDirecciones.DEPARTAMENTO, null);
        addressRequest.setState(Optional.ofNullable(departamento).map(GeograficoPoliticoMgl::getGpoNombre).orElse(null));
        addressRequest.setNeighborhood((String) datosCrearDireccion.getOrDefault(ConstantsDirecciones.BARRIO_STR, ""));
        /*Crea Dirección */
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
        responseMessageCreateDir = addressEJBRemote.createAddress(addressRequest, usuario.getUsuario(), "MGL", "", requestCreaSolicitud.getDrDireccion());

        if (StringUtils.isBlank(responseMessageCreateDir.getIdaddress())) {
            throw new ApplicationException(responseMessageCreateDir.getMessageText());
        }

        return responseMessageCreateDir;
    }

    /**
     * Asigna datos faltantes obre dirección
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @author Gildardo Mora
     */
    private void asignarDatosSobreRequestDireccion(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud) {
        //Asignamos el tipo de direccion al drdireccion P:Principal
        requestCreaSolicitud.getDrDireccion().setDirPrincAlt("P");
        //Asignamos el estado GEO de la direccion al drdireccion
        String estadoDir = Optional.of(requestCreaSolicitud).map(RequestCreaSolicitud::getCityEntity).map(CityEntity::getEstadoDir).orElse("");
        if (StringUtils.isBlank(estadoDir)) {
            requestCreaSolicitud.getDrDireccion().setEstadoDirGeo(estadoDir);
        }

        //Asignamos el estrato GEO de la direccion al drdireccion
        String estratoDir = Optional.of(requestCreaSolicitud).map(RequestCreaSolicitud::getCityEntity).map(CityEntity::getEstratoDir).orElse("");
        if (StringUtils.isNotBlank(estratoDir)) {
            requestCreaSolicitud.getDrDireccion().setEstrato(requestCreaSolicitud.getCityEntity().getEstratoDir());
        }
    }

    /**
     * Valida la información de la solicitud.
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param responseMessageCreateDir {@link ResponseMessage}
     * @param solicitudToCreate {@link Solicitud}
     * @throws ApplicationException Excepción personalizada de la App.
     * @author Gildardo Mora
     */
    private void validarInfoSolicitudConDireccion(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud,
            ResponseMessage responseMessageCreateDir, Solicitud solicitudToCreate) throws ApplicationException {

        BigDecimal dirDetalladaId = Optional.of(responseMessageCreateDir).map(ResponseMessage::getNuevaDireccionDetallada)
                .map(CmtDireccionDetalladaMgl::getDireccionDetalladaId).orElse(null);

        if (Objects.isNull(dirDetalladaId)) {
            throw new ApplicationException("No fue posible obtener el valor de la direccion detallada. Intente de nuevo por favor");
        }

        solicitudToCreate.setDireccionDetallada(responseMessageCreateDir.getNuevaDireccionDetallada());
        if (solicitudToCreate.getTecnologiaId() == null) {
            throw new ApplicationException("No existe una tecnologia con abreviatura:" + requestCreaSolicitud.getTipoTecnologia());
        }

        //Validacion si existe una solicitud en curso para la unidad
        List<Solicitud> solicitudEnCursoList = solictudesHhppEnCurso(dirDetalladaId, requestCreaSolicitud.getIdCentroPoblado(),
                solicitudToCreate.getTecnologiaId().getBasicaId(), requestCreaSolicitud.getTipoAccionSolicitud());

        if (CollectionUtils.isNotEmpty(solicitudEnCursoList)) {
            throw new ApplicationException("Ya existe una solicitud en curso que se encuentra PENDIENTE.");
        }
    }

    /**
     * Verfíca si la ciudad en proceso de la solicitud es una de las declaradas como ciudades especiales.
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param centroPoblado {@link GeograficoPoliticoMgl}
     * @throws ApplicationException Excepción personalizada de la App.
     * @author Gildardo Mora
     */
    private void validarSiCiudadEsEspecial(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud, GeograficoPoliticoMgl centroPoblado) throws ApplicationException {
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        //validar si es una ciudad especial para solicitud DTH
        if (requestCreaSolicitud.getTipoTecnologia().equalsIgnoreCase(Constant.TIPO_SOLICITUD_DTH)) {
            List<ParametrosMgl> ciudadesEspecialList = parametrosMglManager.findByAcronimo(Constant.CIUDADES_ESPECIALES);
            if (CollectionUtils.isNotEmpty(ciudadesEspecialList) && ciudadesEspecialList.get(0).getParValor().contains(centroPoblado.getGeoCodigoDane())) {
                throw new ApplicationException("No se puede crear el HHPP porque la ciudad es especial. ");
            }
        }
    }

    /**
     * Busca el tipo de solicitud para validar su existencia.
     *
     * @param requestCreaSolicitud {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param cmtBasicaMglManager {@link CmtBasicaMglManager}
     * @return {@link CmtBasicaMgl}
     * @throws ApplicationException Excepción personalizada de la App.
     * @author Gildardo Mora
     */
    private CmtBasicaMgl buscarTipoSolicitud(RequestCreaSolicitudTrasladoHhppBloqueado requestCreaSolicitud, CmtBasicaMglManager cmtBasicaMglManager) throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtTipoBasicaMgl tipoBasicaTipoAccion =  cmtTipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_ACCION_SOLICITUD);
        //obtiene listado de tipo de solicitud basica
        List<CmtBasicaMgl> tipoAccionSolicitudBasicaList = cmtBasicaMglManager.findByTipoBasica(tipoBasicaTipoAccion);

        if (CollectionUtils.isEmpty(tipoAccionSolicitudBasicaList)) {
            throw new ApplicationException(ConstantsSolicitudHhpp.MSG_TIPO_SOL_NO_VALIDO);
        }

        CmtBasicaMgl cmtBasicaMgl = tipoAccionSolicitudBasicaList.stream()
                .filter(basica -> basica.getCodigoBasica().equalsIgnoreCase(requestCreaSolicitud.getTipoAccionSolicitud()))
                .findFirst().orElse(null);

        if (Objects.isNull(Optional.ofNullable(cmtBasicaMgl).map(CmtBasicaMgl::getBasicaId).orElse(null))) {
            throw new ApplicationException("El tipo de solicitud ingresado no se encuentra configurado.");
        }

        return cmtBasicaMgl;
    }

    /**
     * Busca el usuario en la BD
     *
     * @param idUsuario {@link String}
     * @return {@link UsuariosServicesDTO}
     * @throws ApplicationException {@link ApplicationException} Excepción personalizada de la App.
     * @author Gildardo Mora
     */
    private UsuariosServicesDTO buscarUsuario(String idUsuario) throws ApplicationException {
        UsuariosServicesDTO usuario = null;

        if (StringUtils.isNotBlank(idUsuario)) {
            UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
            // Buscar al usuario a traves de la cédula.
            // Si no se encuentra, realiza la búsqueda por medio del parametro de VISOR.
            usuario = usuariosManager.consultaInfoUserPorCedulaVISOR(idUsuario);
        }

        if (Objects.isNull(usuario)) {
            throw new ApplicationException("El usuario no fue encontrado");
        }
        return usuario;
    }

    /**
     * Realiza la gestión de la solicitud de traslado de HHPP bloqueado
     *
     * @param solicitud        {@link Solicitud} Datos de la solicitud de traslado de HHPP bloqueado
     * @param solicitudCreated {@link Solicitud} Datos de solicitud que fue creada.
     * @param request          {@link RequestCreaSolicitudTrasladoHhppBloqueado} Datos de la petición.
     * @return Retorna true cuando se realizó correctamente la gestión de la solicitud
     * @author Gildardo Mora
     */
    public Map<String, Object> gestionarSolicitudTrasladoHhppBloquedo(Solicitud solicitud, Solicitud solicitudCreated,
            RequestCreaSolicitudTrasladoHhppBloqueado request) throws ApplicationException {
        try {
            Map<String, Object> resultadoGestion = new HashMap<>();
            /*Creación de HHPP virtual en RR y MER*/
            HhppMgl hhppMglVirtual = solicitud.getHhppMgl() != null ? solicitud.getHhppMgl() : solicitud.getHhppReal();
            HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
            Map<String, Object> resultCreateHhppVirtual = hhppVirtualMglManager.createHhppVirtual(request, hhppMglVirtual);
            boolean isHhppVirtualCreate = (boolean) resultCreateHhppVirtual.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
            String msgHhppCreate = String.valueOf(resultCreateHhppVirtual.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULT, ""));
            /* Gestión para cerrar la solicitud traslado HHPP Bloqueado en curso */
            CmtTiempoSolicitudMgl tiempoSolicitudMgl;

            if (solicitudCreated.getTiempoSolicitudMgl() != null) {
                tiempoSolicitudMgl = solicitudCreated.getTiempoSolicitudMgl();
            } else if (solicitud.getTiempoSolicitudMgl() != null) {
                tiempoSolicitudMgl = solicitud.getTiempoSolicitudMgl();
            } else {
                tiempoSolicitudMgl = new CmtTiempoSolicitudMgl();
            }
            //Se guardan los tiempos consumidos por la solicitud
            tiempoSolicitudMgl.setTiempoGestion(ConstantsSolicitudHhpp.DEFAULT_TIME);
            tiempoSolicitudMgl.setTiempoEspera(getTiempo(solicitud.getFechaIngreso(), new Date()));
            tiempoSolicitudMgl.setTiempoTotal(getTiempo(solicitud.getFechaIngreso(), new Date()));
            solicitudCreated.setFechaCancelacion(new Date());
            solicitudCreated.setEstado(ConstantsSolicitudHhpp.SOL_FINALIZADO);
            solicitudCreated.setTiempoSolicitudMgl(tiempoSolicitudMgl);
            String msgRespuesta = isHhppVirtualCreate ? "HHPP VIRTUAL CREADO" : "NO SE CREO EL HHPP VIRTUAL";
            solicitudCreated.setRespuesta(msgRespuesta);
            solicitudCreated.setRptGestion(msgRespuesta);
            //Actualiza los datos de la solicitud
            SolicitudDaoImpl solicitudDao = new SolicitudDaoImpl();
            solicitudDao.update(solicitudCreated);
            tiempoSolicitudMgl.setArchivoSoporte("NA");//Para esta solicitud no requiere archivo de soporte
            //Actualiza los datos de tiempo sobre la solicitud
            CmtTiempoSolicitudMglManager cmtTiempoSolicitudMglManager = new CmtTiempoSolicitudMglManager();
            cmtTiempoSolicitudMglManager.update(tiempoSolicitudMgl, request.getUsuarioVt(), request.getPerfilVt());
            enviarCorreoSolicitudTrasladoHhppBloqueado(request, solicitudCreated, true);
            resultadoGestion.put(ConstantsSolicitudHhpp.RESULTADO_GESTION, isHhppVirtualCreate);
            resultadoGestion.put(ConstantsSolicitudHhpp.MSG_RESULTADO, msgHhppCreate);
            return resultadoGestion;// TODO: revisar forma de contemplar respuesta en caso de fallo

        } catch (ApplicationException e) {
            String msgError = "Error al gestionar la solicitud de traslado de HHPP bloqueado";
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Realiza el envío de correo con los datos de la solicitud al usuario.
     *
     * @param request {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param solicitudCreated {@link Solicitud}
     * @param isSolicitudGestionada {@code boolean}
     * @author Gildardo Mora
     */
    private void enviarCorreoSolicitudTrasladoHhppBloqueado(RequestCreaSolicitudTrasladoHhppBloqueado request, Solicitud solicitudCreated, boolean isSolicitudGestionada){
        try {
            UsuariosServicesDTO usuario;
            if (Objects.isNull(request) || StringUtils.isBlank(request.getIdUsuario())) {
                throw new ApplicationException("El id de usuario recibido es nulo");
            }

            UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
            // Realiza la búsqueda por medio del parametro de VISOR.
            usuario = usuariosManager.consultaInfoUserPorCedulaVISOR(request.getIdUsuario());
            if (Objects.isNull(usuario)) {
                throw new ApplicationException("Los datos de usuario son nulos");
            }

            GeograficoPoliticoMgl centroPobladoDireccion = request.getCentroPobladoDireccion();
            GeograficoPoliticoMgl ciudadDireccion = request.getCiudadDireccion();
            GeograficoPoliticoMgl departamentoDireccion = request.getDepartamentoDireccion();
            //DATOS PARA ENVIAR AL CORREO
            String tipoSolicitudNombre = "";
            CmtBasicaMgl tipoSolicitudBasica = obtenerTipoAccionSolicitud(solicitudCreated.getCambioDir());
            if (Objects.nonNull(tipoSolicitudBasica) && StringUtils.isNotBlank(tipoSolicitudBasica.getNombreBasica())) {
                tipoSolicitudNombre = tipoSolicitudBasica.getNombreBasica();
            }

            if (!isSolicitudGestionada) {
                StringBuffer msgCorreo = mensajeCorreoSolicitud(solicitudCreated, tipoSolicitudNombre, request.getDepartamentoDireccion().getGpoNombre(),
                        request.getCiudadDireccion().getGpoNombre(), request.getCentroPobladoDireccion().getGpoNombre(), request.getAddressDir());
                MailSender.send(usuario.getEmail(), "", "", "Creacion solicitud de Hhpp", msgCorreo);
                return;
            }

            String subjet = "Gestion solicitud de Hhpp";
            String emailDestinatario = StringUtils.isNotBlank(solicitudCreated.getCorreoCopia()) ? solicitudCreated.getCorreoCopia() : "";
            //obtiene el estrato de la direccion
            String estrato = validarEstrato(request.getDrDireccion());
            String email = solicitudCreated.getCorreo() != null ? solicitudCreated.getCorreo() : "";
            StringBuffer msgCorreo = mensajeCorreoGestionSolicitud(solicitudCreated, tipoSolicitudNombre, departamentoDireccion.getGpoNombre(),
                    ciudadDireccion.getGpoNombre(), centroPobladoDireccion.getGpoNombre(), estrato,
                    solicitudCreated.getRespuesta(), solicitudCreated.getRptGestion(), request.getUsuarioVt(), request.getUnidadesList());
            // Envio de correo a solicitante
            MailSender.send(emailDestinatario, email, "", subjet, msgCorreo);

        } catch (ApplicationException ae) {
            String msgError = "Error en el envío del correo de gestión de la solicitud" + ae.getMessage();
            LOGGER.error(msgError);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
