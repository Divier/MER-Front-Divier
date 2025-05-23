package co.com.telmex.catastro.util;

import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase FacesUtil
 *
 * @author Jose Luis Caicedo
 * @version	1.0
 */
public class FacesUtil {

    private static final Logger LOGGER = LogManager.getLogger(FacesUtil.class);
    public static final String NO_FUE_ESPECIFICADO_NINGUN_MENSAJE_PARA_IMPRIMIR = "No fue especificado ningún mensaje para imprimir.";
    public static final String SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO = "Se produjo un error al momento de ejecutar el método '";

    /* ---------------------------------------- */

    private FacesUtil() {
        //Evitar instancias invalidas
    }

    /**
     *
     * @return
     */
    public static ServletContext getServletContext() {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }

    /**
     *
     * @return
     */
    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    /**
     *
     * @return
     */
    public static HttpServletRequest getServletRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    /**
     *
     * @return
     */
    public static HttpServletResponse getServletResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }
    
    
    /**
     * Elimina de una URL la informaci&oacute;n relacionada con el 
     * protocolo (http:// o https://),
     * nombre de dominio y puerto.
     * @param fullRequestContextPath URL que contiene el path completo del request.
     * @return Para <i>http://dominio:7001/console</i>, el resultado es <i>/console</i>.
     * @throws ApplicationException 
     */
    public static String removeRequestContextPathPrefix(String fullRequestContextPath) throws ApplicationException {
        String requestURL = null;

        ExternalContext context =
                FacesContext.getCurrentInstance().getExternalContext();

        try {
            if (context != null) {
                requestURL = fullRequestContextPath;
                if (requestURL != null) {
                    // eliminar esquema (protocolo), en caso tal que exista.
                    if (context.getRequestScheme() != null) {
                        requestURL = requestURL.replace(context.getRequestScheme()+"://", "");
                    }
                    // eliminar dominio, en caso tal que exista.
                    if (context.getRequestServerName() != null) {
                        requestURL = requestURL.replace(context.getRequestServerName(), "");
                    }
                    // eliminar puerto, en caso tal que exista.
                    if (context.getRequestServerPort() > 0) {
                        requestURL = requestURL.replace(":"+context.getRequestServerPort(), "");
                    }
                }
            }
            
            return (requestURL);

        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
            ClassUtils.getCurrentMethodName(FacesUtil.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    
    /**
     * Obtiene la URL corta del Request Context Path
     * (A partir del nombre del contexto, incluyendo el "/" del inicio).
     * 
     * @return Request Context Path URL.
     * @throws ApplicationException
     */
    public static String getShortRequestContextPath() throws ApplicationException {
        String requestURL = null;

        ExternalContext context =
                FacesContext.getCurrentInstance().getExternalContext();

        try {
            if (context != null) {
                requestURL = context.getRequestContextPath();
                if (requestURL != null) {
                    // eliminar protocolo, dominio y puerto de la URL, en caso tal que existan.
                    requestURL = removeRequestContextPathPrefix(requestURL);
                }
            }
            
        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
            ClassUtils.getCurrentMethodName(FacesUtil.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e);
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
            ClassUtils.getCurrentMethodName(FacesUtil.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }

        return (requestURL);
    }
    
    
    /**
     * Obtiene la URL completa del Request Context Path
     * (Incluye protocolo, host, puerto, y context path).
     * 
     * @return Request Context Path URL.
     */
    public static String getFullRequestContextPath() {
        String requestURL;

        ExternalContext context =
                FacesContext.getCurrentInstance().getExternalContext();

        try {
            URL url =
                    new URL(context.getRequestScheme(),
                    context.getRequestServerName(),
                    context.getRequestServerPort(),
                    context.getRequestContextPath());

            requestURL = url.toString();
        } catch (MalformedURLException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
            ClassUtils.getCurrentMethodName(FacesUtil.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            requestURL = null;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +
            ClassUtils.getCurrentMethodName(FacesUtil.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            requestURL = null;
        }

        return (requestURL);
    }
    
    
    /**
     * Realiza una redirecci&oacute;n a la URL especificada.
     * @param url URL a la que se desea dirigir.
     * @throws ApplicationException 
     */
    public static void navegarAPagina(String url) throws ApplicationException {
        if (url != null && !url.isEmpty()) {
            try {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.
                        redirect(getShortRequestContextPath() + url.replace(getShortRequestContextPath(), ""));
            } catch (IOException e) {
                throw new ApplicationException("Error al navegar a la página: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Muestra un mensaje de error en pantalla, junto a un mensaje de Log.
     *
     * @param mensaje Mensaje a Mostrar.
     * @param e       Causa de la excepción (<i>opcional</i>).
     * @param logger  Logger (log4j2) (<i>opcional</i>).
     */
    public static void mostrarMensajeError(String mensaje, Throwable e, Logger logger) {
        if (Objects.isNull(mensaje)) {
            LogManager.getLogger(FacesUtil.class).error(NO_FUE_ESPECIFICADO_NINGUN_MENSAJE_PARA_IMPRIMIR);
            return;
        }

        String msg = delimitarMensaje(mensaje);
        // Imprimir el mensaje corto en un diálogo de la pantalla del usuario.
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
        registrarLog(mensaje, e, logger);
    }

    /**
     * Registrar el mensaje completo en el log.
     *
     * @param mensaje {@link String}
     * @param e       {@link Throwable}
     * @param logger  {@link Logger}
     */
    private static void registrarLog(String mensaje, Throwable e, Logger logger) {
        if (logger == null) logger = LogManager.getLogger(FacesUtil.class);

        if (e == null) {
            logger.error(mensaje);
            return;
        }

        mensaje = ApplicationException.replaceExceptionCode(mensaje, e);
        logger.error(mensaje, e);
    }

    /**
     * Delimita el contenido del mensaje que se va a mostrar al usuario
     *
     * @param mensaje {@link String}
     * @return {@link String}
     */
    private static String delimitarMensaje(String mensaje) {
        String delim = "(EX)\\d{3}(_)*[A-Za-z]*(:)*";// Expresión regular para usar como delimitador del mensaje.
        String[] tokens = mensaje.split(delim);
        return Arrays.stream(tokens)
                .filter(Objects::nonNull)
                .filter(token -> !token.isEmpty())
                .reduce((primero, ultimo) -> ultimo.trim())
                .orElse(mensaje);
    }

    /**
     * Muestra un mensaje de error en pantalla, junto a un mensaje de Log.
     * 
     * @param mensaje Mensaje a Mostrar.
     * @param e Causa de la excepci&oacute;n (<i>opcional</i>).
     */
    public static void mostrarMensajeError(String mensaje, Throwable e) {
        mostrarMensajeError(mensaje, e, LogManager.getLogger(FacesUtil.class));
    }
    
    /**
     * Mostrar mensaje FACES
     * @param mensaje
     * @deprecated usar {@link #mostrarMensajePopUp(MessageSeverityEnum, String)}
    */
    public static void mostrarMensaje(String mensaje) {
        if (mensaje != null) {
            //
            // Imprimir el mensaje corto en un dialogo de la pantalla del usuario.
            //
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, mensaje, ""));
        } else {
            LogManager.getLogger(FacesUtil.class).error(NO_FUE_ESPECIFICADO_NINGUN_MENSAJE_PARA_IMPRIMIR);
        }
    }
    /**
     * Mostrar mensaje FACES
     * @param mensaje
     * @deprecated usar {@link #mostrarMensajePopUp(MessageSeverityEnum, String)}
    */
    public static void mostrarMensajeWarn(String mensaje) {
        if (mensaje != null) {
            //
            // Imprimir el mensaje corto en un dialogo de la pantalla del usuario.
            //
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, mensaje, ""));
        } else {
            LogManager.getLogger(FacesUtil.class).error(NO_FUE_ESPECIFICADO_NINGUN_MENSAJE_PARA_IMPRIMIR);
        }
    }

    /**
     * Muestra mensajes de notificación en el popup al usuario.
     *
     * @param severity {@link MessageSeverityEnum} Nivel de severidad del mensaje.
     * @param msg      {@link String } Mensaje a mostrar en el popup.
     * @author Gildardo Mora
     */
    public static void mostrarMensajePopUp(MessageSeverityEnum severity, @NonNull String msg) {
        if (Objects.isNull(severity)) {
            LOGGER.warn("No se especificó el nivel de severidad del mensaje, se mostrará como advertencia.");
            severity = MessageSeverityEnum.WARN;
        }

        FacesContext currentInstance = FacesContext.getCurrentInstance();

        if (Objects.isNull(currentInstance)) {
            LOGGER.warn("No se pudo obtener la instancia de FacesContext.");
            return;
        }

        currentInstance.addMessage(null, new FacesMessage(severity.getSeverity(), delimitarMensaje(msg), ""));
    }
    
}
