package co.com.telmex.catastro.services.georeferencia;

import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.businessmanager.address.UbicacionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.dtos.AddressGeoDirDataDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoDireccionMgl;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.ConsultaEspecificaManager;
import co.com.telmex.catastro.data.AddressAggregated;
import co.com.telmex.catastro.data.AddressFactResultBatch;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressResultBatch;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressServiceBatchXml;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.DireccionAlterna;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.data.TipoDireccion;
import co.com.telmex.catastro.data.TipoGeografico;
import co.com.telmex.catastro.data.TipoUbicacion;
import co.com.telmex.catastro.data.Ubicacion;
import co.com.telmex.catastro.services.seguridad.AuditoriaEJB;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.DireccionUtil;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import co.com.telmex.catastro.services.wservice.ResponseArrayFromWS;
import co.com.telmex.catastro.services.wservice.WsSitidataStandarLocator;
import co.com.telmex.catastro.services.wservice.WsSitidataStandarPortType;
import co.com.telmex.catastro.utilws.ClientProxyWs;
import co.com.telmex.catastro.utilws.ConstantWS;
import co.com.telmex.catastro.utilws.ConvertXML;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.JDOMException;

import static co.com.telmex.catastro.services.georeferencia.GeoUtil.*;

/**
 * Clase AddressEJB implementa AddressEJBRemote
 *
 * @author Jose Luis Caicedo G.
 * @version	1.0 - Modificado por: Direcciones face I Carlos Vilamil 2012-12-11
 * @version	1.1 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 * @version	1.2 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 * @version	1.3 - Modificado por: Direcciones face I Johnnatan Ortiz 2013-10-01
 * @version 1.4 - Modificado por: Nodo DTH 2015-09-30
 * @version 1.5 - Modificacion por: identificacion Direccion y Sub 2018-07-25
 * @version 1.6 - Modificacion por: Nuevas Geozonas Inspira 2018-07-25 Direccion
 * espinosadiea
 */
@Stateless(name = "addressEJB", mappedName = "addressEJB", description = "direccion")
@Remote({AddressEJBRemote.class})
public class AddressEJB implements AddressEJBRemote {

    /**
     * objetivo de la clase. Clase encargada de manejar la logica de
     * direcciones.
     *
     * @author carlos villamil
     * @versión 1.00.000
     */
    private static final Logger LOGGER = LogManager.getLogger(AddressEJB.class);
    public static final String DIR_ES_AMBIGUA = " La dirección es ambigua.";
    public static final String DIR_ES_ANTIGUA_SE_DEBE_ACTUALIZAR = "La dirección es antigua, se debe actualizar";
    public static final String DIR_EXISTE_EN_EL_REPOSITORIO = "La dirección existe en el repositorio.";
    public static final String DIR_NO_EXISTE_EN_EL_REPOSITORIO = "La dirección no existe en el repositorio.";
    public static final String DIR_RESIVAR_EN_CAMPO = "La dirección debe ser revisada en campo.";
    public static final String NIVEL_DETALLE_COMPLETO = "C";
    public static final String DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO = "Dirección ya existente en repositorio, por lo tanto no se puede guardar.";
    public static final String DIR_NO_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO = "Dirección NO existente en repositorio, por lo tanto no se puede Actualizar.";
    public static final String MESSAGE_DIR_INSERTADA_SIN_VALIDAR = "Dirección almacenada sin validar en Georeferenciador";
    public static final String MESSAGE_DIR_ACTUALIZADA_SIN_VALIDAR = "Dirección actualizada sin validar en Georeferenciador";
    private BigDecimal ID_CASA = BigDecimal.ZERO;
    private BigDecimal ID_EDIFICIO = BigDecimal.ZERO;
    private String nombreLog;
    private String Estrato;
    private List<AddressGeodata> procesointercambio = null;
    private DireccionMglManager direccionManager = new DireccionMglManager();
    private final SubDireccionMglManager subDireccionManager = new SubDireccionMglManager();
    private final CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();

    /**
     *
     * @throws IOException
     */
    public AddressEJB() throws IOException {
        super();
        queryParametrosConfig();
    }

    /**
     *
     * @param addressRequest
     * @param i
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @param addressResult
     * @param Geodatas
     * @param addressServices
     * @throws ExceptionDB
     */
    private void ServiceCreateAddress(List<AddressRequest> addressRequest, int i, String user, String nombreFuncionalidad, String validate, List<AddressFactResultBatch> addressResult, List<AddressGeodata> Geodatas, List<AddressService> addressServices) throws ApplicationException {
        ResponseMessage logTransactionBD;
        logTransactionBD = createAddress(addressRequest.get(i), user, nombreFuncionalidad, validate);
        addressResult.get(i).setLogTransactionBD(logTransactionBD.getMessageText());
        addressResult.get(i).setAddressTraslateGeodata(Geodatas.get(i).getDirtrad());
        addressServiceNull(addressServices, i, addressResult);
    }

    /**
     *
     * @param addressServices
     * @param i
     * @param addressResult
     */
    private void addressServiceNull(List<AddressService> addressServices, int i, List<AddressFactResultBatch> addressResult) {
        if (addressServices.get(i) == null) {
            addressServices.get(i).setAddress("null");
            addressServices.get(i).setQualifiers("null");
            addressServices.get(i).setRecommendations("null");
            addressResult.get(i).setAddressResult(addressServices.get(i));
        } else {
            addressResult.get(i).setAddressResult(addressServices.get(i));
        }
    }

    /**
     * Carga las variables de configuración globales
     */
    private void queryParametrosConfig() {
        ResourceEJB recursos = new ResourceEJB();
        try {
            Parametros param = recursos.queryParametros(Constant.ID_CASA);
            if (param != null) {
                ID_CASA = new BigDecimal(param.getValor());
            }
            param = recursos.queryParametros(Constant.ID_EDIFICIO);
            if (param != null) {
                ID_EDIFICIO = new BigDecimal(param.getValor());
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }
    
    /**
     *
     * @param addressesRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<AddressService> queryAddressBatch(List<AddressRequest> addressesRequest) throws ApplicationException {
        List<AddressService> addressServices = null;
        /*call service address por lotes - XML*/
        List<AddressGeodata> geodatas;
        String nivelDetalle = "";

        try {
            geodatas = callServiceAddressBatch(addressesRequest);
            procesointercambio = geodatas;
        } catch (IOException | ServiceException | JDOMException ex) {
            geodatas = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

        // Construir los AddressService
        if (geodatas != null) {
            addressServices = new ArrayList<>();
            for (int i = 0; i < geodatas.size(); i++) {
                //Valida el nivel de detalle de cada item - Camilo Gaviria
                nivelDetalle = addressesRequest.get(i).getLevel().toUpperCase();
                AddressGeodata addressGeo = geodatas.get(i);
                AddressService addressService = new AddressService();
                
                 //JDHT
                    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                    GeograficoPoliticoMgl centroPobladoDireccion
                            = geograficoPoliticoManager.findCentroPobladoCodDane(addressesRequest.get(i).getCodDaneVt());

                    if (centroPobladoDireccion == null) {
                        throw new ApplicationException("Error consultando el centro poblado por codigo dane. No Encontrada.");

                    }

                boolean multiorigen = false;
                try {
                    multiorigen = queryCiudadMultiorigen(addressesRequest.get(i).getCity());
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                }

                //Se valida la obligatoriedad del barrio (Ciudades multiorigen)
                if ((multiorigen) && (addressesRequest.get(i).getNeighborhood() == null || addressesRequest.get(i).getNeighborhood().isEmpty())) {
                    addressService.setRecommendations("ciudad multiorigen, el barrio es obligatorio");
                }

                if (("".equals(addressesRequest.get(i).getCity())) || ("".equals(addressesRequest.get(i).getLevel())) || ("".equals(addressesRequest.get(i).getAddress())) || ("".equals(addressesRequest.get(i).getState())) || ((multiorigen) && (addressesRequest.get(i).getNeighborhood() == null || addressesRequest.get(i).getNeighborhood().isEmpty()))) {
                    addressService.setRecommendations("Faltan datos se requiere nivel de detalle, dirección, ciudad y departamento");
                    if ((multiorigen) && (addressesRequest.get(i).getNeighborhood() == null || addressesRequest.get(i).getNeighborhood().isEmpty())) {
                        addressService.setRecommendations("ciudad multiorigen, el barrio es obligatorio");
                    }
                } else {
                    addressService.setAddress(addressGeo.getDirtrad());

                    // Dirección Alterna Camilo Gaviria 2012-12-24
                    if ("NUEVA".equals(addressGeo.getFuente())) {
                        addressService.setAlternateaddress("");
                    } else {
                        addressService.setAlternateaddress(addressGeo.getDiralterna());
                    }
                    //Fin direccion alterna Camilo Gaviria 2012-12-24

                    addressService.setActivityeconomic(addressGeo.getActeconomica());
                    addressService.setAppletstandar(addressGeo.getManzana());
                    addressService.setNeighborhood(addressGeo.getBarrio());

                    //INICIO Direcciones face I Carlos Vilamil 2012-12-11
                    addressService.setComentEconomicLevel(addressGeo.getComentEconomicLevel());
                    addressService.setZipCode(addressGeo.getZipCode());
                    addressService.setCoddanemcpio(addressGeo.getCoddanemcpio());
                    //FIN Direcciones face I Carlos Vilamil 2012-12-11
                    //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                    addressService.setNodoUno(addressGeo.getNodo1());
                    addressService.setNodoDos(addressGeo.getNodo2());
                    addressService.setNodoTres(addressGeo.getNodo3());
                    //FIN Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                    addressService.setNodoDth(addressGeo.getNodoDth());
                    addressService.setNodoMovil(addressGeo.getNodoMovil());
                    addressService.setNodoFtth(addressGeo.getNodoFtth());
                    addressService.setNodoCuatro(addressGeo.getNodo4());
                    addressService.setNodoWifi(addressGeo.getNodoWifi());

                    addressGeo.getCoddir().substring(addressGeo.getCodencont().length());
                    boolean exist = validExistAddress(addressGeo.getDirtrad(), addressGeo.getEstado(), addressGeo.getValplaca(), addressGeo.getCodencont(), centroPobladoDireccion.getGpoId());
                    addressService.setExist(String.valueOf(exist));

                    if (addressesRequest.get(i).getLevel().equalsIgnoreCase("C")) {
                        addressService.setLevellive(addressGeo.getValplaca());
                        addressService.setQualifiers(addressGeo.getValplaca());
                    }

                    //Codigo de la direccion hasta la placa estandarizada por el WS
                    String codAddres = addressGeo.getCoddir();
                    String codAddresPlaca = addressGeo.getCodencont();
                    String complemento = codAddres.substring(codAddresPlaca.length());
                    String revisarEnCampo = "";

                    String activityeconomicOnRepo = "";
                    List<AddressSuggested> addressSuggested;                   

                    Direccion direccion = queryAddressOnRepository(addressGeo.getDirtrad(),centroPobladoDireccion.getGpoId());
                    String stratum = addressGeo.getEstrato();
                    String leveleconomic = addressGeo.getNivsocio();

                    //addressService.setLeveleconomic(addressGeo.getNivsocio()); - se comentarea para reparar nivel socio economico todas las consultas. - Camilo Gaviria
                    //estrato 
                    if (direccion != null) {
                        if (direccion.getDirEstrato() != null) {
                            stratum = direccion.getDirEstrato().toString();
                        } //else // Direcciones face I Carlos Vilamil 2013-05-24  V 1.2
                        if (direccion.getDirNivelSocioecono() != null) {
                            leveleconomic = direccion.getDirNivelSocioecono().toString();
                        }
                        //INICIO Direcciones face I Carlos Vilamil 2013-05-24  V 1.1
                        if (direccion.getNodoUno() != null && direccion.getNodoUno().equals("")) {
                            addressService.setNodoUno(direccion.getNodoUno());
                        }
                        if (direccion.getNodoDos() != null && direccion.getNodoDos().equals("")) {
                            addressService.setNodoDos(direccion.getNodoDos());
                        }
                        if (direccion.getNodoTres() != null && direccion.getNodoTres().equals("")) {
                            addressService.setNodoTres(direccion.getNodoTres());
                        }
                        //FIN Direcciones face I Carlos Vilamil 2013-05-24 V 1.1

                        if (direccion.getNodoDth() != null && direccion.getNodoDth().equals("")) {
                            addressService.setNodoDth(direccion.getNodoDth());
                        }
                        if (direccion.getNodoMovil() != null
                                && direccion.getNodoMovil().trim().isEmpty()
                                && !direccion.getNodoMovil().equalsIgnoreCase("NA")) {
                            addressService.setNodoMovil(direccion.getNodoMovil());
                        }
                        if (direccion.getNodoFtth() != null
                                && direccion.getNodoFtth().trim().isEmpty()
                                && !direccion.getNodoFtth().equalsIgnoreCase("NA")) {
                            addressService.setNodoFtth(direccion.getNodoFtth());
                        }

                        if (direccion.getNodoCuatro() != null
                                && direccion.getNodoCuatro().trim().isEmpty()
                                && !direccion.getNodoCuatro().equalsIgnoreCase("NA")) {
                            addressService.setNodoCuatro(direccion.getNodoCuatro());
                        }

                        if (direccion.getNodoWifi() != null
                                && !direccion.getNodoWifi().equalsIgnoreCase("NA")) {
                            addressService.setNodoWifi(direccion.getNodoWifi());
                        }
                    }

                    //Se consulta la direccion en el repositorio por el codDireccion retornado por el WS
                    //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.2
                    String idaddress;
                    if (direccion == null) {
                        idaddress = "0";
                        addressService.setRecommendations("La direccion no existe en el repositorio.");
                    } else {
                        //Si existe la direccion
                        //Se verifica si la direccion es una subdireccion
                        if (isSoloCeros(complemento)) {
                            //Es una direccion y si existe
                            idaddress = "d" + direccion.getDirId().toString();
                            revisarEnCampo = direccion.getDirRevisar();
                            addressService.setRecommendations("La direccion existe en el repositorio.");//Direcciones face I Carlos Vilamil 2013-05-24 V 1.2
                        } else {
                            //Se consulta como una subDireccion
                            SubDireccion subDireccion = querySubAddressOnRepoByCod(addressGeo.getDirtrad(), centroPobladoDireccion.getGpoId());
                            if (subDireccion == null) {
                                idaddress = "0";
                                addressService.setRecommendations("La direccion no existe en el repositorio.");
                            } else {
                                idaddress = "s" + subDireccion.getSdiId().toString();
                                revisarEnCampo = direccion.getDirRevisar();
                                addressService.setRecommendations("La direccion existe en el repositorio.");//Direcciones face I Carlos Vilamil 2013-05-24 V 1.2
                                if (subDireccion.getSdiEstrato() != null) {
                                    stratum = subDireccion.getSdiEstrato().toString();
                                } //else //Direcciones face I Carlos Vilamil 2013-05-24 V 1.2
                                if (subDireccion.getSdiNivelSocioecono() != null) {
                                    leveleconomic = subDireccion.getSdiNivelSocioecono().toString();
                                }
                                if (subDireccion.getSdiActividadEcono() != null) {
                                    activityeconomicOnRepo = subDireccion.getSdiActividadEcono().toString();
                                }
                                //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                                if (subDireccion.getNodoUno() != null && subDireccion.getNodoUno().equals("")) {
                                    addressService.setNodoUno(subDireccion.getNodoUno());
                                }
                                if (subDireccion.getNodoDos() != null && subDireccion.getNodoDos().equals("")) {
                                    addressService.setNodoDos(subDireccion.getNodoDos());
                                }
                                if (subDireccion.getNodoTres() != null && subDireccion.getNodoTres().equals("")) {
                                    addressService.setNodoTres(subDireccion.getNodoTres());
                                }
                                //FIN Direcciones face I Carlos Vilamil 2013-05-24 V 1.1

                                if (subDireccion.getNodoDth() != null && subDireccion.getNodoDth().equals("")) {
                                    addressService.setNodoDth(subDireccion.getNodoDth());
                                }
                                if (subDireccion.getNodoMovil() != null
                                        && subDireccion.getNodoMovil().trim().isEmpty()
                                        && !subDireccion.getNodoMovil().equalsIgnoreCase("NA")) {
                                    addressService.setNodoMovil(subDireccion.getNodoMovil());
                                }
                                if (subDireccion.getNodoFtth() != null
                                        && subDireccion.getNodoFtth().trim().isEmpty()
                                        && !subDireccion.getNodoFtth().equalsIgnoreCase("NA")) {
                                    addressService.setNodoFtth(subDireccion.getNodoFtth());
                                }
                                if (subDireccion.getNodoWifi() != null
                                        && subDireccion.getNodoWifi().trim().isEmpty()
                                        && !subDireccion.getNodoWifi().equalsIgnoreCase("NA")) {
                                    addressService.setNodoWifi(subDireccion.getNodoWifi());
                                }
                            }
                        }
                    }
                    addressService.setIdaddress(idaddress);
                    //FIN Direcciones face I Carlos Vilamil 2013-05-24 V 1.2

                    addressService.setLeveleconomic(leveleconomic);//Direcciones face I Carlos Vilamil 2013-05-24 V 1.2
                    addressService.setEstratoDef(stratum);//Direcciones face I Carlos Vilamil 2013-05-24 V 1.2

                    //Si el Nivel de detalle es Completo "C", se debe realizar la consulta en el repositorio
                    if (NIVEL_DETALLE_COMPLETO.equals(nivelDetalle.toUpperCase())) {
                        List<AddressAggregated> direccionesAgregadas = null;
                        if (queryCuentaMatriz(codAddresPlaca) && isSoloCeros(complemento)) {
                            //Se consultan las subdirecciones asociadas a la cuenta matriz
                            direccionesAgregadas = queryAddressAggregatedOnRepo(addressGeo.getDirtrad(), centroPobladoDireccion.getGpoId());
                        }
                    }

                    if (direccion == null && !exist) {
                        try {
                            if (addressesRequest.get(i).getLevel().equalsIgnoreCase("C")) {
                                addressSuggested = callServiceAddressSuggested(addressesRequest.get(i));
                                if (addressSuggested != null && !addressSuggested.isEmpty()) {
                                    addressService.setAddressSuggested(addressSuggested);
                                }
                            }
                        } catch (RemoteException | ServiceException ex) {
                            addressSuggested = null;
                            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                            LOGGER.error(msg);
                        }
                    }
                }
                addressServices.add(addressService);
            }
        }
            return addressServices;
    }
    /**
     *
     * @param addressesRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ApplicationException
     */
    @Override
    public List<AddressServiceBatchXml> queryAddressBatchPreficha(List<AddressRequest> addressesRequest) throws ApplicationException {
        List<AddressServiceBatchXml> addressServices = null;
        /*call service address por lotes - XML*/
        List<AddressGeoDirDataDto> geodatas;
        double regActual,porcentaje;
        try {
            geodatas = callServiceGeoAddressBatchXml(addressesRequest);
        } catch (IOException  | ServiceException | JDOMException ex) {
            geodatas = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,ex);
            
        }

        // Construir los AddressService
        if (geodatas != null) {
            addressServices = new ArrayList<>();
            for (int i = 0; i < geodatas.size(); i++) {
                AddressGeoDirDataDto addressGeo = geodatas.get(i);
                AddressServiceBatchXml addressService = createAddressServiceBatch(addressGeo,addressesRequest.get(i));
                
                addressServices.add(addressService);
                regActual = i+1;
                porcentaje = returnPorcentaje(regActual, addressesRequest.size());
                porcentaje = redondearDecimales(porcentaje, 1);
            }
        }
        return addressServices;
    }

    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public AddressService queryAddress(AddressRequest addressRequest) throws ApplicationException {
        AddressGeodata addressGeodata;
        AddressService addressService;
        List<AddressSuggested> addressSuggested;
        /*call service address por lotes - XML*/
        try {
            addressGeodata = callServiceAddress(addressRequest);
        } catch (IOException | ServiceException | JDOMException ex) {
            addressGeodata = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

        /*call service address suggestes*/
        try {
            addressSuggested = callServiceAddressSuggested(addressRequest);
        } catch (RemoteException | ServiceException ex) {
            addressSuggested = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

        /*implementar logica de negocio*/
        if (addressGeodata == null) {
            addressService = new AddressService();
            addressService.setRecommendations("ERROR: Geodata fuera de servicio");
        } else {
            addressService = createAddressService(addressGeodata, addressRequest);

            //Fin de la validación de direcciones.
            addressService.setAddressSuggested(addressSuggested);
        }

        if (addressRequest.getLevel().trim().equalsIgnoreCase("S")) {
            addressService = getAddressServiceSimpleQuery(addressService);
        }

        if (addressService.getBarrioTraducido() == null
                || addressService.getBarrioTraducido().trim().isEmpty()) {
            addressService.setBarrioTraducido(null);
        }

        if (addressService.getEstratoDef() == null
                || addressService.getEstratoDef().trim().isEmpty()) {
            addressService.setEstratoDef(null);
        }

        return addressService;
    }

    private AddressService getAddressServiceSimpleQuery(AddressService addressService) {
        AddressService addressServiceSimple = new AddressService();

        addressServiceSimple.setAddress(addressService.getAddress());
        addressServiceSimple.setActivityeconomic(addressService.getActivityeconomic());
        addressServiceSimple.setNeighborhood(addressService.getNeighborhood());
        addressServiceSimple.setExist(addressService.getExist());
        addressServiceSimple.setAlternateaddress(addressService.getAlternateaddress());
        addressServiceSimple.setAppletstandar(addressService.getAppletstandar());
        addressServiceSimple.setEstratoDef(addressService.getEstratoDef());
        addressServiceSimple.setLeveleconomic(addressService.getLeveleconomic());
        addressServiceSimple.setLevellive(addressService.getLevellive());

        return addressServiceSimple;
    }

    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public AddressService queryAddressLTE(AddressRequest addressRequest) throws ApplicationException {
        AddressGeodata addressGeodata;
        AddressService addressService;
        List<AddressSuggested> addressSuggested;
        /*call service address por lotes - XML*/
        try {
            addressGeodata = callServiceAddress(addressRequest);
        } catch (IOException | ServiceException | JDOMException ex) {
            addressGeodata = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

        /*call service address suggestes*/
        try {
            addressSuggested = callServiceAddressSuggested(addressRequest);
        } catch (RemoteException | ServiceException ex) {
            addressSuggested = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

        /*implementar logica de negocio*/
        if (addressGeodata == null) {
            addressService = new AddressService();
            addressService.setRecommendations("ERROR: Geodata fuera de servicio");
        } else {
            addressService = createAddressService(addressGeodata, addressRequest);
            if (addressSuggested != null) {
                addressService.setAddressSuggested(addressSuggested);
            }
        }
        return addressService;
    }

    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public AddressGeodata queryAddressGeodata(AddressRequest addressRequest) throws ApplicationException {
        AddressGeodata addressGeodata;
        try {
            addressGeodata = callServiceAddress(addressRequest);
        } catch (IOException | ServiceException | JDOMException ex) {
            addressGeodata = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg);
        }
        return addressGeodata;
    }

    /**
     * Consulta de direcciones en batch con una sola identificacion
     *
     * @param listAddressRequest
     * @return
     */
    @Override
    public List<AddressGeoDirDataDto> queryListAddressGeodataXml(List<AddressRequest> listAddressRequest) throws ApplicationException {
        List<AddressGeoDirDataDto> listAddress;
        try {
            listAddress = callServiceGeoAddressBatchXml(listAddressRequest);
        } catch (IOException | ServiceException | JDOMException ex) {
            listAddress = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return listAddress;
    }
    
    /**
     * Consulta de direcciones en batch con una sola identificacion
     *
     * @param listAddressRequest
     * @return
     */
    @Override
    public List<AddressGeodata> queryListAddressGeodata(List<AddressRequest> listAddressRequest) throws ApplicationException {
        List<AddressGeodata> listAddress;
        try {
            listAddress = callServiceAddressBatch(listAddressRequest);
        } catch (IOException | ServiceException | JDOMException ex) {
            listAddress = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return listAddress;
    }

    /**
     * Ejecuta el llamado a la operación enrich de SITIDATA para enriquecer la dirección
     * @param addressRequest Objeto con los datos de la dirección a enriquecer
     * @return {@link AddressService}Objeto con los datos de la dirección enriquecida
     * @throws ApplicationException Excepción en caso de error
     */
    @Override
    public AddressService queryAddressEnrich(AddressRequest addressRequest) throws ApplicationException {
        AddressGeodata addressGeodata;
        AddressService addressService;
        List<AddressSuggested> addressSuggested;

        /*call service address*/
        try {
            addressGeodata = callServiceEnrich(addressRequest);
        } catch (RemoteException | ServiceException ex) {
            addressGeodata = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }

        if (addressGeodata == null) {
            addressService = new AddressService();
            addressService.setRecommendations("ERROR: Geodata fuera de servicio");
            return addressService;
        }

        /*call service address suggestes*/
        try {
            addressSuggested = callServiceAddressSuggested(addressRequest);
        } catch (RemoteException | ServiceException ex) {
            addressSuggested = null;
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }

        /*implementar logica de negocio*/
        addressService = createAddressService(addressGeodata, addressRequest);
        addressService.setRespuestaGeo(addressGeodata);
        //Fin de la validación de direcciones.
        addressService.setAddressSuggested(addressSuggested);
        return addressService;
    }

    /**
     * Método Enriquecer del WebService
     *
     * @param addressRequest
     * @return
     * @throws Exception
     * @throws SOAPException
     * @throws XPathExpressionException
     */
    private AddressGeodata callServiceEnrich(AddressRequest addressRequest) throws RemoteException, ApplicationException, ServiceException {
        WsSitidataStandarLocator locator = new WsSitidataStandarLocator();
        WsSitidataStandarPortType proxyWS = locator.getwsSitidataStandarPort();
        ClientProxyWs client = new ClientProxyWs();

        String address = addressRequest.getAddress();
        String city = addressRequest.getCity();
        String state = addressRequest.getState();
        //JDHT si llega el codigo dane, enviarlo en la ciudad y quitar el departamento (nuevo ajuste geo)
        if (addressRequest.getCodDaneVt() != null && !addressRequest.getCodDaneVt().isEmpty()) {
             city = addressRequest.getCodDaneVt();
             state = "";
        }
        String neighborhood = addressRequest.getNeighborhood();   

        ResponseArrayFromWS response = proxyWS.enrich(address, state, city, neighborhood, ConstantWS.USER, ConstantWS.PWD);

        //Se obtiene la lista de parametros retornados por el WebService
        String[] array = response.getGeo();
        List<String> list = Arrays.asList(array);

        boolean multiorigen = false;
        try {
            multiorigen = queryCiudadMultiorigen(addressRequest.getCity());
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage());
        }

        return client.createAddressGeoFromList(list,
                addressRequest.getNeighborhood(),
                multiorigen);
    }

    /**
     * Consulta el método por Lotes
     *
     * @param addressRequest
     * @return
     * @throws Exception
     * @throws SOAPException
     * @throws XPathExpressionException
     */
    private AddressGeodata callServiceAddress(AddressRequest addressRequest) throws RemoteException, IOException, JDOMException, ApplicationException, ServiceException {

        return callServiceEnrich(addressRequest);
    }
    
    /**
     * Consulta el metodo por Lotes
     *
     * @param addressesRequest lista de direcciones a validar
     * @return
     * @throws Exception
     * @throws SOAPException
     * @throws XPathExpressionException
     */
    private List<AddressGeodata> callServiceAddressBatch100(List<AddressRequest> addressesRequest) throws ServiceException, RemoteException, IOException, JDOMException {
        WsSitidataStandarLocator locator = new WsSitidataStandarLocator();
        WsSitidataStandarPortType proxyWS = locator.getwsSitidataStandarPort();
        ConvertXML convertXML = new ConvertXML();
        String textXML = convertXML.createXMLRequest(addressesRequest).replaceAll("[\r\n]", "");
        String responseXML = proxyWS.enrichXmlBatch(textXML, ConstantWS.USER, ConstantWS.PWD);

        return convertXML.transformToListAddressGeodata(responseXML);
    }
    /**
     * Consulta el metodo por Lotes
     *
     * @param addressesRequest lista de direcciones a validar
     * @return
     * @throws Exception
     * @throws SOAPException
     * @throws XPathExpressionException
     */
    private List<AddressGeoDirDataDto> callServiceAddressEnrichXml(List<AddressRequest> addressesRequest) throws ServiceException, RemoteException, IOException, JDOMException {
        WsSitidataStandarLocator locator = new WsSitidataStandarLocator();
        WsSitidataStandarPortType proxyWS = locator.getwsSitidataStandarPort();
        ConvertXML convertXML = new ConvertXML();
        List<AddressGeodata> addressesGeo;
        List<AddressGeoDirDataDto> responseAddressGeo = null;
        String textXML = convertXML.createXMLRequest(addressesRequest).replaceAll("[\r\n]", "");
        String responseXML = proxyWS.enrichXmlBatch(textXML, ConstantWS.USER, ConstantWS.PWD);
       
        responseXML = convertXML.formatResponseBatchToXml(responseXML);
        addressesGeo = convertXML.transformToListAddressGeodata(responseXML);
        responseAddressGeo = copyToAddressGeoDirDataDto(addressesGeo);
        return responseAddressGeo;
    }
    
    private List<AddressGeoDirDataDto> copyToAddressGeoDirDataDto(List<AddressGeodata> addressesGeo){
        List<AddressGeoDirDataDto> responseAddressGeoDirDataDto = new ArrayList<>();
        
        for(AddressGeodata element : addressesGeo){
            AddressGeoDirDataDto addressDto = new AddressGeoDirDataDto(
                               element.getIdentificador(), element.getDirtrad(),  element.getBartrad(), element.getCoddir(),
                               element.getCodencont(), element.getFuente(), element.getDiralterna(), element.getAmbigua(),
                               element.getValagreg(), element.getManzana(), element.getBarrio(), element.getLocalidad(),
                               element.getNivsocio(), element.getCx(), element.getCy(), element.getNodo1(),element.getNodo2(),
                               element.getNodo3(), element.getEstrato(), element.getActeconomica(), element.getNodoDth(),
                               element.getNodoMovil(), element.getNodoFtth(),element.getNodoWifi(), element.getGeoZonaUnifilar(),
                               element.getGeoZonaGponDiseniado(), element.getGeoZonaMicroOndas(), element.getGeoZona3G(),
                               element.getGeoZona4G(), element.getGeoZonaCoberturaCavs(), element.getGeoZonaCoberturaUltimaMilla(),
                               element.getGeoZonaCurrier(), element.getGeoZona5G(), element.getCoddanemcpio(), element.getEstado(),
                               element.getMensaje(), element.getValplaca(),null,null,null);
            
            responseAddressGeoDirDataDto.add(addressDto);
        }
        return responseAddressGeoDirDataDto;
    }
    
    /**
     *
     * @param addressesRequest
     * @return
     * @throws ServiceException
     * @throws RemoteException
     * @throws IOException
     * @throws JDOMException
     */
    private List<AddressGeoDirDataDto> callServiceGeoAddressBatchXml(List<AddressRequest> addressesRequest) throws ServiceException, RemoteException, IOException, JDOMException, ApplicationException {
        int control = 0;
        List<AddressRequest> listacorta = new ArrayList<>();
        List<AddressGeoDirDataDto> temporal;
        List<AddressGeoDirDataDto> resultado = new ArrayList<>();
        int paramDirLote = 60;
        //Se consulta parametro para la cantidad de direcciones a procesar por lote
        ParametrosMglManager parametros = new ParametrosMglManager();
        String dirBatchXml = (parametros.findByAcronimoName(Constant.DIR_BATCH_XML_WSGEO)).getParValor();
        if(dirBatchXml == null || dirBatchXml.isEmpty()){
            throw new ApplicationException("No se ecuentra parametro" +Constant.DIR_BATCH_XML_WSGEO);
        }
        else{
            paramDirLote = Integer.parseInt(dirBatchXml);
        }
            
        while (control < addressesRequest.size()) {
            if ((control > 0) && ((control % paramDirLote) == 0) || (control == addressesRequest.size() - 1)) {
                //Si es el ultimo registro lo agrega para el proceso.
                if (control == addressesRequest.size() - 1) {
                    listacorta.add(addressesRequest.get(control));
                }
                //Llamado al WS
                temporal = callServiceAddressEnrichXml(listacorta);
                for (AddressGeoDirDataDto item : temporal) {
                    resultado.add(item);
                }
                //Limpia lista y agrega a resultado
                listacorta = new ArrayList<>();
                listacorta.add(addressesRequest.get(control));
                control = control + 1;
                
            } else {
                //Agregar a la lista
                listacorta.add(addressesRequest.get(control));
                control = control + 1;
                
            }
        }
        LOGGER.info("Proceso por lotes  finalizado. \n");
        return resultado;
    }
    
    private double returnPorcentaje(double regActual, double regTotales) {

        double cien = 100;
        double porcentaje = (regActual * cien) / regTotales;
        return porcentaje;
    }
    
    private double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
    }
    
    /**
     *
     * @param addressesRequest
     * @return
     * @throws ServiceException
     * @throws RemoteException
     * @throws IOException
     * @throws JDOMException
     */
    private List<AddressGeodata> callServiceAddressBatch(List<AddressRequest> addressesRequest) throws ServiceException, RemoteException, IOException, JDOMException, ApplicationException {
        int control = 0;
        List<AddressRequest> listacorta = new ArrayList<>();
        List<AddressGeodata> temporal;
        List<AddressGeodata> resultado = new ArrayList<>();

        while (control < addressesRequest.size()) {
            if ((control > 0) && ((control % 100) == 0) || (control == addressesRequest.size() - 1)) {
                //Si es el ultimo registro lo agrega para el proceso.
                if (control == addressesRequest.size() - 1) {
                    listacorta.add(addressesRequest.get(control));
                }
                //Llamado al WS
                temporal = callServiceAddressBatch100(listacorta);
                for (AddressGeodata item : temporal) {
                    resultado.add(item);
                }
                //Limpia lista y agrega a resultado
                listacorta = new ArrayList<>();
                listacorta.add(addressesRequest.get(control));
                control = control + 1;
            } else {
                //Agregar a la lista
                listacorta.add(addressesRequest.get(control));
                control = control + 1;
            }
        }
        LOGGER.info("Proceso por lotes  finalizado. \n");
        return resultado;
    }

    /**
     * Consulta de direcciones sugeridas
     *
     * @param addressRequest
     * @return
     * @throws Exception
     * @throws SOAPException
     * @throws XPathExpressionException
     */
    private List<AddressSuggested> callServiceAddressSuggested(AddressRequest addressRequest) throws ServiceException, RemoteException {
        WsSitidataStandarLocator locator = new WsSitidataStandarLocator();
        WsSitidataStandarPortType proxyWS = locator.getwsSitidataStandarPort();

        ClientProxyWs client = new ClientProxyWs();
        
        String address = addressRequest.getAddress();
        String city = addressRequest.getCity();
        String state = addressRequest.getState();
        //JDHT si llega el codigo dane, enviarlo en la ciudad y quitar el departamento (nuevo ajuste geo)
        if (addressRequest.getCodDaneVt() != null && !addressRequest.getCodDaneVt().isEmpty()) {
             city = addressRequest.getCodDaneVt();
             state = "";
        }
        String neighborhood = addressRequest.getNeighborhood();        

        ResponseArrayFromWS responseArray = proxyWS.enrichAssist(address, state, city, neighborhood, ConstantWS.USER, ConstantWS.PWD);

        //Se obtiene la lista de parametros retornados por el WebService
        String[] array = responseArray.getGeo();
        List<String> list = Arrays.asList(array);
        return client.createListSuggested(list);
    }
    
    /**
     *
     * @param addressGeodata
     * @param addressRequest
     * @return
     * @throws ApplicationException
     */
    @Override
    public AddressServiceBatchXml createAddressServiceBatch(AddressGeoDirDataDto addressGeodata, AddressRequest addressRequest) throws ApplicationException {
    
        AddressServiceBatchXml addressService = new AddressServiceBatchXml();
        List<AddressSuggested> addressSuggested;
        
        String idaddress;
        String category;
        String appletstandar;
        String recommendations;
        String address = addressGeodata.getDirtrad();
        String neighborhood = addressGeodata.getBarrio();
        String stratum = addressGeodata.getEstrato();
        //TODO: Falta por definirse por Servinformacion
        String leveleconomic = addressGeodata.getNivsocio();
        String chagenumber = addressGeodata.getFuente();
        String alternateaddress = addressGeodata.getDiralterna();
        String levellive = addressGeodata.getValplaca();
        String qualifiers = addressGeodata.getValplaca();
        String activityeconomic = addressGeodata.getActeconomica();
        String activityeconomicOnRepo = "";
        String state = addressGeodata.getEstado();
        //Codigo de la direccion hasta la placa estandarizada por el WS
        String codAddres = addressGeodata.getCoddir();
        String codAddresPlaca = addressGeodata.getCodencont();
        String ambigua = addressGeodata.getAmbigua();
        String revisarEnCampo = "";
        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
        String codZip = addressGeodata.getZipCode();
        String codDivipola = addressGeodata.getCoddanemcpio();
        String comSocioEconomico = addressGeodata.getComentEconomicLevel();
        //Fin Modificacion Carlos Villamil Direcciones Fase I 20121218

        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1
        String nodoUno = addressGeodata.getNodo1();
        String nodoDos = addressGeodata.getNodo2();
        String nodoTres = addressGeodata.getNodo3();
        String nodoCuatro = addressGeodata.getNodo4();

        String nodoDth = addressGeodata.getNodoDth();
        String nodoMovil = addressGeodata.getNodoMovil();
        String nodoFtth = addressGeodata.getNodoFtth();
        String nodoWifi = addressGeodata.getNodoWifi();
        //JDHT
        String nodoZonaUnifilar = addressGeodata.getGeoZonaUnifilar();
        String nodoZona3G = addressGeodata.getGeoZona3G();
        String nodoZona4G = addressGeodata.getGeoZona4G();
        String nodoZona5G = addressGeodata.getGeoZona5G();
        String nodoZonaCoberturaCavs = addressGeodata.getGeoZonaCoberturaCavs();
        String nodoZonaCoberturaUltimaMilla = addressGeodata.getGeoZonaCoberturaUltimaMilla();
        String nodoZonaGponDiseniado = addressGeodata.getGeoZonaGponDiseniado();
        String nodoZonaMicroOndas = addressGeodata.getGeoZonaMicroOndas();
        String nodoZonaCurrier = addressGeodata.getGeoZonaCurrier();
        
        //valor de la placa de la direccion (campo 7)
        String confiabilidadDir = addressGeodata.getValagreg();
        //valor de la direccion (campo 8)
        String confiabilidadPlaca = addressGeodata.getValplaca();
        
        boolean exist;
        boolean traslate;
        List<AddressAggregated> direccionesAgregadas = null;
        //Se extrae el complemento de la direccion
        String complemento = codAddres.substring(codAddresPlaca.length());
        //Si el Nivel de detalle es Completo "C", se debe realizar la consulta en el repositorio
        DireccionMglManager direccionMglManager = new DireccionMglManager();
        UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();


        addressService.setAmbigua(ambigua);
        addressService.setSource(addressGeodata.getFuente());
        addressService.setBarrioTraducido(addressGeodata.getBartrad());
        addressService.setAddressCode(addressGeodata.getCoddir());
        addressService.setAddressCodeFound(addressGeodata.getCodencont());
        addressService.setLocality(addressGeodata.getLocalidad());
        addressService.setCx(addressGeodata.getCx());
        addressService.setCy(addressGeodata.getCy());
        addressService.setState(addressGeodata.getEstado());
        
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();       
        GeograficoPoliticoMgl centroPobladoDireccion 
               = geograficoPoliticoManager.findCentroPobladoCodDane(addressRequest.getCodDaneVt());
         
         if(centroPobladoDireccion == null){
              throw new ApplicationException("Error consultando el centro poblado por codigo dane. No Encontrada.");
             
         }
        
        /*Si la direccion es multiorign es Calle-Carrera 
         tiene seteado el tipo de direccion (desde Factibilidad, HHPP, CCMM) */
        if(centroPobladoDireccion.getGpoMultiorigen().equalsIgnoreCase("1")
                && addressRequest.getTipoDireccion() != null
                && !addressRequest.getTipoDireccion().isEmpty()
                && addressRequest.getTipoDireccion().equalsIgnoreCase("CK")
                && addressRequest.getNeighborhood() != null 
                && !addressRequest.getNeighborhood().isEmpty()){
            
            if(addressGeodata.getDirtrad() != null 
                    && !addressGeodata.getDirtrad().isEmpty()
                    && addressGeodata.getDirtrad().toUpperCase().contains(addressRequest.getNeighborhood().toUpperCase())){
         
            }else{
                addressGeodata.setDirtrad(addressGeodata.getDirtrad()+ " " + addressRequest.getNeighborhood().toUpperCase());
            }
        }
         
         
        //Se consulta la direccion en el repositorio por el codDireccion retornado por el WS
        /*JDHT se realiza cambio de valor de busqueda debido a que no todas las direcciones tiene codigo de serviinformacion,
        se realiza busque por formato igac*/
        Direccion direccion = queryAddressOnRepository(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
        SubDireccion subDireccion = querySubAddressOnRepoByCod(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
        
        if (direccion == null && subDireccion == null) {
            Direccion intraducible
                    = queryAddressIntraducibleOnRepoByIgac(addressRequest.getAddress(), addressRequest.getCodDaneVt());

            if (intraducible == null) {
                idaddress = "0";
                addressService.setRecommendations("La direccion no existe en el repositorio.");
                //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                addressService.setNodoUno(nodoUno);
                addressService.setNodoDos(nodoDos);
                addressService.setNodoTres(nodoTres);
                //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                
                addressService.setNodoDth(nodoDth);
                addressService.setNodoMovil(nodoMovil);
                addressService.setNodoFtth(nodoFtth);
                addressService.setNodoCuatro(nodoCuatro);
                addressService.setNodoWifi(nodoWifi);
                
                addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                addressService.setNodoZona3G(nodoZona3G);
                addressService.setNodoZona4G(nodoZona4G);
                addressService.setNodoZona5G(nodoZona5G);
                addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                addressService.setNodoZonaCurrier(nodoZonaCurrier);
                addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
 
                
            } else {
                /*author Juan David Hernandez*/
                //se comenta debido a que se realizó la funcionalidad pero ya no va agosto 17 2018
                //Actualización de coberturas y geoData por consulta
                try {
                    
                    DireccionMgl direccionMglIntraducibleActualizada = new DireccionMgl();
                    direccionMglIntraducibleActualizada.setDirId(intraducible.getDirId());
                    if (addressGeodata.getNivsocio() != null && !addressGeodata.getNivsocio().isEmpty()) {
                        direccionMglIntraducibleActualizada.setDirNivelSocioecono(new BigDecimal(addressGeodata.getNivsocio()));
                    } else {
                        direccionMglIntraducibleActualizada.setDirNivelSocioecono(new BigDecimal(-1));
                    }

                    direccionMglIntraducibleActualizada.setDirNodouno(nodoUno != null && !nodoUno.isEmpty() ? nodoUno : null);
                    direccionMglIntraducibleActualizada.setDirNododos(nodoDos != null && !nodoDos.isEmpty() ? nodoDos : null);
                    direccionMglIntraducibleActualizada.setDirNodotres(nodoTres != null && !nodoTres.isEmpty() ? nodoTres : null);
                    direccionMglIntraducibleActualizada.setDirNodoDth(nodoDth != null && !nodoDth.isEmpty() ? nodoDth : null);
                    direccionMglIntraducibleActualizada.setDirNodoMovil(nodoMovil != null && !nodoMovil.isEmpty() ? nodoMovil : null);
                    direccionMglIntraducibleActualizada.setDirNodoFtth(nodoFtth != null && !nodoFtth.isEmpty() ? nodoFtth : null);
                    direccionMglIntraducibleActualizada.setDirNodoWifi(nodoWifi != null && !nodoWifi.isEmpty() ? nodoWifi : null);
                    
                    direccionMglIntraducibleActualizada.setGeoZonaUnifilar(nodoZonaUnifilar != null && !nodoZonaUnifilar.isEmpty() ? nodoZonaUnifilar : null);
                    direccionMglIntraducibleActualizada.setGeoZona3G(nodoZona3G != null && !nodoZona3G.isEmpty() ? nodoZona3G : null);
                    direccionMglIntraducibleActualizada.setGeoZona4G(nodoZona4G != null && !nodoZona4G.isEmpty() ? nodoZona4G : null);
                    direccionMglIntraducibleActualizada.setGeoZona5G(nodoZona5G != null && !nodoZona5G.isEmpty() ? nodoZona5G : null);
                    direccionMglIntraducibleActualizada.setGeoZonaCoberturaCavs(nodoZonaCoberturaCavs != null && !nodoZonaCoberturaCavs.isEmpty() ? nodoZonaCoberturaCavs : null);
                    direccionMglIntraducibleActualizada.setGeoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla != null && !nodoZonaCoberturaUltimaMilla.isEmpty() ? nodoZonaCoberturaUltimaMilla : null);
                    direccionMglIntraducibleActualizada.setGeoZonaCurrier(nodoZonaCurrier != null && !nodoZonaCurrier.isEmpty() ? nodoZonaCurrier : null);
                    direccionMglIntraducibleActualizada.setGeoZonaGponDiseniado(nodoZonaGponDiseniado != null && !nodoZonaGponDiseniado.isEmpty() ? nodoZonaGponDiseniado : null);
                    direccionMglIntraducibleActualizada.setGeoZonaMicroOndas(nodoZonaMicroOndas != null && !nodoZonaMicroOndas.isEmpty() ? nodoZonaMicroOndas : null);
            
                    boolean actualizacionExitosaDireccionIt = direccionMglManager
                            .actualizarCoberturasGeoDireccionMgl(direccionMglIntraducibleActualizada);

                    if (intraducible.getUbicacion() != null && intraducible.getUbicacion().getUbiId() != null
                            && hasValidCoordinatesAddressGeoDir.test(addressGeodata)
                            && (hasInvalidCoordinatesLocation.test(intraducible.getUbicacion())
                            || hasDifferentCoordinatesGeoDir.test(intraducible.getUbicacion(), addressGeodata))) {
                        UbicacionMgl ubicacionDireccion = generarObjetoUbicacion(intraducible, addressGeodata.getCy(), addressGeodata.getCx());
                        ubicacionMglManager.actualizarCoordenadasGeoDireccionMgl(ubicacionDireccion);
                    }

                    //Si se actualiza correctamente las coberturas, se consulta de nuevo el objeto con toda la información.               
                    if (actualizacionExitosaDireccionIt) {
                        intraducible
                                = queryAddressIntraducibleOnRepoByIgac(addressRequest.getAddress(), addressRequest.getCodDaneVt());
                    }
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);  
                }
                //Fin Ajuste Juan David Hernandez */

                // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
                //los valores de Cx y Cy son cambiados por los consultados en ubicacion por medio de la direccion
                if (intraducible.getCx() != null && !intraducible.getCx().isEmpty()
                        && intraducible.getCy() != null && !intraducible.getCy().isEmpty()) {
                    addressService.setCx(intraducible.getCx());
                    addressService.setCy(intraducible.getCy());
                }
                // </editor-fold>
                //Si existe la direccion
                idaddress = "d" + intraducible.getDirId().toString();
                revisarEnCampo = intraducible.getDirRevisar();
                if ("1".equals(intraducible.getDirRevisar())) {
                    addressService.setRecommendations("La direccion existe en el repositorio. La dirección se debe validar en terreno.");
                } else {
                    addressService.setRecommendations("La direccion existe en el repositorio.");
                }
                if (intraducible.getNodoUno() == null
                        || intraducible.getNodoUno().trim().isEmpty()) {
                    addressService.setNodoUno(nodoUno);
                } else {
                    addressService.setNodoUno(intraducible.getNodoUno());
                }
                if (intraducible.getNodoDos() == null
                        || intraducible.getNodoDos().trim().isEmpty()) {
                    addressService.setNodoDos(nodoDos);
                } else {
                    addressService.setNodoDos(intraducible.getNodoDos());
                }
                if (intraducible.getNodoTres() == null
                        || intraducible.getNodoTres().trim().isEmpty()) {
                    addressService.setNodoTres(nodoTres);
                } else {
                    addressService.setNodoTres(intraducible.getNodoTres());
                }
                if (intraducible.getNodoCuatro() == null
                        || intraducible.getNodoCuatro().trim().isEmpty()) {
                    addressService.setNodoCuatro(nodoCuatro);
                } else {
                    addressService.setNodoCuatro(intraducible.getNodoCuatro());
                }
                if (intraducible.getNodoDth() == null
                        || intraducible.getNodoDth().trim().isEmpty()) {
                    addressService.setNodoDth(nodoDth);
                } else {
                    addressService.setNodoDth(intraducible.getNodoDth());
                }
                if (intraducible.getNodoMovil() == null
                        || intraducible.getNodoMovil().trim().isEmpty()) {
                    addressService.setNodoMovil(nodoMovil);
                } else {
                    addressService.setNodoMovil(intraducible.getNodoMovil());
                }
                if (intraducible.getNodoFtth() == null
                        || intraducible.getNodoFtth().trim().isEmpty()) {
                    addressService.setNodoFtth(nodoFtth);
                } else {
                    addressService.setNodoFtth(intraducible.getNodoFtth());
                }
                if (intraducible.getNodoWifi() == null
                        || intraducible.getNodoWifi().trim().isEmpty()) {
                    addressService.setNodoWifi(nodoWifi);
                } else {
                    addressService.setNodoWifi(intraducible.getNodoWifi());
                }
                //JDHT
                if (intraducible.getGeoZonaUnifilar() == null
                        || intraducible.getGeoZonaUnifilar().trim().isEmpty()) {
                    addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                } else {
                    addressService.setNodoZonaUnifilar(intraducible.getGeoZonaUnifilar());
                }
                
                 if (intraducible.getGeoZona3G()== null
                        || intraducible.getGeoZona3G().trim().isEmpty()) {
                    addressService.setNodoZona3G(nodoZona3G);
                } else {
                    addressService.setNodoZona3G(intraducible.getGeoZona3G());
                }
                 
                  if (intraducible.getGeoZona4G()== null
                        || intraducible.getGeoZona4G().trim().isEmpty()) {
                    addressService.setNodoZona4G(nodoZona4G);
                } else {
                    addressService.setNodoZona4G(intraducible.getGeoZona4G());
                }
                  
                if (intraducible.getGeoZona5G() == null
                        || intraducible.getGeoZona5G().trim().isEmpty()) {
                    addressService.setNodoZona5G(nodoZona5G);
                } else {
                    addressService.setNodoZona5G(intraducible.getGeoZona5G());
                }
                
                if (intraducible.getGeoZonaCoberturaCavs()== null
                        || intraducible.getGeoZonaCoberturaCavs().trim().isEmpty()) {
                    addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                } else {
                    addressService.setNodoZonaCoberturaCavs(intraducible.getGeoZonaCoberturaCavs());
                }
                
                 if (intraducible.getGeoZonaCoberturaUltimaMilla()== null
                        || intraducible.getGeoZonaCoberturaUltimaMilla().trim().isEmpty()) {
                    addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                } else {
                    addressService.setNodoZonaCoberturaUltimaMilla(intraducible.getGeoZonaCoberturaUltimaMilla());
                }
                 
                  if (intraducible.getGeoZonaCurrier()== null
                        || intraducible.getGeoZonaCurrier().trim().isEmpty()) {
                    addressService.setNodoZonaCurrier(nodoZonaCurrier);
                } else {
                    addressService.setNodoZonaCurrier(intraducible.getGeoZonaCurrier());
                }
                  
                if (intraducible.getGeoZonaGponDiseniado()== null
                        || intraducible.getGeoZonaGponDiseniado().trim().isEmpty()) {
                    addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                } else {
                    addressService.setNodoZonaGponDiseniado(intraducible.getGeoZonaGponDiseniado());
                }
                
                 if (intraducible.getGeoZonaMicroOndas()== null
                        || intraducible.getGeoZonaMicroOndas().trim().isEmpty()) {
                    addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                } else {
                    addressService.setNodoZonaMicroOndas(intraducible.getGeoZonaMicroOndas());
                }
                  
                
            }
        } else {
            //Si existe la direccion          
            //Se verifica si la direccion es una subdireccion
            if (direccion != null) {
                /*author Juan David Hernandez*/
                //se comenta debido a que se realizó la funcionalidad pero ya no va agosto 17 2018
                //Actualización de coberturas y geoData por consulta
                try {
                    
                    DireccionMgl direccionMglActualizada = new DireccionMgl();
                    direccionMglActualizada.setDirId(direccion.getDirId());
                    if (leveleconomic != null && !leveleconomic.isEmpty()) {
                        direccionMglActualizada.setDirNivelSocioecono(new BigDecimal(leveleconomic));
                    } else {
                        direccionMglActualizada.setDirNivelSocioecono(new BigDecimal(-1));
                    }
                    
                    direccionMglActualizada.setDirNodouno(nodoUno != null && !nodoUno.isEmpty() ? nodoUno : null);
                    direccionMglActualizada.setDirNododos(nodoDos != null && !nodoDos.isEmpty() ? nodoDos : null);
                    direccionMglActualizada.setDirNodotres(nodoTres != null && !nodoTres.isEmpty() ? nodoTres : null);
                    direccionMglActualizada.setDirNodoFttx(nodoCuatro != null && !nodoCuatro.isEmpty() ? nodoCuatro : null);
                    direccionMglActualizada.setDirNodoDth(nodoDth != null && !nodoDth.isEmpty() ? nodoDth : null);
                    direccionMglActualizada.setDirNodoMovil(nodoMovil != null && !nodoMovil.isEmpty() ? nodoMovil : null);
                    direccionMglActualizada.setDirNodoFtth(nodoFtth != null && !nodoFtth.isEmpty() ? nodoFtth : null);
                    direccionMglActualizada.setDirNodoWifi(nodoWifi != null && !nodoWifi.isEmpty() ? nodoWifi : null);
                    direccionMglActualizada.setGeoZona3G(nodoZona3G != null && !nodoZona3G.isEmpty() ? nodoZona3G : null);    
                    direccionMglActualizada.setGeoZona4G(nodoZona4G != null && !nodoZona4G.isEmpty() ? nodoZona4G : null); 
                    direccionMglActualizada.setGeoZona5G(nodoZona5G != null && !nodoZona5G.isEmpty() ? nodoZona5G : null); 
                    direccionMglActualizada.setGeoZonaCoberturaCavs(nodoZonaCoberturaCavs != null && !nodoZonaCoberturaCavs.isEmpty() ? nodoZonaCoberturaCavs : null); 
                    direccionMglActualizada.setGeoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla != null && !nodoZonaCoberturaUltimaMilla.isEmpty() ? nodoZonaCoberturaUltimaMilla : null); 
                    direccionMglActualizada.setGeoZonaCurrier(nodoZonaCurrier != null && !nodoZonaCurrier.isEmpty() ? nodoZonaCurrier : null); 
                    direccionMglActualizada.setGeoZonaGponDiseniado(nodoZonaGponDiseniado != null && !nodoZonaGponDiseniado.isEmpty() ? nodoZonaGponDiseniado : null); 
                    direccionMglActualizada.setGeoZonaMicroOndas(nodoZonaMicroOndas != null && !nodoZonaMicroOndas.isEmpty() ? nodoZonaMicroOndas : null); 
                    direccionMglActualizada.setGeoZonaUnifilar(nodoZonaUnifilar != null && !nodoZonaUnifilar.isEmpty() ? nodoZonaUnifilar : null); 
                                                           
                    boolean actualizacionExitosaDireccion
                            = direccionMglManager.actualizarCoberturasGeoDireccionMgl(direccionMglActualizada);
                    
                    if (direccion.getUbicacion() != null && direccion.getUbicacion().getUbiId() != null) {
                        UbicacionMgl ubicacionBusqueda = new UbicacionMgl();
                        ubicacionBusqueda.setUbiId(direccion.getUbicacion().getUbiId());
                        UbicacionMgl ubicacionEncontrada = ubicacionMglManager.findById(ubicacionBusqueda);

                        if (ubicacionEncontrada != null
                                && hasValidCoordinatesAddressGeoDir.test(addressGeodata)
                                && (hasInvalidCoordinatesLocationUbi.test(ubicacionEncontrada)
                                || hasDifferentCoordinatesGeoDirMgl.test(ubicacionEncontrada, addressGeodata))) {
                            UbicacionMgl ubicacionDireccion = generarObjetoUbicacion(direccion, addressGeodata.getCy(), addressGeodata.getCx());
                            ubicacionMglManager.actualizarCoordenadasGeoDireccionMgl(ubicacionDireccion);
                        }
                    }
                    
                    //Si se actualiza correctamente las coberturas, se consulta de nuevo el objeto con toda la información.               
                    if (actualizacionExitosaDireccion) {
                        direccion = queryAddressOnRepository(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
                    }
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg); 
                }
                //Fin Ajuste Juan David Hernandez */            

                // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
                //los valores de Cx y Cy son cambiados por los consultados en ubicacion por medio de la direccion
                if (direccion.getCx() != null && !direccion.getCx().isEmpty()
                        && direccion.getCy() != null && !direccion.getCy().isEmpty()) {
                    addressService.setCx(direccion.getCx());
                    addressService.setCy(direccion.getCy());
                }
                // </editor-fold>
                //Es una direccion y si existe
                idaddress = "d" + direccion.getDirId().toString();
                revisarEnCampo = direccion.getDirRevisar();
                //En caso de que el estrato este vacio se debe asignar el nivel socioeconomico
                if (direccion.getDirEstrato() != null) {
                    stratum = direccion.getDirEstrato().toString();//else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                } //else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                if (direccion.getDirNivelSocioecono() != null) {
                    leveleconomic = direccion.getDirNivelSocioecono().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                }
                //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                if (direccion.getNodoUno() != null && !direccion.getNodoUno().equals("")) {
                    addressService.setNodoUno(direccion.getNodoUno());
                } else {
                    addressService.setNodoUno(nodoUno);
                }
                if (direccion.getNodoDos() != null && !direccion.getNodoDos().equals("")) {
                    addressService.setNodoDos(direccion.getNodoDos());
                } else {
                    addressService.setNodoDos(nodoDos);
                }
                if (direccion.getNodoTres() != null && !direccion.getNodoTres().equals("")) {
                    addressService.setNodoTres(direccion.getNodoTres());
                } else {
                    addressService.setNodoTres(nodoTres);
                }
                if (direccion.getNodoCuatro() != null && !direccion.getNodoCuatro().equals("")) {
                    addressService.setNodoCuatro(direccion.getNodoCuatro());
                } else {
                    addressService.setNodoCuatro(nodoCuatro);
                }
                //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1 

                if (direccion.getNodoDth() != null && !direccion.getNodoDth().equals("")) {
                    addressService.setNodoDth(direccion.getNodoDth());
                } else {
                    addressService.setNodoDth(nodoDth);
                }
                if (direccion.getNodoMovil() != null
                        && !direccion.getNodoMovil().trim().isEmpty()) {
                    addressService.setNodoMovil(direccion.getNodoMovil());
                } else {
                    addressService.setNodoMovil(nodoMovil);
                }
                if (direccion.getNodoFtth() != null
                        && !direccion.getNodoFtth().trim().isEmpty()) {
                    addressService.setNodoFtth(direccion.getNodoFtth());
                } else {
                    addressService.setNodoFtth(nodoFtth);
                }
                if (direccion.getNodoWifi() != null
                        && !direccion.getNodoWifi().trim().isEmpty()) {
                    addressService.setNodoWifi(direccion.getNodoWifi());
                } else {
                    addressService.setNodoWifi(nodoWifi);
                }
                
                //JDHT
                if (direccion.getGeoZona3G()!= null
                        && !direccion.getGeoZona3G().trim().isEmpty()) {
                    addressService.setNodoZona3G(direccion.getGeoZona3G());
                } else {
                    addressService.setNodoZona3G(nodoZona3G);
                }
                
                if (direccion.getGeoZona4G()!= null
                        && !direccion.getGeoZona4G().trim().isEmpty()) {
                    addressService.setNodoZona4G(direccion.getGeoZona4G());
                } else {
                    addressService.setNodoZona4G(nodoZona4G);
                }
                
                if (direccion.getGeoZona5G()!= null
                        && !direccion.getGeoZona5G().trim().isEmpty()) {
                    addressService.setNodoZona5G(direccion.getGeoZona5G());
                } else {
                    addressService.setNodoZona5G(nodoZona5G);
                }
                
                  if (direccion.getGeoZonaCoberturaCavs()!= null
                        && !direccion.getGeoZonaCoberturaCavs().trim().isEmpty()) {
                    addressService.setNodoZonaCoberturaCavs(direccion.getGeoZonaCoberturaCavs());
                } else {
                    addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                }
                  
                   if (direccion.getGeoZonaCurrier()!= null
                        && !direccion.getGeoZonaCurrier().trim().isEmpty()) {
                    addressService.setNodoZonaCurrier(direccion.getGeoZonaCurrier());
                } else {
                    addressService.setNodoZonaCurrier(nodoZonaCurrier);
                }
                   
                if (direccion.getGeoZonaGponDiseniado()!= null
                        && !direccion.getGeoZonaGponDiseniado().trim().isEmpty()) {
                    addressService.setNodoZonaGponDiseniado(direccion.getGeoZonaGponDiseniado());
                } else {
                    addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                }
                
                if (direccion.getGeoZonaMicroOndas()!= null
                        && !direccion.getGeoZonaMicroOndas().trim().isEmpty()) {
                    addressService.setNodoZonaMicroOndas(direccion.getGeoZonaMicroOndas());
                } else {
                    addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                }
                
                 if (direccion.getGeoZonaUnifilar()!= null
                        && !direccion.getGeoZonaUnifilar().trim().isEmpty()) {
                    addressService.setNodoZonaUnifilar(direccion.getGeoZonaUnifilar());
                } else {
                    addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                }
                
            } else {
                //Se consulta como una subDireccion
                //SubDireccion subDireccion = querySubAddressOnRepoByCod(codAddres, centroPobladoDireccion.getGpoId());
                if (subDireccion == null) {
                    idaddress = "0";
                    addressService.setRecommendations("La direccion no existe en el repositorio.");
                    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                    addressService.setNodoUno(nodoUno);
                    addressService.setNodoDos(nodoDos);
                    addressService.setNodoTres(nodoTres);
                    addressService.setNodoCuatro(nodoCuatro);
                    //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1

                    addressService.setNodoDth(nodoDth);
                    addressService.setNodoMovil(nodoMovil);
                    addressService.setNodoFtth(nodoFtth);
                    addressService.setNodoWifi(nodoWifi);
                    
                    addressService.setNodoZona3G(nodoZona3G);
                    addressService.setNodoZona4G(nodoZona4G);
                    addressService.setNodoZona5G(nodoZona5G);
                    addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                    addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                    addressService.setNodoZonaCurrier(nodoZonaCurrier);
                    addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                    addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                    addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                    
                } else {
                    /*author Juan David Hernandez*/
                    //se comenta debido a que se realizó la funcionalidad pero ya no va agosto 17 2018
                    /*Actualización de coberturas y geoData por consulta*/
                    try {
                        SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
                        SubDireccionMgl subDireccionMglActualizada = new SubDireccionMgl();
                        if (leveleconomic != null && !leveleconomic.isEmpty()) {
                            subDireccionMglActualizada.setSdiNivelSocioecono(new BigDecimal(leveleconomic));
                        } else {
                            subDireccionMglActualizada.setSdiNivelSocioecono(new BigDecimal(-1));
                        }
                        subDireccionMglActualizada.setSdiId(subDireccion.getSdiId());
                        
                        subDireccionMglActualizada.setSdiNodouno(nodoUno != null && !nodoUno.isEmpty() ? nodoUno : null);
                        subDireccionMglActualizada.setSdiNododos(nodoDos != null && !nodoDos.isEmpty() ? nodoDos : null);
                        subDireccionMglActualizada.setSdiNodotres(nodoTres != null && !nodoTres.isEmpty() ? nodoTres : null);
                        subDireccionMglActualizada.setSdiNodoFttx(nodoCuatro != null && !nodoCuatro.isEmpty() ? nodoCuatro : null);
                        subDireccionMglActualizada.setSdiNodoDth(nodoDth != null && !nodoDth.isEmpty() ? nodoDth : null);
                        subDireccionMglActualizada.setSdiNodoMovil(nodoMovil != null && !nodoMovil.isEmpty() ? nodoMovil : null);
                        subDireccionMglActualizada.setSdiNodoFtth(nodoFtth != null && !nodoFtth.isEmpty() ? nodoFtth : null);
                        subDireccionMglActualizada.setSdiNodoWifi(nodoWifi != null && !nodoWifi.isEmpty() ? nodoWifi : null);
                        subDireccionMglActualizada.setGeoZona3G(nodoZona3G != null && !nodoZona3G.isEmpty() ? nodoZona3G : null);
                        subDireccionMglActualizada.setGeoZona4G(nodoZona4G != null && !nodoZona4G.isEmpty() ? nodoZona4G : null);
                        subDireccionMglActualizada.setGeoZona5G(nodoZona5G != null && !nodoZona5G.isEmpty() ? nodoZona5G : null);
                        subDireccionMglActualizada.setGeoZonaCoberturaCavs(nodoZonaCoberturaCavs != null && !nodoZonaCoberturaCavs.isEmpty() ? nodoZonaCoberturaCavs : null);
                        subDireccionMglActualizada.setGeoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla != null && !nodoZonaCoberturaUltimaMilla.isEmpty() ? nodoZonaCoberturaUltimaMilla : null);
                        subDireccionMglActualizada.setGeoZonaCurrier(nodoZonaCurrier != null && !nodoZonaCurrier.isEmpty() ? nodoZonaCurrier : null);
                        subDireccionMglActualizada.setGeoZonaGponDiseniado(nodoZonaGponDiseniado != null && !nodoZonaGponDiseniado.isEmpty() ? nodoZonaGponDiseniado : null);
                        subDireccionMglActualizada.setGeoZonaMicroOndas(nodoZonaMicroOndas != null && !nodoZonaMicroOndas.isEmpty() ? nodoZonaMicroOndas : null);
                        subDireccionMglActualizada.setGeoZonaUnifilar(nodoZonaUnifilar != null && !nodoZonaUnifilar.isEmpty() ? nodoZonaUnifilar : null);
                        
                        boolean actualizacionExitosa
                                = subDireccionMglManager.actualizarCoberturasGeoSubDireccionMgl(subDireccionMglActualizada);
                                              //Si se actualiza correctamente las coberturas, se consulta de nuevo el objeto con toda la información.
                        if (actualizacionExitosa) {
                            subDireccion = querySubAddressOnRepoByCod(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
                        }
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                        LOGGER.error(msg);
                    }
                    /*Fin Ajuste Juan David Hernandez */

                    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
                    //los valores de Cx y Cy son cambiados por los consultados en ubicacion por medio de la direccion
                    if (subDireccion.getCx() != null && !subDireccion.getCx().isEmpty()
                            && subDireccion.getCy() != null && !subDireccion.getCy().isEmpty()) {
                        addressService.setCx(subDireccion.getCx());
                        addressService.setCy(subDireccion.getCy());
                    }
                    // </editor-fold>
                    idaddress = "s" + subDireccion.getSdiId().toString();
                    revisarEnCampo = "0";
                    //En caso de que el estrato este vacio se debe asignar el nivel socioeconomico
                    if (subDireccion.getSdiEstrato() != null) {
                        stratum = subDireccion.getSdiEstrato().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    } //else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    if (subDireccion.getSdiNivelSocioecono() != null) {
                        leveleconomic = subDireccion.getSdiNivelSocioecono().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    }
                    if (subDireccion.getSdiActividadEcono() != null) {
                        activityeconomicOnRepo = subDireccion.getSdiActividadEcono().toString();
                    }
                    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1        

                    if (subDireccion.getNodoUno() != null && !subDireccion.getNodoUno().equals("")) {
                        addressService.setNodoUno(subDireccion.getNodoUno());
                    } else {
                        addressService.setNodoUno(nodoUno);
                    }
                    if (subDireccion.getNodoDos() != null && !subDireccion.getNodoDos().equals("")) {
                        addressService.setNodoDos(subDireccion.getNodoDos());
                    } else {
                        addressService.setNodoDos(nodoDos);
                    }
                    if (subDireccion.getNodoTres() != null && !subDireccion.getNodoTres().equals("")) {
                        addressService.setNodoTres(subDireccion.getNodoTres());
                    } else {
                        addressService.setNodoTres(nodoTres);
                    }
                    //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1   

                    if (subDireccion.getNodoDth() != null && !subDireccion.getNodoDth().equals("")) {
                        addressService.setNodoDth(subDireccion.getNodoDth());
                    } else {
                        addressService.setNodoDth(nodoDth);
                    }
                    if (subDireccion.getNodoMovil() != null
                            && !subDireccion.getNodoMovil().trim().isEmpty()) {
                        addressService.setNodoMovil(subDireccion.getNodoMovil());
                    } else {
                        addressService.setNodoMovil(nodoMovil);
                    }
                    if (subDireccion.getNodoFtth() != null
                            && !subDireccion.getNodoFtth().trim().isEmpty()) {
                        addressService.setNodoFtth(subDireccion.getNodoFtth());
                    } else {
                        addressService.setNodoFtth(nodoFtth);
                    }

                    if (subDireccion.getNodoFttx() != null
                            && !subDireccion.getNodoFttx().trim().isEmpty()) {
                        addressService.setNodoCuatro(subDireccion.getNodoFttx());
                    } else {
                        addressService.setNodoCuatro(nodoCuatro);
                    }
                    if (subDireccion.getNodoWifi() != null
                            && !subDireccion.getNodoWifi().trim().isEmpty()) {
                        addressService.setNodoWifi(subDireccion.getNodoWifi());
                    } else {
                        addressService.setNodoWifi(nodoWifi);
                    }
                    //JDHT
                     if (subDireccion.getGeoZona3G()!= null
                            && !subDireccion.getGeoZona3G().trim().isEmpty()) {
                        addressService.setNodoZona3G(subDireccion.getGeoZona3G());
                    } else {
                        addressService.setNodoZona3G(nodoZona3G);
                    }
                     
                     if (subDireccion.getGeoZona4G()!= null
                            && !subDireccion.getGeoZona4G().trim().isEmpty()) {
                        addressService.setNodoZona4G(subDireccion.getGeoZona4G());
                    } else {
                        addressService.setNodoZona4G(nodoZona4G);
                    }
                     
                     if (subDireccion.getGeoZona5G()!= null
                            && !subDireccion.getGeoZona5G().trim().isEmpty()) {
                        addressService.setNodoZona5G(subDireccion.getGeoZona5G());
                    } else {
                        addressService.setNodoZona5G(nodoZona5G);
                    }
                     
                     if (subDireccion.getGeoZonaCoberturaCavs()!= null
                            && !subDireccion.getGeoZonaCoberturaCavs().trim().isEmpty()) {
                        addressService.setNodoZonaCoberturaCavs(subDireccion.getGeoZonaCoberturaCavs());
                    } else {
                        addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                    }
                     
                      if (subDireccion.getGeoZonaCoberturaUltimaMilla()!= null
                            && !subDireccion.getGeoZonaCoberturaUltimaMilla().trim().isEmpty()) {
                        addressService.setNodoZonaCoberturaUltimaMilla(subDireccion.getGeoZonaCoberturaUltimaMilla());
                    } else {
                        addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                    }                      
                      
                    if (subDireccion.getGeoZonaCurrier()!= null
                            && !subDireccion.getGeoZonaCurrier().trim().isEmpty()) {
                        addressService.setNodoZonaCurrier(subDireccion.getGeoZonaCurrier());
                    } else {
                        addressService.setNodoZonaCurrier(nodoZonaCurrier);
                    }
                    
                     if (subDireccion.getGeoZonaGponDiseniado()!= null
                            && !subDireccion.getGeoZonaGponDiseniado().trim().isEmpty()) {
                        addressService.setNodoZonaGponDiseniado(subDireccion.getGeoZonaGponDiseniado());
                    } else {
                        addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                    }
                     
                     if (subDireccion.getGeoZonaMicroOndas()!= null
                            && !subDireccion.getGeoZonaMicroOndas().trim().isEmpty()) {
                        addressService.setNodoZonaMicroOndas(subDireccion.getGeoZonaMicroOndas());
                    } else {
                        addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                    }
                     
                      if (subDireccion.getGeoZonaUnifilar()!= null
                            && !subDireccion.getGeoZonaUnifilar().trim().isEmpty()) {
                        addressService.setNodoZonaUnifilar(subDireccion.getGeoZonaUnifilar());
                    } else {
                        addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                    }
  
                }
            }
        }
        recommendations = evaluateRecommendations(address, idaddress, chagenumber, ambigua, alternateaddress, revisarEnCampo);

        category = queryCategoryAddress(address);

        appletstandar = addressGeodata.getManzana();

        exist = validExistAddress(addressGeodata.getDirtrad(), state, qualifiers, codAddresPlaca, centroPobladoDireccion.getGpoId());
        traslate = validTraslateAdrress(address, state);

        addressService.setIdaddress(idaddress);
        if (NIVEL_DETALLE_COMPLETO.equalsIgnoreCase(addressRequest.getLevel())) {
            //Estas se retornan sólo para consulta completa
            addressService.setRecommendations(recommendations);
        } else {

            addressService.setRecommendations(null);
        }

        addressService.setAddress(address);
        /*Se evalua si el campo neighborhood es nulo o vacio, en caso de serlo debe consultar el barrio en el método enrich assist*/
        if(neighborhood!= null && !neighborhood.trim().isEmpty()){
            addressService.setNeighborhood(neighborhood);
        }else{
           try {
                addressSuggested = callServiceAddressSuggested(addressRequest);
           }catch (RemoteException | ServiceException ex) {
                addressSuggested = null;
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            }
           if(addressSuggested.size()>0){
               addressService.setAddressSuggested(addressSuggested);
               addressService.setNeighborhood(addressSuggested.get(0).getNeighborhood());
           }
           else{
               addressService.setNeighborhood(neighborhood);
           }
            
        }
        addressService.setCategory(category);
        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
        addressService.setZipCode(codZip);
        addressService.setCoddanemcpio(codDivipola);
        addressService.setComentEconomicLevel(comSocioEconomico);
        //Fin Modificacion Carlos Villamil Direcciones Fase I 20121218

        //Se valida si existe el levelEconomic en el repositorio para determinar cual se debe devolver en el WS
        addressService.setLeveleconomic(leveleconomic);// Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
        addressService.setEstratoDef(stratum);// Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
        
        addressService.setAppletstandar(appletstandar);
        addressService.setChagenumber(chagenumber);
        addressService.setAlternateaddress(alternateaddress);
        addressService.setLevellive(levellive);
        addressService.setQualifiers(qualifiers);
        addressService.setRespuestaGeo(addressGeodata);
        if (!activityeconomicOnRepo.isEmpty()) {
            addressService.setActivityeconomic(activityeconomicOnRepo);
        } else {
            addressService.setActivityeconomic(activityeconomic);
        }

        addressService.setExist(String.valueOf(exist));
        addressService.setTraslate(String.valueOf(traslate));

        if (direccionesAgregadas != null) {
            addressService.setAddressAggregated(direccionesAgregadas);
        }
        if (confiabilidadDir != null
                && !confiabilidadDir.trim().isEmpty()) {
            addressService.setReliability(confiabilidadDir);
        }
         if (confiabilidadPlaca != null
                && !confiabilidadPlaca.trim().isEmpty()) {
            addressService.setReliabilityPlaca(confiabilidadPlaca);
        }

        return addressService;
    }

    private UbicacionMgl generarObjetoUbicacion(Direccion intraducible, String coordenadaX, String coordenadaY) {
        UbicacionMgl ubicacionDireccion = new UbicacionMgl();
        ubicacionDireccion.setUbiId(intraducible.getUbicacion().getUbiId());
        ubicacionDireccion.setUbiLatitud(coordenadaX);
        ubicacionDireccion.setUbiLongitud(coordenadaY);
        ubicacionDireccion.setUbiLatitudNum(new BigDecimal(coordenadaX));
        ubicacionDireccion.setUbiLongitudNum(new BigDecimal(coordenadaY));
        return ubicacionDireccion;
    }

    //*Metodo lite*//
    /**
     *
     * @param addressGeodata
     * @param addressRequest
     * @return
     * @throws ApplicationException
     */
    public AddressServiceBatchXml createSimpleAddressService(AddressGeoDirDataDto addressGeodata, AddressRequest addressRequest) throws ApplicationException {
    
        AddressServiceBatchXml addressService = new AddressServiceBatchXml();
        List<AddressSuggested> addressSuggested;
        
        String idaddress;
        String category;
        String appletstandar;
        String recommendations;
        String address = addressGeodata.getDirtrad();
        String neighborhood = addressGeodata.getBarrio();
        String stratum = addressGeodata.getEstrato();
        //TODO: Falta por definirse por Servinformacion
        String leveleconomic = addressGeodata.getNivsocio();
        String chagenumber = addressGeodata.getFuente();
        String alternateaddress = addressGeodata.getDiralterna();
        String levellive = addressGeodata.getValplaca();
        String qualifiers = addressGeodata.getValplaca();
        String activityeconomic = addressGeodata.getActeconomica();
        String activityeconomicOnRepo = "";
        String state = addressGeodata.getEstado();
        //Codigo de la direccion hasta la placa estandarizada por el WS
        String codAddres = addressGeodata.getCoddir();
        String codAddresPlaca = addressGeodata.getCodencont();
        String ambigua = addressGeodata.getAmbigua();
        String revisarEnCampo = "";
        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
        String codZip = addressGeodata.getZipCode();
        String codDivipola = addressGeodata.getCoddanemcpio();
        String comSocioEconomico = addressGeodata.getComentEconomicLevel();
        //Fin Modificacion Carlos Villamil Direcciones Fase I 20121218

        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1
        String nodoUno = addressGeodata.getNodo1();
        String nodoDos = addressGeodata.getNodo2();
        String nodoTres = addressGeodata.getNodo3();
        String nodoCuatro = addressGeodata.getNodo4();

        String nodoDth = addressGeodata.getNodoDth();
        String nodoMovil = addressGeodata.getNodoMovil();
        String nodoFtth = addressGeodata.getNodoFtth();
        String nodoWifi = addressGeodata.getNodoWifi();
        //JDHT
        String nodoZonaUnifilar = addressGeodata.getGeoZonaUnifilar();
        String nodoZona3G = addressGeodata.getGeoZona3G();
        String nodoZona4G = addressGeodata.getGeoZona4G();
        String nodoZona5G = addressGeodata.getGeoZona5G();
        String nodoZonaCoberturaCavs = addressGeodata.getGeoZonaCoberturaCavs();
        String nodoZonaCoberturaUltimaMilla = addressGeodata.getGeoZonaCoberturaUltimaMilla();
        String nodoZonaGponDiseniado = addressGeodata.getGeoZonaGponDiseniado();
        String nodoZonaMicroOndas = addressGeodata.getGeoZonaMicroOndas();
        String nodoZonaCurrier = addressGeodata.getGeoZonaCurrier();
        
        //valor de la placa de la direccion (campo 7)
        String confiabilidadDir = addressGeodata.getValagreg();
        //valor de la direccion (campo 8)
        String confiabilidadPlaca = addressGeodata.getValplaca();
        
        boolean exist;
        boolean traslate;
        List<AddressAggregated> direccionesAgregadas = null;
        //Se extrae el complemento de la direccion
        String complemento = codAddres.substring(codAddresPlaca.length());
        //Si el Nivel de detalle es Completo "C", se debe realizar la consulta en el repositorio
        DireccionMglManager direccionMglManager = new DireccionMglManager();
        UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();


        addressService.setAmbigua(ambigua);
        addressService.setSource(addressGeodata.getFuente());
        addressService.setBarrioTraducido(addressGeodata.getBartrad());
        addressService.setAddressCode(addressGeodata.getCoddir());
        addressService.setAddressCodeFound(addressGeodata.getCodencont());
        addressService.setLocality(addressGeodata.getLocalidad());
        addressService.setCx(addressGeodata.getCx());
        addressService.setCy(addressGeodata.getCy());
        addressService.setState(addressGeodata.getEstado());
        
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();       
        GeograficoPoliticoMgl centroPobladoDireccion 
               = geograficoPoliticoManager.findCentroPobladoCodDane(addressRequest.getCodDaneVt());
         
         if(centroPobladoDireccion == null){
              throw new ApplicationException("Error consultando el centro poblado por codigo dane. No Encontrada.");
             
         }
        
        /*Si la direccion es multiorign es Calle-Carrera 
         tiene seteado el tipo de direccion (desde Factibilidad, HHPP, CCMM) */
        if(centroPobladoDireccion.getGpoMultiorigen().equalsIgnoreCase("1")
                && addressRequest.getTipoDireccion() != null
                && !addressRequest.getTipoDireccion().isEmpty()
                && addressRequest.getTipoDireccion().equalsIgnoreCase("CK")
                && addressRequest.getNeighborhood() != null 
                && !addressRequest.getNeighborhood().isEmpty()){
            
            if(addressGeodata.getDirtrad() != null 
                    && !addressGeodata.getDirtrad().isEmpty()
                    && addressGeodata.getDirtrad().toUpperCase().contains(addressRequest.getNeighborhood().toUpperCase())){
         
            }else{
                addressGeodata.setDirtrad(addressGeodata.getDirtrad()+ " " + addressRequest.getNeighborhood().toUpperCase());
            }
        }
        
        
        //Se consulta la direccion en el repositorio por el codDireccion retornado por el WS
        /*JDHT se realiza cambio de valor de busqueda debido a que no todas las direcciones tiene codigo de serviinformacion,
        se realiza busque por formato igac*/
        Direccion direccion = queryAddressOnRepository(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
        SubDireccion subDireccion = querySubAddressOnRepoByCod(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
        
        if (direccion == null && subDireccion == null) {
            Direccion intraducible
                    = queryAddressIntraducibleOnRepoByIgac(addressRequest.getAddress(), addressRequest.getCodDaneVt());

            if (intraducible == null) {
                idaddress = "0";
                addressService.setRecommendations("La direccion no existe en el repositorio.");
                //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                addressService.setNodoUno(nodoUno);
                addressService.setNodoDos(nodoDos);
                addressService.setNodoTres(nodoTres);
                //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                
                addressService.setNodoDth(nodoDth);
                addressService.setNodoMovil(nodoMovil);
                addressService.setNodoFtth(nodoFtth);
                addressService.setNodoCuatro(nodoCuatro);
                addressService.setNodoWifi(nodoWifi);
                
                addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                addressService.setNodoZona3G(nodoZona3G);
                addressService.setNodoZona4G(nodoZona4G);
                addressService.setNodoZona5G(nodoZona5G);
                addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                addressService.setNodoZonaCurrier(nodoZonaCurrier);
                addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
 
                
            } else {
                
                // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
                //los valores de Cx y Cy son cambiados por los consultados en ubicacion por medio de la direccion
                if (intraducible.getCx() != null && !intraducible.getCx().isEmpty()
                        && intraducible.getCy() != null && !intraducible.getCy().isEmpty()) {
                    addressService.setCx(intraducible.getCx());
                    addressService.setCy(intraducible.getCy());
                }
                // </editor-fold>
                //Si existe la direccion
                idaddress = "d" + intraducible.getDirId().toString();
                revisarEnCampo = intraducible.getDirRevisar();
                if ("1".equals(intraducible.getDirRevisar())) {
                    addressService.setRecommendations("La direccion existe en el repositorio. La dirección se debe validar en terreno.");
                } else {
                    addressService.setRecommendations("La direccion existe en el repositorio.");
                }
            }
        } else {
            //Si existe la direccion          
            //Se verifica si la direccion es una subdireccion
            if (direccion != null) {
                /*author Juan David Hernandez*/
                //Es una direccion y si existe
                idaddress = "d" + direccion.getDirId().toString();
                revisarEnCampo = direccion.getDirRevisar();
                //En caso de que el estrato este vacio se debe asignar el nivel socioeconomico
                if (direccion.getDirEstrato() != null) {
                    stratum = direccion.getDirEstrato().toString();//else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                } //else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                if (direccion.getDirNivelSocioecono() != null) {
                    leveleconomic = direccion.getDirNivelSocioecono().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                }
                //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                if (direccion.getNodoUno() != null && !direccion.getNodoUno().equals("")) {
                    addressService.setNodoUno(direccion.getNodoUno());
                } else {
                    addressService.setNodoUno(nodoUno);
                }
                if (direccion.getNodoDos() != null && !direccion.getNodoDos().equals("")) {
                    addressService.setNodoDos(direccion.getNodoDos());
                } else {
                    addressService.setNodoDos(nodoDos);
                }
                if (direccion.getNodoTres() != null && !direccion.getNodoTres().equals("")) {
                    addressService.setNodoTres(direccion.getNodoTres());
                } else {
                    addressService.setNodoTres(nodoTres);
                }
                if (direccion.getNodoCuatro() != null && !direccion.getNodoCuatro().equals("")) {
                    addressService.setNodoCuatro(direccion.getNodoCuatro());
                } else {
                    addressService.setNodoCuatro(nodoCuatro);
                }
                //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1 

                if (direccion.getNodoDth() != null && !direccion.getNodoDth().equals("")) {
                    addressService.setNodoDth(direccion.getNodoDth());
                } else {
                    addressService.setNodoDth(nodoDth);
                }
                if (direccion.getNodoMovil() != null
                        && !direccion.getNodoMovil().trim().isEmpty()) {
                    addressService.setNodoMovil(direccion.getNodoMovil());
                } else {
                    addressService.setNodoMovil(nodoMovil);
                }
                if (direccion.getNodoFtth() != null
                        && !direccion.getNodoFtth().trim().isEmpty()) {
                    addressService.setNodoFtth(direccion.getNodoFtth());
                } else {
                    addressService.setNodoFtth(nodoFtth);
                }
                if (direccion.getNodoWifi() != null
                        && !direccion.getNodoWifi().trim().isEmpty()) {
                    addressService.setNodoWifi(direccion.getNodoWifi());
                } else {
                    addressService.setNodoWifi(nodoWifi);
                }
                
                //JDHT
                if (direccion.getGeoZona3G()!= null
                        && !direccion.getGeoZona3G().trim().isEmpty()) {
                    addressService.setNodoZona3G(direccion.getGeoZona3G());
                } else {
                    addressService.setNodoZona3G(nodoZona3G);
                }
                
                if (direccion.getGeoZona4G()!= null
                        && !direccion.getGeoZona4G().trim().isEmpty()) {
                    addressService.setNodoZona4G(direccion.getGeoZona4G());
                } else {
                    addressService.setNodoZona4G(nodoZona4G);
                }
                
                if (direccion.getGeoZona5G()!= null
                        && !direccion.getGeoZona5G().trim().isEmpty()) {
                    addressService.setNodoZona5G(direccion.getGeoZona5G());
                } else {
                    addressService.setNodoZona5G(nodoZona5G);
                }
                
                  if (direccion.getGeoZonaCoberturaCavs()!= null
                        && !direccion.getGeoZonaCoberturaCavs().trim().isEmpty()) {
                    addressService.setNodoZonaCoberturaCavs(direccion.getGeoZonaCoberturaCavs());
                } else {
                    addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                }
                  
                   if (direccion.getGeoZonaCurrier()!= null
                        && !direccion.getGeoZonaCurrier().trim().isEmpty()) {
                    addressService.setNodoZonaCurrier(direccion.getGeoZonaCurrier());
                } else {
                    addressService.setNodoZonaCurrier(nodoZonaCurrier);
                }
                   
                if (direccion.getGeoZonaGponDiseniado()!= null
                        && !direccion.getGeoZonaGponDiseniado().trim().isEmpty()) {
                    addressService.setNodoZonaGponDiseniado(direccion.getGeoZonaGponDiseniado());
                } else {
                    addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                }
                
                if (direccion.getGeoZonaMicroOndas()!= null
                        && !direccion.getGeoZonaMicroOndas().trim().isEmpty()) {
                    addressService.setNodoZonaMicroOndas(direccion.getGeoZonaMicroOndas());
                } else {
                    addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                }
                
                 if (direccion.getGeoZonaUnifilar()!= null
                        && !direccion.getGeoZonaUnifilar().trim().isEmpty()) {
                    addressService.setNodoZonaUnifilar(direccion.getGeoZonaUnifilar());
                } else {
                    addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                }
                
            } else {
                //Se consulta como una subDireccion
                //SubDireccion subDireccion = querySubAddressOnRepoByCod(codAddres, centroPobladoDireccion.getGpoId());
                if (subDireccion == null) {
                    idaddress = "0";
                    addressService.setRecommendations("La direccion no existe en el repositorio.");
                    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                    addressService.setNodoUno(nodoUno);
                    addressService.setNodoDos(nodoDos);
                    addressService.setNodoTres(nodoTres);
                    addressService.setNodoCuatro(nodoCuatro);
                    //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1

                    addressService.setNodoDth(nodoDth);
                    addressService.setNodoMovil(nodoMovil);
                    addressService.setNodoFtth(nodoFtth);
                    addressService.setNodoWifi(nodoWifi);
                    
                    addressService.setNodoZona3G(nodoZona3G);
                    addressService.setNodoZona4G(nodoZona4G);
                    addressService.setNodoZona5G(nodoZona5G);
                    addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                    addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                    addressService.setNodoZonaCurrier(nodoZonaCurrier);
                    addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                    addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                    addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                    
                } else {
                    
                    idaddress = "s" + subDireccion.getSdiId().toString();
                    revisarEnCampo = "0";
                    //En caso de que el estrato este vacio se debe asignar el nivel socioeconomico
                    if (subDireccion.getSdiEstrato() != null) {
                        stratum = subDireccion.getSdiEstrato().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    } //else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    if (subDireccion.getSdiNivelSocioecono() != null) {
                        leveleconomic = subDireccion.getSdiNivelSocioecono().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    }
                    if (subDireccion.getSdiActividadEcono() != null) {
                        activityeconomicOnRepo = subDireccion.getSdiActividadEcono().toString();
                    }
                    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1        

                    if (subDireccion.getNodoUno() != null && !subDireccion.getNodoUno().equals("")) {
                        addressService.setNodoUno(subDireccion.getNodoUno());
                    } else {
                        addressService.setNodoUno(nodoUno);
                    }
                    if (subDireccion.getNodoDos() != null && !subDireccion.getNodoDos().equals("")) {
                        addressService.setNodoDos(subDireccion.getNodoDos());
                    } else {
                        addressService.setNodoDos(nodoDos);
                    }
                    if (subDireccion.getNodoTres() != null && !subDireccion.getNodoTres().equals("")) {
                        addressService.setNodoTres(subDireccion.getNodoTres());
                    } else {
                        addressService.setNodoTres(nodoTres);
                    }
                    //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1   

                    if (subDireccion.getNodoDth() != null && !subDireccion.getNodoDth().equals("")) {
                        addressService.setNodoDth(subDireccion.getNodoDth());
                    } else {
                        addressService.setNodoDth(nodoDth);
                    }
                    if (subDireccion.getNodoMovil() != null
                            && !subDireccion.getNodoMovil().trim().isEmpty()) {
                        addressService.setNodoMovil(subDireccion.getNodoMovil());
                    } else {
                        addressService.setNodoMovil(nodoMovil);
                    }
                    if (subDireccion.getNodoFtth() != null
                            && !subDireccion.getNodoFtth().trim().isEmpty()) {
                        addressService.setNodoFtth(subDireccion.getNodoFtth());
                    } else {
                        addressService.setNodoFtth(nodoFtth);
                    }

                    if (subDireccion.getNodoFttx() != null
                            && !subDireccion.getNodoFttx().trim().isEmpty()) {
                        addressService.setNodoCuatro(subDireccion.getNodoFttx());
                    } else {
                        addressService.setNodoCuatro(nodoCuatro);
                    }
                    if (subDireccion.getNodoWifi() != null
                            && !subDireccion.getNodoWifi().trim().isEmpty()) {
                        addressService.setNodoWifi(subDireccion.getNodoWifi());
                    } else {
                        addressService.setNodoWifi(nodoWifi);
                    }
                    //JDHT
                     if (subDireccion.getGeoZona3G()!= null
                            && !subDireccion.getGeoZona3G().trim().isEmpty()) {
                        addressService.setNodoZona3G(subDireccion.getGeoZona3G());
                    } else {
                        addressService.setNodoZona3G(nodoZona3G);
                    }
                     
                     if (subDireccion.getGeoZona4G()!= null
                            && !subDireccion.getGeoZona4G().trim().isEmpty()) {
                        addressService.setNodoZona4G(subDireccion.getGeoZona4G());
                    } else {
                        addressService.setNodoZona4G(nodoZona4G);
                    }
                     
                     if (subDireccion.getGeoZona5G()!= null
                            && !subDireccion.getGeoZona5G().trim().isEmpty()) {
                        addressService.setNodoZona5G(subDireccion.getGeoZona5G());
                    } else {
                        addressService.setNodoZona5G(nodoZona5G);
                    }
                     
                     if (subDireccion.getGeoZonaCoberturaCavs()!= null
                            && !subDireccion.getGeoZonaCoberturaCavs().trim().isEmpty()) {
                        addressService.setNodoZonaCoberturaCavs(subDireccion.getGeoZonaCoberturaCavs());
                    } else {
                        addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                    }
                     
                      if (subDireccion.getGeoZonaCoberturaUltimaMilla()!= null
                            && !subDireccion.getGeoZonaCoberturaUltimaMilla().trim().isEmpty()) {
                        addressService.setNodoZonaCoberturaUltimaMilla(subDireccion.getGeoZonaCoberturaUltimaMilla());
                    } else {
                        addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                    }                      
                      
                    if (subDireccion.getGeoZonaCurrier()!= null
                            && !subDireccion.getGeoZonaCurrier().trim().isEmpty()) {
                        addressService.setNodoZonaCurrier(subDireccion.getGeoZonaCurrier());
                    } else {
                        addressService.setNodoZonaCurrier(nodoZonaCurrier);
                    }
                    
                     if (subDireccion.getGeoZonaGponDiseniado()!= null
                            && !subDireccion.getGeoZonaGponDiseniado().trim().isEmpty()) {
                        addressService.setNodoZonaGponDiseniado(subDireccion.getGeoZonaGponDiseniado());
                    } else {
                        addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                    }
                     
                     if (subDireccion.getGeoZonaMicroOndas()!= null
                            && !subDireccion.getGeoZonaMicroOndas().trim().isEmpty()) {
                        addressService.setNodoZonaMicroOndas(subDireccion.getGeoZonaMicroOndas());
                    } else {
                        addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                    }
                     
                      if (subDireccion.getGeoZonaUnifilar()!= null
                            && !subDireccion.getGeoZonaUnifilar().trim().isEmpty()) {
                        addressService.setNodoZonaUnifilar(subDireccion.getGeoZonaUnifilar());
                    } else {
                        addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                    }
  
                }
            }
        }
        recommendations = evaluateRecommendations(address, idaddress, chagenumber, ambigua, alternateaddress, revisarEnCampo);

        category = queryCategoryAddress(address);

        appletstandar = addressGeodata.getManzana();

        exist = validExistAddress(addressGeodata.getDirtrad(), state, qualifiers, codAddresPlaca, centroPobladoDireccion.getGpoId());
        traslate = validTraslateAdrress(address, state);

        addressService.setIdaddress(idaddress);
        if (NIVEL_DETALLE_COMPLETO.equalsIgnoreCase(addressRequest.getLevel())) {
            //Estas se retornan sólo para consulta completa
            addressService.setRecommendations(recommendations);
        } else {

            addressService.setRecommendations(null);
        }

        addressService.setAddress(address);
        /*Se evalua si el campo neighborhood es nulo o vacio, en caso de serlo debe consultar el barrio en el método enrich assist*/
        if(neighborhood!= null && !neighborhood.trim().isEmpty()){
            addressService.setNeighborhood(neighborhood);
        }else{
           try {
                addressSuggested = callServiceAddressSuggested(addressRequest);
           }catch (RemoteException | ServiceException ex) {
                addressSuggested = null;
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            }
            if (addressSuggested != null) {
                if (!addressSuggested.isEmpty()) {
                    addressService.setAddressSuggested(addressSuggested);
                    addressService.setNeighborhood(addressSuggested.get(0).getNeighborhood());
                }
            }
           else{
               addressService.setNeighborhood(neighborhood);
           }
            
        }
        addressService.setCategory(category);
        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
        addressService.setZipCode(codZip);
        addressService.setCoddanemcpio(codDivipola);
        addressService.setComentEconomicLevel(comSocioEconomico);
        //Fin Modificacion Carlos Villamil Direcciones Fase I 20121218

        //Se valida si existe el levelEconomic en el repositorio para determinar cual se debe devolver en el WS
        addressService.setLeveleconomic(leveleconomic);// Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
        addressService.setEstratoDef(stratum);// Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
        
        addressService.setAppletstandar(appletstandar);
        addressService.setChagenumber(chagenumber);
        addressService.setAlternateaddress(alternateaddress);
        addressService.setLevellive(levellive);
        addressService.setQualifiers(qualifiers);
        addressService.setRespuestaGeo(addressGeodata);
        if (!activityeconomicOnRepo.isEmpty()) {
            addressService.setActivityeconomic(activityeconomicOnRepo);
        } else {
            addressService.setActivityeconomic(activityeconomic);
        }

        addressService.setExist(String.valueOf(exist));
        addressService.setTraslate(String.valueOf(traslate));

        if (direccionesAgregadas != null) {
            addressService.setAddressAggregated(direccionesAgregadas);
        }
        if (confiabilidadDir != null
                && !confiabilidadDir.trim().isEmpty()) {
            addressService.setReliability(confiabilidadDir);
        }
         if (confiabilidadPlaca != null
                && !confiabilidadPlaca.trim().isEmpty()) {
            addressService.setReliabilityPlaca(confiabilidadPlaca);
        }

        return addressService;
    }
    //*Metodo lite*//
    
    /**
     *
     * @param addressGeodata
     * @param nivelDetalle
     * @param direccionconsulta
     * @return
     * @throws Exception
     */
    private AddressService createAddressService(AddressGeodata addressGeodata, AddressRequest addressReques) throws ApplicationException {
        AddressService addressService = new AddressService();

        String idaddress;
        String category;
        String appletstandar;
        String recommendations;
        String address = addressGeodata.getDirtrad();
        String neighborhood = addressGeodata.getBarrio();
        String stratum = addressGeodata.getEstrato();
        //TODO: Falta por definirse por Servinformacion
        String leveleconomic = addressGeodata.getNivsocio();
        String chagenumber = addressGeodata.getFuente();
        String alternateaddress = addressGeodata.getDiralterna();
        String levellive = addressGeodata.getValplaca();
        String qualifiers = addressGeodata.getValplaca();
        String activityeconomic = addressGeodata.getActeconomica();
        String activityeconomicOnRepo = "";
        String state = addressGeodata.getEstado();
        //Codigo de la direccion hasta la placa estandarizada por el WS
        String codAddres = addressGeodata.getCoddir();
        LOGGER.debug("CODIGO ADDRESS: {} ", codAddres);
        String codAddresPlaca = addressGeodata.getCodencont();
        String ambigua = addressGeodata.getAmbigua();
        String revisarEnCampo = "";
        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
        String codZip = addressGeodata.getZipCode();
        String codDivipola = addressGeodata.getCoddanemcpio();
        String comSocioEconomico = addressGeodata.getComentEconomicLevel();
        //Fin Modificacion Carlos Villamil Direcciones Fase I 20121218

        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1
        String nodoUno = addressGeodata.getNodo1();
        String nodoDos = addressGeodata.getNodo2();
        String nodoTres = addressGeodata.getNodo3();
        String nodoCuatro = addressGeodata.getNodo4();

        String nodoDth = addressGeodata.getNodoDth();
        String nodoMovil = addressGeodata.getNodoMovil();
        String nodoFtth = addressGeodata.getNodoFtth();
        String nodoWifi = addressGeodata.getNodoWifi();
        //JDHT
        String nodoZonaUnifilar = addressGeodata.getGeoZonaUnifilar();
        String nodoZona3G = addressGeodata.getGeoZona3G();
        String nodoZona4G = addressGeodata.getGeoZona4G();
        String nodoZona5G = addressGeodata.getGeoZona5G();
        String nodoZonaCoberturaCavs = addressGeodata.getGeoZonaCoberturaCavs();
        String nodoZonaCoberturaUltimaMilla = addressGeodata.getGeoZonaCoberturaUltimaMilla();
        String nodoZonaGponDiseniado = addressGeodata.getGeoZonaGponDiseniado();
        String nodoZonaMicroOndas = addressGeodata.getGeoZonaMicroOndas();
        String nodoZonaCurrier = addressGeodata.getGeoZonaCurrier();
        //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1
        //cardenaslb
        //valor de la placa de la direccion (campo 7)
        String confiabilidadDir = addressGeodata.getValagreg();
        //valor de la direccion (campo 8)
        String confiabilidadPlaca = addressGeodata.getValplaca();
        
        boolean exist;
        boolean traslate;
        List<AddressAggregated> direccionesAgregadas = null;
        //Se extrae el complemento de la direccion
        //Si el Nivel de detalle es Completo "C", se debe realizar la consulta en el repositorio
        DireccionMglManager direccionMglManager = new DireccionMglManager();
        UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();


        // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
        addressService.setAmbigua(ambigua);
        addressService.setSource(addressGeodata.getFuente());
        addressService.setBarrioTraducido(addressGeodata.getBartrad());
        addressService.setAddressCode(addressGeodata.getCoddir());// sin complemento-->Revisar
        addressService.setAddressCodeFound(addressGeodata.getCodencont());// sin complemento-->Revisar
        addressService.setLocality(addressGeodata.getLocalidad());
        addressService.setCx(addressGeodata.getCx());// -->Cambiar por los de ubicacion
        addressService.setCy(addressGeodata.getCy());// -->Cambiar por los de ubicacion
        addressService.setState(addressGeodata.getEstado());
        
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();       
        GeograficoPoliticoMgl centroPobladoDireccion 
               = geograficoPoliticoManager.findCentroPobladoCodDane(addressReques.getCodDaneVt());
         
         if(centroPobladoDireccion == null){
              throw new ApplicationException("Error consultando el centro poblado por codigo dane. No Encontrada.");
             
         }
        
        /*Si la direccion es multiorign es Calle-Carrera 
         tiene seteado el tipo de direccion (desde Factibilidad, HHPP, CCMM) */
        if(centroPobladoDireccion.getGpoMultiorigen().equalsIgnoreCase("1")
                && addressReques.getTipoDireccion() != null
                && !addressReques.getTipoDireccion().isEmpty()
                && addressReques.getTipoDireccion().equalsIgnoreCase("CK")
                && addressReques.getNeighborhood() != null 
                && !addressReques.getNeighborhood().isEmpty()){
            
            if(addressGeodata.getDirtrad() != null 
                    && !addressGeodata.getDirtrad().isEmpty()
                    && addressGeodata.getDirtrad().toUpperCase().contains(addressReques.getNeighborhood().toUpperCase())){
         
            }else{
                addressGeodata.setDirtrad(addressGeodata.getDirtrad()+ " " + addressReques.getNeighborhood().toUpperCase());
            }
        }
         
         
        //Se consulta la direccion en el repositorio por el codDireccion retornado por el WS
        /*JDHT se realiza cambio de valor de busqueda debido a que no todas las direcciones tiene codigo de serviinformacion,
        se realiza busque por formato igac*/
        Direccion direccion = queryAddressOnRepository(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
        SubDireccion subDireccion = querySubAddressOnRepoByCod(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
        
        if (direccion == null && subDireccion == null) {
            Direccion intraducible
                    = queryAddressIntraducibleOnRepoByIgac(addressReques.getAddress(), addressReques.getCodDaneVt());

            if (intraducible == null) {
                idaddress = "0";
                addressService.setRecommendations("La direccion no existe en el repositorio.");
                //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                addressService.setNodoUno(nodoUno);
                addressService.setNodoDos(nodoDos);
                addressService.setNodoTres(nodoTres);
                //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                
                addressService.setNodoDth(nodoDth);
                addressService.setNodoMovil(nodoMovil);
                addressService.setNodoFtth(nodoFtth);
                addressService.setNodoCuatro(nodoCuatro);
                addressService.setNodoWifi(nodoWifi);
                
                addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                addressService.setNodoZona3G(nodoZona3G);
                addressService.setNodoZona4G(nodoZona4G);
                addressService.setNodoZona5G(nodoZona5G);
                addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                addressService.setNodoZonaCurrier(nodoZonaCurrier);
                addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
 
                
            } else {
                /*author Juan David Hernandez*/
                //se comenta debido a que se realizó la funcionalidad pero ya no va agosto 17 2018
                //Actualización de coberturas y geoData por consulta
                try {
                    
                    DireccionMgl direccionMglIntraducibleActualizada = new DireccionMgl();
                    direccionMglIntraducibleActualizada.setDirId(intraducible.getDirId());
                    if (addressGeodata.getNivsocio() != null && !addressGeodata.getNivsocio().isEmpty()) {
                        direccionMglIntraducibleActualizada.setDirNivelSocioecono(new BigDecimal(addressGeodata.getNivsocio()));
                    } else {
                        direccionMglIntraducibleActualizada.setDirNivelSocioecono(new BigDecimal(-1));
                    }

                    direccionMglIntraducibleActualizada.setDirNodouno(nodoUno != null && !nodoUno.isEmpty() ? nodoUno : null);
                    direccionMglIntraducibleActualizada.setDirNododos(nodoDos != null && !nodoDos.isEmpty() ? nodoDos : null);
                    direccionMglIntraducibleActualizada.setDirNodotres(nodoTres != null && !nodoTres.isEmpty() ? nodoTres : null);
                    direccionMglIntraducibleActualizada.setDirNodoDth(nodoDth != null && !nodoDth.isEmpty() ? nodoDth : null);
                    direccionMglIntraducibleActualizada.setDirNodoMovil(nodoMovil != null && !nodoMovil.isEmpty() ? nodoMovil : null);
                    direccionMglIntraducibleActualizada.setDirNodoFtth(nodoFtth != null && !nodoFtth.isEmpty() ? nodoFtth : null);
                    direccionMglIntraducibleActualizada.setDirNodoWifi(nodoWifi != null && !nodoWifi.isEmpty() ? nodoWifi : null);
                    
                    direccionMglIntraducibleActualizada.setGeoZonaUnifilar(nodoZonaUnifilar != null && !nodoZonaUnifilar.isEmpty() ? nodoZonaUnifilar : null);
                    direccionMglIntraducibleActualizada.setGeoZona3G(nodoZona3G != null && !nodoZona3G.isEmpty() ? nodoZona3G : null);
                    direccionMglIntraducibleActualizada.setGeoZona4G(nodoZona4G != null && !nodoZona4G.isEmpty() ? nodoZona4G : null);
                    direccionMglIntraducibleActualizada.setGeoZona5G(nodoZona5G != null && !nodoZona5G.isEmpty() ? nodoZona5G : null);
                    direccionMglIntraducibleActualizada.setGeoZonaCoberturaCavs(nodoZonaCoberturaCavs != null && !nodoZonaCoberturaCavs.isEmpty() ? nodoZonaCoberturaCavs : null);
                    direccionMglIntraducibleActualizada.setGeoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla != null && !nodoZonaCoberturaUltimaMilla.isEmpty() ? nodoZonaCoberturaUltimaMilla : null);
                    direccionMglIntraducibleActualizada.setGeoZonaCurrier(nodoZonaCurrier != null && !nodoZonaCurrier.isEmpty() ? nodoZonaCurrier : null);
                    direccionMglIntraducibleActualizada.setGeoZonaGponDiseniado(nodoZonaGponDiseniado != null && !nodoZonaGponDiseniado.isEmpty() ? nodoZonaGponDiseniado : null);
                    direccionMglIntraducibleActualizada.setGeoZonaMicroOndas(nodoZonaMicroOndas != null && !nodoZonaMicroOndas.isEmpty() ? nodoZonaMicroOndas : null);
            
                    boolean actualizacionExitosaDireccionIt = direccionMglManager
                            .actualizarCoberturasGeoDireccionMgl(direccionMglIntraducibleActualizada);
                    
                     if(intraducible.getUbicacion() != null && intraducible.getUbicacion().getUbiId() != null
                             && hasValidCoordinatesAddress.test(addressGeodata)
                             && (hasInvalidCoordinatesLocation.test(intraducible.getUbicacion())
                             || hasDifferentCoordinates.test(intraducible.getUbicacion(), addressGeodata))) {

                         UbicacionMgl ubicacionDireccion = generarObjetoUbicacion(intraducible, addressGeodata.getCy(), addressGeodata.getCx());
                         ubicacionMglManager.actualizarCoordenadasGeoDireccionMgl(ubicacionDireccion);
                    }

                    //Si se actualiza correctamente las coberturas, se consulta de nuevo el objeto con toda la información.               
                    if (actualizacionExitosaDireccionIt) {
                        intraducible
                                = queryAddressIntraducibleOnRepoByIgac(addressReques.getAddress(), addressReques.getCodDaneVt());
                    }
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);  
                }
                //Fin Ajuste Juan David Hernandez */

                // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
                //los valores de Cx y Cy son cambiados por los consultados en ubicacion por medio de la direccion
                if (intraducible.getCx() != null && !intraducible.getCx().isEmpty()
                        && intraducible.getCy() != null && !intraducible.getCy().isEmpty()) {
                    addressService.setCx(intraducible.getCx());
                    addressService.setCy(intraducible.getCy());
                }
                // </editor-fold>
                //Si existe la direccion
                idaddress = "d" + intraducible.getDirId().toString();
                revisarEnCampo = intraducible.getDirRevisar();
                if ("1".equals(intraducible.getDirRevisar())) {
                    addressService.setRecommendations("La direccion existe en el repositorio. La dirección se debe validar en terreno.");
                } else {
                    addressService.setRecommendations("La direccion existe en el repositorio.");
                }
                if (intraducible.getNodoUno() == null
                        || intraducible.getNodoUno().trim().isEmpty()) {
                    addressService.setNodoUno(nodoUno);
                } else {
                    addressService.setNodoUno(intraducible.getNodoUno());
                }
                if (intraducible.getNodoDos() == null
                        || intraducible.getNodoDos().trim().isEmpty()) {
                    addressService.setNodoDos(nodoDos);
                } else {
                    addressService.setNodoDos(intraducible.getNodoDos());
                }
                if (intraducible.getNodoTres() == null
                        || intraducible.getNodoTres().trim().isEmpty()) {
                    addressService.setNodoTres(nodoTres);
                } else {
                    addressService.setNodoTres(intraducible.getNodoTres());
                }
                if (intraducible.getNodoCuatro() == null
                        || intraducible.getNodoCuatro().trim().isEmpty()) {
                    addressService.setNodoCuatro(nodoCuatro);
                } else {
                    addressService.setNodoCuatro(intraducible.getNodoCuatro());
                }
                if (intraducible.getNodoDth() == null
                        || intraducible.getNodoDth().trim().isEmpty()) {
                    addressService.setNodoDth(nodoDth);
                } else {
                    addressService.setNodoDth(intraducible.getNodoDth());
                }
                if (intraducible.getNodoMovil() == null
                        || intraducible.getNodoMovil().trim().isEmpty()) {
                    addressService.setNodoMovil(nodoMovil);
                } else {
                    addressService.setNodoMovil(intraducible.getNodoMovil());
                }
                if (intraducible.getNodoFtth() == null
                        || intraducible.getNodoFtth().trim().isEmpty()) {
                    addressService.setNodoFtth(nodoFtth);
                } else {
                    addressService.setNodoFtth(intraducible.getNodoFtth());
                }
                if (intraducible.getNodoWifi() == null
                        || intraducible.getNodoWifi().trim().isEmpty()) {
                    addressService.setNodoWifi(nodoWifi);
                } else {
                    addressService.setNodoWifi(intraducible.getNodoWifi());
                }
                //JDHT
                if (intraducible.getGeoZonaUnifilar() == null
                        || intraducible.getGeoZonaUnifilar().trim().isEmpty()) {
                    addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                } else {
                    addressService.setNodoZonaUnifilar(intraducible.getGeoZonaUnifilar());
                }
                
                 if (intraducible.getGeoZona3G()== null
                        || intraducible.getGeoZona3G().trim().isEmpty()) {
                    addressService.setNodoZona3G(nodoZona3G);
                } else {
                    addressService.setNodoZona3G(intraducible.getGeoZona3G());
                }
                 
                  if (intraducible.getGeoZona4G()== null
                        || intraducible.getGeoZona4G().trim().isEmpty()) {
                    addressService.setNodoZona4G(nodoZona4G);
                } else {
                    addressService.setNodoZona4G(intraducible.getGeoZona4G());
                }
                  
                if (intraducible.getGeoZona5G() == null
                        || intraducible.getGeoZona5G().trim().isEmpty()) {
                    addressService.setNodoZona5G(nodoZona5G);
                } else {
                    addressService.setNodoZona5G(intraducible.getGeoZona5G());
                }
                
                if (intraducible.getGeoZonaCoberturaCavs()== null
                        || intraducible.getGeoZonaCoberturaCavs().trim().isEmpty()) {
                    addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                } else {
                    addressService.setNodoZonaCoberturaCavs(intraducible.getGeoZonaCoberturaCavs());
                }
                
                 if (intraducible.getGeoZonaCoberturaUltimaMilla()== null
                        || intraducible.getGeoZonaCoberturaUltimaMilla().trim().isEmpty()) {
                    addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                } else {
                    addressService.setNodoZonaCoberturaUltimaMilla(intraducible.getGeoZonaCoberturaUltimaMilla());
                }
                 
                  if (intraducible.getGeoZonaCurrier()== null
                        || intraducible.getGeoZonaCurrier().trim().isEmpty()) {
                    addressService.setNodoZonaCurrier(nodoZonaCurrier);
                } else {
                    addressService.setNodoZonaCurrier(intraducible.getGeoZonaCurrier());
                }
                  
                if (intraducible.getGeoZonaGponDiseniado()== null
                        || intraducible.getGeoZonaGponDiseniado().trim().isEmpty()) {
                    addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                } else {
                    addressService.setNodoZonaGponDiseniado(intraducible.getGeoZonaGponDiseniado());
                }
                
                 if (intraducible.getGeoZonaMicroOndas()== null
                        || intraducible.getGeoZonaMicroOndas().trim().isEmpty()) {
                    addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                } else {
                    addressService.setNodoZonaMicroOndas(intraducible.getGeoZonaMicroOndas());
                }
                  
                
            }
        } else {
            //Si existe la direccion          
            //Se verifica si la direccion es una subdireccion
            if (direccion != null) {
                /*author Juan David Hernandez*/
                //se comenta debido a que se realizó la funcionalidad pero ya no va agosto 17 2018
                //Actualización de coberturas y geoData por consulta
                try {
                    
                    DireccionMgl direccionMglActualizada = new DireccionMgl();
                    direccionMglActualizada.setDirId(direccion.getDirId());
                    if (leveleconomic != null && !leveleconomic.isEmpty()) {
                        direccionMglActualizada.setDirNivelSocioecono(new BigDecimal(leveleconomic));
                    } else {
                        direccionMglActualizada.setDirNivelSocioecono(new BigDecimal(-1));
                    }
                    
                    direccionMglActualizada.setDirNodouno(nodoUno != null && !nodoUno.isEmpty() ? nodoUno : null);
                    direccionMglActualizada.setDirNododos(nodoDos != null && !nodoDos.isEmpty() ? nodoDos : null);
                    direccionMglActualizada.setDirNodotres(nodoTres != null && !nodoTres.isEmpty() ? nodoTres : null);
                    direccionMglActualizada.setDirNodoFttx(nodoCuatro != null && !nodoCuatro.isEmpty() ? nodoCuatro : null);
                    direccionMglActualizada.setDirNodoDth(nodoDth != null && !nodoDth.isEmpty() ? nodoDth : null);
                    direccionMglActualizada.setDirNodoMovil(nodoMovil != null && !nodoMovil.isEmpty() ? nodoMovil : null);
                    direccionMglActualizada.setDirNodoFtth(nodoFtth != null && !nodoFtth.isEmpty() ? nodoFtth : null);
                    direccionMglActualizada.setDirNodoWifi(nodoWifi != null && !nodoWifi.isEmpty() ? nodoWifi : null);
                    direccionMglActualizada.setGeoZona3G(nodoZona3G != null && !nodoZona3G.isEmpty() ? nodoZona3G : null);    
                    direccionMglActualizada.setGeoZona4G(nodoZona4G != null && !nodoZona4G.isEmpty() ? nodoZona4G : null); 
                    direccionMglActualizada.setGeoZona5G(nodoZona5G != null && !nodoZona5G.isEmpty() ? nodoZona5G : null); 
                    direccionMglActualizada.setGeoZonaCoberturaCavs(nodoZonaCoberturaCavs != null && !nodoZonaCoberturaCavs.isEmpty() ? nodoZonaCoberturaCavs : null); 
                    direccionMglActualizada.setGeoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla != null && !nodoZonaCoberturaUltimaMilla.isEmpty() ? nodoZonaCoberturaUltimaMilla : null); 
                    direccionMglActualizada.setGeoZonaCurrier(nodoZonaCurrier != null && !nodoZonaCurrier.isEmpty() ? nodoZonaCurrier : null); 
                    direccionMglActualizada.setGeoZonaGponDiseniado(nodoZonaGponDiseniado != null && !nodoZonaGponDiseniado.isEmpty() ? nodoZonaGponDiseniado : null); 
                    direccionMglActualizada.setGeoZonaMicroOndas(nodoZonaMicroOndas != null && !nodoZonaMicroOndas.isEmpty() ? nodoZonaMicroOndas : null); 
                    direccionMglActualizada.setGeoZonaUnifilar(nodoZonaUnifilar != null && !nodoZonaUnifilar.isEmpty() ? nodoZonaUnifilar : null); 
                                                           
                    boolean actualizacionExitosaDireccion
                            = direccionMglManager.actualizarCoberturasGeoDireccionMgl(direccionMglActualizada);
                    
                    if (direccion.getUbicacion() != null && direccion.getUbicacion().getUbiId() != null) {
                        UbicacionMgl ubicacionBusqueda = new UbicacionMgl();
                        ubicacionBusqueda.setUbiId(direccion.getUbicacion().getUbiId());
                        UbicacionMgl ubicacionEncontrada = ubicacionMglManager.findById(ubicacionBusqueda);

                        if (ubicacionEncontrada != null
                                && hasValidCoordinatesAddress.test(addressGeodata)
                                && (hasInvalidCoordinatesLocationUbi.test(ubicacionEncontrada)
                                || hasDifferentCoordinatesUbiGeoDirMgl.test(ubicacionEncontrada, addressGeodata))) {

                            UbicacionMgl ubicacionDireccion = generarObjetoUbicacion(direccion, addressGeodata.getCy(), addressGeodata.getCx());
                            ubicacionMglManager.actualizarCoordenadasGeoDireccionMgl(ubicacionDireccion);
                        }
                    }
                    
                    //Si se actualiza correctamente las coberturas, se consulta de nuevo el objeto con toda la información.               
                    if (actualizacionExitosaDireccion) {
                        direccion = queryAddressOnRepository(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
                    }
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg); 
                }
                //Fin Ajuste Juan David Hernandez */            

                // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
                //los valores de Cx y Cy son cambiados por los consultados en ubicacion por medio de la direccion
                if (direccion.getCx() != null && !direccion.getCx().isEmpty()
                        && direccion.getCy() != null && !direccion.getCy().isEmpty()) {
                    addressService.setCx(direccion.getCx());
                    addressService.setCy(direccion.getCy());
                }
                // </editor-fold>
                //Es una direccion y si existe
                idaddress = "d" + direccion.getDirId().toString();
                revisarEnCampo = direccion.getDirRevisar();
                //En caso de que el estrato este vacio se debe asignar el nivel socioeconomico
                if (direccion.getDirEstrato() != null) {
                    stratum = direccion.getDirEstrato().toString();//else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                } //else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                if (direccion.getDirNivelSocioecono() != null) {
                    leveleconomic = direccion.getDirNivelSocioecono().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                }
                //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                if (direccion.getNodoUno() != null && !direccion.getNodoUno().equals("")) {
                    addressService.setNodoUno(direccion.getNodoUno());
                } else {
                    addressService.setNodoUno(nodoUno);
                }
                if (direccion.getNodoDos() != null && !direccion.getNodoDos().equals("")) {
                    addressService.setNodoDos(direccion.getNodoDos());
                } else {
                    addressService.setNodoDos(nodoDos);
                }
                if (direccion.getNodoTres() != null && !direccion.getNodoTres().equals("")) {
                    addressService.setNodoTres(direccion.getNodoTres());
                } else {
                    addressService.setNodoTres(nodoTres);
                }
                if (direccion.getNodoCuatro() != null && !direccion.getNodoCuatro().equals("")) {
                    addressService.setNodoCuatro(direccion.getNodoCuatro());
                } else {
                    addressService.setNodoCuatro(nodoCuatro);
                }
                //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1 

                if (direccion.getNodoDth() != null && !direccion.getNodoDth().equals("")) {
                    addressService.setNodoDth(direccion.getNodoDth());
                } else {
                    addressService.setNodoDth(nodoDth);
                }
                if (direccion.getNodoMovil() != null
                        && !direccion.getNodoMovil().trim().isEmpty()) {
                    addressService.setNodoMovil(direccion.getNodoMovil());
                } else {
                    addressService.setNodoMovil(nodoMovil);
                }
                if (direccion.getNodoFtth() != null
                        && !direccion.getNodoFtth().trim().isEmpty()) {
                    addressService.setNodoFtth(direccion.getNodoFtth());
                } else {
                    addressService.setNodoFtth(nodoFtth);
                }
                if (direccion.getNodoWifi() != null
                        && !direccion.getNodoWifi().trim().isEmpty()) {
                    addressService.setNodoWifi(direccion.getNodoWifi());
                } else {
                    addressService.setNodoWifi(nodoWifi);
                }
                
                //JDHT
                if (direccion.getGeoZona3G()!= null
                        && !direccion.getGeoZona3G().trim().isEmpty()) {
                    addressService.setNodoZona3G(direccion.getGeoZona3G());
                } else {
                    addressService.setNodoZona3G(nodoZona3G);
                }
                
                if (direccion.getGeoZona4G()!= null
                        && !direccion.getGeoZona4G().trim().isEmpty()) {
                    addressService.setNodoZona4G(direccion.getGeoZona4G());
                } else {
                    addressService.setNodoZona4G(nodoZona4G);
                }
                
                if (direccion.getGeoZona5G()!= null
                        && !direccion.getGeoZona5G().trim().isEmpty()) {
                    addressService.setNodoZona5G(direccion.getGeoZona5G());
                } else {
                    addressService.setNodoZona5G(nodoZona5G);
                }
                
                  if (direccion.getGeoZonaCoberturaCavs()!= null
                        && !direccion.getGeoZonaCoberturaCavs().trim().isEmpty()) {
                    addressService.setNodoZonaCoberturaCavs(direccion.getGeoZonaCoberturaCavs());
                } else {
                    addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                }
                  
                   if (direccion.getGeoZonaCurrier()!= null
                        && !direccion.getGeoZonaCurrier().trim().isEmpty()) {
                    addressService.setNodoZonaCurrier(direccion.getGeoZonaCurrier());
                } else {
                    addressService.setNodoZonaCurrier(nodoZonaCurrier);
                }
                   
                if (direccion.getGeoZonaGponDiseniado()!= null
                        && !direccion.getGeoZonaGponDiseniado().trim().isEmpty()) {
                    addressService.setNodoZonaGponDiseniado(direccion.getGeoZonaGponDiseniado());
                } else {
                    addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                }
                
                if (direccion.getGeoZonaMicroOndas()!= null
                        && !direccion.getGeoZonaMicroOndas().trim().isEmpty()) {
                    addressService.setNodoZonaMicroOndas(direccion.getGeoZonaMicroOndas());
                } else {
                    addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                }
                
                 if (direccion.getGeoZonaUnifilar()!= null
                        && !direccion.getGeoZonaUnifilar().trim().isEmpty()) {
                    addressService.setNodoZonaUnifilar(direccion.getGeoZonaUnifilar());
                } else {
                    addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                }
                
            } else {
                //Se consulta como una subDireccion
                //SubDireccion subDireccion = querySubAddressOnRepoByCod(codAddres, centroPobladoDireccion.getGpoId());
                if (subDireccion == null) {
                    idaddress = "0";
                    addressService.setRecommendations("La direccion no existe en el repositorio.");
                    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1                            
                    addressService.setNodoUno(nodoUno);
                    addressService.setNodoDos(nodoDos);
                    addressService.setNodoTres(nodoTres);
                    addressService.setNodoCuatro(nodoCuatro);
                    //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1

                    addressService.setNodoDth(nodoDth);
                    addressService.setNodoMovil(nodoMovil);
                    addressService.setNodoFtth(nodoFtth);
                    addressService.setNodoWifi(nodoWifi);
                    
                    addressService.setNodoZona3G(nodoZona3G);
                    addressService.setNodoZona4G(nodoZona4G);
                    addressService.setNodoZona5G(nodoZona5G);
                    addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                    addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                    addressService.setNodoZonaCurrier(nodoZonaCurrier);
                    addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                    addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                    addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                    
                } else {
                    /*author Juan David Hernandez*/
                    //se comenta debido a que se realizó la funcionalidad pero ya no va agosto 17 2018
                    /*Actualización de coberturas y geoData por consulta*/
                    try {
                        SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
                        SubDireccionMgl subDireccionMglActualizada = new SubDireccionMgl();
                        if (leveleconomic != null && !leveleconomic.isEmpty()) {
                            subDireccionMglActualizada.setSdiNivelSocioecono(new BigDecimal(leveleconomic));
                        } else {
                            subDireccionMglActualizada.setSdiNivelSocioecono(new BigDecimal(-1));
                        }
                        subDireccionMglActualizada.setSdiId(subDireccion.getSdiId());
                        
                        subDireccionMglActualizada.setSdiNodouno(nodoUno != null && !nodoUno.isEmpty() ? nodoUno : null);
                        subDireccionMglActualizada.setSdiNododos(nodoDos != null && !nodoDos.isEmpty() ? nodoDos : null);
                        subDireccionMglActualizada.setSdiNodotres(nodoTres != null && !nodoTres.isEmpty() ? nodoTres : null);
                        subDireccionMglActualizada.setSdiNodoFttx(nodoCuatro != null && !nodoCuatro.isEmpty() ? nodoCuatro : null);
                        subDireccionMglActualizada.setSdiNodoDth(nodoDth != null && !nodoDth.isEmpty() ? nodoDth : null);
                        subDireccionMglActualizada.setSdiNodoMovil(nodoMovil != null && !nodoMovil.isEmpty() ? nodoMovil : null);
                        subDireccionMglActualizada.setSdiNodoFtth(nodoFtth != null && !nodoFtth.isEmpty() ? nodoFtth : null);
                        subDireccionMglActualizada.setSdiNodoWifi(nodoWifi != null && !nodoWifi.isEmpty() ? nodoWifi : null);
                        subDireccionMglActualizada.setGeoZona3G(nodoZona3G != null && !nodoZona3G.isEmpty() ? nodoZona3G : null);
                        subDireccionMglActualizada.setGeoZona4G(nodoZona4G != null && !nodoZona4G.isEmpty() ? nodoZona4G : null);
                        subDireccionMglActualizada.setGeoZona5G(nodoZona5G != null && !nodoZona5G.isEmpty() ? nodoZona5G : null);
                        subDireccionMglActualizada.setGeoZonaCoberturaCavs(nodoZonaCoberturaCavs != null && !nodoZonaCoberturaCavs.isEmpty() ? nodoZonaCoberturaCavs : null);
                        subDireccionMglActualizada.setGeoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla != null && !nodoZonaCoberturaUltimaMilla.isEmpty() ? nodoZonaCoberturaUltimaMilla : null);
                        subDireccionMglActualizada.setGeoZonaCurrier(nodoZonaCurrier != null && !nodoZonaCurrier.isEmpty() ? nodoZonaCurrier : null);
                        subDireccionMglActualizada.setGeoZonaGponDiseniado(nodoZonaGponDiseniado != null && !nodoZonaGponDiseniado.isEmpty() ? nodoZonaGponDiseniado : null);
                        subDireccionMglActualizada.setGeoZonaMicroOndas(nodoZonaMicroOndas != null && !nodoZonaMicroOndas.isEmpty() ? nodoZonaMicroOndas : null);
                        subDireccionMglActualizada.setGeoZonaUnifilar(nodoZonaUnifilar != null && !nodoZonaUnifilar.isEmpty() ? nodoZonaUnifilar : null);
                        
                        boolean actualizacionExitosa
                                = subDireccionMglManager.actualizarCoberturasGeoSubDireccionMgl(subDireccionMglActualizada);
                                              //Si se actualiza correctamente las coberturas, se consulta de nuevo el objeto con toda la información.
                        if (actualizacionExitosa) {
                            subDireccion = querySubAddressOnRepoByCod(addressGeodata.getDirtrad(), centroPobladoDireccion.getGpoId());
                        }
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                        LOGGER.error(msg);
                    }
                    /*Fin Ajuste Juan David Hernandez */

                    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
                    //los valores de Cx y Cy son cambiados por los consultados en ubicacion por medio de la direccion
                    if (subDireccion.getCx() != null && !subDireccion.getCx().isEmpty()
                            && subDireccion.getCy() != null && !subDireccion.getCy().isEmpty()) {
                        addressService.setCx(subDireccion.getCx());
                        addressService.setCy(subDireccion.getCy());
                    }
                    // </editor-fold>
                    idaddress = "s" + subDireccion.getSdiId().toString();
                    revisarEnCampo = "0";
                    //En caso de que el estrato este vacio se debe asignar el nivel socioeconomico
                    if (subDireccion.getSdiEstrato() != null) {
                        stratum = subDireccion.getSdiEstrato().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    } //else Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    if (subDireccion.getSdiNivelSocioecono() != null) {
                        leveleconomic = subDireccion.getSdiNivelSocioecono().toString();//Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
                    }
                    if (subDireccion.getSdiActividadEcono() != null) {
                        activityeconomicOnRepo = subDireccion.getSdiActividadEcono().toString();
                    }
                    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1        

                    if (subDireccion.getNodoUno() != null && !subDireccion.getNodoUno().equals("")) {
                        addressService.setNodoUno(subDireccion.getNodoUno());
                    } else {
                        addressService.setNodoUno(nodoUno);
                    }
                    if (subDireccion.getNodoDos() != null && !subDireccion.getNodoDos().equals("")) {
                        addressService.setNodoDos(subDireccion.getNodoDos());
                    } else {
                        addressService.setNodoDos(nodoDos);
                    }
                    if (subDireccion.getNodoTres() != null && !subDireccion.getNodoTres().equals("")) {
                        addressService.setNodoTres(subDireccion.getNodoTres());
                    } else {
                        addressService.setNodoTres(nodoTres);
                    }
                    //Fin Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.1   

                    if (subDireccion.getNodoDth() != null && !subDireccion.getNodoDth().equals("")) {
                        addressService.setNodoDth(subDireccion.getNodoDth());
                    } else {
                        addressService.setNodoDth(nodoDth);
                    }
                    if (subDireccion.getNodoMovil() != null
                            && !subDireccion.getNodoMovil().trim().isEmpty()) {
                        addressService.setNodoMovil(subDireccion.getNodoMovil());
                    } else {
                        addressService.setNodoMovil(nodoMovil);
                    }
                    if (subDireccion.getNodoFtth() != null
                            && !subDireccion.getNodoFtth().trim().isEmpty()) {
                        addressService.setNodoFtth(subDireccion.getNodoFtth());
                    } else {
                        addressService.setNodoFtth(nodoFtth);
                    }

                    if (subDireccion.getNodoFttx() != null
                            && !subDireccion.getNodoFttx().trim().isEmpty()) {
                        addressService.setNodoCuatro(subDireccion.getNodoFttx());
                    } else {
                        addressService.setNodoCuatro(nodoCuatro);
                    }
                    if (subDireccion.getNodoWifi() != null
                            && !subDireccion.getNodoWifi().trim().isEmpty()) {
                        addressService.setNodoWifi(subDireccion.getNodoWifi());
                    } else {
                        addressService.setNodoWifi(nodoWifi);
                    }
                    //JDHT
                     if (subDireccion.getGeoZona3G()!= null
                            && !subDireccion.getGeoZona3G().trim().isEmpty()) {
                        addressService.setNodoZona3G(subDireccion.getGeoZona3G());
                    } else {
                        addressService.setNodoZona3G(nodoZona3G);
                    }
                     
                     if (subDireccion.getGeoZona4G()!= null
                            && !subDireccion.getGeoZona4G().trim().isEmpty()) {
                        addressService.setNodoZona4G(subDireccion.getGeoZona4G());
                    } else {
                        addressService.setNodoZona4G(nodoZona4G);
                    }
                     
                     if (subDireccion.getGeoZona5G()!= null
                            && !subDireccion.getGeoZona5G().trim().isEmpty()) {
                        addressService.setNodoZona5G(subDireccion.getGeoZona5G());
                    } else {
                        addressService.setNodoZona5G(nodoZona5G);
                    }
                     
                     if (subDireccion.getGeoZonaCoberturaCavs()!= null
                            && !subDireccion.getGeoZonaCoberturaCavs().trim().isEmpty()) {
                        addressService.setNodoZonaCoberturaCavs(subDireccion.getGeoZonaCoberturaCavs());
                    } else {
                        addressService.setNodoZonaCoberturaCavs(nodoZonaCoberturaCavs);
                    }
                     
                      if (subDireccion.getGeoZonaCoberturaUltimaMilla()!= null
                            && !subDireccion.getGeoZonaCoberturaUltimaMilla().trim().isEmpty()) {
                        addressService.setNodoZonaCoberturaUltimaMilla(subDireccion.getGeoZonaCoberturaUltimaMilla());
                    } else {
                        addressService.setNodoZonaCoberturaUltimaMilla(nodoZonaCoberturaUltimaMilla);
                    }                      
                      
                    if (subDireccion.getGeoZonaCurrier()!= null
                            && !subDireccion.getGeoZonaCurrier().trim().isEmpty()) {
                        addressService.setNodoZonaCurrier(subDireccion.getGeoZonaCurrier());
                    } else {
                        addressService.setNodoZonaCurrier(nodoZonaCurrier);
                    }
                    
                     if (subDireccion.getGeoZonaGponDiseniado()!= null
                            && !subDireccion.getGeoZonaGponDiseniado().trim().isEmpty()) {
                        addressService.setNodoZonaGponDiseniado(subDireccion.getGeoZonaGponDiseniado());
                    } else {
                        addressService.setNodoZonaGponDiseniado(nodoZonaGponDiseniado);
                    }
                     
                     if (subDireccion.getGeoZonaMicroOndas()!= null
                            && !subDireccion.getGeoZonaMicroOndas().trim().isEmpty()) {
                        addressService.setNodoZonaMicroOndas(subDireccion.getGeoZonaMicroOndas());
                    } else {
                        addressService.setNodoZonaMicroOndas(nodoZonaMicroOndas);
                    }
                     
                      if (subDireccion.getGeoZonaUnifilar()!= null
                            && !subDireccion.getGeoZonaUnifilar().trim().isEmpty()) {
                        addressService.setNodoZonaUnifilar(subDireccion.getGeoZonaUnifilar());
                    } else {
                        addressService.setNodoZonaUnifilar(nodoZonaUnifilar);
                    }
  
                }
            }
        }
        recommendations = evaluateRecommendations(address, idaddress, chagenumber, ambigua, alternateaddress, revisarEnCampo);

        category = queryCategoryAddress(address);

        appletstandar = addressGeodata.getManzana();

        exist = validExistAddress(addressGeodata.getDirtrad(), state, qualifiers, codAddresPlaca, centroPobladoDireccion.getGpoId());
        traslate = validTraslateAdrress(address, state);

        addressService.setIdaddress(idaddress);
        if (NIVEL_DETALLE_COMPLETO.equals(addressReques.getLevel().toUpperCase())) {
            //Estas se retornan sólo para consulta completa
            addressService.setRecommendations(recommendations);
        } else {

            addressService.setRecommendations(null);
        }

        addressService.setAddress(address);
        addressService.setNeighborhood(neighborhood);
        addressService.setCategory(category);
        //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
        addressService.setZipCode(codZip);
        addressService.setCoddanemcpio(codDivipola);
        addressService.setComentEconomicLevel(comSocioEconomico);
        //Fin Modificacion Carlos Villamil Direcciones Fase I 20121218

        //Se valida si existe el levelEconomic en el repositorio para determinar cual se debe devolver en el WS
        addressService.setLeveleconomic(leveleconomic);// Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
        addressService.setEstratoDef(stratum);// Modificacion Carlos Villamil Direcciones Fase I 20130523 v. 1.2
        
        addressService.setAppletstandar(appletstandar);
        addressService.setChagenumber(chagenumber);
        addressService.setAlternateaddress(alternateaddress);
        addressService.setLevellive(levellive);
        addressService.setQualifiers(qualifiers);
        if (!activityeconomicOnRepo.isEmpty()) {
            addressService.setActivityeconomic(activityeconomicOnRepo);
        } else {
            addressService.setActivityeconomic(activityeconomic);
        }

        addressService.setExist(String.valueOf(exist));
        addressService.setTraslate(String.valueOf(traslate));

        if (direccionesAgregadas != null) {
            addressService.setAddressAggregated(direccionesAgregadas);
        }
        if (confiabilidadDir != null
                && !confiabilidadDir.trim().isEmpty()) {
            addressService.setReliability(confiabilidadDir);
        }
         if (confiabilidadPlaca != null
                && !confiabilidadPlaca.trim().isEmpty()) {
            addressService.setReliabilityPlaca(confiabilidadPlaca);
        }

        return addressService;
    }

    /**
     *
     * @param direccion
     * @param geodataDireccionNueva
     * @return
     */
    private Direccion mapearDireccionNueva(Direccion direccion, AddressGeodata geodataDireccionNueva) {
        Direccion dirNueva = new Direccion();
        Ubicacion ubicacion = new Ubicacion();
        TipoDireccion tipoDir = new TipoDireccion();
        tipoDir.setTdiId(direccion.getTipoDireccion().getTdiId());
        ubicacion.setUbiId(direccion.getUbicacion().getUbiId());
        dirNueva.setUbicacion(ubicacion);
        dirNueva.setTipoDireccion(tipoDir);
        dirNueva.setDirFormatoIgac(geodataDireccionNueva.getDirtrad());
        dirNueva.setDirServinformacion(geodataDireccionNueva.getCodencont());
        dirNueva.setDirNostandar(geodataDireccionNueva.getDirtrad());
        dirNueva.setDirOrigen(geodataDireccionNueva.getFuente());
        //TODO: Validar el mapeo de Confiabilidad
        if ("".equals(geodataDireccionNueva.getValplaca()) || "0".equals(geodataDireccionNueva.getValplaca())) {
            dirNueva.setDirConfiabilidad(BigDecimal.ZERO);
        } else {
            dirNueva.setDirConfiabilidad(new BigDecimal(geodataDireccionNueva.getValplaca()));
        }
        if ("".equals(geodataDireccionNueva.getNivsocio())) {
            dirNueva.setDirNivelSocioecono(new BigDecimal(-2)); //Se envia negativo para el CASE en la DB
        } else {
            dirNueva.setDirNivelSocioecono(new BigDecimal(geodataDireccionNueva.getNivsocio()));
        }
        if ("".equals(geodataDireccionNueva.getEstrato())) {
            dirNueva.setDirEstrato(new BigDecimal(-2));//Se envia negativo para el CASE en la DB
        } else {
            dirNueva.setDirEstrato(new BigDecimal(geodataDireccionNueva.getEstrato()));
        }
        //TODO: PENDIENTE por validar quien entrega este codPostal
        //modificado carlos villamil fase 1 dircciones 2012-12-14
        dirNueva.setDirComentarioNivelSocioEconomico(geodataDireccionNueva.getComentEconomicLevel());//modificado carlos villamil fase 1 dircciones 2012-12-14
        dirNueva.setDirUsuarioCreacion(direccion.getDirUsuarioCreacion());
        dirNueva.setDirManzanaCatastral(geodataDireccionNueva.getManzana());
        dirNueva.setDirManzana(getAppletFromAddress(geodataDireccionNueva.getCoddir()));
        dirNueva.setDirRevisar(direccion.getDirRevisar());
        dirNueva.setDirActividadEconomica(geodataDireccionNueva.getActeconomica());
        dirNueva.setNodoUno(geodataDireccionNueva.getNodo1());
        dirNueva.setNodoDos(geodataDireccionNueva.getNodo2());
        dirNueva.setNodoTres(geodataDireccionNueva.getNodo3());
        dirNueva.setNodoCuatro(geodataDireccionNueva.getNodo4());
        dirNueva.setNodoDth(geodataDireccionNueva.getNodoDth());
        dirNueva.setNodoMovil(geodataDireccionNueva.getNodoMovil());
        dirNueva.setNodoFtth(geodataDireccionNueva.getNodoFtth());
        dirNueva.setNodoWifi(geodataDireccionNueva.getNodoWifi());
        dirNueva.setGeoZonaUnifilar(geodataDireccionNueva.getGeoZonaUnifilar());
        return dirNueva;
    }

    /**
     * Consulta en el repositorio la direccion estandarizada y retorna el Id
     *
     * @param codDireccion
     * @param centroPobladoId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Direccion queryAddressOnRepository(String codDireccion, BigDecimal centroPobladoId) throws ApplicationException {
        Direccion dirRepositorio = null;
        AccessData adb = null;
        AccessData adbUbi = null;

        try {
            adb = ManageAccess.createAccessData();
            // Realiza la consulta de la dirección en la base de datos
            DataObject obj = adb.outDataObjec("dir3", codDireccion, centroPobladoId);
            DireccionUtil.closeConnection(adb);

            if (obj == null) {
                LOGGER.warn("No se encontró la dirección en el repositorio. CodDirección: {} CentroPobladoId: {}",
                        codDireccion , centroPobladoId);
                return dirRepositorio;
            }

            dirRepositorio = mapAddressFromDataObject(obj);
            BigDecimal idUbi = Optional.of(dirRepositorio)
                    .map(Direccion::getUbicacion)
                    .map(Ubicacion::getUbiId)
                    .orElse(null);

                if (idUbi != null) {
                    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-09-30">
                    adbUbi = ManageAccess.createAccessData();
                    DataObject objUbi = adbUbi.outDataObjec("getUbiCxCyById",idUbi.toBigInteger().toString());
                    adbUbi.clear();
                    String ubiCx = objUbi.getString("CX");
                    String ubiCy = objUbi.getString("CY");
                    
                    if (ubiCx != null && !ubiCx.isEmpty() && ubiCy != null && !ubiCy.isEmpty()) {
                        dirRepositorio.setCx(ubiCx);
                        dirRepositorio.setCy(ubiCy);
                    }
                    // </editor-fold>
                }
                
        } catch (ExceptionDB e) {
            try {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                DireccionUtil.closeConnection(adb);
                adbUbi.clear();
                throw new ApplicationException("Error en queryAddressOnRepository. EX000 " + e.getMessage(), e);
            } catch (ExceptionDB ex) {
                throw new ApplicationException("Error en queryAddressOnRepository. EX000 " + e.getMessage(), e);
            }
        }
        return dirRepositorio;
    }

    /**
     * Mapea la direccion de un objeto DataObject a un objeto Direccion
     *
     * @param obj DataObject
     * @return {@link Direccion}  Direccion mapeada
     */
    private Direccion mapAddressFromDataObject(DataObject obj) {
        Direccion address = new Direccion();
        BigDecimal dirId = obj.getBigDecimal("DIRECCION_ID");
        String dirIgac = obj.getString("FORMATO_IGAC");
        String dirRevisar = obj.getString("REVISAR_DIRECCION");
        BigDecimal idUbi = obj.getBigDecimal("UBICACION_ID");//pendiente retornar
        BigDecimal estrato = obj.getBigDecimal("ESTRATO");
        BigDecimal nvlSocio = obj.getBigDecimal("NIVEL_SOCIOECONOMICO");
        String comentarioNivelSocioEconomico = obj.getString("COMENTARIO_SOCIOECONOMICO");//Cambio carlos villamil direcciones fase I 2012-12-14
        String nodoUno = obj.getString("NODO_UNO");//Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1
        String nodoDos = obj.getString("NODO_DOS");//Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1
        String nodoTres = obj.getString("NODO_TRES");//Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1
        String nodoDth = obj.getString("NODO_DTH");
        String nodoMovil = obj.getString("NODO_MOVIL");
        String nodoFtth = obj.getString("NODO_FTTH");
        String nodoWifi = obj.getString("NODO_WIFI");

        address.setDirId(dirId);
        address.setDirFormatoIgac(dirIgac);
        address.setDirRevisar(dirRevisar);
        address.setDirEstrato(estrato);
        address.setDirNivelSocioecono(nvlSocio);
        address.setDirComentarioNivelSocioEconomico(comentarioNivelSocioEconomico);
        address.setNodoUno(nodoUno);
        address.setNodoDos(nodoDos);
        address.setNodoTres(nodoTres);
        address.setNodoDth(nodoDth);
        address.setNodoMovil(StringUtils.isNotBlank(nodoMovil) && !nodoMovil.equalsIgnoreCase("NA") ? nodoMovil : null);
        address.setNodoFtth(StringUtils.isNotBlank(nodoFtth) && !nodoFtth.equalsIgnoreCase("NA") ? nodoFtth : null);
        address.setNodoWifi(StringUtils.isNotBlank(nodoWifi) && !nodoWifi.equalsIgnoreCase("NA") ? nodoWifi : null);

        if (idUbi != null) {
            Ubicacion ubiDir = new Ubicacion();
            ubiDir.setUbiId(idUbi);
            address.setUbicacion(ubiDir);
        }

        return address;
    }


    /**
     * Consulta en el repositorio la direccion estandarizada y retorna el Id
     *
     * @param formatoIgac
     * @return
     * @throws Exception
     */
    private Direccion queryAddressIntraducibleOnRepoByIgac(String formatoIgac, String codigoDane) throws ApplicationException {
        Direccion dirRepositorio = null;
        AccessData adb = null;
        try {      
            GeograficoPoliticoManager manager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPoblado = manager.findCentroPobladoCodDane(codigoDane);
    
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dir10", formatoIgac, centroPoblado.getGpoId());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                BigDecimal dir_id = obj.getBigDecimal("DIRECCION_ID");
                String dir_igac = obj.getString("FORMATO_IGAC");
                String dir_revisar = obj.getString("REVISAR_DIRECCION");
                BigDecimal id_ubi = obj.getBigDecimal("UBICACION_ID");
                //Inicio Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1
                String nodoUno = obj.getString("NODO_UNO");
                String nodoDos = obj.getString("NODO_DOS");
                String nodoTres = obj.getString("NODO_TRES");

                String nodoDth = obj.getString("NODO_DTH");
                String nodoMovil = obj.getString("NODO_MOVIL");
                String nodoFtth = obj.getString("NODO_FTTH");
                String nodoWifi = obj.getString("NODO_WIFI");
                //FIN Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1
                Ubicacion ubi_dir = new Ubicacion();
                ubi_dir.setUbiId(id_ubi);
                dirRepositorio = new Direccion();
                dirRepositorio.setDirId(dir_id);

                //Inicio Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1
                if (nodoUno != null && nodoUno.equals("")) {
                    dirRepositorio.setNodoUno(nodoUno);
                }
                if (nodoDos != null && nodoDos.equals("")) {
                    dirRepositorio.setNodoDos(nodoDos);
                }
                if (nodoTres != null && nodoTres.equals("")) {
                    dirRepositorio.setNodoTres(nodoTres);
                }
                //FIN Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1

                if (nodoDth != null && nodoDth.equals("")) {
                    dirRepositorio.setNodoDth(nodoDth);
                }
                if (nodoMovil != null && nodoMovil.trim().isEmpty()
                        && !nodoMovil.equalsIgnoreCase("NA")) {
                    dirRepositorio.setNodoMovil(nodoMovil);
                }
                if (nodoFtth != null && nodoFtth.trim().isEmpty()
                        && !nodoFtth.equalsIgnoreCase("NA")) {
                    dirRepositorio.setNodoFtth(nodoFtth);
                }
                if (nodoWifi != null && nodoWifi.trim().isEmpty()
                        && !nodoWifi.equalsIgnoreCase("NA")) {
                    dirRepositorio.setNodoWifi(nodoWifi);
                }

                if (id_ubi != null) {
                    dirRepositorio.setUbicacion(ubi_dir);

                    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-09-30">
                    AccessData adbUbi = ManageAccess.createAccessData();
                    DataObject objUbi = adbUbi.outDataObjec("getUbiCxCyById", id_ubi.toBigInteger().toString());
                    adbUbi.clear();
                    String ubiCx = objUbi.getString("CX");
                    String ubiCy = objUbi.getString("CY");
                    if (ubiCx != null && !ubiCx.isEmpty() && ubiCy != null && !ubiCy.isEmpty()) {
                        dirRepositorio.setCx(ubiCx);
                        dirRepositorio.setCy(ubiCy);
                    }
                    // </editor-fold>
                }
                dirRepositorio.setDirFormatoIgac(dir_igac);
                dirRepositorio.setDirRevisar(dir_revisar);
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryAddressIntraducibleOnRepoByIgac. EX000 " + e.getMessage(), e);
        }
        return dirRepositorio;
    }

    /**
     * Consulta en repositorio dirección por su direccion no estandarizada
     *
     * @param dirNoEstandar
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Direccion queryAddressOnRepoByDirNoStand(String dirNoEstandar) throws ApplicationException {
        Direccion dirRepositorio = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dir11", dirNoEstandar);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                Ubicacion ubi_dir = null;
                TipoDireccion tipo_dir = null;
                dirRepositorio = new Direccion();
                dirRepositorio.setDirId(obj.getBigDecimal("DIRECCION_ID"));
                BigDecimal tdi_id = obj.getBigDecimal("TIPO_DIRECCION_ID");
                BigDecimal id_ubi = obj.getBigDecimal("UBICACION_ID");
                dirRepositorio.setDirFormatoIgac(obj.getString("FORMATO_IGAC"));
                dirRepositorio.setDirServinformacion(obj.getString("SERV_INFORMACION"));
                dirRepositorio.setDirNostandar(obj.getString("NO_STANDAR"));
                dirRepositorio.setDirOrigen(obj.getString("ORIGEN"));
                dirRepositorio.setDirConfiabilidad(obj.getBigDecimal("CONFIABILIDAD"));
                dirRepositorio.setDirEstrato(obj.getBigDecimal("ESTRATO"));
                dirRepositorio.setDirNivelSocioecono(obj.getBigDecimal("NIVEL_SOCIOECONOMICO"));
                dirRepositorio.setDirComentarioNivelSocioEconomico(obj.getString("COMENTARIO_SOCIOECONOMICO"));//Cambio Carlos villamil Fase i direcciones //2012-12-14
                dirRepositorio.setDirManzanaCatastral(obj.getString("MANZANA_CATASTRAL"));
                dirRepositorio.setDirManzana(obj.getString("MANZANA"));
                dirRepositorio.setDirRevisar(obj.getString("REVISAR_DIRECCION"));
                dirRepositorio.setDirActividadEconomica(obj.getString("ACTIVIDAD_ECONOMICA"));

                if (tdi_id != null) {
                    tipo_dir = new TipoDireccion();
                    tipo_dir.setTdiId(tdi_id);
                }
                dirRepositorio.setTipoDireccion(tipo_dir);
                if (id_ubi != null) {
                    ubi_dir = new Ubicacion();
                    ubi_dir.setUbiId(id_ubi);
                }
                dirRepositorio.setUbicacion(ubi_dir);
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryAddressOnRepoByDirNoStand. EX000 " + e.getMessage(), e);
        }
        return dirRepositorio;
    }

    /**
     * @param codDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public SubDireccion querySubAddressOnRepoByCod(String codDireccion, BigDecimal centroPobladoId) throws ApplicationException {
        SubDireccion subDir = null;
        AccessData adb = null;
        AccessData adbUbi = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("sdi4", codDireccion, centroPobladoId);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                BigDecimal subDir_id = obj.getBigDecimal("SUB_DIRECCION_ID");
                BigDecimal dir_id = obj.getBigDecimal("DIRECCION_ID");
                String subDir_igac = obj.getString("FORMATO_IGAC");
                String subDir_comSo = obj.getString("COMENTARIO_SOCIOECONOMICO");//Modificado Carlos Villamil Fase I direcciones 2012-12-18
                String subDir_servi = obj.getString("SERVINFORMACION");
                BigDecimal estrato = obj.getBigDecimal("ESTRATO");
                BigDecimal nvlSocio = obj.getBigDecimal("NIVEL_SOCIOECONO");
                BigDecimal actEcono = obj.getBigDecimal("ACTIVIDAD_ECONO");
                //Inicio Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1
                String nodoUno = obj.getString("NODOUNO");
                String nodoDos = obj.getString("NODODOS");
                String nodoTres = obj.getString("NODOTRES");
                //FIN Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1

                String nodoDth = obj.getString("NODODTH");
                String nodoMovil = obj.getString("NODOMOVIL");
                String nodoFtth = obj.getString("NODOFTTH");
                String nodoWifi = obj.getString("NODOWIFI");
                subDir = new SubDireccion();
                subDir.setSdiId(subDir_id);
                Direccion direccion = new Direccion();
                direccion.setDirId(dir_id);
                subDir.setDireccion(direccion);
                subDir.setSdiFormatoIgac(subDir_igac);
                subDir.setSdiComentarioNivelSocioeconomico(subDir_comSo);//Modificado Carlos Villamil Fase I direcciones 2012-12-18
                subDir.setSdiServinformacion(subDir_servi);
                subDir.setSdiEstrato(estrato);
                subDir.setSdiNivelSocioecono(nvlSocio);
                subDir.setSdiActividadEcono(actEcono);
                //Inicio Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1
                if (nodoUno != null && !nodoUno.equals("")) {
                    subDir.setNodoUno(nodoUno);
                }
                if (nodoDos != null && !nodoDos.equals("")) {
                    subDir.setNodoDos(nodoDos);
                }
                if (nodoTres != null && !nodoTres.equals("")) {
                    subDir.setNodoTres(nodoTres);
                }
                //FIN Cambio carlos villamil direcciones fase I 2013-05-23 V 1.1

                if (nodoDth != null && !nodoDth.equals("")) {
                    subDir.setNodoDth(nodoDth);
                }
                if (nodoMovil != null && nodoMovil.trim().isEmpty()
                        && !nodoMovil.equalsIgnoreCase("NA")) {
                    subDir.setNodoMovil(nodoMovil);
                }
                if (nodoFtth != null && nodoFtth.trim().isEmpty()
                        && !nodoFtth.equalsIgnoreCase("NA")) {
                    subDir.setNodoFtth(nodoFtth);
                }
                if (nodoWifi != null && nodoWifi.trim().isEmpty()
                        && !nodoWifi.equalsIgnoreCase("NA")) {
                    subDir.setNodoWifi(nodoWifi);
                }
                // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-10-01">
                /*
                 * si encontramos la subdireccion, debemos buscar la direcion asociada
                 * y asi su ubicacion para obtener la ubucicion Cx y Cy
                 */
                if (dir_id != null) {
                    adbUbi = ManageAccess.createAccessData();
                    DataObject objUbi = adbUbi.outDataObjec("getUbiCxCyDirById", dir_id.toBigInteger().toString());
                    adbUbi.clear();
                    String ubiCx = objUbi.getString("CX");
                    String ubiCy = objUbi.getString("CY");
                    if (ubiCx != null && !ubiCx.isEmpty() && ubiCy != null && !ubiCy.isEmpty()) {
                        subDir.setCx(ubiCx);
                        subDir.setCy(ubiCy);
                    }
                }
                // </editor-fold>

            }
        } catch (ExceptionDB e) {
            try {
                LOGGER.error(e.getMessage());
                DireccionUtil.closeConnection(adb);
                adbUbi.clear();
                throw new ApplicationException("Error en querySubAddressOnRepoByCod. EX000 " + e.getMessage(), e);
            } catch (ExceptionDB ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException("Error en querySubAddressOnRepoByCod. EX000 " + ex.getMessage(), ex);
            }
        }
        return subDir;
    }

    /**
     *
     * @param address
     * @return
     */
    private String queryCategoryAddress(String address) {
        return "I";
    }

    /**
     *
     * @param address
     * @return
     */
    public String getAppletFromAddress(String address) {
        String applet = "";
        if (address != null && address.length() == 99) {
            try {
                applet = "MZ" + address.substring(21, 27);
            } catch (NumberFormatException e) {
                applet = "";
            }
        }
        return applet;
    }

    /**
     * Evalua las diferentes condiciones para realizar las recomendaciones
     * necesarias
     *
     * @param address
     * @param idOnRepository
     * @param fuente
     * @return
     */
    private String evaluateRecommendations(String address, String idOnRepository, String fuente, String ambigua, String dirAlterna, String revisarEnCampo) {
        String recomendation = "";
        //Si la dir Existe en el repositorio y proviene de una fuente NUEVA, se debe actualizar
        if (!"0".equals(idOnRepository) && idOnRepository != null) {
            recomendation += DIR_EXISTE_EN_EL_REPOSITORIO;
            if ("1".equals(revisarEnCampo)) {
                recomendation += DIR_RESIVAR_EN_CAMPO;
            }
        } else {
            recomendation += DIR_NO_EXISTE_EN_EL_REPOSITORIO;
        }
        //Determina la ambiguedad de una direccion
        if (ambigua.equals("1")) {
            recomendation += DIR_ES_AMBIGUA;
        }
        //Si la direccion pertenece a la malla antigua y existe una direccion alterna en la malla nueva
        if (Constant.TIPO_MALLA_ANTIGUA.equals(fuente) && !"".equals(dirAlterna)) {
            recomendation += DIR_ES_ANTIGUA_SE_DEBE_ACTUALIZAR;
        }
        return recomendation;
    }

    /**
     *
     * @param address
     * @param state
     * @param valComplemento
     * @param codAddressPlaca
     * @return
     */
    private boolean validExistAddress(String address, String state, String valComplemento, String codAddressPlaca, BigDecimal centroPobladoId) {
        boolean exist = false;
        if ("".equals(address) || "".equals(valComplemento)) {
            exist = false;
        } else {
            if (("B".equals(state) || "D".equals(state) || "F".equals(state) || "L".equals(state) || "M".equals(state) || "N".equals(state) || "I".equals(state) || "Y".equals(state))
                    && (!"".equals(valComplemento) && Integer.valueOf(valComplemento) >= 95)) {
                exist = true;
            } else {
                try {
                    Direccion direccion = queryAddressOnRepository(address,centroPobladoId);
                    if (direccion == null) {
                        exist = false;
                    } else {
                        if ("0".equals(direccion.getDirRevisar())) {
                            exist = true;
                        } else {
                            exist = false;
                        }
                    }
                } catch (ApplicationException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                    LOGGER.error(msg);
                }
            }
        }
        return exist;
    }

    /**
     *
     * @param address
     * @param state
     * @param valComplemento
     * @return
     */
    private boolean validExistAddress(String address, String state, String valComplemento) {
        boolean exist;
        if ("".equals(address) || "".equals(valComplemento)) {
            exist = false;
        } else {
            if (("B".equals(state) || "D".equals(state) || "F".equals(state) || "L".equals(state)
                    || "M".equals(state) || "N".equals(state) || "I".equals(state) || "Y".equals(state))
                    && (!"".equals(valComplemento) && Integer.valueOf(valComplemento) >= 95)) {
                exist = true;
            } else {
                exist = false;
            }
        }
        return exist;
    }

    /**
     *
     * @param address
     * @param state
     * @return
     */
    private boolean validTraslateAdrress(String address, String state) {
        boolean traduct;
        if ("".equals(address)) {
            traduct = false;
        } else {
            if ("A".equals(state) || "B".equals(state) || "D".equals(state) || "E".equals(state) || "F".equals(state) || "G".equals(state) || "I".equals(state) || "J".equals(state) || "L".equals(state) || "M".equals(state) || "N".equals(state) || "I".equals(state) || "Z".equals(state) || "Y".equals(state)) {
                traduct = true;
            } else {
                traduct = false;
            }
        }
        return traduct;
    }

    /**
     *
     * @param diferencia
     * @return
     */
    private static boolean isSoloCeros(String diferencia) {
        for (int i = 0; i < diferencia.length(); i++) {
            if (diferencia.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    private AddressGeodata dummyServicioEnriquecerXML() {
        AddressGeodata geodataResp = new AddressGeodata();
        
        geodataResp.setAmbigua("dumy_ambigua");
        geodataResp.setBarrio("dumy_barrio");
        geodataResp.setBartrad("dumy_bar_traducido");
        geodataResp.setCoddir("dumy_cod_Direccion");
        geodataResp.setCodencont("dumy_codecont");
        geodataResp.setCx("dumy_cx");
        geodataResp.setCy("dumy_cy");
        geodataResp.setDiralterna("dumy_dir_alterna");
        geodataResp.setDirtrad("dumy_dir_traducida");
        geodataResp.setEstado("dumy_estado");
        geodataResp.setEstrato("dumy_estrato");
        geodataResp.setFuente("dumy_Fuente");
        geodataResp.setIdentificador("dumy_Identificador");
        geodataResp.setLocalidad("dumy_Localidad");
        geodataResp.setMensaje("dumy_Mensaje");
        geodataResp.setNivsocio("dumy_Nivel_SocioEconomico");
        geodataResp.setNodo1("dumy_Nodo1");
        geodataResp.setNodo2("dumy_Nodo2");
        geodataResp.setNodo3("dumy_Nodo3");
        geodataResp.setNodo4("dumy_Nodo4");
        geodataResp.setValplaca("dumy_Val_Placa");
        geodataResp.setValagreg("dumy_Val_agregado");
        geodataResp.setZona("dumy_Zona");

        return geodataResp;
    }

    /**
     * Crea una dirección en el repositorio
     *
     * @param request
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    @Override
    public ResponseMessage createAddress(AddressRequest request, String user,
            String nombreFuncionalidad, String validate) throws ApplicationException {
        ResponseMessage respMesg = new ResponseMessage();
        AddressGeodata geodata = null;
        String neighborhood = null;

        if ("".equals(validate)) {
            validate = "TRUE";
        }
        
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPoblado = null;
        //JDHT
        if (request.getCodDaneVt() != null && !request.getCodDaneVt().isEmpty()) {
            centroPoblado = geograficoPoliticoManager.findCentroPobladoCodDane(request.getCodDaneVt());
        }


        //Se valida la obligatoriedad del barrio (Ciudades multiorigen)
        boolean multiorigen;
        try {
            if (centroPoblado != null && centroPoblado.getGpoMultiorigen() != null && centroPoblado.getGpoMultiorigen() != null) {
                multiorigen = centroPoblado.getGpoMultiorigen().equalsIgnoreCase("1") ? true : false;
            } else {
                multiorigen = queryCiudadMultiorigen(request.getCity());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_CIUDAD_NO_EXISTE);
            return respMesg;
        }
        if (request.getNeighborhood() != null && !request.getNeighborhood().isEmpty()) {
            neighborhood = request.getNeighborhood();
        }
        //Se valida la obligatoriedad del barrio (Ciudades multiorigen)
        if ((multiorigen) && (neighborhood == null || neighborhood.isEmpty())) {
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_BARRIO_OBLIGATORIO);
            return respMesg;
        }

        //Se consultan los atributos de la direccion consumiendo el servicio
        try {
            geodata = queryAddressGeodata(request);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(e.getMessage());
        }

        if (geodata != null) {
            String nodoUno = geodata.getNodo1() == null ? "" : geodata.getNodo1();
            geodata.setNodo1(estandarizaNodo(nodoUno));
            String nodoDos = geodata.getNodo2() == null ? "" : geodata.getNodo2();
            geodata.setNodo2(estandarizaNodo(nodoDos));
            String nodoTres = geodata.getNodo3() == null ? "" : geodata.getNodo3();
            geodata.setNodo3(estandarizaNodo(nodoTres));
            String nodoCuatro = geodata.getNodo4() == null ? "" : geodata.getNodo4();
            geodata.setNodo4(estandarizaNodo(nodoCuatro));


            String nodoDth = geodata.getNodoDth() == null ? "" : geodata.getNodoDth();
            geodata.setNodoDth(estandarizaNodo(nodoDth));
            String nodoMovil = geodata.getNodoMovil() == null ? "" : geodata.getNodoMovil();
            geodata.setNodoMovil(estandarizaNodo(nodoMovil));
            String nodoFtth = geodata.getNodoFtth() == null ? "" : geodata.getNodoFtth();
            geodata.setNodoFtth(estandarizaNodo(nodoFtth));
            String nodoWifi = geodata.getNodoWifi() == null ? "" : geodata.getNodoWifi();
            geodata.setNodoWifi(estandarizaNodo(nodoWifi));

//Inicio cambio 1.6            
            String geoZona3G = geodata.getGeoZona3G()
                    == null ? "" : geodata.getGeoZona3G();
            geodata.setGeoZona3G(estandarizaNodo(geoZona3G));
            String geoZona4G = geodata.getGeoZona4G()
                    == null ? "" : geodata.getGeoZona4G();
            geodata.setGeoZona4G(estandarizaNodo(geoZona4G));
            String geoZona5G = geodata.getGeoZona5G()
                    == null ? "" : geodata.getGeoZona5G();
            geodata.setGeoZona5G(estandarizaNodo(geoZona5G));
            String geoZonaCoberturaCavs = geodata.getGeoZonaCoberturaCavs()
                    == null ? "" : geodata.getGeoZonaCoberturaCavs();
            geodata.setGeoZonaCoberturaCavs(estandarizaNodo(geoZonaCoberturaCavs));
            String geoZonaCoberturaUltimaMilla = geodata.getGeoZonaCoberturaUltimaMilla()
                    == null ? "" : geodata.getGeoZonaCoberturaUltimaMilla();
            geodata.setGeoZonaCoberturaUltimaMilla(estandarizaNodo(geoZonaCoberturaUltimaMilla));
            String geoZonaCurrier = geodata.getGeoZonaCurrier()
                    == null ? "" : geodata.getGeoZonaCurrier();
            geodata.setGeoZonaCurrier(estandarizaNodo(geoZonaCurrier));
            String geoZonaGponDiseniado = geodata.getGeoZonaGponDiseniado()
                    == null ? "" : geodata.getGeoZonaGponDiseniado();
            geodata.setGeoZonaGponDiseniado(estandarizaNodo(geoZonaGponDiseniado));
            String geoZonaMicroOndas = geodata.getGeoZonaMicroOndas()
                    == null ? "" : geodata.getGeoZonaMicroOndas();
            geodata.setGeoZonaMicroOndas(estandarizaNodo(geoZonaMicroOndas));
            String geoZonaUnifilar = geodata.getGeoZonaUnifilar()
                    == null ? "" : geodata.getGeoZonaUnifilar();
            geodata.setGeoZonaUnifilar(estandarizaNodo(geoZonaUnifilar));
//FIN cambio 1.6            

            if (Constant.DIR_ESTADO_INTRADUCIBLE.equals(geodata.getEstado())) {
                Direccion dirIntrad;
                if (geodata.getDirtrad() == null || geodata.getDirtrad().isEmpty()) {
                    dirIntrad = validarExistenciaDirIntraducibleOnRepo(request.getAddress(), request.getCodDaneVt(), respMesg);
                } else {
                    dirIntrad = validarExistenciaDirIntraducibleOnRepo(geodata.getDirtrad(), request.getCodDaneVt(), respMesg);
                }
                if (dirIntrad != null && dirIntrad.getDirId() != null && !"0".equals(dirIntrad.getDirId().toString())) {
                    //la direccion ya existe y no se puede guardar
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                    respMesg.setIdaddress("d" + dirIntrad.getDirId().toString());
                    return respMesg;
                } else {
                    //Se debe crear la direccion y almacenar con ubicacion 0,0
                    if (validate.equalsIgnoreCase("TRUE")) {
                        guardarDireccion(geodata, request, respMesg, user, nombreFuncionalidad);
                    } else {
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR);
                    }
                }
            } else {
                AddressGeodata geodataEnOtraMalla = null;
                AddressRequest requestEnOtraMalla = new AddressRequest();

                if (!geodata.getDiralterna().isEmpty()) {
                    requestEnOtraMalla.setState(request.getState());
                    requestEnOtraMalla.setCity(request.getCity());
                    requestEnOtraMalla.setNeighborhood(request.getNeighborhood());
                    requestEnOtraMalla.setAddress(geodata.getDiralterna());
                    requestEnOtraMalla.setLevel(request.getLevel());

                    //Se consulta los atributos de la direccion alterna en la otra malla
                    try {
                        geodataEnOtraMalla = queryAddressGeodata(requestEnOtraMalla);
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                        LOGGER.error(msg);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(e.getMessage());
                    }
                }
                //Si la direccion no existe y si el flag esta en FALSE, no se puede guardar
                if ((!validExistAddress(geodata.getDirtrad(), geodata.getEstado(), geodata.getValplaca())) && (validate.toUpperCase().equals("FALSE"))) {
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR);
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    return respMesg;
                }
                /*espinosadiea: este medoto es temporal mientras se termina el desarrollo completo de direcciones ya que su impacto es en muchas partes.*/
                String tipoDireccionConsultada = validarExistenciaDireccionOnRepo(geodata, geodataEnOtraMalla, respMesg, centroPoblado.getGpoId());
                String subDireccionNoExiste = Constant.DIRECCION + Constant.SUB_DIRECCION;

                if (Constant.DIR_SI_EXISTE_MSJ.equals(tipoDireccionConsultada)) {
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    try {
                        AddressService addressService = queryAddress(request);
                        respMesg.setIdaddress(addressService.getIdaddress());
                    } catch (ApplicationException ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                        LOGGER.error(msg);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(ex.getMessage());
                    }
                    return respMesg;
                } else if (DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO.equals(tipoDireccionConsultada)) {
                    if (geodataEnOtraMalla.getFuente().equals(Constant.DIR_FUENTE_ANTIGUA)) {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        return respMesg;
                    } else if (geodataEnOtraMalla.getFuente().equals(Constant.DIR_FUENTE_NUEVA)) {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_NUEVA);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        return respMesg;
                    }
                } //La direccion no EXISTE, se debe validar almacenarla en DIRECCION o SUBDIRECCION
                else if (Constant.DIRECCION.equals(tipoDireccionConsultada)) {
                    guardarDireccion(geodata, request, respMesg, user, nombreFuncionalidad);
                } else if (Constant.SUB_DIRECCION.equals(tipoDireccionConsultada)) {
                    guardarSubDireccion(geodata, geodata, respMesg, user, nombreFuncionalidad, null);
                } //Si es una subdireccion y tampoco existe la direccion
                else if ((subDireccionNoExiste).equals(tipoDireccionConsultada)) {
                    guardarDireccion(geodata, request, respMesg, user, nombreFuncionalidad);
                    guardarSubDireccion(geodata, geodata, respMesg, user, nombreFuncionalidad, null);
                } else {
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    return respMesg;
                }
            }//Fin de direccion TRADUCIBLE
            //Se almacena como dirección
        } else if (geodata == null) {
            respMesg = new ResponseMessage();
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_FUERA_SERVICIO);
        }
        return respMesg;
    }

/**
     * Crea una dirección en el repositorio
     *
     * @param request
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @param dirNueva
     * @return
     * @throws ExceptionDB
     */
    @Override
    public ResponseMessage createAddress(AddressRequest request, String user,
            String nombreFuncionalidad, String validate, DrDireccion dirNueva) throws ExceptionDB {
        dirNueva.setUsuarioCreacion(user);
        ResponseMessage respMesg = new ResponseMessage();
        AddressGeodata geodataDirecci = null;
        AddressGeodata geodataSubDireccion = null;
        boolean infoBarioValidaDir = true;// espinosadiea informacion devuelta por el servicio de geodata valida para ciudades multiorigen
        boolean infoBarioValidaSubDir = true;// espinosadiea informacion devuelta por el servicio de geodata valida para ciudades multiorigen
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPoblado = null;
        DrDireccionManager adminDireccion = new DrDireccionManager();

        //SE ELIMINAN ESPACIO EN BLANCO DE LOS COMPLEMENTOS
        dirNueva.setCpTipoNivel1(dirNueva.getCpTipoNivel1() != null ? dirNueva.getCpTipoNivel1().trim() : null);
        dirNueva.setCpTipoNivel2(dirNueva.getCpTipoNivel2() != null ? dirNueva.getCpTipoNivel2().trim() : null);
        dirNueva.setCpTipoNivel3(dirNueva.getCpTipoNivel3() != null ? dirNueva.getCpTipoNivel3().trim() : null);
        dirNueva.setCpTipoNivel4(dirNueva.getCpTipoNivel4() != null ? dirNueva.getCpTipoNivel4().trim() : null);
        dirNueva.setCpTipoNivel5(dirNueva.getCpTipoNivel5() != null ? dirNueva.getCpTipoNivel5().trim() : null);
        dirNueva.setCpTipoNivel6(dirNueva.getCpTipoNivel6() != null ? dirNueva.getCpTipoNivel6().trim() : null);

        dirNueva.setCpValorNivel1(dirNueva.getCpValorNivel1() != null ? dirNueva.getCpValorNivel1().trim() : null);
        dirNueva.setCpValorNivel2(dirNueva.getCpValorNivel2() != null ? dirNueva.getCpValorNivel2().trim() : null);
        dirNueva.setCpValorNivel3(dirNueva.getCpValorNivel3() != null ? dirNueva.getCpValorNivel3().trim() : null);
        dirNueva.setCpValorNivel4(dirNueva.getCpValorNivel4() != null ? dirNueva.getCpValorNivel4().trim() : null);
        dirNueva.setCpValorNivel5(dirNueva.getCpValorNivel5() != null ? dirNueva.getCpValorNivel5().trim() : null);
        dirNueva.setCpValorNivel6(dirNueva.getCpValorNivel6() != null ? dirNueva.getCpValorNivel6().trim() : null);

         
        //JDHT
        if (request.getCodDaneVt() != null && !request.getCodDaneVt().isEmpty()) {
            centroPoblado = geograficoPoliticoManager.findCentroPobladoCodDane(request.getCodDaneVt());
        }

        if ("".equals(validate)) {
            validate = "TRUE";
        }
        //Se valida la obligatoriedad del barrio (Ciudades multiorigen)
        boolean multiorigen;
        try {
            if (centroPoblado != null && centroPoblado.getGpoMultiorigen() != null && centroPoblado.getGpoMultiorigen() != null) {
                multiorigen =  centroPoblado.getGpoMultiorigen().equalsIgnoreCase("1") ? true : false;
            } else {
                multiorigen = queryCiudadMultiorigen(request.getCity());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_CIUDAD_NO_EXISTE);
            return respMesg;
        }
        
        if (multiorigen) {
            dirNueva.setMultiOrigen("1");
            if(dirNueva.getIdTipoDireccion().equalsIgnoreCase("BM")){
                request.setNeighborhood("");
            }
        } else {
            dirNueva.setMultiOrigen("0");
            //se elimina el barrio para CK y BM si no es multiorigen
            if (!dirNueva.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                request.setNeighborhood("");                
            }
        }
        
        //Se valida la obligatoriedad del barrio (Ciudades multiorigen)
        if (dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK") && multiorigen 
                && (request.getNeighborhood() == null || request.getNeighborhood().isEmpty())) {
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_BARRIO_OBLIGATORIO);
            return respMesg;
        }
        //Se consultan los atributos de la direccion consumiendo el servicio
        try {
            boolean esSubDireccion = !direccionCampo5(dirNueva);
           
            //Debe llegar IT, y recrear el objeto como CK, y seguir como IT.
            //Si es IT, check Calle - Carrera, se debe comportar como CK para la construcción del Address en la subDireccion y Dirección
            if (dirNueva.getIdTipoDireccion() != null
                    && dirNueva.getPlacaDireccion() != null
                    && !dirNueva.getPlacaDireccion().isEmpty()
                    && dirNueva.getIdTipoDireccion().equalsIgnoreCase("IT")
                    && (dirNueva.getPlacaDireccion().length() > 3
                    || (dirNueva.getItTipoPlaca() != null
                    && (dirNueva.getItTipoPlaca().equalsIgnoreCase("CONTADOR") || dirNueva.getItTipoPlaca().equalsIgnoreCase("CD")))
                    || (dirNueva.getTipoViaGeneradora() != null
                    && dirNueva.getTipoViaGeneradora().equalsIgnoreCase("KDX")))) {
                dirNueva.setIdTipoDireccion("CK");                
                request.setAddress(adminDireccion.getDireccionSinComplemento(dirNueva));//obtener de drDireccion sin complemento
                geodataDirecci = queryAddressGeodata(request);
                geodataDirecci.setDirtrad(geodataDirecci.getDirtrad());
                geodataDirecci.setAmbigua("0");
                geodataDirecci.setEstado("R");
                geodataDirecci.setMensaje(null);

                //si geodata contesta que la direccion es intraducible y el tipo de direccion es de
                //CK o BM debe mostrar error de busqueda.
                if (geodataDirecci != null) {
                    if (esSubDireccion) {
                        AddressRequest requestSubDireccion = new AddressRequest();
                        requestSubDireccion.setId(request.getId());
                        requestSubDireccion.setAddress(adminDireccion.getDireccion(dirNueva));//obtener de drDireccion con complemento
                        requestSubDireccion.setCity(request.getCity());                      
                        requestSubDireccion.setNeighborhood(request.getNeighborhood());
                        requestSubDireccion.setState(request.getState());
                        requestSubDireccion.setLevel("C");
                        requestSubDireccion.setCodDaneVt(request.getCodDaneVt());
                        geodataSubDireccion = queryAddressGeodata(requestSubDireccion);
              
                    }
                }
                
                dirNueva.setIdTipoDireccion("IT");
            } else {               
  
                DrDireccion DrDireccionPrincipalSinComplemento = new DrDireccion();
                DrDireccionPrincipalSinComplemento = dirNueva.clone();    
                
                DrDireccionPrincipalSinComplemento.setCpTipoNivel1(null);
                DrDireccionPrincipalSinComplemento.setCpTipoNivel2(null);                
                DrDireccionPrincipalSinComplemento.setCpTipoNivel3(null);
                DrDireccionPrincipalSinComplemento.setCpTipoNivel4(null);
                
                DrDireccionPrincipalSinComplemento.setCpValorNivel1(null);
                DrDireccionPrincipalSinComplemento.setCpValorNivel2(null);
                DrDireccionPrincipalSinComplemento.setCpValorNivel3(null);
                DrDireccionPrincipalSinComplemento.setCpValorNivel4(null);
     
                DrDireccionPrincipalSinComplemento.setCpTipoNivel5(null);
                DrDireccionPrincipalSinComplemento.setCpTipoNivel6(null);                
                DrDireccionPrincipalSinComplemento.setCpValorNivel5(null);
                DrDireccionPrincipalSinComplemento.setCpValorNivel6(null);

                String direccionPrincipalOriginal = request.getAddress();
                request.setAddress(adminDireccion.getDireccionSinComplemento(DrDireccionPrincipalSinComplemento));
                geodataDirecci = queryAddressGeodata(request);

                request.setAddress(direccionPrincipalOriginal);
                DrDireccionPrincipalSinComplemento = null;
                //si geodata contesta que la direccion es intraducible y el tipo de direccion es de
                //CK o BM debe mostrar error de busqueda.
                if (geodataDirecci != null) {
                    
                    if (multiorigen && dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                        if (geodataDirecci.getDirtrad().contains(request.getNeighborhood())) {
                             //si la direccion traducida tiene el barrio no se hace nada
                        }else{
                            //si la direccion traducida no tiene el barrio en la direccion traducida se le concatena por ser multiorigen
                            geodataDirecci.setDirtrad(geodataDirecci.getDirtrad() + " " + request.getNeighborhood().toUpperCase());
                        }
                    }
                    
                    if (esSubDireccion) {
                        AddressRequest requestSubDireccion = new AddressRequest();
                        requestSubDireccion.setId(request.getId());
                        requestSubDireccion.setAddress(adminDireccion.getDireccion(dirNueva));//obtener de drDireccion con complemento
                        requestSubDireccion.setCity(request.getCity());
                        requestSubDireccion.setNeighborhood(request.getNeighborhood());
                        requestSubDireccion.setState(request.getState());
                        requestSubDireccion.setLevel("C");
                        requestSubDireccion.setCodDaneVt(request.getCodDaneVt());
                        geodataSubDireccion = queryAddressGeodata(requestSubDireccion);
                    }
                }
            }
            boolean creaDireccion = (geodataDirecci != null) && ((esSubDireccion && geodataSubDireccion != null)
                    || (!esSubDireccion && geodataSubDireccion == null));
            if (creaDireccion) {
                if (multiorigen && dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    geodataDirecci.setCodencont(geodataDirecci.getCodencont() + "|" + request.getNeighborhood().trim().toUpperCase());
                    if (esSubDireccion) {
                        geodataSubDireccion.setCoddir(geodataSubDireccion.getCoddir() + "|" + request.getNeighborhood().trim().toUpperCase());
                    }
                }
                if (!esSubDireccion) {//para tener referencia en los metodos de abajo cuando no exista subdireccion
                    geodataSubDireccion = geodataDirecci;
                }
                //espinosadiea ciudad multiorigen pero el barrio que vuelve el geodata no corresponde con el enviado para la creacion
                if (multiorigen && dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK") && geodataDirecci.getBarrio() != null
                        && !geodataDirecci.getBarrio().trim().equalsIgnoreCase(request.getNeighborhood().trim()) ){
                    infoBarioValidaDir = false;// espinosadiea informacion devuelta por el servicio de geodata invalida para ciudades multiorigen
                } else {
                    String nodoUno = geodataDirecci.getNodo1() == null ? "" : geodataDirecci.getNodo1();
                    geodataDirecci.setNodo1(estandarizaNodo(nodoUno));
                    String nodoDos = geodataDirecci.getNodo2() == null ? "" : geodataDirecci.getNodo2();
                    geodataDirecci.setNodo2(estandarizaNodo(nodoDos));
                    String nodoTres = geodataDirecci.getNodo3() == null ? "" : geodataDirecci.getNodo3();
                    geodataDirecci.setNodo3(estandarizaNodo(nodoTres));
                    String nodoCuatro = geodataDirecci.getNodo4() == null ? "" : geodataDirecci.getNodo4();
                    geodataDirecci.setNodo4(estandarizaNodo(nodoCuatro));


                    String nodoDth = geodataDirecci.getNodoDth() == null ? "" : geodataDirecci.getNodoDth();
                    geodataDirecci.setNodoDth(estandarizaNodo(nodoDth));
                    String nodoMovil = geodataDirecci.getNodoMovil() == null ? "" : geodataDirecci.getNodoMovil();
                    geodataDirecci.setNodoMovil(estandarizaNodo(nodoMovil));
                    String nodoFtth = geodataDirecci.getNodoFtth() == null ? "" : geodataDirecci.getNodoFtth();
                    geodataDirecci.setNodoFtth(estandarizaNodo(nodoFtth));
                    String nodoWifi = geodataDirecci.getNodoWifi() == null ? "" : geodataDirecci.getNodoWifi();
                    geodataDirecci.setNodoWifi(estandarizaNodo(nodoWifi));
                    //Inicio cambio 1.6            
                    String geoZona3G = geodataDirecci.getGeoZona3G()
                            == null ? "" : geodataDirecci.getGeoZona3G();
                    geodataDirecci.setGeoZona3G(estandarizaNodo(geoZona3G));
                    String geoZona4G = geodataDirecci.getGeoZona4G()
                            == null ? "" : geodataDirecci.getGeoZona4G();
                    geodataDirecci.setGeoZona4G(estandarizaNodo(geoZona4G));
                    String geoZona5G = geodataDirecci.getGeoZona5G()
                            == null ? "" : geodataDirecci.getGeoZona5G();
                    geodataDirecci.setGeoZona5G(estandarizaNodo(geoZona5G));
                    String geoZonaCoberturaCavs = geodataDirecci.getGeoZonaCoberturaCavs()
                            == null ? "" : geodataDirecci.getGeoZonaCoberturaCavs();
                    geodataDirecci.setGeoZonaCoberturaCavs(estandarizaNodo(geoZonaCoberturaCavs));
                    String geoZonaCoberturaUltimaMilla = geodataDirecci.getGeoZonaCoberturaUltimaMilla()
                            == null ? "" : geodataDirecci.getGeoZonaCoberturaUltimaMilla();
                    geodataDirecci.setGeoZonaCoberturaUltimaMilla(estandarizaNodo(geoZonaCoberturaUltimaMilla));
                    String geoZonaCurrier = geodataDirecci.getGeoZonaCurrier()
                            == null ? "" : geodataDirecci.getGeoZonaCurrier();
                    geodataDirecci.setGeoZonaCurrier(estandarizaNodo(geoZonaCurrier));
                    String geoZonaGponDiseniado = geodataDirecci.getGeoZonaGponDiseniado()
                            == null ? "" : geodataDirecci.getGeoZonaGponDiseniado();
                    geodataDirecci.setGeoZonaGponDiseniado(estandarizaNodo(geoZonaGponDiseniado));
                    String geoZonaMicroOndas = geodataDirecci.getGeoZonaMicroOndas()
                            == null ? "" : geodataDirecci.getGeoZonaMicroOndas();
                    geodataDirecci.setGeoZonaMicroOndas(estandarizaNodo(geoZonaMicroOndas));
                    String geoZonaUnifilar = geodataDirecci.getGeoZonaUnifilar()
                            == null ? "" : geodataDirecci.getGeoZonaUnifilar();
                    geodataDirecci.setGeoZonaUnifilar(estandarizaNodo(geoZonaUnifilar));
                    //FIN cambio 1.6            

                }
                if (geodataSubDireccion != null && esSubDireccion) {
                    //espinosadiea ciudad multiorigen pero el barrio que vuelve el geodata no corresponde con el enviado para la creacion
                    if (multiorigen && dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK") && geodataSubDireccion.getBarrio() != null
                            && !geodataSubDireccion.getBarrio().trim().equalsIgnoreCase(request.getNeighborhood().trim())) {
                        infoBarioValidaSubDir = false;// espinosadiea informacion devuelta por el servicio de geodata invalida para ciudades multiorigen
                    } else {
                        String nodoUnoSD = geodataSubDireccion.getNodo1() == null ? "" : geodataSubDireccion.getNodo1();
                        geodataSubDireccion.setNodo1(estandarizaNodo(nodoUnoSD));
                        String nodoDosSD = geodataSubDireccion.getNodo2() == null ? "" : geodataSubDireccion.getNodo2();
                        geodataSubDireccion.setNodo2(estandarizaNodo(nodoDosSD));
                        String nodoTresSD = geodataSubDireccion.getNodo3() == null ? "" : geodataSubDireccion.getNodo3();
                        geodataSubDireccion.setNodo3(estandarizaNodo(nodoTresSD));
                        String nodoCuatroSD = geodataSubDireccion.getNodo4() == null ? "" : geodataSubDireccion.getNodo4();
                        geodataSubDireccion.setNodo4(estandarizaNodo(nodoCuatroSD));


                        String nodoDthSD = geodataSubDireccion.getNodoDth() == null ? "" : geodataSubDireccion.getNodoDth();
                        geodataSubDireccion.setNodoDth(estandarizaNodo(nodoDthSD));
                        String nodoMovilSD = geodataSubDireccion.getNodoMovil() == null ? "" : geodataSubDireccion.getNodoMovil();
                        geodataSubDireccion.setNodoMovil(estandarizaNodo(nodoMovilSD));
                        String nodoFtthSD = geodataSubDireccion.getNodoFtth() == null ? "" : geodataSubDireccion.getNodoFtth();
                        geodataSubDireccion.setNodoFtth(estandarizaNodo(nodoFtthSD));
                        String nodoWifiSD = geodataSubDireccion.getNodoWifi() == null ? "" : geodataSubDireccion.getNodoWifi();
                        geodataSubDireccion.setNodoWifi(estandarizaNodo(nodoWifiSD));
                        //Inicio cambio 1.6            
                        String geoZona3G = geodataSubDireccion.getGeoZona3G()
                                == null ? "" : geodataSubDireccion.getGeoZona3G();
                        geodataSubDireccion.setGeoZona3G(estandarizaNodo(geoZona3G));
                        String geoZona4G = geodataSubDireccion.getGeoZona4G()
                                == null ? "" : geodataSubDireccion.getGeoZona4G();
                        geodataSubDireccion.setGeoZona4G(estandarizaNodo(geoZona4G));
                        String geoZona5G = geodataSubDireccion.getGeoZona5G()
                                == null ? "" : geodataSubDireccion.getGeoZona5G();
                        geodataSubDireccion.setGeoZona5G(estandarizaNodo(geoZona5G));
                        String geoZonaCoberturaCavs = geodataSubDireccion.getGeoZonaCoberturaCavs()
                                == null ? "" : geodataSubDireccion.getGeoZonaCoberturaCavs();
                        geodataSubDireccion.setGeoZonaCoberturaCavs(estandarizaNodo(geoZonaCoberturaCavs));
                        String geoZonaCoberturaUltimaMilla = geodataSubDireccion.getGeoZonaCoberturaUltimaMilla()
                                == null ? "" : geodataSubDireccion.getGeoZonaCoberturaUltimaMilla();
                        geodataSubDireccion.setGeoZonaCoberturaUltimaMilla(estandarizaNodo(geoZonaCoberturaUltimaMilla));
                        String geoZonaCurrier = geodataSubDireccion.getGeoZonaCurrier()
                                == null ? "" : geodataSubDireccion.getGeoZonaCurrier();
                        geodataSubDireccion.setGeoZonaCurrier(estandarizaNodo(geoZonaCurrier));
                        String geoZonaGponDiseniado = geodataSubDireccion.getGeoZonaGponDiseniado()
                                == null ? "" : geodataSubDireccion.getGeoZonaGponDiseniado();
                        geodataSubDireccion.setGeoZonaGponDiseniado(estandarizaNodo(geoZonaGponDiseniado));
                        String geoZonaMicroOndas = geodataSubDireccion.getGeoZonaMicroOndas()
                                == null ? "" : geodataSubDireccion.getGeoZonaMicroOndas();
                        geodataSubDireccion.setGeoZonaMicroOndas(estandarizaNodo(geoZonaMicroOndas));
                        String geoZonaUnifilar = geodataSubDireccion.getGeoZonaUnifilar()
                                == null ? "" : geodataSubDireccion.getGeoZonaUnifilar();
                        geodataSubDireccion.setGeoZonaUnifilar(estandarizaNodo(geoZonaUnifilar));
                        //FIN cambio 1.6            

                    }
                }
                Direccion dir = null;
                SubDireccion subDir = null;

                //valido que la direccion sea intraducible por el codigo de direccion devuelto por el geodata 
                //Si es IT check Calle - Carrera o Si es IT check Calle - Carrera KDX
                if ((geodataDirecci.getCoddir() == null || geodataDirecci.getCoddir().isEmpty()
                        || geodataSubDireccion.getCoddir() == null || geodataSubDireccion.getCoddir().isEmpty())
                        || dirNueva.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                    //Se debe crear la direccion y almacenar con ubicacion 0,0
                    if (validate.toUpperCase().equals("TRUE")) {
                        DrDireccion dirClonNueva = dirNueva.clone();
                        //se clona para quitar el complemento 
                        dirClonNueva.setCpTipoNivel5(null);
                        dirClonNueva.setCpTipoNivel6(null);
                        dirClonNueva.setCpValorNivel5(null);
                        dirClonNueva.setCpValorNivel6(null);
                        if ((geodataDirecci.getDirtrad() == null || geodataDirecci.getDirtrad().isEmpty()) 
                            || dirClonNueva.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                            //Si es IT, check Calle - Carrera, se debe comportar como CK para la construcción del Address en la subDireccion y Dirección
                            //Si es IT check Calle - Carrera KDX, se debe comportar como CK para la construcción del Address en la subDireccion y Dirección
                            if (dirClonNueva.getIdTipoDireccion() != null
                                    && dirClonNueva.getPlacaDireccion() != null
                                    && !dirClonNueva.getPlacaDireccion().isEmpty()
                                    && dirClonNueva.getIdTipoDireccion().equalsIgnoreCase("IT")
                                    && (dirClonNueva.getPlacaDireccion().length() > 3
                                    || (dirClonNueva.getTipoViaGeneradora() != null
                                    && dirClonNueva.getTipoViaGeneradora().equalsIgnoreCase("KDX")))) {
                                dirClonNueva.setIdTipoDireccion("CK");
                                //garantizo que que colocara un valor en el codigo igac de la direccion que sirve para realizar busqueda basado en el drdireccion
                                geodataDirecci.setDirtrad(adminDireccion.getDireccionSinComplemento(dirClonNueva)); //obtiene la direccion sin complemento
                                dirClonNueva.setIdTipoDireccion("IT");
                            } else {
                                //garantizo que que colocara un valor en el codigo igac de la direccion que sirve para realizar busqueda basado en el drdireccion
                                geodataDirecci.setDirtrad(adminDireccion.getDireccionSinComplemento(dirClonNueva)); //obtiene la direccion sin complemento
                            }

                        }
                        dir = validarExistenciaDirIntraducibleOnRepo(geodataDirecci.getDirtrad().trim(), request.getCodDaneVt(), respMesg);
                        if (dir != null && dir.getDirId() != null && !"0".equals(dir.getDirId().toString())) {
                            //la direccion ya existe y no se puede guardar
                            if (!esSubDireccion) {// si la direccion es sin complemento sale del metodo por que existe la direccion
                                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                                respMesg.setIdaddress("d" + dir.getDirId().toString());
                            }
                        } else {
                            dir = guardarDireccion(geodataDirecci, request, respMesg, user, nombreFuncionalidad);
                            if (esSubDireccion) {
                                respMesg.setNuevaDireccionDetallada(cmtDireccionDetalleMglManager
                                        .guardarDireccionDetalleXSolicitud(respMesg.getIdaddress(), dirClonNueva));
                            }
                        }
                        //valida si la direccion intraducible pudo ser traducida por el geodata
                        if (esSubDireccion) {
                            //Si es IT, check Calle - Carrera, se debe comportar como CK para la construcción del Address en la subDireccion y Dirección
                            //Si es IT check Calle - Carrera KDX, se debe comportar como CK para la construcción del Address en la subDireccion y Dirección
                            String subDireccionIntraducible = null;
                            if (dirNueva.getIdTipoDireccion() != null
                                    && dirNueva.getPlacaDireccion() != null
                                    && !dirNueva.getPlacaDireccion().isEmpty()
                                    && dirNueva.getIdTipoDireccion().equalsIgnoreCase("IT")
                                    && (dirNueva.getPlacaDireccion().length() > 3)) {
                                dirNueva.setIdTipoDireccion("CK");
                                subDireccionIntraducible = adminDireccion.getDireccion(dirNueva);//obtiene la direccion con complemento
                                dirNueva.setIdTipoDireccion("IT");
                            }
                            else {
                                subDireccionIntraducible = adminDireccion.getDireccion(dirNueva);//obtiene la direccion con complemento
                            }

                            //funcion busca la direccion en la tabla Mgl_direccion
                            List<CmtDireccionDetalladaMgl> lisSubDir = cmtDireccionDetalleMglManager.buscarDireccionDetalladaTabulada(dirNueva,centroPoblado.getGpoId());
                            if (lisSubDir == null || lisSubDir.isEmpty()) {//no encuentra la direccion
                                subDir = guardarSubDireccionIntraducible(geodataSubDireccion, respMesg, user,
                                        nombreFuncionalidad, dir, subDireccionIntraducible, dirNueva);
                            } else {
                                //la SubDireccion ya existe y no se puede guardar
                                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                                respMesg.setIdaddress("s" + lisSubDir.get(0).getSubDireccion().getSdiId().toString());
                            }
                        }
                    } else {
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR);
                        return respMesg;
                    }
                } else {
                    if (!infoBarioValidaDir) {
                        geodataDirecci.setComentEconomicLevel("");
                        geodataDirecci.setConfiability("");
                        geodataDirecci.setEstrato("");
                        geodataDirecci.setNivsocio("");
                        geodataDirecci.setNodo1("");
                        geodataDirecci.setNodo2("");
                        geodataDirecci.setNodo3("");
                        geodataDirecci.setNodo4("");
                        geodataDirecci.setNodoDth("");
                        geodataDirecci.setNodoFtth("");
                        geodataDirecci.setNodoMovil("");
                        geodataDirecci.setNodoWifi("");
                        geodataDirecci.setGeoZonaUnifilar("");
                        geodataDirecci.setGeoZonaMicroOndas("");
                        geodataDirecci.setGeoZonaGponDiseniado("");
                        geodataDirecci.setGeoZonaCurrier("");
                        geodataDirecci.setGeoZonaCoberturaUltimaMilla("");
                        geodataDirecci.setGeoZonaCoberturaCavs("");
                        geodataDirecci.setGeoZona5G("");
                        geodataDirecci.setGeoZona4G("");
                        geodataDirecci.setGeoZona3G("");
                        geodataDirecci.setZipCode("");
                        geodataDirecci.setZona("");
                        geodataDirecci.setCx("");
                        geodataDirecci.setCy("");
                        
                    }
                    if (!infoBarioValidaSubDir) {
                        geodataSubDireccion.setComentEconomicLevel("");
                        geodataSubDireccion.setConfiability("");
                        geodataSubDireccion.setEstrato("");
                        geodataSubDireccion.setNivsocio("");
                        geodataSubDireccion.setNodo1("");
                        geodataSubDireccion.setNodo2("");
                        geodataSubDireccion.setNodo3("");
                        geodataSubDireccion.setNodoDth("");
                        geodataSubDireccion.setNodoFtth("");
                        geodataSubDireccion.setNodoMovil("");
                        geodataSubDireccion.setNodoWifi("");
                        geodataSubDireccion.setGeoZonaUnifilar("");
                        geodataSubDireccion.setGeoZonaMicroOndas("");
                        geodataSubDireccion.setGeoZonaGponDiseniado("");
                        geodataSubDireccion.setGeoZonaCurrier("");
                        geodataSubDireccion.setGeoZonaCoberturaUltimaMilla("");
                        geodataSubDireccion.setGeoZonaCoberturaCavs("");
                        geodataSubDireccion.setGeoZona5G("");
                        geodataSubDireccion.setGeoZona4G("");
                        geodataSubDireccion.setGeoZona3G("");
                        geodataSubDireccion.setZipCode("");
                        geodataSubDireccion.setZona("");
                        geodataSubDireccion.setCx("");
                        geodataSubDireccion.setCy("");
                    }                    
                    //Si la direccion no existe y si el flag esta en FALSE, no se puede guardar
                    if ((!validExistAddress(geodataDirecci.getDirtrad(), geodataDirecci.getEstado(), geodataDirecci.getValplaca()))
                            && (validate.toUpperCase().equals("FALSE"))) {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR + " Direccion ");
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        return respMesg;
                    }
                    //Si la direccion no existe y si el flag esta en FALSE, no se puede guardar
                    if ((!validExistAddress(geodataSubDireccion.getDirtrad(), geodataSubDireccion.getEstado(), geodataSubDireccion.getValplaca()))
                            && (validate.toUpperCase().equals("FALSE"))) {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR + " SubDireccion");
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        return respMesg;
                    }
                    String tipoDireccionConsultada = "";
                    if (centroPoblado != null) {
                        tipoDireccionConsultada = validarExistenciaDireccionOnRepoInspira(geodataDirecci,
                                geodataSubDireccion, respMesg, dirNueva, centroPoblado.getGpoId());
                    }
                    if (Constant.DIR_SI_EXISTE_MSJ.equals(tipoDireccionConsultada)) {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    } else if (DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO.equals(tipoDireccionConsultada)) {
                   
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        
                    } //La direccion no EXISTE, se debe validar almacenarla en DIRECCION o SUBDIRECCION
                    else if (Constant.DIRECCION.equals(tipoDireccionConsultada)) {
                        dir = guardarDireccion(geodataDirecci, request, respMesg, user, nombreFuncionalidad);
                    } else if (Constant.SUB_DIRECCION.equals(tipoDireccionConsultada)) {
                        subDir = guardarSubDireccion(geodataDirecci, geodataSubDireccion, respMesg, user, nombreFuncionalidad, dirNueva);
                    } //Si es una subdireccion y tampoco existe la direccion
                    else if ((Constant.DIRECCION + Constant.SUB_DIRECCION).equals(tipoDireccionConsultada)) {
                        dir = guardarDireccion(geodataDirecci, request, respMesg, user, nombreFuncionalidad);
                        DrDireccion dirTemporal = dirNueva.clone();
                        //espinosadiea estos campos se vacian para cuando crea subdireccion sin existir la direccion la direccion
                        //quede sin complemento al momento de crear la DireccionDetallada en base de datos.
                        
                        dirTemporal.setCpTipoNivel1(null);
                        dirTemporal.setCpValorNivel1(null);
                        dirTemporal.setCpTipoNivel2(null);
                        dirTemporal.setCpValorNivel2(null);
                        dirTemporal.setCpTipoNivel3(null);
                        dirTemporal.setCpValorNivel3(null);
                        dirTemporal.setCpTipoNivel4(null);
                        dirTemporal.setCpValorNivel4(null);

                        
                        dirTemporal.setCpTipoNivel5(null);
                        dirTemporal.setCpValorNivel5(null);
                        dirTemporal.setCpTipoNivel6(null);
                        dirTemporal.setCpValorNivel6(null);
                        respMesg.setNuevaDireccionDetallada(cmtDireccionDetalleMglManager
                                .guardarDireccionDetalleXSolicitud(respMesg.getIdaddress(), dirTemporal));

                        //se debe crear un registro para la direccion y luego para la subdireccion                        
                        subDir = guardarSubDireccion(geodataDirecci, geodataSubDireccion, respMesg, user, nombreFuncionalidad, dirNueva);
                    } else {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    }
                }//Fin de direccion IN-TRADUCIBLE
                //se asigna el valor de id de la direccion detalla para retornala
                if (respMesg != null && respMesg.getIdaddress() != null) {
                    respMesg.setNuevaDireccionDetallada(cmtDireccionDetalleMglManager
                            .guardarDireccionDetalleXSolicitud(respMesg.getIdaddress(), dirNueva));
                }
            } else {
                respMesg = new ResponseMessage();
                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_FUERA_SERVICIO);
                return respMesg;
            }
        } catch (ApplicationException | CloneNotSupportedException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(e.getMessage());
            return respMesg;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(e.getMessage());
            return respMesg;
        }
        return respMesg;
    }
    
     /**
     * Crea una dirección en el repositorio
     *
     * @param request
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @param dirNueva
     * @return
     * @throws ExceptionDB
     */
    @Override
    public ResponseMessage createAddressFichas(AddressGeodata geoDataDireccionPrincipal, AddressGeodata geoDataSubDireccion, AddressRequest request, String user,
            String nombreFuncionalidad, String validate, DrDireccion dirNueva) throws ExceptionDB {
        dirNueva.setUsuarioCreacion(user);
        ResponseMessage respMesg = new ResponseMessage();
        AddressGeodata geodataDirecci = null;
        AddressGeodata geodataSubDireccion = null;
        boolean infoBarioValidaDir = true;// espinosadiea informacion devuelta por el servicio de geodata valida para ciudades multiorigen
        boolean infoBarioValidaSubDir = true;// espinosadiea informacion devuelta por el servicio de geodata valida para ciudades multiorigen
        if ("".equals(validate)) {
            validate = "TRUE";
        }
        
        //JDHT
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPoblado = null;        
        if (request.getCodDaneVt() != null && !request.getCodDaneVt().isEmpty()) {
            centroPoblado = geograficoPoliticoManager.findCentroPobladoCodDane(request.getCodDaneVt());
        }else{           
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText("Es necesario el codigo dane del centro poblado");
            return respMesg;
        }
        
        //Se valida la obligatoriedad del barrio (Ciudades multiorigen)
        boolean multiorigen;
        try {
             if (centroPoblado != null && centroPoblado.getGpoMultiorigen() != null && centroPoblado.getGpoMultiorigen() != null) {
                multiorigen =  centroPoblado.getGpoMultiorigen().equalsIgnoreCase("1") ? true : false;
            } else {
                multiorigen = queryCiudadMultiorigen(request.getCity());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_CIUDAD_NO_EXISTE);
            return respMesg;
        }
        
         if (multiorigen) {
            dirNueva.setMultiOrigen("1");
            if(dirNueva.getIdTipoDireccion().equalsIgnoreCase("BM")){
                request.setNeighborhood("");
            }
        } else {
             dirNueva.setMultiOrigen("0");
            //se elimina el barrio para CK y BM si no es multiorigen
            if (!dirNueva.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                request.setNeighborhood("");                
            }
        }
         
        //Se valida la obligatoriedad del barrio (Ciudades multiorigen)
        if (dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK") && multiorigen && (request.getNeighborhood() == null || request.getNeighborhood().isEmpty())) {
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_BARRIO_OBLIGATORIO);
            return respMesg;
        }
        //Se consultan los atributos de la direccion consumiendo el servicio
        try {
            boolean esSubDireccion = !direccionCampo5(dirNueva);
            DrDireccionManager adminDireccion = new DrDireccionManager();
            request.setAddress(adminDireccion.getDireccionSinComplemento(dirNueva));//obtener de drDireccion sin complemento
            // JDHT Se establece la geodata recibida de la Direccion Principal
            geodataDirecci = geoDataDireccionPrincipal;
            //si geodata contesta que la direccion es intraducible y el tipo de direccion es de
            //CK o BM debe mostrar error de busqueda.
            if (geodataDirecci != null) {
                if (multiorigen && dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    if (geodataDirecci.getDirtrad().contains(request.getNeighborhood())) {
                        //si la direccion traducida tiene el barrio no se hace nada
                    } else {
                        //si la direccion traducida no tiene el barrio en la direccion traducida se le concatena por ser multiorigen
                        geodataDirecci.setDirtrad(geodataDirecci.getDirtrad() + " " + request.getNeighborhood().toUpperCase());
                    }
                }

                if (esSubDireccion) {
                    AddressRequest requestSubDireccion = new AddressRequest();
                    requestSubDireccion.setId(request.getId());
                    requestSubDireccion.setAddress(adminDireccion.getDireccion(dirNueva));//obtener de drDireccion con complemento
                    requestSubDireccion.setCity(request.getCity());
                    requestSubDireccion.setNeighborhood(request.getNeighborhood());
                    requestSubDireccion.setState(request.getState());
                    requestSubDireccion.setLevel("C");
                    requestSubDireccion.setCodDaneVt(request.getCodDaneVt());
                    // JDHT Se establece la geodata recibida de la subDireccion
                    geodataSubDireccion = geoDataSubDireccion;
                }
            }
            boolean creaDireccion = (geodataDirecci != null) && ((esSubDireccion && geodataSubDireccion != null)
                    || (!esSubDireccion && geodataSubDireccion == null));
            if (creaDireccion) {
                if (multiorigen && dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK")) {
                    geodataDirecci.setCodencont(geodataDirecci.getCodencont() + "|" + request.getNeighborhood().trim().toUpperCase());
                    if (esSubDireccion) {
                        geodataSubDireccion.setCoddir(geodataSubDireccion.getCoddir() + "|" + request.getNeighborhood().trim().toUpperCase());
                    }
                }
                if (!esSubDireccion) {//para tener referencia en los metodos de abajo cuando no exista subdireccion
                    geodataSubDireccion = geodataDirecci;
                }
                //espinosadiea ciudad multiorigen pero el barrio que vuelve el geodata no corresponde con el enviado para la creacion
                if (multiorigen && dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK") && geodataDirecci.getBarrio() != null
                        && geodataDirecci.getBarrio().trim().equalsIgnoreCase(request.getNeighborhood().trim()) ){
                    infoBarioValidaDir = false;// espinosadiea informacion devuelta por el servicio de geodata invalida para ciudades multiorigen
                }  else {
                    String nodoUno = geodataDirecci.getNodo1() == null ? "" : geodataDirecci.getNodo1();
                    geodataDirecci.setNodo1(estandarizaNodo(nodoUno));
                    String nodoDos = geodataDirecci.getNodo2() == null ? "" : geodataDirecci.getNodo2();
                    geodataDirecci.setNodo2(estandarizaNodo(nodoDos));
                    String nodoTres = geodataDirecci.getNodo3() == null ? "" : geodataDirecci.getNodo3();
                    geodataDirecci.setNodo3(estandarizaNodo(nodoTres));
                    String nodoDth = geodataDirecci.getNodoDth() == null ? "" : geodataDirecci.getNodoDth();
                    geodataDirecci.setNodoDth(estandarizaNodo(nodoDth));
                    String nodoMovil = geodataDirecci.getNodoMovil() == null ? "" : geodataDirecci.getNodoMovil();
                    geodataDirecci.setNodoMovil(estandarizaNodo(nodoMovil));
                    String nodoFtth = geodataDirecci.getNodoFtth() == null ? "" : geodataDirecci.getNodoFtth();
                    geodataDirecci.setNodoFtth(estandarizaNodo(nodoFtth));
                    String nodoWifi = geodataDirecci.getNodoWifi() == null ? "" : geodataDirecci.getNodoWifi();
                    geodataDirecci.setNodoWifi(estandarizaNodo(nodoWifi));
                    //Inicio cambio 1.6            
                    String geoZona3G = geodataDirecci.getGeoZona3G()
                            == null ? "" : geodataDirecci.getGeoZona3G();
                    geodataDirecci.setGeoZona3G(estandarizaNodo(geoZona3G));
                    String geoZona4G = geodataDirecci.getGeoZona4G()
                            == null ? "" : geodataDirecci.getGeoZona4G();
                    geodataDirecci.setGeoZona4G(estandarizaNodo(geoZona4G));
                    String geoZona5G = geodataDirecci.getGeoZona5G()
                            == null ? "" : geodataDirecci.getGeoZona5G();
                    geodataDirecci.setGeoZona5G(estandarizaNodo(geoZona5G));
                    String geoZonaCoberturaCavs = geodataDirecci.getGeoZonaCoberturaCavs()
                            == null ? "" : geodataDirecci.getGeoZonaCoberturaCavs();
                    geodataDirecci.setGeoZonaCoberturaCavs(estandarizaNodo(geoZonaCoberturaCavs));
                    String geoZonaCoberturaUltimaMilla = geodataDirecci.getGeoZonaCoberturaUltimaMilla()
                            == null ? "" : geodataDirecci.getGeoZonaCoberturaUltimaMilla();
                    geodataDirecci.setGeoZonaCoberturaUltimaMilla(estandarizaNodo(geoZonaCoberturaUltimaMilla));
                    String geoZonaCurrier = geodataDirecci.getGeoZonaCurrier()
                            == null ? "" : geodataDirecci.getGeoZonaCurrier();
                    geodataDirecci.setGeoZonaCurrier(estandarizaNodo(geoZonaCurrier));
                    String geoZonaGponDiseniado = geodataDirecci.getGeoZonaGponDiseniado()
                            == null ? "" : geodataDirecci.getGeoZonaGponDiseniado();
                    geodataDirecci.setGeoZonaGponDiseniado(estandarizaNodo(geoZonaGponDiseniado));
                    String geoZonaMicroOndas = geodataDirecci.getGeoZonaMicroOndas()
                            == null ? "" : geodataDirecci.getGeoZonaMicroOndas();
                    geodataDirecci.setGeoZonaMicroOndas(estandarizaNodo(geoZonaMicroOndas));
                    String geoZonaUnifilar = geodataDirecci.getGeoZonaUnifilar()
                            == null ? "" : geodataDirecci.getGeoZonaUnifilar();
                    geodataDirecci.setGeoZonaUnifilar(estandarizaNodo(geoZonaUnifilar));
                    //FIN cambio 1.6            

                }
                if (geodataSubDireccion != null && esSubDireccion) {
                    //espinosadiea ciudad multiorigen pero el barrio que vuelve el geodata no corresponde con el enviado para la creacion
                    if (multiorigen && dirNueva.getIdTipoDireccion().equalsIgnoreCase("CK") && geodataSubDireccion.getBarrio() != null
                            && geodataSubDireccion.getBarrio().trim().equalsIgnoreCase(request.getNeighborhood().trim())) {
                        infoBarioValidaSubDir = false;// espinosadiea informacion devuelta por el servicio de geodata invalida para ciudades multiorigen
                    } else {
                        String nodoUnoSD = geodataSubDireccion.getNodo1() == null ? "" : geodataSubDireccion.getNodo1();
                        geodataSubDireccion.setNodo1(estandarizaNodo(nodoUnoSD));
                        String nodoDosSD = geodataSubDireccion.getNodo2() == null ? "" : geodataSubDireccion.getNodo2();
                        geodataSubDireccion.setNodo2(estandarizaNodo(nodoDosSD));
                        String nodoTresSD = geodataSubDireccion.getNodo3() == null ? "" : geodataSubDireccion.getNodo3();
                        geodataSubDireccion.setNodo3(estandarizaNodo(nodoTresSD));
                        String nodoDthSD = geodataSubDireccion.getNodoDth() == null ? "" : geodataSubDireccion.getNodoDth();
                        geodataSubDireccion.setNodoDth(estandarizaNodo(nodoDthSD));
                        String nodoMovilSD = geodataSubDireccion.getNodoMovil() == null ? "" : geodataSubDireccion.getNodoMovil();
                        geodataSubDireccion.setNodoMovil(estandarizaNodo(nodoMovilSD));
                        String nodoFtthSD = geodataSubDireccion.getNodoFtth() == null ? "" : geodataSubDireccion.getNodoFtth();
                        geodataSubDireccion.setNodoFtth(estandarizaNodo(nodoFtthSD));
                        String nodoWifiSD = geodataSubDireccion.getNodoWifi() == null ? "" : geodataSubDireccion.getNodoWifi();
                        geodataSubDireccion.setNodoWifi(estandarizaNodo(nodoWifiSD));
                        //Inicio cambio 1.6            
                        String geoZona3G = geodataSubDireccion.getGeoZona3G()
                                == null ? "" : geodataSubDireccion.getGeoZona3G();
                        geodataSubDireccion.setGeoZona3G(estandarizaNodo(geoZona3G));
                        String geoZona4G = geodataSubDireccion.getGeoZona4G()
                                == null ? "" : geodataSubDireccion.getGeoZona4G();
                        geodataSubDireccion.setGeoZona4G(estandarizaNodo(geoZona4G));
                        String geoZona5G = geodataSubDireccion.getGeoZona5G()
                                == null ? "" : geodataSubDireccion.getGeoZona5G();
                        geodataSubDireccion.setGeoZona5G(estandarizaNodo(geoZona5G));
                        String geoZonaCoberturaCavs = geodataSubDireccion.getGeoZonaCoberturaCavs()
                                == null ? "" : geodataSubDireccion.getGeoZonaCoberturaCavs();
                        geodataSubDireccion.setGeoZonaCoberturaCavs(estandarizaNodo(geoZonaCoberturaCavs));
                        String geoZonaCoberturaUltimaMilla = geodataSubDireccion.getGeoZonaCoberturaUltimaMilla()
                                == null ? "" : geodataSubDireccion.getGeoZonaCoberturaUltimaMilla();
                        geodataSubDireccion.setGeoZonaCoberturaUltimaMilla(estandarizaNodo(geoZonaCoberturaUltimaMilla));
                        String geoZonaCurrier = geodataSubDireccion.getGeoZonaCurrier()
                                == null ? "" : geodataSubDireccion.getGeoZonaCurrier();
                        geodataSubDireccion.setGeoZonaCurrier(estandarizaNodo(geoZonaCurrier));
                        String geoZonaGponDiseniado = geodataSubDireccion.getGeoZonaGponDiseniado()
                                == null ? "" : geodataSubDireccion.getGeoZonaGponDiseniado();
                        geodataSubDireccion.setGeoZonaGponDiseniado(estandarizaNodo(geoZonaGponDiseniado));
                        String geoZonaMicroOndas = geodataSubDireccion.getGeoZonaMicroOndas()
                                == null ? "" : geodataSubDireccion.getGeoZonaMicroOndas();
                        geodataSubDireccion.setGeoZonaMicroOndas(estandarizaNodo(geoZonaMicroOndas));
                        String geoZonaUnifilar = geodataSubDireccion.getGeoZonaUnifilar()
                                == null ? "" : geodataSubDireccion.getGeoZonaUnifilar();
                        geodataSubDireccion.setGeoZonaUnifilar(estandarizaNodo(geoZonaUnifilar));
                        //FIN cambio 1.6            

                    }
                }
                Direccion dir = null;
                SubDireccion subDir = null;
                //valido que la direeccion sea intraducible por el codigo de direccion devuelto por el geodata 
                if (geodataDirecci.getCoddir() == null || geodataDirecci.getCoddir().isEmpty()
                        || geodataSubDireccion.getCoddir() == null || geodataSubDireccion.getCoddir().isEmpty()) {
                    //Se debe crear la direccion y almacenar con ubicacion 0,0
                    if (validate.toUpperCase().equals("TRUE")) {
                        DrDireccion dirClonNueva = dirNueva.clone();
                        //se clona para quitar el complemento 
                        
                        dirClonNueva.setCpTipoNivel1(null);
                        dirClonNueva.setCpTipoNivel2(null);
                        dirClonNueva.setCpTipoNivel3(null);
                        dirClonNueva.setCpTipoNivel4(null);
                        dirClonNueva.setCpValorNivel1(null);
                        dirClonNueva.setCpValorNivel2(null);
                        dirClonNueva.setCpValorNivel3(null);
                        dirClonNueva.setCpValorNivel4(null);                        
                        dirClonNueva.setCpTipoNivel5(null);
                        dirClonNueva.setCpTipoNivel6(null);
                        dirClonNueva.setCpValorNivel5(null);
                        dirClonNueva.setCpValorNivel6(null);
                        
                        if (geodataDirecci.getDirtrad() == null || geodataDirecci.getDirtrad().isEmpty()) {
                            //garantizo que que colocara un valor en el codigo igac de la direccion que sirve para realizar busqueda basado en el drdireccion
                            geodataDirecci.setDirtrad(adminDireccion.getDireccionSinComplemento(dirClonNueva));
                        }
                        dir = validarExistenciaDirIntraducibleOnRepo(geodataDirecci.getDirtrad().trim(), request.getCodDaneVt(), respMesg);
                        if (dir != null && dir.getDirId() != null && !"0".equals(dir.getDirId().toString())) {
                            //la direccion ya existe y no se puede guardar
                            if (!esSubDireccion) {// si la direccion es sin complemento sale del metodo por que existe la direccion
                                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                                respMesg.setIdaddress("d" + dir.getDirId().toString());
                            }
                        } else {
                            dir = guardarDireccion(geodataDirecci, request, respMesg, user, nombreFuncionalidad);
                            if (esSubDireccion) {
                                respMesg.setNuevaDireccionDetallada(cmtDireccionDetalleMglManager
                                        .guardarDireccionDetalleXSolicitud(respMesg.getIdaddress(), dirClonNueva));
                            }
                        }
                        //valida si la direccion intraducible pudo ser traducida por el geodata
                        if (esSubDireccion) {
                            String subDireccionIntraducible = adminDireccion.getDireccion(dirNueva);//obtiene la direccion con complemento
                            //funcion busca la direccion en la tabla Mgl_direccion
                            List<CmtDireccionDetalladaMgl> lisSubDir = cmtDireccionDetalleMglManager.buscarDireccionDetalladaTabulada(dirNueva,
                                    queryGeograficoPoliticoByStateCityName(request.getState(), request.getCity()));
                            if (lisSubDir == null || lisSubDir.isEmpty()) {//no encuentra la direccion
                                subDir = guardarSubDireccionIntraducible(geodataSubDireccion, respMesg, user,
                                        nombreFuncionalidad, dir, subDireccionIntraducible, dirNueva);
                            } else {
                                //la SubDireccion ya existe y no se puede guardar
                                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                                respMesg.setIdaddress("s" + lisSubDir.get(0).getSubDireccion().getSdiId().toString());
                            }
                        }
                    } else {
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR);
                        return respMesg;
                    }
                } else {
                    if (!infoBarioValidaDir) {
                        geodataDirecci.setComentEconomicLevel("");
                        geodataDirecci.setConfiability("");
                        geodataDirecci.setEstrato("");
                        geodataDirecci.setNivsocio("");
                        geodataDirecci.setNodo1("");
                        geodataDirecci.setNodo2("");
                        geodataDirecci.setNodo3("");
                        geodataDirecci.setNodoDth("");
                        geodataDirecci.setNodoFtth("");
                        geodataDirecci.setNodoMovil("");
                        geodataDirecci.setNodoWifi("");
                        geodataDirecci.setZipCode("");
                        geodataDirecci.setZona("");
                        geodataDirecci.setGeoZonaUnifilar("");
                        geodataDirecci.setGeoZonaMicroOndas("");
                        geodataDirecci.setGeoZonaGponDiseniado("");
                        geodataDirecci.setGeoZonaCurrier("");
                        geodataDirecci.setGeoZonaCoberturaUltimaMilla("");
                        geodataDirecci.setGeoZonaCoberturaCavs("");
                        geodataDirecci.setGeoZona5G("");
                        geodataDirecci.setGeoZona4G("");
                        geodataDirecci.setGeoZona3G("");
                        geodataDirecci.setZipCode("");
                        geodataDirecci.setZona("");
                        geodataDirecci.setCx("");
                        geodataDirecci.setCy("");
                    }
                    if (!infoBarioValidaSubDir) {
                        geodataSubDireccion.setComentEconomicLevel("");
                        geodataSubDireccion.setConfiability("");
                        geodataSubDireccion.setEstrato("");
                        geodataSubDireccion.setNivsocio("");
                        geodataSubDireccion.setNodo1("");
                        geodataSubDireccion.setNodo2("");
                        geodataSubDireccion.setNodo3("");
                        geodataSubDireccion.setNodoDth("");
                        geodataSubDireccion.setNodoFtth("");
                        geodataSubDireccion.setNodoMovil("");
                        geodataSubDireccion.setNodoWifi("");
                        geodataSubDireccion.setZipCode("");
                        geodataSubDireccion.setZona("");
                        geodataSubDireccion.setGeoZonaUnifilar("");
                        geodataSubDireccion.setGeoZonaMicroOndas("");
                        geodataSubDireccion.setGeoZonaGponDiseniado("");
                        geodataSubDireccion.setGeoZonaCurrier("");
                        geodataSubDireccion.setGeoZonaCoberturaUltimaMilla("");
                        geodataSubDireccion.setGeoZonaCoberturaCavs("");
                        geodataSubDireccion.setGeoZona5G("");
                        geodataSubDireccion.setGeoZona4G("");
                        geodataSubDireccion.setGeoZona3G("");
                        geodataSubDireccion.setZipCode("");
                        geodataSubDireccion.setZona("");
                        geodataSubDireccion.setCx("");
                        geodataSubDireccion.setCy("");
                        
                    }
                    AddressGeodata geodataEnOtraMalla = null;
                    AddressRequest requestEnOtraMalla = new AddressRequest();
                    if (!geodataDirecci.getDiralterna().isEmpty() && !geodataDirecci.getDirtrad().equalsIgnoreCase(geodataDirecci.getDiralterna())) {
                        requestEnOtraMalla.setState(request.getState());
                        requestEnOtraMalla.setCity(request.getCity());
                        requestEnOtraMalla.setNeighborhood(request.getNeighborhood());
                        requestEnOtraMalla.setAddress(geodataDirecci.getDiralterna());
                        requestEnOtraMalla.setLevel(request.getLevel());
                        //Se consulta los atributos de la direccion alterna en la otra malla
                        try {
                            geodataEnOtraMalla = queryAddressGeodata(requestEnOtraMalla);
                        } catch (ApplicationException e) {
                            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                            respMesg.setMessageText(e.getMessage());
                        }
                    }
                    //Si la direccion no existe y si el flag esta en FALSE, no se puede guardar
                    if ((!validExistAddress(geodataDirecci.getDirtrad(), geodataDirecci.getEstado(), geodataDirecci.getValplaca()))
                            && (validate.toUpperCase().equals("FALSE"))) {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR + " Direccion ");
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        return respMesg;
                    }
                    //Si la direccion no existe y si el flag esta en FALSE, no se puede guardar
                    if ((!validExistAddress(geodataSubDireccion.getDirtrad(), geodataSubDireccion.getEstado(), geodataSubDireccion.getValplaca()))
                            && (validate.toUpperCase().equals("FALSE"))) {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR + " SubDireccion");
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        return respMesg;
                    }
                    String tipoDireccionConsultada = validarExistenciaDireccionOnRepo(geodataDirecci,
                            geodataSubDireccion, geodataEnOtraMalla, respMesg, dirNueva, centroPoblado.getGpoId());
                    if (Constant.DIR_SI_EXISTE_MSJ.equals(tipoDireccionConsultada)) {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        
                    } else if (DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO.equals(tipoDireccionConsultada)) {
                        if (geodataEnOtraMalla.getFuente().equals(Constant.DIR_FUENTE_ANTIGUA)) {
                            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA);
                            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        } else if (geodataEnOtraMalla.getFuente().equals(Constant.DIR_FUENTE_NUEVA)) {
                            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_NUEVA);
                            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        }
                    } //La direccion no EXISTE, se debe validar almacenarla en DIRECCION o SUBDIRECCION
                    else if (Constant.DIRECCION.equals(tipoDireccionConsultada)) {
                        dir = guardarDireccion(geodataDirecci, request, respMesg, user, nombreFuncionalidad);
                    } else if (Constant.SUB_DIRECCION.equals(tipoDireccionConsultada)) {
                        subDir = guardarSubDireccion(geodataDirecci, geodataSubDireccion, respMesg, user, nombreFuncionalidad, dirNueva);
                    } //Si es una subdireccion y tampoco existe la direccion
                    else if ((Constant.DIRECCION + Constant.SUB_DIRECCION).equals(tipoDireccionConsultada)) {
                        dir = guardarDireccion(geodataDirecci, request, respMesg, user, nombreFuncionalidad);
                        DrDireccion dirTemporal = dirNueva.clone();
                        //espinosadiea estos campos se vacian para cuando crea subdireccion sin existir la direccion la direccion
                        //quede sin complemento al momento de crear la DireccionDetallada en base de datos.
                        dirTemporal.setCpTipoNivel1(null);
                        dirTemporal.setCpValorNivel1(null);
                        dirTemporal.setCpTipoNivel2(null);
                        dirTemporal.setCpValorNivel2(null);
                        dirTemporal.setCpTipoNivel3(null);
                        dirTemporal.setCpValorNivel3(null);
                        dirTemporal.setCpTipoNivel4(null);
                        dirTemporal.setCpValorNivel4(null);                        
                        dirTemporal.setCpTipoNivel5(null);
                        dirTemporal.setCpValorNivel5(null);
                        dirTemporal.setCpTipoNivel6(null);
                        dirTemporal.setCpValorNivel6(null);
                        respMesg.setNuevaDireccionDetallada(cmtDireccionDetalleMglManager
                                .guardarDireccionDetalleXSolicitud(respMesg.getIdaddress(), dirTemporal));

                        //se debe crear un registro para la direccion y luego para la subdireccion                        
                        subDir = guardarSubDireccion(geodataDirecci, geodataSubDireccion, respMesg, user, nombreFuncionalidad, dirNueva);
                    } else {
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    }
                }//Fin de direccion IN-TRADUCIBLE
                //se asigna el valor de id de la direccion detalla para retornala
                if (respMesg != null && respMesg.getIdaddress() != null) {
                    respMesg.setNuevaDireccionDetallada(cmtDireccionDetalleMglManager
                            .guardarDireccionDetalleXSolicitud(respMesg.getIdaddress(), dirNueva));
                }
            } else {
                respMesg = new ResponseMessage();
                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_FUERA_SERVICIO);
                return respMesg;
            }
        } catch (ApplicationException | CloneNotSupportedException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(e.getMessage());
            return respMesg;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(e.getMessage());
            return respMesg;
        }
        return respMesg;
    }

    /**
     *
     * @param geodata
     * @param address
     * @param respMesg
     * @param user
     * @param nombreFuncionalidad
     */
    private Direccion guardarDireccion(AddressGeodata geodata, AddressRequest address,
            ResponseMessage respMesg, String user, String nombreFuncionalidad)
            throws ApplicationException {
        try {
            //pase por aqui subedificio vt
            //Se arma la direccion a ingresar en el repositorio
            Direccion dir = new Direccion();
            TipoDireccion tipoDir = new TipoDireccion();
            Ubicacion ubicacion = new Ubicacion();
            GeograficoPolitico geo_politico = new GeograficoPolitico();
            Geografico geografico = new Geografico();
            boolean direccionInvalida = false;
            BigDecimal tipoDir_id;
            BigDecimal gpo_id = BigDecimal.ZERO;
            BigDecimal geo_id = BigDecimal.ZERO;
            String estadoDir = geodata.getEstado();
            BigDecimal idLocalidad = null;
            BigDecimal idBarrio = null;
            BigDecimal idManzana = null;
            String latitud = geodata.getCy();
            String longitud = geodata.getCx();
            if ("".equals(latitud) && "".equals(longitud)
                    && Constant.DIR_ESTADO_INTRADUCIBLE.equals(estadoDir)) {
                direccionInvalida = true;
            }
            //Consulto GeoPolitico
            BigDecimal centroPo;
            if (address.getCodDaneVt() != null
                    && !address.getCodDaneVt().trim().isEmpty()) {
                centroPo = queryGeograficoPoliticoByDivipola(address.getCodDaneVt());
            } else {
                //Se carga el geografico politico por codigo Divipola
                if (geodata.getCoddanemcpio() != null
                        && !geodata.getCoddanemcpio().trim().isEmpty()) {
                    centroPo = queryGeograficoPoliticoByDivipola(geodata.getCoddanemcpio().
                            substring(0, 8));
                } else {
                    centroPo = queryGeograficoPoliticoByStateCityName(
                            address.getState(), address.getCity());
                }
            }
            BigDecimal ubi_id = BigDecimal.ZERO;

            if (centroPo.equals(BigDecimal.ZERO)) {
                ubi_id = validarCxCyOnRepository(latitud, longitud);
            } else {
                UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();
                UbicacionMgl ubica = ubicacionMglManager.findByCentroAndLongAndLat(centroPo, longitud, latitud);
                if (ubica != null) {
                    ubi_id = ubica.getUbiId();
                }
            }
            ubicacion.setUbiLongitud(longitud);
            ubicacion.setUbiLatitud(latitud);

            //Si la Ubicacion no existe se debe crear
            if (BigDecimal.ZERO == ubi_id || direccionInvalida) {
                try {

                    if (address.getCodDaneVt() != null
                            && !address.getCodDaneVt().trim().isEmpty()) {
                        gpo_id = queryGeograficoPoliticoByDivipola(address.getCodDaneVt());
                    } else {
                        //Se carga el geografico politico por codigo Divipola
                        if (geodata.getCoddanemcpio() != null
                                && !geodata.getCoddanemcpio().trim().isEmpty()) {
                            gpo_id = queryGeograficoPoliticoByDivipola(geodata.getCoddanemcpio().
                                    substring(0, 8));
                        } else {
                            gpo_id = queryGeograficoPoliticoByStateCityName(
                                    address.getState(), address.getCity());
                        }
                    }
                    if (gpo_id.equals(BigDecimal.ZERO)) {
                        throw new ApplicationException("No se encontro la ciudad: "
                                + address.getCity() + " Para el departamento:"
                                + address.getState());
                    }
                } catch (ApplicationException e) {
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                }
                //Se encontro un geo_politico
                geo_politico.setGpoId(gpo_id);
                //}
                if (!direccionInvalida) {

                    //Se valida cual geo_id se debe asociar a la ubicación
                    if (geodata.getLocalidad() != null && !geodata.getLocalidad().isEmpty()) {
                        try {
                            //Se consulta el geografico por localidad
                            idLocalidad = queryGeograficoLocalidadByNombre(
                                    geodata.getLocalidad());
                        } catch (ApplicationException e) {
                            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                        }

                        if (idLocalidad != null && idLocalidad != BigDecimal.ZERO) {
                            geo_id = idLocalidad;
                        } else {
                            //Se debe crear la localidad
                            Geografico geo = new Geografico();
                            geo.setGeoNombre(geodata.getLocalidad());
                            TipoGeografico tipoGeo = new TipoGeografico();
                            tipoGeo.setTgeId(BigDecimal.ONE);
                            geo.setTipoGeografico(tipoGeo);
                            GeograficoPolitico gpolitico = new GeograficoPolitico();
                            gpolitico.setGpoId(gpo_id);
                            geo.setGeograficoPolitico(gpolitico);
                            geo.setGeoUsuarioCreacion(user);
                            try {
                                //Se debe crear la localidad
                                idLocalidad = insertGeograficoIntorepository(
                                        nombreFuncionalidad, geo);
                            } catch (ApplicationException e) {
                                respMesg.setMessageType(
                                        ResponseMessage.TYPE_ERROR);
                                respMesg.setMessageText(
                                        ResponseMessage.MESSAGE_ERROR_GUARDANDO_LOCALIDAD);
                                String msg = "Se produjo un error al momento de ejecutar el método '"+
                                        ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                            }
                            if (idLocalidad != null) {
                                geo_id = idLocalidad;
                            }
                        }

                        if (geodata.getBarrio() != null && !geodata.getBarrio().isEmpty()) {
                            try {
                                idBarrio = queryGeograficoBarrioByIDLocalidad(
                                        idLocalidad.toString(), geodata.getBarrio());
                            } catch (ApplicationException e) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                            }
                            if (idBarrio != null && idBarrio != BigDecimal.ZERO) {
                                geo_id = idBarrio;
                            } else {
                                //Se debe crear el barrio
                                Geografico geo = new Geografico();
                                geo.setGeoNombre(geodata.getBarrio());
                                TipoGeografico tipoGeo = new TipoGeografico();
                                tipoGeo.setTgeId(new BigDecimal("2"));
                                geo.setTipoGeografico(tipoGeo);
                                Geografico geoLocalidad = new Geografico();
                                geoLocalidad.setGeoId(new BigDecimal(idLocalidad.toString()));
                                geo.setGeografico(geoLocalidad);
                                GeograficoPolitico gpolitico = new GeograficoPolitico();
                                gpolitico.setGpoId(gpo_id);
                                geo.setGeograficoPolitico(gpolitico);
                                geo.setGeoUsuarioCreacion(user);
                                try {
                                    idBarrio = insertGeograficoIntorepository(
                                            nombreFuncionalidad, geo);
                                } catch (ApplicationException e) {
                                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                    LOGGER.error(msg);
                                    respMesg.setMessageType(
                                            ResponseMessage.TYPE_ERROR);
                                    respMesg.setMessageText(
                                            ResponseMessage.MESSAGE_ERROR_GUARDANDO_BARRIO);
                                }
                                if (idBarrio != null) {
                                    geo_id = idBarrio;
                                }
                            }
                        } else {
                            //Si el barrio no viene informado se pasa el idlocalidad
                            geo_id = idLocalidad;
                        }

                        if (geodata.getCoddir().length() == 99) {
                            //Es una direccion manzana casa
                            String manzana = getAppletFromAddress(geodata.getCoddir());
                            try {
                                idManzana = queryGeograficoMzaByIdBarrio(
                                        idBarrio.toString(), manzana);
                            } catch (ApplicationException e) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                            }

                            if (idManzana != null && idManzana != BigDecimal.ZERO) {
                                geo_id = idManzana;
                            } else {
                                //Se debe crear el barrio
                                Geografico geo = new Geografico();
                                geo.setGeoNombre(geodata.getBarrio());
                                TipoGeografico tipoGeo = new TipoGeografico();
                                tipoGeo.setTgeId(new BigDecimal("3"));
                                geo.setTipoGeografico(tipoGeo);
                                Geografico geoBarrio = new Geografico();
                                geoBarrio.setGeoId(new BigDecimal(idBarrio.toString()));
                                geo.setGeografico(geoBarrio);
                                GeograficoPolitico gpolitico = new GeograficoPolitico();
                                gpolitico.setGpoId(gpo_id);
                                geo.setGeograficoPolitico(gpolitico);
                                geo.setGeoUsuarioCreacion(user);
                                try {
                                    //Se debe crear el barrio
                                    idManzana = insertGeograficoIntorepository(
                                            nombreFuncionalidad, geo);
                                } catch (ApplicationException e) {
                                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                    LOGGER.error(msg);
                                    respMesg.setMessageType(
                                            ResponseMessage.TYPE_ERROR);
                                    respMesg.setMessageText(
                                            ResponseMessage.MESSAGE_ERROR_GUARDANDO_MANZANA);
                                }
                                if (idManzana != null) {
                                    geo_id = idManzana;
                                }
                            }
                        } else {
                            geo_id = idBarrio;
                        }
                        //Se encontro un geografico
                        geografico.setGeoId(geo_id);
                        //}
                    } else {
                        geografico.setGeoId(null);
                    }
                } else {
                    //Se crea una ubicacion con longitud, latitud =0
                    ubicacion.setUbiLatitud("0");
                    ubicacion.setUbiLongitud("0");
                }

                ubicacion.setGpoId(geo_politico);
                ubicacion.setGeoId(geografico);
                ubicacion.setUbiUsuarioCreacion(user);
                ubicacion.setUbiDistritoCodigoPostal(geodata.getZipCodeDistrict());//Cambio carlos villamil direcciones fase I 2012-12-14
                ubicacion.setUbiZonaDivipola(geodata.getDanePopulatedArea());//Cambio carlos villamil direcciones fase I 2012-12-14
                TipoUbicacion tipoubicacion = new TipoUbicacion();
                if (geodata.getDirtrad().contains(
                        Constant.PREFIJO_COMPLEMENTO_APARTAMENTO)) {
                    tipoubicacion.setTubId(BigDecimal.ONE);
                } else {
                    tipoubicacion.setTubId(new BigDecimal("2"));
                }
                if (Constant.DIR_ESTADO_INTRADUCIBLE.equals(geodata.getEstado())) {
                    ubicacion.setTubId(null);
                } else {
                    ubicacion.setTubId(tipoubicacion);
                }

                BigDecimal ubicacion_id = null;
                try {
                    //Se crea una nueva ubicacion.
                    ubicacion.setUbiDistritoCodigoPostal(geodata.getZipCodeDistrict());//Cambio carlos villamil direcciones fase I 2012-12-14
                    ubicacion.setUbiZonaDivipola(geodata.getDanePopulatedArea());//Cambio carlos villamil direcciones fase I 2012-12-14
                    ubicacion_id = insertUbicacion(nombreFuncionalidad, ubicacion);
                } catch (ApplicationException e) {
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                }

                if (ubicacion_id != null) {
                    ubicacion.setUbiId(ubicacion_id);
                }
                //si la ubicacion no existe, tipoDireccion = PRINCIPAL
                tipoDir_id = queryTipoDireccionByEstado(Constant.TIPO_DIRECCION_PRINCIPAL);
                tipoDir.setTdiId(tipoDir_id);
            } else {
                //La ubicacion si existe
                ubicacion.setUbiId(ubi_id);

                //Se asocia el tipo de Direccion = ALIAS
                tipoDir_id = queryTipoDireccionByEstado(Constant.TIPO_DIRECCION_ALIAS);
                tipoDir.setTdiId(tipoDir_id);
            }
            dir.setUbicacion(ubicacion);
            dir.setTipoDireccion(tipoDir);
            dir.setDirFormatoIgac(geodata.getDirtrad().trim());
            dir.setDirServinformacion(geodata.getCodencont());
            dir.setDirNostandar(address.getAddress());
            dir.setDirOrigen(geodata.getFuente());
            //TODO: Validar el mapeo de Confiabilidad
            if ("".equals(geodata.getValplaca()) || "0".equals(geodata.getValplaca())) {
                dir.setDirConfiabilidad(BigDecimal.ZERO);
            } else {
                dir.setDirConfiabilidad(new BigDecimal(geodata.getValplaca()));
            }
            if ("".equals(geodata.getNivsocio())) {
                dir.setDirNivelSocioecono(new BigDecimal(-1));//Se envia negativo para el CASE en la DB
                dir.setDirEstrato(new BigDecimal(-1));
            } else {
                dir.setDirNivelSocioecono(new BigDecimal(geodata.getNivsocio()));
                dir.setDirEstrato(new BigDecimal(geodata.getNivsocio()));
            }
            //Valida si trae un estrato de la funcionalidad queryAddressResultBatch
            if ("queryAddressResultBatch".equals(nombreFuncionalidad)) {
                if (this.Estrato.equals("N/A")) {//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                    dir.setDirEstrato(new BigDecimal(-1));//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                } else {//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                    dir.setDirEstrato(new BigDecimal(this.Estrato));
                }//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2 
            }
            //TODO: PENDIENTE por validar quien entrega este codPostal
            //Modificado fase 1 direcciones carlos villamil l012-12-14
            dir.setDirUsuarioCreacion(user);
            dir.setDirManzanaCatastral(geodata.getManzana());
            dir.setDirManzana(getAppletFromAddress(geodata.getCoddir()));
            String revisarEncampo = validarRevisar(geodata.getEstado(),
                    geodata.getDirtrad(), geodata.getValplaca(), gpo_id);
            dir.setDirRevisar(revisarEncampo);
            dir.setDirActividadEconomica(geodata.getActeconomica());
            dir.setDirComentarioNivelSocioEconomico(geodata.getComentEconomicLevel());//Modificado fase 1 direcciones carlos villamil l012-12-14
            dir.setNodoUno(geodata.getNodo1());//Modificado fase 1 direcciones carlos villamil l013-05-23 V 1.1
            dir.setNodoDos(geodata.getNodo2());//Modificado fase 1 direcciones carlos villamil l013-05-23 V 1.1
            dir.setNodoTres(geodata.getNodo3());//Modificado fase 1 direcciones carlos villamil l013-05-23 V 1.1
            dir.setNodoDth(geodata.getNodoDth());
            dir.setNodoMovil(geodata.getNodoMovil());
            dir.setNodoFtth(geodata.getNodoFtth());
            dir.setNodoWifi(geodata.getNodoWifi());
            //Inicio Cambio 1.6
            dir.setGeoZona3G(geodata.getGeoZona3G());
            dir.setGeoZona4G(geodata.getGeoZona4G());
            dir.setGeoZona5G(geodata.getGeoZona5G());
            dir.setGeoZonaCoberturaCavs(geodata.getGeoZonaCoberturaCavs());
            dir.setGeoZonaCoberturaUltimaMilla(geodata.getGeoZonaCoberturaUltimaMilla());
            dir.setGeoZonaCurrier(geodata.getGeoZonaCurrier());
            dir.setGeoZonaGponDiseniado(geodata.getGeoZonaGponDiseniado());
            dir.setGeoZonaMicroOndas(geodata.getGeoZonaMicroOndas());
            dir.setGeoZonaUnifilar(geodata.getGeoZonaUnifilar());
            //FIN Cambio 1.6
            //Se guarda la Direccion en el repositorio
            BigDecimal dir_id = insertAddressOnRepository(nombreFuncionalidad, dir);
            //Se crea el msg de respuesta
            if (dir_id != null) {
                respMesg.setMessageType(ResponseMessage.TYPE_SUCCESSFUL);
                respMesg.setMessageText(ResponseMessage.MESSAGE_SUCCESSFUL);
                respMesg.setIdaddress("d" + dir_id.toString());
                respMesg.setAddress(dir.getDirFormatoIgac());
                //TODO: Validar este qualifier
                respMesg.setQualifiers(geodata.getValplaca());
            }
            return dir;
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * Guardar una dirección en el repositorio.
     *
     * @param solicitud Entidad que contiene toda la información necesaria para
     * crear una dirección
     * @param tipoDireccion Tipo de direccion a almacenar.
     * @param user Usuario que solicita la creación de la dirección en
     * Repositorio.
     * @param nombreFuncionalidad Nombre de funcionalidad que solicita la
     * creación de la dirección en Repositorio.
     * @return id con el cual fue guardada la dirección en el repositorio.
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public BigDecimal saveAddress(SolicitudNegocio solicitud,
            String tipoDireccion, String user, String nombreFuncionalidad) throws ApplicationException {
        BigDecimal dir_id = BigDecimal.ZERO;
        //Se arma la direccion a ingresar en el repositorio
        Direccion dir = new Direccion();
        //Alias o Principal
        TipoDireccion tipoDir = new TipoDireccion();
        TipoUbicacion tipoUbi = new TipoUbicacion();
        Ubicacion ubicacion = new Ubicacion();
        GeograficoPolitico geo_politico = solicitud.getGeograficoPolitico();
        Geografico geografico = solicitud.getGeografico();
        BigDecimal ubi_id = BigDecimal.ZERO;
        BigDecimal tipoDir_id = BigDecimal.ZERO;
        String latitud = "0";
        String longitud = "0";
        String direccionsubDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
        if (tipoDireccion.equals(Constant.DIRECCION) || tipoDireccion.equals(
                Constant.DIR_INTRADUCIBLE)) {
            tipoUbi.setTubId(ID_CASA);
            ubicacion.setUbiCuentaMatriz("0");
            dir.setDirManzana(getAppletFromAddress(solicitud.getSonServinformacion()));
        } else if (tipoDireccion.equals(direccionsubDireccion)) {
            tipoUbi.setTubId(ID_EDIFICIO);
            ubicacion.setUbiCuentaMatriz("1");
        }
        if (solicitud.getSonLatitud() != null) {
            if (!solicitud.getSonLatitud().equals("")) {
                latitud = solicitud.getSonLatitud();
            }
        }
        if (solicitud.getSonLongitud() != null) {
            if (!solicitud.getSonLongitud().equals("")) {
                longitud = solicitud.getSonLongitud();
            }
        }
        if (!latitud.equals("0") || !longitud.equals("0")) {
            ubi_id = validarCxCyOnRepository(latitud, longitud);
            if (!ubi_id.equals(BigDecimal.ZERO) && !tipoDireccion.equals(Constant.DIR_INTRADUCIBLE)) {
                List<Direccion> direccionesUbi = findAddressByIdUbi(ubi_id);
                if (direccionesUbi != null) {
                    tipoDir_id = Constant.ID_TDI_ALIAS;
                } else {
                    tipoDir_id = Constant.ID_TDI_PRINCIPAL;
                }
            } else if (tipoDir_id.equals(BigDecimal.ZERO)) {
                tipoDir_id = Constant.ID_TDI_PRINCIPAL;
            }
            ubicacion.setUbiLongitud(longitud);
            ubicacion.setUbiLatitud(latitud);
        } else {
            tipoDir_id = Constant.ID_TDI_PRINCIPAL;
        }
        tipoDir.setTdiId(tipoDir_id);
        //Si la Ubicacion no existe se debe crear
        if (ubi_id.equals(BigDecimal.ZERO)) {
            ubicacion.setTubId(tipoUbi);
            ubicacion.setUbiLatitud(latitud);
            ubicacion.setUbiLongitud(longitud);
            ubicacion.setGpoId(geo_politico);
            ubicacion.setGeoId(geografico);
            ubicacion.setUbiUsuarioCreacion(user);
            //Inicio Modificacion face I carlos villamil 2012-12-14
            //Modigicar codigo para que la solictud traiga los datos necesarios de zona poblada y distrito postal.
            ubicacion.setUbiDistritoCodigoPostal("0000");
            ubicacion.setUbiZonaDivipola("000");
            //Fin Modificacion face I carlos villamil 2012-12-14
            ubi_id = insertUbicacion(nombreFuncionalidad, ubicacion);
            ubicacion.setUbiId(ubi_id);
            
        } else {
            ubicacion.setUbiId(ubi_id);
        }
        if (!ubi_id.equals(BigDecimal.ZERO)) {
            ubicacion.setUbiId(ubi_id);
        }
        dir.setDirServinformacion(solicitud.getSonPlaca());
        dir.setUbicacion(ubicacion);
        dir.setTipoDireccion(tipoDir);
        dir.setDirFormatoIgac(solicitud.getSonFormatoIgac());
        dir.setDirNostandar(solicitud.getSonNostandar());
        if (solicitud.getSonFuente() != null) {
            dir.setDirOrigen(solicitud.getSonFuente());
        } else {
            dir.setDirOrigen("");
        }
        //TODO: Validar el mapeo de Confiabilidad
        if (solicitud.getSonConfiabilidad() != null) {
            if (solicitud.getSonConfiabilidad().toString().equals("") || solicitud.getSonConfiabilidad().toString().equals("0")) {
                dir.setDirConfiabilidad(BigDecimal.ZERO);
            } else {
                dir.setDirConfiabilidad(solicitud.getSonConfiabilidad());
            }
        } else {
            dir.setDirConfiabilidad(BigDecimal.ZERO);
        }
        if (solicitud.getSonNivSocioeconomico() == null) {
            dir.setDirNivelSocioecono(BigDecimal.ZERO);
        } else {
            dir.setDirNivelSocioecono(new BigDecimal(solicitud.getSonNivSocioeconomico()));
        }
        if (solicitud.getSonEstrato() != null) {
            if (solicitud.getSonEstrato().equals("")) {
                dir.setDirEstrato(BigDecimal.ZERO);
            } else {
                dir.setDirEstrato(new BigDecimal(solicitud.getSonEstrato()));
            }
        } else if (tipoDireccion.equals(Constant.DIR_INTRADUCIBLE)) {
            dir.setDirEstrato(new BigDecimal("-1"));
        } else {
            dir.setDirEstrato(BigDecimal.ZERO);
        }
        //Modificado fase 1 direcciones carlos villamil l012-12-14
        dir.setDirUsuarioCreacion(user);
        dir.setDirManzanaCatastral(solicitud.getSonManzanaCatastral());
        String revisarEncampo;
        if (solicitud.getSonDirValidada().equals("false")) {
            revisarEncampo = "1";
        } else {
            revisarEncampo = "0";
        }
        dir.setDirRevisar(revisarEncampo);
        dir.setDirActividadEconomica(solicitud.getSonActividadEconomica());
        // se debe modificar para que traiga el comentario de nivel socioeconomico en la solictud.
        dir.setDirComentarioNivelSocioEconomico("-------------");//Modificado fase 1 direcciones carlos villamil l012-12-14
        //Se guarda la Direccion en el repositorio
        if (dir.getUbicacion().getUbiId() != BigDecimal.ZERO) {
            dir_id = insertAddressOnRepository(nombreFuncionalidad, dir);
        }
        return dir_id;
    }

    /**
     * Guardar una dirección en el repositorio.
     *
     * @param detalle Entidad que contiene toda la información necesaria para
     * crear una dirección
     * @param tipoDireccion Tipo de direccion a almacenar.
     * @param user Usuario que solicita la creación de la dirección en
     * Repositorio.
     * @param nombreFuncionalidad Nombre de funcionalidad que solicita la
     * creación de la dirección en Repositorio.
     * @return id con el cual fue guardada la dirección en el repositorio.
     *
     */
    public BigDecimal saveAddressfromRed(DetalleSolicitud detalle, String tipoDireccion, String user, String nombreFuncionalidad) {

        BigDecimal dir_id = BigDecimal.ZERO;

        return dir_id;
    }

    /**
     *
     * @param geodata
     * @param respMesg
     * @param user
     * @param nombreFuncionalidad
     */
    private SubDireccion guardarSubDireccion(AddressGeodata geodataDir, AddressGeodata geodataSubDir,
            ResponseMessage respMesg, String user, String nombreFuncionalidad, DrDireccion dirNueva) {
        try {
            //Se arma la SubDireccion a ingresar en el repositorio
            SubDireccion subDireccion = new SubDireccion();
            Direccion dirConsultada;
            Direccion direccion = new Direccion();
            //Se consulta la direccion en el repositorio
            //dirConsultada = queryAddressOnRepository(geodataDir.getCodencont());
            //JDHT
            if (respMesg.getIdaddress() != null && !respMesg.getIdaddress().isEmpty()) {
                String idDireccionStr = respMesg.getIdaddress().substring(1);
                BigDecimal dirId = new BigDecimal(idDireccionStr);
                direccion.setDirId(dirId);
            }
            subDireccion.setDireccion(direccion);
            subDireccion.setSdiFormatoIgac(geodataSubDir.getDirtrad());
            subDireccion.setSdiUsuarioCreacion(user);
            subDireccion.setSdiServinformacion(concatenaSubDireccion(geodataSubDir.getCoddir(), dirNueva));
            if (!geodataSubDir.getNivsocio().isEmpty()) {
                subDireccion.setSdiNivelSocioecono(new BigDecimal(geodataSubDir.getNivsocio()));
                subDireccion.setSdiEstrato(new BigDecimal(geodataSubDir.getNivsocio()));
            } else {
                subDireccion.setSdiNivelSocioecono(new BigDecimal(-1));//Se envia negativo para el CASE en la DB
                subDireccion.setSdiEstrato(new BigDecimal(-1));
            }
            if (!geodataSubDir.getActeconomica().isEmpty()) {
                subDireccion.setSdiActividadEcono(new BigDecimal(geodataSubDir.getActeconomica()));
            }
            //Valida si trae un estrato de la funcionalidad queryAddressResultBatch
            if ("queryAddressResultBatch".equals(nombreFuncionalidad)) {
                if (this.Estrato.equals("N/A")) {//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                    subDireccion.setSdiEstrato(new BigDecimal(-1));//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                } else {//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                    subDireccion.setSdiEstrato(new BigDecimal(this.Estrato));
                }//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2 
            }
            // Inicio Modificado Carlos Villamil Fase I direcciones 2013-05-24 V 1.1           
            subDireccion.setNodoUno(geodataSubDir.getNodo1());
            subDireccion.setNodoDos(geodataSubDir.getNodo2());
            subDireccion.setNodoTres(geodataSubDir.getNodo3());
            // Fin Modificado Carlos Villamil Fase I direcciones 2013-05-24   V 1.1
            subDireccion.setNodoDth(geodataSubDir.getNodoDth());
            subDireccion.setNodoMovil(geodataSubDir.getNodoMovil());
            subDireccion.setNodoFtth(geodataSubDir.getNodoFtth());
            subDireccion.setNodoWifi(geodataSubDir.getNodoWifi());
            //Inicio Cambio 1.6
            subDireccion.setGeoZona3G(geodataSubDir.getGeoZona3G());
            subDireccion.setGeoZona4G(geodataSubDir.getGeoZona4G());
            subDireccion.setGeoZona5G(geodataSubDir.getGeoZona5G());
            subDireccion.setGeoZonaCoberturaCavs(geodataSubDir.getGeoZonaCoberturaCavs());
            subDireccion.setGeoZonaCoberturaUltimaMilla(geodataSubDir.getGeoZonaCoberturaUltimaMilla());
            subDireccion.setGeoZonaCurrier(geodataSubDir.getGeoZonaCurrier());
            subDireccion.setGeoZonaGponDiseniado(geodataSubDir.getGeoZonaGponDiseniado());
            subDireccion.setGeoZonaMicroOndas(geodataSubDir.getGeoZonaMicroOndas());
            subDireccion.setGeoZonaUnifilar(geodataSubDir.getGeoZonaUnifilar());
            //Se agrega confiabilidad de la subdirección.
            if (geodataSubDir.getValagreg() != null && !geodataSubDir.getValagreg().isEmpty()) {
                subDireccion.setSdiConfiabilidad(new BigDecimal(geodataSubDir.getValagreg()));
            } else {
                subDireccion.setSdiConfiabilidad(new BigDecimal(0));
            }
            //FIN Cambio 1.6

            if (geodataSubDir.getComentEconomicLevel() != null && !geodataSubDir.getComentEconomicLevel().isEmpty()) {
                subDireccion.setSdiComentarioNivelSocioeconomico(geodataSubDir.getComentEconomicLevel());//Modificado Carlos Villamil Fase I direcciones 2012-12-18
            } else {
                subDireccion.setSdiComentarioNivelSocioeconomico("");
            }
            //Se guarda la Direccion en el repositorio
            BigDecimal dir_id = insertSubAddressOnRepository(nombreFuncionalidad, subDireccion);
            //Se crea el msg de respuesta
            if (dir_id != null) {
                respMesg.setMessageType(ResponseMessage.TYPE_SUCCESSFUL);
                respMesg.setMessageText(ResponseMessage.MESSAGE_SUCCESSFUL);
                respMesg.setIdaddress("s" + dir_id.toString());
                respMesg.setAddress(subDireccion.getSdiFormatoIgac());
                //TODO: Validar este qualifier
                respMesg.setQualifiers(geodataSubDir.getValplaca());
                return subDireccion;
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
        }
        return null;
    }

    /**
     *
     * @param geodata
     * @param respMesg
     * @param user
     * @param nombreFuncionalidad
     */
    private SubDireccion guardarSubDireccionIntraducible(AddressGeodata geodata, ResponseMessage respMesg,
            String user, String nombreFuncionalidad, Direccion dirConsultada, String drDireccionIntra,
            DrDireccion dirNueva) {
        SubDireccion subDireccion = new SubDireccion();
        try {
            //Se arma la SubDireccion a ingresar en el repositorio
            if (dirConsultada != null) {
                dirConsultada.setDirId(dirConsultada.getDirId());
            }
            subDireccion.setDireccion(dirConsultada);
            subDireccion.setSdiFormatoIgac(drDireccionIntra);
            subDireccion.setSdiUsuarioCreacion(user);
            subDireccion.setSdiServinformacion("");//sin codigo ya que servi informacion no es capas de traducir           
            subDireccion.setSdiNivelSocioecono(new BigDecimal(-1));//Se envia negativo para el CASE en la DB
            subDireccion.setSdiEstrato(new BigDecimal(-1));
            subDireccion.setSdiActividadEcono(new BigDecimal(-1));
            //Valida si trae un estrato de la funcionalidad queryAddressResultBatch
            if ("queryAddressResultBatch".equals(nombreFuncionalidad)) {
                if (this.Estrato.equals("N/A")) {//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                    subDireccion.setSdiEstrato(new BigDecimal(-1));//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                } else {//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2
                    subDireccion.setSdiEstrato(new BigDecimal(this.Estrato));
                }//Modificado fase 1 direcciones carlos villamil l012-12-14 V 1.2 
            }
            // Inicio Modificado Carlos Villamil Fase I direcciones 2013-05-24 V 1.1           
            subDireccion.setNodoUno(geodata.getNodo1());
            subDireccion.setNodoDos(geodata.getNodo2());
            subDireccion.setNodoTres(geodata.getNodo3());
            // Fin Modificado Carlos Villamil Fase I direcciones 2013-05-24   V 1.1
            subDireccion.setNodoDth(geodata.getNodoDth());
            subDireccion.setNodoMovil(geodata.getNodoMovil());
            subDireccion.setNodoFtth(geodata.getNodoFtth());
            subDireccion.setNodoWifi(geodata.getNodoWifi());
            subDireccion.setSdiComentarioNivelSocioeconomico("");
            //Se guarda la Direccion en el repositorio
            BigDecimal dir_id = insertSubAddressOnRepository(nombreFuncionalidad, subDireccion);
            //Se crea el msg de respuesta
            if (dir_id != null) {
                respMesg.setMessageType(ResponseMessage.TYPE_SUCCESSFUL);
                respMesg.setMessageText(ResponseMessage.MESSAGE_SUCCESSFUL);
                respMesg.setIdaddress("s" + dir_id.toString());
                respMesg.setAddress(subDireccion.getSdiFormatoIgac());
                //TODO: Validar este qualifier
                respMesg.setQualifiers(geodata.getValplaca());
            }
            return subDireccion;
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
        }
        return null;
    }

    /**
     * Guardar una subdirección en el repositorio.
     *
     * @param solicitud Objeto que contiene toda la información necesaria para
     * crear una dirección
     * @param user Usuario que solicita la creación
     * @param nombreFuncionalidad Nombre de la funcionalidad que solicita la
     * creación de Subdirección
     * @param id_Direccion
     * @return Mensaje informando resultado de la operacion
     */
    public BigDecimal saveSubAddress(SolicitudNegocio solicitud, String user, String nombreFuncionalidad, BigDecimal id_Direccion) {
        BigDecimal id_subDir = BigDecimal.ZERO;
        try {
            //Se arma la SubDireccion a ingresar en el repositorio
            SubDireccion subDireccion = new SubDireccion();
            Direccion direccion = new Direccion();
            if (id_Direccion != null) {
                direccion.setDirId(id_Direccion);
                subDireccion.setDireccion(direccion);
                subDireccion.setSdiFormatoIgac(solicitud.getSonFormatoIgac());
                subDireccion.setSdiCodigoPostal(solicitud.getSonZipcode());
                subDireccion.setSdiServinformacion(solicitud.getSonServinformacion());
                if (solicitud.getSonEstrato() != null) {
                    subDireccion.setSdiEstrato(new BigDecimal(solicitud.getSonEstrato()));
                }
                if (solicitud.getSonNivSocioeconomico() != null) {
                    subDireccion.setSdiNivelSocioecono(new BigDecimal(solicitud.getSonNivSocioeconomico()));
                }
                if (solicitud.getSonActividadEconomica() != null) {
                    subDireccion.setSdiActividadEcono(new BigDecimal(solicitud.getSonActividadEconomica()));
                }
                subDireccion.setSdiUsuarioCreacion(user);

                subDireccion.setSdiComentarioNivelSocioeconomico("Modificar par HHPP");//Modificado Carlos Villamil Fase I direcciones 2012-12-18

                //Se guarda la Direccion en el repositorio
                if (subDireccion.getDireccion().getDirId() != BigDecimal.ZERO) {
                    id_subDir = insertSubAddressOnRepository(nombreFuncionalidad, subDireccion);
                }
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return id_subDir;
    }

    /**
     * Guardar una subdirección en el repositorio partiendo de un detalle de
     * solicitud de Red.
     *
     * @param detalle Objeto que contiene toda la información necesaria para
     * crear una sub dirección
     * @param user Usuario que solicita la creación
     * @param nombreFuncionalidad Nombre de la funcionalidad que solicita la
     * creación de Subdirección
     * @param id_Direccion
     * @return Mensaje informando resultado de la operacion
     */
    public BigDecimal saveSubAddressfromRed(DetalleSolicitud detalle, String user, String nombreFuncionalidad, BigDecimal id_Direccion) {
        BigDecimal id_subDir = BigDecimal.ZERO;

        return id_subDir;
    }

    /**
     *
     * @param formatoIgac
     * @param respMesg
     * @return
     */
    private Direccion validarExistenciaDirIntraducibleOnRepo(String formatoIgac, String codigoDane, ResponseMessage respMesg) {
        Direccion direccion = null;
        //Se consulta la direccion en el repositorio por la direccion ingresada como formato_igac
        try {
            direccion = queryAddressIntraducibleOnRepoByIgac(formatoIgac, codigoDane);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
        }
        return direccion;
    }

    /**
     *
     * @param geodata
     * @param geodataEnOtraMalla
     * @param respMesg
     * @return
     */
    private String validarExistenciaDireccionOnRepo(AddressGeodata geodata,
            AddressGeodata geodataEnOtraMalla, ResponseMessage respMesg, BigDecimal centroPobladoId) {
        String tipoDireccion = Constant.DIR_NO_EXISTE_MSJ;
        Direccion direccion = null;
        Direccion direccionEnOtraMalla = null;
        SubDireccion subDireccion = null;
        //lógica para consultar si existe una versión de la direccion en la otra malla
        if (geodataEnOtraMalla != null) {
            String codigoPlacaEnOtraMalla = geodataEnOtraMalla.getCodencont();
            try {
                direccionEnOtraMalla = queryAddressOnRepository(geodata.getDiralterna(), centroPobladoId);
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
            }
            if (direccionEnOtraMalla != null) {
                tipoDireccion = DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO;
                respMesg.setIdaddress("d" + direccionEnOtraMalla.getDirId().toString());
                return tipoDireccion;
            }
        }
        //Se consulta la direccion en el repositorio
        String codigoPlacaDir = geodata.getCodencont();
        String codigoAddressDir = geodata.getCoddir();
        String complemento = codigoAddressDir.substring(codigoPlacaDir.length());
        try {
            direccion = queryAddressOnRepository(geodata.getDirtrad(),centroPobladoId);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
        }
        if (direccion == null) {
            if (isSoloCeros(complemento)) {//Es una direccion y se debe crear en DIRECCION
                tipoDireccion = Constant.DIRECCION;
            } else {//Es una Subdireccion y se debe crear DIRECCION y SUBDIRECCION
                tipoDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
            }
        } else {
            if (isSoloCeros(complemento)) {//Ya existe la direccion y NO SE ALMACENA
                tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                respMesg.setIdaddress("d" + direccion.getDirId().toString());
            } else {//Es una sub-direccion y se consulta como tal
                try {
                    subDireccion = querySubAddressOnRepoByCod(geodata.getDirtrad(),centroPobladoId);
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                }
                if (subDireccion == null) {//Es sub-direccion y se almacena en SUBDIRECCION
                    tipoDireccion = Constant.SUB_DIRECCION;
                } else {//Ya existe la subdireccion y NO SE ALMACENA
                    tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                    respMesg.setIdaddress("s" + subDireccion.getSdiId().toString());
                }
            }
        }
        return tipoDireccion;
    }

    /**
     *
     * @param geodata
     * @param geodataEnOtraMalla
     * @param respMesg
     * @return
     */
    private String validarExistenciaDireccionOnRepo(AddressGeodata geodataDir, AddressGeodata geodataSubDir,
            AddressGeodata geodataEnOtraMalla, ResponseMessage respMesg, DrDireccion dirNueva, BigDecimal centroPobladoId) {
        boolean esSubDireccion = !direccionCampo5(dirNueva);
        String tipoDireccion = Constant.DIR_NO_EXISTE_MSJ;//
        Direccion direccion = null;
        Direccion direccionEnOtraMalla = null;
        SubDireccion subDireccion = null;
        //lógica para consultar si existe una versión de la direccion en la otra malla
        if (geodataEnOtraMalla != null) {
            String codigoPlacaEnOtraMalla = geodataEnOtraMalla.getCodencont();
            try {
                direccionEnOtraMalla = queryAddressOnRepository(geodataEnOtraMalla.getDirtrad(), centroPobladoId );
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
            }
            if (direccionEnOtraMalla != null) {
                tipoDireccion = DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO;
                respMesg.setIdaddress("d" + direccionEnOtraMalla.getDirId().toString());
                return tipoDireccion;
            }
        }
        //Se consulta la direccion en el repositorio
        try {
            direccion = queryAddressOnRepository(geodataDir.getDirtrad(), centroPobladoId);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText("Error consultando la direccion en el sistema");
        }
        if (direccion == null) {
            if (!esSubDireccion) {//Es una direccion y se debe crear en DIRECCION
                tipoDireccion = Constant.DIRECCION;
            } else {//Es una Subdireccion y se debe crear DIRECCION y SUBDIRECCION
                tipoDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
            }
        } else {
            if (!esSubDireccion) {//Ya existe la direccion y NO SE ALMACENA
                tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                respMesg.setIdaddress("d" + direccion.getDirId().toString());
            } else {//Es una sub-direccion y se consulta como tal
                try {
                    subDireccion = querySubAddressOnRepoByCod(concatenaSubDireccion(geodataSubDir.getDirtrad(), dirNueva),centroPobladoId);
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                }
                if (subDireccion == null) {//Es sub-direccion y se almacena en SUBDIRECCION
                    tipoDireccion = Constant.SUB_DIRECCION;
                } else {//Ya existe la subdireccion y NO SE ALMACENA
                    tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                    respMesg.setIdaddress("s" + subDireccion.getSdiId().toString());
                }
            }
        }
        return tipoDireccion;
    }

    /**
     *
     * @param geodata
     * @param geodataEnOtraMalla
     * @param respMesg
     * @return
     */
    private String validarExistenciaDireccionOnRepoDESDEA(AddressGeodata geodata,
            AddressGeodata geodataEnOtraMalla, ResponseMessage respMesg, DrDireccion dirNueva, BigDecimal centroPobladoId) {
        String tipoDireccion = Constant.DIR_NO_EXISTE_MSJ;
        Direccion direccion = null;
        Direccion direccionEnOtraMalla = null;
        SubDireccion subDireccion = null;
        //lógica para consultar si existe una versión de la direccion en la otra malla
        if (geodataEnOtraMalla != null) {
            String codigoPlacaEnOtraMalla = geodataEnOtraMalla.getCodencont();
            try {
                direccionEnOtraMalla = queryAddressOnRepository(geodata.getDiralterna(),centroPobladoId );
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
            }
            if (direccionEnOtraMalla != null) {
                tipoDireccion = DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO;
                respMesg.setIdaddress("d" + direccionEnOtraMalla.getDirId().toString());
                return tipoDireccion;
            }
        }
        //Se consulta la direccion en el repositorio
        String codigoPlaca = geodata.getCodencont();
        String codigoAddress = geodata.getCoddir();
        try {
            direccion = queryAddressOnRepository(geodata.getDirtrad(),centroPobladoId);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
        }
        if (direccion == null) {
            if (direccionCampo5(dirNueva)) {
                //Es una direccion y se debe crear en DIRECCION
                tipoDireccion = Constant.DIRECCION;
            } else {
                //Es una Subdireccion y se debe crear DIRECCION y SUBDIRECCION
                tipoDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
            }
        } else {
            if (direccionCampo5(dirNueva)) {
                //Ya existe la direccion y NO SE ALMACENA
                tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                respMesg.setIdaddress("d" + direccion.getDirId().toString());
            } else {
                //Es una sub-direccion y se consulta como tal
                try {
                    subDireccion = querySubAddressOnRepoByCod(codigoAddress, centroPobladoId);
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                }
                if (subDireccion == null) {
                    //Es sub-direccion y se almacena en SUBDIRECCION
                    tipoDireccion = Constant.SUB_DIRECCION;
                } else {
                    //Ya existe la subdireccion y NO SE ALMACENA
                    tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                    respMesg.setIdaddress("s" + subDireccion.getSdiId().toString());
                }
            }
        }

        return tipoDireccion;
    }

    /**
     *
     * @param codigoPlaca
     * @param codigoAddress
     * @param complemento
     * @return
     */
    public String validarTipoDireccion(String codigoPlaca, String codigoAddress, String complemento) {
        String tipoDireccion;
        if (codigoPlaca != null || codigoAddress != null || complemento != null) {
            if (codigoPlaca.equals("") && codigoAddress.equals("") && complemento.equals("")) {
                tipoDireccion = Constant.DIR_INTRADUCIBLE;
            } else {
                if (isSoloCeros(complemento)) {
                    //Es una direccion y se debe crear en DIRECCION
                    tipoDireccion = Constant.DIRECCION;
                } else {
                    //Es una Subdireccion y se debe crear DIRECCION y SUBDIRECCION
                    tipoDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
                }
            }
        } else {
            tipoDireccion = Constant.DIR_INTRADUCIBLE;
        }

        return tipoDireccion;
    }

    /**
     * Valida si se debe marcar la direccion para revisar en campo
     *
     * @param estado
     * @param codDirecion
     * @return
     * @throws Exception
     */
    private String validarRevisar(String estado, String codDirecion, String valComplemento, BigDecimal centroPobladoId) throws ApplicationException {
        String revisar = "0";
        String idaddress;
        //Si el estado es intraducible, debe ser revisada en campo
        if (Constant.DIR_ESTADO_INTRADUCIBLE.equals(estado)) {
            revisar = "1";
        }
        //Si no existe en repositorio y no existe en servinformacion, se debe marcar revisar
        Direccion direccion = queryAddressOnRepository(codDirecion,centroPobladoId);
        if (direccion == null) {
            idaddress = "0";
        } else {
            idaddress = direccion.getDirId().toString();
        }

        boolean exist = validExistAddress(codDirecion, estado, valComplemento);

        if (exist == Boolean.FALSE && "0".equals(idaddress)) {
            revisar = "1";
        }
        return revisar;
    }

    /**
     *
     * @param latitud
     * @param longitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    @Override
    public BigDecimal validarCxCyOnRepository(String latitud, String longitud) throws ApplicationException {
        BigDecimal ubi_id = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("ubi1", latitud, longitud);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                ubi_id = obj.getBigDecimal("UBICACION_ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de validar el CX CY. EX000: " + e.getMessage(), e);
        }
        return ubi_id;
    }

    /**
     * Cargar una dirección segun Id de ubicación
     *
     * @param id_ubi Id de la ubicación a buscar en direcciones
     * @return lista de direcciones que referencian esa ubicación
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Direccion> findAddressByIdUbi(BigDecimal id_ubi) throws ApplicationException {
        List<Direccion> listDirecciones = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("dir13", id_ubi.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listDirecciones = new ArrayList<Direccion>();
                for (DataObject obj : list.getList()) {
                    BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                    BigDecimal tdi_id = obj.getBigDecimal("TDI_ID");
                    String tdi_valor = obj.getString("TDI_VALOR");
                    BigDecimal ubi_id = obj.getBigDecimal("UBI_ID");
                    String dir_igac = obj.getString("DIR_FORMATO_IGAC");
                    String dir_servi = obj.getString("DIR_SERVINFORMACION");
                    String dir_noStan = obj.getString("DIR_NOSTANDAR");
                    Direccion direccion = new Direccion();
                    direccion.setDirId(dir_id);
                    TipoDireccion tipoDir = new TipoDireccion();
                    tipoDir.setTdiId(tdi_id);
                    tipoDir.setTdiValor(tdi_valor);
                    direccion.setTipoDireccion(tipoDir);
                    Ubicacion ubicacion = new Ubicacion();
                    ubicacion.setUbiId(ubi_id);
                    direccion.setUbicacion(ubicacion);
                    direccion.setDirFormatoIgac(dir_igac);
                    direccion.setDirServinformacion(dir_servi);
                    direccion.setDirNostandar(dir_noStan);
                    listDirecciones.add(direccion);
                }
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en setValue. EX000 " + e.getMessage(), e);
        }
        return listDirecciones;
    }

    /**
     *
     * @param tipoDireccion
     * @return
     * @throws ExceptionDB
     */
    private BigDecimal queryTipoDireccionByEstado(String tipoDireccion) throws ApplicationException {
        BigDecimal tdi_id = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("tdi1", tipoDireccion);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tdi_id = obj.getBigDecimal("TIPO_DIRECCION_ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el tipo de dirección. EX000: " + e.getMessage(), e);
        }
        return tdi_id;
    }

    /**
     *
     * @param CodDivipola
     * @return
     * @throws ExceptionDB
     */
    private BigDecimal queryGeograficoPoliticoByDivipola(String CodDivipola) throws ApplicationException {
        BigDecimal tdi_id = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo8", CodDivipola);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tdi_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el geográfico político. EX000: " + e.getMessage(), e);
        }
        return tdi_id;
    }

    /**
     * Descripción del objetivo del método. Busca en geografico politico el
     * nombre del departamento y ciudad para determinar el id de la ciudad
     * geografico politico.
     *
     * @author carlos Leonardo Villamil
     * @param [state] parametro que cotiene el departamento.
     * @param [city] parametro que cotiene el ciudad.
     * @return BigDecimal id de la ciudad.
     * @throws [ExceptionDB], Error de bae de datos.
     */
    private BigDecimal queryGeograficoPoliticoByStateCityName(String state, String city)
            throws ApplicationException {
        BigDecimal tdi_id = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo13", city, state);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tdi_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el geográfico político. EX000: " + e.getMessage(), e);
        }
        return tdi_id;
    }

    /**
     *
     * @param CodDivipola
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    @Override
    public String queryMultiorigenByDivipola(String CodDivipola) throws ApplicationException {
        String resultado = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo12", CodDivipola);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                resultado = obj.getString("MULTIORIGEN");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el multiorigen. EX000: " + e.getMessage(), e);
        }
        return resultado;
    }

    /**
     *
     * @param nombreLocalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal queryGeograficoLocalidadByNombre(String nombreLocalidad) throws ApplicationException {
        BigDecimal tdi_id = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo3", "%" + nombreLocalidad + "%");
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tdi_id = obj.getBigDecimal("GEOGRAFICO_ID");
            }
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryGeograficoLocalidadByNombre. EX000 " + ex.getMessage(), ex);
        }
        return tdi_id;
    }

    /**
     *
     * @param idLocalidad
     * @param barrio
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal queryGeograficoBarrioByIDLocalidad(String idLocalidad, String barrio) throws ApplicationException {
        BigDecimal idBarrio = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo4", idLocalidad, "%" + barrio + "%");
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                idBarrio = obj.getBigDecimal("GEOGRAFICO_ID");
            }
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryGeograficoBarrioByIDLocalidad. EX000 " + ex.getMessage(), ex);
        }
        return idBarrio;
    }

    /**
     *
     * @param idBarrio
     * @param manzana
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal queryGeograficoMzaByIdBarrio(String idBarrio, String manzana) throws ApplicationException {
        BigDecimal idMza = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo5", idBarrio, "%" + manzana + "%");
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                idMza = obj.getBigDecimal("GEOGRAFICO_ID");
            }
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryGeograficoMzaByIdBarrio. EX000 " + ex.getMessage(), ex);
        }
        return idMza;
    }

    /**
     *
     * @param nombreFuncionalidad
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal insertAddressOnRepository(String nombreFuncionalidad, Direccion direccion) throws ApplicationException {
        BigDecimal idDir = null;
        AccessData adb = null;
        try {
            // ya pase guardando mgl_direccion en repo
            BigDecimal tipoDireccion = direccion.getTipoDireccion().getTdiId();
            BigDecimal idUbicacion = direccion.getUbicacion().getUbiId();
            String dirFormatoIgac = direccion.getDirFormatoIgac();
            String dirServinformacion = direccion.getDirServinformacion();
            String dirNoestandar = direccion.getDirNostandar();
            String dirOrigen = direccion.getDirOrigen() != null
                    && !direccion.getDirOrigen().trim().isEmpty() ? direccion.getDirOrigen() : "NUEVA";
            BigDecimal dirConfiabilidad = direccion.getDirConfiabilidad();
            //Inicio Modificado fase 1 direcciones carlos villamil l013-05-23 V 1.1
            String NodoUno = direccion.getNodoUno();
            String NodoDos = direccion.getNodoDos();
            String NodoTres = direccion.getNodoTres();

            String nodoDth = direccion.getNodoDth();
            String nodoMovil = direccion.getNodoMovil();
            String nodoFtth = direccion.getNodoFtth();
            String nodoWifi = direccion.getNodoWifi();

            //Fin Modificado fase 1 direcciones carlos villamil l013-05-23 V 1.1
            String zona3G = direccion.getGeoZona3G();
            String zona4G = direccion.getGeoZona4G();
            String zona5G = direccion.getGeoZona5G();
            String zonaCoberturaCavs = direccion.getGeoZonaCoberturaCavs();
            String zonaCoberturaUltimaMilla = direccion.getGeoZonaCoberturaUltimaMilla();
            String zonaCurrier = direccion.getGeoZonaCurrier();
            String zonaGponDiseniado = direccion.getGeoZonaGponDiseniado();
            String zonaMicroOndas = direccion.getGeoZonaMicroOndas();
            String zonaUnifilar = direccion.getGeoZonaUnifilar();

            BigDecimal estrato = null;
            BigDecimal nivsocioE = null;
            String actEconomica;
            if (direccion.getDirNivelSocioecono().toString() != null) {
                nivsocioE = direccion.getDirNivelSocioecono();//.toString();
            }

            if (direccion.getDirEstrato() != null) {
                estrato = direccion.getDirEstrato();//.toString();
            }
            String mzacatastral = direccion.getDirManzanaCatastral();
            String manzana = direccion.getDirManzana();
            String usuario = direccion.getDirUsuarioCreacion();
            String revisarEnCampo = direccion.getDirRevisar();
            String comentarioNivelSocioEconomico = direccion.getDirComentarioNivelSocioEconomico();
            actEconomica = direccion.getDirActividadEconomica();

            //cambio de persistencia de direccion a JPA
            DireccionMgl dir = new DireccionMgl();
            TipoDireccionMgl tipoDir = new TipoDireccionMgl();
            tipoDir.setTdiId(tipoDireccion);
            dir.setTipoDirObj(tipoDir);
            UbicacionMgl ubicacionMgl = new UbicacionMgl();
            ubicacionMgl.setUbiId(idUbicacion);
            dir.setDirFormatoIgac(dirFormatoIgac);
            dir.setDirServinformacion(dirServinformacion);
            dir.setDirNostandar(dirNoestandar);
            dir.setDirOrigen(dirOrigen);
            dir.setDirConfiabilidad(dirConfiabilidad);
            dir.setDirEstrato(estrato);
            dir.setDirNivelSocioecono(nivsocioE);
            dir.setDirManzanaCatastral(mzacatastral);
            dir.setDirManzana(manzana);
            dir.setUsuarioCreacion(usuario);
            dir.setFechaCreacion(new Date());
            dir.setDirRevisar(revisarEnCampo);
            dir.setDirActividadEcono(actEconomica);
            dir.setDirComentarioSocioeconomico(comentarioNivelSocioEconomico);
            dir.setDirNodouno(NodoUno);
            dir.setDirNododos(NodoDos);
            dir.setDirNodotres(NodoTres);
            dir.setDirNodoDth(nodoDth);
            dir.setDirNodoMovil(nodoMovil);
            dir.setDirNodoFtth(nodoFtth);
            dir.setDirNodoWifi(nodoWifi);
            dir.setGeoZona3G(zona3G);
            dir.setGeoZona4G(zona4G);
            dir.setGeoZona5G(zona5G);
            dir.setGeoZonaCoberturaCavs(zonaCoberturaCavs);
            dir.setGeoZonaCoberturaUltimaMilla(zonaCoberturaUltimaMilla);
            dir.setGeoZonaCurrier(zonaCurrier);
            dir.setGeoZonaGponDiseniado(zonaGponDiseniado);
            dir.setGeoZonaMicroOndas(zonaMicroOndas);
            dir.setGeoZonaUnifilar(zonaUnifilar);
            dir.setUbicacion(ubicacionMgl);

            direccionManager = new DireccionMglManager();
            dir = direccionManager.create(dir);
            idDir = dir.getDirId();
            if (idDir != null) {
                direccion.setDirId(idDir);
                Direccion dirAuditoria = queryAddressOnRepoById(idDir.toString());
                AuditoriaEJB audi = new AuditoriaEJB();
            }
            DireccionUtil.closeConnection(adb);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw ex;
        }
        return idDir;
    }

    /**
     *
     * @param adb
     * @return
     * @throws Exception
     */
    private BigDecimal getIdDireccion(AccessData adb) throws ApplicationException {
        BigDecimal id = null;
        try {
            DataObject obj = adb.outDataObjec("dir5");
            if (obj != null) {
                id = obj.getBigDecimal("ID");
            }
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en getIdDireccion. EX000 " + ex.getMessage(), ex);
        }
        return id;
    }

    /**
     *
     * @param adb
     * @return
     * @throws Exception
     */
    private BigDecimal getIdSubDireccion(AccessData adb) throws ApplicationException {
        BigDecimal id = null;
        try {
            DataObject obj = adb.outDataObjec("sdi7");
            if (obj != null) {
                id = obj.getBigDecimal("ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en getIdSubDireccion. EX000 " + e.getMessage(), e);
        }
        return id;
    }

    /**
     *
     * @param nombreFuncionalidad
     * @param ubicacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal insertUbicacion(String nombreFuncionalidad, Ubicacion ubicacion) throws ApplicationException {
        BigDecimal idUbi = null;
        try {
            String gpoId;
            if (ubicacion.getGpoId() != null && ubicacion.getGpoId().getGpoId() != null) {
                if (ubicacion.getGpoId().getGpoId().toString().equals("0")) {
                    gpoId = "";
                } else {
                    gpoId = ubicacion.getGpoId().getGpoId().toString();
                }
            } else {
                gpoId = "";
            }
            String geoId;
            if (ubicacion.getGeoId() != null && ubicacion.getGeoId().getGeoId() != null) {
                if (ubicacion.getGeoId().getGeoId().toString().equals("0")) {
                    geoId = "";
                } else {
                    geoId = ubicacion.getGeoId().getGeoId().toString();
                }
            } else {
                geoId = "";
            }

            String tipoUbicacion = (ubicacion.getTubId() != null && ubicacion.getTubId().getTubId() != null) ? ubicacion.getTubId().getTubId().toString() : "";

            String latitud = (ubicacion.getUbiLatitud() == null
                    || ubicacion.getUbiLatitud().trim().isEmpty()) ? "0" : ubicacion.getUbiLatitud();

            String longitud = (ubicacion.getUbiLongitud() == null
                    || ubicacion.getUbiLongitud().trim().isEmpty()) ? "0" : ubicacion.getUbiLongitud();

            String usuario = ubicacion.getUbiUsuarioCreacion();
            //Inicio Modificacion Fase I Carlos Villamil 2012-12-14
            String zonaDivipola = ubicacion.getUbiZonaDivipola();
            String distritoZip = ubicacion.getUbiDistritoCodigoPostal();
            //FIN Modificacion Fase I Carlos Villamil 2012-12-14
            if ("0".equals(gpoId)) {
                gpoId = "";
            }

            //Ajuste para persistir por JPA
            UbicacionMgl ubicacionMgl = new UbicacionMgl();
            if (gpoId != null
                    && !gpoId.trim().isEmpty()
                    && !gpoId.trim().equalsIgnoreCase("0")) {
                GeograficoPoliticoMgl geograficoPoliticoMgl
                        = new GeograficoPoliticoMgl();
                geograficoPoliticoMgl.setGpoId(new BigDecimal(gpoId));
                ubicacionMgl.setGpoIdObj(geograficoPoliticoMgl);
            }
            if (geoId != null
                    && !geoId.trim().isEmpty()
                    && !geoId.trim().equalsIgnoreCase("0")) {
                GeograficoMgl geograficoMgl = new GeograficoMgl();
                geograficoMgl.setGeoId(new BigDecimal(geoId));
                ubicacionMgl.setGeoIdObj(geograficoMgl);
            }
            if (tipoUbicacion != null
                    && !tipoUbicacion.trim().isEmpty()
                    && !tipoUbicacion.trim().equalsIgnoreCase("0")) {
                ubicacionMgl.setTubId(new BigDecimal(tipoUbicacion));
            }
            ubicacionMgl.setUbiLatitud(latitud);
            ubicacionMgl.setUbiLongitud(longitud);
            //JDHT
            ubicacionMgl.setUbiLatitudNum(new BigDecimal(latitud));
            ubicacionMgl.setUbiLongitudNum(new BigDecimal(longitud));

            ubicacionMgl.setUbiZonaDiviPola(zonaDivipola);
            ubicacionMgl.setUbiZonaDistritoCodigoZip(distritoZip);
            ubicacionMgl.setUbiUsuarioCreacion(usuario);
            ubicacionMgl.setUbiFechaCreacion(new Date());

            UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();
            ubicacionMgl = ubicacionMglManager.create(ubicacionMgl);
            idUbi = ubicacionMgl.getUbiId();

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en insertUbicacion. EX000 " + e.getMessage(), e);
        }
        return idUbi;
    }

    /**
     *
     * @param adb
     * @return
     * @throws Exception
     */
    private BigDecimal getIdUbicacion(AccessData adb) throws ApplicationException {
        BigDecimal id = null;
        try {
            DataObject obj = adb.outDataObjec("ubi4");
            if (obj != null) {
                id = obj.getBigDecimal("ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en getIdUbicacion. EX000 " + e.getMessage(), e);
        }
        return id;
    }

    /**
     *
     * @param nombreFuncionalidad
     * @param subDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal insertSubAddressOnRepository(String nombreFuncionalidad, SubDireccion subDireccion) throws ApplicationException {
        BigDecimal idSdi = null;
        AccessData adb = null;
        try {
            String idDireccion = subDireccion.getDireccion().getDirId().toString();
            String formatoIgac = subDireccion.getSdiFormatoIgac();
            String servinformacion = subDireccion.getSdiServinformacion();
            String comSocioEconomico = subDireccion.getSdiComentarioNivelSocioeconomico();//Modificacion Carlos Vilamil Primera fase 2012-12-18
            BigDecimal estrato = null;
            // Inicio Modificado Carlos Villamil Fase I direcciones 2013-05-24   V 1.1        
            String nodoUno = subDireccion.getNodoUno();
            String nodoDos = subDireccion.getNodoDos();
            String nodoTres = subDireccion.getNodoTres();
            // Fin Modificado Carlos Villamil Fase I direcciones 2013-05-24   V 1.1 

            String nodoDth = subDireccion.getNodoDth();
            String nodoMovil = subDireccion.getNodoMovil();
            String nodoFtth = subDireccion.getNodoFtth();
            String nodoWifi = subDireccion.getNodoWifi();

            String zona3G = subDireccion.getGeoZona3G();
            String zona4G = subDireccion.getGeoZona4G();
            String zona5G = subDireccion.getGeoZona5G();
            String zonaCoberturaCavs = subDireccion.getGeoZonaCoberturaCavs();
            String zonaCoberturaUltimaMilla = subDireccion.getGeoZonaCoberturaUltimaMilla();
            String zonaCurrier = subDireccion.getGeoZonaCurrier();
            String zonaGponDiseniado = subDireccion.getGeoZonaGponDiseniado();
            String zonaMicroOndas = subDireccion.getGeoZonaMicroOndas();
            String zonaUnifilar = subDireccion.getGeoZonaUnifilar();
            BigDecimal confiabilidadSub = subDireccion.getSdiConfiabilidad();

            if (subDireccion.getSdiEstrato() != null) {
                estrato = subDireccion.getSdiEstrato();//.toString();
            }
            BigDecimal nivSocioEco = null;
            if (subDireccion.getSdiNivelSocioecono() != null) {
                nivSocioEco = subDireccion.getSdiNivelSocioecono();//.toString();
            }
            BigDecimal actividadEco = null;
            if (subDireccion.getSdiActividadEcono() != null) {
                actividadEco = subDireccion.getSdiActividadEcono();//.toString();
            }
            String user = subDireccion.getSdiUsuarioCreacion();

            //cambio de persistencia de subdireccion a JPA
            SubDireccionMgl subDir = new SubDireccionMgl();
            subDir.setDirId(new BigDecimal(idDireccion));
            subDir.setSdiFormatoIgac(formatoIgac);
            subDir.setSdiServinformacion(servinformacion);
            subDir.setSdiEstrato(estrato);
            subDir.setSdiNivelSocioecono(nivSocioEco);
            subDir.setSdiActividadEcono(actividadEco);
            subDir.setUsuarioCreacion(user);
            subDir.setFechaCreacion(new Date());
            subDir.setSdiComentarioSocioeconomico(comSocioEconomico);
            subDir.setSdiNodouno(nodoUno);
            subDir.setSdiNododos(nodoDos);
            subDir.setSdiNodotres(nodoTres);

            subDir.setSdiNodoDth(nodoDth);
            subDir.setSdiNodoMovil(nodoMovil);
            subDir.setSdiNodoFtth(nodoFtth);
            subDir.setSdiNodoWifi(nodoWifi);

            subDir.setGeoZona3G(zona3G);
            subDir.setGeoZona4G(zona4G);
            subDir.setGeoZona5G(zona5G);
            subDir.setGeoZonaCoberturaCavs(zonaCoberturaCavs);
            subDir.setGeoZonaCoberturaUltimaMilla(zonaCoberturaUltimaMilla);
            subDir.setGeoZonaCurrier(zonaCurrier);
            subDir.setGeoZonaGponDiseniado(zonaGponDiseniado);
            subDir.setGeoZonaMicroOndas(zonaMicroOndas);
            subDir.setGeoZonaUnifilar(zonaUnifilar);
            subDir.setSdirConfiabilidad(confiabilidadSub);
            subDir = subDireccionManager.create(subDir);
            idSdi = subDir.getSdiId();

            if (idSdi != null) {
                ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
                subDireccion.setSdiId(idSdi);
                AuditoriaEJB audi = new AuditoriaEJB();
                SubDireccion subDirAuditoria = manager.querySubAddressOnRepositoryById(idSdi);
                audi.auditar(nombreFuncionalidad, Constant.SUB_DIRECCION, subDirAuditoria.getSdiUsuarioCreacion(), Constant.INSERT, subDirAuditoria.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de insertar la subdirección. EX000: " + e.getMessage(), e);
        }
        return idSdi;
    }

    /**
     *
     * @param codDir
     * @return
     * @throws Exception
     */
    private List<AddressAggregated> queryAddressAggregatedOnRepo(String codDir, BigDecimal centroPobladoId) throws ApplicationException {
        List<AddressAggregated> addressAggregated = null;
        //Se consulta la direccion en el repositorio por el codDir
        Direccion direccion = queryAddressOnRepository(codDir, centroPobladoId);
        if (direccion != null) {
            //Se consultan las subdirecciones asociadas a la direccion encontrada
            addressAggregated = querySubAddressOnRepoByIdDir(direccion.getDirId().toString());
        }

        return addressAggregated;
    }

    /**
     *
     * @param nombreFuncionalidad
     * @param geografico
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal insertGeograficoIntorepository(String nombreFuncionalidad, Geografico geografico) throws ApplicationException {
        BigDecimal idGeo = null;
        AccessData adb = null;
        try {
            String geo_geo_id = (geografico.getGeografico() != null && geografico.getGeografico().getGeoId() != null) ? geografico.getGeografico().getGeoId().toString() : "";
            String tipoGeografico = (geografico.getTipoGeografico() != null && geografico.getTipoGeografico().getTgeId() != null) ? geografico.getTipoGeografico().getTgeId().toString() : "";
            String geoPolitico = (geografico.getGeograficoPolitico() != null && geografico.getGeograficoPolitico().getGpoId() != null) ? geografico.getGeograficoPolitico().getGpoId().toString() : "";
            String nombre = geografico.getGeoNombre();
            String userCreacion = geografico.getGeoUsuarioCreacion();

            adb = ManageAccess.createAccessData();
            adb.in("geo6", geo_geo_id, tipoGeografico, geoPolitico, nombre, userCreacion);
            idGeo = getIdGeografico(adb);
            if (idGeo != null) {
                geografico.setGeoId(idGeo);
                AuditoriaEJB audi = new AuditoriaEJB();
            }
            DireccionUtil.closeConnection(adb);
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en insertGeograficoIntorepository. EX000 " + e.getMessage(), e);
        }
        return idGeo;
    }

    /**
     *
     * @param adb
     * @return
     * @throws Exception
     */
    private BigDecimal getIdGeografico(AccessData adb) throws ApplicationException {
        BigDecimal id = null;
        try {
            DataObject obj = adb.outDataObjec("geo7");
            if (obj != null) {
                id = obj.getBigDecimal("ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en getIdGeografico. EX000 " + e.getMessage(), e);
        }
        return id;
    }

    /**
     * Determina si la dirección es cuenta matriz
     *
     * @param codDireccion
     * @return
     * @throws ExceptionDB
     */
    private boolean queryCuentaMatriz(String codDireccion) throws ApplicationException {
        boolean isCtaMatriz = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("ubi2", codDireccion);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                String ctaMtz = obj.getString("CUENTA_MATRIZ");
                if (ctaMtz != null && ctaMtz.equals("1")) {
                    isCtaMatriz = true;
                }
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            isCtaMatriz = false;
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryCuentaMatriz. EX000 " + e.getMessage(), e);
        }
        return isCtaMatriz;
    }

    /**
     *
     * @param nombreCiudad
     * @return
     * @throws Exception
     */
    private boolean queryCiudadMultiorigen(String nombreCiudad) throws ApplicationException {
        boolean multiorigen = false;
        AccessData adb = null;
        try {
            String ciudad = null;
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo10", nombreCiudad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                ciudad = obj.getString("MULTIORIGEN");
                if (ciudad != null && "1".equals(ciudad)) {
                    multiorigen = Boolean.TRUE;
                }
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryCiudadMultiorigen. EX000 " + e.getMessage(), e);
        }
        return multiorigen;
    }

    /**
     *
     * @param nombreCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal getIdCiudadGeoPByName(String nombreCiudad) throws ApplicationException {

        BigDecimal idCiudad = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo10", nombreCiudad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                idCiudad = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en getIdCiudadGeoPByName. EX000 " + e.getMessage(), e);
        }
        return idCiudad;
    }

    /**
     *
     * @param idAddress
     * @return
     * @throws Exception
     */
    private List<AddressAggregated> querySubAddressOnRepoByIdDir(String idAddress) throws ApplicationException {
        List<AddressAggregated> listSubAddress = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sdi2", idAddress);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listSubAddress = new ArrayList<AddressAggregated>();
                for (DataObject obj : list.getList()) {
                    BigDecimal sdi_id = obj.getBigDecimal("SUB_DIRECCION_ID");
                    String sdi_igac = obj.getString("FORMATO_IGAC");
                    AddressAggregated subDireccion = new AddressAggregated();
                    subDireccion.setIdSubAddress(sdi_id.toString());
                    subDireccion.setAddress(sdi_igac);
                    listSubAddress.add(subDireccion);
                }
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en querySubAddressOnRepoByIdDir. EX000 " + e.getMessage(), e);
        }
        return listSubAddress;
    }

    /**
     * Permite Actualizar una direccion.Realiza la actualizacion de una
 direccion.
     *
     * @author Johnnatan Ortiz
     * @param idAdresses id de la direccion, .
     * @param user usuario que esta ejecutando la actualizacion.
     * @param applicationName aplicacion desde la cual se ejecuta la
     * actualizacion.
     * @param updateToNewAddress nueva direccion a la cual se actualizara.
     * @return si el proceso se realizo correctamente.
     */
    @Override
    public boolean updateAddresses(String idAdresses, String user,
            String applicationName, String updateToNewAddress) {
        if (idAdresses.contains("d")) {
            updateAddress(idAdresses.replace("d", ""),
                    user, applicationName, updateToNewAddress);
            return true;
        } else {
            updateSubAddress(new BigDecimal(idAdresses.replace("s", "")),
                    user, applicationName, updateToNewAddress);
            return true;
        }
    }

    /**
     *
     * @param idaddress
     * @param user
     * @param applicationName
     * @param updateToNewAddress
     * @return
     */
    @Override
    public ResponseMessage updateAddress(String idaddress, String user, String applicationName, String updateToNewAddress) {
        ResponseMessage respMesg = new ResponseMessage();
        Direccion direccion = null;
        String NewAddressString = "";
        try {
            direccion = queryAddressOnRepoById(idaddress);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(e.getMessage());
            return respMesg;
        }
        //Se verifica que se cargaron los datos de la direccion desde el repositorio    
        if (direccion != null) {
            AddressGeodata geodata = null;
            String departamento = null;
            String ciudad = null;
            AddressRequest request = new AddressRequest();
            String idCiudad = null;
            try {
                idCiudad = direccion.getUbicacion().getGpoId().getGpoId().toString();
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                respMesg.setMessageType(ResponseMessage.MESSAGE_ERROR);
                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_INVALIDA);
            }

            if (idCiudad != null) {
                try {
                    ciudad = queryGeograficoPoliticoById(idCiudad);
                    departamento = queryDepartamentoByIdCiudad(idCiudad);
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(e.getMessage());
                }
            }
            request.setAddress(direccion.getDirFormatoIgac());
            request.setState(departamento);
            request.setCity(ciudad);

            try {
                geodata = queryAddressGeodata(request);
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                respMesg.setMessageText(e.getMessage());
            }

            if (geodata != null) {
                //Si existe una version mas nueva 
                if (!geodata.getDiralterna().isEmpty() && geodata.getFuente().equals(Constant.DIR_FUENTE_ANTIGUA)) {

                    //Se consultan los datos de la nueva direccion
                    String nuevaDir = geodata.getDiralterna();
                    boolean exitoso = false;
                    request.setAddress(geodata.getDiralterna());
                    AddressGeodata geodataDireccionNueva = null;
                    try {
                        geodataDireccionNueva = queryAddressGeodata(request);
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                        LOGGER.error(msg);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(e.getMessage());
                    }
                    //Se acutualiza la direccion con los nuevos datos
                    if (geodataDireccionNueva != null) {
                        //Se crea una copia de la direccion
                        Direccion dirNueva = mapearDireccionNueva(direccion, geodataDireccionNueva);
                        //Si se desea actualizar a la versión más NUEVA
                        if (dirNueva.getDirOrigen().equals("NUEVA")) {
                            //Guardo en alternas y actualizo todos los cmpos de la direccion
                            //Se debe almacenar la antigua en la tabla de alternas y se debe copiar la nueva en la direccion actual
                            BigDecimal dirAlternaId;
                            try {
                                DireccionAlterna dirAlterna = new DireccionAlterna();
                                dirAlterna.setDiaUsuarioCreacion(user);
                                dirAlterna.setDiaDireccionIgac(direccion.getDirFormatoIgac());
                                dirAlterna.setDireccion(direccion);

                                dirAlternaId = insertIntoDireccionesAgregadas(applicationName, user, dirAlterna);
                            } catch (ApplicationException e) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                                respMesg.setMessageText(e.getMessage());
                                return respMesg;
                            }

                            //Se creo exitosamente la direccion alterna, ahora se debe copiar la direccion nueva en la antigua
                            if (dirAlternaId != null) {
                                try {
                                    exitoso = updateAddressOnRepoById(idaddress, dirNueva, applicationName, user);
                                    NewAddressString = dirNueva.getDirFormatoIgac();
                                } catch (ApplicationException e) {
                                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                    LOGGER.error(msg);
                                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                                    respMesg.setMessageText(e.getMessage());
                                    return respMesg;
                                }
                            }
                        }
                    }
                    if (exitoso) {
                        respMesg.setMessageType(ResponseMessage.TYPE_SUCCESSFUL);
                        respMesg.setMessageText(ResponseMessage.MESSAGE_SUCCESSFUL + ". La dirección actualizada es: " + NewAddressString);
                        respMesg.setIdaddress(idaddress);
                        respMesg.setAddress(nuevaDir);
                        respMesg.setQualifiers(geodataDireccionNueva.getValplaca());
                        return respMesg;
                    } else {
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                        return respMesg;
                    }
                } else {
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_SIN_DIR_NUEVA);
                    return respMesg;
                }
            } else {
                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_FUERA_SERVICIO);
                return respMesg;
            }
        } else {
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_REPOSITORIO);

        }
        return respMesg;
    }

    public ResponseMessage updateSubAddress(BigDecimal iSubdAddress,
            String user, String applicationName, String updateToNewAddress) {
        ResponseMessage respMesg = new ResponseMessage();
        SubDireccion subDireccion;
        ConsultaEspecificaManager consultaEspecificaManager
                = new ConsultaEspecificaManager();
        try {
            subDireccion = consultaEspecificaManager.
                    querySubAddressOnRepositoryById(iSubdAddress);
            if (subDireccion != null) {
                try {
                    updateSubAddress(iSubdAddress, updateToNewAddress,
                            applicationName, user);
                    respMesg.setMessageType(ResponseMessage.TYPE_SUCCESSFUL);
                    respMesg.setMessageText(
                            ResponseMessage.MESSAGE_SUCCESSFUL
                            + ". La dirección actualizada es: "
                            + updateToNewAddress);
                    respMesg.setIdaddress(iSubdAddress.toString());
                    respMesg.setAddress(updateToNewAddress);
                } catch (Exception e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                    respMesg.setMessageText(e.getMessage());
                    return respMesg;
                }
            } else {
                respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                respMesg.setMessageText(
                        ResponseMessage.MESSAGE_ERROR_DIR_NO_EXISTE_EN_REPOSITORIO);
            }
            return respMesg;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(e.getMessage());
            return respMesg;
        }
    }

    /**
     *
     * @param nombreFuncionalidad
     * @param user
     * @param direccion
     * @return
     * @throws Exception
     */
    private BigDecimal insertIntoDireccionesAgregadas(String nombreFuncionalidad, String user, DireccionAlterna direccion) throws ApplicationException {
        BigDecimal dia_id = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            adb.in("dia1", direccion.getDireccion().getDirId().toString(), direccion.getDiaDireccionIgac(), user);
            dia_id = getIdDireccionAlterna(adb);
            if (dia_id != null) {
                direccion.setDiaId(dia_id);
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(nombreFuncionalidad, Constant.DIRECCION_ALTERNA, user, Constant.INSERT, direccion.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en insertIntoDireccionesAgregadas. EX000 " + e.getMessage(), e);
        }
        return dia_id;
    }

    /**
     *
     * @param adb
     * @return
     * @throws Exception
     */
    private BigDecimal getIdDireccionAlterna(AccessData adb) throws ApplicationException {
        BigDecimal id = null;
        try {
            DataObject obj = adb.outDataObjec("dia2");
            if (obj != null) {
                id = obj.getBigDecimal("ID");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en getIdDireccionAlterna. EX000 " + e.getMessage(), e);
        }
        return id;
    }

    /**
     *
     * @param idDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Direccion queryAddressOnRepoById(String idDireccion) throws ApplicationException {
        Direccion direccion = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dir6", idDireccion);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                direccion = new Direccion();
                TipoDireccion tipo_dir = new TipoDireccion();
                Ubicacion ubi = new Ubicacion();
                BigDecimal id = obj.getBigDecimal("DIRECCION_ID");
                BigDecimal tdi_id = obj.getBigDecimal("TIPO_DIRECCION_ID");
                if (tdi_id != null) {
                    tipo_dir = queryTipoDireccionById(tdi_id.toString());
                }
                BigDecimal ubi_id = obj.getBigDecimal("UBICACION_ID");
                if (ubi_id != null) {
                    ubi = queryUbicacionById(ubi_id);
                }
                direccion.setDirFormatoIgac(obj.getString("FORMATO_IGAC"));
                direccion.setDirServinformacion(obj.getString("SERV_INFORMACION"));
                direccion.setDirNostandar(obj.getString("NO_STANDAR"));
                direccion.setDirOrigen(obj.getString("ORIGEN"));
                BigDecimal confiabilidad = obj.getBigDecimal("CONFIABILIDAD");
                if (confiabilidad != null) {
                    direccion.setDirConfiabilidad(confiabilidad);
                }
                BigDecimal estrato = obj.getBigDecimal("ESTRATO");
                if (estrato != null) {
                    direccion.setDirConfiabilidad(estrato);
                }
                BigDecimal nvl_socio = obj.getBigDecimal("NIVEL_SOCIOECONOMICO");
                if (nvl_socio != null) {
                    direccion.setDirNivelSocioecono(nvl_socio);
                }
                direccion.setDirId(id);
                tipo_dir.setTdiId(tdi_id);
                direccion.setTipoDireccion(tipo_dir);
                ubi.setUbiId(ubi_id);
                direccion.setUbicacion(ubi);
                direccion.setDirComentarioNivelSocioEconomico(obj.getString("COMENTARIO_SOCIOECONOMICO"));//cambio carlos villamil Fase I catastro 2012-12-14
                direccion.setDirManzanaCatastral(obj.getString("MANZANA_CATASTRAL"));
                direccion.setDirManzana(obj.getString("MANZANA"));
                String fecha_creacion = obj.getString("FECHA_CREACION");
                if (fecha_creacion != null) {
                    direccion.setDirFechaCreacion(new Date(fecha_creacion));
                }
                direccion.setDirUsuarioCreacion(obj.getString("USUARIO_CREACION"));
                String fecha_mod = obj.getString("FECHA_EDICION");
                if (fecha_mod != null) {
                    direccion.setDirFechaModificacion(new Date(fecha_mod));
                }
                direccion.setDirUsuarioModificacion(obj.getString("USUARIO_EDICION"));
                direccion.setDirRevisar(obj.getString("REVISAR_DIRECCION"));
                direccion.setDirActividadEconomica(obj.getString("ACTIVIDAD_ECONOMICA"));
                direccion.setNodoMovil(obj.getString("NODO_UNO"));
                direccion.setNodoMovil(obj.getString("NODO_DOS"));
                direccion.setNodoMovil(obj.getString("NODO_TRES"));
                direccion.setNodoMovil(obj.getString("NODO_DTH"));
                direccion.setNodoMovil(obj.getString("NODO_MOVIL"));
                direccion.setNodoMovil(obj.getString("NODO_FTTH"));
                direccion.setNodoMovil(obj.getString("NODO_WIFI"));
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryAddressOnRepoById. EX000 " + e.getMessage(), e);
        }
        return direccion;
    }

    /**
     *
     * @param idDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean querySingleAddressOnRepoById(String idDireccion) throws ApplicationException {
        boolean direccion = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dir8", idDireccion);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                direccion = true;
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en querySingleAddressOnRepoById. EX000 " + e.getMessage(), e);
        }
        return direccion;
    }

    /**
     * Retorna el nombre del departamento de acuerdo al id de la ciudad enviada
     * por parametro
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String queryDepartamentoByIdCiudad(String idCiudad) throws ApplicationException {
        String dpto = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo11", idCiudad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                dpto = obj.getString("NOMBRE");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en querySingleAddressOnRepoById. EX000 " + e.getMessage(), e);
        }
        return dpto;
    }

    /**
     *
     * @param idCiudad
     * @return
     * @throws ExceptionDB
     */
    private String queryGeograficoPoliticoById(String idCiudad) throws ApplicationException {
        String gpo_nombre = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo1", idCiudad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                gpo_nombre = obj.getString("NOMBRE");
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el geográfico político. EX000: " + e.getMessage(), e);
        }
        return gpo_nombre;
    }

    /**
     *
     * @param idtipoDir
     * @return
     * @throws ExceptionDB
     */
    private TipoDireccion queryTipoDireccionById(String idtipoDir) throws ApplicationException {
        TipoDireccion tipoDir = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("tdi2", idtipoDir);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoDir = new TipoDireccion();
                tipoDir.setTdiValor(obj.getString("VALOR"));
                tipoDir.setTdiUsuarioCreacion(obj.getString("USUARIO_CREACION"));
                tipoDir.setTdiFechaCreacion(new Date(obj.getString("FECHA_CREACION")));
                tipoDir.setTdiUsuarioModificacion(obj.getString("USUARIO_EDICION"));
                String fecha_modificacion = obj.getString("FECHA_EDICION");
                if (fecha_modificacion != null) {
                    tipoDir.setTdiFechaModificacion(new Date(fecha_modificacion));
                }
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el tipo de dirección. EX000: " + e.getMessage(), e);
        }
        return tipoDir;
    }

    /**
     *
     * @param id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    @Override
    public Ubicacion queryUbicacionById(BigDecimal id) throws ApplicationException {
        Ubicacion ubicacion = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject objeto = adb.outDataObjec("ubi5", id.toString());
            DireccionUtil.closeConnection(adb);
            if (objeto != null) {
                ubicacion = new Ubicacion();
                BigDecimal id_ubi = (id);
                if (id_ubi != null) {
                    ubicacion.setUbiId(id_ubi);
                }
                ubicacion.setUbiNombre(objeto.getString("NOMBRE"));
                Geografico geo = new Geografico();
                GeograficoPolitico geo_politico = new GeograficoPolitico();
                BigDecimal geo_id = objeto.getBigDecimal("GEOGRAFICO_ID");
                if (geo_id != null) {
                    geo.setGeoId(geo_id);
                    ubicacion.setGeoId(geo);
                }
                BigDecimal gpo_id = objeto.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                if (gpo_id != null) {
                    geo_politico.setGpoId(gpo_id);
                    ubicacion.setGpoId(geo_politico);
                }
                ubicacion.setUbiLatitud(objeto.getString("LATITUD"));
                ubicacion.setUbiLongitud(objeto.getString("LONGITUD"));
                ubicacion.setUbiEstadoRed(objeto.getString("ESTADO_RED"));
                ubicacion.setUbiDistritoCodigoPostal("ZONA_DISTRITO_CODIGO_ZIP");//Modificacion Carlos Villamil fase 1 direcciones 2012-12-14
                ubicacion.setUbiZonaDivipola("ZONA_DIVIPOLA");//Modificacion Carlos Villamil fase 1 direcciones 2012-12-14                        
                String fecha = objeto.getString("FECHA_CREACION");
                if (fecha != null) {
                    ubicacion.setUbiFechaCreacion(new Date(fecha));
                }
                ubicacion.setUbiUsuarioCreacion(objeto.getString("USUARIO_CREACION"));
                String fecha_mod = objeto.getString("FECHA_EDICION");
                if (fecha_mod != null) {
                    ubicacion.setUbiFechaModificacion(new Date(fecha_mod));
                }
                ubicacion.setUbiUsuarioModificacion(objeto.getString("USUARIO_EDICION"));
                ubicacion.setUbiCuentaMatriz(objeto.getString("CUENTA_MATRIZ"));
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la ubicación. EX000: " + e.getMessage(), e);
        }

        return ubicacion;
    }

    /**
     *
     * @param dir_id
     * @param dirNueva
     * @param funcionalidad
     * @param user
     * @return
     * @throws Exception
     */
    private boolean updateAddressOnRepoById(String dir_id, Direccion dirNueva, String funcionalidad, String user) throws ApplicationException {
        boolean ok = false;
        AccessData adb = null;
        try {
            String tipoDireccion = dirNueva.getTipoDireccion().getTdiId().toString();
            String idUbicacion = dirNueva.getUbicacion().getUbiId().toString();
            String dirFormatoIgac = dirNueva.getDirFormatoIgac();
            String dirServinformacion = dirNueva.getDirServinformacion();
            String dirNoestandar = dirNueva.getDirNostandar();
            String dirOrigen = (dirNueva.getDirOrigen().equals("")) ? "NUEVA" : dirNueva.getDirOrigen();
            String dirConfiabilidad = dirNueva.getDirConfiabilidad().toString();
            String dirEstrato = (dirNueva.getDirEstrato().toString().equals("0")) ? "" : dirNueva.getDirEstrato().toString();
            String nivelSocioecono = (dirNueva.getDirNivelSocioecono().toString().equals("0") ? "" : dirNueva.getDirNivelSocioecono().toString());
            //String codPostal = dirNueva.getDirCodPostal();//Cambio carlos villamil direcciones fase I 2012-12-14
            String comentarioNiveslSocioEconomico = dirNueva.getDirComentarioNivelSocioEconomico();//Cambio carlos villamil direcciones fase I 2012-12-14
            String mzacatastral = dirNueva.getDirManzanaCatastral();
            String manzana = dirNueva.getDirManzana();
            String usuario = dirNueva.getDirUsuarioCreacion();
            String revisarEnCampo = dirNueva.getDirRevisar();
            String actEconomica = dirNueva.getDirActividadEconomica();
            String nodoUno = dirNueva.getNodoUno();
            String nodoDos = dirNueva.getNodoDos();
            String nodoTres = dirNueva.getNodoTres();
            String nodoDth = dirNueva.getNodoDth();
            String nodoMovil = dirNueva.getNodoMovil();
            String nodoFtth = dirNueva.getNodoFtth();
            String nodoWifi = dirNueva.getNodoWifi();

            adb = ManageAccess.createAccessData();
            ok = adb.in("dir7r2", tipoDireccion, idUbicacion, dirFormatoIgac, dirServinformacion, dirNoestandar, dirOrigen,
                    dirConfiabilidad, dirEstrato, dirEstrato, nivelSocioecono, nivelSocioecono, comentarioNiveslSocioEconomico, mzacatastral, manzana, usuario,//Cambio carlos villamil direcciones fase I 2012-12-14
                    revisarEnCampo, actEconomica, dir_id, nodoUno, nodoDos, nodoTres, nodoDth, nodoMovil, nodoFtth, nodoWifi);
            if (ok) {
                //no estaba sirviendo el  proceso almacenado. se hace update normal
                BigDecimal id = new BigDecimal(dir_id);
                List<DireccionMgl> dir2 = direccionManager.findByDirId(id);

                DireccionMgl dir = dir2.get(0);
                TipoDireccionMgl tipoDirObj = new TipoDireccionMgl();
                UbicacionMgl ubicacion = new UbicacionMgl();
                tipoDirObj.setTdiId(dirNueva.getTipoDireccion().getTdiId());
                ubicacion.setUbiId(dirNueva.getUbicacion().getUbiId());

                dir.setTipoDirObj(tipoDirObj);
                dir.setUbicacion(ubicacion);
                dir.setDirFormatoIgac(dirNueva.getDirFormatoIgac());
                dir.setDirServinformacion(dirNueva.getDirServinformacion());
                dir.setDirNostandar(dirNueva.getDirNostandar());
                dir.setDirOrigen(dirNueva.getDirOrigen().equals("") ? "NUEVA" : dirNueva.getDirOrigen());
                dir.setDirConfiabilidad(dirNueva.getDirConfiabilidad());
                dir.setDirEstrato(dirNueva.getDirEstrato().toString().equals("0") ? new BigDecimal(BigInteger.ZERO) : dirNueva.getDirEstrato());
                dir.setDirNivelSocioecono(dirNueva.getDirNivelSocioecono().toString().equals("0") ? new BigDecimal(BigInteger.ZERO) : dirNueva.getDirNivelSocioecono());
                dir.setDirComentarioSocioeconomico(dirNueva.getDirComentarioNivelSocioEconomico());
                dir.setDirManzanaCatastral(dirNueva.getDirManzanaCatastral());
                dir.setDirManzana(dirNueva.getDirManzana());
                dir.setUsuarioCreacion(dirNueva.getDirUsuarioCreacion());
                dir.setDirRevisar(dirNueva.getDirRevisar());
                dir.setDirActividadEcono(dirNueva.getDirActividadEconomica());
                dir.setDirNodouno(dirNueva.getNodoUno());
                dir.setDirNododos(dirNueva.getNodoDos());
                dir.setDirNodotres(dirNueva.getNodoTres());
                dir.setDirNodoDth(dirNueva.getNodoDth());
                dir.setDirNodoMovil(dirNueva.getNodoMovil());
                dir.setDirNodoFtth(dirNueva.getNodoFtth());
                dir.setDirNodoWifi(dirNueva.getNodoWifi());
                direccionManager.update(dir);

                AuditoriaEJB auditoria = new AuditoriaEJB();
                auditoria.auditar(funcionalidad, Constant.DIRECCION, user, Constant.UPDATE, dirNueva.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en updateAddressOnRepoById. EX000 " + e.getMessage(), e);
        }
        return ok;
    }

    /**
     * Actualizar el estrato de una dirección
     *
     * @param estrato Nuevo estrato de la dirección
     * @param direccion Dirección a la cual se le va a actualizar el estrato
     * @param subdir
     * @param funcionalidad Funcioanlidad que solicita la modificación de
     * Estrato
     * @param user Usuario que solicita la modificación de Estrato
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean updateAddressEstrato(BigDecimal estrato, Direccion direccion, BigDecimal subdir, String funcionalidad, String user) throws ApplicationException {
        boolean ok = false;
        AccessData adb = null;
        try {
            String dir_id = "";
            String dir_estrato = "";
            String subdir_id = "";

            if (direccion != null) {
                dir_id = direccion.getDirId().toString();
            }
            if (estrato != null) {
                dir_estrato = estrato.toString();
            }

            if (subdir != null) {
                subdir_id = subdir.toString();
                dir_estrato = estrato.toString();
            }
            adb = ManageAccess.createAccessData();
            if (direccion != null && subdir == null) {
                ok = adb.in("dir14", dir_estrato, user, dir_id);
            } else if (subdir != null) {
                ok = adb.in("dir17", dir_estrato, user, subdir_id);
            }
            if (ok) {
                AuditoriaEJB auditoria = new AuditoriaEJB();
                Direccion dir = queryAddressOnRepoById(dir_id);
                auditoria.auditar(funcionalidad, Constant.DIRECCION, user, Constant.UPDATE, dir.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en updateAddressOnRepoById. EX000 " + e.getMessage(), e);
        }
        return ok;
    }

    /**
     *
     * @return
     */
    public String getNombreLog() {
        return nombreLog;
    }

    /**
     * Ejecutar transacción de inserción o actualización de direcciones por
     * Batch
     *
     * @param addressRequest
     * @param addressResult
     * @param user Usuario que solicita la modificación de Estrato
     * @param addressServices
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<AddressResultBatch> queryAddressResultBatch(List<AddressRequest> addressRequest, List<AddressResultBatch> addressResult, List<AddressService> addressServices, String user) throws ApplicationException {
        String nombreFuncionalidad = "queryAddressResultBatch";
        List<AddressGeodata> Geodatas = null;
        AddressGeodata geodatasOtraMalla = null;
        AddressRequest requestOtraMalla = null;
        Direccion dirExisteBD = new Direccion();
        Direccion dirNuevaGeodata = new Direccion();
        Direccion dirExisteOtraMalla = new Direccion();
        ResponseMessage logTransactionBD = new ResponseMessage();
        String validate = "";
        try {
            //Esto se modifica para reemplazar reproceso al WS
            if (procesointercambio != null) {
                Geodatas = procesointercambio;
            } else {
                Geodatas = queryListAddressGeodata(addressRequest);
            }
        } catch (Exception et) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + et.getMessage();
            LOGGER.error(msg);
            Geodatas = null;
        }

        if (Geodatas != null) {
            requestOtraMalla = new AddressRequest();
            geodatasOtraMalla = new AddressGeodata();

            for (int i = 0; i < Geodatas.size(); i++) {

                this.Estrato = addressResult.get(i).getLevel(); //Captura el estrato

                if (addressResult.get(i).getLogTraslate() == null) {
                    requestOtraMalla.setState(addressRequest.get(i).getState());
                    requestOtraMalla.setCity(addressRequest.get(i).getCity());
                    requestOtraMalla.setNeighborhood(addressRequest.get(i).getNeighborhood());
                    requestOtraMalla.setAddress(Geodatas.get(i).getDiralterna());
                    requestOtraMalla.setLevel(addressRequest.get(i).getLevel());

                    geodatasOtraMalla = queryAddressGeodata(requestOtraMalla);

                    try {
                        dirExisteBD = queryAddressOnRepository(Geodatas.get(i).getDirtrad(), null); // Se reemplaza cod Dir por CodenCont
                        dirExisteOtraMalla = queryAddressOnRepository(Geodatas.get(i).getDirtrad(), null); // Se reemplaza cod Dir por CodenCont
                    } catch (ApplicationException e2) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e2.getMessage();
                        LOGGER.error(msg);
                        dirExisteBD = null;
                        dirExisteOtraMalla = null;
                    }
                    addressResult.get(i).setAddressTraslateGeodatas(Geodatas.get(i).getDirtrad());

                    //Verifica si la dirección no existe.
                    String complemento = Geodatas.get(i).getCoddir().replace(Geodatas.get(i).getCodencont(), "");
                    int cod;
                    try {
                        cod = Integer.parseInt(complemento);
                    } catch (NumberFormatException ec) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ec.getMessage();
                        LOGGER.error(msg);
                        cod = 1;
                    }

                    SubDireccion subDireccion = null;
                    if (cod == 1) {
                        subDireccion = querySubAddressOnRepoByCod(Geodatas.get(i).getDirtrad(),null);
                    }

                    if (addressResult.get(i).getTransaction().equals("I")) {
                        if ((dirExisteBD == null && dirExisteOtraMalla == null) || (dirExisteBD != null && subDireccion == null && cod == 1)) {
                            if (addressResult.get(i).getFlagoperacion().equals("S")) {
                                validate = "FALSE";
                                logTransactionBD = createAddress(addressRequest.get(i), user, nombreFuncionalidad, validate);
                                addressResult.get(i).setLogTransactionBD(logTransactionBD.getMessageText());
                                addressResult.get(i).setAddressTraslateGeodatas(Geodatas.get(i).getDirtrad());
                                if (addressServices.get(i) == null) {
                                    addressServices.get(i).setAddress("null");
                                    addressServices.get(i).setQualifiers("null");
                                    addressServices.get(i).setRecommendations("null");
                                    addressResult.get(i).setAddressResult(addressServices.get(i));
                                } else {
                                    addressResult.get(i).setAddressResult(addressServices.get(i));
                                }
                            } else if (addressResult.get(i).getFlagoperacion().equals("N")) {
                                validate = "TRUE";
                                logTransactionBD = createAddress(addressRequest.get(i), user, nombreFuncionalidad, validate);
                                addressResult.get(i).setLogTransactionBD(logTransactionBD.getMessageText());
                                addressResult.get(i).setAddressTraslateGeodatas(Geodatas.get(i).getDirtrad());
                                if (addressServices.get(i) == null) {
                                    addressServices.get(i).setAddress("null");
                                    addressServices.get(i).setQualifiers("null");
                                    addressServices.get(i).setRecommendations("null");
                                    addressResult.get(i).setAddressResult(addressServices.get(i));
                                } else {
                                    addressResult.get(i).setAddressResult(addressServices.get(i));
                                }
                            } else {
                                addressResult.get(i).setLogTransactionBD("No ejecutado " + " " + "Error, No se indíco un valor en 'Validar'.");
                                if (addressServices.get(i) == null) {
                                    addressServices.get(i).setAddress("null");
                                    addressServices.get(i).setQualifiers("null");
                                    addressServices.get(i).setRecommendations("null");
                                    addressResult.get(i).setAddressResult(addressServices.get(i));
                                } else {
                                    addressResult.get(i).setAddressResult(addressServices.get(i));
                                }
                            }
                        } else {
                            addressResult.get(i).setLogTransactionBD("No ejecutado " + " " + DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO);
                            if (addressServices.get(i) == null) {
                                addressServices.get(i).setAddress("null");
                                addressServices.get(i).setQualifiers("null");
                                addressServices.get(i).setRecommendations("null");
                                addressResult.get(i).setAddressResult(addressServices.get(i));
                            } else {
                                addressResult.get(i).setAddressResult(addressServices.get(i));
                            }
                        }
                    } else if (addressResult.get(i).getTransaction().equals("U")) {
                        if (dirExisteBD != null) {
                            logTransactionBD = updateAddress(dirExisteBD.getDirId().toString(), user, nombreFuncionalidad, Geodatas.get(i).getDirtrad());
                            addressResult.get(i).setLogTransactionBD(logTransactionBD.getMessageText());
                            addressResult.get(i).setAddressTraslateGeodatas(Geodatas.get(i).getDirtrad());
                            if (addressServices.get(i) == null) {
                                addressServices.get(i).setAddress("null");
                                addressServices.get(i).setQualifiers("null");
                                addressServices.get(i).setRecommendations("null");
                                addressResult.get(i).setAddressResult(addressServices.get(i));
                            } else {
                                addressResult.get(i).setAddressResult(addressServices.get(i));
                            }
                        } else {
                            addressResult.get(i).setLogTransactionBD("No Ejecutado" + " " + DIR_NO_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO);
                            if (addressServices.get(i) == null) {
                                addressServices.get(i).setAddress("null");
                                addressServices.get(i).setQualifiers("null");
                                addressServices.get(i).setRecommendations("null");
                                addressResult.get(i).setAddressResult(addressServices.get(i));
                            } else {
                                addressResult.get(i).setAddressResult(addressServices.get(i));
                            }
                        }
                    } else {
                        addressResult.get(i).setLogTransactionBD("No Ejecutado" + " " + "No se definio un indicador en el campo ACCION.");
                        if (addressServices.get(i) == null) {
                            addressServices.get(i).setAddress("null");
                            addressServices.get(i).setQualifiers("null");
                            addressServices.get(i).setRecommendations("null");
                            addressResult.get(i).setAddressResult(addressServices.get(i));
                        } else {
                            addressResult.get(i).setAddressResult(addressServices.get(i));
                        }
                    }
                }// Fin del Si el campo LogTraslate no es null
                else {
                    addressResult.get(i).setLogTransactionBD("No Ejecutado" + " " + "Errores en el log de estandarizacion.");
                    if (addressServices.get(i) == null) {
                        addressServices.get(i).setAddress("null");
                        addressServices.get(i).setQualifiers("null");
                        addressServices.get(i).setRecommendations("null");
                        addressResult.get(i).setAddressResult(addressServices.get(i));
                    } else {
                        addressResult.get(i).setAddressResult(addressServices.get(i));
                    }
                }
            }            //Fin del For del proceso
        } else {
            for (int i = 0; i < addressResult.size(); i++) {
                addressResult.get(i).setLogTransactionBD("No Ejecutado" + " " + " Web service no disponible.");
                if (addressServices.get(i) == null) {
                    addressServices.get(i).setAddress("null");
                    addressServices.get(i).setQualifiers("null");
                    addressServices.get(i).setRecommendations("null");
                    addressResult.get(i).setAddressResult(addressServices.get(i));
                } else {
                    addressResult.get(i).setAddressResult(addressServices.get(i));
                }
            }
        }
        return addressResult;
    }

    //Camilo Gaviria - Direcciones Facturación 10/01/2013
    /**
     *
     * @param addressRequest
     * @param addressResult
     * @param addressServices
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<AddressFactResultBatch> queryAddressFactResultBatch(List<AddressRequest> addressRequest,
            List<AddressFactResultBatch> addressResult,
            List<AddressService> addressServices,
            String user) throws ApplicationException {

        String nombreFuncionalidad = "queryAddressFactResultBatch";
        List<AddressGeodata> Geodatas = null;
        AddressGeodata geodatasOtraMalla = null;
        AddressRequest requestOtraMalla = null;
        Direccion dirExisteBD = new Direccion();
        Direccion dirExisteOtraMalla = new Direccion();
        ResponseMessage logTransactionBD = new ResponseMessage();
        String validate = "";
        try {
            //Esto se modifica para reemplazar reproceso al WS
            if (procesointercambio != null) {
                Geodatas = procesointercambio;
            } else {
                Geodatas = queryListAddressGeodata(addressRequest);
            }
        } catch (Exception et) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + et.getMessage();
            LOGGER.error(msg);
            Geodatas = null;
        }

        if (Geodatas != null) {
            requestOtraMalla = new AddressRequest();
            geodatasOtraMalla = new AddressGeodata();

            for (int i = 1; i < Geodatas.size(); i++) {
                if (addressResult.get(i).getLogTraslate() == null) {
                    requestOtraMalla.setState(addressRequest.get(i).getState());
                    requestOtraMalla.setCity(addressRequest.get(i).getCity());
                    requestOtraMalla.setNeighborhood(addressRequest.get(i).getNeighborhood());
                    requestOtraMalla.setAddress(Geodatas.get(i).getDiralterna());
                    requestOtraMalla.setLevel(addressRequest.get(i).getLevel());

                    geodatasOtraMalla = queryAddressGeodata(requestOtraMalla);
                    addressResult.get(i).setCodDir(Geodatas.get(i).getCoddir());
                    addressResult.get(i).setAddressTraslateGeodata(Geodatas.get(i).getDirtrad());

                    try {
                        dirExisteBD = queryAddressOnRepository(Geodatas.get(i).getDirtrad(), null);
                        dirExisteOtraMalla = queryAddressOnRepository(Geodatas.get(i).getDiralterna(), null);
                    } catch (ApplicationException e2) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e2.getMessage();
                        LOGGER.error(msg);
                        dirExisteBD = null;
                        dirExisteOtraMalla = null;
                    }

                    //Verifica si la dirección no existe.
                    String complemento = Geodatas.get(i).getCoddir().replace(Geodatas.get(i).getCodencont(), "");
                    int cod;
                    try {
                        cod = Integer.parseInt(complemento);
                    } catch (NumberFormatException ec) {
                        cod = 1;
                    }

                    SubDireccion subDireccion = null;
                    if (cod == 1) {
                        subDireccion = querySubAddressOnRepoByCod(Geodatas.get(i).getDirtrad(), null);
                    }

                    if (addressResult.get(i).getTransaction().toUpperCase().equals("INSERTAR")) {
                        if ((dirExisteBD == null && dirExisteOtraMalla == null) || (dirExisteBD != null && subDireccion == null && cod == 1)) {
                            if (addressResult.get(i).getFlagoperacion().toUpperCase().equals("SI")) {
                                ServiceCreateAddress(addressRequest, i, user, nombreFuncionalidad, "FALSE", addressResult, Geodatas, addressServices);
                            } else if (addressResult.get(i).getFlagoperacion().toUpperCase().equals("NO")) {
                                ServiceCreateAddress(addressRequest, i, user, nombreFuncionalidad, "TRUE", addressResult, Geodatas, addressServices);
                            } else {
                                addressResult.get(i).setLogTransactionBD("No ejecutado " + " " + "Error, No se indíco un valor en 'Validar'.");
                                addressServiceNull(addressServices, i, addressResult);
                            }
                        } else {
                            String DireccionAntigua = "";
                            if ("ANTIGUA".equals(Geodatas.get(i).getFuente())) {
                                DireccionAntigua = "La dirección es Antigua debe ser actualizada";
                            }
                            addressResult.get(i).setLogTransactionBD("No ejecutado " + " " + DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO + " " + DireccionAntigua);
                            addressServiceNull(addressServices, i, addressResult);
                        }
                    } else if (addressResult.get(i).getTransaction().toUpperCase().equals("ACTUALIZAR")) {
                        if (dirExisteBD != null) {
                            logTransactionBD = updateAddress(dirExisteBD.getDirId().toString(), user, nombreFuncionalidad, Geodatas.get(i).getDirtrad());
                            addressResult.get(i).setLogTransactionBD(logTransactionBD.getMessageText());
                            addressResult.get(i).setAddressTraslateGeodata(Geodatas.get(i).getDirtrad());
                            addressServiceNull(addressServices, i, addressResult);
                        } else {
                            addressResult.get(i).setLogTransactionBD("No Ejecutado " + DIR_NO_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO);
                            addressServiceNull(addressServices, i, addressResult);
                        }
                    } else {
                        addressResult.get(i).setLogTransactionBD("No Ejecutado" + " " + "No se definio un indicador en el campo ACCION.");
                        addressServiceNull(addressServices, i, addressResult);
                    }
                }// Fin del Si el campo LogTraslate no es null
                else {
                    addressResult.get(i).setLogTransactionBD("No Ejecutado" + " " + "Errores en la estandarizacion." + addressResult.get(i).getLogTraslate());
                    if (addressServices.get(i) == null) {
                        addressServices.get(i).setAddress("null");
                        addressServices.get(i).setQualifiers("null");
                        addressServices.get(i).setRecommendations("null");
                        addressResult.get(i).setAddressResult(addressServices.get(i));
                    } else {
                        addressResult.get(i).setAddressResult(addressServices.get(i));
                    }
                }
            }
        } else {
            for (int i = 0; i < addressResult.size(); i++) {
                addressResult.get(i).setLogTransactionBD("No Ejecutado" + " " + " Web service no disponible.");
                addressServiceNull(addressServices, i, addressResult);
            }
        }
        return addressResult;
    }
    //Fin Modificacion - Direcciones facturacion

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
     * Permite Actualizar una direccion Antigua a la Nueva.Realiza la
 actualizacion de una direccion antigua a la nueva junto con todas sus
 subdirecciones.
     *
     * @author Johnnatan Ortiz
     * @param idAdresses id de la direccion, el primer caracter indica si se
     * trata de una direccion o una subdireccion.
     * @param user usuario que esta ejecutando la actualizacion.
     * @param //nombreFuncionalidad aplicacion desde la cual se ejecuta la
     * actualizacion.
     * @param newAddresses nueva direccion a la cual se actualizara la antigua
     * direccion.
     * @return si el proceso se realizo correctamente.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean updateAddressesAndSubAddress(String idAdresses,
            String user,
            String applicationName,
            AddressRequest newAddresses) throws ApplicationException {

        try {
            ConsultaEspecificaManager consultaEspecificaManager = new ConsultaEspecificaManager();
            boolean result = false;
            BigDecimal idDirPrincipal = null;
            SubDireccion subDireccion = null;
            if (idAdresses.contains("d")) {
                idDirPrincipal = new BigDecimal(idAdresses.replace("d", ""));
                updateAddress(idAdresses.replace("d", ""), user, applicationName, "");
            } else if (idAdresses.contains("s")) {
                //TODO:find direccion - enviar cambio de direccion
                subDireccion = consultaEspecificaManager.
                        querySubAddressOnRepositoryById(new BigDecimal(idAdresses.replace("s", "")));

                if (subDireccion != null && subDireccion.getDireccion() != null
                        && subDireccion.getDireccion().getDirId() != null
                        && subDireccion.getDireccion().getDirId().compareTo(BigDecimal.ZERO) != 0) {
                    idDirPrincipal = subDireccion.getDireccion().getDirId();
                    updateAddress(idDirPrincipal.toString(), user, applicationName, "");
                }
            }

            AddressGeodata addressGeodata = callServiceEnrich(newAddresses);

            SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
            List<SubDireccionMgl> subDireccionList = subDireccionMglManager.findByDirId(idDirPrincipal);
            if (subDireccionList != null
                    && !subDireccionList.isEmpty()) {
                for (SubDireccionMgl sd : subDireccionList) {
                    String codDirServinf = sd.getSdiServinformacion();
                    sd.setSdiServinformacion(addressGeodata.getCodencont()
                            + codDirServinf.substring(addressGeodata.getCodencont().length()));
                    String dirFormatoIgac = sd.getSdiFormatoIgac();
                    sd.setSdiFormatoIgac(addressGeodata.getDirtrad()
                            + dirFormatoIgac.substring(addressGeodata.getDiralterna().length()));
                    subDireccionMglManager.update(sd);
                }
            }
            result = true;

            return result;
        } catch (RemoteException | ServiceException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en updateAddressesAndSubAddress. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * identificar si es una direccion o subdireccion apartir de parametros
     *
     * @param drdir
     * @return
     * @autor: espinosadiea fecha de creacion: 24/07/2018
     */
    public static boolean direccionCampo5(DrDireccion drdir) {
        try {
            ParametrosMglManager e_pm = new ParametrosMglManager();
            String cadena = (e_pm.findByAcronimoName(Constant.IDENTIFICADOR_DIR_SUBDIR)).getParValor();
            if (cadena == null || cadena.isEmpty()) {
                return false;//subdireccion
            }
            String tipo = drdir.getCpTipoNivel5();
            if (tipo == null || tipo.isEmpty()) {
                return true;//espinosadiea si el tipo 5 es null se define como direccion 
            }
            String valor = drdir.getCpValorNivel5();
            if (valor == null || valor.isEmpty()) {
                valor = "null";
            }
            String[] cad5 = cadena.split("\\|");
            for (int i = 0; i < cad5.length; i++) {
                try {
                    String[] val5 = cad5[i].split(":");
                    if (val5[0].trim().compareToIgnoreCase(tipo.trim()) == 0
                            && val5[1].trim().compareToIgnoreCase(valor.trim()) == 0) {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    LOGGER.error("Clase: direccionCampo5: Error leyendo parametros de tipo y valor");
                }
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(AddressEJB.class)+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return false;//el tipo es diferente de null y no esta parametrizado el tipo5, valor5 
    }

    /**
     * Función que corta una coordenada y le quita los últimos 3 digitos.En
 caso de que falten digitos a la coordenada es rellenada con ceros.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public String cortarCoordenadas(String coordenadaOriginal) {
        try {

            if (coordenadaOriginal != null && !coordenadaOriginal.equalsIgnoreCase("0")) {
                // tamaño de la coordenada antes del punto
                int tamañoCordenadaAntesPunto = coordenadaOriginal.lastIndexOf(".");
                // valor de la coordenada despues del punto, agregar 1 para omitir el punto
                String coordenadaPostPunto = coordenadaOriginal.substring(tamañoCordenadaAntesPunto + 1);
                // tamaño de la coordenada despues del punto que debe ser 8
                int tamañoCoordenada = coordenadaPostPunto.length();
                //se igualan las coordenada para agregarle ceros a la derecha en caso de que falten
                String coordenadaCortada = coordenadaOriginal;
                //Se realiza resta para conocer cuantos punto hay que agregar
                int cantidadCerosAgregar = 8 - tamañoCoordenada;
                //se realiza for para agregar los ceros faltantes para completa 8
                if (cantidadCerosAgregar != 0) {
                    for (int i = 0; i < cantidadCerosAgregar; i++) {
                        coordenadaCortada += "0";
                    }
                }
                /* se obtiene el index de la coordenada despues de rellenarla de ceros (si fue necesario)
                 de quitar 3 digitos que es como se va a cortar la coordenada*/
                int indexCoordenadaCortada = coordenadaCortada.length() - 3;
                String coordenadaCoortadaFinal = coordenadaCortada.substring(0, indexCoordenadaCortada);
                return coordenadaCoortadaFinal;
            }
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * complementos que el geodata no esta trabajando de manera adecuada
     *
     * @param drdir
     * @return
     * @autor: espinosadiea fecha de creacion: 03/08/2018
     */
    public static boolean geodataExclusionTipoNivel5(DrDireccion drdir) {
        try {
            ParametrosMglManager e_pm = new ParametrosMglManager();
            String cadena = (e_pm.findByAcronimoName(Constant.GEODATA_EXCLUSION_COMPLEMENTO)).getParValor();
            if (cadena == null || cadena.isEmpty()) {
                return false;//no excluir datos
            }
            String tipo = drdir.getCpTipoNivel5();
            if (tipo == null || tipo.isEmpty()) {
                return false;//no excluir datos
            }
            String[] arrayCad = cadena.split("\\|");
            for (int i = 0; i < arrayCad.length; i++) {
                try {
                    if (arrayCad[i].trim().compareToIgnoreCase(tipo.trim()) == 0) {
                        return true;//excluir y concatenar para la tabla de subdirecciones AddressEJB
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    LOGGER.error("AddressEJB: geodataExclusionTipoNivel5: Error leyendo parametros de tipo y valor");
                }
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(AddressEJB.class)+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return false;//no excluir 
    }

    // espinosadiea: se realiza ajuste para la direcciones que no son reconocidas por el geodata
    // deben enviar otros y un valor para concatenarlos con el codigo devuelto por el serviinformacion
    public String concatenaSubDireccion(String codigoGeoData, DrDireccion dirNueva) {
        if (dirNueva != null) {
            if (geodataExclusionTipoNivel5(dirNueva)) {
                //espinosadiea cuando es diferente a otros contatena con el tipo 5 si es otros no concatena el tipo
                if (dirNueva.getCpTipoNivel5().compareToIgnoreCase("OTROS") != 0) {
                    //concatena messanine, pen house, campamento,
                    codigoGeoData = codigoGeoData + dirNueva.getCpTipoNivel5();
                }
                codigoGeoData = codigoGeoData + dirNueva.getCpValorNivel5();
                //TO_DO espinosadiea: se debe eliminar cuando el servicio de geodata este funcionando correctamente 
                //es para solucionar error de casa+piso entre otros.
                if (!(dirNueva.getCpTipoNivel6() == null || dirNueva.getCpTipoNivel6().isEmpty())
                        && !(dirNueva.getCpValorNivel6() == null || dirNueva.getCpValorNivel6().isEmpty())) {
                    codigoGeoData = codigoGeoData + dirNueva.getCpTipoNivel6() + dirNueva.getCpValorNivel6();
                }
            }
        }
        return codigoGeoData;
    }        
    
       /**
     *
     * @param geodata
     * @param geodataEnOtraMalla
     * @param respMesg
     * @return
     */
    private String validarExistenciaDireccionOnRepoInspira(AddressGeodata geodataDir, AddressGeodata geodataSubDir,
        ResponseMessage respMesg, DrDireccion direccionNueva, BigDecimal centroPobladoId) throws CloneNotSupportedException, CloneNotSupportedException {
        String tipoDireccion = Constant.DIR_NO_EXISTE_MSJ;
        Direccion direccion = null;
        SubDireccion subDireccion = null;
        CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
        List<CmtDireccionDetalladaMgl> direccionDetalladaPrincipal = new ArrayList();
        List<CmtDireccionDetalladaMgl> direccionDetalladaSubDireccion = new ArrayList();
        
        //JDHT
        //valida si es un direccion principal o sub direccion
        boolean esSubDireccion = !direccionCampo5(direccionNueva);
        
        //Se consulta la direccion en el repositorio
        String codigoPlacaDir = geodataDir.getCodencont();
        String codigoAddressDir = geodataDir.getCoddir();
        String complemento = codigoAddressDir.substring(codigoPlacaDir.length());
        try {
            if (direccionNueva != null && centroPobladoId != null && !centroPobladoId.equals(BigDecimal.ZERO)) {
                DrDireccion drDireccionTmp = direccionNueva.clone();
                if (drDireccionTmp != null && drDireccionTmp.getCpTipoNivel5() != null
                        && !drDireccionTmp.getCpTipoNivel5().isEmpty()
                        && (drDireccionTmp.getCpTipoNivel5().equalsIgnoreCase("CASA") 
                        && drDireccionTmp.getCpValorNivel5() == null) ) {
                    //si es casa no se pone en null el valor para que la busque asi
                } else {
                    drDireccionTmp.setCpTipoNivel5(null);
                }
                drDireccionTmp.setCpTipoNivel1(null);
                drDireccionTmp.setCpTipoNivel2(null);
                drDireccionTmp.setCpTipoNivel3(null);
                drDireccionTmp.setCpTipoNivel4(null);               
                drDireccionTmp.setCpTipoNivel6(null);
                drDireccionTmp.setCpValorNivel1(null);
                drDireccionTmp.setCpValorNivel2(null); 
                drDireccionTmp.setCpValorNivel3(null);
                drDireccionTmp.setCpValorNivel4(null); 
                drDireccionTmp.setCpValorNivel5(null); 
                drDireccionTmp.setCpValorNivel6(null);
                
                //busca la direccion de manera tabulada en el repositorio sin complemento de apto.
                direccionDetalladaPrincipal = cmtDireccionDetalleMglManager.findDireccionByDireccionDetalladaPrincipalExacta(drDireccionTmp, centroPobladoId, true);

            } else {
                direccion = queryAddressOnRepository(geodataDir.getDirtrad(), centroPobladoId);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
            respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
        }

        if (direccionNueva != null && centroPobladoId != null && !centroPobladoId.equals(BigDecimal.ZERO)) {

            //si esta llena quiere decir que encontro la direccion principal.
            if (direccionDetalladaPrincipal == null || direccionDetalladaPrincipal.isEmpty()) {
                if (!esSubDireccion) {//Es una direccion y se debe crear en DIRECCION
                    tipoDireccion = Constant.DIRECCION;                    
                } else {//Es una Subdireccion y se debe crear DIRECCION y SUBDIRECCION
                    tipoDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
                }
            } else {
                if (!esSubDireccion) {//Ya existe la direccion y NO SE ALMACENA
                    tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                    respMesg.setIdaddress("d" + direccionDetalladaPrincipal.get(0).getDireccion().getDirId().toString());
                } else {//Es una sub-direccion y se consulta como tal
                    try {
                        direccionDetalladaSubDireccion = cmtDireccionDetalleMglManager.findDireccionByDireccionDetalladaExacta(direccionNueva, centroPobladoId, true, null);                      
                       
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                        LOGGER.error(msg);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                    }
                    if (direccionDetalladaSubDireccion == null || direccionDetalladaSubDireccion.isEmpty()) {
                     //Es sub-direccion y se almacena en SUBDIRECCION
                        tipoDireccion = Constant.SUB_DIRECCION;
                        respMesg.setIdaddress("d" + direccionDetalladaPrincipal.get(0).getDireccion().getDirId().toString());
                    } else {//Ya existe la subdireccion y NO SE ALMACENA
                        tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                        respMesg.setIdaddress("s" + direccionDetalladaSubDireccion.get(0).getSubDireccion().getSdiId().toString());
                    }
                }
            }

        } else {
            if (direccion == null) {
                if (!esSubDireccion) {//Es una direccion y se debe crear en DIRECCION
                    tipoDireccion = Constant.DIRECCION;
                } else {//Es una Subdireccion y se debe crear DIRECCION y SUBDIRECCION
                    tipoDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
                }
            } else {
                if (!esSubDireccion) {//Ya existe la direccion y NO SE ALMACENA
                    tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                    respMesg.setIdaddress("d" + direccion.getDirId().toString());
                } else {//Es una sub-direccion y se consulta como tal
                    try {
                        subDireccion = querySubAddressOnRepoByCod(concatenaSubDireccion(geodataSubDir.getDirtrad(), direccionNueva), centroPobladoId);
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                        LOGGER.error(msg);
                        respMesg.setMessageType(ResponseMessage.TYPE_ERROR);
                        respMesg.setMessageText(ResponseMessage.MESSAGE_ERROR);
                    }
                    if (subDireccion == null) {//Es sub-direccion y se almacena en SUBDIRECCION
                        tipoDireccion = Constant.SUB_DIRECCION;
                    } else {//Ya existe la subdireccion y NO SE ALMACENA
                        tipoDireccion = Constant.DIR_SI_EXISTE_MSJ;
                        respMesg.setIdaddress("s" + subDireccion.getSdiId().toString());
                    }
                }
            }

        }
        return tipoDireccion;
    }  

    
}
