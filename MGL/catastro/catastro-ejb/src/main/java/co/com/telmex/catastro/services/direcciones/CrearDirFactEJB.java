package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressFactResultBatch;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.services.util.CrearDirFactBatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase CrearDirFactEJB Representa La información para la creación de
 * direcciones de facturación implementa CrearDirFactEJBRemote
 *
 * @author Camilo E. Gaviria
 * @version	1.0
 */
@Stateless(name = "CrearDirFactEJB", mappedName = "CrearDirFactEJB", description = "CrearDireccionesFactacturacion")
@Remote({CrearDirFactEJBRemote.class})
public class CrearDirFactEJB implements CrearDirFactEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(CrearDirFactEJB.class);
    
    public boolean validaTraduccion;
    public boolean validaTransaccionBD;
    public StringBuffer archivoPlano;
    public CrearDirFactBatch CrearDirBatch;
    public byte[] archivoCsv;
    public AddressEJB addressEJBValidator = null;
    public ArrayList<AddressRequest> addressRequest;
    public ArrayList<AddressService> addressServices;
    public ArrayList<AddressFactResultBatch> addressResult;
    public ArrayList<AddressGeodata> addressGeodata;
    public static final String ERROR_CARGA_ARCHIVO_PLANO = "Error al cargar la información del archivo en el sistema, verifique la estructura.";
    public static final String ERROR_TRADUCCION_DIRECCION = "Error al traducir direcciones, Servicio Web no disponible. Intente más tarde.";
    public static final String ERROR_LOG_TRADUCCION_DIRECCION = "Error, la información estandarizada no pudo ser validada en Base de Datos.";
    public static final String ERROR_CARGA_TRADUCCION = "Error, la información a estandarizar no se cargó en el sistema.";
    public static final String ERROR_CREAR_ARCHIVO = "Error, no se pudo crear el archivo.";
    public static final String ERROR_GENERAR_LOG = "Error, no se puede leer el log de transacción.";
    public static final String ERROR_VALIDAR_DIR_EN_BD = "Error, la base de datos no se encuentra disponible para realizar el proceso.";
    public static final String DIR_YA_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO = "Ya existe una versión de esta dirección en el repositorio, por lo tanto no se puede guardar.";
    public static final String DIR_NO_EXISTE_UNA_VERSION_EN_EL_REPOSITORIO = "No existe una versión de esta dirección en el repositorio, por lo tanto no se puede Actualizar.";
    private String messageOk = "La operación fue correcta.";
    private String messageError = "Fallo en la operación";
    private String flagTransaction = "0";

    /**
     *
     */
    public CrearDirFactEJB() {
        CrearDirBatch = new CrearDirFactBatch();
        addressResult = new ArrayList<AddressFactResultBatch>();
        addressRequest = new ArrayList<AddressRequest>();

    }

    /**
     *
     */
    public void inicialize() {
        addressServices = null;
        addressGeodata = null;
        archivoPlano = null;
        archivoCsv = null;
        validaTraduccion = false;
        CrearDirBatch = new CrearDirFactBatch();
    }

    /**
     *
     * @param fileFlat
     * @return
     */
    private ArrayList<AddressRequest> loadFile(StringBuffer fileFlat) {
        try {
            addressRequest = CrearDirBatch.cargaArchivo(fileFlat);
            addressResult = CrearDirBatch.getFileAddressResultLog();
            return addressRequest;
        } catch (IOException ex) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
            return null;
        }
    }

    /**
     *
     * @param fileFlat
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public byte[] processFileAddress(StringBuffer fileFlat, String user) throws UnsupportedEncodingException, ApplicationException {
        try {
            inicialize();
            archivoPlano = fileFlat;
            addressRequest = loadFile(fileFlat);
        } catch (Exception e1) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e1.getMessage();
            LOGGER.error(msg);
            messageError = ERROR_CARGA_ARCHIVO_PLANO;//error al traducir direcciones Servicio Web no disponible
            archivoCsv = fileFlat.toString().getBytes("utf-8");
            setFlagTransaction("1001");
            return archivoCsv;
        }
        if (addressRequest != null) {
            try {
                addressServices = queryAddressBatchTraslate(addressRequest);
                flagTransaction = "2";
            } catch (Exception e2) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e2.getMessage();
                LOGGER.error(msg);
                archivoCsv = fileFlat.toString().getBytes("utf-8");
                flagTransaction = "1002";
                messageError = ERROR_CARGA_TRADUCCION;
                return archivoCsv;
            }
        }

        if ("2".equals(flagTransaction)) {
            if (traslateComplete(addressServices) == true) {
                try {
                    addressResult = (ArrayList<AddressFactResultBatch>) addressEJBValidator.queryAddressFactResultBatch(addressRequest,
                            addressResult,
                            addressServices,
                            user);
                    archivoCsv = createFileValidateCSV(addressResult);
                    return archivoCsv;
                } catch (UnsupportedEncodingException e3) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e3.getMessage();
                    LOGGER.error(msg);
                    addressResult = fillArrayAddresResult(addressServices);
                    archivoCsv = createFileValidateCSV(addressResult);
                    return archivoCsv;
                }
            } else if (traslateComplete(addressServices) == false) {
                try {
                    addressResult = (ArrayList<AddressFactResultBatch>) addressEJBValidator.queryAddressFactResultBatch(addressRequest,
                            addressResult,
                            addressServices,
                            user);
                    archivoCsv = createFileValidateCSV(addressResult);
                    return archivoCsv;
                } catch (UnsupportedEncodingException e4) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e4.getMessage();
                    LOGGER.error(msg);
                    messageError = ERROR_VALIDAR_DIR_EN_BD;//error al traducir direcciones Servicio Web no disponible
                    flagTransaction = "1004";
                    archivoCsv = createFileValidateCSV(addressResult);
                    return archivoCsv;
                }
            }
        }
        messageError = ERROR_CARGA_ARCHIVO_PLANO;//error al traducir direcciones Servicio Web no disponible
        archivoCsv = fileFlat.toString().getBytes("utf-8");
        flagTransaction = "1001";
        return archivoCsv;
    }

    /**
     *
     * @param addressRequest
     * @return
     * @throws Exception
     */
    private ArrayList<AddressService> queryAddressBatchTraslate(ArrayList<AddressRequest> addressRequest) throws ApplicationException {
        try {
            addressEJBValidator = new AddressEJB();
            try {
                addressServices = (ArrayList<AddressService>) addressEJBValidator.queryAddressBatch(addressRequest);
                return addressServices;
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                addressServices = null;
                return addressServices;
            }
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consultar la address batch translate. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param addressService
     * @return
     * @throws Exception
     */
    private boolean traslateComplete(ArrayList<AddressService> addressService) throws ApplicationException {
        validaTraduccion = true;
        return validaTraduccion;
    }

    /**
     *
     * @param addresservice
     * @return
     * @throws Exception
     */
    private ArrayList<AddressFactResultBatch> fillArrayAddresResult(ArrayList<AddressService> addresservice) throws ApplicationException {
        addressResult = CrearDirBatch.getFileAddressResultLog();
        int topeArchivo = addressResult.size();
        int topeAdrServ = addresservice.size();
        int i = 0;
        if (addressResult != null && addressServices != null) {
            while (i < topeArchivo && i < topeAdrServ) {
                if (addresservice.get(i).getAddress() == null || "".equals(addresservice.get(i).getAddress()) || (addresservice.get(i).getAddress()).equalsIgnoreCase("null")) {
                    addressServices.get(i).setAddress("null");
                    if (addresservice.get(i).getRecommendations() == null || "".equals(addresservice.get(i).getRecommendations())) {
                        addressServices.get(i).setRecommendations("La dirección, el barrio o la ciudad son inconsistentes, información Incompleta.");
                        addressServices.get(i).setQualifiers("0");
                        addressResult.get(i).setLogTraslate(addressResult.get(i).getLogTraslate() + "Dirección intraducible.");
                    } else {
                        addressServices.get(i).setQualifiers("0");
                        addressResult.get(i).setLogTraslate("Dirección intraducible.");
                    }
                    addressResult.get(i).setFlagoperacion("Operación no Ejecutada");
                    addressResult.get(i).setLogTransactionBD("Rechazada");
                } else {
                    addressResult.get(i).setAddressTraslateGeodata(addresservice.get(i).getAddress());
                    if (addressServices.get(i).getRecommendations() == null || "".equals(addresservice.get(i).getRecommendations())) {
                        addressResult.get(i).setLogTraslate("Estandarizada");
                        addresservice.get(i).setRecommendations("Traducción exitosa");
                    } else {
                        addressResult.get(i).setLogTraslate("Estandarizada");
                    }
                    addressResult.get(i).setFlagoperacion("Operación no Ejecutada");
                    addressResult.get(i).setLogTransactionBD("Rechazada");
                }
                try {
                    addressResult.get(i).setAddressResult(addresservice.get(i));
                } catch (Exception em) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + em.getMessage();
                    LOGGER.error(msg);
                    addressResult.get(i).setAddressResult(null);
                }
                i++;
            }
        }
        return addressResult;
    }

    /**
     *
     * @param addressResult
     * @return
     * @throws UnsupportedEncodingException
     */
    private byte[] createFileValidateCSV(ArrayList<AddressFactResultBatch> addressResult) throws UnsupportedEncodingException {
        archivoCsv = CrearDirBatch.generarArchivo(addressResult);
        if (archivoCsv != null) {
            return archivoCsv;
        } else {
            messageError = ERROR_CREAR_ARCHIVO;
            archivoCsv = archivoPlano.toString().getBytes("utf-8");
            return archivoCsv;
        }
    }

    /**
     *
     * @return
     */
    public String getFlagTransaction() {
        return flagTransaction;
    }

    /**
     *
     * @param flagTransaction
     */
    public void setFlagTransaction(String flagTransaction) {
        this.flagTransaction = flagTransaction;
    }

    /**
     *
     * @return
     */
    public String getMessageError() {
        return messageError;
    }

    /**
     *
     * @param messageError
     */
    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    /**
     *
     * @return
     */
    public String getMessageOk() {
        return messageOk;
    }

    /**
     *
     * @param messageOk
     */
    public void setMessageOk(String messageOk) {
        this.messageOk = messageOk;
    }
}
