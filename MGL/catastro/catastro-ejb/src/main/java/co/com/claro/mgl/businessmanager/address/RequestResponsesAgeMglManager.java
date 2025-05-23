/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.RequestResponsesAgeMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RequestResponsesAgeMgl;

/**
 *
 * @author bocanegravm
 */
public class RequestResponsesAgeMglManager {

    /**
     * Crea un RequestResponsesAgeMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param requestResponsesAgeMgl
     * @return RequestResponsesAgeMgl creado en el repositorio
     * @throws ApplicationException
     */
    public RequestResponsesAgeMgl create(RequestResponsesAgeMgl requestResponsesAgeMgl)
            throws ApplicationException {

        RequestResponsesAgeMglDaoImpl dao = new RequestResponsesAgeMglDaoImpl();
        return dao.create(requestResponsesAgeMgl);
    }

}
