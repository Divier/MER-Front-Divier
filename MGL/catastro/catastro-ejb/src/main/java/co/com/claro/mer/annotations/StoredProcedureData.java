package co.com.claro.mer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Anotación para los parámetros de los procedimientos almacenados
 * <p>
 *     Ejemplo:
 *     <pre>
 *         {@code
 *         @StoredProcedureData(parameterName = "PI_NOMBRE", parameterType = String.class)
 *         private String nombre;
 *         }
 *     </pre>
 *     <p>
 *         En este ejemplo se está definiendo que el parámetro de entrada se llama PI_NOMBRE y es de tipo String.
 *         Este debe coincidir con la firma del procedimiento almacenado.
 *     <p>
 * @see co.com.claro.mer.utils.StoredProcedureUtil Clase encargada de procesar la ejecución de procedimientos almacenados.
 * @author Manuel Hernández
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface StoredProcedureData {
    String parameterName();
    Class<?> parameterType();
}
