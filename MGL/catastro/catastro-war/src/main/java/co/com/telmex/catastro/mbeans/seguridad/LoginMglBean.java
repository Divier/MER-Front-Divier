/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.mbeans.seguridad;

import co.com.claro.mer.dtos.response.service.RolPortalResponseDto;
import co.com.claro.mer.dtos.response.service.UsuarioPortalResponseDto;
import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.login.facade.LoginFacadeLocal;
import co.com.claro.mer.utils.constants.SessionConstants;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.mbeans.cm.menu.MenuCmBean;
import co.com.claro.mgl.mbeans.vt.menu.MenuVtBean;
import co.com.telmex.catastro.data.DatosSesionMgl;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static co.com.claro.mer.utils.constants.SessionConstants.PERFIL_SESION;
import static co.com.claro.mer.utils.constants.SessionConstants.PORTAL_LOGIN;
import static co.com.claro.mgl.utils.Constant.USER_RR;
import static co.com.claro.mgl.utils.Constant.USER_SESION;

/**
 * Bean para gestionar inicio y cierre de sesión al aplicativo.
 *
 * @author User
 */
@ManagedBean
@SessionScoped
@Log4j2
public class LoginMglBean extends BaseMBean {
    @Getter
    @Setter
    private String usuario;
    @Getter
    @Setter
    private String passw;
    @Getter
    @Setter
    private UsuarioPortalResponseDto usuarioMgl;
    @Getter
    @Setter
    private DatosSesionMgl datosSesion;
    private HttpServletRequest httpRequest ;
    private HttpSession httpSession ;
    private FacesContext fContext ;
    private ParametrosMglManager parametrosMglManager;
    private String urlMain;
    private String urlLogin;
    @Getter
    @Setter
    private String respuestaXml;
    /* ------------------------------------ */
    @EJB
    private LoginFacadeLocal loginFacade;
    @Inject
    private ExceptionServBean exceptionServBean;

    /* ------------------------------------ */

    @PostConstruct
    public void init(){
        httpRequest = FacesUtil.getServletRequest();
        HttpSession sesionAnt = httpRequest.getSession(false);
        fContext = FacesContext.getCurrentInstance();
        parametrosMglManager= new ParametrosMglManager();
        if (sesionAnt != null) {
            sesionAnt.invalidate();
        }
        httpSession = httpRequest.getSession();
    }

    public LoginMglBean(){
        parametrosMglManager = new ParametrosMglManager();
        actualizarParametros();
    }

    private void actualizarParametros(){
        try {
            List<ParametrosMgl> parametrosUrl = parametrosMglManager.findByAcronimo(Constant.BASE_URI_MAIN_LOG_IN);
            urlMain = parametrosUrl.stream().map(ParametrosMgl::getParValor).findFirst().orElse(null);

            if (StringUtils.isNotBlank(urlMain)) {
                if (!urlMain.toLowerCase().contains("http")) {
                    // Adicionar el path completo.
                    urlMain = FacesUtil.getFullRequestContextPath() + urlMain;
                }
            } else {
                LOGGER.error("No se encuentra parametrizada la propiedad BASE_URI_MAIN_LOG_IN.");
            }

            List<ParametrosMgl> parametrosUriLog = parametrosMglManager.findByAcronimo(Constant.BASE_URI_LOG_IN_MGL);
            urlLogin = parametrosUriLog.stream().map(ParametrosMgl::getParValor).findFirst().orElse(null);

            if (urlLogin != null && !urlLogin.isEmpty()) {
                if (!urlLogin.toLowerCase().contains("http")) {
                    // Adicionar el path completo.
                    urlLogin = FacesUtil.getFullRequestContextPath() + urlLogin;
                }
            } else {
                LOGGER.error("No se encuentra parametrizada la propiedad BASE_URI_LOG_IN_MGL.");
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizar Parametros" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en actualizar Parametros" + e.getMessage(), e, LOGGER);
        }
    }

