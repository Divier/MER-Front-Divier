/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.facade;

import co.com.claro.cmas400.ejb.manager.CmOrdenTrabajoManager;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaDealer;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionVt;
import co.com.claro.cmas400.ejb.request.RequestDataNotasOtCmSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataNotasOtCuentaMatriz;
import co.com.claro.cmas400.ejb.request.RequestDataOtEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataOtSubEdificio;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaDealerList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionVtList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasOtCmSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasOtCuentaMatrizList;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseOtSubEdificiosList;
import javax.ejb.Stateless;

/**
 *
 * @author Alquiler
 */
@Stateless
public class OrdenTrabajoFacade implements OrdenTrabajoFacadeLocal {

    private final CmOrdenTrabajoManager cmOrdenTrabajo = new CmOrdenTrabajoManager();
    
    /* Informacion VT */
    @Override
    public ResponseInformacionVtList informacionVtQuery(
            RequestDataInformacionVt alimentacion) {
        return cmOrdenTrabajo.informacionVtQuery(alimentacion);
    }
    
    /* OT Edificio */
    @Override
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(
            RequestDataOtEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoEdificioQuery(alimentacion);
    }
    
    @Override
    public ResponseOtEdificiosList ordenTrabajoEdificioAdd(
            RequestDataOtEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoEdificioAdd(alimentacion);
    }
    
    @Override
    public ResponseOtEdificiosList ordenTrabajoEdificioUpdate(
            RequestDataOtEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoEdificioUpdate(alimentacion);
    }
    
    @Override
    public ResponseOtEdificiosList ordenTrabajoEdificioDelete(
            RequestDataOtEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoEdificioDelete(alimentacion);
    }
    
    /* OT SubEdificio */
    @Override
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioQuery(
            RequestDataOtSubEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoSubEdificioQuery(alimentacion);
    }
    
    @Override
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioAdd(
            RequestDataOtSubEdificio alimentacion){
        return cmOrdenTrabajo.ordenTrabajoSubEdificioAdd(alimentacion);
    }
    
    @Override
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioUpdate(
            RequestDataOtSubEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoSubEdificioUpdate(alimentacion);
    }
    
    @Override
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioDelete(
            RequestDataOtSubEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoSubEdificioDelete(alimentacion);
    }
    
    @Override
    public ResponseConsultaDealerList consultaDealerHelp(
            RequestDataConsultaDealer alimentacion) {
        return cmOrdenTrabajo.consultaDealerHelp(alimentacion);
    }
    
    /* Notas Ot Cuenta Matriz */
    @Override
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizListQuery(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return cmOrdenTrabajo.notasOtCuentaMatrizListQuery(alimentacion);
    }
    
    @Override
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizDescripcionQuery(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return cmOrdenTrabajo.notasOtCuentaMatrizDescripcionQuery(alimentacion);
    }
    
    @Override
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizAdd(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return cmOrdenTrabajo.notasOtCuentaMatrizAdd(alimentacion);
    }
    
    @Override
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizUpdate(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return cmOrdenTrabajo.notasOtCuentaMatrizUpdate(alimentacion);
    }
    
    /* Notas Ot Cuenta Matriz SubEdificio */
    @Override
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioListQuery(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return cmOrdenTrabajo.notasOtCmSubEdificioListQuery(alimentacion);
    }
    
    @Override
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioDescripcionQuery(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return cmOrdenTrabajo.notasOtCmSubEdificioDescripcionQuery(alimentacion);
    }
    
    @Override
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioAdd(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return cmOrdenTrabajo.notasOtCmSubEdificioAdd(alimentacion);
    }
    
    @Override
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioUpdate(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return cmOrdenTrabajo.notasOtCmSubEdificioUpdate(alimentacion);
    }
}
