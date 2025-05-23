package co.com.claro.mgl.utils;

import co.com.claro.mer.utils.enums.SchemesMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitario para manejo de EntityManager al usar Procedimientos Almacenados
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/12/13
 */
public class EntityManagerUtils {

    public static final Logger LOGGER = LogManager.getLogger(EntityManagerUtils.class);
    private static final Map<String, EntityManagerFactory> entityManagerFactoryMap = new HashMap<>();

    private EntityManagerUtils() {
        //impedir instancias invalidas
    }

    /**
     * Genera la conexión con la unidad de persistencia y otorga el entityManager para ejecutar
     * procedimientos almacenados sobre el esquema MGL_SCHEME en MER
     *
     * @param clase Nombre de la clase que invoca al entityManager
     * @return {@link EntityManager} Retorna el entityManager
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    public static EntityManager getEntityManager(String clase) throws ApplicationException {
        return getEntityManager(SchemesMerEnum.MGL_SCHEME, clase);
    }

    /**
     * Genera la conexión con la unidad de persistencia y otorga el entityManager para ejecutar procedimientos almacenados,
     * sobre el esquema MGL_SCHEME en MER.
     *
     * @param clase {@link <T>} Nombre de la clase que invoca al entityManager
     * @return {@link EntityManager} Retorna el entityManager
     * @throws ApplicationException {@link ApplicationException} Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    public static <T> EntityManager getEntityManager(Class<T> clase) throws ApplicationException {
        return getEntityManager(SchemesMerEnum.MGL_SCHEME, clase.getName());
    }

    /**
     * Genera la conexión con la unidad de persistencia y otorga el entityManager para ejecutar procedimientos almacenados,
     * sobre el esquema recibido, y su unidad de persistencia asociada en MER.
     *
     * @param scheme {@link SchemesMerEnum} Esquema de persistencia.
     * @param clase {@link <T>} Nombre de la clase que invoca al entityManager
     * @return {@link EntityManager} Retorna el entityManager
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    public static <T> EntityManager getEntityManager(SchemesMerEnum scheme, Class<T> clase) throws ApplicationException {
        return getEntityManager(scheme, clase.getName());
    }

    /**
     * Genera la conexión con la unidad de persistencia y otorga el entityManager para ejecutar
     * procedimientos almacenados a partir del esquema y la unidad de persistencia recibida.
     *
     * @param scheme {@link SchemesMerEnum} Esquema de persistencia.
     * @param clase  Nombre de la clase que invoca al entityManager
     * @return {@code EntityManager} Retorna el entityManager
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private static EntityManager getEntityManager(SchemesMerEnum scheme, String clase) throws ApplicationException {
        try {
            if (StringUtils.isBlank(clase)) {
                throw new ApplicationException("El nombre de la clase no puede ser nulo");
            }

            String unidadPersistencia = scheme.getUnidadPersistencia();
            initializePersistenceUnit(unidadPersistencia);
            EntityManagerFactory entityManagerFactory = entityManagerFactoryMap.get(unidadPersistencia);

            if (entityManagerFactory == null) {
                throw new ApplicationException("La unidad de persistencia '" + unidadPersistencia + "' no está inicializada");
            }

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getEntityManagerFactory().getCache().evictAll();
            return entityManager;
        } catch (ApplicationException ae) {
            String msgError = "Error al inicializar getEntityManager llamado desde " + clase + " : " + ae.getMessage();
            LOGGER.error(msgError, ae);
            throw new ApplicationException(msgError);
        } catch (Exception e) {
            String msj = "Error al inicializar getEntityManager llamado desde " + clase + " : " + e.getMessage();
            LOGGER.error(msj, e);
            throw new ApplicationException("Excp: " + msj);
        }
    }

    /**

     * verifica y agrega la unidad de persistencia a la lista de persistencias de la aplicación.
     *
     * @param persistenceUnit Nombre de la unidad de persistencia
     * @author Gildardo Mora
     */
     private static synchronized void initializePersistenceUnit(String persistenceUnit) {
        if (!entityManagerFactoryMap.containsKey(persistenceUnit)) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
            entityManagerFactoryMap.put(persistenceUnit, entityManagerFactory);
        }
    }
}