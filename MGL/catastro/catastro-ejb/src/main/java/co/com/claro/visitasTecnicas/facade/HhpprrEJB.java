package co.com.claro.visitasTecnicas.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitastecnicas.ws.proxy.ChangeUnitAddressRequest;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless(name = "hhpprrEJB", mappedName = "hhpprrEJB", description = "hhpprr")
@Remote({HhpprrEJBRemote.class})
public class HhpprrEJB implements HhpprrEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(HhpprrEJB.class);
    private static final String SECUENCIA_DIRECCION_RR = "DIRECCION_RR_SEQ";
    private static final String NEXTVAL = "NEXTVAL";
    private static final String CALLE = "CALLE";
    private static final String VALOR_UNIDAD = "VALOR_UNIDAD";
    private static final String VALOR_APARTAMENTO = "VALOR_APARTAMENTO";

    @Override
    public DireccionRREntity gestionarHHPP(DetalleDireccionEntity direccion) throws ApplicationException {

        AccessData adb = null;
        try {
            DireccionRRManager direccionRRManager = new DireccionRRManager(direccion);
            DireccionRREntity direccionRR = direccionRRManager.getDireccion();
            adb = ManageAccess.createAccessData();

            DataObject obj = adb.outDataObjec("seq1", SECUENCIA_DIRECCION_RR);
            BigDecimal id = obj.getBigDecimal(NEXTVAL);
            String numeroUnidadCompleto = direccionRR.getNumeroUnidad();
            String numeroUnidad = numeroUnidadCompleto.length() > 10
                    ? numeroUnidadCompleto.substring(0, 10)
                    : numeroUnidadCompleto;

            String numeroApartamentoCompleto = direccionRR.getNumeroApartamento();
            String numeroApartamento = numeroApartamentoCompleto.length() > 10
                    ? numeroApartamentoCompleto.substring(0, 10)
                    : numeroApartamentoCompleto;

            adb.in("dir18", String.valueOf(id), direccionRR.getCalle(),
                    numeroUnidad, numeroApartamento);

            DireccionUtil.closeConnection(adb);
            direccionRR.setId(String.valueOf(id));

            return direccionRR;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de gestionar el HHPP. EX000: " + e.getMessage(), e);
        }
    }
    
    @Override
    public DireccionRREntity obtenerRegistroRR(String idDireccionRR) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dir20", idDireccionRR);
            DireccionRREntity direccionRR = new DireccionRREntity();
            direccionRR.setId(idDireccionRR);
            direccionRR.setCalle(obj.getString(CALLE));
            direccionRR.setCalle(obj.getString(VALOR_UNIDAD));
            direccionRR.setCalle(obj.getString(VALOR_APARTAMENTO));
            DireccionUtil.closeConnection(adb);
            return direccionRR;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de obtener el registro de RR. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param direccion
     * @param nodo
     * @param usuario
     * @param carpeta
     * @param nombreFuncionalidad
     * @param nvlSocioEconomico
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public DireccionRREntity crearHHPPRR(DetalleDireccionEntity direccion, String nodo, String nodoReal,
            String usuario, String carpeta, String nombreFuncionalidad,
            String nvlSocioEconomico, boolean validarConfiabiliadDir,
            String solicitud, String tipoSol, String codCiudad, 
            String observaciones, String usuarioSol,
            String razon, String idUsuario, 
            String contacto, String telContacto,String campoUno) throws ApplicationException {
        DireccionRRManager direccionRRManager = new DireccionRRManager(direccion);
        
        //TODO: Enviar valor del id Centro Poblado de creación del Hhpp 
        return direccionRRManager.registrarHHPP(nodo.toUpperCase(), nodoReal.toUpperCase(), usuario,
                carpeta, nombreFuncionalidad, nvlSocioEconomico,
                validarConfiabiliadDir, solicitud, tipoSol.toUpperCase(), 
                codCiudad, observaciones,usuarioSol,razon,idUsuario, 
                contacto, telContacto,campoUno,null, null);
    }
    
    @Override
    public DireccionRREntity cambiarDirHHPPRR(String comunidadOld, String divisionOld,
            String houseNumberOld, String streetNameOld, String apartmentNumberOld,
            String comunidadNew, String divisionNew,
            String houseNumberNew, String streetNameNew, String apartmentNumberNew,
            String solicitud, String usuario, String tipoSol,String campoUno, String tipoDir)  throws ApplicationException {

        DireccionRRManager direccionRRManager = new DireccionRRManager(true);
        return direccionRRManager.cambiarDirHHPPRR(comunidadOld, divisionOld, 
                houseNumberOld, streetNameOld, apartmentNumberOld, 
                comunidadNew, divisionNew, 
                houseNumberNew, streetNameNew, apartmentNumberNew, 
                solicitud, usuario, tipoSol,campoUno,tipoDir);

    }
    @Deprecated
    @Override
    public DireccionRREntity cambiarDireccionRRHHPP(DetalleDireccionEntity direccion,
            String comunidad, String division, String codDane,
            boolean validarConfiabilidad, String solicitud,String houseNumber,
            String streetName,String apartmentNumber, String usuario, String tipoSol,
            String campoUno, String tipoDir) throws ApplicationException {
        DireccionRRManager direccionRRManager = new DireccionRRManager(direccion);
        return direccionRRManager.cambiarDireccionHHPPRR(comunidad.toUpperCase(), division.toUpperCase(), codDane,
                validarConfiabilidad, solicitud,
                houseNumber.toUpperCase(), streetName.toUpperCase(), apartmentNumber.toUpperCase(),
                usuario, tipoSol, campoUno,tipoDir);
    }

    @Deprecated
    @Override
    public boolean cambiarEstratoHHPPRR(DetalleDireccionEntity direccion, String comunidad,
            String division, String estrato, String solicitud,
            String usuario, String observaciones) throws ApplicationException {
        DireccionRRManager direccionRRManager = new DireccionRRManager(direccion);

        return direccionRRManager.cambioEstrato(comunidad, division, estrato, solicitud, usuario, observaciones);
    }

    @Override
    public boolean cambiarEstratoHHPPRR(String comunidad,
            String division, String estrato, String solicitud,
            String usuario, String observaciones,
            String houseNumber, String streetName, String apartmentNumber, String tipoSol, String tipoDocSoporte) throws ApplicationException {
        DireccionRRManager direccionRRManager = new DireccionRRManager(true);

        return direccionRRManager.cambioEstrato(comunidad, division, estrato, solicitud, usuario, observaciones,
                houseNumber.toUpperCase(), streetName.toUpperCase(), apartmentNumber.toUpperCase(), tipoSol, tipoDocSoporte);
    }
    
    @Override
    public List<ChangeUnitAddressRequest> cambioDireccionRRMasivo(List<ChangeUnitAddressRequest> changeUnitAddressRequestList) throws ApplicationException{
        DireccionRRManager direccionRRManager = new DireccionRRManager(true);
        return direccionRRManager.cambiarDirHHPPRRMasivo(changeUnitAddressRequestList);
    }
    
    @Override
    public DireccionRREntity reactivarHHPP(DetalleDireccionEntity direccion,
            String comunidad,String division, String nodo,
            String numSolicitud, String usuarioSol, 
            String tipoSol, String observaciones) throws ApplicationException{
        DireccionRRManager direccionRRManager = new DireccionRRManager(direccion);
        return direccionRRManager.reactivarHHPPRR(
                direccionRRManager.getDireccion().getNumeroUnidad(), 
                direccionRRManager.getDireccion().getCalle(), 
                direccionRRManager.getDireccion().getNumeroApartamento(),
                comunidad, division, nodo,
                numSolicitud,usuarioSol,tipoSol,observaciones);
    }
    
    @Override
    public boolean cambiarAptoHHPP(DetalleDireccionEntity direccion, String comunidad, String division, 
            String solicitud, String oldApto, String newApto, String usuario, String tipoSol) throws ApplicationException {
        
        DireccionRRManager direccionRRManager = new DireccionRRManager(direccion);
        String calle = direccionRRManager.getDireccion().getCalle();
        String unidad = direccionRRManager.getDireccion().getNumeroUnidad();
        return direccionRRManager.cambiarEdificioHHPPRR(solicitud, 
                comunidad.toUpperCase(), division.toUpperCase(), 
                calle.toUpperCase(),unidad.toUpperCase(),
                oldApto.toUpperCase(), newApto.toUpperCase(),
                usuario, tipoSol);
    }
    
    @Override
    public String cambioEstadoNodoHHPP(DetalleDireccionEntity direccion,
            String comunidad,String division, String nodo, String estado, String tipoSol, String solicitud, String usuario, String tipoSolicitud){
        try {
            DireccionRRManager direccionRRManager = new DireccionRRManager(direccion);
            return direccionRRManager.cambioEstadoNodoHHPP(direccionRRManager.getDireccion().getNumeroUnidad(), 
                                                      direccionRRManager.getDireccion().getCalle(), 
                                                      direccionRRManager.getDireccion().getNumeroApartamento(),
                                                      comunidad, division, nodo, estado, tipoSol, solicitud, usuario, tipoSolicitud, null);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    public DireccionRREntity cambiarDireccionRRHHPP(DetalleDireccionEntity direccion, String comunidad, String division, String codDane, boolean validarConfiabilidad, String solicitud, String houseNumber, String streetName, String apartmentNumber, String usuario, String tipoSol, String campoUno) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
