package co.com.claro.mer.address.standardize.application.usecases;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mgl.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el caso de uso StandardizeComplementUseCaseImpl
 * @see StandardizeComplementUseCaseImpl
 * @see AddressComplementDto
 * @see AddressTypeEnum
 * @author Gildardo Mora
 */
class StandardizeComplementUseCaseImplTest {

    private StandardizeComplementUseCaseImpl standardizeComplementUseCase;
    private AddressComplementDto addressComplementDto;

    @BeforeEach
    void setUp() {
        standardizeComplementUseCase = new StandardizeComplementUseCaseImpl();
        addressComplementDto = new AddressComplementDto();
    }

    @Test
    @DisplayName("Debería estandarizar un complemento con todos los niveles")
    void standardizeCompleteComplement() {
        addressComplementDto.setTypeComplementLevel1("TORRE");
        addressComplementDto.setValueComplementLevel1("1");
        addressComplementDto.setTypeComplementLevel2("BLOQUE");
        addressComplementDto.setValueComplementLevel2("A");
        addressComplementDto.setTypeComplementLevel3("INTERIOR");
        addressComplementDto.setValueComplementLevel3("2");
        addressComplementDto.setTypeComplementLevel4("APARTAMENTO");
        addressComplementDto.setValueComplementLevel4("301");
        addressComplementDto.setTypeComplementLevel5("EDIFICIO");
        addressComplementDto.setValueComplementLevel5("MIRADOR");
        addressComplementDto.setNeighborhood("SAN JOSE");
        addressComplementDto.setTypeComplementLevel6("CONJUNTO");
        addressComplementDto.setValueComplementLevel6("EL BOSQUE");

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("TORRE 1 BLOQUE A INTERIOR 2 APARTAMENTO 301 EDIFICIO MIRADOR BARRIO SAN JOSE CONJUNTO EL BOSQUE", result);
    }

    @Test
    @DisplayName("Debería estandarizar un complemento con valores vacíos")
    void standardizeWithEmptyValues() {
        addressComplementDto.setTypeComplementLevel1("TORRE");
        addressComplementDto.setValueComplementLevel1("1");
        // Level2 está vacío intencionalmente
        addressComplementDto.setTypeComplementLevel3("INTERIOR");
        addressComplementDto.setValueComplementLevel3("2");

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("TORRE 1 INTERIOR 2", result);
    }

    @Test
    @DisplayName("Debería estandarizar un complemento tipo CASA_PISO")
    void standardizeComplementWithCasaPiso() {
        addressComplementDto.setTypeComplementLevel5(Constant.OPT_CASA_PISO);
        addressComplementDto.setValueComplementLevel5("5");

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("CASA 5", result);
    }

    @Test
    @DisplayName("Debería estandarizar un complemento tipo PISO")
    void standardizeComplementWithPiso() {
        addressComplementDto.setTypeComplementLevel5(Constant.OPT_PISO_INTERIOR);
        addressComplementDto.setValueComplementLevel5("3");

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("PISO 3", result);
    }

    @Test
    @DisplayName("Debería estandarizar un complemento tipo PISO_CAJERO con AddressType CK")
    void standardizeComplementWithPisoCajeroCK() {
        addressComplementDto.setTypeComplementLevel5(Constant.OPT_PISO_CAJERO);
        addressComplementDto.setValueComplementLevel5("2");
        addressComplementDto.setValueComplementLevel6("123");
        addressComplementDto.setAddressType(AddressTypeEnum.CK);

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("PI 2 CAJ 123", result);
    }

    @Test
    @DisplayName("Debería estandarizar un complemento tipo PISO_CAJERO con AddressType BM")
    void standardizeComplementWithPisoCajeroBM() {
        addressComplementDto.setTypeComplementLevel5(Constant.OPT_PISO_CAJERO);
        addressComplementDto.setValueComplementLevel5("2");
        addressComplementDto.setValueComplementLevel6("123");
        addressComplementDto.setAddressType(AddressTypeEnum.BM);

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("CAJ 123 PI 2", result);
    }

    @Test
    @DisplayName("Debería estandarizar un complemento tipo CASA sin valor")
    void standardizeComplementWithCasaWithoutValue() {
        addressComplementDto.setTypeComplementLevel5("CASA");
        addressComplementDto.setValueComplementLevel5("");

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("CASA", result);
    }

    @Test
    @DisplayName("Debería estandarizar un complemento con barrio")
    void standardizeComplementWithNeighborhood() {
        addressComplementDto.setNeighborhood("LOS PINOS");

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("BARRIO LOS PINOS", result);
    }

    @Test
    @DisplayName("Debería manejar espacios extras correctamente")
    void standardizeComplementWithExtraSpaces() {
        addressComplementDto.setTypeComplementLevel1("TORRE");
        addressComplementDto.setValueComplementLevel1("1");
        addressComplementDto.setTypeComplementLevel2("  ");
        addressComplementDto.setValueComplementLevel2("  ");
        addressComplementDto.setTypeComplementLevel3("INTERIOR");
        addressComplementDto.setValueComplementLevel3("2  ");

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto);

        assertEquals("TORRE 1 INTERIOR 2", result);
    }

    @Test
    @DisplayName("Deberia estandarizar complemento de dirección de ciudad multi origen")
    void standardizeComplementWithCityMultiOrigin() {
        addressComplementDto.setAddressType(AddressTypeEnum.CK);
        addressComplementDto.setTypeComplementLevel1("TORRE");
        addressComplementDto.setValueComplementLevel1("1");
        addressComplementDto.setTypeComplementLevel2("BLOQUE");
        addressComplementDto.setValueComplementLevel2("A");
        addressComplementDto.setTypeComplementLevel3("INTERIOR");
        addressComplementDto.setValueComplementLevel3("2");
        addressComplementDto.setTypeComplementLevel4("APARTAMENTO");
        addressComplementDto.setValueComplementLevel4("301");
        addressComplementDto.setTypeComplementLevel5("EDIFICIO");
        addressComplementDto.setValueComplementLevel5("MIRADOR");
        addressComplementDto.setNeighborhood("SAN JOSE");
        addressComplementDto.setTypeComplementLevel6("CONJUNTO");
        addressComplementDto.setValueComplementLevel6("EL BOSQUE");

        String result = standardizeComplementUseCase.standardizeComplement(addressComplementDto, true);

        assertEquals("TORRE 1 BLOQUE A INTERIOR 2 APARTAMENTO 301 EDIFICIO MIRADOR SAN JOSE CONJUNTO EL BOSQUE", result);
    }

}