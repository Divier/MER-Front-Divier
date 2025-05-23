package co.com.claro.mer.homepassed.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * DTO para representar el type HHPP_DATA_TYPE para creación de Home Passed.
 *
 * @implNote Tener en cuenta que este DTO se usa para enviar la información
 * de creación de Home Passed a través de un procedimiento almacenado.
 * Por lo tanto, los atributos deben coincidir con los atributos del type HHPP_DATA_TYPE
 * creado en la base de datos, y deben estar en el mismo orden.
 * @see co.com.claro.mer.dtos.request.procedure.CreateHhppRequestDto
 *  Clase que representa el objeto de entrada para el procedimiento almacenado CREATE_HHPP_SP.
 * @author Gildardo Mora
 * @version 1.0, 2024/08/14
 */
@Getter
@Setter
public class HomePassedDto {

    private BigDecimal direccionId;
    private BigDecimal nodoId;
    private String tipoTecnologiaHabId;
    private BigDecimal subDireccionId;
    private Integer tipoConexionTecHabiId;
    private Integer tipoRedTecHabiId;
    private String estadoId;
    private String usuarioCreacion;
    private String tecnologiaHabilitadaIdRr;
    private String calle;
    private String placa;
    private String apart;
    private String comunidad;
    private String division;
    private String estadoUnit;
    private String vendedor;
    private String codigoPostal;
    private String tipoAcomet;
    private String ultUbicacion;
    private String headEnd;
    private String tipo;
    private String edificio;
    private String tipoUnidad;
    private String tipoCblAcometida;
    private Date fechaAudit;
    private Integer estadoRegistro;
    private String nap;
    private String idDireccionHhppVirtualRr;

}
