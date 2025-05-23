package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.dao.impl.DireccionMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtDireccionDetalleMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtDireccionesMglDaoImpl;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public class CmtDireccionMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtDireccionMglManager.class);
    CmtDireccionesMglDaoImpl cmtDireccionesMglDaoImpl = new CmtDireccionesMglDaoImpl();
    ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
    String BASE_URI;
    DireccionRRManager direccionRRManager;
    CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDaoImpl = new CmtDireccionDetalleMglDaoImpl();

    public CmtDireccionMglManager() {

    }

    public CmtDireccionMgl create(CmtDireccionMgl drDirec, String user, int perfil) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.createCm(drDirec, user, perfil);
    }

    public CmtDireccionMgl updateSinRr(CmtDireccionMgl drDirec, String user, int perfil) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.updateCm(drDirec, user, perfil);
    }

    public CmtDireccionMgl update(CmtDireccionMgl drDirec, String user, int perfil) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.updateCm(drDirec, user, perfil);
    }

    public boolean deleteSinRr(CmtDireccionMgl drDirec, String user, int perfil) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.deleteCm(drDirec, user, perfil);
    }

    public boolean delete(CmtDireccionMgl drDirec, String user, int perfil) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.deleteCm(drDirec, user, perfil);
    }

    public CmtDireccionMgl findById(BigDecimal id) throws ApplicationException {
        return (CmtDireccionMgl) cmtDireccionesMglDaoImpl.find(id);
    }

    public CmtDireccionMgl findByIdSolicitud(BigDecimal id) throws ApplicationException {
        return (CmtDireccionMgl) cmtDireccionesMglDaoImpl.findByIdSolicitud(id);
    }

    public CmtDireccionMgl findByIdSubEdificio(BigDecimal id) throws ApplicationException {
        return (CmtDireccionMgl) cmtDireccionesMglDaoImpl.findByIdSubEdificio(id);
    }

    public CmtDireccionMgl findByDirAlt(String id) throws ApplicationException {
        return (CmtDireccionMgl) cmtDireccionesMglDaoImpl.findDirAlt(id);
    }

    public boolean findAny(CmtDireccionMgl newAltAddress) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.findAny(newAltAddress);
    }

    public Long countFindByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.countFindByCm(cmtCuentaMatrizMgl);
    }

    public List<CmtDireccionMgl> findByCuentaMatriz(CmtCuentaMatrizMgl cmtCuentaMatrizMgl,
            int page, int numberOfResult, Constant.TYPE_QUERY type) throws ApplicationException {
        int firstResult = 0;
        if (page > 0) {
            firstResult = (numberOfResult * (page - 1));
        }
        return cmtDireccionesMglDaoImpl.findByCuentaMatriz(cmtCuentaMatrizMgl, firstResult, numberOfResult, type);
    }

    public List<CmtDireccionMgl> findBySubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().equals(
                cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId())) {
            return cmtDireccionesMglDaoImpl.findByCuentaMatriz(cmtSubEdificioMgl);
        } else {
            return cmtDireccionesMglDaoImpl.findBySubEdificio(cmtSubEdificioMgl);
        }

    }

    public List<CmtDireccionMgl> findDireccionesByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        List<CmtDireccionMgl> resulList;
        CmtDireccionesMglDaoImpl daoImpl = new CmtDireccionesMglDaoImpl();
        resulList = daoImpl.findDireccionesByCuentaMatriz(cuentaMatriz);
        return resulList;
    }

    public CmtDireccionMgl crearSolicitud(CmtDireccionMgl dir, String usuario, int perfil) throws ApplicationException {
        CmtDireccionesMglDaoImpl daoImpl = new CmtDireccionesMglDaoImpl();
        return daoImpl.createCm(dir, usuario, perfil);
    }

    public boolean esNivelDireccion(String palabra, List<CmtBasicaMgl> tipoSubEdificioList) {
        boolean result = false;
        for (CmtBasicaMgl tipoSubEdi : tipoSubEdificioList) {
            if (palabra.trim().equals(tipoSubEdi.getNombreBasica().trim())) {
                return true;
            }
        }
        return result;
    }

    public DireccionRRManager estandarizarNombreSubEdificioDireccion(CmtDireccionMgl cmtdireccionMgl, 
            String nombreSubEdi) throws ApplicationException {
        /* Obteniendo Niveles a traves del nombre SubEdificio */
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtTipoBasicaMgl cmtTipoBasicaMgl;
        cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                Constant.TIPO_BASICA_TIPO_SUBEDIFICIO);
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        List<CmtBasicaMgl> tipoSubEdificioList = cmtBasicaMglManager.findByTipoBasica(cmtTipoBasicaMgl);
        int numeroNivel = 1;
        String nivel1 = "";
        String nivel2 = "";
        String nivel3 = "";
        String nivel4 = "";
        String valorNivel1 = "";
        String valorNivel2 = "";
        String valorNivel3 = "";
        String valorNivel4 = "";
        
        if (nombreSubEdi != null && !nombreSubEdi.trim().isEmpty()) {

            String[] niveles = nombreSubEdi.split(" ");
            for (int i = 0; i <= niveles.length - 1; i++) {
                if (esNivelDireccion(niveles[i], tipoSubEdificioList)) {
                    if (numeroNivel == 1) {
                        nivel1 = niveles[i];
                        numeroNivel--;
                    }
                    if (numeroNivel == 2) {
                        nivel2 = niveles[i];
                        numeroNivel--;
                    }
                    if (numeroNivel == 3) {
                        nivel3 = niveles[i];
                        numeroNivel--;
                    }
                    if (numeroNivel == 4) {
                        nivel4 = niveles[i];
                        numeroNivel--;
                    }
                } else {
                    if (numeroNivel == 1) {
                        valorNivel1 = valorNivel1 + niveles[i];
                    }
                    if (numeroNivel == 2) {
                        valorNivel2 = valorNivel2 + niveles[i];
                    }
                    if (numeroNivel == 3) {
                        valorNivel3 = valorNivel3 + niveles[i];
                    }
                    if (numeroNivel == 4) {
                        valorNivel4 = valorNivel4 + niveles[i];
                    }
                }
                numeroNivel++;
            }
        }
        
        /*Estandarizando direccion a formato RR */
        DetalleDireccionEntity detalleDireccionEntity;
        detalleDireccionEntity = cmtdireccionMgl.getDetalleDireccionEntity();
        if (nombreSubEdi != null && !nombreSubEdi.trim().isEmpty()) {
            detalleDireccionEntity.setCptiponivel1(nivel1);
            detalleDireccionEntity.setCpvalornivel1(valorNivel1);
            detalleDireccionEntity.setCptiponivel2(nivel2);
            detalleDireccionEntity.setCpvalornivel2(valorNivel2);
            detalleDireccionEntity.setCptiponivel3(nivel3);
            detalleDireccionEntity.setCpvalornivel3(valorNivel3);
            detalleDireccionEntity.setCptiponivel4(nivel4);
            detalleDireccionEntity.setCpvalornivel4(valorNivel4);
        }        
        
        //JDHT
        GeograficoPoliticoMgl centroPoblado = null;
        if(cmtdireccionMgl.getCuentaMatrizObj() != null && cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado() != null){
            centroPoblado = cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado();
        }
        
        detalleDireccionEntity.setMultiOrigen(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen());
        return new DireccionRRManager(detalleDireccionEntity, (centroPoblado != null) ? centroPoblado.getGpoMultiorigen() : "", centroPoblado);
    }

    public boolean esNombreSubEdificioEstandar(String nombreSubEdi) throws ApplicationException {
        CmtTipoBasicaMgl cmtTipoBasicaMgl;
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                Constant.TIPO_BASICA_TIPO_SUBEDIFICIO);
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        List<CmtBasicaMgl> tipoSubEdificioList = cmtBasicaMglManager.findByTipoBasica(cmtTipoBasicaMgl);
        if (nombreSubEdi != null && !nombreSubEdi.trim().isEmpty()) {
            String[] niveles = nombreSubEdi.split(" ");
            if (esNivelDireccion(niveles[0], tipoSubEdificioList)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public HhppMgl actualizarDireccionHHPP(HhppMgl hhppMod, HhppMgl hhppModOld,
            CmtDireccionMgl cmtdireccionMgl, String direccionCM,
            String nombreSubEdi, String solicitud, String tipoSolicitud,
            String usuarioVT, String usuario, int perfil, DrDireccion dirModificar) throws ApplicationException {

        try {
            AddressEJB addressEJB = new AddressEJB();
            SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
            HhppMglManager hhppMglManager = new HhppMglManager();
            DireccionMglManager direccionMglManager = new DireccionMglManager();
            CmtDireccionDetalleMglManager detalleMglManager = new CmtDireccionDetalleMglManager();

            /*Validando si es un nombre estandar*/
            if (!esNombreSubEdificioEstandar(nombreSubEdi)) {
                nombreSubEdi = "";
            }

            /*Estandarizando direccion a formato RR */
            DireccionRRManager direccionRRManager
                    = estandarizarNombreSubEdificioDireccion(cmtdireccionMgl, nombreSubEdi);

            /*Creando SubDireccion del Repositorio*/
            AddressRequest addressRequest = new AddressRequest();
            String barrio = cmtdireccionMgl.obtenerBarrio();
            String direccion = "";
            if (hhppMod.getHhpApart() != null) {
                direccion = direccionCM.concat(" ").concat(nombreSubEdi == null
                        ? "" : nombreSubEdi).concat(" ").concat(hhppMod.getHhpApart());
            } else {
                direccion = direccionCM.concat(" ").concat(nombreSubEdi == null
                        ? "" : nombreSubEdi).concat(" ").concat(" ");
            }
            addressRequest.setAddress(direccion);
            addressRequest.setCity(cmtdireccionMgl.getCuentaMatrizObj().getMunicipio().getGpoNombre());
            addressRequest.setCodDaneVt(cmtdireccionMgl.getCuentaMatrizObj().getMunicipio().getGeoCodigoDane());
            DrDireccion dirCambioSub = dirModificar.clone();
            if (cmtdireccionMgl.getBarrio() != null && !cmtdireccionMgl.getBarrio().isEmpty()) {
                addressRequest.setNeighborhood(cmtdireccionMgl.getBarrio());
            } else {
                if (dirCambioSub.getBarrio() == null || dirCambioSub.getBarrio().isEmpty()) {
                    addressRequest.setNeighborhood(cmtdireccionMgl.getBarrio());
                }
            }
            if (!tipoSolicitud.contains("MODIFICACIÓN HHPP")) {
                CmtDireccionDetalladaMgl direccionDetalladaMgl = detalleMglManager.findByHhPP(hhppModOld);
               
                if (direccionDetalladaMgl != null) {
                    dirCambioSub.setCpTipoNivel4(direccionDetalladaMgl.getCpTipoNivel4());
                    dirCambioSub.setCpTipoNivel5(direccionDetalladaMgl.getCpTipoNivel5());
                    dirCambioSub.setCpTipoNivel6(direccionDetalladaMgl.getCpTipoNivel6());
                    dirCambioSub.setCpValorNivel4(direccionDetalladaMgl.getCpValorNivel4());
                    dirCambioSub.setCpValorNivel5(direccionDetalladaMgl.getCpValorNivel5());
                    dirCambioSub.setCpValorNivel6(direccionDetalladaMgl.getCpValorNivel6());

                }
            } else {
                CmtDireccionDetalladaMgl direccionDetalladaMgl = detalleMglManager.findByHhPP(hhppMod);
             
                if (direccionDetalladaMgl != null) {
                    dirCambioSub.setCpTipoNivel4(dirModificar.getCpTipoNivel4());
                    dirCambioSub.setCpTipoNivel5(dirModificar.getCpTipoNivel5());
                    dirCambioSub.setCpTipoNivel6(dirModificar.getCpTipoNivel6());
                    dirCambioSub.setCpValorNivel4(dirModificar.getCpValorNivel4());
                    dirCambioSub.setCpValorNivel5(dirModificar.getCpValorNivel5());
                    dirCambioSub.setCpValorNivel6(dirModificar.getCpValorNivel6());

                }
            }

 
                
            addressRequest.setState(cmtdireccionMgl.getCuentaMatrizObj().getDepartamento().getGpoNombre());
            ResponseMessage responseMessage;
            try {
                responseMessage = addressEJB.createAddress(addressRequest, usuarioVT, "CM", "true", dirCambioSub);
            } catch (ExceptionDB ex) {
                 String msg = "Error al momento de actualizar la dirección. EX000 '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  ex);
            }
            if (responseMessage.getMessageText().equalsIgnoreCase(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA)) {
                CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                boolean isCalleCarrera = cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK");
                direccionesManager.actualizarDireccionMayaNueva(addressRequest.getAddress(), cmtdireccionMgl.getCuentaMatrizObj().getMunicipio(), barrio, isCalleCarrera);
            }

            if (responseMessage.getMessageType().equalsIgnoreCase("ERROR")) {
                if (responseMessage.getIdaddress() == null
                        || !(responseMessage.getIdaddress().toUpperCase().contains("D")
                        || responseMessage.getIdaddress().toUpperCase().contains("S"))) {
                    throw new ApplicationException(" " + responseMessage.getMessageText());
                }
            }

            if (responseMessage.getIdaddress().toUpperCase().contains("D")) {
                DireccionMgl direccionMglCreada = new DireccionMgl();
                BigDecimal direccionId
                        = new BigDecimal(responseMessage.getIdaddress().replace("d", "").replace("D", ""));
                direccionMglCreada.setDirId(direccionId);
                direccionMglCreada = direccionMglManager.findById(direccionMglCreada);
                hhppMod.setDireccionObj(direccionMglCreada);
                direccionMglManager.update(direccionMglCreada);
            } else {
                DireccionMgl direccionMglCreada = new DireccionMgl();
                BigDecimal subDireccionId
                        = new BigDecimal(responseMessage.getIdaddress().replace("s", "").replace("S", ""));
                SubDireccionMgl subDireccionMgl = subDireccionMglManager.findById(subDireccionId);
                direccionMglCreada.setDirId(subDireccionMgl.getDirId());
                hhppMod.setDireccionObj(direccionMglCreada);
                hhppMod.setSubDireccionObj(subDireccionMgl);
                subDireccionMglManager.update(subDireccionMgl);
            }
            
            //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
            boolean habilitarRR = false;      
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            
            //Si RR esta encendido y el hhpp existe en RR
            if (habilitarRR && hhppModOld.getHhpIdrR() != null && !hhppModOld.getHhpIdrR().isEmpty()) {

                /*Actualizando la dirección del HHPP en RR*/
                //Traemos el hhpp real de rr
                DireccionRRManager manager = new DireccionRRManager(true);

                String comunidad = hhppModOld.getHhpComunidad();
                String division = hhppModOld.getHhpDivision();
                String placa = hhppModOld.getHhpPlaca();
                String calle = hhppModOld.getHhpCalle();
                String apartamento = hhppModOld.getHhpApart();

                if (hhppModOld.getHhpIdrR() != null) {
                    HhppResponseRR responseHhppRR = manager.getHhppByIdRR(hhppModOld.getHhpIdrR());
                    if (responseHhppRR.getTipoMensaje() != null
                            && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                        comunidad = responseHhppRR.getComunidad();
                        division = responseHhppRR.getDivision();
                        calle = responseHhppRR.getStreet();
                        placa = responseHhppRR.getHouse();
                        apartamento = responseHhppRR.getApartamento();
                    } else {
                        LOGGER.error("Ocurrio un error consultando la data del hhpp en RR");
                    }
                }
            

            //JDHT
            if (calle.contains(" EN ")) {
                if (cmtdireccionMgl != null && cmtdireccionMgl.getCuentaMatrizObj() != null
                        && cmtdireccionMgl.getCuentaMatrizObj().getCuentaMatrizId() != null) {

                    CmtDireccionMglManager cmtDireccionManager = new CmtDireccionMglManager();
                    List<CmtDireccionMgl> cmtDireccionCuentaMatrizPrincipalList
                            = cmtDireccionManager.findDireccionesByCuentaMatriz(cmtdireccionMgl.getCuentaMatrizObj());

                    CmtDireccionMgl cmtDireccionMglCuentaMatrizPrincipal = null;
                    if (cmtDireccionCuentaMatrizPrincipalList != null
                            && !cmtDireccionCuentaMatrizPrincipalList.isEmpty()) {

                        for (CmtDireccionMgl cmtDireccionMgl : cmtDireccionCuentaMatrizPrincipalList) {
                            if (cmtDireccionMgl.getSubEdificioObj() == null) {
                                cmtDireccionMglCuentaMatrizPrincipal = cmtDireccionMgl;
                                break;
                            }
                        }

                        if (cmtDireccionMglCuentaMatrizPrincipal != null) {
                            direccionRRManager.getDireccion()
                                    .setCalle(cmtDireccionMglCuentaMatrizPrincipal.getCalleRr() + " EN "
                                            + cmtdireccionMgl.getCalleRr()
                                            + " " + cmtdireccionMgl.getUnidadRr());

                            direccionRRManager.getDireccion().setNumeroUnidad(placa);

                        }
                    }
                }
            }
            
            //JDHT si es solicitud de modificacion de cm.
            if (tipoSolicitud.contains("MODIFICACIÓN CM")) {
                hhppMod.setHhpApart(apartamento);
            }

                direccionRRManager.cambiarDirHHPPRRCm(comunidad,
                                division, placa,
                                calle, apartamento,
                                hhppMod.getHhpComunidad(), hhppMod.getHhpDivision(),
                                direccionRRManager.getDireccion().getNumeroUnidad(),
                                calle, hhppMod.getHhpApart(),
                                solicitud, usuarioVT, tipoSolicitud, "",
                                cmtdireccionMgl.getCodTipoDir(),
                                responseMessage.getIdaddress());

                // actualizacion del tipo de vivienda en RR
                direccionRRManager.cambioTipoUnidadViviendaHhppRR(hhppMod);
            
            }

            /*Actualizando la direccion del HHPP en el Repositorio*/
            hhppMod.setHhpCalle(hhppModOld.getHhpCalle());
            hhppMod.setHhpPlaca(direccionRRManager.getDireccion().getNumeroUnidad());
            hhppMod.setHhpApart(hhppMod.getHhpApart());

            hhppMglManager.update(hhppMod);


            return hhppMod;
        } catch (IOException ex) {
				 String msg = "Error al momento de actualizar la dirección. EX000 '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  ex);
        } catch (CloneNotSupportedException ex) {
                 String msg = "Error al momento de actualizar la dirección. EX000 '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  ex);
        }
    }

    public CmtDireccionMgl actualizarDireccionCmOSubEdificio(CmtDireccionMgl cmtdireccionMgl, CmtDireccionSolicitudMgl cmtdireccionSolMgl,
            int tipoDireccion, String direccion, CmtSubEdificioMgl subEdificioMgl,
            String usuarioVT, boolean isUpdate, String usuario, int perfil, DrDireccion dirModifica) throws ApplicationExceptionCM {
        try {
            String messageError = "";
            AddressEJB addressEJB = new AddressEJB();
            DireccionMglManager direccionMglManager = new DireccionMglManager();
            CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
            boolean habilitarRR = false;
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }

            /*Estandarizando direccion a formato RR */
            if (cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen().equals("1")
                    && cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {
                DetalleDireccionEntity detalleDireccionEntity;
                detalleDireccionEntity = cmtdireccionMgl.getDetalleDireccionEntity();
                if(cmtdireccionMgl.getBarrio() != null && !cmtdireccionMgl.getBarrio().isEmpty()){
                detalleDireccionEntity.setBarrio(cmtdireccionMgl.getBarrio().toUpperCase());
                }
                detalleDireccionEntity.setMultiOrigen("1");
                DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity, cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                detalleDireccionEntity.setMultiOrigen(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen());
                cmtdireccionSolMgl.setCalleRr(direccionRRManager.getDireccion().getCalle());
                cmtdireccionSolMgl.setUnidadRr(direccionRRManager.getDireccion().getNumeroUnidad());
            } else {
                DetalleDireccionEntity detalleDireccionEntity;
                detalleDireccionEntity = cmtdireccionMgl.getDetalleDireccionEntity();
                if(cmtdireccionMgl.getBarrio() != null && !cmtdireccionMgl.getBarrio().isEmpty()){
                detalleDireccionEntity.setBarrio(cmtdireccionMgl.getBarrio().toUpperCase());
                }
                detalleDireccionEntity.setMultiOrigen("0");

                DireccionRRManager direccionRRManager;
                if (cmtdireccionMgl.getDireccionId() != null && !cmtdireccionMgl.equals(BigDecimal.ZERO)) {
                    //Buscar el campo DireccionID
                    CmtDireccionMgl direccionMgl = cmtDireccionesMglDaoImpl.find(cmtdireccionMgl.getDireccionId());
                    cmtdireccionMgl.setDireccionObj(direccionMgl.getDireccionObj());

                    //Buscar en la tabla DireccionDetallada, el campo ID_DIR_CATASTRO, y setearlo al detalleDireccionEntity
                    List<CmtDireccionDetalladaMgl> dirCatastro = cmtDireccionDetalleMglDaoImpl.findDireccionDetallaByDirIdSdirId(cmtdireccionMgl.getDireccionObj().getDirId(), null);
                    if (dirCatastro != null && !dirCatastro.isEmpty()) {
                        dirCatastro.get(0);
                        detalleDireccionEntity.setIdDirCatastro(dirCatastro.get(0).getIdDirCatastro());
                        direccionRRManager = new DireccionRRManager(detalleDireccionEntity);
                    } else {
                        direccionRRManager = new DireccionRRManager(detalleDireccionEntity, cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), 
                                cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                    }
                } else {
                    direccionRRManager = new DireccionRRManager(detalleDireccionEntity, cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                }

                detalleDireccionEntity.setMultiOrigen(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen());
                cmtdireccionSolMgl.setCalleRr(direccionRRManager.getDireccion().getCalle());
                cmtdireccionSolMgl.setUnidadRr(direccionRRManager.getDireccion().getNumeroUnidad());
            }

			  /*Creando Direccion en el repositorio*/
            AddressRequest addressRequest = new AddressRequest();
            String barrio = "";
            if(cmtdireccionMgl.getBarrio() != null && !cmtdireccionMgl.getBarrio().isEmpty()){
               barrio = cmtdireccionMgl.getBarrio().toUpperCase();
            }
            addressRequest.setAddress(direccion);
            addressRequest.setCity(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoNombre());
            addressRequest.setCodDaneVt(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGeoCodigoDane());
  
            if (cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen().equalsIgnoreCase("1")
                    && dirModifica.getBarrio() != null && !dirModifica.getBarrio().isEmpty()) {
                addressRequest.setNeighborhood(dirModifica.getBarrio().toUpperCase());
                cmtdireccionMgl.setBarrio(dirModifica.getBarrio().toUpperCase());
            } else {
                if (dirModifica.getBarrio() == null || dirModifica.getBarrio().isEmpty()) {
                    dirModifica.setBarrio("-");
                } else {
                    addressRequest.setNeighborhood(dirModifica.getBarrio().toUpperCase());
                    cmtdireccionMgl.setBarrio(dirModifica.getBarrio().toUpperCase());
                }

            }
            

            addressRequest.setState(cmtdireccionMgl.getCuentaMatrizObj().getDepartamento().getGpoNombre());
            ResponseMessage responseMessage = addressEJB.createAddress(addressRequest, usuarioVT, "CM", "true", dirModifica);
            if (responseMessage.getMessageText().equalsIgnoreCase(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA)) {
                CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                boolean isCalleCarrera = cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK");
                direccionesManager.actualizarDireccionMayaNueva(addressRequest.getAddress(), cmtdireccionMgl.getCuentaMatrizObj().getMunicipio(), barrio, isCalleCarrera);
            }

            // se construye la nueva direccion
            DireccionMgl direccionMgl = new DireccionMgl();
            direccionMgl.setDirId(new BigDecimal(responseMessage.getIdaddress().replace("s", "").replace("S", "").replace("d", "").replace("D", "").replace("d", "")));
            direccionMgl = direccionMglManager.findById(direccionMgl);

            /*Actualizando la tabla CmtDireccion*/
            cmtdireccionMgl.setTdiId(tipoDireccion);
            cmtdireccionMgl.setCalleRr(cmtdireccionSolMgl.getCalleRr());
            cmtdireccionMgl.setUnidadRr(cmtdireccionSolMgl.getUnidadRr());
            cmtdireccionMgl.setBarrio(barrio);
            cmtdireccionMgl.setDireccionObj(direccionMgl);

            // direcciones antiguas 
          if (cmtdireccionSolMgl.getDireccionAntigua() != null && !cmtdireccionSolMgl.getDireccionAntigua().isEmpty()) {
                if (cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {
                    CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                    DrDireccion drDireccionAntigua = direccionesManager.convertirDireccionStringADrDireccion(
                            cmtdireccionSolMgl.getDireccionAntigua(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoId());
                    if (drDireccionAntigua != null && cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen().equals("1")) {
                        drDireccionAntigua.setBarrio(barrio);
                    }
                    if (drDireccionAntigua != null) {
                        CmtDireccionMgl cmtDireccionMglAntigua = drDireccionAntigua.convertToCmtDireccionMgl();
                    }
                }
            }   

            if (isUpdate) {
                cmtdireccionMgl.setEstadoRegistro(1);
                cmtdireccionMgl.setComentario("Actualización de dirección");
                cmtdireccionMgl.setDireccionId(cmtdireccionMgl.getDireccionId());
                cmtdireccionMgl.setSubEdificioObj(subEdificioMgl);
                cmtDireccionesMglDaoImpl.updateCm(cmtdireccionMgl, usuario, perfil);
            } else {
                cmtdireccionMgl.setEstadoRegistro(1);
                cmtdireccionMgl.setComentario("Creación de dirección");
                cmtdireccionMgl.setDireccionId(null);
                cmtDireccionesMglDaoImpl.createCm(cmtdireccionMgl, usuario, perfil);
            }
            
                /*Actualizando la dirección de la cuenta Matriz o Sub-Edificio en RR*/
                subEdificioMgl = cmtSubEdificioMglManager.findById(subEdificioMgl);
                subEdificioMgl.setDireccion(direccion);
                cmtSubEdificioMglManager.update(subEdificioMgl, usuario, perfil, false, false);
            

            if (!messageError.isEmpty()) {
                throw new ApplicationExceptionCM(messageError, cmtdireccionMgl.getCuentaMatrizObj());
            }

        } catch (ApplicationExceptionCM | ApplicationException | ExceptionDB | IOException ex) {
            LOGGER.error("Error en actualizarDireccionCmOSubEdificio. EX000 " + ex.getMessage(), ex);
            throw new ApplicationExceptionCM("Error en actualizarDireccionCmOSubEdificio. EX000 " + ex.getMessage(), ex);
        }

        return cmtdireccionMgl;
    }

    public CmtDireccionMgl actualizar(CmtSubEdificioMgl cmtSubEdificioMgl, CmtDireccionMgl cmtdireccionMgl, DrDireccion drDireccion,
            String selectBarrio, ResponseValidarDireccionDto responseValidarDireccionDto, String direccion,
            String usuarioVT, boolean isUpdate, String usuario, int perfil, boolean desdeFichas) throws ApplicationExceptionCM, ApplicationException {
        try {
            AddressEJB addressEJB = new AddressEJB();
            DireccionMglManager direccionMglManager = new DireccionMglManager();
            CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
            cmtdireccionMgl.setTdiId(Constant.ID_TIPO_DIRECCION_ALTERNA);
            if (desdeFichas) {
                cmtdireccionMgl.setSubEdificioObj(cmtSubEdificioMgl);
            }
            if (cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen().equals("1") && cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {
                DetalleDireccionEntity detalleDireccionEntity;
                detalleDireccionEntity = drDireccion.convertToDetalleDireccionEntity();
                detalleDireccionEntity.setBarrio(selectBarrio);
                detalleDireccionEntity.setMultiOrigen(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen());
                DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity, cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(),
                        cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                responseValidarDireccionDto.setDireccionRREntity(direccionRRManager.getDireccion());
            }
            cmtdireccionMgl.setCalleRr(responseValidarDireccionDto.getDireccionRREntity().getCalle());
            cmtdireccionMgl.setUnidadRr(responseValidarDireccionDto.getDireccionRREntity().getNumeroUnidad());
            cmtdireccionMgl.setBarrio(selectBarrio);
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setAddress(direccion);
            addressRequest.setCity(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoNombre());
            addressRequest.setCodDaneVt(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGeoCodigoDane());
            if (cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen().equalsIgnoreCase("1")) {
                if (cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("IT")) {
                    selectBarrio = ".";
                }
                addressRequest.setNeighborhood(selectBarrio);
            }
            addressRequest.setState(cmtdireccionMgl.getCuentaMatrizObj().getDepartamento().getGpoNombre());
            ResponseMessage responseMessage = addressEJB.createAddress(addressRequest, usuarioVT, "CM", "true", drDireccion);
            if (responseMessage.getMessageText().equalsIgnoreCase(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA)) {
                CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                boolean isCalleCarrera = cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK");
                direccionesManager.actualizarDireccionMayaNueva(addressRequest.getAddress(), cmtdireccionMgl.getCuentaMatrizObj().getMunicipio(), selectBarrio, isCalleCarrera);
            }
            DireccionMgl direccionMgl = new DireccionMgl();
            direccionMgl.setDirId(new BigDecimal(responseMessage.getIdaddress().replace("s", "").replace("S", "").replace("d", "").replace("D", "").replace("d", "")));
            direccionMgl = direccionMglManager.findById(direccionMgl);
            cmtdireccionMgl.setDireccionObj(direccionMgl);
            siExistenSolictudesEnCurso(cmtdireccionMgl);
            if (responseValidarDireccionDto.getDireccionAntigua() != null && !responseValidarDireccionDto.getDireccionAntigua().isEmpty()) {
                if (cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {
                    CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                    DrDireccion drDireccionAntigua = direccionesManager.convertirDireccionStringADrDireccion(
                            responseValidarDireccionDto.getDireccionAntigua(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoId());
                    if (drDireccionAntigua != null && cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen().equals("1")) {
                        drDireccionAntigua.setBarrio(selectBarrio);
                    }
                    if (drDireccionAntigua != null) {
                        CmtDireccionMgl cmtDireccionMglAntigua = drDireccionAntigua.convertToCmtDireccionMgl();
                        cmtDireccionMglAntigua.setCuentaMatrizObj(cmtdireccionMgl.getCuentaMatrizObj());                        
                        siExistenSolictudesEnCurso(cmtDireccionMglAntigua);
                    }
                }
            }

            //valbuenayf inicio ajuste incidencia D33
            try {
                if (isUpdate) {
                    cmtdireccionMgl.setEstadoRegistro(1);
                    cmtdireccionMgl.setDireccionId(cmtdireccionMgl.getDireccionId());
                    cmtDireccionesMglDaoImpl.updateCm(cmtdireccionMgl, usuario, perfil);
                } else {
                    cmtdireccionMgl.setEstadoRegistro(1);
                    cmtdireccionMgl.setDireccionId(null);
                    cmtDireccionesMglDaoImpl.createCm(cmtdireccionMgl, usuario, perfil);
                }

                CmtSubEdificioMgl subEdificioMgl = cmtdireccionMgl.getCuentaMatrizObj().getSubEdificioGeneral();
                subEdificioMgl = cmtSubEdificioMglManager.findById(subEdificioMgl);
                cmtSubEdificioMglManager.update(subEdificioMgl, usuario, perfil, false, false);

            } catch (ApplicationException e) {
                LOGGER.error("Error en actualizar de CmtDireccionMglManager: " + e);
                throw new ApplicationException(e);
            }
            //valbuenayf fin ajuste incidencia D33
            //valbuenayf fin ajuste incidencia D33

            return cmtdireccionMgl;
        } catch (ExceptionDB | IOException ex) {
            LOGGER.error("Error en actualizar de CmtDireccionMglManager: " + ex);
            throw new ApplicationException("Error en actualizar de CmtDireccionMglManager: ", ex);
        }
        //valbuenayf fin ajuste incidencia D33
        //valbuenayf fin ajuste incidencia D33
    }

    public List<CmtCuentaMatrizMgl> findByDrDireccion(DrDireccion drDireccion, String comunidad)
            throws ApplicationException {
        return cmtDireccionesMglDaoImpl.findByDrDireccion(drDireccion, comunidad);
    }

    public List<AuditoriaDto> construirAuditoria(CmtDireccionMgl cmtDireccionMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtDireccionMgl, CmtDireccionAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtDireccionMgl, CmtDireccionAuditoriaMgl>();
        return utilsCMAuditoria.construirAuditoria(cmtDireccionMgl);
    }

    public void siExistenSolictudesEnCurso(CmtDireccionMgl cmtdireccionMgl) throws ApplicationException, ApplicationExceptionCM {
        try {
            String messageError = "";
            CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglManager = new CmtDireccionSolicitudMglManager();
            DrDireccionManager direccionManager = new DrDireccionManager();
            DrDireccion drDireccion = new DrDireccion();
            drDireccion.obtenerFromCmtDireccionMgl(cmtdireccionMgl);
            CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
            List<CmtCuentaMatrizMgl> listaCmtCuentaMatrizMgl;
            listaCmtCuentaMatrizMgl = cuentaMatrizMglManager.findCuentasMatricesByDrDireccion(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado(),drDireccion);
            
            List<CmtSolicitudCmMgl> listaCmtSolicitudCmMgl = cmtDireccionSolicitudMglManager.findByDrDireccion(drDireccion, cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGeoCodigoDane());
            List<Solicitud> listaSolicitudvt = direccionManager.findByDrDireccion(drDireccion, cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoId().toString());
            CmtCuentaMatrizMgl cuentaMatrizMglValue = null;
            if (listaCmtCuentaMatrizMgl != null && !listaCmtCuentaMatrizMgl.isEmpty()) {
                for (CmtCuentaMatrizMgl cm : listaCmtCuentaMatrizMgl) {
                    messageError += "La direccion nueva o antigua esta relacionada en la Cuenta matriz: " + cm.getNumeroCuenta().toString() + "\n";
                    cuentaMatrizMglValue = cm;
                }
            }
            if (listaCmtSolicitudCmMgl != null && !listaCmtSolicitudCmMgl.isEmpty()) {
                for (CmtSolicitudCmMgl csm : listaCmtSolicitudCmMgl) {
                    messageError += "La direccion nueva o antigua esta relacionada en la solictud de Cuenta matriz: " + csm.getSolicitudCmId().toString() + "\n";
                }
            }
            if (listaSolicitudvt != null && !listaSolicitudvt.isEmpty()) {
                for (Solicitud svt : listaSolicitudvt) {
                    messageError += "La direccion nueva o antigua esta relacionada en una solicitud de direccion: " + svt.getIdSolicitud().toString() + "\n";
                }
            }

            if (!messageError.isEmpty()) {
                throw new ApplicationExceptionCM(messageError + "No se puede crear la direccion", cuentaMatrizMglValue);
            }

        } catch (ApplicationException | ApplicationExceptionCM ex) {
            LOGGER.error("Error en siExistenSolictudesEnCurso. EX000 " + ex.getMessage(), ex);
            if (ex instanceof ApplicationExceptionCM) {
                throw ex;
            } else {
                throw new ApplicationExceptionCM("Error en siExistenSolictudesEnCurso. EX000 " + ex.getMessage(), ex);
            }
        }

    }

    /**
     * M&eacute;todo para encontrar una direcci&oacute;n en base de datos por la
     * direcci&oacute;n digitada
     *
     * @param direccion {@link CmtDireccionMgl} direcci&oacute;n digitada por el
     * usuario
     * @return {@link List}&lt{@link CmtDireccionMgl}> Direcciones encontradas
     * en base de datos
     * @throws ApplicationException Excepci&oacute;n lanzada por las consultas
     */
    public List<CmtDireccionMgl> findByDireccion(CmtDireccionMgl direccion) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.findByDireccion(direccion);
    }

    public HhppMgl actualizarDireccionHHPPModCm(HhppMgl hhppMod, HhppMgl hhppModOld,
            CmtDireccionMgl cmtdireccionMgl, String direccionCM,
            String nombreSubEdi, String solicitud, String tipoSolicitud,
            String usuarioVT, String usuario, int perfil, CmtSubEdificioMgl ss,
            DrDireccion dirCambio, boolean esSubPropiaDir, String nombreSubedificio) throws ApplicationException {

        HhppMglManager hhppMglManager = new HhppMglManager();
        CmtCuentaMatrizRRMglManager rRMglManager = new CmtCuentaMatrizRRMglManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl cmtBasicaMgl;
        String idAddress = "";
        /*Validando si es un nombre estandar*/
        if (!esNombreSubEdificioEstandar(nombreSubEdi)) {
            nombreSubEdi = "";
        }

        /*Estandarizando direccion a formato RR */
        DireccionRRManager direccionRRManager
                = estandarizarNombreSubEdificioDireccion(cmtdireccionMgl, nombreSubEdi);
        LOGGER.error("edificio" + direccionRRManager.getDireccionEntity().getCptiponivel1() + " " + direccionRRManager.getDireccionEntity().getCpvalornivel1());

        /*se guardar las direcciones de salaventas y campamentos adjuntas a la direccion principal en mgl*/
        // los subedificios con su propia direccion no se les cambia la direccion ya que no es la principal solo salaventas y campamentos
        if (!esSubPropiaDir) {
            if (hhppModOld.getHhpApart().equals("CAMPAMENTO") || hhppModOld.getHhpApart().equals("SALAVENTAS")) {
                try {
                    guardarEnDetallada(hhppMod, hhppModOld,
                            cmtdireccionMgl, direccionCM,
                            nombreSubEdi, solicitud, tipoSolicitud,
                            usuarioVT, usuario, perfil, ss,
                            dirCambio, esSubPropiaDir);
                } catch (IOException | CloneNotSupportedException | ExceptionDB ex) {
                    String msg = "Error al momento de actualizar la dirección. EX000 '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  ex);
                }
            } else {
                try {
                    guardarEnDetallada(hhppMod, hhppModOld,
                            cmtdireccionMgl, direccionCM,
                            nombreSubEdi, solicitud, tipoSolicitud,
                            usuarioVT, usuario, perfil, ss,
                            dirCambio, esSubPropiaDir);
                } catch (IOException | CloneNotSupportedException | ExceptionDB ex) {
                    String msg = "Error al momento de actualizar la dirección. EX000 '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  ex);
                }
            }
            /*Actualizando la direccion del HHPP en el Repositorio*/
            hhppMod.setHhpCalle(direccionRRManager.getDireccion().getCalle());
            hhppMod.setHhpPlaca(direccionRRManager.getDireccion().getNumeroUnidad());
            hhppMod.setHhpApart(hhppMod.getHhpApart());
            hhppMglManager.update(hhppMod);

        } else {
            String calleConstruida = "";
            String calleFinal = "";
            String calleCad = " ";
            String calleSubNueva = "";
            String apartamentoOtros = "";
            if (cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {
                apartamentoOtros = hhppMod.getHhpApart();
                String calleNueva = "";
                calleSubNueva = hhppModOld.getHhpCalle().substring(hhppModOld.getHhpCalle().lastIndexOf(" EN ") + 3);
                calleNueva = "EN " + calleSubNueva.trim();
                calleCad = direccionRRManager.getDireccion().getCalle();
                DireccionRRManager direccionRRManagerSubEnt = new DireccionRRManager(cmtdireccionMgl.getDetalleDireccionEntity(),
                        cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                DireccionRREntity detalleDirSubEntRR = direccionRRManagerSubEnt.getDireccion();
                calleCad = detalleDirSubEntRR.getCalle() + " " + calleNueva;

            } else if (cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("BM")) {
                apartamentoOtros = hhppMod.getHhpApart();
                String calleNueva = "";
                calleSubNueva = hhppModOld.getHhpCalle().substring(hhppModOld.getHhpCalle().lastIndexOf(" EN ") + 3);
                calleNueva = "EN " + calleSubNueva.trim();
                DireccionRRManager direccionRRManagerSubEnt = new DireccionRRManager(cmtdireccionMgl.getDetalleDireccionEntity(),
                        cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                DireccionRREntity detalleDirSubEntRR = direccionRRManagerSubEnt.getDireccion();
                calleCad = detalleDirSubEntRR.getCalle() + " " + calleNueva;
            } else {
                apartamentoOtros = hhppMod.getHhpApart();
                String calleNueva = "";
                calleSubNueva = hhppModOld.getHhpCalle().substring(hhppModOld.getHhpCalle().lastIndexOf(" EN ") + 3);
                calleNueva = "EN " + calleSubNueva.trim();
                DireccionRRManager direccionRRManagerSubEnt = new DireccionRRManager(cmtdireccionMgl.getDetalleDireccionEntity(),
                        cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                DireccionRREntity detalleDirSubEntRR = direccionRRManagerSubEnt.getDireccion();
                calleCad = detalleDirSubEntRR.getCalle() + " " + calleNueva;
            }
            hhppMod.setHhpCalle(calleCad);
            hhppMod.setHhpPlaca(direccionRRManager.getDireccion().getNumeroUnidad());
            hhppMod.setHhpApart(hhppMod.getHhpApart());
            hhppMglManager.update(hhppMod);
        }

        String calleConstruida = "";
        String calleFinal = "";
        String calleCad = " ";
        String calleSubNueva = "";
        String apartamentoOtros = "";
        String placaCasas = "";
        // variable para desactivar la modificacion en RR
        boolean habilitarRR = false;
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }
        if (habilitarRR) {
            if (ss.getCuentaMatrizObj().getNumeroCuenta().
                    compareTo(Constant.NUMERO_CM_MGL) != 0) {
                /*Actualizando la dirección del HHPP en detallada*/

                DireccionRRManager manager = new DireccionRRManager(true);
                // valor de los hhpp presentes en RR antes del cambio
                String comunidad = hhppModOld.getHhpComunidad();
                String division = hhppModOld.getHhpDivision();
                String placa = hhppModOld.getHhpPlaca();
                String calle = hhppModOld.getHhpCalle();
                String apartamento = hhppModOld.getHhpApart();

                // se consulta uno a uno los hhpp en rr por idHHPP
                if (hhppModOld.getHhpIdrR() != null) {
                    HhppResponseRR responseHhppRR = manager.getHhppByIdRR(hhppModOld.getHhpIdrR());
                    if (responseHhppRR.getTipoMensaje() != null
                            && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {

                        comunidad = responseHhppRR.getComunidad();
                        division = responseHhppRR.getDivision();
                        calle = responseHhppRR.getStreet();
                        placa = responseHhppRR.getHouse();
                        apartamento = responseHhppRR.getApartamento();
                    } else {
                        LOGGER.error("Ocurrio un error consultando la data del hhpp en RR");

                    }
                }

                // cambio de direccion a hhpp con subedificios con su propia direccion(con entrada)
                if (esSubPropiaDir) {
                    apartamentoOtros = hhppMod.getHhpApart();
                    if (hhppMod.getHhpApart().length() > 10) {
                        apartamentoOtros = hhppMod.getHhpApart();
                        apartamentoOtros = apartamentoOtros.substring(0, 10);
                    } else {
                        apartamentoOtros = hhppMod.getHhpApart();
                    }
                    if (hhppModOld.getHhpApart().contentEquals("CASA")) {
                        placaCasas = apartamento;
                    } else {
                        placaCasas = hhppModOld.getHhpPlaca();
                    }
                    if (cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {

                        String calleNueva = "";
                        calleSubNueva = calle.substring(calle.lastIndexOf(" EN ") + 3);
                        calleNueva = "EN " + calleSubNueva.trim();
                        DireccionRRManager direccionRRManagerSubEnt = new DireccionRRManager(cmtdireccionMgl.getDetalleDireccionEntity(),
                                cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                        DireccionRREntity detalleDirSubEntRR = direccionRRManagerSubEnt.getDireccion();
                        calleCad = detalleDirSubEntRR.getCalle() + " " + calleNueva;
                    } else if (cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("BM")) {

                        String calleNueva = "";
                        calleSubNueva = calle.substring(calle.lastIndexOf(" EN ") + 3);
                        calleNueva = "EN " + calleSubNueva.trim();
                        DireccionRRManager direccionRRManagerSubEnt = new DireccionRRManager(cmtdireccionMgl.getDetalleDireccionEntity(),
                                cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                        DireccionRREntity detalleDirSubEntRR = direccionRRManagerSubEnt.getDireccion();
                        calleCad = detalleDirSubEntRR.getCalle() + " " + calleNueva;
                    } else {

                        String calleNueva = "";
                        calleSubNueva = calle.substring(calle.indexOf(" EN ") + 3);
                        calleNueva = "EN " + calleSubNueva.trim();
                        DireccionRRManager direccionRRManagerSubEnt = new DireccionRRManager(cmtdireccionMgl.getDetalleDireccionEntity(),
                                cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                        DireccionRREntity detalleDirSubEntRR = direccionRRManagerSubEnt.getDireccion();
                        calleCad = detalleDirSubEntRR.getCalle() + " " + calleNueva;
                        if (calleCad.length() > 50) {
                            calleCad = calleCad.substring(0, 49);
                        }
                        // para actualizar el nombre del subedificio de  hhpp salaventas y campamentos cambiamos el codigo rr a torre 1 0001
                        if (hhppModOld.getHhpApart().equals("CAMPAMENTO") || hhppModOld.getHhpApart().equals("SALAVENTAS")) {
                            ss.setCodigoRr("0001");
                            ss.setNombreEntSubedificio(calleNueva);
                        } else {
                            ss.setNombreEntSubedificio(calleNueva);
                        }
                        // como se ajusto la longitud de la calle a 50 caracteres se actualiza el nombre del subedificio a la nueva logitud 
                        rRMglManager.updateInfoAdicionalSubEdificio(ss, true, usuario);
                    }

                    calleFinal = calleCad;
                    calleConstruida = calleFinal;
                } else {
                    if (hhppModOld.getHhpApart().contentEquals("CASA")) {
                        placaCasas = apartamento;
                    } else {
                        placaCasas = hhppModOld.getHhpPlaca();
                    }

                    if (calle.contains("EN ")) {
                        apartamentoOtros = hhppMod.getHhpApart();
                        if (hhppMod.getHhpApart().length() > 10) {
                            apartamentoOtros = hhppMod.getHhpApart();
                            apartamentoOtros = apartamentoOtros.substring(0, 10);
                        } else {
                            apartamentoOtros = hhppMod.getHhpApart();
                        }

                        String calleNueva = "";
                        calleSubNueva = calle.substring(calle.lastIndexOf(" EN ") + 3);
                        calleNueva = "EN" + " " + calleSubNueva.trim();
                         DireccionRRManager direccionRRManagerSubEnt = new DireccionRRManager(cmtdireccionMgl.getDetalleDireccionEntity(),
                                cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                        DireccionRREntity detalleDirSubEntRR = direccionRRManagerSubEnt.getDireccion();
                        calleConstruida = detalleDirSubEntRR.getCalle() + " " + calleNueva;

                        if (calleConstruida.length() > 50) {
                            calleConstruida = calleConstruida.substring(0, 49);
                        }
                        // para actualizar el nombre del subedificio de  hhpp salaventas y campamentos cambiamos el codigo rr a torre 1 0001
                        if (hhppModOld.getHhpApart().equals("CAMPAMENTO") || hhppModOld.getHhpApart().equals("SALAVENTAS")) {
                            cmtBasicaMgl = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SIN_VISITA_TECNICA);
                            ss.setCodigoRr("0001");
                            ss.setEstadoSubEdificioObj(cmtBasicaMgl);
                            ss.setNombreEntSubedificio(calleNueva);
                        } else {
                            ss.setNombreEntSubedificio(calleNueva);
                        }
                        // como se ajusto la longitud de la calle a 50 caracteres se actualiza el nombre del subedificio a la nueva logitud 
                        rRMglManager.updateInfoAdicionalSubEdificio(ss, true, usuario);

                    } else {
                        // se extrae el nombre del subedificio completo 
                        String[] nombreSubEdificioSep = null;
                        apartamentoOtros = hhppMod.getHhpApart();
                        if (hhppMod.getHhpApart().length() > 10) {
                            apartamentoOtros = hhppMod.getHhpApart();
                            apartamentoOtros = apartamentoOtros.substring(0, 10);
                        } else {
                            apartamentoOtros = hhppMod.getHhpApart();
                        }
                        // validar si es unico edificio o multiedificio para cambiar el nombre del subedificio
                        if (ss.getCmtCuentaMatrizMglObj().getListCmtSubEdificioMglActivos().size() > 1) {
                            if (ss.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                                    && !ss.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                nombreSubEdificioSep = ss.getNombreSubedificio().split(" ");
                            } else {
                                nombreSubEdificioSep = nombreSubedificio.split(" ");
                            }
                            // octener el nombre del subedificio dividido
                            if (nombreSubEdificioSep.length == 2) {
                                cmtdireccionMgl.setCpTipoNivel1(nombreSubEdificioSep[0]);
                                cmtdireccionMgl.setCpValorNivel1(nombreSubEdificioSep[1]);

                            } else if (nombreSubEdificioSep.length == 4) {
                                cmtdireccionMgl.setCpTipoNivel1(nombreSubEdificioSep[0]);
                                cmtdireccionMgl.setCpValorNivel1(nombreSubEdificioSep[1]);
                                cmtdireccionMgl.setCpTipoNivel2(nombreSubEdificioSep[2]);
                                cmtdireccionMgl.setCpValorNivel2(nombreSubEdificioSep[3]);
                            } else if (nombreSubEdificioSep.length == 6) {
                                cmtdireccionMgl.setCpTipoNivel1(nombreSubEdificioSep[0]);
                                cmtdireccionMgl.setCpValorNivel1(nombreSubEdificioSep[1]);
                                cmtdireccionMgl.setCpTipoNivel2(nombreSubEdificioSep[2]);
                                cmtdireccionMgl.setCpValorNivel2(nombreSubEdificioSep[3]);
                                cmtdireccionMgl.setCpTipoNivel3(nombreSubEdificioSep[4]);
                                cmtdireccionMgl.setCpValorNivel3(nombreSubEdificioSep[5]);
                            } else {
                                cmtdireccionMgl.setCpTipoNivel1(nombreSubEdificioSep[0]);
                                cmtdireccionMgl.setCpValorNivel1(nombreSubEdificioSep[1]);
                                cmtdireccionMgl.setCpTipoNivel2(nombreSubEdificioSep[2]);
                                cmtdireccionMgl.setCpValorNivel2(nombreSubEdificioSep[3]);
                                cmtdireccionMgl.setCpTipoNivel3(nombreSubEdificioSep[4]);
                                cmtdireccionMgl.setCpValorNivel3(nombreSubEdificioSep[5]);
                                cmtdireccionMgl.setCpTipoNivel4(nombreSubEdificioSep[6]);
                                cmtdireccionMgl.setCpValorNivel4(nombreSubEdificioSep[7]);
                            }
                        }

                        DireccionRRManager direccionRRManagerSubEnt = new DireccionRRManager(cmtdireccionMgl.getDetalleDireccionEntity(),
                                cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen(), cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado());
                        DireccionRREntity detalleDirSubEntRR = direccionRRManagerSubEnt.getDireccion();
                        if (hhppModOld.getHhpApart().equals("CAMPAMENTO") || hhppModOld.getHhpApart().equals("SALAVENTAS")) {
                            calleConstruida = detalleDirSubEntRR.getCalle();
                        } else {

                            if (ss.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                                    && ss.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                calleConstruida = detalleDirSubEntRR.getCalle().toUpperCase();
                            } else {
                                calleConstruida = detalleDirSubEntRR.getCalle().toUpperCase();
                            }

                        }

                    }
                }

                DireccionRREntity direccionRREntity
                        = direccionRRManager.cambiarDirHHPPRRCm(comunidad,
                                division, placa,
                                calle, apartamento,
                                hhppMod.getHhpComunidad(), hhppMod.getHhpDivision(),
                                placaCasas,
                                calleConstruida, apartamentoOtros,
                                solicitud, usuarioVT, tipoSolicitud, "",
                                cmtdireccionMgl.getCodTipoDir(),
                                idAddress);
            }

            /*Actualizando la direccion del HHPP en el Repositorio*/
            hhppMod.setHhpCalle(calleConstruida);
            hhppMod.setHhpPlaca(direccionRRManager.getDireccion().getNumeroUnidad());
            hhppMod.setHhpApart(hhppMod.getHhpApart());
            hhppMglManager.update(hhppMod);
        }
        return hhppMod;
    }

    /**
     * Metodo para consultar las direcciones detalladas de una cm Autor: Lenis
     * Cardenas
     *
     * @param cmtCuentaMatrizMgl
     * @param HhppMod
     * @throws ApplicationException
     * @throws com.jlcg.db.exept.ExceptionDB
     * @throws java.lang.CloneNotSupportedException
     */
    public void guardarEnDetallada(HhppMgl hhppMod, HhppMgl hhppModOld,
            CmtDireccionMgl cmtdireccionMgl, String direccionCM,
            String nombreSubEdi, String solicitud, String tipoSolicitud,
            String usuarioVT, String usuario, int perfil, CmtSubEdificioMgl ss,
            DrDireccion dirCambio, boolean esSubPropiaDir) throws ApplicationException, IOException, CloneNotSupportedException, ExceptionDB {
        AddressRequest addressRequest = new AddressRequest();
        SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
        CmtDireccionDetalleMglManager detalleMglManager = new CmtDireccionDetalleMglManager();
        CmtCuentaMatrizRRMglManager rRMglManager = new CmtCuentaMatrizRRMglManager();
        AddressEJB addressEJB = new AddressEJB();
        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
        DireccionMglManager direccionMglManager = new DireccionMglManager();
        DireccionMglDaoImpl direccionMglDaoImpl = new DireccionMglDaoImpl();
        String idAddress = "";
        String barrio = cmtdireccionMgl.obtenerBarrio();
        String direccion = "";
        BigDecimal estratoCM = hhppMod.getDireccionObj().getDirEstrato();
        if (hhppMod.getHhpApart() != null) {
            direccion = direccionCM.concat(" ").concat(nombreSubEdi == null
                    ? "" : nombreSubEdi).concat(" ").concat(hhppMod.getHhpApart());
        } else {
            direccion = direccionCM.concat(" ").concat(nombreSubEdi == null
                    ? "" : nombreSubEdi).concat(" ").concat(" ");
        }
        addressRequest.setAddress(direccion);
        addressRequest.setCity(cmtdireccionMgl.getCuentaMatrizObj().getMunicipio().getGpoNombre());
        addressRequest.setCodDaneVt(cmtdireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGeoCodigoDane());
        if (cmtdireccionMgl.getCuentaMatrizObj().getMunicipio().getGpoMultiorigen().equalsIgnoreCase("1") && dirCambio.getBarrio() != null) {
            addressRequest.setNeighborhood(dirCambio.getBarrio());
        } else {
            if ((dirCambio.getIdTipoDireccion().equalsIgnoreCase("BM")
                    || dirCambio.getIdTipoDireccion().equalsIgnoreCase("IT")
                    || dirCambio.getIdTipoDireccion().equalsIgnoreCase("CK")) && dirCambio.getBarrio() == null) {
                addressRequest.setNeighborhood("-");
                dirCambio.setBarrio("-");
            } else {
                addressRequest.setNeighborhood(dirCambio.getBarrio());
            }
        }

        /////////////se consulta los hhpp antiguos en direccion detallada//////////////////////
        CmtDireccionDetalladaMgl direccionDetalladaMgl = detalleMglManager.findByHhPP(hhppModOld);
        DrDireccion dirCambioSub = dirCambio.clone();
        if (direccionDetalladaMgl != null) {

            if (direccionDetalladaMgl.getCpTipoNivel1() != null && !direccionDetalladaMgl.getCpTipoNivel1().isEmpty()) {
                if (direccionDetalladaMgl.getCpValorNivel1() != null && !direccionDetalladaMgl.getCpValorNivel1().isEmpty()) {
                    dirCambioSub.setCpTipoNivel1(direccionDetalladaMgl.getCpTipoNivel1());
                    dirCambioSub.setCpValorNivel1(direccionDetalladaMgl.getCpValorNivel1());
                }
            }

            if (direccionDetalladaMgl.getCpTipoNivel2() != null && !direccionDetalladaMgl.getCpTipoNivel2().isEmpty()) {
                if (direccionDetalladaMgl.getCpValorNivel2() != null && !direccionDetalladaMgl.getCpValorNivel2().isEmpty()) {
                    dirCambioSub.setCpTipoNivel2(direccionDetalladaMgl.getCpTipoNivel2());
                    dirCambioSub.setCpValorNivel2(direccionDetalladaMgl.getCpValorNivel2());
                }
            }
            if (direccionDetalladaMgl.getCpTipoNivel3() != null && !direccionDetalladaMgl.getCpTipoNivel3().isEmpty()) {
                if (direccionDetalladaMgl.getCpValorNivel3() != null && !direccionDetalladaMgl.getCpValorNivel3().isEmpty()) {
                    dirCambioSub.setCpTipoNivel3(direccionDetalladaMgl.getCpTipoNivel3());
                    dirCambioSub.setCpValorNivel3(direccionDetalladaMgl.getCpValorNivel3());
                }
            }
            if (direccionDetalladaMgl.getCpTipoNivel4() != null && !direccionDetalladaMgl.getCpTipoNivel4().isEmpty()) {
                if (direccionDetalladaMgl.getCpValorNivel4() != null && !direccionDetalladaMgl.getCpValorNivel4().isEmpty()) {
                    dirCambioSub.setCpTipoNivel4(direccionDetalladaMgl.getCpTipoNivel4());
                    dirCambioSub.setCpValorNivel4(direccionDetalladaMgl.getCpValorNivel4());
                }
            }
            if (direccionDetalladaMgl.getCpTipoNivel5() != null && !direccionDetalladaMgl.getCpTipoNivel5().isEmpty()) {
                if (direccionDetalladaMgl.getCpValorNivel5() != null && !direccionDetalladaMgl.getCpValorNivel5().isEmpty()) {
                    dirCambioSub.setCpTipoNivel5(direccionDetalladaMgl.getCpTipoNivel5());
                    dirCambioSub.setCpValorNivel5(direccionDetalladaMgl.getCpValorNivel5());
                }
            }
            if (direccionDetalladaMgl.getCpTipoNivel6() != null && !direccionDetalladaMgl.getCpTipoNivel6().isEmpty()) {
                if (direccionDetalladaMgl.getCpValorNivel6() != null && !direccionDetalladaMgl.getCpValorNivel6().isEmpty()) {
                    dirCambioSub.setCpTipoNivel6(direccionDetalladaMgl.getCpTipoNivel6());
                    dirCambioSub.setCpValorNivel6(direccionDetalladaMgl.getCpValorNivel6());
                }
            }
        }

        addressRequest.setState(cmtdireccionMgl.getCuentaMatrizObj().getDepartamento().getGpoNombre());

        // creando direcciones en direccion detallada 
        if (!esSubPropiaDir || !hhppModOld.getHhpApart().equalsIgnoreCase("CASA")) {
            ResponseMessage responseMessage = addressEJB.createAddress(addressRequest, usuarioVT, "CM", "true", dirCambioSub);
            if (responseMessage.getMessageText().equalsIgnoreCase(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA)) {
                CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                boolean isCalleCarrera = cmtdireccionMgl.getCodTipoDir().equalsIgnoreCase("CK");
                direccionesManager.actualizarDireccionMayaNueva(addressRequest.getAddress(), cmtdireccionMgl.getCuentaMatrizObj().getMunicipio(), barrio, isCalleCarrera);
            }

            if (responseMessage.getMessageType().equalsIgnoreCase("ERROR")) {
                if (responseMessage.getIdaddress() == null
                        || !(responseMessage.getIdaddress().toUpperCase().contains("D")
                        || responseMessage.getIdaddress().toUpperCase().contains("S"))) {
                    throw new ApplicationException(" " + responseMessage.getMessageText());
                }

            }
            // se actualiza el estrato con el valor de la cuenta matriz actual
            if (responseMessage.getIdaddress().toUpperCase().contains("D")) {
                DireccionMgl direccionMglCreada = new DireccionMgl();
                BigDecimal direccionId
                        = new BigDecimal(responseMessage.getIdaddress().replace("d", "").replace("D", ""));
                direccionMglCreada.setDirId(direccionId);
                direccionMglCreada = direccionMglManager.findById(direccionMglCreada);
                if (dirCambioSub.getCpTipoNivel5() != null && dirCambioSub.getCpTipoNivel5().equalsIgnoreCase("LOCAL")
                        || dirCambioSub.getCpTipoNivel5() != null && dirCambioSub.getCpTipoNivel5().equalsIgnoreCase("BODEGA")
                        || dirCambioSub.getCpTipoNivel5() != null && dirCambioSub.getCpTipoNivel5().equalsIgnoreCase("OFICINA")
                        || dirCambioSub.getCpTipoNivel5() != null && dirCambioSub.getCpTipoNivel5().equalsIgnoreCase("PISO + LOCAL")) {
                    direccionMglCreada.setDirEstrato(BigDecimal.ZERO);
                } else {
                    direccionMglCreada.setDirEstrato(hhppMod.getDireccionObj().getDirEstrato());
                }
                direccionMglDaoImpl.update(direccionMglCreada);
                hhppMod.setDireccionObj(direccionMglCreada);
            } else {
                DireccionMgl direccionMglCreada = new DireccionMgl();
                BigDecimal subDireccionId
                        = new BigDecimal(responseMessage.getIdaddress().replace("s", "").replace("S", ""));
                SubDireccionMgl subDireccionMgl = subDireccionMglManager.findById(subDireccionId);
                direccionMglCreada.setDirId(subDireccionMgl.getDirId());
                if (dirCambioSub.getCpTipoNivel5() != null && dirCambioSub.getCpTipoNivel5().equalsIgnoreCase("LOCAL")
                        || dirCambioSub.getCpTipoNivel5() != null && dirCambioSub.getCpTipoNivel5().equalsIgnoreCase("BODEGA")
                        || dirCambioSub.getCpTipoNivel5() != null && dirCambioSub.getCpTipoNivel5().equalsIgnoreCase("OFICINA")
                        || dirCambioSub.getCpTipoNivel5() != null && dirCambioSub.getCpTipoNivel5().equalsIgnoreCase("PISO + LOCAL")) {
                    subDireccionMgl.setSdiEstrato(BigDecimal.ZERO);
                } else {
                    subDireccionMgl.setSdiEstrato(hhppMod.getSubDireccionObj().getSdiEstrato());
                }
                subDireccionMglManager.update(subDireccionMgl);
                hhppMod.setDireccionObj(direccionMglCreada);
                hhppMod.setSubDireccionObj(subDireccionMgl);

            }
            idAddress = responseMessage.getIdaddress();
        }
    }

    /**
     * Metodo para consultar las direcciones aletrnas de una cm Autor: Victor
     * Bocanegra
     *
     * @param cuentaMatriz
     * @return List<CmtDireccionMgl>
     * @throws ApplicationException
     */
    public List<CmtDireccionMgl> findDireccionesByCuentaMatrizAlternas(CmtCuentaMatrizMgl cuentaMatriz)
            throws ApplicationException {

        return cmtDireccionesMglDaoImpl.findDireccionesByCuentaMatrizAlternas(cuentaMatriz);
    }

    /**
     * Metodo para consultar las direccion alternas de una cuenta matriz
     * edificio general Autor: Juan David Hernandez
     *
     * @param cuentaMatrizId
     * @param subEdificioId
     * @return List<CmtDireccionMgl>
     * @throws ApplicationException
     */
    public List<CmtDireccionMgl> findDireccionAlternaByCmSub(BigDecimal cuentaMatrizId,
            BigDecimal subEdificioId) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.findDireccionAlternaByCmSub(cuentaMatrizId, subEdificioId);
    }

    /**
     * Metodo para consultar la cuenta matriz Autor: cardenaslb
     *
     * @param cuentaMatrizId
     * @return CmtDireccionMgl
     * @throws ApplicationException
     */
    public CmtDireccionMgl findCmtDireccion(BigDecimal cuentaMatrizId) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.findCmtDireccion(cuentaMatrizId);
    }
    
    /**
     * Metodo para consultar una direccion de ccmm por direccionMGL Autor:
     * Victor Bocanegra
     *
     * @param direccionMgl
     * @return List<CmtDireccionMgl>
     * @throws ApplicationException
     */
    public List<CmtDireccionMgl> findDireccionByDirMgl(DireccionMgl direccionMgl)
            throws ApplicationException {
        return cmtDireccionesMglDaoImpl.findDireccionByDirMgl(direccionMgl);
    }
    
    /**
     * Metodo para consultar una direccion de ccmm por id direccionMGL Autor:
     * Victor Bocanegra
     *
     * @param idDireccionMgl
     * @return CmtDireccionMgl
     * @throws ApplicationException
     */
    public CmtDireccionMgl findCmtIdDireccion(BigDecimal idDireccionMgl) throws ApplicationException {
        return cmtDireccionesMglDaoImpl.findCmtIdDireccion(idDireccionMgl);
    }    
    
    public List<CmtDireccionMgl> findDireccionAlternaByDireccionId(BigDecimal idDireccion) throws ApplicationException {
        List<CmtDireccionMgl> resulList;
        CmtDireccionesMglDaoImpl daoImpl = new CmtDireccionesMglDaoImpl();
        resulList = daoImpl.findDireccionAlternaByDireccionId(idDireccion);
        return resulList;
    }
}
