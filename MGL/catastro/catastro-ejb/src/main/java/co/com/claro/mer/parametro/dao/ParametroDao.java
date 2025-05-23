package co.com.claro.mer.parametro.dao;

import co.com.claro.mer.dtos.request.procedure.*;
import co.com.claro.mer.dtos.response.procedure.CreateParameterResponseDto;
import co.com.claro.mer.dtos.response.procedure.DeleteParameterResponseDto;
import co.com.claro.mer.dtos.response.procedure.FindParameterResponseDto;
import co.com.claro.mer.dtos.response.procedure.FindParameterValueByAcronymResponseDto;
import co.com.claro.mer.dtos.sp.cursors.ParameterResponseDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static co.com.claro.mer.constants.StoredProcedureNamesConstants.*;

/**
 * Gestiona las operaciones CRUD sobre los parámetros de la aplicación MER,
 * de la tabla MGL_PARAMETROS en la BD.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/07/10
 */
@Stateless
@Log4j2
public class ParametroDao implements IParametroDao {

    /**
     * Busca un parámetro a partir de su acrónimo.
     *
     * @param acronym {@link String} Acrónimo del parámetro a buscar.
     * @return {@link String} Retorna el valor del parámetro.
     * @throws ApplicationException Excepción que se genera cuando ocurre un error al buscar el parámetro
     * @author Gildardo Mora
     */
    @Override
    public String findParameterValueByAcronym(String acronym) throws ApplicationException {
        try {
            FindParameterValueByAcronymRequestDto request = new FindParameterValueByAcronymRequestDto();
            request.setAcronym(acronym);
            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_CONSULTA_VALOR_X_ACRONIMO_SP);
            spUtil.addRequestData(request);
            FindParameterValueByAcronymResponseDto response = spUtil.executeStoredProcedure(FindParameterValueByAcronymResponseDto.class);

            if (response.getCode() == null || response.getCode() != 0) {
                String msgError = String.format("No se encontró el parámetro con el acrónimo %s : %s", acronym, response.getResult());
                throw new ApplicationException(msgError);
            }

            return response.getResponse();
        } catch (ApplicationException ae) {
            String msgError = String.format("Error al buscar el parámetro con el acrónimo %s : %s", acronym, ae.getMessage());
            LOGGER.error(msgError);
            throw ae;
        } catch (Exception ae) {
            String msgError = "Ocurrió un error al buscar el parámetro con el acrónimo " + acronym;
            LOGGER.error(msgError, ae.getMessage());
            throw new ApplicationException(msgError, ae);
        }
    }

    /**
     * Realiza la búsqueda de un parámetro a partir de su acrónimo.
     *
     * @param acronimoEnum {@link ParametrosMerEnum} Enum del parámetro a buscar.
     * @return {@link String} Retorna el valor del parámetro.
     * @throws ApplicationException Excepción que se genera cuando ocurre un error al buscar el parámetro.
     * @author Gildardo Mora
     */
    @Override
    public String findParameterValueByAcronym(ParametrosMerEnum acronimoEnum) throws ApplicationException {
        return findParameterValueByAcronym(acronimoEnum.getAcronimo());
    }

    /**
     * Busca un parámetro a partir de su acrónimo.
     *
     * @param acronym {@link String} Acrónimo del parámetro a buscar.
     * @return {@link ParametrosMgl} Retorna el parámetro consultado.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    @Override
    public Optional<ParametrosMgl> findParameterByAcronym(String acronym) throws ApplicationException {
        validateAcronym(acronym);

        try {
            FindParameterValueByAcronymRequestDto request = new FindParameterValueByAcronymRequestDto();
            request.setAcronym(acronym.toUpperCase());
            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_CONSULTA_X_ACRONIMO_SP);
            spUtil.addRequestData(request);
            FindParameterResponseDto response = spUtil.executeStoredProcedure(FindParameterResponseDto.class);

            if (response.getCode() == null || response.getCode() != 0) {
                String msgError = String.format("No se encontró el parámetro con el acrónimo %s : %s", acronym, response.getResult());
                throw new ApplicationException(msgError);
            }

            return Optional.ofNullable(response.getCursor())
                    .flatMap(cursor -> cursor.stream().findFirst())
                    .map(mapperParametroMgl);
        } catch (ApplicationException e) {
            String msgError = "Error al buscar el parámetro con el acrónimo" + acronym;
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al buscar el parámetro con el acrónimo " + acronym;
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Valida que el acrónimo no sea nulo o vacío
     *
     * @param acronym {@link String} Acrónimo a validar
     * @throws ApplicationException Excepción en caso de que el acrónimo sea nulo o vacío
     * @author Gildardo Mora
     */
    private void validateAcronym(String acronym) throws ApplicationException {
        if (StringUtils.isBlank(acronym)) {
            String msg = "No se recibió parámetro valido para realizar la búsqueda";
            LOGGER.warn(msg);
            throw new ApplicationException(msg);
        }
    }

    /**
     * Realiza la consulta de todos los parámetros existentes
     *
     * @return {@link List<ParametrosMgl>} Retorna la lista de parámetros encontrados.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    @Override
    public List<ParametrosMgl> findAllParameters() throws ApplicationException {
        try {
            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_CONSULTA_TODO_SP);
            FindParameterResponseDto responseParameters = spUtil.executeStoredProcedure(FindParameterResponseDto.class);

            if (responseParameters.getCode() == null || responseParameters.getCode() != 0) {
                String msgError = String.format("No se encontraron parámetros : %s", responseParameters.getResult());
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            return mapResponseToOrderedList.apply(responseParameters);
        } catch (ApplicationException e) {
            String msgError = "Error al consultar los parámetros";
            LOGGER.error(msgError, e);
            throw e;
        } catch (Exception e) {
            String msgError = "Ocurrió un error al consultar los parámetros";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Realiza la búsqueda de parámetros por el tipo especificado.
     *
     * @param parameterType {@link String} Tipo de parámetro
     * @return {@link List<ParametrosMgl>} Retorna la lista de parámetros encontrados.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    @Override
    public List<ParametrosMgl> findParametersByType(String parameterType) throws ApplicationException {
        try {
            FindParameterValueByTypeRequestDto request = new FindParameterValueByTypeRequestDto();
            request.setType(parameterType);
            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_CONSULTA_X_TPO_PARAMETRO_SP);
            spUtil.addRequestData(request);
            FindParameterResponseDto response = spUtil.executeStoredProcedure(FindParameterResponseDto.class);

            if (response.getCode() == null || response.getCode() != 0) {
                String msgError = String.format("No se encontraron parámetros : %s", response.getResult());
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            return mapResponseToOrderedList.apply(response);
        } catch (ApplicationException e) {
            String msgError = "Ocurrió un error al buscar los parámetros por tipo: " + parameterType;
            LOGGER.error(msgError, e);
            throw e;
        } catch (Exception e) {
            String msgError = "Ocurrió un error al buscar los parámetros por tipo: " + parameterType;
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Realiza la búsqueda de los parámetros que coincidan el acrónimo recibido.
     *
     * @param acronym {@link String} Acrónimo para filtrar resultados
     * @return {@link List<ParametrosMgl>} Retorna la lista de parámetros encontrados
     * @throws ApplicationException Excepción en caso de error en la consulta
     * @author Gildardo Mora
     */
    @Override
    public List<ParametrosMgl> findParametersByAcronymLike(String acronym) throws ApplicationException {
        validateAcronym(acronym);

        try {
            FindParameterValueByAcronymRequestDto request = new FindParameterValueByAcronymRequestDto();
            request.setAcronym(acronym.toUpperCase());
            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_CONSULTA_X_LIKE_ACRNMO_SP);
            spUtil.addRequestData(request);
            FindParameterResponseDto response = spUtil.executeStoredProcedure(FindParameterResponseDto.class);

            if (response.getCode() == null || response.getCode() != 0) {
                String msgError = String.format("No se encontraron parámetros por acrónimo %s : %s", acronym, response.getResult());
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            return mapResponseToOrderedList.apply(response);
        } catch (ApplicationException ae) {
            String msgError = "Error al buscar los parámetros por acrónimo " + acronym;
            LOGGER.error(msgError, ae);
            throw new ApplicationException(msgError, ae);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al buscar los parámetros por acrónimo: " + acronym;
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Crea el registro de un parámetro en la base de datos.
     *
     * @param parameter {@link ParametrosMgl} Parámetro a registrar.
     * @return {@code boolean} Retorna true si fue creado el parámetro
     * @throws ApplicationException Excepción en caso de error al crear el parámetro
     * @author Gildardo Mora
     */
    @Override
    public boolean createParameter(ParametrosMgl parameter) throws ApplicationException {
        if (Objects.isNull(parameter) || StringUtils.isBlank(parameter.getParAcronimo())) {
            throw new ApplicationException("El acrónimo del parámetro no puede estar vacío");
        }

        try {
            CreateParameterRequestDto request = new CreateParameterRequestDto();
            request.setAcronym(parameter.getParAcronimo());
            request.setValue(parameter.getParValor());
            request.setDescription(parameter.getParDescripcion());
            request.setParameterType(parameter.getParTipoParametro());
            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_INSERTA_REGISTRO_SP);
            spUtil.addRequestData(request);
            CreateParameterResponseDto response = spUtil.executeStoredProcedure(CreateParameterResponseDto.class);

            if (response.getCode() == null || response.getCode() != 0) {
                String msgError = String.format("No se insertó el parámetro %s : %s", parameter.getParAcronimo(), response.getResult());
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            return true;

        } catch (ApplicationException e) {
            String msgError = "Error al crear el parámetro " + parameter.getParAcronimo();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al crear el parámetro " + parameter.getParAcronimo();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Realiza la actualización de un parámetro
     *
     * @param parameter {@link ParametrosMgl} Parámetro a actualizar.
     * @return {@link Optional<ParametrosMgl>} Retorna el parámetro actualizado
     * @throws ApplicationException Excepción en caso de error al actualizar un parámetro
     * @author Gildardo Mora
     */
    @Override
    public Optional<ParametrosMgl> updateParameter(ParametrosMgl parameter) throws ApplicationException {
        try {
            UpdateParameterRequestDto request = new UpdateParameterRequestDto();
            request.setAcronym(parameter.getParAcronimo());
            request.setValue(parameter.getParValor());
            request.setDescription(parameter.getParDescripcion());
            request.setParameterType(parameter.getParTipoParametro());

            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_ACTUALIZA_REGISTRO_SP);
            spUtil.addRequestData(request);
            FindParameterResponseDto response = spUtil.executeStoredProcedure(FindParameterResponseDto.class);

            if (response.getCode() == null || response.getCode() != 0) {
                String msgError = String.format("No se actualizó el parámetro %s : %s", parameter.getParAcronimo(), response.getResult());
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            return Optional.ofNullable(response.getCursor())
                    .map(List::stream)
                    .orElseGet(Stream::empty)
                    .map(mapperParametroMgl)
                    .findFirst();
        } catch (ApplicationException e) {
            String msgError = "Error al actualizar el parámetro " + parameter.getParAcronimo();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al actualizar el parámetro " + parameter.getParAcronimo();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Realiza la eliminación de un parámetro
     *
     * @param parameter {@link ParametrosMgl} Parámetro a eliminar.
     * @return {@code boolean} Retorna true si fue eliminado el parámetro
     * @author Gildardo Mora
     */
    @Override
    public boolean deleteParameter(ParametrosMgl parameter) throws ApplicationException {
        try {
            FindParameterValueByAcronymRequestDto request = new FindParameterValueByAcronymRequestDto();
            request.setAcronym(parameter.getParAcronimo());
            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_ELIMINA_REGISTRO_SP);
            spUtil.addRequestData(request);
            DeleteParameterResponseDto response = spUtil.executeStoredProcedure(DeleteParameterResponseDto.class);

            if (response.getCode() == null || response.getCode() != 0) {
                String msgError = String.format("No se eliminó el parámetro %s : %s", parameter.getParAcronimo(), response.getResult());
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            return true;
        } catch (ApplicationException e) {
            String msgError = "Error al eliminar el parámetro " + parameter.getParAcronimo();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al eliminar el parámetro " + parameter.getParAcronimo();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Realiza la búsqueda de parámetros por el acrónimo y el tipo
     *
     * @param acronym   {@link String} Acrónimo para aplicar el filtro de búsqueda
     * @param typeParam {@link String} Tipo de parámetro para aplicar el filtro de búsqueda
     * @return {@link List<ParametrosMgl>} Retorna la lista de parámetros encontrados.
     * @throws ApplicationException Excepción en caso de error en la consulta
     */
    @Override
    public List<ParametrosMgl> findParametersByAcronymAndType(String acronym, String typeParam) throws ApplicationException {
        validateAcronym(acronym);

        try {
            FindParameterByAcronymAndTypeRequestDto request = new FindParameterByAcronymAndTypeRequestDto();
            request.setAcronym(acronym.toUpperCase());
            request.setParameterType(typeParam);
            StoredProcedureUtil spUtil = new StoredProcedureUtil(PARAM_CONSULTA_X_ACRONIMO_X_PARAMETRO_SP);
            spUtil.addRequestData(request);
            FindParameterResponseDto response = spUtil.executeStoredProcedure(FindParameterResponseDto.class);

            if (response.getCode() == null || response.getCode() != 0) {
                String msgError = String.format("No se encontró los parámetros por acrónimo %s : %s", acronym, response.getResult());
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

            return mapResponseToOrderedList.apply(response);
        } catch (ApplicationException e) {
            String msgError = "Error al buscar los parámetros por acrónimo " + acronym + " y tipo " + typeParam;
            throw new ApplicationException(msgError, e);
        } catch (Exception e) {
            String msgError = "Ocurrió un error al buscar los parámetros por acrónimo " + acronym + " y tipo " + typeParam;
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Mapea los datos de la respuesta a un objeto de tipo {@link ParametrosMgl}
     */
    private final Function<ParameterResponseDto, ParametrosMgl> mapperParametroMgl =
            data -> {
                ParametrosMgl parametrosMgl = new ParametrosMgl();
                parametrosMgl.setParAcronimo(data.getAcronym());
                parametrosMgl.setParValor(data.getValue());
                parametrosMgl.setParDescripcion(data.getDescription());
                parametrosMgl.setParTipoParametro(data.getParameterType());
                return parametrosMgl;
            };

    /**
     * Mapea la respuesta a una Lista ordenada
     */
    private final Function<FindParameterResponseDto, List<ParametrosMgl>> mapResponseToOrderedList = response ->
            Optional.ofNullable(response)
                    .map(FindParameterResponseDto::getCursor)
                    .map(cursor -> cursor.stream()
                            .map(mapperParametroMgl)
                            .sorted(Comparator.comparing(ParametrosMgl::getParAcronimo))
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());

}
