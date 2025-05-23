package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.dao.impl.cm.CmtMensajeTipoValidacionDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtMensajeTipoValidacion;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.util.cm.PaginadorTablas;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Maneja la lógica de negocio de mensajes de validación.
 * Administrar la logica de negocio de los mensajes de validación.
 * 
 * @author Ricardo Cortés Rodríguez.
 * @version 1.0 Revision 10/05/2017.
 */
public class CmtMensajeTipoValidacionManager {

    /**
     * Permite el acceso a la capa de datos de la tabla
     */
    private CmtMensajeTipoValidacionDaoImpl mensajesTVDao;
    
    /**
     * Permite acceder a los datos de tipos de validación y proyectos en la tabla
     * cmt_basica.
     */
    private CmtBasicaMglManager basicaManager; 
    
    /**
     * Permite acceder a la funcionalidad del paginador.
     */
    private PaginadorTablas paginador;

    /**
     * Buscar todos los mensajes de validación en el sistema.
     * Realiza la búsqueda de todos los items que existen en la tabla de
     * mensajes de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 Revision 10/05/2017.
     * @return Lista de mensajes de validación.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public List<CmtMensajeTipoValidacion> findAll() throws ApplicationException {
        mensajesTVDao = new CmtMensajeTipoValidacionDaoImpl();
        return mensajesTVDao.findAllItemsActive();
    }

    /**
     * Buscar un mensaje específico por el id.
     * Busca un mensaje específico por el id.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 10/05/2017.
     * @param idMensaje Identificado del mensaje en la tabla
     * CMT_MESAJES_TIP_VAL.
     * @return Retorna un mensaje tipo CmtMensajesTipoValidacion.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public CmtMensajeTipoValidacion findForId(BigDecimal idMensaje) throws
            ApplicationException {
        mensajesTVDao = new CmtMensajeTipoValidacionDaoImpl();
        return mensajesTVDao.find(idMensaje);
    }
    
    /**
     * Crear un mensaje para un tipo de validación.Crea un mensaje para un tipo de validación.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 Revisión 10/05/2017.
     * @param mensajeValidacion
     * @param perfil Identificado del Perfil del usuario. 
     * @param usuario Usuario que realizó la creación del mensaje.
     * @return Retorna el id actualizado en el mensaje de validación.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public CmtMensajeTipoValidacion create(CmtMensajeTipoValidacion mensajeValidacion
            ,String usuario, int perfil)throws ApplicationException{
        mensajesTVDao = new CmtMensajeTipoValidacionDaoImpl();
        Hashtable<String, String> parametros = new Hashtable<String, String>();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl valorValidacion = basicaMglManager.findById(
                 mensajeValidacion.getIdValidacion().getBasicaId());
        CmtMensajeTipoValidacion mensaje = 
                mensajesTVDao.findByValorTipo(valorValidacion);
        
        if (mensaje == null ) {
            return mensajesTVDao.createCm(mensajeValidacion, usuario, perfil);
        }else{
            throw new ApplicationException(MensajeTipoValidacion.ERROR_DUPLICIDAD.getValor());
        }
        
    }
    
    /**
     * Actualizar el mensaje de validación.
     * Actualiza el mensaje de validación en la aplicación.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 10/05/2017.
     * @param mensajesValidacion Datos del mensaje a actualizar.
     * @param usuario Usuario que realiza la actualización en el sistema.
     * @param perfil Perfil con el cual se realiza la actualización en el sistema.
     * @return El objeto CmtMensajesTipoValidacion con sus datos actualizados.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public CmtMensajeTipoValidacion update(CmtMensajeTipoValidacion mensajesValidacion
            ,String usuario, int perfil) throws ApplicationException{
        return mensajesTVDao.updateCm(mensajesValidacion, usuario, perfil);
    }
    
    /**
     * Reailzar la eliminación lógica del mensaje.
     * Reliza la eliminación lógica del mensaje.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 10/05/2017.
     * @param mensajesValidacion Datos a eliminar logicamente.
     * @param usuario Usuario que realiza la eliminación.
     * @param perfil Perfil con el cual se realiza la modificación.
     * @return Retorna true si la eliminación es exitosa de lo contrario false.
     * @throws ApplicationException Lanza la excepcion para ser manejada por la
     * vista.
     */
    public Boolean delete(CmtMensajeTipoValidacion mensajesValidacion
            ,String usuario, int perfil) throws ApplicationException{
        return mensajesTVDao.deleteCm(mensajesValidacion, usuario, perfil);
    }

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
    public List<CmtBasicaMgl> findAllForTipoBasica(BigDecimal id) 
            throws ApplicationException{
        basicaManager = new CmtBasicaMglManager();
        CmtTipoBasicaMgl tipoBasica = new CmtTipoBasicaMgl();
        tipoBasica.setTipoBasicaId(id);
        return basicaManager.findByTipoBasica(tipoBasica);
    }
    
