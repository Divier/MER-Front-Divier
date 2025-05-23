/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class DireccionRRFacade implements DireccionRRFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(DireccionRRFacade.class);

    @Override
    public String getFormatoRR(String valor) throws ApplicationException {
        DireccionRRManager direccionRRManager = new DireccionRRManager(false);
        return direccionRRManager.formatoRR(valor);
    }

    @Override
    public String generarNumAptoRR(DetalleDireccionEntity direccion) throws
            ApplicationException {
        DireccionRRManager direccionRRManager = new DireccionRRManager(false);
        return direccionRRManager.generarNumAptoBMRR(direccion);
    }

    @Override
    public DireccionRREntity generarDirFormatoRR(
            DetalleDireccionEntity direccion) throws ApplicationException {
        DireccionRRManager direccionRRManager
                = new DireccionRRManager(direccion, direccion.getMultiOrigen(),null);
        return direccionRRManager.getDireccion();
    }

    @Override
    public ResponseConstruccionDireccion
            construirDireccionSolicitud(RequestConstruccionDireccion request)
            throws ApplicationException {
        DrDireccionManager manager = new DrDireccionManager();
        return manager.construirDireccionSolicitud(request);
    }

    @Override
    public String getDireccion(DrDireccion drDirecion) {
        DrDireccionManager manager = new DrDireccionManager();
        return manager.getDireccion(drDirecion);
    }

    @Override
    public String traerHeadEnd(String hhpComunidad) {
        DireccionRRManager manager = new DireccionRRManager(false);
        try {
            return manager.traerHeadEnd(hhpComunidad);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.
                    getCurrentMethodName(this.getClass()) + "': " + e.
                    getMessage();
            LOGGER.error(msg);
            throw new Error(msg, e);
        }
    }

    @Override
    public DireccionRREntity registrarHHPP_Inspira_Independiente(String strNodo,
            String nodoReal,
            String usuario, String carpeta, String nombreFuncionalidad,
            String nvlSocioEconomico, boolean validarConfiabilidad,
            String tipoSol, String razon,
            String idUsuario, BigDecimal idCentroPobladoGpo,
            boolean sincronizaRr, String tipoHhppGestion, NodoMgl nodoMgl)
            throws ApplicationException {
        DireccionRRManager manager = new DireccionRRManager(true);
        return manager.registrarHHPP_Inspira_Independiente(strNodo, nodoReal,
                usuario, carpeta, nombreFuncionalidad, nvlSocioEconomico,
                validarConfiabilidad, tipoSol, razon, idUsuario,
                idCentroPobladoGpo, sincronizaRr, "", nodoMgl);
    }

}
