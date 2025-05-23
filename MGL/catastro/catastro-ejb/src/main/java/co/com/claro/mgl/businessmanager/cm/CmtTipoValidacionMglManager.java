package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.PcmlManager;
import co.com.claro.mgl.businessmanager.address.rr.NodoRRManager;
import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtReglaValidacionDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtTipoBasicaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtTipoValidacionMglDaoImpl;
import co.com.claro.mgl.dtos.CmtTipoValidacionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import co.com.claro.mgl.jpa.entities.rr.NodoRR;
import co.com.claro.mgl.util.cm.PaginadorTablas;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestValidacionViabilidad;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionViabilidad;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.ws.Serviceadc;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.telmex.pcml.service.Edificio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * clase encargada de la administracion de la tabla
 * {@link CmtTipoValidacionMgl}. ofrece operaciones necesarias para crear,
 * modificar y eliminar registros de la tabla {@link CmtTipoValidacionMgl}.
 *
 * @author ortizjaf
 * @versión 1.0
 */
public class CmtTipoValidacionMglManager {

    /**
     * Constante de proyecto.
     */
    private final String TIPOBASICA_PROYECTO = "MATRIZ DE VIABILIDAD";
    
    /**
     * COnstante de estado de la cuenta matriz
     */
    private final String TIPOBASICA_ESTADOCUENTAMATRIZ = "ESTADOS CUENTA MATRIZ";
    
    /**
     * Constante de estrato.
     */
    private final String TIPOBASICA_ESTRATO = "ESTRATO";
    
    /**
     * Constante de estado nodo.
     */
    private final String TIPOBASICA_ESTADO_NODO = "ESTADO NODO";
    
    /**
     * Constante de estado hhpp.
     */
    private final String TIPOBASICA_ESTADO_HHPP = "ESTADO HHPP";
    
    /**
     * Constante de tipo red.
     */
    private final String TIPOBASICA_TIPO_RED = "TIPO RED";
    
    /**
     * Constante de tipo de segmento.
     */
    private final String TIPOBASICA_TIPO_SEGMENTO = "TIPO SEGMENTO";
    /**
     * Permite acceder a la funcionalidad del paginador.
     */
    private PaginadorTablas paginador;
    
    CmtBasicaMglDaoImpl basicaMgl = new CmtBasicaMglDaoImpl();

    private static final Logger LOGGER = LogManager.getLogger(CmtTipoValidacionMglManager.class);

