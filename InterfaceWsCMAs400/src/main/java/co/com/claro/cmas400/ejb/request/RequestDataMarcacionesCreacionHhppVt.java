package co.com.claro.cmas400.ejb.request;

import lombok.*;

/**
 * Representa el Request de datos requerido para consumo de servicio Rest
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/01/11
 */

@ToString
@AllArgsConstructor
public class RequestDataMarcacionesCreacionHhppVt {

    @Getter
    @Setter
    private String fecha;

}