package co.com.claro.mgl.businessmanager.address;

import co.com.claro.ejb.mgl.address.dto.UnidadesConflictoDto;
import co.com.claro.mer.utils.constants.ConstantsDirecciones;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtComunidadRrManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtValidadorDireccionesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.rest.dtos.CmtAddressSuggestedDto;
import co.com.claro.mgl.rest.dtos.CmtCityEntityDto;
import co.com.claro.mgl.rest.dtos.CmtRequestConstruccionDireccionDto;
import co.com.claro.mgl.rest.dtos.CmtSuggestedNeighborhoodsDto;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.visitasTecnicas.business.DetalleDireccionManager;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.facade.DetalleDireccionEJBRemote;
import co.com.claro.visitasTecnicas.facade.ParametrosMultivalorEJB;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.utilws.DataSourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author yimy diaz
 */
public class DireccionesValidacionManager {

    public static final String SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO = "Se produjo un error al momento de ejecutar el método '";
    private final ParametrosMultivalorEJB parametrosMultivalorEJBRemote = new ParametrosMultivalorEJB();
    private static final Logger LOGGER = LogManager.getLogger(DireccionesValidacionManager.class);
    private static final String ADDRESSEJB = "addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote";
    private static final String DETALLEDIRECCIONEJB = "detalleDireccionEJB#co.com.claro.visitasTecnicas.facade.DetalleDireccionEJBRemote";
    private static final int LONGITUD_TIPO_MAZANA_CASA = 99;

