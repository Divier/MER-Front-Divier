package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.HhppVirtualMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtSolictudHhppMglDaoImpl;
import co.com.claro.mgl.dtos.ResponseCreateHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitudTrasladoHhppBloqueado;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;


/**
 * Manager de Solicitud HHPP
 *
 * @author gilaj
 */
public class CmtSolictudHhppMglManager {

    private CmtSolictudHhppMglDaoImpl cmtSolictudHhppMglDaoImpl = new CmtSolictudHhppMglDaoImpl();
    private DireccionRRManager direccionRRManager = new DireccionRRManager(true);
    private CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
    private CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
    private HhppMglManager hhppMglManager = new HhppMglManager();
    private static final Logger LOGGER = LogManager.getLogger(CmtSolictudHhppMglManager.class);

    /**
     * Lista toda la informacion de solicitudes HHPP
     *
     * @author Antonio Gil
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findAll() throws ApplicationException {
        List<CmtSolicitudHhppMgl> resultList;
        resultList = cmtSolictudHhppMglDaoImpl.findAll();
        return resultList;
    }

    /**
     * Lista toda la informacion de solicitudes HHPP
     *
     * @author Antonio Gil
     * @param hhpId
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findByHhpp(HhppMgl hhpId) throws ApplicationException {
        return cmtSolictudHhppMglDaoImpl.findByHhpp(hhpId);
    }

    /**
     * Lista toda la informacion de solicitudes cm
     *
     * @author Antonio Gil
     * @param solicitudId
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findBySolicitud(CmtSolicitudCmMgl solicitudId) throws ApplicationException {
        return cmtSolictudHhppMglDaoImpl.findBySolicitud(solicitudId);
    }

    /**
     * Lista toda la informacion de las solicitudes hhpp por subedificios
     *
     * @author Antonio Gil
     * @param subEdificioId
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findBySubEdificio(CmtSubEdificioMgl subEdificioId) throws ApplicationException {
        return cmtSolictudHhppMglDaoImpl.findBySubEdificio(subEdificioId);
    }

    /**
     * Insert solicitudes HHPP
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return Object
     * @throws ApplicationException
     */
    public CmtSolicitudHhppMgl create(CmtSolicitudHhppMgl cmtSolicitudHhppMgl, String user, int perfil) throws ApplicationException {
        return cmtSolictudHhppMglDaoImpl.createCm(cmtSolicitudHhppMgl, user, perfil);
    }

    /**
     * Update solicitudes HHPP
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return Object
     * @throws ApplicationException
     */
    public CmtSolicitudHhppMgl update(CmtSolicitudHhppMgl cmtSolicitudHhppMgl, String user, int perfil) throws ApplicationException {
        return cmtSolictudHhppMglDaoImpl.updateCm(cmtSolicitudHhppMgl, user, perfil);
    }

    /**
     * Validador Direccion Nvl5 Nvl6
     *
     * @author Antonio Gil
     * @param cmtSolicitudHhppMgl {@link CmtSolicitudHhppMgl}
     * @return {@link String}
     * @throws ApplicationException Excepción personalizada de la App
     */
    public String ValidadorDireccionHHPP(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) throws ApplicationException {
        boolean bandera = false;
        String newApto = null;
        DetalleDireccionEntity detalleDireccionEntity = new DetalleDireccionEntity();

        if (StringUtils.isNotBlank(cmtSolicitudHhppMgl.getOpcionNivel5()) && StringUtils.isNotBlank(cmtSolicitudHhppMgl.getValorNivel5())) {
            detalleDireccionEntity.setCptiponivel5(cmtSolicitudHhppMgl.getOpcionNivel5());
            detalleDireccionEntity.setCpvalornivel5(cmtSolicitudHhppMgl.getValorNivel5());
            bandera = true;
        }

        if (!bandera) {
            return newApto;
        }

        if (cmtSolicitudHhppMgl.getOpcionNivel5().contains("+") && StringUtils.isNotBlank(cmtSolicitudHhppMgl.getOpcionNivel6())
                && StringUtils.isNotBlank(cmtSolicitudHhppMgl.getValorNivel6())) {
            detalleDireccionEntity.setCptiponivel6(cmtSolicitudHhppMgl.getOpcionNivel6());
            detalleDireccionEntity.setCpvalornivel6(cmtSolicitudHhppMgl.getValorNivel6());
        }

        newApto = direccionRRManager.generarNumAptoBMRR(detalleDireccionEntity);
        CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdificioMglManager.findById(cmtSolicitudHhppMgl.getCmtSubEdificioMglObj());
        List<HhppMgl> listaHhpp = cmtSubEdificioMgl.getListHhpp();

        if (CollectionUtils.isNotEmpty(listaHhpp) && StringUtils.isNotBlank(newApto)) {
            BigDecimal basicaId = Optional.of(cmtSolicitudHhppMgl).map(CmtSolicitudHhppMgl::getCmtSolicitudCmMglObj)
                    .map(CmtSolicitudCmMgl::getBasicaIdTecnologia).map(CmtBasicaMgl::getBasicaId).orElse(null);

            for (HhppMgl hhpp : listaHhpp) {
                if (StringUtils.isNotBlank(hhpp.getHhpApart()) && hhpp.getHhpApart().trim().equalsIgnoreCase(newApto.trim())
                        && Objects.nonNull(basicaId) && basicaId.equals(hhpp.getNodId().getNodTipo().getBasicaId())) {
                    newApto = null;
                    break;
                }
            }
        }

        return newApto;
    }

