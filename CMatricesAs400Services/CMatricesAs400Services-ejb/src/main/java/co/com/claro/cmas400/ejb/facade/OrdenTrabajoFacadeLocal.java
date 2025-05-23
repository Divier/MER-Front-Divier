/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.facade;

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
import javax.ejb.Local;

/**
 *
 * @author Alquiler
 */
@Local
public interface OrdenTrabajoFacadeLocal {

    /* Informacion VT */
    public ResponseInformacionVtList informacionVtQuery(
            RequestDataInformacionVt alimentacion);
    
    /* OT Edificio */
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(
            RequestDataOtEdificio alimentacion);
    
    public ResponseOtEdificiosList ordenTrabajoEdificioAdd(
            RequestDataOtEdificio alimentacion);
    
    public ResponseOtEdificiosList ordenTrabajoEdificioUpdate(
            RequestDataOtEdificio alimentacion);
    
    public ResponseOtEdificiosList ordenTrabajoEdificioDelete(
            RequestDataOtEdificio alimentacion);
    
    /* OT SubEdificio */
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioQuery(
            RequestDataOtSubEdificio alimentacion);
    
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioAdd(
            RequestDataOtSubEdificio alimentacion);
    
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioUpdate(
            RequestDataOtSubEdificio alimentacion);
    
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioDelete(
            RequestDataOtSubEdificio alimentacion);
    
    /* Lista Dealer */
    public ResponseConsultaDealerList consultaDealerHelp(
            RequestDataConsultaDealer alimentacion);
    
    /* Notas Ot Cuenta Matriz */
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizListQuery(
            RequestDataNotasOtCuentaMatriz alimentacion);
    
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizDescripcionQuery(
            RequestDataNotasOtCuentaMatriz alimentacion);
    
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizAdd(
            RequestDataNotasOtCuentaMatriz alimentacion);
    
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizUpdate(
            RequestDataNotasOtCuentaMatriz alimentacion);
    
    /* Notas Ot Cuenta Matriz SubEdificio */
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioListQuery(
            RequestDataNotasOtCmSubEdificio alimentacion);
    
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioDescripcionQuery(
            RequestDataNotasOtCmSubEdificio alimentacion);
    
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioAdd(
            RequestDataNotasOtCmSubEdificio alimentacion);
    
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioUpdate(
            RequestDataNotasOtCmSubEdificio alimentacion);
}
