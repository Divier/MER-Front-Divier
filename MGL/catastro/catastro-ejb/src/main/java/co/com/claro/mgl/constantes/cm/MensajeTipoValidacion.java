package co.com.claro.mgl.constantes.cm;

/**
 * Manejar las constantes.
 * Maneja las constantes de la funcionalidad de mensajes de aplicación.
 * 
 * @author Ricardo Cortés Rodríguez
 * @version 1.0 revision 10/05/2017
 */
public enum MensajeTipoValidacion {
    /**
     * Id de validación.
     */
    ID_VALIDACIONES("141"),
    
    /**
     * Id de proyecto.
     */
    ID_PROYECTOS("140"),
    
    /**
     * Mensaje de error de los registros duplicados en el sistema.
     */
    ERROR_DUPLICIDAD("El registro ya se encuentra en el sistema."),
    

    
    /**
     * Mensaje exitoso cuando un registro fue almacenado en el sistema.
     */
    EXITOSO("El registro fue almacenado en el sistema con id "),
    
    /**
     * Mensaje exitoso cuando una serie de mensajes fueron almacenados en el 
     * sistema.
     */
    EXITOSOS("Se han agregado al sistema los mensajes exitosamente."),
    
    /**
     * Mensaje cuando un registro no fue almacenado en el sistema por validación 
     * o excepción dada.
     */
    FALLIDO("No fue almacenado en el sistema el registro con id "),
    
    /**
     * Mensaje exitoso de un registro actualizado exitosamente.
     */
    ACTUALIZA_OK("El registro fue actualizado en el sistema con id "),
    
    /**
     * Mensaje de un registro no actualizado por incumplimiento de validaciones 
     * o falla.
     */
    ACTUALIZA_FALLIDA("No se pudo actualizar en el sistema el registro con id "),
    
    /**
     * Mensaje de registro borrado exitosamente.
     */
    BORRADO_OK("El registro fue eliminado con exito "),
    
    /**
     * Mensaje de registro que no se pudo borrado por incumplimiento de 
     * validaciones o excepciones lanzadas.
     */
    BORRADO_FALLIDO("No se pudo eliminar del sistema el registro con id "),
    
    /**
     * Mensaje que informa que se deben seleccionar registro para realizar una 
     * consulta determinada en el sistema.
     */
    SIN_CRITERIOS("Se debe seleccionar un criterio de búsqueda."),
    
    /**
     * Contiene los registros mostrados en la consulta de la página de mensajes.
     */
    REGISTROS_POR_PAGINA("10"),
    
    /**
     * Valor de acción siguiente en la paginación.
     */
    SIGUIENTE("Siguiente"),
    
    /**
     * Valor de acción atrás en la paginación.
     */
    ATRAS("Atras"),
    
    /**
     * Valor de acción primera página en la paginación.
     */
    PRIMERA("Primera"),
    
    /**
     * Valor de acción última página en la paginación.
     */
    ULTIMA("Ultima"),
    
    /**
     * Valor de acción ir a x página en la paginación.
     */
    IR_A_PAGINA("IrA"),
    
    /**
     * Mensaje de validación cuando los campos requeridos se encuentran vacios.
     */
    CAMPOS_VACIOS("La configuración a crear, aún tiene campos vacíos por favor "
            + "verifique."),
    
    /**
     * Mensaje de validación especifico por campo cuando son requeridos y se 
     * encuentran vacíos.
     */
    CAMPOS_VACIOS_ESPECIFICOS("Por favor diligencie el campo de "),

    /**
     * Mensajes cuando los criterios de búsqueda dados no encuentra datos en el 
     * sistema.
     */
    SIN_COINCIDENCIA("El criterio seleccionado no contiene datos asociados en el"
            + " sistema."),
    
    /**
     * Mensaje de validación que informa al usuario que esta intentando borrar 
     * un único tipo de validación en una regla.
     */
    ELIMINACION_RESTRINGIDA("No se puede eliminar el tipo de validación porque "
            + "es el único registro. Si desea borre la regla."),
    /**
     * Mensaje de validación que informa al usuario que está intentando 
     * adicionar una regla sin tipos de validación y esto no está permitido.
     */
    VALIDA_CREACION_REGLA("Debe adicionar por lo menos un tipo de validación a "
            + "la regla.");
    
    /**
     * Contiene el valor de la constante.
     */
    private final String valor;

    /**
     * Constructor del enum.
     * Constructor del enum.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param valor Asignación de valor del enum.
     * 
     */
    private MensajeTipoValidacion(String valor) {
        this.valor = valor;
    }

    /**
     * Obtiener el valor de las constantes.
     * Obtiene el valor de la constante.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El valor de la constante.
     */
    public String getValor() {
        return valor;
    }
    
}
