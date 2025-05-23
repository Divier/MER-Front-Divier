package co.com.claro.mgl.utils;

import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Objects;

import static co.com.claro.mgl.utils.ClassUtils.getCurrentMethodName;
import static co.com.claro.mgl.utils.Constant.MGL_DATABASE_SCHEMA;
import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * Utilidad que Consulta el valor del acrónimo recibido, perteneciente a la tabla MGL_PARAMETROS DE MER
 *
 * @author Gildardo Mora
 * @version 1.1, 2022/05/23 Rev. Gildardo Mora
 * @since 1.0, 2021/12/23
 */
public class ParametrosMerUtil {

    private static final Logger LOGGER = LogManager.getLogger(ParametrosMerUtil.class);
    private static final String MGL_CONSULTA_PARAMETRO_MER_SP = "MGL_CONSULTA_PARAMETRO_MER_SP";
    private static final String DOT = ".";
    private static final String V_ACRONIMO = "V_ACRONIMO";
    private static final String V_RESULTADO = "V_RESULTADO";
    private static final String NO_HAY_RESULTADOS = "NO HAY RESULTADOS";

    private ParametrosMerUtil() {
    }

    /**
     * Consulta el valor del parámetro buscado a partir del acrónimo.
     *
     * @param acronimo {@link String} Nombre del acrónimo que se va a buscar en la tabla MGL_PARAMETROS DE MER
     * @return valorParametro {@link String} Valor del parámetro buscado, basándonos en el acrónimo recibido.
     * @throws ApplicationException Excepción de la aplicación
     */
    public static String findValor(String acronimo) throws ApplicationException {
        EntityManager entityManager = null;
        try {
            if (StringUtils.isBlank(acronimo)) {
                throw new ApplicationException("No se recibió un acrónimo valido para consultar");
            }

            entityManager = EntityManagerUtils.getEntityManager("ParametrosMerUtil");
            String valorParametro;
            entityManager.getTransaction().begin();//Inicializa el proceso de la transacción
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(MGL_DATABASE_SCHEMA + DOT
                    + MGL_CONSULTA_PARAMETRO_MER_SP);
            /*se registran los parámetros de entrada y salida del procedimiento*/
            query.registerStoredProcedureParameter(V_ACRONIMO, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(V_RESULTADO, String.class, ParameterMode.OUT);
            /* Se asigna el valor de entrada requerido, al procedimiento*/
            query.setParameter(V_ACRONIMO, acronimo);
            query.execute();//ejecuta el procedimiento
            valorParametro = (String) query.getOutputParameterValue(V_RESULTADO);
            entityManager.getTransaction().commit(); //confirma la transacción

            if (valorParametro.equals(NO_HAY_RESULTADOS)) {
                String msgError = " No se encontró resultados para el parámetro: "
                        + valorParametro + " en : " + getCurrentMethodName(ParametrosMerUtil.class);
                LOGGER.warn(msgError);
                throw new ApplicationException(NO_HAY_RESULTADOS);
            }

            return valorParametro;

        } catch (NoResultException ex) {
            String msgError = "Error ejecutando " + MGL_CONSULTA_PARAMETRO_MER_SP + SPACE + ex.getMessage();
            LOGGER.error(msgError, ex.getMessage());
            throw new ApplicationException(" Error ejecutando el Procedimiento almacenado " + MGL_CONSULTA_PARAMETRO_MER_SP + ": ", ex);

        } catch (Exception e) {
            String msgError = "Error en : " + getCurrentMethodName(ParametrosMerUtil.class) + " : " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);

        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    /**
     * Consulta el valor del parámetro a partir del acrónimo.
     *
     * @param acronimo {@link String} Nombre del acrónimo que se va a buscar en la tabla MGL_PARAMETROS DE MER
     * @return {@link String} Valor del parámetro buscado, basándonos en el acrónimo recibido.
     * @throws ApplicationException {@link ApplicationException} Excepción de la aplicación.
     * @author Gildardo Mora
     */
    public static String findValor(ParametrosMerEnum acronimo) throws ApplicationException {
        return ParametrosMerUtil.findValor(acronimo.getAcronimo());
    }

}
