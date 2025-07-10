/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.ws.usaggeocoverage;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
class UsagGeoCoverageRestClientPoolFactory extends BasePooledObjectFactory<UsagGeoCoverageRestClient> {

    private String endPoint;

    public UsagGeoCoverageRestClientPoolFactory(String endPoint) {

        this.endPoint = endPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsagGeoCoverageRestClient create() throws Exception {

        return new UsagGeoCoverageRestClient(this.endPoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PooledObject<UsagGeoCoverageRestClient> wrap(UsagGeoCoverageRestClient obj) {

        return new DefaultPooledObject<>(obj);
    }
}
