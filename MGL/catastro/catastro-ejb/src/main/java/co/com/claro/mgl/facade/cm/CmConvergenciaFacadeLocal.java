/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.ws.cm.request.RequestViabilidadTecnicaVenta;
import co.com.claro.mgl.ws.cm.response.ResponseViabilidadTecnicaVenta;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface CmConvergenciaFacadeLocal {

    ResponseViabilidadTecnicaVenta viabilidadTecnicaVenta(
            RequestViabilidadTecnicaVenta viabilidadTecnicaVenta) throws ApplicationException;

    public DireccionRREntity convertirDireccionStringADrDireccion(
            String direccion, String comunidad,String barrio) throws ApplicationException, ApplicationException;
}
