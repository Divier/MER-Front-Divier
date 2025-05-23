package co.com.claro.visitasTecnicas.business;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.CreateHhppRequestDto;
import co.com.claro.mer.dtos.response.procedure.CreateHhppResponseDto;
import co.com.claro.mer.homepassed.dto.HomePassedDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.EstadoHhppMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.businessmanager.address.TipoHhppConexionMglManager;
import co.com.claro.mgl.businessmanager.address.TipoHhppMglManager;
import co.com.claro.mgl.businessmanager.address.TipoHhppRedMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSubEdificioMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.dtos.CmtHhppMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppConexionMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppRedMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtUtilidadesCM;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.Marcas;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author user
 */
public class HHPPManager {

    private static final Logger LOGGER = LogManager.getLogger(HHPPManager.class);
    private static final int OPT_SAVE_IN_REPO = 2;
    private static final int OPT_PERSIST_HHPP = 1;
    private CmtTipoBasicaMgl cmtTipoBasicaMgl = null;

    /**
     * Permite Actualizar La cobertura de Un HHPP de RR en el repositorio
     * Mediate el id de HHPP de RR
     *
     * @param idHhppRr
     * @return String id del HHPP de RR
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String coverageRepositoryUpdate(String idHhppRr) throws ApplicationException {
        HhppMglManager hhppMglManager = new HhppMglManager();
        DireccionMglManager direccionMglManager = new DireccionMglManager();
        SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        HhppMgl hhppMgl = new HhppMgl();
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
        if (idHhppRr == null || idHhppRr.isEmpty()) {
            throw new ApplicationException("El parametro idHhppRr es obligatorio");
        }
        if (!CmtUtilidadesCM.isNumeber(idHhppRr)) {
            throw new ApplicationException("El valor idHhppRr debe ser numerico");
        }
        hhppMgl = hhppMglManager.findHhppByIdRROne(idHhppRr);
        if (hhppMgl == null) {
            return "0";
        }
        SubDireccionMgl subDireccionMgl = hhppMgl.getSubDireccionObj();
        DireccionMgl direccionMgl = hhppMgl.getDireccionObj();

        if (direccionMgl != null) {
            AddressRequest addressRequest = new AddressRequest();
            GeograficoPoliticoMgl geograficoPoliticoMgl;
            geograficoPoliticoMgl = geograficoPoliticoManager.findById(direccionMgl.getUbicacion().getGpoIdObj().getGeoGpoId());

            addressRequest.setState(geograficoPoliticoMgl.getGpoNombre());
            addressRequest.setCity(direccionMgl.getUbicacion().getGpoIdObj().getGpoNombre());
            addressRequest.setCodDaneVt(direccionMgl.getUbicacion().getGpoIdObj().getGeoCodigoDane());
            addressRequest.setLevel("C");
            addressRequest.setAddress(direccionMgl.getDirFormatoIgac());
            if (direccionMgl.getUbicacion().getGpoIdObj().getGpoMultiorigen().equalsIgnoreCase("1")) {
                if (direccionMgl.getUbicacion().getGeoIdObj() != null) {
                    if (direccionMgl.getUbicacion().getGeoIdObj().getTipoGeografico().
                            compareTo(new BigDecimal(2)) == 0) {
                        addressRequest.setNeighborhood(direccionMgl.getUbicacion().
                                getGeoIdObj().getGeoNombre());
                    } else if (direccionMgl.getUbicacion().getGeoIdObj().
                            getTipoGeografico().compareTo(new BigDecimal(3)) == 0) {
                        addressRequest.setNeighborhood(".");
                    } else {
                        throw new ApplicationException("El valor idHhppRr debe ser numerico");
                    }
                }
            }
            AddressService addressService = addressEJBRemote.queryAddress(addressRequest);
            if (addressService == null || addressService.getIdaddress() == null) {
                throw new ApplicationException("No se puede actualizar la cobertura, Fallo en Sitidata");
            }
            if (direccionMgl.getUbicacion().getGpoIdObj().getGpoMultiorigen().equalsIgnoreCase("1")) {
                if (!addressRequest.getNeighborhood().equalsIgnoreCase(".")
                        && addressService.getNeighborhood().equalsIgnoreCase(addressRequest.getNeighborhood())) {

                    direccionMgl.setDirNodouno(addressService.getNodoUno());
                    direccionMgl.setDirNododos(addressService.getNodoDos());
                    direccionMgl.setDirNodotres(addressService.getNodoTres());
                    direccionMgl.setDirNodoDth(addressService.getNodoDth());
                    direccionMgl.setDirNodoMovil(addressService.getNodoMovil());
                    direccionMgl.setDirNodoFtth(addressService.getNodoFtth());
                    direccionMgl.setDirNodoWifi(addressService.getNodoWifi());
                    direccionMglManager.update(direccionMgl);
                    if (subDireccionMgl != null) {
                        subDireccionMgl.setSdiNodouno(addressService.getNodoUno());
                        subDireccionMgl.setSdiNododos(addressService.getNodoDos());
                        subDireccionMgl.setSdiNodotres(addressService.getNodoTres());
                        subDireccionMgl.setSdiNodoDth(addressService.getNodoDth());
                        subDireccionMgl.setSdiNodoMovil(addressService.getNodoMovil());
                        subDireccionMgl.setSdiNodoFtth(addressService.getNodoFtth());
                        subDireccionMgl.setSdiNodoWifi(addressService.getNodoWifi());
                        subDireccionMglManager.update(subDireccionMgl);
                    }
                }
            } else {
                direccionMgl.setDirNodouno(addressService.getNodoUno());
                direccionMgl.setDirNododos(addressService.getNodoDos());
                direccionMgl.setDirNodotres(addressService.getNodoTres());
                direccionMgl.setDirNodoDth(addressService.getNodoDth());
                direccionMgl.setDirNodoMovil(addressService.getNodoMovil());
                direccionMgl.setDirNodoFtth(addressService.getNodoFtth());
                direccionMgl.setDirNodoWifi(addressService.getNodoWifi());
                direccionMglManager.update(direccionMgl);
                if (subDireccionMgl != null) {
                    subDireccionMgl.setSdiNodouno(addressService.getNodoUno());
                    subDireccionMgl.setSdiNododos(addressService.getNodoDos());
                    subDireccionMgl.setSdiNodotres(addressService.getNodoTres());
                    subDireccionMgl.setSdiNodoDth(addressService.getNodoDth());
                    subDireccionMgl.setSdiNodoMovil(addressService.getNodoMovil());
                    subDireccionMgl.setSdiNodoFtth(addressService.getNodoFtth());
                    subDireccionMgl.setSdiNodoWifi(addressService.getNodoWifi());
                    subDireccionMglManager.update(subDireccionMgl);
                }
            }
        }
        return hhppMgl.getHhpId().toString().trim();
    }

    /**
     * Permite realizar una busqueda de un HHPP sobre el repositorio por el Id
     * RR
     *
     * @param idHhppRr
     * @return String id del HHPP de RR
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String findByIdRr(String idHhppRr) throws ApplicationException {
        HhppMglManager hhppMglManager = new HhppMglManager();
        HhppMgl hhppMgl;
        if (idHhppRr == null || idHhppRr.isEmpty()) {
            throw new ApplicationException("El parametro idHhppRr es obligatorio");
        }
        if (!CmtUtilidadesCM.isNumeber(idHhppRr)) {
            throw new ApplicationException("El valor idHhppRr debe ser numerico");
        }
        hhppMgl = hhppMglManager.findHhppByIdRROne(idHhppRr);
        if (hhppMgl == null) {
            return "0";
        }
        return hhppMgl.getHhpId().toString().trim();
    }

    /**
     * Permite validar si una direccion es viable wifi movil
     *
     * @param idHhppRr
     * @return String S=viable N=no viable;
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String viavilidadWifiDth(String idHhppRr) throws ApplicationException {
        String comunidadDth = ParametrosMerUtil.findValor(Constant.ACRONIMO_COMUNIDAD_DTH_LTE);
        HhppMglManager hhppMglManager = new HhppMglManager();

        if (StringUtils.isEmpty(idHhppRr)) {
            throw new ApplicationException("El parametro idHhppRr es obligatorio");
        }

        if (!CmtUtilidadesCM.isNumeber(idHhppRr)) {
            throw new ApplicationException("El valor idHhppRr debe ser numerico");
        }

        HhppMgl hhppMgl = hhppMglManager.findHhppByIdRROne(idHhppRr);
        if (hhppMgl == null) {
            return "X";
        }
        //Validamos si la comunidad es una comunidad especial para venta LTE
        if (hhppMgl.getHhpComunidad() != null
                && StringUtils.isNotBlank(comunidadDth)
                && comunidadDth.contains(hhppMgl.getHhpComunidad())) {
            return "S";
        }

        //Si el HHPP es una subdireccion valida la viabilidad con la subdireccion.
        if (hhppMgl.getSubDireccionObj() != null) {
            if (hhppMgl.getSubDireccionObj().getSdiNodoWifi() != null
                    && !hhppMgl.getSubDireccionObj().getSdiNodoWifi().isEmpty()
                    && !hhppMgl.getSubDireccionObj().getSdiNodoWifi().equalsIgnoreCase("NA")) {
                return "S";
            } else {
                return "N";
            }
        } else {
            if (hhppMgl.getDireccionObj().getDirNodoWifi() != null
                    && !hhppMgl.getDireccionObj().getDirNodoWifi().isEmpty()
                    && !hhppMgl.getDireccionObj().getDirNodoWifi().equalsIgnoreCase("NA")) {
                return "S";
            } else {
                return "N";
            }
        }
    }

    /**
     * Permite realizar la creacion o actualizacion de un HHPP en Repositorio
     * direcciones.
     *
     * @param hhpp
     * @param nombreFuncionalidad
     * @return BigDesimal 0 si no pudo completar la tarea id de HHPP si completa
     * la tarea.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal updateCreateHhppDb(CmtHhppMglDto hhpp,
            String nombreFuncionalidad) throws ApplicationException {

        HhppMglManager hhppMglManager = new HhppMglManager();
        EstadoHhppMglManager estadoHhppMglManager = new EstadoHhppMglManager();
        DireccionMglManager direccionMglManager = new DireccionMglManager();
        NodoMglManager nodoMglManager = new NodoMglManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        TipoHhppConexionMglManager tipoHhppConexionMglManager = new TipoHhppConexionMglManager();
        TipoHhppRedMglManager tipoHhppRedMglManager = new TipoHhppRedMglManager();
        TipoHhppMglManager tipoHhppMglManager = new TipoHhppMglManager();
        SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();

        EstadoHhppMgl estadoHhpp = null;
        DireccionMgl direccionMgl = null;
        NodoMgl nodoMgl;
        CmtSubEdificioMgl cmtSubEdificioMgl;
        TipoHhppConexionMgl tipoHhppConexionMgl = null;
        TipoHhppRedMgl tipoHhppRedMgl = null;
        TipoHhppMgl tipoHhppMgl = null;
        SubDireccionMgl subDireccionMgl = null;
        HhppMgl hhppMgl = new HhppMgl();

        BigDecimal direccionId;
        BigDecimal subDireccionId;
        BigDecimal subEdificioId;
        BigDecimal tipoConexionId;
        BigDecimal tipoRedId;
        BigDecimal hhppId;
        Date fechaAuditoriaRr;
        String estUnit;

        if (hhpp == null || nombreFuncionalidad == null || nombreFuncionalidad.isEmpty()) {
            throw new ApplicationException("Los parametros son Obligatorios");
        }

        if (hhpp.getUsuario() == null || hhpp.getUsuario().isEmpty()) {
            throw new ApplicationException("El  'Usuario' es obligatorio");
        }

        /*@author Juan David Hernandez*/
 /*Ajuste de busqueda se sube en el metodo para obtener la tecnologia 
         * y enviarla al metodo que obtiene el estado homologado*/
        if (hhpp.getNodoRrId() == null || hhpp.getNodoRrId().isEmpty()) {
            throw new ApplicationException("El nodo  'NodoRrId' es obligatorio");
        } else {
            if (!nodoMglManager.isNodoCertificado(hhpp.getNodoRrId())) {
                throw new ApplicationException("El nodo digitado 'NodoRrId' no esta certificado o no existe en RR");
            } else {
                nodoMgl = nodoMglManager.findByCodigo(hhpp.getNodoRrId());
                if (nodoMgl == null) {
                    throw new ApplicationException("El valor digitado en 'NodoRrId' no existe en MGL y Repositorio");
                }
                hhppMgl.setNodId(nodoMgl);
            }
        }

