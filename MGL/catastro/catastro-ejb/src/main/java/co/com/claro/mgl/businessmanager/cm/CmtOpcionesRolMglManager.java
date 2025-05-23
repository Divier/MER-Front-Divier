package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtOpcionesRolMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import java.util.List;

/**
 * <b>Manager para exponer el dao de {@link CmtOpcionesRolMglDaoImpl}</b><br />
 *
 * @author wgavidia
 * @version 2017/09/20
 */
public class CmtOpcionesRolMglManager {

    private final CmtOpcionesRolMglDaoImpl DAO = new CmtOpcionesRolMglDaoImpl();

    /**
     * M&eacute;todo para retornar listado de opciones por rol
     *
     * @param formulario String, nombre del formulario
     * @return List&lt;{@link CmtOpcionesRolMgl}> listado de opciones por rol
     */
    public List<CmtOpcionesRolMgl> consultarOpcionesRol(String formulario) {
        return DAO.consultarOpcionesRol(formulario);
    }

    /**
     * M&eacute;todo para consulta de la acci&oacute;n de un componente en un
     * formulario espec&iacute;fico
     *
     * @param formulario String nombre del formulario al que pertenece el
     * componente
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return {@link CmtOpcionesRolMgl} Objeto de base de datos recuperado en
     * la consulta
     * @throws ApplicationException Excepci&oacute;n generada al no encontrar
     * registros
     */
    public CmtOpcionesRolMgl consultarOpcionRol(String formulario, String opcion)
            throws ApplicationException {
        return DAO.consultarOpcionRol(formulario, opcion);
    }

    /**
     * Metodo que consulta los roles
     *
     * @return {@link CmtOpcionesRolMgl} Objeto de base de datos recuperado en
     * la consulta
     * @throws ApplicationException Excepci&oacute;n generada al no encontrar
     * registros
     */
    public List<String> consultarRoles()
            throws ApplicationException {
        return DAO.consultarRol();
    }

}