    /**
     * Buscar todos los registro activos.
     * Retornar todos los registros que se encuentran en estado Activo Retorna
     * todos los registros que se encuentran en estado Activo
     *
     * @author jdHernandez.
     * @version 1.0 revision 17/05/2017.
     * @return La lista de tipos de validacion.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public List<CmtTipoValidacionMgl> findAllItemsActive() throws ApplicationException {
        List<CmtTipoValidacionMgl> resulList;
        CmtTipoValidacionMglDaoImpl cmtTipoValidacionMglDaoImpl = new CmtTipoValidacionMglDaoImpl();

        resulList = cmtTipoValidacionMglDaoImpl.findAllItemsActive();

        return resulList;
    }

    public CmtTipoValidacionMgl create(CmtTipoValidacionMgl cmtTipoSolicitudMgl,
            String usuario, int perfil) throws ApplicationException {

        CmtTipoValidacionMglDaoImpl cmtTipoValidacionMglDaoImpl = new CmtTipoValidacionMglDaoImpl();

        return cmtTipoValidacionMglDaoImpl.createCm(cmtTipoSolicitudMgl, usuario, perfil);
    }

    public CmtTipoValidacionMgl update(CmtTipoValidacionMgl cmtTipoSolicitudMgl,
            String usuario, int perfil) throws ApplicationException {
        CmtTipoValidacionMglDaoImpl cmtTipoValidacionMglDaoImpl = new CmtTipoValidacionMglDaoImpl();
        return cmtTipoValidacionMglDaoImpl.updateCm(cmtTipoSolicitudMgl, usuario, perfil);
    }

    public boolean delete(CmtTipoValidacionMgl cmtTipoSolicitudMgl,
            String usuario, int perfil) throws ApplicationException {
        CmtTipoValidacionMglDaoImpl cmtTipoValidacionMglDaoImpl = new CmtTipoValidacionMglDaoImpl();
        return cmtTipoValidacionMglDaoImpl.deleteCm(
                cmtTipoSolicitudMgl, usuario, perfil);
    }

    /**
     * Buscar el tipo de validacion por el id. 
     * Busca el tipo de validacion por el id.
     *
     * @author jdHernandez.
     * @version 1.0 revision 17/05/2017.
     * @param cmtTipoValidacionMgl Identificador del tipo de validacion a buscar.
     * @return El tipo de validacion coincidente con el id.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public CmtTipoValidacionMgl findById(CmtTipoValidacionMgl cmtTipoValidacionMgl)
            throws ApplicationException {
        CmtTipoValidacionMglDaoImpl cmtTipoValidacionMglDaoImpl = new CmtTipoValidacionMglDaoImpl();
        return cmtTipoValidacionMglDaoImpl.find(
                cmtTipoValidacionMgl.getIdTipoValidacion());
    }

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
            throws ApplicationException {
        CmtTipoValidacionMglDaoImpl cmtTipoValidacionMglDaoImpl = new CmtTipoValidacionMglDaoImpl();
        return cmtTipoValidacionMglDaoImpl.existsTipoBasica(tipoValidacion);
    }

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
    public Integer obtenerCantidadPaginas(
            Hashtable<String, String> parametros)
            throws ApplicationException {
        CmtTipoValidacionMglDaoImpl cmtTipoValidacionMglDaoImpl = new CmtTipoValidacionMglDaoImpl();
        Integer cantidadRegistros = cmtTipoValidacionMglDaoImpl.
                findCantidadRegistrosForFiltro(parametros);
        return cantidadRegistros / new Integer(
                MensajeTipoValidacion.REGISTROS_POR_PAGINA.getValor());
    }

    /**
     * Buscar mensajes de validacion.
     * Realiza la busqueda de los mensajes por los parametros filtrados por el
     * usuario.
     *
     * @author Ricardo Cortes Rodriguez.
     * @version 1.0 revision 18/05/2017.
     * @param parametros Criterio de búsqueda dados.
     * @return Lista de mensajes según criterios dados.
     * @throws ApplicationException Lanza la excepsion cuando no hay criterios 
     * de busqueda.
     */
    public List<CmtTipoValidacionMgl> findMensajesPorFiltros(
            Hashtable<String, String> parametros)
            throws ApplicationException {
        Integer cantidadRegistros = 0;
        Integer registroInicio = 0;
        Integer paginaActual = 1;
        CmtTipoValidacionMglDaoImpl cmtTipoValidacionMglDaoImpl = new CmtTipoValidacionMglDaoImpl();
        if (parametros != null && !parametros.isEmpty()) {
            if (parametros.containsKey("pagina")
                    && parametros.containsKey("movimiento")) {
                paginaActual = new Integer(parametros.get("pagina"));
                cantidadRegistros = cmtTipoValidacionMglDaoImpl.
                        findCantidadRegistrosForFiltro(parametros);
                paginador = new PaginadorTablas();
                registroInicio = paginador.paginar(
                        paginaActual,
                        cantidadRegistros,
                        parametros.get("movimiento"));
                parametros.remove("pagina");
                parametros.put("pagina",
                        paginador.getPagina().toString());
                parametros.put("totalPaginas",
                        paginador.getTotalPaginas().toString());
                parametros.put("ultimaPagina",
                        paginador.getEsUltimaPagina().toString());

            }
            if (!parametros.containsKey("registroInicio")) {
                parametros.put("registroInicio", registroInicio.toString());
            }
            return cmtTipoValidacionMglDaoImpl.findMensajesPorFiltro(parametros);
        }
        throw new ApplicationException(
                MensajeTipoValidacion.SIN_CRITERIOS.getValor());
    }

    /**
     * Verificar la validez del request.
     * Verifica si es valido el request da.
     *
     * @author jdHernandez.
     * @version 1.0 revision 18/05/2017.
     * @param requestValidacionViabilidad Requerimiento del servicio
     * @param responseValidacionViabilidad Respuesta del servicio
     * @return true si el request es valido y false en caso contrario
     */
    private boolean isValidoRequestValidacionViabilidad(
            RequestValidacionViabilidad requestValidacionViabilidad,
            ResponseValidacionViabilidad responseValidacionViabilidad) {

        if (requestValidacionViabilidad == null) {
            responseValidacionViabilidad.setCodRespuesta("I");
            responseValidacionViabilidad.setMensajeRespuesta("No hay data para evaluar");
            return false;
        }
        //Validación de los campos que no estén vacios ni nulos
        if (!requestValidacionViabilidad.hayCamposConValores()) {
            responseValidacionViabilidad.setCodRespuesta("I");
            responseValidacionViabilidad.setMensajeRespuesta("Todos los campos de entrada son obligatorios");
            return false;
        }
        return true;
    }

