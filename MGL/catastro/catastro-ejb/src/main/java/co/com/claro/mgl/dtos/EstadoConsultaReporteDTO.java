package co.com.claro.mgl.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class EstadoConsultaReporteDTO {
    private BigDecimal idReporte;
    private String ruta;
    private String nomSpool;
    private String poSpool;
    private String poBat;
}
