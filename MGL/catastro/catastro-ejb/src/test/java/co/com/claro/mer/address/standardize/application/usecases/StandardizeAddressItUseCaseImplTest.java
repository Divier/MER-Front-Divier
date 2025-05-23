package co.com.claro.mer.address.standardize.application.usecases;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mer.address.standardize.domain.models.dto.ItAddressStructureDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para estandarizar direcciones IT.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/04/02
 */
class StandardizeAddressItUseCaseImplTest {

    private static final String IT_VALUE_PLATE = "12345";
    private static final String IT_VALUE_PLATE_TEXT = "ABC123";
    private StandardizeAddressItUseCaseImpl standardizeAddressItUseCase;
    private ItAddressStructureDto itAddressStructureDto;

    @BeforeEach
    void setUp() {
        standardizeAddressItUseCase = new StandardizeAddressItUseCaseImpl();
        AddressComplementDto addressComplementDto = new AddressComplementDto();
        itAddressStructureDto = new ItAddressStructureDto(addressComplementDto);
    }

    @Test
    @DisplayName("Debería estandarizar una dirección IT con todos los niveles completos")
    void standardizeFullItAddress() {
        itAddressStructureDto.setItTypeLevel1("VEREDA");
        itAddressStructureDto.setItValueLevel1("SAN ANTONIO");
        itAddressStructureDto.setItTypeLevel2("FINCA");
        itAddressStructureDto.setItValueLevel2("EL MANANTIAL");
        itAddressStructureDto.setItTypeLevel3("SECTOR");
        itAddressStructureDto.setItValueLevel3("NORTE");
        itAddressStructureDto.setItTypeLevel4("ZONA");
        itAddressStructureDto.setItValueLevel4("ALTA");
        itAddressStructureDto.setItTypeLevel5("LOTE");
        itAddressStructureDto.setItValueLevel5("5");
        itAddressStructureDto.setItTypeLevel6("PREDIO");
        itAddressStructureDto.setItValueLevel6("LOS PINOS");
        String result = standardizeAddressItUseCase.standardizeItAddress(itAddressStructureDto);
        assertEquals("VEREDA SAN ANTONIO FINCA EL MANANTIAL SECTOR NORTE ZONA ALTA LOTE 5 PREDIO LOS PINOS", result);
    }

    @Test
    @DisplayName("Debería estandarizar una dirección IT con algunos niveles vacíos")
    void standardizeItAddressWithEmptyLevels() {
        itAddressStructureDto.setItTypeLevel1("VEREDA");
        itAddressStructureDto.setItValueLevel1("SAN ANTONIO");
        itAddressStructureDto.setItTypeLevel3("SECTOR");
        itAddressStructureDto.setItValueLevel3("NORTE");
        itAddressStructureDto.setItTypeLevel5("LOTE");
        itAddressStructureDto.setItValueLevel5("5");
        String result = standardizeAddressItUseCase.standardizeItAddress(itAddressStructureDto);
        assertEquals("VEREDA SAN ANTONIO SECTOR NORTE LOTE 5", result);
    }

    @Test
    @DisplayName("Debería estandarizar una dirección IT solo con barrio")
    void standardizeItAddressWithOnlyNeighborhood() {
        itAddressStructureDto.setNeighborhood("LOS PINOS");
        String result = standardizeAddressItUseCase.standardizeItAddress(itAddressStructureDto);
        assertEquals("BARRIO LOS PINOS", result);
    }

    @Test
    @DisplayName("Debería manejar espacios extra en la dirección IT")
    void standardizeItAddressWithExtraSpaces() {
        itAddressStructureDto.setItTypeLevel1("VEREDA ");
        itAddressStructureDto.setItValueLevel1(" SAN ANTONIO ");
        itAddressStructureDto.setItTypeLevel2("  FINCA");
        itAddressStructureDto.setItValueLevel2("EL  MANANTIAL  ");
        String result = standardizeAddressItUseCase.standardizeItAddress(itAddressStructureDto);
        assertEquals("VEREDA SAN ANTONIO FINCA EL MANANTIAL", result);
    }