    /**
     * Verifica si existe el HHPP real en MER con la información de apartamento,
     * para poder realizar el traslado de HHPP bloqueado.
     *
     * @param cmtSolicitudHhppMgl {@link CmtSolicitudCmMgl}
     * @return {@code Map<String,Object>}
     * @author Gildardo Mora
     */
    public Map<String, Object> validarApartHhppVirtual(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) {
        Map<String, Object> map = new HashMap<>();
        boolean flag = false;
        String apart;
        DetalleDireccionEntity detalleDireccionEntity = new DetalleDireccionEntity();

        if (StringUtils.isNotBlank(cmtSolicitudHhppMgl.getOpcionNivel5())
                && StringUtils.isNotBlank(cmtSolicitudHhppMgl.getValorNivel5())) {
            detalleDireccionEntity.setCptiponivel5(cmtSolicitudHhppMgl.getOpcionNivel5());
            detalleDireccionEntity.setCpvalornivel5(cmtSolicitudHhppMgl.getValorNivel5());
            flag = true;
        }

        if (!flag) {
            return map;
        }

        if (cmtSolicitudHhppMgl.getOpcionNivel5().contains("+")
                && StringUtils.isNotBlank(cmtSolicitudHhppMgl.getOpcionNivel6())
                && StringUtils.isNotBlank(cmtSolicitudHhppMgl.getValorNivel6())) {
            detalleDireccionEntity.setCptiponivel6(cmtSolicitudHhppMgl.getOpcionNivel6());
            detalleDireccionEntity.setCpvalornivel6(cmtSolicitudHhppMgl.getValorNivel6());
        }

        try {
            apart = direccionRRManager.generarNumAptoBMRR(detalleDireccionEntity);
            CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdificioMglManager.
                    findById(cmtSolicitudHhppMgl.getCmtSubEdificioMglObj());

            List<HhppMgl> listaHhpp = cmtSubEdificioMgl.getListHhpp();
            String apartamento = apart;
            BigDecimal basicaId = cmtSolicitudHhppMgl.getCmtSolicitudCmMglObj().getBasicaIdTecnologia().getBasicaId();
            Optional<HhppMgl> hhppMgl = listaHhpp.stream()
                    .filter(hhpp -> hhpp.getNodId().getNodTipo().getBasicaId().equals(basicaId)
                            && StringUtils.isNotBlank(hhpp.getHhpApart())
                            && hhpp.getHhpApart().trim().equalsIgnoreCase(apartamento))
                    .findAny();

            if (!hhppMgl.isPresent()) {
                map.put("hhppEncontrado", null);
                map.put("newAdress", apart);
                return map;
            }

            map.put("hhppEncontrado", hhppMgl.get());
            map.put("newAdress", apart);
            return map;

        } catch (Exception e) {
            String msgError = "Ocurrió un error en : " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            return map;
        }
    }

    /**
     * Guarda las solicitudes de HHPP en BD
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return null
     * @throws ApplicationException
     */
    public String GuardaListadoHHPP(List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges, CmtSolicitudCmMgl cmtSolicitudCmMgl, String user, int perfil) throws ApplicationException {
        for (CmtSolicitudHhppMgl saveCmtSolicitudHhpp : cmtSolicitudHhppMglListToChanges) {
            if (saveCmtSolicitudHhpp.getCmtSubEdificioMglObj() != null) {
                saveCmtSolicitudHhpp.setCmtSolicitudCmMglObj(cmtSolicitudCmMgl);
                create(saveCmtSolicitudHhpp, user, perfil);
            }
        }
        return "Ok";
    }

