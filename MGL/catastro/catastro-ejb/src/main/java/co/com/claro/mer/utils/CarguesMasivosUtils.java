package co.com.claro.mer.utils;

import co.com.claro.mer.dtos.sp.cursors.CamposFiltrosDto;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.commons.io.FilenameUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static co.com.claro.mer.utils.constants.ConstantsCargueMasivo.*;

/**
 * @author Manuel Hernández Rivas
 * @version 1.0, 2024/12/06
 */
public class CarguesMasivosUtils {
    private static String[] dividirNombreArchivo(String nombreArchivo, String validatePattern) {
        String baseName = FilenameUtils.removeExtension(nombreArchivo);

        Pattern pattern = Pattern.compile(validatePattern);
        Matcher matcher = pattern.matcher(baseName);

        if (matcher.find()) {
            String datePart = baseName.substring(matcher.start());
            String namePart = baseName.substring(0, matcher.start());
            return new String[]{namePart, datePart};
        }else {
            return null;
        }
    }

    public static boolean validarNombreArchivo(String nombreArchivo, String estructuraNombre) {
        String[] partesNombre = dividirNombreArchivo(nombreArchivo, DATE_PATTERN);
        String[] partesEstructura = dividirNombreArchivo(estructuraNombre, DATE_FORMAT_NOMBRE_PLANTILLA_BASE);

        if (partesNombre == null || partesEstructura == null) {
            return false;
        }

        partesEstructura[1] = StringToolUtils.convertirCaracteresMayusculas(partesEstructura[1], 'h');
        partesEstructura[1] = StringToolUtils.reemplazarCaracteres(partesEstructura[1], 'a', 'y');

        return partesNombre[0].equals(partesEstructura[0]) &&
                DateToolUtils.validarFormatoFecha(partesNombre[1], partesEstructura[1]);
    }

    /**
     * genera las cadenas de filtro para los reportes generados en cargues masivos
     * @param campoFilros
     * @return Map con la cadena de filtro logico y legible para los reportes
     *@author Manuel Hernández Rivas
     */
    public static Map<String, String> generarCadenaFilro(CamposFiltrosDto campoFilros) throws ApplicationException {
        StringBuilder cadenaFiltro = new StringBuilder();
        StringBuilder cadenaLegible= new StringBuilder();

        switch (Optional.ofNullable(campoFilros.getHtml_e()).orElse("").toUpperCase()){
            case TIPO_SELECCION:
                cadenaFiltro.append(String.format("%s %s '%s';", campoFilros.getColumna(), campoFilros.getOperador(), campoFilros.getValor()));

                String nombreElemento = campoFilros.getListaItemsFIltro().stream().filter(
                        item -> item.getIdItem().equals(new BigDecimal(campoFilros.getValor())))
                        .findFirst().orElseThrow(()-> new ApplicationException("No se encontro el elemento seleccionado")).getNombreItem();

                cadenaLegible.append(String.format("%s %s '%s'; ", campoFilros.getNombreColumna(), campoFilros.getOperador(), nombreElemento));
                break;
            case TIPO_TEXTO:
                cadenaFiltro.append(String.format("%s %s '%s';", campoFilros.getColumna(), campoFilros.getOperador(), campoFilros.getValor()));
                cadenaLegible.append(String.format("%s %s '%s'; ", campoFilros.getNombreColumna(), campoFilros.getOperador(), campoFilros.getValor()));
                break;
            case TIPO_FECHA:
                cadenaFiltro.append(String.format("%s %s TO_DATE('%s','%s');", campoFilros.getColumna(), campoFilros.getOperador(), DateToolUtils.formatoFechaDate(campoFilros.getFecha(), DATE_FORMAT_FILTROS_FECHA), DATE_FORMAT_FILTROS_FECHA));
                cadenaLegible.append(String.format("%s %s '%s'; ", campoFilros.getNombreColumna(), campoFilros.getOperador(), DateToolUtils.formatoFechaDate(campoFilros.getFecha(), DATE_FORMAT_FILTROS_FECHA)));
                break;
        }
        //mapa con las cadenas de filtro y legible
        Map<String, String> mapFiltros = new HashMap<>();
        mapFiltros.put(FILTRO_LOGICO, cadenaFiltro.toString());
        mapFiltros.put(FILTRO_LEGIBLE, cadenaLegible.toString());

        return mapFiltros;

    }

}
