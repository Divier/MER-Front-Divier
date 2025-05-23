package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtValidadorDireccionesManager;
import co.com.claro.mgl.businessmanager.ptlus.UsuariosServicesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.ptlus.UsuariosPortal;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.vo.cm.DetalleCargaHhppMgl;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.visitasTecnicas.business.DetalleDireccionManager;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author yimy diaz
 */
public class DireccionesValidacionCargueManager {

    private static final Logger LOGGER = LogManager.getLogger(DireccionesValidacionCargueManager.class);
    private static final int LONGITUD_TIPO_MAZANA_CASA = 99;
    private HhppMgl hhpp;
    private HhppMglManager hhppMglManager = new HhppMglManager();
    private DireccionMglManager direccionMglManager = new DireccionMglManager();
    private SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
    private UsuariosPortal usuario;
    
    /**
     * Valida una direccion.Realiza las operaciones de validacion en el
     * repositorio para verificar existencia.Incluye la validacion de direccion
     * nueva y antigua.
     *
     * @author Carlos Leonardo Villamil
     * @param request           objeto con la informacion de la direccion a validar.
     * @param mallaNuevaAmbigua TRUE indica si es una dirección ambigua ubicada
     *                          en malla vial Nueva.
     * @param detalle
     * @return resultado de la validacion direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws java.lang.Exception
     */
    public CityEntity validaDireccion(
            RequestConstruccionDireccion request,
            boolean mallaNuevaAmbigua,
            DetalleCargaHhppMgl detalle) throws ApplicationException {
        try {

            NegocioParamMultivalor param = new NegocioParamMultivalor();
            DireccionMgl direccionMgl;
            UbicacionMgl ubicacionMgl;
            CityEntity cityEntity;

            CmtDireccionDetalleMglManager manager = new CmtDireccionDetalleMglManager();
            HhppMglManager hhppMglManager = new HhppMglManager();
            DireccionMglManager direccionMglManager = new DireccionMglManager();
            UbicacionMglManager ubicacionMglManager = new UbicacionMglManager();

            hhpp = hhppMglManager.findById(request.getDrDireccion().getId());

            DrDireccion dirOriginal = manager.find(hhpp);

            if (request.getComunidad().length() <= 3 || request.getComunidad() == null
                    || request.getComunidad().isEmpty()) {
                direccionMgl = direccionMglManager.findById(hhpp.getDireccionObj());
                ubicacionMgl = ubicacionMglManager.findById(direccionMgl.getUbicacion());
                cityEntity = param.consultaDptoCiudadGeo(ubicacionMgl.getGpoIdObj().getGeoCodigoDane());
            } else {
                //se envia el codigoDane en la comunidad del request
                cityEntity = param.consultaDptoCiudadGeo(request.getComunidad());
            }

            cityEntity.setDireccionAntiguaGeo(dirOriginal);

            if (cityEntity == null || cityEntity.getCityName() == null
                    || cityEntity.getDpto() == null
                    || cityEntity.getCityName().isEmpty()
                    || cityEntity.getDpto().isEmpty()) {
                throw new ApplicationException("La Ciudad no esta configurada en "
                        + "Direcciones");
            }   

            DrDireccionManager drDireccionManager = new DrDireccionManager();

            if (request.getBarrio() != null && !request.getBarrio().isEmpty()) {
                request.getDrDireccion().setBarrio(request.getBarrio().toUpperCase());
            }
            String barrioStr
                    = drDireccionManager.obtenerBarrio(request.getDrDireccion());
            cityEntity.setCodCity(request.getComunidad());
            if (barrioStr != null && barrioStr.isEmpty()) {
                cityEntity.setBarrio(barrioStr.toUpperCase());
            }

            DetalleDireccionEntity detalleDireccionActual
                    = request.getDrDireccion().convertToDetalleDireccionEntity();
            cityEntity.setActualDetalleDireccionEntity(detalleDireccionActual);
            cityEntity.setCityId(cityEntity.getCityId());
            cityEntity.setDetalleDireccionEntity(null);

            GeograficoPoliticoManager geograficoPoliticoMglManager
                    = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl politicoMgl
                    = geograficoPoliticoMglManager.findCityByCodDane( 
                            cityEntity.getCityId().toString());
            if (politicoMgl.getGpoMultiorigen().trim().equalsIgnoreCase("1")
                    && (barrioStr == null || barrioStr.trim().isEmpty())
                    && request.getDrDireccion().getIdTipoDireccion()
                            .equalsIgnoreCase("CK")) {
                throw new ApplicationException("{multiorigen=1}");
            }

            cityEntity.getActualDetalleDireccionEntity().setMultiOrigen(
                    politicoMgl.getGpoMultiorigen());

            String address
                    = drDireccionManager.getDireccion(request.getDrDireccion());
            cityEntity.setAddress(address);
            cityEntity.setTipoSolictud("0");
            cityEntity.setIdUsuario(request.getIdUsuario());
            /* author Juan David Hernandez */
            cityEntity.setDireccionSinApto(obtenerDireccionSinApto(request.getDrDireccion()));
            cityEntity.setDireccionPrincipal(request.getDrDireccion());

            String idCastro = validarDir(cityEntity, mallaNuevaAmbigua, request.getDrDireccion(),detalle);
            if (cityEntity.getDetalleDireccionEntity() != null) {
                DrDireccion responseDrDireccion = new DrDireccion();
                responseDrDireccion.obtenerFromDetalleDireccionEntity(
                        cityEntity.getDetalleDireccionEntity());
                request.setDrDireccion(responseDrDireccion);
            }
            if (idCastro != null && !idCastro.trim().isEmpty()
                    && !idCastro.trim().equalsIgnoreCase("0")) {
                request.getDrDireccion().setIdDirCatastro(idCastro);
            }
            if (cityEntity.getDireccionRREntityAntigua() == null
                    && cityEntity.getDireccionRREntityNueva() == null) {
                DireccionRRManager direccionRRManager
                        = new DireccionRRManager(
                                cityEntity.getActualDetalleDireccionEntity(),
                                politicoMgl.getGpoMultiorigen().trim(), politicoMgl);
                cityEntity.setDireccionRREntityNueva(
                        direccionRRManager.getDireccion());
            }
            return cityEntity;
        } catch (Exception e) {
            String msg = "Ocurrio un error validando la direccion: " + e.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Valida una direccion.Realiza las operaciones de validacion en el
     * repositorio y contra sitidata para verificar existencia.Incluye la
     * validacion de direccion nueva y antigua.
     *
     * @author Carlos Leonardo Villamil
     * @param cityEntityRequest Valores para validar la direccion.
     * @param mallaNuevaAmbigua TRUE indica si es una dirección ambigua ubicada
     * en malla vial Nueva. Si no se va a realizar proceso de validacion de
     * dirección ambigue enviar el valor FALSE
     * @param drDirEntrada
     * @param detalle
     * @return Id de la direccion si existe en el repositirio.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String validarDir(CityEntity cityEntityRequest, boolean mallaNuevaAmbigua, DrDireccion drDirEntrada, DetalleCargaHhppMgl detalle) throws ApplicationException {
       
        String idUsuario = cityEntityRequest != null ? cityEntityRequest.getIdUsuario() : null;
        UsuariosServicesDTO usuario = null;
            
        if (idUsuario != null && !idUsuario.isEmpty()) {
            UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
            // Buscar al usuario a traves de la cédula. 
            // Si no se encuentra, realiza la búsqueda por medio del parametro de VISOR.
            usuario = usuariosManager.consultaInfoUserPorCedulaVISOR(idUsuario);
        }
        
        if (usuario == null) {
            throw new ApplicationException("El usuario no fue encontrado.");
        }

        cityEntityRequest.setCambioDireccion(false);
        cityEntityRequest.setDireccionRREntityAntigua(null);
        cityEntityRequest.setDireccionRREntityNueva(null);
        cityEntityRequest.setDirIgacAntiguaStr(null);
        cityEntityRequest.setExisteHhppAntiguoNuevo(false);

        DetalleDireccionManager detalleDireccionManager = new DetalleDireccionManager();
        GeograficoPoliticoManager geograficoPoliticoMglManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPoblado = null;
        DireccionRRManager direccionRRManager = null;
        DireccionRRManager direccionDetalleHhpp = null;
        DetalleDireccionEntity detalleDireccionEntityTemp = null;

        ArrayList<UnidadStructPcml> litUnidadStructPcml;
        ArrayList<UnidadStructPcml> litUnidadStructPcmlTemp = null;
        DetalleDireccionEntity detalleDireccionEntityTemp2 = null;
        boolean crearDireccion = false;
        boolean actualizarDirDet = false;
        boolean actualizarHhpp = false;
        try {
            /*Consultar el WebService parera obtener los datos de la direccion
             * ingresada */
            AddressService responseSrv;
            AddressRequest requestSrv = new AddressRequest();
            AddressService responseSrvSegunda;
            AddressRequest requestSrvSegunda = new AddressRequest();

            //Asignar la ciudad
            requestSrv.setCity(cityEntityRequest.getCityName());
            requestSrvSegunda.setCity(cityEntityRequest.getCityName());
            //Asignar el departamento
            requestSrv.setState(cityEntityRequest.getDpto());
            requestSrvSegunda.setState(cityEntityRequest.getDpto());
            //Asignar la direccion a consultar
            requestSrv.setAddress(cityEntityRequest.getAddress());
            //Asignar el barrio
            requestSrv.setNeighborhood(cityEntityRequest.getBarrio());
            requestSrvSegunda.setNeighborhood(cityEntityRequest.getBarrio());

            requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);
            requestSrvSegunda.setLevel(Constant.TIPO_CONSULTA_COMPLETA);
            
            if(cityEntityRequest.getCodDane() != null && !cityEntityRequest.getCodDane().isEmpty()){
                requestSrv.setCodDaneVt(cityEntityRequest.getCodDane());
                requestSrvSegunda.setCodDaneVt(cityEntityRequest.getCodDane());
                 centroPoblado = geograficoPoliticoMglManager.findCityByCodDaneCp(cityEntityRequest.getCodDane());
            }

            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);

            
            if (drDirEntrada.getIdTipoDireccion().equals("CK")) {
                drDirEntrada.setBarrio(responseSrv.getNeighborhood());
            }else{
                drDirEntrada.setBarrio(cityEntityRequest.getBarrio());
            }
            
            requestSrvSegunda.setAddress(responseSrv.getAlternateaddress());
            responseSrvSegunda = addressEJBRemote.queryAddressEnrich(requestSrvSegunda);

            cityEntityRequest.setExistencia(responseSrv.getExist());
            //Estandarizacion del Nodo
            cityEntityRequest.setNodo1(estandarizaNodo(responseSrv.getNodoUno()));
            cityEntityRequest.setNodo2(estandarizaNodo(responseSrv.getNodoDos()));
            cityEntityRequest.setNodo3(estandarizaNodo(responseSrv.getNodoTres()));
            cityEntityRequest.setNodoDTH(estandarizaNodo(responseSrv.getNodoDth()));

            cityEntityRequest.setEstratoDir(responseSrv.getLeveleconomic() == null ? "0"
                    : responseSrv.getLeveleconomic());
            cityEntityRequest.setEstadoDir(responseSrv.getState() == null ? ""
                    : responseSrv.getState());

            if (responseSrv != null) {
                // si el barrio viene nulo o vacio asignamos el que nos entrega el GEO
                if (responseSrv.getNeighborhood() != null
                        && !responseSrv.getNeighborhood().isEmpty()
                        && !responseSrv.getNeighborhood().equalsIgnoreCase("N.N.")) {

                    cityEntityRequest.setBarrio(responseSrv.getNeighborhood().toUpperCase());

                }
                cityEntityRequest.setExistencia(responseSrv.getExist());

                //Direccion obtenido del geo
                if (responseSrv.getAddress() == null
                        || responseSrv.getAddress().trim().isEmpty()) {
                    cityEntityRequest.setDireccion(" ");
                } else {
                    cityEntityRequest.setDireccion(responseSrv.getAddress());
                }

                //Barrio sugerido
                if (responseSrv.getAddressSuggested() != null
                        && !responseSrv.getAddressSuggested().isEmpty()) {
                    cityEntityRequest.setBarrioSugerido(
                            responseSrv.getAddressSuggested());
                }
                //confiabilidad de la direccion
                if (responseSrv.getReliability() != null) {
                    cityEntityRequest.setConfiabilidadDir((responseSrv.getReliability()));
                }

                if (cityEntityRequest.getTipoSolictud().equalsIgnoreCase(
                        co.com.claro.mgl.utils.Constant.RR_DIR_CREA_HHPP_0)
                        && responseSrv.getTraslate().equalsIgnoreCase("TRUE")) {


                        //se llenan los list que contienen las listas de los posibles hhpps de cada direccion
                        //la actual y la alternativa, se ahce aca para no depender de si es nueva o antigua solamente
                        //siemrpe se ahce para cda escenario. ya sea (nueva o antigua) o vacia
                        //<editor-fold defaultstate="collapsed" desc="listas de hhpp's ">
                        cityEntityRequest.setFuente(responseSrv.getSource());

                        if (responseSrv.getAddressCodeFound() != null
                                && !responseSrv.getAddressCodeFound().isEmpty()) {
                            direccionRRManager
                                    = new DireccionRRManager(
                                            cityEntityRequest.getActualDetalleDireccionEntity(),
                                            cityEntityRequest.getActualDetalleDireccionEntity().getMultiOrigen(), centroPoblado);

                            direccionDetalleHhpp
                                    = new DireccionRRManager(
                                            cityEntityRequest.getActualDetalleDireccionEntity(),
                                            cityEntityRequest.getActualDetalleDireccionEntity().getMultiOrigen(), centroPoblado);

                            DireccionRREntity direccionRREntityTemp = null;
                            DireccionRREntity direccionRREntity
                                    = direccionRRManager.getDireccion();

                            litUnidadStructPcml = obtenerHhppByDrDireccion(cityEntityRequest.getDireccionPrincipal(),
                                    cityEntityRequest.getCodCity(), true);

                            if (responseSrvSegunda.getAddressCodeFound() != null
                                    && !responseSrvSegunda.getAddressCodeFound().isEmpty()) {

                                detalleDireccionEntityTemp = detalleDireccionManager
                                        .conversionADetalleDireccion(
                                                responseSrvSegunda.getAddressCodeFound(),
                                                responseSrvSegunda.getAddressCode(),
                                                cityEntityRequest.getCityId(),
                                                cityEntityRequest.getBarrio(),
                                                co.com.claro.mgl.utils.Constant.PROCESO_MASIVO_HHPP);

                                detalleDireccionEntityTemp2
                                        = cityEntityRequest.getActualDetalleDireccionEntity().clone();

                                detalleDireccionEntityTemp2.setTipoviaprincipal(
                                        detalleDireccionEntityTemp.getTipoviaprincipal());
                                detalleDireccionEntityTemp2.setNumviaprincipal(
                                        detalleDireccionEntityTemp.getNumviaprincipal());
                                detalleDireccionEntityTemp2.setLtviaprincipal(
                                        detalleDireccionEntityTemp.getLtviaprincipal());
                                detalleDireccionEntityTemp2.setNlpostviap(
                                        detalleDireccionEntityTemp.getNlpostviap());
                                detalleDireccionEntityTemp2.setBisviaprincipal(
                                        detalleDireccionEntityTemp.getBisviaprincipal());
                                detalleDireccionEntityTemp2.setCuadviaprincipal(
                                        detalleDireccionEntityTemp.getCuadviaprincipal());
                                detalleDireccionEntityTemp2.setTipoviageneradora(
                                        detalleDireccionEntityTemp.getTipoviageneradora());
                                detalleDireccionEntityTemp2.setNumviageneradora(
                                        detalleDireccionEntityTemp.getNumviageneradora());
                                detalleDireccionEntityTemp2.setLtviageneradora(
                                        detalleDireccionEntityTemp.getLtviageneradora());
                                detalleDireccionEntityTemp2.setNlpostviag(
                                        detalleDireccionEntityTemp.getNlpostviag());
                                detalleDireccionEntityTemp2.setBisviageneradora(
                                        detalleDireccionEntityTemp.getBisviageneradora());
                                detalleDireccionEntityTemp2.setCuadviageneradora(
                                        detalleDireccionEntityTemp.getCuadviageneradora());

                                detalleDireccionEntityTemp2.setPlacadireccion(
                                        detalleDireccionEntityTemp.getPlacadireccion());

                                direccionRRManager = new DireccionRRManager(
                                        detalleDireccionEntityTemp2,
                                        centroPoblado.getGpoMultiorigen(), centroPoblado);
                                direccionRREntityTemp = direccionRRManager.getDireccion();

                                DrDireccion direccionAlterna
                                        = obtenerDireccionAlternaConApto(cityEntityRequest.getDireccionPrincipal(),
                                                responseSrvSegunda.getAddress(), cityEntityRequest.getCodDane());

                                if (direccionAlterna != null) {
                                    cityEntityRequest.setDireccionAlterna(direccionAlterna);
                                    litUnidadStructPcmlTemp = obtenerHhppByDrDireccion(cityEntityRequest.getDireccionAlterna(),
                                            cityEntityRequest.getCodCity(), true);
                                }
                            }

                            cityEntityRequest.setDireccionNuevaGeo(obtenerDireccionNueva(responseSrv,
                                    responseSrvSegunda, cityEntityRequest));
//</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="Codigo de direccion != null">
                            if (responseSrv.getAddressCodeFound() != null
                                    && responseSrv.getAddressCodeFound().length()
                                    != LONGITUD_TIPO_MAZANA_CASA
                                    && !responseSrv.getAlternateaddress().trim().isEmpty()
                                    && !responseSrv.getAlternateaddress().equalsIgnoreCase("NO CAMBIO")) {

                                if ((responseSrv.getSource().equalsIgnoreCase("NUEVA")
                                        || responseSrv.getSource().equalsIgnoreCase("ANTIGUA"))
                                        && (cityEntityRequest.getDireccionSinApto() != null
                                        && !cityEntityRequest.getDireccionSinApto().isEmpty())) {

                                    if (!mallaNuevaAmbigua) {
                                        //direccion Digitada es Nueva
                                        if (responseSrv.getSource().equalsIgnoreCase("NUEVA")) {
                                            cityEntityRequest.setDirIgacAntiguaStr(responseSrv.getAlternateaddress());
                                            //Direccion Nueva existe en MGL, no se deja crear la solicitud
                                            if (litUnidadStructPcml != null
                                                    && !litUnidadStructPcml.isEmpty()) {
                                                cityEntityRequest.setExisteRr("La Direccion es NUEVA["
                                                        + direccionRREntity.getCalle() + " "
                                                        + direccionRREntity.getNumeroUnidad()
                                                        + " " + direccionRREntity.getNumeroApartamento()
                                                        + "], Y existe en MGL, No se puede generar Solictud.(N)");
                                                cityEntityRequest.setDireccionRREntityAntigua(direccionRREntityTemp);
                                                cityEntityRequest.setDireccionRREntityNueva(direccionRREntity);
                                            } else {//Direccion Antigua Existe en MGL, se envia cambio de direccion de Antigua a Nueva

                                                if (litUnidadStructPcmlTemp != null
                                                        && !litUnidadStructPcmlTemp.isEmpty()) {
                                                    cityEntityRequest.setExisteRr("La Direccion es NUEVA["
                                                            + direccionRREntity.getCalle()
                                                            + " " + direccionRREntity.getNumeroUnidad()
                                                            + " " + direccionRREntity.getNumeroApartamento()
                                                            + "] Y existe en MGL como ANTIGUA["
                                                            + direccionRREntityTemp.getCalle()
                                                            + " " + direccionRREntityTemp.getNumeroUnidad()
                                                            + " " + direccionRREntityTemp.getNumeroApartamento()
                                                            + "], se modificara el HHPP a la Direccion NUEVA["
                                                            + direccionRREntity.getCalle()
                                                            + " " + direccionRREntity.getNumeroUnidad()
                                                            + " " + direccionRREntity.getNumeroApartamento()
                                                            + "].(C)");
                                                    cityEntityRequest.setDireccionRREntityAntigua(direccionRREntityTemp);
                                                    cityEntityRequest.setDireccionRREntityNueva(direccionRREntity);
                                                    cityEntityRequest.setCambioDireccion(true);
                                                } else {//Direccion Nueva y Antigua no existe en RR, se envia creacion de HHPP
                                                    cityEntityRequest.setExisteRr("La Direccion es NUEVA["
                                                            + direccionRREntity.getCalle()
                                                            + " " + direccionRREntity.getNumeroUnidad()
                                                            + " " + direccionRREntity.getNumeroApartamento()
                                                            + "]");
                                                    cityEntityRequest.setDireccionRREntityAntigua(direccionRREntityTemp);
                                                    cityEntityRequest.setDireccionRREntityNueva(direccionRREntity);
                                                    crearDireccion = true;
                                                }
                                            }
                                        } else {//direccion Digitada es Antigua
                                            //Direccion Antigua Existe en MGL, se envia cambio de direccion de Antigua a Nueva
                                            cityEntityRequest.setDirIgacAntiguaStr(responseSrv.getAddress());
                                            if ((litUnidadStructPcml != null
                                                    && !litUnidadStructPcml.isEmpty())
                                                    && (litUnidadStructPcmlTemp == null
                                                    || litUnidadStructPcmlTemp.isEmpty())) {
                                                if (responseSrv.getAmbigua().equals("0")) {
                                                    cityEntityRequest.setExisteRr("La Direccion es ANTIGUA["
                                                            + direccionRREntity.getCalle()
                                                            + " " + direccionRREntity.getNumeroUnidad()
                                                            + " " + direccionRREntity.getNumeroApartamento()
                                                            + "], Y existe en MGL, se modificara el HHPP a la Direccion NUEVA["
                                                            + direccionRREntityTemp.getCalle()
                                                            + " " + direccionRREntityTemp.getNumeroUnidad()
                                                            + " " + direccionRREntityTemp.getNumeroApartamento()
                                                            + "].(C)");
                                                    cityEntityRequest.setDireccionRREntityAntigua(direccionRREntity);
                                                    cityEntityRequest.setDireccionRREntityNueva(direccionRREntityTemp);

                                                    cityEntityRequest.setDetalleDireccionEntity(
                                                            detalleDireccionEntityTemp2);
                                                    cityEntityRequest.setCambioDireccion(true);
                                                }
                                            } else {//Direccion Nueva existe en RR, no se deja crear la solicitud
                                                if (litUnidadStructPcmlTemp != null
                                                        && !litUnidadStructPcmlTemp.isEmpty()) {
                                                    String detalleStr = "La Direccion es ANTIGUA["
                                                            + direccionRREntity.getCalle()
                                                            + " " + direccionRREntity.getNumeroUnidad()
                                                            + " " + direccionRREntity.getNumeroApartamento()
                                                            + "] Y existe en MGL como NUEVA["
                                                            + direccionRREntityTemp.getCalle()
                                                            + " " + direccionRREntityTemp.getNumeroUnidad()
                                                            + " " + direccionRREntityTemp.getNumeroApartamento()
                                                            + "], No se puede generar Solictud.(N)";
                                                    cityEntityRequest.setExisteRr(detalleStr);
                                                    detalle.setDetalle(detalleStr);
                                                    cityEntityRequest.setDireccionRREntityAntigua(direccionRREntity);
                                                } else {//Direccion Nueva y Antigua no existe en RR, se envia creacion de HHPP

                                                    cityEntityRequest.setExisteRr("La Direccion ingresada es ANTIGUA["
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
                                                    crearDireccion = true;
                                                }
                                            }
                                        }
                                    }
                                    //La direccion alterna georeferenciada no contiene el apto.
                                    ArrayList<UnidadStructPcml> litUnidadStructPcml1
                                            = obtenerHhppByDrDireccion(cityEntityRequest.getDireccionPrincipal(),
                                                    cityEntityRequest.getCodCity(), false);

                                    //Antigua
                                    ArrayList<UnidadStructPcml> litUnidadStructPcml2 = new ArrayList<>();
                                    if (cityEntityRequest.getDireccionAlterna() != null) {
                                        litUnidadStructPcml2
                                                = obtenerHhppByDrDireccion(cityEntityRequest.getDireccionAlterna(),
                                                        cityEntityRequest.getCodCity(), false);
                                    }

                                    if (litUnidadStructPcml1 != null
                                            && !litUnidadStructPcml1.isEmpty()
                                            && litUnidadStructPcml2 != null
                                            && !litUnidadStructPcml2.isEmpty()) {
                                        boolean existeHhppAntiguoNuevo = false;
                                        for (UnidadStructPcml u : litUnidadStructPcml1) {
                                            for (UnidadStructPcml d : litUnidadStructPcml2) {
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

                                    //si se decidio no cambiar de direccion se hace una ultima revision
                                    //
                                    if (!cityEntityRequest.isCambioDireccion()) {

                                        ArrayList<UnidadStructPcml> unidadesPredioAntiguoList
                                                = obtenerHhppByDrDireccion(cityEntityRequest.getDireccionAlterna(),
                                                        cityEntityRequest.getCodCity(), false);

                                        if (unidadesPredioAntiguoList != null
                                                && !unidadesPredioAntiguoList.isEmpty()) {

                                            for (UnidadStructPcml u : unidadesPredioAntiguoList) {
                                                if (u.getCalleUnidad().equalsIgnoreCase(
                                                        direccionRREntity.getCalle())
                                                        && u.getCasaUnidad().equalsIgnoreCase(
                                                                direccionRREntity.getNumeroUnidad())) {
                                                    cityEntityRequest.setCambioDireccion(true);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }//Se valida cuando Source y AdreessAlternative son nulos
                            else if (responseSrv.getSource() == null || responseSrv.getSource().isEmpty()
                                    && responseSrv.getAlternateaddress() == null
                                    || responseSrv.getAlternateaddress().isEmpty()) {
                                //si no exioste la direccion.
                                if (responseSrv.getIdaddress().equalsIgnoreCase("0")) {
                                    cityEntityRequest.setExisteRr("La Direccion será creada["
                                            + direccionRREntity.getCalle()
                                            + " " + direccionRREntity.getNumeroUnidad()
                                            + " " + direccionRREntity.getNumeroApartamento()
                                            + "]");
                                    cityEntityRequest.setDireccionRREntityNueva(direccionRREntity);
                                    crearDireccion = true;
                                } else if ((litUnidadStructPcml == null
                                        || litUnidadStructPcml.isEmpty())) {
                                    actualizarHhpp = true;
                                }
                            }
                            //</editor-fold>

                        }
                    
                }
            }

            //<editor-fold defaultstate="collapsed" desc="Actualizar subdireccion">
            if (cityEntityRequest.getTipoSolictud().equalsIgnoreCase(
                    co.com.claro.mgl.utils.Constant.RR_DIR_CREA_HHPP_0)) {
                if (responseSrv.getTraslate().equalsIgnoreCase("TRUE")) {
                    if (responseSrv.getAddressCodeFound() != null
                            && responseSrv.getAddressCodeFound().length()
                            != LONGITUD_TIPO_MAZANA_CASA
                            && !responseSrv.getAlternateaddress().equalsIgnoreCase("NO CAMBIO")) {

                        if (responseSrv.getSource().equalsIgnoreCase("NUEVA") || responseSrv.getSource().equalsIgnoreCase("ANTIGUA")) {
                            cityEntityRequest.getIdUsuario();
                            //cuando la direccion es nueva, validacion de existencia
                            if (responseSrv.getSource().equalsIgnoreCase("NUEVA")) {
                                if (responseSrv.getIdaddress().equalsIgnoreCase("0")
                                        && responseSrvSegunda.getIdaddress().equalsIgnoreCase("0")) {
                                } else if (responseSrv.getIdaddress().equalsIgnoreCase("0")
                                        && !responseSrvSegunda.getIdaddress().equalsIgnoreCase("0")) {
                                    //cuando es nueva y no exite la nueva, pero la antigua si existe y tiene el hhpp asociado.
                                    if (cityEntityRequest.getExisteRr().contains("C")) {
                                        //se envia a actualizar la direccion antigua
                                        requestSrv.setAddress(cityEntityRequest.getActualDetalleDireccionEntity().getDirCkPlaca());
                                        addressEJBRemote.updateAddressesAndSubAddress(
                                                responseSrvSegunda.getIdaddress(),
                                                usuario.getUsuario(), "MGL", requestSrv);
                                        actualizarDirDet = true;
                                    }
                                }
                            } else {
                                //la direccion es antigua
                                if (responseSrv.getIdaddress().equalsIgnoreCase("0")
                                        && responseSrvSegunda.getIdaddress().equalsIgnoreCase("0")) {
                                
                                } //cuando es una direccion antigua, que exite en base de datos,
                                //y la direccion alternativa no existe en base de datos
                                //se crea la nueva y se le actualiza al hhpp
                                else if (!responseSrv.getIdaddress().equalsIgnoreCase("0")
                                        && responseSrvSegunda.getIdaddress().equalsIgnoreCase("0")) {

                                    if (cityEntityRequest.getExisteRr().contains("C")) {
                                        //se envia a actualizar la direccion antigua
                                        requestSrv.setAddress(detalleDireccionEntityTemp2.getDirCkPlaca());
                                        addressEJBRemote.updateAddressesAndSubAddress(
                                                responseSrv.getIdaddress(),
                                                usuario.getUsuario(), "MGL", requestSrv);
                                        actualizarDirDet = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (drDirEntrada.getIdTipoDireccion().equals("IT")) {

                if (responseSrv.getAddress() != null) {
                    litUnidadStructPcml = obtenerHhppByDrDireccion(cityEntityRequest.getDireccionPrincipal(),
                            cityEntityRequest.getCodCity(), true);
                    if ((litUnidadStructPcml == null || litUnidadStructPcml.isEmpty())) {
                        crearDireccion = true;
                    } else {
                        actualizarHhpp = true;
                    }
                    direccionDetalleHhpp = new DireccionRRManager(
                            cityEntityRequest.getActualDetalleDireccionEntity(),
                             cityEntityRequest.getActualDetalleDireccionEntity().getMultiOrigen(), centroPoblado);
                } else {
                    throw new ApplicationException("Esta dirección no se pudo georeferenciar");
                }
            }

            //</editor-fold>
            if (crearDireccion) {
                String idAddress = crearDireccion(requestSrv, drDirEntrada, usuario.getUsuario());
                responseSrv.setIdaddress(idAddress);
                BigDecimal direccionId = null;
                BigDecimal subDireccionId = null;
                //si l oque se crea e la direccion y la subdireccion 
                //buscamos la direccion por codigo de subdireccion para enviarselo al hhpp
                if (idAddress.contains("s")) {
                    SubDireccionMglManager manager = new SubDireccionMglManager();
                    subDireccionId = new BigDecimal(idAddress.replace("s", ""));
                    SubDireccionMgl subDireccionMgl = manager.findById(subDireccionId);
                    direccionId = subDireccionMgl.getDirId();
                } else {
                    direccionId = new BigDecimal(idAddress.replace("d", ""));
                }
                actualizarHhppMglRr(direccionId, subDireccionId, direccionDetalleHhpp, drDirEntrada, detalle);
            } else if (actualizarHhpp) {
                SubDireccionMglManager manager = new SubDireccionMglManager();
                String idAdreessSt = responseSrv.getIdaddress();
                BigDecimal direccionId = null;
                BigDecimal subDireccionId = null;
                //si lo que se crea e la direccion y la subdireccion 
                //buscamos la direccion por codigo de subdireccion para enviarselo al hhpp
                if (idAdreessSt.contains("s")) {
                    subDireccionId = new BigDecimal(idAdreessSt.replace("s", ""));
                    SubDireccionMgl subDireccionMgl = manager.findById(subDireccionId);
                    direccionId = subDireccionMgl.getDirId();
                } else {
                    direccionId = new BigDecimal(idAdreessSt.replace("d", ""));
                }

                actualizarHhppMglRr(direccionId, subDireccionId, direccionDetalleHhpp, drDirEntrada, detalle);

            } else if (actualizarDirDet) {
                actualizarDirDetalalda(cityEntityRequest);
            }

            return responseSrv.getIdaddress() == null
                    || responseSrv.getIdaddress().equalsIgnoreCase("0")
                    ? null : responseSrv.getIdaddress();
        } catch (ApplicationException | ExceptionDB | IOException | CloneNotSupportedException ex) {
            String msg = "Error al validar la direccion. EX000: " + ex.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, ex);
        }
    }

    /**
     *
     * @param request informacion de la direccion a crear
     * @param response del servicio georeferenciador
     * @param usuario
     * @return el Id de la direccion creada
     * @throws IOException
     * @throws ExceptionDB
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String crearDireccion(AddressRequest request, DrDireccion drdirEntrada, String usuario) throws IOException, ExceptionDB {
        String idDireccion = "";
        try {
            AddressEJB addressEJB = new AddressEJB();
            ResponseMessage responseMessage = addressEJB.createAddress(request, usuario, Constantes.NOMBRE_FUNCIONALIDAD, "", drdirEntrada);
            idDireccion = responseMessage.getIdaddress();
        } catch (ExceptionDB | IOException ex) {
            LOGGER.error("Error al buscar la direccion ", ex);
            throw ex;
        }
        return idDireccion;
    }

    public void actualizarDirDetalalda(CityEntity city) throws ApplicationException {
        CmtDireccionDetalleMglManager cddmm = new CmtDireccionDetalleMglManager();
        CmtDireccionDetalladaMgl direccionDetallada = cddmm.findByHhPP(hhpp);
        direccionDetallada.setIdTipoDireccion(city.getDireccionPrincipal().getIdTipoDireccion());
        direccionDetallada.setDirPrincAlt(city.getDireccionPrincipal().getDirPrincAlt());
        direccionDetallada.setBarrio(city.getDireccionPrincipal().getBarrio());
        direccionDetallada.setTipoViaPrincipal(city.getDireccionPrincipal().getTipoViaPrincipal());
        direccionDetallada.setNumViaPrincipal(city.getDireccionPrincipal().getNumViaPrincipal());
        direccionDetallada.setLtViaPrincipal(city.getDireccionPrincipal().getLtViaPrincipal());
        direccionDetallada.setNlPostViaP(city.getDireccionPrincipal().getNlPostViaP());
        direccionDetallada.setBisViaPrincipal(city.getDireccionPrincipal().getBisViaPrincipal());
        direccionDetallada.setCuadViaPrincipal(city.getDireccionPrincipal().getCuadViaPrincipal());
        direccionDetallada.setTipoViaGeneradora(city.getDireccionPrincipal().getTipoViaGeneradora());
        direccionDetallada.setNumViaGeneradora(city.getDireccionPrincipal().getNumViaGeneradora());
        direccionDetallada.setLtViaGeneradora(city.getDireccionPrincipal().getLtViaGeneradora());
        direccionDetallada.setNlPostViaG(city.getDireccionPrincipal().getNlPostViaG());
        direccionDetallada.setBisViaGeneradora(city.getDireccionPrincipal().getBisViaGeneradora());
        direccionDetallada.setCuadViaGeneradora(city.getDireccionPrincipal().getCuadViaGeneradora());
        direccionDetallada.setPlacaDireccion(city.getDireccionPrincipal().getPlacaDireccion());
        direccionDetallada.setCpTipoNivel1(city.getDireccionPrincipal().getCpTipoNivel1());
        direccionDetallada.setCpTipoNivel2(city.getDireccionPrincipal().getCpTipoNivel2());
        direccionDetallada.setCpTipoNivel3(city.getDireccionPrincipal().getCpTipoNivel3());
        direccionDetallada.setCpTipoNivel4(city.getDireccionPrincipal().getCpTipoNivel4());
        direccionDetallada.setCpTipoNivel5(city.getDireccionPrincipal().getCpTipoNivel5());
        direccionDetallada.setCpTipoNivel6(city.getDireccionPrincipal().getCpTipoNivel6());
        direccionDetallada.setCpValorNivel1(city.getDireccionPrincipal().getCpValorNivel1());
        direccionDetallada.setCpValorNivel2(city.getDireccionPrincipal().getCpValorNivel2());
        direccionDetallada.setCpValorNivel3(city.getDireccionPrincipal().getCpValorNivel3());
        direccionDetallada.setCpValorNivel4(city.getDireccionPrincipal().getCpValorNivel4());
        direccionDetallada.setCpValorNivel5(city.getDireccionPrincipal().getCpValorNivel5());
        direccionDetallada.setCpValorNivel6(city.getDireccionPrincipal().getCpValorNivel6());
        direccionDetallada.setMzTipoNivel1(city.getDireccionPrincipal().getMzTipoNivel1());
        direccionDetallada.setMzTipoNivel2(city.getDireccionPrincipal().getMzTipoNivel2());
        direccionDetallada.setMzTipoNivel3(city.getDireccionPrincipal().getMzTipoNivel3());
        direccionDetallada.setMzTipoNivel4(city.getDireccionPrincipal().getMzTipoNivel4());
        direccionDetallada.setMzTipoNivel5(city.getDireccionPrincipal().getMzTipoNivel5());
        direccionDetallada.setMzValorNivel1(city.getDireccionPrincipal().getMzValorNivel1());
        direccionDetallada.setMzValorNivel2(city.getDireccionPrincipal().getMzValorNivel2());
        direccionDetallada.setMzValorNivel3(city.getDireccionPrincipal().getMzValorNivel3());
        direccionDetallada.setMzValorNivel4(city.getDireccionPrincipal().getMzValorNivel4());
        direccionDetallada.setMzValorNivel5(city.getDireccionPrincipal().getMzValorNivel5());
        direccionDetallada.setItTipoPlaca(city.getDireccionPrincipal().getItTipoPlaca());
        direccionDetallada.setItValorPlaca(city.getDireccionPrincipal().getItValorPlaca());
        direccionDetallada.setLetra3G(city.getDireccionPrincipal().getLetra3G());
        
        int perfil = usuario.getIdPerfil() != null ? Integer.parseInt(usuario.getIdPerfil().toString()) : 0;
        cddmm.update(direccionDetallada, usuario.getUsuario(), perfil);
    }

    /**
     *
     * @param direccionId Id del hhpp a actualizar
     * @param subdireccionId
     * @param direccionRRManager detalle de informacion actualizada del hhpp
     * @throws ApplicationException
     */
    public void actualizarHhpp(BigDecimal direccionId, BigDecimal subdireccionId, DireccionRRManager direccionRRManager)
            throws ApplicationException {

        DireccionMgl direccion = new DireccionMgl();
        direccion.setDirId(direccionId);
        DireccionMgl direccionTemp = direccionMglManager.findById(direccion);
        
        NodoMglManager mglManager = new NodoMglManager();
        NodoMgl nodo = mglManager.findById(hhpp.getNodId().getNodId());
        
        String comunidad = hhpp.getNodId().getComId().getCodigoRr();
        String division = hhpp.getNodId().getComId().getRegionalRr().getCodigoRr();
        
        BigDecimal centropoblado = hhpp.getDireccionObj().getUbicacion().getGpoIdObj().getGpoId();
        String codInterno = nodo.getNodTipo().getIdentificadorInternoApp();        
       
        if (centropoblado.toString().equals(Constant.BOGOTA_CP_ID)
                && codInterno.equals(co.com.claro.mgl.utils.Constant.HFC_UNI)
                || codInterno.equals(co.com.claro.mgl.utils.Constant.DTH)) {
            comunidad = Constant.ZBO;
            division = Constant.CCB;

        }
          
        SubDireccionMgl subdireccion = new SubDireccionMgl();
        subdireccion.setSdiId(subdireccionId);
        if (subdireccionId == null || subdireccionId == new BigDecimal(BigInteger.ZERO)) {
            hhpp.setSubDireccionObj(null);
        } else {
            subdireccion = subDireccionMglManager.findById(subdireccionId);
            hhpp.setSubDireccionObj(subdireccion);
        }
        
        hhpp.setDireccionObj(direccionTemp);
        hhpp.setHhpCalle(direccionRRManager.getDireccion().getCalle());
        hhpp.setHhpPlaca(direccionRRManager.getDireccion().getNumeroUnidad());
        hhpp.setHhpApart(direccionRRManager.getDireccion().getNumeroApartamento());
        hhpp.setHhpComunidad(comunidad);
        hhpp.setHhpDivision(division);
        hhppMglManager.update(hhpp);
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

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    public static String estandarizaNodo(String nodo) {
        String nodoResult = nodo;
        if (isNodo6080(nodo)) {
            nodoResult = "0" + nodo.trim();
        }
        return nodoResult;
    }

    /**
     * Función que obtiene los hhpp asociados a una dirección que se recibe en
     * String y se convierte a DrDireccion
     *
     * @param drDireccion
     * @param codigoDaneCentroPoblado
     * @param drDireccionApto true para indicar si se desea realizar la busqueda
     * con apto de la direccion
     * @author Juan David Hernandez
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ArrayList<UnidadStructPcml> obtenerHhppByDrDireccion(
            DrDireccion drDireccion, String codigoDaneCentroPoblado,
            boolean drDireccionApto) throws ApplicationException {

        try {
            DrDireccion drDireccionActual = drDireccion.clone();
            ArrayList<UnidadStructPcml> unidadesList = new ArrayList<>();

            if (drDireccionActual != null && codigoDaneCentroPoblado != null
                    && !codigoDaneCentroPoblado.isEmpty()) {

                CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();

                // Si es False se le quita el apto al DrDireccion
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

                // Hallamos el centro poblado de la dirección apartir del codigoDane
                GeograficoPoliticoManager geoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl centroPoblado = null;

                centroPoblado = (codigoDaneCentroPoblado.length() <= 3 || codigoDaneCentroPoblado.isEmpty())
                        ? geoManager.findCentroPobladoCodDane(
                                geoManager.findGeoPoliticoByCodigo(codigoDaneCentroPoblado).getGeoCodigoDane())
                        : geoManager.findCentroPobladoCodDane(codigoDaneCentroPoblado);

                if (centroPoblado != null) {
                    // Obtenemos los hhpp asociados a la dirección antigua
                    List<HhppMgl> hhppDirList = cmtDireccionDetalleMglManager
                            .findHhppByDireccion(drDireccion, centroPoblado.getGpoId(), true,
                                    0, 0, false);

                    // Los hhpp encontrados los pasamos a la estructura de unidades.
                    if (hhppDirList != null && !hhppDirList.isEmpty()) {
                        for (HhppMgl hhppMgl : hhppDirList) {
                            UnidadStructPcml unidad = new UnidadStructPcml();
                            unidad.setCalleUnidad(hhppMgl.getHhpCalle() != null
                                    ? hhppMgl.getHhpCalle()
                                    : "");
                            unidad.setCasaUnidad(hhppMgl.getHhpPlaca() != null
                                    ? hhppMgl.getHhpPlaca()
                                    : "");
                            unidad.setAptoUnidad(hhppMgl.getHhpApart() != null
                                    ? hhppMgl.getHhpApart()
                                    : "");
                            unidad.setEstadUnidadad(
                                    hhppMgl.getEhhId().getEhhNombre() != null ? hhppMgl.getEhhId().getEhhNombre() : "");
                            unidad.setEstratoUnidad(hhppMgl.getDireccionObj().getDirEstrato() != null
                                    ? hhppMgl.getDireccionObj().getDirEstrato().toString()
                                    : "");
                            unidad.setNodUnidad(
                                    hhppMgl.getNodId().getNodNombre() != null ? hhppMgl.getNodId().getNodNombre() : "");
                            unidadesList.add(unidad);
                        }

                    }
                }

            }
            return unidadesList;
        } catch (ApplicationException | CloneNotSupportedException ex) {
            LOGGER.error("Error al obtener Hhpp por DireccionStr ".concat(ex.getMessage()), ex);
        }
        return new ArrayList<>();
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
     * @param codigoDaneCentroPoblado
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DrDireccion obtenerDireccionAlternaConApto(
            DrDireccion direccionPrincipal, String direccionAlternaStr,
            String codigoDaneCentroPoblado) throws ApplicationException {
        DrDireccion drDireccionAlternaApto = null;
        try {
            if (codigoDaneCentroPoblado != null
                    && !codigoDaneCentroPoblado.isEmpty()
                    && direccionAlternaStr != null && !direccionAlternaStr.isEmpty()
                    && direccionPrincipal != null) {

                CmtValidadorDireccionesManager validadorDireccionesManager
                        = new CmtValidadorDireccionesManager();

                //Hallamos el centro poblado de la dirección apartir del codigoDane
                GeograficoPoliticoManager geoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl centroPoblado = geoManager.findCentroPobladoCodDane(codigoDaneCentroPoblado);

                if (centroPoblado != null) {

                    drDireccionAlternaApto = validadorDireccionesManager
                            .convertirDireccionStringADrDireccion(
                                    direccionAlternaStr, centroPoblado.getGpoId());

                    drDireccionAlternaApto.setCpTipoNivel1(direccionPrincipal.getCpTipoNivel1()
                            != null ? direccionPrincipal.getCpTipoNivel1() : null);
                    drDireccionAlternaApto.setCpTipoNivel2(direccionPrincipal.getCpTipoNivel2()
                            != null ? direccionPrincipal.getCpTipoNivel2() : null);
                    drDireccionAlternaApto.setCpTipoNivel3(direccionPrincipal.getCpTipoNivel3()
                            != null ? direccionPrincipal.getCpTipoNivel3() : null);
                    drDireccionAlternaApto.setCpTipoNivel4(direccionPrincipal.getCpTipoNivel4()
                            != null ? direccionPrincipal.getCpTipoNivel4() : null);
                    drDireccionAlternaApto.setCpTipoNivel5(direccionPrincipal.getCpTipoNivel4()
                            != null ? direccionPrincipal.getCpTipoNivel4() : null);
                    drDireccionAlternaApto.setCpTipoNivel6(direccionPrincipal.getCpTipoNivel4()
                            != null ? direccionPrincipal.getCpTipoNivel4() : null);

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
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al obtener la direccion alterna en drDireccion con apto: ".concat(ex.getMessage()));
        }
        return drDireccionAlternaApto;
    }

    private static boolean isNodo6080(String nodo) {
        Pattern pat = Pattern.compile("6[0-9]|7[234789]|80");
        Matcher mat = pat.matcher(nodo);
        return !nodo.trim().isEmpty() && (mat.matches());
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
    public DrDireccion obtenerDireccionNueva(
            AddressService responseSrv, 
            AddressService responseSrvSegunda,
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
                if (responseSrvSegunda.getSource() != null
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

    public void actualizarHhppMglRr(BigDecimal direccionId, BigDecimal subDireccionId,
            DireccionRRManager direccionRRManager, DrDireccion drDirEntrada, DetalleCargaHhppMgl detalle) throws ApplicationException {
        hhpp =detalle.getHhppMgl();
        try {
            actualizarHhpp(direccionId, subDireccionId, direccionRRManager);
            detalle.setEstado(DetalleCargaHhppMgl.PROCESADO);
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage());
            detalle.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
        }
        String houseNumberNew = hhpp.getHhpPlaca();
        String streetNameNew = hhpp.getHhpCalle();
        String apartmentNumberNew = hhpp.getHhpApart();
        
        detalle.getHhppMgl().setHhpEdificio(drDirEntrada.getBarrio());
        
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl geograficoPolitico
                = geograficoPoliticoManager.findById(detalle.getHhppMgl().getNodId().getGpoId());
        if (geograficoPolitico.getGpoMultiorigen().equals("1") && drDirEntrada.getBarrio() != null) {
            detalle.getHhppMgl().setHhpCalle(streetNameNew);
        }
        String tipoDir = drDirEntrada.getIdTipoDireccion();
        String barrio = drDirEntrada.getBarrio();

        CmtComunidadRr comunidadRegionalRr = hhpp.getNodId().getComId();
        //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
        boolean habilitarRR = false;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.RR_HABILITADO)) {
            habilitarRR = true;
        }
        
        //JDHT
        //Se corta la dependencia de RR, Si RR esta encendido y el HHPP tiene ID RR
        if (habilitarRR && hhpp.getHhpIdrR() != null) {
            HhppResponseRR hhppResponseRR = direccionRRManager.getHhppByIdRR(hhpp.getHhpIdrR());
            if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {
                direccionRRManager.cambiarDirHHPPRR_Masiva(hhppResponseRR.getComunidad(), hhppResponseRR.getDivision(),
                        hhppResponseRR.getHouse(),
                        hhppResponseRR.getStreet(), hhppResponseRR.getApartamento(), 
                        hhppResponseRR.getComunidad(), hhppResponseRR.getDivision(), houseNumberNew,
                        streetNameNew, apartmentNumberNew, tipoDir, comunidadRegionalRr, barrio);
            }
        }
    }
}
