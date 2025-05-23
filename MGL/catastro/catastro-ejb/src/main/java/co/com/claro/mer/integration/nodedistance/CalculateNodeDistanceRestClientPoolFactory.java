/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.nodedistance;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
class CalculateNodeDistanceRestClientPoolFactory extends BasePooledObjectFactory<CalculateNodeDistanceRestClient> {

    private String endPoint;

    public CalculateNodeDistanceRestClientPoolFactory(String endPoint) {

        this.endPoint = endPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalculateNodeDistanceRestClient create() throws Exception {

        return new CalculateNodeDistanceRestClient(this.endPoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PooledObject<CalculateNodeDistanceRestClient> wrap(CalculateNodeDistanceRestClient obj) {

        return new DefaultPooledObject<>(obj);
    }
}
