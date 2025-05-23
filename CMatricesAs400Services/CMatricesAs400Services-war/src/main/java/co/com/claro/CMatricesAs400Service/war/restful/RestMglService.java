/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.request.RequestDataConstructorasMgl;
import co.com.claro.cmas400.ejb.respons.ResponseConstructorasList;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtCompaniaMglFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author bocanegravm
 */
@Path("RestMglService")
@Stateless
public class RestMglService {

    @EJB
    private CmtCompaniaMglFacadeLocal cmtCompaniaMglFacadeLocal;

    /**
     * Autor: Victor Bocanegra Metodo encargado de consultar los registros de
     * constructoras en la tabla CMT_COMPANIAS.
     *
     * @param request
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("constructorasQueryMgl/")
    public ResponseConstructorasList constructorasQueryMgl(
            RequestDataConstructorasMgl request) throws ApplicationException{
        return cmtCompaniaMglFacadeLocal.constructorasQueryMgl(request);
    }
}
