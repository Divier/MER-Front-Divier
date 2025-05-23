package co.com.claro.mer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotación para enviar clases personalizadas a los procedimientos almacenados
 * representando los Types de la base de datos Oracle.
 * <p>
 *     Ejemplo:
 *     <pre>
 *         {@code
 *         @ProcedureType(name = "PERSONA_TYPE")
 *         }
 *     </pre>
 *     <p>
 *        En este ejemplo se está definiendo que el procedimiento almacenado usará un parámetro de entrada de tipo 'Type',
 *        que se llama PERSONA_TYPE.
 *        Este debe coincidir con la firma del procedimiento almacenado.
 *     <p>
 *        En el caso de que el procedimiento almacenado sea un tipo de dato compuesto, se debe definir el tipo de dato
 *        en la anotación {@link StoredProcedureData} y se debe definir el nombre del tipo de dato en la anotación {@link ProcedureType}.
 *        <p>
 *            Ejemplo:
 *               <pre>
 *                  {@code
 *                     @StoredProcedureData(parameterName = "p_persona", parameterType = Persona.class)
 *                     @ProcedureType(name = "PERSONA_TYPE")
 *                  }
 *               </pre>
 *                    <p>
 *                      En este ejemplo se está definiendo que el procedimiento almacenado recibe un parámetro de tipo PERSONA_TYPE y el tipo de dato
 *                      que se envía como parámetro es de tipo Persona. La clase Persona debe tener los atributos que coincidan con los atributos del Type
 *                      de la base de datos y debe tener los atributos asignados en el mismo orden que el Type.
 *                    <p>
 *                        En el caso de que el procedimiento almacenado reciba una lista de un tipo de dato compuesto, se debe definir el tipo de dato
 *                        en la anotación {@link StoredProcedureData} y se debe definir el nombre del tipo de dato en la anotación {@link ProcedureType}.
 *                        <p>
 *                            Ejemplo:
 *                            <pre>
 *                                {@code
 *                                @ProcedureType(name = "PERSONA_TYPE", nameOfListType = "LISTA_PERSONAS_T")
 *                                @StoredProcedureData(parameterName = "p_personas", parameterType = List.class)
 *                                private List<Persona> personas;
 *                                }
 *                                </pre>
 *                                <p>
 *                                    En este ejemplo se está definiendo que el procedimiento almacenado recibe un parámetro de tipo LISTA_PERSONAS_T y el tipo de dato
 *                                    que se envía como parámetro es de tipo List<Persona>.
 *                                    Adicionalmente, se está definiendo que el tipo de dato que se envía como parámetro es de tipo PERSONA_TYPE.
 *
 * @see co.com.claro.mer.utils.StoredProcedureUtil Clase encargada de procesar la ejecución de procedimientos almacenados.
 * @see StoredProcedureData Anotación para los parámetros de los procedimientos almacenados.
 * @author Gildardo Mora
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcedureType {
    String nameOfType();
    String nameOfListType() default "";
}