    public void validar() {
        try {
            if (httpSession.getAttribute("token") != null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext extContext = facesContext.getExternalContext();
                extContext.redirect(urlMain);
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al validar la información del usuario: " + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al validar la información del usuario: " + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Permite iniciar sesión
     */
    public void iniciarSesion() {
        try {
            Optional<String> errorValidacion = validarCredenciales();
            if (errorValidacion.isPresent()) {
                procesarError(errorValidacion.get());
                return;
            }

            Optional<DatosSesionMgl> sesionOptional = loginFacade.authenticateUser(usuario, passw);

            this.datosSesion = sesionOptional.orElse(null);
            this.usuarioMgl = sesionOptional.map(DatosSesionMgl::getUsuarioMgl).orElse(null);
            String estadoSesion = sesionOptional.map(DatosSesionMgl::getEstadoSesion)
                    .orElse("Error de autenticación. Intente nuevamente.");

            if (usuarioMgl == null) {
                procesarError(estadoSesion);
                return;
            }

            if ("OK_SESSION".equals(estadoSesion)) {
                procesarSesionExitosa();
                return;
            }

            String mensajeFormateado = formatearMensajeError(estadoSesion);
            procesarError(mensajeFormateado);
            LOGGER.error("Error al iniciar sesión: {}", mensajeFormateado);

        } catch (ApplicationException ae) {
            FacesUtil.mostrarMensajeError(ae.getMessage(), ae, LOGGER);
            message = ae.getMessage();
            datosSesion = null;
            LOGGER.error("Error al iniciar sesión: {}", ae.getMessage(), ae);
        } catch (Exception ex) {
            String mensaje = "Se produjo un error al momento de iniciar sesión: " + ex.getMessage();
            FacesUtil.mostrarMensajeError(mensaje, ex, LOGGER);
            message = mensaje;
            datosSesion = null;
            LOGGER.error("Error al iniciar sesión: {}", mensaje, ex);
        }
    }

    /**
     * Validar las credenciales de usuario
     */
    private Optional<String> validarCredenciales() {
        if (StringUtils.isBlank(usuario) && StringUtils.isBlank(passw)) {
            return Optional.of("Usuario y contraseña sin ingresar, por favor intente de nuevo");
        }

        if (StringUtils.isBlank(usuario)) {
            return Optional.of("No ha ingresado el usuario, por favor intente de nuevo.");
        }

        if (StringUtils.isBlank(passw)) {
            return Optional.of("No ha ingresado la contraseña, por favor intente de nuevo.");
        }

        return Optional.empty();
    }

    /**
     * Procesa una sesión autenticada exitosamente
     */
    private void procesarSesionExitosa() throws ApplicationException, IOException {
        addDataOnSession();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        cargarPermisosMenu(facesContext);
        CompletableFuture.runAsync(this::registerLogin);
        redireccionarAPaginaPrincipal(facesContext);
    }

    /**
     * Formatea un mensaje de error capitalizando la primera letra de cada palabra
     */
    private String formatearMensajeError(String mensaje) {
        return Arrays.stream(mensaje.split("\\s+"))
                .map(palabra -> StringUtils.isEmpty(palabra) ? palabra :
                        Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1))
                .collect(Collectors.joining(DelimitadorEnum.ESPACIO.getDelimitador()));
    }

    /**
     * Redirecciona a la página principal después del login exitoso
     */
    private void redireccionarAPaginaPrincipal(FacesContext facesContext) throws IOException {
        ExternalContext extContext = facesContext.getExternalContext();

        if (extContext.isResponseCommitted()) {
            try {
                extContext.responseReset();
            } catch (Exception e) {
                LOGGER.error("No se pudo limpiar el buffer: {}", e.getMessage());
            }
        }

        if (!facesContext.getResponseComplete() && !extContext.isResponseCommitted()) {
            extContext.redirect(urlMain);
            LOGGER.info("Redireccionado a página principal.");
            facesContext.responseComplete();
        } else {
            LOGGER.warn("No se pudo redirigir porque la respuesta ya está comprometida");
        }
    }

    /**
     * Procesa un mensaje de error durante el inicio de sesión
     */
    private void procesarError(String mensaje) {
        message = mensaje;
        datosSesion = null;
        asignarMsgError(mensaje);
    }

    /**
     * Registra el login del usuario en el historial de sesiones.
     *
     * @author Gildardo Mora
     */
    private void registerLogin() {
        try {
            loginFacade.registerUserLogin(datosSesion);
        } catch (Exception e) {
            String msgError = "Error al registrar la sesión del usuario en el historial de sesiones : " + e.getMessage();
            exceptionServBean.notifyError(e, msgError, "LOGIN");
        }
    }

    /**
     * Realiza el carga de los permisos del menu
     *
     * @param facesContext {@link FacesContext}
     * @author Gildardo Mora
     */
    private void cargarPermisosMenu(FacesContext facesContext) {
        try {
            MenuCmBean menuBean = facesContext.getApplication()
                    .evaluateExpressionGet(facesContext, "#{menuCmBean}", MenuCmBean.class);
            menuBean.cargaPermisosMenu();

            MenuVtBean menuBeanVt = facesContext.getApplication()
                    .evaluateExpressionGet(facesContext, "#{menuVtBean}", MenuVtBean.class);
            menuBeanVt.cargaPermisosMenu();
        } catch (Exception e) {
            LOGGER.error("Error al cargar los permisos del menu: {}", e.getMessage(), e);
        }
    }

    /**
     * Asigna el mensaje de error a la vista del login
     *
     * @param message {@link String} Mensaje de error
     * @author Gildardo Mora
     */
    private void asignarMsgError(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
    }

    private void addDataOnSession() throws ApplicationException {
        if (datosSesion == null) {
            throw new ApplicationException("No se encontraron datos de sesión.");
        }

        UsuarioPortalResponseDto usuarioSesion = datosSesion.getUsuarioMgl();
        StringBuilder roles = new StringBuilder();

        if (usuarioSesion == null) {
            throw new ApplicationException("No se encontró usuario.");
        }

        if (CollectionUtils.isNotEmpty(usuarioSesion.getListRoles())) {
            for (int i = 0; i < usuarioSesion.getListRoles().size(); i++) {
                roles.append("/").append(usuarioSesion.getListRoles().get(i).getCodRol());
            }
        }
                // Portal usuario, origen sera portal usuario
                // set lo que llegue de portal usuario y validar para llenar la Cookie
                // validacion para mirar pod donde entra ()
                // donde y como dispara el servicio, redirector que dispara el logeo
                //

               String cookie = "<XUSUARIOS><XUSUARIO>"
                        + "<IDUSUARIO>" + usuarioSesion.getIdUsuario() + "</IDUSUARIO>"
                        + "<USUARIO>" + usuario + "</USUARIO>"
                        + "<PERFIL>" + usuarioSesion.getIdPerfil() + "</PERFIL>"
                        + "<NOMBRE>" + usuarioSesion.getNombre() + "</NOMBRE>"
                        + "<ROLES>" + roles + "</ROLES>"
                        + "<TIPOSTRABAJO></TIPOSTRABAJO>"
                        + "<CIUDADES>BOG</CIUDADES>"
                        + "<ALIADOS></ALIADOS>"
                        + "<ALIADO>hitss</ALIADO>"
                        + "<PARAMETROS></PARAMETROS>"
                        + "</XUSUARIO></XUSUARIOS>";


                if(httpSession.getAttribute(SessionConstants.COOKIE) != null){
                    httpSession.setAttribute(SessionConstants.COOKIE,"");
                }

                if(httpSession.getAttribute(SessionConstants.COOKIE_XML) != null){
                    httpSession.setAttribute(SessionConstants.COOKIE_XML,"");
                }
                httpSession.setAttribute("idUser", usuarioSesion.getIdUsuario());
                httpSession.setAttribute(SessionConstants.DELTA_TIEMPO, "0");
                httpSession.setAttribute(SessionConstants.COOKIE_XML, cookie);
                httpSession.setAttribute(SessionConstants.COOKIE, cookie);
                httpSession.setAttribute("loginUserSecurity", usuario);
                httpSession.setAttribute("user", usuarioSesion);
                httpSession.setAttribute("token", datosSesion.getTokenSession());
                httpSession.setAttribute("tokenExp", datosSesion.getFechaExpiracionToken());
                httpSession.setAttribute(PERFIL_SESION, usuarioSesion.getIdPerfil());
                httpSession.setAttribute(PORTAL_LOGIN, "PU");
                httpSession.setAttribute(USER_SESION, usuarioSesion.getUsuario());
                httpSession.setAttribute(USER_RR, usuarioSesion.getUsuarioRr());
                httpSession.setAttribute("email", usuarioSesion.getEmail());
                httpSession.setAttribute(SessionConstants.LISTA_ROLES, usuarioSesion.getListRoles());

                Date dateServer = new Date();
                java.util.Date dates = new java.util.Date();
                long fechaSis = dates.getTime();
                Date datePc = new Date(fechaSis);
                long deltaTime = dateServer.getTime() - datePc.getTime();
                httpSession.setAttribute(SessionConstants.DELTA_TIEMPO, deltaTime);
    }


    /**
     * Adiciona la informaci&oacute;n requerida en sesi&oacute;n, con la data 
     * proveniente del login a trav&eacute;s del m&oacute;dulo de Agendamiento.
     */
    public void addDataOnSessionAgendamiento() {
        UsuarioPortalResponseDto usuarioSesion = new UsuarioPortalResponseDto();
        List<RolPortalResponseDto> listRoles = new ArrayList<>();

        if (respuestaXml == null) {
            if (httpSession != null) {
                // Se busca el parámetro de SESIÓN.
                respuestaXml = (String) httpSession.getAttribute(SessionConstants.COOKIE);
            }

            if (StringUtils.isBlank(respuestaXml)
                    && fContext != null && fContext.getExternalContext() != null) {
                // Si no se encuentra en sesión, se obtiene el parámetro a través de POST.
                Map<String,String> requestParams = fContext.getExternalContext().getRequestParameterMap();
                respuestaXml = requestParams.get(SessionConstants.COOKIE);
            }
        }

        if (respuestaXml == null) {
            FacesUtil.mostrarMensajeError("No fue enviada la información de autenticación. No es posible ingresar al sistema. " , null, LOGGER);
            return;
        }

            httpSession.removeAttribute(SessionConstants.COOKIE);
            String cedula = "";
            String login = "";
            String perfil = "";
            String nombre = "";

            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(respuestaXml));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("XUSUARIO");

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList id = element.getElementsByTagName("IDUSUARIO");
                    Element line = (Element) id.item(0);
                    cedula = getCharacterDataFromElement(line);
                    LOGGER.debug("identificacion: {}", cedula);

                    NodeList loggeo = element.getElementsByTagName("USUARIO");
                    line = (Element) loggeo.item(0);
                    login = getCharacterDataFromElement(line);

                    LOGGER.debug("login: {}", login);
                    NodeList per = element.getElementsByTagName("PERFIL");
                    line = (Element) per.item(0);
                    perfil = getCharacterDataFromElement(line);
                    LOGGER.debug("perfil: {}", perfil);

                    NodeList name = element.getElementsByTagName("NOMBRE");
                    line = (Element) name.item(0);
                    nombre = getCharacterDataFromElement(line);
                    LOGGER.debug("nombre: {}", nombre);

                    NodeList roles = element.getElementsByTagName("ROLES");
                    line = (Element) roles.item(0);
                    String rolesUsu = getCharacterDataFromElement(line);
                    String[] partesRoles = rolesUsu.split("/");

                        for (String s : partesRoles) {
                            RolPortalResponseDto usRolPerfil = new RolPortalResponseDto();
                            usRolPerfil.setCodRol(s);
                            listRoles.add(usRolPerfil);
                        }
                }

                usuarioSesion.setIdUsuario(cedula);
                usuarioSesion.setNombre(nombre);
                usuarioSesion.setIdPerfil(perfil);
                usuarioSesion.setUsuario(login);
                usuarioSesion.setListRoles(listRoles);

                httpSession.setAttribute("idUser", usuarioSesion.getIdUsuario());
                httpSession.setAttribute(SessionConstants.DELTA_TIEMPO, "0");
                httpSession.setAttribute(SessionConstants.COOKIE_XML, respuestaXml);
                httpSession.setAttribute("loginUserSecurity", login);
                httpSession.setAttribute("user", usuarioSesion);

                Date dateServer = new Date();
                java.util.Date dates = new java.util.Date();
                long fechaSis = dates.getTime();
                Date datePc = new Date(fechaSis);
                long deltaTime = dateServer.getTime() - datePc.getTime();
                httpSession.setAttribute(SessionConstants.DELTA_TIEMPO, deltaTime);

                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext extContext = facesContext.getExternalContext();
                extContext.redirect(urlMain);
            } catch (ParserConfigurationException | SAXException | IOException pe) {
                FacesUtil.mostrarMensajeError("Error al momento de registrar la información de sesión en agendamiento: " + pe.getMessage() , pe, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Ocurrió un error al momento de registrar la información de sesión en agendamiento: " + e.getMessage() , e, LOGGER);
            }
    }

    /**
     * Realiza el cierre de sesión.
     */
    public void cerrarSesion() {

        actualizarParametros();

        usuario = "";
        passw = "";

        try {
            if (httpRequest != null && httpRequest.getCookies() != null) {
                for (Cookie cooky : httpRequest.getCookies()) {
                    cooky.setMaxAge(0);
                    cooky.setValue("");
                    FacesUtil.getServletResponse().addCookie(cooky);
                }
            }

            if (httpSession != null) {
                while (httpSession.getAttributeNames().hasMoreElements()) {
                    String nextElement = httpSession.getAttributeNames().nextElement();
                    httpSession.removeAttribute(nextElement);
                }
                httpSession.invalidate();
            }

            init();
            FacesContext.getCurrentInstance().getExternalContext().redirect(urlLogin + "?faces-redirect=true");
        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Error al cerrar sesión: " + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al cerrar sesión: " + ex.getMessage() , ex, LOGGER);
        }

    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

}