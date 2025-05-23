package co.com.claro.mgl.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArchActEstComercialDto {

    private Integer estReporteId;
    private String nombreArchivo;
    private String fechaRegistro;
    private String filtro;
    private String estado;
    private String ruta;
    private Integer totalReg;
    private boolean exitoso;
    private String color;

}
