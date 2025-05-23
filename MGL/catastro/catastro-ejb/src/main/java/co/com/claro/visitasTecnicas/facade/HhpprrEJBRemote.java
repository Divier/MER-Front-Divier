package co.com.claro.visitasTecnicas.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitastecnicas.ws.proxy.ChangeUnitAddressRequest;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase HhpprrJBRemote
 *
 * @author Ivan Turriago.
 * @version	1.0
 */
@Remote
public interface HhpprrEJBRemote {

    public DireccionRREntity gestionarHHPP(DetalleDireccionEntity direccion) throws ApplicationException;

    public DireccionRREntity obtenerRegistroRR(String idDireccionRR) throws ApplicationException;

    /**
     *
     * @param direccion
     * @param nodo
     * @param nodoReal
     * @param usuario
     * @param carpeta
     * @param nombreFuncionalidad
     * @param nvlSocioEconomico
     * @param validarConfiabiliadDir
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public DireccionRREntity crearHHPPRR(DetalleDireccionEntity direccion, String nodo, String nodoReal,
            String usuario, String carpeta, String nombreFuncionalidad,
            String nvlSocioEconomico, boolean validarConfiabiliadDir, String solicitud,
            String tipoSol, String codCiudad, String observaciones,String usuarioSol,
            String razon, String idUsuario, 
            String contacto, String telContacto,String campoUno) throws ApplicationException ;
    
    public DireccionRREntity cambiarDireccionRRHHPP(DetalleDireccionEntity direccion,
            String comunidad, String division, String codDane,
            boolean validarConfiabilidad, String solicitud,String houseNumber,
            String streetName,String apartmentNumber, String usuario, String tipoSol,
            String campoUno, String tipoDir) throws ApplicationException;
    
    public DireccionRREntity cambiarDirHHPPRR(String comunidadOld, String divisionOld,
            String houseNumberOld, String streetNameOld, String apartmentNumberOld,
            String comunidadNew, String divisionNew,
            String houseNumberNew, String streetNameNew, String apartmentNumberNew,
            String solicitud, String usuario, String tipoSol,String campoUno, String tipoDir)  throws ApplicationException ;

    @Deprecated
    boolean cambiarEstratoHHPPRR(DetalleDireccionEntity direccion, String comunidad,
            String division, String estrato, String solicitud,
            String usuario, String observaciones) throws ApplicationException;
    
    boolean cambiarEstratoHHPPRR(String comunidad,
            String division, String estrato, String solicitud,
            String usuario, String observaciones,
            String houseNumber,String streetName,String apartmentNumber, String tipoSol, String tipoDocSoporte) throws ApplicationException;
    
    public List<ChangeUnitAddressRequest> cambioDireccionRRMasivo(List<ChangeUnitAddressRequest> changeUnitAddressRequestList) throws ApplicationException;    
    
    DireccionRREntity reactivarHHPP(DetalleDireccionEntity direccion,
            String comunidad,String division, String nodo,
            String numSolicitud, String usuarioSol, 
            String tipoSol, String observaciones) throws ApplicationException;
    
    boolean cambiarAptoHHPP(DetalleDireccionEntity direccion, String comunidad, String division, 
            String solicitud, String oldApto, String newApto, String usuario, String tipoSol) throws ApplicationException;
    
    public String cambioEstadoNodoHHPP(DetalleDireccionEntity direccion,
            String comunidad,String division, String nodo, String estado, String tipoSol, String solcitud, String usuario, String tipoSolicitud);
    
    }
    

