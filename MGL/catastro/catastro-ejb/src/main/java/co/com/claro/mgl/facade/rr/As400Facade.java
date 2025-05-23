/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.facade.rr;

import co.com.claro.mgl.businessmanager.address.rr.As400Manager;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author becerraarmr
 */
@Stateless
public class As400Facade implements As400FacadeLocal {

    /**
     * manager de consulta en la as400;
     */
    private final As400Manager as400Manager;

    /**
     * Crear la instancia
     * <p>
     * Crea una instancia
     *
     * @author becerraarmr
     */
    public As400Facade() {
        as400Manager = new As400Manager();
    }

    /**
     * Buscar la data cuenta matriz
     *
     * Busca en la base de datos la data de la cuenta matriz
     *
     * @author becerraarmr
     * @version 2017 revisión 1.0
     *
     * @param cuentaMatriz
     * @return un Map con los valores consultados
     */
    @Override
    public Map<String, String> findDataCuentaMatriz(String cuentaMatriz) {
        return as400Manager.findDataCuentaMatriz(cuentaMatriz);
    }
}
