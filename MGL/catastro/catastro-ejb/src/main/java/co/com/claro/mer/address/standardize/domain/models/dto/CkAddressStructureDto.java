package co.com.claro.mer.address.standardize.domain.models.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa la estructura de una dirección estandarizada en el formato de calle - carrera.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/02
 */
@ToString
@Getter
@Setter
public class CkAddressStructureDto {

    /**
     * Tipo de vía principal
     */
    private String mainTrackType;

    /**
     * Número de vía principal
     */
    private String numMainTrack;

    /**
     * Primer modificador de la vía principal, que corresponde a la letra
     * que acompaña al número de la vía principal
     * Ejemplo: Calle 1A, Carrera 1A, Avenida 1A
     */
    private String mainTrackFirstModifier;

    /**
     * Segundo modificador de la vía principal, que corresponde a la letra o número
     * que acompaña a la letra de la vía principal
     * Ejemplo: Calle 1A1, Carrera 1A1, Avenida 1A1, calle 1AB
     */
    private String mainTrackSecondModifier;

    /**
     * Bis de la vía principal
     * Ejemplo: Bis, Bis A, Bis B
     */
    private String bisMainTrack;

    /**
     * Cuadrante de la vía principal
     */
    private String mainTrackQuadrant;

    /**
     * Tipo de vía generadora
     */
    private String generativeTrackType;

    /**
     * Número de vía generadora
     */
    private String numGenerativeTrack;

    /**
     * Primer modificador de la vía generadora, que corresponde a la letra
     * que acompaña al número de la vía generadora
     */
    private String generativeTrackFirstModifier;

    /**
     * Segundo modificador de la vía generadora, que corresponde a la letra o número
     * que acompaña a la letra de la vía generadora
     */
    private String generativeTrackSecondModifier;

    /**
     * Letra de la codirección de la vía generadora
     */
    private String genTrackCoDirectionLetter;

    /**
     * Bis de la vía generadora
     */
    private String bisGenerativeTrack;

    /**
     * Cuadrante de la vía generadora
     */
    private String generativeTrackQuadrant;

    /**
     * Placa de la dirección
     */
    private String directionPlate;

    /**
     * Complemento de la dirección
     */
    private AddressComplementDto addressComplement;

    public CkAddressStructureDto(AddressComplementDto addressComplement) {
        this.addressComplement = addressComplement;
    }

}
