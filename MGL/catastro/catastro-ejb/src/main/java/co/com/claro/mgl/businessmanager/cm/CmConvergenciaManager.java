/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.PcmlManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadEstratoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadHhppMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestViabilidadTecnicaVenta;
import co.com.claro.mgl.ws.cm.response.ResponseViabilidadTecnicaVenta;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import java.util.ArrayList;
import net.telmex.pcml.service.Edificio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ADMIN
 */
public class CmConvergenciaManager {
    
    private static final Logger LOGGER = LogManager.getLogger(CmConvergenciaManager.class);

    private PcmlManager pcmlManager = new PcmlManager();
    private CmtConvergenciaViabilidadEstratoMglManager viabilidadEstratoMglManager =
            new CmtConvergenciaViabilidadEstratoMglManager();
    private CmtConvergenciaViabilidadHhppMglManager viabilidadHhppMgManager =
            new CmtConvergenciaViabilidadHhppMglManager();
    private CmtValidadorDireccionesManager validadorDireccionesManager=
            new CmtValidadorDireccionesManager();

    public ResponseViabilidadTecnicaVenta viabilidadTecnicaVenta(
            RequestViabilidadTecnicaVenta viabilidadTecnicaVenta) throws ApplicationException {

        UnidadStructPcml structPcmlEncontrada = null;
        ResponseViabilidadTecnicaVenta responseViabilidadTecnicaVenta =
                new ResponseViabilidadTecnicaVenta();

        if (viabilidadTecnicaVenta == null
                || viabilidadTecnicaVenta.getApartamento() == null || viabilidadTecnicaVenta.getApartamento().isEmpty()
                || viabilidadTecnicaVenta.getCalle() == null || viabilidadTecnicaVenta.getCalle().isEmpty()
                || viabilidadTecnicaVenta.getComunidad() == null || viabilidadTecnicaVenta.getComunidad().isEmpty()
                || viabilidadTecnicaVenta.getDivicion() == null || viabilidadTecnicaVenta.getDivicion().isEmpty()
                || viabilidadTecnicaVenta.getSegmento() == null || viabilidadTecnicaVenta.getSegmento().isEmpty()
                || viabilidadTecnicaVenta.getUnidad() == null || viabilidadTecnicaVenta.getUnidad().isEmpty()) {
            responseViabilidadTecnicaVenta.setTipoRespuesta("E");
            responseViabilidadTecnicaVenta.setMensaje("Todos los campos de entrada son obligatorios");
            return responseViabilidadTecnicaVenta;

        }

        try {
            ArrayList<UnidadStructPcml> unidadStructPcmlList;
            unidadStructPcmlList = pcmlManager.getUnidades(viabilidadTecnicaVenta.getCalle().trim(),
                    viabilidadTecnicaVenta.getUnidad().trim(),
                    viabilidadTecnicaVenta.getApartamento().trim(),
                    viabilidadTecnicaVenta.getComunidad().trim());
            if (unidadStructPcmlList == null || unidadStructPcmlList.isEmpty()) {
                responseViabilidadTecnicaVenta.setTipoRespuesta("I");
                responseViabilidadTecnicaVenta.setViable("No");
                responseViabilidadTecnicaVenta.setMensaje("Unidad(HHPP) no existe. "
                        + "Factibilidad Negativa");
                return responseViabilidadTecnicaVenta;
            }
            for (UnidadStructPcml structPcml : unidadStructPcmlList) {
                if (structPcml.getAptoUnidad().trim().
                        equalsIgnoreCase(viabilidadTecnicaVenta.getApartamento().trim())
                        && structPcml.getCalleUnidad().trim().
                        equalsIgnoreCase(viabilidadTecnicaVenta.getCalle().trim())
                        && structPcml.getCasaUnidad().trim().
                        equalsIgnoreCase(viabilidadTecnicaVenta.getUnidad().trim())) {
                    structPcmlEncontrada = structPcml;
                    break;
                }
            }
            if (structPcmlEncontrada == null) {
                responseViabilidadTecnicaVenta.setTipoRespuesta("I");
                responseViabilidadTecnicaVenta.setViable("No");
                responseViabilidadTecnicaVenta.setMensaje("Unidad(HHPP) no existe. "
                        + "Factibilidad Negativa");
                return responseViabilidadTecnicaVenta;
            }
            Edificio edificio = pcmlManager.getEdificio(
                    structPcmlEncontrada.getIdUnidad().toString());
            if (edificio == null || edificio.getCodEdificio() == null
                    || edificio.getCodEdificio().trim().length() == 0) {
                responseViabilidadTecnicaVenta = extraerDataHhppCasaAbierta(
                        responseViabilidadTecnicaVenta, structPcmlEncontrada);
                responseViabilidadTecnicaVenta.setEsCuentaMatriz("No");
                responseViabilidadTecnicaVenta.setCodEstadoCm("NA");
                responseViabilidadTecnicaVenta.setEstadoCm("No es cuenta matriz");
            } else {
                if (edificio.getCodError().trim().equalsIgnoreCase("I")) {
                    if (edificio.getMensajeError().toUpperCase().contains("Id Unidad no existe".
                            toUpperCase())) {
                        responseViabilidadTecnicaVenta = extraerDataHhppCasaAbierta(
                                responseViabilidadTecnicaVenta, structPcmlEncontrada);
                        responseViabilidadTecnicaVenta.setEsCuentaMatriz("No");
                        responseViabilidadTecnicaVenta.setCodEstadoCm("NA");
                        responseViabilidadTecnicaVenta.setEstadoCm("No es cuenta matriz");
                    } else {
                        responseViabilidadTecnicaVenta.setTipoRespuesta("E");
                        responseViabilidadTecnicaVenta.setViable("No");
                        responseViabilidadTecnicaVenta.setMensaje("Error al cunsultar el edificio "
                                + "Factibilidad Negativa");
                        return responseViabilidadTecnicaVenta;
                    }
                } else {
                    responseViabilidadTecnicaVenta = extraerDataHhppCm(
                            responseViabilidadTecnicaVenta, edificio);
                    responseViabilidadTecnicaVenta = extraerDataHhppCasaAbierta(
                            responseViabilidadTecnicaVenta, structPcmlEncontrada);
                    responseViabilidadTecnicaVenta.setEsCuentaMatriz("Si");
                }

            }
            responseViabilidadTecnicaVenta = validarViabilidadHhpp(
                    responseViabilidadTecnicaVenta, viabilidadTecnicaVenta);
            responseViabilidadTecnicaVenta.setTipoRespuesta("I");
        } catch (ApplicationException e) {
            LOGGER.error("Error en viavilidadTecnicaVenta. " +e.getMessage(), e);
            responseViabilidadTecnicaVenta.setViable("No");
            responseViabilidadTecnicaVenta.setTipoRespuesta("E");
            responseViabilidadTecnicaVenta.setMensaje(e.getMessage());
        }
        return responseViabilidadTecnicaVenta;
    }

