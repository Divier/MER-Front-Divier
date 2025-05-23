package co.com.claro.mer.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumerado para definir los esquemas de BD usados en MER y su respectiva unidad de persistencia asociada.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/03/08
 */
@Getter
@AllArgsConstructor
public enum SchemesMerEnum {

    MGL_SCHEME("MGL_SCHEME", "catastro-ejbPU"),
    GESTIONNEW("GESTIONNEW", "gestionseguridad-ejbPU"),
    STORED_PROCEDURE("MGL_SCHEME", "MER_PU");

    private final String nombreEsquema;
    private final String unidadPersistencia;

}
