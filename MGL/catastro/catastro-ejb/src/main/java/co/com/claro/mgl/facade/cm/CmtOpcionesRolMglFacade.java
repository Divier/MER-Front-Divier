package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtOpcionesRolMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 * <b>Facade para opciones por rol</b><br />
 * Clase para implementar los métodos expuestos en la interfaz
 * {@link CmtOpcionesRolMglFacadeLocal}
 * 
 * @author gavidiawf
 * @version 2017/09/20
 */
@Stateless
public class CmtOpcionesRolMglFacade implements CmtOpcionesRolMglFacadeLocal {

    private final CmtOpcionesRolMglManager MANAGER = new CmtOpcionesRolMglManager();

    /**
     * {@inheritDoc}
     *
     * @param formulario String, nombre del formulario
     * @return List&lt;{@link CmtOpcionesRolMgl}> listado de opciones por rol
     */
    @Override
    public List<CmtOpcionesRolMgl> consultarOpcionesRol(String formulario) {
        return MANAGER.consultarOpcionesRol(formulario);
    }

    /**
     * {@inheritDoc}
     * 
     * @param formulario String nombre del formulario al que pertenece el componente
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el componente
     * @return {@link CmtOpcionesRolMgl} Objeto de base de datos recuperado en la consulta
     * @throws ApplicationException Excepci&oacute;n generada al no encontrar registros
     */
    @Override
    public CmtOpcionesRolMgl consultarOpcionRol(String formulario, String opcion) throws ApplicationException {
        return MANAGER.consultarOpcionRol(formulario, opcion);
    }

}