    /**
     * Busca una instancia UnidadPcml teniendo en cuenta los datos que se
     * encuentran en la variable requestValidacionViabilidad.
     *
     * @param requestValidacionViabilidad instancia que representa la data a
     * enviar
     * @param responseValidacionViabilidad instancia que representa la data de
     * respuesta
     * @return un instancia UnidadStructPcml con los datos que se van analizar
     * como básica
     */
    private UnidadStructPcml buscarUnidadPcml(
            RequestValidacionViabilidad requestValidacionViabilidad,
            ResponseValidacionViabilidad responseValidacionViabilidad) {
        //Obtener informacion del hhpp
        UnidadStructPcml unidadPcml = null;
        PcmlManager pcmlManager = new PcmlManager();
        try {
            List<UnidadStructPcml> unidadStructPcmlList = pcmlManager.getUnidades(
                    requestValidacionViabilidad.getCalle().trim(),
                    requestValidacionViabilidad.getUnidad().trim(),
                    requestValidacionViabilidad.getApartamento().trim(),
                    requestValidacionViabilidad.getComunidad().trim());

            responseValidacionViabilidad.setCodRespuesta("I");
            responseValidacionViabilidad.setResultadoValidacion(false);
            responseValidacionViabilidad.setMensajeRespuesta("Unidad(HHPP) no existe. "
                    + "Factibilidad Negativa");

            if (unidadStructPcmlList == null || unidadStructPcmlList.isEmpty()) {
                return null;
            }

            for (UnidadStructPcml structPcml : unidadStructPcmlList) {
                if (structPcml.getAptoUnidad().trim().
                        equalsIgnoreCase(requestValidacionViabilidad.getApartamento().trim())
                        && structPcml.getCalleUnidad().trim().
                        equalsIgnoreCase(requestValidacionViabilidad.getCalle().trim())
                        && structPcml.getCasaUnidad().trim().
                        equalsIgnoreCase(requestValidacionViabilidad.getUnidad().trim())) {
                    unidadPcml = structPcml;
                    break;
                }
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return unidadPcml;
    }

    /**
     * Realiza el proceso de viabilidad, realizando los procedimientos
     * necesarios para enviar una respuesta a la petición.
     *
     * @param requestValidacionViabilidad instancia con los parametros que se
     * van a validar
     * @return la instancia <code>ResponseValidacionViabilidad</code> con la
     * respuesta de validación realizada.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ResponseValidacionViabilidad getViabilidad(
            RequestValidacionViabilidad requestValidacionViabilidad) throws ApplicationException {
        
         /* @author Juan David Hernandez */
             //Si la comunidad no es un numero (codigoDane) es porque viene de visor en formato RR
             boolean comunidadVisor = isNumeric(requestValidacionViabilidad.getComunidad());
             /*Si la comunidad llega desde visor en formato RR se hace la busqueda de la comunidad para 
             obtener el centro poblado y extraer el codigo dane para seguir con el proceso normal de MGL*/
       
        if (!comunidadVisor) {
            CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
            CmtComunidadRr cmtComunidadRr = cmtComunidadRrManager.findComunidadByComunidad(requestValidacionViabilidad.getComunidad());
            //Se reemplaza la comunidad RR por el codigo dane del centro poblado
            if (cmtComunidadRr != null && cmtComunidadRr.getCiudad() != null) {
                requestValidacionViabilidad.setComunidad(cmtComunidadRr.getCiudad().getGeoCodigoDane());
            } else {
                throw new ApplicationException(" Comunidad RR no tiene configurado"
                        + " la ciudad en MGL para obtener el codigo dane");
            }
        }

        ResponseValidacionViabilidad responseValidacionViabilidad
                = new ResponseValidacionViabilidad();

        if (!isValidoRequestValidacionViabilidad(requestValidacionViabilidad,
                responseValidacionViabilidad)) {
            return responseValidacionViabilidad;
        }

        try {
            UnidadStructPcml unidadPcml = buscarUnidadPcml(requestValidacionViabilidad,
                    responseValidacionViabilidad);
            //Escenario HHpp existe en RR
            if (unidadPcml != null) {
                CmtBasicaMgl estadoNodo = getEstadoNodo(unidadPcml.getNodUnidad());

                // se busca la informacion del edificio al cual pertenece la unidad
                PcmlManager pcmlManager = new PcmlManager();
                Edificio edificio = pcmlManager.getEdificio(
                        unidadPcml.getIdUnidad().toString());

                Map<String, String> estadoCM = new HashMap<>();
                estadoCM.put("CodigoEstadoCM", "NA");
                estadoCM.put("DescripcionEstadoCM", "NA");
                if (edificio != null && edificio.getCodEdificio() != null
                        && !edificio.getCodEdificio().trim().isEmpty()) {
                    estadoCM = getEstadoCM(edificio);
                }
                return procesarViabilidad(
                        estadoNodo, estadoCM,
                        unidadPcml.getEstadUnidadad(),
                        unidadPcml.getEstratoUnidad(),
                        unidadPcml.getTipoRed(),
                        requestValidacionViabilidad.getSegmento(),
                        requestValidacionViabilidad.getProyecto());
            } else {//Escenario HHpp NO existe en RR
                if (requestValidacionViabilidad.getTipoDireccion().
                        equalsIgnoreCase("CK")) {
                    NegocioParamMultivalor param = new NegocioParamMultivalor();                    
                    CityEntity cityEntity = param.consultaDptoCiudad(requestValidacionViabilidad.getComunidad());

                    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                    
                    GeograficoPoliticoMgl geograficoPolitico = geograficoPoliticoManager
                            .findCityByCodDaneCp(requestValidacionViabilidad.getComunidad());
                    
                    GeograficoPoliticoMgl geograficoPoliticoMgl = geograficoPolitico;
                    //Validamos si la ciudad es multiorigen, para enviar el barrio en la validacion de la direccion
                    if (geograficoPoliticoMgl.getGpoMultiorigen() != null
                            && !geograficoPoliticoMgl.getGpoMultiorigen().trim().isEmpty()
                            && geograficoPoliticoMgl.getGpoMultiorigen().equalsIgnoreCase("1")) {
                        if (requestValidacionViabilidad.getBarrio() == null
                                || requestValidacionViabilidad.getBarrio().trim().isEmpty()) {
                            responseValidacionViabilidad.setCodRespuesta("E");
                            responseValidacionViabilidad.setMensajeRespuesta("LA CIUDAD ES MULTIORIGEN REQUIERE BARRIO");
                            return responseValidacionViabilidad;
                        } else {
                            cityEntity.setBarrio(requestValidacionViabilidad.getBarrio());
                        }
                    }
               
                    Serviceadc service = new Serviceadc();
                    AddressService address = service.queryStandarAddress("C",
                            requestValidacionViabilidad.getDireccion(),
                            cityEntity.getDpto(),
                            cityEntity.getCityName(),
                            cityEntity.getBarrio());

                    String[] nodos = {address.getNodoUno(), address.getNodoDos(), address.getNodoTres(), address.getNodoDth()};
                    for (int i = 0; i < 4; i++) {
                        if (nodos[i] != null) {// 
                            if (nodos[i].length() > 0) {//SI NO ESTA VACIO
                                if (i == 2 || i == 1) {
                                    nodos[i] = "UNIDIRECCIONAL";
                                }
                                if (i == 0) {
                                    nodos[i] = "BIDIRECCIONAL";
                                }
                                if (i == 3) {
                                    nodos[i] = "DTH";
                                }
                            } else {//SI ESTÁ VACÍO
                                nodos[i] = null;
                            }
                        }
                    }
                        
                    return procesarViabilidadSinPcml(address.getLeveleconomic(), nodos, requestValidacionViabilidad);
                   
                }else{
                    responseValidacionViabilidad.setCodRespuesta("E");
                    responseValidacionViabilidad.setResultadoValidacion(true);
                    responseValidacionViabilidad.setMensajeRespuesta(
                            "La viabiliada se ejecuta unicamente para direcciones tipo CK");
                    return responseValidacionViabilidad;
                }
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        responseValidacionViabilidad.setCodRespuesta("I");
        responseValidacionViabilidad.setResultadoValidacion(false);
        responseValidacionViabilidad.setMensajeRespuesta("NO SE PUDO PROCESAR");
        return responseValidacionViabilidad;
    }

    /**
     * Procesa la viabilidad de la peticion segun los parametros enviados. 
     * Se buscan las basicas de los parametros y se validan con la basica del
     * proyecto. Si el parametro proyecto es null o vacia se retorna un error
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param estadoNodo parametro que representa el nodo
     * @param estadoCM parametro de la cuentra matriz
     * @param estadoHhpp parametro de la unidad o hhpp
     * @param estrato parametro del estrato
     * @param tipoRed parametro del tipo de red
     * @param segmento parametro del tipo desegmento
     * @param proyecto parametro del tipo proyecto
     * @return una instancia de ResponseValidacionViabilidad o null en caso
     * contrario
     */
    private ResponseValidacionViabilidad procesarViabilidad(CmtBasicaMgl estadoNodo,
            Map<String, String> estadoCM, String estadoHhpp, String estrato,
            String tipoRed, String segmento, String proyecto) {

        try {
            CmtBasicaMgl basicaProyecto = getBasica(proyecto, Constant.TIPO_BASICA_PROYECTO_BASICA_ID);

            CmtBasicaMgl basicaCuentaMatriz = null;

            if (estadoCM != null) {
                String valorCM = estadoCM.get("CodigoEstadoCM");
                StringBuilder real = new StringBuilder("");
                boolean sw = true;
                for (char c : valorCM.toCharArray()) {
                    if (sw) {//si es cero a la izquierda
                        if (c != '0') {
                            real = new StringBuilder("");
                            sw = false;
                            real.append("" + c);
                        }
                    } else {
                        real.append("" + c);
                    }
                }
                basicaCuentaMatriz = getBasica(real.toString(),
                        Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            }

            CmtBasicaMgl basicaEstrato = getBasica(estrato,Constant.TIPO_BASICA_ESTRATOS_HHPP);

            CmtBasicaMgl basicaEstadoNodo = getBasica(estadoNodo.getCodigoBasica(), Constant.TIPO_BASICA_ESTADOS_NODOS);

            CmtBasicaMgl basicaEstadoHpp = getBasica(estadoHhpp, Constant.TIPO_BASICA_ESTADOS_HHPP);

            CmtBasicaMgl basicaTipoRed = getBasica(tipoRed, Constant.TIPO_BASICA_TECNOLOGIA);

            CmtBasicaMgl basicaTipoSegmento = getBasica(segmento, Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);

            CmtBasicaMgl[] basicas = {basicaEstrato, basicaEstadoNodo, basicaCuentaMatriz,
                basicaEstadoHpp, basicaTipoRed, basicaTipoSegmento};

            List<ValidacionBasicaParteDto> validacionesPartes = new ArrayList<>();

            for (CmtBasicaMgl basica : basicas) {
                if (basica == null) {
                    continue;
                }
                ValidacionBasicaParteDto basicaParte = new ValidacionBasicaParteDto(basicaProyecto, basica);
                validacionesPartes.add(basicaParte);
            }

            return respuestaViabilidad(validacionesPartes, basicaProyecto);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    private ResponseValidacionViabilidad procesarViabilidadSinPcml(String estrato,
            String[] tipoRed, RequestValidacionViabilidad requestValidacionViabilidad) {

        try {

            boolean respuestaViabilidadEstrato = false;
            boolean respuestaViabilidadCobertura = false;
            boolean respuestaViabilidadCuentaMatriz = false;         
                   
            CmtBasicaMgl basicaEstrato = getBasica(estrato, TIPOBASICA_ESTRATO);
            CmtBasicaMgl basicaProyecto = getBasica(requestValidacionViabilidad.getProyecto(), TIPOBASICA_PROYECTO);

            ResponseValidacionViabilidad response = new ResponseValidacionViabilidad();

            if (basicaProyecto == null) {
                response.setResultadoValidacion(false);
                response.setCodRespuesta("I");
                response.setMensajeRespuesta("No hay proyecto para procesar");
                return response;
            }

            String tabla = null;//nombre de tipo basica
            String codigo = null;//codigo basica
            String descripcion = null; //descripcion basica
                 
            // validando cobertura
            if (tipoRed[0] != null || tipoRed[1] != null || tipoRed[2] != null || tipoRed[3] != null) {

                if (tipoRed != null) {
                    int respuestaViabilidadTipoRed = 0;
                    for (String tr : tipoRed) {
                        if (tr == null) {
                            continue;
                        }
                        CmtBasicaMgl basicaTipoRed = getBasica(tr, TIPOBASICA_TIPO_RED);
                        ValidacionBasicaParteDto basicaParte = new ValidacionBasicaParteDto(basicaProyecto,
                                basicaTipoRed);
                        String viabilidad = buscarViabilidad(basicaParte.getBasicaProyecto(),
                                basicaParte.getBasica());
                        if (viabilidad != null
                                && !viabilidad.trim().equalsIgnoreCase("N")) {
                            String mensaje = buscarMensaje(basicaParte.getBasica(), viabilidad);
                            if (basicaParte.getBasica() != null) {
                                codigo = basicaParte.getBasica().getCodigoBasica();
                                descripcion = basicaParte.getBasica().getDescripcion();
                                if (basicaParte.getBasica().getTipoBasicaObj() != null) {
                                    tabla = basicaParte.getBasica().getTipoBasicaObj().getNombreTipo();
                                }
                            }
                            response.addMensaje(tabla, codigo, descripcion, viabilidad, mensaje);
                            if (viabilidad.equalsIgnoreCase("R")) {// Sale y retorna rechazado
                                response.setResultadoValidacion(false);
                                response.setCodRespuesta("I");
                                response.setMensajeRespuesta("Solicitud Rechazada: ");
                            } else if (viabilidad.equalsIgnoreCase("Y")) {
                                respuestaViabilidadTipoRed++;
                            }
                        }
                    }
                    if (respuestaViabilidadTipoRed > 0) {
                        respuestaViabilidadCobertura = true;
                    }
                }
                response.setResultadoValidacion(false);
                response.setCodRespuesta("I");
                response.setMensajeRespuesta("Solicitud no se pudo evaluar");
            } else {
                respuestaViabilidadCobertura = false;
            }

           //Si es viable el cobertura se evalua el estrato
                ValidacionBasicaParteDto basicaParte
                        = new ValidacionBasicaParteDto(basicaProyecto, basicaEstrato);
                String viabilidad = buscarViabilidad(basicaParte.getBasicaProyecto(),
                        basicaParte.getBasica());
                if (viabilidad != null) {
                    String mensaje = buscarMensaje(basicaParte.getBasica(), viabilidad);
                    if (basicaParte.getBasica() != null) {
                        codigo = basicaParte.getBasica().getCodigoBasica();
                        descripcion = basicaParte.getBasica().getDescripcion();
                        if (basicaParte.getBasica().getTipoBasicaObj() != null) {
                            tabla = basicaParte.getBasica().getTipoBasicaObj().getNombreTipo();
                        }
                    }
                    response.addMensaje(tabla, codigo, descripcion, viabilidad, mensaje);

                    if (viabilidad.equalsIgnoreCase("Y")) {//Sale y retorna rechazado
                        response.setResultadoValidacion(true);
                        response.setCodRespuesta("E");
                        response.setMensajeRespuesta("El HHPP no existe debe continuar con el proceso de creación del mismo.");
                        respuestaViabilidadEstrato = true;
                    } else {
                        response.setResultadoValidacion(false);
                        response.setCodRespuesta("I");
                        response.setMensajeRespuesta("No se puede realizar la venta");
                        respuestaViabilidadEstrato = false;
                    }                   
                }               
                
                /*@author Juan David Hernandez*/
                //validación de cuenta matriz
                 PcmlManager pcmlManager = new PcmlManager();
                 String calle = requestValidacionViabilidad.getCalle();
                 String casa = requestValidacionViabilidad.getUnidad();
                 String apto = "";
                 String comunidad = requestValidacionViabilidad.getComunidad();
                 
                 ArrayList<UnidadStructPcml> unidadesVecinas = pcmlManager.getUnidades(calle, casa, apto, comunidad);
                
                Map<String, String> estadoCM = new HashMap<>();
                estadoCM.put("CodigoEstadoCM", "NA");
                estadoCM.put("DescripcionEstadoCM", "NA");
                 
                 if (unidadesVecinas != null && !unidadesVecinas.isEmpty()) {                     
             
                // se busca la informacion del edificio al cual pertenece la unidad            
                Edificio edificio = pcmlManager.getEdificio(
                        unidadesVecinas.get(0).getIdUnidad().toString());
               
                if (edificio != null && edificio.getCodEdificio() != null
                        && !edificio.getCodEdificio().trim().isEmpty()) {
                    estadoCM = getEstadoCM(edificio);
                }
                 }
                 
                
            CmtBasicaMgl basicaCuentaMatriz = null;
            if (estadoCM != null) {
                String valorCM = estadoCM.get("CodigoEstadoCM");
                String real = null;
                boolean sw = true;
                for (char c : valorCM.toCharArray()) {
                    if (sw) {//si es cero a la izquierda
                        if (c != '0') {
                            sw = false;
                            real = "" + c;
                        }
                    } else {
                        real += "" + c;
                    }
                }
            
                basicaCuentaMatriz = getBasicaPorCodigo(real,
                        TIPOBASICA_ESTADOCUENTAMATRIZ);
            } 
                   ValidacionBasicaParteDto basicaMatriz
                            = new ValidacionBasicaParteDto(basicaProyecto, basicaCuentaMatriz);
                    String viabilidadCuentaMatriz = buscarViabilidad(basicaMatriz.getBasicaProyecto(),
                            basicaMatriz.getBasica());
                    
                    if (viabilidadCuentaMatriz != null) {
                        String mensaje = buscarMensaje(basicaMatriz.getBasica(), viabilidadCuentaMatriz);
                        if (basicaMatriz.getBasica() != null) {
                           codigo = basicaMatriz.getBasica().getCodigoBasica();
                            descripcion = basicaMatriz.getBasica().getDescripcion();
                            if (basicaMatriz.getBasica().getTipoBasicaObj() != null) {
                                tabla = basicaMatriz.getBasica().getTipoBasicaObj().getNombreTipo();
                            }
                        }
                        response.addMensaje(tabla, codigo, descripcion, viabilidadCuentaMatriz, mensaje);
                                                  
                            respuestaViabilidadCuentaMatriz = false;               
                        if (viabilidadCuentaMatriz.equalsIgnoreCase("R")) {//Sale y retorna rechazado
                            response.setResultadoValidacion(false);
                            response.setCodRespuesta("I");
                            response.setMensajeRespuesta("Solicitud Rechazada: ");  
                        } else if (viabilidad.equalsIgnoreCase("Y")) {
                            respuestaViabilidadCuentaMatriz = true;//
                        }
                    }

            if (!respuestaViabilidadEstrato || !respuestaViabilidadCobertura
                    || !respuestaViabilidadCuentaMatriz) {

                response.setResultadoValidacion(false);
                response.setCodRespuesta("I");
                response.setMensajeRespuesta("No existe viabilidad");
                return response;
            } else {
                response.setResultadoValidacion(true);
                response.setCodRespuesta("I");
                response.setMensajeRespuesta("Viabilidad Exitosa");
                return response;
            }

  

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    private CmtBasicaMgl getBasica(String codigoBasica, String identificadorInternoApp)
            throws ApplicationException {
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        return cmtBasicaMglManager.findIdBasica(codigoBasica, identificadorInternoApp);
    }

    private CmtBasicaMgl getBasicaPorCodigo(String codigoBasica, String nombreTipoBasica)
            throws ApplicationException {
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        return cmtBasicaMglManager.findIdBasicaCodigo(codigoBasica, nombreTipoBasica);
    }

    /**
     * busca el estado del nodo el cual puede ser Activo Inactivo
     *
     * @param nodoUnidad codigo del nodo
     * @return estado del nodo. (Activo/Inactivo)
     * @throws ApplicationException
     */
    private CmtBasicaMgl getEstadoNodo(String nodoUnidad) throws ApplicationException {

        NodoRRManager nodoRRManager = new NodoRRManager();

        NodoRR nodoRr = nodoRRManager.findNodo(nodoUnidad);

        CmtBasicaMgl descripcionEstadoNodo = basicaMgl.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO);
        CmtBasicaMgl codEstadoNodo = null;

        CmtBasicaMgl activo = basicaMgl.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO);

        if (nodoRr != null && nodoRr.getEstado() != null) {
            codEstadoNodo = nodoRr.getEstado();
        }

        if (codEstadoNodo != null && activo.getBasicaId() == codEstadoNodo.getBasicaId()) {
            descripcionEstadoNodo = basicaMgl.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO);
        }

        return descripcionEstadoNodo;
    }

    /**
     * Busca los datos del estado cuenta matriz para un edificio
     *
     * @param edificio parametro que representa una instancia de Edificio
     * @return un Map con el valor del codigoEstadoCM y la descripcionEstadoCM
     */
    private Map<String, String> getEstadoCM(Edificio edificio) {
        Map<String, String> map = new HashMap<>();
        map.put("CodigoEstadoCM", edificio.getCodEstadoMultiEdificio());
        map.put("DescripcionEstadoCM", edificio.getNomEstadoMultiEdificio());

        if (!edificio.getEstadoEdificio().equalsIgnoreCase(
                Constant.CUENTA_MATRIZ_ESTADO_MULTIEDIFICIO)) {
            map.put("CodigoEstadoCM", edificio.getEstadoEdificio());
            map.put("DescripcionEstadoCM", edificio.getMatriz());
        }

        return map;
    }

    /**
     *
     * @param validacionesPartes Listado de las validaciones
     * ValidacionBasicaParteDto
     * @param basicaProyecto valor de la instancia que representa el proyecto
     * @return la instancia con los datos de response
     * @throws ApplicationException
     */
    private ResponseValidacionViabilidad respuestaViabilidad(
            List<ValidacionBasicaParteDto> validacionesPartes,
            CmtBasicaMgl basicaProyecto) throws ApplicationException {

        String validacionRegla = null;
        ResponseValidacionViabilidad response = new ResponseValidacionViabilidad();

        if (basicaProyecto == null) {
            response.setResultadoValidacion(false);
            response.setCodRespuesta("I");
            response.setMensajeRespuesta("Na hay proyecto para procesar");
            return response;
        }

        for (ValidacionBasicaParteDto validacionesParte : validacionesPartes) {
            CmtTipoValidacionMgl tipoVal = buscarTipoVal(validacionesParte.getBasica());
            String viabilidad = buscarViabilidad(validacionesParte.getBasicaProyecto(),
                    validacionesParte.getBasica());
            if (viabilidad == null) {
                continue;
            }
            String mensaje = buscarMensaje(validacionesParte.getBasica(), viabilidad);
            validacionesParte.setTipoValidacion(tipoVal);
            validacionesParte.setViabilidad(viabilidad);
            validacionesParte.setMensaje(mensaje);
            //Datos de la basica
            String tabla = null;//nombre de tipo basica
            String codigo = null;//codigo basica
            String descripcion = null; //descripcion basica

            if (validacionesParte.getBasica() != null) {
                codigo = validacionesParte.getBasica().getCodigoBasica();
                descripcion = validacionesParte.getBasica().getDescripcion();
                if (validacionesParte.getBasica().getTipoBasicaObj() != null) {
                    tabla = validacionesParte.getBasica().getTipoBasicaObj().getNombreTipo();
                }
            }
            response.addMensaje(tabla, codigo, descripcion, viabilidad, mensaje);
            if (tipoVal != null) {
                if (validacionRegla == null) {
                    validacionRegla = "";
                } else {
                    validacionRegla += "|";
                }
                validacionRegla += tipoVal.getIdTipoValidacion() + "=" + viabilidad;
            }
        }

        boolean viable = isViable(validacionRegla, basicaProyecto);
        response.setResultadoValidacion(viable);
        response.setCodRespuesta("E");
        response.setMensajeRespuesta("No es viable Continuar con el proceso de Venta");
        response.setNombreProyecto(basicaProyecto.getNombreBasica());
        if (viable) {
            response.setCodRespuesta("I");        
            response.setMensajeRespuesta("Puede Continuar con el proceso de Venta");
        }
        return response;
    }

    /**
     * Invoca la busqueda del tipo de validación para una basica específica
     *
     * @param basica basica para buscar el tipo de validación
     * @return la instancia con el tipo de validación encontrado, en caso de no
     * encontrarlo retorna NULL
     * @throws ApplicationException
     */
    private CmtTipoValidacionMgl buscarTipoVal(CmtBasicaMgl basica)
            throws ApplicationException {
        CmtTipoValidacionMglDaoImpl mglDaoImpl = new CmtTipoValidacionMglDaoImpl();
        return mglDaoImpl.findTipoValidacion(basica);
    }

    /**
     * Invoca la busqueda de la viabilidad de una basica con respecto al
     * proyecto.
     *
     * @param basicaProyecto instancia que representa el proyecto a validar
     * @param basica instancia que representa la basica a validar
     * @return una Y/P/N/NA
     * @throws ApplicationException
     */
    private String buscarViabilidad(CmtBasicaMgl basicaProyecto, CmtBasicaMgl basica)
            throws ApplicationException {
        CmtValidacionProyectoMglManager cvpm = new CmtValidacionProyectoMglManager();
        return cvpm.buscarViabilidad(basicaProyecto, basica);
    }

    /**
     * Se busca un mensaje teniendo en cuenta los parámetros enviados
     *
     * @param basica valor que representa la basica
     * @param viabilidad viabilidad para buscar el mensaje Y/N/P/NA
     * @return el valor del mensaje encontrado
     * @throws ApplicationException
     */
    private String buscarMensaje(CmtBasicaMgl basica, String viabilidad)
            throws ApplicationException {
        CmtMensajeTipoValidacionManager cmtvm = new CmtMensajeTipoValidacionManager();
        return cmtvm.buscarMensaje(basica, viabilidad);
    }

    /**
     * Verifica si regla la validacionRegla es viable para el proyecto
     * basicaProyecto.
     *
     * @param validacionRegla valor que representa la texto como regla a validar
     * @param basicaProyecto descripcion del proyecto a validar
     * @return true si fue viable la validación y false en caso de no serlo
     * @throws ApplicationException
     */
    private boolean isViable(String validacionRegla, CmtBasicaMgl basicaProyecto)
            throws ApplicationException {
        CmtReglaValidacionManager crvm = new CmtReglaValidacionManager();
        return crvm.isViable(validacionRegla, basicaProyecto);
    }

    /**
     * Busca los tipos de validación activos y con id determinado.
     *
     * @param id Identificador del tipo de validación.
     * @return El tipo de validación que se encuentre activo y que corresponda
     * el identificador proporcionado.
     * @throws ApplicationException Lanza las excepciones si no encuentra
     * resultados o cuando los registros buscados son duplicados o un error no
     * conocido para que lo maneje la aplicación.
     */
    public CmtTipoValidacionMgl findByIdActive(BigDecimal id) throws ApplicationException {
        CmtTipoValidacionMglDaoImpl tipoValidacionMglDao = new CmtTipoValidacionMglDaoImpl();
        return tipoValidacionMglDao.findByIdActive(id);
    }

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
            BigDecimal proyectoDetalleSelected) throws ApplicationException {

        List<CmtTipoValidacionMgl> resulList;
        CmtTipoValidacionMglDaoImpl tipoValidacionMglDao = 
                new CmtTipoValidacionMglDaoImpl();
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = 
                new CmtTipoBasicaMglDaoImpl();

        List<CmtTipoBasicaMgl> resultListTipoBasica = cmtTipoBasicaMglDaoImpl
                .findTipoBasicasByProyecto(proyectoDetalleSelected);

        List<CmtTipoValidacionDto> tipoValidacionDtoList = new ArrayList<>();
        if (resultListTipoBasica != null && !resultListTipoBasica.isEmpty()) {
            resulList = tipoValidacionMglDao.findTipoValByTipoBasica(
                    resultListTipoBasica);

            for (CmtTipoValidacionMgl tipoValidacion : resulList) {

                CmtTipoValidacionMgl validacion = new CmtTipoValidacionMgl();
                CmtTipoBasicaMgl tipoBasica = new CmtTipoBasicaMgl();
                CmtTipoValidacionDto tipoValidacionDto = new CmtTipoValidacionDto();

                tipoBasica.setTipoBasicaId(tipoValidacion.getTipoBasicaObj()
                        .getTipoBasicaId());
                tipoBasica.setNombreTipo(tipoValidacion.getTipoBasicaObj()
                        .getNombreTipo());

                validacion.setIdTipoValidacion(tipoValidacion.getIdTipoValidacion());
                validacion.setTipoBasicaObj(tipoBasica);

                tipoValidacionDto.setValueValidacion("Y");
                tipoValidacionDto.setTipoValidacion(validacion);
                tipoValidacionDtoList.add(tipoValidacionDto);
            }
        }

        return tipoValidacionDtoList;
    }

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
    public boolean findTipoValidacionEnUso(BigDecimal idTipoValSelected) throws ApplicationException {
        CmtTipoValidacionMglDaoImpl tipoValidacionMglDao = new CmtTipoValidacionMglDaoImpl();
        CmtReglaValidacionDaoImpl reglaValidacionMglDao = new CmtReglaValidacionDaoImpl();

        boolean tipoValidacionUso = tipoValidacionMglDao.findTipoValidacionEnUso(idTipoValSelected);

        if (tipoValidacionUso) {
            return tipoValidacionUso;
        } else {
            return reglaValidacionMglDao.findReglaValidacionEnUso(idTipoValSelected);
        }
    }
    
    /**
     * valbuenayf Metodo para consultar la viabilidad para a conulta de una
     * direccion
     *
     * @param estadoNodo
     * @param estadoCM
     * @param estadoHhpp
     * @param estrato
     * @param tipoRed
     * @param segmento
     * @param proyecto
     * @return
     */
    public ResponseValidacionViabilidad procesarViabilidadInspira(CmtBasicaMgl estadoNodo,
            Map<String, String> estadoCM, String estadoHhpp, String estrato,
            String tipoRed, String segmento, String proyecto) {
        ResponseValidacionViabilidad responseValVia = null;
        try {
            responseValVia = procesarViabilidad(estadoNodo, estadoCM, estadoHhpp, estrato, tipoRed, segmento, proyecto);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return responseValVia;
    }
    
                 /**
     * Función para validar si el dato recibido es númerico
     * 
     * @author Juan David Hernandez}
     * return true si el dato es úumerico
     * @param cadena
     * @return 
     */
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + nfe.getMessage();
            LOGGER.error(msg);
            return false;
        }
    }
}
