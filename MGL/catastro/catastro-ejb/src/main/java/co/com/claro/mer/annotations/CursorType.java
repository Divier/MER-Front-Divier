package co.com.claro.mer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación para definir el tipo de cursor que se va a retornar en el procedimiento almacenado
 * se debe anotar junto a la anotacion {@link StoredProcedureData} asociada a un cursor definido del tipo {@link java.util.List}
 * <p>
 *     Ejemplo:
 *     <pre>
 *         {@code
 *         @StoredProcedureData(parameterName = "PO_CURSOR", parameterType = List.class)
 *         @CursorType(type = TipoCursorDto.class)
 *         private List<TipoCursorDto> informacionCursorList;
 *         }
 *     </pre>
 *     <p>
 *         En este ejemplo se está definiendo que el cursor que se va a retornar es de tipo TipoCursorDto.
 *         Este debe coincidir con la firma del cursor definido en el procedimiento almacenado.
 *     <p>
 * @see StoredProcedureData Anotación para definir los datos de entrada y salida de un procedimiento almacenado.
 * @see co.com.claro.mer.utils.StoredProcedureUtil Clase encargada de procesar la ejecución de procedimientos almacenados.
 * @author Manuel Hernández
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CursorType {
     Class<?> type();

}
