package co.com.claro.mer.utils.constants;

import co.com.claro.mer.utils.CarguesMasivosMensajeria;

import java.math.BigDecimal;

/**
 * Constantes para el manejo de cargues masivos
 * @author Manuel Hernández Rivas
 * @version 1.0, 2024/12/04
 */
public class ConstantsCargueMasivo {

    //impedir instancias de esta clase
    private ConstantsCargueMasivo() {
    }

    public static final String DATE_FORMAT_NOMBRE_PLANTILLA_BASE = "ddmmaaaa_hhmmss";
    public static final String DATE_PATTERN = "\\d{2}\\d{2}\\d{4}_\\d{2}\\d{2}(\\d{2})?";

    public static final String ORIGEN_MASIVOS = "ORIGEN_CARGUES_MASIVOS";

    public static final String FILTRO_LOGICO = "filtroLogico";
    public static final String FILTRO_LEGIBLE = "filtroLegible";
    public static final String DATE_FORMAT_FILTROS_FECHA = "dd/MM/yyyy";
    public static final BigDecimal FILTRO_OBLIGATORIO = BigDecimal.ONE;

    public static final BigDecimal ARCHIVO_DUPLICADO = new BigDecimal(-20008);
    public static final BigDecimal RESULTADO_EXITOSO = new BigDecimal(0);
    public static final String EXTENSION_CSV = "csv";

    public static final String USERNAME = CarguesMasivosMensajeria.CARGUE_MASIVO_MGL_USER_NAME;
    public static final String HOST = CarguesMasivosMensajeria.CARGUE_MASIVO_MGL_HOST;
    public static final int PORT = Integer.parseInt(CarguesMasivosMensajeria.CARGUE_MASIVO_MGL_PORT);
    public static final String PASSWORD = CarguesMasivosMensajeria.CARGUE_MASIVO_MGL_PASSWORD;

    public static final String TIPO_SELECCION = "SELECT";
    public static final String TIPO_TEXTO = "TEXT";
    public static final String TIPO_FECHA = "DATE";
}