    private ResponseViabilidadTecnicaVenta validarViabilidadHhpp(
            ResponseViabilidadTecnicaVenta rViabilidadTecnicaVenta,
            RequestViabilidadTecnicaVenta viabilidadTecnicaVenta) throws ApplicationException {
        CmtConvergenciaViabilidadHhppMgl viabilidadHhppMgl = new CmtConvergenciaViabilidadHhppMgl();
        viabilidadHhppMgl.setEstadoNodo(rViabilidadTecnicaVenta.getCodEstadoNodo());
        viabilidadHhppMgl.setEstadoCuentamatriz(rViabilidadTecnicaVenta.getCodEstadoCm());
        viabilidadHhppMgl.setEstadoHhpp(rViabilidadTecnicaVenta.getCodEstadoHhpp());
        viabilidadHhppMgl = viabilidadHhppMgManager.findByRegla(viabilidadHhppMgl);
        if (viabilidadHhppMgl == null) {
            rViabilidadTecnicaVenta.setViable("No");
            rViabilidadTecnicaVenta.setMensaje("La combinacion estado nodo,estado cuenta matriz,"
                    + " estado hhpp, no esta configurada en la matriz de viabilidad");

            rViabilidadTecnicaVenta.setTipoRespuesta("I");
            return rViabilidadTecnicaVenta;
        }
        rViabilidadTecnicaVenta.setViable(viabilidadHhppMgl.getViable());
        rViabilidadTecnicaVenta.setMensaje(viabilidadHhppMgl.getMensaje());
        rViabilidadTecnicaVenta.setTipoRespuesta("I");

        CmtConvergenciaViabilidadEstratoMgl viabilidadEstratoMgl =
                new CmtConvergenciaViabilidadEstratoMgl();
        viabilidadEstratoMgl.setEstrato(rViabilidadTecnicaVenta.getEstrato());
        viabilidadEstratoMgl.setSegmento(viabilidadTecnicaVenta.getSegmento());
        viabilidadEstratoMgl = viabilidadEstratoMglManager.findByRegla(viabilidadEstratoMgl);
        if (viabilidadEstratoMgl == null) {
            rViabilidadTecnicaVenta.setViable("No");
            rViabilidadTecnicaVenta.setMensaje("La combinacion segmento, Estrato,"
                    + "  no esta configurada en la matriz de viabilidad Estrato");
            rViabilidadTecnicaVenta.setTipoRespuesta("I");
            return rViabilidadTecnicaVenta;
        }
        rViabilidadTecnicaVenta.setViable(viabilidadEstratoMgl.getViable());
        rViabilidadTecnicaVenta.setMensaje(viabilidadEstratoMgl.getMensaje());
        rViabilidadTecnicaVenta.setTipoRespuesta("I");

        return rViabilidadTecnicaVenta;
    }

