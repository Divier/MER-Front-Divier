package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtMensajeTipoValidacion;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;

/**
 * Permitir acceder a la capa de negocio de los mensajes de validación.
 * Permite acceder a la fuancionalidad de la capa de negocio de los mensajes de 
 * validación.
 * 
 * @author Ricardo Cortés Rodríguez.
 * @version 1.0 revisión 11/05/2017.
 */
public interface ICmtMensajeTipoValidacionFacadeLocal extends 
        BaseCmFacadeLocal<CmtMensajeTipoValidacion>{
    
    /**
     * Buscar todos los mensajes de validación en el sistema.
     * Realiza la búsqueda de todos los items que existen en la tabla de
     * mensajes de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 Revision 11/05/2017.
     * @return Lista de mensajes de validación.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public List<CmtMensajeTipoValidacion> findAll() throws ApplicationException;
    
    /**
     * Buscar un mensaje específico por el id.
     * Busca un mensaje específico por el id.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param idMensaje Identificado del mensaje en la tabla
     * CMT_MESAJES_TIP_VAL.
     * @return Retorna un mensaje tipo CmtMensajesTipoValidacion.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public CmtMensajeTipoValidacion findForId(BigDecimal idMensaje) 
            throws ApplicationException;
    
    /**
     * Crear un mensaje para un tipo de validación.Crea un mensaje para un tipo de validación.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 Revisión 10/05/2017.
     * @param mensajesValidacion El mensaje a insertar en la tabla.
     * @return Retorna el id actualizado en el mensaje de validación.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public CmtMensajeTipoValidacion create(
            CmtMensajeTipoValidacion mensajesValidacion)
            throws ApplicationException;
    
    /**
     * Actualizar el mensaje de validación.Actualiza el mensaje de validación en la aplicación.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 10/05/2017.
     * @param mensajesValidacion Datos del mensaje a actualizar.
     * @return El objeto CmtMensajesTipoValidacion con sus datos actualizados.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public CmtMensajeTipoValidacion update(
            CmtMensajeTipoValidacion mensajesValidacion)
            throws ApplicationException;
    
    /**
     * Reailzar la eliminación lógica del mensaje.Reliza la eliminación lógica del mensaje.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 10/05/2017.
     * @param mensajesValidacion Datos a eliminar logicamente.
     * @return Retorna true si la eliminación es exitosa de lo contrario false.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public Boolean delete(CmtMensajeTipoValidacion mensajesValidacion)
            throws ApplicationException;
    
    /**
     * Buscar todos los registros que pertenezcan a un tipo básica determinada.
     * Busca todos los registros que pertenezcan a un tipo básica determinada.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 10/05/2017.
     * @param id El identificador de tipo de básica.
     * @return Lista todos los registros de basica que concuerden con el tipo dado.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public List<CmtBasicaMgl> findAllForTipoBasica(BigDecimal id) throws ApplicationException;
    
    /**
     * Realizar la busqueda de los mensajes por filtros.
     * Realiza la busqueda de los mensajes por los parametros filtrados por el 
     * usuario.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 10/05/2017.
     * @param parametros Criterio de búsqueda dados.
     * @return Lista de mensajes según criterios dados.
     * @throws ApplicationException Lanza la excepción cuando no se encuentra 
     * registro en el sistema con el criterio de búsqueda dado.
     */
    public List<CmtMensajeTipoValidacion> findMensajesPorFiltros(Hashtable<String
            , String> parametros) throws ApplicationException;
    
     /**
     * Obtiener la cantidad de páginas.
     * Obtiene la cantidad de páginas con respecto a los registros y a la constante 
     * de cantidad de registros por página.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 10/05/2017.
     * @param parametros Contiene los criterios de búsqueda de la aplicación.
     * @return Cantidad de páginas que contendrá la consulta según criterios.
     * @throws ApplicationException Excepción ejecución de sentencia.
     */
    public Integer obtenerCantidadPaginas(Hashtable<String, String> parametros) 
            throws ApplicationException;
    
    /**
     * Obtiener tipos de validación no asociados a la tabla de mensajes.
     * Encargado de obtener las datos asociados a un tipo de validación 
     * que no se encuentren configurados en la tabla CMT_MENSAJES_TIP_VAL.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 10/05/2017.
     * @param tipoValidacion Tipo de validación con la cual se consultan sus 
     * datos asociados.
     * @param basicaValidacion Lista de datos asociados a un tipo de validación.
     * @return Lista de datos asociados a un tipo de validación que no se 
     * encuentran configurados en los mensajes.
     * @throws ApplicationException Es lanzado cuando no se encontro registros 
     * en el sistema.
     */
    public List<CmtMensajeTipoValidacion> obtenerBasicasCreacionMensajes(
            String tipoValidacion, List<CmtBasicaMgl> basicaValidacion ) 
            throws ApplicationException;
    
    /* Persistir la lista de mensajes configurados.
     * Crea una lista de mensajes en la tabla CMT_MENSAJES_TIP_VAL.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @versión 1.0 revisión 11/05/2017.
     * @param mensajes Lista de mensajes que contiene los datos a almacenar.
     * @param usuario Usuario que realiza la creación de los mensajes.
     * @param perfil Perfil el cual tiene el usuario.
     * @throws ApplicationException Lanza un mensaje si los campos no son válidos 
     * o ocurre una excepción en la insercción en la tabla.
     */
    public void createListaMensajes(
            List<CmtMensajeTipoValidacion> mensajes)
            throws ApplicationException;
    
    /**
     * Consultar tipos de validación que no se encuentran asociados a un mensaje.
     * Consulta los mensajes los tipos de validación a los cuales  no se les ha 
     * configurado mensajes.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 11/05/2017.
     * @param idTipoBasica Identificadro del tipo de validación a consultar.
     * @return La lista de en mensajes de los tipos de validación para configurar 
     * si los hay.
     */
    public List<CmtMensajeTipoValidacion> findMensajesNoConfiguradosPorTipoBasica
        (BigDecimal idTipoBasica);
}
