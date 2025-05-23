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
import co.com.claro.mgl.businessmanager.cm.CmtOpcionesRolMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.UsRolPerfilFacadeLocal;
import co.com.claro.mgl.facade.cm.UsuariosFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import co.com.claro.mgl.jpa.entities.cm.UsRolPerfil;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.utils.CmtUtilidadesAgenda;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import org.apache.commons.collections4.CollectionUtils;
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
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static co.com.claro.mer.utils.constants.MicrositioConstants.*;


/**
 *
 * @author bocanegravm
 */
@WebServlet("/view/MGL/loginFactibilidades/")
public class LoginFactibilidadesServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(LoginFactibilidadesServlet.class);
    public static final String URL_DE_RESPUESTA_ERROR = "/view/MGL/CM/respuestaPostFactibilidades.xhtml";
    public static final String URL_DE_REDIRECT_FACTIBILADES = "/view/coberturas/consultaCoberturas.xhtml";
    private static final String ATTRIBUTE_ERROR = "error";

    @EJB
    private GetUserInfoFacadeLocal getUserInfoFacadeLocal;
    @EJB
    private UsuariosFacadeLocal usuariosFacadeLocal;
    @EJB
    private UsRolPerfilFacadeLocal usRolPerfilFacadeLocal;
    @EJB
    private ParametroFacadeLocal parametroFacadeLocal;
    @Inject
    private ExceptionServBean exceptionServBean;

    /**
     * Método encargado de procesar las peticiones tipo POST.
     *
     * @param request HTTP Servlet Request.
     * @param response HTTP Servlet Response.
     * @throws ServletException Excepción de Servlet.
     * @throws IOException Excepción de E/S.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = null;
        PrintWriter out = null;

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

            if (StringUtils.isBlank(consulta)|| StringUtils.isBlank(token)) {
                session.setAttribute(ATTRIBUTE_ERROR, 4);
                redirectTo(response, request.getContextPath() + URL_DE_RESPUESTA_ERROR);
                return;
            }

            String cadena = CmtUtilidadesAgenda.Desencriptar(consulta);
            String[] datosCadena = cadena.split("\\|");

            if (!isDatePostValid(datosCadena[1])) {
                session.setAttribute(ATTRIBUTE_ERROR, 0);
                redirectTo(response, request.getContextPath() + URL_DE_RESPUESTA_ERROR);
                return;
            }

            session.setAttribute("flagMicro", true);
            LOGGER.debug("ENTRANDO A CONSULTAR USUARIO-------");
            String flagLegacyMicro = ParametrosMerUtil.findValor(FLAG_LEGACY_MICRO_SITIO);//1 encendido, 0 apagado
            boolean isLegacyAccessMicrositio = StringUtils.isNotBlank(flagLegacyMicro) && flagLegacyMicro.trim().equals("1");
            Optional<UserDataDto> datosUsuario;

            if (isLegacyAccessMicrositio) {
                datosUsuario = findLegacyUser(datosCadena); //NOTE: Se removerá este metodo en futuros ajustes para usar solamente el servicio de portal de usuarios.
            } else {
                String nombreApp = parametroFacadeLocal.findParameterValueByAcronym(ParametrosMerEnum.MICROSITIO_APP_NAME.getAcronimo());
                datosUsuario = getUserInfoFacadeLocal.consultarUsuario(datosCadena[0], nombreApp);
            }

            if (!datosUsuario.isPresent()) {
                String msgError = String.format("No se encontró el usuario %s", datosCadena[0]);
                LOGGER.error(msgError);
                exceptionServBean.notifyError(new ApplicationException("Autenticación micrositio"), msgError, MICROSITIO);
                session.setAttribute(ATTRIBUTE_ERROR, 1);
                redirectTo(response, request.getContextPath() + URL_DE_RESPUESTA_ERROR);
                return;
            }

            if (!datosUsuario.get().isAccessRol()) {
                String msgError = String.format("El usuario %s no tiene rol de acceso a micrositio", datosCadena[0]);
                LOGGER.error(msgError);
                exceptionServBean.notifyError(new AccessDeniedException(msgError), msgError, MICROSITIO);
                session.setAttribute(ATTRIBUTE_ERROR, 2);
                redirectTo(response, request.getContextPath() + URL_DE_RESPUESTA_ERROR);
                return;
            }

            //Guardo el token en una variable de sesion
            session.setAttribute("token", token);
            //Guardo si puede o no ver mapas en una variable de sesion
            session.setAttribute("verMapa", datosUsuario.get().isViewMapRol());
            String seguridad = token + "|" + datosUsuario.get().getTelefono() + "|" + datosUsuario.get().isViewMapRol()
                    + "|" + datosCadena[0] + "|" + datosUsuario.get().getIdPerfil() + "|" + datosUsuario.get().getNombreUsuario()
                    + "|" + datosUsuario.get().getEmailUsuario() + "|" + datosUsuario.get().getIdentificacion();
            String security = CmtUtilidadesAgenda.Encriptar(seguridad);
            session.setAttribute("cookieXml", security);
            //Redirijo al modulo de factibilidad
            redirectTo(response, request.getContextPath() + URL_DE_REDIRECT_FACTIBILADES + "?security=" + security);
        } catch (ApplicationException | ParseException ex) {
            exceptionServBean.notifyError(ex, ex.getMessage(), MICROSITIO);
            LOGGER.error(ex.getMessage(), ex);

            if (session != null) {
                session.setAttribute(ATTRIBUTE_ERROR, 5);
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
     * Consulta un usuario en la base de datos.
     * @implSpec Se removerá este metodo en futuros ajustes para usar solamente el servicio de portal de usuarios.
     * @param datosCadena Datos de la cadena.
     * @return {@link Optional} de {@link UserDataDto}.
     * @throws ApplicationException Excepción de aplicación.
     * @author Gildardo Mora
     */
    private Optional<UserDataDto> findLegacyUser(String[] datosCadena) throws ApplicationException {
        Usuarios userDb = null;
        try {
            userDb = usuariosFacadeLocal.findUsuarioByUsuario(datosCadena[0]);
        } catch (ApplicationException e) {
            LOGGER.error("Error al consultar el usuario en la base de datos", e);
        }

        if (userDb == null) {
            return Optional.empty();
        }

        LOGGER.debug("ENTRANDO A CONSULTAR ROLES-------");
        CmtOpcionesRolMglManager opcionesRolMglManager = new CmtOpcionesRolMglManager();
        //Consulto  el rol para ver el modulo
        CmtOpcionesRolMgl opcionesRolMgl = opcionesRolMglManager.consultarOpcionRol(FORM_FACT, OPC_NOM_FACT);
        //Consulto el rol para ver mapas
        CmtOpcionesRolMgl opcionesRolMglMapa = opcionesRolMglManager.consultarOpcionRol(FORM_FACT, OPC_NOM_FACT_MAP);

        if (opcionesRolMgl == null) {
            throw new ApplicationException("No se encontró la opción de rol para ver el módulo de factibilidad");
        }

        String rolFac = opcionesRolMgl.getRol() != null ? opcionesRolMgl.getRol() : "";
        String rolFacMap = opcionesRolMglMapa.getRol() != null ? opcionesRolMglMapa.getRol() : "";
        Map<String, Boolean> rolesMicrositio = new HashMap<>();
        UsPerfil perfilUs = userDb.getPerfil();
        BigDecimal perfil = perfilUs.getIdPerfil();
        List<UsRolPerfil> roles = usRolPerfilFacadeLocal.findByPerfil(perfil);
        rolesMicrositio.put(IS_ROL_FAC, containRol.test(roles, rolFac));
        rolesMicrositio.put(IS_ROL_FAC_MAP, containRol.test(roles, rolFacMap));
        return Optional.of(mapUserToUserDataDto.apply(userDb, rolesMicrositio));
    }

    /**
     * Redirige a la página de respuesta.
     *
     * @param response {@link HttpServletResponse}
     * @param url {@link String} URL de la página de respuesta.
     * @author Gildardo Mora
     */
    private void redirectTo(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            String msgError = "Error al redirigir a la página de respuesta: " + url;
            LOGGER.error(msgError, e);
        }
    }

    /**
     * Valida si la fecha de post es valida.
     *
     * @param fechaPost Fecha de post.
     * @return True si la fecha de post es válida, de lo contrario false.
     * @author Gildardo Mora
     */
    private boolean isDatePostValid(String fechaPost) throws ParseException {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            Date datePost = formato.parse(fechaPost);
            Date hoy = new Date();
            Date hoySinHoras = getZeroTimeDate(hoy);
            Date datePostSinHoras = getZeroTimeDate(datePost);
            return !datePostSinHoras.before(hoySinHoras) && !hoySinHoras.before(datePostSinHoras);
    }

    public static Date getZeroTimeDate(Date fecha) {
        Date res;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        res = calendar.getTime();
        return res;
    }

    /**
     * Verifica si un rol esta contenido en una lista de roles.
     */
    private final BiPredicate<List<UsRolPerfil>, String> containRol = (roles, rolVerificar) -> {
        if (CollectionUtils.isEmpty(roles) || StringUtils.isBlank(rolVerificar)) {
            LOGGER.warn("No se encontraron roles para verificar");
            return false;
        }

        return roles.stream().anyMatch(rol -> rol.getCodRol().equalsIgnoreCase(rolVerificar));
    };

    /**
     * Mapea un usuario a un UserDataDto
     */
    private final BiFunction<Usuarios, Map<String, Boolean>, UserDataDto> mapUserToUserDataDto = (user, roles) -> {
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setNombreUsuario(user.getNombre());
        userDataDto.setEmailUsuario(user.getEmail());
        userDataDto.setIdUsuario(String.valueOf(user.getIdUsuario()));
        BigDecimal idPerfil = Optional.of(user).map(Usuarios::getPerfil)
                .map(UsPerfil::getIdPerfil)
                .orElse(BigDecimal.ZERO);
        userDataDto.setIdPerfil(idPerfil.intValue());
        userDataDto.setAccessRol(roles.getOrDefault(IS_ROL_FAC, false));
        userDataDto.setViewMapRol(roles.getOrDefault(IS_ROL_FAC_MAP, false));
        userDataDto.setTelefono(user.getTelefono());
        userDataDto.setIdentificacion(String.valueOf(user.getIdUsuario()));
        return userDataDto;
    };

}