    /**
     * Guarda las gestiones de HHPP en BD
     *
     * @author Antonio Gil
     * @param perfil {@code int}  Número de peril del usuario
     * @return null
     * @throws ApplicationException  Excepción personalizada de la App.
     */
    public Map<String, Object> guardaGestionHHPP(List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges,
            CmtSolicitudCmMgl cmtSolicitudCmMgl, String user, int perfil) throws ApplicationException {

        HhppMgl hhppMglChange;
        CmtTecnologiaSubMgl tecnologiaSubMgl = null;
        ResponseCreateHhppDto responseCreateHhppDto = null ;
        Map<String, Object> responseGestion = new HashMap<>();

        for (CmtSolicitudHhppMgl saveCmtSolicitudHhpp : cmtSolicitudHhppMglListToChanges) {
            CmtDireccionMgl cmtdireccionMgl;
            String nameSubEdificio = "";
            String subDireccion;

            //JDHT
                    
            // objeto creado para enviar el tipo de Vivienda desde creacion y modificacion de hhpp
            CmtHhppVtMgl cmtHhppVtMgl = new CmtHhppVtMgl();
            cmtHhppVtMgl.setThhVtId(saveCmtSolicitudHhpp.getTipoHhpp());
            if (saveCmtSolicitudHhpp.getCmtSubEdificioMglObj() != null
                    && saveCmtSolicitudHhpp.getEstadoRegistro() >= 1) {
                //Creacion de nuevos HHPP
                if (saveCmtSolicitudHhpp.getTipoSolicitud() == 1) {

                    if (cmtSolicitudCmMgl.getBasicaIdTecnologia() == null) {
                        throw new ApplicationException("La solucitud necesita una tecnología asociada, no es posible gestionar la solicitud, intente realizar una nueva por favor.");
                    }

                        tecnologiaSubMgl = consultarTecnologiaSub(saveCmtSolicitudHhpp, cmtSolicitudCmMgl.getBasicaIdTecnologia());//valbuenayf rq_crear_modificar_hhpp

                        if (tecnologiaSubMgl == null) {
                            throw new ApplicationException("La tecnología : " + cmtSolicitudCmMgl.getBasicaIdTecnologia().getNombreBasica() + " ya no se encuentra asociada al edificio.");
                        }

                            responseCreateHhppDto = hhppMglManager.crearHhppRRAndRepoFromCcMm(saveCmtSolicitudHhpp.getOpcionNivel5(),
                                    saveCmtSolicitudHhpp.getValorNivel5(), saveCmtSolicitudHhpp.getOpcionNivel6(),
                                    saveCmtSolicitudHhpp.getValorNivel6(), saveCmtSolicitudHhpp.getCmtSubEdificioMglObj(),
                                    user, cmtSolicitudCmMgl.getSolicitudCmId().toString(),
                                    cmtSolicitudCmMgl.getOrigenSolicitud().getNombreBasica(), "Creacion HHPP Por Solicitud",
                                    "HHPP CREADO SOLICITUD", tecnologiaSubMgl, cmtHhppVtMgl, perfil);
                }

                //modificacion HHPP
                if (saveCmtSolicitudHhpp.getTipoSolicitud() == 2) {
                    if (saveCmtSolicitudHhpp.getHhppMglObj() == null) {
                        return null;
                    }

                        String tipoNi6="";
                        String valorNi6="";
                        hhppMglChange = saveCmtSolicitudHhpp.getHhppMglObj();
                        hhppMglChange.setHhpApart(this.ValidadorDireccionHHPP(saveCmtSolicitudHhpp));
                        //Validamos si el subEdificio tiene direccion propia.
                        if (hhppMglChange.getHhppSubEdificioObj().getListDireccionesMgl() == null
                                || hhppMglChange.getHhppSubEdificioObj().getListDireccionesMgl().isEmpty()) {
                            cmtdireccionMgl = cmtSolicitudCmMgl.getCuentaMatrizObj().getDireccionPrincipal();
                            nameSubEdificio = hhppMglChange.getHhppSubEdificioObj().getNombreSubedificio();
                            subDireccion = cmtSolicitudCmMgl.getCuentaMatrizObj().getSubEdificioGeneral().getDireccion();
                        } else {
                            cmtdireccionMgl = cmtDireccionMglManager.findByIdSubEdificio(hhppMglChange.getHhppSubEdificioObj().getSubEdificioId());
                            subDireccion = hhppMglChange.getHhppSubEdificioObj().getDireccion();
                        }
                        HhppMgl mglHhppAntiguo = hhppMglManager.findById( saveCmtSolicitudHhpp.getHhppMglObj().getHhpId());
                        DrDireccion drdirTemp = null;//limpia el objeto
                        CmtDireccionDetalleMglManager cmtDirDetMag = new CmtDireccionDetalleMglManager();
                        BigDecimal temSubID = null;
                        if (mglHhppAntiguo.getSdiId()!=null){
                            temSubID = mglHhppAntiguo.getSdiId();
                        }
                        List<CmtDireccionDetalladaMgl> lisDirDetTemporal = cmtDirDetMag.findDireccionDetallaByDirIdSdirId( mglHhppAntiguo.getDirId(), temSubID );
                        if (CollectionUtils.isNotEmpty(lisDirDetTemporal)){
                            CmtDireccionDetalladaMgl dirDetTemporal = lisDirDetTemporal.get(0);
                            drdirTemp = cmtDirDetMag.parseCmtDireccionDetalladaMglToDrDireccion(dirDetTemporal);
                            DetalleDireccionEntity apartamentoNuevoFormatoRr
                                        = new DetalleDireccionEntity();
                            drdirTemp.setCpTipoNivel5(null);
                            drdirTemp.setCpValorNivel5(null);
                            drdirTemp.setCpTipoNivel6(null);
                            drdirTemp.setCpValorNivel6(null);
                            
                            //asigna los valores de los niveles diferentes al apto y que hacen parte de la direccion complemento
                            apartamentoNuevoFormatoRr.setCptiponivel1(drdirTemp.getCpTipoNivel1() != null ? drdirTemp.getCpTipoNivel1() : null);
                            apartamentoNuevoFormatoRr.setCptiponivel2(drdirTemp.getCpTipoNivel1() != null ? drdirTemp.getCpTipoNivel2() : null);
                            apartamentoNuevoFormatoRr.setCptiponivel3(drdirTemp.getCpTipoNivel1() != null ? drdirTemp.getCpTipoNivel3() : null);
                            apartamentoNuevoFormatoRr.setCptiponivel4(drdirTemp.getCpTipoNivel1() != null ? drdirTemp.getCpTipoNivel4() : null);
                            
                            apartamentoNuevoFormatoRr.setCpvalornivel1(drdirTemp.getCpValorNivel1() != null ? drdirTemp.getCpValorNivel1() : null);
                            apartamentoNuevoFormatoRr.setCpvalornivel2(drdirTemp.getCpValorNivel2() != null ? drdirTemp.getCpValorNivel2() : null);
                            apartamentoNuevoFormatoRr.setCpvalornivel3(drdirTemp.getCpValorNivel3() != null ? drdirTemp.getCpValorNivel3() : null);
                            apartamentoNuevoFormatoRr.setCpvalornivel4(drdirTemp.getCpValorNivel4() != null ? drdirTemp.getCpValorNivel4() : null);
                                    
                            if( saveCmtSolicitudHhpp.getOpcionNivel5()!=null && !saveCmtSolicitudHhpp.getOpcionNivel5().isEmpty()){
                                drdirTemp.setCpTipoNivel5(saveCmtSolicitudHhpp.getOpcionNivel5());
                                apartamentoNuevoFormatoRr.setCptiponivel5(saveCmtSolicitudHhpp.getOpcionNivel5());
                            }else{
                                 apartamentoNuevoFormatoRr.setCptiponivel5(null);
                            }
                            
                            if( saveCmtSolicitudHhpp.getValorNivel5()!=null && !saveCmtSolicitudHhpp.getValorNivel5().isEmpty()){
                                drdirTemp.setCpValorNivel5(saveCmtSolicitudHhpp.getValorNivel5());
                                 apartamentoNuevoFormatoRr.setCpvalornivel5(null);
                            }else{
                                 apartamentoNuevoFormatoRr.setCpvalornivel5(null);
                            }
                            
                            if( saveCmtSolicitudHhpp.getOpcionNivel6()!=null && !saveCmtSolicitudHhpp.getOpcionNivel6().isEmpty()){
                                drdirTemp.setCpTipoNivel6(saveCmtSolicitudHhpp.getOpcionNivel6());
                                tipoNi6=saveCmtSolicitudHhpp.getOpcionNivel6();
                                apartamentoNuevoFormatoRr.setCptiponivel6(saveCmtSolicitudHhpp.getOpcionNivel6());
                            }else{
                                apartamentoNuevoFormatoRr.setCptiponivel6(null);
                            }
                            
                            if( saveCmtSolicitudHhpp.getValorNivel6()!=null && !saveCmtSolicitudHhpp.getValorNivel6().isEmpty()){
                                drdirTemp.setCpValorNivel6(saveCmtSolicitudHhpp.getValorNivel6());
                                valorNi6=saveCmtSolicitudHhpp.getValorNivel6();
                                apartamentoNuevoFormatoRr.setCpvalornivel6(saveCmtSolicitudHhpp.getValorNivel6());
                            }else{
                                   apartamentoNuevoFormatoRr.setCpvalornivel6(null);
                            }                           
                           

                            //JDHT cambio de apto de Hhpp
                            if (mglHhppAntiguo != null) {

                                //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
                                boolean habilitarRR = false;
                                ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
                                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                    habilitarRR = true;
                                }                               

                                //CAMBIO DE APTO EN RR
                                //Cambio a subDirecciones del Hhpp y RR debe estar encendido 
                                if (habilitarRR && mglHhppAntiguo != null && mglHhppAntiguo.getSubDireccionObj() != null
                                        && mglHhppAntiguo.getSubDireccionObj().getSdiId() != null) {
                                   
                                    //Se realiza busqueda de todas las subdireccion del Hhpp para hacer actualización de datos del hhpp en todas sus tecnologias
                                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                                            .findHhppSubDireccion(mglHhppAntiguo.getSubDireccionObj().getSdiId());

                                    if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                                        for (HhppMgl hhppMglSubDireccion : hhhpSubDirList) {

                                            //Si la subDireccion tiene id RR se procede a hacer el cambio de Dir
                                            if (hhppMglSubDireccion.getHhpIdrR() != null
                                                    && !hhppMglSubDireccion.getHhpIdrR().isEmpty()) {                               

                                                HhppResponseRR hhppResponseRR = new HhppResponseRR();
                                                //Consume servicio que busca hhpp por Id de RR
                                                hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglSubDireccion.getHhpIdrR());

                                                if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                                    /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                                    if (hhppResponseRR.getComunidad() != null
                                                            && !hhppResponseRR.getComunidad().isEmpty()
                                                            && hhppResponseRR.getDivision() != null
                                                            && !hhppResponseRR.getDivision().isEmpty()
                                                            && hhppMglSubDireccion.getNodId().getNodTipo() != null) {
                                                        //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                                        if (hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                                && !hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                                                            for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                                    : hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                                if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                                        equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                                        && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                                    //cardenaslb
                                                                    HhppMgl hhppCambioTipoVivienda = new HhppMgl();
                                                                    hhppCambioTipoVivienda.setHhpCalle(hhppResponseRR.getStreet());
                                                                    hhppCambioTipoVivienda.setHhpApart(hhppResponseRR.getApartamento());
                                                                    hhppCambioTipoVivienda.setHhpPlaca(hhppResponseRR.getHouse());
                                                                    hhppCambioTipoVivienda.setHhpDivision(hhppResponseRR.getDivision());
                                                                    hhppCambioTipoVivienda.setHhpComunidad(hhppResponseRR.getComunidad());
                                                                    hhppCambioTipoVivienda.setThhId(mglHhppAntiguo.getThhId());

                                                                    direccionRRManager.cambioTipoUnidadViviendaHhppRR(hhppCambioTipoVivienda);

                                                                    //mover de apartamento 
                                                                    direccionRRManager.cambiarEdificioHHPPRR_Inspira(
                                                                            cmtSolicitudCmMgl.getSolicitudCmId().toString(),
                                                                            hhppResponseRR.getComunidad().trim(),
                                                                            hhppResponseRR.getDivision().trim(),
                                                                            hhppResponseRR.getStreet(),
                                                                            hhppResponseRR.getHouse(),
                                                                            hhppResponseRR.getApartamento(), //apartamento antiguo
                                                                            hhppMglChange.getHhpApart(), //apartamento nuevo
                                                                            cmtSolicitudCmMgl.getUsuarioCreacion(),
                                                                            cmtSolicitudCmMgl.getTipoSolicitudObj() != null ?
                                                                                    cmtSolicitudCmMgl.getTipoSolicitudObj().getNombreTipo() : "Sin tipo",
                                                                            hhppMglSubDireccion.getNodId().getComId());
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        throw new ApplicationException("RR se encuentra encendido y la subdirección tiene IdRR pero el nodo de la subdirección"
                                                                + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnología "
                                                                + hhppMglSubDireccion.getNodId().getNodTipo().getNombreBasica());
                                                    }
                                                } else {
                                                    if (hhppResponseRR != null
                                                            && hhppResponseRR.getTipoMensaje() != null
                                                            && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                                        throw new ApplicationException(hhppResponseRR.getMensaje());
                                                    } else {
                                                        throw new ApplicationException("Ocurrio un error intentando "
                                                                + "consumir el servicio de consulta de hhpp en RR ");
                                                    }
                                                }
                                            } else {
                                                LOGGER.error("La subdireccion no cuenta con idRR para realizar operaciones en RR");
                                            }
                                        }
                                    } else {
                                        throw new ApplicationException("Ocurrio un error en la consulta de subdirecciones del hhpp, "
                                                + "no es posible realizar el cambio de dirección en RR");
                                    }
                                } else {
                                    //Obtenemos los Hhpp de la Direccion principal y RR debe estar encendido   
                                    if (mglHhppAntiguo != null && mglHhppAntiguo.getDireccionObj() != null
                                            && mglHhppAntiguo.getDireccionObj().getDirId() != null && habilitarRR) {

                                        //Obtenemos los Hhpp de la Direccion principal  
                                        List<HhppMgl> hhhpDirList = hhppMglManager.findHhppDireccion(mglHhppAntiguo.getDireccionObj().getDirId());

                                        if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                            for (HhppMgl hhppMglDireccion : hhhpDirList) {

                                                /* si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo*/
                                                if (hhppMglDireccion.getHhpIdrR() != null && !hhppMglDireccion.getHhpIdrR().isEmpty()) {

                                                    HhppResponseRR hhppResponseRR = new HhppResponseRR();
                                                    //Consume servicio que busca hhpp por Id de RR
                                                    hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglDireccion.getHhpIdrR());

                                                    if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {

                                                        if (hhppResponseRR.getComunidad() != null
                                                                && !hhppResponseRR.getComunidad().isEmpty()
                                                                && hhppResponseRR.getDivision() != null
                                                                && !hhppResponseRR.getDivision().isEmpty()
                                                                && hhppMglDireccion.getNodId() != null) {

                                                            //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                                                            if (hhppMglDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                                                                    && !hhppMglDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {
                                                                //recorrido sobre la basica extendida de la tecnologia del nodo del hhpp de la subdirección.
                                                                for (CmtBasicaExtMgl cmtBasicaExtMgl
                                                                        : hhppMglDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                                                                    //Validación para conocer si la tecnoligia sincroniza con RR
                                                                    if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                                                            equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                                                            && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                                                                        //cardenaslb
                                                                        HhppMgl hhppCambioTipoVivienda = new HhppMgl();
                                                                        hhppCambioTipoVivienda.setHhpCalle(hhppResponseRR.getStreet());
                                                                        hhppCambioTipoVivienda.setHhpApart(hhppResponseRR.getApartamento());
                                                                        hhppCambioTipoVivienda.setHhpPlaca(hhppResponseRR.getHouse());
                                                                        hhppCambioTipoVivienda.setHhpDivision(hhppResponseRR.getDivision());
                                                                        hhppCambioTipoVivienda.setHhpComunidad(hhppResponseRR.getComunidad());
                                                                        hhppCambioTipoVivienda.setThhId(mglHhppAntiguo.getThhId());

                                                                        direccionRRManager.cambioTipoUnidadViviendaHhppRR(hhppCambioTipoVivienda);

                                                                    //mover de apartamento 
                                                                    direccionRRManager.cambiarEdificioHHPPRR_Inspira(
                                                                            cmtSolicitudCmMgl.getSolicitudCmId().toString(),
                                                                            hhppResponseRR.getComunidad().trim(),
                                                                            hhppResponseRR.getDivision().trim(),
                                                                            hhppResponseRR.getStreet(),
                                                                            hhppResponseRR.getHouse(),
                                                                            hhppResponseRR.getApartamento(), //apartamento antiguo
                                                                            hhppMglChange.getHhpApart(), //apartamento nuevo
                                                                            cmtSolicitudCmMgl.getUsuarioCreacion(),
                                                                            cmtSolicitudCmMgl.getTipoSolicitudObj() != null ?
                                                                                    cmtSolicitudCmMgl.getTipoSolicitudObj().getNombreTipo() : "Sin tipo",
                                                                            hhppMglDireccion.getNodId().getComId());

                                                                    } else {
                                                                        LOGGER.error("La tecnología del hhpp de la subdireccion no sincroniza con RR");
                                                                    }
                                                                }
                                                            } else {
                                                                LOGGER.error("El listado de basica extendida de la tecnologia no se encuentra cargado");
                                                            }
                                                        } else {
                                                            throw new ApplicationException("RR se encuentra encendido y la subdirección tiene IdRR pero el nodo de la subdirección"
                                                                    + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnología "
                                                                    + hhppMglDireccion.getNodId().getNodTipo().getNombreBasica());
                                                        }

                                                    } else {
                                                        if (hhppResponseRR != null
                                                                && hhppResponseRR.getTipoMensaje() != null
                                                                && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                                                            throw new ApplicationException(hhppResponseRR.getMensaje());
                                                        } else {
                                                            throw new ApplicationException("Ocurrio un error intentando "
                                                                    + "consumir el servicio de consulta de hhpp en RR ");
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }

                                UnidadStructPcml unidadModificadaNuevoApto = new UnidadStructPcml();

                                if (StringUtils.isNotBlank(saveCmtSolicitudHhpp.getOpcionNivel5())) {
                                    unidadModificadaNuevoApto.setTipoNivel5(saveCmtSolicitudHhpp.getOpcionNivel5());
                                }
                                if (StringUtils.isNotBlank(saveCmtSolicitudHhpp.getValorNivel5())) {
                                    unidadModificadaNuevoApto.setValorNivel5(saveCmtSolicitudHhpp.getValorNivel5());
                                }
                                if (StringUtils.isNotBlank(saveCmtSolicitudHhpp.getOpcionNivel6())) {
                                    unidadModificadaNuevoApto.setTipoNivel6(saveCmtSolicitudHhpp.getOpcionNivel6());

                                }
                                if (StringUtils.isNotBlank(saveCmtSolicitudHhpp.getValorNivel6())) {
                                    unidadModificadaNuevoApto.setValorNivel6(saveCmtSolicitudHhpp.getValorNivel6());
                                }

                                hhppMglChange.setThhId(saveCmtSolicitudHhpp.getTipoHhpp());
                                CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
                                cmtDireccionDetalleMglManager.cambiarAptoDireccionDetalladaHhpp(mglHhppAntiguo, unidadModificadaNuevoApto,
                                        dirDetTemporal.getDireccion().getUbicacion().getGpoIdObj().getGpoId(), user, perfil);

                            }

                        }

                        guardarRegOriginales(cmtSolicitudCmMgl, hhppMglChange, mglHhppAntiguo, user, perfil, tipoNi6, valorNi6);
                }

                /* Gestión de solicitud traslado HHPP Bloqueado (Creación HHPP Virtual) */
                if (saveCmtSolicitudHhpp.getTipoSolicitud() == ConstantsSolicitudHhpp.CM_SOL_TRASLADO_HHPP_BLOQUEADO) {
                    responseGestion = gestionarTrasladoHhppBloqueado(cmtSolicitudCmMgl, user, perfil, responseCreateHhppDto, saveCmtSolicitudHhpp);
                    responseCreateHhppDto = (ResponseCreateHhppDto) responseGestion.getOrDefault("responseCreate", null);
                }
            }
        
        }

        List<String> validationsMsg = Optional.ofNullable(responseCreateHhppDto)
                .map(ResponseCreateHhppDto::getValidationMessages)
                .filter(CollectionUtils::isNotEmpty)
                .orElse(null);

        responseGestion.put("msgGestionHhpp", Objects.nonNull(validationsMsg) ? responseCreateHhppDto.getValidationMessages().get(0) : "");
        return responseGestion;
    }

    /**
     * Realiza la gestión de la solicitud de traslado HHPP Bloqueado.
     *
     * @param cmtSolicitudCmMgl     {@link CmtSolicitudCmMgl}
     * @param user                  {@link String} Usuario que está gestionando la solicitud.
     * @param perfil                {@code int} Número de perfil del usuario
     * @param responseCreateHhppDto {@link ResponseCreateHhppDto}
     * @param saveCmtSolicitudHhpp  {@link CmtSolicitudHhppMgl}
     * @return {@code Map<String, Object>}
     * @throws ApplicationException Excepción personalizada de App.
     * @author Gildardo Mora
     */
    private Map<String, Object> gestionarTrasladoHhppBloqueado(CmtSolicitudCmMgl cmtSolicitudCmMgl, String user,
            int perfil, ResponseCreateHhppDto responseCreateHhppDto, CmtSolicitudHhppMgl saveCmtSolicitudHhpp) throws ApplicationException {

        Map<String, Object> resultadoGestion = new HashMap<>();
        CmtTecnologiaSubMgl tecnologiaSubMgl;

        if (Objects.isNull(cmtSolicitudCmMgl.getBasicaIdTecnologia())) {
            throw new ApplicationException("Se requiere una tecnología asociada, para gestionar la solicitud");
        }

        tecnologiaSubMgl = consultarTecnologiaSub(saveCmtSolicitudHhpp, cmtSolicitudCmMgl.getBasicaIdTecnologia());
        if (Objects.isNull(tecnologiaSubMgl)) {
            String msgError = "La tecnología : " + cmtSolicitudCmMgl.getBasicaIdTecnologia().getNombreBasica()
                    + " ya no se encuentra asociada al edificio.";
            throw new ApplicationException(msgError);
        }

        HhppMgl hhppMglVirtual = saveCmtSolicitudHhpp.getHhppMglVirtual();
        hhppMglVirtual.setHhpId(null);
        String apart = StringUtils.isNotBlank(hhppMglVirtual.getHhpApart()) ? hhppMglVirtual.getHhpApart() : null;
        hhppMglVirtual.setHhpApart(apart);
        hhppMglVirtual.setCuentaClienteTrasladar(cmtSolicitudCmMgl.getNumeroClienteTrasladar());
        //TODO: llamar a creación de HHPP Virtual teniendo en cuenta que es de Cuenta Matriz
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        RequestCreaSolicitudTrasladoHhppBloqueado request = new RequestCreaSolicitudTrasladoHhppBloqueado();
        request.setNumeroCuentaClienteTrasladar(cmtSolicitudCmMgl.getNumeroClienteTrasladar());
        request.setUsuarioVt(user);
        request.setPerfilVt(perfil);
        Map<String, Object> hhppVirtualResult = hhppVirtualMglManager.createHhppVirtual(request, hhppMglVirtual);
        boolean isHhppVirtualCmCreated = (boolean) hhppVirtualResult.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
        String msgResult = (String) hhppVirtualResult.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULT, "");
        resultadoGestion.put(ConstantsSolicitudHhpp.RESULTADO_GESTION, isHhppVirtualCmCreated);
        resultadoGestion.put(ConstantsSolicitudHhpp.MSG_RESULTADO, msgResult);

        if (isHhppVirtualCmCreated){
            responseCreateHhppDto = new ResponseCreateHhppDto();
            responseCreateHhppDto.setCreacionExitosa(true);
        }

        resultadoGestion.put("responseCreate", responseCreateHhppDto);
        return resultadoGestion;
    }

    public CmtTecnologiaSubMgl consultarTecnologiaSub(CmtSolicitudHhppMgl cmtSolicitudHhpp, CmtBasicaMgl basicaMgl) {

        CmtTecnologiaSubMgl tecnologiaSubMgl = null;
        try {
            CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
            List<CmtTecnologiaSubMgl> tecnologiasSubMgl;

            tecnologiasSubMgl = tecnologiaSubMglManager.findTecnoSubBySubEdiTec(cmtSolicitudHhpp.getCmtSubEdificioMglObj(), basicaMgl);
            tecnologiaSubMgl = (tecnologiasSubMgl != null && !tecnologiasSubMgl.isEmpty()) ? tecnologiasSubMgl.get(0) : null;

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        return tecnologiaSubMgl;
    }

    /**
     * Validacion solicitudes de HHPP cambiadas
     *
     * @author Antonio Gil
     * @param listHhpp
     * @param listSolicitudHhppMgl
     * @return null
     * @throws ApplicationException
     */
    public List<HhppMgl> ValidacionCambiosHhpp(List<HhppMgl> listHhpp, List<CmtSolicitudHhppMgl> listSolicitudHhppMgl) throws ApplicationException {
        if (listSolicitudHhppMgl == null || listSolicitudHhppMgl.isEmpty()) {
            return listHhpp;
        }
        for (CmtSolicitudHhppMgl solicitudHhppMgl : listSolicitudHhppMgl) {
            for (HhppMgl hhppMgl : listHhpp) {
                if (solicitudHhppMgl.getHhppMglObj() != null) {
                    if (hhppMgl.getHhpId().compareTo(solicitudHhppMgl.getHhppMglObj().getHhpId()) == 0) {
                        hhppMgl.setHhpApart(this.ValidadorDireccionHHPP(solicitudHhppMgl));
                    }
                }
            }
        }
        return listHhpp;
    }

    /**
     * Actualiza los estados de la Solicitud HHPP
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return null
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> ActualizaCambiosHhpp(List<CmtSolicitudHhppMgl> listDeleteCmtSolicitudHhppMgl, CmtSolicitudHhppMgl cmtSolicitudHhppMgl, String user, int perfil) throws ApplicationException {
        if (listDeleteCmtSolicitudHhppMgl != null && !listDeleteCmtSolicitudHhppMgl.isEmpty()) {
            for (CmtSolicitudHhppMgl cshm : listDeleteCmtSolicitudHhppMgl) {
                if (cshm.equals(cmtSolicitudHhppMgl)) {
                    if (cshm.getEstadoRegistro() >= 1) {
                        cshm.setEstadoRegistro(0);
                    } else {
                        cshm.setEstadoRegistro(1);
                    }
                    update(cshm, user, perfil);
                }
            }
        }
        return listDeleteCmtSolicitudHhppMgl;
    }
    
      public void guardarRegOriginales(CmtSolicitudCmMgl solicitudCmMgl, HhppMgl modificado, HhppMgl antiguo,
            String usuario, int perfil, String tipoNi6, String valorNi6) {

        try {

            CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl = new CmtModificacionesCcmmAudMgl();
            cmtModificacionesCcmmAudMgl.setSolicitudCMObj(solicitudCmMgl);
            cmtModificacionesCcmmAudMgl.setCuentaMatrizObj(solicitudCmMgl.getCuentaMatrizObj());
            cmtModificacionesCcmmAudMgl.setHhpIdObj(antiguo);
            cmtModificacionesCcmmAudMgl.setHhpApartAnt(antiguo.getHhpApart());
            String complemento = tipoNi6+""+valorNi6;
            cmtModificacionesCcmmAudMgl.setHhpApartDes(modificado.getHhpApart()+" "+complemento);

            CmtModCcmmAudMglManager manager = new CmtModCcmmAudMglManager();
            cmtModificacionesCcmmAudMgl = manager.crear(cmtModificacionesCcmmAudMgl, usuario, perfil);

            if (cmtModificacionesCcmmAudMgl.getModCcmmAudId() != null) {
                LOGGER.info("Registro original del Hhpp guardado en el repositorio");
            }

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }

    }
}