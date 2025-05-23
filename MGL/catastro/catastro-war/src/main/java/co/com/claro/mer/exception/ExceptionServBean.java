package co.com.claro.mer.exception;

import co.com.claro.mer.dtos.response.service.UsuarioPortalResponseDto;
import co.com.claro.mer.email.domain.models.enums.EmailTemplatesHtmlEnum;
import co.com.claro.mer.error.domain.models.dto.EmailErrorDto;
import co.com.claro.mer.error.facade.EmailErrorFacadeLocal;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service Bean encargado de gestionar los procesos de excepciones ocurridas en la aplicación.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/04/29
 */
@Named
@RequestScoped
@Log4j2
public class ExceptionServBean implements Serializable {

    @EJB
    private EmailErrorFacadeLocal emailErrorFacadeLocal;
    private String user = "NA";
    private String userName = "NA";

    @PostConstruct
    private void init() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            if (Objects.isNull(facesContext)) {
                LOGGER.error("No se puede obtener el contexto de JSF");
                return;
            }

            SecurityLogin securityLogin = new SecurityLogin(facesContext);

            if (!securityLogin.isLogin()) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
                return;
            }

            user = securityLogin.getLoginUser();
            userName = Optional.ofNullable(securityLogin.getUsuario()).map(UsuarioPortalResponseDto::getNombre).orElse(null);
        } catch (IOException e) {
            LOGGER.error("Error al crear la instancia de manejo de excepciones ", e);
        } catch (Exception e) {
            LOGGER.error("Error al inicializar ", e);
        }
    }

    /**
     * Notifica el error ocurrido al usuario, escribe el log
     * y envía un correo electrónico con los detalles del error al grupo de operaciones.
     * <p>
     * Debe ser llamado en el bloque catch de un try-catch. En las clases de tipo Bean
     * se debe inyectar la instancia de esta clase para poder utilizar este método.
     * La instancia se debe inyectar con la anotación @Inject.
     *  Ejemplo de uso:
     * <pre>
     *     try {
     *     // Código que puede lanzar una excepción
     *     } catch (Exception e) {
     *     exceptionServBean.notifyError(e, "Error al realizar la operación", "Nombre del módulo");
     *     }
     * </pre>
     * <p>
     *
     * @param exception {@link Throwable}
     * @param message {@link String} Mensaje de error
     * @param moduleName {@link String} Nombre del módulo donde ocurrió el error
     * @author Gildardo Mora
     */
    public void notifyError(@NonNull Throwable exception, @NonNull String message,
                            @NonNull String moduleName) {

        String uuid = Long.toString(Math.abs(UUID.randomUUID().getMostSignificantBits())).substring(0, 10);
        String methodName = getErrorSourceMethodName();
        String msg = String.format("[UUID: %S ] : [MÉTODO: %s] [ %s]", uuid, methodName, message);
        Optional<Throwable> appException = Optional.of(exception);
        ApplicationException appExceptionIntern = null;

        while ((appException.isPresent() && appException.get() instanceof ApplicationException)) {
            appExceptionIntern = (ApplicationException) appException.get();
            appException = Optional.ofNullable(appException.get().getCause());
        }

        if (appExceptionIntern != null && !appException.isPresent()) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, message);
            LOGGER.warn(msg, exception.getMessage());
            return;
        }

        LOGGER.error(msg, exception);
        // Mostrar mensaje de error en la pantalla
        FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.ERROR, "[UUID:" + uuid + "] : " + message);
        EmailErrorDto emailErrorDto = getEmailErrorDto((Exception) exception, moduleName, msg, uuid);
        // Utiliza ExecutorService para enviar el correo electrónico en un hilo separado
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> emailErrorFacadeLocal.sendEmailErrorDetails(emailErrorDto));
        executorService.shutdown();
    }

    /**
     * Crea un objeto EmailErrorDto con la información del error ocurrido.
     *
     * @param exception  {@link Exception} Excepción ocurrida
     * @param moduleName {@link String} Nombre del módulo donde ocurrió el error
     * @param msg        {@link String} Mensaje de error
     * @return {@link EmailErrorDto} Objeto con la información del error ocurrido
     * @author Gildardo Mora
     */
    private EmailErrorDto getEmailErrorDto(Exception exception, String moduleName, String msg, String uuid) {
        EmailErrorDto emailErrorDto = new EmailErrorDto();
        emailErrorDto.setException(exception);
        emailErrorDto.setUuid(uuid);
        emailErrorDto.setModule(moduleName);
        emailErrorDto.setSessionUser(user);
        emailErrorDto.setSessionUserName(userName);
        emailErrorDto.setMessage(msg);
        emailErrorDto.setTemplateHtml(EmailTemplatesHtmlEnum.ERROR_IN_PRESENTATION);
        return emailErrorDto;
    }

    /**
     * Obtiene el nombre del método que invoca la notificación de error.
     *
     * @return {@link String} Nombre del método que invoca la notificación de error
     * @author Gildardo Mora
     */
    private String getErrorSourceMethodName() {
        try {
            return Thread.currentThread().getStackTrace()[4].getMethodName();
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.error("Error al obtener el nombre del método que invoca la notificación de error.. ");
            return "";
        }
    }

}