        if (hhpp.getHhppRepositorioId() == null || hhpp.getHhppRepositorioId().isEmpty()) {

            if (hhpp.getEstadoHhppRepositorioId() == null || hhpp.getEstadoHhppRepositorioId().isEmpty()) {
                throw new ApplicationException("El valor 'EstadoHhppRepositorioId' es obligatorio");
            } else {
                try {
                    estadoHhpp = estadoHhppMglManager.find(hhpp.getEstadoHhppRepositorioId());
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException("El valor digitado en 'EstadoHhppRepositorioId'"
                            .concat(" no existe en repositorio de direcciones"));
                }
                //tipo basica que realiza homologacion de estados de hhpp
                cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                        co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADOS_HHPP);
                estadoHhpp.setEhhID(cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, estadoHhpp.getEhhID(), true));
                hhppMgl.setEhhId(estadoHhpp);
            }

            if (hhpp.getDireccionRepositorioId() == null || hhpp.getDireccionRepositorioId().isEmpty()) {
                throw new ApplicationException("El valor 'DireccionRepositorioId' es obligatorio");
            } else {
                if (!(hhpp.getDireccionRepositorioId().substring(0, 1).equalsIgnoreCase("D")
                        || hhpp.getDireccionRepositorioId().substring(0, 1).equalsIgnoreCase("S"))
                        || !(hhpp.getDireccionRepositorioId().length() >= 2)) {
                    throw new ApplicationException("El id debe estar precesido por 'd' o por 's' para identificar direccion ".
                            concat("o subdireccion, debe contener mas de dos caracters"));
                } else {
                    String direccionTemId = hhpp.getDireccionRepositorioId().substring(1);
                    String categoriaDireccion = hhpp.getDireccionRepositorioId().substring(0, 1);
                    if (!CmtUtilidadesCM.isNumeber(direccionTemId)) {
                        throw new ApplicationException("El valor digitado en 'DireccionRepositorioId' debe 'd#####' o 's#####'");
                    } else {

                        if (categoriaDireccion.equalsIgnoreCase("d")) {
                            direccionId = new BigDecimal(direccionTemId);
                            try {
                                direccionMgl = new DireccionMgl();
                                direccionMgl.setDirId(direccionId);
                                direccionMgl = direccionMglManager.findById(direccionMgl);
                            } catch (ApplicationException e) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+
                                ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                                throw new ApplicationException("El valor digitado en 'DireccionRepositorioId'".
                                        concat("no existe en repositorio de direcciones"));
                            }
                            hhppMgl.setDirId(direccionMgl.getDirId());
                        } else {
                            subDireccionId = new BigDecimal(direccionTemId);
                            try {
                                subDireccionMgl = subDireccionMglManager.findById(subDireccionId);
                            } catch (ApplicationException e) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+
                                ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                                throw new ApplicationException("El valor digitado en 'DireccionRepositorioId'".
                                        concat("no existe en repositorio de direcciones"));
                            }
                            hhppMgl.setSdiId(subDireccionMgl.getSdiId());

                            try {
                                direccionMgl = new DireccionMgl();
                                direccionMgl.setDirId(subDireccionMgl.getDirId());
                                direccionMgl = direccionMglManager.findById(direccionMgl);
                            } catch (ApplicationException e) {
                                String msg = "El valor digitado en 'DireccionRepositorioId'".
                                        concat("no existe en repositorio de direcciones");
                                LOGGER.error(msg + e.getMessage());
                                throw new ApplicationException(msg, e);
                            }
                            hhppMgl.setDirId(direccionMgl.getDirId());
                        }
                    }
                }
            }

            if (hhpp.getSubEdificioIdRepositorio() != null && !hhpp.getSubEdificioIdRepositorio().isEmpty()) {
                if (!CmtUtilidadesCM.isNumeber(hhpp.getSubEdificioIdRepositorio())) {
                    throw new ApplicationException("El valor digitado en 'SubEdificioIdRepositorio' debe ser númerico");
                } else {
                    hhppMgl.setSubDireccionObj(subDireccionMgl);
                }
            }

            if (hhpp.getTipoConexionRepositorioId() == null || hhpp.getTipoConexionRepositorioId().isEmpty()) {
                throw new ApplicationException("El campo  'TipoConexionRepositorioId' es obligatorio");
            } else {
                if (!CmtUtilidadesCM.isNumeber(hhpp.getTipoConexionRepositorioId())) {
                    throw new ApplicationException("El valor digitado en 'TipoConexionRepositorioId' debe ser númerico");
                } else {
                    tipoConexionId = new BigDecimal(hhpp.getTipoConexionRepositorioId());
                    try {
                        tipoHhppConexionMgl = tipoHhppConexionMglManager.findById(tipoConexionId);
                    } catch (ApplicationException e) {

                        String msg = "El valor digitado en 'TipoConexionRepositorioId' "
                                .concat("no existe en repositorio de direcciones");
                        LOGGER.error(msg + e.getMessage());
                        throw new ApplicationException(msg, e);
                    }
                    hhppMgl.setThcId(tipoHhppConexionMgl.getThcId());
                }
            }
            if (hhpp.getTipoRedRepositorioId() == null || hhpp.getTipoRedRepositorioId().isEmpty()) {
                throw new ApplicationException("El campo  'TipoRedRepositorioId' es obligatorio");
            } else {
                if (!CmtUtilidadesCM.isNumeber(hhpp.getTipoRedRepositorioId())) {
                    throw new ApplicationException("El valor digitado en 'TipoRedRepositorioId' debe ser númerico");
                }
                tipoRedId = new BigDecimal(hhpp.getTipoRedRepositorioId());
                try {
                    tipoHhppRedMgl = tipoHhppRedMglManager.findById(tipoRedId);
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException("El valor digitado en 'TipoRedRhepositorioId' "
                            .concat("no existe en repositorio de direcciones"));
                }
                hhppMgl.setThrId(tipoHhppRedMgl.getThrId());
            }

            if (hhpp.getTipoHhppRepositorioId() == null || hhpp.getTipoHhppRepositorioId().isEmpty()) {
                throw new ApplicationException("El campo  'TipoHhppRepositorioId' es obligatorio");
            } else {
                try {
                    tipoHhppMgl = tipoHhppMglManager.findById(hhpp.getTipoHhppRepositorioId());
                } catch (ApplicationException e) {
                    String msg = "El valor digitado en 'EstadoHhppRepositorioId' "
                            .concat("no existe en repositorio de direcciones");
                    LOGGER.error(msg + e.getMessage());
                    throw new ApplicationException(msg, e);
             }
                hhppMgl.setThhId(tipoHhppMgl.getThhID());
            }

            if (hhpp.getApartamentoRr() == null || hhpp.getApartamentoRr().isEmpty()) {
                throw new ApplicationException("El campo  'ApartamentoRr' es obligatorio");
            } else {
                hhppMgl.setHhpApart(hhpp.getApartamentoRr());
            }

            if (hhpp.getCalleRr() == null || hhpp.getCalleRr().isEmpty()) {
                throw new ApplicationException("El campo  'CalleRr' es obligatorio");
            } else {
                hhppMgl.setHhpCalle(hhpp.getCalleRr());
            }

            if (hhpp.getCodigoPostalRr() == null || hhpp.getCodigoPostalRr().isEmpty()) {
                throw new ApplicationException("El campo  'CodigoPostalRr' es obligatorio");
            } else {
                hhppMgl.setHhpCodigoPostal(hhpp.getCodigoPostalRr());
            }

            if (hhpp.getComunidadRr() == null || hhpp.getComunidadRr().isEmpty()) {
                throw new ApplicationException("El campo  'ComunidadRr' es obligatorio");
            } else {
                hhppMgl.setHhpComunidad(hhpp.getComunidadRr());
            }

            if (hhpp.getDivisionRr() == null || hhpp.getDivisionRr().isEmpty()) {
                throw new ApplicationException("El campo  'DivisionRr' es obligatorio");
            } else {
                hhppMgl.setHhpDivision(hhpp.getDivisionRr());
            }

            if (hhpp.getEstadoUnidadRr() == null || hhpp.getEstadoUnidadRr().isEmpty()) {
                throw new ApplicationException("El campo  'EstadoUnidadRr' es obligatorio");
            } else {
                estUnit = cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, hhpp.getEstadoUnidadRr(), true);
                hhppMgl.setHhpEstadoUnit(estUnit);
            }

            if (hhpp.getFechaAuditRr() == null || hhpp.getFechaAuditRr().isEmpty()) {
                throw new ApplicationException("El campo  'FechaAuditRr' es obligatorio");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    fechaAuditoriaRr = dateFormat.parse(hhpp.getFechaAuditRr());
                } catch (ParseException e) {
                    String msg = "El campo  'FechaAuditRr' debe tener el formato 'yyyy-MM-dd'";
                    LOGGER.error(msg + e.getMessage());
                    throw new ApplicationException(msg, e);
                }
                hhppMgl.setHhpFechaAudit(fechaAuditoriaRr);
            }

            if (hhpp.getHeadEndRr() == null || hhpp.getHeadEndRr().isEmpty()) {
                throw new ApplicationException("El campo  'HeadEndRr' es obligatorio");
            } else {
                hhppMgl.setHhpHeadEnd(hhpp.getHeadEndRr());
            }
            if (hhpp.getIdHhppRr() == null || hhpp.getIdHhppRr().isEmpty()) {
                throw new ApplicationException("El campo  'HeadEndRr' es obligatorio");
            } else {
                if (!CmtUtilidadesCM.isNumeber(hhpp.getIdHhppRr())) {
                    throw new ApplicationException("El valor digitado en 'IdHhppRr' debe ser númerico");
                }
                hhppMgl.setHhpIdrR(hhpp.getIdHhppRr());
            }

            if (hhpp.getPlacaRr() == null || hhpp.getPlacaRr().isEmpty()) {
                throw new ApplicationException("El campo  'PlacaRr' es obligatorio");
            } else {
                hhppMgl.setHhpPlaca(hhpp.getPlacaRr());
            }

            if (hhpp.getTipoAcometidaRr() == null || hhpp.getTipoAcometidaRr().isEmpty()) {
                throw new ApplicationException("El campo  'TipoAcometidaRr' es obligatorio");
            } else {
                hhppMgl.setHhpTipoAcomet(hhpp.getTipoAcometidaRr());
            }

            if (hhpp.getTipoCblAcometidaRr() == null || hhpp.getTipoCblAcometidaRr().isEmpty()) {
                throw new ApplicationException("El campo  'TipoCblAcometidaRr' es obligatorio");
            } else {
                hhppMgl.setHhpTipoCblAcometida(hhpp.getTipoCblAcometidaRr());
            }

            if (hhpp.getTipoUnidadRr() == null || hhpp.getTipoUnidadRr().isEmpty()) {
                throw new ApplicationException("El campo  'TipoUnidadRr' es obligatorio");
            } else {
                hhppMgl.setHhpTipoUnidad(hhpp.getTipoUnidadRr());
            }

            if (hhpp.getUltimaUbicacionNodoRr() == null || hhpp.getUltimaUbicacionNodoRr().isEmpty()) {
                throw new ApplicationException("El campo  'UltimaUbicacionNodoRr' es obligatorio");
            } else {
                hhppMgl.setHhpUltUbicacion(hhpp.getUltimaUbicacionNodoRr());
            }

            if (hhpp.getVendedorRr() == null || hhpp.getVendedorRr().isEmpty()) {
                throw new ApplicationException("El campo  'getVendedorRr' es obligatorio");
            } else {
                hhppMgl.setHhpVendedor(hhpp.getVendedorRr());
            }

            hhppMgl.setMarker("NA");

            hhppMgl.setHhpNotasAdd1(hhpp.getNotasAdd1HhppRr());
            hhppMgl.setHhpNotasAdd2(hhpp.getNotasAdd2HhppRr());
            hhppMgl.setHhpNotasAdd3(hhpp.getNotasAdd3HhppRr());
            hhppMgl.setHhpNotasAdd4(hhpp.getNotasAdd4HhppRr());
            Date toDay = Calendar.getInstance().getTime();
            hhppMgl.setFechaCreacion(toDay);
            hhppMgl.setUsuarioCreacion(hhpp.getUsuario());
            hhppMgl = hhppMglManager.create(hhppMgl);
            return hhppMgl.getHhpId();

        } else {
            if (!CmtUtilidadesCM.isNumeber(hhpp.getHhppRepositorioId())) {
                throw new ApplicationException("El valor 'HhppRepositorioId' debe ser númerico");
            } else {
                hhppId = new BigDecimal(hhpp.getHhppRepositorioId());
                try {
                    hhppMgl = hhppMglManager.findById(hhppId);
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException("El valor digitado en 'HhppRepositorioId'".
                            concat("no existe en repositorio de direcciones"));
                }

            }

            //JDHT
            if (hhpp.getEstadoHhppRepositorioId() != null && !hhpp.getEstadoHhppRepositorioId().isEmpty()) {
                try {
                    cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                            co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADOS_HHPP);

                    EstadoHhppMgl estadoHhppMgl = new EstadoHhppMgl();
                    String estHom;
                    estHom = cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, hhpp.getEstadoHhppRepositorioId(), true);
                    estadoHhppMgl.setEhhID(estHom);
                    hhppMgl.setEhhId(estadoHhppMgl);
                    hhppMgl.setHhpEstadoUnit(estHom);

                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException("El valor digitado en 'EstadoHhppRepositorioId'"
                            .concat(" no existe en repositorio de direcciones"));
                }
            }

            if (hhpp.getDireccionRepositorioId() != null && !hhpp.getDireccionRepositorioId().isEmpty()) {
                if (!(hhpp.getDireccionRepositorioId().substring(0, 1).equalsIgnoreCase("D")
                        || hhpp.getDireccionRepositorioId().substring(0, 1).equalsIgnoreCase("S"))
                        || !(hhpp.getDireccionRepositorioId().length() >= 2)) {
                    throw new ApplicationException("El id debe estar precesido por 'd' o por 's' para identificar direccion ".
                            concat("o subdireccion, debe contener mas de dos caracters"));
                } else {
                    String direccionTemId = hhpp.getDireccionRepositorioId().substring(1);
                    String categoriaDireccion = hhpp.getDireccionRepositorioId().substring(0, 1);
                    if (!CmtUtilidadesCM.isNumeber(direccionTemId)) {
                        throw new ApplicationException("El valor digitado en 'DireccionRepositorioId' debe 'd#####' o 's#####'");
                    } else {

                        if (categoriaDireccion.equalsIgnoreCase("d")) {
                            direccionId = new BigDecimal(direccionTemId);
                            try {
                                direccionMgl = new DireccionMgl();
                                direccionMgl.setDirId(direccionId);
                                direccionMgl = direccionMglManager.findById(direccionMgl);
                            } catch (ApplicationException e) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                                throw new ApplicationException("El valor digitado en 'DireccionRepositorioId'".
                                        concat("no existe en repositorio de direcciones"));
                            }
                            hhppMgl.setDirId(direccionMgl.getDirId());
                        } else {
                            subDireccionId = new BigDecimal(direccionTemId);
                            try {
                                subDireccionMgl = subDireccionMglManager.findById(subDireccionId);
                            } catch (ApplicationException e) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                                throw new ApplicationException("El valor digitado en 'DireccionRepositorioId'".
                                        concat("no existe en repositorio de direcciones"));
                            }
                            hhppMgl.setSdiId(subDireccionMgl.getSdiId());

                            try {
                                direccionMgl = new DireccionMgl();
                                direccionMgl.setDirId(subDireccionMgl.getDirId());
                                direccionMgl = direccionMglManager.findById(direccionMgl);
                            } catch (ApplicationException e) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                LOGGER.error(msg);
                                throw new ApplicationException("El valor digitado en 'DireccionRepositorioId'".
                                        concat("no existe en repositorio de direcciones"));
                            }
                            hhppMgl.setDirId(direccionMgl.getDirId());
                        }
                    }
                }
            }
            if (hhpp.getNodoRrId() != null && !hhpp.getNodoRrId().isEmpty()) {
                if (!nodoMglManager.isNodoCertificado(hhpp.getNodoRrId())) {
                    throw new ApplicationException("El nodo digitado 'NodoRrId' no esta certificado o no existe en RR");
                } else {
                    nodoMgl = nodoMglManager.findByCodigo(hhpp.getNodoRrId());
                    if (nodoMgl == null) {
                        throw new ApplicationException("El valor digitado en 'NodoRrId' no existe en RR y Repositorio");
                    }
                    hhppMgl.setNodId(nodoMgl);
                }
            }

            if (hhpp.getSubEdificioIdRepositorio() != null && !hhpp.getSubEdificioIdRepositorio().isEmpty()) {
                if (!CmtUtilidadesCM.isNumeber(hhpp.getSubEdificioIdRepositorio())) {
                    throw new ApplicationException("El valor digitado en 'SubEdificioIdRepositorio' debe ser númerico");
                } else {
                    cmtSubEdificioMgl = new CmtSubEdificioMgl();
                    subEdificioId = new BigDecimal(hhpp.getSubEdificioIdRepositorio());
                    cmtSubEdificioMgl.setSubEdificioId(subEdificioId);
                    try {
                        cmtSubEdificioMgl = cmtSubEdificioMglManager.findById(cmtSubEdificioMgl);
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                        LOGGER.error(msg);
                        throw new ApplicationException("El valor digitado en 'SubEdificioIdRepositorio' "
                                .concat("no existe en repositorio de direcciones"));
                    }
                    hhppMgl.setHhppSubEdificioObj(cmtSubEdificioMgl);
                }
            }

            if (hhpp.getTipoConexionRepositorioId() != null && !hhpp.getTipoConexionRepositorioId().isEmpty()) {
                if (!CmtUtilidadesCM.isNumeber(hhpp.getTipoConexionRepositorioId())) {
                    throw new ApplicationException("El valor digitado en 'TipoConexionRepositorioId' debe ser númerico");
                } else {
                    tipoConexionId = new BigDecimal(hhpp.getTipoConexionRepositorioId());
                    try {
                        tipoHhppConexionMgl = tipoHhppConexionMglManager.findById(tipoConexionId);
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                        LOGGER.error(msg);
                        throw new ApplicationException("El valor digitado en 'TipoConexionRepositorioId' "
                                .concat("no existe en repositorio de direcciones"));
                    }
                    hhppMgl.setThcId(tipoHhppConexionMgl.getThcId());
                }
            }
            if (hhpp.getTipoRedRepositorioId() != null && !hhpp.getTipoRedRepositorioId().isEmpty()) {
                if (!CmtUtilidadesCM.isNumeber(hhpp.getTipoRedRepositorioId())) {
                    throw new ApplicationException("El valor digitado en 'TipoRedRepositorioId' debe ser númerico");
                }
                tipoRedId = new BigDecimal(hhpp.getTipoRedRepositorioId());
                try {
                    tipoHhppRedMgl = tipoHhppRedMglManager.findById(tipoRedId);
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException("El valor digitado en 'TipoRedRhepositorioId' "
                            .concat("no existe en repositorio de direcciones"));
                }
                hhppMgl.setThrId(tipoHhppRedMgl.getThrId());
            }

            if (hhpp.getTipoHhppRepositorioId() != null && !hhpp.getTipoHhppRepositorioId().isEmpty()) {
                try {
                    tipoHhppMgl = tipoHhppMglManager.findById(hhpp.getTipoHhppRepositorioId());
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException("El valor digitado en 'EstadoHhppRepositorioId' "
                            .concat("no existe en repositorio de direcciones"));
                }
                hhppMgl.setThhId(tipoHhppMgl.getThhID());
            }

            if (hhpp.getApartamentoRr() != null && !hhpp.getApartamentoRr().isEmpty()) {
                hhppMgl.setHhpApart(hhpp.getApartamentoRr());
            }

            if (hhpp.getCalleRr() != null && !hhpp.getCalleRr().isEmpty()) {
                hhppMgl.setHhpCalle(hhpp.getCalleRr());
            }

            if (hhpp.getCodigoPostalRr() != null && !hhpp.getCodigoPostalRr().isEmpty()) {
                hhppMgl.setHhpCodigoPostal(hhpp.getCodigoPostalRr());
            }

            if (hhpp.getComunidadRr() != null && !hhpp.getComunidadRr().isEmpty()) {
                hhppMgl.setHhpComunidad(hhpp.getComunidadRr());
            }

            if (hhpp.getDivisionRr() != null && !hhpp.getDivisionRr().isEmpty()) {
                hhppMgl.setHhpDivision(hhpp.getDivisionRr());
            }

            if (hhpp.getEstadoUnidadRr() != null && !hhpp.getEstadoUnidadRr().isEmpty()) {
                estUnit = cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, hhpp.getEstadoUnidadRr(), true);
                hhppMgl.setHhpEstadoUnit(estUnit);
            }

            if (hhpp.getFechaAuditRr() != null && !hhpp.getFechaAuditRr().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    fechaAuditoriaRr = dateFormat.parse(hhpp.getFechaAuditRr());
                } catch (ParseException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException("El campo  'FechaAuditRr' debe tener el formato 'yyyy-MM-dd'");
                }
                hhppMgl.setHhpFechaAudit(fechaAuditoriaRr);
            }

            if (hhpp.getHeadEndRr() != null && !hhpp.getHeadEndRr().isEmpty()) {
                hhppMgl.setHhpHeadEnd(hhpp.getHeadEndRr());
            }
            if (hhpp.getIdHhppRr() != null && !hhpp.getIdHhppRr().isEmpty()) {
                if (!CmtUtilidadesCM.isNumeber(hhpp.getIdHhppRr())) {
                    throw new ApplicationException("El valor digitado en 'IdHhppRr' debe ser númerico");
                }
                hhppMgl.setHhpIdrR(hhpp.getIdHhppRr());
            }

            if (hhpp.getPlacaRr() != null && !hhpp.getPlacaRr().isEmpty()) {
                hhppMgl.setHhpPlaca(hhpp.getPlacaRr());
            }

            if (hhpp.getTipoAcometidaRr() != null && !hhpp.getTipoAcometidaRr().isEmpty()) {
                hhppMgl.setHhpTipoAcomet(hhpp.getTipoAcometidaRr());
            }

            if (hhpp.getTipoCblAcometidaRr() != null && !hhpp.getTipoCblAcometidaRr().isEmpty()) {
                hhppMgl.setHhpTipoCblAcometida(hhpp.getTipoCblAcometidaRr());
            }

            if (hhpp.getTipoUnidadRr() != null && !hhpp.getTipoUnidadRr().isEmpty()) {
                hhppMgl.setHhpTipoUnidad(hhpp.getTipoUnidadRr());
            }

            if (hhpp.getUltimaUbicacionNodoRr() != null && !hhpp.getUltimaUbicacionNodoRr().isEmpty()) {
                hhppMgl.setHhpUltUbicacion(hhpp.getUltimaUbicacionNodoRr());
            }

            if (hhpp.getVendedorRr() != null && !hhpp.getVendedorRr().isEmpty()) {
                hhppMgl.setHhpVendedor(hhpp.getVendedorRr());
            }

            if (hhpp.getNotasAdd1HhppRr() != null && !hhpp.getNotasAdd1HhppRr().isEmpty()) {
                hhppMgl.setHhpNotasAdd1(hhpp.getNotasAdd1HhppRr());
            }
            if (hhpp.getNotasAdd2HhppRr() != null && !hhpp.getNotasAdd2HhppRr().isEmpty()) {
                hhppMgl.setHhpNotasAdd2(hhpp.getNotasAdd2HhppRr());
            }
            if (hhpp.getNotasAdd3HhppRr() != null && !hhpp.getNotasAdd3HhppRr().isEmpty()) {
                hhppMgl.setHhpNotasAdd3(hhpp.getNotasAdd3HhppRr());
            }
            if (hhpp.getNotasAdd4HhppRr() != null && !hhpp.getNotasAdd4HhppRr().isEmpty()) {
                hhppMgl.setHhpNotasAdd4(hhpp.getNotasAdd4HhppRr());
            }
            Date toDay = Calendar.getInstance().getTime();
            hhppMgl.setFechaEdicion(toDay);
            hhppMgl.setUsuarioEdicion(hhpp.getUsuario());
            hhppMgl = hhppMglManager.update(hhppMgl);
            return hhppMgl.getHhpId();
        }
    }

    public BigDecimal persistHhpp(Hhpp hhpp, String nombreFuncionalidad) throws ApplicationException {
            return createHomePassed(hhpp, nombreFuncionalidad, OPT_PERSIST_HHPP);
    }

    public BigDecimal saveHhppInRepo(Hhpp hhpp, String nombreFuncionalidad) throws ApplicationException {
            return createHomePassed(hhpp, nombreFuncionalidad, OPT_SAVE_IN_REPO);
    }

    /**
     * Método que se encarga de registrar un HHPP en el repositorio de direcciones
     *
     * @param hhpp Objeto HHPP
     * @param nombreFuncionalidad Nombre de la funcionalidad
     * @param option Indica el flujo a seguir para la creación del HHPP
     * @return {@link BigDecimal} id del hhpp
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    private BigDecimal createHomePassed(Hhpp hhpp, String nombreFuncionalidad, Integer option) throws ApplicationException {
        AccessData adb = null;

        try {
            adb = ManageAccess.createAccessData();
            StoredProcedureUtil sp = new StoredProcedureUtil(StoredProcedureNamesConstants.CREATE_HHPP_SP);
            CreateHhppRequestDto procedureRequest = new CreateHhppRequestDto();
            HomePassedDto hhppDto = asignarRequestCrearHhpp(hhpp, option);
            procedureRequest.setHomePassedDto(hhppDto);
            procedureRequest.setOption(option);
            sp.addRequestData(procedureRequest);
            CreateHhppResponseDto response = sp.executeStoredProcedure(CreateHhppResponseDto.class);

            if (response.getCode() != 0) {
                String msgError = "No se pudo registrar el HHPP: " + response.getDescription();
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            if (response.getIdHhpp() != null && hhpp.getMarcas() != null) {
                hhpp.setHhppId(response.getIdHhpp());
                insertMarcasHhpp(hhpp.getMarcas(), hhpp, nombreFuncionalidad, adb);
            }

            return response.getIdHhpp();
        } catch (ApplicationException | ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de almacenar el HHPP. EX000: " + ex.getMessage(), ex);
        } finally {
            DireccionUtil.closeConnection(adb);
        }
    }

    /**
     * Método que se encarga de registrar un HHPP en el repositorio de direcciones
     *
     * @param hhpp Objeto HHPP
     * @param option Indica el flujo a seguir para la creación del HHPP
     * @return {@link CreateHhppRequestDto}
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    private HomePassedDto asignarRequestCrearHhpp(Hhpp hhpp, Integer option) throws ApplicationException {
        Objects.requireNonNull(hhpp, "El objeto HHPP no puede ser nulo para registrarlo en el repositorio");
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();

        try {
            HomePassedDto createHhppRequestDto = new HomePassedDto();
            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADOS_HHPP);
            createHhppRequestDto.setDireccionId(Optional.of(hhpp)
                    .map(Hhpp::getDireccion).map(Direccion::getDirId)
                    .orElse(null));
            createHhppRequestDto.setNodoId(Optional.of(hhpp)
                    .map(Hhpp::getNodo).map(Nodo::getNodId)
                    .orElse(null));
            createHhppRequestDto.setTipoTecnologiaHabId(Optional.of(hhpp)
                    .map(Hhpp::getTipoHhpp).map(TipoHhpp::getThhId)
                    .orElse(StringUtils.SPACE));
            createHhppRequestDto.setSubDireccionId(Optional.of(hhpp)
                    .map(Hhpp::getSubDireccion).map(SubDireccion::getSdiId)
                    .orElse(null));
            createHhppRequestDto.setTipoConexionTecHabiId(Optional.of(hhpp)
                    .map(Hhpp::getTipoConexionHhpp).map(TipoHhppConexion::getThcId)
                    .map(BigDecimal::intValue)
                    .orElse(null));
            createHhppRequestDto.setTipoRedTecHabiId(Optional.of(hhpp)
                    .map(Hhpp::getTipoRedHhpp).map(TipoHhppRed::getThrId)
                    .map(BigDecimal::intValue)
                    .orElse(null));

            if (hhpp.getEstadoHhpp() != null) {
                String estadoHhpp = cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, hhpp.getEstadoHhpp().getEhhId(), true);
                createHhppRequestDto.setEstadoId(estadoHhpp);
            }

            createHhppRequestDto.setTecnologiaHabilitadaIdRr(Optional.of(hhpp).map(Hhpp::getIdRR).orElse(null));
            createHhppRequestDto.setUsuarioCreacion(hhpp.getHhppUsuarioCreacion());
            /* NOTE: hasta esta linea comparten el mismo flujo "persistHhpp" y "saveHhhppInRepo"*/

            if (option == OPT_PERSIST_HHPP) {
                return createHhppRequestDto;
            }

            createHhppRequestDto.setCalle(Optional.of(hhpp).map(Hhpp::getCalleRR).orElse(null));
            createHhppRequestDto.setPlaca(Optional.of(hhpp).map(Hhpp::getUnidadRR).orElse(null));
            createHhppRequestDto.setApart(Optional.of(hhpp).map(Hhpp::getAptoRR).orElse(null));
            createHhppRequestDto.setComunidad(Optional.of(hhpp).map(Hhpp::getComunidadRR).orElse(null));
            createHhppRequestDto.setDivision(Optional.of(hhpp).map(Hhpp::getDivisionRR).orElse(null));

            if (hhpp.getEstado_unit() != null) {
                String estadoUnit = cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, hhpp.getEstado_unit(), true);
                createHhppRequestDto.setEstadoUnit(estadoUnit);
            }

            createHhppRequestDto.setVendedor(Optional.of(hhpp).map(Hhpp::getVendedor).orElse(null));
            createHhppRequestDto.setCodigoPostal(Optional.of(hhpp).map(Hhpp::getCodigo_postal).orElse(null));
            createHhppRequestDto.setTipoAcomet(Optional.of(hhpp).map(Hhpp::getTipo_acomet).orElse(null));
            createHhppRequestDto.setUltUbicacion(Optional.of(hhpp).map(Hhpp::getUlt_ubicacion).orElse(null));
            createHhppRequestDto.setHeadEnd(Optional.of(hhpp).map(Hhpp::getHead_end).orElse(null));
            createHhppRequestDto.setTipo(Optional.of(hhpp).map(Hhpp::getTipo).orElse(null));
            createHhppRequestDto.setEdificio(Optional.of(hhpp).map(Hhpp::getEdificio).orElse(null));
            createHhppRequestDto.setTipoUnidad(Optional.of(hhpp).map(Hhpp::getTipo_unidad).orElse(null));
            createHhppRequestDto.setTipoCblAcometida(Optional.of(hhpp).map(Hhpp::getTipo_cbl_acometida).orElse(null));
            createHhppRequestDto.setFechaAudit(new java.sql.Date(new Date().getTime()));
            createHhppRequestDto.setEstadoRegistro(1);
            String nap = Objects.isNull(hhpp.getAmp()) ? hhpp.getNap() : hhpp.getAmp();
            createHhppRequestDto.setNap(nap);
            return createHhppRequestDto;
        } catch (ApplicationException  e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de asignar el request en la creación del HHPP. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param marcas
     * @param hhpp
     * @param nombreFuncionalidad
     * @param adb
     * @return
     * @throws Exception
     */
    private boolean insertMarcasHhpp(List<Marcas> marcas, Hhpp hhpp, String nombreFuncionalidad, AccessData adb) throws ApplicationException {
        boolean resultado = false;
        for (int i = 0; i < marcas.size(); i++) {
            try {
                Marcas marca = marcas.get(i);
                adb.in("mhh1", hhpp.getHhppId().toString(), marca.getMarId().toString(), hhpp.getHhppUsuarioCreacion());
                if (i == marcas.size()) {
                    resultado = true;
                }
            } catch (ExceptionDB ex) {
                LOGGER.error("Error al insertar las marcas del HHPP, funcionalidad: {}", nombreFuncionalidad);
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException("Error al momento de insertar las marcas del HHPP. EX000: " + ex.getMessage(), ex);
            }
        }
        return resultado;
    }

    /**
     * Consultar un estado HHPP
     *
     * @param nombreEstado
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    public EstadoHhpp queryEstadoHhpp(String nombreEstado) throws ApplicationException {
        EstadoHhpp estadohhpp = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("ehh1", nombreEstado);

            //SELECT NOD_ID, THH_ID, SDI_ID, THC_ID, THR_ID, HHP_BLACKLIST, HHP_ESTADO
            if (obj != null) {
                estadohhpp = new EstadoHhpp();
                String ehhpIdEstado = obj.getString("ESTADO_ID");
                String hhpNombreEstado = obj.getString("NOMBRE");
                estadohhpp.setEhhId(ehhpIdEstado);
                estadohhpp.setEhhNombre(hhpNombreEstado);
            }
            DireccionUtil.closeConnection(adb);
            return estadohhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el estado del HHPP. EX000: " + e.getMessage(), e);
        }
    }

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ne.getMessage();
            LOGGER.error(msg);
            throw new RuntimeException(ne);
        }
    }

    /**
     * Permite validar si una comunidad es viable 3G
     *
     * @param comunityStr comunidad para validar la cobertura 3G
     * @return String S=viable N=no viable;
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String thirdGViability(String comunityStr) throws ApplicationException {
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        List<ParametrosMgl> parametrosMgl
                = parametrosMglManager.findByAcronimo(Constant.ACRONIMO_COMUNIDAD_DTH_LTE);
        if (comunityStr == null || comunityStr.trim().isEmpty()) {
            throw new ApplicationException("El parametro comunityStr es obligatorio");
        }
        //Validamos si la comunidad es una comunidad especial para venta LTE
        if (parametrosMgl != null && !parametrosMgl.isEmpty()
                && parametrosMgl.get(0).getParValor() != null
                && !parametrosMgl.get(0).getParValor().trim().isEmpty()
                && parametrosMgl.get(0).getParValor().contains(comunityStr)) {
            return "S";
        }
        return "N";
    }
}
