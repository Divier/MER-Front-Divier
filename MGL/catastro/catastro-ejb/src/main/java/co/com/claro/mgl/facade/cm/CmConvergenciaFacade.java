package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmConvergenciaManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.ws.cm.request.RequestViabilidadTecnicaVenta;
import co.com.claro.mgl.ws.cm.response.ResponseViabilidadTecnicaVenta;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import javax.ejb.Stateless;

/**
 * Facade VConvergencia. Expone la logica de negocio para el manejo de
 *  Convergencia en el repositorio.
 *
 * @author Carlos Villamil - HITSS
 * @version 1.00.000
 */
@Stateless
public class CmConvergenciaFacade implements CmConvergenciaFacadeLocal {    
    private CmConvergenciaManager cmConvergenciaManager;
    @Override
 public ResponseViabilidadTecnicaVenta viabilidadTecnicaVenta(
            RequestViabilidadTecnicaVenta viabilidadTecnicaVenta) throws ApplicationException{
            
     return null;
 }   
    @Override
 public DireccionRREntity convertirDireccionStringADrDireccion(
            String direccion, String comunidad, String barrio) throws ApplicationException, ApplicationException {
       return cmConvergenciaManager.convertirDireccionStringADrDireccion(direccion, comunidad,barrio);
   } 
}
