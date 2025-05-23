package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mer.utils.constants.ConstantsDirecciones;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static co.com.claro.mer.utils.constants.ConstantsDirecciones.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase para pruebas unitarias asociadas a DireccionesValidacionManager
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/10/12
 */
class DireccionesValidacionManagerTest {

    private static final String MSG_EL_TIPO_DE_NIVEL_CASA_NO_DEBE_LLEVAR_COMPLEMENTO = "El tipo de nivel CASA  no debe llevar complemento.";
    public static final String DIRECCION = "direccion";
    public static final String IDENTIFICADOR_APP = "identificadorApp";
    public static final String TIPO_NIVEL = "tipoNivel";
    public static final String MAP_APARTAMENTO = "apartamento";
    @InjectMocks
    DireccionesValidacionManager direccionesValidacionManager = new DireccionesValidacionManager();
    public static final String B_BI = "@B_BI";
    private DrDireccion direccion;
    private final String APARTAMENTO = "2";

    /**
     * Realiza la validación de los campos para construir la dirección apartamento.
     *
     * @param direccion            {@link DrDireccion}
     * @param identificadorApp     {@link String}
     * @param tipoNivelApartamento {@link String}
     * @param apartamento          {@link String}
     * @return Retorna true cuando la validación cumple las condiciones.
     */
    private boolean validarConstruccionApartamentoBiDireccional(DrDireccion direccion, String identificadorApp,
            String tipoNivelApartamento, String apartamento) throws ApplicationException {
        return direccionesValidacionManager.validarConstruccionApartamentoBiDireccional(
                direccion, identificadorApp, tipoNivelApartamento, apartamento);
    }

