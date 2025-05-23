package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.utilws.ResponseMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase GeoreferenciaDelegate
 *
 * @author 	Jose Luis Caicedo
 * @version	1.0
 */
public class GeoreferenciaDelegate {
    
    private static final Logger LOGGER = LogManager.getLogger(GeoreferenciaDelegate.class);

    private static String ADDRESSEJB = "addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote";

    /**
     * 
     * @return
     * @throws javax.naming.NamingException
     */
    public static AddressEJBRemote getAddressEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        AddressEJBRemote obj = (AddressEJBRemote) ctx.lookup(ADDRESSEJB);
        return obj;
    }

    /**
     * 
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static AddressService queryAddress(AddressRequest addressRequest) throws ApplicationException {
        AddressService addressService = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                addressService = obj.queryAddress(addressRequest);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la dirección. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar la dirección. EX000 " + ex.getMessage(), ex);
        }
        return addressService;
    }

    /**
     * 
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static AddressGeodata queryAddressGeodata(AddressRequest addressRequest) throws ApplicationException {
        AddressGeodata addressGeodata = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                addressGeodata = obj.queryAddressGeodata(addressRequest);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la dirección geodata. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar la dirección geodata. EX000 " + ex.getMessage(), ex);
        }
        return addressGeodata;
    }

    /**
     * 
     * @param addressesRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<AddressService> queryAddressBatch(List<AddressRequest> addressesRequest) throws ApplicationException {
        List<AddressService> addressesService = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                addressesService = obj.queryAddressBatch(addressesRequest);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar las direcciones EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar las direcciones EX000 " + ex.getMessage(), ex);
        }
        return addressesService;
    }

    /**
     * 
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static AddressService queryAddressEnrich(AddressRequest addressRequest) throws ApplicationException {
        AddressService addressService = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                addressService = obj.queryAddressEnrich(addressRequest);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la direcciones . EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar la direcciones . EX000 " + ex.getMessage(), ex);
        }
        return addressService;
    }

    /**
     * 
     * @param listAddressRequest
     * @param nivel
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<AddressGeodata> queryListAddressGeodata(List<AddressRequest> listAddressRequest, String nivel) throws ApplicationException {
        List<AddressGeodata> listaAddressgeodata = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                listaAddressgeodata = obj.queryListAddressGeodata(listAddressRequest);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error sl consultar la lista de direcciones geodata. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error sl consultar la lista de direcciones geodata. EX000 " + ex.getMessage(), ex);
        }
        return listaAddressgeodata;
    }

    /**
     * 
     * @param addressRequest
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static ResponseMessage createAddress(AddressRequest addressRequest, String user, String nombreFuncionalidad, String validate) throws ApplicationException {
        ResponseMessage response = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                response = obj.createAddress(addressRequest, user, nombreFuncionalidad, validate);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error eal crear direcciones. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error eal crear direcciones. EX000 " + ex.getMessage(), ex);
        }
        return response;
    }

    /**
     * 
     * @param nombreLocalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal queryGeograficoLocalidadByNombre(String nombreLocalidad) throws ApplicationException {
        BigDecimal idLocalidad = BigDecimal.ZERO;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                idLocalidad = obj.queryGeograficoLocalidadByNombre(nombreLocalidad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al cosultar localidad geografica por nombre . EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al cosultar localidad geografica por nombre . EX000 " + ex.getMessage(), ex);
        }
        return idLocalidad;
    }

    /**
     * 
     * @param idLocalidad
     * @param barrio
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal queryGeograficoBarrioByIDLocalidad(String idLocalidad, String barrio) throws ApplicationException {
        BigDecimal idBarrio = BigDecimal.ZERO;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                idBarrio = obj.queryGeograficoBarrioByIDLocalidad(idLocalidad, barrio);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar barrio geografico por id de localidad. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar barrio geografico por id de localidad. EX000 " + ex.getMessage(), ex);
        }
        return idBarrio;
    }

    /**
     * 
     * @param idBarrio
     * @param manzana
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal queryGeograficoMzaByIdBarrio(String idBarrio, String manzana) throws ApplicationException {
        BigDecimal idMza = BigDecimal.ZERO;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                idMza = obj.queryGeograficoMzaByIdBarrio(idBarrio, manzana);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar el geográfico de manzana. EX000: " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar el geográfico de manzana. EX000: " + ex.getMessage(), ex);
        }
        return idMza;
    }

    /**
     * 
     * @param nombreFuncionalidad
     * @param geografico
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal insertGeograficoIntorepository(String nombreFuncionalidad, Geografico geografico) throws ApplicationException {
        BigDecimal idGeo = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                idGeo = obj.insertGeograficoIntorepository(nombreFuncionalidad, geografico);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al agregar geografico. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al agregar geografico. EX000 " + ex.getMessage(), ex);
        }
        return idGeo;
    }
}