    private ResponseViabilidadTecnicaVenta extraerDataHhppCm(
            ResponseViabilidadTecnicaVenta responseViabilidadTecnicaVenta, Edificio edificio) {
        if (!edificio.getEstadoEdificio().equalsIgnoreCase(Constant.CUENTA_MATRIZ_ESTADO_MULTIEDIFICIO)) {
            responseViabilidadTecnicaVenta.setCodEstadoCm(edificio.getEstadoEdificio());
            responseViabilidadTecnicaVenta.setEstadoCm(edificio.getMatriz());
        } else {
            responseViabilidadTecnicaVenta.setCodEstadoCm(edificio.getCodEstadoMultiEdificio());
            responseViabilidadTecnicaVenta.setEstadoCm(edificio.getNomEstadoMultiEdificio());
        }
        return responseViabilidadTecnicaVenta;
    }

    private ResponseViabilidadTecnicaVenta extraerDataHhppCasaAbierta(
            ResponseViabilidadTecnicaVenta responseViabilidadTecnicaVenta,
            UnidadStructPcml structPcmlEncontrada) throws ApplicationException {
        NodoMglManager nodoManager = new NodoMglManager();
        responseViabilidadTecnicaVenta.setCodEstadoHhpp(structPcmlEncontrada.getEstadUnidadad());
        responseViabilidadTecnicaVenta.setEstadoHhpp(structPcmlEncontrada.getDescEstado());
        NodoMgl nodoRr = nodoManager.findByCodigo(structPcmlEncontrada.getNodUnidad());
        String descripcionEstadoNodo = "";
        String codEstadoNodo = "";
        if (nodoRr != null && nodoRr.getEstado() != null 
                && !nodoRr.getEstado().getIdentificadorInternoApp().equalsIgnoreCase("")) {
            codEstadoNodo = nodoRr.getEstado().getIdentificadorInternoApp();
        }
        
        responseViabilidadTecnicaVenta.setEstrato(structPcmlEncontrada.getEstratoUnidad());

        if (codEstadoNodo.equalsIgnoreCase(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO)) {
            descripcionEstadoNodo = "Activo";
            codEstadoNodo = "A";
        } else if (codEstadoNodo.equalsIgnoreCase(Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO)) {
            descripcionEstadoNodo = "Inactivo";
            codEstadoNodo = "I";
        }
        if (codEstadoNodo.equalsIgnoreCase("P")) {
            descripcionEstadoNodo = "Preventa";
        }
        responseViabilidadTecnicaVenta.setEstadoNodo(descripcionEstadoNodo);
        responseViabilidadTecnicaVenta.setCodEstadoNodo(codEstadoNodo);
        return responseViabilidadTecnicaVenta;     
    }
    
    
   public DireccionRREntity convertirDireccionStringADrDireccion(
            String direccion, String comunidad,String barrio) throws ApplicationException {
       return validadorDireccionesManager.convertirDireccionStringADrDireccion(direccion, comunidad,barrio);
   } 
}