    @Test
    @DisplayName("Debería estandarizar placa IT con tipo MANZANA-CASA")
    void standardizeItPlateAddressWithManzanaCasa() {
        itAddressStructureDto.setAddressComplement(null);
        itAddressStructureDto.setNeighborhood(null);
        itAddressStructureDto.setItTypePlate("MANZANA-CASA");
        itAddressStructureDto.setItValuePlate("M5-C10");
        String result = standardizeAddressItUseCase.standardizeItPlateAddress(itAddressStructureDto);
        assertEquals("MANZANA-CASA M5-C10", result);
    }

    @Test
    @DisplayName("Debería estandarizar placa IT con tipo VP-PLACA")
    void standardizeItPlateAddressWithVpPlaca() {
        itAddressStructureDto.setAddressComplement(null);
        itAddressStructureDto.setItTypePlate("VP-PLACA");
        itAddressStructureDto.setItValuePlate(IT_VALUE_PLATE);
        String result = standardizeAddressItUseCase.standardizeItPlateAddress(itAddressStructureDto);
        assertEquals("VP-PLACA 12345", result);
    }

    @Test
    @DisplayName("Debería estandarizar placa IT con tipo KDX")
    void standardizeItPlateAddressWithKdx() {
        itAddressStructureDto.setItTypePlate("KDX");
        itAddressStructureDto.setItValuePlate(IT_VALUE_PLATE_TEXT);
        String result = standardizeAddressItUseCase.standardizeItPlateAddress(itAddressStructureDto);
        assertEquals("KDX ABC123", result);
    }

    @Test
    @DisplayName("Debería estandarizar placa IT con tipo inválido")
    void standardizeItPlateAddressWithInvalidType() {
        itAddressStructureDto.setItTypePlate("OTRO");
        itAddressStructureDto.setItValuePlate(IT_VALUE_PLATE_TEXT);
        String result = standardizeAddressItUseCase.standardizeItPlateAddress(itAddressStructureDto);
        assertEquals(IT_VALUE_PLATE_TEXT, result);
    }

    @Test
    @DisplayName("Debería estandarizar placa IT con tipo CONTADOR")
    void standardizeItPlateAddressWithContador() {
        itAddressStructureDto.setItTypePlate("CONTADOR");
        itAddressStructureDto.setItValuePlate(IT_VALUE_PLATE);
        String result = standardizeAddressItUseCase.standardizeItPlateAddress(itAddressStructureDto);
        assertEquals("CONTADOR 12345", result);
    }

    @Test
    @DisplayName("Debería retornar string vacío para placa IT con valores vacíos")
    void standardizeItPlateAddressWithEmptyValues() {
        itAddressStructureDto.setItTypePlate("");
        itAddressStructureDto.setItValuePlate("");
        String result = standardizeAddressItUseCase.standardizeItPlateAddress(itAddressStructureDto);
        assertEquals("", result);
    }

    @Test
    @DisplayName("Debería retornar string vacío para placa IT con tipo vacío")
    void standardizeItPlateAddressWithEmptyType() {
        itAddressStructureDto.setItTypePlate("");
        itAddressStructureDto.setItValuePlate(IT_VALUE_PLATE);
        String result = standardizeAddressItUseCase.standardizeItPlateAddress(itAddressStructureDto);
        assertEquals("", result);
    }

    @Test
    @DisplayName("Debería retornar string vacío para placa IT con valor vacío")
    void standardizeItPlateAddressWithEmptyValue() {
        itAddressStructureDto.setItTypePlate("MANZANA-CASA");
        itAddressStructureDto.setItValuePlate("");
        String result = standardizeAddressItUseCase.standardizeItPlateAddress(itAddressStructureDto);
        assertEquals("", result);
    }

}