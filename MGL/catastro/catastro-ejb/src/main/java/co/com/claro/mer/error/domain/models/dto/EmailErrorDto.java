package co.com.claro.mer.error.domain.models.dto;

import co.com.claro.mer.email.domain.models.dto.EmailBaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO para mapear la información de errores ocurridos en la capa de presentación
 * a notificar por correo.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmailErrorDto extends EmailBaseDto {

    private Exception exception;
    private String uuid;

}