    /**
     *
     * @return
     * @throws javax.naming.NamingException @
     */
    public static AddressEJBRemote getAddressEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        return  (AddressEJBRemote) ctx.lookup(ADDRESSEJB);
    }

    /**
     * consulta los datos de Ciudad, Departamento y Pais
     *
     * @param codCity
     * @return
     */
    public CityEntity getCityData(String codCity) {
        try {
            return parametrosMultivalorEJBRemote.getCiudadByCodDane(codCity);
        } catch (ApplicationException e) {
            LOGGER.error("Error al consultando los datos de la Ciudad ", e);
            return null;
        }
    }

    public BigDecimal findSolicitudInProcess(DetalleDireccionEntity detalleDireccionEntity, String comunidad) {
        try {
            return parametrosMultivalorEJBRemote.isSolicitudInProcess(detalleDireccionEntity, comunidad);
        } catch (Exception ex) {
            LOGGER.error("No se puede realizar la consulta de la solicitud para cononcer si esta en proceso", ex);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Valida la direccion ingresada por el usuario
     *
     * @return
     */

    /**
     * @param cityEntityRequest
     * @return queryAddressEnrich
     * @
     */
    public AddressService validarDirAlterna(CityEntity cityEntityRequest) {
        AddressRequest requestSrv = new AddressRequest();
        // Asignar la ciudad
        requestSrv.setCity(cityEntityRequest.getCityName());
        // Asignar el departamento
        requestSrv.setState(cityEntityRequest.getDpto());
        // Asignar la direccion a consultar
        requestSrv.setAddress(cityEntityRequest.getAddress());
        // Asignar el barrio
        requestSrv.setNeighborhood(cityEntityRequest.getBarrio());

        requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);

        if (cityEntityRequest.getCodDane() != null && !cityEntityRequest.getCodDane().isEmpty()) {
            requestSrv.setCodDaneVt(cityEntityRequest.getCodDane());
        }
        return queryAddressEnrich(requestSrv);
    }

    /**
     * se comentarea ya que no esta siendo utilizada desde otras partes del
     * sistema espinosadie public String guardarDireccionRepo(String address,
     * String city, String dpto, String barrio, String user) { try {
     * AddressRequest req = new AddressRequest(); req.setAddress(address);
     * req.setCity(city); req.setState(dpto); req.setNeighborhood(barrio);
     * ResponseMessage response = createAddress(req, user,
     * Constantes.NOMBRE_FUNCIONALIDAD, ""); //message =
     * response.getMessageText(); return response.getIdaddress(); } catch
     * (Exception ex) { LOGGER.error("Error al guardar la direccion ", ex);
     * return null; } }
    *
     * @param nodo
     * @return 
     */

    public static String estandarizaNodo(String nodo) {
        String nodoResult = nodo;
        if (isNodo6080(nodo)) {
            nodoResult = "0" + nodo.trim();
        }
        return nodoResult;
    }

    private static boolean isNodo6080(String nodo) {
        Pattern pat = Pattern.compile("6[0-9]|7[234789]|80");
        Matcher mat = pat.matcher(nodo);
        return !nodo.trim().isEmpty() && (mat.matches());
    }

    /**
     *
     * @param addressRequest
     * @return
     * @
     */
    public static AddressService queryAddressEnrich(AddressRequest addressRequest) {
        AddressService addressService = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                addressService = obj.queryAddressEnrich(addressRequest);
            }
        } catch (NamingException | ApplicationException e) {
            LOGGER.error("Error en queryAddressEnrich. EX000 ".concat(e.getMessage()), e);
        }
        return addressService;
    }

    public static UnidadStructPcml getUnidadesDir(DetalleDireccionEntity direccionEntity, String codCity) throws ApplicationException {
        UnidadStructPcml result = null;
        if (direccionEntity != null) {
            DireccionRRManager direccionRRManager = new DireccionRRManager(direccionEntity, direccionEntity.getMultiOrigen(),null);
            String calle = direccionRRManager.getDireccion().getCalle();
            String casa = direccionRRManager.getDireccion().getNumeroUnidad();
            String apto = direccionRRManager.getDireccion().getNumeroApartamento();
            List<UnidadStructPcml> lista;
            PcmlManager pcmlManager = new PcmlManager();
            lista = pcmlManager.getUnidades(calle, casa, apto, codCity);
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        }
        return result;
    }

    public static ArrayList<String> getDirStandarRR(DetalleDireccionEntity direccionEntity) throws ApplicationException {
        ArrayList<String> formatoRR = new ArrayList<>();
        if (direccionEntity != null) {
            DireccionRRManager direccionRRManager = new DireccionRRManager(direccionEntity, direccionEntity.getMultiOrigen(),null);
            formatoRR.add(0, direccionRRManager.getDireccion().getCalle());
            formatoRR.add(1, direccionRRManager.getDireccion().getNumeroUnidad());
            formatoRR.add(2, direccionRRManager.getDireccion().getNumeroApartamento());
        }
        return formatoRR;
    }

    public static List<DetalleDireccionEntity> queryConsultarDetalle(String idSolicitud) {
        List<DetalleDireccionEntity> direcciones = null;
        try {
            DetalleDireccionEJBRemote obj = getDetalleDireccionEjb();
            if (obj != null) {
                direcciones = obj.consultarDireccionPorSolicitud(idSolicitud);
            }
        } catch (NamingException e) {
            LOGGER.info("Ha ocurrido un error bucando el EJB " + DETALLEDIRECCIONEJB, e);
        } catch (ApplicationException e) {
            LOGGER.error("Error Consultando la informacion del detalle de la direccion ".concat(e.getMessage()), e);
        }
        return direcciones;
    }

    private static DetalleDireccionEJBRemote getDetalleDireccionEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        return (DetalleDireccionEJBRemote) ctx.lookup(DETALLEDIRECCIONEJB);
    }

    public static boolean isIdDirCatastroNull(String idCatastro) {
        return idCatastro == null
                || idCatastro.equals(Constantes.ID_DIR_REPO_NULL)
                || idCatastro.isEmpty();
    }

    /**
     * Objetivo y descripcion.metodo para consultar el servicio pcmlService, y
 traer la informacion de las unidades encontradas
     *
     * @author yimy diaz
     * @param direccionEntity
     * @param codCity
     * @return List<UnidadStructPcml>
     * @throws co.com.claro.mgl.error.ApplicationException
     * @
     */
    public static List<UnidadStructPcml> getUnidadesCruce(
            DetalleDireccionEntity direccionEntity, String codCity) throws ApplicationException {
        List<UnidadStructPcml> lista = null;
        if (direccionEntity != null) {
            DireccionRRManager direccionRRManager
                    = new DireccionRRManager(direccionEntity, direccionEntity.getMultiOrigen(),null);
            String calle = direccionRRManager.getDireccion().getCalle();
            String casa
                    = direccionRRManager.getDireccion().getNumeroUnidad().contains("-")
                    ? direccionRRManager.getDireccion().getNumeroUnidad().split("-")[0]
                    : direccionRRManager.getDireccion().getNumeroUnidad();
            String apto = "";
            PcmlManager pcmlManager = new PcmlManager();
            lista = pcmlManager.getUnidades(calle, casa, apto, codCity);
        }
        return lista;
    }

    /**
     * @param codCity
     * @return
     */
    public boolean validarMultiorigen(String codCity) {
        String query = "SELECT gp.gpo_multiorigen FROM rr_dane rrd LEFT JOIN geografico_politico gp " +
                "ON SUBSTR(SUBSTR('00'||rrd.codigo,-8,LENGTH('00'||rrd.codigo)),1,8)=gp.geo_codigodane " +
                "WHERE rrd.codciudad = ? AND gp.gpo_tipo = ?";
        boolean isMultiOrigen = false;
        try (Connection miConexion = DataSourceFactory.getOracleConnection();
                PreparedStatement miPreparedStatement = miConexion.prepareStatement(query)) {

            miPreparedStatement.setString(1, codCity);
            miPreparedStatement.setString(2, "CIUDAD");

            try (ResultSet miResultSet = miPreparedStatement.executeQuery()) {
                if (miResultSet.next()) {
                    isMultiOrigen = miResultSet.getBoolean("gpo_multiorigen");
                }
            } catch (SQLException e) {
                LOGGER.error("Error al momento de realizar el proceso de consulta | validarMultiorigen : ".concat(e.getMessage()));
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error("Ha ocurrido un error obteniendo la información multiorigen en Dircomponent", e);
        }
        return isMultiOrigen;
    }

    /**
     * Valida una direccion.Realiza las operaciones de validacion en el
 repositorio para verificar existencia. Incluye la validacion de direccion
 nueva y antigua.
     *
     * @author Carlos Leonardo Villamil
     * @param request objeto con la informacion de la direccion a validar.
     * @param mallaNuevaAmbigua TRUE indica si es una dirección ambigua ubicada
     * en malla vial Nueva.
     * @return resultado de la validacion direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CityEntity validaDireccion(
            RequestConstruccionDireccion request, boolean mallaNuevaAmbigua) throws ApplicationException {
        try {

            NegocioParamMultivalor param = new NegocioParamMultivalor();
            GeograficoPoliticoManager geograficoPoliticoMglManager = new GeograficoPoliticoManager();
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            /* @author Juan David Hernandez */
            //Si la comunidad no es un numero (codigoDane) es porque viene de visor en formato RR
            boolean comunidadVisor = isNumeric(request.getComunidad());
            /*Si la comunidad llega desde visor en formato RR se hace la busqueda de la comunidad para 
             obtener el centro poblado y extraer el codigo dane para seguir con el proceso normal de MGL*/
            CmtComunidadRr cmtComunidadRr = new CmtComunidadRr();
            if (!comunidadVisor) {
                CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
                cmtComunidadRr = cmtComunidadRrManager.findComunidadByComunidad(request.getComunidad());
                //Se reemplaza la comunidad RR por el codigo dane del centro poblado
                if (cmtComunidadRr != null && cmtComunidadRr.getCiudad() != null) {
                    request.setComunidad(cmtComunidadRr.getCiudad().getGeoCodigoDane());
                } else {
                    throw new ApplicationException(" Comunidad RR no tiene configurado"
                            + " la ciudad en MGL para obtener el codigo dane");
                }
            }

            //Se reemplaza por las consultas realizadas anteriormente
            CityEntity cityEntity = param.consultaDptoCiudadGeo(request.getComunidad());

            if (cityEntity == null || cityEntity.getCityName() == null
                    || cityEntity.getDpto() == null
                    || cityEntity.getCityName().isEmpty()
                    || cityEntity.getDpto().isEmpty()) {
                throw new ApplicationException("La Ciudad no esta configurada en "
                        + "Direcciones");
            }             

            if (request.getBarrio() != null && !request.getBarrio().isEmpty()) {
                request.getDrDireccion().setBarrio(request.getBarrio().toUpperCase());
            }
            String barrioStr = drDireccionManager.obtenerBarrio(request.getDrDireccion());

            //JDHT   
            if (!comunidadVisor) {
                if (cmtComunidadRr != null && cmtComunidadRr.getCodigoRr() != null
                        && !cmtComunidadRr.getCodigoRr().isEmpty()) {
                    cityEntity.setCodCity(cmtComunidadRr.getCodigoRr());
                } else {
                    throw new ApplicationException(" Comunidad RR se encuentra mal configurada en tablas. ");
                }
            } else {
                cityEntity.setCodCity(request.getComunidad());
            }

            if (barrioStr != null && !barrioStr.isEmpty()) {
                cityEntity.setBarrio(barrioStr.toUpperCase());
            }

            //obtiene la direccion al detalle de manera tabulada 
            DetalleDireccionEntity detalleDireccionActual = request.getDrDireccion().convertToDetalleDireccionEntity();
            cityEntity.setActualDetalleDireccionEntity(detalleDireccionActual);
            cityEntity.setCityId(cityEntity.getCityId());
            cityEntity.setDetalleDireccionEntity(null);
            
            GeograficoPoliticoMgl centroPoblado = geograficoPoliticoMglManager.findCityByCodDane(cityEntity.getCityId().toString());
           
            //Validacion si la ciudad es multiorigen y aun no se le ingresa el barrio a la dirección
            if (centroPoblado.getGpoMultiorigen().trim().equalsIgnoreCase("1")
                    && (barrioStr == null || barrioStr.trim().isEmpty())
                    && request.getDrDireccion().getIdTipoDireccion()
                            .equalsIgnoreCase("CK")) {
                throw new ApplicationException("{multiorigen=1}");
            }

            cityEntity.getActualDetalleDireccionEntity().setMultiOrigen(centroPoblado.getGpoMultiorigen());

            //DIRECCION EN STRING DE LA DIRECCION CONSTRUIDA EN PANTALLA QUE SE ENCUENTRA TABULADA EN EL OBJETO
            String address = drDireccionManager.getDireccion(request.getDrDireccion());
            
            cityEntity.setAddress(address);
            if(request.getDrDireccion() != null && request.getDrDireccion().getIdSolicitud() != null){
                cityEntity.setTipoSolictud(request.getDrDireccion().getIdSolicitud().toString());
            }else{
                 cityEntity.setTipoSolictud("0");
            }
           
            cityEntity.setIdUsuario(request.getIdUsuario());
            /* author Juan David Hernandez */ //DIRECCION HASTA LA PLACA SIN COMPLEMENTOS
            cityEntity.setDireccionSinApto(obtenerDireccionSinApto(request.getDrDireccion()));
            //DIRECCION COMPLETA PRINCIPAL QUE SE ESTA VALIDANDO
            cityEntity.setDireccionPrincipal(request.getDrDireccion());

            //******METODO QUE VA AL GEO (SITIDATA) Y VALIDA LA DIRECCIÓN *******//
            String idCastro = validarDir(cityEntity, mallaNuevaAmbigua, centroPoblado);
            
            if (cityEntity.getDetalleDireccionEntity() != null) {
                DrDireccion responseDrDireccion = new DrDireccion();
                responseDrDireccion.obtenerFromDetalleDireccionEntity(cityEntity.getDetalleDireccionEntity());
                request.setDrDireccion(responseDrDireccion);
            }
            
            if (idCastro != null && !idCastro.trim().isEmpty() && !idCastro.trim().equalsIgnoreCase("0")) {
                request.getDrDireccion().setIdDirCatastro(idCastro);
            }
            
            if (cityEntity.getDireccionRREntityAntigua() == null
                    && cityEntity.getDireccionRREntityNueva() == null) {
                
                DireccionRRManager direccionRRManager
                        = new DireccionRRManager(cityEntity.getActualDetalleDireccionEntity(),
                                centroPoblado.getGpoMultiorigen().trim(),centroPoblado);
                
                cityEntity.setDireccionRREntityNueva(direccionRRManager.getDireccion());
            }            
            
            return cityEntity;
        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en validaDireccion. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Función que extrae la dirección en texto sin el apto de la direccion
     *
     * @author Juan David Hernandez return String direccion extraida del
     * Drdireccion hasta la placa sin el apto.
     * @param drDireccionCompleta
     * @return 
     */
    public String obtenerDireccionSinApto(DrDireccion drDireccionCompleta) {
        try {
            DrDireccion drDireccion = drDireccionCompleta.clone();
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            drDireccion.setCpTipoNivel1(null);
            drDireccion.setCpTipoNivel2(null);
            drDireccion.setCpTipoNivel3(null);
            drDireccion.setCpTipoNivel4(null);
            drDireccion.setCpTipoNivel5(null);
            drDireccion.setCpTipoNivel6(null);
            drDireccion.setCpValorNivel1(null);
            drDireccion.setCpValorNivel2(null);
            drDireccion.setCpValorNivel3(null);
            drDireccion.setCpValorNivel4(null);
            drDireccion.setCpValorNivel5(null);
            drDireccion.setCpValorNivel6(null);
            return drDireccionManager.getDireccion(drDireccion);
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Error al obtener direccion sin apto ", e);
            return null;
        }
    }

    /**
     * Valida una direccion.Realiza las operaciones de validacion en el
     * repositorio y contra sitidata para verificar existencia.Incluye la
 validacion de direccion nueva y antigua.
     *
     * @author Carlos Leonardo Villamil
     * @param cityEntityRequest Valores para validar la direccion.
     * @param mallaNuevaAmbigua TRUE indica si es una dirección ambigua ubicada
     * en malla vial Nueva. Si no se va a realizar proceso de validacion de
     * dirección ambigue enviar el valor FALSE
     * @param centroPoblado
     * @return Id de la direccion si existe en el repositirio.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String validarDir(CityEntity cityEntityRequest, boolean mallaNuevaAmbigua, GeograficoPoliticoMgl centroPoblado) throws ApplicationException {

        cityEntityRequest.setCambioDireccion(false);
        cityEntityRequest.setDireccionRREntityAntigua(null);
        cityEntityRequest.setDireccionRREntityNueva(null);
        cityEntityRequest.setDirIgacAntiguaStr(null);
        cityEntityRequest.setExisteHhppAntiguoNuevo(false);

        DetalleDireccionManager detalleDireccionManager = new DetalleDireccionManager();
        DireccionRRManager direccionRRManager;
        ArrayList<UnidadStructPcml> litUnidadStructPcml = null;
        ArrayList<UnidadStructPcml> litUnidadStructPcmlTemp = null;
        DetalleDireccionEntity detalleDireccionEntityTemp2 = null;
        try {
            /*Consultar el WebService parera obtener los datos de la direccion
             * ingresada */
            AddressService responseSrv;
            AddressRequest requestSrv = new AddressRequest();
            //Asignar la ciudad
            requestSrv.setCity(cityEntityRequest.getCityName());
            //Asignar el departamento
            requestSrv.setState(cityEntityRequest.getDpto());
            //Asignar la direccion a consultar
            requestSrv.setAddress(cityEntityRequest.getAddress());
            //Asignar el barrio
            requestSrv.setNeighborhood(cityEntityRequest.getBarrio());
            requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);

            AddressRequest requestSrvSegunda = new AddressRequest();

            if (centroPoblado != null) {
                if (centroPoblado.getGeoCodigoDane() != null && !centroPoblado.getGeoCodigoDane().isEmpty()) {
                    requestSrv.setCodDaneVt(centroPoblado.getGeoCodigoDane());
                    requestSrvSegunda.setCodDaneVt(cityEntityRequest.getCodDane());
                }
            } else {
                if (cityEntityRequest.getCodDane() != null && !cityEntityRequest.getCodDane().isEmpty()) {
                    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                    requestSrv.setCodDaneVt(cityEntityRequest.getCodDane());
                    requestSrvSegunda.setCodDaneVt(cityEntityRequest.getCodDane());
                    centroPoblado = geograficoPoliticoManager.findCityByCodDaneCp(cityEntityRequest.getCodDane());
                }
            }

            requestSrv.setTipoDireccion("CK");
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            //CONSUME SITIDATA CON DIRECCION PRINCIPAL
            responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
            AddressService responseSrvSegunda;

            // si la direccion tiene direccion alterna se consume el geo nuevamente sino se evita el consumo.
            if (responseSrv != null && responseSrv.getAlternateaddress() != null 
                    && !responseSrv.getAlternateaddress().isEmpty() ) {
                requestSrvSegunda.setCity(cityEntityRequest.getCityName());
                requestSrvSegunda.setState(cityEntityRequest.getDpto());
                requestSrvSegunda.setNeighborhood(cityEntityRequest.getBarrio());
                requestSrvSegunda.setLevel(Constant.TIPO_CONSULTA_COMPLETA);
                //Si la direccion tiene una direccion alterna sea antigua o nueva la georeferencia.           
                requestSrvSegunda.setAddress(responseSrv.getAlternateaddress());
                //CONSUME SITIDATA CON DIRECCION ALTERNA (NUEVA O ANTIGUA)
                responseSrvSegunda = addressEJBRemote.queryAddressEnrich(requestSrvSegunda);
            } else {
                responseSrvSegunda = null;
            }

            cityEntityRequest.setExistencia(responseSrv.getExist());
            //Estandarizacion del Nodo            
            cityEntityRequest.setEstratoDir(responseSrv.getLeveleconomic() == null ? "0"
                    : responseSrv.getLeveleconomic());
            cityEntityRequest.setEstadoDir(responseSrv.getState() == null ? ""
                    : responseSrv.getState());

            //respuesta del objeto del geo de la direccion principal
            if (responseSrv != null) {
                cityEntityRequest.setExistencia(responseSrv.getExist());
                // si el barrio viene nulo o vacio asignamos el que nos entrega el GEO
                if (responseSrv.getNeighborhood() != null
                        && !responseSrv.getNeighborhood().isEmpty()
                        && !responseSrv.getNeighborhood().equalsIgnoreCase("N.N.")) {
                            cityEntityRequest.setBarrio(responseSrv.getNeighborhood().toUpperCase());                    
                }

                //Direccion obtenido del geo
                if (responseSrv.getAddress() != null && !responseSrv.getAddress().trim().isEmpty()) {                   
                    cityEntityRequest.setDireccion(responseSrv.getAddress());
                } else {
                     cityEntityRequest.setDireccion(" ");
                }

                //Barrio sugerido
                if (responseSrv.getAddressSuggested() != null && !responseSrv.getAddressSuggested().isEmpty()) {
                    cityEntityRequest.setBarrioSugerido(responseSrv.getAddressSuggested());
                }
                //confiabilidad de la direccion
                if (responseSrv.getReliability() != null) {
                    cityEntityRequest.setConfiabilidadDir((responseSrv.getReliability()));
                }

                //SI LA VALIDACIÖN ES DE UNA CREACIÓN DE HHPP
                if (cityEntityRequest.getTipoSolictud().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.RR_DIR_CREA_HHPP_0)) {

                    //SI LA DIRECCIÖN PUDO SER TRADUCIDA
                    if (responseSrv.getTraslate().equalsIgnoreCase("TRUE")) {
                        //JDHT   Asigna la direccion texto que retorno el geo como respuesta
                        cityEntityRequest.setDireccionRespuestaGeo(responseSrv.getAddress());

                        if (responseSrv.getAddressCodeFound() != null && responseSrv.getAddressCodeFound().length()
                                != LONGITUD_TIPO_MAZANA_CASA && !responseSrv.getAlternateaddress().trim().isEmpty()
                                && !responseSrv.getAlternateaddress().equalsIgnoreCase("NO CAMBIO")) {

                            //SI LA DIRECCIÓN VALDADA ES NUEVA O ANTIGUA SEGUN EL GEO
                            if ((responseSrv.getSource().equalsIgnoreCase("NUEVA")
                                    || responseSrv.getSource().equalsIgnoreCase("ANTIGUA"))
                                    && (cityEntityRequest.getDireccionSinApto() != null
                                    && !cityEntityRequest.getDireccionSinApto().isEmpty())) {

                                cityEntityRequest.setFuente(responseSrv.getSource());

                                if (responseSrv.getAddressCodeFound() != null && !responseSrv.getAddressCodeFound().isEmpty()) {

                                    direccionRRManager = new DireccionRRManager(cityEntityRequest.getActualDetalleDireccionEntity(),
                                            centroPoblado.getGpoMultiorigen(), centroPoblado);

                                    DireccionRREntity direccionRREntityTemp = null;
                                    DireccionRREntity direccionRREntity = direccionRRManager.getDireccion();                                   

                                    //SI LA DIRECCION TIENE UNA DIRECCION ALTERNA CORRECTAMENTE GEOREFERENCIADA
                                    if (responseSrvSegunda != null && responseSrvSegunda.getAddressCodeFound() != null
                                            && !responseSrvSegunda.getAddressCodeFound().isEmpty()) {

                                        DetalleDireccionEntity detalleDireccionEntityTemp = detalleDireccionManager.conversionADetalleDireccion(
                                                responseSrvSegunda.getAddressCodeFound(), responseSrvSegunda.getAddressCode(),
                                                cityEntityRequest.getCityId(), cityEntityRequest.getBarrio());

                                        detalleDireccionEntityTemp2 = cityEntityRequest.getActualDetalleDireccionEntity().clone();

                                        detalleDireccionEntityTemp2.setTipoviaprincipal(detalleDireccionEntityTemp.getTipoviaprincipal());
                                        detalleDireccionEntityTemp2.setNumviaprincipal(detalleDireccionEntityTemp.getNumviaprincipal());
                                        detalleDireccionEntityTemp2.setLtviaprincipal(detalleDireccionEntityTemp.getLtviaprincipal());
                                        detalleDireccionEntityTemp2.setNlpostviap(detalleDireccionEntityTemp.getNlpostviap());
                                        detalleDireccionEntityTemp2.setBisviaprincipal(detalleDireccionEntityTemp.getBisviaprincipal());
                                        detalleDireccionEntityTemp2.setCuadviaprincipal(detalleDireccionEntityTemp.getCuadviaprincipal());
                                        detalleDireccionEntityTemp2.setTipoviageneradora(detalleDireccionEntityTemp.getTipoviageneradora());
                                        detalleDireccionEntityTemp2.setNumviageneradora(detalleDireccionEntityTemp.getNumviageneradora());
                                        detalleDireccionEntityTemp2.setLtviageneradora(detalleDireccionEntityTemp.getLtviageneradora());
                                        detalleDireccionEntityTemp2.setNlpostviag(detalleDireccionEntityTemp.getNlpostviag());
                                        detalleDireccionEntityTemp2.setBisviageneradora(detalleDireccionEntityTemp.getBisviageneradora());
                                        detalleDireccionEntityTemp2.setCuadviageneradora(detalleDireccionEntityTemp.getCuadviageneradora());
                                        detalleDireccionEntityTemp2.setPlacadireccion( detalleDireccionEntityTemp.getPlacadireccion());

                                        direccionRRManager = new DireccionRRManager(detalleDireccionEntityTemp2,centroPoblado.getGpoMultiorigen(), centroPoblado);

                                        direccionRREntityTemp = direccionRRManager.getDireccion();
                                       
                                        DrDireccion direccionAlterna = obtenerDireccionAlternaConApto(cityEntityRequest.getDireccionPrincipal(),
                                                responseSrvSegunda.getAddress(), centroPoblado);

                                        //Obtiene los hhpp que existen con la dirección Alterna
                                        if (direccionAlterna != null) {
                                            cityEntityRequest.setDireccionAlterna(direccionAlterna);
                                            litUnidadStructPcmlTemp = obtenerHhppByDrDireccion(cityEntityRequest.getDireccionAlterna(),
                                                    centroPoblado, true);
                                        }
                                    }
                                    /*author Juan David Hernandez*/
                                   /*Asignamos la Antigua para hacer uso en el cambio de direccion de unidades de antigua a nueva */
                                    cityEntityRequest.setDireccionAntiguaGeo(obtenerDireccionAntigua(responseSrv,responseSrvSegunda, cityEntityRequest));
                                    /*Asignamos la Nueva para hacer uso en el cambio de direccion de unidades de antigua a nueva */
                                    cityEntityRequest.setDireccionNuevaGeo(obtenerDireccionNueva(responseSrv,responseSrvSegunda, cityEntityRequest));

                                    if (!mallaNuevaAmbigua) {
                                        //JDHT BUSCA HHHPP EN LA DIRECCION NUEVA
                                        litUnidadStructPcml = obtenerHhppByDrDireccion(cityEntityRequest.getDireccionPrincipal(),
                                                centroPoblado, true);
                                        //direccion Digitada es Nueva
                                        if (responseSrv.getSource().equalsIgnoreCase("NUEVA") && responseSrvSegunda != null) {
                                            cityEntityRequest.setDirIgacAntiguaStr(responseSrv.getAlternateaddress());

                                            //Direccion Antigua Existe en MGL, se envia cambio de direccion de Antigua a Nueva
                                            if (litUnidadStructPcmlTemp != null && !litUnidadStructPcmlTemp.isEmpty()) {
                                                cityEntityRequest.setExisteRr("La Dirección es NUEVA["
                                                        + direccionRREntity.getCalle()
                                                        + " " + direccionRREntity.getNumeroUnidad()
                                                        + " " + direccionRREntity.getNumeroApartamento()
                                                        + "] Y existe en MER como ANTIGUA["
                                                        + direccionRREntityTemp.getCalle()
                                                        + " " + direccionRREntityTemp.getNumeroUnidad()
                                                        + " " + direccionRREntityTemp.getNumeroApartamento()
                                                        + "], se modificara el HHPP a la Dirección NUEVA["
                                                        + direccionRREntity.getCalle()
                                                        + " " + direccionRREntity.getNumeroUnidad()
                                                        + " " + direccionRREntity.getNumeroApartamento()
                                                        + "].(C)");
                                                cityEntityRequest.setDireccionRREntityAntigua(direccionRREntityTemp);
                                                cityEntityRequest.setDireccionRREntityNueva(direccionRREntity);
                                                cityEntityRequest.setCambioDireccion(true);
                                                cityEntityRequest.setHhppMglCambioANueva(litUnidadStructPcmlTemp.get(0).getHhppCambioVal());

                                            } else {//Direccion Nueva y Antigua no existe en MER, se envia creacion de HHPP
                                                cityEntityRequest.setExisteRr("La Dirección es NUEVA["
                                                        + direccionRREntity.getCalle()
                                                        + " " + direccionRREntity.getNumeroUnidad()
                                                        + " " + direccionRREntity.getNumeroApartamento()
                                                        + "]");
                                                cityEntityRequest.setDireccionRREntityAntigua(direccionRREntityTemp);
                                                cityEntityRequest.setDireccionRREntityNueva(direccionRREntity);
                                            }

                                        } else {
                                             //dirección Digitada es Antigua
                                            //Direccion Antigua Existe en MGL, se envia cambio de direccion de Antigua a Nueva
                                            cityEntityRequest.setDirIgacAntiguaStr(responseSrv.getAddress());
                                            
                                            if ((litUnidadStructPcml != null && !litUnidadStructPcml.isEmpty())
                                                    && (litUnidadStructPcmlTemp == null || litUnidadStructPcmlTemp.isEmpty())) {
                                                
                                                if (responseSrv.getAmbigua().equals("0")) {
                                                    
                                                    cityEntityRequest.setExisteRr("La Dirección es ANTIGUA["
                                                            + direccionRREntity.getCalle()
                                                            + " " + direccionRREntity.getNumeroUnidad()
                                                            + " " + direccionRREntity.getNumeroApartamento()
                                                            + "], Y existe en MER, se modificara el HHPP a la Direccion NUEVA["
                                                            + direccionRREntityTemp.getCalle()
                                                            + " " + direccionRREntityTemp.getNumeroUnidad()
                                                            + " " + direccionRREntityTemp.getNumeroApartamento()
                                                            + "].(C)");
                                                    
                                                    cityEntityRequest.setDireccionRREntityAntigua(direccionRREntity);
                                                    cityEntityRequest.setDireccionRREntityNueva(direccionRREntityTemp);
                                                    cityEntityRequest.setDetalleDireccionEntity(detalleDireccionEntityTemp2);
                                                    cityEntityRequest.setCambioDireccion(true);
                                                    cityEntityRequest.setHhppMglCambioANueva(litUnidadStructPcml.get(0).getHhppCambioVal());
                                                }
                                                
                                            } else {//Direccion Nueva existe en RR, no se deja crear la solicitud
                                                if (litUnidadStructPcmlTemp != null
                                                        && !litUnidadStructPcmlTemp.isEmpty()) {
                                                    cityEntityRequest.setExisteRr("La Direccion es ANTIGUA["
                                                            + direccionRREntity.getCalle()
                                                            + " " + direccionRREntity.getNumeroUnidad()
                                                            + " " + direccionRREntity.getNumeroApartamento()
                                                            + "] Y existe en MER como NUEVA["
                                                            + direccionRREntityTemp.getCalle()
                                                            + " " + direccionRREntityTemp.getNumeroUnidad()
                                                            + " " + direccionRREntityTemp.getNumeroApartamento()
                                                            + "], No se puede generar Solictud.(N)");
                                                    cityEntityRequest.setDireccionRREntityAntigua(direccionRREntity);
                                                } else {//Direccion Nueva y Antigua no existe en RR, se envia creacion de HHPP

                                                    cityEntityRequest.setExisteRr("La Dirección ingresada es ANTIGUA["
                                                            + direccionRREntity.getCalle()
                                                            + " " + direccionRREntity.getNumeroUnidad()
                                                            + " " + direccionRREntity.getNumeroApartamento()
                                                            + "] Existe una dirección más reciente. Dirección Nueva:["
                                                            + direccionRREntityTemp.getCalle()
                                                            + " " + direccionRREntityTemp.getNumeroUnidad()
                                                            + " " + direccionRREntityTemp.getNumeroApartamento()
                                                            + "].");
                                                    cityEntityRequest.setDetalleDireccionEntity(
                                                            detalleDireccionEntityTemp2);
                                                    cityEntityRequest.setDireccionRREntityAntigua(direccionRREntity);
                                                    cityEntityRequest.setDireccionRREntityNueva(direccionRREntityTemp);
                                                }
                                            }
                                        }
                                    }
                                    //@Juan David Hernandez
                                    //validamos la existencia de algun hhpp en la direccion antigua y nueva                       

                                    // SI TIENE UNIDADES CON LA DIRECCION ACTUAL
                                    if (litUnidadStructPcml != null && !litUnidadStructPcml.isEmpty() && litUnidadStructPcmlTemp != null
                                            && !litUnidadStructPcmlTemp.isEmpty()) {
                                        boolean existeHhppAntiguoNuevo = false;
                                        for (UnidadStructPcml u : litUnidadStructPcml) {
                                            for (UnidadStructPcml d : litUnidadStructPcmlTemp) {                                                
                                                if (d.getAptoUnidad().equalsIgnoreCase(u.getAptoUnidad())) {
                                                    existeHhppAntiguoNuevo = true;
                                                    break;
                                                }
                                            }
                                            if (existeHhppAntiguoNuevo) {
                                                break;
                                            }
                                        }
                                        cityEntityRequest.setExisteHhppAntiguoNuevo(existeHhppAntiguoNuevo);
                                    }

                                    //validamos la existencia de unidades en el predio
                                    //para realizar es cambio de esas unidades
                                    if (!cityEntityRequest.isCambioDireccion() && cityEntityRequest.getDireccionAlterna() != null 
                                            && responseSrv.getSource().equalsIgnoreCase("NUEVA") && responseSrvSegunda != null
                                            && responseSrvSegunda .getSource().equalsIgnoreCase("ANTIGUA")) {                                       

                                        if (litUnidadStructPcmlTemp != null && !litUnidadStructPcmlTemp.isEmpty()) {
                                            for (UnidadStructPcml u : litUnidadStructPcmlTemp) {
                                                if (u.getCalleUnidad().equalsIgnoreCase(direccionRREntity.getCalle())
                                                        && u.getCasaUnidad().equalsIgnoreCase(direccionRREntity.getNumeroUnidad())) {
                                                    cityEntityRequest.setCambioDireccion(true);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //si es una validación de creación de HHPP realiza este proceso
            if (cityEntityRequest.getTipoSolictud().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.RR_DIR_CREA_HHPP_0)) {
                //Si la dirección principal pudo ser traducida por el GEO
                if (responseSrv.getTraslate().equalsIgnoreCase("TRUE")) {
                    if (responseSrv.getAddressCodeFound() != null
                            && responseSrv.getAddressCodeFound().length()
                            != LONGITUD_TIPO_MAZANA_CASA
                            && !responseSrv.getAlternateaddress().equalsIgnoreCase("NO CAMBIO")) {
                        //si la dirección principal georeferencia el geo dice que es NUEVA o ANTIGUA
                        if (responseSrv.getSource().equalsIgnoreCase("NUEVA") || responseSrv.getSource().equalsIgnoreCase("ANTIGUA")) {
                            cityEntityRequest.getIdUsuario();
                        }
                    }
                }
            }

            return responseSrv.getIdaddress() == null
                    || responseSrv.getIdaddress().equalsIgnoreCase("0")
                    ? null : responseSrv.getIdaddress();
        } catch (ApplicationException | CloneNotSupportedException ex) {
            LOGGER.error("Error al validar la direccion ", ex);
            throw new ApplicationException("Error georeferenciando dirección ", ex);
        }
    }

    /*
     * Función que obtiene la direccion nueva entre las 2 direccion 
     * georeferenciadas en la validacion de una direccion
     *
     * @param responseSrv primero direccion georeferenciada
     * @param responseSrvSegunda direccion alterna referenciada
     *
     * @author Juan David Hernandez
     */
    public DrDireccion obtenerDireccionNueva(AddressService responseSrv, AddressService responseSrvSegunda,
            CityEntity cityEntityRequest) {
        try {
            if (responseSrv.getSource() != null
                    && responseSrv.getSource().equalsIgnoreCase("NUEVA")
                    && cityEntityRequest.getDireccionPrincipal() != null) {

                DrDireccion direccionNueva = cityEntityRequest.getDireccionPrincipal().clone();
                if (direccionNueva != null) {
                    direccionNueva.setCpTipoNivel1(null);
                    direccionNueva.setCpTipoNivel2(null);
                    direccionNueva.setCpTipoNivel3(null);
                    direccionNueva.setCpTipoNivel4(null);
                    direccionNueva.setCpTipoNivel5(null);
                    direccionNueva.setCpTipoNivel6(null);
                    direccionNueva.setCpValorNivel1(null);
                    direccionNueva.setCpValorNivel2(null);
                    direccionNueva.setCpValorNivel3(null);
                    direccionNueva.setCpValorNivel4(null);
                    direccionNueva.setCpValorNivel5(null);
                    direccionNueva.setCpValorNivel6(null);
                    return direccionNueva;
                }
            } else {
                if (responseSrvSegunda != null && responseSrvSegunda.getSource() != null
                        && responseSrvSegunda.getSource().equalsIgnoreCase("NUEVA")
                        && cityEntityRequest.getDireccionAlterna() != null) {

                    DrDireccion direccionNueva = cityEntityRequest.getDireccionAlterna().clone();
                    if (direccionNueva != null) {
                        direccionNueva.setCpTipoNivel1(null);
                        direccionNueva.setCpTipoNivel2(null);
                        direccionNueva.setCpTipoNivel3(null);
                        direccionNueva.setCpTipoNivel4(null);
                        direccionNueva.setCpTipoNivel5(null);
                        direccionNueva.setCpTipoNivel6(null);
                        direccionNueva.setCpValorNivel1(null);
                        direccionNueva.setCpValorNivel2(null);
                        direccionNueva.setCpValorNivel3(null);
                        direccionNueva.setCpValorNivel4(null);
                        direccionNueva.setCpValorNivel5(null);
                        direccionNueva.setCpValorNivel6(null);
                        return direccionNueva;
                    }
                }
            }
            return null;
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Error al intentar obtener la dirección antigua ", e);
            return null;
        }
    }

    /*
     * Función que obtiene la direccion antigua entre las 2 direccion 
     * georeferenciadas en la validacion de una direccion
     *
     * @param responseSrv primero direccion georeferenciada
     * @param responseSrvSegunda direccion alterna referenciada
     *
     * @author Juan David Hernandez
     */
    public DrDireccion obtenerDireccionAntigua(AddressService responseSrv, AddressService responseSrvSegunda,
            CityEntity cityEntityRequest) {
        try {
            if (responseSrv.getSource() != null && responseSrv.getSource().equalsIgnoreCase("ANTIGUA")
                    && cityEntityRequest.getDireccionPrincipal() != null) {

                DrDireccion direccionAntigua = cityEntityRequest.getDireccionPrincipal().clone();
                if (direccionAntigua != null) {
                    direccionAntigua.setCpTipoNivel1(null);
                    direccionAntigua.setCpTipoNivel2(null);
                    direccionAntigua.setCpTipoNivel3(null);
                    direccionAntigua.setCpTipoNivel4(null);
                    direccionAntigua.setCpTipoNivel5(null);
                    direccionAntigua.setCpTipoNivel6(null);
                    direccionAntigua.setCpValorNivel1(null);
                    direccionAntigua.setCpValorNivel2(null);
                    direccionAntigua.setCpValorNivel3(null);
                    direccionAntigua.setCpValorNivel4(null);
                    direccionAntigua.setCpValorNivel5(null);
                    direccionAntigua.setCpValorNivel6(null);
                    return direccionAntigua;
                }
            } else {
                if (responseSrvSegunda != null && responseSrvSegunda.getSource() != null
                        && responseSrvSegunda.getSource().equalsIgnoreCase("ANTIGUA")
                        && cityEntityRequest.getDireccionAlterna() != null) {

                    DrDireccion direccionAntigua = cityEntityRequest.getDireccionAlterna().clone();
                    if (direccionAntigua != null) {
                        direccionAntigua.setCpTipoNivel1(null);
                        direccionAntigua.setCpTipoNivel2(null);
                        direccionAntigua.setCpTipoNivel3(null);
                        direccionAntigua.setCpTipoNivel4(null);
                        direccionAntigua.setCpTipoNivel5(null);
                        direccionAntigua.setCpTipoNivel6(null);
                        direccionAntigua.setCpValorNivel1(null);
                        direccionAntigua.setCpValorNivel2(null);
                        direccionAntigua.setCpValorNivel3(null);
                        direccionAntigua.setCpValorNivel4(null);
                        direccionAntigua.setCpValorNivel5(null);
                        direccionAntigua.setCpValorNivel6(null);
                        return direccionAntigua;
                    }
                }
            }
            return null;
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Error al intentar obtener la dirección antigua ", e);
            return null;
        }
    }

    /**
     * Función que obtiene los hhpp asociados a una dirección que se recibe en
     * String y se convierte a DrDireccion
     *
     * @param drDireccion     direccion que deseamos buscarle hhpp
     *                        asociados
     * @param centroPoblado   centro poblado de la direccion a buscar
     * @param drDireccionApto true para indicar si se desea realizar la busqueda
     *                        con apto de la direccion
     * @author Juan David Hernandez
     * @return {@link ArrayList<UnidadStructPcml>}
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ArrayList<UnidadStructPcml> obtenerHhppByDrDireccion(
            DrDireccion drDireccion, GeograficoPoliticoMgl centroPoblado,
            boolean drDireccionApto) throws ApplicationException {
        ArrayList<UnidadStructPcml> unidadesList = new ArrayList<>();
                try {
            DrDireccion drDireccionActual = drDireccion.clone();
            
            if (drDireccionActual != null && centroPoblado != null) {                
                CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
                //Si es False se le quita el apto al DrDireccion
                if (!drDireccionApto) {
                    drDireccionActual.setCpTipoNivel1(null);
                    drDireccionActual.setCpTipoNivel2(null);
                    drDireccionActual.setCpTipoNivel3(null);
                    drDireccionActual.setCpTipoNivel4(null);
                    drDireccionActual.setCpTipoNivel5(null);
                    drDireccionActual.setCpTipoNivel6(null);
                    drDireccionActual.setCpValorNivel1(null);
                    drDireccionActual.setCpValorNivel2(null);
                    drDireccionActual.setCpValorNivel3(null);
                    drDireccionActual.setCpValorNivel4(null);
                    drDireccionActual.setCpValorNivel5(null);
                    drDireccionActual.setCpValorNivel6(null);
                }
                //Obtenemos los hhpp asociados a la dirección alterna
                List<HhppMgl> hhppDirList = cmtDireccionDetalleMglManager
                        .findHhppByDireccion(drDireccionActual, centroPoblado.getGpoId(), true,
                                0, 0, false);

                //Los hhpp encontrados los pasamos a la estructura de unidades.
                if (hhppDirList != null && !hhppDirList.isEmpty()) {
                    for (HhppMgl hhppMgl : hhppDirList) {
                        UnidadStructPcml unidad = new UnidadStructPcml();
                        unidad.setCalleUnidad(hhppMgl.getHhpCalle() != null
                                ? hhppMgl.getHhpCalle() : "");
                        unidad.setCasaUnidad(hhppMgl.getHhpPlaca() != null
                                ? hhppMgl.getHhpPlaca() : "");
                        unidad.setAptoUnidad(hhppMgl.getHhpApart() != null
                                ? hhppMgl.getHhpApart() : "");
                        unidad.setEstadUnidadad(hhppMgl.getEhhId().getEhhNombre()
                                != null ? hhppMgl.getEhhId().getEhhNombre() : "");
                        //si el hhpp es subdireccion saca el estrato de la subdieccion
                        if(hhppMgl.getSubDireccionObj() != null){
                             unidad.setEstratoUnidad(hhppMgl.getSubDireccionObj().getSdiEstrato()
                                != null ? hhppMgl.getSubDireccionObj().getSdiEstrato().toString() : "");
                        }else{
                             unidad.setEstratoUnidad(hhppMgl.getDireccionObj().getDirEstrato()
                                != null ? hhppMgl.getDireccionObj().getDirEstrato().toString() : "");
                        }
                       
                        unidad.setNodUnidad(hhppMgl.getNodId().getNodNombre()
                                != null ? hhppMgl.getNodId().getNodNombre() : "");
                        
                        unidad.setHhppCambioVal(hhppMgl);
                        unidadesList.add(unidad);
                    }
                }
            }
        } catch (ApplicationException | CloneNotSupportedException ex) {
            LOGGER.error("Error al obtener Hhpp por DireccionStr ".concat(ex.getMessage()), ex);
        }
        return unidadesList;
    }

    /**
     * Función que obtiene la direccion alterna en String con el apto incluido y
     * extraido de la direccion principal
     *
     * @param direccionPrincipal drDireccion de la direccion principal de la
     * cual se extrae el apto.
     * @param direccionAlternaStr String de la direccion alterna al a cual se le
     * introducira el apto.
     *
     * @author Juan David Hernandez
     * @param centroPoblado
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DrDireccion obtenerDireccionAlternaConApto(DrDireccion direccionPrincipal,
            String direccionAlternaStr, GeograficoPoliticoMgl centroPoblado)
            throws ApplicationException {
        try {
            DrDireccion drDireccionAlternaApto = null;

            if (centroPoblado != null && direccionAlternaStr != null && !direccionAlternaStr.isEmpty()
                    && direccionPrincipal != null) {

                CmtValidadorDireccionesManager validadorDireccionesManager = new CmtValidadorDireccionesManager();

                drDireccionAlternaApto = validadorDireccionesManager
                        .convertirDireccionStringADrDireccion(direccionAlternaStr, centroPoblado.getGpoId());

                drDireccionAlternaApto.setCpTipoNivel1(direccionPrincipal.getCpTipoNivel1()
                        != null ? direccionPrincipal.getCpTipoNivel1() : null);
                drDireccionAlternaApto.setCpTipoNivel2(direccionPrincipal.getCpTipoNivel2()
                        != null ? direccionPrincipal.getCpTipoNivel2() : null);
                drDireccionAlternaApto.setCpTipoNivel3(direccionPrincipal.getCpTipoNivel3()
                        != null ? direccionPrincipal.getCpTipoNivel3() : null);
                drDireccionAlternaApto.setCpTipoNivel4(direccionPrincipal.getCpTipoNivel4()
                        != null ? direccionPrincipal.getCpTipoNivel4() : null);
                drDireccionAlternaApto.setCpTipoNivel5(direccionPrincipal.getCpTipoNivel5()
                        != null ? direccionPrincipal.getCpTipoNivel5() : null);
                drDireccionAlternaApto.setCpTipoNivel6(direccionPrincipal.getCpTipoNivel6()
                        != null ? direccionPrincipal.getCpTipoNivel6() : null);

                drDireccionAlternaApto.setCpValorNivel1(direccionPrincipal.getCpValorNivel1()
                        != null ? direccionPrincipal.getCpValorNivel1() : null);
                drDireccionAlternaApto.setCpValorNivel2(direccionPrincipal.getCpValorNivel2()
                        != null ? direccionPrincipal.getCpValorNivel2() : null);
                drDireccionAlternaApto.setCpValorNivel3(direccionPrincipal.getCpValorNivel3()
                        != null ? direccionPrincipal.getCpValorNivel3() : null);
                drDireccionAlternaApto.setCpValorNivel4(direccionPrincipal.getCpValorNivel4()
                        != null ? direccionPrincipal.getCpValorNivel4() : null);
                drDireccionAlternaApto.setCpValorNivel5(direccionPrincipal.getCpValorNivel5()
                        != null ? direccionPrincipal.getCpValorNivel5() : null);
                drDireccionAlternaApto.setCpValorNivel6(direccionPrincipal.getCpValorNivel6()
                        != null ? direccionPrincipal.getCpValorNivel6() : null);
            }
            return drDireccionAlternaApto;
        } catch (ApplicationException ex) {
            LOGGER.error("Error al obtener la direccion alterna en drDireccion con apto ".concat(ex.getMessage()), ex);
            return null;
        }
    }

    public List<AddressSuggested> obtenerBarrioSugerido(RequestConstruccionDireccion request, boolean mallaNuevaAmbigua) {
        List<AddressSuggested> barrioList = new ArrayList<>();
        try {
            CityEntity cityEntityRequest = new CityEntity();
            cityEntityRequest.setCodCity(request.getComunidad());
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String direccion = drDireccionManager.getDireccion(request.getDrDireccion());
            cityEntityRequest.setAddress(direccion);
            //Enviamos tipo solicicitud '4' para que no realice la validacion de direccion antigua nueva
            cityEntityRequest.setTipoSolictud("4");
            CityEntity cityEntity;
            NegocioParamMultivalor param = new NegocioParamMultivalor();

            /* @author Juan David Hernandez */
            //Si la comunidad no es un numero (codigoDane) es porque viene de visor en formato RR
            boolean comunidadVisor = isNumeric(request.getComunidad());
            /*Si la comunidad llega desde visor en formato RR se hace la busqueda de la comunidad para 
             obtener el centro poblado y extraer el codigo dane para seguir con el proceso normal de MGL*/
            if (!comunidadVisor) {
                CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
                CmtComunidadRr cmtComunidadRr
                        = cmtComunidadRrManager.findComunidadByComunidad(request.getComunidad());
                //Se reemplaza la comunidad RR por el codigo dane del centro poblado
                if (cmtComunidadRr != null && cmtComunidadRr.getCiudad() != null) {
                    request.setComunidad(cmtComunidadRr.getCiudad().getGeoCodigoDane());
                } else {
                    throw new ApplicationException(" Comunidad RR no tiene configurado"
                            + " la ciudad en MGL para obtener el codigo dane");
                }
            }

            cityEntity = param.consultaDptoCiudad(request.getComunidad());

            if (cityEntity != null && cityEntity.getCityName() != null
                    && !cityEntity.getCityName().isEmpty()
                    && cityEntity.getDpto() != null && !cityEntity.getDpto().isEmpty()) {
                cityEntityRequest.setCityName(cityEntity.getCityName());
                cityEntityRequest.setDpto(cityEntity.getDpto());
                cityEntityRequest.setPais(cityEntity.getPais());
                cityEntityRequest.setCodDane(cityEntity.getCodDane());
            } else {
                throw new ApplicationException("La Ciudad no esta configurada en Direcciones");
            }
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPoblado;
            centroPoblado = geograficoPoliticoManager.findCentroPobladoCodDane(cityEntity.getCodDane());
            
            //END 
            cityEntityRequest.setBarrio(".");
            cityEntityRequest.setIdUsuario(request.getIdUsuario());
            validarDir(cityEntityRequest, mallaNuevaAmbigua, centroPoblado);
            
            if (cityEntityRequest.getBarrioSugerido() != null
                    && !cityEntityRequest.getBarrioSugerido().isEmpty()) {
                
                barrioList = new ArrayList<>();
                
                for (AddressSuggested addressSuggested : cityEntityRequest.getBarrioSugerido()) {
                    if (cityEntityRequest.getDireccion().trim().
                            equalsIgnoreCase(addressSuggested.getAddress().trim())) {
                        barrioList.add(addressSuggested);
                    }
                }
                
                List <String> barriosSugeridosList = new ArrayList<>();
                String direccionBarrio = "";
                
                if (!barrioList.isEmpty()) {
                    /*Se extrae la direccion de cualquier registro del listado de barrios
                    para posteriormente setearlo en el listado de barrios final*/
                    direccionBarrio = barrioList.get(0).getAddress();
                    //Se carga listado de solo Strings de barrios para eliminar repetidos
                    for (AddressSuggested addressSuggested : barrioList) {
                        barriosSugeridosList.add(addressSuggested.getNeighborhood());
                    }
                    
                 //se crea estructura que nos ayuda a eliminar repetidos
                HashSet hs = new HashSet();
                //Lo cargamos con los valores del array, esto hace quite los repetidos
                hs.addAll(barriosSugeridosList);
                //se limpia el listado original
                barriosSugeridosList.clear();                
                //se agregan nuevamente los barrios ya sin registros repetidos
                barriosSugeridosList.addAll(hs);                

                    if (!barriosSugeridosList.isEmpty()) {
                        barrioList.clear();
                        for (String barrioFinal : barriosSugeridosList) {
                            AddressSuggested barrio = new AddressSuggested();
                            barrio.setAddress(direccionBarrio);
                            barrio.setNeighborhood(barrioFinal);
                            barrioList.add(barrio);
                        }
                    }else{
                        barrioList = new ArrayList<>();
                    }
                    
                }else{
                     barrioList = new ArrayList<>();
                }

            } else {
                barrioList = new ArrayList<>();
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error Cargando lista de Barrio sugerido ".concat(ex.getMessage()), ex);
        }
        return barrioList;
    }

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    /**
     * Valida estructura de una direccion.
     *
     * @author Juan David Hernandez
     * @param direccion objeto con la informacion de la direccion a validar.
     * @param tipoValidacionCM
     *
     * @return true si la direccion contiene lo minimo de la validacion
     * direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean validarEstructuraDireccion(DrDireccion direccion, String tipoValidacionCM) throws ApplicationException {
        try {
            if (direccion == null) {
                throw new ApplicationException("No es posible realizar la validación por"
                        + " datos incompletos en la construcción de la dirección. ");
            }            
            
            
            /*Valida por tipo de dirección que contenga el minino de elementros 
             ingresados a la construcción de la dirección */
            if (direccion != null &&
                    direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty() 
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {

                if (direccion.getTipoViaPrincipal() == null
                        || direccion.getTipoViaPrincipal().trim().isEmpty()) {
                    throw new ApplicationException("Es necesario ingresar el "
                            + "TIPO VIA PRINCIPAL. ");
                }
                if (direccion.getNumViaPrincipal() == null
                        || direccion.getNumViaPrincipal().trim().isEmpty()) {
                    throw new ApplicationException("Es necesario ingresar la "
                            + "NUM VIA PRINCIPAL. ");
                }
                
                if (direccion.getNumViaGeneradora() == null
                        || direccion.getNumViaGeneradora().trim().isEmpty()) {
                    throw new ApplicationException("Es necesario ingresar la NUM "
                            + "VIA GENERADORA. ");
                }
                if (direccion.getPlacaDireccion() == null
                        || direccion.getPlacaDireccion().trim().isEmpty()) {
                    throw new ApplicationException("Es necesario ingresar la PLACA de"
                            + " la dirección. ");
                }

            } else if (direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty() 
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("BM")) {
                if ((direccion.getMzValorNivel1() == null
                        || direccion.getMzValorNivel1().trim().isEmpty()) && (direccion.getMzValorNivel2() == null
                        || direccion.getMzValorNivel2().trim().isEmpty()) && (direccion.getMzValorNivel3() == null
                        || direccion.getMzValorNivel3().trim().isEmpty()) && (direccion.getMzValorNivel4() == null
                        || direccion.getMzValorNivel4().trim().isEmpty()) && (direccion.getMzValorNivel5() == null
                        || direccion.getMzValorNivel5().trim().isEmpty()) && (direccion.getMzValorNivel6() == null
                        || direccion.getMzValorNivel6().trim().isEmpty())) {
                    throw new ApplicationException("Es necesario ingresar un nivel  "
                            + "  para la dirección barrio-manzana. ");
                }
            } else if (direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty() 
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("IT")) {

                if ((direccion.getMzValorNivel1() == null
                        || direccion.getMzValorNivel1().trim().isEmpty()) && (direccion.getMzValorNivel2() == null
                        || direccion.getMzValorNivel2().trim().isEmpty()) && (direccion.getMzValorNivel3() == null
                        || direccion.getMzValorNivel3().trim().isEmpty()) && (direccion.getMzValorNivel4() == null
                        || direccion.getMzValorNivel4().trim().isEmpty()) && (direccion.getMzValorNivel5() == null
                        || direccion.getMzValorNivel5().trim().isEmpty()) && (direccion.getMzValorNivel6() == null
                        || direccion.getMzValorNivel6().trim().isEmpty())) {
                    throw new ApplicationException("Es necesario ingresar un nivel  "
                            + "  para la dirección intraducible. ");
                }
                if (direccion.getItTipoPlaca() == null
                        || direccion.getItTipoPlaca().isEmpty()
                        || direccion.getItValorPlaca() == null
                        || direccion.getItValorPlaca().isEmpty()) {
                    throw new ApplicationException("Es necesario ingresar el valor"
                            + " de la Placa para la dirección intraducible. ");
                }
            }

            if (!co.com.claro.mgl.utils.Constant.TIPO_VALIDACION_DIR_CM.equalsIgnoreCase(tipoValidacionCM)) {
                /*Valida que el complemento Apartamento si tiene "+" contenga los 
                 2 valores */
                if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                        .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPT_PISO_INTERIOR)) {

                    if (direccion.getCpValorNivel5() == null
                            || direccion.getCpValorNivel5().isEmpty()
                            || direccion.getCpValorNivel6() == null
                            || direccion.getCpValorNivel6().isEmpty()) {
                        throw new ApplicationException("Debe ingresar los 2 valores del nivel "
                                + "apartamento: PISO + INTERIOR");
                    }
                } else {
                    if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                            .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPT_PISO_LOCAL)) {
                        if (direccion.getCpValorNivel5() == null
                                || direccion.getCpValorNivel5().isEmpty()
                                || direccion.getCpValorNivel6() == null
                                || direccion.getCpValorNivel6().isEmpty()) {
                            throw new ApplicationException("Debe ingresar los 2 valores del nivel "
                                    + "apartamento: PISO + LOCAL");
                        }
                    } else {
                        if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                                .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPT_PISO_APTO)) {
                            if (direccion.getCpValorNivel5() == null
                                    || direccion.getCpValorNivel5().isEmpty()
                                    || direccion.getCpValorNivel6() == null
                                    || direccion.getCpValorNivel6().isEmpty()) {
                                throw new ApplicationException("Debe ingresar los 2 valores del nivel "
                                        + "apartamento: PISO + APARTAMENTO");
                            }
                        } else {
                            if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                                    .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPT_CASA_PISO)) {
                                if (direccion.getCpValorNivel5() == null
                                        || direccion.getCpValorNivel5().isEmpty()
                                        || direccion.getCpValorNivel6() == null
                                        || direccion.getCpValorNivel6().isEmpty()) {
                                    throw new ApplicationException("Debe ingresar los 2 valores del nivel "
                                            + "apartamento: CASA + PISO");
                                }
                            }
                        }
                    }
                }
            }
            return true;

        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al validar la direccion. EX000: " + e.getMessage(), e);
        }
    }
    
    /**
     * Valida estructura de una direccion este completa y correctamente construida
     *
     * @author Juan David Hernandez
     * @param direccion objeto con la informacion de la direccion a validar.
     * @param tipoValidacionCM
     *
     * @return true si la direccion contiene lo minimo de la validacion.
     * direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean validarEstructuraDireccionBoolean(DrDireccion direccion, String tipoValidacionCM) throws ApplicationException {
        try {
            if (direccion == null) {
                throw new ApplicationException("No es posible realizar la validación por"
                        + " datos incompletos en la construcción de la dirección. ");
            }            
            
            
            /*Valida por tipo de dirección que contenga el minino de elementros 
             ingresados a la construcción de la dirección */
            if (direccion != null &&
                    direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty() 
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {

                if (direccion.getTipoViaPrincipal() == null
                        || direccion.getTipoViaPrincipal().trim().isEmpty()) {
                    return false;
                }
                if (direccion.getNumViaPrincipal() == null
                        || direccion.getNumViaPrincipal().trim().isEmpty()) {
                     return false;
                }
                if (direccion.getNumViaGeneradora() == null
                        || direccion.getNumViaGeneradora().trim().isEmpty()) {
                     return false;
                }
                if (direccion.getPlacaDireccion() == null
                        || direccion.getPlacaDireccion().trim().isEmpty()) {
                     return false;
                }

            } else if (direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty() 
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("BM")) {
                if ((direccion.getMzValorNivel1() == null
                        || direccion.getMzValorNivel1().trim().isEmpty()) && (direccion.getMzValorNivel2() == null
                        || direccion.getMzValorNivel2().trim().isEmpty()) && (direccion.getMzValorNivel3() == null
                        || direccion.getMzValorNivel3().trim().isEmpty()) && (direccion.getMzValorNivel4() == null
                        || direccion.getMzValorNivel4().trim().isEmpty()) && (direccion.getMzValorNivel5() == null
                        || direccion.getMzValorNivel5().trim().isEmpty()) && (direccion.getMzValorNivel6() == null
                        || direccion.getMzValorNivel6().trim().isEmpty())) {
                     return false;
                }
            } else if (direccion.getIdTipoDireccion() != null && !direccion.getIdTipoDireccion().isEmpty() 
                    && direccion.getIdTipoDireccion().equalsIgnoreCase("IT")) {

                if ((direccion.getMzValorNivel1() == null
                        || direccion.getMzValorNivel1().trim().isEmpty()) && (direccion.getMzValorNivel2() == null
                        || direccion.getMzValorNivel2().trim().isEmpty()) && (direccion.getMzValorNivel3() == null
                        || direccion.getMzValorNivel3().trim().isEmpty()) && (direccion.getMzValorNivel4() == null
                        || direccion.getMzValorNivel4().trim().isEmpty()) && (direccion.getMzValorNivel5() == null
                        || direccion.getMzValorNivel5().trim().isEmpty()) && (direccion.getMzValorNivel6() == null
                        || direccion.getMzValorNivel6().trim().isEmpty())) {
                    return false;
                }
                if (direccion.getItTipoPlaca() == null
                        || direccion.getItTipoPlaca().isEmpty()
                        || direccion.getItValorPlaca() == null
                        || direccion.getItValorPlaca().isEmpty()) {
                     return false;
                }
            }

            if (!co.com.claro.mgl.utils.Constant.TIPO_VALIDACION_DIR_CM.equalsIgnoreCase(tipoValidacionCM)) {
                /*Valida que el complemento Apartamento si tiene "+" contenga los 
                 2 valores */
                if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                        .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPT_PISO_INTERIOR)) {

                    if (direccion.getCpValorNivel5() == null
                            || direccion.getCpValorNivel5().isEmpty()
                            || direccion.getCpValorNivel6() == null
                            || direccion.getCpValorNivel6().isEmpty()) {
                         return false;
                    }
                } else {
                    if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                            .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPT_PISO_LOCAL)) {
                        if (direccion.getCpValorNivel5() == null
                                || direccion.getCpValorNivel5().isEmpty()
                                || direccion.getCpValorNivel6() == null
                                || direccion.getCpValorNivel6().isEmpty()) {
                             return false;
                        }
                    } else {
                        if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                                .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPT_PISO_APTO)) {
                            if (direccion.getCpValorNivel5() == null
                                    || direccion.getCpValorNivel5().isEmpty()
                                    || direccion.getCpValorNivel6() == null
                                    || direccion.getCpValorNivel6().isEmpty()) {
                                 return false;
                            }
                        } else {
                            if (direccion.getCpTipoNivel5() != null && direccion.getCpTipoNivel5()
                                    .equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPT_CASA_PISO)) {
                                if (direccion.getCpValorNivel5() == null
                                        || direccion.getCpValorNivel5().isEmpty()
                                        || direccion.getCpValorNivel6() == null
                                        || direccion.getCpValorNivel6().isEmpty()) {
                                     return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;

        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al validar la direccion. EX000: " + e.getMessage(), e);
        }
    }

    public boolean validarConstruccionApartamentoBiDireccional(DrDireccion direccion, String identificadorApp,
            String tipoNivelApartamento, String apartamento) throws ApplicationException {
        try {

            if (direccion != null) {
                if (StringUtils.isBlank(identificadorApp) || StringUtils.isBlank(tipoNivelApartamento)) {
                    throw new ApplicationException("No es posible realizar la validación por"
                            + " datos incompletos en la construcción de la dirección ");
                }

                if (identificadorApp.equalsIgnoreCase(co.com.claro.mgl.utils.Constant.HFC_BID)) {
                    validarCamposConstruirDireccionApartamento(direccion, tipoNivelApartamento, apartamento);
                }
            }
            return true;
        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al validar el apartamento. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica los campos requeridos en la construcción de dirección apartamento
     *
     * @param direccion            {@link DrDireccion}
     * @param tipoNivelApartamento {@link String}
     * @param apartamento          {@link String}
     * @throws ApplicationException Excepción de la App
     */
    private static void validarCamposConstruirDireccionApartamento(DrDireccion direccion, String tipoNivelApartamento,
            String apartamento) throws ApplicationException {
        final String MSG_VALIDACION_NIVEL = "No es posible agregar este tipo de nivel. ";

        if (tipoNivelApartamento.equalsIgnoreCase(ConstantsDirecciones.TIPO_NIVEL_ES_APARTAMENTO)
                && (StringUtils.isBlank(direccion.getCpValorNivel5()))) {
            throw new ApplicationException(MSG_VALIDACION_NIVEL + "Seleccione PISO + APARTAMENTO.");
        }

        if (tipoNivelApartamento.equalsIgnoreCase(ConstantsDirecciones.TIPO_NIVEL_ES_INTERIOR)
                && StringUtils.isBlank(direccion.getCpValorNivel5())) {
            throw new ApplicationException(MSG_VALIDACION_NIVEL + "Seleccione PISO + INTERIOR.");
        }

        if (tipoNivelApartamento.equalsIgnoreCase(ConstantsDirecciones.TIPO_NIVEL_ES_LOCAL)
                && StringUtils.isBlank(direccion.getCpValorNivel5())) {
            throw new ApplicationException(MSG_VALIDACION_NIVEL + "Seleccione PISO + LOCAL.");
        }

        if (tipoNivelApartamento.equalsIgnoreCase(ConstantsDirecciones.TIPO_NIVEL_ES_CASA)
                && StringUtils.isNotBlank(apartamento)) {
            throw new ApplicationException("El tipo de nivel CASA  no debe llevar complemento.");
        }
    }

    /**
     * Verifica si la tecnología seleccionada cumple las condiciones para crear dirección de apartamento.
     *
     * @param direccion               {@link DrDireccion}
     * @param codigosTecnologias      {@link List<String>}
     * @param datosValidarApartamento {@link Map<>}
     * @return Retorna true cuando cumple las validaciones descritas
     * @throws ApplicationException Excepción de la App
     * @author Gildardo Mora
     */
    public boolean validarConstruccionApartamentoPorTecnologias(DrDireccion direccion, List<String> codigosTecnologias,
            Map<String, String> datosValidarApartamento) throws ApplicationException {
        try {
            if (Objects.isNull(direccion) || Objects.isNull(codigosTecnologias)) return true;

            String msgErrorValidation = "No es posible realizar la validación por datos incompletos en la construcción de la dirección ";
            codigosTecnologias.replaceAll(String::toUpperCase);

            if (Objects.isNull((datosValidarApartamento)) || datosValidarApartamento.isEmpty()) {
                throw new ApplicationException(msgErrorValidation);
            }

            String apartamento = datosValidarApartamento.getOrDefault(ConstantsDirecciones.APARTAMENTO, "");
            String tipoNivelApartamento = datosValidarApartamento.getOrDefault(ConstantsDirecciones.TIPO_NIVEL_APARTAMENTO, "");
            String codigoBasica = datosValidarApartamento.getOrDefault(ConstantsSolicitudHhpp.CODIGO_BASICA, "").toUpperCase();

            if (StringUtils.isBlank(tipoNivelApartamento)) {
                throw new ApplicationException(msgErrorValidation);
            }

            if (StringUtils.isNotBlank(apartamento) && StringUtils.isNotBlank(codigoBasica)
                    && codigosTecnologias.contains(codigoBasica)) {
                validarCamposConstruirDireccionApartamento(direccion, tipoNivelApartamento, apartamento);
            }

            return true;

        } catch (ApplicationException e) {
            String msgError = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException("Error al validar el apartamento. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Función que resuelve los conflictos presentados en una dirección que se
     * encuentra en RR como antigua y como nueva
     *
     * @author Juan David Hernandez
     * @param cityEntity que contiene los listados de unidades antiguas y nuevas
     * @return Listado de unidades que presentaron conflicto y no se pudieron
     * resolver
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public UnidadesConflictoDto resolverConflictosUnidades(CityEntity cityEntity) throws ApplicationException {
        try {
            UnidadesConflictoDto unidadesConflictoDto = new UnidadesConflictoDto();
            //validamos la existencia de algun hhpp en la direccion antigua y nueva
            PcmlManager pcmlManager = new PcmlManager();
            HhppMglManager hhppMglManager = new HhppMglManager();
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            List<UnidadStructPcml> unidadesConflictoSinResolver = new ArrayList<>();
            List<UnidadStructPcml> unidadesConflictoResueltos = new ArrayList<>();
            List<UnidadStructPcml> litUnidadStructPcmlNuevas = cityEntity.getUnidadStructPcmlNuevasList();
            List<UnidadStructPcml> litUnidadStructPcmlAntiguas = cityEntity.getUnidadStructPcmlAntiguasList();
            String estadoPotencialHhppRR = null;
            String estadoPrecableadoHhppRR = null;

            CmtBasicaMgl estadoPotencialHhpp = cmtBasicaMglManager
                    .findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_POTENCIAL);
            CmtBasicaMgl estadoPrecableadoHhpp = cmtBasicaMglManager
                    .findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_PRECABLEADO);
            if (estadoPotencialHhpp != null) {
                estadoPotencialHhppRR = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoPotencialHhpp);
            }
            if (estadoPrecableadoHhpp != null) {
                estadoPrecableadoHhppRR = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoPrecableadoHhpp);
            }

            if (estadoPotencialHhpp != null && estadoPrecableadoHhpp != null
                    && estadoPotencialHhppRR != null && estadoPrecableadoHhppRR != null) {

                if (litUnidadStructPcmlNuevas != null && !litUnidadStructPcmlNuevas.isEmpty()
                        && litUnidadStructPcmlAntiguas != null && !litUnidadStructPcmlAntiguas.isEmpty()) {

                    //n = nueva -- a = antigua
                    for (UnidadStructPcml n : litUnidadStructPcmlNuevas) {
                        for (UnidadStructPcml a : litUnidadStructPcmlAntiguas) {
                            //Si la unidad antigua tiene el mismo apto de la nueva y misma tecnologia 
                            if (a.getHhppMgl() != null && n.getHhppMgl() != null
                                    && a.getHhppMgl().getHhpApart().equalsIgnoreCase(n.getHhppMgl().getHhpApart())
                                    && a.getHhppMgl().getNodId() != null
                                    && a.getHhppMgl().getNodId().getNodTipo() != null
                                    && a.getHhppMgl().getNodId().getNodTipo().getBasicaId() != null
                                    && n.getHhppMgl().getNodId() != null
                                    && n.getHhppMgl().getNodId().getNodTipo() != null
                                    && n.getHhppMgl().getNodId().getNodTipo().getBasicaId() != null
                                    && a.getHhppMgl().getNodId().getNodTipo().getBasicaId()
                                            .equals(n.getHhppMgl().getNodId().getNodTipo().getBasicaId())) {

                                //se valida si la antigua se puede borrar
                                if (a.getHhppMgl().getEhhId().getEhhID().equalsIgnoreCase(estadoPotencialHhpp.getCodigoBasica())
                                        || a.getHhppMgl().getEhhId().getEhhID().equalsIgnoreCase(estadoPrecableadoHhpp.getCodigoBasica())) {

                                    HhppMgl hhppMglAntigua = a.getHhppMgl();
                                    hhppMglAntigua.setEstadoRegistro(0);
                                    hhppMglManager.update(hhppMglAntigua);
                                    unidadesConflictoResueltos.add(a);

                                    //Validación para borrar antigua en RR
                                    if (a.getHhppMgl().getHhpIdrR() != null && !a.getHhppMgl().getHhpIdrR().isEmpty()
                                            && a.getHhppMgl().getNodId().getComId() != null
                                            && a.getHhppMgl().getNodId().getComId().getCodigoRr() != null
                                            && a.getHhppMgl().getNodId().getComId().getRegionalRr() != null
                                            && a.getHhppMgl().getNodId().getComId().getRegionalRr().getCodigoRr() != null) {

                                        //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                        if (a.getHhppMgl().getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                && !a.getHhppMgl().getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                            for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                    : a.getHhppMgl().getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                        equalsIgnoreCase(co.com.claro.mgl.utils.Constant.SINCRONIZA_RR)
                                                        && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                    direccionRRManager.borrarHHPPRR(a.getHhppMgl().getNodId().getComId().getCodigoRr(),
                                                            a.getHhppMgl().getNodId().getComId().getRegionalRr().getCodigoRr(),
                                                            a.getHhppMgl().getHhpApart(),
                                                            a.getHhppMgl().getHhpCalle(), a.getHhppMgl().getHhpPlaca());
                                                }
                                            }
                                        }
                                    }

                                } else {
                                    //se valida si la nueva se puede borrar
                                    if (n.getHhppMgl().getEhhId().getEhhID().equalsIgnoreCase(estadoPotencialHhpp.getCodigoBasica())
                                            || n.getHhppMgl().getEhhId().getEhhID().equalsIgnoreCase(estadoPrecableadoHhpp.getCodigoBasica())) {

                                        HhppMgl hhppMglNueva = n.getHhppMgl();
                                        hhppMglNueva.setEstadoRegistro(0);
                                        hhppMglManager.update(hhppMglNueva);
                                        unidadesConflictoResueltos.add(n);

                                        //Validación para borrar nueva en RR
                                        if (n.getHhppMgl().getNodId().getComId() != null
                                                && n.getHhppMgl().getNodId().getComId().getCodigoRr() != null
                                                && n.getHhppMgl().getNodId().getComId().getRegionalRr() != null
                                                && n.getHhppMgl().getNodId().getComId().getRegionalRr().getCodigoRr() != null) {

                                            //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                            if (n.getHhppMgl().getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                    && !n.getHhppMgl().getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                                for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                        : n.getHhppMgl().getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                    if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                            equalsIgnoreCase(co.com.claro.mgl.utils.Constant.SINCRONIZA_RR)
                                                            && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                        direccionRRManager.borrarHHPPRR(n.getHhppMgl().getNodId().getComId().getCodigoRr(),
                                                                n.getHhppMgl().getNodId().getComId().getRegionalRr().getCodigoRr(),
                                                                n.getHhppMgl().getHhpApart(),
                                                                n.getHhppMgl().getHhpCalle(), n.getHhppMgl().getHhpPlaca());
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        /*al no poderse resolver el conflicto por los estado de las unidades se guarda para 
                                         * ser gestionada manualmente */
                                        UnidadStructPcml unidadStructPcml = new UnidadStructPcml();
                                        unidadStructPcml = a.clone();
                                        unidadStructPcml.setTecHabilitadaIdNuevaDireccion(n.getTecnologiaHabilitadaId());
                                        unidadStructPcml.setConflictApto("1");
                                        unidadesConflictoSinResolver.add(unidadStructPcml);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                throw new ApplicationException("Ocurrio un error cargando los estado de la "
                        + "unidad en resolución de unidades con conflicto");
            }
            unidadesConflictoDto.setUnidadesConflicto(unidadesConflictoSinResolver);
            unidadesConflictoDto.setUnidadesConflictoResuelto(unidadesConflictoResueltos);
            return unidadesConflictoDto;
        } catch (ApplicationException | CloneNotSupportedException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se produjo un error al momento de resolver conflictos unidades: " + e.getMessage(), e);
        }
    }

    public List<String> validarDireccionAmbigua(String comunidad, String barrio,
            DrDireccion drDireccion) {
                List<String> ambiguaList = null;
        try {
            NegocioParamMultivalor param = new NegocioParamMultivalor();
            CityEntity cityEntity = param.consultaDptoCiudad(comunidad);
            if (cityEntity == null || cityEntity.getCityName() == null
                    || cityEntity.getDpto() == null
                    || cityEntity.getCityName().isEmpty()
                    || cityEntity.getDpto().isEmpty()) {
                throw new ApplicationException("La Ciudad no esta configurada en "
                        + "Direcciones");
            }

            DrDireccionManager drDireccionManager = new DrDireccionManager();

            if (barrio != null && !barrio.isEmpty()) {
                drDireccion.setBarrio(barrio.toUpperCase());
            }
            String barrioStr
                    = drDireccionManager.obtenerBarrio(drDireccion);
            cityEntity.setCodCity(comunidad);
            if (barrioStr !=  null && !barrioStr.isEmpty()) {
                cityEntity.setBarrio(barrioStr.toUpperCase());
            }

            DetalleDireccionEntity detalleDireccionActual
                    = drDireccion.convertToDetalleDireccionEntity();
            cityEntity.setActualDetalleDireccionEntity(detalleDireccionActual);
            cityEntity.setCityId(cityEntity.getCityId());

            cityEntity.setDetalleDireccionEntity(null);

            String address
                    = drDireccionManager.getDireccion(drDireccion);
            cityEntity.setAddress(address);

            cityEntity.setCambioDireccion(false);
            cityEntity.setDireccionRREntityAntigua(null);
            cityEntity.setDireccionRREntityNueva(null);
            cityEntity.setDirIgacAntiguaStr(null);
            cityEntity.setExisteHhppAntiguoNuevo(false);

            /*Consultar el WebService parera obtener los datos de la direccion
             * ingresada */
            AddressRequest requestSrv = new AddressRequest();

            //Asignar la ciudad
            requestSrv.setCity(cityEntity.getCityName());
            //Asignar el departamento
            requestSrv.setState(cityEntity.getDpto());
            //Asignar la direccion a consultar
            requestSrv.setAddress(cityEntity.getAddress());
            //Asignar el barrio
            requestSrv.setNeighborhood(cityEntity.getBarrio());

            requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);
            
            if (cityEntity.getCodDane() != null && !cityEntity.getCodDane().isEmpty()) {
                requestSrv.setCodDaneVt(cityEntity.getCodDane());   
            }
            requestSrv.setState((cityEntity.getCodDane() != null && !cityEntity.getCodDane().isEmpty()) ? "" : cityEntity.getDpto());

            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            AddressService responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);

            ambiguaList = new ArrayList<>();
            ambiguaList.add(responseSrv.getAmbigua() != null
                    && !responseSrv.getAmbigua().isEmpty()
                    ? responseSrv.getAmbigua() : "");

            // dirección nueva-antigua
            ambiguaList.add(responseSrv.getAddress() != null
                    && !responseSrv.getAddress().isEmpty()
                    ? responseSrv.getAddress() : "");

            //dirección nueva-nueva  
            ambiguaList.add(responseSrv.getAlternateaddress() != null
                    && !responseSrv.getAlternateaddress().isEmpty()
                    ? responseSrv.getAlternateaddress() : "");
        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return ambiguaList;
    }

    /**
     * Valida una direccion. Realiza las operaciones de validacion en el
     * repositorio para verificar existencia. Incluye la validacion de direccion
     * nueva y antigua.
     *
     * @author Victor Bocanegra
     * @param request objeto con la informacion de la direccion a validar.
     * @return resultado de la validacion direccion.
     */
    public CmtCityEntityDto validaDireccionHhpp(
            CmtRequestConstruccionDireccionDto request) {
        try {
            RequestConstruccionDireccion construccionDireccion = new RequestConstruccionDireccion();
            CmtCityEntityDto cmtCityEntityDto = new CmtCityEntityDto();
            
            if(request == null){                
                cmtCityEntityDto.setMessageType("E");
                cmtCityEntityDto.setMessage("Debe construir una petición para consumir el servicio");
                return cmtCityEntityDto;
            }
            
            if (request.getComunidad() == null || request.getComunidad().isEmpty()) {                
                cmtCityEntityDto.setMessageType("E");
                cmtCityEntityDto.setMessage("Debe ingresar la comunidad");
                return cmtCityEntityDto;
            }
       
            if (request.getIdUsuario() == null || request.getIdUsuario().isEmpty()) {
                cmtCityEntityDto.setMessageType("E");
                cmtCityEntityDto.setMessage("Debe ingresar un usuario por favor");
                return cmtCityEntityDto;
            }
            
            if (request.getDrDireccion() == null) {
                cmtCityEntityDto.setMessageType("E");
                cmtCityEntityDto.setMessage("Debe construir una direccion en el DrDireccion para validarla");
                return cmtCityEntityDto;
            }
            
           if (request.getDrDireccion().getIdTipoDireccion() == null
                   || request.getDrDireccion().getIdTipoDireccion().isEmpty()) {
                cmtCityEntityDto.setMessageType("E");
                cmtCityEntityDto.setMessage("Debe ingresar un IdTipoDireccion en el DrDireccion: Ck. Bm, o IT");
                return cmtCityEntityDto;
            }
           
            if (request.getDrDireccion() == null) {
                cmtCityEntityDto.setMessageType("E");
                cmtCityEntityDto.setMessage("Debe construir una direccion en el DrDireccion para validarla");
                return cmtCityEntityDto;
            }
            
            construccionDireccion.setDrDireccion(request.getDrDireccion());
            construccionDireccion.setDireccionStr(request.getDireccionStr());
            construccionDireccion.setComunidad(request.getComunidad());
            if (request.getBarrio() != null && !request.getBarrio().isEmpty()) {
                construccionDireccion.setBarrio(request.getBarrio().toUpperCase());
            }
            construccionDireccion.setTipoAdicion(request.getTipoAdicion());
            construccionDireccion.setTipoNivel(request.getTipoNivel());
            construccionDireccion.setValorNivel(request.getValorNivel());
            construccionDireccion.setIdUsuario(request.getIdUsuario());
            CityEntity cityEntity = validaDireccion(construccionDireccion, false);

            if (cityEntity != null) {
                cmtCityEntityDto = new CmtCityEntityDto();
                cmtCityEntityDto.setCodCity(cityEntity.getCodCity());
                cmtCityEntityDto.setCityName(cityEntity.getCityName());
                cmtCityEntityDto.setDpto(cityEntity.getDpto());
                cmtCityEntityDto.setCodDane(cityEntity.getCodDane());
                cmtCityEntityDto.setPais(cityEntity.getPais());
                if (cityEntity.getBarrio() != null && !cityEntity.getBarrio().isEmpty()) {
                    cmtCityEntityDto.setBarrio(cityEntity.getBarrio().toUpperCase());
                }
                cmtCityEntityDto.setAddress(cityEntity.getAddress());
                cmtCityEntityDto.setExistencia(cityEntity.getExistencia());
                cmtCityEntityDto.setDireccion(cityEntity.getDireccion());
                cmtCityEntityDto.setNodo1(cityEntity.getNodo1());
                cmtCityEntityDto.setNodo2(cityEntity.getNodo2());
                cmtCityEntityDto.setNodo3(cityEntity.getNodo3());
                cmtCityEntityDto.setNodoDTH(cityEntity.getNodoDTH());
                cmtCityEntityDto.setPostalCode(cityEntity.getPostalCode());
                cmtCityEntityDto.setEstratoDir(cityEntity.getEstratoDir());
                cmtCityEntityDto.setEstadoDir(cityEntity.getEstadoDir());
                cmtCityEntityDto.setConfiabilidadDir(cityEntity.getConfiabilidadDir());
                cmtCityEntityDto.setDirStandar(cityEntity.getDirStandar());
                cmtCityEntityDto.setBarrioSugerido(cityEntity.getBarrioSugerido());
                cmtCityEntityDto.setFuente(cityEntity.getFuente());
                cmtCityEntityDto.setCodigoDirccion(cityEntity.getCodigoDirccion());
                cmtCityEntityDto.setDetalleDireccionEntity(cityEntity.getDetalleDireccionEntity());
                cmtCityEntityDto.setCityId(cityEntity.getCityId());
                cmtCityEntityDto.setExisteRr(cityEntity.getExisteRr());
                cmtCityEntityDto.setDireccionRREntityAntigua(cityEntity.getDireccionRREntityAntigua());
                cmtCityEntityDto.setDireccionRREntityNueva(cityEntity.getDireccionRREntityNueva());
                cmtCityEntityDto.setTipoSolictud(cityEntity.getTipoSolictud());
                cmtCityEntityDto.setCambioDireccion(cityEntity.isCambioDireccion());
                cmtCityEntityDto.setDirIgacAntiguaStr(cityEntity.getDirIgacAntiguaStr());
                cmtCityEntityDto.setExisteHhppAntiguoNuevo(cityEntity.isExisteHhppAntiguoNuevo());
                cmtCityEntityDto.setIdUsuario(cityEntity.getIdUsuario());
                cmtCityEntityDto.setMessageType("I");
                cmtCityEntityDto.setMessage("Proceso Exitoso");
            } else {
                cmtCityEntityDto = new CmtCityEntityDto();
                cmtCityEntityDto.setMessageType("E");
                cmtCityEntityDto.setMessage("Ha ocurrido un error construyendo la direccion");
            }
            return cmtCityEntityDto;
        } catch (ApplicationException e) {
            CmtCityEntityDto cmtCityEntityDto = new CmtCityEntityDto();
            cmtCityEntityDto.setMessageType("E");
            cmtCityEntityDto.setMessage("Ha ocurrido un error intentando consumir la peticion, "
                    + "verifique que este correctamente construida");
             return cmtCityEntityDto;
        }
    }

    /**
     * Autor: Victor Bocanegra
     *
     * @param request
     * @return List<CmtAddressSuggestedDto>
     * @
     */
    public CmtSuggestedNeighborhoodsDto obtenerBarrioSugeridoHhpp(
            CmtRequestConstruccionDireccionDto request) {
        List<CmtAddressSuggestedDto> barrioList;
        CmtAddressSuggestedDto cmtAddressSuggestedDto = new CmtAddressSuggestedDto();
                        
        if (request == null) {
            barrioList = new ArrayList<>();
            cmtAddressSuggestedDto.setMessageType("E");
            cmtAddressSuggestedDto.setMessage("Debe construir una petición para consumir el servicio.");
            barrioList.add(cmtAddressSuggestedDto);
            CmtSuggestedNeighborhoodsDto cmtSuggestedNeighborhoodsDto = new CmtSuggestedNeighborhoodsDto();
            cmtSuggestedNeighborhoodsDto.setNeighborhoodsList(barrioList);
            return cmtSuggestedNeighborhoodsDto;
        }
        
        if (request.getDrDireccion() == null) {
            barrioList = new ArrayList<>();
            cmtAddressSuggestedDto.setMessageType("E");
            cmtAddressSuggestedDto.setMessage("Debe construir un DrDireccion");
            barrioList.add(cmtAddressSuggestedDto);
            CmtSuggestedNeighborhoodsDto cmtSuggestedNeighborhoodsDto = new CmtSuggestedNeighborhoodsDto();
            cmtSuggestedNeighborhoodsDto.setNeighborhoodsList(barrioList);
            return cmtSuggestedNeighborhoodsDto;
        }     
        
        if (request.getIdUsuario() == null || request.getIdUsuario().isEmpty()) {
            barrioList = new ArrayList<>();
            cmtAddressSuggestedDto.setMessageType("E");
            cmtAddressSuggestedDto.setMessage("Debe ingresar el idUsuario por favor.");
            barrioList.add(cmtAddressSuggestedDto);
            CmtSuggestedNeighborhoodsDto cmtSuggestedNeighborhoodsDto = new CmtSuggestedNeighborhoodsDto();
            cmtSuggestedNeighborhoodsDto.setNeighborhoodsList(barrioList);
            return cmtSuggestedNeighborhoodsDto;
        }

         if (request.getDrDireccion().getIdTipoDireccion() != null
                            && !request.getDrDireccion().getIdTipoDireccion().isEmpty()) {
             if (!request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")
                     && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")
                     && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")) {
                 barrioList = new ArrayList<>();
                 cmtAddressSuggestedDto.setMessageType("E");
                 cmtAddressSuggestedDto.setMessage("Debe ingresar un tipo de direccion valido: CK, Bm, o IT");
                 barrioList.add(cmtAddressSuggestedDto);
                 CmtSuggestedNeighborhoodsDto cmtSuggestedNeighborhoodsDto = new CmtSuggestedNeighborhoodsDto();
                 cmtSuggestedNeighborhoodsDto.setNeighborhoodsList(barrioList);
                 return cmtSuggestedNeighborhoodsDto;
             }
        } else {          
            barrioList = new ArrayList<>();
            cmtAddressSuggestedDto.setMessageType("E");
            cmtAddressSuggestedDto.setMessage("Debe ingresar el IdTipoDireccion por favor");
            barrioList.add(cmtAddressSuggestedDto);
            CmtSuggestedNeighborhoodsDto cmtSuggestedNeighborhoodsDto = new CmtSuggestedNeighborhoodsDto();
            cmtSuggestedNeighborhoodsDto.setNeighborhoodsList(barrioList);
            return cmtSuggestedNeighborhoodsDto;
        }   

        if (request.getComunidad() == null || request.getComunidad().isEmpty()) {
            barrioList = new ArrayList<>();
            cmtAddressSuggestedDto.setMessageType("E");
            cmtAddressSuggestedDto.setMessage("Debe ingresar el codigo dane en el campo comunidad");
            barrioList.add(cmtAddressSuggestedDto);
            CmtSuggestedNeighborhoodsDto cmtSuggestedNeighborhoodsDto = new CmtSuggestedNeighborhoodsDto();
            cmtSuggestedNeighborhoodsDto.setNeighborhoodsList(barrioList);
            return cmtSuggestedNeighborhoodsDto;
        }

        RequestConstruccionDireccion construccionDireccion = new RequestConstruccionDireccion();

        construccionDireccion.setDrDireccion(request.getDrDireccion());
        construccionDireccion.setDireccionStr(request.getDireccionStr());
        construccionDireccion.setComunidad(request.getComunidad());
        construccionDireccion.setBarrio(request.getBarrio() != null ? request.getBarrio().toUpperCase() : null);
        construccionDireccion.setTipoAdicion(request.getTipoAdicion());
        construccionDireccion.setTipoNivel(request.getTipoNivel());
        construccionDireccion.setValorNivel(request.getValorNivel());
        construccionDireccion.setIdUsuario(request.getIdUsuario());
        List<AddressSuggested> lsAddressSuggesteds = obtenerBarrioSugerido(construccionDireccion, false);       

        if (lsAddressSuggesteds != null) {
            barrioList = new ArrayList<>();

            
            for (AddressSuggested addres : lsAddressSuggesteds) {
                cmtAddressSuggestedDto = new CmtAddressSuggestedDto();
                cmtAddressSuggestedDto.setAddress(addres.getAddress());
                cmtAddressSuggestedDto.setNeighborhood(addres.getNeighborhood());
                cmtAddressSuggestedDto.setMessageType("I");
                cmtAddressSuggestedDto.setMessage("Proceso Exitoso");
                barrioList.add(cmtAddressSuggestedDto);
            }
        } else {
            cmtAddressSuggestedDto = new CmtAddressSuggestedDto();
            barrioList = new ArrayList<>();
            cmtAddressSuggestedDto.setMessageType("E");
            cmtAddressSuggestedDto.setMessage("Ocurrio un error cargando la lista de barrios sugeridos");
            barrioList.add(cmtAddressSuggestedDto);
        }
        CmtSuggestedNeighborhoodsDto cmtSuggestedNeighborhoodsDto = new CmtSuggestedNeighborhoodsDto();
        cmtSuggestedNeighborhoodsDto.setNeighborhoodsList(barrioList);
        return cmtSuggestedNeighborhoodsDto;
    } 
    
    
    /**
     * Función que valida los campos obligatorios para el servicio
     * de ObtenerBarriosSugeridoHhpp
     * @param request 
     *
     * @return CmtAddressSuggestedDto con mensaje de validacion
     *
     * @author Juan David Hernandez
     */
    public CmtAddressSuggestedDto validacionCamposObtenerBarrioSugeridoHhpp(CmtRequestConstruccionDireccionDto request){
        CmtAddressSuggestedDto cmtAddressSuggestedDto = new CmtAddressSuggestedDto();
        try {                
            if (request != null) {
                if (request.getDrDireccion() != null) {

                    if (request.getDrDireccion().getIdTipoDireccion() != null
                            && !request.getDrDireccion().getIdTipoDireccion().isEmpty()) {

                        if (!request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")
                                && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")
                                && !request.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")) {
                            cmtAddressSuggestedDto.setMessageType("E");
                            cmtAddressSuggestedDto.setMessage("Los valores permitidos para tipo de direccion son CK, BM o IT");
                            return cmtAddressSuggestedDto;
                        }
                    } else {
                        cmtAddressSuggestedDto.setMessageType("E");
                        cmtAddressSuggestedDto.setMessage("Debe ingresar el IdTipoDireccion por favor");
                        return cmtAddressSuggestedDto;
                    }
                    
                    if(request.getDrDireccion().getDirPrincAlt() != null && !request.getDrDireccion().getDirPrincAlt().isEmpty()){
                        if(!request.getDrDireccion().getDirPrincAlt().equalsIgnoreCase("P")){
                            cmtAddressSuggestedDto.setMessageType("E");
                            cmtAddressSuggestedDto.setMessage("El valor para DirPrincAlt debe ser 'P' ");
                            return cmtAddressSuggestedDto;
                        }
                    } else {
                        cmtAddressSuggestedDto.setMessageType("E");
                        cmtAddressSuggestedDto.setMessage("Debe ingresar el valor P para DirPrincAlt");
                        return cmtAddressSuggestedDto;
                    }                    
                    
                    if (request.getDireccionStr() == null || request.getDireccionStr().isEmpty()){
                         cmtAddressSuggestedDto.setMessageType("E");
                        cmtAddressSuggestedDto.setMessage("Debe ingresar una dirección por favor en el campo DireccionStr.");
                        return cmtAddressSuggestedDto;
                    }
                    
                    if (request.getComunidad() == null || request.getComunidad().isEmpty()){
                        cmtAddressSuggestedDto.setMessageType("E");
                        cmtAddressSuggestedDto.setMessage("Debe ingresar el codigo dane en el campo comunidad");
                        return cmtAddressSuggestedDto;
                    }  
                    
                    
                    if(request.getTipoAdicion() != null && !request.getTipoAdicion().isEmpty()){                        
                        if(!request.getTipoAdicion().equalsIgnoreCase("N") && !request.getTipoAdicion().equalsIgnoreCase("C")
                                && !request.getTipoAdicion().equalsIgnoreCase("P")
                                && !request.getTipoAdicion().equalsIgnoreCase("A")){                            
                            cmtAddressSuggestedDto.setMessageType("E");
                            cmtAddressSuggestedDto.setMessage("Los valores permitidos para TipoAdición son: N para calle "
                                    + "carrera dirección hasta placa, C para complementos, P para dirección principal "
                                    + "barrio-manzana e intraducible, A para apartamento");
                            return cmtAddressSuggestedDto;
                        }                        
                        
                    }else{
                        cmtAddressSuggestedDto.setMessageType("E");
                        cmtAddressSuggestedDto.setMessage("Debe ingresar el tipo de adición: N para calle "
                                    + "carrera dirección hasta placa, C para complementos, P para dirección principal "
                                    + "barrio-manzana e intraducible, A para apartamento");
                        return cmtAddressSuggestedDto;
                    }                        
                    
                    //validacion de campos exitosa
                    cmtAddressSuggestedDto.setMessageType("I");
                    cmtAddressSuggestedDto.setMessage("Validacion de campos obligatorios correcta");
                    return cmtAddressSuggestedDto;
                    
                } else {
                    cmtAddressSuggestedDto.setMessageType("E");
                    cmtAddressSuggestedDto.setMessage("Es necesario construir el DrDireccion de la petición para consumir el servicio");
                    return cmtAddressSuggestedDto;
                }
            } else {
                cmtAddressSuggestedDto.setMessageType("E");
                cmtAddressSuggestedDto.setMessage("Es necesario construir una petición para consumir el servicio correctamente");
                return cmtAddressSuggestedDto;
            }
            
        } catch (Exception e) {
            cmtAddressSuggestedDto.setMessageType("E");
            cmtAddressSuggestedDto.setMessage("Ocurrio un error intentando validar los campos obligatorios del servicio");
            return cmtAddressSuggestedDto;
        }
    }
            

    /**
     * Obtiene una direccion georeferenciada y actualizando los valores
     * correspondientes a un camnbio de malla de antigua a nueva
     *
     * @param direccionNueva direccion detallada con los nuevos valores a
     * actualizar
     * @param direccionAntigua direccion detallada con direccion
     * antigua.
     * @param centroPobladoId
     * @param barrio
     *
     * @return direccion con valores actualizados.
     *
     * @author Juan David Hernandez
     */
    public DireccionMgl georeferenciacionMallaAntiguaANueva(DireccionMgl direccionAntigua, String direccionNueva,
            BigDecimal centroPobladoId, String barrio) {
        try {
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();

            if (direccionAntigua != null && direccionNueva != null && !direccionNueva.isEmpty()) {

                GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPobladoId);
                GeograficoPoliticoMgl ciudadGpo = geograficoPoliticoManager.findById(centroPobladoCompleto.getGeoGpoId());
                GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(ciudadGpo.getGeoGpoId());

                AddressEJB geoWs = new AddressEJB();
                AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();

                /*Consultar el WebService parera obtener los datos de la direccion
                 * ingresada */
                AddressService responseSrv;
                AddressRequest requestSrv = new AddressRequest();

                //Asignar la ciudad
                requestSrv.setCity(centroPobladoCompleto.getGpoNombre());
                //Asignar el departamento
                requestSrv.setState(departamento.getGpoNombre());
                //Asignar la direccion a consultar
                requestSrv.setAddress(direccionNueva);
                requestSrv.setCity(centroPobladoCompleto.getGpoNombre());
                requestSrv.setCodDaneVt(centroPobladoCompleto.getGeoCodigoDane());
                requestSrv.setLevel(co.com.telmex.catastro.services.util.Constant.TIPO_CONSULTA_COMPLETA);
                requestSrv.setNeighborhood(barrio);

                responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);

                if (responseSrv != null && responseSrv.getTraslate().equalsIgnoreCase("TRUE")) {

                    AddressGeodata geodata = geoWs.queryAddressGeodata(requestSrv);

                    direccionAntigua.setDirServinformacion(geodata.getCoddir() != null ? geodata.getCoddir() : "NA");
                    direccionAntigua.setDirOrigen(geodata.getFuente() != null ? geodata.getFuente() : null);
                    direccionAntigua.setDirNodouno(geodata.getNodo1() != null ? geodata.getNodo1() : "NA");
                    direccionAntigua.setDirNododos(geodata.getNodo2() != null ? geodata.getNodo2() : "NA");
                    direccionAntigua.setDirNodotres(geodata.getNodo3() != null ? geodata.getNodo3() : "NA");
                    direccionAntigua.setDirNodoDth(geodata.getNodoDth() != null ? geodata.getNodoDth() : "NA");
                    direccionAntigua.setDirNodoFtth(geodata.getNodoFtth() != null ? geodata.getNodoFtth() : "NA");
                    direccionAntigua.setDirNodoMovil(geodata.getNodoMovil() != null ? geodata.getNodoMovil() : "NA");
                    direccionAntigua.setDirNodoWifi(geodata.getNodoWifi() != null ? geodata.getNodoWifi() : "NA");
                    direccionAntigua.setDirManzanaCatastral(geodata.getManzana() != null ? geodata.getManzana() : "NA");
                    direccionAntigua.setDirNivelSocioecono(new BigDecimal(geodata.getNivsocio()));
                    direccionAntigua.setDirConfiabilidad(new BigDecimal(geodata.getConfiability() != null ? geodata.getConfiability() : "0"));
                    if (Constant.DIR_ESTADO_INTRADUCIBLE.equals(geodata.getEstado())) {
                        direccionAntigua.setDirFormatoIgac(requestSrv.getAddress().trim());
                    } else {
                        direccionAntigua.setDirFormatoIgac(geodata.getDirtrad().trim());
                    }
                    direccionAntigua.setDirNostandar(requestSrv.getAddress());

                    if (direccionAntigua.getUbicacion() != null) {
                        direccionAntigua.getUbicacion().setUbiLatitud(geodata.getCy());
                        direccionAntigua.getUbicacion().setUbiLongitud(geodata.getCx());
                    }
                } else {
                    //Si no se georeferencia (direccion BM / IT) se deja la direccion nueva de texto.
                    direccionAntigua.setDirNostandar(direccionNueva.trim() + " " + barrio);
                    direccionAntigua.setDirFormatoIgac(direccionNueva.trim() + " " + barrio);
                }

            } else {
                throw new Error("Ocurrio un error debido a que la direccion antigua o nueva se encuentran vacias "
                        + "al momento de georeferenciar la subdireccion al realizar el cambio de apto : ");
            }
            return direccionAntigua;
        } catch (ApplicationException | IOException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new Error("No fue posible georeferencia la dirección para realizar la actualización");
        }
    }

    /**
     * Obtiene una Subdireccion georeferenciada y actualizando los valores
     * correspondientes a un camnbio de malla de antigua a nueva
     *
     * @param direccionNueva direccion detallada con los nuevos valores a
     * actualizar
     * @param direccionAntigua direccion detallada con direccion
     * antigua.
     * @param barrio
     *
     * @return Subdireccion con valores actualizados.
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public SubDireccionMgl georeferenciacionSubDireccionCambioDir(SubDireccionMgl direccionAntigua, String direccionNueva,
            BigDecimal centroPobladoId, String barrio) throws ApplicationException {
        try {
            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();

            if (direccionAntigua != null && direccionNueva != null && !direccionNueva.isEmpty()) {

                GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPobladoId);
                GeograficoPoliticoMgl ciudadGpo = geograficoPoliticoManager.findById(centroPobladoCompleto.getGeoGpoId());
                GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(ciudadGpo.getGeoGpoId());

                AddressEJB geoWs = new AddressEJB();
                AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
                /*Consultar el WebService parera obtener los datos de la direccion
                 * ingresada */
                AddressService responseSrv;
                AddressRequest requestSrv = new AddressRequest();

                //Asignar la ciudad
                requestSrv.setCity(centroPobladoCompleto.getGpoNombre());
                //Asignar el departamento
                requestSrv.setState(departamento.getGpoNombre());
                //Asignar la direccion a consultar
                requestSrv.setAddress(direccionNueva);
                requestSrv.setCity(centroPobladoCompleto.getGpoNombre());
                requestSrv.setCodDaneVt(centroPobladoCompleto.getGeoCodigoDane());
                requestSrv.setLevel(co.com.telmex.catastro.services.util.Constant.TIPO_CONSULTA_COMPLETA);
                requestSrv.setNeighborhood(barrio);

                responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);

                if (responseSrv != null && responseSrv.getTraslate().equalsIgnoreCase("TRUE")) {

                    AddressGeodata geodata = geoWs.queryAddressGeodata(requestSrv);

                    if (geodata != null) {
                        direccionAntigua.setSdiNodouno(geodata.getNodo1() != null ? geodata.getNodo1() : "NA");
                        direccionAntigua.setSdiNododos(geodata.getNodo2() != null ? geodata.getNodo2() : "NA");
                        direccionAntigua.setSdiNodotres(geodata.getNodo3() != null ? geodata.getNodo3() : "NA");
                        direccionAntigua.setSdiNodoDth(geodata.getNodoDth() != null ? geodata.getNodoDth() : "NA");
                        direccionAntigua.setSdiNodoFtth(geodata.getNodoFtth() != null ? geodata.getNodoFtth() : "NA");
                        direccionAntigua.setSdiNodoMovil(geodata.getNodoMovil() != null ? geodata.getNodoMovil() : "NA");
                        direccionAntigua.setSdiServinformacion(geodata.getCoddir() != null ? geodata.getCoddir() : "NA");
                        if (geodata.getNivsocio() != null && !geodata.getNivsocio().isEmpty()) {
                            direccionAntigua.setSdiNivelSocioecono(new BigDecimal(geodata.getNivsocio()));
                        } else {
                            direccionAntigua.setSdiNivelSocioecono(null);
                        }

                        if (Constant.DIR_ESTADO_INTRADUCIBLE.equals(geodata.getEstado())) {
                            direccionAntigua.setSdiFormatoIgac(requestSrv.getAddress().trim() != null ? requestSrv.getAddress() : null);
                        } else {
                            direccionAntigua.setSdiFormatoIgac(geodata.getDirtrad().trim() != null ? geodata.getDirtrad().trim() : null);
                        }

                    } else {
                        throw new Error("Error georeferenciando la sub direccion: Antigua, "
                                + "debido a que el Georeferenciador no respondió de manera correcta."
                                + " Intente de nuevo por favor. ");
                    }
                } else {
                    //Si no se georeferencia (direccion BM / IT) se deja la direccion nueva de texto.
                    direccionAntigua.setSdiFormatoIgac(direccionNueva.trim() + " " + barrio);
                }
            } else {
                throw new Error("Ocurrio un error debido a que la direccion antigua o nueva se encuentran vacias "
                        + "al momento de georeferenciar la subdireccion al realizar el cambio de apto : ");
            }
            return direccionAntigua;

        } catch (ApplicationException | IOException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new Error("Error georeferenciando la sub direccion: Antigua : " + direccionAntigua.getSdiId() + "debido a: " + e.getMessage());
        }
    }

    /**
     * Función para validar si el dato recibido es númerico
     *
     * @author Juan David Hernandez return true si el dato es úumerico
     * @param cadena
     * @return 
     */
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO +ClassUtils.getCurrentMethodName(this.getClass())+"': " + nfe.getMessage();
            LOGGER.error(msg);
            return false;
        }
    }

}
