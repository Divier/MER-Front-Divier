package co.com.claro.mgl.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArchivoReporteDTO {

    private Integer idReporte;
    private String totalRegistros;
    private String fechaEntrega;
    private String rutaCargue;
    private String nomeReporte;
    private Integer codigo;
    private String resultado;
}
