package co.com.telmex.catastro.utilws;

import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;

/**
 * Clase ResponseMessage
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
public class ResponseMessage {

    /**
     * 
     */
    public static String TYPE_SUCCESSFUL = "OK";
    /**
     * 
     */
    public static String TYPE_ERROR = "ERROR";
    /*** tipo de operacion     */
    public static String MESSAGE_SUCCESSFUL = "La operación se realizó con exito";
    /**
     * 
     */
    public static String MESSAGE_ERROR = "Error: La operación no se pudo realizar.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_GUARDANDO_LOCALIDAD = "Ocurrió un error al tratar de guardar la localidad.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_GUARDANDO_BARRIO = "Ocurrió un error al tratar de guardar el barrio.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_GUARDANDO_MANZANA = "Ocurrió un error al tratar de guardar la manzana.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_GUARDANDO_DIR_ALTERNA = "Ocurrió un error al tratar de guardar la dirección alterna.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_SIN_DIR_NUEVA = "Esta dirección no tiene una dirección más nueva a la cual actualizar.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_DIR_NO_EXISTE_EN_GEOREFERENCIADOR = "la dirección no existe en Georreferenciador";
    /**
     * 
     */
    public static String MESSAGE_ERROR_DIR_NO_EXISTE_EN_REPOSITORIO = "La dirección no existe en el repositorio";
    /**
     * 
     */
    public static String MESSAGE_ERROR_CONSULTA_DIR = "Ocurrió un error al consultar la dirección";
    /**
     * 
     */
    public static String MESSAGE_ERROR_DIR_YA_EXISTE = "La dirección ya existe y no se puede guardar.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_NUEVA = "La dirección ya existe en el repositorio con la dirección Nueva";
    /**
     * 
     */
    public static String MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA = "La dirección ya existe en el repositorio con la dirección Antigua, se debe actualizar a la nueva";
   
    public static String MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_NUEVA_FACT = "La dirección ingresada es ANTIGUA y ya existe en el repositorio con la dirección NUEVA";
    /**
     * 
     */
    public static String MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA_FACT = "La dirección ingresada es NUEVA y ya existe en el repositorio con la dirección ANTIGUA.";
    /**
    /**
     * 
     */
    public static String MESSAGE_ERROR_DIR_INVALIDA = "La dirección consultada presenta inconsistencias de data, por favor validarlos.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_TOKEN_INVALIDO = "Token no válido.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_USUARIO_OBLIGATORIO = "Se requiere usuario.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_APPLICATION_NAME_OBLIGATORIO = "Se requiere nombre de la aplicación.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_BARRIO_OBLIGATORIO = "La ciudad es multiorigen, se requiere barrio.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_CIUDAD_NO_EXISTE = "La ciudad ingresada no existe en el repositorio.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_FLAG_UPDATE_TO_NEW_INVALID = "Valor inválido para el flag de actualización.";
    /**
     * 
     */
    public static String MESSAGE_ERROR_FUERA_SERVICIO = "ERROR: Geodata fuera de servicio";
    /**
     * 
     */
    public static String MESSAGE_CAMPOS_OBLIGATORIOS = "Faltan datos se requiere dirección, ciudad y departamento";
    /**
     * 
     */
    public static String MESSAGE_ADDRESS_OBLIGATORIOS = "Faltan datos se requiere el id de la dirección";
    /**
     * Variable que indica el contenido del mensaje
     */
    private String messageText;
    /**
     * Variable que indica el tipo de mensaje
     */
    private String messageType;
    /**
     * Id de la direccion creada
     */
    private String idaddress;
    /**
     * Direccion estandarizada
     */
    private String address;
    /**
     * Varible que indica confibilidad de la respuesta
     */
    private String qualifiers;
    
    /**
     * 
     **/
    private CmtDireccionDetalladaMgl nuevaDireccionDetallada;

    /**
     * Variable que indica la creacion exitosa de la direccion
     **/
    private boolean validacionExitosa;

    
    public boolean isValidacionExitosa() {
        return validacionExitosa;
    }

    public void setValidacionExitosa(boolean validacionExitosa) {
        this.validacionExitosa = validacionExitosa;
    }
    
    public boolean direccionAntiguaFactibilidad;

    public boolean isDireccionAntiguaFactibilidad() {
        return direccionAntiguaFactibilidad;
    }

    public void setDireccionAntiguaFactibilidad(boolean direccionAntiguaFactibilidad) {
        this.direccionAntiguaFactibilidad = direccionAntiguaFactibilidad;
    }
    
    

    

    
            
    /**
     * 
     * @return
     */
    public String getMessageText() {
        return messageText;
    }
    
    /**
     * 
     * @param messageText
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * 
     * @return
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * 
     * @param messageType
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * 
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     */
    public String getIdaddress() {
        return idaddress;
    }

    /**
     * 
     * @param idaddress
     */
    public void setIdaddress(String idaddress) {
        this.idaddress = idaddress;
    }

    /**
     * 
     * @return
     */
    public String getQualifiers() {
        return qualifiers;
    }

    /**
     * 
     * @param qualifiers
     */
    public void setQualifiers(String qualifiers) {
        this.qualifiers = qualifiers;
    }

    public CmtDireccionDetalladaMgl getNuevaDireccionDetallada() {
        return nuevaDireccionDetallada;
    }

    public void setNuevaDireccionDetallada(CmtDireccionDetalladaMgl nuevaDireccionDetallada) {
        this.nuevaDireccionDetallada = nuevaDireccionDetallada;
    }
    
}
