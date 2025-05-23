/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.nodedistance;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import co.com.claro.mer.integration.nodedistance.dto.CalculateNodeDistanceRestRequest;
import co.com.claro.mer.integration.nodedistance.dto.CalculateNodeDistanceRestResponse;
import java.util.Map;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class CalculateNodeDistanceRestClientPool {

    private static CalculateNodeDistanceRestClientPool instance;
    private static GenericObjectPool<CalculateNodeDistanceRestClient> pool;
    private static Logger logger = LogManager.getLogger();
    private String endPoint;

    /**
     * Garantiza una unica instancia de la clase teniendo en cuenta la
     * concurrencia
     *
     * @param endPoint
     */
    private static synchronized void createInstance(String endPoint) {

        if (instance == null || !endPoint.equals(instance.endPoint)) {
            instance = new CalculateNodeDistanceRestClientPool(endPoint);
            logger.debug("CalculateNodeDistanceRestClientPool instance created");
        }
    }

    /**
     * Devuelve la instancia Singletion de la clase
     *
     * @param endPoint endpoint del servicio.
     * @return
     */
    public static CalculateNodeDistanceRestClientPool getInstance(String endPoint) {

        if (instance == null || !endPoint.equals(instance.endPoint)) {
            createInstance(endPoint);
        }
        return instance;
    }

    /**
     * Realiza el llamado a la operacion calculateNodeDistance
     *
     * @param CalculateNodeDistanceRestRequest
     * @return
     * @throws Exception
     */
    public CalculateNodeDistanceRestResponse calculateNodeDistance(
            CalculateNodeDistanceRestRequest calculateNodeDistanceRestRequest, 
            Map<String, Object> headers) throws Exception {

        CalculateNodeDistanceRestResponse response = null;
        CalculateNodeDistanceRestClient connection = null;
        try {
            connection = borrowObject();
            response = connection.calculateNodeDistance(
                calculateNodeDistanceRestRequest, headers);
        } finally {
            returnObjetToThePool(connection);
        }
        return response;
    }

    /**
     * Constructor
     *
     * @param endPoint
     */
    private CalculateNodeDistanceRestClientPool(String endPoint) {

        this.endPoint = endPoint;
        buildPool();
    }

    /**
     * Pide el objeto <tt>CalculateNodeDistanceRestClient</tt> al Pool
     *
     * @return
     * @throws Exception
     */
    private CalculateNodeDistanceRestClient borrowObject() throws Exception {

        CalculateNodeDistanceRestClient connection = null;
        try {
            connection = pool.borrowObject();
        } catch (Exception e) {
            logger.error("It was not possible to obtain the object from the pool");
            throw e;
        }
        return connection;
    }

    /**
     * Devuelve el objeto especificado al pool
     *
     * @param object
     */
    private void returnObjetToThePool(CalculateNodeDistanceRestClient object) {

        if (null != object) {
            pool.returnObject(object);
        }
    }

    private void buildPool() {

        GenericObjectPoolConfig<CalculateNodeDistanceRestClient> poolConfig = getPoolConfigDefault();
        logger.debug("CalculateNodeDistanceRestClientPool config. [minIdle=" + poolConfig.getMinIdle() + ", maxIdle="
                + poolConfig.getMaxIdle() + ", maxTotal=" + poolConfig.getMaxTotal(), "]");
        pool = new GenericObjectPool<>(new CalculateNodeDistanceRestClientPoolFactory(endPoint), poolConfig);
    }

    private GenericObjectPoolConfig<CalculateNodeDistanceRestClient> getPoolConfigDefault() {

        GenericObjectPoolConfig<CalculateNodeDistanceRestClient> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMinIdle(0);
        poolConfig.setMaxIdle(-1);
        poolConfig.setMaxTotal(-1);
        return poolConfig;
    }
}
