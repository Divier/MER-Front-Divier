/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.ptlus;

import co.com.claro.mer.auth.portal.usuarios.IPortalUsuariosClient;
import co.com.claro.mer.auth.portal.usuarios.impl.PortalUsuariosClientImpl;
import co.com.claro.mer.dtos.request.service.ConsultaInfoRequestDto;
import co.com.claro.mer.dtos.request.service.ConsultaRolesPorAplicativoUsuarioRequestDto;
import co.com.claro.mer.dtos.response.service.ConsultaInfoResponseDto;
import co.com.claro.mer.dtos.response.service.ConsultaRolesResponseDto;
import co.com.claro.mer.dtos.response.service.InformacionUsuarioDto;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.cm.UsPerfilManager;
import co.com.claro.mgl.businessmanager.cm.UsuariosManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.UsArea;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.jpa.entities.ptlus.UsuariosPortal;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static co.com.claro.mer.utils.constants.MicrositioConstants.FLAG_LEGACY_MICRO_SITIO;


/**
 * Administrador del servicio de Usuarios.
 * 
 * @author Victor Bocanegra (<i>bocanegravm</i>).
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class UsuariosServicesManager {

    private static final String SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO = "Se produjo un error al momento de ejecutar el método '";
    private static final Logger LOGGER = LogManager.getLogger(UsuariosServicesManager.class);
    private UsuariosPortalManager usuariosPortalManager;
    private UsuariosManager usuariosManager;

    /** 
     * Mapa que contiene la informaci&oacute;n del usuario, teniendo como llave 
     * a la c&eacute;dula o el nombre de usuario.
     */
    private static Map<String, UsuariosServicesDTO> usuariosMap = new HashMap<>();
    
    /** &Uacute;nica instancia de la clase. */
    private static UsuariosServicesManager instance;
    
    
    /**
     * Obtiene la &uacute;nica instancia de la clase.
     * 
     * @return Instancia de UsuariosServicesManager.
     * @throws ApplicationException 
     */
    public static UsuariosServicesManager getInstance() throws ApplicationException {
        if (instance == null) {
            instance = new UsuariosServicesManager();
        }
        
        return (instance);
    }
    

    /**
     * Constructor.
     *
     */
    private UsuariosServicesManager() {
        usuariosPortalManager = new UsuariosPortalManager();
        usuariosManager = new UsuariosManager();
    }

    private String findBaseUri() throws ApplicationException {
        try {
            String baseUri = ParametrosMerUtil.findValor(ParametrosMerEnum.BASE_URI_REST_USUARIOS.getAcronimo());
            baseUri = baseUri.endsWith("/") ? baseUri :  baseUri + "/";

            if (StringUtils.isBlank(baseUri)) {
                throw new ApplicationException("No se encuentra configurado el parámetro BASE_URI_REST_USUARIOS.");
            }

            return baseUri;
        } catch (NoSuchElementException e) {
            String msgError = "No se encuentra configurado el parámetro BASE_URI_REST_USUARIOS.";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError);
        } catch (Exception ex) {
            String msg = "Error al momento de ejecutar el método: '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se produjo un error al momento de inicializar el administrador de usuarios: " + ex.getMessage(), ex);
        }
    }

    /**
     * M&eacute;todo para realizar la consulta de un usuario por identificador 
     * del usuario.
     *
     * @param usuario Identificador del usuario a consultar.
     * @return UsuariosServicesDTO Informaci&oacute;n del Usuario consultado.
     * @throws ApplicationException Excepcion lanzada al realizar la consulta.
     */
    public UsuariosServicesDTO consultaInfoUserPorUsuario (String usuario)
            throws ApplicationException {
        return ( this.consultaInfoUser(null, usuario) );
    }

    /**
     * M&eacute;todo para realizar la consulta de un usuario por c&eacute;dula.
     *
     * @param cedula C&eacute;dula del usuario a consultar.
     * @return UsuariosServicesDTO Informaci&oacute;n del Usuario consultado.
     * @throws ApplicationException Excepcion lanzada al realizar la consulta.
     */
    public UsuariosServicesDTO consultaInfoUserPorCedula (String cedula)
            throws ApplicationException {
        return ( this.consultaInfoUser(cedula, null) );
    }

    /**
     * M&eacute;todo para realizar la consulta de un usuario por c&eacute;dula.
     *
     * @param cedula C&eacute;dula del usuario a consultar.
     * @return UsuariosServicesDTO Informaci&oacute;n del Usuario consultado.
     * @throws ApplicationException Excepcion lanzada al realizar la consulta.
     */
    public UsuariosServicesDTO consultaInfoUserPorCedula (BigDecimal cedula)
            throws ApplicationException {
        String cedulaStr = cedula != null ? cedula.toString() : null;
        return ( this.consultaInfoUser(cedulaStr, null) );
    }

    /**
     * Método para realizar la consulta de un usuario, bien sea a través
     * de la <i>cédula</i>, o por medio del <i>identificador</i> del mismo.
     *
     * @param cedula Cédula del usuario a consultar (<i>OPCIONAL</i>).
     * @param usuario Identificador del usuario (Nombre de usuario en el LDAP) (<i>OPCIONAL</i>).
     * @return UsuariosServicesDTO Información del usuario.
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    private UsuariosServicesDTO consultaInfoUser(String cedula, String usuario) throws ApplicationException {
        UsuariosServicesDTO usuariosServicesDTO;

        if (StringUtils.isBlank(cedula) && StringUtils.isBlank(usuario)) {
            String message = "Se está intentando realizar una consulta de usuario sin la información del mismo.";
            LOGGER.error(message);
            throw new ApplicationException(message);
        }

        try {
            Optional<UsuariosServicesDTO> usuarioInCache = getUsuariosServicesDTO(cedula, usuario);
            if (usuarioInCache.isPresent()) {
                return usuarioInCache.get();
            }

            IPortalUsuariosClient portalUsuariosClient = new PortalUsuariosClientImpl(findBaseUri());
            ConsultaInfoRequestDto requestConsultaInfo = new ConsultaInfoRequestDto();

            if (StringUtils.isNotBlank(usuario)) {
                requestConsultaInfo.setUsuario(usuario);
            } else if (StringUtils.isNotBlank(cedula)) {
                requestConsultaInfo.setCedula(cedula);
            }

            Optional<ConsultaInfoResponseDto> response = portalUsuariosClient.consultaInfo(requestConsultaInfo);
            Optional<InformacionUsuarioDto> infoUsuario = response
                    .map(ConsultaInfoResponseDto::getInformacionUsuario)
                    .map(info -> info.get(0));

            if (!infoUsuario.isPresent()) {
                throw new ApplicationException(defineErrorUsuario(requestConsultaInfo));
            }

            Optional<String> idPerfilUsuario = findPerfilUsuario(infoUsuario.get(), portalUsuariosClient);

            if (!idPerfilUsuario.isPresent()) {
                throw new ApplicationException("No se encontró el perfil del usuario.");
            }

            usuariosServicesDTO = new UsuariosServicesDTO(infoUsuario.get().getCedula(),
                    infoUsuario.get().getNombre(), infoUsuario.get().getTelefono(),
                    infoUsuario.get().getEmail(), infoUsuario.get().getDireccion(),
                    infoUsuario.get().getUsuario(), new BigDecimal(idPerfilUsuario.get()),
                    infoUsuario.get().getCodigoPerfil(),infoUsuario.get().getDescripcionPerfil(),
                    StringUtils.isNotBlank(infoUsuario.get().getIdArea()) ? new BigInteger(infoUsuario.get().getIdArea()) : null,
                    infoUsuario.get().getDescripcionArea());

            if (cedula != null) {
                usuariosMap.put(cedula, usuariosServicesDTO);
            } else {
                usuariosMap.put(usuario, usuariosServicesDTO);
            }
            return usuariosServicesDTO;
        } catch (ApplicationException e) {
            String flagLegacyMicrositio = ParametrosMerUtil.findValor(FLAG_LEGACY_MICRO_SITIO);
            boolean isLegacyAccessMicrositio = StringUtils.isNotBlank(flagLegacyMicrositio) && flagLegacyMicrositio.equals("1");

            if (isLegacyAccessMicrositio) { //NOTE: Esta consulta será removida en futuras modificaciones
                return findUserDb(cedula, usuario, e.getMessage());
            }

            String msg = "Error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw e;
        } catch (Exception ex) {
            String msg = "ocurrió un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener el usuario: " + ex.getMessage(), ex);
        }
    }

    /**
     * Busca el identificador del perfil del usuario.
     * @param infoUsuario Información del usuario.
     * @param portalUsuariosClient Cliente del servicio de PortalUsuarios.
     * @return Identificador del perfil del usuario.
     * @throws ApplicationException Excepción lanzada al buscar el perfil del usuario.
     * @author Gildardo Mora
     */
    private Optional<String> findPerfilUsuario(InformacionUsuarioDto infoUsuario, IPortalUsuariosClient portalUsuariosClient) throws ApplicationException {
        String nombreApp = ParametrosMerUtil.findValor(ParametrosMerEnum.APP_NAME.getAcronimo());
        //Consultar roles del usuario de portal usuarios
        ConsultaRolesPorAplicativoUsuarioRequestDto rolesRequest = new ConsultaRolesPorAplicativoUsuarioRequestDto(
                nombreApp, infoUsuario.getUsuario());
        Optional<ConsultaRolesResponseDto> rolesResponseDto = portalUsuariosClient.consultaRolesPorAplicativoUsuario(rolesRequest);
        Optional<String> idPerfilUsuario = rolesResponseDto.map(ConsultaRolesResponseDto::getIdPerfil)
                .filter(StringUtils::isNotBlank);

        if (!idPerfilUsuario.isPresent()) {
            idPerfilUsuario = findIdPerfilByCodPerfil(infoUsuario.getCodigoPerfil());
        }

        return idPerfilUsuario;
    }

    /**
     * Busca el identificador del perfil a través del código de perfil.
     * @implNote Se removerá en futuras modificaciones, debido a que se va a dejar solo consumos al servicio de PortalUsuarios.
     * @param codPerfil Código del perfil.
     * @return Identificador del perfil.
     * @author Gildardo Mora
     */
    private Optional<String> findIdPerfilByCodPerfil(String codPerfil) {
        if (StringUtils.isBlank(codPerfil)) {
            LOGGER.warn("No se encontró el código de perfil del usuario.");
            return Optional.empty();
        }

        try {
            // Buscar el perfil de base de datos a traves del codigo.
            UsPerfilManager usPerfilManager = new UsPerfilManager();
            UsPerfil usPerfil = usPerfilManager.findByCodigo(codPerfil);
            return Optional.ofNullable(usPerfil).map(UsPerfil::getIdPerfil).map(String::valueOf);
        } catch (ApplicationException e) {
            LOGGER.error("No se encontró la información del perfil {} : {}", codPerfil, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Verifica si el usuario existe en la respuesta de la consulta.
     * @param requestConsultaInfo Datos de la consulta.
     * @author Gildardo Mora
     */
    private String defineErrorUsuario(ConsultaInfoRequestDto requestConsultaInfo) {
        String msgError = "No existe usuario con ";
        if (StringUtils.isNotBlank(requestConsultaInfo.getCedula())) {
            msgError += "identificación No. " + requestConsultaInfo.getCedula();
        } else if (StringUtils.isNotBlank(requestConsultaInfo.getUsuario())) {
            msgError += "identificador " + requestConsultaInfo.getUsuario();
        } else {
            msgError += "información recibida.";
        }

        return msgError;
    }

    /**
     * Consulta la información del usuario en el mapa caché de usuarios consultados.
     *
     * @param cedula Cédula del usuario.
     * @param usuario Identificador del usuario.
     * @return Información del usuario.
     * @author Gildardo Mora
     */
    private Optional<UsuariosServicesDTO> getUsuariosServicesDTO(String cedula, String usuario) {
       Optional<UsuariosServicesDTO> usuariosServicesDTO = Optional.empty();

        if (StringUtils.isNotBlank(cedula)) {
            usuariosServicesDTO = Optional.ofNullable(usuariosMap.get(cedula));
        } else if (StringUtils.isNotBlank(usuario)) {
            usuariosServicesDTO = Optional.ofNullable(usuariosMap.get(usuario));
        }

        return usuariosServicesDTO;
    }

    /**
     * Obtiene la informaci&oacute;n del usuario a trav&eacute;s de la c&eacute;dula.
     * Primero realiza la b&uacute;squeda por medio de la c&eacute;dula como par&aacute;metro,
     * de no encontrarse, se realiza la b&uacute;squeda con la c&eacute;dula 
     * configurada para <b>VISOR</b> en los par&aacute;metros de <i>MGL</i>.
     * 
     * @param cedula C&eacute;dula del usuario.
     * @return Informaci&oacute;n del usuario.
     * @throws ApplicationException 
     */
    public UsuariosServicesDTO consultaInfoUserPorCedulaVISOR(String cedula) 
            throws ApplicationException {
        UsuariosServicesDTO usuario = null;
        
        try {
            if (cedula != null && !cedula.isEmpty()) {
                try {
                    usuario 
                        = this.consultaInfoUserPorCedula(cedula);
                } catch (ApplicationException ex) {
                    LOGGER.warn("Se buscará el usuario en VISOR, debido a: " + ex.getMessage());
                    usuario = null;
                }
            }

            if (usuario == null) {
                // Buscar el usuario configurado para VISOR.
                ParametrosMglManager parametrosManager = new ParametrosMglManager();
                ParametrosMgl usuarioVisorDefault =
                        parametrosManager.findParametroMgl(co.com.claro.mgl.utils.Constant.USUARIO_VISOR_DEFAULT);

                if (usuarioVisorDefault != null
                        && usuarioVisorDefault.getParValor() != null
                        && !usuarioVisorDefault.getParValor().isEmpty()) {

                    usuario
                        = this.consultaInfoUserPorCedula(usuarioVisorDefault.getParValor());

                    if (usuario == null) {
                        throw new ApplicationException("El usuario configurado en VISOR (" + usuarioVisorDefault.getParValor() + ") no fue encontrado.");
                    }

                } else {
                    throw new ApplicationException("El usuario por defecto para la aplicación VISOR no se encuentra configurado correctamente.");
                }
            }
        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw e;
        } catch (Exception e) {
            String msg = "Error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener el usuario: " + e.getMessage(), e);
        }
        
        return (usuario);
    }

    /**
     * Realiza la búsqueda del usuario en la base de datos, primero en la usuarios portal y luego en la de usuarios.
     * @param cedula Cédula del usuario a buscar.
     * @param usuario Usuario a buscar.
     * @param msgError Mensaje de error.
     * @return {@link UsuariosServicesDTO}
     * @throws ApplicationException Excepción lanzada al buscar el usuario en la base de datos.
     */
    private UsuariosServicesDTO findUserDb(String cedula, String usuario, String msgError) throws ApplicationException {
        UsuariosServicesDTO usuariosServicesDTO;

        try {
            // BASE DE DATOS: Portal Usuarios.
            // ESQUEMA: GESTIONNEW
            // TABLA: PTLUS_USUARIOS_PORTAL

            String userIdentifier = cedula != null ? cedula : usuario;
            LOGGER.info("Reintentando la obtención del usuario {} a través de GESTIONNEW.PTLUS_USUARIOS_PORTAL.", userIdentifier);
            usuariosServicesDTO = this.getUsuarioFromDBUsuariosPortal(cedula, usuario);

            if (usuariosServicesDTO == null) {
                String msg;
                if (cedula != null) {
                    msg = "No se obtuvo información en la base de datos del portal, por medio de la identificación No. "
                            + cedula + ".";
                } else {
                    msg = "No se obtuvo información en la base de datos del portal, por medio del identificador '"
                            + usuario + "'.";
                }

                LOGGER.error(msg);
                throw new ApplicationException(msg);
            }

            return usuariosServicesDTO;
        } catch (ApplicationException e) {
            LOGGER.error("Error consultando usuario: {}", e.getMessage());

            try {
                // BASE DE DATOS: Portal Usuarios.
                // ESQUEMA: GESTIONNEW
                // TABLA: USUARIOS
                String userIdentifier = cedula != null ? cedula : usuario;
                LOGGER.info("Reintentando la obtención del usuario {} a través de GESTIONNEW.USUARIOS.", userIdentifier);
                return getUsuarioFromDBUsuarios(cedula, usuario);

            } catch (ApplicationException ex) {
                String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException("Ocurrió un error al momento de consultar el usuario: "
                        + msgError + " / " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Obtiene la informaci&oacute;n del usuario a trav&eacute;s de la
     * Base de Datos del Portal Usuarios.<br/>
     *
     * <br/><b>BASE DE DATOS: </b> Portal Usuarios.
     * <br/><b>ESQUEMA: </b> <i>GESTIONNEW</i>.
     * <br/><b>TABLA: </b> <i>PTLUS_USUARIOS_PORTAL</i>.
     *
     * @param cedula C&eacute;dula del usuario a consultar.
     * @param usuario Identificador del usuario a consultar.
     * @return Informaci&oacute;n del Usuario.
     * @throws ApplicationException
     */
    private UsuariosServicesDTO getUsuarioFromDBUsuariosPortal(String cedula, String usuario)
            throws ApplicationException {
        UsuariosServicesDTO usuariosServicesDTO = null;

        try {
            UsuariosPortal usuarioPortal = null;

            if (usuariosPortalManager == null) {
                throw new ApplicationException("No se encuentra inicializado el administrador de usuarios del portal.");
            }

            if (cedula != null) {
                usuarioPortal = usuariosPortalManager.findUsuarioPortalByCedula(cedula);
            } else if (usuario != null) {
                usuarioPortal = usuariosPortalManager.findUsuarioPortalByUsuario(usuario);
            }

            if (usuarioPortal == null) {
                return usuariosServicesDTO;
            }

            String descArea = null;
            BigInteger codArea = null;
            String descPerfil = null;
            String codPerfil = null;

            if (usuarioPortal.getPerfil() != null) {
                codPerfil = usuarioPortal.getPerfil().getCodPerfil();
                descPerfil = usuarioPortal.getPerfil().getDescripcion();
                UsArea area = usuarioPortal.getPerfil().getArea();
                if (area != null) {
                    if (area.getIdArea() != null) {
                        codArea = new BigInteger(area.getIdArea().toString());
                    }
                    descArea = area.getAreaNombre();
                }
            }

            usuariosServicesDTO =
                    new UsuariosServicesDTO(usuarioPortal.getCedula(),
                            usuarioPortal.getNombre(), usuarioPortal.getTelefono(),
                            usuarioPortal.getEmail(), usuarioPortal.getDireccion(),
                            usuarioPortal.getUsuario(), usuarioPortal.getIdPerfil(),
                            codPerfil, descPerfil, codArea, descArea);
        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
                    ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw e;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
                    ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se produjo un error al momento de obtener el usuario: " +
                    e.getMessage(), e);
        }

        return usuariosServicesDTO;
    }

    /**
     * Obtiene la informaci&oacute;n del usuario a trav&eacute;s de la
     * Base de Datos del Portal Usuarios.<br/>
     *
     * <br/><b>BASE DE DATOS: </b> Portal Usuarios.
     * <br/><b>ESQUEMA: </b> <i>GESTIONNEW</i>.
     * <br/><b>TABLA: </b> <i>USUARIOS</i>.
     *
     * @param cedula C&eacute;dula del usuario a consultar.
     * @param usuario Identificador del usuario a consultar.
     * @return Informaci&oacute;n del Usuario.
     * @throws ApplicationException Excepción lanzada al obtener el usuario de la base de datos.
     */
    private UsuariosServicesDTO getUsuarioFromDBUsuarios(String cedula, String usuario)
            throws ApplicationException {
        UsuariosServicesDTO usuariosServicesDTO = null;

        try {
            Usuarios usuarios = null;

            if (usuariosManager == null) {
                throw new ApplicationException("No se encuentra inicializado el administrador de usuarios.");
            }

            if (cedula != null) {
                usuarios = usuariosManager.findUsuarioById(new BigDecimal(cedula));
            } else if (usuario != null) {
                usuarios = usuariosManager.findUsuarioByUsuario(usuario);
            }

            if (usuarios != null) {
                String descArea = null;
                BigInteger codArea = null;
                String descPerfil = null;
                String codPerfil = null;
                BigDecimal idPerfil = null;

                if (usuarios.getPerfil() != null) {
                    idPerfil = usuarios.getPerfil().getIdPerfil();
                    codPerfil = usuarios.getPerfil().getCodPerfil();
                    descPerfil = usuarios.getPerfil().getDescripcion();
                    UsArea area = usuarios.getPerfil().getArea();
                    if (area != null) {
                        if (area.getIdArea() != null) {
                            codArea = new BigInteger(area.getIdArea().toString());
                        }
                        descArea = area.getAreaNombre();
                    }
                }

                usuariosServicesDTO =
                        new UsuariosServicesDTO(usuarios.getIdUsuario().toString(),
                                usuarios.getNombre(), usuarios.getTelefono(),
                                usuarios.getEmail(), usuarios.getDireccion(),
                                usuarios.getUsuario(), idPerfil, codPerfil, descPerfil,
                                codArea, descArea);
            }
        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
                    ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw e;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
                    ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se produjo un error al momento de obtener el usuario: " +
                    e.getMessage(), e);
        }

        return (usuariosServicesDTO);
    }

}
