package co.com.claro.mgl.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ActEstComercialDto {

    private String tecnologia;
    private String departamento;
    private String ciudad;
    private String centroPoblado;
    private String divisional;
    private String estadoNodo;
    private String anchoBanda;
    private List<ArchActEstComercialDto> archivoConsulta;
    private List<ArchActEstComercialDto> archivoCarga;

}
