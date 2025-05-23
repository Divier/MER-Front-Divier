package co.com.telmex.catastro.data;

import java.io.Serializable;

/**
 * Clase AddressRequestBatch
 * Representa el resultado en batch de la solicitud de direcciones.
 * implementa Serialización
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
public class AddressResultBatch extends AddressRequest implements Serializable {

    private String transaction;
    private String logTraslate;
    private String flagOperacion;
    private String logTransactionBD;
    private AddressService addressService;
    private String addressTraslateGeodatas;


    public AddressResultBatch() {
    }

    /**
     * 
     * @param transaction
     * @param logTraslate
     * @param logTransactionBD
     * @param addressService
     * @param flagOperacion
     * @param addressTraslateGeodatas
     */
    public AddressResultBatch(String transaction,
            String logTraslate,
            String logTransactionBD,
            AddressService addressService,
            String flagOperacion,
            String addressTraslateGeodatas) {
        this.transaction = transaction;
        this.logTraslate = logTraslate;
        this.logTransactionBD = logTransactionBD;
        this.addressService = addressService;
        this.flagOperacion = flagOperacion;
        this.addressTraslateGeodatas = addressTraslateGeodatas;
    }

    /**
     * 
     * @return
     */
    public String getAddressTraslateGeodatas() {
        return addressTraslateGeodatas;
    }

    /**
     * 
     * @param addressTraslateGeodatas
     */
    public void setAddressTraslateGeodatas(String addressTraslateGeodatas) {
        this.addressTraslateGeodatas = addressTraslateGeodatas;
    }

    /**
     * 
     * @return
     */
    public String getFlagoperacion() {
        return flagOperacion;
    }

    /**
     * 
     * @param flagoperacion
     */
    public void setFlagoperacion(String flagoperacion) {
        this.flagOperacion = flagoperacion;
    }

    /**
     * 
     * @return
     */
    public AddressService getAddressResult() {
        return addressService;
    }

    /**
     * 
     * @param addressResult
     */
    public void setAddressResult(AddressService addressResult) {
        this.addressService = addressResult;
    }

    /**
     * 
     * @return
     */
    public String getLogTransactionBD() {
        return logTransactionBD;
    }

    /**
     * 
     * @param logTransactionBD
     */
    public void setLogTransactionBD(String logTransactionBD) {
        this.logTransactionBD = logTransactionBD;
    }

    /**
     * 
     * @return
     */
    public String getLogTraslate() {
        return logTraslate;
    }

    /**
     * 
     * @param logTraslate
     */
    public void setLogTraslate(String logTraslate) {
        this.logTraslate = logTraslate;
    }

    /**
     * 
     * @return
     */
    public String getTransaction() {
        return transaction;
    }

    /**
     * 
     * @param transaction
     */
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