    /**
     * Realiza la validación de los campos para construir la dirección apartamento, verificando el mensaje de respuesta.
     *
     * @param validarDato {@code Map<String, Object>}
     * @param msg         {@link String}
     */
    private void validarConstruccionApartamentoBiDireccional_withApplicationException(Map<String, Object> validarDato, String msg) {
        DrDireccion dir = (DrDireccion) validarDato.getOrDefault(DIRECCION, null);
        String idApp = (String) validarDato.getOrDefault(IDENTIFICADOR_APP, null);
        String tipoNivel = (String) validarDato.getOrDefault(TIPO_NIVEL, "");
        String apartamento = (String) validarDato.getOrDefault(MAP_APARTAMENTO, "");

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> validarConstruccionApartamentoBiDireccional(dir, idApp, tipoNivel, apartamento));
        assertTrue(exception.getMessage().contains(msg));
    }

    /**
     * Verifica si aplica como válida cuando la dirección es nula.
     *
     * @throws ApplicationException Excepción de la App
     */
    @Test
    void validarConstruccionApartamentoBiDireccional_direccionIsNull() throws ApplicationException {
        boolean isValid = direccionesValidacionManager.validarConstruccionApartamentoBiDireccional(
                null, B_BI, TIPO_NIVEL_ES_CASA, APARTAMENTO);
        assertTrue(isValid);
    }

    /**
     * Verifica el mensaje de validación generado cuando el identificadorApp o el nivelApartamento son nulos o vacíos.
     */
    @Test
    void validarConstruccionApartamentoBiDireccional_identificadorAppOrTipoNivelApartamentoAreNullOrEmpty() {
        direccion = new DrDireccion();
        String msg = "No es posible realizar la validación por datos incompletos en la construcción de la dirección";
        Map<String, Object> validarDato = new HashMap<>();
        validarDato.put(DIRECCION, direccion);
        validarDato.put(IDENTIFICADOR_APP, null);
        validarDato.put(TIPO_NIVEL, TIPO_NIVEL_ES_CASA);
        validarDato.put(MAP_APARTAMENTO, APARTAMENTO);
        validarConstruccionApartamentoBiDireccional_withApplicationException(validarDato, msg);
        validarDato.put(IDENTIFICADOR_APP, "");
        validarConstruccionApartamentoBiDireccional_withApplicationException(validarDato, msg);
        validarDato.put(IDENTIFICADOR_APP, B_BI);
        validarDato.put(TIPO_NIVEL, null);
        validarConstruccionApartamentoBiDireccional_withApplicationException(validarDato, msg);
        validarDato.put(TIPO_NIVEL, "");
        validarConstruccionApartamentoBiDireccional_withApplicationException(validarDato, msg);
    }

    /**
     * Verifica cuando el identificadorApp is distinto de la tecnología Bidireccional
     */
    @Test
    void validarConstruccionApartamentoBiDireccional_identificadorAppIsDifferentOfBidireccional() throws ApplicationException {
        direccion = new DrDireccion();
        String identificadorApp = "@otherValue";
        assertTrue(validarConstruccionApartamentoBiDireccional(direccion, identificadorApp, TIPO_NIVEL_ES_CASA, APARTAMENTO));
    }

    /**
     * Verifica el mensaje de validación generado cuando el tipo de nivel apartamento es "APARTAMENTO" y el campo
     * "valorNivel5" etá vacío.
     */
    @Test
    void validarConstruccionApartamentoBiDireccional__tipoNivelApartamentoIsApartamentoAndCpValorNivel5IsEmpty() {
        direccion = new DrDireccion();
        direccion.setCpTipoNivel5(null);
        Map<String, Object> validarDato = new HashMap<>();
        validarDato.put(DIRECCION, direccion);
        validarDato.put(IDENTIFICADOR_APP, B_BI);
        validarDato.put(TIPO_NIVEL, TIPO_NIVEL_ES_APARTAMENTO);
        validarDato.put(MAP_APARTAMENTO, APARTAMENTO);
        String msg = "No es posible agregar este tipo de nivel. Seleccione PISO + APARTAMENTO.";
        validarConstruccionApartamentoBiDireccional_withApplicationException(validarDato, msg);
    }

    /**
     * Verifica el mensaje de validación generado cuando el tipo de nivel apartamento es "INTERIOR" y el campo
     * "valorNivel5" está vacío.
     */
    @Test
    void validarConstruccionApartamentoBiDireccional__tipoNivelApartamentoIsInteriorAndCpValorNivel5IsEmpty() {
        direccion = new DrDireccion();
        direccion.setCpTipoNivel5(null);
        String msg = "No es posible agregar este tipo de nivel. Seleccione PISO + INTERIOR.";
        Map<String, Object> validarDato = new HashMap<>();
        validarDato.put(DIRECCION, direccion);
        validarDato.put(IDENTIFICADOR_APP, B_BI);
        validarDato.put(TIPO_NIVEL, TIPO_NIVEL_ES_INTERIOR);
        validarDato.put(MAP_APARTAMENTO, APARTAMENTO);
        validarConstruccionApartamentoBiDireccional_withApplicationException(validarDato, msg);
    }

    /**
     * Verifica el mensaje de validación generado cuando el tipo de nivel apartamento es "LOCAL" y el campo
     * "valorNivel5" está vacío.
     */
    @Test
    void validarConstruccionApartamentoBiDireccional__tipoNivelApartamentoIsLocalAndCpValorNivel5IsEmpty() {
        direccion = new DrDireccion();
        direccion.setCpTipoNivel5(null);
        String msg = "No es posible agregar este tipo de nivel. Seleccione PISO + LOCAL.";
        Map<String, Object> validarDato = new HashMap<>();
        validarDato.put(DIRECCION, direccion);
        validarDato.put(IDENTIFICADOR_APP, B_BI);
        validarDato.put(TIPO_NIVEL, TIPO_NIVEL_ES_LOCAL);
        validarDato.put(MAP_APARTAMENTO, APARTAMENTO);
        validarConstruccionApartamentoBiDireccional_withApplicationException(validarDato, msg);
    }

    /**
     * Verifica el mensaje de validación generado cuando el tipo de nivel apartamento es "CASA" y el campo
     * "apartamento" no está vacío.
     */
    @Test
    void validarConstruccionApartamentoBiDireccional__tipoNivelApartamentoIsCasaAndApartamentoNotIsEmpty() {
        direccion = new DrDireccion();
        Map<String, Object> validarDato = new HashMap<>();
        validarDato.put(DIRECCION, direccion);
        validarDato.put(IDENTIFICADOR_APP, B_BI);
        validarDato.put(TIPO_NIVEL, TIPO_NIVEL_ES_CASA);
        validarDato.put(MAP_APARTAMENTO, APARTAMENTO);
        validarConstruccionApartamentoBiDireccional_withApplicationException(validarDato, MSG_EL_TIPO_DE_NIVEL_CASA_NO_DEBE_LLEVAR_COMPLEMENTO);
    }

    /* --------------------------------------------- */

    /**
     * Verifica las respuestas de validación para el proceso de validar tecnologias
     * para la construcción de apartamento.
     *
     * @throws ApplicationException Excepción de la App
     */
    @Test
    void validarConstruccionApartamentoPorTecnologias() throws ApplicationException {
        direccion = new DrDireccion();
        String msg = "Error al validar el apartamento. EX000: "
                + "No es posible realizar la validación por datos incompletos en la construcción de la dirección ";

        assertTrue(direccionesValidacionManager.validarConstruccionApartamentoPorTecnologias(null, new ArrayList<>(), new HashMap<>()));
        assertTrue(direccionesValidacionManager.validarConstruccionApartamentoPorTecnologias(direccion, null, new HashMap<>()));

        ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> direccionesValidacionManager.validarConstruccionApartamentoPorTecnologias(direccion, new ArrayList<>(), new HashMap<>()));
        assertEquals(msg, applicationException.getMessage());

        Map<String, String> datosValidarApartamento = new HashMap<>();
        datosValidarApartamento.put(TIPO_NIVEL_APARTAMENTO, "");
        applicationException = assertThrows(ApplicationException.class,
                () -> direccionesValidacionManager.validarConstruccionApartamentoPorTecnologias(direccion, new ArrayList<>(), datosValidarApartamento));
        assertEquals(msg, applicationException.getMessage());

        datosValidarApartamento.put(ConstantsDirecciones.APARTAMENTO, "2");
        datosValidarApartamento.put(ConstantsSolicitudHhpp.CODIGO_BASICA, "BI");
        datosValidarApartamento.put(ConstantsDirecciones.TIPO_NIVEL_APARTAMENTO, "CASA");
        applicationException = assertThrows(ApplicationException.class,
                () -> direccionesValidacionManager.validarConstruccionApartamentoPorTecnologias(
                        direccion, new ArrayList<>(Arrays.asList("BI", "FOG")), datosValidarApartamento));
        assertEquals("Error al validar el apartamento. EX000: "
                + MSG_EL_TIPO_DE_NIVEL_CASA_NO_DEBE_LLEVAR_COMPLEMENTO, applicationException.getMessage());
    }
}