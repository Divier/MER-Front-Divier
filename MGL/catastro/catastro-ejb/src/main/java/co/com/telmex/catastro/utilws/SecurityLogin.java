/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.utilws;

import co.com.claro.mer.dtos.response.service.RolPortalResponseDto;
import co.com.claro.mer.dtos.response.service.UsuarioPortalResponseDto;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.utils.ClassUtils;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Metodo encargado de validar la secion desde Visistas Tecnicas
 *
 * @author Admin
 */
public class SecurityLogin {

    private HttpSession session;
    @Getter
    private boolean login = true;
    private HttpServletRequest request;
    private static final Logger LOGGER = LogManager.getLogger(SecurityLogin.class);
    private String loginUser;
    private String idUser;
    private String idSolSelected;
    private String cookie;
    private ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
    public static String SERVER_PARA_AUTENTICACION; //"http://172.31.2.176:8204/";
    private boolean usarLogin;
    private boolean validacionTokenHabilitada;
    private boolean validacionRol;

    /**
     * *
     * Constructor
     *
     * @param facesContext
     * @throws IOException
     */
    public SecurityLogin(FacesContext facesContext) throws IOException {

        try {
            parametrosMglManager = new ParametrosMglManager();
            SERVER_PARA_AUTENTICACION
                    = parametrosMglManager.findByAcronimo(
                            co.com.telmex.catastro.services.util.Constant.BASE_URI_LOG_IN_MGL)
                            .iterator().next().getParValor();
            
            if (SERVER_PARA_AUTENTICACION != null 
                    && !SERVER_PARA_AUTENTICACION.isEmpty()) {
                
                // En caso tal que se configure como un Path del mismo contexto
                // Se adiciona el protocolo, puerto y contexto al valor configurado
                // en el parámetro. Sino, se deja la URL intacta.
                if (!SERVER_PARA_AUTENTICACION.toLowerCase().contains("http")) {
                    ExternalContext context = facesContext.getExternalContext();

                    try {
                        URL url =
                                new URL(context.getRequestScheme(),
                                context.getRequestServerName(),
                                context.getRequestServerPort(),
                                context.getRequestContextPath());

                        SERVER_PARA_AUTENTICACION = url.toString() 
                                + SERVER_PARA_AUTENTICACION;
                    } catch (MalformedURLException e) {
                        LOGGER.error("Error construyendo la URL asociada al parámetro '" 
                                + co.com.telmex.catastro.services.util.Constant.BASE_URI_LOG_IN_MGL 
                                + "': " + e.getMessage(), e);
                        SERVER_PARA_AUTENTICACION = null;
                    }
                }
            } else {
                LOGGER.error("No se encuentra configurado el parámetro '" 
                        + co.com.telmex.catastro.services.util.Constant.BASE_URI_LOG_IN_MGL + "'.");
            }
            
            List<ParametrosMgl> listParam
                    = parametrosMglManager.findByAcronimo(
                            co.com.telmex.catastro.services.util.Constant.FEATURE_FLAG_LOG_IN_MGL);
            
            List<ParametrosMgl> listParamRoles = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.FLAG_VALIDACION_ROLES.getAcronimo());

            if (listParam != null && !listParam.isEmpty()) {
                usarLogin = Boolean.valueOf(listParam
                        .iterator().next().getParValor());
            } else {
                usarLogin = true;
                LOGGER.error("No se encuentra configurado el parámetro '" 
                        + co.com.telmex.catastro.services.util.Constant.FEATURE_FLAG_LOG_IN_MGL + "'.");
            }
            if (listParamRoles != null && !listParamRoles.isEmpty()) {
                validacionRol = Boolean.valueOf(listParamRoles
                        .iterator().next().getParValor());
            } else {
                validacionRol = true;
                LOGGER.error("No se encuentra configurado el parámetro {}",
                        ParametrosMerEnum.FLAG_VALIDACION_ROLES.getAcronimo());
            }
            
            validacionTokenHabilitada = Boolean.valueOf(parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.LOG_IN_TOKEN_VALIDATION_ENABLED)
                    .iterator().next().getParValor());

        } catch (ApplicationException ex) {
            LOGGER.error("Error al llamar la URL: " + ex.getMessage(), ex);
        }
        this.request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String relativeUrl = Optional.ofNullable(request)
                .map(HttpServletRequest::getRequestURI)
                .orElse("");

        if (relativeUrl.contains("/view/MGL/CM/respuestaPostFactibilidades.xhtml")){
            LOGGER.debug("Se redirecciona a la página de respuesta de factibilidades.");
            return;
        }

        validateLogin(false);
    }

    public String redireccionarLogin() {
        return SERVER_PARA_AUTENTICACION;
    }

    /**
     * *
     *
     * @param request
     * @throws IOException
     */
    public SecurityLogin(HttpServletRequest request) throws IOException {
        this.request = request;
        String urlreturnVT = this.request.getParameter("adss");
        boolean validar = false;
        if (!urlreturnVT.equalsIgnoreCase("NA")) {
            validar = true;
        }
        validateLogin(validar);
    }

    /**
     * *
     * Metodo En cargado de realizar validaacion de sesion
     */
    private void validateLogin(boolean validad) {
        if (this.request == null){
            LOGGER.info("El request HTTP es null");
            return;
        }

        this.session = this.request.getSession();
        Object objSession = session.getAttribute("loginUserSecurity");
        String usuarioActivo = null;
        if(objSession!=null){
            usuarioActivo = session.getAttribute("loginUserSecurity").toString();
        }

        if (!usarLogin &&  StringUtils.isBlank(usuarioActivo)) {
            //Asignamos hora local del cliente:
            Date dateServer = new Date();
            java.util.Date dates = new java.util.Date();
            long fechaSis = dates.getTime();
            Date datePc = new Date(fechaSis);
            long deltaTime = dateServer.getTime() - datePc.getTime();
            session.setAttribute("deltaTiempo", deltaTime);
        }

            idSolSelected = this.request.getParameter("idSolSelected");
            // Para las peticiones tipo POST.
            String loginSource = (String) session.getAttribute("loginSource");

            if (loginSource != null && loginSource.equals("servlet")) {
                obtenerYasignarDatosSesionDeAgendamiento();
            }

            if (this.session.getAttribute("cookieXml") == null && !validad){
                this.login = false;
                return;
            }

                if (session.getAttribute("loginUserSecurity") != null) {
                    loginUser = (String) session.getAttribute("loginUserSecurity");
                } else if (request.getParameter("home") != null) {
                    loginUser = request.getParameter("home");
                }

                if (StringUtils.isNotBlank(loginUser)) {
                    session.setAttribute("loginUserSecurity", loginUser);
                }

                idUser = this.request.getParameter("idUser");
                session.setAttribute("idSolSelected", idSolSelected);
                String verificaVT = this.request.getParameter("vlid");
                String urlreturnVT = this.request.getParameter("adss");

        asignarDatosCookieYatributosSesion(urlreturnVT);

        if (!usarLogin) {//flag de validación
            LOGGER.info("No se usara el login en mgl");
            return;
        }

        validarToken();
    }

    /**
     * Realiza la asignación de los atributos de la sesión.
     *
     * @param urlreturnVT {@link String}
     */
    private void asignarDatosCookieYatributosSesion(String urlreturnVT) {
        if (this.session.getAttribute("cookieXml") != null) {
            cookie = (String) this.session.getAttribute("cookieXml");
        } else if (this.request.getParameter("cookieXml") != null) {
            cookie = this.request.getParameter("cookieXml");
        }

        if (StringUtils.isNotBlank(cookie)) {
            session.setAttribute("cookieXml", cookie);
            session.setAttribute("loginUserSecurity", loginUser);
        } else {
            this.login = false;
        }

        if (StringUtils.isNotBlank(urlreturnVT)) {
            session.setAttribute("urlVisitasTecnicas", urlreturnVT);
        }
    }

    /**
     * Realiza la validación del token de la sesión.
     */
    private void validarToken() {
        try {
            String usuarioSesion = (String) session.getAttribute("loginUserSecurity");

            if (!validacionTokenHabilitada) {
                LOGGER.debug("La validación de TOKEN se encuentra desactivada.");
                return;
            }

            String tokenSesion = (String) session.getAttribute("token");
            String tokenExp = (String) session.getAttribute("tokenExp");

            if (StringUtils.isBlank(tokenExp)) {
                this.login = false;
                session.invalidate();
                return;
            }

            verificarTokenYfechasParainvalidarSesion(usuarioSesion, tokenSesion, tokenExp);

        } catch (ApplicationException ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método {} {}",
                    ClassUtils.getCurrentMethodName(this.getClass()), ex.getMessage(), ex);
        }
    }

    /**
     * Realiza la validación del estado de la sesion, y las fechas de expiración
     * para invalidar la sesión.
     *
     * @param usuarioSesion {@link String}
     * @param tokenSesion   {@link String}
     * @param tokenExp      {@link String}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private void verificarTokenYfechasParainvalidarSesion(String usuarioSesion, String tokenSesion, String tokenExp) throws ApplicationException {
        try {
            SimpleDateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date fechaExp = formatter.parse(tokenExp.substring(0, 24));
            Timestamp horaSystema = new Timestamp(System.currentTimeMillis());
            Date fechaSys = new Date(horaSystema.getTime());

            if (fechaSys.after(fechaExp)) {
                this.login = false;
                session.invalidate();
            }

        } catch (ParseException ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método {} {}",
                    ClassUtils.getCurrentMethodName(this.getClass()), ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene la data de sesión proveniente del módulo de agendamiento
     */
    private void obtenerYasignarDatosSesionDeAgendamiento() {
        try {
            session.removeAttribute("loginSource");
            // Se obtiene la data proveniente de agendamiento.
            this.addDataOnSessionAgendamiento();
        } catch (ApplicationException ex) {
            String msgError = "Se produjo un error al momento de adicionar la data de sesión proveniente"
                    + " del módulo de agendamiento, al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    /**
     * @return the loginUser
     */
    public String getLoginUser() {
        loginUser = (String) session.getAttribute("loginUserSecurity");
        return loginUser;
    }

    /**
     * @param loginUser the loginUser to set
     */
    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getIdUser() {
        idUser = String.valueOf(session.getAttribute("idUser"));
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdSolSelected() {
        return idSolSelected;
    }

    public void setIdSolSelected(String idSolSelected) {
        this.idSolSelected = idSolSelected;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public boolean usuarioTieneRoll(String roll) throws ApplicationException {
        boolean tieneRoll = true;
        // cuando la app no tiene el login activado el login retorne true
        if(usarLogin && !validacionRol){
            return tieneRoll;
        }
        String valoresUsuarioLogeado = (String) session.getAttribute("cookieXml");
        org.w3c.dom.Document doc;
        NodeList nList;
        String roles;
        try {
            if (valoresUsuarioLogeado != null) {
                doc = (org.w3c.dom.Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(valoresUsuarioLogeado)));
                nList = doc.getElementsByTagName("ROLES");
                roles = nList.item(0).getTextContent();
                if (roles.toUpperCase().contains("/" + roll.toUpperCase())
                        || roles.toUpperCase().contains(roll.toUpperCase() + "/")) {
                    tieneRoll = true;
                } else {
                    tieneRoll = false;
                }
            } else {
                tieneRoll = false;
                LOGGER.error("No se encontraron cookies.");
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .{} {}",
                    ClassUtils.getCurrentMethodName(this.getClass()), e.getMessage());
            throw new ApplicationException("Error en usuarioTieneRoll. ", e);
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error al momento de ejecutar el método .{} {}",
                    ClassUtils.getCurrentMethodName(this.getClass()), e.getMessage());
            throw new ApplicationException("Error en usuarioTieneRoll. ", e);
        }
        return tieneRoll;
    }

    /**
     * Retorna el perfil del usuario en sesión.
     *
     * @return {@code int} Retorna el perfil del usuario en sesión.
     */
    public int getPerfilUsuario() {
        String valoresUsuarioLogeado = (String) session.getAttribute("cookieXml");
        org.w3c.dom.Document doc;
        NodeList nList;
        int perfil = 0;
        try {
           if (Objects.isNull(valoresUsuarioLogeado)){
               LOGGER.warn("Los valores del usuario logueado son nulos, no se puede obtener el perfil.");
               return perfil;
           }

            doc = (org.w3c.dom.Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(valoresUsuarioLogeado)));
            nList = doc.getElementsByTagName("PERFIL");
            perfil = Integer.parseInt(nList.item(0).getTextContent());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'{} : {}'",
                    ClassUtils.getCurrentMethodName(this.getClass()),  ex.getMessage());
        }
        return perfil;
    }
    
    
    /**
     * Obtiene el objeto asociado al response del servicio de
     * autenticaci&oacute;n.
     *
     * @return Instancia de <i>Usuarios</i>.
     */
    public UsuarioPortalResponseDto getUsuario() {
        return ((UsuarioPortalResponseDto) session.getAttribute("user"));
    }
    
    
    /**
     * Adiciona la información requerida en sesión, con la data
     * proveniente del login a través del módulo de Agendamiento.
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void addDataOnSessionAgendamiento() throws ApplicationException {

        UsuarioPortalResponseDto usuarioSesion = new UsuarioPortalResponseDto();
        List<RolPortalResponseDto> listRoles = new ArrayList<>();
        FacesContext fContext = FacesContext.getCurrentInstance();
        String respuestaXml = null;
        
        if (session != null) {
            // Se busca el parámetro de SESIÓN.
            respuestaXml = (String) session.getAttribute("cookie");
        }

        if (StringUtils.isBlank(respuestaXml)
                && fContext != null && fContext.getExternalContext() != null) {
            // Si no se encuentra en sesión, se obtiene el parámetro a través de POST.
            Map<String,String> requestParams =
                    fContext.getExternalContext().getRequestParameterMap();
            respuestaXml = requestParams.get("cookie");
        }

        if (respuestaXml == null || session == null) {
            throw new ApplicationException("No fue enviada la información de autenticación. No es posible ingresar al sistema. ");
        }

            session.removeAttribute("cookie");

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

                    NodeList loggeo = element.getElementsByTagName("USUARIO");
                    line = (Element) loggeo.item(0);
                    login = getCharacterDataFromElement(line);


                    NodeList per = element.getElementsByTagName("PERFIL");
                    line = (Element) per.item(0);
                    perfil = getCharacterDataFromElement(line);

                    NodeList name = element.getElementsByTagName("NOMBRE");
                    line = (Element) name.item(0);
                    nombre = getCharacterDataFromElement(line);

                    NodeList roles = element.getElementsByTagName("ROLES");
                    line = (Element) roles.item(0);
                    String rolesUsu = getCharacterDataFromElement(line);
                    String[] partesRoles = rolesUsu.split("/");

                    if (partesRoles != null && partesRoles.length > 0) {

                        for (String s : partesRoles) {
                            RolPortalResponseDto usRolPerfil = new RolPortalResponseDto();
                            usRolPerfil.setCodRol(s);
                            listRoles.add(usRolPerfil);
                        }
                    }
                }

                usuarioSesion.setIdUsuario(cedula);
                usuarioSesion.setNombre(nombre);
                usuarioSesion.setIdPerfil(perfil);
                usuarioSesion.setUsuario(login);
                usuarioSesion.setListRoles(listRoles);

                session.setAttribute("idUser", usuarioSesion.getIdUsuario());
                session.setAttribute("deltaTiempo", "0");
                session.setAttribute("cookieXml", respuestaXml);
                session.setAttribute("loginUserSecurity", login);
                session.setAttribute("user", usuarioSesion);

                Date dateServer = new Date();
                java.util.Date dates = new java.util.Date();
                long fechaSis = dates.getTime();
                Date datePc = new Date(fechaSis);
                long deltaTime = dateServer.getTime() - datePc.getTime();
                session.setAttribute("deltaTiempo", deltaTime);


            } catch (ParserConfigurationException | SAXException | IOException pe) {
                throw new ApplicationException("Error al momento de registrar la información de sesión en agendamiento: " + pe.getMessage());
            } catch (Exception e) {
                throw new ApplicationException("Error al momento de registrar la información de sesión en agendamiento: " + e.getMessage());
            }
    }
    
    
    private String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public boolean isValidacionRol() {
        return validacionRol;
    }

    public void setValidacionRol(boolean validacionRol) {
        this.validacionRol = validacionRol;
    }
    
}
