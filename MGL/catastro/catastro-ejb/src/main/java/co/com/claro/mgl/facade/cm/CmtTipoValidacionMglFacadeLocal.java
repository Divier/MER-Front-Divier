/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoValidacionMglManager;
import co.com.claro.mgl.dtos.CmtTipoValidacionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import javax.ejb.Local;

/**
 * interface con operaciones a implementar. Encargada de exponer la logica del
 * manager {@link CmtTipoValidacionMglManager}.
 *
 * @author Johnnatan Ortiz.
 * @version 1.0 revision 17/05/2017.
 * @see BaseCmFacadeLocal
 */
@Local
public interface CmtTipoValidacionMglFacadeLocal
        extends BaseCmFacadeLocal<CmtTipoValidacionMgl> {

    /**
     * Crear registros de tipo validacion. Crea registros en
     * CmtTipoValidacionMgl.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param t Datos de tipo de validación
     * @return El tipo de validacion con el id de la creacion.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public CmtTipoValidacionMgl create(CmtTipoValidacionMgl t)
            throws ApplicationException;

    /**
     * Actualizar los tipos de validacion. actualizacion de elementos de
     * CmtTipoValidacionMgl.
     *
     * @author jdHernandez.
     * @version 1.0 revision 17/05/2017.
     * @param t Los datos del tipo de validacion.
     * @return El tipo de validacion actualizada.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public CmtTipoValidacionMgl update(CmtTipoValidacionMgl t)
            throws ApplicationException;

    /**
     * Borrar el tipo de validacion. Borra los el tipo de validación.
     *
     * @author jdHernandez.
     * @version 1.0 revision 17/05/2017.
     * @param t Tipo de validación a borrar.
     * @return True cuando la eliminacion fue exitosa.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public boolean delete(CmtTipoValidacionMgl t) throws ApplicationException;

    /**
     * Retornar todos los registros que se encuentran en estado Activo Retorna
     * todos los registros que se encuentran en estado Activo
     *
     * @author jdHernandez.
     * @version 1.0 revision 17/05/2017.
     * @return La lista de tipos de validacion.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public List<CmtTipoValidacionMgl> findAll() throws ApplicationException;

    /**
     * Buscar el tipo de validacion por el id. 
     * Busca el tipo de validacion por el id.
     *
     * @author jdHernandez.
     * @version 1.0 revision 17/05/2017.
     * @param t Identificador del tipo de validacion a buscar.
     * @return El tipo de validacion coincidente con el id.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public CmtTipoValidacionMgl findById(CmtTipoValidacionMgl t)
            throws ApplicationException;

    /**
     * Buscar el tipo de validacion. 
     * Comprueba si se encuentra un tipoBasica en la persistencia o no 
     * (true/false).
     *
     * @author jdHernandez.
     * @version 1.0 revision 18/05/2017.
     * @param tipoValidacion Identificador del tipo de validacion.
     * @return True si existe el tipo de validacion de los contrario false.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public boolean existsTipoBasica(CmtTipoBasicaMgl tipoValidacion)
            throws ApplicationException;

    /**
     * Obtener la cantidad de paginas del paginador. Obtiene la cantidad de
     * páginas con respecto a los registros y a la constante de cantidad de
     * registros por página.
     *
     * @author jdHernandez.
     * @version 1.0 revision 18/05/2017.
     * @param parametros Contiene los criterios de búsqueda de la aplicación.
     * @return Cantidad de páginas que contendrá la consulta según criterios.
     * @throws ApplicationException Excepción ejecución de sentencia.
     */
    public Integer obtenerCantidadPaginas(Hashtable<String, String> parametros)
            throws ApplicationException;

    /**
     * Buscar el mensaje del tipo de validacion Busca el mensaje con los
     * parametros pasados como criterio de busqueda.
     *
     * @author Ricardo Cotes Rodriguez.
     * @version 1.0 revision 18/05/2017.
     * @param parametros Criterios de busqueda.
     * @return Lista de mensajes correspondiente a los criterios de busqueda
     * dados.
     * @throws ApplicationException Lanza la excepsion cuando no existen
     * registros en la busqueda realizada.
     */
    public List<CmtTipoValidacionMgl> findMensajesPorFiltros(Hashtable<String
            , String> parametros) throws ApplicationException;

    /**
     * Buscar reglas por proyecto.
     * Busca las reglas por proyecto. Retorna todos los registro por proyecto.
     *
     * @author jdHernandez.
     * @version 1.0 revision 18/05/2017.
     * @param proyectoDetalleSelected Proyecto como criterio de busqueda.
     * @return Lista de dtos del tipo de validacion.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public List<CmtTipoValidacionDto> findReglasByProyecto(
            BigDecimal proyectoDetalleSelected) throws ApplicationException;

    /**
     * Busca cuantos tipos de validacion coincidente con el parametro. Retorna
     * conteo de registros en uso.
     *
     * @author jdHernandez.
     * @version 1.0 revision 18/05/2017.
     * @param idTipoValSelected Identificador del tipo de validacion
     * seleccionado.
     * @return True si encuentra registros de los contrario false
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public boolean findTipoValidacionEnUso(BigDecimal idTipoValSelected)
            throws ApplicationException;

}
