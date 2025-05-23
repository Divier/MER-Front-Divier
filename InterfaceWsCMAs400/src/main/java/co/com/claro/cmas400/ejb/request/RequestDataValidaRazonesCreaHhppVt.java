package co.com.claro.cmas400.ejb.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa el Request de datos requerido para consumo de
 * servicio Rest validateMoveReasonsResource
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestDataValidaRazonesCreaHhppVt {

    private String cuenta;
    private String identificadorCalle;
    private String identificadorCasa;
    private String identificadorApartamento;
    private String divisionalRegion;
    private String comunidad;

}
