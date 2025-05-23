package co.com.claro.mgl.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ReporteConsultaQueryDTO {
    
    private String ruta;
    private String nombreArchivo;
    private String estado;
    private Integer codigo;
    private String resultado;
}