    /**
     * Realizar la busqueda de los mensajes por filtros.
     * Realiza la busqueda de los mensajes por los parametros filtrados por el 
     * usuario.
     * 
     * @author Ricardo Cortés Rodríguez
     * @version 1.0 revisión 10/05/2017.
     * @param parametros Criterio de búsqueda dados.
     * @return Lista de mensajes según criterios dados.
     * @throws ApplicationException Lanza la excepción cuando no se encuentra 
     * registro en el sistema con el criterio de búsqueda dado.
     */
    public List<CmtMensajeTipoValidacion> findMensajesPorFiltros(Hashtable<String
            , String> parametros) throws ApplicationException{
        Integer cantidadRegistros = 0;
        Integer registroInicio = 0;
        Integer paginaActual = 1;
        mensajesTVDao = new CmtMensajeTipoValidacionDaoImpl();
        
        if (parametros != null && !parametros.isEmpty()) {
            
            if (parametros.containsKey("pagina") 
                    && parametros.containsKey("movimiento")) {
                paginaActual = new Integer(parametros.get("pagina"));
                cantidadRegistros = mensajesTVDao.findCantidadRegistrosForFiltro(parametros);
                paginador = new PaginadorTablas();
                registroInicio = paginador.paginar(paginaActual, cantidadRegistros, parametros.get("movimiento"));
                parametros.remove("pagina");
                parametros.put("pagina", paginador.getPagina().toString());
                parametros.put("totalPaginas", paginador.getTotalPaginas().toString());
                parametros.put("ultimaPagina", paginador.getEsUltimaPagina().toString());
                
            }
            
            if (!parametros.containsKey("registroInicio")) {
                parametros.put("registroInicio", registroInicio.toString());
            }else{
                parametros.remove("registroInicio");
                parametros.put("registroInicio", registroInicio.toString());
            }
            
            return mensajesTVDao.findMensajesPorFiltro(parametros);
        }
        
        throw new ApplicationException(MensajeTipoValidacion.SIN_CRITERIOS.getValor());
    }
    
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
            throws ApplicationException{
        Integer cantidadRegistros = mensajesTVDao.findCantidadRegistrosForFiltro(parametros);
        Integer paginas = cantidadRegistros / new Integer(MensajeTipoValidacion
                .REGISTROS_POR_PAGINA.getValor());
        return paginas;
    }
    
    //TODO: La revisión quedó aquí
    
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
    public List<CmtMensajeTipoValidacion> obtenerBasicasCreacionMensajes(String tipoValidacion
            , List<CmtBasicaMgl> basicaValidacion ) throws ApplicationException{
        List<CmtMensajeTipoValidacion> mensajesTV = new ArrayList<CmtMensajeTipoValidacion>();

        if (basicaValidacion != null && !basicaValidacion.isEmpty()) {
            Hashtable<String, String>parametros = new Hashtable<String, String>();
            parametros.put("tipoValidacion", tipoValidacion);
            List<CmtMensajeTipoValidacion>mensajesValidacion = findMensajesPorFiltros(parametros);

                for (int i = 0; i < basicaValidacion.size(); i++) {
                    BigDecimal idBasicaTV = basicaValidacion.get(i).getBasicaId();
                    Boolean existe = false;
                    if (mensajesValidacion != null && !mensajesValidacion.isEmpty()) {

                        for (int j = 0; j < mensajesValidacion.size(); j++) {
                            BigDecimal idMensajeValidacion = mensajesValidacion
                                    .get(j).getIdValidacion().getBasicaId();

                            if (idMensajeValidacion.compareTo(idBasicaTV) == 0) {
                                existe = true;
                                break;
                            }
                        }
                        
                    }

                    if (!existe) {
                        CmtMensajeTipoValidacion mensajeTv = new CmtMensajeTipoValidacion();
                        mensajeTv.setIdValidacion(basicaValidacion.get(i));
                        mensajesTV.add(mensajeTv);
                    }
                    
                }
                
                return mensajesTV;

            }

        throw new ApplicationException(MensajeTipoValidacion.SIN_COINCIDENCIA.getValor());
        
    }
    
