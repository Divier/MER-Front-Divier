package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import java.util.List;
import javax.ejb.Local;

/**
 * <b>Facade para opciones por rol</b><br />
 * Interfaz para exponer los métodos de negocio para operaciones por rol
 *
 * @author wgavidia
 * @version 2017/09/20
 */
@Local
public interface CmtOpcionesRolMglFacadeLocal {

    /**
     * M&eacute;todo para retornar el listado de permisos por rol.
     *
     * @param formulario String, nombre del formulario
     * @return List&lt;{@link CmtOpcionesRolMgl}> listado de opciones por rol
     */
    List<CmtOpcionesRolMgl> consultarOpcionesRol(String formulario);

    /**
     * M&eacute;todo para consulta de la acci&oacute;n de un componente en un
     *      formulario espec&iacute;fico
     * 
     * @param formulario String nombre del formulario al que pertenece el componente
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el componente
     * @return {@link CmtOpcionesRolMgl} Objeto de base de datos recuperado en la consulta
     * @throws ApplicationException Excepci&oacute;n generada al no encontrar registros
     */
    CmtOpcionesRolMgl consultarOpcionRol(String formulario, String opcion)
            throws ApplicationException;

}
