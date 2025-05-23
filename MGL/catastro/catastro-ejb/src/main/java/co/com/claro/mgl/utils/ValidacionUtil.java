package co.com.claro.mgl.utils;

import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.cm.CmtOpcionesRolMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

/**
 * <b>Utilitario para validaci&oacute;n de acciones</b><br />
 * Clase para generar validaciones de datos en el sistema
 * 
 * @author wgavidia
 * @version 2017/09/21
 */
@NoArgsConstructor(access = PRIVATE)
public class ValidacionUtil {

    public static final String OPC_CREACION = "CREAR";
    public static final String OPC_EDICION = "EDITAR";
    public static final String OPC_BORRADO = "ELIMINAR";
    public static final String OPC_APROBAR = "APROBAR";
    //opcion para validar si el usuario teine el rol de
    public static final String OPC_GESTIONAR = "GESTIONAR";

    /**
     * Método para validar la visualización de un componente
     * para un rol específico.
     * 
     * @param formulario String nombre del formulario al que pertenece el componente
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el componente
     * @param opcionesFacade Objeto {@link CmtOpcionesRolMglFacadeLocal} para realizar consulta
     * @param securityLogin Objeto {@link SecurityLogin} para validar el usaurio
     * @return {@code boolean}
     * @throws ApplicationException Excepci&oacute;n generada al no encontrar registros
     */
    public static boolean validarVisualizacion(final String formulario, final String opcion,
            final CmtOpcionesRolMglFacadeLocal opcionesFacade, final SecurityLogin securityLogin)
            throws ApplicationException {
        final CmtOpcionesRolMgl opcionRol = opcionesFacade.consultarOpcionRol(formulario, opcion);

        if (Objects.isNull(opcionRol)) {
            String flagValidarRoles = ParametrosMerUtil.findValor(ParametrosMerEnum.FLAG_VALIDACION_ROLES.getAcronimo());
            return StringUtils.isBlank(flagValidarRoles) || !flagValidarRoles.equalsIgnoreCase(Boolean.TRUE.toString());
        }

        return securityLogin.usuarioTieneRoll(opcionRol.getRol());
    }

    /**
     * Método para verificar si un String es nulo o vacio
     * @param parametro {@link String}
     * @return {@code boolean}
     */
    public static Boolean validarParametro(String parametro) {
        return parametro != null && !parametro.isEmpty();
    }

    /**
     * Método para convertir una fecha a un String de tipo <b><i>yyyy-MM-dd</i></b>
     * 
     * @param date {@link Date} fecha a convertir
     * @return {@link String} fecha en formato String
     */
    public static String dateFormatyyyyMMdd(Date date) {
        if (date == null) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    
      /**
     * Metodo para validar si el usuario en sesion tiene el rol aprobador.
     * 
     * @param rolAprobador String rolAprobador
     * @param securityLogin Objeto {@link SecurityLogin} para validar el usaurio
     * @return {@code boolean}
     * @throws ApplicationException Excepci&oacute;n generada al no encontrar registros
     */
    public static boolean usuarioTieneRolAprobador(final String rolAprobador,
              final SecurityLogin securityLogin)
            throws ApplicationException {

        return securityLogin.usuarioTieneRoll(rolAprobador);

    }
    
    /**
     * Método para validar la visualización de un componente para
     * un rol específico.
     *
     * @param formulario String nombre del formulario al que pertenece el
     * componente
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @param securityLogin Objeto {@link SecurityLogin} para validar el usuario
     * @return {@code boolean}
     * @throws ApplicationException Excepción generada al no encontrar
     * registros
     */
    public static boolean validarVisualizacionRol(final String formulario, final String opcion,
            final SecurityLogin securityLogin)
            throws ApplicationException {
        CmtOpcionesRolMglManager manager = new CmtOpcionesRolMglManager();
        final CmtOpcionesRolMgl opcionRol = manager.consultarOpcionRol(formulario, opcion);
        return opcionRol != null && securityLogin.usuarioTieneRoll(opcionRol.getRol());
    }
}
