package co.com.claro.mer.parametro.dao;

import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;

import java.util.List;
import java.util.Optional;

/**
 * Procesos asociados a operaciones con parámetros de la aplicación (tabla MGL_PARAMETROS)
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/07/10
 */
public interface IParametroDao {

    /**
     * Busca un parámetro a partir de su acrónimo.
     *
     * @param acronym {@link String} Acrónimo del parámetro a buscar.
     * @return {@link String} Retorna el valor del parámetro.
     * @throws ApplicationException Excepción que se genera cuando ocurre un error al buscar el parámetro
     * @author Gildardo Mora
     */
    String findParameterValueByAcronym(String acronym) throws ApplicationException;

    /**
     * Realiza la búsqueda de un parámetro a partir de su acrónimo.
     *
     * @param acronimoEnum {@link ParametrosMerEnum} Enum del parámetro a buscar.
     * @return {@link String} Retorna el valor del parámetro.
     * @throws ApplicationException Excepción que se genera cuando ocurre un error al buscar el parámetro.
     * @author Gildardo Mora
     */
    String findParameterValueByAcronym(ParametrosMerEnum acronimoEnum) throws ApplicationException;

    /**
     * Busca un parámetro a partir de su acrónimo.
     *
     * @param acronym {@link String} Acrónimo del parámetro a buscar.
     * @return {@link ParametrosMgl} Retorna el parámetro consultado.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    Optional<ParametrosMgl> findParameterByAcronym(String acronym) throws ApplicationException;

    /**
     * Realiza la consulta de todos los parámetros existentes
     *
     * @return {@link List <ParametrosMgl>} Retorna la lista de parámetros encontrados.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    List<ParametrosMgl> findAllParameters() throws ApplicationException;

    /**
     * Realiza la búsqueda de parámetros por el tipo especificado.
     *
     * @param parameterType {@link String} Tipo de parámetro
     * @return {@link List<ParametrosMgl>} Retorna la lista de parámetros encontrados.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    List<ParametrosMgl> findParametersByType(String parameterType) throws ApplicationException;

    /**
     * Realiza la búsqueda de los parámetros que coincidan el acrónimo recibido.
     *
     * @param acronym {@link String} Acrónimo para filtrar resultados
     * @return {@link List<ParametrosMgl>} Retorna la lista de parámetros encontrados
     * @throws ApplicationException Excepción en caso de error en la consulta
     * @author Gildardo Mora
     */
    List<ParametrosMgl> findParametersByAcronymLike(String acronym) throws ApplicationException;

    /**
     * Realiza la búsqueda de parámetros por el acrónimo y el tipo
     *
     * @param acronym   {@link String} Acrónimo para aplicar el filtro de búsqueda
     * @param typeParam {@link String} Tipo de parámetro para aplicar el filtro de búsqueda
     * @return {@link List<ParametrosMgl>} Retorna la lista de parámetros encontrados.
     * @throws ApplicationException Excepción en caso de error en la consulta
     */
    List<ParametrosMgl> findParametersByAcronymAndType(String acronym, String typeParam) throws ApplicationException;

    /**
     * Crea el registro de un parámetro en la base de datos.
     *
     * @param parameter {@link ParametrosMgl} Parámetro a registrar.
     * @return {@code boolean} Retorna true si fue creado el parámetro
     * @throws ApplicationException Excepción en caso de error al crear el parámetro
     * @author Gildardo Mora
     */
    boolean createParameter(ParametrosMgl parameter) throws ApplicationException;

    /**
     * Realiza la actualización de un parámetro
     *
     * @param parameter {@link ParametrosMgl} Parámetro a actualizar.
     * @return {@link Optional<ParametrosMgl>} Retorna el parámetro actualizado
     * @throws ApplicationException Excepción en caso de error al actualizar un parámetro
     * @author Gildardo Mora
     */
    Optional<ParametrosMgl> updateParameter(ParametrosMgl parameter) throws ApplicationException;

    /**
     * Realiza la eliminación de un parámetro
     *
     * @param parameter {@link ParametrosMgl} Parámetro a eliminar.
     * @return {@code boolean} Retorna true si fue eliminado el parámetro
     * @author Gildardo Mora
     */
    boolean deleteParameter(ParametrosMgl parameter) throws ApplicationException;

}
