/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.servlet;

import co.com.claro.mer.auth.micrositio.dto.UserDataDto;
import co.com.claro.mer.auth.micrositio.facade.GetUserInfoFacadeLocal;
import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mer.parametro.facade.ParametroFacadeLocal;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.UsuariosFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.utils.CmtUtilidadesAgenda;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static co.com.claro.mer.utils.constants.MicrositioConstants.*;


/**
 *  Clase para exponer el servlet del login del Visor factibilidad
 *
 * @author edisonjpg
 */
@WebServlet("/view/MGL/loginVisorfactibilidad/")
public class LoginVisorfactibilidadServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(LoginVisorfactibilidadServlet.class);
    public static final String URL_DE_REDIRECT_FACTIBILADES = "/view/coberturas/consultaCoberturasV1Externo.xhtml";

    @EJB
    private GetUserInfoFacadeLocal getUserInfoFacadeLocal;
    @EJB
    private UsuariosFacadeLocal usuariosFacadeLocal;
    @EJB
    private ParametroFacadeLocal parametroFacadeLocal;
    @Inject
    private ExceptionServBean exceptionServBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;
        HttpSession session = null;

        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("utf-8");
            out = response.getWriter();
            HttpSession sessionAnt = request.getSession(false);

            if (sessionAnt != null) {
                sessionAnt.invalidate();
            }

            String consulta = request.getParameter("consulta");
            String token = request.getParameter("token");
            session = request.getSession();

            if (consulta == null || token == null) {
                session.setAttribute(ERROR_ATTRIBUTE, 1);
                redirectTo(response, request.getContextPath() + URL_DE_RESPUESTA_ERROR);
                return;
            }

            consulta = consulta.replace(" ", "+");
            String cadena = CmtUtilidadesAgenda.Desencriptar(consulta);
            String[] datosCadena = cadena.split("\\|");
            LOGGER.debug("ENTRANDO A CONSULTAR USUARIO-------");
            String flagLegacyMicro = ParametrosMerUtil.findValor(FLAG_LEGACY_MICRO_SITIO);//1 encendido, 0 apagado
            boolean isLegacyAccessMicrositio = StringUtils.isNotBlank(flagLegacyMicro) && flagLegacyMicro.equals("1");
            Optional<UserDataDto> user;

            if (isLegacyAccessMicrositio) {
                user = findLegacyUserVisor(datosCadena[0]);
            } else {
                String nombreApp = parametroFacadeLocal.findParameterValueByAcronym(ParametrosMerEnum.MICROSITIO_APP_NAME.getAcronimo());
                user = getUserInfoFacadeLocal.consultarUsuario(datosCadena[0], nombreApp);
            }

            if (!user.isPresent()) {
                String mensaje = "No se encontró el usuario con el id: " + datosCadena[0];
                LOGGER.error(mensaje);
                exceptionServBean.notifyError(new Exception(mensaje), mensaje, "Micrositio visor factibilidad");
                session.setAttribute(ERROR_ATTRIBUTE, 1);
                redirectTo(response, request.getContextPath() + URL_DE_RESPUESTA_ERROR);
                return;
            }

            LOGGER.debug("ENTRANDO A CONSULTAR ROLES-------");
            session.setAttribute("flagMicro", true);
            boolean isRolFacMap = user.get().isViewMapRol();
            //Guardo el token en una variable de sesion
            session.setAttribute("token", token);
            //Guardo si puede o no ver mapas en una variable de sesion
            session.setAttribute("verMapa", isRolFacMap);
            String seguridad = token + "|" + user.get().getTelefono() + "|" + isRolFacMap + "|" + datosCadena[0] + "|"
                    + user.get().getIdPerfil() + "|" + user.get().getNombreUsuario()
                    + "|" + user.get().getEmailUsuario() + "|" + user.get().getIdentificacion();
            String security = CmtUtilidadesAgenda.Encriptar(seguridad);
            session.setAttribute("cookieXml", security);
            //Redirijo al modulo de factibilidad
            redirectTo(response, request.getContextPath() + URL_DE_REDIRECT_FACTIBILADES + "?security=" + security + "&consulta=" + consulta);

        } catch (Exception ex) {
            exceptionServBean.notifyError(ex, ex.getMessage(), "Micrositio visor factibilidad");

            if (session != null) {
                session.setAttribute(ERROR_ATTRIBUTE, 5);
                session.setAttribute(MENSAJE_EXEPCION, ex.getMessage());
            }

            redirectTo(response, request.getContextPath() + URL_DE_RESPUESTA_ERROR);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Mapea un usuario a un UserDataDto
     */
    private final Function<Usuarios, UserDataDto> mapUserToUserDataDto = user -> {
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setNombreUsuario(user.getNombre());
        userDataDto.setEmailUsuario(user.getEmail());
        userDataDto.setIdUsuario(String.valueOf(user.getIdUsuario()));
        BigDecimal idPerfil = Optional.of(user).map(Usuarios::getPerfil)
                .map(UsPerfil::getIdPerfil)
                .orElse(BigDecimal.ZERO);
        userDataDto.setIdPerfil(idPerfil.intValue());
        userDataDto.setAccessRol(true);
        userDataDto.setViewMapRol(false);
        userDataDto.setTelefono(user.getTelefono());
        userDataDto.setIdentificacion(String.valueOf(user.getIdUsuario()));
        return userDataDto;
    };

    /**
     * Método que redirecciona a una página
     *
     * @param response Objeto de respuesta
     * @param url     URL de la página a la que se redireccionará
     * @author Gildardo Mora
     */
    private void redirectTo(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOGGER.error("Error al redireccionar a la página de respuesta: {} ", url, e);
        }
    }

    /**
     * Busca un usuario en la base de datos
     * @implNote Este proceso será removido en próximos ajustes para usar solo el servicio de portal de usuarios.
     * @param user Usuario a buscar
     * @return Optional<UserDataDto>
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    private Optional<UserDataDto> findLegacyUserVisor(String user) throws ApplicationException {
        LOGGER.debug("ENTRANDO A CONSULTAR USUARIO..");
        Usuarios userBd = usuariosFacadeLocal.findUsuarioByUsuario(user);

        if (userBd == null) {
            return Optional.empty();
        }

        boolean isRolFacMap = false;
        UserDataDto userDataDto = mapUserToUserDataDto.apply(userBd);
        userDataDto.setAccessRol(true);
        userDataDto.setViewMapRol(isRolFacMap);
        return Optional.of(userDataDto);
    }

}
