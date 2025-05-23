package co.com.claro.mer.parametro.facade;

import co.com.claro.mer.parametro.dao.IParametroDao;
import co.com.claro.mer.parametro.service_bean.ParametroMerServBean;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import lombok.extern.log4j.Log4j2;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de los casos de uso definidos para los procesos
 * con parámetros.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/07/11
 */
@Stateless
@Log4j2
public class ParametroFacade implements ParametroFacadeLocal {

    @Inject
    private ParametroMerServBean parametroMerServBean;
    @EJB
    private IParametroDao parametroDao;

    /**
     * Busca un parámetro a partir de su acrónimo.
     *
     * @param acronimo {@link String} Enum del acrónimo del parámetro a buscar.
     * @return {@link String} Retorna el valor del parámetro consultado.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    @Override
    public String findParameterValueByAcronym(String acronimo) throws ApplicationException {
        return parametroDao.findParameterValueByAcronym(acronimo);
    }

    /**
     * Busca un parámetro a partir de su acrónimo.
     *
     * @param acronym {@link String} Enum del acrónimo del parámetro a buscar.
     * @return {@link String} Retorna el valor del parámetro consultado.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    @Override
    public String findParameterValueByAcronymInCache(String acronym) throws ApplicationException {
        return parametroMerServBean.findValueByAcronymInCache(acronym);
    }

    /**
     * Busca un parámetro a partir de su acrónimo.
     *
     * @param acronimo {@link ParametrosMerEnum} Enum del acrónimo del parámetro a buscar.
     * @return {@link String} Retorna el valor del parámetro consultado.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    @Override
    public String findParameterValueByAcronym(ParametrosMerEnum acronimo) throws ApplicationException {
        return parametroDao.findParameterValueByAcronym(acronimo);
    }

    /**
     * Busca un parámetro a partir de su acrónimo.
     *
     * @param acronym {@link ParametrosMerEnum} Enum del acrónimo del parámetro a buscar.
     * @return {@link String} Retorna el valor del parámetro consultado.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    @Override
    public String findParameterValueByAcronymInCache(ParametrosMerEnum acronym) throws ApplicationException {
        return parametroMerServBean.findValueByAcronymInCache(acronym);
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
        return parametroDao.findParameterByAcronym(acronym);
    }

    /**
     * Realiza la consulta de todos los parámetros existentes
     *
     * @return {@link List <ParametrosMgl>} Retorna la lista de parámetros encontrados.
     * @throws ApplicationException Excepción en caso de error en la consulta.
     * @author Gildardo Mora
     */
    @Override
    public List<ParametrosMgl> findAllParameters() throws ApplicationException {
        return parametroDao.findAllParameters();
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
        return parametroDao.findParametersByType(parameterType);
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
        return parametroDao.findParametersByAcronymLike(acronym);
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
        return parametroDao.findParametersByAcronymAndType(acronym, typeParam);
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
        return parametroDao.createParameter(parameter);
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
        return parametroDao.updateParameter(parameter);
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
        return parametroDao.deleteParameter(parameter);
    }

}
