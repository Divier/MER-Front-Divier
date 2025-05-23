package co.com.claro.mer.utils;

import co.com.claro.mer.dtos.response.service.RolPortalResponseDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.CmtUtilidadesAgenda;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static co.com.claro.mer.utils.constants.SessionConstants.*;
import static co.com.claro.mgl.utils.Constant.USER_SESION;

/**
 * Utilitario para realizar operaciones sobre la sesión del usuario.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/30
 */
@Log4j2
public class SesionUtil {

    private SesionUtil() {
        //Impedir instancias invalidas.
    }

    /**
     * Obtiene el ID de perfil del usuario en sesión
     * @return {@link String} Perfil en sesión o cadena vacia si no hay perfil en sesión
     * @author Gildardo Mora
     */
    public static String getIdPerfil() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        return Optional.ofNullable(request)
                .map(HttpServletRequest::getSession)
                .map(sesion -> sesion.getAttribute(PERFIL_SESION))
                .map(Object::toString)
                .orElse("");
    }

    /**
     * Obtiene el usuario en sesión de acuerdo al tipo de login
     *
     * @return {@link String} Nombre de usuario en sesión o cadena vacia si no hay usuario en sesion
     * @author Manuel Hernández
     */
    public static String getUsuarioLogueado()  {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        if (Objects.isNull(request)) {
            LOGGER.warn("No se encontró el request en el contexto al momento de obtener el usuario logueado");
            return "";
        }

        String flagLogin = Optional.of(request)
                .map(HttpServletRequest::getSession)
                .map(sesion -> sesion.getAttribute(PORTAL_LOGIN))
                .map(Object::toString)
                .orElse("");

        if (StringUtils.isBlank(flagLogin)) {
            return "";
        }

        if (flagLogin.equals("PU") && request.getSession().getAttribute(USER_SESION) != null) {
            return request.getSession().getAttribute(USER_SESION).toString();
        }

        if (flagLogin.equals("US") && request.getSession().getAttribute("loginUserSecurity") != null) {
            return (String) request.getSession().getAttribute("loginUserSecurity");
        }

        return "";
    }

    public static List<RolPortalResponseDto> getRoles() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        @SuppressWarnings("unchecked")
        List<RolPortalResponseDto> roles = Optional.ofNullable(request)
                .map(HttpServletRequest::getSession)
                .map(sesion -> sesion.getAttribute(LISTA_ROLES))
                .map(x -> (List<RolPortalResponseDto>) x)
                .orElse(Collections.emptyList());

        return roles;
    }

    /**
     * Obtiene los datos del usuario de micrositio almacenados en la sesión
     *
     * @return {@link UsuariosServicesDTO} Datos del usuario
     * @throws ApplicationException Error al obtener los datos del usuario de micrositio
     * @author Gildardo Mora
     */
    public static UsuariosServicesDTO getUserDataMicroSitio() throws ApplicationException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest httpRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            String cookieXml = Optional.ofNullable(httpRequest)
                    .map(HttpServletRequest::getSession)
                    .map(sesion -> sesion.getAttribute("cookieXml"))
                    .map(Object::toString)
                    .orElseThrow(() -> new ApplicationException("No se encontró la cookieXml en la sesión"));

            String desencriptar = CmtUtilidadesAgenda.Desencriptar(cookieXml);
            desencriptar = desencriptar.replace("|", ":");
            String[] paramQueryString = desencriptar.split(":");
            UsuariosServicesDTO usuarioMicro = new UsuariosServicesDTO();
            usuarioMicro.setCedula(paramQueryString[7]);
            usuarioMicro.setNombre(paramQueryString[5]);
            usuarioMicro.setTelefono(paramQueryString[1]);
            usuarioMicro.setEmail(paramQueryString[6]);
            usuarioMicro.setUsuario(paramQueryString[3]);
            int idPerfilMicro = paramQueryString[4] != null ? Integer.parseInt(paramQueryString[4]) : 0;
            usuarioMicro.setIdPerfil(new BigDecimal(idPerfilMicro));
            return usuarioMicro;
        } catch (Exception e) {
            String msgError = "Error al obtener los datos del usuario de micrositio";
            throw new ApplicationException(msgError, e);
        }
    }

}
