package co.com.telmex.catastro.data;

import java.io.Serializable;

/**
 * Define la clase resultado para las direcciones de facturación
 * Extiende de AddresRequest e implementa Serialización
 *
 * @author 	camilo.gaviria.ext
 * @version	1.0
 */
public class AddressFactResultBatch extends AddressRequest implements Serializable {

    private String cuenta;
    private String nombre;
    private String No_Unidad;
    private String Calle_Carrera;
    private String Apto;
    private String Complemento;
    private AddressService addressResult;
    private String AddressTraslateGeodata;
    private String Mensaje;
    private String transaction;
    private String logTraslate;
    private String flagOperacion;
    private String logTransactionBD;
    private String CodDir;

    /** Constructor de la clase. */
    public AddressFactResultBatch() {
    }

    /**
     * Constructor de la clase con parámetros.
     *
     * @author                                     camilo.gaviria.ext
     * @version                                    1.0
     * @param cuenta 
     * @param nombre 
     * @param addressService 	 Objeto de clase AddressServices.
     */
    public AddressFactResultBatch(String cuenta,
            String nombre,
            AddressService addressService) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.addressResult = addressService;
    }

    /** Retorna el Flag de la operación.
     * @return Cadena con el Flag de operación.
     */
    public String getFlagoperacion() {
        return flagOperacion;
    }

    /**
     * Establece el Flag de la operación
     * @param flagoperacion  Valor para el flag de la operación.
     */
    public void setFlagoperacion(String flagoperacion) {
        this.flagOperacion = flagoperacion;
    }

    /** Retorna el mensaje obtenido en el proceso.
     * @return Cadena con el mensaje obtenido en el proceso.
     */
    public String getMensaje() {
        return Mensaje;
    }

    /**
     * Establece el mensaje de acuerdo al resultado del proceso
     * @param mensaje Mensaje retornado por el proceso ejecutado.
     */
    public void setMensaje(String mensaje) {
        this.Mensaje = mensaje;
    }

    /** Retorna el Código de la dirección
     * @return Cadena con el código de la dirección.
     */
    public String getCodDir() {
        return CodDir;
    }

    /**
     * Establece el código de la dirección.
     * @param CodDir Cadena de texto con el código de la dirección, obtenido en el proceso.
     */
    public void setCodDir(String CodDir) {
        this.CodDir = CodDir;
    }

    /** Retorna el número de la cuenta del usuario
     * @return Cadena con el numero de la cuenta del usuario.
     */
    public String getcuenta() {
        return cuenta;
    }

    /** 
     * Establece el numero de la cuenta del usuario.
     * @param cuenta Cadena con el número de cuenta del usuario
     */
    public void setcuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /** Retorna el nombre del cliente
     * @return Cadena con el nombre del cliente.
     */
    public String getnombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente
     * @param nombre Cadena con el nombre del cliente.
     */
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    /** Retorna el AddressService Resultado del proceso de consulta
     * @return AddressService con el resultado del proceso de consulta.
     */
    public AddressService getAddressResult() {
        return addressResult;
    }

    /**
     * Establece el valor del AddressService Resultado del proceso 
     * @param addressResult AddressService obtenido en el proceso de consulta.
     */
    public void setAddressResult(AddressService addressResult) {
        this.addressResult = addressResult;
    }

    /** Retorna el valor de la dirección traducida por Geodata.
     * @return Cadena con la dirección traducida.
     */
    public String getAddressTraslateGeodata() {
        return AddressTraslateGeodata;
    }

    /**
     * Establece el valor de la dirección traducida de acuerdo con el resultado del Geodata. 
     * @param AddressTraslateGeodata Cadena con el valor de la dirección traducida.
     */
    public void setAddressTraslateGeodata(String AddressTraslateGeodata) {
        this.AddressTraslateGeodata = AddressTraslateGeodata;
    }

    /** Retorna el valor obtenido por la transacción en la base de datos.
     * @return Cadena con el resultado de la transacción en la DB.
     */
    public String getLogTransactionBD() {
        return logTransactionBD;
    }

    /**
     * Establece el valor de la transacción de acuerdo al resultado obtenido por el procesos en base de datos.
     * @param logTransactionBD Cadena con el resultado de la transacción.
     */
    public void setLogTransactionBD(String logTransactionBD) {
        this.logTransactionBD = logTransactionBD;
    }

    /** Retorna el valor obtenido al ejecutar la traducción de la dirección.
     * @return Cadena con el valor obtenido al ejecutar la traducción de la dirección.
     */
    public String getLogTraslate() {
        return logTraslate;
    }

    /**
     * Establece el valor de log de traducción de la dirección. 
     * @param logTraslate Cadena con el resultado de la traducción de la dirección.
     */
    public void setLogTraslate(String logTraslate) {
        this.logTraslate = logTraslate;
    }

    /** Obtiene la transacción a realizar.
     * @return Cadena con la transacción a realizar.
     */
    public String getTransaction() {
        return transaction;
    }

    /** 
     * Establece la transacción a realizar. 
     * @param transaction Cadena con el valor de la transacción a realizar en el proceso.
     */
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    /** Obtiene el numero de la unidad. (corresponde a la placa de la unidad).
     * @return Cadena con el numero de la unidad.
     */
    public String getNo_Unidad() {
        return No_Unidad;
    }

    /**
     * Establece el numero de la unidad, de acuerdo con el valor leído del archivo de cargue.
     * @param No_Unidad Cadena con el numero correspondiente al numero de la unidad.
     */
    public void setNo_Unidad(String No_Unidad) {
        this.No_Unidad = No_Unidad;
    }

    /** Obtiene el valor de la calle o carrera. (Numero de placa principal)
     * @return Cadena con el valor de la calle o carrera de la placa.
     */
    public String getCalle_Carrera() {
        return Calle_Carrera;
    }

    /**
     * Establece el valor de la calle o carrera de acuerdo con lo leído en el archivo de cargue.
     * @param Calle_Carrera Cadena con el texto descriptor de placa principal.
     */
    public void setCalle_Carrera(String Calle_Carrera) {
        this.Calle_Carrera = Calle_Carrera;
    }

    /** Obtiene el numero del apartamento.
     * @return Cadena con el Numero del apartamento.
     */
    public String getApto() {
        return Apto;
    }

    /**
     * Establece el numero del apartamento de acuerdo a lo leído en el archivo de cargue.
     * @param Apto Cadena con la información del apartamento.
     */
    public void setApto(String Apto) {
        this.Apto = Apto;
    }

    /** Obtiene el complemento de la unidad predial.
     * @return Cadena con el complemento de la dirección.
     */
    public String getComplemento() {
        return Complemento;
    }

    /**
     * Establece el valor del complemento de acuerdo a lo leído en el archivo de cargue.
     * @param Complemento Cadena con la información del complemento de la unidad predial.
     */
    public void setComplemento(String Complemento) {
        this.Complemento = Complemento;
    }
}
