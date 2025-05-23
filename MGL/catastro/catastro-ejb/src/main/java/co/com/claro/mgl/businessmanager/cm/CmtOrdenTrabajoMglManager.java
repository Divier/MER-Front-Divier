package co.com.claro.mgl.businessmanager.cm;

import co.claro.wcc.client.gestor.ClientGestorDocumental;
import co.claro.wcc.schema.schemadocument.DocumentType;
import co.claro.wcc.schema.schemadocument.FieldType;
import co.claro.wcc.schema.schemadocument.FileType;
import co.claro.wcc.schema.schemaoperations.ActionStatusEnumType;
import co.claro.wcc.schema.schemaoperations.FileRequestType;
import co.claro.wcc.schema.schemaoperations.RequestType;
import co.claro.wcc.schema.schemaoperations.ResponseType;
import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dao.impl.cm.CmtOrdenTrabajoMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaOrdenesDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.dtos.CmtSubEdificioDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoRrMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtUtilidadesCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import co.com.claro.visitastecnicas.ws.proxy.ConsultaList_Response;
import co.com.claro.visitastecnicas.ws.proxy.ConsultaMglOnyxList;
import co.com.claro.visitastecnicas.ws.proxy.HeaderRequest;
import co.com.claro.visitastecnicas.ws.proxy.OtHijaOnixRequest;
import co.com.claro.visitastecnicas.ws.proxy.OtHijaOnixRequestNew;
import co.com.claro.visitastecnicas.ws.proxy.PortManager;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.amx.service.automaticclosingonyx.v1.FaultMessage;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyRequestType;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import com.amx.service.exp.operation.updateclosingtask.v1.ClientOnix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;
import javax.faces.context.FacesContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manager Orden de Trabajo. Contiene la logica de negocio para el manejo de
 * ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtOrdenTrabajoMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtOrdenTrabajoMglManager.class);
    CmtOrdenTrabajoMglDaoImpl dao = new CmtOrdenTrabajoMglDaoImpl();
    CmtOrdenTrabajoReportThreadManager daoReport = new CmtOrdenTrabajoReportThreadManager();
    private String wsURL = "";
    private String wsService = "";

    public CmtOrdenTrabajoMglManager() {
        configParametrosWS();
    }

    /**
     * Crea una Orden de Trabajo.Permite realizar la creacion de una Orden de
     * Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param ot Orden de Trabajo a crear en el repositorio
     * @param usuario
     * @param perfil
     * @return Orden de Trabajo creada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl crearOt(CmtOrdenTrabajoMgl ot, String usuario, int perfil) throws ApplicationException {
        //TODO: RR Crear OT en RR
        ot = dao.createCm(ot, usuario, perfil);
        //Creacion Nota de OT
        CmtNotaOtMglManager notaOtMglManager = new CmtNotaOtMglManager();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

        CmtNotaOtMgl notaOtMgl = new CmtNotaOtMgl();
        notaOtMgl.setDescripcion("Creacion Ot " + ot.getIdOt().toString());
        notaOtMgl.setNota(ot.getObservacion());
        CmtBasicaMgl tipoNota = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_NOTA_VISITA_TECNICA);
        notaOtMgl.setTipoNotaObj(tipoNota);
        notaOtMgl.setOrdenTrabajoObj(ot);
        notaOtMglManager.crear(notaOtMgl, usuario, perfil);
        return ot;
    }

    /**
     * Actualiza una Orden de Trabajo.Permite realizar la actualizacion de una
     * Orden de Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param ot Orden de Trabajo a actualizar en el repositorio
     * @param perfil
     * @param usuario
     * @return Orden de Trabajo actualizada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl actualizarOt(CmtOrdenTrabajoMgl ot,
            BigDecimal nuevoEstado, String usuario, int perfil) throws ApplicationException, ApplicationException {
        //TODO:cambiar estado CM

        //TODO: RR crea ot segun el tipo de ot rr
        //Verificamos que exista cambio de estado para asignarlo a la OT
        if (nuevoEstado.compareTo(ot.getEstadoInternoObj().getBasicaId()) != 0) {
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
            CmtBasicaMgl nuevoEstadoObj = basicaMglManager.findById(nuevoEstado);
            ot.setEstadoInternoObj(nuevoEstadoObj);
        }
        //TODO: validar creacion de OT en RR

        changeStatusCmByFlow(ot, usuario, perfil);
        return dao.updateCm(ot, usuario, perfil);
        //TODO: RR Actualizar OT en RR
    }

    /**
     * Actualiza la cuenta matriz o subedificios con el estado de flujo
     *
     * @author Carlos Villamil Hitss
     * @param ot Orden de Trabajo a actualizar en el repositorio
     * @param usuario
     * @param perfil
     * @return Void
     * @throws ApplicationException
     */
    public void changeStatusCmByFlow(CmtOrdenTrabajoMgl ot, String usuario, int perfil)
            throws ApplicationException, ApplicationException {
        CmtVisitaTecnicaMgl cmtVisitaTecnicaMglActiva = ot.getVtActivaFromOt();
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        CmtVisitaTecnicaMglManager cmtVisitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();

        if (ot.getVtActivaFromOt() == null
                && !ot.getBasicaIdTecnologia().getIdentificadorInternoApp().
                        equalsIgnoreCase(Constant.RED_FO)) {
            return;
        }

        if (ot.getTipoOtObj().getEsTipoVT().equalsIgnoreCase("Y")
                || ot.getBasicaIdTecnologia().getIdentificadorInternoApp().
                        equalsIgnoreCase(Constant.RED_FO)) {

            if (!ot.getBasicaIdTecnologia().getIdentificadorInternoApp().
                    equalsIgnoreCase(Constant.RED_FO)) {
                if ((ot.getVtActivaFromOt().getListCmtSubEdificiosVt() == null
                        || ot.getVtActivaFromOt().getListCmtSubEdificiosVt().isEmpty())) {
                    return;
                }
            }

            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl
                    = cmtEstadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(
                            ot.getTipoOtObj().getTipoFlujoOt(),
                            ot.getEstadoInternoObj(),
                            ot.getBasicaIdTecnologia());

            if (!ot.getBasicaIdTecnologia().getIdentificadorInternoApp().
                    equalsIgnoreCase(Constant.RED_FO) && cmtVisitaTecnicaMglActiva != null) {
                for (CmtSubEdificiosVt cmtSubEdificiosVt : cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt()) {
                    if (cmtSubEdificiosVt.getEstadoRegistro() == 1
                            && cmtSubEdificiosVt.getSubEdificioObj() != null) {
                        CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdificiosVt.getSubEdificioObj();
                        //Verificamos si no cambia estado
                        List<CmtTecnologiaSubMgl> lstCmtTecnologiaSubMgls = cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(cmtSubEdificioMgl);
                        if (lstCmtTecnologiaSubMgls != null && !lstCmtTecnologiaSubMgls.isEmpty() && lstCmtTecnologiaSubMgls.size() == 1) {
                            if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null
                                    && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null) {
                                cmtSubEdificioMgl.setEstadoSubEdificioObj(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
                            }
                        }
                        cmtSubEdificioMglManager.update(cmtSubEdificioMgl, usuario, perfil, false, false);
                        cmtVisitaTecnicaMglManager.crearRegistroTecnologiaSub(
                                cmtSubEdificiosVt, usuario, perfil, cmtSubEdificiosVt.getSubEdificioObj());
                    }
                }

                CmtBasicaMgl estadocombinadoRR = cambiarEstadosForRegla(cmtVisitaTecnicaMglActiva, usuario, perfil);
                if (estadocombinadoRR == null) {
                    String estadoTecnologia = ot.getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getNombreBasica().trim();
                    String mensaje = "No se encontró regla de sincronización  con RR, se actualizará el estado " + estadoTecnologia + " y se enviará a RR ";
                    ot.setMensajeTecnologiaRR(mensaje);
                } else {
                    String mensaje = "Se encontró una regla de sincronizacion con RR de estado combinado,"
                            + " se actualizara el estado de la CM a: " + estadocombinadoRR.getNombreBasica() + " y se enviara a RR ";
                    ot.setMensajeTecnologiaRR(mensaje);
                }
                if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getEsEstadoInicial().equalsIgnoreCase("F")) {
                    try {
                        cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                        cmtTecnologiaSubMglManager.actualizarCostosTecnologiaSubEdificioGeneral(
                                ot.getVtActivaFromOt().getOtObj().getCmObj(),
                                ot.getVtActivaFromOt().getOtObj().getBasicaIdTecnologia(),
                                ot.getVtActivaFromOt(),
                                usuario, perfil);
                        actualizaCreaInfoTecnica(ot, cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt(),
                                usuario, perfil);
                    } catch (ApplicationException e) {
                        LOGGER.error(e.getMessage());
                        throw new ApplicationException(
                                "Ocurrio un error actualizando los costos en la cuenta matriz: " + e.getMessage());
                    }
                    try {
                        cmtTecnologiaSubMglManager.actualizarCostosSubEdificios(
                                ot.getVtActivaFromOt().getOtObj(), usuario, perfil);
                    } catch (ApplicationException e) {
                        LOGGER.error(e.getMessage());
                        throw new ApplicationException(
                                "Ocurrio un error actualizando los costos de cada sub edificio: " + e.getMessage());
                    }
                    cmtTecnologiaSubMglManager
                            .guardarMetaSubEdificios(ot.getVtActivaFromOt(), usuario, perfil);
                    cmtTecnologiaSubMglManager
                            .guardarFechaHabilitacionSubEdificios(ot.getVtActivaFromOt(), usuario, perfil);
                    cmtTecnologiaSubMglManager
                            .guardarTiempoRecuperacionSubEdificios(ot.getVtActivaFromOt(), usuario, perfil);
                }

            } else {
                CmtSubEdificioMgl cmtSubEdificioMglParaRedFo;
                if (ot.getCmObj().isUnicoSubEdificioBoolean()) {
                    cmtSubEdificioMglParaRedFo = ot.getCmObj().getSubedificioUnicoSubedificio();

                } else {
                    cmtSubEdificioMglParaRedFo = ot.getCmObj().getSubEdificioGeneral();
                }
                CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                List<CmtTecnologiaSubMgl> cmtTecnologiaSubMglRedFoList
                        = tecnologiaSubMglManager.findTecnoSubBySubEdiTec(
                                cmtSubEdificioMglParaRedFo, ot.getBasicaIdTecnologia());
                CmtTecnologiaSubMgl cmtSubEdificioMgl;
                if (cmtTecnologiaSubMglRedFoList == null || cmtTecnologiaSubMglRedFoList.isEmpty()) {
                    cmtSubEdificioMgl = tecnologiaSubMglManager.
                            generarTecnologiaSubRedFo(cmtSubEdificioMglParaRedFo,
                                    ot, cmtEstadoxFlujoMgl.getCambiaCmEstadoObj(), usuario);
                    tecnologiaSubMglManager.crear(cmtSubEdificioMgl, usuario, perfil);

                } else {
                    cmtSubEdificioMgl = cmtTecnologiaSubMglRedFoList.get(0);

                    //Verificamos que exista cambio de estado para asignarlo   
                    if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null
                            && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null) {
                        cmtSubEdificioMgl.setBasicaIdEstadosTec(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
                    }
                    tecnologiaSubMglManager.actualizar(cmtSubEdificioMgl, usuario, perfil);

                }
            }
            //Consulto las tecnologia si se crearon por subedificio
            if (cmtVisitaTecnicaMglActiva != null
                    && cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt() != null
                    && !cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt().isEmpty()) {

                for (CmtSubEdificiosVt cmtSubEdificiosVt
                        : cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt()) {
                    if (cmtSubEdificiosVt.getSubEdificioObj() != null) {
                        List<CmtTecnologiaSubMgl> listTecnoSubedificio
                                = cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(cmtSubEdificiosVt.getSubEdificioObj());
                        crearInfoTecnica(listTecnoSubedificio, usuario, perfil);
                    }
                }
            }
        } else {
            //ES ACOMETIDA
            CmtSubEdificiosVtManager subEdificiosVtManager = new CmtSubEdificiosVtManager();
            List<CmtSubEdificiosVt> suebEdificiosVts = subEdificiosVtManager.findByIdOtAcometida(ot.getIdOt());

            if (!ot.getBasicaIdTecnologia().getIdentificadorInternoApp().
                    equalsIgnoreCase(Constant.RED_FO)) {
                if (suebEdificiosVts != null && suebEdificiosVts.size() > 0
                        && cmtVisitaTecnicaMglActiva != null) {
                    cmtVisitaTecnicaMglActiva.setListCmtSubEdificiosVt(suebEdificiosVts);
                    cmtVisitaTecnicaMglManager.updateCm(cmtVisitaTecnicaMglActiva, usuario, perfil);
                } else {
                    return;
                }
            }
            if (!ot.getBasicaIdTecnologia().getIdentificadorInternoApp().
                    equalsIgnoreCase(Constant.RED_FO)) {
                if (ot.getVtActivaFromOt().getListCmtSubEdificiosVt() == null
                        || ot.getVtActivaFromOt().getListCmtSubEdificiosVt().isEmpty()) {
                    return;
                }
            }
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl
                    = cmtEstadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(
                            ot.getTipoOtObj().getTipoFlujoOt(),
                            ot.getEstadoInternoObj(),
                            ot.getBasicaIdTecnologia());
            if (!ot.getBasicaIdTecnologia().getIdentificadorInternoApp().equalsIgnoreCase(Constant.RED_FO)
                    && cmtVisitaTecnicaMglActiva != null) {

                for (CmtSubEdificiosVt cmtSubEdificiosVt : cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt()) {
                    if (cmtSubEdificiosVt.getEstadoRegistro() == 1
                            && cmtSubEdificiosVt.getSubEdificioObj() != null) {
                        CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdificiosVt.getSubEdificioObj();
                        //Verificamos si no cambia estado
                        if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null
                                && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null) {
                            cmtSubEdificioMgl.setEstadoSubEdificioObj(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
                        }
                        cmtSubEdificioMglManager.update(cmtSubEdificioMgl, usuario, perfil, false, false);
                        cmtVisitaTecnicaMglManager.crearRegistroTecnologiaSubXAcometida(
                                cmtSubEdificiosVt, usuario, perfil, ot);
                    }
                }
                CmtBasicaMgl estadocombinadoRR = cambiarEstadosForRegla(cmtVisitaTecnicaMglActiva, usuario, perfil);
                if (estadocombinadoRR == null) {
                    String estadoTecnologia = ot.getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getNombreBasica().trim();
                    String mensaje = "No se encontró regla de sincronización  con RR, se actualizará el estado " + estadoTecnologia + " y se enviará a RR ";
                    ot.setMensajeTecnologiaRR(mensaje);
                } else {
                    String mensaje = "Se encontró una regla de sincronizacion con RR de estado combinado,"
                            + " se actualizara el estado de la CM a: " + estadocombinadoRR.getNombreBasica() + " y se enviara a RR ";
                    ot.setMensajeTecnologiaRR(mensaje);
                }
                if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getEsEstadoInicial().equalsIgnoreCase("F")) {
                    try {
                        cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                        cmtTecnologiaSubMglManager.actualizarCostosAcometidasTecnologiaSubEdificioGeneral(
                                ot.getVtActivaFromOt().getOtObj().getCmObj(),
                                ot.getVtActivaFromOt().getOtObj().getBasicaIdTecnologia(),
                                ot.getVtActivaFromOt(),
                                usuario, perfil);
                        actualizaCreaInfoTecnica(ot, cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt(),
                                usuario, perfil);
                    } catch (ApplicationException e) {
                        LOGGER.error(e.getMessage());
                        throw new ApplicationException(
                                "Ocurrio un error actualizando los costos en la cuenta matriz: " + e.getMessage());
                    }
                    try {
                        cmtTecnologiaSubMglManager.
                                actualizarCostosAcometidaSubEdificios(suebEdificiosVts, cmtVisitaTecnicaMglActiva,
                                        usuario, perfil);
                    } catch (ApplicationException e) {
                        LOGGER.error(e.getMessage());
                        throw new ApplicationException(
                                "Ocurrio un error actualizando los costos de cada sub edificio: " + e.getMessage());
                    }
                    cmtTecnologiaSubMglManager
                            .guardarMetaSubEdificios(ot.getVtActivaFromOt(), usuario, perfil);
                    cmtTecnologiaSubMglManager
                            .guardarFechaHabilitacionSubEdificios(ot.getVtActivaFromOt(), usuario, perfil);
                    cmtTecnologiaSubMglManager
                            .guardarTiempoRecuperacionSubEdificios(ot.getVtActivaFromOt(), usuario, perfil);
                }

            } else {
                CmtSubEdificioMgl cmtSubEdificioMglParaRedFo;
                if (ot.getCmObj().isUnicoSubEdificioBoolean()) {
                    cmtSubEdificioMglParaRedFo = ot.getCmObj().getSubedificioUnicoSubedificio();

                } else {
                    cmtSubEdificioMglParaRedFo = ot.getCmObj().getSubEdificioGeneral();
                }
                CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
                List<CmtTecnologiaSubMgl> cmtTecnologiaSubMglRedFoList
                        = tecnologiaSubMglManager.findTecnoSubBySubEdiTec(
                                cmtSubEdificioMglParaRedFo, ot.getBasicaIdTecnologia());
                CmtTecnologiaSubMgl cmtSubEdificioMgl;
                if (cmtTecnologiaSubMglRedFoList == null || cmtTecnologiaSubMglRedFoList.isEmpty()) {
                    cmtSubEdificioMgl = tecnologiaSubMglManager.
                            generarTecnologiaSubRedFo(cmtSubEdificioMglParaRedFo,
                                    ot, cmtEstadoxFlujoMgl.getCambiaCmEstadoObj(), usuario);
                    tecnologiaSubMglManager.crear(cmtSubEdificioMgl, usuario, perfil);

                } else {
                    cmtSubEdificioMgl = cmtTecnologiaSubMglRedFoList.get(0);
                    if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null
                            && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null) {
                        cmtSubEdificioMgl.setBasicaIdEstadosTec(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
                    }
                    tecnologiaSubMglManager.actualizar(cmtSubEdificioMgl, usuario, perfil);
                }
            }
            //Consulto las tecnologia si se crearon por subedificio
            if (cmtVisitaTecnicaMglActiva != null
                    && cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt() != null
                    && !cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt().isEmpty()) {

                for (CmtSubEdificiosVt cmtSubEdificiosVt
                        : cmtVisitaTecnicaMglActiva.getListCmtSubEdificiosVt()) {
                    if (cmtSubEdificiosVt.getSubEdificioObj() != null) {
                        List<CmtTecnologiaSubMgl> listTecnoSubedificio
                                = cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(cmtSubEdificiosVt.getSubEdificioObj());
                        crearInfoTecnica(listTecnoSubedificio, usuario, perfil);
                    }
                }
            }
        }
    }

    /**
     * Busca una Orden de Trabajo por ID. Permite realizar la busqueda de una
     * Orden de Trabajo por su ID en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param idOt ID de la Orden de Trabajo a buscar en el repositorio
     * @return Orden de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findOtById(BigDecimal idOt) throws ApplicationException {

        CmtOrdenTrabajoMgl ordenTrabajo = dao.find(idOt);

        CmtSubEdificiosVtManager subEdificiosVtManager = new CmtSubEdificiosVtManager();
        List<CmtSubEdificioDto> listaSubEdificio = new ArrayList<>();

        if (ordenTrabajo != null) {

            if (ordenTrabajo.getTipoOtObj() != null
                    && ordenTrabajo.getTipoOtObj().getEsTipoVT().equalsIgnoreCase("N")) {

                if (ordenTrabajo.getClaseOrdenTrabajo() == null) {
                    ordenTrabajo.setClaseOrdenTrabajo(new CmtBasicaMgl());
                }

                List<CmtSubEdificiosVt> suebEdificiosVts = subEdificiosVtManager.findByIdOtAcometida(idOt);
                if (suebEdificiosVts != null && suebEdificiosVts.size() > 0) {
                    for (CmtSubEdificiosVt s : suebEdificiosVts) {
                        if (s.getEstadoRegistro() == 1) {
                            CmtSubEdificioDto subEdiDto = new CmtSubEdificioDto();
                            subEdiDto.setNombre(s.getNombreSubEdificio());
                            subEdiDto.setCodigoNodo(s.getNodo() != null
                                    ? s.getNodo().getNodCodigo() : "");
                            subEdiDto.setPiso(String.valueOf(s.getNumeroPisosCasas()));
                            subEdiDto.setDireccionSub(s.getSubEdificioObj().getDireccionSubEdificio());
                            listaSubEdificio.add(subEdiDto);

                        }
                    }
                }
                if (listaSubEdificio != null && !listaSubEdificio.isEmpty()) {
                    ordenTrabajo.setListaSubEdificio(listaSubEdificio);
                }
            } else {
                //valbuenayf inicio ajuste para nombre nodo y piso del subedificio
                if (ordenTrabajo.getClaseOrdenTrabajo() == null) {
                    ordenTrabajo.setClaseOrdenTrabajo(new CmtBasicaMgl());
                }
                if (ordenTrabajo.getVisitaTecnicaMglList() != null
                        && !ordenTrabajo.getVisitaTecnicaMglList().isEmpty()) {

                    for (CmtVisitaTecnicaMgl v : ordenTrabajo.getVisitaTecnicaMglList()) {
                        if (v.getEstadoVisitaTecnica().compareTo(BigDecimal.ONE) == 0 && v.getEstadoRegistro() == 1) {
                            for (CmtSubEdificiosVt s : v.getListCmtSubEdificiosVt()) {
                                if (s.getEstadoRegistro() == 1) {
                                    CmtSubEdificioDto subEdiDto = new CmtSubEdificioDto();
                                    subEdiDto.setNombre(s.getNombreSubEdificio());
                                    subEdiDto.setCodigoNodo(s.getNodo() != null
                                            ? s.getNodo().getNodCodigo() : "");
                                    subEdiDto.setPiso(String.valueOf(s.getNumeroPisosCasas()));
                                    if (s.getSubEdificioObj() != null) {
                                        subEdiDto.setDireccionSub(s.getSubEdificioObj().getDireccionSubEdificio());
                                    }
                                    listaSubEdificio.add(subEdiDto);
                                }
                            }
                        }
                    }
                    if (!listaSubEdificio.isEmpty()) {
                        ordenTrabajo.setListaSubEdificio(listaSubEdificio);
                    }
                }
            }
        }
        return ordenTrabajo;
    }

    /**
     * Busca las Ordenes de Trabajo asociadas a una CM. Permite realizar la
     * busqueda de las Ordenes de Trabajo asociadas a una Cuenta Matriz en el
     * repositorio por el ID de la Cuenta Matriz.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatrizMgl Cuenta Matriz
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a una
     * Cuenta Matriz
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByIdCm(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        return dao.findByIdCm(cuentaMatrizMgl);
    }

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Johnnatan Ortiz
     * @param firstResult pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByFiltroParaGestionPaginacion(
            List<BigDecimal> estadosFLujoUsuario, int paginaSelected, int maxResults,
            CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException {
        try {
            int firstResult = 0;
            if (paginaSelected > 1) {
                firstResult = (maxResults * (paginaSelected - 1));
            }
            return estadosFLujoUsuario != null && estadosFLujoUsuario.isEmpty()
                    ? null : dao.findByFiltroParaGestionPaginacion(
                            firstResult, maxResults, estadosFLujoUsuario, filtro);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Cuenta las Ordenes de Trabajo por estados X flujo.Permite realizar el
     * conteo de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Johnnatan Ortiz
     * @param estadosFLujoUsuario lista de estado X flujo
     * @param filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public int getCountByFiltroParaGestion(List<BigDecimal> estadosFLujoUsuario,
            CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException {
        try {
            return estadosFLujoUsuario != null && estadosFLujoUsuario.isEmpty() ? 0
                    : dao.getCountByFiltroParaGestion(estadosFLujoUsuario, filtro);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Busca los estados a los cuales tiene acceso un usuario. Permite realizar
     * la busqueda de los estado dentro de los flujos de los tipo de ordenes de
     * trabajo a los cuales tiene acceso un usuario.
     *
     * @author Johnnatan Ortiz
     * @param facesContext faces Context
     * @return lista de id de estados a los cuales tiene acceso el usuario
     * @throws ApplicationException
     */
    public List<BigDecimal> getEstadosFLujoUsuario(FacesContext facesContext)
            throws ApplicationException {
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
            List<CmtEstadoxFlujoMgl> estadosFLujo = estadoxFlujoMglManager.findAllItemsActive();
            List<BigDecimal> estadosFLujoUsuario = new ArrayList<>();

            if (estadosFLujo != null && !estadosFLujo.isEmpty()) {
                //verificamos si el ROL del estado x flujo lo tiene asigando el usuario
                for (CmtEstadoxFlujoMgl e : estadosFLujo) {
                    if (e.getGestionRol() != null) {
                        if (securityLogin.usuarioTieneRoll(e.getGestionRol())) {
                            estadosFLujoUsuario.add(e.getEstadoxFlujoId());
                        }
                    }
                }
            }

            return estadosFLujoUsuario;

        } catch (ApplicationException | IOException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Valida si para el estado de la OT hay cambio del estado de la CM. Permite
     * realizar la validacion de si para el estado actual de la OT se debe hacer
     * un cambio de estado de la OT.
     *
     * @author Johnnatan Ortiz
     * @param ot orden de trabajo sobre la cual se queire hacer la validacion
     * @throws ApplicationException
     */
    public void validaCambioEstadoCm(CmtOrdenTrabajoMgl ot) throws ApplicationException {
        CmtTipoOtMgl tipoOtMgl = ot.getTipoOtObj();
        CmtBasicaMgl tipoFlujo = tipoOtMgl.getTipoFlujoOt();
        CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();

        CmtEstadoxFlujoMgl detalleEstadoxFlujoMgl
                = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(tipoFlujo,
                        ot.getEstadoInternoObj(), ot.getBasicaIdTecnologia());

        if (detalleEstadoxFlujoMgl != null) {
            //Verificamos si no cambia estado
            if (detalleEstadoxFlujoMgl.getCambiaCmEstadoObj() != null
                    && detalleEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null) {
                CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();

                cuentaMatrizMglManager.cambiarEstadoCm(ot.getCmObj(),
                        detalleEstadoxFlujoMgl.getCambiaCmEstadoObj());
            }
        } else {
            throw new ApplicationException("No fue Posible Validar el cambio de "
                    + "estado de la CM, no se encontro el detalle del estado");
        }
    }

    /**
     * Valida el estado de la CM para la creacion de la OT. Permite validar el
     * estado actual de la cuenta matriz para la creacion de un Orden de
     * trabajo.
     *
     * @author Johnnatan Ortiz
     * @param tipoOt Tipo de Orden de trabajo
     * @param cuentaMatrizMgl Cuenta matriz sobre la cual se quiere crear la OT
     * @return si el estado actual de la CM permite la creacion de la OT
     * @throws ApplicationException
     */
    public boolean validaEstadoCm(CmtTipoOtMgl tipoOt,
            CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.validaEstadoCm(tipoOt, cuentaMatrizMgl);
    }

    /**
     * Valida Si se debe habilitar algun proceso sobre la OT. Permite validar si
     * para el estado actual de una OT se debe habilitar algun proceso, puede
     * ser habilitar alguna pestana de la OT o del Formulario de visita Tecnica
     * o algun proceso sobre RR.
     *
     * @author Johnnatan Ortiz
     * @param ot Tipo de Orden de trabajo
     * @param proceso Cuenta matriz sobre la cual se quiere crear la OT
     * @return si debe habiliar el formulario
     * @throws ApplicationException
     */
    public boolean validaProcesoOt(CmtOrdenTrabajoMgl ot, String proceso) throws ApplicationException {
        boolean result = false;
        CmtBasicaMgl tipoFlujoOt = null;
        if (ot != null && proceso != null) {
            if (ot.getTipoOtObj() != null) {
                tipoFlujoOt = ot.getTipoOtObj().getTipoFlujoOt();
            }
            CmtBasicaMgl estadoActualOt = ot.getEstadoInternoObj();
            CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
            CmtEstadoxFlujoMgl estadoxFlujoMgl = null;
            if (tipoFlujoOt != null) {
                estadoxFlujoMgl = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(tipoFlujoOt,
                        estadoActualOt, ot.getBasicaIdTecnologia());
            }
            if (estadoxFlujoMgl != null
                    && estadoxFlujoMgl.getFormulario() != null
                    && estadoxFlujoMgl.getFormulario().getNombreBasica().contains(proceso)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Valida Si se debe realizar creacion de una OT de RR.Permite validar y
     * crear una OT de RR con base en el estado actual de la OT.
     *
     * @author Johnnatan Ortiz
     * @param ot Tipo de Orden de trabajo
     * @param usuario
     * @param perfil
     * @return si debe habiliar el formulario
     * @throws ApplicationException
     */
    public boolean validaCreacionOtRr(CmtOrdenTrabajoMgl ot, String usuario, int perfil) throws ApplicationException {
        boolean result = false;
        CmtBasicaMgl tipoFlujoOt = ot.getTipoOtObj().getTipoFlujoOt();
        CmtBasicaMgl estadoActualOt = ot.getEstadoInternoObj();
        CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        CmtEstadoxFlujoMgl estadoxFlujoMgl = estadoxFlujoMglManager
                .findByTipoFlujoAndEstadoInt(tipoFlujoOt, estadoActualOt, ot.getBasicaIdTecnologia());
        //Verificamos que para el el estado actual de la OT se deba hacer 
        //la creacion de una OT de RR
        if (estadoxFlujoMgl != null && estadoxFlujoMgl.getTipoOtRr() != null) {
            CmtOrdenTrabajoRrMglManager ordenTrabajoRrMglManager = new CmtOrdenTrabajoRrMglManager();
            CmtOrdenTrabajoRrMgl ordenTrabajoRrMgl = new CmtOrdenTrabajoRrMgl();
            ordenTrabajoRrMgl.setTipoOtRrObj(estadoxFlujoMgl.getTipoOtRr());
            ordenTrabajoRrMgl.setOtObj(ot);
            ordenTrabajoRrMglManager.create(ordenTrabajoRrMgl, usuario, perfil);
            result = true;
        }
        return result;
    }

    public String crearOtRRporAgendamiento(CmtOrdenTrabajoMgl ot, CmtEstadoxFlujoMgl estadoFlujo, String usuario, int perfil) throws ApplicationException {
        CmtOrdenTrabajoRrMglManager ordenTrabajoRrMglManager = new CmtOrdenTrabajoRrMglManager();
        CmtOrdenTrabajoRrMgl ordenTrabajoRrMgl = new CmtOrdenTrabajoRrMgl();
        ordenTrabajoRrMgl.setTipoTrabajoRR(estadoFlujo.getTipoTrabajoRR());
        ordenTrabajoRrMgl.setOtObj(ot);
        String numeroOT = ordenTrabajoRrMglManager.crearOrdenTrabajoRr(ordenTrabajoRrMgl, usuario, perfil);
        if (numeroOT != null && !numeroOT.isEmpty()) {
            ordenTrabajoRrMglManager.actualizarEstadoResultadoOTRR(ordenTrabajoRrMgl,
                    numeroOT, usuario, estadoFlujo, true, null);
        }
        return numeroOT;
    }

    public List<CmtOrdenTrabajoMgl> findPaginacion(CmtCuentaMatrizMgl cmtCuentaMatrizMgl)
            throws ApplicationException {
        return dao.findPaginacion(cmtCuentaMatrizMgl);
    }

    /**
     * Busca las ordenes de trabajo asociadas a los Subedificios
     *
     * @author cardenaslb
     * @param cmtSubEdificioMgl
     * @return si debe habiliar el formulario
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findPaginacionSub(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {
        return dao.findPaginacionSub(cmtSubEdificioMgl);
    }

    public Long countByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) throws ApplicationException {
        return dao.countByCm(cmtCuentaMatrizMgl);
    }

    /**
     * Cuenta las ordenes de trabajo asociadas a los Subedificios
     *
     * @author cardenaslb
     * @param cmtSubEdificioMgl
     * @return si debe habiliar el formulario
     * @throws ApplicationException
     */
    public Long countBySub(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        return dao.countBySub(cmtSubEdificioMgl);
    }

    /**
     * Metodo para construir la auditoria de ordenes de Trabajo
     *
     * @autor Julie Sarmiento
     * @param cmtOrdenTrabajoMgl
     * @return Auditoria Ordenes de trabajo
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public List<AuditoriaDto> construirAuditoria(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtOrdenTrabajoMgl, CmtOrdenTrabajoAuditoriaMgl> utilsCMAuditoria
                = new UtilsCMAuditoria<>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtOrdenTrabajoMgl);
        return listAuditoriaDto;
    }

    public List<CmtOrdenTrabajoMgl> findByCcmmAndTecnologia(CmtCuentaMatrizMgl cuentaMatriz,
            CmtBasicaMgl tecnologia) throws ApplicationException {
        CmtOrdenTrabajoMglDaoImpl mglDaoImpl = new CmtOrdenTrabajoMglDaoImpl();
        return mglDaoImpl.findByCcmmAndTecnologia(cuentaMatriz, tecnologia);
    }

    /**
     * M&eacute;todo para consultar ordenes de trabajo abiertas
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} CM a la que esta asociada
     * la OT
     * @return {@link List}&lt{@link CmtOrdenTrabajoMgl}> Ordenes de trabajo
     * encontradas
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    public List<CmtOrdenTrabajoMgl> ordenesTrabajoActivas(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtOrdenTrabajoMglDaoImpl mglDaoImpl = new CmtOrdenTrabajoMglDaoImpl();
        return mglDaoImpl.ordenesTrabajoActivas(cuentaMatriz);
    }

    /**
     * Metodo para consultar ordenes de trabajo de vt cerradas Autor: Victor
     * Bocanegra
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} CM a la que esta asociada
     * la OT
     * @return CmtOrdenTrabajoMgl Orden de trabajo encontrada
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    public CmtOrdenTrabajoMgl ordenesTrabajoVtCerradas(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {

        CmtOrdenTrabajoMglDaoImpl mglDaoImpl = new CmtOrdenTrabajoMglDaoImpl();

        CmtBasicaMglManager manager = new CmtBasicaMglManager();
        CmtTipoOtMglManager tipoOtMglManager = new CmtTipoOtMglManager();

        CmtTipoOtMgl tipoOtMgl = tipoOtMglManager.findById(BigDecimal.ONE);
        CmtBasicaMgl estadoInternoOt = manager.findById(BigDecimal.TEN);

        List<CmtOrdenTrabajoMgl> ordenes = mglDaoImpl.ordenesTrabajoVtCerradas(cuentaMatriz, tipoOtMgl, estadoInternoOt);

        CmtOrdenTrabajoMgl resultadoUnico = null;

        if (ordenes.size() > 0) {
            resultadoUnico = ordenes.get(0);
        }

        return resultadoUnico;
    }

    /**
     * Metodo para cargar un documento al UCM Autor: Victor Bocanegra
     *
     * @param ordenTrabajoMgl a la cual se le van adjuntar los documentos
     * @param archivo archivo que se va a cargar al UCM
     * @param usuario que realiza la operacion
     * @param perfil del usuario que realiza la operacion
     * @param origenArchivo origen del archivo
     * @return boolean
     * @throws java.net.MalformedURLException
     * @throws ApplicationException Excepcion lanzada por la carga del archivo
     * @throws java.io.FileNotFoundException
     */
    public boolean cargarArchivoCierreComercial(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            UploadedFile archivo, String usuario, int perfil, String origenArchivo)
            throws MalformedURLException, FileNotFoundException, ApplicationException,
            SearchCuentasMatricesFault, UploadCuentasMatricesFault {

        boolean respuesta = false;
        CmtArchivosVtMglManager archivosVtMglManager = new CmtArchivosVtMglManager();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        int maximo;
        CmtCuentaMatrizMgl cuentaMatrizMgl = ordenTrabajoMgl.getCmObj();

        try {

            URL url;
            ParametrosMglManager parametros = new ParametrosMglManager();
            ParametrosMgl paramUser = parametros.findByAcronimoName(Constant.USER_AUTENTICACION_GESTOR_DOCUMENTAL);
            String user = "";
            if (paramUser != null) {
                user = paramUser.getParValor();
            }

            ParametrosMgl paramPass = parametros.findByAcronimoName(Constant.PASS_AUTENTICACION_GESTOR_DOCUMENTAL);
            String pass = "";
            if (paramPass != null) {
                pass = paramPass.getParValor();
            }

            ParametrosMgl param = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_UPLOAD_CCMM);
            String ruta = "";
            if (param != null) {
                ruta = param.getParValor();
            }

            String tipoDocumental = "";
            CmtBasicaMgl basicaTipoDoc = basicaMglManager.
                    findByCodigoInternoApp(Constant.TIPO_DOCUMENTAL_CUENTA_MATRIZ);

            if (basicaTipoDoc != null) {
                tipoDocumental = basicaTipoDoc.getNombreBasica();
            }

            String empresa = "";
            ParametrosMgl param2 = parametros.findByAcronimoName(Constant.PROPERTY_UCM_TIPO_EMPRESA);
            if (param2 != null) {
                empresa = param2.getParValor();
            }

            url = new URL(null, ruta, new sun.net.www.protocol.http.Handler());

            ClientGestorDocumental cliente = new ClientGestorDocumental(user, pass);

            FileRequestType request = new FileRequestType();
            DocumentType documentType = new DocumentType();
            DocumentType documentTypeBuscar = new DocumentType();

            FileType fielFileType = new FileType();

            FieldType field1 = new FieldType();
            field1.setName("xdEmpresa");
            field1.setValue(empresa);
            documentType.getField().add(field1);
            documentTypeBuscar.getField().add(field1);

            FieldType field2 = new FieldType();
            field2.setName("xdTipoDocumental");
            field2.setValue(tipoDocumental);
            documentType.getField().add(field2);
            documentTypeBuscar.getField().add(field2);

            FieldType field3 = new FieldType();
            field3.setName("xdNumeroCtaMatriz");
            field3.setValue(cuentaMatrizMgl.getCuentaMatrizId().toString());
            documentType.getField().add(field3);
            documentTypeBuscar.getField().add(field3);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            FieldType field4 = new FieldType();
            field4.setName("xdFechaDocumento");
            field4.setValue(dateFormat.format(date));
            documentType.getField().add(field4);
            documentTypeBuscar.getField().add(field4);

            maximo = archivosVtMglManager.selectMaximoSecXOt(ordenTrabajoMgl, origenArchivo);

            if (maximo == 0) {
                maximo++;
            } else {
                maximo++;
            }

            String numUnico = ordenTrabajoMgl.getIdOt().toString() + String.valueOf(maximo) + origenArchivo;

            FieldType field5 = new FieldType();
            field5.setName("xdIdProceso");
            field5.setValue(numUnico);
            documentType.getField().add(field5);
            documentTypeBuscar.getField().add(field5);

            FieldType field6 = new FieldType();
            field6.setName("xdDireccionPredio");
            if (cuentaMatrizMgl.getDireccionPrincipal() != null && cuentaMatrizMgl.getDireccionPrincipal().getDireccionObj() != null) {
                field6.setValue(cuentaMatrizMgl.getDireccionPrincipal().getDireccionObj().getDirFormatoIgac());
            } else {
                field6.setValue("Sin Direccion");
            }

            documentType.getField().add(field6);
            documentTypeBuscar.getField().add(field6);

            FieldType field7 = new FieldType();
            field7.setName("xdPredio");
            field7.setValue(cuentaMatrizMgl.getNombreCuenta());
            documentType.getField().add(field7);
            documentTypeBuscar.getField().add(field7);

            FieldType field8 = new FieldType();
            field8.setName("xdIdOrden");
            field8.setValue(ordenTrabajoMgl.getIdOt().toString());
            documentType.getField().add(field8);
            documentTypeBuscar.getField().add(field8);

            FieldType field9 = new FieldType();
            field9.setName("xdNumeroIdentificacion");
            field9.setValue(cuentaMatrizMgl.getNumeroCuenta().toString());
            documentType.getField().add(field9);
            documentTypeBuscar.getField().add(field9);

            fielFileType.setName(archivo.getFileName());
            fielFileType.setContent(archivo.getContent());

            request.setDocument(documentType);
            request.setFile(fielFileType);

            ResponseType response = cliente.insert(request, url);
            ActionStatusEnumType status = response.getActionStatus();

            String urlArchivo = null;

            if (status.value().equalsIgnoreCase("success")) {

                //Consultamos el archivo subido al gestor
                ParametrosMgl paramUrl = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_SEARCH_CCMM);
                String rutaBuscar = "";
                if (paramUrl != null) {
                    rutaBuscar = paramUrl.getParValor();
                }

                URL urlBuscar = new URL(null, rutaBuscar, new sun.net.www.protocol.http.Handler());
                RequestType requestBuscar = new RequestType();
                requestBuscar.setDocument(documentTypeBuscar);

                ResponseType responseBuscar = cliente.find(requestBuscar, urlBuscar);
                ActionStatusEnumType statusBuscar = responseBuscar.getActionStatus();

                if (statusBuscar.value().equalsIgnoreCase("success")) {

                    for (DocumentType documentType1 : responseBuscar.getDocument()) {
                        if (documentType1.getField().size() > 0) {

                            for (FieldType atributos : documentType1.getField()) {
                                if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                    urlArchivo = atributos.getValue();
                                }
                            }

                        }

                    }
                }

                if (urlArchivo != null && !urlArchivo.isEmpty()) {
                    CmtArchivosVtMgl cmtArchivosVtMgl = new CmtArchivosVtMgl();
                    cmtArchivosVtMgl.setOrdenTrabajoMglobj(ordenTrabajoMgl);
                    cmtArchivosVtMgl.setSecArchivo(maximo);
                    cmtArchivosVtMgl.setRutaArchivo(urlArchivo);
                    cmtArchivosVtMgl.setNombreArchivo(archivo.getFileName());
                    cmtArchivosVtMgl.setOrigenArchivos(origenArchivo);
                    cmtArchivosVtMgl = archivosVtMglManager.create(cmtArchivosVtMgl, usuario, perfil);
                    respuesta = cmtArchivosVtMgl.getIdArchivosVt() != null;

                } else {
                    LOGGER.info("Empiezan los 10 segundos");
                    esperar(10);

                    responseBuscar = cliente.find(requestBuscar, urlBuscar);
                    ActionStatusEnumType statusBuscar2 = responseBuscar.getActionStatus();

                    if (statusBuscar2.value().equalsIgnoreCase("success")) {

                        for (DocumentType documentType1 : responseBuscar.getDocument()) {
                            if (documentType1.getField().size() > 0) {

                                for (FieldType atributos : documentType1.getField()) {
                                    if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                        urlArchivo = atributos.getValue();
                                    }
                                }

                            }

                        }
                    }
                    if (urlArchivo != null && !urlArchivo.isEmpty()) {
                        CmtArchivosVtMgl cmtArchivosVtMgl = new CmtArchivosVtMgl();
                        cmtArchivosVtMgl.setOrdenTrabajoMglobj(ordenTrabajoMgl);
                        cmtArchivosVtMgl.setSecArchivo(maximo);
                        cmtArchivosVtMgl.setRutaArchivo(urlArchivo);
                        cmtArchivosVtMgl.setNombreArchivo(archivo.getFileName());
                        cmtArchivosVtMgl.setOrigenArchivos(origenArchivo);
                        cmtArchivosVtMgl = archivosVtMglManager.create(cmtArchivosVtMgl, usuario, perfil);
                        respuesta = cmtArchivosVtMgl.getIdArchivosVt() != null;
                    } else {
                        respuesta = false;
                    }
                }
            } else {
                respuesta = false;
            }

        } catch (ApplicationException | IOException ex) {
            String msg = "Error al momento de cargar el archivo. EX000 '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return respuesta;
    }

    /**
     * Metodo para obtener los documentos en el UCM de una OT Autor: Victor
     * Bocanegra
     *
     * @param ordenTrabajoMgl a la cual se le van adjuntar los documentos
     * @return List<String>
     * @throws javax.xml.datatype.DatatypeConfigurationException
     * @throws ApplicationException Excepcion lanzada por la consulta
     * @throws java.io.FileNotFoundException
     */
    public List<String> obtenerArchivosCierreComercial(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws DatatypeConfigurationException, FileNotFoundException,
            ApplicationException, SearchCuentasMatricesFault {

        Set<String> lstUrls = new HashSet<>();
        List<String> urls = new ArrayList<>();

        try {
            URL url;
            ParametrosMglManager parametros = new ParametrosMglManager();
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

            ParametrosMgl paramUser = parametros.findByAcronimoName(Constant.USER_AUTENTICACION_GESTOR_DOCUMENTAL);
            String user = "";
            if (paramUser != null) {
                user = paramUser.getParValor();
            }

            ParametrosMgl paramPass = parametros.findByAcronimoName(Constant.PASS_AUTENTICACION_GESTOR_DOCUMENTAL);
            String pass = "";
            if (paramPass != null) {
                pass = paramPass.getParValor();
            }

            ParametrosMgl param = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_SEARCH_CCMM);
            String ruta = "";
            if (param != null) {
                ruta = param.getParValor();
            }

            String tipoDocumental = "";
            CmtBasicaMgl basicaTipoDoc = basicaMglManager.
                    findByCodigoInternoApp(Constant.TIPO_DOCUMENTAL_CUENTA_MATRIZ);

            if (basicaTipoDoc != null) {
                tipoDocumental = basicaTipoDoc.getNombreBasica();
            }
            String empresa = "";
            ParametrosMgl param2 = parametros.findByAcronimoName(Constant.PROPERTY_UCM_TIPO_EMPRESA);
            if (param2 != null) {
                empresa = param2.getParValor();
            }

            url = new URL(null, ruta, new sun.net.www.protocol.http.Handler());
            ClientGestorDocumental cliente = new ClientGestorDocumental(user, pass);

            DocumentType documentTypeBuscar = new DocumentType();

            FieldType field1 = new FieldType();
            field1.setName("xdEmpresa");
            field1.setValue(empresa);
            documentTypeBuscar.getField().add(field1);

            FieldType field2 = new FieldType();
            field2.setName("xdTipoDocumental");
            field2.setValue(tipoDocumental);
            documentTypeBuscar.getField().add(field2);

            FieldType field3 = new FieldType();
            field3.setName("xdOrdenServicio");
            field3.setValue(ordenTrabajoMgl.getIdOt().toString());
            documentTypeBuscar.getField().add(field3);

            RequestType requestBuscar = new RequestType();
            requestBuscar.setDocument(documentTypeBuscar);

            ResponseType responseBuscar = cliente.find(requestBuscar, url);
            ActionStatusEnumType statusBuscar = responseBuscar.getActionStatus();

            if (statusBuscar.value().equalsIgnoreCase("success")) {

                for (DocumentType document1 : responseBuscar.getDocument()) {
                    if (document1.getField().size() > 0) {

                        for (FieldType atributos : document1.getField()) {
                            if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                urls.add(atributos.getValue());
                            }
                        }
                        lstUrls.addAll(urls);
                        urls.clear();
                        urls.addAll(lstUrls);
                    }

                }
            }
        } catch (MalformedURLException ex) {
            String msg = "Error al momento de cargar el archivo. EX000 '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return urls;
    }

    /**
     * Actualiza una Orden de Trabajo.Permite realizar la actualizacion de una
     * Orden de Trabajo proceso cierre comercial en el repositorio.
     *
     * @author Victor Bocanegra
     * @param ot Orden de Trabajo a actualizar en el repositorio
     * @param nuevoEstado estado interno cierre comercial
     * @param usuario
     * @param perfil
     * @return Orden de Trabajo actualizada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl actualizarOtCierreComercial(CmtOrdenTrabajoMgl ot,
            BigDecimal nuevoEstado, String usuario, int perfil) throws ApplicationException {

        if (nuevoEstado.compareTo(ot.getEstadoInternoObj().getBasicaId()) != 0) {
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
            CmtBasicaMgl nuevoEstadoObj = basicaMglManager.findById(nuevoEstado);
            ot.setEstadoInternoObj(nuevoEstadoObj);
        }

        return dao.updateCm(ot, usuario, perfil);
    }

    public void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            String msg = "Ocurrio un error en el timeout '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }

    /**
     * Busca las Ordenes de Trabajo asociadas a un tipo de trabajo.
     *
     * @author Victor Bocanegra
     * @param tipoOtMgl tipo de trabajo
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a un
     * tipo de trabajo
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByTipoTrabajo(CmtTipoOtMgl tipoOtMgl) throws ApplicationException {

        return dao.findByTipoOt(tipoOtMgl);
    }

    /**
     * Actualiza el estado de la Ot de acometida y sus subedificios asociados
     * despues de la creacion.
     *
     * @author Victor Bocanegra
     * @param ot de acometida
     * @param usuario en sesion
     * @param perfil del usuario
     * @throws ApplicationException
     */
    public void changeStatusCmByFlowAco(CmtOrdenTrabajoMgl ot, String usuario, int perfil)
            throws ApplicationException, ApplicationException {

        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        CmtVisitaTecnicaMglManager cmtVisitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();

        CmtSubEdificiosVtManager subEdificiosVtManager = new CmtSubEdificiosVtManager();
        List<CmtSubEdificiosVt> suebEdificiosVts = subEdificiosVtManager.findByIdOtAcometida(ot.getIdOt());

        if (suebEdificiosVts.size() > 0) {

            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl
                    = cmtEstadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(
                            ot.getTipoOtObj().getTipoFlujoOt(),
                            ot.getEstadoInternoObj(),
                            ot.getBasicaIdTecnologia());

            for (CmtSubEdificiosVt cmtSubEdificiosVt : suebEdificiosVts) {
                if (cmtSubEdificiosVt.getEstadoRegistro() == 1
                        && cmtSubEdificiosVt.getSubEdificioObj() != null) {
                    CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdificiosVt.getSubEdificioObj();
                    //Verificamos si no cambia estado
                    if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null
                            && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null) {
                        cmtSubEdificioMgl.setEstadoSubEdificioObj(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
                    }
                    cmtSubEdificioMglManager.update(cmtSubEdificioMgl, usuario, perfil, false, false);
                    cmtVisitaTecnicaMglManager.crearRegistroTecnologiaSubXAcometida(
                            cmtSubEdificiosVt, usuario, perfil, ot);
                }
            }

        }

    }

    /**
     * Busca una Orden de Trabajo por ID. Permite realizar la busqueda de una
     * Orden de Trabajo por su ID en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idOt ID de la Orden de Trabajo a buscar en el repositorio
     * @param estadosxflujoList lista de estados por flujos a la cual tiene
     * acceso el usuario en sesion
     * @return Orden de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findOtByIdAndPermisos(BigDecimal idOt, List<BigDecimal> estadosxflujoList)
            throws ApplicationException {

        CmtOrdenTrabajoMgl ordenTrabajo = dao.findByIdOtAndEstXFlu(idOt, estadosxflujoList);

        CmtSubEdificiosVtManager subEdificiosVtManager = new CmtSubEdificiosVtManager();
        List<CmtSubEdificioDto> listaSubEdificio = new ArrayList<>();

        if (ordenTrabajo != null) {

            if (ordenTrabajo.getTipoOtObj() != null
                    && ordenTrabajo.getTipoOtObj().getEsTipoVT().equalsIgnoreCase("N")) {

                if (ordenTrabajo.getClaseOrdenTrabajo() == null) {
                    ordenTrabajo.setClaseOrdenTrabajo(new CmtBasicaMgl());
                }

                List<CmtSubEdificiosVt> suebEdificiosVts = subEdificiosVtManager.findByIdOtAcometida(idOt);
                if (suebEdificiosVts.size() > 0) {
                    for (CmtSubEdificiosVt s : suebEdificiosVts) {
                        if (s.getEstadoRegistro() == 1) {
                            CmtSubEdificioDto subEdiDto = new CmtSubEdificioDto();
                            subEdiDto.setNombre(s.getNombreSubEdificio());
                            subEdiDto.setCodigoNodo(s.getNodo() != null
                                    ? s.getNodo().getNodCodigo() : "");
                            subEdiDto.setPiso(String.valueOf(s.getNumeroPisosCasas()));
                            listaSubEdificio.add(subEdiDto);

                        }
                    }
                }
                if (!listaSubEdificio.isEmpty()) {
                    ordenTrabajo.setListaSubEdificio(listaSubEdificio);
                }
            } else {
                //valbuenayf inicio ajuste para nombre nodo y piso del subedificio
                if (ordenTrabajo.getClaseOrdenTrabajo() == null) {
                    ordenTrabajo.setClaseOrdenTrabajo(new CmtBasicaMgl());
                }
                if (ordenTrabajo.getVisitaTecnicaMglList() != null
                        && !ordenTrabajo.getVisitaTecnicaMglList().isEmpty()) {

                    for (CmtVisitaTecnicaMgl v : ordenTrabajo.getVisitaTecnicaMglList()) {
                        if (v.getEstadoVisitaTecnica().compareTo(BigDecimal.ONE) == 0 && v.getEstadoRegistro() == 1) {
                            for (CmtSubEdificiosVt s : v.getListCmtSubEdificiosVt()) {
                                if (s.getEstadoRegistro() == 1) {
                                    CmtSubEdificioDto subEdiDto = new CmtSubEdificioDto();
                                    subEdiDto.setNombre(s.getNombreSubEdificio());
                                    subEdiDto.setCodigoNodo(s.getNodo() != null
                                            ? s.getNodo().getNodCodigo() : "");
                                    subEdiDto.setPiso(String.valueOf(s.getNumeroPisosCasas()));
                                    listaSubEdificio.add(subEdiDto);
                                }
                            }
                        }
                    }
                    if (!listaSubEdificio.isEmpty()) {
                        ordenTrabajo.setListaSubEdificio(listaSubEdificio);
                    }
                }
            }
        }
        return ordenTrabajo;
    }

    private void configParametrosWS() {
        ResourceEJB resourceEJB = new ResourceEJB();
        Initialized.getInstance();
        Parametros param;
        try {
            param = resourceEJB.queryParametros(co.com.telmex.catastro.services.util.Constant.URL_WS_ONYX);
            if (param != null) {
                wsURL = param.getValor();
            }
            param = resourceEJB.queryParametros(co.com.telmex.catastro.services.util.Constant.SERVICE_WS_ONYX);
            if (param != null) {
                wsService = param.getValor();
            }
        } catch (Exception ex) {
            String msg = "Error al momento de cargar los parámetros. EX000: '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByFiltroParaGestionExportarOt(
            List<BigDecimal> estadosxflujoList, CmtFiltroConsultaOrdenesDto filtro)
            throws ApplicationException {

        return dao.findByFiltroParaGestionExportarOt(estadosxflujoList, filtro);
    }

    /**
     * Actualiza una Orden de Trabajo. Permite realizar la actualizacion de una
     * Orden de Trabajo
     *
     * @author Victor Bocanegra
     * @param ot Orden de Trabajo a actualizar en el repositorio
     * @param usuario que actualiza
     * @param perfil del usuario
     * @return Orden de Trabajo actualizada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl actualizarOtCcmm(CmtOrdenTrabajoMgl ot,
            String usuario, int perfil) throws ApplicationException {

        return dao.updateCm(ot, usuario, perfil);
    }

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo que generan OTs de acometidas por
     * medio de los estado X flujo, comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByOtCloseForGenerarAcometida(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro, List<CmtTipoOtMgl> tiposOtgeneranAco,
            int paginaSelected, int maxResults) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }

        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl estIntPenDocCierreComercial = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_PENDOCCIECOM);
        CmtBasicaMgl estIntPenCierreComercial = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_PENCIECOM);
        CmtBasicaMgl cierreComercialOk = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_CIERRECOM);

        List<BigDecimal> estIntCierreCom = new ArrayList<>();
        List<BigDecimal> tiposOtgeneranAcoIds = new ArrayList<>();

        if (estIntPenDocCierreComercial != null && estIntPenDocCierreComercial.getBasicaId() != null) {
            estIntCierreCom.add(estIntPenDocCierreComercial.getBasicaId());
        }

        if (estIntPenCierreComercial != null && estIntPenCierreComercial.getBasicaId() != null) {
            estIntCierreCom.add(estIntPenCierreComercial.getBasicaId());
        }

        if (cierreComercialOk != null && cierreComercialOk.getBasicaId() != null) {
            estIntCierreCom.add(cierreComercialOk.getBasicaId());
        }

        if (tiposOtgeneranAco != null && tiposOtgeneranAco.size() > 0) {

            for (CmtTipoOtMgl tipoOtMgl : tiposOtgeneranAco) {
                tiposOtgeneranAcoIds.add(tipoOtMgl.getIdTipoOt());
            }
        }

        List<CmtOrdenTrabajoMgl> result = dao.findByOtCloseForGenerarAcometida(estadosxflujoList,
                filtro, tiposOtgeneranAcoIds, estIntCierreCom, firstResult, maxResults);

        return result;
    }

    /**
     * Cuenta las Ordenes de Trabajo por estados X flujo.Permite contar la las
     * Ordenes de Trabajo que generan OTs de acometidas por medio de los estado
     * X flujo, comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public int getCountOtCloseForGenerarAcometida(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro, List<CmtTipoOtMgl> tiposOtgeneranAco) throws ApplicationException {

        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

        CmtBasicaMgl estIntPenDocCierreComercial = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_PENDOCCIECOM);
        CmtBasicaMgl estIntPenCierreComercial = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_PENCIECOM);
        CmtBasicaMgl cierreComercialOk = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_CIERRECOM);

        List<BigDecimal> estIntCierreCom = new ArrayList<>();
        List<BigDecimal> tiposOtgeneranAcoIds = new ArrayList<>();

        if (estIntPenDocCierreComercial != null && estIntPenDocCierreComercial.getBasicaId() != null) {
            estIntCierreCom.add(estIntPenDocCierreComercial.getBasicaId());
        }

        if (estIntPenCierreComercial != null && estIntPenCierreComercial.getBasicaId() != null) {
            estIntCierreCom.add(estIntPenCierreComercial.getBasicaId());
        }

        if (cierreComercialOk != null && cierreComercialOk.getBasicaId() != null) {
            estIntCierreCom.add(cierreComercialOk.getBasicaId());
        }

        if (tiposOtgeneranAco != null && tiposOtgeneranAco.size() > 0) {

            for (CmtTipoOtMgl tipoOtMgl : tiposOtgeneranAco) {
                tiposOtgeneranAcoIds.add(tipoOtMgl.getIdTipoOt());
            }
        }

        int result = dao.getCountOtCloseForGenerarAcometida(estadosxflujoList,
                filtro, tiposOtgeneranAcoIds, estIntCierreCom);

        return result;
    }

    /**
     * Busca una Orden de Trabajo por estados X flujo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param idOt id de la Ot a buscar
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @param conPermisos consulta con roles
     * @return CmtOrdenTrabajoMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findIdOtCloseForGenerarAcometidaAndPermisos(List<BigDecimal> estadosxflujoList,
            List<CmtTipoOtMgl> tiposOtgeneranAco, BigDecimal idOt, boolean conPermisos)
            throws ApplicationException {

        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

        CmtBasicaMgl estIntPenDocCierreComercial = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_PENDOCCIECOM);
        CmtBasicaMgl estIntPenCierreComercial = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_PENCIECOM);
        CmtBasicaMgl cierreComercialOk = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_INT_CIERRECOM);

        List<BigDecimal> estIntCierreCom = new ArrayList<>();
        List<BigDecimal> tiposOtgeneranAcoIds = new ArrayList<>();

        if (estIntPenDocCierreComercial != null && estIntPenDocCierreComercial.getBasicaId() != null) {
            estIntCierreCom.add(estIntPenDocCierreComercial.getBasicaId());
        }

        if (estIntPenCierreComercial != null && estIntPenCierreComercial.getBasicaId() != null) {
            estIntCierreCom.add(estIntPenCierreComercial.getBasicaId());
        }

        if (cierreComercialOk != null && cierreComercialOk.getBasicaId() != null) {
            estIntCierreCom.add(cierreComercialOk.getBasicaId());
        }

        if (tiposOtgeneranAco != null && tiposOtgeneranAco.size() > 0) {

            for (CmtTipoOtMgl tipoOtMgl : tiposOtgeneranAco) {
                tiposOtgeneranAcoIds.add(tipoOtMgl.getIdTipoOt());
            }
        }

        CmtOrdenTrabajoMgl result = dao.findIdOtCloseForGenerarAcometidaAndPermisos(estadosxflujoList, tiposOtgeneranAcoIds, estIntCierreCom, idOt, conPermisos);

        return result;
    }

    public CmtBasicaMgl cambiarEstadosForRegla(CmtVisitaTecnicaMgl visitaTecnicaMgl,
            String usuario, int perfil) throws ApplicationException {
        CmtBasicaMgl estadoCombinado = null;
        if (visitaTecnicaMgl.getListCmtSubEdificiosVt() != null
                && !visitaTecnicaMgl.getListCmtSubEdificiosVt().isEmpty()) {

            CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
            CmtSubEdificioMglManager subEdificioMglManager = new CmtSubEdificioMglManager();
            CmtUtilidadesCM utilidadesCM = new CmtUtilidadesCM();

            for (CmtSubEdificiosVt cmtSubEdificiosVt : visitaTecnicaMgl.getListCmtSubEdificiosVt()) {
                if (cmtSubEdificiosVt.getEstadoRegistro() == 1
                        && cmtSubEdificiosVt.getSubEdificioObj() != null) {
                    CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdificiosVt.getSubEdificioObj();
                    List<CmtTecnologiaSubMgl> lstCmtTecnologiaSubMgls = tecnologiaSubMglManager.findTecnoSubBySubEdi(cmtSubEdificioMgl);
                    List<CmtBasicaMgl> estadosTecnologias = null;
                    List<BigDecimal> estados = new ArrayList<>();
                    if (lstCmtTecnologiaSubMgls != null && !lstCmtTecnologiaSubMgls.isEmpty()) {
                        estadosTecnologias = new ArrayList<>();
                        for (CmtTecnologiaSubMgl tecnologiaSubMgl : lstCmtTecnologiaSubMgls) {
                            if (tecnologiaSubMgl.getBasicaIdEstadosTec() != null) {
                                estadosTecnologias.add(tecnologiaSubMgl.getBasicaIdEstadosTec());
                            }
                        }
                    }

                    if (estadosTecnologias != null && !estadosTecnologias.isEmpty()) {
                        estadoCombinado = utilidadesCM.obtenerEstadoCmRrByReglaEstado(lstCmtTecnologiaSubMgls);
                        if (estadoCombinado != null) {
                            cmtSubEdificioMgl.setEstadoSubEdificioObj(estadoCombinado);
                            subEdificioMglManager.update(cmtSubEdificioMgl, usuario, perfil, false, false);
                        }
                    }
                }
            }

        }
        return estadoCombinado;
    }

    /**
     * Elimina una orden de trabajo de rr.
     *
     * @author Victor Bocanegra
     * @param agenda con la informacion de la ot
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean eliminarOtRRporAgendamiento(CmtAgendamientoMgl agenda, String usuario)
            throws ApplicationException {

        CmtOrdenTrabajoRrMglManager ordenTrabajoRrMglManager = new CmtOrdenTrabajoRrMglManager();
        CmtOrdenTrabajoRrMgl ordenTrabajoRrMgl = new CmtOrdenTrabajoRrMgl();
        ordenTrabajoRrMgl.setOtObj(agenda.getOrdenTrabajo());
        return ordenTrabajoRrMglManager.ordenTrabajoEdificioDelete(ordenTrabajoRrMgl, agenda.getIdOtenrr(), usuario);

    }

    /**
     * Metodo para consultar los trabajos que tiene una CM en RR
     *
     * @author Victor bocanegra
     * @param numeroCuenta cnumero de la cuenta matriz
     * @return ResponseOtEdificiosList encontrado
     * @throws ApplicationException
     */
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(String numeroCuenta)
            throws ApplicationException {
        CmtOrdenTrabajoRrMglManager manager = new CmtOrdenTrabajoRrMglManager();
        return manager.ordenTrabajoEdificioQuery(numeroCuenta);

    }

    /**
     * Metodo para consumir el nuevo servicio de consulta a una OT hija en onix
     *
     * @author Victor bocanegra
     * @param numeroOtHija numero de la ot hija a consultar
     * @return ResponseOtEdificiosList encontrado
     */
    public CmtOnyxResponseDto consultarOTHijaOnyx(String numeroOtHija) throws ApplicationException {

        OtHijaOnixRequest otHijaOnixRequest = new OtHijaOnixRequest();
        CmtOnyxResponseDto cmtOnyxResponseDto = null;
        otHijaOnixRequest.setIdOtHija(numeroOtHija);

        PortManager portManager = new PortManager(wsURL, wsService);
        ConsultaList_Response consultaList_Response = new ConsultaList_Response();
        try {
            consultaList_Response = portManager.consultarOtHija(otHijaOnixRequest);

            if (consultaList_Response != null
                    && consultaList_Response.getConsultaMglOnyxList() != null) {
                ConsultaMglOnyxList consultaMglOnyxList = consultaList_Response.getConsultaMglOnyxList();
                cmtOnyxResponseDto = new CmtOnyxResponseDto();

                cmtOnyxResponseDto.setNIT_Cliente(consultaMglOnyxList.getNIT_Cliente() != null ? consultaMglOnyxList.getNIT_Cliente() : "");
                cmtOnyxResponseDto.setNombre(consultaMglOnyxList.getNombre() != null ? consultaMglOnyxList.getNombre() : "");
                cmtOnyxResponseDto.setNombre_OT_Hija(consultaMglOnyxList.getNombre_OT_Hija() != null ? consultaMglOnyxList.getNombre_OT_Hija() : "");
                cmtOnyxResponseDto.setOTP(consultaMglOnyxList.getOTP());
                cmtOnyxResponseDto.setOTH(consultaMglOnyxList.getOTH());
                cmtOnyxResponseDto.setFechaCreacionOTH(consultaMglOnyxList.getFechaCreacionOTH() != null ? consultaMglOnyxList.getFechaCreacionOTH() : "");
                cmtOnyxResponseDto.setFechaCreacionOTP(consultaMglOnyxList.getFechaCreacionOTP() != null ? consultaMglOnyxList.getFechaCreacionOTP() : "");
                cmtOnyxResponseDto.setContactoTecnicoOTP(consultaMglOnyxList.getNombreContactoTecnicoPredOTP() != null && !consultaMglOnyxList.getNombreContactoTecnicoPredOTP().isEmpty() ? consultaMglOnyxList.getNombreContactoTecnicoPredOTP() : "");
                cmtOnyxResponseDto.setTelefonoContacto(consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP() != null && !consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP().isEmpty() ? consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP() : "");
                cmtOnyxResponseDto.setDescripcion(consultaMglOnyxList.getDescripcion() != null && !consultaMglOnyxList.getDescripcion().isEmpty() ? consultaMglOnyxList.getDescripcion() : "");
                cmtOnyxResponseDto.setDireccion(consultaMglOnyxList.getDireccionFacturacion() != null && !consultaMglOnyxList.getDireccionFacturacion().isEmpty() ? consultaMglOnyxList.getDireccionFacturacion() : "");
                cmtOnyxResponseDto.setSegmento(consultaMglOnyxList.getSegmento() != null && !consultaMglOnyxList.getSegmento().isEmpty() ? consultaMglOnyxList.getSegmento() : "");
                cmtOnyxResponseDto.setTipoServicio(consultaMglOnyxList.getSolucionPrev() != null && !consultaMglOnyxList.getSolucionPrev().isEmpty() ? consultaMglOnyxList.getSolucionPrev() : "");
                cmtOnyxResponseDto.setServicios(consultaMglOnyxList.getServicios() != null && !consultaMglOnyxList.getServicios().isEmpty() ? consultaMglOnyxList.getServicios() : "");
                cmtOnyxResponseDto.setRecurrenteMensual(consultaMglOnyxList.getRecurrenteMensual() != null ? consultaMglOnyxList.getRecurrenteMensual() : new BigDecimal(0));
                cmtOnyxResponseDto.setCodigoServicio(consultaMglOnyxList.getCodigoServicio() != null && !consultaMglOnyxList.getCodigoServicio().isEmpty() ? consultaMglOnyxList.getCodigoServicio() : "");
                cmtOnyxResponseDto.setVendedor(consultaMglOnyxList.getVendedor() != null && !consultaMglOnyxList.getVendedor().isEmpty() ? consultaMglOnyxList.getVendedor() : "");
                cmtOnyxResponseDto.setTelefono(consultaMglOnyxList.getTelefono() != null && !consultaMglOnyxList.getTelefono().isEmpty() ? consultaMglOnyxList.getTelefono() : "");
                cmtOnyxResponseDto.setNotasOTH(consultaMglOnyxList.getNotasOTH() != null && !consultaMglOnyxList.getNotasOTH().isEmpty() ? consultaMglOnyxList.getNotasOTH() : "");
                cmtOnyxResponseDto.setEstadoOTH(consultaMglOnyxList.getEstadoOTH() != null && !consultaMglOnyxList.getEstadoOTH().isEmpty() ? consultaMglOnyxList.getEstadoOTH() : "");
                cmtOnyxResponseDto.setEstadoOTP(consultaMglOnyxList.getEstadoOTP() != null && !consultaMglOnyxList.getEstadoOTH().isEmpty() ? consultaMglOnyxList.getEstadoOTP() : "");
                cmtOnyxResponseDto.setFechaCompromisoOTP(consultaMglOnyxList.getFechaCompromisoOTP() != null && !consultaMglOnyxList.getFechaCompromisoOTP().isEmpty() ? consultaMglOnyxList.getFechaCompromisoOTP() : "");
                cmtOnyxResponseDto.setCodResolucion1OTP(consultaMglOnyxList.getCodResolucion1OTP() != null && !consultaMglOnyxList.getCodResolucion1OTP().isEmpty() ? consultaMglOnyxList.getCodResolucion1OTP() : "");
                cmtOnyxResponseDto.setCodResolucion2OTP(consultaMglOnyxList.getCodResolucion2OTP() != null && !consultaMglOnyxList.getCodResolucion2OTP().isEmpty() ? consultaMglOnyxList.getCodResolucion2OTP() : "");
                cmtOnyxResponseDto.setCodResolucion3OTP(consultaMglOnyxList.getCodResolucion3OTP() != null && !consultaMglOnyxList.getCodResolucion3OTP().isEmpty() ? consultaMglOnyxList.getCodResolucion3OTP() : "");
                cmtOnyxResponseDto.setCodResolucion4OTP(consultaMglOnyxList.getCodResolucion4OTP() != null && !consultaMglOnyxList.getCodResolucion4OTP().isEmpty() ? consultaMglOnyxList.getCodResolucion4OTP() : "");
                cmtOnyxResponseDto.setCodResolucion1OTH(consultaMglOnyxList.getCodResolucion1OTH() != null && !consultaMglOnyxList.getCodResolucion1OTH().isEmpty() ? consultaMglOnyxList.getCodResolucion1OTH() : "");
                cmtOnyxResponseDto.setCodResolucion2OTH(consultaMglOnyxList.getCodResolucion2OTH() != null && !consultaMglOnyxList.getCodResolucion2OTH().isEmpty() ? consultaMglOnyxList.getCodResolucion2OTH() : "");
                cmtOnyxResponseDto.setCodResolucion3OTH(consultaMglOnyxList.getCodResolucion3OTH() != null && !consultaMglOnyxList.getCodResolucion3OTH().isEmpty() ? consultaMglOnyxList.getCodResolucion3OTH() : "");
                cmtOnyxResponseDto.setCodResolucion4OTH(consultaMglOnyxList.getCodResolucion4OTH() != null && !consultaMglOnyxList.getCodResolucion4OTH().isEmpty() ? consultaMglOnyxList.getCodResolucion4OTH() : "");
                cmtOnyxResponseDto.setEmailCTec(consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP() != null && !consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP().isEmpty() ? consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP() : "");
                cmtOnyxResponseDto.setaImplement(consultaMglOnyxList.getAliadoImplementacion() != null && !consultaMglOnyxList.getAliadoImplementacion().isEmpty() ? consultaMglOnyxList.getAliadoImplementacion() : "");
            }
        } catch (ApplicationException ex) {
            String msg = "Error al consultar OT en Onyx: '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al consultar OT en Onyx: '. EX000: " + ex.getMessage(), ex);

        }
        return cmtOnyxResponseDto;
    }

    /**
     * Crea y actualiza una orden en rr
     *
     * @author Victor Bocanegra
     * @param numeroCuentaParametro
     * @param tipoOtHhppMgl
     * @param usuario
     * @return
     * @throws ApplicationException
     */
    public String crearOtRRporAgendamientoHhpp(String numeroCuentaParametro,
            TipoOtHhppMgl tipoOtHhppMgl, String usuario, OtHhppMgl ot) throws ApplicationException {

        CmtOrdenTrabajoRrMglManager ordenTrabajoRrMglManager = new CmtOrdenTrabajoRrMglManager();
        String tipoTrabajoRr = tipoOtHhppMgl.getTipoTrabajoRR().getCodigoBasica();

        String numeroOT = ordenTrabajoRrMglManager.crearOrdenTrabajoRrHhpp(numeroCuentaParametro, tipoTrabajoRr, usuario, ot);
        if (numeroOT != null && !numeroOT.isEmpty()) {
            ordenTrabajoRrMglManager.actualizarEstadoResultadoOTRRHhpp(numeroCuentaParametro, numeroOT, usuario, tipoOtHhppMgl, true, ot, null);
        }
        return numeroOT;
    }

    /**
     * Elimina una orden de trabajo de rr.
     *
     * @author Victor Bocanegra
     * @param numeroCuentaParametro
     * @param numeroOt numero de ot a eliminar
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean ordenTrabajoEdificioDeleteHhpp(String numeroCuentaParametro, String numeroOt, String usuario)
            throws ApplicationException {

        CmtOrdenTrabajoRrMglManager ordenTrabajoRrMglManager = new CmtOrdenTrabajoRrMglManager();
        return ordenTrabajoRrMglManager.ordenTrabajoEdificioDeleteHhpp(numeroCuentaParametro, numeroOt, usuario);
    }

    /**
     * Busqueda el ultimo estado de la orden en auditoria con estado razonado
     *
     * @author victor bocanegra
     * @param ot valor de la cuenta matriz para la busqueda
     * @param estadoIntActual valor de la basica de tecnologia para la busqueda
     * @return CmtOrdenTrabajoAuditoriaMgl
     */
    public CmtOrdenTrabajoAuditoriaMgl findUltimoEstadoOtRazonada(CmtOrdenTrabajoMgl ot,
            BigDecimal estadoIntActual) throws ApplicationException {
        return dao.findUltimoEstadoOtRazonada(ot, estadoIntActual);
    }

    /**
     * Actualiza con OK la ot en RR
     *
     * @author victor bocanegra
     * @param ot orden de trabajo
     * @param numeroOT numero de laorden en RR
     * @param estadoFlujo de la orden
     * @param usuario
     * @param perfil
     * @return true or false
     */
    public boolean cerrarOTRR(CmtOrdenTrabajoMgl ot, String numeroOT,
            CmtEstadoxFlujoMgl estadoFlujo, String usuario, int perfil) throws ApplicationException {

        boolean result = false;

        CmtOrdenTrabajoRrMglManager ordenTrabajoRrMglManager = new CmtOrdenTrabajoRrMglManager();
        CmtOrdenTrabajoRrMgl ordenTrabajoRrMgl = new CmtOrdenTrabajoRrMgl();
        ordenTrabajoRrMgl.setTipoTrabajoRR(estadoFlujo.getTipoTrabajoRR());
        ordenTrabajoRrMgl.setOtObj(ot);
        String notaCierre = "Cierre de orden en RR por agendas agendas canceladas - " + usuario + "";
        if (numeroOT != null && !numeroOT.isEmpty()) {
            result = ordenTrabajoRrMglManager.actualizarEstadoResultadoOTRR(ordenTrabajoRrMgl, numeroOT, usuario, estadoFlujo, false, notaCierre);
        }
        return result;
    }

    /**
     * Actualiza la una ot en RR creada desde direcciones
     *
     * @author victor bocanegra
     * @param numeroCuentaParametro numero de la cuenta a la que esta asociada
     * la orden
     * @param numeroOt
     * @param usuario
     * @param tipoOtHhppMgl
     * @param isIncial tipo de cambio de estado
     * @param idOt identificacion de la orden de direcciones
     * @param notaCierre nota de cierre de OT en RR.
     * @return true or false
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean actualizarEstadoResultadoOTRRHhpp(String numeroCuentaParametro, String numeroOt,
            String usuario, TipoOtHhppMgl tipoOtHhppMgl, boolean isIncial,
            OtHhppMgl ot, String notaCierre)
            throws ApplicationException {

        CmtOrdenTrabajoRrMglManager ordenTrabajoRrMglManager = new CmtOrdenTrabajoRrMglManager();
        return ordenTrabajoRrMglManager.actualizarEstadoResultadoOTRRHhpp(numeroCuentaParametro, numeroOt, usuario, tipoOtHhppMgl, isIncial, ot, notaCierre);
    }

    /**
     * Bloquea o Desbloquea una orden. Permite realizar el bloqueo o desbloqueo
     * de una orden en el repositorio para la gestion.
     *
     * @author Victor Bocanegra
     * @param orden orden a bloquear o desbloquear
     * @param bloqueo si es bloqueo o no
     * @param usuario usuario que realiza el bloqueo-desbloque
     * @param perfil perfil
     * @return CmtOrdenTrabajoMgl
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl bloquearDesbloquearOrden(CmtOrdenTrabajoMgl orden,
            boolean bloqueo, String usuario, int perfil) throws ApplicationException {

        String disponiblidadGestion = (bloqueo ? usuario : null);
        orden = dao.find(orden.getIdOt());
        orden.setDisponibilidadGestion(disponiblidadGestion);
        return dao.updateCm(orden, usuario, perfil);
    }

    /**
     * Metodo para notificar una orden de trabajo en ONIX
     *
     * @author Victor Bocanegra
     * @param orden orden a notificar
     * @param numOtHija numero de la Ot hija
     * @return MERNotifyResponseType
     * @throws ApplicationException
     */
    public MERNotifyResponseType notificarOrdenOnix(CmtOrdenTrabajoMgl orden, String numOtHija)
            throws ApplicationException {

        MERNotifyResponseType responseType = null;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        co.com.claro.visitastecnicas.ws.proxy.HeaderRequest headerRequest = new co.com.claro.visitastecnicas.ws.proxy.HeaderRequest();

        GregorianCalendar c = new GregorianCalendar();
        Date fecha = new Date();
        c.setTime(fecha);

        try {

            URL url = null;
            String wsURL1;

            ClientOnix clientOnix = new ClientOnix();

            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            headerRequest.setRequestDate(date2);
            headerRequest.setSystem("MER");

            ParametrosMgl param = parametrosMglManager.
                    findByAcronimoName(Constant.PROPERTY_URL_WS_NOTIFICA_UPDATE_CLOSING_ONIX);
            if (param != null) {
                wsURL1 = param.getParValor();
                url = new URL(wsURL1);
            }

            //Validamos disponibilidad del servicio
            ConsumoGenerico.conectionWsdlTest(url, Constant.PROPERTY_URL_WS_NOTIFICA_UPDATE_CLOSING_ONIX);
            //Fin Validacion disponibilidad del servicio

            MERNotifyRequestType request = new MERNotifyRequestType();
            request.setIncidentMer(orden.getIdOt().toString());
            int otHija = Integer.parseInt(numOtHija);
            request.setIncidentonyx(otHija);

            responseType = clientOnix.merNotify(headerRequest, request, url);

        } catch (ApplicationException | FaultMessage
                | DatatypeConfigurationException | WebServiceException | IOException ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return responseType;

    }

    public void eliminarOTByCM(BigDecimal idCuentaMatriz) throws ApplicationException {
        dao.eliminarOTByCM(idCuentaMatriz);
    }

    /**
     * Metodo para consumir el nuevo servicio de consulta a una OT hija en onix
     *
     * @param numeroOtHija numero de la ot hija a consultar
     * @return ResponseOtEdificiosList encontrado
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    //CONSUMIR SIEMPRE ESTE PARA CONSULTAR OT HIJA ONYX, SERVICIO VIGENTE 16/07/2021
    public CmtOnyxResponseDto consultarOTHijaWsOnyx(String numeroOtHija) throws ApplicationException, DatatypeConfigurationException {
        OtHijaOnixRequestNew otHijaOnixRequestNew = new OtHijaOnixRequestNew();
        HeaderRequest headerRequest = new HeaderRequest();
        CmtOnyxResponseDto cmtOnyxResponseDto = null;
        otHijaOnixRequestNew.setIdOtHija(numeroOtHija);
        GregorianCalendar c = new GregorianCalendar();
        Date fecha = new Date();
        c.setTime(fecha);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        headerRequest.setRequestDate(date2);
        headerRequest.setSystem("MER");
        otHijaOnixRequestNew.setHeaderRequest(headerRequest);
        PortManager portManager = new PortManager(wsURL, wsService);
        ConsultaList_Response consultaList_Response;
        try {
            consultaList_Response = portManager.consultarOtHija(otHijaOnixRequestNew);

            if (consultaList_Response != null
                    && consultaList_Response.getConsultaMglOnyxList() != null) {
                ConsultaMglOnyxList consultaMglOnyxList = consultaList_Response.getConsultaMglOnyxList();
                cmtOnyxResponseDto = new CmtOnyxResponseDto();
                cmtOnyxResponseDto.setNIT_Cliente(consultaMglOnyxList.getNIT_Cliente() != null ? consultaMglOnyxList.getNIT_Cliente() : "");
                cmtOnyxResponseDto.setNombre(consultaMglOnyxList.getNombre() != null ? consultaMglOnyxList.getNombre() : "");
                cmtOnyxResponseDto.setNombre_OT_Hija(consultaMglOnyxList.getNombre_OT_Hija() != null ? consultaMglOnyxList.getNombre_OT_Hija() : "");
                cmtOnyxResponseDto.setOTP(consultaMglOnyxList.getOTP());
                cmtOnyxResponseDto.setOTH(consultaMglOnyxList.getOTH());
                cmtOnyxResponseDto.setFechaCreacionOTH(consultaMglOnyxList.getFechaCreacionOTH());
                cmtOnyxResponseDto.setFechaCreacionOTP(consultaMglOnyxList.getFechaCreacionOTP());
                cmtOnyxResponseDto.setContactoTecnicoOTP(consultaMglOnyxList.getNombreContactoTecnicoPredOTP() != null && !consultaMglOnyxList.getNombreContactoTecnicoPredOTP().isEmpty() ? consultaMglOnyxList.getNombreContactoTecnicoPredOTP() : "");
                cmtOnyxResponseDto.setTelefonoContacto(consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP() != null && !consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP().isEmpty() ? consultaMglOnyxList.getTelefonoContactoTecnicoPredOTP() : "");
                cmtOnyxResponseDto.setDescripcion(consultaMglOnyxList.getDescripcion() != null && !consultaMglOnyxList.getDescripcion().isEmpty() ? consultaMglOnyxList.getDescripcion() : "");
                cmtOnyxResponseDto.setDireccion(consultaMglOnyxList.getDireccionFacturacion() != null && !consultaMglOnyxList.getDireccionFacturacion().isEmpty() ? consultaMglOnyxList.getDireccionFacturacion() : "");
                cmtOnyxResponseDto.setSegmento(consultaMglOnyxList.getSegmento() != null && !consultaMglOnyxList.getSegmento().isEmpty() ? consultaMglOnyxList.getSegmento() : "");
                cmtOnyxResponseDto.setTipoServicio(consultaMglOnyxList.getSolucionPrev() != null && !consultaMglOnyxList.getSolucionPrev().isEmpty() ? consultaMglOnyxList.getSolucionPrev() : "");
                cmtOnyxResponseDto.setServicios(consultaMglOnyxList.getServicios() != null && !consultaMglOnyxList.getServicios().isEmpty() ? consultaMglOnyxList.getServicios() : "");
                cmtOnyxResponseDto.setRecurrenteMensual(consultaMglOnyxList.getRecurrenteMensual() != null ? consultaMglOnyxList.getRecurrenteMensual() : new BigDecimal(0));
                cmtOnyxResponseDto.setCodigoServicio(consultaMglOnyxList.getCodigoServicio() != null && !consultaMglOnyxList.getCodigoServicio().isEmpty() ? consultaMglOnyxList.getCodigoServicio() : "");
                cmtOnyxResponseDto.setVendedor(consultaMglOnyxList.getVendedor() != null && !consultaMglOnyxList.getVendedor().isEmpty() ? consultaMglOnyxList.getVendedor() : "");
                cmtOnyxResponseDto.setTelefono(consultaMglOnyxList.getTelefono() != null && !consultaMglOnyxList.getTelefono().isEmpty() ? consultaMglOnyxList.getTelefono() : "");
                cmtOnyxResponseDto.setNotasOTH(consultaMglOnyxList.getNotasOTH() != null && !consultaMglOnyxList.getNotasOTH().isEmpty() ? consultaMglOnyxList.getNotasOTH() : "");
                cmtOnyxResponseDto.setEstadoOTH(consultaMglOnyxList.getEstadoOTH() != null && !consultaMglOnyxList.getEstadoOTH().isEmpty() ? consultaMglOnyxList.getEstadoOTH() : "");
                cmtOnyxResponseDto.setEstadoOTP(consultaMglOnyxList.getEstadoOTP() != null && !consultaMglOnyxList.getEstadoOTH().isEmpty() ? consultaMglOnyxList.getEstadoOTP() : "");
                cmtOnyxResponseDto.setFechaCompromisoOTP(consultaMglOnyxList.getFechaCompromisoOTP() != null && !consultaMglOnyxList.getFechaCompromisoOTP().isEmpty() ? consultaMglOnyxList.getFechaCompromisoOTP() : "");
                cmtOnyxResponseDto.setCodResolucion1OTP(consultaMglOnyxList.getCodResolucion1OTP() != null && !consultaMglOnyxList.getCodResolucion1OTP().isEmpty() ? consultaMglOnyxList.getCodResolucion1OTP() : "");
                cmtOnyxResponseDto.setCodResolucion2OTP(consultaMglOnyxList.getCodResolucion2OTP() != null && !consultaMglOnyxList.getCodResolucion2OTP().isEmpty() ? consultaMglOnyxList.getCodResolucion2OTP() : "");
                cmtOnyxResponseDto.setCodResolucion3OTP(consultaMglOnyxList.getCodResolucion3OTP() != null && !consultaMglOnyxList.getCodResolucion3OTP().isEmpty() ? consultaMglOnyxList.getCodResolucion3OTP() : "");
                cmtOnyxResponseDto.setCodResolucion4OTP(consultaMglOnyxList.getCodResolucion4OTP() != null && !consultaMglOnyxList.getCodResolucion4OTP().isEmpty() ? consultaMglOnyxList.getCodResolucion4OTP() : "");
                cmtOnyxResponseDto.setCodResolucion1OTH(consultaMglOnyxList.getCodResolucion1OTH() != null && !consultaMglOnyxList.getCodResolucion1OTH().isEmpty() ? consultaMglOnyxList.getCodResolucion1OTH() : "");
                cmtOnyxResponseDto.setCodResolucion2OTH(consultaMglOnyxList.getCodResolucion2OTH() != null && !consultaMglOnyxList.getCodResolucion2OTH().isEmpty() ? consultaMglOnyxList.getCodResolucion2OTH() : "");
                cmtOnyxResponseDto.setCodResolucion3OTH(consultaMglOnyxList.getCodResolucion3OTH() != null && !consultaMglOnyxList.getCodResolucion3OTH().isEmpty() ? consultaMglOnyxList.getCodResolucion3OTH() : "");
                cmtOnyxResponseDto.setCodResolucion4OTH(consultaMglOnyxList.getCodResolucion4OTH() != null && !consultaMglOnyxList.getCodResolucion4OTH().isEmpty() ? consultaMglOnyxList.getCodResolucion4OTH() : "");
                cmtOnyxResponseDto.setCodProyecto(consultaMglOnyxList.getCodProyecto() != null && !consultaMglOnyxList.getCodProyecto().isEmpty() ? consultaMglOnyxList.getCodProyecto() : "");
                cmtOnyxResponseDto.setEmailCTec(consultaMglOnyxList.getEmailContactTecnicoPredOTP() != null && !consultaMglOnyxList.getEmailContactTecnicoPredOTP().isEmpty() ? consultaMglOnyxList.getEmailContactTecnicoPredOTP() : "");
                cmtOnyxResponseDto.setaImplement(consultaMglOnyxList.getAliadoImplementacion() != null && !consultaMglOnyxList.getAliadoImplementacion().isEmpty() ? consultaMglOnyxList.getAliadoImplementacion() : "");
                cmtOnyxResponseDto.setRegionalDestino(consultaMglOnyxList.getRegionalDestino() != null && !consultaMglOnyxList.getRegionalDestino().isEmpty() ? consultaMglOnyxList.getRegionalDestino() : "");
                cmtOnyxResponseDto.setCiudadFacturacion(consultaMglOnyxList.getCiudadFacturacion() != null && !consultaMglOnyxList.getCiudadFacturacion().isEmpty() ? consultaMglOnyxList.getCiudadFacturacion() : "");
            }
        } catch (ApplicationException ex) {
            String msg = "Error al consultar OT en Onyx: '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al consultar OT en Onyx: '. EX000: " + ex.getMessage(), ex);

        }
        return cmtOnyxResponseDto;
    }

    public int countByFiltroParaGestionExportarOt(
            List<BigDecimal> estadosxflujoList, BigDecimal gpoDepartamento,
            BigDecimal gpoCiudad, BigDecimal idTipoOt, BigDecimal estadoInternoBasicaId,
            BigDecimal tecnologia, String codigoReg) throws ApplicationException {

        return dao.countByFiltroParaGestionExportarOt(estadosxflujoList, gpoDepartamento,
                gpoCiudad, idTipoOt, estadoInternoBasicaId, tecnologia, codigoReg);
    }

    /**
     * Busqueda de todas las Ordenes de trabajo por id Enlace y tecnologia
     * GPON/FOU
     *
     * @author Victor Bocanegra
     * @param idEnlace valor id del enlace para la busqueda
     * @param tecnologias lista de tecnologia para la busqueda
     * @return Retorna una lista de Ordenes de trabajo
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByIdEnlaceAndTecnologia(String idEnlace,
            List<BigDecimal> tecnologias)
            throws ApplicationException {
        return dao.findByIdEnlaceAndTecnologia(idEnlace, tecnologias);
    }

    public void crearInfoTecnica(List<CmtTecnologiaSubMgl> listTecnologias, String user, int perfil)
            throws ApplicationException {

        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtInfoTecnicaMglManager infoTecnicaMglManager = new CmtInfoTecnicaMglManager();
        CmtBasicaMgl cmtBasicaMglInfoN1Nodo = basicaMglManager.findByCodigoInternoApp(Constant.NIVEL_UNO_NODO);
        CmtBasicaMgl cmtBasicaMglInfoN1Nap = basicaMglManager.findByCodigoInternoApp(Constant.NIVEL_UNO_NAP);
        CmtInfoTecnicaMgl cmtInfoTecni;

        if (listTecnologias != null
                && !listTecnologias.isEmpty()) {

            for (CmtTecnologiaSubMgl tecnoSub : listTecnologias) {

                if (tecnoSub != null
                        && tecnoSub.getTecnoSubedificioId() != null) {

                    cmtInfoTecni = infoTecnicaMglManager.
                            findBySubEdificioIdAndTecnoSub(tecnoSub.getSubedificioId(), tecnoSub);

                    if (cmtInfoTecni == null) {

                        cmtInfoTecni = new CmtInfoTecnicaMgl();
                        cmtInfoTecni.setIdSubedificio(tecnoSub.getSubedificioId());

                        if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp() != null) {

                            if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOG")) {
                                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                            } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOU")) {
                                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                            } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_HOST")) {
                                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                            } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_BI")) {
                                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                            } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_DTH")) {
                                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                            } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FTH")) {
                                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                            } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_UNI")) {
                                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                            } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOR")) {
                                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                            }

                            if (cmtInfoTecni != null && cmtInfoTecni.getBasicaIdInfoN1() != null) {
                                cmtInfoTecni.setFechaCreacion(new Date());
                                cmtInfoTecni.setEstadoRegistro(1);
                                cmtInfoTecni.setPerfilEdicion(0);
                                cmtInfoTecni.setTecnologiaSubMglObj(tecnoSub);
                                cmtInfoTecni = infoTecnicaMglManager.create(cmtInfoTecni, user, perfil);
                            }
                        }
                    }

                }

            }
        }
    }

    /**
     * Busca una Orden de Trabajo por estados X flujo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findByEstadosXFlujoAndIdOt(List<BigDecimal> estadosxflujoList,
            BigDecimal idOt) throws ApplicationException {
        return dao.findByEstadosXFlujoAndIdOt(estadosxflujoList, idOt);
    }

    public void actualizaCreaInfoTecnica(CmtOrdenTrabajoMgl ot,
            List<CmtSubEdificiosVt> subEdificiosVts, String usuario, int perfil) throws ApplicationException {

        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        CmtInfoTecnicaMglManager infoTecnicaMglManager = new CmtInfoTecnicaMglManager();

        CmtBasicaMgl cmtBasicaMglInfoN1Nodo = basicaMglManager.findByCodigoInternoApp(Constant.NIVEL_UNO_NODO);
        CmtBasicaMgl cmtBasicaMglInfoN1Nap = basicaMglManager.findByCodigoInternoApp(Constant.NIVEL_UNO_NAP);

        if (subEdificiosVts != null
                && !subEdificiosVts.isEmpty()) {

            for (CmtSubEdificiosVt subVts : subEdificiosVts) {

                CmtTecnologiaSubMgl tecnologiaSubMgl
                        = tecnologiaSubMglManager.findBySubEdificioTecnologia(subVts.getSubEdificioObj(),
                                subVts.getVtObj().getOtObj().getBasicaIdTecnologia());

                if (tecnologiaSubMgl != null
                        && tecnologiaSubMgl.getTecnoSubedificioId() != null) {

                    //Buscamos la info tecnica
                    CmtInfoTecnicaMgl cmtInfoTecni = infoTecnicaMglManager.
                            findBySubEdificioIdAndTecnoSub(subVts.getSubEdificioObj(), tecnologiaSubMgl);

                    if (cmtInfoTecni == null) {
                        //creo infotecnica
                        cmtInfoTecni = new CmtInfoTecnicaMgl();
                        cmtInfoTecni.setIdSubedificio(subVts.getSubEdificioObj());

                        if (subVts.getVtObj().getOtObj().getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase("@B_FOG")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                        } else if (subVts.getVtObj().getOtObj().getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase("@B_FOU")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                        } else if (subVts.getVtObj().getOtObj().getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase("@B_HOST")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (subVts.getVtObj().getOtObj().getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase("@B_BI")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (subVts.getVtObj().getOtObj().getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase("@B_DTH")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (subVts.getVtObj().getOtObj().getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase("@B_FTH")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (subVts.getVtObj().getOtObj().getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase("@B_UNI")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (subVts.getVtObj().getOtObj().getBasicaIdTecnologia()
                                .getIdentificadorInternoApp().equalsIgnoreCase("@B_FOR")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                        }

                        cmtInfoTecni.setFechaCreacion(new Date());
                        cmtInfoTecni.setEstadoRegistro(1);
                        cmtInfoTecni.setPerfilEdicion(0);

                        cmtInfoTecni.setTecnologiaSubMglObj(tecnologiaSubMgl);
                        cmtInfoTecni = infoTecnicaMglManager.create(cmtInfoTecni, usuario, perfil);
                        if (cmtInfoTecni.getId() != null) {
                            LOGGER.info("Registro infotecnica creado satisfatoriamente");
                        }
                    }

                }

            }

        }
    }

    public List<CmtOrdenTrabajoMgl> findOtAcometidaSubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {
        return dao.findOtAcometidaSubEdificio(cmtSubEdificioMgl);
    }
}
