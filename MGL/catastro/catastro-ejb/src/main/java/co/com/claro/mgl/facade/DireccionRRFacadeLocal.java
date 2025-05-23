/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import java.math.BigDecimal;

/**
 * Expone la funcionalidad de la Estadarizacion de la Direccion. Permite
 * realizar todo el proceso de estandarizacion de una direccion a formato RR.
 *
 * @author Johnnatan Ortiz
 * @versión 1.00.000
 */
public interface DireccionRRFacadeLocal {

    /**
     * Obtiene el formato RR.Permite obtener el estandar en formtato RR de un
        valor dado.
     *
     * @author Johnnatan Ortiz
     * @param valor valor del cual se necesita obtener el estandar en formato
     * RR.
     * @return Estandar del Valor en formato RR.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    String getFormatoRR(String valor) throws ApplicationException;

    /**
     * Genera el Valor de Numero de Apartamento en formato RR.Permite obtener
        el valor del numero de apartemento en formtato RR, en la direccion se
        deben enviar los niveles 5 y 6 que son los que corresponden al numero de
        apartamento.
     *
     * @author Johnnatan Ortiz
     * @param direccion direccion con los niveles 5 y 6.
     * @return Numero de apartamento en formato RR.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    String generarNumAptoRR(DetalleDireccionEntity direccion) throws ApplicationException;

    /**
     * Genera la direccion formato RR.Permite obtener el valor de la direccion
        en formtato RR.
     *
     * @author Johnnatan Ortiz
     * @param direccion direccion con los niveles 5 y 6.
     * @return Numero de apartamento en formato RR.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    DireccionRREntity generarDirFormatoRR(DetalleDireccionEntity direccion) throws ApplicationException;
    /**
     * Construye la direccion para una Solicitud.Permite crear una direccion
        detallada campo a campo para luego se usada en una solicitud.
     *
     * @author Johnnatan Ortiz
     * @param request request con la informacion necesaria para la creacion de
     * la solicitud
     * @return respuesta con el proceso de creacion de la solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    ResponseConstruccionDireccion construirDireccionSolicitud(
            RequestConstruccionDireccion request) throws ApplicationException;
    
    /**
     * Metodo que retorna la direccion dependiendo del tipo seleccionado + el
     * complemento
     *
     * @param drDirecion
     * @return
     */
    String getDireccion(DrDireccion drDirecion);

  public String traerHeadEnd(String hhpComunidad);
  
   public DireccionRREntity registrarHHPP_Inspira_Independiente(String strNodo, String nodoReal, String usuario,
            String carpeta, String nombreFuncionalidad, String nvlSocioEconomico,
            boolean validarConfiabilidad, String tipoSol,
            String razon, String idUsuario,
            BigDecimal idCentroPobladoGpo, boolean sincronizaRr, String tipoHhppGestion, NodoMgl nodoMgl) throws ApplicationException;

}
