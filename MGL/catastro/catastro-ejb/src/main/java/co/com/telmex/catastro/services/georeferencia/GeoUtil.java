package co.com.telmex.catastro.services.georeferencia;

import co.com.claro.mgl.dtos.AddressGeoDirDataDto;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.Ubicacion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Utilitario de georeferencia.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/01/10
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GeoUtil {

    /**
     * Predicado que verifica si la ubicación tiene coordenadas inválidas.
     */
    public static final Predicate<Ubicacion> hasInvalidCoordinatesLocation = u ->
            isInvalidCoordinate(u.getUbiLatitud()) || isInvalidCoordinate(u.getUbiLongitud());

    /**
     * Predicado que verifica si la ubicación tiene coordenadas inválidas.
     */
    public static final Predicate<UbicacionMgl> hasInvalidCoordinatesLocationUbi = u ->
            isInvalidCoordinate(u.getUbiLatitud()) || isInvalidCoordinate(u.getUbiLongitud());

    /**
     * Predicado que verifica si la dirección tiene coordenadas válidas.
     */
    public static final Predicate<AddressGeodata> hasValidCoordinatesAddress = ag ->
            isValidCoordinate(ag.getCx()) && isValidCoordinate(ag.getCy());

    /**
     * Predicado que verifica si la dirección tiene coordenadas válidas.
     */
    public static final Predicate<AddressGeoDirDataDto> hasValidCoordinatesAddressGeoDir = ag ->
            isValidCoordinate(ag.getCx()) && isValidCoordinate(ag.getCy());

    /**
     * Predicado que verifica si las coordenadas de la ubicación y la dirección son diferentes.
     */
    public static final BiPredicate<Ubicacion, AddressGeodata> hasDifferentCoordinates = (u, ag) ->
            !hasInvalidCoordinatesLocation.test(u)
            && !u.getUbiLatitud().equalsIgnoreCase(ag.getCy()) || !u.getUbiLongitud().equalsIgnoreCase(ag.getCx());

    /**
     * Predicado que verifica si las coordenadas de la ubicación y la dirección son diferentes.
     */
    public static final BiPredicate<Ubicacion, AddressGeoDirDataDto> hasDifferentCoordinatesGeoDir = (u, ag) ->
            !hasInvalidCoordinatesLocation.test(u)
                    && !u.getUbiLatitud().equalsIgnoreCase(ag.getCy()) || !u.getUbiLongitud().equalsIgnoreCase(ag.getCx());

    /**
     * Predicado que verifica si las coordenadas de la ubicación y la dirección son diferentes.
     */
    public static final BiPredicate<UbicacionMgl, AddressGeoDirDataDto> hasDifferentCoordinatesGeoDirMgl = (u, ag) ->
            !hasInvalidCoordinatesLocationUbi.test(u)
            && !u.getUbiLatitud().equalsIgnoreCase(ag.getCy()) || !u.getUbiLongitud().equalsIgnoreCase(ag.getCx());

    /**
     * Predicado que verifica si las coordenadas de la ubicación y la dirección son diferentes.
     */
    public static final BiPredicate<UbicacionMgl, AddressGeodata> hasDifferentCoordinatesUbiGeoDirMgl = (u, ag) ->
            !hasInvalidCoordinatesLocationUbi.test(u)
                    && !u.getUbiLatitud().equalsIgnoreCase(ag.getCy()) || !u.getUbiLongitud().equalsIgnoreCase(ag.getCx());

    /**
     * Verifica si una coordenada es inválida.
     *
     * @param coordinate la coordenada a verificar
     * @return true si la coordenada es inválida, false en caso contrario
     */
    private static boolean isInvalidCoordinate(String coordinate) {
        return StringUtils.isBlank(coordinate) || "0".equalsIgnoreCase(coordinate);
    }

    /**
     * Verifica si una coordenada es válida.
     *
     * @param coordinate la coordenada a verificar
     * @return true si la coordenada es válida, false en caso contrario
     */
    private static boolean isValidCoordinate(String coordinate) {
        return StringUtils.isNotBlank(coordinate) && !"0".equalsIgnoreCase(coordinate);
    }


}
