/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.error;

import com.googlecode.hiberpcml.PcmlException;
import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;
import javax.persistence.NoResultException;
import lombok.Data;

/**
 * Excepci&oacute;n usada por el aplicativo MGL.
 *
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class ApplicationException extends Exception {

    /**
     * Inner class para Data Transfer Object de Excepci&oacute;n.
     *
     * @author Camilo Miranda (<i>mirandaca</i>).
     */
    @Data
    static class ExceptionDTO {

        /**
         * C&oacute;digo de la Excepci&oacute;n.
         */
        private String code;
        /**
         * Clase de la Excepci&oacute;n.
         */
        private Class exceptionClass;
        /**
         * Mensaje de la Excepci&oacute;n.
         */
        private String message;

        /**
         * Constructor.
         *
         * @param code C&oacute;digo de la Excepci&oacute;n.
         * @param exceptionClass Clase de la Excepci&oacute;n.
         * @param message Mensaje de la Excepci&oacute;n.
         */
        public ExceptionDTO(String code, Class exceptionClass, String message) {
            this.code = code;
            this.exceptionClass = exceptionClass;
            this.message = message;
        }

        /*
        * (non-Javadoc)
        * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (code != null && !code.isEmpty()) {
                builder.append(code);
            }
            if (exceptionClass != null) {
                if (builder.length() > 0) {
                    builder.append(" - ");
                }
                builder.append(exceptionClass.getSimpleName());
            }
            if (message != null) {
                if (builder.length() > 0) {
                    builder.append(" - ");
                }
                builder.append(message);
            }

            return (builder.toString());
        }
    }

    /**
     * Mapa de excepciones con su respectivo c&oacute;digo de acuerdo al
     * est&aacute;ndar.
     *
     * key = Class.getSimpleName() value = Exception DTO.
     */
    private static Map<String, ExceptionDTO> mapExcepciones;

    /**
     * Cadena de texto que ser&aacute; reemplazado por el c&oacute;digo de la
     * excepci&oacute;n.
     */
    private final static String EXCEPTION_TAG = "EX000";

    /**
     * Constructor.
     */
    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * Realiza el mapeo de las excepciones de acuerdo al est&aacute;ndar de
     * c&oacute;digos.
     */
    private static void cargarMapaExcepciones() {
        mapExcepciones = new HashMap<>();

        mapExcepciones.put(ArithmeticException.class.getSimpleName(), new ExceptionDTO("EX001_ARITHMETICEXCEPTION", ArithmeticException.class, "Está intentando realizar una operación aritmética inválida."));
        mapExcepciones.put(ArrayIndexOutOfBoundsException.class.getSimpleName(), new ExceptionDTO("EX002_ARRAYINDEXOUTOFBOUNDSEXCEPTION", ArrayIndexOutOfBoundsException.class, "Está intentando ingresar a una posición por fuera de los límites de un Array."));
        mapExcepciones.put(ArrayStoreException.class.getSimpleName(), new ExceptionDTO("EX003_ARRAYSTOREEXCEPTION", ArrayStoreException.class, "Está intentando guardar un objeto de tipo erróneo en un Array."));
        mapExcepciones.put(ClassCastException.class.getSimpleName(), new ExceptionDTO("EX004_CLASSCASTEXCEPTION", ClassCastException.class, "Está intentado aplicar un Cast de una clase a un objeto y la conversión no es permitida."));
        mapExcepciones.put(ClassNotFoundException.class.getSimpleName(), new ExceptionDTO("EX005_CLASSNOTFOUNDEXCEPTION", ClassNotFoundException.class, "Una clase no está en el classpath."));
        mapExcepciones.put(CloneNotSupportedException.class.getSimpleName(), new ExceptionDTO("EX006_CLONENOTSUPPORTEDEXCEPTION", CloneNotSupportedException.class, "Está intentando hacer un clone de un objeto cuya clase no tiene implementado la interface Cloneable."));
        mapExcepciones.put(IllegalAccessException.class.getSimpleName(), new ExceptionDTO("EX007_ILLEGALACCESSEXCEPTION", IllegalAccessException.class, "Está intentando crear una instancia reflexiva, asignar u obtener un campo, o invocar un método, pero el método que se está ejecutando no tiene acceso a la definición de la clase, el campo o método."));
        mapExcepciones.put(IllegalArgumentException.class.getSimpleName(), new ExceptionDTO("EX008_ILLEGALARGUMENTEXCEPTION", IllegalArgumentException.class, "Está intentado pasar un argumento inapropiado a un método."));
        mapExcepciones.put(IllegalMonitorStateException.class.getSimpleName(), new ExceptionDTO("EX009_ILLEGALMONITORSTATEEXCEPTION", IllegalMonitorStateException.class, "Un hilo está intentado esperar un objeto de un monitor o de notificar a otros hilos que esperan un objeto de un monitor sin poseer el monitor especificado."));
        mapExcepciones.put(IllegalStateException.class.getSimpleName(), new ExceptionDTO("EX010_ILLEGALSTATEEXCEPTION", IllegalStateException.class, "Un método ha sido invocado en un momento inapropiado, el entorno Java o aplicación Java no está en un estado apropiado para la operación solicitada."));
        mapExcepciones.put(IllegalThreadStateException.class.getSimpleName(), new ExceptionDTO("EX011_ILLEGALTHREADSTATEEXCEPTION", IllegalThreadStateException.class, "Un hilo no está en un estado apropiado para la operación solicitada."));
        mapExcepciones.put(IndexOutOfBoundsException.class.getSimpleName(), new ExceptionDTO("EX012_INDEXOUTOFBOUNDSEXCEPTION", IndexOutOfBoundsException.class, "Está intentando ingresar a un elemento en una posición por fuera de los límites de un Array."));
        mapExcepciones.put(InstantiationException.class.getSimpleName(), new ExceptionDTO("EX013_INSTANTIATIONEXCEPTION", InstantiationException.class, "Está intentando crear una instancia de una clase pero la clase no se puede instanciar."));
        mapExcepciones.put(InterruptedException.class.getSimpleName(), new ExceptionDTO("EX014_INTERRUPTEDEXCEPTION", InterruptedException.class, "Un hilo ocupado ha sido interrumpido durante su actividad."));
        mapExcepciones.put(NegativeArraySizeException.class.getSimpleName(), new ExceptionDTO("EX015_NEGATIVEARRAYSIZEEXCEPTION", NegativeArraySizeException.class, "Está intentando crear un array de longitud negativa."));
        mapExcepciones.put(NoSuchFieldException.class.getSimpleName(), new ExceptionDTO("EX016_NOSUCHFIELDEXCEPTION", NoSuchFieldException.class, "Un atributo dentro de la clase no puede ser encontrado."));
        mapExcepciones.put(NoSuchMethodException.class.getSimpleName(), new ExceptionDTO("EX017_NOSUCHMETHODEXCEPTION", NoSuchMethodException.class, "Un método dentro de la clase no puede ser encontrado."));
        mapExcepciones.put(NullPointerException.class.getSimpleName(), new ExceptionDTO("EX018_NULLPOINTEREXCEPTION", NullPointerException.class, "Está intentando usar un objeto null."));
        mapExcepciones.put(NumberFormatException.class.getSimpleName(), new ExceptionDTO("EX019_NUMBERFORMATEXCEPTION", NumberFormatException.class, "Está intentando convertir un String a un tipo numérico, pero el String no tiene el formato adecuado."));
        mapExcepciones.put(SecurityException.class.getSimpleName(), new ExceptionDTO("EX020_SECURITYEXCEPTION", SecurityException.class, "El security manager indica que hay una violación de seguridad."));
        mapExcepciones.put(StringIndexOutOfBoundsException.class.getSimpleName(), new ExceptionDTO("EX021_STRINGINDEXOUTOFBOUNDS", StringIndexOutOfBoundsException.class, "Está intentando ingresar a un índice negativo o mayor que el tamaño de una cadena."));
        mapExcepciones.put(UnsupportedOperationException.class.getSimpleName(), new ExceptionDTO("EX022_UNSUPPORTEDOPERATIONEXCEPTION", UnsupportedOperationException.class, "Una operación solicitada no está soportada."));

        mapExcepciones.put(ConcurrentModificationException.class.getSimpleName(), new ExceptionDTO("EX023_CONCURRENTMODIFICATIONEXCEPTION", ConcurrentModificationException.class, "Un método ha detectado la modificación simultánea de un objeto cuando dicha modificación no es permisible."));
        mapExcepciones.put(EmptyStackException.class.getSimpleName(), new ExceptionDTO("EX024_EMPTYSTACKEXCEPTION", EmptyStackException.class, "Un método de la clase Stack indica que la pila está vacía."));
        mapExcepciones.put(MissingResourceException.class.getSimpleName(), new ExceptionDTO("EX025_MISSINGRESOURCEEXCEPTION", MissingResourceException.class, "Un recurso solicitado no se encuentra."));
        mapExcepciones.put(NoSuchElementException.class.getSimpleName(), new ExceptionDTO("EX026_NOSUCHELEMENTEXCEPTION", NoSuchElementException.class, "El método nextElement de una enumeración indica que no hay más elementos en la enumeración."));

        mapExcepciones.put(IntrospectionException.class.getSimpleName(), new ExceptionDTO("EX027_INTROSPECTIONEXCEPTION", IntrospectionException.class, "Una excepción ocurrió durante una Introspección."));
        mapExcepciones.put(PropertyVetoException.class.getSimpleName(), new ExceptionDTO("EX028_PROPERTYVETOEXCEPTION", PropertyVetoException.class, "Un cambio propuesto a una propiedad representa un valor inaceptable."));

        mapExcepciones.put(CharConversionException.class.getSimpleName(), new ExceptionDTO("EX029_CHARCONVERSIONEXCEPTION", CharConversionException.class, "Ha ocurrido una excepción en la conversión de un carácter."));
        mapExcepciones.put(EOFException.class.getSimpleName(), new ExceptionDTO("EX030_EOFEXCEPTION", EOFException.class, "Un end of file or end of stream ha llegado de forma inesperada durante la entrada."));
        mapExcepciones.put(FileNotFoundException.class.getSimpleName(), new ExceptionDTO("EX031_FILENOTFOUNDEXCEPTION", FileNotFoundException.class, "No fue posible abrir un archivo en una ruta de acceso especificada."));
        mapExcepciones.put(InterruptedIOException.class.getSimpleName(), new ExceptionDTO("EX032_INTERRUPTEDIOEXCEPTION", InterruptedIOException.class, "Una transferencia de entrada o salida se ha terminado porque el hilo se interrumpió."));
        mapExcepciones.put(InvalidClassException.class.getSimpleName(), new ExceptionDTO("EX033_INVALIDCLASSEXCEPTION", InvalidClassException.class, "La versión serializada de una clase no coincide con el descriptor de la clase leída del stream, una clase contiene tipos de datos desconocidos, una clase no tiene acceso a un constructor sin argumentos."));
        mapExcepciones.put(InvalidObjectException.class.getSimpleName(), new ExceptionDTO("EX034_INVALIDOBJECTEXCEPTION", InvalidObjectException.class, "Uno o varios objetos deserializados fallaron las pruebas de validación."));
        mapExcepciones.put(IOException.class.getSimpleName(), new ExceptionDTO("EX035_IOEXCEPTION", IOException.class, "Una operación de E/S ha fallado o se ha interrumpido."));
        mapExcepciones.put(NotActiveException.class.getSimpleName(), new ExceptionDTO("EX036_NOTACTIVEEXCEPTION", NotActiveException.class, "La serialización o deserialización no está activa."));
        mapExcepciones.put(NotSerializableException.class.getSimpleName(), new ExceptionDTO("EX037_NOTSERIALIZABLEEXCEPTION", NotSerializableException.class, "Una instancia requiere tener la interfaz Serializable."));
        mapExcepciones.put(StreamCorruptedException.class.getSimpleName(), new ExceptionDTO("EX038_STREAMCORRUPTEDEXCEPTION", StreamCorruptedException.class, "La información de control de un objeto stream ha violado alguna comprobación de coherencia interna."));
        mapExcepciones.put(SyncFailedException.class.getSimpleName(), new ExceptionDTO("EX039_SYNCFAILEDEXCEPTION", SyncFailedException.class, "Una operación de sincronización ha fallado."));
        mapExcepciones.put(UnsupportedEncodingException.class.getSimpleName(), new ExceptionDTO("EX040_UNSUPPORTEDENCODINGEXCEPTION", UnsupportedEncodingException.class, "Codificación de caracteres no soportado."));
        mapExcepciones.put(UTFDataFormatException.class.getSimpleName(), new ExceptionDTO("EX041_UTFDATAFORMATEXCEPTION", UTFDataFormatException.class, "En los datos de un input Stream ha sido leído un cadena malformada en formato UTF-8 modificado."));
        mapExcepciones.put(WriteAbortedException.class.getSimpleName(), new ExceptionDTO("EX042_WRITEABORTEDEXCEPTION", WriteAbortedException.class, "Ha fallado un proceso de escritura."));

        mapExcepciones.put(BindException.class.getSimpleName(), new ExceptionDTO("EX043_BINDEXCEPTION", BindException.class, "Se produjo un error al intentar enlazar un socket a una dirección y puerto local."));
        mapExcepciones.put(ConnectException.class.getSimpleName(), new ExceptionDTO("EX044_CONNECTEXCEPTION", ConnectException.class, "Se produjo un error al intentar conectar un socket a una dirección y puerto remoto."));
        mapExcepciones.put(MalformedURLException.class.getSimpleName(), new ExceptionDTO("EX045_MALFORMEDURLEXCEPTION", MalformedURLException.class, "Se ha encontrado una URL con formato incorrecto."));
        mapExcepciones.put(NoRouteToHostException.class.getSimpleName(), new ExceptionDTO("EX046_NOROUTETOHOSTEXCEPTION", NoRouteToHostException.class, "Se produjo un error al intentar conectar un socket a una dirección y puerto remoto."));
        mapExcepciones.put(PortUnreachableException.class.getSimpleName(), new ExceptionDTO("EX047_PORTUNREACHABLEEXCEPTION", PortUnreachableException.class, "Se ha recibido un mensaje ICMP puerto inalcanzable sobre un datagrama conectado."));
        mapExcepciones.put(ProtocolException.class.getSimpleName(), new ExceptionDTO("EX048_PROTOCOLEXCEPTION", ProtocolException.class, "Se produjo un error en el protocolo subyacente, tal como un error de TCP."));
        mapExcepciones.put(SocketException.class.getSimpleName(), new ExceptionDTO("EX049_SOCKETEXCEPTION", SocketException.class, "Se produjo un error creando o accediendo a un Socket."));
        mapExcepciones.put(SocketTimeoutException.class.getSimpleName(), new ExceptionDTO("EX050_SOCKETTIMEOUTEXCEPTION", SocketTimeoutException.class, "El tiempo de espera se ha agotado esperando leer o aceptar socket."));
        mapExcepciones.put(UnknownHostException.class.getSimpleName(), new ExceptionDTO("EX051_UNKNOWNHOSTEXCEPTION", UnknownHostException.class, "La dirección IP de un host no pudo ser determinada."));
        mapExcepciones.put(UnknownServiceException.class.getSimpleName(), new ExceptionDTO("EX052_UNKNOWNSERVICEEXCEPTION", UnknownServiceException.class, "Se ha producido una excepción servicio desconocido."));
        mapExcepciones.put(URISyntaxException.class.getSimpleName(), new ExceptionDTO("EX053_URISYNTAXEXCEPTION", URISyntaxException.class, "Se ha producido un error de sintaxis una cadena, no se pudo analizar como una referencia URI."));
        mapExcepciones.put(SQLException.class.getSimpleName(), new ExceptionDTO("EX055_SQLEXCEPTION", SQLException.class, "Ha ocurrido un error de acceso a la base de datos."));
        mapExcepciones.put(PcmlException.class.getSimpleName(), new ExceptionDTO("EX056_PCMLEXCEPTION", PcmlException.class, "Ha ocurrido una excepción PCML."));
        mapExcepciones.put(TimeoutException.class.getSimpleName(), new ExceptionDTO("EX057_TIMEOUTEXCEPTION", TimeoutException.class, "El tiempo de espera se ha agotado."));
        mapExcepciones.put(NoResultException.class.getSimpleName(), new ExceptionDTO("EX058_NORESULTEXCEPTION", NoResultException.class, "Se ha ejecutado una query y no retorno resultados."));
    }
    
    
    /**
     * Reemplaza el c&oacute;digo de la excepci&oacute;n en el mensaje, de
     * acuerdo al est&aacute;ndar de manejo de excepciones.
     *
     * @param message Mensaje de excepci&oacute;n.
     * @param cause Excepci&oacute;n causante.
     * @return Mensaje de excepci&oacute;n con su respectivo c&oacute;digo de
     * error.
     */
    public static String replaceExceptionCode(String message, Throwable cause) {
        String result = message;

        if (message != null && cause != null && message.contains(EXCEPTION_TAG)) {
            // refrescar el listado de excepciones.
            cargarMapaExcepciones();

            if (mapExcepciones != null && !mapExcepciones.isEmpty()) {
                ExceptionDTO exceptionDTO = null;
                if (cause instanceof ApplicationException) {
                    if (cause.getCause() != null) {
                        exceptionDTO = mapExcepciones.get(cause.getCause().getClass().getSimpleName());
                    }
                } else {
                    exceptionDTO = mapExcepciones.get(cause.getClass().getSimpleName());
                }
                
                if (exceptionDTO != null && exceptionDTO.getCode() != null && !exceptionDTO.getCode().isEmpty()) {
                    result = result.replaceAll(EXCEPTION_TAG, exceptionDTO.getCode());
                }
            }
        }

        return (result);
    }
}
