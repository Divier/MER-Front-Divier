package co.com.claro.mer.address.standardize.application.usecases;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mer.address.standardize.domain.models.dto.CkAddressStructureDto;
import co.com.claro.mgl.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para estandarizar direcciones en el formato de calle - carrera.
 *
 * @author Gildardo Mora
 */
class StandardizeAddressCkUseCaseImplTest {

    private static final String CALLE = "CALLE";
    private static final String CARRERA = "CARRERA";
    private static final String BIS = "BIS";
    private static final String NORTE = "NORTE";
    private static final String SUR = "SUR";

    private StandardizeAddressCkUseCaseImpl standardizeAddressCkUseCase;

    @BeforeEach
    void setUp() {
        standardizeAddressCkUseCase = new StandardizeAddressCkUseCaseImpl();
    }

    @Test
    @DisplayName("Debe estandarizar dirección completa correctamente")
    void testStandardizeCompleteAddress() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", "A", "2", BIS, SUR)
                .withGenerativeTrack(CARRERA, "30", "B", "1", BIS, "ESTE", "67-89")
                .build();

        String expected = "CALLE 50 A - 2 BIS SUR CARRERA 30 B - 1 BIS ESTE 67-89";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe estandarizar dirección solo con vía principal")
    void testStandardizeMainTrackOnly() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", null, null, null, null)
                .build();

        String expected = "CALLE 50";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe estandarizar dirección solo con vía generadora")
    void testStandardizeGenerativeTrackOnly() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withGenerativeTrack(null, "30", null, null, null, null, null)
                .build();

        String expected = "# 30";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe manejar BIS con letra en vía principal")
    void testStandardizeBisWithLetterMainTrack() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", null, null, "A", null)
                .build();

        String expected = "CALLE 50 BIS A";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe manejar BIS solo en vía principal")
    void testStandardizeBisOnlyMainTrack() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", null, null, BIS, null)
                .build();

        String expected = "CALLE 50 BIS";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe manejar cuadrantes en vía principal")
    void testStandardizeWithMainTrackQuadrant() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", null, null, null, NORTE)
                .build();

        String expected = "CALLE 50 NORTE";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe manejar letra de codirección")
    void testStandardizeWithCoDirectionLetter() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", null, null, null, null)
                .withGenerativeTrack(CARRERA, "30", null, null, null, null, null)
                .withCoDirectionLetter("D")
                .build();

        String expected = "CALLE 50 CARRERA 30 - D";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe manejar tipo de vía generadora vacía")
    void testStandardizeWithEmptyGenerativeTrackType() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", null, null, null, null)
                .withGenerativeTrack("", "30", null, null, null, null, null)
                .build();

        String expected = "CALLE 50 # 30";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe manejar el caso especial cuando tipo de vía generadora es V_VACIO")
    void testStandardizeWithVEmptyGenerativeTrackType() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", null, null, null, null)
                .withGenerativeTrack(Constant.V_VACIO, "30", null, null, null, null, null)
                .build();

        String expected = "CALLE 50 # 30";
        assertAddressStandardization(expected, addressDto);
    }

    @Test
    @DisplayName("Debe eliminar espacios múltiples")
    void testRemoveMultipleSpaces() {
        CkAddressStructureDto addressDto = createAddressBuilder()
                .withMainTrack(CALLE, "50", "  A  ", null, null, null)
                .build();

        String expected = "CALLE 50 A";
        assertAddressStandardization(expected, addressDto);
    }

    @ParameterizedTest
    @MethodSource("provideAddressesAndExpected")
    @DisplayName("Debe estandarizar direcciones según casos parametrizados")
    void testStandardizeParameterizedCases(CkAddressStructureDto addressDto, String expected) {
        assertAddressStandardization(expected, addressDto);
    }

    private static Stream<Arguments> provideAddressesAndExpected() {
        return Stream.of(
                Arguments.of(
                        new AddressBuilder()
                                .withMainTrack(CALLE, "100", "A", null, null, null)
                                .withGenerativeTrack(CARRERA, "20", null, null, null, null, "1-23")
                                .build(),
                        "CALLE 100 A CARRERA 20 1-23"
                ),
                Arguments.of(
                        new AddressBuilder()
                                .withMainTrack(CARRERA, "7", null, null, BIS, SUR)
                                .withGenerativeTrack("", "15", null, null, null, null, null)
                                .build(),
                        "CARRERA 7 BIS SUR # 15"
                ),
                Arguments.of(
                        new AddressBuilder()
                                .withMainTrack("DIAGONAL", "45", null, "1", null, NORTE)
                                .withGenerativeTrack("TRANSVERSAL", "67", "A", null, null, "OESTE", null)
                                .build(),
                        "DIAGONAL 45 - 1 NORTE TRANSVERSAL 67 A OESTE"
                )
        );
    }

    private void assertAddressStandardization(String expected, CkAddressStructureDto addressDto) {
        String actual = standardizeAddressCkUseCase.standardizeCkAddress(addressDto);
        assertEquals(expected, actual);
    }

    private AddressBuilder createAddressBuilder() {
        return new AddressBuilder();
    }

    private static class AddressBuilder {
        private final CkAddressStructureDto dto;

        public AddressBuilder() {
            this.dto = new CkAddressStructureDto(new AddressComplementDto());
        }

        public AddressBuilder withMainTrack(String type, String number, String firstMod,
                                            String secondMod, String bis, String quadrant) {
            Optional.ofNullable(type).ifPresent(dto::setMainTrackType);
            Optional.ofNullable(number).ifPresent(dto::setNumMainTrack);
            Optional.ofNullable(firstMod).ifPresent(dto::setMainTrackFirstModifier);
            Optional.ofNullable(secondMod).ifPresent(dto::setMainTrackSecondModifier);
            Optional.ofNullable(bis).ifPresent(dto::setBisMainTrack);
            Optional.ofNullable(quadrant).ifPresent(dto::setMainTrackQuadrant);
            return this;
        }

        public AddressBuilder withGenerativeTrack(String type, String number, String firstMod,
                                                  String secondMod, String bis, String quadrant, String plate) {
            Optional.ofNullable(type).ifPresent(dto::setGenerativeTrackType);
            Optional.ofNullable(number).ifPresent(dto::setNumGenerativeTrack);
            Optional.ofNullable(firstMod).ifPresent(dto::setGenerativeTrackFirstModifier);
            Optional.ofNullable(secondMod).ifPresent(dto::setGenerativeTrackSecondModifier);
            Optional.ofNullable(bis).ifPresent(dto::setBisGenerativeTrack);
            Optional.ofNullable(quadrant).ifPresent(dto::setGenerativeTrackQuadrant);
            Optional.ofNullable(plate).ifPresent(dto::setDirectionPlate);
            return this;
        }

        public AddressBuilder withCoDirectionLetter(String letter) {
            Optional.ofNullable(letter).ifPresent(dto::setGenTrackCoDirectionLetter);
            return this;
        }

        public CkAddressStructureDto build() {
            return dto;
        }
    }

}