    /**
     * Validar campos vacíos del formulario de creación de mensajes.
     * Valida que los campos al momento de crear la configuración de mensajes 
     * para un tipo de validación todos sus items y mensajes se encuentren 
     * diligenciados.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @versión 1.0 revisión 11/05/2017.
     * @param mensajes Lista de mensajes a crear.
     * @throws ApplicationException Se lanza en el caso de que un campo de algún 
     * mensaje no se encuentre diligenciado.
     */
    private void validarCamposCreacion(List<CmtMensajeTipoValidacion> mensajes)
            throws ApplicationException{
        
        for (CmtMensajeTipoValidacion mensaje : mensajes) {
            
            if (mensaje.getMensajeSi()== null || mensaje.getMensajeSi().equals("")) {
                throw new ApplicationException(
                        MensajeTipoValidacion.CAMPOS_VACIOS.getValor());
            }
            
            if (mensaje.getMensajeNo()== null || mensaje.getMensajeNo().equals("")) {
                throw new ApplicationException(
                        MensajeTipoValidacion.CAMPOS_VACIOS.getValor());
            }
            
            if (mensaje.getMensajeProcesos()== null || mensaje.getMensajeProcesos().equals("")) {
                throw new ApplicationException(
                        MensajeTipoValidacion.CAMPOS_VACIOS.getValor());
            }
            
            if (mensaje.getMensajeNa()== null || mensaje.getMensajeNa().equals("")) {
                throw new ApplicationException(
                        MensajeTipoValidacion.CAMPOS_VACIOS.getValor());
            }
            
            if (mensaje.getMensajeRestringido()== null || mensaje.getMensajeRestringido().equals("")) {
                throw new ApplicationException(
                        MensajeTipoValidacion.CAMPOS_VACIOS.getValor());
            }
            
        }
        
    }
    
    /**
     * Persistir la lista de mensajes configurados.
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
            List<CmtMensajeTipoValidacion> mensajes, String usuario, Integer perfil)
            throws ApplicationException{
        validarCamposCreacion(mensajes);
        
        for (CmtMensajeTipoValidacion mensaje : mensajes) {
            create(mensaje, usuario, perfil);
        }
    }
    
    /**
     * Consultar tipos de validación que no se encuentran asociados a un mensaje.
     * Consulta los mensajes los tipos de validación a los cuales  no se les ha 
     * configurado mensajes.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 11/05/2017.
     * @param idTipoBasica Identificadro del tipo de validación a consultar
     * @return La lista de en mensajes de los tipos de validación para configurar 
     * si los hay.
     */
    public List<CmtMensajeTipoValidacion> findMensajesNoConfiguradosPorTipoBasica(BigDecimal idTipoBasica){
        mensajesTVDao = new CmtMensajeTipoValidacionDaoImpl();
        return mensajesTVDao.findMensajesNoConfiguradosPorTipoBasica(idTipoBasica);
    }

    /**
     * Buscar mensajes de viabilidad.
     * Busca el mensaje de viabilidad filtrado por viabilidad y la básica.
     * 
     * @author hitss.
     * versión 1.0 revisión 11/05/2017.
     * @param basica basica donde se va encontrar la viabilidad.
     * @param viabilidad valor Y/P/N/NA para ver la viabilidad.
     * @return El mensaje según la viabilidad.
     * @throws ApplicationException Lanza cualquier tipo de excepción de la 
     * consulta.
     */
    public String buscarMensaje(CmtBasicaMgl basica, String viabilidad)
            throws ApplicationException{
        CmtMensajeTipoValidacionDaoImpl cmtvdi=new CmtMensajeTipoValidacionDaoImpl();
        return cmtvdi.findMensaje(basica,viabilidad);
    }
}