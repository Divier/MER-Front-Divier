package co.com.claro.mer.blockreport.domain.models.dto;

import co.com.claro.mer.email.domain.models.dto.EmailBaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa los datos requeridos para procesar el envío de correos
 * asociados a detalles de reportes o exportaciones de datos ejecutados en MER.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/08/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmailDetailsReportDto extends EmailBaseDto {

    private String reportName;
    private String reportDescription;

}
