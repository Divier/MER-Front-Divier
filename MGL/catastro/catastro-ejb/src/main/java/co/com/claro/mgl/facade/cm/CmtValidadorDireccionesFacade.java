/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtValidadorDireccionesManager;
import co.com.claro.mgl.dtos.FichasGeoDrDireccionDto;
import co.com.claro.mgl.dtos.NodoDto;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesPreviasMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author ADMIN
 */
@Stateless
public class CmtValidadorDireccionesFacade implements CmtValidadorDireccionesFacadeLocal {

    private String user = "";
    private int perfil = 0;
    CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
    private static final Logger LOGGER = LogManager.getLogger(CmtValidadorDireccionesFacade.class);

    /**
     *
     * @param drDireccion
     * @param direccion
     * @param ciudad
     * @param barrio
     * @return
     * @throws ApplicationException
     */
    @Override
    public ResponseValidarDireccionDto validarDireccion
            (DrDireccion drDireccion, String direccion, GeograficoPoliticoMgl ciudad, 
                    String barrio, boolean cambiarMallaDir)
            throws ApplicationException {
        try {
            return direccionesManager.validarDireccion(drDireccion, direccion, ciudad, barrio, cambiarMallaDir);
        } catch (Exception e) {
              String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
              LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    @Override
    public AddressService calcularCobertura(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) throws ApplicationException {
        return direccionesManager.calcularCobertura(cmtDireccionSolictudMgl);
    }

    @Override
    public HashMap<String, NodoDto> calcularNodoCercano(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) 
            throws ApplicationException {
        return direccionesManager.calcularNodoCercano(cmtDireccionSolictudMgl, user, perfil);
    }
    
    @Override
    public HashMap<String, NodoDto> calcularNodoCercano(String calleRr, String unidadRr, String comunidad) 
            throws ApplicationException {
        return direccionesManager.calcularNodoCercano(calleRr,unidadRr,comunidad, user, perfil);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<CmtUnidadesPreviasMgl> unidadesDeLaDireccion(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) throws ApplicationException {
        return direccionesManager.unidadesDeLaDireccion(cmtDireccionSolictudMgl);
    }

    @Override
    public DrDireccion convertirDireccionStringADrDireccion(String direccion, BigDecimal ciudad) 
            throws ApplicationException, ApplicationException {
        return direccionesManager.convertirDireccionStringADrDireccion(direccion, ciudad);
    }
    
    @Override
    public FichasGeoDrDireccionDto convertirDireccionStringADrDireccionFichas(String direccion, BigDecimal ciudad)
            throws ApplicationException, ApplicationException{
        return direccionesManager.convertirDireccionStringADrDireccionFichas(direccion, ciudad);
    }
    
    @Override
    public DireccionRREntity convertirDrDireccionARR (DrDireccion drDireccion, String multiorigen)throws ApplicationException{
        return direccionesManager.convertirDrDireccionARR(drDireccion,multiorigen);    
    }
    
    @Override
    public DireccionRREntity convertirDireccionStringADrDireccion(
            String direccion, String comunidad,String barrio) throws ApplicationException, ApplicationException {
        return direccionesManager.convertirDireccionStringADrDireccion(direccion, comunidad,barrio);
    }
    /**
     * Autor: Victor Bocanegra Consulta las unidades previas desde MGL desde la
     * direccion Detallada
     *
     * @param cmtDireccionSolictudMgl
     * @param cmtDireccionSolicitudMgl
     * @return List<CmtUnidadesPreviasMgl>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<CmtUnidadesPreviasMgl> unidadesDeLaDireccionMgl(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) 
            throws ApplicationException {

        return direccionesManager.unidadesDeLaDireccionMgl(cmtDireccionSolictudMgl);
    }    
    
    @Override
    public AddressService calcularCoberturaDrDireccion(DrDireccion drDireccion,
            GeograficoPoliticoMgl centroPoblado) throws ApplicationException {
        return direccionesManager.calcularCoberturaDrDireccion(drDireccion, centroPoblado);
    }

}
