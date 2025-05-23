/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FichasGeoDrDireccionDto;
import co.com.claro.mgl.dtos.NodoDto;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesPreviasMgl;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface CmtValidadorDireccionesFacadeLocal {

    ResponseValidarDireccionDto validarDireccion
            (DrDireccion drDireccion, String direccion, GeograficoPoliticoMgl ciudad,
                    String barrio, boolean cambiarMallaDir)
            throws ApplicationException;

    AddressService calcularCobertura(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) throws ApplicationException;

    HashMap<String, NodoDto> calcularNodoCercano(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    List<CmtUnidadesPreviasMgl> unidadesDeLaDireccion(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) throws ApplicationException;

    DrDireccion convertirDireccionStringADrDireccion(String direccion, BigDecimal ciudad)
            throws ApplicationException, ApplicationException;
    
    FichasGeoDrDireccionDto convertirDireccionStringADrDireccionFichas(String direccion, BigDecimal ciudad)
            throws ApplicationException, ApplicationException;

    DireccionRREntity convertirDrDireccionARR(DrDireccion drDireccion, String multiorigen) throws ApplicationException;
    
    DireccionRREntity convertirDireccionStringADrDireccion(
            String direccion, String comunidad,String barrio) throws ApplicationException, ApplicationException;
    
     HashMap<String, NodoDto> calcularNodoCercano(String calleRr, String unidadRr, String comunidad) throws ApplicationException;

    /**
     * Autor: Victor Bocanegra Consulta las unidades previas desde MGL desde la
     * direccion Detallada
     *
     * @param cmtDireccionSolictudMgl
     * @param cmtDireccionSolicitudMgl
     * @return List<CmtUnidadesPreviasMgl>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtUnidadesPreviasMgl> unidadesDeLaDireccionMgl(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl)
            throws ApplicationException;
    
    AddressService calcularCoberturaDrDireccion(DrDireccion drDireccion, GeograficoPoliticoMgl centroPoblado) throws ApplicationException;
}
