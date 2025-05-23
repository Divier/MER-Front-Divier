package co.com.telmex.catastro.services.georeferencia;

import co.com.claro.mgl.dtos.AddressGeoDirDataDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressServiceBatchXml;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.data.Ubicacion;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase AddressEJBRemote
 *
 * @author Jose Luis Caicedo
 * @version	1.0
 */
@Remote
public interface AddressEJBRemote {

    /**
     *
     * @param codDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SubDireccion querySubAddressOnRepoByCod(String direccionTraducida, BigDecimal centroPobladoId) throws ApplicationException;

    /**
     *
     * @param codDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Direccion queryAddressOnRepository(String codDireccion, BigDecimal centroPobladoId) throws ApplicationException;

    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public AddressService queryAddress(AddressRequest addressRequest) throws ApplicationException;

    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public AddressGeodata queryAddressGeodata(AddressRequest addressRequest) throws ApplicationException;

    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public AddressService queryAddressEnrich(AddressRequest addressRequest) throws ApplicationException;

    /**
     *
     * @param addressRequest
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public ResponseMessage createAddress(AddressRequest addressRequest, String user, String nombreFuncionalidad, String validate) throws ApplicationException;

        /**
     *
     * @param addressRequest
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @param dirNueva
     * @return
     * @throws com.jlcg.db.exept.ExceptionDB
     */
    public ResponseMessage createAddress(AddressRequest addressRequest, 
            String user, String nombreFuncionalidad, String validate, DrDireccion dirNueva ) throws ExceptionDB;
    
            /**
     *
     * @param geoDataDireccionPrincipal
     * @param geoDataSubDireccion
     * @param addressRequest
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @param dirNueva
     * @return
     * @throws com.jlcg.db.exept.ExceptionDB
     * @author Juan David Hernandez
     */
    public ResponseMessage createAddressFichas(AddressGeodata geoDataDireccionPrincipal, AddressGeodata geoDataSubDireccion, 
            AddressRequest addressRequest, String user, String nombreFuncionalidad, String validate, DrDireccion dirNueva ) throws ExceptionDB;
    
    /**
     *
     * @param idaddress
     * @param user
     * @param applicationName
     * @param updateToNewAddress
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public ResponseMessage updateAddress(String idaddress, String user, String applicationName, String updateToNewAddress) throws ApplicationException;

    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<AddressGeodata> queryListAddressGeodata(List<AddressRequest> addressRequest) throws ApplicationException;
    
    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<AddressGeoDirDataDto> queryListAddressGeodataXml(List<AddressRequest> addressRequest) throws ApplicationException;

    /**
     *
     * @param addressesRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<AddressService> queryAddressBatch(List<AddressRequest> addressesRequest) throws ApplicationException;

    /**
     * @param addressGeo
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ApplicationException
     */
    public AddressServiceBatchXml createAddressServiceBatch(AddressGeoDirDataDto addressGeo, AddressRequest addressRequest) throws ApplicationException;
    
    /**
     *
     * @param addressesRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<AddressServiceBatchXml> queryAddressBatchPreficha(List<AddressRequest> addressesRequest) throws ApplicationException;
    /**
     *
     * @param nombreLocalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal queryGeograficoLocalidadByNombre(String nombreLocalidad) throws ApplicationException;

    /**
     *
     * @param idLocalidad
     * @param barrio
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal queryGeograficoBarrioByIDLocalidad(String idLocalidad, String barrio) throws ApplicationException;

    /**
     *
     * @param idBarrio
     * @param manzana
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal queryGeograficoMzaByIdBarrio(String idBarrio, String manzana) throws ApplicationException;

    /**
     *
     * @param nombreFuncionalidad
     * @param geografico
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal insertGeograficoIntorepository(String nombreFuncionalidad, Geografico geografico) throws ApplicationException;

    /**
     *
     * @param addressRequest
     * @param addressResult
     * @param addressServices
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws java.lang.Exception
     */
    public java.util.List<co.com.telmex.catastro.data.AddressResultBatch> queryAddressResultBatch(java.util.List<co.com.telmex.catastro.data.AddressRequest> addressRequest, java.util.List<co.com.telmex.catastro.data.AddressResultBatch> addressResult, java.util.List<co.com.telmex.catastro.data.AddressService> addressServices, String user) throws ApplicationException;

    /**
     *
     * @param CodDivipola
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws com.jlcg.db.exept.ExceptionDB
     */
    public java.lang.String queryMultiorigenByDivipola(java.lang.String CodDivipola) throws ApplicationException;

    /**
     *
     * @param latitud
     * @param longitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws com.jlcg.db.exept.ExceptionDB
     */
    public BigDecimal validarCxCyOnRepository(String latitud, String longitud) throws ApplicationException;

    public BigDecimal insertUbicacion(String nombreFuncionalidad, Ubicacion ubicacion) throws ApplicationException;

    public Ubicacion queryUbicacionById(BigDecimal id) throws ApplicationException;

    public BigDecimal insertAddressOnRepository(String nombreFuncionalidad, Direccion direccion) throws ApplicationException;

    public BigDecimal insertSubAddressOnRepository(String nombreFuncionalidad, SubDireccion subDireccion) throws ApplicationException;

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
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception.
     */
    boolean updateAddresses(String idAdresses, String user, 
            String applicationName, String updateToNewAddress) throws ApplicationException;

    /**
     * Permite Actualizar una direccion Antigua a la Nueva.Realiza la
 actualizacion de una direccion antigua a la nueva junto con todas sus
 subdirecciones.
     *
     * @author Johnnatan Ortiz
     * @param iDAddresses id de la direccion, el primer caracter indica si se
     * trata de una direccion o una subdireccion.
     * @param user usuario que esta ejecutando la actualizacion.
     * @param nombreFuncionalidad aplicacion desde la cual se ejecuta la
     * actualizacion.
     * @param newAddresses nueva direccion a la cual se actualizara la antigua
     * direccion.
     * @return si el proceso se realizo correctamente.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception.
     */
    boolean updateAddressesAndSubAddress(String idAdresses,
            String user, String applicationName,
            AddressRequest newAddresses) throws ApplicationException;
}
