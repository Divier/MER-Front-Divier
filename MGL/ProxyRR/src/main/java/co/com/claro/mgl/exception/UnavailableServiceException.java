package co.com.claro.mgl.exception;

/**
 * Excepci&oacute;n lanzada al momento en que alg&uacute;n servicio se encuentre
 * indisponible.
 *
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class UnavailableServiceException extends Exception {

    /**
     * Objeto que hace referencia a la excepci&oacute;n.
     */
    private Object reference;

    /**
     * Constructor.
     */
    public UnavailableServiceException() {
        super();
    }

    public UnavailableServiceException(String message) {
        super(message);
    }

    public UnavailableServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnavailableServiceException(Throwable cause) {
        super(cause);
    }

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

}
