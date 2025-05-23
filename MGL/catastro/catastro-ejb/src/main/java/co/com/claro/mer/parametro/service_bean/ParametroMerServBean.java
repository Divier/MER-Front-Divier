package co.com.claro.mer.parametro.service_bean;


import co.com.claro.mer.parametro.dao.IParametroDao;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static co.com.claro.mer.utils.enums.ParametrosMerEnum.FLAG_STORE_PARAMETERS_IN_CACHE;

/**
 * Gestiona la búsqueda de parámetros de la aplicación durante la sesión del usuario.
 * <p>
 * Administra los parámetros de la aplicación minimizando el número de
 * transacciones hacia la base de datos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/07/10
 */
@Named
@SessionScoped
@Log4j2
public class ParametroMerServBean implements Serializable {

    /**
     * Mapa que almacena en memoria los parámetros de la aplicación.
     */
    private Map<String, String> parameterMap;

    /**
     * Determina si se va a mantener los parámetros de la aplicación en memoria
     * durante la sesión del usuario.
     */
    private boolean flagStoreParametersInCache;

    @EJB
    private IParametroDao parametroDao;

    /* ---------------------------------------------- */
    @PostConstruct
    public void init() {
        parameterMap = new HashMap<>();
        try {
            String storedInCache = parametroDao.findParameterValueByAcronym(FLAG_STORE_PARAMETERS_IN_CACHE);
            if (StringUtils.isNotBlank(storedInCache) && storedInCache.trim().equalsIgnoreCase("1")) {
                flagStoreParametersInCache = true;
            }
        } catch (ApplicationException ae) {
            LOGGER.error("Error al consultar el parámetro FLAG_STORE_PARAMETERS_IN", ae);
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error al momento de consultar el FLAG_STORE_PARAMETERS_IN_CACHE", e);
        }
    }

    /**
     * Busca el valor del parámetro a partir del acrónimo recibido.
     *
     * @param acronimoEnum {@link ParametrosMerEnum} El acrónimo del parámetro a consultar.
     * @return {@link String} Retorna el valor del parámetro consultado.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    public String findValueByAcronymInCache(ParametrosMerEnum acronimoEnum) throws ApplicationException {
        return findValueByAcronymInCache(acronimoEnum.getAcronimo());
    }

    /**
     * Consulta el valor del parámetro en MGL_PARAMETROS a partir del acrónimo recibido.
     *
     * @param acronimo {@link String}
     * @return {@link String} El valor del parámetro a consultar
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    public String findValueByAcronymInCache(String acronimo) throws ApplicationException {
        if (StringUtils.isBlank(acronimo)) {
            String msgWarn = "No se recibió un acrónimo valido para realizar la búsqueda del parámetro";
            LOGGER.warn(msgWarn);
            throw new ApplicationException(msgWarn);
        }

        if (parameterMap.containsKey(acronimo)
                && StringUtils.isNotBlank(parameterMap.get(acronimo))
                && flagStoreParametersInCache) {
            return parameterMap.get(acronimo);
        }

        try {
            String valor = parametroDao.findParameterValueByAcronym(acronimo);
            addParametroToMap(acronimo, valor);
            return parameterMap.get(acronimo);
        } catch (ApplicationException e) {
            LOGGER.error("Error al realizar la búsqueda del parametro {} : {}", acronimo, e.getMessage());
            throw e;
        }
    }

    /**
     * Consulta el parámetro por acrónimo
     *
     * @param acronimo {@link String} El acrónimo del parámetro a consultar
     * @author Gildardo Mora
     */
    private void addParametroToMap(String acronimo, String valor) {
        parameterMap.put(acronimo, valor);
    }

}
