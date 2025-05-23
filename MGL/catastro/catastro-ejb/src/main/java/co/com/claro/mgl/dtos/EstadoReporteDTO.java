package co.com.claro.mgl.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class EstadoReporteDTO {

    private String accion;
    private Integer idReporte;
    private String tipoProceso;
    private String filtro;
    private String nombreArchivo;
    private String ruta;
    private String rolEjecucion;
    private Integer estadoReporte;
    private String estado;
    private Integer codigo;
    private String resultado;

}
