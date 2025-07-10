/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.ws.usaggeocoverage;

import co.com.claro.mer.integration.ws.usaggecoverage.dto.UsagGeoCoverageRestRequest;
import co.com.claro.mer.integration.ws.usaggecoverage.dto.UsagGeoCoverageRestResponse;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class UsagGeoCoverageRestClientPool {

    private static UsagGeoCoverageRestClientPool instance;
    private static GenericObjectPool<UsagGeoCoverageRestClient> pool;
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
            instance = new UsagGeoCoverageRestClientPool(endPoint);
            logger.debug("CalculateNodeDistanceRestClientPool instance created");
        }
    }

    /**
     * Devuelve la instancia Singletion de la clase
     *
     * @param endPoint endpoint del servicio.
     * @return
     */
    public static UsagGeoCoverageRestClientPool getInstance(String endPoint) {

        if (instance == null || !endPoint.equals(instance.endPoint)) {
            createInstance(endPoint);
        }
        return instance;
    }

    /**
     * Realiza el llamado a la operacion calculateNodeDistance
     *
     * @param usagGeoCoverageRestRequest
     * @param headers
     * @return
     * @throws Exception
     */
    public UsagGeoCoverageRestResponse coordsByNode(
            UsagGeoCoverageRestRequest usagGeoCoverageRestRequest, 
            Map<String, Object> headers) throws Exception {

        UsagGeoCoverageRestResponse response = null;
        UsagGeoCoverageRestClient connection = null;
        try {
            connection = borrowObject();
            response = connection.coordsByNode(
                usagGeoCoverageRestRequest, headers);
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
    private UsagGeoCoverageRestClientPool(String endPoint) {

        this.endPoint = endPoint;
        buildPool();
    }

    /**
     * Pide el objeto <tt>UsagGeoCoverageRestClient</tt> al Pool
     *
     * @return
     * @throws Exception
     */
    private UsagGeoCoverageRestClient borrowObject() throws Exception {

        UsagGeoCoverageRestClient connection = null;
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
    private void returnObjetToThePool(UsagGeoCoverageRestClient object) {

        if (null != object) {
            pool.returnObject(object);
        }
    }

    private void buildPool() {

        GenericObjectPoolConfig<UsagGeoCoverageRestClient> poolConfig = getPoolConfigDefault();
        logger.debug("CalculateNodeDistanceRestClientPool config. [minIdle=" + poolConfig.getMinIdle() + ", maxIdle="
                + poolConfig.getMaxIdle() + ", maxTotal=" + poolConfig.getMaxTotal(), "]");
        pool = new GenericObjectPool<>(new UsagGeoCoverageRestClientPoolFactory(endPoint), poolConfig);
    }

    private GenericObjectPoolConfig<UsagGeoCoverageRestClient> getPoolConfigDefault() {

        GenericObjectPoolConfig<UsagGeoCoverageRestClient> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMinIdle(0);
        poolConfig.setMaxIdle(-1);
        poolConfig.setMaxTotal(-1);
        return poolConfig;
    }
}
